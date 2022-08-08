package uk.ac.soton.food_delivery.entity.DTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import uk.ac.soton.food_delivery.entity.DO.User;
import uk.ac.soton.food_delivery.enums.UserRole;

/**
 * @author ShimonZhan
 * @since 2022-03-16 20:49:10
 */
@ApiModel
@Data
public class UserRegisterDTO {
    private String username;

    private String password;

    private UserRole roleId;

    private String email;

    private String nickName;

    public static User toUser(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        user.setRoleId(userRegisterDTO.getRoleId());
        user.setEmail(userRegisterDTO.getEmail());
        user.setNickName(userRegisterDTO.getNickName());
        return user;
    }
}
