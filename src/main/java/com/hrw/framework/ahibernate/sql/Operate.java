
package com.hrw.framework.ahibernate.sql;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hrw.framework.ahibernate.annotation.Column;
import com.hrw.framework.ahibernate.annotation.Id;
import com.hrw.framework.ahibernate.annotation.OneToMany;
import com.hrw.framework.ahibernate.table.TableUtils;

public class Operate {

    public Operate(Class clazz) {
        tableName = TableUtils.getTableName(clazz);
    }

    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public String buildSelectSql(String tableName, Map<String, String> where) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("SELECT * FROM ");
        sb.append(tableName);
        Iterator iter = null;
        if (where != null) {
            sb.append(" WHERE ");
            iter = where.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry e = (Map.Entry) iter.next();
                sb.append(e.getKey())
                        .append(" = ")
                        .append(isNumeric(e.getValue().toString()) ? e.getValue() : "'"
                                + e.getValue() + "'");
                if (iter.hasNext()) {
                    sb.append(" AND ");
                }
            }
        }
        return sb.toString();
    }

    public String buildInsertSql(String tableName, Map<String, String> insertColumns) {
        StringBuilder columns = new StringBuilder(256);
        StringBuilder values = new StringBuilder(256);
        columns.append("INSERT INTO ");

        columns.append(tableName).append(" (");
        values.append("(");

        Iterator iter = insertColumns.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry e = (Map.Entry) iter.next();
            columns.append(e.getKey());
            values.append(isNumeric(e.getValue() != null ? e.getValue().toString() : "") ? e
                    .getValue() : "'" + e.getValue() + "'");
            if (iter.hasNext()) {
                columns.append(", ");
                values.append(", ");
            }
        }
        columns.append(") values ");
        values.append(")");
        columns.append(values);
        return columns.toString();
    }

    public String buildUpdateSql(String tableName, Map<String, String> needUpdate,
            Map<String, String> where) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("UPDATE ");
        sb.append(tableName).append(" SET ");

        Iterator iter = needUpdate.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry e = (Map.Entry) iter.next();
            sb.append(e.getKey())
                    .append(" = ")
                    .append(isNumeric(e.getValue().toString()) ? e.getValue() : "'" + e.getValue()
                            + "'");
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        if (where != null) {
            sb.append(" where ");
            iter = where.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry e = (Map.Entry) iter.next();
                sb.append(e.getKey())
                        .append(" = ")
                        .append(isNumeric(e.getValue().toString()) ? e.getValue() : "'"
                                + e.getValue() + "'");
                if (iter.hasNext()) {
                    sb.append(" and ");
                }
            }
        }
        return sb.toString();
    }

    public String buildDeleteSql(String tableName, Map<String, String> where) {
        StringBuffer buf = new StringBuffer(tableName.length() + 10);
        buf.append("DELETE FROM ").append(tableName);
        if (where != null) {
            buf.append(" WHERE ");
            Iterator iter = where.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry e = (Map.Entry) iter.next();
                buf.append(e.getKey())
                        .append(" = ")
                        .append(isNumeric(e.getValue().toString()) ? e.getValue() : "'"
                                + e.getValue() + "'");
                if (iter.hasNext()) {
                    buf.append(" AND ");
                }
            }
        }
        return buf.toString();
    }

    public Map<String, String> buildWhere(Object entity) throws Exception {
        Map<String, String> where = new HashMap<String, String>();
        Class clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Annotation[] fieldAnnotations = null;
        for (Field field : fields) {
            field.setAccessible(true);
            fieldAnnotations = field.getAnnotations();
            if (fieldAnnotations.length != 0) {
                for (Annotation annotation : fieldAnnotations) {
                    String columnName = null;
                    if (annotation instanceof Id) {
                        columnName = ((Id) annotation).name();
                    } else if (annotation instanceof Column) {
                        columnName = ((Column) annotation).name();
                    } else if (annotation instanceof OneToMany) {
                        continue;
                        // Ignore
                    }
                    try {
                        if (null != field.get(entity) && field.get(entity).toString().length() > 0) {
                            where.put((columnName != null && !columnName.equals("")) ? columnName
                                    : field.getName(), field.get(entity).toString());
                        }
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
        if (where.isEmpty()) {
            throw new Exception("can't delete,entity is illegal");
        }
        return where;
    }

}
