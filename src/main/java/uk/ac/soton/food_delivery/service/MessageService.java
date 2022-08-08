package uk.ac.soton.food_delivery.service;

import uk.ac.soton.food_delivery.entity.Message;
import uk.ac.soton.food_delivery.utils.R;

import java.util.List;

/**
 * @author ShimonZhan
 * @since 2022-05-11 07:03:52
 */
public interface MessageService {
    R getMessagesByUserId(Long userId);
    R markMessageRead(String messageId);
    R chat(Long from, Long to, String content);


    void addMenuChangeMessages(List<Message> messages, Long restaurantId);

    boolean menuChangeMessageLimit(Long restaurantId);

    void limitMenuMessage(Long restaurantId);

    R getChats(Long userId);

    void cancelOrder(Long restaurantId, Long customerId, Long orderId);
}
