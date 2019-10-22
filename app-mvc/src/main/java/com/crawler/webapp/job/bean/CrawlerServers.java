package com.crawler.webapp.job.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by SongCQ on 2017/8/29.
 */
public class CrawlerServers {

    private List<String> serverAddresList;

    private String solrAddres;

    private String solrHost;

    private String solrPort;

    private String solrServiceName;

    private String spiderAddres;

    private String spiderHost;

    private String spiderPort;

    private String spiderServiceName;



    private AtomicInteger automicInteger = new AtomicInteger(0);

    public List<String> getServerAddresList() {
        return serverAddresList;
    }

    public void setServerAddresList(List<String> serverAddresList) {
        this.serverAddresList = serverAddresList;
    }

    public String getCrawlerServer(){
        int nowVal = automicInteger.getAndIncrement();
        System.out.println("nowVal ......."+nowVal);
        if(nowVal==serverAddresList.size()){
            automicInteger.set(0);
            return serverAddresList.get(0);
        }else{
            return serverAddresList.get(nowVal);
        }
    }

    public String getSolrAddres() {
        return solrAddres;
    }

    public void setSolrAddres(String solrAddres) {
        this.solrAddres = solrAddres;
    }

    public String getSolrHost() {
        return solrHost;
    }

    public void setSolrHost(String solrHost) {
        this.solrHost = solrHost;
    }

    public String getSolrPort() {
        return solrPort;
    }

    public void setSolrPort(String solrPort) {
        this.solrPort = solrPort;
    }

    public String getSolrServiceName() {
        return solrServiceName;
    }

    public void setSolrServiceName(String solrServiceName) {
        this.solrServiceName = solrServiceName;
    }

    public String getSpiderAddres() {
        return spiderAddres;
    }

    public void setSpiderAddres(String spiderAddres) {
        this.spiderAddres = spiderAddres;
    }

    public String getSpiderHost() {
        return spiderHost;
    }

    public void setSpiderHost(String spiderHost) {
        this.spiderHost = spiderHost;
    }

    public String getSpiderPort() {
        return spiderPort;
    }

    public void setSpiderPort(String spiderPort) {
        this.spiderPort = spiderPort;
    }

    public String getSpiderServiceName() {
        return spiderServiceName;
    }

    public void setSpiderServiceName(String spiderServiceName) {
        this.spiderServiceName = spiderServiceName;
    }
}
