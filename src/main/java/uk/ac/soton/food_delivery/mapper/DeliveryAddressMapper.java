package uk.ac.soton.food_delivery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import uk.ac.soton.food_delivery.entity.DO.DeliveryAddress;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Mapper
public interface DeliveryAddressMapper extends BaseMapper<DeliveryAddress> {

}
