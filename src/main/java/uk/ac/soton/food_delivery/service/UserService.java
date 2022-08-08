package uk.ac.soton.food_delivery.service;

import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DTO.UserInfoDTO;
import uk.ac.soton.food_delivery.entity.DTO.UserRegisterDTO;
import uk.ac.soton.food_delivery.entity.DO.User;
import com.baomidou.mybatisplus.extension.service.IService;
import uk.ac.soton.food_delivery.entity.DTO.LoginUserDTO;
import uk.ac.soton.food_delivery.utils.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
public interface UserService extends IService<User> {
    User getUserByUsername(String username);

    User getUserByEmail(String email);

    R login(LoginUserDTO user);

    R register(UserRegisterDTO userRegisterDTO);

    R logout();

    R activateAccount(String token);

    R sendActivateMail(String email);

    R sendForgetPasswordMail(String email);

    R changeForgetPassword(String newPassword, String token);

    R updateUserInfo(UserInfoDTO userInfo);

    R getUserInfo(Long userId);

    R updateAvatar(Long userId, MultipartFile file);

    R changePassword(Long userId, String oldPassword, String newPassword);
}
