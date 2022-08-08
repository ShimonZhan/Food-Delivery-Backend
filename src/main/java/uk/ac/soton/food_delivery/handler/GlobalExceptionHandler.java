package uk.ac.soton.food_delivery.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uk.ac.soton.food_delivery.utils.R;

/**
 * @author ShimonZhan
 * @since 2022-03-13 15:28:14
 */
@Slf4j
@RestControllerAdvice  // catch exceptions from controller
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(value = ServicesException.class)
    public R servicesExceptionHandler(ServicesException e) {
        log.error("Service exception! The reason is：{}", e.getMessage());
        return R.serviceException(e);
    }


    /**
     * 其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public R exceptionHandler(Exception e) {
        log.error("Unknown exception! The reason is:", e);
        return R.error();
    }


    /**
     * 将 AuthenticationException 异常往上抛，让认证处理器去处理
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public void accountExpiredExceptionHandler(AuthenticationException authException) {
        throw authException;
    }

    /**
     * 将 AccessDeniedException 异常往上抛，让授权处理器去处理
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public void accessDeniedExceptionHandler(AccessDeniedException accDenException) {
        throw accDenException;
    }
}

