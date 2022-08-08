package uk.ac.soton.food_delivery.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ShimonZhan
 * @since 2022-05-12 01:46:56
 */
@Getter
@RequiredArgsConstructor
public enum MessageType {
    MENU_CHANGE(0),
    CHAT(1),
    CANCEL_ORDER(2);

    private final int code;
}
