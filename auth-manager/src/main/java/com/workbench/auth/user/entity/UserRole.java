package com.workbench.auth.user.entity;

import com.workbench.auth.role.entity.Role;

/**
 * Created by pc on 2017/7/6.
 */
public class UserRole {

    private int user_id;

    private int user_role_id;

    private User user;

    private Role role;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
    }
}
