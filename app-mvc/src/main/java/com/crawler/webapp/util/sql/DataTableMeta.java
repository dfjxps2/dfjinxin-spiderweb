package com.crawler.webapp.util.sql;

import java.util.List;

/**
 * Created by SongCQ on 2017/7/26.
 */
public class DataTableMeta {
    private String table_name;
    private boolean is_drop;
    private boolean is_dynamic;
    private boolean is_original;
    private List<DataFieldMeta> dataField;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public boolean isIs_drop() {
        return is_drop;
    }

    public void setIs_drop(boolean is_drop) {
        this.is_drop = is_drop;
    }

    public boolean isIs_dynamic() {
        return is_dynamic;
    }

    public void setIs_dynamic(boolean is_dynamic) {
        this.is_dynamic = is_dynamic;
    }

    public boolean isIs_original() {
        return is_original;
    }

    public void setIs_original(boolean is_original) {
        this.is_original = is_original;
    }

    public List<DataFieldMeta> getDataField() {
        return dataField;
    }

    public void setDataField(List<DataFieldMeta> dataField) {
        this.dataField = dataField;
    }
}
