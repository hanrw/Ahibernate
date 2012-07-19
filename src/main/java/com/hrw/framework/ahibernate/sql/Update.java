
package com.hrw.framework.ahibernate.sql;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.hrw.framework.ahibernate.annotation.Column;
import com.hrw.framework.ahibernate.annotation.Id;
import com.hrw.framework.ahibernate.annotation.OneToMany;

public class Update extends Operate {
    private Object entity;

    private Map<String, String> where;

    public Update(Object entity) {
        super(entity.getClass());
        this.entity = entity;
        try {
            this.where = getDefaultWhereField();
        } catch (Exception e) {
            this.where = null;
            e.printStackTrace();
        }
    }

    public Update(Object entity, Map<String, String> where) {
        super(entity.getClass());
        this.entity = entity;
        this.where = where;
    }

    public Map<String, String> getDefaultWhereField() throws IllegalArgumentException,
            IllegalAccessException {
        Map<String, String> defaultWhereField = new HashMap<String, String>();
        Class clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Annotation[] fieldAnnotations = null;
        for (Field field : fields) {
            fieldAnnotations = field.getAnnotations();
            if (fieldAnnotations.length != 0) {
                for (Annotation annotation : fieldAnnotations) {
                    String columnName = null;
                    if (annotation instanceof Id) {
                        // do not update id.default primary key
                        columnName = ((Id) annotation).name();
                        field.setAccessible(true);
                        defaultWhereField.put(
                                (columnName != null && !columnName.equals("")) ? columnName : field
                                        .getName(),
                                field.get(entity) == null ? null : field.get(entity).toString());
                    }

                }
            }
        }
        return defaultWhereField;

    }

    @SuppressWarnings("rawtypes")
    public Map<String, String> getUpdateFields() {
        Map<String, String> updateFields = new HashMap<String, String>();
        Class clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Annotation[] fieldAnnotations = null;
        for (Field field : fields) {
            fieldAnnotations = field.getAnnotations();
            if (fieldAnnotations.length != 0) {
                for (Annotation annotation : fieldAnnotations) {
                    String columnName = null;
                    if (annotation instanceof Id && !((Id)annotation).autoGenerate()) {
                        columnName = ((Id) annotation).name();
                        // do not update id.default primary key
                        // columnName = ((Id) annotation).name();
                        continue;
                    } else if (annotation instanceof Column) {
                        columnName = ((Column) annotation).name();
                    } else if (annotation instanceof OneToMany) {
                        continue;
                        // Ignore
                    }
                    field.setAccessible(true);
                    try {
                        updateFields.put(
                                (columnName != null && !columnName.equals("")) ? columnName : field
                                        .getName(),
                                field.get(entity) == null ? null : field.get(entity).toString());
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return updateFields;
    }

    public String toStatementString() {
        return buildUpdateSql(getTableName(), getUpdateFields(), where);
    }

    public Map<String, String> getWhereFiled() {
        return where;
    }

}
