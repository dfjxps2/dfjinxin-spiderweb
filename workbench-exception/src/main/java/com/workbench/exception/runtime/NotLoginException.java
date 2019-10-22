package com.workbench.exception.runtime;


/**
 * Created by SongCQ on 2017/8/29.
 * 用户未登录系统
 */

public class NotLoginException extends RuntimeException{

    public NotLoginException(String exceptionMsg){
        super(exceptionMsg);
    }
}
