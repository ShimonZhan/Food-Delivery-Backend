package uk.ac.soton.food_delivery.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author: ShimonZhan
 * @Date: 2022-03-11 05:21:31
 */
@Getter
@RequiredArgsConstructor
public enum AddressType {
    CUSTOMER(0),
    RESTAURANT(1);

    @EnumValue // label the value which will in the database
    private final int code;
}
