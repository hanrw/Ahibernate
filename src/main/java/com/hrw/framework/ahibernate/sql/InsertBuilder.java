
package com.hrw.framework.ahibernate.sql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class InsertBuilder {

    private String tableName;
    private String comment;
    private Map columns = new LinkedHashMap();

    public InsertBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public InsertBuilder addColumn(String columnName) {
        return addColumn(columnName, "?");
    }

    public InsertBuilder addColumns(String[] columnNames) {
        for (int i = 0; i < columnNames.length; i++) {
            addColumn(columnNames[i]);
        }
        return this;
    }

    public InsertBuilder addColumns(String[] columnNames, boolean[] insertable) {
        for (int i = 0; i < columnNames.length; i++) {
            if (insertable[i]) {
                addColumn(columnNames[i]);
            }
        }
        return this;
    }

    public InsertBuilder addColumns(String[] columnNames, boolean[] insertable,
            String[] valueExpressions) {
        for (int i = 0; i < columnNames.length; i++) {
            if (insertable[i]) {
                addColumn(columnNames[i], valueExpressions[i]);
            }
        }
        return this;
    }

    public InsertBuilder addColumn(String columnName, String valueExpression) {
        columns.put(columnName, valueExpression);
        return this;
    }

    public InsertBuilder addColumn(String columnName, Object value)
            throws Exception {
        return addColumn(columnName, value.toString());
    }

    public InsertBuilder addIdentityColumn(String columnName) {
        String value = getIdentityInsertString();
        if (value != null) {
            addColumn(columnName, value);
        }
        return this;
    }

    public InsertBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String toStatementString() {
        StringBuilder buf = new StringBuilder(columns.size() * 15 + tableName.length() + 10);
        if (comment != null) {
            buf.append("/* ").append(comment).append(" */ ");
        }
        buf.append("insert into ")
                .append(tableName);
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

    private String getNoColumnsInsertString() {
        return "values ( )";
    }

    /**
     * The keyword used to insert a generated value into an identity column (or
     * null). Need if the dialect does not support inserts that specify no
     * column values.
     * 
     * @return The appropriate keyword.
     */
    public String getIdentityInsertString() {
        return null;
    }

}
