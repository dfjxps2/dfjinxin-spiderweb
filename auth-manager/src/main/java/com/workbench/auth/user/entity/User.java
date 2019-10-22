package com.workbench.auth.user.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by pc on 2017/6/29.
 */
public class User {
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int user_id;
    private String user_pwd;
    private String user_name;
    private String user_name_cn;
    private String user_type;
    private Date reg_date;
    private String user_status;
    private Date last_login_time;
    private Integer  origin_id;
    private String  origin_name;

    public int getUser_id()
    {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        if (this.user_name == null) {
            this.user_name = "";
        }
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getReg_date() {
        try{
            Preconditions.checkNotNull(this.reg_date);
            return dateFormat.format(this.reg_date);
        }catch (NullPointerException e){
            return null;
        }
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getLast_login_time() {
        try{
            Preconditions.checkNotNull(this.last_login_time);
            return dateFormat.format(this.last_login_time);
        }catch (NullPointerException e){
            return null;
        }
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String toString()
    {

        return MoreObjects.toStringHelper(this).add("user_id",getUser_id()).add("user_nm",this.getUser_name())
                .add("user_type",user_type).add("reg_date",getReg_date()).add("user_status",user_status)
                .add("last_login_time",getLast_login_time()).toString();

//        return "User [user_id=" + this.user_id + ", user_nm=" + this.user_nm + ", user_real_nm=" + this.user_real_nm + ", user_pwd=" + this.user_pwd + ", active_ind=" + this.active_ind + ", superior_id=" + this.superior_id + ", sign_in_ip=" + this.sign_in_ip + ", user_group_id=" + this.user_group_id + ", group_name=" + this.group_name + ", role_id=" + this.role_id + ", create_id=" + this.create_id + ", create_name=" + this.create_name + ", create_ts=" + this.create_ts + ", str_create_time=" + this.str_create_time + ", update_id=" + this.update_id + ", update_name=" + this.update_name + ", update_ts=" + this.update_ts + ", str_update_time=" + this.str_update_time + ", menuList=" + this.menuList + ", roleList=" + this.roleList + "]";
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_name_cn() {
        return user_name_cn;
    }

    public void setUser_name_cn(String user_name_cn) {
        this.user_name_cn = user_name_cn;
    }

    public String getOrigin_name() {
        return origin_name;
    }

    public void setOrigin_name(String origin_name) {
        this.origin_name = origin_name;
    }
    public Integer getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(Integer origin_id) {
        this.origin_id = origin_id;
    }
}
