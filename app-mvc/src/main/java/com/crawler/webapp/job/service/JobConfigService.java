package com.crawler.webapp.job.service;

import com.crawler.webapp.job.bean.CrawlerConfig;
import com.github.pagehelper.Page;

/**
 * Created by SongCQ on 2017/10/10.
 */
public interface JobConfigService {
    Page<CrawlerConfig> pagingCrawlConfigList(int currPage, int pageSize,CrawlerConfig crawlerConfig);

    CrawlerConfig getCrawlerConfig(String param_name,int job_id,int user_id);

    void saveCrawlConfig(CrawlerConfig crawlerConfig);

    void updateCrawlConfig(String param_name,String param_value,int user_id,int job_id,String p_name,int j_id,int u_id);

    void delCrawlConfig(int job_id,int user_id,String param_name);
}
