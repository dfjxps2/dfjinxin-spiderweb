package com.crawler.webapp.search.controller;

import com.crawler.webapp.job.bean.JobCategory;
import com.crawler.webapp.search.bean.SearchBean;
import com.crawler.webapp.search.service.PageSearchManageService;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/9/27.
 */

@Controller
@RequestMapping("search/pageSearch")
public class PageSearchManageController {

    @Autowired
    private PageSearchManageService pageSearchManageService;

    @RequestMapping("listJobTypes")
    @CrossOrigin(allowCredentials="true")
    @ResponseBody
    public String listJobTypes(){
        List<JobCategory> jobCataList = pageSearchManageService.listJobCategory();
        String responseStr = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, jobCataList);

        return responseStr;
    }

    @RequestMapping("listJobAndTypes")
    @CrossOrigin(allowCredentials="true")
    @ResponseBody
    public String listJobAndTypes(){
        List<Map<String, Object>> jobsAndTypes = pageSearchManageService.listJobAndTypes();
        String responseStr = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, jobsAndTypes);

        return responseStr;
    }

    @RequestMapping("doSearch")
    @CrossOrigin(allowCredentials="true")
    @ResponseBody
    public JsonResult doSearch(@RequestBody SearchBean searchBean){
        Map<String, Object> reponseFromSolr = pageSearchManageService.doSearch(searchBean);
        JsonResult response = JsonSupport.makeJsonpResult(JsonResult.RESULT.SUCCESS, "获取成功", null, reponseFromSolr);
        return response;
    }

    @RequestMapping("searchDetail")
    @CrossOrigin(allowCredentials="true")
    @ResponseBody
    public JsonResult searchDetail(String job_id, String version){
        SearchBean searchBean = new SearchBean();
        List<Object> jobList = new ArrayList<>();
        jobList.add(new Integer(job_id));
        searchBean.setJobIdList(jobList);
        searchBean.setVersion(version);
        Map<String, Object> reponseFromSolr = pageSearchManageService.doSearch(searchBean);
        JsonResult response = JsonSupport.makeJsonpResult(JsonResult.RESULT.SUCCESS, "获取成功", null, reponseFromSolr);
        return response;
    }

}
