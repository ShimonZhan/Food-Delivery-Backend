package uk.ac.soton.food_delivery.entity.DTO;

import lombok.Data;
import uk.ac.soton.food_delivery.entity.DO.Restaurant;

/**
 * @author ShimonZhan
 * @since 2022-04-20 01:53:35
 */
@Data
public class AddRestaurantDTO {
    private Long ownerId;

    private String phone;

    private Long categoryId;

    private String name;

    private String description;

    private Integer averageCookingTime;

    public static Restaurant toRestaurantDO(AddRestaurantDTO restaurantDTO) {
        if (restaurantDTO == null) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(restaurantDTO.getOwnerId());
        restaurant.setPhone(restaurantDTO.getPhone());
        restaurant.setCategoryId(restaurantDTO.getCategoryId());
        restaurant.setName(restaurantDTO.getName());
        restaurant.setDescription(restaurantDTO.getDescription());
        restaurant.setAverageCookingTime(restaurantDTO.getAverageCookingTime());
        return restaurant;
    }
}
