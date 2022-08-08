package uk.ac.soton.food_delivery.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import uk.ac.soton.food_delivery.utils.R;
import uk.ac.soton.food_delivery.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ShimonZhan
 * @since 2022-03-12 23:35:48
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
//        R r = R.error().code(HttpStatus.SC_FORBIDDEN).message("Your permissions are insufficient");
//        String json = JSON.toJSONString(r);
//        // handle exception
//        WebUtils.renderString(response, json);

        // Insufficient user rights
        R r = R.resultCode(ResultCode.USER_NO_PERMISSIONS);
        // output
        WebUtils.writeJSON(response, r);
    }
}
