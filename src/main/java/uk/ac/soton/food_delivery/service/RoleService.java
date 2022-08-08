package uk.ac.soton.food_delivery.service;

import uk.ac.soton.food_delivery.entity.DO.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import uk.ac.soton.food_delivery.enums.UserRole;
import uk.ac.soton.food_delivery.utils.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-12
 */
public interface RoleService extends IService<Role> {

    R getRoleAuthorities(UserRole userRole);

    R grantAuthority(UserRole userRole, String name, String perm);

    R deleteAuthority(UserRole userRole, String perm);
}
