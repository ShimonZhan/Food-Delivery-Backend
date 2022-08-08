package uk.ac.soton.food_delivery.serviceImpl;

import uk.ac.soton.food_delivery.entity.DO.OrderItem;
import uk.ac.soton.food_delivery.mapper.OrderItemMapper;
import uk.ac.soton.food_delivery.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
