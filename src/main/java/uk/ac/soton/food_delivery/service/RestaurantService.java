package uk.ac.soton.food_delivery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DO.Restaurant;
import uk.ac.soton.food_delivery.entity.DTO.AddRestaurantDTO;
import uk.ac.soton.food_delivery.entity.filter.SearchRestaurantFilter;
import uk.ac.soton.food_delivery.enums.RestaurantStatus;
import uk.ac.soton.food_delivery.utils.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
public interface RestaurantService extends IService<Restaurant> {

    R addRestaurant(AddRestaurantDTO restaurantDTO);

    R updateRestaurantAvatar(Long restaurantId, MultipartFile file);

    R updateRestaurantCertification(Long restaurantId, MultipartFile file);

    R updateRestaurantInfo(Restaurant restaurant);

    R getRestaurant(String restaurantId);

    R removeRestaurant(Long restaurantId);

    R searchRestaurants(SearchRestaurantFilter filter);

    R adminChangeRestaurantStatus(Long restaurantId, RestaurantStatus restaurantStatus);

    R adminGetRestaurants(RestaurantStatus restaurantStatus, Long pageCurrent, Long pageSize);

    R ownerGetRestaurants(Long ownerId, RestaurantStatus restaurantStatus, Long pageCurrent, Long pageSize);
}
