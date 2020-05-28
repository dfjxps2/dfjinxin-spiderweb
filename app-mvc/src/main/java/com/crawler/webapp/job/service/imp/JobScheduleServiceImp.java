package com.crawler.webapp.job.service.imp;

import com.crawler.webapp.job.bean.CrawlerConfig;
import com.crawler.webapp.job.bean.JobSchedule;
import com.crawler.webapp.job.bean.JobScheduleParam;
import com.crawler.webapp.job.dao.IJobScheduleDao;
import com.crawler.webapp.job.service.JobScheduleService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by SongCQ on 2017/10/10.
 */
@Service("jobScheduleService")
public class JobScheduleServiceImp implements JobScheduleService {

    @Autowired(required = false)
    private IJobScheduleDao iJobScheduleDao;

    @Override
    public Page<JobScheduleParam> pagingJobScheduleList(int currPage, int pageSize) {
        Page<JobScheduleParam> result = iJobScheduleDao.pagingJobScheduleList(currPage, pageSize);
        return result;
    }

    @Override
    public void saveJobSchedule(int job_schedule_id,int job_schedule_type) {
        iJobScheduleDao.saveJobSchedule(job_schedule_id,job_schedule_type);
    }

    @Override
    public void saveNewScheduleParam(String param_name,String param_value,int job_schedule_id) {
        iJobScheduleDao.saveNewScheduleParam(param_name,param_value,job_schedule_id);
    }

    @Override
    public JobScheduleParam getScheduleParamInfo(int job_schedule_id,String param_name) {
        return iJobScheduleDao.getScheduleParamInfo(job_schedule_id,param_name);
    }

    @Override
    public void updateScheduleAndSceduleParam(int job_schedule_id,int job_schedule_type,String param_name,String param_value,int schedule_id,String pName) {
           iJobScheduleDao.updateScheduleAndSceduleParam(job_schedule_id,job_schedule_type,param_name,param_value,schedule_id,pName);
    }


    @Override
    public void delSchedule(int job_schedule_id,String param_name) {
        iJobScheduleDao.delSchedule(job_schedule_id,param_name);
    }

    @Override
    public List<Map<String,Object>> jobScheduleList() {
        return iJobScheduleDao.jobScheduleList();
    }
}
