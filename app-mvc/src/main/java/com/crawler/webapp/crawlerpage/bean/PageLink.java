package com.crawler.webapp.crawlerpage.bean;

/**
 * Created by SongCQ on 2017/7/25.
 */
public class PageLink {

    private int link_id;

    private int page_id;

    private int job_id;

    private int user_id;

    private String link_locate_pattern;

    private String link_ext_pattern;

    private int next_page_id;

    public int getLink_id() {
        return link_id;
    }

    public void setLink_id(int link_id) {
        this.link_id = link_id;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLink_locate_pattern() {
        return link_locate_pattern;
    }

    public void setLink_locate_pattern(String link_locate_pattern) {
        this.link_locate_pattern = link_locate_pattern;
    }

    public String getLink_ext_pattern() {
        return link_ext_pattern;
    }

    public void setLink_ext_pattern(String link_ext_pattern) {
        this.link_ext_pattern = link_ext_pattern;
    }

    public int getNext_page_id() {
        return next_page_id;
    }

    public void setNext_page_id(int next_page_id) {
        this.next_page_id = next_page_id;
    }
}
