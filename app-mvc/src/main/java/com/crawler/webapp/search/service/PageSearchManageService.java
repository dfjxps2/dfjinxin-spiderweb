package com.crawler.webapp.search.service;

import com.crawler.webapp.job.bean.JobCategory;
import com.crawler.webapp.search.bean.SearchBean;

import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/9/27.
 */
public interface PageSearchManageService {

    List<JobCategory> listJobCategory();

    List<Map<String,Object>> listJobAndTypes();

    Map<String, Object> doSearch(SearchBean searchBean);

}
