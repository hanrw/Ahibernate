
package com.hrw.framework.ahibernate.sql;

import java.util.Map;

public class Delete extends Operate {
    private Object entity;

    private Map<String, String> where;

    /**
     * delete entity by id.
     * 
     * @param entity
     * @throws Exception
     */
    public Delete(Object entity) {
        super(entity.getClass());
        this.entity = entity;
        try {
            this.where = buildWhere(entity);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * delete entity by where,if where is null it will delete all records.
     * 
     * @param entity
     * @param where
     */
    @SuppressWarnings("rawtypes")
    public Delete(Class clazz, Map<String, String> where) {
        super(clazz);
        this.where = where;
    }

    public String toStatementString() {
        return buildDeleteSql(getTableName(), where);
    }
}
