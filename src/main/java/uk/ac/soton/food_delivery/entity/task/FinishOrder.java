package uk.ac.soton.food_delivery.entity.task;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ShimonZhan
 * @since 2022-04-28 00:59:51
 */
@Data
@AllArgsConstructor
public class FinishOrder {
    private Long orderId;
    private LocalDateTime finishTime;
}
