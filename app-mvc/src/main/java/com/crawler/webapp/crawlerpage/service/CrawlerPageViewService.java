package com.crawler.webapp.crawlerpage.service;

import com.crawler.webapp.crawlerpage.bean.JobPage;
import com.github.pagehelper.Page;

/**
 * Created by SongCQ on 2017/7/28.
 */
public interface CrawlerPageViewService {
    Page<JobPage> listCrawlerPage(int currPage, int pageSize, JobPage jobPage);
}
