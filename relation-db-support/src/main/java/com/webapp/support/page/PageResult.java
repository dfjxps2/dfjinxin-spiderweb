package com.webapp.support.page;

import com.github.pagehelper.Page;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.webapp.support.json.JsonSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/7/5.
 */
public class PageResult {

    private int currPage;

    private int pageSize;

    private int totalPage;

    private long totalNum;

    private List dataList;

    public static PageResult pageHelperList2PageResult(Page pageList){
        if(pageList!=null){
            PageResult result = new PageResult();
            result.setCurrPage(pageList.getPageNum());
            result.setPageSize(pageList.getPageSize());
            result.setTotalNum(pageList.getTotal());
            result.setTotalPage(pageList.getPages());
            ArrayList<Object> resultList = Lists.newArrayList();
            resultList.addAll(pageList);
            result.setDataList(resultList);
            return result;
        }

        return null;
    }

    public static String pageHelperList2PageResultStr(Page pageList){
        if(pageList!=null){
            return pageHelperList2PageResult(pageList).toString();
        }

        return null;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public String toString(){
        return JsonSupport.objectToJson(this);
    }
}
