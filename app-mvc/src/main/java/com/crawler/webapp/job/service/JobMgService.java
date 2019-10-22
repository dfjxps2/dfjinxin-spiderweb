package com.crawler.webapp.job.service;

import com.crawler.webapp.job.bean.JobInfoBean;
import com.crawler.webapp.proxyserver.bean.ProxyServer;
import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/7/31.
 */
public interface JobMgService {

    Page<JobInfoBean> pagingCrawlList(int currPage,int pageSize, JobInfoBean jobInfoBean);

    List<Map<String, Object>> crawlSrcType();

    List<Map<String,Object>> dataStore();

    List<Map<String,Object>> jobScheduleList();

    List<Map<String,Object>> jobHostList();

    void saveNewJob(JobInfoBean jobInfo, List<String> proxyServers);

    JobInfoBean getCrawlData(int job_id);

    List<ProxyServer> listAllProxyServerByJob(int job_id);

    void updateJobInfo(JobInfoBean jobInfo, ArrayList<String> proxyServers);

    void deleJob(int job_id);

    Page<JobInfoBean> pagingListByHost(int currPage, int pageSize,Integer host_id, String host_name);

    String startJob(int job_id, int user_id);

    String stopJob(int job_id, int user_id);

    String updateJob(int job_id, int user_id);

    List<JobInfoBean> listAllJob();
}
