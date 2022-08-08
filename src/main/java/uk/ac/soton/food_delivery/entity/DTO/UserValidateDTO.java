package uk.ac.soton.food_delivery.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author ShimonZhan
 * @since 2022-03-14 14:56:17
 */
@Builder
@AllArgsConstructor
@Data
public class UserValidateDTO {
    private Long id;
    private String email;
    private String username;
}
