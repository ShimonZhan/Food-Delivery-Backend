package uk.ac.soton.food_delivery.serviceImpl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DO.LoginUser;
import uk.ac.soton.food_delivery.entity.DO.User;
import uk.ac.soton.food_delivery.entity.DTO.LoginUserDTO;
import uk.ac.soton.food_delivery.entity.DTO.UserInfoDTO;
import uk.ac.soton.food_delivery.entity.DTO.UserRegisterDTO;
import uk.ac.soton.food_delivery.entity.DTO.UserValidateDTO;
import uk.ac.soton.food_delivery.entity.VO.UserInfoVO;
import uk.ac.soton.food_delivery.enums.UserStatus;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;
import uk.ac.soton.food_delivery.mapper.UserMapper;
import uk.ac.soton.food_delivery.service.MailService;
import uk.ac.soton.food_delivery.service.OssService;
import uk.ac.soton.food_delivery.service.UserService;
import uk.ac.soton.food_delivery.utils.JwtUtil;
import uk.ac.soton.food_delivery.utils.R;
import uk.ac.soton.food_delivery.utils.RedisCache;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Random;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Value("${host}")
    private String host;

    @Resource
    private MailService mailService;

    @Resource
    private OssService ossService;

    @Override
    public R login(LoginUserDTO user) {
        // AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果认证没通过,给出对应的提示
        if (authenticate == null) {
            throw new ServicesException(ResultCode.USERNAME_PASSWORD_ERROR);
        }
        // 如果认证通过了,使用userid生成一个jwt jwt存入R运回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId.toString());
        // 把完整的用户信息存入redis userid作为key
        redisCache.setCacheObject("login:" + userId, loginUser);
        UserInfoVO userInfoVO = User.toUserInfoVO(loginUser.getUser());
        return R.ok().message("Login success").data("token", jwt).data("userInfo", userInfoVO);
    }

    @Override
    public R register(UserRegisterDTO userRegisterDTO) {
        User user = UserRegisterDTO.toUser(userRegisterDTO);
        User user1 = getUserByUsername(user.getUsername());
        if (user1 != null) {
            throw new ServicesException(ResultCode.USER_NAME_EXISTED);
        }
        User user2 = getUserByEmail(user.getEmail());
        if (user2 != null) {
            throw new ServicesException(ResultCode.USER_EMAIL_EXISTED);
        }
        user.setStatus(UserStatus.INACTIVATED); // to inactivate and send mail
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAvatar(String.format("https://oss.fd.shimonzhan.com/user/default/avatar%03d.png!avatar", new Random().nextInt(100)));
        save(user);
        sendActivateMail(user.getEmail());
        return R.ok().message("Registration successful");
    }

    @Override
    public R logout() {
        // get userId form SecurityContextHolder
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        // delete user info in redis
        redisCache.deleteObject("login:" + userId);
        return R.ok().message("Logout successful");
    }

    @Override
    public User getUserByUsername(String username) {
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }

    @Override
    public User getUserByEmail(String email) {
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, email));
    }

    @Override
    public R activateAccount(String token) {
        User user = checkToken(token);
        if (user.getStatus() != UserStatus.INACTIVATED) {
            throw new ServicesException(ResultCode.USER_ACCOUNT_ALREADY_ACTIVATED);
        }
        user.setStatus(UserStatus.NORMAL);
        updateById(user);
        return R.ok().message("User account activated successfully");
    }

    @Override
    public R sendActivateMail(String email) {
        User user = getUserByEmail(email);
        if (user == null) {
            throw new ServicesException(ResultCode.USER_EMAIL_NOT_EXISTED);
        } else if (user.getStatus() != UserStatus.INACTIVATED) {
            throw new ServicesException(ResultCode.USER_ACCOUNT_ALREADY_ACTIVATED);
        }
        // Create JWT token
        UserValidateDTO userValidateDTO = new UserValidateDTO(user.getId(), user.getEmail(), user.getUsername());
        String token = JwtUtil.createJWT(userValidateDTO, 172800000L); // 2d  expire
        // send mail
        String link = host + "/activate?token=" + token; //改为前端地址
        mailService.sendMail(user.getEmail(), link, "Activate Your Account", user.getNickName());
        return R.ok().message("Send activate mail Successfully");//.data("link", link);
    }

    @Override
    public R sendForgetPasswordMail(String email) {
        User user = getUserByEmail(email);
        if (user == null) {
            throw new ServicesException(ResultCode.USER_EMAIL_NOT_EXISTED);
        }
        // Create JWT token
        UserValidateDTO userValidateDTO = new UserValidateDTO(user.getId(), user.getEmail(), user.getUsername());
        String token = JwtUtil.createJWT(userValidateDTO, 172800000L); // 2d  expire
        // TODO send mail
        String link = host + "/forgetpswd?token=" + token;
        mailService.sendMail(user.getEmail(), link, "Change Your Password", user.getNickName());
        return R.ok().message("Send forget password mail Successfully");//.data("link", link);
    }

    @Override
    public R changeForgetPassword(String newPassword, String token) {
        User user = checkToken(token);
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new ServicesException(ResultCode.USER_PASSWORD_SAME);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        updateById(user);
        LoginUser loginUser = redisCache.getCacheObject("login:" + user.getId());
        if (loginUser != null) {
            loginUser.setUser(this.getById(user.getId()));
            redisCache.setCacheObject("login:" + user.getId(), loginUser);
        }
        return R.ok().message("Successfully change password");
    }

    @Override
    public R updateUserInfo(UserInfoDTO userInfoDTO) {
        if (userInfoDTO.getId() == null) {
            throw new ServicesException(ResultCode.USER_ID_REQUIRED);
        }
        User user = UserInfoDTO.toUser(userInfoDTO);
        LoginUser loginUser = redisCache.getCacheObject("login:" + userInfoDTO.getId());
        if (loginUser == null) {
            throw new ServicesException(ResultCode.USER_ID_ERROR);
        }
        User oldUser = loginUser.getUser();
        if (Objects.equals(oldUser.getEmail(), user.getEmail()) ||
                Objects.equals(oldUser.getUsername(), user.getUsername())) {
            return R.ok();
        } else if (getUserByEmail(user.getEmail()) != null) {
            throw new ServicesException(ResultCode.USER_EMAIL_EXISTED);
        } else if (getUserByUsername(user.getUsername()) != null) {
            throw new ServicesException(ResultCode.USER_NAME_EXISTED);
        }
        updateById(user);
        // update redis
        loginUser.setUser(this.getById(userInfoDTO.getId()));
        redisCache.setCacheObject("login:" + userInfoDTO.getId(), loginUser);
        return R.ok();
    }

    @Override
    public R getUserInfo(Long userId) {
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if (loginUser == null) {
            throw new ServicesException(ResultCode.USER_ID_ERROR);
        }

        User user = loginUser.getUser();
        UserInfoVO userInfoVO = User.toUserInfoVO(user);
        return R.ok().data("userInfo", userInfoVO);
    }

    @Override
    public R updateAvatar(Long userId, MultipartFile file) {
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if (loginUser == null) {
            throw new ServicesException(ResultCode.USER_ID_ERROR);
        }
        String url = ossService.uploadFile("user/" + userId, file, "!avatar");
        User user = User.builder().id(userId).avatar(url).build();
        updateById(user);
        loginUser.setUser(this.getById(userId));
        redisCache.setCacheObject("login:" + userId, loginUser);
        return R.ok().data("avatar", url);
    }

    @Override
    public R changePassword(Long userId, String oldPassword, String newPassword) {
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if (loginUser == null) {
            throw new ServicesException(ResultCode.USER_ID_ERROR);
        }
        User user = loginUser.getUser();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ServicesException(ResultCode.USER_OLD_PASSWORD_ERROR);
        } else if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new ServicesException(ResultCode.USER_PASSWORD_SAME);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        updateById(user);
        loginUser.setUser(this.getById(userId));
        redisCache.setCacheObject("login:" + userId, loginUser);
        return R.ok().message("Successfully change password");
    }

    private User checkToken(String token) {
        UserValidateDTO userValidateDTO = null;
        try {
            userValidateDTO = JwtUtil.parseJWT(token, UserValidateDTO.class);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new ServicesException(ResultCode.TOKEN_VALIDATE_EXPIRED);
        } catch (JwtException e) {
            throw new ServicesException(ResultCode.TOKEN_VALIDATE_FAILED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert userValidateDTO != null;
        return getById(userValidateDTO.getId());
    }

}
