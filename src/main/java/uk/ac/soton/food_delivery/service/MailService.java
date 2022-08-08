package uk.ac.soton.food_delivery.service;

import java.util.concurrent.CompletableFuture;

public interface MailService {
    CompletableFuture<Boolean> sendMail(String toEmail, String link, String event, String username);
}
