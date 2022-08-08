package uk.ac.soton.food_delivery.controller;


import io.swagger.annotations.ApiImplicitParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uk.ac.soton.food_delivery.entity.DTO.AddOrderDTO;
import uk.ac.soton.food_delivery.enums.OrderStatus;
import uk.ac.soton.food_delivery.service.OrderService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    // customer
    @PostMapping("/customerAddOrder")
    @PreAuthorize("@ex.hasAuthority('system:order:customerAddOrder')")
    public R customerAddOrder(@RequestBody AddOrderDTO addOrderDTO) {
        return orderService.customerAddOrder(addOrderDTO);
    }

    @PostMapping("/customerPayOrder")
    @PreAuthorize("@ex.hasAuthority('system:order:customerPayOrder')")
    public R customerPayOrder(Long orderId) {
        return orderService.customerPayOrder(orderId);
    }

    @GetMapping("/customerGetOrders")
    @PreAuthorize("@ex.hasAuthority('system:order:customerGetOrders')")
    @ApiImplicitParam(name = "orderStatuses", value = "orderStatus List", paramType = "query", allowMultiple = true, dataTypeClass = ArrayList.class)
    public R customerGetOrders(Long customerId, @RequestParam(value = "orderStatuses", required = false) ArrayList<OrderStatus> orderStatuses, Long pageCurrent, Long pageSize) { //page
        return orderService.customerGetOrders(customerId, orderStatuses,pageCurrent, pageSize);
    }

    // restaurant
    @GetMapping("/restaurantGetOrders")
    @PreAuthorize("@ex.hasAuthority('system:order:restaurantGetOrders')")
    @ApiImplicitParam(name = "orderStatuses", value = "orderStatus List", paramType = "query", allowMultiple = true, dataTypeClass = ArrayList.class)
    public R restaurantGetOrders(Long restaurantId, @RequestParam(value = "orderStatuses", required = false) ArrayList<OrderStatus> orderStatuses, Long pageCurrent, Long pageSize) { //page
        return orderService.restaurantGetOrders(restaurantId, orderStatuses, pageCurrent, pageSize);
    }

    @GetMapping("/restaurantGetDeliveryMans")
    @PreAuthorize("@ex.hasAuthority('system:order:restaurantGetDeliveryMans')")
    public R restaurantGetDeliveryMans() {
        return orderService.restaurantGetDeliveryMans();
    }

    @PostMapping("/restaurantSetDeliveryMan")
    @PreAuthorize("@ex.hasAuthority('system:order:restaurantSetDeliveryMan')")
    public R restaurantSetDeliveryMan(Long orderId, String name, String phone) {
        return orderService.restaurantSetDeliveryMan(orderId, name, phone);
    }

    // common
    @PreAuthorize("@ex.hasAuthority('system:order:customerCancelOrder') or @ex.hasAuthority('system:order:restaurantCancelOrder')")
    @GetMapping("/cancelOrder")
    public R cancelOrder(Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @PreAuthorize("@ex.hasAuthority('system:order:customerGetOrderDetails') or @ex.hasAuthority('system:order:restaurantGetOrderDetails')")
    @GetMapping("/getOrderDetails")
    public R getOrderDetails(Long orderId) {
        return orderService.getOrderDetails(orderId);
    }

}
