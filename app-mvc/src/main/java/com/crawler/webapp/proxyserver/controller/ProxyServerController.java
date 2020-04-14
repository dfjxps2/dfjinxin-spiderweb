package com.crawler.webapp.proxyserver.controller;

import com.crawler.webapp.proxyserver.bean.ProxyServer;
import com.crawler.webapp.proxyserver.service.ProxyServerService;
import com.github.pagehelper.Page;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by SongCQ on 2017/8/2.
 */
@Controller
@RequestMapping("crawler/proxyServer")
public class ProxyServerController {

    @Autowired
    private ProxyServerService proxyServerService;

    @RequestMapping("listProxyServers")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String listProxyServers(){
        List<ProxyServer> resultData = proxyServerService.listProxyServers();
        String jsonResult = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, resultData);
        return jsonResult;
    }

    @RequestMapping("pagingProxyServers")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String pagingProxyServers(int currPage,int pageSize,String proxy_server_name ){
        Page<ProxyServer> resultData = proxyServerService.pagingProxyServers(currPage,pageSize,proxy_server_name);
        PageResult pageResult = PageResult.pageHelperList2PageResult(resultData);
        String jsonpResult = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, pageResult);
        return jsonpResult;
    }

    @RequestMapping("proxyServer")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String proxyServers(Integer proxy_server_id){
        ProxyServer resultData = proxyServerService.proxyServer(proxy_server_id);
        String jsonpResult = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, resultData);
        return jsonpResult;
    }

    @RequestMapping("saveNewServer")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String saveNewServer(@RequestBody ProxyServer proxyServer){
        proxyServerService.saveNewServer(proxyServer);
        String jsonpResult = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "保存成功", null, null);
        return jsonpResult;
    }

    @RequestMapping("updateServer")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String updateServer(ProxyServer proxyServer){
        proxyServerService.updateServer(proxyServer);
        String jsonpResult = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "保存成功", null, null);
        return jsonpResult;
    }


    @RequestMapping("delServer")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String delServer(Integer proxy_server_id){
        proxyServerService.delServer(proxy_server_id);
        String jsonpResult = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "删除成功", null, null);
        return jsonpResult;
    }


}
