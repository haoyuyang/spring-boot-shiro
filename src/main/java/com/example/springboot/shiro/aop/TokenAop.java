package com.example.springboot.shiro.aop;

import com.example.springboot.shiro.annotation.Token;
import com.example.springboot.shiro.exception.BusinessException;
import com.example.springboot.shiro.util.JwtUtil;
import com.example.springboot.shiro.vo.BaseRequest;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Component
public class TokenAop {

    private static final String ADD = "add";
    private static final String UPDATE = "update";

    @Before(("execution(* com.example.springboot.shiro.controller..*.*(..)) && @annotation(token)"))
    public void beforeAdvice(JoinPoint point, Token token) {
        String operateType = token.value();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String tokenStr = request.getHeader("token");

        BaseRequest baseRequest = null;
        Object[] args = point.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        for (Object obj : args) {
            if (obj instanceof BaseRequest) {
                baseRequest = (BaseRequest) obj;
                break;
            }
        }

       //验证前端是否传了token
        if (StringUtils.isEmpty(tokenStr)) {
            throw new BusinessException("Token为空");
        }

        Claims claims = JwtUtil.validateJWT(tokenStr);

        if (baseRequest == null) {
            return;
        }

        if (StringUtils.isEmpty(operateType)) {
            return;
        }

        String userName = claims.getSubject();
        Date now = new Date();

        if (operateType.contains(ADD)) {
            baseRequest.setCreateTime(now);
            baseRequest.setCreator(userName);
            baseRequest.setUpdater(userName);
        }
        if (operateType.contains(UPDATE)) {
            baseRequest.setUpdateTime(now);
            baseRequest.setUpdater(userName);
        }
    }

}
