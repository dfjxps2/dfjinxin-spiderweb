package com.crawler.webapp.search.dao;

import com.crawler.webapp.job.bean.JobCategory;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/9/27.
 */
public interface PageSearchManageDao {

    @Select("select job_cat_id,job_cat_name,super_cat_id from crawl_job_category")
    @Options(useCache = false)
    List<JobCategory> listJobCategory();

    @Select("<script>select job_id,user_id,job_name,is_valid,host_id,max_page_num,page_life_cycle," +
            "entry_page_id,job_cat_id,max_depth ,crawl_src_type_id, " +
            "start_urls, data_store_id,job_schedule_id from crawl_job where job_cat_id in " +
                "<foreach item='jobTypeItem' index='index' collection='jobTypes' open='(' separator=',' close=')'>" +
                "   #{jobTypeItem}" +
                "</foreach>" +
            "</script>")
    @Options(useCache = false)
    List<Integer> listJobsByCatId(@Param("jobTypes") List<Integer> jobTypes);

    @Select("SELECT job_id id,job_name name,concat(job_cat_id,'S')  super_id FROM crawl_job cj  union " +
            "SELECT concat(job_cat_id,\"S\") id,job_cat_name name,concat(super_cat_id,'S') super_id FROM crawl_job_category")
    @Options(useCache = false)
    List<Map<String,Object>> listJobAndTypes();
}
