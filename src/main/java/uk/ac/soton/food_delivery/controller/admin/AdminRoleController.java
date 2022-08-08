package uk.ac.soton.food_delivery.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uk.ac.soton.food_delivery.enums.UserRole;
import uk.ac.soton.food_delivery.service.RoleService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;

/**
 * @author ShimonZhan
 * @since 2022-03-16 13:18:59
 */
@RestController
@RequestMapping("/admin/role")
@PreAuthorize("@ex.hasAuthority('system:role:all')")
public class AdminRoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("/getRoleAuthorities/{userRole}")
    public R getRoleAuthorities(@PathVariable UserRole userRole) {
        return roleService.getRoleAuthorities(userRole);
    }

    @PostMapping("/grantAuthority")
    public R grantAuthority(UserRole userRole, String name, String perm) {
        return roleService.grantAuthority(userRole, name, perm);
    }

    @DeleteMapping("/deleteAuthority/{userRole}/{perm}")
    public R deleteAuthority(@PathVariable UserRole userRole, @PathVariable String perm) {
        return roleService.deleteAuthority(userRole, perm);
    }
}
