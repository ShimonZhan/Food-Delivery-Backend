package uk.ac.soton.food_delivery.entity.DO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment")
@ApiModel(value = "Comment对象")
public class Comment {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("customer_id")
    private Long customerId;

    @TableField("restaurant_id")
    private Long restaurantId;

    @TableField("review")
    private String review;

    @TableField("mark")
    private Integer mark;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonIgnore
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private LocalDateTime updateTime;


}
