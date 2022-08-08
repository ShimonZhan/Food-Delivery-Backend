package uk.ac.soton.food_delivery.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author ShimonZhan
 * @since 2022-05-11 07:01:47
 */
@Data
@Accessors(chain = true)
public class Message {
    private Integer type;

    private String id;

    private Long from;

    private String fromName;

    private Long to;

    private String toName;

    private String content;

    private LocalDateTime time;
}
