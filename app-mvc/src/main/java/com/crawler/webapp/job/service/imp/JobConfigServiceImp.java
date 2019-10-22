package com.crawler.webapp.job.service.imp;

import com.crawler.webapp.job.bean.CrawlerConfig;
import com.crawler.webapp.job.dao.IJobConfigDao;
import com.crawler.webapp.job.service.JobConfigService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by SongCQ on 2017/10/10.
 */
@Service("jobConfigService")
public class JobConfigServiceImp implements JobConfigService {

    @Autowired(required = false)
    private IJobConfigDao iJobConfigDao;

    @Override
    public Page<CrawlerConfig> pagingCrawlConfigList(int currPage, int pageSize,CrawlerConfig crawlerConfig) {
        Page<CrawlerConfig> result = iJobConfigDao.pagingCrawlConfigList(currPage, pageSize,crawlerConfig);
        return result;
    }

    @Override
    public void saveCrawlConfig(CrawlerConfig crawlerConfig) {
        iJobConfigDao.saveCrawlConfig(crawlerConfig);
    }
    @Override
    public void updateCrawlConfig(String param_name,String param_value,int user_id,int job_id,String p_name,int j_id,int u_id) {
        iJobConfigDao.updateCrawlConfig(param_name,param_value,user_id,job_id,p_name,j_id,u_id);
    }
    @Override
    public CrawlerConfig getCrawlerConfig(String param_name,int job_id,int user_id) {
        CrawlerConfig crawlerConfig = iJobConfigDao.getCrawlerConfig(param_name,job_id,user_id);
        return crawlerConfig;
    }

    @Override
    public void delCrawlConfig(int job_id,int user_id,String param_name) {
        iJobConfigDao.delCrawlConfig(job_id,user_id,param_name);

    }
}
