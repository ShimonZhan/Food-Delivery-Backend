package uk.ac.soton.food_delivery.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import uk.ac.soton.food_delivery.entity.DO.DeliveryAddress;
import uk.ac.soton.food_delivery.mapper.DeliveryAddressMapper;
import uk.ac.soton.food_delivery.service.DeliveryAddressService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Service
public class DeliveryAddressServiceImpl extends ServiceImpl<DeliveryAddressMapper, DeliveryAddress> implements DeliveryAddressService {

}
