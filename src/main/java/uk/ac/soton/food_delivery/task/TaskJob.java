package uk.ac.soton.food_delivery.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.ac.soton.food_delivery.entity.task.FinishOrder;
import uk.ac.soton.food_delivery.service.OrderService;
import uk.ac.soton.food_delivery.utils.RedisCache;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ShimonZhan
 * @since 2022-04-28 00:44:26
 */
@Component
@Slf4j
public class TaskJob {
    @Resource
    private RedisCache redisCache;

    @Resource
    private OrderService orderService;

    @Scheduled(fixedDelay = 120000, initialDelay = 10000)
    public void deliveredOrder() {
        List<FinishOrder> finishOrders = redisCache.getMultiCacheObject("finishJob:*");
        List<Long> finishIds = finishOrders.stream()
                .filter(x -> x.getFinishTime().isBefore(LocalDateTime.now()))
                .map(FinishOrder::getOrderId)
                .toList();
        if (!finishIds.isEmpty()) {
            redisCache.deleteObject(finishIds.stream()
                    .map(id -> "finishJob:" + id)
                    .toList());
            orderService.deliverManFinishOrder(finishIds);
            String idsString = finishIds.stream()
                    .map(x -> Long.toString(x))
                    .reduce((a, b) -> a + ", " + b)
                    .get();
            log.info("finishJob：orderIds=[{}]", idsString);
        }

    }
}