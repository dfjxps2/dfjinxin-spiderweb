package com.crawler.webapp.job.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobSchedule {
    private Integer job_schedule_id;

    private Integer job_schedule_type;

    private String job_schedule_name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date job_schedule_start_time;

    private String cron_expression;


    public Integer getJob_schedule_id() {
        return job_schedule_id;
    }

    public void setJob_schedule_id(Integer job_schedule_id) {
        this.job_schedule_id = job_schedule_id;
    }

    public Integer getJob_schedule_type() {
        return job_schedule_type;
    }

    public void setJob_schedule_type(Integer job_schedule_type) {
        this.job_schedule_type = job_schedule_type;
    }

    public String getJob_schedule_name() {
        return job_schedule_name;
    }

    public void setJob_schedule_name(String job_schedule_name) {
        this.job_schedule_name = job_schedule_name;
    }

    public Date getJob_schedule_start_time() {
        return job_schedule_start_time;
    }

    public void setJob_schedule_start_time(Date job_schedule_start_time) {
        this.job_schedule_start_time = job_schedule_start_time;
    }

    public String getCron_expression() {
        return cron_expression;
    }

    public void setCron_expression(String cron_expression) {
        this.cron_expression = cron_expression;
    }
}
