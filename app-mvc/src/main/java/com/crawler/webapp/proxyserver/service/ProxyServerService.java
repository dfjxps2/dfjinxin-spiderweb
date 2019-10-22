package com.crawler.webapp.proxyserver.service;

import com.crawler.webapp.proxyserver.bean.ProxyServer;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Created by SongCQ on 2017/8/2.
 */
public interface ProxyServerService {
    List<ProxyServer> listProxyServers();

    Page<ProxyServer> pagingProxyServers(int currPage, int pageSize, String proxy_server_name);

    ProxyServer proxyServer(Integer proxy_server_id);

    void saveNewServer(ProxyServer proxyServer);

    void updateServer(ProxyServer proxyServer);

    void delServer(Integer proxy_server_id);
}
