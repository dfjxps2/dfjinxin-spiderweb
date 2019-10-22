package com.workbench.auth.group.entity;

/**
 * Created by SongCQ on 2017/7/7.
 */
public class Group {

    private  int user_group_id;
    private String user_group_name;
    private int super_user_group_id;

    public int getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(int user_group_id) {
        this.user_group_id = user_group_id;
    }

    public String getUser_group_name() {
        return user_group_name;
    }

    public void setUser_group_name(String user_group_name) {
        this.user_group_name = user_group_name;
    }

    public int getSuper_user_group_id() {
        return super_user_group_id;
    }

    public void setSuper_user_group_id(int super_user_group_id) {
        this.super_user_group_id = super_user_group_id;
    }
}
