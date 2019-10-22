package com.workbench.spring.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by SongCQ on 2017/7/28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface JsonMsgParam {
    /**
     *     Json报文名称（因为使用jsonp只能发送get无法发送post，所以前端传过来的json报文也有对应的名字:例如）
     *     http://localhost:8080/AAA?jsonName=[{id:i,name:a},{id:i,name:a}]&jsonName2=[{id:i,name:a},{id:i,name:a}]
     *     这时候就可以设置jsonNames 为jsonName,jsonName2
     */
    String jsonName() default "jsonObj";

    /**
     * Json报文对应的java类型
     * 例如前端界面URL：http://localhost:8080/AAA?jsonName=[{id:i,name:a},{id:i,name:a}]&jsonName2={id:i,name:a},{id:i,name:a}
     * 那么jsonName参数就是个list类型的json对象，jsonName2是一个普通的json对象。
     * 此时获取数据的方法必然是类似下面的方法
     * public String excampleReqeust(List<A> jsonName,B jsonName2){
     *     *****
     * }
     *
     * 这时候该注解就可以设置为：A.class,B.class 注意：这里使用A.class 而不是list.class
     * @return
     */
    Class<?>[] jsonObjTypes() default {Object.class};
}
