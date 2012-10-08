
package com.hrw.framework.ahibernate.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.database.Cursor;

import com.hrw.framework.ahibernate.annotation.AnnotationReader;
import com.hrw.framework.ahibernate.annotation.Column;
import com.hrw.framework.ahibernate.annotation.Id;

public class EntityBuilder<T> {
    private Class clazz;

    private Cursor cursor;

    public EntityBuilder(Class clazz, Cursor cursor) {
        this.clazz = clazz;
        this.cursor = cursor;
    }

    public List<T> buildQueryList() {
        List<T> queryList = new ArrayList<T>();
        Field[] fields = clazz.getDeclaredFields();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                try {
                    T t = (T) clazz.newInstance();
                    Annotation[] fieldAnnotations = null;
                    for (Field field : fields) {
                        field.setAccessible(true);
                        AnnotationReader ar = new AnnotationReader(field);
                        String columnName = ar.getAnnotationName();
                        if (ar.getAnnotation(Column.class) != null
                                || ar.getAnnotation(Id.class) != null) {
                            Object fiedValue = null;
                            if (isTypeOfLong(field)) {
                                fiedValue = cursor.getLong(getColumnIndexByName(field, columnName));
                            } else if (isTypeOfString(field)) {
                                fiedValue = cursor
                                            .getString(getColumnIndexByName(field, columnName));
                            }
                            field.set(
                                        t,
                                        fiedValue);
                        }

                    }
                    queryList.add(t);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        return queryList;
    }

    private int getColumnIndexByName(Field field, String columnName) {
        return cursor
                .getColumnIndexOrThrow((!StringUtils.isBlank(columnName) ? columnName : field
                        .getName()));
    }

    private boolean isTypeOfString(Field field) {
        return getFieldTypeSimpleName(field).equals("String");
    }

    private boolean isTypeOfLong(Field field) {
        return getFieldTypeSimpleName(field).equals("Long");
    }

    private String getFieldTypeSimpleName(Field field) {
        return field.getType().getSimpleName();
    }

}
