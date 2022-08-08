package uk.ac.soton.food_delivery.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import uk.ac.soton.food_delivery.entity.VO.UserInfoVO;
import uk.ac.soton.food_delivery.enums.UserRole;
import uk.ac.soton.food_delivery.enums.UserStatus;

/**
 * <p>
 *
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
@ApiModel(value = "User对象")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("role_id")
    private UserRole roleId;

    @TableField("email")
    private String email;

    @TableField("nick_name")
    private String nickName;

    @TableField("avatar")
    private String avatar;

    @TableField("status")
    private UserStatus status;

    public static UserInfoVO toUserInfoVO(User user) {
        if (user == null) {
            return null;
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(user.getId());
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setRoleId(user.getRoleId());
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setNickName(user.getNickName());
        userInfoVO.setAvatar(user.getAvatar());
        return userInfoVO;
    }

}
