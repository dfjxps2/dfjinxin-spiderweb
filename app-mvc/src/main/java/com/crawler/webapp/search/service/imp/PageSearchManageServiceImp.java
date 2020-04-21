package com.crawler.webapp.search.service.imp;

import com.crawler.webapp.job.bean.CrawlerServers;
import com.crawler.webapp.job.bean.JobCategory;
import com.crawler.webapp.search.bean.SearchBean;
import com.crawler.webapp.search.dao.PageSearchManageDao;
import com.crawler.webapp.search.service.PageSearchManageService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.webapp.support.httpClient.HttpClientSupport;
import com.webapp.support.httpClient.HttpSendMessage;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.session.SessionSupport;
import com.workbench.auth.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by SongCQ on 2017/9/27.
 */
@Service("pageSearchManageService")
public class PageSearchManageServiceImp implements PageSearchManageService {

    private Logger logger = LoggerFactory.getLogger(PageSearchManageServiceImp.class);

    @Autowired
    private PageSearchManageDao pageSearchManageDao;

    @Autowired
    private CrawlerServers crawlerServers;

    @Override
    public List<JobCategory> listJobCategory() {
        List<JobCategory> resultList = pageSearchManageDao.listJobCategory();
        return resultList;
    }

    @Override
    public List<Map<String, Object>> listJobAndTypes() {
        List<Map<String, Object>> jobListAndTypes = pageSearchManageDao.listJobAndTypes();
        return jobListAndTypes;
    }

    @Override
    public Map<String, Object> doSearch(SearchBean searchBean) {
        List<Object> jobIds = searchBean.getJobIdList();
        Map<String,Object> responseMap = new HashMap<>();

        User user = SessionSupport.checkoutUserFromSession();

        if(jobIds!=null&&jobIds.size()>0){
            for(Object jobId:jobIds){
                if(jobId!=null && jobId instanceof String && new StringBuilder().append(jobId).toString().endsWith("S")){
                    continue;
                }

//                builder.append(solrServerAddres).append("/job_").append("1").append("_").append(jobId).append("/select");

                Map<String,Object> paramMap = new IdentityHashMap<>();
                if(!Strings.isNullOrEmpty(searchBean.getSearchContent())){
                    paramMap.put("q",new StringBuilder().append("page_content:").append(searchBean.getSearchContent()).toString());
                }else{
                    paramMap.put("q","*:*");
                }
                paramMap.put("sort","id desc");
                paramMap.put("wt","json");

                boolean fullData = false;
                if(Strings.isNullOrEmpty(searchBean.getVersion())){
                    searchAll(searchBean,paramMap);
                    Map<String, String> pagingMap = searchBean.getPagingMap();
                    Integer pageNo = Integer.valueOf(pagingMap.get(jobId.toString())) - 1;
                    Integer start = pageNo * Integer.valueOf(searchBean.getRows());
                    paramMap.put("start",start.toString());
                    paramMap.put("rows",searchBean.getRows());
                }else{
                    searchDetail(searchBean,paramMap);
                    paramMap.put("start","0");
                    paramMap.put("rows","8");
                    fullData = true;
                }


                HttpClientSupport httpClient = HttpClientSupport.getInstance(
                        new StringBuilder()
                                .append(crawlerServers.getSolrHost())
                                .append(":")
                                .append(crawlerServers.getSolrPort()).toString());
                String requestUrl = new StringBuilder().append(
                        crawlerServers.getSolrServiceName()).append("/job_").append(user.getUser_id()).append("_").append(jobId).append("/select/").toString();
                try {
                    String response = httpClient.sendRequest(requestUrl, paramMap, RequestMethod.GET, false);
                    logger.debug("send to solr service,param-->{} #### solr service response-->{}",paramMap.toString(),response);
                    if(Strings.isNullOrEmpty(response)){
                        responseMap.put(jobId.toString(), Lists.newArrayList());
                        continue;
                    }

                    checkOutResponse(response,jobId,responseMap,fullData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }



            }
        }

        return responseMap;
    }


    private void searchDetail(SearchBean searchBean,Map<String,Object> paramMap){
        String version = searchBean.getVersion();
        paramMap.put(new String("fq"),new StringBuilder("_version_:").append(version).toString());

    }

    private void searchAll(SearchBean searchBean,Map<String,Object> paramMap){
        String jobStartDate = searchBean.getJobStartDate();
        String jobEndDate = searchBean.getJobEndData();
        boolean hasStartDate = !Strings.isNullOrEmpty(jobStartDate);
        boolean hasEndDate = !Strings.isNullOrEmpty(jobEndDate);
        if(hasStartDate&&hasEndDate){
            paramMap.put(new String("fq"),new StringBuilder().append("crawl_time:[").append(jobStartDate).append("T00:00:00Z")
                    .append(" TO ").append(jobEndDate).append("T24:00:00Z]").toString());
        }else if(!hasStartDate&&hasEndDate){
            paramMap.put(new String("fq"),new StringBuilder().append("crawl_time:[").append("1900-01-01T00:00:00Z")
                    .append(" TO ").append(jobEndDate).append("T24:00:00Z]").toString());
        }else if(hasStartDate&&!hasEndDate){
            paramMap.put(new String("fq"),new StringBuilder().append("crawl_time:[").append(jobStartDate).append("T00:00:00Z")
                    .append(" TO ").append("NOW]").toString());
        }

        if(!Strings.isNullOrEmpty(searchBean.getUrl())){
            paramMap.put(new String("fq"),new StringBuilder("id:*").append(searchBean.getUrl()).append("*").toString());
        }

        if(!Strings.isNullOrEmpty(searchBean.getPageId())){
            paramMap.put(new String("fq"),new StringBuilder("page_id:").append(searchBean.getPageId()).toString());
        }
    }

    public void checkOutResponse(String response,Object jobId,Map<String,Object> responseMap,boolean fullData){

        HashMap resultMap = JsonSupport.jsonToMap(response);
        if(resultMap == null){
            List<Map<String,Object>> docsList = new ArrayList<>();
            responseMap.put(jobId.toString(),docsList);
            responseMap.put(jobId+"_num_found",0);
            return;
        }
        Map<String,Object> responseMapTmp = (Map<String,Object>) resultMap.get("response");
        List<Map<String,Object>> docsList = (List<Map<String, Object>>) responseMapTmp.get("docs");
        Integer numFound = (Integer) responseMapTmp.get("numFound");
        for(Map<String,Object> docsMap:docsList){
            String page_source = (String) docsMap.get("page_source");
            page_source = page_source.replaceAll("/r","");
            page_source = page_source.replaceAll("/n","");
            if(!fullData&&page_source.length()>200)
                docsMap.put("page_source",page_source.substring(0,200));
            else
                docsMap.put("page_source",page_source);
            String page_content = (String) docsMap.get("page_content");
            if(!fullData&&page_content.length()>200)
                docsMap.put("page_content",page_content.substring(0,200));
            else
                docsMap.put("page_content",page_content);


            String id = (String) docsMap.get("id");
            String[] idSplit = id.split(",");
            if(idSplit!=null&&idSplit.length>1){
                String url = idSplit[0];
                docsMap.put("url",url);
            }else
                docsMap.put("url",id);

            Long version_long = (Long) docsMap.get("_version_");

            docsMap.put("_version_",version_long.toString());
        }
        responseMap.put(jobId.toString(),docsList);
        responseMap.put(jobId+"_num_found",numFound);
    }
}
