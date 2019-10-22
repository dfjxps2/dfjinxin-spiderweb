package com.workbench.exception.runtime;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String exceptionMsg){
        super(exceptionMsg);
    }
}
