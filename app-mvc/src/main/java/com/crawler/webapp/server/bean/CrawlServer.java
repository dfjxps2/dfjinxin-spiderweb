package com.crawler.webapp.server.bean;

import com.workbench.auth.group.entity.Group;
import com.workbench.auth.user.dao.IUserGroupDao;

/**
 * Created by SongCQ on 2017/8/3.
 */
public class CrawlServer {

    private int host_id;

    private String host_name;

    private String host_ip;

    private Integer host_status;

    private String host_status_cn = "未知";

    private Integer user_group_id;

    private Group user_group;


    public int getHost_id() {
        return host_id;
    }

    public void setHost_id(int host_id) {
        this.host_id = host_id;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getHost_ip() {
        return host_ip;
    }

    public void setHost_ip(String host_ip) {
        this.host_ip = host_ip;
    }

    public Integer getHost_status() {
        return host_status;
    }

    public void setHost_status(Integer host_status) {
        this.host_status = host_status;
        if(host_status==null)
            return;
        switch (host_status){
            case 0:host_status_cn="离线";break;
            case 1:host_status_cn="在线";break;
        }
    }

    public Integer getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(Integer user_group_id) {
        this.user_group_id = user_group_id;
    }

    public Group getUser_group() {
        return user_group;
    }

    public void setUser_group(Group user_group) {
        this.user_group = user_group;
    }

    public String getHost_status_cn() {
        return host_status_cn;
    }

    public void setHost_status_cn(String host_status_cn) {
        this.host_status_cn = host_status_cn;

    }
}
