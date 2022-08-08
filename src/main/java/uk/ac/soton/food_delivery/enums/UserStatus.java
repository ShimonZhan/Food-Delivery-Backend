package uk.ac.soton.food_delivery.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-11 05:07:26
 */
@Getter
@RequiredArgsConstructor
public enum UserStatus {
    INACTIVATED(0),
    NORMAL(1),
    DISABLED(2);

    @EnumValue // label the value which will in the database
    private final int code;
}
