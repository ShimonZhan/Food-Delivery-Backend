package uk.ac.soton.food_delivery.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-07 01:18:17
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data;

    //把构造方法私有
    private R() {
    }

    //成功静态方法
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getDescription());
        return r;
    }

    //失败静态方法
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR.getCode());
        r.setMessage(ResultCode.ERROR.getDescription());
        return r;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    // get ServiceException msg and code
    public static R serviceException(ServicesException e) {
        R r = new R();
        return r.success(false).code(e.getCode()).message(e.getMessage());
    }

    public static R resultCode(ResultCode resultCode) {
        R r = new R();
        return r.success(false).code(resultCode.getCode()).message(resultCode.getDescription());
    }
}
