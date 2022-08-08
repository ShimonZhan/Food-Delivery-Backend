package uk.ac.soton.food_delivery.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uk.ac.soton.food_delivery.entity.DO.LoginUser;

import java.util.List;

/**
 * @author ShimonZhan
 * @since 2022-03-13 00:16:57
 */
@Component("ex")
public class MySecurityExpressionRoot {

    public final boolean hasAuthority(String authority) {
        // get current user authorities
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        // Determine whether the user has permissions
        for (String permission : permissions) {
            permission = "^" + permission.replaceAll("\\*", "[^:]*") + "$";
            if (authority.matches(permission)) {
                return true;
            }
        }
        return false;
    }
}
