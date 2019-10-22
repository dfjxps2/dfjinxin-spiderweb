package com.crawler.webapp.util.sql;

public class DataFieldMeta {
    private String field_name;
    private String data_type;
    private String data_size;
    private String data_scale;
    private String data_default;
    private boolean nullable;
    private boolean primary_key;
    private boolean primary_index;

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getData_size() {
        return data_size;
    }

    public void setData_size(String data_size) {
        this.data_size = data_size;
    }

    public String getData_scale() {
        return data_scale;
    }

    public void setData_scale(String data_scale) {
        this.data_scale = data_scale;
    }

    public String getData_default() {
        return data_default;
    }

    public void setData_default(String data_default) {
        this.data_default = data_default;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(boolean primary_key) {
        this.primary_key = primary_key;
    }

    public boolean isPrimary_index() {
        return primary_index;
    }

    public void setPrimary_index(boolean primary_index) {
        this.primary_index = primary_index;
    }
}
