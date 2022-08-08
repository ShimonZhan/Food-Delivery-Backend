package uk.ac.soton.food_delivery.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DO.Menu;
import uk.ac.soton.food_delivery.service.MenuService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;

/**
 * @author ShimonZhan
 * @since 2022-04-23 02:48:24
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/getMenusByRestaurants")
    public R getMenusByRestaurants(Long restaurantId) {
        return menuService.getMenusByRestaurantId(restaurantId);
    }

    @PreAuthorize("@ex.hasAuthority('system:menu:add')")
    @PostMapping("/addMenu")
    public R addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    @PreAuthorize("@ex.hasAuthority('system:menu:remove')")
    @DeleteMapping("/removeMenu")
    public R removeMenu(Long menuId) {
        return menuService.removeMenu(menuId);
    }

    @PreAuthorize("@ex.hasAuthority('system:menu:update')")
    @PutMapping("/updateMenu")
    public R updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @PreAuthorize("@ex.hasAuthority('system:menu:update')")
    @PutMapping("/updateMenuPhoto")
    public R updateMenuPhoto(@RequestParam Long menuId, @RequestPart("file") MultipartFile file) {
        return menuService.updateMenuPhoto(menuId, file);
    }

    @GetMapping("/searchMenus")
    public R searchMenu(Long restaurantId, String name, Long pageCurrent, Long pageSize) {
        return menuService.searchMenus(restaurantId, name, pageCurrent, pageSize);
    }
}
