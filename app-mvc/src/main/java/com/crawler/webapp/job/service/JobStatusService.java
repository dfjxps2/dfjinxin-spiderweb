package com.crawler.webapp.job.service;

import com.crawler.webapp.job.bean.JobStatus;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Select;

/**
 * Created by SongCQ on 2017/8/3.
 */
public interface JobStatusService {
    Page<JobStatus> pagingCrawlStatusList(int currPage, int pageSize, String job_name);
}
