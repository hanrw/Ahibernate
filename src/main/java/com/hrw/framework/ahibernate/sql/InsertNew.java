
package com.hrw.framework.ahibernate.sql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class InsertNew {
    private String tableName;

    private Map columns = new LinkedHashMap();

    public InsertNew setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getNoColumnsInsertString() {
        return "values ( )";
    }

    public InsertNew addColumn(String columnName, String valueExpression) {
        columns.put(columnName, valueExpression);
        return this;
    }

    public String toStatementString() {
        StringBuilder buf = new StringBuilder(columns.size() * 15 + tableName.length() + 10);
        buf.append("insert into ").append(tableName);
        if (columns.size() == 0) {
            buf.append(' ').append(getNoColumnsInsertString());
        } else {
            buf.append(" (");
            Iterator iter = columns.keySet().iterator();
            while (iter.hasNext()) {
                buf.append(iter.next());
                if (iter.hasNext()) {
                    buf.append(", ");
                }
            }
            buf.append(") values (");
            iter = columns.values().iterator();
            while (iter.hasNext()) {
                buf.append(iter.next());
                if (iter.hasNext()) {
                    buf.append(", ");
                }
            }
            buf.append(')');
        }
        return buf.toString();
    }
}
