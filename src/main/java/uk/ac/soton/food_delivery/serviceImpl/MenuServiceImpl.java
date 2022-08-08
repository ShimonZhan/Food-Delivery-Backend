package uk.ac.soton.food_delivery.serviceImpl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DO.Menu;
import uk.ac.soton.food_delivery.entity.DO.Order;
import uk.ac.soton.food_delivery.entity.DO.Restaurant;
import uk.ac.soton.food_delivery.entity.Message;
import uk.ac.soton.food_delivery.enums.MenuStatus;
import uk.ac.soton.food_delivery.enums.MessageType;
import uk.ac.soton.food_delivery.enums.OrderStatus;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;
import uk.ac.soton.food_delivery.mapper.MenuMapper;
import uk.ac.soton.food_delivery.service.*;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private RestaurantService restaurantService;

    @Resource
    private OrderService orderService;

    @Resource
    private OssService ossService;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private MessageService messageService;

    @Resource
    private UserService userService;

    @Override
    public R addMenu(Menu menu) {
        if (restaurantService.getById(menu.getRestaurantId()) == null) {
            throw new ServicesException(ResultCode.RESTAURANT_NOT_EXIST);
        }
        menu.setStatus(MenuStatus.NORMAL);
        save(menu);
        sendMessages(menu.getRestaurantId());
        return R.ok().data("menuId", menu.getId());
    }

    @Override
    public R getMenusByRestaurantId(Long restaurantId) {
        if (restaurantService.getById(restaurantId) == null) {
            throw new ServicesException(ResultCode.RESTAURANT_NOT_EXIST);
        }
        List<Menu> menus = list(Wrappers.<Menu>lambdaQuery().eq(Menu::getRestaurantId, restaurantId));
        return R.ok().data("menus", menus);
    }

    @Override
    public R removeMenu(Long menuId) {
        if (getById(menuId) == null) {
            throw new ServicesException(ResultCode.MENU_NOT_EXIST);
        }
        removeById(menuId);
        return R.ok();
    }

    @Override
    public R updateMenu(Menu menu) {
        if (getById(menu.getId()) == null) {
            throw new ServicesException(ResultCode.MENU_NOT_EXIST);
        }
        updateById(menu);
        sendMessages(menu.getRestaurantId());
        return R.ok();
    }

    @Override
    public R updateMenuPhoto(Long menuId, MultipartFile file) {
        Menu menu = getById(menuId);
        if (menu == null) {
            throw new ServicesException(ResultCode.MENU_NOT_EXIST);
        }
        String link = ossService.uploadFile("menu/" + menu.getRestaurantId() + "/" + menuId, file, "");
        menu.setPhoto(link);
        updateById(menu);
        return R.ok();
    }

    @Override
    public R searchMenus(Long restaurantId, String name, Long pageCurrent, Long pageSize) {
        Page<Menu> menuPage = menuMapper.searchMenus(restaurantId, name, new Page<>(pageCurrent, pageSize));
        return R.ok().data("menus", menuPage);
    }

    @Async
    public void sendMessages(Long restaurantId) {
        if (!messageService.menuChangeMessageLimit(restaurantId)) {
            return;
        }
        messageService.limitMenuMessage(restaurantId);

        Restaurant restaurant = restaurantService.getById(restaurantId);
        List<Order> orders = orderService.list(Wrappers.<Order>lambdaQuery()
                .eq(Order::getRestaurantId, restaurantId)
                .eq(Order::getStatus, OrderStatus.DELIVERED)
                .select(Order::getCustomerId));

        List<Message> messages = orders
                .stream()
                .map(Order::getCustomerId)
                .distinct()
                .map(cid -> new Message()
                        .setType(MessageType.MENU_CHANGE.getCode())
                        .setFrom(restaurantId)
                        .setFromName(restaurant.getName())
                        .setTo(cid)
                        .setToName(userService.getById(cid).getNickName())
                        .setContent("The store where you have placed a successful order has updated its menu. " + restaurant.getName() + " menus changes."))
                .toList();

        messageService.addMenuChangeMessages(messages, restaurantId);
    }
}
