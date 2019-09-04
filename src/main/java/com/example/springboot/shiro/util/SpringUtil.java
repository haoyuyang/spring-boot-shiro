package com.example.springboot.shiro.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }



    public static String getAppName() {
        return context.getEnvironment().getProperty("spring.application.name");
    }

    public static <T> T getBean(Class<T> tClass) {
        return context.getBean(tClass);
    }

    public static <T> T getBean(String var1, Class<T> var2) {
        return context.getBean(var1, var2);
    }

    public static Object getBean(String idOrName) {
        return context.getBean(idOrName);
    }


}
