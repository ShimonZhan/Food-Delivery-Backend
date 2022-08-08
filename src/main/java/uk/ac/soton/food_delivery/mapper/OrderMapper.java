package uk.ac.soton.food_delivery.mapper;

import uk.ac.soton.food_delivery.entity.DO.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
