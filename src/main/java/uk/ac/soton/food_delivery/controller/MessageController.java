package uk.ac.soton.food_delivery.controller;

import org.springframework.web.bind.annotation.*;
import uk.ac.soton.food_delivery.service.MessageService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;

/**
 * @author ShimonZhan
 * @since 2022-05-11 07:50:18
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    @PostMapping("/chat")
    public R chat(Long from, Long to, String content) {
        return messageService.chat(from, to, content);
    }

    @GetMapping("/getChats")
    public R getChats(Long userId) {
        return messageService.getChats(userId);
    }

    @GetMapping("/getMessages")
    public R getMessages(Long userId) {
        return messageService.getMessagesByUserId(userId);
    }

    @DeleteMapping("/readMessage")
    public R readMessage(String messageId) {
        return messageService.markMessageRead(messageId);
    }
}
