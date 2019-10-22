package com.crawler.webapp.proxyserver.bean;

/**
 * Created by SongCQ on 2017/8/2.
 */
public class ProxyServer {

    private int proxy_server_id;

    private String proxy_server_name;

    private String proxy_server_ip;

    private String proxy_user_name;

    private String proxy_user_password;

    public int getProxy_server_id() {
        return proxy_server_id;
    }

    public void setProxy_server_id(int proxy_server_id) {
        this.proxy_server_id = proxy_server_id;
    }

    public String getProxy_server_name() {
        return proxy_server_name;
    }

    public void setProxy_server_name(String proxy_server_name) {
        this.proxy_server_name = proxy_server_name;
    }

    public String getProxy_server_ip() {
        return proxy_server_ip;
    }

    public void setProxy_server_ip(String proxy_server_ip) {
        this.proxy_server_ip = proxy_server_ip;
    }

    public String getProxy_user_name() {
        return proxy_user_name;
    }

    public void setProxy_user_name(String proxy_user_name) {
        this.proxy_user_name = proxy_user_name;
    }

    public String getProxy_user_password() {
        return proxy_user_password;
    }

    public void setProxy_user_password(String proxy_user_password) {
        this.proxy_user_password = proxy_user_password;
    }
}
