package com.crawler.webapp.proxyserver.service.imp;

import com.crawler.webapp.proxyserver.bean.ProxyServer;
import com.crawler.webapp.proxyserver.dao.IProxyServerDao;
import com.crawler.webapp.proxyserver.service.ProxyServerService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by SongCQ on 2017/8/2.
 */
@Service("proxyServerService")
public class ProxyServerServiceImp implements ProxyServerService {

    @Autowired
    private IProxyServerDao proxyServerDao;

    @Override
    public List<ProxyServer> listProxyServers() {
        List<ProxyServer> resultServerList = proxyServerDao.listAllProxyServers();
        return resultServerList;
    }

    @Override
    public Page<ProxyServer> pagingProxyServers(int currPage, int pageSize, String proxy_server_name) {

        Page<ProxyServer> dbPage = proxyServerDao.pagingProxyServers(currPage, pageSize, proxy_server_name);

        return dbPage;
    }

    @Override
    public ProxyServer proxyServer(Integer proxy_server_id) {
        ProxyServer result = proxyServerDao.proxyServer(proxy_server_id);
        return result;
    }

    @Override
    public void saveNewServer(ProxyServer proxyServer) {
        SimpleDateFormat format = new SimpleDateFormat("ssSSS");
        StringBuilder builder = new StringBuilder();
        builder.append(format.format(Calendar.getInstance().getTime()));
        builder.append(new Random().nextInt(50));
        int server_id = new Integer(builder.toString());
        server_id = server_id<<(new Random().nextInt(5));
        proxyServer.setProxy_server_id(server_id);

        proxyServerDao.saveNewServer(proxyServer);
    }

    @Override
    public void updateServer(ProxyServer proxyServer) {
        proxyServerDao.updateServer(proxyServer);
    }

    @Override
    public void delServer(Integer proxy_server_id){
        proxyServerDao.delServer(proxy_server_id);
    }
}
