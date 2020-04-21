package com.crawler.webapp.job.dao;

import com.crawler.webapp.job.bean.JobStatus;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

/**
 * Created by SongCQ on 2017/8/3.
 */
public interface IJobStatusDao {

    @Select("<script>select cs.*,cj.job_name,cs.user_id as user_id_copy from crawl_status cs " +
            "inner join crawl_job cj on " +
            "cs.job_id = cj.job_id " +
            "<if test='job_name!=null and job_name!=&quot;&quot;'>" +
            " and cj.job_name like concat('%',#{job_name},'%')" +
            "</if>" +
            " order by cs.job_id,cs.start_time desc" +
            "</script> ")
    @Results({
            @Result(property = "user",column = "user_id_copy",
                    many = @Many(select="com.workbench.auth.user.dao.IUserServiceDao.getUserByUserId"))})
    @Options(useCache = false)
    Page<JobStatus> pagingCrawlStatusList(@Param("currPage") int currPage, @Param("pageSize") int pageSize,
                                          @Param("job_name") String job_name);
}
