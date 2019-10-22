package com.crawler.webapp.proxyserver.dao;

import com.crawler.webapp.proxyserver.bean.ProxyServer;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by SongCQ on 2017/8/2.
 */
public interface IProxyServerDao {

    @Select("select * from poxy_server")
    @Options(useCache = false)
    List<ProxyServer> listAllProxyServers();

    @Select("<script>" +
            "select * from poxy_server " +
            "<where> " +
            "   <if test='proxy_server_name!=null and proxy_server_name!=&quot;&quot;'>" +
            "       proxy_server_name like concat('%',#{proxy_server_name},'%')" +
            "   </if>" +
            "</where>" +
            "</script>")
    @Options(useCache = false)
    Page<ProxyServer> pagingProxyServers(@Param("currPage") int currPage,
                                         @Param("pageSize") int pageSize,
                                         @Param("proxy_server_name") String proxy_server_name);

    @Select("select * from poxy_server where proxy_server_id = #{proxy_server_id}")
    @Options(useCache = false)
    ProxyServer proxyServer(Integer proxy_server_id);

    @Insert("insert into poxy_server (proxy_server_id,proxy_server_name,proxy_server_ip,proxy_user_name,proxy_user_password) " +
            "values (#{proxy_server_id},#{proxy_server_name},#{proxy_server_ip},#{proxy_user_name},#{proxy_user_password})")
    void saveNewServer(ProxyServer proxyServer);

    @Update("update poxy_server set proxy_server_name=#{proxy_server_name}," +
            "proxy_server_ip=#{proxy_server_ip},proxy_user_name=#{proxy_user_name}," +
            "proxy_user_password=#{proxy_user_password} where proxy_server_id=#{proxy_server_id}")
    void updateServer(ProxyServer proxyServer);

    @Delete("delete from poxy_server where proxy_server_id = #{proxy_server_id}")
    void delServer(Integer proxy_server_id);
}
