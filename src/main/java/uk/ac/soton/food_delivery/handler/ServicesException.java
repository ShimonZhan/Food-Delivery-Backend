package uk.ac.soton.food_delivery.handler;

import uk.ac.soton.food_delivery.utils.R;

/**
 * @author ShimonZhan
 * @since 2022-03-13 15:33:14
 */
public class ServicesException extends RuntimeException {
    private final R r;

    public ServicesException(ResultCode resultCode) {
        r = R.error().code(resultCode.getCode()).message(resultCode.getDescription());
    }

    public Integer getCode() {
        return r.getCode();
    }

    public String getMessage() {
        return r.getMessage();
    }

    public void setCode(final Integer code) {
        r.setCode(code);
    }

    public void setMessage(final String message) {
        r.setMessage(message);
    }
}
