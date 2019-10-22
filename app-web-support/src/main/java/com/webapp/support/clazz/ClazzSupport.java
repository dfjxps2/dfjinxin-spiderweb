package com.webapp.support.clazz;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * Created by SongCQ on 2017/7/27.
 */
public class ClazzSupport {
    public static String[] checkoutAllParams4Method(Class clazz,String methodName){
        for(Method method :clazz.getMethods()){
            if(method.getName().equals(methodName)){
                String[] allParams = checkoutAllParams4Method(method);
                return allParams;
            }
        }
        return null;
    }


    private static String[] checkoutAllParams4Method(Method method){
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

        String[] allParams = localVariableTableParameterNameDiscoverer.getParameterNames(method);

        return allParams;
    }
}
