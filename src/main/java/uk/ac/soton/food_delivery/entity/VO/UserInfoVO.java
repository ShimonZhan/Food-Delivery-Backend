package uk.ac.soton.food_delivery.entity.VO;

import lombok.Data;
import uk.ac.soton.food_delivery.enums.UserRole;

/**
 * @author ShimonZhan
 * @since 2022-03-16 21:34:58
 */
@Data
public class UserInfoVO {
    private Long id;

    private String username;

    private UserRole roleId;

    private String email;

    private String nickName;

    private String avatar;
}
