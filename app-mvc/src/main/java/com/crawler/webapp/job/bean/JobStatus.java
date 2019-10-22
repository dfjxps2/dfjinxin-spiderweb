package com.crawler.webapp.job.bean;

import com.workbench.auth.user.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SongCQ on 2017/7/31.
 */
public class JobStatus {

    private int user_id;

    private User user;

    private int job_id;

    private String job_name;

    private Date start_time;

    private String start_time_str;

    private int run_status;

    private String run_status_cn = "未知";

    private int download_page_num;

    private int pending_page_num;

    private int error_page_num;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        start_time_str = format.format(start_time);
    }

    public int getRun_status() {
        return run_status;
    }

    public void setRun_status(int run_status) {
        switch (run_status){
            case 0:run_status_cn = "开始";
            case 1:run_status_cn = "运行";
            case 2:run_status_cn = "结束";
        }
        this.run_status = run_status;
    }

    public int getDownload_page_num() {
        return download_page_num;
    }

    public void setDownload_page_num(int download_page_num) {
        this.download_page_num = download_page_num;
    }

    public int getPending_page_num() {
        return pending_page_num;
    }

    public void setPending_page_num(int pending_page_num) {
        this.pending_page_num = pending_page_num;
    }

    public int getError_page_num() {
        return error_page_num;
    }

    public void setError_page_num(int error_page_num) {
        this.error_page_num = error_page_num;
    }

    public String getRun_status_cn() {
        return run_status_cn;
    }

    public void setRun_status_cn(String run_status_cn) {


        this.run_status_cn = run_status_cn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getStart_time_str() {
        return start_time_str;
    }

    public void setStart_time_str(String start_time_str) {
        this.start_time_str = start_time_str;
    }
}
