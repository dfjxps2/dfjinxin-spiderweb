package com.crawler.webapp.crawlerpage.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SongCQ on 2017/7/28.
 */
public class JobPage {

    private String page_url;

    private Date download_time;

    private String download_time_str;

    private String download_time_start;

    private String download_time_end;

    private int job_id;

    private String job_name;

    private int page_id;

    private String page_name;

    private int user_id;

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }


//    public void setDownload_time(Date download_time) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        this.download_time = format.format(download_time);
//    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getDownload_time() {
        return download_time;
    }

    public void setDownload_time(Date download_time) {
        this.download_time = download_time;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        if(download_time!=null)
            download_time_str = format.format(download_time);

    }

    public String getDownload_time_start() {
        return download_time_start;
    }

    public void setDownload_time_start(String download_time_start) {
        this.download_time_start = download_time_start;
    }

    public String getDownload_time_end() {
        return download_time_end;
    }

    public void setDownload_time_end(String download_time_end) {
        this.download_time_end = download_time_end;
    }

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getDownload_time_str() {
        return download_time_str;
    }

    public void setDownload_time_str(String download_time_str) {
        this.download_time_str = download_time_str;
    }
}
