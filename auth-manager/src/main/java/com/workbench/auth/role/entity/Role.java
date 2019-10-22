package com.workbench.auth.role.entity;

/**
 * Created by pc on 2017/7/6.
 */
public class Role {

    private int user_role_id;
    private String user_role_name;

    public int getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
    }

    public String getUser_role_name() {
        return user_role_name;
    }

    public void setUser_role_name(String user_role_name) {
        this.user_role_name = user_role_name;
    }
}
