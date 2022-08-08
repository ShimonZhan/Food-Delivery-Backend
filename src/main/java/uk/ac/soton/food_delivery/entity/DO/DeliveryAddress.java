package uk.ac.soton.food_delivery.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import uk.ac.soton.food_delivery.enums.AddressType;

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
@TableName("delivery_address")
@ApiModel(value = "DeliveryAddress对象")
public class DeliveryAddress {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("type")
    private AddressType type;

    @TableField("customer_id")
    private Long customerId;

    @TableField("restaurant_id")
    private Long restaurantId;

    @TableField("first_name")
    private String firstName;

    @TableField("last_name")
    private String lastName;

    @TableField("phone")
    private String phone;

    @TableField("first_address")
    private String firstAddress;

    @TableField("second_address")
    private String secondAddress;

    @TableField("city")
    private String city;

    @TableField("country")
    private String country;

    @TableField("postcode")
    private String postcode;

    @TableField("latitude")
    private Double latitude;

    @TableField("longitude")
    private Double longitude;

}
