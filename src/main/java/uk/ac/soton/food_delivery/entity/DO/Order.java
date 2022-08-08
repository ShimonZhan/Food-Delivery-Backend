package uk.ac.soton.food_delivery.entity.DO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import uk.ac.soton.food_delivery.enums.OrderStatus;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
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
@TableName("`order`")
@ApiModel(value = "Order对象")
public class Order {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("restaurant_id")
    private Long restaurantId;

    @TableField("customer_id")
    private Long customerId;

    @TableField("restaurant_address_id")
    private Long restaurantAddressId;

    @TableField("customer_address_id")
    private Long customerAddressId;

    @TableField("total_price")
    private Float totalPrice;

    @TableField("status")
    private OrderStatus status;

    @TableField("begin_time")
    private LocalDateTime beginTime;

    @TableField("estimated_delivery_time")
    private LocalDateTime estimatedDeliveryTime;

    @TableField("delivered_time")
    private LocalDateTime deliveredTime;

    @TableField("delivery_man_name")
    private String deliveryManName;

    @TableField("delivery_man_phone")
    private String deliveryManPhone;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonIgnore
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private LocalDateTime updateTime;
}
