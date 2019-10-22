package com.crawler.webapp.search.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Strings;
import com.webapp.support.json.JsonSupport;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/9/27.
 */
public class SearchBean {

    private String version;

    private String pageId;

    private String searchContent;

    private String url;

    private String jobStartDate;

    private String jobEndData;

    private List<String> jobTypes;

    private String jobIds;

    private List<Object> jobIdList;

    private String rows;

    private Map<String,String> pagingMap;

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJobStartDate() {
        return jobStartDate;
    }

    public void setJobStartDate(String jobStartDate) {
        this.jobStartDate = jobStartDate;
    }

    public String getJobEndData() {
        return jobEndData;
    }

    public void setJobEndData(String jobEndData) {
        this.jobEndData = jobEndData;
    }

    public List<String> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(List<String> jobTypes) {
        this.jobTypes = jobTypes;
    }

    public String getJobIds() {
        return jobIds;
    }

    public void setJobIds(String jobIds) throws IOException {
        List<Object> jobIdList = (List<Object>) JsonSupport.jsonToObect(jobIds,List.class);
        this.setJobIdList(jobIdList);
        this.jobIds = jobIds;
    }

    public List<Object> getJobIdList() {
        return jobIdList;
    }

    public void setJobIdList(List<Object> jobIdList) {
        this.jobIdList = jobIdList;
    }

    public Map<String, String> getPagingMap() {
        return pagingMap;
    }

    public void setPagingMap(Map<String, String> pagingMap) {
        this.pagingMap = pagingMap;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
