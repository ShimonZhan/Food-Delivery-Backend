package uk.ac.soton.food_delivery.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import uk.ac.soton.food_delivery.entity.DO.LoginUser;
import uk.ac.soton.food_delivery.enums.UserRole;

/**
 * @author ShimonZhan
 * @since 2022-04-22 18:58:10
 */
public class RoleUtils {
    public static boolean isAdmin() {
        return isRole(UserRole.ROLE_ADMIN);
    }

    private static boolean isRole(UserRole role) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser().getRoleId().equals(role);
    }
}
