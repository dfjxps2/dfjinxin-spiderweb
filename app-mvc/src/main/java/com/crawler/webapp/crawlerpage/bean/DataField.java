package com.crawler.webapp.crawlerpage.bean;

/**
 * Created by SongCQ on 2017/7/26.
 */
public class DataField {
    private int field_id;

    private String field_name;

    private String field_name_cn;

    private String table_name;

    private String table_name_cn;

    public int getField_id() {
        return field_id;
    }

    public void setField_id(int field_id) {
        this.field_id = field_id;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_name_cn() {
        return field_name_cn;
    }

    public void setField_name_cn(String field_name_cn) {
        this.field_name_cn = field_name_cn;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getTable_name_cn() {
        return table_name_cn;
    }

    public void setTable_name_cn(String table_name_cn) {
        this.table_name_cn = table_name_cn;
    }
}
