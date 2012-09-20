
package com.hrw.framework.ahibernate.table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TableInfo {

    private Map<String, String> columns;

    private Map<String, String> columnsType;

    private String tableName;

    private String primaryKey;

    public TableInfo(Class<?> clazz) {
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

    public static List<Annotation> getAnnotations(Class<?> clazz) {
        List<Annotation> list = new ArrayList<Annotation>();
        list.addAll(Arrays.asList(findClassAnnotation(clazz)));
//        list.addAll(findMethodsAnnotation(clazz));
        list.addAll(findFieldsAnnotation(clazz));
        return list;
    }

    private static List<Annotation> findMethodsAnnotation(Class<?> clazz) {
        List<Annotation> list = new ArrayList<Annotation>();
        for (Method method : clazz.getDeclaredMethods()) {
            list.addAll((Arrays.asList(findMethodAnnotation(clazz, method.getName()))));
        }
        return list;
    }

    private static List<Annotation> findFieldsAnnotation(Class<?> clazz) {
        List<Annotation> list = new ArrayList<Annotation>();
        for (Field field : clazz.getDeclaredFields()) {
            list.addAll((Arrays.asList(findFieldAnnotation(clazz, field.getName()))));
        }
        return list;
    }

    public static Annotation[] findClassAnnotation(Class<?> clazz) {
        return clazz.getAnnotations();
    }

    public static Annotation[] findMethodAnnotation(Class<?> clazz, String methodName) {

        Annotation[] annotations = null;
        try {
            Class<?>[] params = null;
            Method method = clazz.getDeclaredMethod(methodName, params);
            if (method != null) {
                annotations = method.getAnnotations();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return annotations;
    }

    public static Annotation[] findFieldAnnotation(Class<?> clazz, String fieldName) {
        Annotation[] annotations = null;
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (field != null) {
                annotations = field.getAnnotations();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return annotations;
    }

}
