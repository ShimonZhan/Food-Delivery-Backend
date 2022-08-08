package uk.ac.soton.food_delivery.entity.filter;

import lombok.Data;

/**
 * @author ShimonZhan
 * @since 2022-04-23 02:20:16
 */
@Data
public class SearchRestaurantFilter {
    private String keyword;
    private Long categoryId;
    private Long PageCurrent;
    private Long PageSize;
}
