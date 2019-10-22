package com.crawler.webapp.server.service;

import com.crawler.webapp.server.bean.CrawlServer;
import com.github.pagehelper.Page;

/**
 * Created by SongCQ on 2017/8/3.
 */
public interface CrawlServerService {

    Page<CrawlServer> pagingServer(int currPage, int pageSize, String host_name);

    void saveNewServer(CrawlServer crawlServer);

    CrawlServer getServer(int host_id);

    void updateServer(CrawlServer crawlServer);

    void delServer(Integer host_id);
}
