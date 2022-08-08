package uk.ac.soton.food_delivery.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author ShimonZhan
 * @since 2022-03-12 23:20:06
 */
@Data
@Builder
@AllArgsConstructor
public class LoginUserDTO {

    private String username;

    private String password;
}
