package com.crawler.webapp.job.bean;

import com.workbench.auth.user.entity.User;

/**
 * Created by SongCQ on 2017/10/10.
 */
public class CrawlerConfig {

    private Integer user_id;

    private User user_info;

    private Integer job_id;

    private Integer job_id_cp;

    private JobInfoBean job_info;

    private String param_name;

    private String param_value;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public String getParam_value() {
        return param_value;
    }

    public void setParam_value(String param_value) {
        this.param_value = param_value;
    }

    public JobInfoBean getJob_info() {
        return job_info;
    }

    public void setJob_info(JobInfoBean job_info) {
        this.job_info = job_info;
    }

    public Integer getJob_id_cp() {
        return job_id_cp;
    }

    public void setJob_id_cp(Integer job_id_cp) {
        this.job_id_cp = job_id_cp;
    }

    public User getUser_info() {
        return user_info;
    }

    public void setUser_info(User user_info) {
        this.user_info = user_info;
    }
}
