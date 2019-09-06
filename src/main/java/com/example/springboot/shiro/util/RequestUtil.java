package com.example.springboot.shiro.util;

import com.example.springboot.shiro.vo.BaseResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @description:
 * @author: HYY
 * @date: 2019/9/5
 * @since
 */
public class RequestUtil {
    /**
     * 处理请求参数异常公共方法
     */
    public static <T extends BaseResponse> T handleInvalidRequest(BindingResult bindingResult, T t) {
        FieldError fieldError = bindingResult.getFieldError();
        String errorMsg = fieldError.getField() + ": " + fieldError.getDefaultMessage();
        t.setCode(21);
        t.setMessage(errorMsg);
        return t;
    }
}
