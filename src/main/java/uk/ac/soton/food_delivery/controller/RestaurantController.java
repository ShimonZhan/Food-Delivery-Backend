package uk.ac.soton.food_delivery.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DO.Address;
import uk.ac.soton.food_delivery.entity.DO.Restaurant;
import uk.ac.soton.food_delivery.entity.DTO.AddRestaurantDTO;
import uk.ac.soton.food_delivery.entity.filter.SearchRestaurantFilter;
import uk.ac.soton.food_delivery.enums.RestaurantStatus;
import uk.ac.soton.food_delivery.service.AddressService;
import uk.ac.soton.food_delivery.service.CategoryService;
import uk.ac.soton.food_delivery.service.RestaurantService;
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
@RequestMapping("/restaurant")
public class RestaurantController {
    @Resource
    private RestaurantService restaurantService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private AddressService addressService;


    @PreAuthorize("@ex.hasAuthority('system:restaurant:add')")
    @PostMapping("/addRestaurant")
    public R addRestaurant(@RequestBody AddRestaurantDTO restaurantDTO) {
        return restaurantService.addRestaurant(restaurantDTO);
    }

    @PostMapping("/searchRestaurants") // filter
    public R searchRestaurants(@RequestBody SearchRestaurantFilter filter) {
        return restaurantService.searchRestaurants(filter);
    }

    @GetMapping("/getRestaurant")
    public R getRestaurant(String restaurantId) {
        return restaurantService.getRestaurant(restaurantId);
    }

    @PreAuthorize("@ex.hasAuthority('system:restaurant:update')")
    @PutMapping("/updateRestaurantInfo")
    public R updateRestaurantInfo(@RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurantInfo(restaurant);
    }

    @PreAuthorize("@ex.hasAuthority('system:restaurant:update')")
    @PutMapping("/updateRestaurantAddress")
    public R updateRestaurantAddress(@RequestBody Address address) {
        return addressService.updateRestaurantAddress(address);
    }

    @PreAuthorize("@ex.hasAuthority('system:restaurant:update')")
    @PutMapping("/updateRestaurantAvatar")
    public R updateRestaurantAvatar(Long restaurantId, @RequestPart("file") MultipartFile file) {
        return restaurantService.updateRestaurantAvatar(restaurantId, file);
    }

    @PreAuthorize("@ex.hasAuthority('system:restaurant:update')")
    @PutMapping("/updateRestaurantCertification")
    public R updateRestaurantCertification(Long restaurantId, @RequestPart("file") MultipartFile file) {
        return restaurantService.updateRestaurantCertification(restaurantId, file);
    }

    @DeleteMapping("/removeRestaurant")
    public R removeRestaurant(Long restaurantId) {
        return restaurantService.removeRestaurant(restaurantId);
    }

    @GetMapping("/getCategories")
    public R getCategories() {
        return R.ok().data("categories", categoryService.list());
    }

    @PutMapping("/adminChangeRestaurantStatus")
    @PreAuthorize("@ex.hasAuthority('system:admin:updateRestaurant')")
    public R adminChangeRestaurantStatus(Long restaurantId, RestaurantStatus restaurantStatus) {
        return restaurantService.adminChangeRestaurantStatus(restaurantId, restaurantStatus);
    }

    @GetMapping("/adminGetRestaurants")
    @PreAuthorize("@ex.hasAuthority('system:admin:getRestaurants')")
    public R adminGetRestaurants(RestaurantStatus restaurantStatus, Long pageCurrent, Long pageSize) {
        return restaurantService.adminGetRestaurants(restaurantStatus, pageCurrent, pageSize);
    }

    @GetMapping("/ownerGetRestaurants")
    @PreAuthorize("@ex.hasAuthority('system:restaurant:ownerGetRestaurants')")
    public R ownerGetRestaurants(Long ownerId, RestaurantStatus restaurantStatus, Long pageCurrent, Long pageSize) {
        return restaurantService.ownerGetRestaurants(ownerId, restaurantStatus, pageCurrent, pageSize);
    }

}

