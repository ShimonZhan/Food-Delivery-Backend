package uk.ac.soton.food_delivery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import uk.ac.soton.food_delivery.entity.DO.Order;
import uk.ac.soton.food_delivery.entity.DTO.AddOrderDTO;
import uk.ac.soton.food_delivery.enums.OrderStatus;
import uk.ac.soton.food_delivery.utils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
public interface OrderService extends IService<Order> {

    R customerAddOrder(AddOrderDTO addOrderDTO);

    R customerPayOrder(Long orderId);

    R customerGetOrders(Long customerId, ArrayList<OrderStatus> orderStatuses, Long pageCurrent, Long pageSize);

    R restaurantGetOrders(Long restaurantId, List<OrderStatus> orderStatus, Long pageCurrent, Long pageSize);

    R cancelOrder(Long orderId);

    R getOrderDetails(Long orderId);

    void deliverManFinishOrder(List<Long> orderIds);

    R restaurantGetDeliveryMans();

    R restaurantSetDeliveryMan(Long orderId, String name, String phone);
}
