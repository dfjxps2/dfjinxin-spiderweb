package com.crawler.webapp.job.controller;

import com.crawler.webapp.job.bean.JobStatus;
import com.crawler.webapp.job.service.JobStatusService;
import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by SongCQ on 2017/8/3.
 */
@Controller
@RequestMapping("crawler/jobStatus")
public class JobStatusController {

    private Logger logger = LoggerFactory.getLogger(JobStatusController.class);

    @Autowired
    private JobStatusService jobStatusService;

    @RequestMapping("pagingList")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String pagingCrawlStatusList(int currPage,int pageSize, String job_name){
        Page<JobStatus> crawStatusListPage = jobStatusService.pagingCrawlStatusList(currPage, pageSize, job_name);
        PageResult pageResult = PageResult.pageHelperList2PageResult(crawStatusListPage);
        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null,pageResult);

        logger.debug("paging crawl list result :{}",result);
        return result;
    }


}
