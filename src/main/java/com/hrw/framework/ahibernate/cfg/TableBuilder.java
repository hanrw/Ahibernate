
package com.hrw.framework.ahibernate.cfg;

import java.lang.reflect.AnnotatedElement;

import com.hrw.framework.ahibernate.mapping.Table;

public class TableBuilder {

    public static Table build(AnnotatedElement al) {
        String tableName = al.getAnnotation(com.hrw.framework.ahibernate.annotation.Table.class)
                .name();
        return new Table().setName(tableName);
    }

}
