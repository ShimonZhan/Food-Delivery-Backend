package uk.ac.soton.food_delivery.entity.VO;

import lombok.Data;
import uk.ac.soton.food_delivery.entity.DO.Address;
import uk.ac.soton.food_delivery.entity.DO.Restaurant;
import uk.ac.soton.food_delivery.enums.RestaurantStatus;

/**
 * @author ShimonZhan
 * @since 2022-05-18 18:26:44
 */
@Data
public class RestaurantVO {

        private Long id;

        private Long ownerId;

        private Address address;

        private String name;

        private String description;

        private String phone;

        private RestaurantStatus status;

        private String categoryName;

        private String certificationFile;

        private Integer averageCookingTime;

        private Float mark;

        private String avatar;

    public static RestaurantVO toRestaurantVO(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }
        RestaurantVO restaurantVO = new RestaurantVO();
        restaurantVO.setId(restaurant.getId());
        restaurantVO.setOwnerId(restaurant.getOwnerId());
        restaurantVO.setName(restaurant.getName());
        restaurantVO.setDescription(restaurant.getDescription());
        restaurantVO.setPhone(restaurant.getPhone());
        restaurantVO.setStatus(restaurant.getStatus());
        restaurantVO.setCertificationFile(restaurant.getCertificationFile());
        restaurantVO.setAverageCookingTime(restaurant.getAverageCookingTime());
        restaurantVO.setMark(restaurant.getMark());
        restaurantVO.setAvatar(restaurant.getAvatar());
        return restaurantVO;
    }

}
