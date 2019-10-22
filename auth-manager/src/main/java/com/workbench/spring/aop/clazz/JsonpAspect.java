package com.workbench.spring.aop.clazz;

import com.google.common.base.Strings;
import com.webapp.support.clazz.ClazzSupport;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.jsonp.JsonpConfig;
import com.webapp.support.jsonp.JsonpSupport;
import com.workbench.exception.runtime.NotLoginException;
import com.workbench.spring.aop.annotation.JsonMsgParam;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/7/25.
 * JSONP报文AOP类
 */
@Component
@Aspect
@Order(10)
public class JsonpAspect {

    private Logger logger = LoggerFactory.getLogger(JsonpAspect.class);

    @Autowired
    private JsonpConfig jsonpConfig;

    @Pointcut("@annotation(com.workbench.spring.aop.annotation.JsonpCallback)")
    private void annotationJsonpCallback(){} //声明一个切入点,切入点的名称其实是一个方法


    @Around("annotationJsonpCallback()")
    public String doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
            if(!jsonpConfig.isUseJsonp()){
                return (String) pjp.proceed();
            }

            Object object= null;
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            request.setCharacterEncoding("utf-8");
            if(checkIsJsonMsg()) {//发用数据是否为JSON报文

                int argsLength = pjp.getArgs().length;
                Object[] paramObjs = new Object[argsLength];

                String methodName = pjp.getSignature().getName();
                Method[] allMethods = pjp.getTarget().getClass().getMethods();
                for (Method method : allMethods) {
                    if(!method.getName().equals(methodName)) continue;

                    JsonpCallback jsonpCallback = method.getAnnotation(JsonpCallback.class);
                    boolean isJsonRequest = jsonpCallback != null ? jsonpCallback.isJsonRequest() : false;
                    if (isJsonRequest) {

                        Annotation[][] allParamAnnotations = method.getParameterAnnotations();
                        Class<?>[] allParamTypes = method.getParameterTypes();
                        int paramCount = 0;
                        for (Annotation[] paramAnnotations : allParamAnnotations) {
                            Object paramObj = null;
                            for (Annotation paramAnnotation : paramAnnotations) {
                                if (paramAnnotation instanceof JsonMsgParam) {
                                    JsonMsgParam jsonMsgParam = (JsonMsgParam) paramAnnotation;
                                    String jsonName = jsonMsgParam.jsonName();
                                    String jsonStr = getJsonStrFromRequest(jsonName, request);
                                    Class<?>[] jsonObjType = jsonMsgParam.jsonObjTypes();
                                    Class<?> rootClassType = allParamTypes[paramCount];
                                    if (jsonObjType.length==1&&rootClassType.equals(jsonObjType[0])) {//非泛型
                                        paramObj = JsonSupport.jsonToObect(jsonStr, rootClassType);
                                    } else {//泛型
                                        if(List.class.isAssignableFrom(rootClassType)){
                                            paramObj = JsonSupport.jsonToList4Generic(jsonStr, rootClassType, jsonObjType[0]);
                                        }else if(Map.class.isAssignableFrom(rootClassType)){
                                            paramObj = JsonSupport.jsonToMap4Generic(jsonStr, rootClassType, jsonObjType);
                                        }else if(Array.class.isAssignableFrom(rootClassType)){

                                        }
                                    }
                                }
                            }
                            paramObjs[paramCount] = paramObj;
                            paramCount++;
                        }
                    }
                    object = pjp.proceed(paramObjs);
                }
            }
            else{
                object = pjp.proceed();
            }

            String jsonpCallBackStr = JsonpSupport.objectToJsonp(getJsonpCallbackName(), object);
            logger.debug("Response message to web page value is: {}",jsonpCallBackStr);

            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse().setContentType("text/html;charset=utf-8");
            return jsonpCallBackStr;

    }

    private String getJsonpCallbackName(){
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String callBackFunctionName = JsonpSupport.jsonpCallbackFunctionName(request);
        callBackFunctionName = Strings.isNullOrEmpty(callBackFunctionName)?"callback":callBackFunctionName;
        logger.debug("call back function name is {}",callBackFunctionName);
        return callBackFunctionName;
    }

    private boolean checkIsJsonMsg(){
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String isJsonStr = request.getParameter("isJson");

        return  isJsonStr!=null&&"Y".equals(isJsonStr)?true:false;
    }

    public String getJsonStrFromRequest(String jsonStrName,HttpServletRequest request){
        String jsonMsg = request.getParameter(jsonStrName);
        return jsonMsg;
    }


}
