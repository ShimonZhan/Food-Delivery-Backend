package uk.ac.soton.food_delivery.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import uk.ac.soton.food_delivery.enums.AddressType;

/**
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("address")
@ApiModel(value = "Address对象")
public class Address {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("type")
    private AddressType type;

    @TableField("customer_id")
    private Long customerId;

    @TableField("restaurant_id")
    private Long restaurantId;

    @TableField("first_address")
    private String firstAddress;

    @TableField("second_address")
    private String secondAddress;

    @TableField("first_name")
    private String firstName;

    @TableField("last_name")
    private String lastName;

    @TableField("phone")
    private String phone;

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

    public static DeliveryAddress toDeliveryAddress(Address address) {
        if (address == null) {
            return null;
        }
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setType(address.getType());
        deliveryAddress.setCustomerId(address.getCustomerId());
        deliveryAddress.setRestaurantId(address.getRestaurantId());
        deliveryAddress.setFirstAddress(address.getFirstAddress());
        deliveryAddress.setSecondAddress(address.getSecondAddress());
        deliveryAddress.setPhone(address.getPhone());
        deliveryAddress.setCity(address.getCity());
        deliveryAddress.setCountry(address.getCountry());
        deliveryAddress.setPostcode(address.getPostcode());
        deliveryAddress.setLatitude(address.getLatitude());
        deliveryAddress.setLongitude(address.getLongitude());
        return deliveryAddress;
    }

    public String fullAddress() {
        return firstAddress + (Strings.isNullOrEmpty(secondAddress) ? "" : ", " + secondAddress) + ", " + city + ", " + postcode;
    }
}
