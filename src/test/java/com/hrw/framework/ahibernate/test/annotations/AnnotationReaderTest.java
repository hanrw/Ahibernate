
package com.hrw.framework.ahibernate.test.annotations;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import com.hrw.framework.ahibernate.annotation.AnnotationReader;
import com.hrw.framework.ahibernate.annotation.Id;
import com.hrw.framework.ahibernate.annotation.Table;
import com.hrw.framework.ahibernate.test.domain.Demo;

public class AnnotationReaderTest {

    private AnnotationReader mAnnotationReader;

    @Before
    public void setUp() {

    }

    @Test
    public void should_return_not_null_when_get_table_annotation() {
        mAnnotationReader = new AnnotationReader(Demo.class);
        assertNotNull(mAnnotationReader.getAnnotation(Table.class));
    }

    @Test
    public void should_return_not_null_when_get_id_annotation() throws SecurityException,
            NoSuchFieldException {
        Field idField;
        idField = Demo.class.getDeclaredField("id");
        assertNotNull(idField);
        mAnnotationReader = new AnnotationReader(idField);
        assertNotNull(mAnnotationReader.getAnnotation(Id.class));
    }
}
