package com.crawler.webapp.crawlerpage.service.imp;

import com.crawler.webapp.crawlerpage.bean.JobPage;
import com.crawler.webapp.crawlerpage.dao.CrawlerPageMgDao;
import com.crawler.webapp.crawlerpage.dao.CrawlerPageViewDao;
import com.crawler.webapp.crawlerpage.service.CrawlerPageViewService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SongCQ on 2017/7/28.
 */
@Service("crawlerPageViewService")
public class CrawlerPageViewServiceImp implements CrawlerPageViewService {

    @Autowired
    private CrawlerPageViewDao crawlerPageViewDao;

    @Override
    public Page<JobPage> listCrawlerPage(int currPage, int pageSize, JobPage jobPage) {
        Page<JobPage> pageResult = crawlerPageViewDao.listCrawlerPage(currPage, pageSize, jobPage);
        return pageResult;
    }

}
