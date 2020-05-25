package com.crawler.webapp.crawlerpage.dao;

import com.crawler.webapp.crawlerpage.bean.*;
import com.crawler.webapp.util.tree.EntityTree;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by SongCQ on 2017/7/25.
 */
public interface CrawlerPageMgDao {

    @Select("<script>" +
            "select c.*,u.user_name_cn,j.job_name from crawl_page_config c left join user u on c.user_id=u.user_id left join crawl_job j on c.job_id=j.job_id where 1=1"+
            "<if test='bean.page_id>0'> and c.page_id = #{bean.page_id} </if>" +
            "<if test='bean.job_name!=null'>  and j.job_name like concat('%',#{bean.job_name},'%') </if>" +
            "</script>")
    @Options(useCache = false)
    Page<CrawlerPage> listCrawlerPageByPaging(@Param("currPage") int currPage, @Param("pageSize") int pageSize, @Param("bean") CrawlerPage bean);

    @Select("select * from crawl_page_config where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id}")
    @Options(useCache = false)
    CrawlerPage craPageData(@Param("page_id") int page_id,@Param("job_id") int job_id,@Param("user_id") int user_id);

    @Select("select * from page_link where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id}")
    @Options(useCache = false)
    List<PageLink> listPageLink(@Param("page_id") int page_id,@Param("job_id") int job_id,@Param("user_id") int user_id);

//    @Select("select pf.*,pfl.* from page_field pf inner join page_field_locate_relation pflr on" +

    @Select("<script>" +
            "select pf.* from page_field pf where " +
            " pf.page_id=#{page_id} and pf.job_id=#{job_id} and pf.user_id=#{user_id}"+
            "<if test='keyWords!=null'> and pf.field_name like concat('%',#{keyWords},'%') </if>"+
            "</script>")
    @Results({
            @Result(property = "page_id", column = "page_id"),
            @Result(property = "job_id", column = "job_id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "field_id", column = "field_id"),
            @Result(property = "pageFieldLocate",javaType = List.class,column = "{page_id=page_id,job_id=job_id,user_id=user_id,field_id=field_id}",
            many = @Many(select="com.crawler.webapp.crawlerpage.dao.CrawlerPageMgDao.listPageFieldLocate"))})
    @Options(useCache = false)
    List<PageField> listPageField(@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id,@Param("keyWords") String keyWords);

    @Select("select * from page_field_locate where field_locate_id = #{field_locate_id}")
    @Options(useCache = false)
    PageFieldLocate getPageFieldLocate(int field_locate_id);

    @Select("select * from page_field_locate where field_locate_id in (select field_locate_id from page_field_locate_relation where field_id=#{field_id} and page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id} )")
    @Options(useCache = false)
    List<PageFieldLocate> listPageFieldLocate(Map<String,Object> param);

    @Select("SELECT max(page_id) max_page_id FROM crawl_page_config where job_id=#{job_id} and user_id=#{user_id}")
    @Options(useCache = false)
    Integer getMaxPageId(@Param("job_id") int job_id, @Param("user_id") int user_id);

    @Select("SELECT max(field_id) max_field_id FROM page_field where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id}")
    @Options(useCache = false)
    Integer getMaxFieldId(@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id);

    @Select("SELECT max(link_id) max_link_id FROM page_link where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id}")
    @Options(useCache = false)
    Integer getMaxLinkId(@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id);


    @Insert("insert into crawl_page_config " +
            "(page_id,job_id,user_id,page_name,page_type,data_format,is_multi_page,paginate_element,load_indicator," +
            "page_interval,max_page_num,save_page_source,data_file) " +
            "values " +
            "(#{page_id},#{job_id},#{user_id},#{page_name},#{page_type},#{data_format},#{is_multi_page},#{paginate_element},#{load_indicator}," +
            "#{page_interval},#{max_page_num},#{save_page_source},#{data_file})")
    @Options(useCache = false)
    void newSaveCrawlerPage(CrawlerPage crawlerPage);

    @Insert("insert into page_field_locate_relation (field_id,page_id,job_id,user_id,field_locate_id) " +
            "values (#{field_id},#{page_id},#{job_id},#{user_id},#{field_locate_id})")
    void savePageFiledLocateRelation(@Param("field_id") int field_id,@Param("page_id") int page_id,@Param("job_id") int job_id,
                                     @Param("user_id") int user_id, @Param("field_locate_id") int field_locate_id);

    @Insert("insert into page_field (field_id,page_id,job_id,user_id,field_name,field_datatype,parent_field_id,combine_field_value) " +
            "values (#{field_id}, #{page_id},#{job_id},#{user_id},#{field_name},#{field_datatype},#{parent_field_id},#{combine_field_value})")
    void savePageField(PageField pageField);

    @Insert("insert into page_field_locate (field_locate_id,field_locate_pattern,field_ext_pattern) " +
            "values (#{field_locate_id},#{field_locate_pattern},#{field_ext_pattern}) ")
    void savePageFieldLocate(PageFieldLocate pageFieldLocate);

    @Insert("insert into page_link (link_id,page_id,job_id,user_id,link_locate_pattern,link_ext_pattern,next_page_id) " +
            "values (#{link_id},#{page_id},#{job_id},#{user_id},#{link_locate_pattern},#{link_ext_pattern},#{next_page_id})")
    void savePageLink(PageLink pageLinks);

    @Delete("<script>delete from page_link where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id} " +
            "<if test='link_id!=0'> and link_id = #{link_id}</if></script>")
    void removePageLinks(@Param("link_id") int link_id,@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id);


    @Update("update crawl_page_config set page_name=#{page_name},page_type=#{page_type},data_format=#{data_format},is_multi_page=#{is_multi_page}," +
            "paginate_element=#{paginate_element},load_indicator=#{load_indicator}," +
            "page_interval=#{page_interval},max_page_num=#{max_page_num},save_page_source=#{save_page_source},data_file=#{data_file} where " +
            "page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id}")
    void updateCrawlerPage(CrawlerPage crawlerPage);

    @Delete("delete from crawl_page_config where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id} ")
    void deleteCrawlerPage(@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id);

    @Delete("<script>delete from page_field where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id} " +
            "<if test='field_id!=0'> and field_id = #{field_id}</if></script>")
    void removePageFields(@Param("field_id")  int field_id,@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id);

    @Delete("delete from page_field_locate_relation where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id}")
    void removeLocateRelation(@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id);

    @Delete("delete from page_field_locate where field_locate_id in (select field_locate_id from page_field_locate_relation where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id})")
    void removeLocates(@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id);

    @Select("select * from crawl_page_config")
    @Options(useCache = false)
    List<CrawlerPage> listCrawlerPage();

    @Select("select * from page_field where field_id=#{field_id} and page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id}")
    @Options(useCache = false)
    PageField craFieldData(@Param("field_id") int field_id,@Param("page_id") int page_id,@Param("job_id") int job_id,@Param("user_id") int user_id);

    @Select("select * from page_link where link_id=#{link_id} and page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id}")
    @Options(useCache = false)
    PageLink craLinkData(@Param("link_id") int link_id,@Param("page_id") int page_id,@Param("job_id") int job_id,@Param("user_id") int user_id);

    @Select("SELECT  " +
            " field_id id,  " +
            " field_name label,  " +
            " parent_field_id parentId  " +
            " FROM  " +
            " page_field pf where pf.page_id=#{page_id} and pf.job_id=#{job_id} and pf.user_id=#{user_id} ")
    List<EntityTree> treePageField(@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id);

    @Insert("insert into data_field " +
            "(table_name, table_name_cn, field_id, field_name, field_name_cn) " +
            "values " +
            "(#{table_name},#{table_name_cn},#{field_id},#{field_name},#{field_name_cn})")
    @Options(useCache = false)
    void newSaveDataField(DataField dataField);

    @Update("update data_field set table_name_cn=#{table_name_cn} where " +
            "table_name=#{table_name}")
    void updateTableNameCn(DataField crawlerPage);

    @Update("update data_field set table_name_cn=#{table_name_cn},field_id=#{field_id},field_name_cn=#{field_name_cn} where " +
            "table_name=#{table_name} and field_name=#{field_name}")
    void updateDataField(DataField crawlerPage);

    @Delete("delete from data_field where table_name=#{table_name} and field_name=#{field_name}")
    void deleteDataField(@Param("table_name") String table_name, @Param("field_name") String field_name);


    @Select("select * from data_field where table_name=#{table_name} and field_name=#{field_name}")
    @Options(useCache = false)
    DataField craDataField(@Param("table_name") String table_name,@Param("field_name") String field_name);

    @Select("<script>" +
            "select c.* from data_field c where 1=1 "+
            "<if test='bean.table_name!=null'>  and (c.table_name like concat('%',#{bean.table_name},'%') or c.table_name_cn like concat('%',#{bean.table_name},'%') ) </if>" +
            "</script>")
    @Options(useCache = false)
    Page<DataField> listDataFieldByPaging(@Param("currPage") int currPage, @Param("pageSize") int pageSize, @Param("bean") DataField bean);

    @Select("select distinct data_file table_name,table_name_cn from crawl_page_config t left join data_field d on t.data_file=d.table_name " +
            " where (data_file is not null and data_file != '')")
    @Options(useCache = false)
    List<DataField> listAllTableName();

    @Select("<script>" +
            "select pf.* from page_field pf where " +
            " pf.page_id=#{page_id} and pf.job_id=#{job_id} and pf.user_id=#{user_id}" +
            "<if test='noadd!=0'> and pf.field_id not in (select field_id from data_field where table_name in (select data_file from crawl_page_config where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id}) )</if>" +
            "</script>")
    @Options(useCache = false)
    List<PageField> listAllTaleField(@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id, @Param("noadd") int noadd);

    @Update("${sql}")
    void execute(@Param("sql") String sql);

    @Delete("delete from page_field_locate where field_locate_id in (select field_locate_id from page_field_locate_relation where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id} and field_id=#{field_id})")
    void removeLocate(@Param("field_id") int field_id,@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id);

    @Delete("delete from page_field_locate_relation where page_id=#{page_id} and job_id=#{job_id} and user_id=#{user_id} and field_id=#{field_id}")
    void removeLocateRelations(@Param("field_id") int field_id,@Param("page_id") int page_id, @Param("job_id") int job_id, @Param("user_id") int user_id);

}
