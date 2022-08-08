package uk.ac.soton.food_delivery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import uk.ac.soton.food_delivery.entity.DO.Restaurant;
import uk.ac.soton.food_delivery.entity.filter.SearchRestaurantFilter;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Mapper
public interface RestaurantMapper extends BaseMapper<Restaurant> {
    Page<Restaurant> searchRestaurantPage(@Param("page") Page<Restaurant> page, @Param("filter") SearchRestaurantFilter filter);

}
