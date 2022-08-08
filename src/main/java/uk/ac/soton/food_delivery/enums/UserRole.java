package uk.ac.soton.food_delivery.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-11 05:07:36
 */
@Getter
@RequiredArgsConstructor
public enum UserRole {
    ROLE_ADMIN(1, "admin"),
    ROLE_CUSTOMER(2, "customer"),
    ROLE_RESTAURANT_OWNER(3, "restaurant_owner"),
//    ROLE_DELIVERY_MAN(4, "delivery_man")
    ;

    @EnumValue // label the value which will in the database
    private final long code;

    private final String description;
}
