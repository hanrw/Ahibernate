
package com.hrw.framework.ahibernate.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * A relational table
 */
public class Table {
    private String name;

    private String identifierName;

    public String getIdentifierName() {
        return identifierName;
    }

    public void setIdentifierName(String identifierName) {
        this.identifierName = identifierName;
    }

    private Map<String, Column> columns = new HashMap<String, Column>();

    public String getName() {
        return name;
    }

    public Table setName(String name) {
        this.name = name;
        return this;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public Column getColumn(String columnName) {
        return columns.get(columnName);
    }

    public void addColumn(String fieldName, Column column) {
        columns.put(fieldName, column);
    }

}
