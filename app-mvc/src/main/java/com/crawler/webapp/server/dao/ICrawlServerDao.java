package com.crawler.webapp.server.dao;

import com.crawler.webapp.server.bean.CrawlServer;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

/**
 * Created by SongCQ on 2017/8/3.
 */
public interface ICrawlServerDao {

    @Select("<script>select *,user_group_id as user_group_id_cp from crawl_host " +
            "<where>" +
            "<if test='host_name !=null and host_name!=&quot;&quot;'> " +
            "   host_name like concat('%',#{host_name},'%') " +
            "</if>" +
            "</where></script>")
    @Results({
            @Result(property = "user_group",column = "user_group_id_cp",
                    many = @Many(select="com.workbench.auth.group.dao.IGroupDao.getUserGroup"))})
    @Options(useCache = false)
    Page<CrawlServer> pagingServer(@Param("currPage") int currPage,@Param("pageSize") int pageSize,
                                   @Param("host_name") String host_name);

    @Insert("insert into crawl_host (host_id,host_name,host_ip,host_status,user_group_id) values " +
            "(#{host_id},#{host_name},#{host_ip},#{host_status},#{user_group_id})")
    void saveNewServer(CrawlServer crawlServer);

    @Select("select * from crawl_host where host_id=#{host_id}")
    @Options(useCache = false)
    CrawlServer getServer(int host_id);

    @Update("update crawl_host set host_id=#{host_id},host_name=#{host_name},host_ip=#{host_ip}," +
            "host_status=#{host_status},user_group_id=#{user_group_id} where host_id=#{host_id}")
    void updateServer(CrawlServer crawlServer);

    @Delete("delete from crawl_host where host_id=#{host_id}")
    void delServer(Integer host_id);
}
