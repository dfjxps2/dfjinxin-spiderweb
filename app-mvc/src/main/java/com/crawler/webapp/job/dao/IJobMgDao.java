package com.crawler.webapp.job.dao;

import com.crawler.webapp.job.bean.JobInfoBean;
import com.crawler.webapp.job.bean.JobStatus;
import com.crawler.webapp.proxyserver.bean.ProxyServer;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/7/31.
 */
@Repository
public interface IJobMgDao {

    @Select("<script>" +
            "select *,job_id as job_id_copy,user_id as user_id_copy from crawl_job  " +
            "<where>" +
            "<if test='jobInfoBean.job_id!=null  '> " +
            "   job_id=#{jobInfoBean.job_id}" +
            "</if>"+
            "<if test='jobInfoBean.is_valid!=null  '> " +
            "   is_valid=#{jobInfoBean.is_valid}" +
            "</if>"+
            "<if test='jobInfoBean.job_name!=null  '> " +
            "   and job_name like concat('%',#{jobInfoBean.job_name},'%')" +
            " </if> " +
            "</where>" +
            "</script>")
    @Results({
            @Result(property = "jobStatus",column = "job_id_copy",
            many = @Many(select="com.crawler.webapp.job.dao.IJobMgDao.getJobStatus")),
            @Result(property = "user",column = "user_id_copy",
                    many = @Many(select="com.workbench.auth.user.dao.IUserServiceDao.getUserByUserId"))})
    @Options(useCache = false)
    Page<JobInfoBean> pagingCrawlList(@Param("currPage") int currPage,@Param("pageSize") int pageSize,@Param("jobInfoBean") JobInfoBean jobInfoBean);

    @Select("<script>" +
            "select cj.*,ch.host_name from crawl_job cj inner join crawl_host ch on cj.host_id = ch.host_id " +
            "<if test='host_id!=null'>" +
            " and cj.host_id = #{host_id}" +
            "</if>" +
            "<if test='host_name!=null'>" +
            " and ch.host_name like concat('%',#{host_name},'%')" +
            "</if>" +
            "</script>")
    @Options(useCache = false)
    Page<JobInfoBean> pagingListByHost(@Param("currPage") int currPage,@Param("pageSize") int pageSize,
                                       @Param("host_id") Integer host_id,@Param("host_name") String host_name);


    @Select("select cs.* from crawl_status cs where job_id = #{job_id} order by start_time desc limit 1")
    @Options(useCache = false)
    JobStatus getJobStatus(int job_id);

    @Select("select * from crawl_src_type")
    @Options(useCache = false)
    List<Map<String, Object>> crawlSrcType();

    @Select("select * from data_store")
    @Options(useCache = false)
    List<Map<String,Object>> dataStore();

    @Select("select * from job_schedule")
    @Options(useCache = false)
    List<Map<String,Object>> jobScheduleList();

    @Select("select * from crawl_host")
    @Options(useCache = false)
    List<Map<String,Object>> jobHostList();

    @Insert("insert into crawl_job (job_id,user_id,job_name,is_valid,host_id,max_page_num,page_life_cycle," +
            "entry_page_id,job_cat_id,max_depth,crawl_src_type_id,start_urls,data_store_id,job_schedule_id) values " +
            "(#{job_id},#{user_id},#{job_name},#{is_valid},#{host_id},#{max_page_num},#{page_life_cycle}," +
            "#{entry_page_id},#{job_cat_id},#{max_depth},#{crawl_src_type_id},#{start_urls},#{data_store_id},#{job_schedule_id})")
    void saveJobInfo(JobInfoBean jobInfo);


    @Insert("insert into poxy_assign (proxy_server_id,job_id,user_id ) values (#{proxy_server_id},#{job_id},#{user_id})")
    void saveProxyServer(@Param("proxy_server_id") String proxy_server_id,@Param("job_id") Integer job_id,@Param("user_id") Integer user_id);

    @Select("select * from crawl_job where job_id = #{job_id}")
    @Options(useCache = false)
    JobInfoBean getCrawlData(int job_id);

    @Select("select ps.* from poxy_server ps inner join poxy_assign pa on ps.proxy_server_id=pa.proxy_server_id and pa.job_id = #{job_id}")
    @Options(useCache = false)
    List<ProxyServer> listAllProxyServerByJob(int job_id);

    @Update("update crawl_job set job_name=#{job_name},is_valid=#{is_valid},host_id=#{host_id},max_page_num=#{max_page_num}," +
            "page_life_cycle=#{page_life_cycle},entry_page_id=#{entry_page_id},job_cat_id=#{job_cat_id},max_depth=#{max_depth}," +
                    "crawl_src_type_id=#{crawl_src_type_id},start_urls=#{start_urls},data_store_id=#{data_store_id},job_schedule_id=#{job_schedule_id} " +
            " where job_id=#{job_id}")
    void updateJobInfo(JobInfoBean jobInfo);

    @Delete("delete from poxy_assign where job_id=#{job_id}")
    void deleteAllProxyServer(int job_id);

    @Delete("delete from crawl_job where job_id=#{job_id}")
    void delJob(int job_id);

    @Delete("delete from page_field_locate_relation where job_id = #{job_id}")
    void removeLocateRelationByJob(int job_id);

    @Delete("delete from page_field where job_id=#{job_id}")
    void removePageFieldsByJob(int job_id);

    @Delete("delete from crawl_page_config where job_id=#{job_id}")
    void deleteCrawlerPageByJob(int job_id);

    @Delete("delete from page_link where job_id=#{job_id}")
    void removePageLinksByJob(int job_id);

    @Select("select * from crawl_job")
    @Options(useCache = false)
    List<JobInfoBean> listAllJob();

    @Select("select max(job_id) from crawl_job")
    @Options(useCache = false)
    Integer getjobInfoMaxId();

    @Select("select * from crawl_job where job_schedule_id=#{job_schedule_id}")
    @Options(useCache = false)
    List<JobInfoBean> listJobByScheduleId(@Param("job_schedule_id") Integer jobScheduleId);
}
