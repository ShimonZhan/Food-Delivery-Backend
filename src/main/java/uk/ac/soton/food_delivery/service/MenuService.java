package uk.ac.soton.food_delivery.service;

import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DO.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import uk.ac.soton.food_delivery.utils.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
public interface MenuService extends IService<Menu> {

    R addMenu(Menu menu);

    R getMenusByRestaurantId(Long restaurantId);

    R removeMenu(Long menuId);

    R updateMenu(Menu menu);

    R updateMenuPhoto(Long menuId,  MultipartFile file);

    R searchMenus(Long restaurantId, String name, Long pageCurrent, Long pageSize);
}
