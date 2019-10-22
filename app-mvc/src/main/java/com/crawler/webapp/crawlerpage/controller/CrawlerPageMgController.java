package com.crawler.webapp.crawlerpage.controller;

import com.crawler.webapp.crawlerpage.bean.*;
import com.crawler.webapp.crawlerpage.service.CrawlerPageMgService;
import com.crawler.webapp.util.URIEncoder;
import com.crawler.webapp.util.sql.DataTableMeta;
import com.crawler.webapp.util.tree.TreeUtil;
import com.github.pagehelper.Page;
import com.crawler.webapp.util.tree.EntityTree;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import com.webapp.support.session.SessionSupport;
import com.workbench.auth.user.entity.User;
import com.workbench.spring.aop.annotation.JsonMsgParam;
import com.workbench.spring.aop.annotation.JsonpCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/7/25.
 */

@Controller
@RequestMapping("crawler/pageMg")
public class CrawlerPageMgController {

    @Autowired
    private CrawlerPageMgService crawlerPageMgService;

    @RequestMapping("pagingCrawlerPage")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String listCrawlerPageByPaging(int currPage, int pageSize, Integer page_id,String job_name){
        CrawlerPage bean = new CrawlerPage();
        if(page_id != null)
            bean.setPage_id(page_id);
        bean.setJob_name(job_name);
        Page<CrawlerPage> list = crawlerPageMgService.listCrawlerPageByPaging(currPage, pageSize, bean);
        //转义表达式
        for(CrawlerPage page : list){
            encoderPage(page);
        }
        PageResult pageResult = PageResult.pageHelperList2PageResult(list);

        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null,pageResult);
        return result;
    }

    @RequestMapping("listCrawlerPage")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String listCrawlerPage(){
        List<CrawlerPage> dataResult = crawlerPageMgService.listCrawlerPage();
        //转义表达式
        for(CrawlerPage page : dataResult){
            encoderPage(page);
        }
        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null,dataResult);
        return result;
    }

    @RequestMapping("craPageData")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String craPageData(int page_id, int job_id, int user_id){
        CrawlerPage page = crawlerPageMgService.craPageData(page_id, job_id, user_id);
        encoderPage(page);
        return  JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null, page);
    }

    /**
     * 转义表达式
     */
    private void encoderPage(CrawlerPage page){
        String paginate_element = page.getPaginate_element();
        String load_indicator = page.getLoad_indicator();
        if(paginate_element != null && !"".equals(paginate_element)){
            page.setPaginate_element(URIEncoder.encodeURIComponent(paginate_element));
        }
        if(load_indicator != null && !"".equals(load_indicator)){
            page.setLoad_indicator(URIEncoder.encodeURIComponent(load_indicator));
        }
    }
    private void encoderPageField(PageField field){
        List<PageFieldLocate> locates = field.getPageFieldLocate();
        if(locates == null || locates.size() == 0) return;
        for(PageFieldLocate locate : locates) {
            String field_locate_pattern = locate.getField_locate_pattern();
            if (field_locate_pattern != null && !"".equals(field_locate_pattern)) {
                locate.setField_locate_pattern(URIEncoder.encodeURIComponent(field_locate_pattern));
            }
            String field_ext_pattern = locate.getField_ext_pattern();
            if (field_ext_pattern != null && !"".equals(field_ext_pattern)) {
                locate.setField_ext_pattern(URIEncoder.encodeURIComponent(field_ext_pattern));
            }
        }
    }
    private void encoderPageLink(PageLink link){
        String link_locate_pattern = link.getLink_locate_pattern();
        if(link_locate_pattern != null && !"".equals(link_locate_pattern)){
            link.setLink_locate_pattern(URIEncoder.encodeURIComponent(link_locate_pattern));
        }
        String link_ext_pattern = link.getLink_ext_pattern();
        if(link_ext_pattern != null && !"".equals(link_ext_pattern)){
            link.setLink_ext_pattern(URIEncoder.encodeURIComponent(link_ext_pattern));
        }
    }
    @RequestMapping("listPageLink")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String listPageLink(int page_id, int job_id, int user_id) {
        List<PageLink> list = crawlerPageMgService.listPageLink(page_id, job_id, user_id);
        for(PageLink link : list){
            encoderPageLink(link);
        }
        return  JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null, list);
    }

    @RequestMapping("listPageField")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String listPageField(int page_id, int job_id, int user_id) {
        List<PageField> list = crawlerPageMgService.listPageField(page_id, job_id, user_id);
        for(PageField field : list){
            encoderPageField(field);
        }
        return  JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null, list);
    }

    /*
        新增
     */
    @RequestMapping("newSaveCrawlerPage")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String newSaveCrawlerPage(@RequestBody CrawlerPage crawlerPage){
        User user = SessionSupport.checkoutUserFromSession();
        crawlerPage.setUser_id(user.getUser_id());

        CrawlerPage checkResult = crawlerPageMgService.craPageData(crawlerPage.getPage_id(), crawlerPage.getJob_id(), crawlerPage.getUser_id());
        if(checkResult!=null)
            return JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD,"保存失败",
                    "当前用户下已新增过PAGE_ID:"+crawlerPage.getPage_id()+" JOB_ID:"+crawlerPage.getJob_id()+"的组合",null);
        crawlerPageMgService.newSaveCrawlerPage(crawlerPage);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
    }

    @RequestMapping("newSaveFields")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String newSaveFields(@JsonMsgParam(jsonObjTypes = PageField.class,jsonName = "pageFields")
                                                ArrayList<PageField> pageFields){
        crawlerPageMgService.newSavePageFields(pageFields);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
    }

    @RequestMapping("newSaveLinks")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String newSaveLinks(@JsonMsgParam(jsonObjTypes = PageLink.class,jsonName = "pageLinks")
                                           ArrayList<PageLink> pageLinks){
        crawlerPageMgService.newSavePageLinks(pageLinks);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
    }

    /*
        更新
     */
    @RequestMapping("updateCrawlerPage")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String updateCrawlerPage(@RequestBody CrawlerPage crawlerPage){
        crawlerPageMgService.updateCrawlerPage(crawlerPage);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
    }

    @RequestMapping("updatePageLinks")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String updatePageLinks(@JsonMsgParam(jsonObjTypes = PageLink.class)
                                                  ArrayList<PageLink> pageLinks){
        crawlerPageMgService.updatePageLinks(pageLinks);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
    }

    @RequestMapping("updatePageFields")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String updatePageFields(@JsonMsgParam(jsonObjTypes = PageField.class)
                                                   ArrayList<PageField> pageFields){
        crawlerPageMgService.updatePageFields(pageFields);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
    }

    /*
        删除
     */
    @RequestMapping("deleteCrawlerPage")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String deleteCrawlerPage(int page_id, int job_id, int user_id){
        crawlerPageMgService.deleteCrawlerPage(page_id, job_id, user_id);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"删除成功",null,null);
    }

    @RequestMapping("deletePageField")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String deletePageField(int field_id,int page_id, int job_id, int user_id){
        crawlerPageMgService.removePageField(field_id, page_id, job_id, user_id);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"删除成功",null,null);
    }

    @RequestMapping("deletePageLink")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String deletePageLink(int link_id,int page_id, int job_id, int user_id){
        crawlerPageMgService.removePageLink(link_id, page_id, job_id, user_id);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"删除成功",null,null);
    }

    @RequestMapping("craFieldData")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String craFieldData(int field_id, int page_id, int job_id, int user_id){
        PageField page = crawlerPageMgService.craFieldData(field_id, page_id, job_id, user_id);
        encoderPageField(page);
        return  JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null, page);
    }

    @RequestMapping("craLinkData")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String craLinkData(int link_id, int page_id, int job_id, int user_id){
        PageLink page = crawlerPageMgService.craLinkData(link_id, page_id, job_id, user_id);
        encoderPageLink(page);
        return  JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null, page);
    }

    /**
     * 机构的树结构
     * @return
     */
    @RequestMapping("treePageField")
    @ResponseBody
    @JsonpCallback
    @CrossOrigin(allowCredentials="true")
    public String treePageField(int page_id, int job_id, int user_id){
        List<EntityTree> list = crawlerPageMgService.treePageField(page_id, job_id, user_id);
        List<EntityTree> tree = TreeUtil.getTreeList("0", list);//调用工具类，第一个参数是默认传入的顶级id，和查询出来的数据

        String jsonpResponse = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "获取成功", null, tree);
        return jsonpResponse;
    }

    @RequestMapping("saveAllFields")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String saveAllFields(@RequestBody List<PageField> list){
        crawlerPageMgService.saveAllPageFields(list);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
    }
    @RequestMapping("saveAllLinks")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String saveAllLinks(@RequestBody Map<String,List<PageLink>> maps){
        crawlerPageMgService.saveAllPageLinks(maps);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
    }

    @RequestMapping("pagingDataField")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String listDataFieldByPaging(int currPage, int pageSize, String table_name){
        DataField bean = new DataField();
        if(table_name != null && !"".equals(table_name))
            bean.setTable_name(table_name);
        Page<DataField> list = crawlerPageMgService.listDataFieldByPaging(currPage, pageSize, bean);
        PageResult pageResult = PageResult.pageHelperList2PageResult(list);

        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null,pageResult);
        return result;
    }
    @RequestMapping("craDataField")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String craDataField(String table_name, String field_name){
        DataField page = crawlerPageMgService.craDataField(table_name, field_name);
        return  JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null, page);
    }
    @RequestMapping("deleteDataField")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String deleteDataField(String table_name, String field_name){
        crawlerPageMgService.deleteDataField(table_name, field_name);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"删除成功",null,null);
    }
    @RequestMapping("newSaveDataField")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String newSaveDataField(@RequestBody List<DataField> list){
        crawlerPageMgService.newSaveAllDataField(list);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
    }
    @RequestMapping("updateDataField")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String updateDataField(@RequestBody DataField dataField){
        crawlerPageMgService.updateDataField(dataField);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"保存成功",null,null);
    }

    @RequestMapping("listAllTableName")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String listAllTableName(){
        List<DataField> dataResult = crawlerPageMgService.listAllTableName();
        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null,dataResult);
        return result;
    }

    @RequestMapping("listAllTableField")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String listAllTableField(int page_id, int job_id, int user_id, int noadd){
        List<PageField> dataResult = crawlerPageMgService.listAllTaleField(page_id, job_id, user_id, noadd);
        String result = JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"获取成功",null,dataResult);
        return result;
    }

    @RequestMapping("createTable")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String createTable(@RequestBody DataTableMeta dataTable){
        String msg = crawlerPageMgService.createTable(dataTable, false);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"执行成功",null,msg);
    }
    @RequestMapping("createTableSql")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String createTableSql(@RequestBody DataTableMeta dataTable){
        String sql = crawlerPageMgService.createTable(dataTable, true);
        sql = URIEncoder.encodeURIComponent(sql);
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS,"执行成功",null,sql);
    }
}
