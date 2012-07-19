
package com.hrw.framework.ahibernate.table;

import java.util.List;
import java.util.Map;

public class TableInfo {

    private Map<String, String> columns;

    private Map<String, String> columnsType;

    private String tableName;

    private String primaryKey;

    public TableInfo(Class clazz) {
        List<Object> tableInfo = TableUtils.extratToTableInfo(clazz);
        this.tableName = (String) tableInfo.get(0);
        this.primaryKey = (String) tableInfo.get(1);
        this.columns = (Map<String, String>) tableInfo.get(2);
        this.columnsType = (Map<String, String>) tableInfo.get(3);

    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getColumns() {
        return this.columns;
    }

    public Map<String, String> getColumnsType() {
        return this.columnsType;
    }

    public String getPrimaryColoum() {
        return primaryKey;
    }

}
