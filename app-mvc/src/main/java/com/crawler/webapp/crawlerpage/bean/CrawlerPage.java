package com.crawler.webapp.crawlerpage.bean;

/**
 * Created by SongCQ on 2017/7/25.
 */
public class CrawlerPage {

    private int page_id;

    private int job_id;

    private int user_id;

    private String page_name;

    private Integer page_type = null; //0 静态页面 1 动态页面 2 JSON页面

    private String page_type_cn = "未知";//0 静态页面 1 动态页面 2 JSON页面

    private Integer data_format = null;//0 TABLE 1 JSON

    private String data_format_cn = "未知";//0 TABLE 1 JSON

    private Integer is_multi_page = null;//0 否 1 是

    private String is_multi_page_cn = "未知";//0 否 1 是

    private String paginate_element;

    private String load_indicator;

    private int page_interval;

    private int max_page_num;

    private int save_page_source;

    private String data_file;

    private String user_name_cn;

    private String job_name;

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

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public Integer getPage_type() {
        return page_type;
    }

    public void setPage_type(Integer page_type) {
        if(page_type != null){
             if(page_type == 0){
                 page_type_cn = "静态页面";
             }else if(page_type == 1){
                 page_type_cn = "动态页面";
             }else if(page_type == 2){
                 page_type_cn = "JSON页面";
             }else{
                 page_type_cn = "未知";
             }
            this.page_type = page_type;
        }
    }

    public String getPage_type_cn() {
        return page_type_cn;
    }

    public void setPage_type_cn(String page_type_cn) {
        this.page_type_cn = page_type_cn;
    }

    public Integer getData_format() {
        return data_format;
    }

    public void setData_format(Integer data_format) {
        if(data_format != null){
            if(data_format == 0){
                data_format_cn = "TABLE";
            }else if(data_format == 1){
                data_format_cn = "JSON";
            }else{
                data_format_cn = "未知";
            }
            this.data_format = data_format;
        }
    }

    public Integer getIs_multi_page() {
        return is_multi_page;
    }

    public void setIs_multi_page(Integer is_multi_page) {
        if(is_multi_page != null){
            if(is_multi_page == 0){
                is_multi_page_cn = "否";
            }else if(is_multi_page == 1){
                is_multi_page_cn = "是";
            }else{
                is_multi_page_cn = "未知";
            }
            this.is_multi_page = is_multi_page;
        }
    }

    public String getData_format_cn() {
        return data_format_cn;
    }

    public void setData_format_cn(String data_format_cn) {
        this.data_format_cn = data_format_cn;
    }

    public String getIs_multi_page_cn() {
        return is_multi_page_cn;
    }

    public void setIs_multi_page_cn(String is_multi_page_cn) {
        this.is_multi_page_cn = is_multi_page_cn;
    }

    public String getPaginate_element() {
        return paginate_element;
    }

    public void setPaginate_element(String paginate_element) {
        this.paginate_element = paginate_element;
    }

    public String getLoad_indicator() {
        return load_indicator;
    }

    public void setLoad_indicator(String load_indicator) {
        this.load_indicator = load_indicator;
    }

    public int getPage_interval() {
        return page_interval;
    }

    public void setPage_interval(int page_interval) {
        this.page_interval = page_interval;
    }

    public int getMax_page_num() {
        return max_page_num;
    }

    public void setMax_page_num(int max_page_num) {
        this.max_page_num = max_page_num;
    }

    public int getSave_page_source() {
        return save_page_source;
    }

    public void setSave_page_source(int save_page_source) {
        this.save_page_source = save_page_source;
    }

    public String getData_file() {
        return data_file;
    }

    public void setData_file(String data_file) {
        this.data_file = data_file;
    }

    public String getUser_name_cn() {
        return user_name_cn;
    }

    public void setUser_name_cn(String user_name_cn) {
        this.user_name_cn = user_name_cn;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }
}
