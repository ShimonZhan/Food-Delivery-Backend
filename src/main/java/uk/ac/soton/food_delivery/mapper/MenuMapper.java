package uk.ac.soton.food_delivery.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import uk.ac.soton.food_delivery.entity.DO.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {
    Page<Menu> searchMenus(@Param("restaurantId") Long restaurantId, @Param("name") String name, @Param("page") Page<Menu> page);
}
