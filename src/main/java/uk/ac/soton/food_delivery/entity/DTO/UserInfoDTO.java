package uk.ac.soton.food_delivery.entity.DTO;

import lombok.Data;
import uk.ac.soton.food_delivery.entity.DO.User;
import uk.ac.soton.food_delivery.enums.UserRole;

/**
 * @author ShimonZhan
 * @since 2022-03-16 21:55:27
 */
@Data
public class UserInfoDTO {
    private Long id;

    private String username;

    private UserRole roleId;

    private String email;

    private String nickName;

    public static User toUser(UserInfoDTO userInfoDTO) {
        if (userInfoDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userInfoDTO.getId());
        user.setUsername(userInfoDTO.getUsername());
        user.setRoleId(userInfoDTO.getRoleId());
        user.setEmail(userInfoDTO.getEmail());
        user.setNickName(userInfoDTO.getNickName());
        return user;
    }
}
