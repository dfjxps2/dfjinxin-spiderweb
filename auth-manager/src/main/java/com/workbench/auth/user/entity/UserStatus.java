package com.workbench.auth.user.entity;

public enum UserStatus {

    NORMAL(0,"正常"),
    LOCK(1,"锁定"),
    DELETE(2,"软删除"),
    PWD_EXPIRED(3,"密码过期"),
    STOP(4,"停用");

    private int status ;

    private String status_cn;


    UserStatus(int status,String status_cn){
        this.status = status;
        this.status_cn = status_cn;
    }

    public int getStatus(){
        return status;
    }

    public boolean equal(int status){
        if(this.status == status)
            return true;
        else
            return false;
    }

}
