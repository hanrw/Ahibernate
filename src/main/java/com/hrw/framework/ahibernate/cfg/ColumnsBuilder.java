
package com.hrw.framework.ahibernate.cfg;

import java.util.LinkedList;
import java.util.List;

import com.hrw.framework.ahibernate.mapping.Column;

public class ColumnsBuilder {
    List<Column> columns;

    public ColumnsBuilder() {
        columns = new LinkedList<Column>();
    }

    public List<Column> getColumns() {
        return columns;
    }

    public ColumnsBuilder add() {
        return this;
    }

    public ColumnsBuilder addColumn(Column column) {
        columns.add(column);
        return this;
    }

}
