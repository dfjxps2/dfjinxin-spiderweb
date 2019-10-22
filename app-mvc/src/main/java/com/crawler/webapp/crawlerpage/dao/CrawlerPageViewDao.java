package com.crawler.webapp.crawlerpage.dao;

import com.crawler.webapp.crawlerpage.bean.JobPage;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by SongCQ on 2017/7/28.
 */
public interface CrawlerPageViewDao {

    @Select("<script>select  distinct cp.*,cj.job_name from crawl_page cp inner join crawl_job cj on cp.job_id = cj.job_id " +
            " <if test='jobPageObj.page_url!=null and jobPageObj.page_url!=&quot;&quot;'> " +
            "       and cj.page_url like concat('%',#{jobPageObj.page_url},'%') " +
            "</if> " +

            " <if test='jobPageObj.job_id!=null and jobPageObj.job_id!=&quot;&quot;'> " +
            "       and cj.job_id = #{jobPageObj.job_id} " +
            "</if> " +
            " <if test='jobPageObj.page_url!=null and jobPageObj.page_url!=&quot;&quot;'> " +
            "       and cj.page_url like concat('%',#{jobPageObj.page_url},'%') " +
            "</if> " +
            " <if test='jobPageObj.user_id!=0'> and cp.user_id=#{jobPageObj.user_id}</if> " +
            " <if test='jobPageObj.download_time_start!=null and jobPageObj.download_time_start!=&quot;&quot; '> " +
            " and cp.download_time<![CDATA[> ]]>#{jobPageObj.download_time_start}" +
            " </if> " +
            " <if test='jobPageObj.download_time_end!=null and jobPageObj.download_time_end!=&quot;&quot; '> " +
            " and cp.download_time <![CDATA[< ]]> #{jobPageObj.download_time_end}" +
            " </if> " +
            "</script>")
    @Options(useCache = false)
    Page<JobPage> listCrawlerPage(@Param("currPage") int currPage,@Param("pageSize") int pageSize,
                                  @Param("jobPageObj") JobPage jobPageObj);

}
