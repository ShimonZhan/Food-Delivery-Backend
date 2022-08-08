package uk.ac.soton.food_delivery.serviceImpl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import uk.ac.soton.food_delivery.entity.DO.*;
import uk.ac.soton.food_delivery.entity.DTO.AddOrderDTO;
import uk.ac.soton.food_delivery.entity.VO.OrderVO;
import uk.ac.soton.food_delivery.entity.task.FinishOrder;
import uk.ac.soton.food_delivery.enums.OrderStatus;
import uk.ac.soton.food_delivery.enums.UserRole;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;
import uk.ac.soton.food_delivery.mapper.OrderMapper;
import uk.ac.soton.food_delivery.service.*;
import uk.ac.soton.food_delivery.utils.NameGenerator;
import uk.ac.soton.food_delivery.utils.PhoneNumGenerator;
import uk.ac.soton.food_delivery.utils.R;
import uk.ac.soton.food_delivery.utils.RedisCache;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private RedisCache redisCache;

    @Resource
    private OrderItemService orderItemService;

    @Resource
    private RestaurantService restaurantService;

    @Resource
    private UserService userService;

    @Resource
    private AddressService addressService;

    @Resource
    private DeliveryAddressService deliveryAddressService;

    @Resource
    private MenuService menuService;

    @Resource
    private MessageService messageService;

    @Override
    public R customerAddOrder(AddOrderDTO addOrderDTO) {
        Address customerAddress = addressService.getById(addOrderDTO.getCustomerAddressId());
        Restaurant restaurant = restaurantService.getById(addOrderDTO.getRestaurantId());
        User customer = userService.getById(addOrderDTO.getCustomerId());

        if (customer == null || customer.getRoleId() != UserRole.ROLE_CUSTOMER) {
            throw new ServicesException(ResultCode.CUSTOMER_ID_ERROR);
        } else if (restaurant == null) {
            throw new ServicesException(ResultCode.RESTAURANT_NOT_EXIST);
        } else if (customerAddress == null) {
            throw new ServicesException(ResultCode.ADDRESS_ID_ERROR);
        }

        // copy address
        DeliveryAddress customerDeliveryAddress = Address.toDeliveryAddress(customerAddress);
        Address restaurantAddress = addressService.getById(restaurant.getAddressId());
        DeliveryAddress restaurantDeliveryAddress = Address.toDeliveryAddress(restaurantAddress);
        deliveryAddressService.saveBatch(Lists.newArrayList(customerDeliveryAddress, restaurantDeliveryAddress));

        Order order = Order.builder()
                .customerId(addOrderDTO.getCustomerId())
                .customerAddressId(customerDeliveryAddress.getId())
                .restaurantAddressId(restaurantDeliveryAddress.getId())
                .restaurantId(restaurant.getId())
                .status(OrderStatus.PENDING_PAYMENT) // set order status pending_payment
                .build();
        save(order);

        // copy menus
        HashMap<Long, Integer> menuIdQuantityMap = addOrderDTO.getMenuIdQuantityMap();
        List<Menu> menus = menuService.listByIds(menuIdQuantityMap.keySet());
        List<OrderItem> orderItems = menus.stream()
                .map(x -> Menu.toOrderItem(x).setOrderId(order.getId())
                        .setQuantity(menuIdQuantityMap.get(x.getId())))
                .toList();
        orderItemService.saveBatch(orderItems);

        // summary totalPrice
        float totalPrice = (float) orderItems.stream()
                .mapToDouble(a -> a.getPrice() * a.getQuantity())
                .sum();
        order.setTotalPrice(totalPrice);
        updateById(order);
        return R.ok().message("Order Success!").data("orderId", order.getId());
    }

    @Override
    public R customerPayOrder(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new ServicesException(ResultCode.ORDER_ID_ERROR);
        } else if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new ServicesException(ResultCode.ORDER_STATUS_NOT_PENDING_PAYMENT);
        }
        order.setStatus(OrderStatus.PENDING_DELIVERY);
        updateById(order);
        return R.ok();
    }

    @Override
    public R customerGetOrders(Long customerId, ArrayList<OrderStatus> orderStatuses, Long pageCurrent, Long pageSize) {
        if (userService.getById(customerId) == null) {
            throw new ServicesException(ResultCode.CUSTOMER_ID_ERROR);
        }
        Page orders = page(new Page<>(pageCurrent, pageSize), Wrappers.<Order>lambdaQuery()
                .eq(Order::getCustomerId, customerId)
                .in(orderStatuses != null && !orderStatuses.isEmpty(), Order::getStatus, orderStatuses)
                .orderByDesc(Order::getCreateTime)
        );

        List<OrderVO> records = orders.getRecords().stream()
                .map(x -> {
                    Order order = (Order) x;
                    OrderVO orderVO = OrderVO.toOrderVO(order);
                    String restaurantName = restaurantService.getById(order.getRestaurantId()).getName();
                    orderVO.setRestaurantName(restaurantName);
                    orderVO.setCustomerAddress(deliveryAddressService.getById(order.getCustomerAddressId()));
                    orderVO.setRestaurantAddress(deliveryAddressService.getById(order.getRestaurantAddressId()));
                    orderVO.setOrderItems(orderItemService
                            .list(Wrappers.<OrderItem>lambdaQuery()
                                    .eq(OrderItem::getOrderId, order.getId())));
                    return orderVO;
                }).toList();
        orders.setRecords(records);
        return R.ok().data("orders", orders);
    }

    @Override
    public R restaurantGetOrders(Long restaurantId, List<OrderStatus> orderStatuses, Long pageCurrent, Long pageSize) {
        if (restaurantService.getById(restaurantId) == null) {
            throw new ServicesException(ResultCode.RESTAURANT_NOT_EXIST);
        }
        Page orders = page(new Page<>(pageCurrent, pageSize), Wrappers.<Order>lambdaQuery()
                .eq(Order::getRestaurantId, restaurantId)
                .in(orderStatuses != null && !orderStatuses.isEmpty(), Order::getStatus, orderStatuses)
                .orderByDesc(Order::getCreateTime)
        );
        List<OrderVO> records = orders.getRecords().stream()
                .map(x -> {
                    Order order = (Order) x;
                    OrderVO orderVO = OrderVO.toOrderVO(order);
                    String restaurantName = restaurantService.getById(order.getRestaurantId()).getName();
                    orderVO.setRestaurantName(restaurantName);
                    orderVO.setCustomerAddress(deliveryAddressService.getById(order.getCustomerAddressId()));
                    orderVO.setRestaurantAddress(deliveryAddressService.getById(order.getRestaurantAddressId()));
                    orderVO.setOrderItems(orderItemService
                            .list(Wrappers.<OrderItem>lambdaQuery()
                                    .eq(OrderItem::getOrderId, order.getId())));
                    return orderVO;
                }).toList();
        orders.setRecords(records);
        return R.ok().data("orders", orders);
    }

    @Override
    public R cancelOrder(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new ServicesException(ResultCode.ORDER_ID_ERROR);
        } else if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new ServicesException(ResultCode.ORDER_ALREADY_DELIVERED);
        }
        order.setStatus(OrderStatus.CANCELLED);
        updateById(order);
        // Clean TASK
        redisCache.deleteObject("finishJob:" + orderId);
        messageService.cancelOrder(order.getRestaurantId(), order.getCustomerId(), orderId);
        return R.ok();
    }

    @Override
    public R getOrderDetails(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new ServicesException(ResultCode.ORDER_ID_ERROR);
        }
        List<OrderItem> orderItems = orderItemService
                .list(Wrappers.<OrderItem>lambdaQuery()
                        .eq(OrderItem::getOrderId, orderId));

        return R.ok()
                .data("order", order)
                .data("orderItems", orderItems);
    }

    @Override
    public void deliverManFinishOrder(List<Long> orderIds) {
        LocalDateTime now = LocalDateTime.now();
        List<Order> orders = orderIds.stream()
                .map(x -> Order.builder()
                        .id(x)
                        .status(OrderStatus.DELIVERED)
                        .deliveredTime(now)
                        .build())
                .toList();
        updateBatchById(orders);
    }

    @Override
    public R restaurantGetDeliveryMans() {
        int num = new Random().nextInt(6) + 5;
        ArrayList<DeliveryMan> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(new DeliveryMan(NameGenerator.generateName(), PhoneNumGenerator.generate()));
        }
        return R.ok().data("deliveryMans", list);
    }

    @Override
    public R restaurantSetDeliveryMan(Long orderId, String name, String phone) {
        Order order = getById(orderId);
        if (order == null) {
            throw new ServicesException(ResultCode.ORDER_ID_ERROR);
        } else if (order.getStatus() != OrderStatus.PENDING_DELIVERY && order.getStatus() != OrderStatus.DELIVERING) {
            throw new ServicesException(ResultCode.ORDER_STATUS_NOT_PENDING_DELIVERY_OR_NOT_DELIVERING);
        }
        order.setDeliveryManName(name).setDeliveryManPhone(phone);
        order.setBeginTime(LocalDateTime.now());
        order.setEstimatedDeliveryTime(LocalDateTime.now().plusMinutes(45));
        order.setStatus(OrderStatus.DELIVERING);
        updateById(order);
        // ADD TASK
        redisCache.setCacheObject("finishJob:" + orderId, new FinishOrder(orderId, LocalDateTime.now().plusMinutes(45)));
        return R.ok();
    }

    @AllArgsConstructor
    @Data
    public static class DeliveryMan {
        private String name;
        private String phone;
    }
}
