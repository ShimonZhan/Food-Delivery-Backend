package uk.ac.soton.food_delivery.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.soton.food_delivery.entity.DO.LoginUser;
import uk.ac.soton.food_delivery.entity.DO.User;
import uk.ac.soton.food_delivery.service.MailService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-07 02:22:16
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private MailService mailService;

    @GetMapping("/helloWorld")
    public R helloWorld() {
        return R.ok().message("hello world");
    }

    @GetMapping("/helloWorld2")
    public R helloWorld2() {
        return R.ok().message("hello world2");
    }

    @GetMapping("/testLoginRole")
    @PreAuthorize("isAuthenticated()")
    public R testLoginRole() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User user = loginUser.getUser();
        return R.ok().data("role", user.getRoleId()).data("username", user.getUsername());
    }

}
