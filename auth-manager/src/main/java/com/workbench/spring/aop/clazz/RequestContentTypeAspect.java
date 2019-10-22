package com.workbench.spring.aop.clazz;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring MVC对于参数的映射使用注解方式@RequestBody @RequestParam
 * 本AOP用于简化注解，将content-type:application/json的body方式直接转成参数，避免在controller层使用注解参数
 * Created by SongCQ on 2018/10/17.
 */
//@Component
//@Aspect
//@Order(2)//权重最高的是日志AOP
public class RequestContentTypeAspect {

    @Around("annotationJsonpCallback()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String contentTypes = request.getContentType();
        String[] contentTypeArray = contentTypes.split(";");
        for(String contentType : contentTypeArray){
            if(contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)){
            }
        }

        return pjp.proceed();
    }
}
