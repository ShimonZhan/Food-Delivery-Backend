package uk.ac.soton.food_delivery.entity.DTO;

import lombok.Data;

import java.util.HashMap;

/**
 * @author ShimonZhan
 * @since 2022-04-26 17:59:49
 */
@Data
public class AddOrderDTO {
    Long customerId;
    Long customerAddressId;
    Long restaurantId;
    HashMap<Long, Integer> menuIdQuantityMap;

}
