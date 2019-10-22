package com.crawler.webapp.job.bean;

/**
 * Created by SongCQ on 2017/9/27.
 */
public class JobCategory {

    private Integer job_cat_id;

    private Integer super_cat_id;

    private String job_cat_name;

    public Integer getJob_cat_id() {
        return job_cat_id;
    }

    public void setJob_cat_id(Integer job_cat_id) {
        this.job_cat_id = job_cat_id;
    }

    public Integer getSuper_cat_id() {
        return super_cat_id;
    }

    public void setSuper_cat_id(Integer super_cat_id) {
        this.super_cat_id = super_cat_id;
    }

    public String getJob_cat_name() {
        return job_cat_name;
    }

    public void setJob_cat_name(String job_cat_name) {
        this.job_cat_name = job_cat_name;
    }
}
