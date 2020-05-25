package com.crawler.webapp.crawlerpage.service.imp;

import com.crawler.webapp.crawlerpage.bean.*;
import com.crawler.webapp.crawlerpage.dao.CrawlerPageMgDao;
import com.crawler.webapp.crawlerpage.service.CrawlerPageMgService;
import com.crawler.webapp.util.sql.DataTableMeta;
import com.crawler.webapp.util.sql.ISqlUitl;
import com.github.pagehelper.Page;
import com.crawler.webapp.util.tree.EntityTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/7/25.
 */
@Service("crawlerPageMgService")
public class CrawlerPageMgServiceImp implements CrawlerPageMgService{

    private Logger logger = LoggerFactory.getLogger(CrawlerPageMgServiceImp.class);

    @Autowired
    private CrawlerPageMgDao crawlerPageMgDao;
    @Autowired
    private ISqlUitl sqlUitl;

    @Override
    public Page<CrawlerPage> listCrawlerPageByPaging(int currPage, int pageSize, CrawlerPage bean) {
        Page<CrawlerPage> resultList = crawlerPageMgDao.listCrawlerPageByPaging(currPage, pageSize, bean);
        return resultList;
    }

    @Override
    public CrawlerPage craPageData(int page_id, int job_id, int user_id) {
        CrawlerPage crawlerPageData = crawlerPageMgDao.craPageData(page_id, job_id, user_id);
        return crawlerPageData;
    }

    @Override
    public List<PageLink> listPageLink(int page_id, int job_id, int user_id) {
        List<PageLink> result = crawlerPageMgDao.listPageLink(page_id, job_id, user_id);
        return result;
    }

    @Override
    public List<PageField> listPageField(int page_id, int job_id, int user_id) {
        List<PageField> resultList = crawlerPageMgDao.listPageField(page_id, job_id, user_id);
        return resultList;
    }

    @Override
    public void newSaveCrawlerPage(CrawlerPage crawlerPage) {
        Integer maxId = crawlerPageMgDao.getMaxPageId(
                crawlerPage.getJob_id(),
                crawlerPage.getUser_id());
        if(maxId==null)
            maxId = 1;
        else
            maxId++;
        crawlerPage.setPage_id(maxId);

        crawlerPageMgDao.newSaveCrawlerPage(crawlerPage);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updateCrawlerPage(CrawlerPage crawlerPage){
        crawlerPageMgDao.updateCrawlerPage(crawlerPage);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void deleteCrawlerPage(int page_id, int job_id, int user_id){
        removeAllPageLinks(page_id,job_id,user_id);
        removeAllPageFields(page_id, job_id, user_id);
        crawlerPageMgDao.deleteCrawlerPage(page_id,job_id,user_id);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void newSavePageFields(List<PageField> pageFields) {

        logger.debug("pageFields list value is {}",pageFields);
        PageField newField = pageFields.get(0);
        Integer maxId = crawlerPageMgDao.getMaxFieldId(newField.getPage_id(), newField.getJob_id(), newField.getUser_id());
        if(maxId==null)
            maxId = 1;
        else
            maxId++;

        for(PageField pageField : pageFields){
            pageField.setField_id(maxId++);
            crawlerPageMgDao.savePageField(pageField);
            int fieldId = pageField.getField_id();

            List<PageFieldLocate> pageFieldLocates = pageField.getPageFieldLocate();
            if(pageFieldLocates!=null && pageFieldLocates.size()>0){
                Integer x = 1;
                for(PageFieldLocate pageFieldLocate : pageFieldLocates) {
                    String pid = String.valueOf(pageField.getPage_id());
                    String jid = String.valueOf(pageField.getJob_id());
                    String uid = String.valueOf(pageField.getUser_id());
                    if(pid.length()<10) pid = "0" + pid;
                    if(jid.length()<10) jid = "0" + jid;
                    if(uid.length()<10) uid = "0" + uid;
                    int relationId = new Integer(new StringBuilder().append(pid).
                            append(jid).append(uid).append(fieldId).
                            toString() + x.toString());
                    crawlerPageMgDao.savePageFiledLocateRelation(fieldId, pageField.getPage_id(), pageField.getJob_id(), pageField.getUser_id(), relationId);
                    pageFieldLocate.setField_locate_id(relationId);
                    crawlerPageMgDao.savePageFieldLocate(pageFieldLocate);
                    x++;
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void newSavePageLinks(List<PageLink> pageLinks) {
        PageLink newLink = pageLinks.get(0);
        Integer maxId = crawlerPageMgDao.getMaxLinkId(newLink.getPage_id(), newLink.getJob_id(), newLink.getUser_id());
        if(maxId==null)
            maxId = 1;
        else
            maxId++;
        for (PageLink pageLink:pageLinks){
            pageLink.setLink_id(maxId++);
            crawlerPageMgDao.savePageLink(pageLink);
        }
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updatePageLinks(List<PageLink> pageLinks){
        pageLinks.get(0).getPage_id();
        pageLinks.get(0).getJob_id();
        pageLinks.get(0).getUser_id();
        this.removeAllPageLinks(pageLinks.get(0).getPage_id(),pageLinks.get(0).getJob_id(),pageLinks.get(0).getUser_id());
        this.newSavePageLinks(pageLinks);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updatePageFields(List<PageField> pageFields){
        if(pageFields!=null&&pageFields.size()>0){
            int pageId = pageFields.get(0).getPage_id();
            int jobId = pageFields.get(0).getJob_id();
            int userId = pageFields.get(0).getUser_id();
            removeAllPageFields(pageId,jobId,userId);
            newSavePageFields(pageFields);
        }
    }

    @Override
    public void removeAllPageLinks(int page_id, int job_id, int user_id) {
        crawlerPageMgDao.removePageLinks(0,page_id,job_id,user_id);
    }

    @Override
    public void removePageLink(int link_id,int page_id, int job_id, int user_id){
        crawlerPageMgDao.removePageLinks(link_id,page_id,job_id,user_id);

    }

    @Override
    public void removeAllPageFields(int page_id, int job_id, int user_id){
        crawlerPageMgDao.removeLocates(page_id, job_id, user_id);
        crawlerPageMgDao.removeLocateRelation(page_id, job_id, user_id);
        crawlerPageMgDao.removePageFields(0,page_id,job_id,user_id);
    }

    @Override
    public void removePageFields(int field_id, int page_id, int job_id, int user_id){
        crawlerPageMgDao.removePageFields(field_id,page_id,job_id,user_id);
    }

    @Override
    public void removePageField(int field_id, int page_id, int job_id, int user_id){
        crawlerPageMgDao.removeLocate(field_id,page_id, job_id, user_id);
        crawlerPageMgDao.removeLocateRelations(field_id,page_id, job_id, user_id);
        crawlerPageMgDao.removePageFields(field_id,page_id,job_id,user_id);
    }

    @Override
    public List<CrawlerPage> listCrawlerPage() {
        List<CrawlerPage> list = crawlerPageMgDao.listCrawlerPage();
        return list;
    }

    @Override
    public PageField craFieldData(int field_id, int page_id, int job_id, int user_id) {
        PageField pageField = crawlerPageMgDao.craFieldData(field_id, page_id, job_id, user_id);
        return pageField;
    }

    @Override
    public PageLink craLinkData(int link_id, int page_id, int job_id, int user_id) {
        PageLink pageLink = crawlerPageMgDao.craLinkData(link_id, page_id, job_id, user_id);
        return pageLink;
    }

    @Override
    public List<EntityTree> treePageField(int page_id, int job_id, int user_id) {
        List<EntityTree> resultList = crawlerPageMgDao.treePageField(page_id, job_id, user_id);
        return resultList;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveAllPageFields(List<PageField> list) {
        if(list == null || list.size() == 0)
            return;
        PageField field = list.get(0);
        if(field.getPage_id() == 0 || field.getJob_id() == 0 || field.getUser_id() == 0 )
            return;
        this.removeAllPageFields(field.getPage_id(), field.getJob_id(), field.getUser_id());
        this.newSavePageFields(list);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveAllPageLinks(Map<String, List<PageLink>> maps) {
        if(maps.containsKey("edit")){
            List<PageLink> list = maps.get("edit");
            if(list.size()>0)
                this.updatePageLinks(list);
        }
        if(maps.containsKey("add")){
            List<PageLink> list = maps.get("add");
            if(list.size()>0)
                this.newSavePageLinks(list);
        }
    }

    @Override
    public Page<DataField> listDataFieldByPaging(int currPage, int pageSize, DataField bean) {
        Page<DataField> resultList = crawlerPageMgDao.listDataFieldByPaging(currPage, pageSize, bean);
        return resultList;
    }

    @Override
    public DataField craDataField(String table_name, String field_name) {
        DataField dataField = crawlerPageMgDao.craDataField(table_name, field_name);
        return dataField;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void newSaveAllDataField(List<DataField> list) {
        for(DataField dataField : list){
            this.newSaveDataField(dataField);
        }
    }

    @Override
    public void newSaveDataField(DataField dataField) {
        crawlerPageMgDao.newSaveDataField(dataField);
    }

    @Override
    public void updateDataField(DataField dataField) {
        crawlerPageMgDao.updateDataField(dataField);
    }

    @Override
    public void deleteDataField(String table_name, String field_name) {
        crawlerPageMgDao.deleteDataField(table_name, field_name);
    }

    @Override
    public List<DataField> listAllTableName() {
        return crawlerPageMgDao.listAllTableName();
    }

    @Override
    public List<PageField> listAllTaleField(int page_id, int job_id, int user_id, int noadd) {
        return crawlerPageMgDao.listAllTaleField(page_id, job_id, user_id, noadd);
    }

    @Override
    public String createTable(DataTableMeta dataTable, boolean showSql) {
        String sql = sqlUitl.createSql(dataTable);
        if(showSql)
            return sql;
        else{
            try{
                crawlerPageMgDao.execute(sql);
                return "";
            }catch (Exception ex){
                ex.printStackTrace();
                return "执行失败，" + ex.getMessage();
            }
        }
    }

}
