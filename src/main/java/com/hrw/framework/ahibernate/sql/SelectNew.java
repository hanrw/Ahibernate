
package com.hrw.framework.ahibernate.sql;

import com.hrw.framework.ahibernate.util.StringHelper;

public class SelectNew {

    private String selectClause;
    private String fromClause;
    private String whereClause;

    private int guesstimatedBufferSize = 20;

    /**
     * Construct an SQL <tt>SELECT</tt> statement from the given clauses
     */
    public String toStatementString() {
        StringBuffer buf = new StringBuffer(guesstimatedBufferSize);

        buf.append("select ").append(selectClause)
                .append(" from ").append(fromClause);

        if (StringHelper.isNotEmpty(whereClause)) {
            buf.append(" where ");
            buf.append(whereClause);
        }

        return buf.toString();
    }

    /**
     * Sets the fromClause.
     * 
     * @param fromClause The fromClause to set
     */
    public SelectNew setFromClause(String fromClause) {
        this.fromClause = fromClause;
        this.guesstimatedBufferSize += fromClause.length();
        return this;
    }

    public SelectNew setFromClause(String tableName, String alias) {
        this.fromClause = tableName + ' ' + alias;
        this.guesstimatedBufferSize += fromClause.length();
        return this;
    }

    /**
     * Sets the selectClause.
     * 
     * @param selectClause The selectClause to set
     */
    public SelectNew setSelectClause(String selectClause) {
        this.selectClause = selectClause;
        this.guesstimatedBufferSize += selectClause.length();
        return this;
    }

    /**
     * Sets the whereClause.
     * 
     * @param whereClause The whereClause to set
     */
    public SelectNew setWhereClause(String whereClause) {
        this.whereClause = whereClause;
        this.guesstimatedBufferSize += whereClause.length();
        return this;
    }

}
