package com.example.springboot.shiro.handler;

import com.example.springboot.shiro.exception.BusinessException;
import com.example.springboot.shiro.vo.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @description: 全局异常处理类
 * @author: HYY
 * @date: 2019/9/6
 * @since
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse exceptionHandler(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException exception = (BusinessException) e;
            return new BaseResponse(exception.getCode(), e.getMessage());
        }

        return new BaseResponse(500, "未知错误");
    }
}
