
package com.hrw.framework.ahibernate.sql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Insert {
    private String tableName;

    private Map columns = new LinkedHashMap();

    public Insert setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getNoColumnsInsertString() {
        return "values ( )";
    }

    public Insert addColumn(String columnName, Object valueExpression) {
        columns.put(columnName, valueExpression);
        return this;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
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
                Object value = iter.next();
                buf.append(isNumeric(value != null ? value.toString() : "") ? value : "'" + value
                        + "'");
                if (iter.hasNext()) {
                    buf.append(", ");
                }
            }
            buf.append(')');
        }
        return buf.toString();
    }
}
