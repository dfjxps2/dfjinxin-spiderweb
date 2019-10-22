package com.crawler.webapp.job.bean;

public class JobScheduleParam {

    private Integer job_schedule_id;
    private String  param_name;
    private String  param_value;
    private JobSchedule jobSchedule;


    public Integer getJob_schedule_id() {
        return job_schedule_id;
    }

    public void setJob_schedule_id(Integer job_schedule_id) {
        this.job_schedule_id = job_schedule_id;
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

    public JobSchedule getJobSchedule() {
        return jobSchedule;
    }

    public void setJobSchedule(JobSchedule jobSchedule) {
        this.jobSchedule = jobSchedule;
    }
}
