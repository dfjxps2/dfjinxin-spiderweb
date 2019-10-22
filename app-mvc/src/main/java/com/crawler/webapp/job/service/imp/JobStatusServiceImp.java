package com.crawler.webapp.job.service.imp;

import com.crawler.webapp.job.bean.JobStatus;
import com.crawler.webapp.job.dao.IJobStatusDao;
import com.crawler.webapp.job.service.JobStatusService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SongCQ on 2017/8/3.
 */
@Service("jobStatusService")
public class JobStatusServiceImp implements JobStatusService {

    @Autowired
    private IJobStatusDao jobStatusDao;

    @Override
    public Page<JobStatus> pagingCrawlStatusList(int currPage, int pageSize, String job_name) {
        Page<JobStatus>  jobStatusPage = jobStatusDao.pagingCrawlStatusList(currPage,pageSize,job_name);
        return jobStatusPage;
    }
}
