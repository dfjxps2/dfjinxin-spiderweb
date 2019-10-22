package com.crawler.webapp.job.dao;

import com.crawler.webapp.job.bean.CrawlerConfig;
import com.crawler.webapp.job.bean.JobInfoBean;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

/**
 * Created by SongCQ on 2017/10/10.
 */
public interface IJobConfigDao {

    @Select("<script>" +
            "SELECT user_id,user_id as user_id_cp,job_id,job_id as job_id_cp,param_name,param_value FROM crawl_config  " +
            "<where>" +
            "<if test='crawlerConfig.param_name!=null  '> " +
            "   and param_name like concat('%',#{crawlerConfig.param_name},'%')" +
            " </if> " +
            "</where>" +
            "</script>")
    @Results({
            @Result(property = "job_info",column = "job_id_cp",
                    many = @Many(select="com.crawler.webapp.job.dao.IJobMgDao.getCrawlData")),
            @Result(property = "user_info",column = "user_id_cp",
                    many = @Many(select="com.workbench.auth.user.dao.IUserServiceDao.getUserByUserId"))})
    @Options(useCache = false)
    Page<CrawlerConfig> pagingCrawlConfigList(@Param("currPage") int currPage, @Param("pageSize") int pageSize,@Param("crawlerConfig") CrawlerConfig crawlerConfig);


    @Insert("insert into crawl_config (user_id,job_id,param_name,param_value) values (#{user_id},#{job_id},#{param_name},#{param_value})")
    void saveCrawlConfig(CrawlerConfig crawlerConfig);

    @Delete("delete from crawl_config where user_id=#{u_id} and job_id=#{j_id} and param_name=#{name}")
    void delCrawlConfig(@Param("j_id") int job_id,@Param("u_id") int user_id,@Param("name") String param_name);

    @Select("select * from crawl_config where param_name = #{name} and job_id=#{id} and user_id = #{userId}")
    @Options(useCache = false)
    CrawlerConfig getCrawlerConfig(@Param("name") String param_name,@Param("id") int job_id,@Param("userId")int user_id);

    @Update("update crawl_config set user_id=#{userId},job_id=#{jobId},param_name=#{pName},param_value=#{pValue} where param_name = #{name} and job_id = #{id} and user_id = #{Uid}")
    void updateCrawlConfig(@Param("pName")String param_name,@Param("pValue")String param_value,@Param("userId")int user_id,@Param("jobId")int job_id,@Param("name")String p_name,@Param("id")int j_id,@Param("Uid")int u_id);
}
