package uk.ac.soton.food_delivery.handler;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-07 01:18:54
 */
@Getter
@RequiredArgsConstructor
public enum ResultCode {
    SUCCESS(200,"Request successful!"),
    ERROR(500,"Server response error!"),

    /** 100X indicates user error*/
    // register, update
    USER_NAME_EXISTED(1001,"Username already exists"),
    USER_EMAIL_EXISTED(1002,"User email already exists"),
    // login
    USERNAME_PASSWORD_ERROR(1003,"Wrong username or password"),
    USER_ACCOUNT_INACTIVATED(1004,"Account not activated"),
    USER_ACCOUNT_DISABLE(1005,"Account is disabled"),
//    USER_ACCOUNT_LOGIN_IN_OTHER_PLACE(1006,"Account timeout or account login in another place"),
    // token
    TOKEN_VALIDATE_FAILED(1007,"Token authentication failed"),
    TOKEN_VALIDATE_EXPIRED(1008,"Token is already expired"),
    USER_NOT_LOGIN(1009,"User not logged in"),
    // no permissions
    USER_NO_PERMISSIONS(1010,"Insufficient user authorities"),
//    USER_UNAUTHORIZED(1011, "User Authorized failed, please login again"),
    // activate and forget password
    USER_EMAIL_NOT_EXISTED(1012,"User email not exists"),
    USER_ACCOUNT_ALREADY_ACTIVATED(1013,"User account is already activated"),
    USER_PASSWORD_SAME(1014, "The new password is the same as the old password"),

    USER_ID_REQUIRED(1015, "userId is required"),
    USER_ID_ERROR(1016, "userId error"),
    USER_OLD_PASSWORD_ERROR(1017, "user old password error"),

    /** 20XX indicates a server error */
    FILE_UPLOAD_FAILED(2001,"Failed to upload file"),
//    UPDATE_USER_INFO_FAILED(2004,"Failed to modify user information"),
//    UPDATE_USER_PASSWORD_FAILED(2005,"Failed to modify password"),


    /** Category 11XX **/
    CATEGORY_EXIST(1101, "Category already exists"),
    CATEGORY_NOT_EXIST(1102, "Category not exists"),
    CATEGORY_HAS_RESTAURANT(1103, "There are restaurants under this category"),


    /** Restaurant 12XX **/
    RESTAURANT_NOT_EXIST(1201, "Restaurant not exists"),

    MENU_NOT_EXIST(1301, "Menu not exists"),

    ADDRESS_ID_ERROR(1401, "Address id error"),
    CUSTOMER_ID_ERROR(1402, "Customer id error"),
//    ADDRESS_EXIST(1403, "Address exists"),

    ORDER_ID_ERROR(1501, "Order id error"),
    ORDER_ALREADY_DELIVERED(1502, "Order has already delivered"),
    ORDER_STATUS_NOT_PENDING_PAYMENT(1503, "order status is not pending payment"),
    ORDER_STATUS_NOT_PENDING_DELIVERY_OR_NOT_DELIVERING(1504, "order status is not pending delivery or not delivering"),


    /*perm*/
    PERM_EXIST(5001, "User permission exist"),
    PERM_NOT_EXIST(5001, "User permission not exist"),
    ;

    @EnumValue // label the value which will in the database
    private final int code;

    private final String description;
}

