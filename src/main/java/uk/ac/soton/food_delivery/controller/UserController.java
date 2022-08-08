package uk.ac.soton.food_delivery.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DTO.LoginUserDTO;
import uk.ac.soton.food_delivery.entity.DTO.UserInfoDTO;
import uk.ac.soton.food_delivery.entity.DTO.UserRegisterDTO;
import uk.ac.soton.food_delivery.service.UserService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/user")
@PreAuthorize("isAnonymous()")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public R register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.register(userRegisterDTO);
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginUserDTO user) {
        return userService.login(user);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logout")
    public R logout() {
        return userService.logout();
    }

    @GetMapping("/sendActivateMail")
    public R sendActivateMail(@RequestParam String email) {
        return userService.sendActivateMail(email);
    }

    @GetMapping("/sendForgetPasswordMail")
    public R sendForgetPasswordMail(@RequestParam String email) {
        return userService.sendForgetPasswordMail(email);
    }

    @GetMapping("/activate")
    public R activateAccount(@RequestParam String token) {
        return userService.activateAccount(token);
    }

    @PutMapping("/changeForgetPassword")
    public R activateAccount(String newPassword, String token) {
        return userService.changeForgetPassword(newPassword, token);
    }

    @PreAuthorize("@ex.hasAuthority('system:user:update')")
    @PutMapping("/updateUserInfo")
    public R updateUserInfo(@RequestBody UserInfoDTO userInfo) {
        return userService.updateUserInfo(userInfo);
    }

    @PreAuthorize("@ex.hasAuthority('system:user:getUserInfo')")
    @GetMapping("/getUserInfo")
    public R getUserInfo(Long userId) {
        return userService.getUserInfo(userId);
    }

    @PreAuthorize("@ex.hasAuthority('system:user:updateAvatar')")
    @PostMapping("/updateAvatar")
    public R updateAvatar(@RequestParam Long userId, @RequestPart("file") MultipartFile file) {
        return userService.updateAvatar(userId, file);
    }

    @PreAuthorize("@ex.hasAuthority('system:user:updatePassword')")
    @PutMapping("/changePassword")
    public R changePassword(Long userId, String oldPassword, String newPassword) {
        return userService.changePassword(userId, oldPassword, newPassword);
    }


}
