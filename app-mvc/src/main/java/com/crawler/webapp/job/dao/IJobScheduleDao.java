package com.crawler.webapp.job.dao;

import com.crawler.webapp.job.bean.CrawlerConfig;
import com.crawler.webapp.job.bean.JobSchedule;
import com.crawler.webapp.job.bean.JobScheduleParam;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/10/10.
 */
public interface IJobScheduleDao {

   /* @Select("select *,job_schedule_id as jobScheduleId from job_schedule_param" )
    @Results({
            @Result(property = "jobSchedule", column = "jobScheduleId",
                    many = @Many(select="com.crawler.webapp.job.dao.IJobScheduleDao.getJobSchedule"))
    })*/
    @Select("select job_schedule_id,job_schedule_type,job_schedule_start_time,cron_expression from job_schedule" )
    @Options(useCache = false)
    Page<JobSchedule> pagingJobScheduleList(@Param("currPage") int currPage, @Param("pageSize") int pageSize);

    @Select("select * from job_schedule  where job_schedule_id = #{job_schedule_id}")
    @Options(useCache = false)
    JobSchedule getJobSchedule(int job_schedule_id);


    @Insert("insert into job_schedule (job_schedule_id,job_schedule_type) values (#{job_schedule_id},#{job_schedule_type})")
    void saveJobSchedule(@Param("job_schedule_id") int job_schedule_id,@Param("job_schedule_type") int job_schedule_type);



    @Insert("insert into job_schedule_param (job_schedule_id,param_name,param_value) values (#{job_schedule_id},#{param_name},#{param_value})")
    void saveNewScheduleParam(@Param("param_name") String param_name,@Param("param_value")
            String param_value,@Param("job_schedule_id") int job_schedule_id);


    @Select("select *,job_schedule_id as jscheduleId from job_schedule_param where job_schedule_id = #{id} and param_name = #{name}" )
    @Results({
            @Result(property = "jobSchedule", column = "jscheduleId",
                    many = @Many(select="com.crawler.webapp.job.dao.IJobScheduleDao.getJobSchedule"))
    })
    @Options(useCache = false)
    JobScheduleParam getScheduleParamInfo(@Param("id") int job_schedule_id,@Param("name") String param_name);

    @Update("update job_schedule js,job_schedule_param jsp set js.job_schedule_id = #{sId},js.job_schedule_type = #{type}," +
            "jsp.job_schedule_id = #{sId},jsp.param_name = #{Pname},jsp.param_value = #{Pvalue} " +
            "where js.job_schedule_id = jsp.job_schedule_id and jsp.job_schedule_id = #{id} and jsp.param_name = #{name}")
    void updateScheduleAndSceduleParam(@Param("sId") int job_schedule_id,@Param("type") int job_schedule_type,@Param("Pname") String param_name,@Param("Pvalue") String param_value,@Param("id") int schedule_id,@Param("name") String pName);

    @Delete("delete from job_schedule_param where job_schedule_id = #{id} and param_name = #{name}")
    void delSchedule(@Param("id") int job_schedule_id,@Param("name") String param_name);

 @Select("select job_schedule_id,job_schedule_name,job_schedule_type,job_schedule_start_time,cron_expression from job_schedule" )
 @Options(useCache = false)
 List<Map<String,Object>> jobScheduleList();

}
