package uk.ac.soton.food_delivery.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import uk.ac.soton.food_delivery.utils.R;
import uk.ac.soton.food_delivery.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ShimonZhan
 * @since 2022-03-12 23:29:49
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        R r;
        // handle exception
        if (authException instanceof BadCredentialsException) {
            // username or password error
            r = R.resultCode(ResultCode.USERNAME_PASSWORD_ERROR);
        } else if (authException instanceof DisabledException) {
            r = R.resultCode(ResultCode.USER_ACCOUNT_DISABLE);
        } else if (authException instanceof LockedException) {
            r = R.resultCode(ResultCode.USER_ACCOUNT_INACTIVATED);
        }else if (authException instanceof InternalAuthenticationServiceException && authException.getCause() instanceof ServicesException) {
            // custom ServicesException
            r = R.serviceException((ServicesException) authException.getCause());
        } else {
            // other exception
            r = R.resultCode(ResultCode.USER_NOT_LOGIN);
        }

        // print error
        log.error(String.valueOf(authException));

        WebUtils.writeJSON(response, r);
    }
}
