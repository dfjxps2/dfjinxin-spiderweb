package com.crawler.webapp.crawlerpage.controller;

import com.crawler.webapp.crawlerpage.bean.JobPage;
import com.crawler.webapp.crawlerpage.service.CrawlerPageViewService;
import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by SongCQ on 2017/7/28.
 */
@Controller
@RequestMapping("crawler/pageView")
public class CrawlerPageViewController {

    @Autowired
    private CrawlerPageViewService crawlerPageViewService;

    @RequestMapping("listCrawlerPage")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String listCrawlerPage(int currPage,int pageSize,JobPage jobPage){
        Page<JobPage> resultPage = crawlerPageViewService.listCrawlerPage(currPage, pageSize, jobPage);
        PageResult pageData = PageResult.pageHelperList2PageResult(resultPage);
        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null,pageData);

        return result;
    }

}
