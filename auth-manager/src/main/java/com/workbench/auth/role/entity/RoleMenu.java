package com.workbench.auth.role.entity;

/**
 * Created by pc on 2017/7/3.
 */
public class RoleMenu {
    private int user_role_id;
    private int module_id;
    private String access_privilege;

    public int getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public String getAccess_privilege() {
        return access_privilege;
    }

    public void setAccess_privilege(String access_privilege) {
        this.access_privilege = access_privilege;
    }
}
