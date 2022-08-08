package uk.ac.soton.food_delivery.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-11 05:17:29
 */
@Getter
@RequiredArgsConstructor
public enum RestaurantStatus {
    CLOSE(0),
    OPEN(1),
    UNVERIFIED(2),
    PERMANENT_CLOSED(3);

    @EnumValue // label the value which will in the database
    private final int code;
}
