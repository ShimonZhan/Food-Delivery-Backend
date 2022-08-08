package uk.ac.soton.food_delivery.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.soton.food_delivery.service.MailService;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class MailServiceImpl implements MailService {
    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    @Async
    public CompletableFuture<Boolean> sendMail(String toEmail, String link, String event, String username) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toEmail);
            helper.setSubject("Food Delivery: " + event);
            helper.setText("<div style=\"font-size:16px;color:#333;font-family:'Lato',Tahoma,Verdana,Segoe,sans-serif;\"><p>Hi " + username + ",</p>\n" +
                    "<p>You are receiving this email because you have requested to <span class=\"il\">" + event.toLowerCase(Locale.ROOT) + "</span>. To continue with the process please click the below link or copy and paste the URL into your web browser.\n" +
                    "This link will expire in 10 minutes, at which time you will have to request a new link by trying the process again.</p>\n" +
                    "<p><a href=\"" + link + "\" title=\"" + event + "\" target=\"_blank\" ><span class=\"il\">" + event + "</span></a></p>\n" +
                    "<p>If you did not request this action you can simply ignore this email message.\n" +
                    "Your account is safe and has not been compromised.</p></div>", true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(false);
        }
        return CompletableFuture.completedFuture(true);
    }
}
