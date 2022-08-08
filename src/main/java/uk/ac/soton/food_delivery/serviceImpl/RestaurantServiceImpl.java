package uk.ac.soton.food_delivery.serviceImpl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DO.Address;
import uk.ac.soton.food_delivery.entity.DO.Menu;
import uk.ac.soton.food_delivery.entity.DO.Restaurant;
import uk.ac.soton.food_delivery.entity.DO.User;
import uk.ac.soton.food_delivery.entity.DTO.AddRestaurantDTO;
import uk.ac.soton.food_delivery.entity.VO.RestaurantVO;
import uk.ac.soton.food_delivery.entity.filter.SearchRestaurantFilter;
import uk.ac.soton.food_delivery.enums.RestaurantStatus;
import uk.ac.soton.food_delivery.enums.UserRole;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;
import uk.ac.soton.food_delivery.mapper.RestaurantMapper;
import uk.ac.soton.food_delivery.service.*;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Service
@Slf4j
public class RestaurantServiceImpl extends ServiceImpl<RestaurantMapper, Restaurant> implements RestaurantService {

    @Resource
    private UserService userService;

    @Resource
    private OssService ossService;

    @Resource
    private AddressService addressService;

    @Resource
    private MenuService menuService;

    @Resource
    private RestaurantMapper restaurantMapper;

    @Resource
    private CategoryService categoryService;

    @Override
    public R addRestaurant(AddRestaurantDTO restaurantDTO) {
        Long ownerId = restaurantDTO.getOwnerId();
        User owner = userService.getById(ownerId);
        if (owner == null) {
            throw new ServicesException(ResultCode.USER_ID_ERROR);
        } else if (!owner.getRoleId().equals(UserRole.ROLE_RESTAURANT_OWNER)) {
            throw new ServicesException(ResultCode.PERM_NOT_EXIST);
        }
        Restaurant restaurant = AddRestaurantDTO.toRestaurantDO(restaurantDTO);
        restaurant.setAvatar("https://oss.fd.shimonzhan.com/restaurant/avatar/default.webp");
        restaurant.setMark(-1f);
        restaurant.setStatus(RestaurantStatus.UNVERIFIED); //admin verify the certification
        save(restaurant);
        return R.ok().data("restaurantId", restaurant.getId());
    }

    @Override
    public R updateRestaurantAvatar(Long restaurantId, MultipartFile file) {
        Restaurant restaurant = getById(restaurantId);
        if (restaurant == null) {
            throw new ServicesException(ResultCode.RESTAURANT_NOT_EXIST);
        }
        String link = ossService.uploadFile("restaurant/avatar/" + restaurantId, file, "");
        restaurant.setAvatar(link);
        updateById(restaurant);
        return R.ok();
    }

    @Override
    public R updateRestaurantCertification(Long restaurantId, MultipartFile file) {
        Restaurant restaurant = getById(restaurantId);
        if (restaurant == null) {
            throw new ServicesException(ResultCode.RESTAURANT_NOT_EXIST);
        }
        String link = ossService.uploadFile("restaurant/certification/" + restaurantId, file, "");
        restaurant.setCertificationFile(link);
        updateById(restaurant);
        return R.ok();
    }

    @Override
    public R updateRestaurantInfo(Restaurant restaurant) {
        if (getById(restaurant.getId()) == null) {
            throw new ServicesException(ResultCode.RESTAURANT_NOT_EXIST);
        }
        updateById(restaurant);
        return R.ok();
    }

    @Override
    public R getRestaurant(String restaurantId) {
        Restaurant restaurant = getById(restaurantId);
        if (restaurant == null) {
            throw new ServicesException(ResultCode.RESTAURANT_NOT_EXIST);
        }

        Address address = addressService.getById(restaurant.getAddressId());
        List<Menu> menus = menuService.list(Wrappers.<Menu>lambdaQuery().eq(Menu::getRestaurantId, restaurantId));
        return R.ok().data("restaurant", restaurant).data("address", address).data("menus", menus);
    }

    @Override
    public R removeRestaurant(Long restaurantId) {
        Restaurant restaurant = getById(restaurantId);
        if (restaurant.getAddressId() != null) {
            addressService.removeById(restaurant.getAddressId());
        }
        menuService.remove(Wrappers.<Menu>lambdaQuery().eq(Menu::getRestaurantId, restaurantId));
        removeById(restaurantId);
        return R.ok();
    }

    @Override
    public R searchRestaurants(SearchRestaurantFilter filter) {
        Page<Restaurant> restaurantPage = restaurantMapper.searchRestaurantPage(new Page<>(filter.getPageCurrent(), filter.getPageSize()),filter);
        return R.ok().data("restaurants", restaurantPage);
    }

    @Override
    public R adminChangeRestaurantStatus(Long restaurantId, RestaurantStatus restaurantStatus) {
        Restaurant restaurant = getById(restaurantId);
        if (restaurant == null) {
            throw new ServicesException(ResultCode.RESTAURANT_NOT_EXIST);
        }
        restaurant.setStatus(restaurantStatus);
        updateById(restaurant);
        return R.ok();
    }

    @Override
    public R adminGetRestaurants(RestaurantStatus restaurantStatus, Long pageCurrent, Long pageSize) {
        Page restaurantPage = page(new Page<>(pageCurrent, pageSize), Wrappers.<Restaurant>lambdaQuery().eq(restaurantStatus != null, Restaurant::getStatus, restaurantStatus));
        List<RestaurantVO> records = restaurantPage.getRecords().stream()
                .map(r -> {
                    Restaurant restaurant = (Restaurant) r;
                    RestaurantVO restaurantVO = RestaurantVO.toRestaurantVO(restaurant);
                    restaurantVO.setCategoryName(categoryService.getById(restaurant.getCategoryId()).getName());
                    Address address = addressService.getById(restaurant.getAddressId());
                    restaurantVO.setAddress(address);
                    return restaurantVO;
                }).toList();
        restaurantPage.setRecords(records);
        return R.ok().data("restaurants", restaurantPage);
    }

    @Override
    public R ownerGetRestaurants(Long ownerId, RestaurantStatus restaurantStatus, Long pageCurrent, Long pageSize) {
        User owner = userService.getById(ownerId);
        if (owner == null) {
            throw new ServicesException(ResultCode.USER_ID_ERROR);
        } else if (!owner.getRoleId().equals(UserRole.ROLE_RESTAURANT_OWNER)) {
            throw new ServicesException(ResultCode.PERM_NOT_EXIST);
        }
        Page<Restaurant> restaurantPage = page(new Page<>(pageCurrent, pageSize),
                Wrappers.<Restaurant>lambdaQuery()
                        .eq(Restaurant::getOwnerId, ownerId).
                        eq(restaurantStatus != null, Restaurant::getStatus, restaurantStatus));
        return R.ok().data("restaurants", restaurantPage);
    }
}
