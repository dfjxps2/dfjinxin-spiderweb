package com.crawler.webapp.job.controller;

import com.crawler.webapp.job.bean.CrawlerConfig;
import com.crawler.webapp.job.bean.JobInfoBean;
import com.crawler.webapp.job.service.JobConfigService;
import com.crawler.webapp.job.service.JobMgService;
import com.crawler.webapp.util.URIEncoder;
import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.webapp.support.session.SessionSupport;
import com.workbench.auth.user.entity.User;
import com.workbench.auth.user.service.UserService;
import com.workbench.spring.aop.annotation.JsonMsgParam;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/10/10.
 */
@Controller
@RequestMapping("crawler/jobConfig")
public class JobConfigController {
    private Logger logger = LoggerFactory.getLogger(JobConfigController.class);

    @Autowired
    private JobConfigService jobConfigService;
    @Autowired
    private UserService userService;
    @Autowired
    private JobMgService jobMgService;

    @RequestMapping("pagingList")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String pagingCrawlConfigList(int currPage,int pageSize,String param_name)throws Exception{
        param_name = strToUtf8(param_name);
        CrawlerConfig crawlerConfig = new CrawlerConfig();
        crawlerConfig.setParam_name(param_name);
        Page<CrawlerConfig> crawListPage = jobConfigService.pagingCrawlConfigList(currPage, pageSize,crawlerConfig);
        //转义表达式
        for (CrawlerConfig config:crawListPage){
                  encoderConfig(config);
        }
        PageResult pageResult = PageResult.pageHelperList2PageResult(crawListPage);
        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null,pageResult);

        logger.debug("paging crawl list result :{}",result);
        return result;
    }

    /**
     * 编辑时的查询方法
     * @param param_name
     * @return
     */
    @RequestMapping("getJonConfig")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String getJonConfig(String param_name, int job_id,int user_id) throws Exception{
        //得到所有用户
        List<User> userList = userService.listAllUser();
        //得到所有的采集列表
        List<JobInfoBean> jobInfoBeans = jobMgService.listAllJob();

        param_name = strToUtf8(param_name);
        CrawlerConfig crawlerConfig = jobConfigService.getCrawlerConfig(param_name,job_id,user_id);
        //转义表达式
        encoderConfig(crawlerConfig);
        Map<String,Object> resultMap = new HashMap();
        resultMap.put("userList",userList);
        resultMap.put("jobInfoBeans",jobInfoBeans);
        resultMap.put("crawlerConfig",crawlerConfig);

        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,resultMap);
        return result;
    }

    /**
     * 新增时的查询方法
     * @return
     */
    @RequestMapping("getJonConfigInfo")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String getJonConfigInfo(){
        //得到所有用户
        List<User> userList = userService.listAllUser();
        //得到所有采集列表
        List<JobInfoBean> jobInfoBeans = jobMgService.listAllJob();
        Map<String,Object> resultMap = new HashMap();
        resultMap.put("userList",userList);
        resultMap.put("jobInfoBeans",jobInfoBeans);
        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,resultMap);
        return result;
    }


    @RequestMapping("saveNewConfig")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String saveCrawlConfig(@RequestBody CrawlerConfig crawlerConfig){
        jobConfigService.saveCrawlConfig(crawlerConfig);
        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
        return  result;
    }

    @RequestMapping("updateConfig")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String updateConfig(String param_name,String param_value,int user_id,int job_id,String p_name,int j_id,int u_id)throws Exception{
        param_name = strToUtf8(param_name);
        param_value = strToUtf8(param_value);
        p_name = strToUtf8(p_name);
        jobConfigService.updateCrawlConfig(param_name,param_value,user_id,job_id,p_name,j_id,u_id);
        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
        return  result;
    }


    @RequestMapping("delCrawlConfig")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String delCrawlConfig(int job_id,int user_id,String param_name)throws Exception{
        param_name = strToUtf8(param_name);
        jobConfigService.delCrawlConfig(job_id,user_id,param_name);
        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"删除成功",null,null);
        return result;
    }

    /**
     *  转义表达式
     */
    public void encoderConfig(CrawlerConfig config){
        String param_value = config.getParam_value();
        if (param_value != null && !"".equals(param_value)){
            config.setParam_value(URIEncoder.encodeURIComponent(param_value));
        }
    }

    public String strToUtf8(String str)throws Exception{
        str = new String(str.getBytes("iso-8859-1"),"utf-8");
        return str;
    }
}
