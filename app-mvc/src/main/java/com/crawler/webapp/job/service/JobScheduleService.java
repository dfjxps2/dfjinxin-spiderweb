package com.crawler.webapp.job.service;

import com.crawler.webapp.job.bean.JobSchedule;
import com.crawler.webapp.job.bean.JobScheduleParam;
import com.github.pagehelper.Page;

/**
 * Created by SongCQ on 2017/10/10.
 */
public interface JobScheduleService {

    Page<JobScheduleParam> pagingJobScheduleList(int currPage, int pageSize);

    void saveJobSchedule(int job_schedule_id,int job_schedule_type);

    void saveNewScheduleParam(String param_name,String param_value,int job_schedule_id);

    JobScheduleParam getScheduleParamInfo(int job_schedule_id,String param_name);

    void updateScheduleAndSceduleParam(int job_schedule_id,int job_schedule_type,String param_name,String param_value,int schedule_id,String pName);

    void delSchedule(int job_schedule_id,String param_name);


}
