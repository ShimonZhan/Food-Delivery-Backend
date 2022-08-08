package uk.ac.soton.food_delivery.entity.VO;

import lombok.Data;
import uk.ac.soton.food_delivery.entity.DO.Address;
import uk.ac.soton.food_delivery.entity.DO.DeliveryAddress;
import uk.ac.soton.food_delivery.entity.DO.Order;
import uk.ac.soton.food_delivery.entity.DO.OrderItem;
import uk.ac.soton.food_delivery.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ShimonZhan
 * @since 2022-05-11 01:28:29
 */
@Data
public class OrderVO {
    private Long id;

    private Long restaurantId;

    private String restaurantName;

    private Long customerId;

    private DeliveryAddress restaurantAddress;

    private DeliveryAddress customerAddress;

    private Float totalPrice;

    private OrderStatus status;

    private LocalDateTime beginTime;

    private LocalDateTime estimatedDeliveryTime;

    private LocalDateTime deliveredTime;

    private String deliveryManName;

    private String deliveryManPhone;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<OrderItem> orderItems;

    public static OrderVO toOrderVO(Order order) {
        if (order == null) {
            return null;
        }
        OrderVO orderVO = new OrderVO();
        orderVO.setId(order.getId());
        orderVO.setRestaurantId(order.getRestaurantId());
        orderVO.setCustomerId(order.getCustomerId());
        orderVO.setTotalPrice(order.getTotalPrice());
        orderVO.setStatus(order.getStatus());
        orderVO.setBeginTime(order.getBeginTime());
        orderVO.setEstimatedDeliveryTime(order.getEstimatedDeliveryTime());
        orderVO.setDeliveredTime(order.getDeliveredTime());
        orderVO.setDeliveryManName(order.getDeliveryManName());
        orderVO.setDeliveryManPhone(order.getDeliveryManPhone());
        orderVO.setCreateTime(order.getCreateTime());
        orderVO.setUpdateTime(order.getUpdateTime());
        return orderVO;
    }
}
