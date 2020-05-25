package com.crawler.webapp.crawlerpage.service;

import com.crawler.webapp.crawlerpage.bean.*;
import com.crawler.webapp.util.sql.DataTableMeta;
import com.github.pagehelper.Page;
import com.crawler.webapp.util.tree.EntityTree;

import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/7/25.
 */
public interface CrawlerPageMgService {
    Page<CrawlerPage> listCrawlerPageByPaging(int currPage, int pageSize, CrawlerPage bean);

    CrawlerPage craPageData(int page_id,int job_id,int user_id);

    List<PageLink> listPageLink(int page_id, int job_id, int user_id);

    List<PageField> listPageField(int page_id, int job_id, int user_id,String keyWords);

    void newSaveCrawlerPage(CrawlerPage crawlerPage);

    void updateCrawlerPage(CrawlerPage crawlerPage);

    void deleteCrawlerPage(int page_id, int job_id, int user_id);

    void newSavePageFields(List<PageField> pageFields);

    void newSavePageLinks(List<PageLink> pageLinks);

    void updatePageFields(List<PageField> pageFields);

    void removeAllPageLinks(int page_id, int job_id, int user_id);

    void updatePageLinks(List<PageLink> pageLinks);

    void removePageLink(int link_id,int page_id, int job_id, int user_id);

    void removeAllPageFields(int page_id, int job_id, int user_id);

    void removePageFields(int field_id, int page_id, int job_id, int user_id);

    void removePageField(int field_id, int page_id, int job_id, int user_id);

    List<CrawlerPage> listCrawlerPage();

    PageField craFieldData(int field_id, int page_id, int job_id, int user_id);

    PageLink craLinkData(int link_id,int page_id, int job_id, int user_id);

    List<EntityTree> treePageField(int page_id, int job_id, int user_id);

    void saveAllPageFields(List<PageField> list);

    void saveAllPageLinks(Map<String, List<PageLink>> maps);

    Page<DataField> listDataFieldByPaging(int currPage, int pageSize, DataField bean);

    DataField craDataField(String table_name, String field_name);

    void newSaveAllDataField(List<DataField> list);

    void newSaveDataField(DataField dataField);

    void updateDataField(DataField dataField);

    void deleteDataField(String table_name, String field_name);

    List<DataField> listAllTableName();

    List<PageField> listAllTaleField(int page_id, int job_id, int user_id, int noadd);

    String createTable(DataTableMeta dataTable, boolean showSql);
}
