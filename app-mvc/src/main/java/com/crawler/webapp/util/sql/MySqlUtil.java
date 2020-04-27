package com.crawler.webapp.util.sql;

import org.springframework.stereotype.Component;

@Component("mysqlUtil")
public class MySqlUtil implements ISqlUitl {
    @Override
    public String createSql(DataTableMeta tableMeta) {
        StringBuilder sb = new StringBuilder(100);
        String full_table = tableMeta.getTable_name();
        String table_name = full_table;
        String schema_name = "";
        if(full_table.indexOf('.')>-1){
            String[] arr = full_table.split("\\.");
            if(arr.length > 1){
                schema_name = arr[0];
                table_name = arr[1];
                full_table = "`" + schema_name + "`.`" + table_name + "`";
            }

        }

        if(tableMeta.isIs_drop()){
            sb.append("DROP TABLE IF EXISTS ").append(full_table).append(";\n");
        }
        if (tableMeta.isIs_original() == true){
            sb.append("ALTER TABLE ").append(full_table).append(" \n");
        }else{
            sb.append("CREATE TABLE ").append(full_table).append(" (\n");
        }
        int i = 0;
        int ln = tableMeta.getDataField().size() - 1;
        String primary_keys = "";
        for(DataFieldMeta fieldMeta : tableMeta.getDataField()){
            if (tableMeta.isIs_original() == true){
                sb.append("  ADD COLUMN `").append(fieldMeta.getField_name()).append("`");
            }else{
                sb.append("  `").append(fieldMeta.getField_name()).append("`");
            }
            sb.append(" ").append(fieldMeta.getData_type());
            String size = fieldMeta.getData_size();
            String scale = fieldMeta.getData_scale();
            String ddefault = fieldMeta.getData_default();
            String comment = fieldMeta.getField_comment();

            if(size!=null&&!"".equals(size)){
                sb.append("(").append(size);
                if(scale!=null&&!"".equals(scale)){
                    sb.append(",").append(scale);
                }
                sb.append(")");
            }
            if(fieldMeta.isNullable()){
                if(ddefault!=null&&!"".equals(ddefault)){
                    sb.append(" DEFAULT NULL");
                }else{
                    sb.append(" DEFAULT ").append(ddefault);
                }
            }else{
                sb.append(" NOT NULL");
            }
            if(comment!=null&&comment.length()>0){
                sb.append(" COMMENT '" + comment + "'");
            }
            if (tableMeta.isIs_original() == false){
                if(fieldMeta.isPrimary_key()){
                    primary_keys += "`"+fieldMeta.getField_name()+"`,";
                }
            }

            if(i < ln){
                sb.append(",\n");
            }
            i++;
        }
        if (tableMeta.isIs_original() == false){
            if(primary_keys.length()>0){
                sb.append(",\n").append("  PRIMARY KEY ("+primary_keys.substring(0, primary_keys.length()-1)+")");
            }else{
                sb.append("\n");
            }
            sb.append("\n) ENGINE=InnoDB DEFAULT CHARSET=utf8");
            if(tableMeta.isIs_dynamic()){
                sb.append(" ROW_FORMAT=DYNAMIC");
            }
        }
        sb.append(";");
        return sb.toString();
    }
}
