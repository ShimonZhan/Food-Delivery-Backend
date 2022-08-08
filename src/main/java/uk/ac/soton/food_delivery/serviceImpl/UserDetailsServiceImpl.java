package uk.ac.soton.food_delivery.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.ac.soton.food_delivery.entity.DO.LoginUser;
import uk.ac.soton.food_delivery.entity.DO.User;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;
import uk.ac.soton.food_delivery.mapper.AuthorityMapper;
import uk.ac.soton.food_delivery.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-11 20:49:58
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // query user info
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new ServicesException(ResultCode.USERNAME_PASSWORD_ERROR);
        }
        // query user authorities info
        List<String> list = authorityMapper.selectPermsByUserId(user.getId());
        // Encapsulate data as UserDetails
        return new LoginUser(user, list);
    }
}
