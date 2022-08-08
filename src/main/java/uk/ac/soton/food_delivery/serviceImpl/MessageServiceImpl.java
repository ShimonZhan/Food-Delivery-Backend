package uk.ac.soton.food_delivery.serviceImpl;

import cn.hutool.core.util.IdUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uk.ac.soton.food_delivery.entity.Message;
import uk.ac.soton.food_delivery.enums.MessageType;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;
import uk.ac.soton.food_delivery.service.MessageService;
import uk.ac.soton.food_delivery.service.RestaurantService;
import uk.ac.soton.food_delivery.service.UserService;
import uk.ac.soton.food_delivery.utils.R;
import uk.ac.soton.food_delivery.utils.RedisCache;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ShimonZhan
 * @since 2022-05-11 07:04:15
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private RedisCache redisCache;

    @Resource
    private UserService userService;

    @Resource
    private RestaurantService restaurantService;

    @Override
    public void addMenuChangeMessages(List<Message> messages, Long restaurantId) {
        LocalDateTime now = LocalDateTime.now();
        messages.forEach(message -> {
            message.setId(IdUtil.simpleUUID());
            message.setTime(now);
            redisCache.setCacheObject("menuMessage:" + message.getTo() + ":" + message.getId(), message);
        });
    }

    @Override
    public R getMessagesByUserId(Long userId) {
        if (userService.getById(userId) == null) {
            throw new ServicesException(ResultCode.USER_ID_ERROR);
        }
        List<Message> messages = redisCache.getMultiCacheObject("*Message:" + userId + ":*");
        messages.sort(Comparator.comparing(Message::getTime).reversed());
        return R.ok().data("messages", messages);
    }

    @Override
    public R markMessageRead(String messageId) {
        Collection<String> keys = redisCache.keys("*Message:*:" + messageId);
        redisCache.deleteObject(keys);
        return R.ok();
    }

    @Override
    public R chat(Long from, Long to, String content) {
        Message message = new Message()
                .setId(IdUtil.simpleUUID())
                .setType(MessageType.CHAT.getCode())
                .setFrom(from)
                .setFromName(userService.getById(from).getNickName())
                .setTo(to)
                .setToName(userService.getById(to).getNickName())
                .setContent(content)
                .setTime(LocalDateTime.now());
        redisCache.setCacheObject("chat:" + from + ":" + to + ":" + message.getId(), message);
        return R.ok();
    }

    @Override
    public boolean menuChangeMessageLimit(Long restaurantId) {
        return redisCache.getCacheObject("menuChangeMessageLimit:" + restaurantId) == null;
    }

    @Override
    public void limitMenuMessage(Long restaurantId) {
        redisCache.setCacheObject("menuChangeMessageLimit:" + restaurantId, "OK", 1, TimeUnit.DAYS);
    }

    @Override
    public R getChats(Long userId) {
        List<Message> chats = new ArrayList<>();
        chats.addAll(redisCache.getMultiCacheObject("chat:" + userId + ":*:*"));
        chats.addAll(redisCache.getMultiCacheObject("chat:*:" + userId + ":*"));
        chats.sort(Comparator.comparing(Message::getTime).reversed());
        return R.ok().data("chats", chats);
    }

    @Override
    @Async
    public void cancelOrder(Long restaurantId, Long customerId, Long orderId) {
        Message message = new Message();
        message.setType(MessageType.CANCEL_ORDER.getCode());
        message.setId(IdUtil.simpleUUID());
        message.setFrom(restaurantId);
        message.setFromName(restaurantService.getById(restaurantId).getName());
        message.setTo(customerId);
        message.setToName(userService.getById(customerId).getNickName());
        message.setContent("Your order " + orderId + " has been cancelled.");
        message.setTime(LocalDateTime.now());
        redisCache.setCacheObject("cancelOrderMessage:" + customerId + ":" + orderId + ":" + message.getId(), message);
    }
}
