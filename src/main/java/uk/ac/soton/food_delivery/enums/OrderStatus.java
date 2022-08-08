package uk.ac.soton.food_delivery.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-11 05:31:28
 */
@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING_PAYMENT(0),
    PENDING_DELIVERY(1),
    DELIVERING(2),
    DELIVERED(3),
    CANCELLED(4);

    @EnumValue // label the value which will in the database
    private final int code;
}
