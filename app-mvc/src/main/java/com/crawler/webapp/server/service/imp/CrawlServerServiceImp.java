package com.crawler.webapp.server.service.imp;

import com.crawler.webapp.server.bean.CrawlServer;
import com.crawler.webapp.server.dao.ICrawlServerDao;
import com.crawler.webapp.server.service.CrawlServerService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by SongCQ on 2017/8/3.
 */
@Service("crawlServerService")
public class CrawlServerServiceImp implements CrawlServerService {

    @Autowired
    private ICrawlServerDao crawlServerDao;

    @Override
    public Page<CrawlServer> pagingServer(int currPage, int pageSize, String host_name) {
        return crawlServerDao.pagingServer(currPage, pageSize, host_name);
    }

    @Override
    public void saveNewServer(CrawlServer crawlServer) {
        SimpleDateFormat format = new SimpleDateFormat("ssSSS");
        StringBuilder builder = new StringBuilder();
        builder.append(format.format(Calendar.getInstance().getTime()));
        builder.append(new Random().nextInt(50));
        int host_id = new Integer(builder.toString());
        host_id = host_id<<(new Random().nextInt(5));
        crawlServer.setHost_id(host_id);

        crawlServerDao.saveNewServer(crawlServer);
    }

    @Override
    public CrawlServer getServer(int host_id) {
        return crawlServerDao.getServer(host_id);
    }

    @Override
    public void updateServer(CrawlServer crawlServer) {
        crawlServerDao.updateServer(crawlServer);

    }

    @Override
    public void delServer(Integer host_id) {
        crawlServerDao.delServer(host_id);
    }
}
