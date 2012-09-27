
package com.hrw.framework.ahibernate.test.annotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import com.hrw.framework.ahibernate.annotation.AnnotationReader;
import com.hrw.framework.ahibernate.annotation.Column;
import com.hrw.framework.ahibernate.annotation.Id;
import com.hrw.framework.ahibernate.annotation.Table;
import com.hrw.framework.ahibernate.test.domain.Demo;

public class AnnotationReaderTest {

    private AnnotationReader mAnnotationReader;

    private AnnotationReader getAnnotationReaderInstance(AnnotatedElement el) {
        return new AnnotationReader(el);
    }

    private Field getDemoField(String fieldName) throws NoSuchFieldException, SecurityException {
        return Demo.class.getDeclaredField(fieldName);
    }

    private void initAnnotationReaderForField(String fieldName) throws NoSuchFieldException {
        Field idField = getDemoField(fieldName);
        mAnnotationReader = getAnnotationReaderInstance(idField);
    }

    @Before
    public void setUp() {

    }

    @Test
    public void should_return_not_null_when_get_table_annotation() {
        mAnnotationReader = getAnnotationReaderInstance(Demo.class);
        assertNotNull(mAnnotationReader.getAnnotation(Table.class));
    }

    @Test
    public void should_return_not_null_when_get_declared_field() throws SecurityException,
            NoSuchFieldException {
        Field idField = getDemoField("id");
        Field nameField = getDemoField("name");
        Field genderField = getDemoField("gender");
        assertNotNull(idField);
        assertNotNull(nameField);
        assertNotNull(genderField);
    }

    @Test
    public void should_return_not_null_when_get_id_annotation() throws SecurityException,
            NoSuchFieldException {
        initAnnotationReaderForField("id");
        assertNotNull(mAnnotationReader.getAnnotation(Id.class));
    }

    @Test
    public void should_return_table_annotation() {
        mAnnotationReader = new AnnotationReader(Demo.class);
        assertNotNull(equalTo(mAnnotationReader.getAnnotation(Table.class)));
    }

    @Test
    public void should_return_empty_when_get_id_annotation_name() throws SecurityException,
            NoSuchFieldException {
        initAnnotationReaderForField("id");
        assertThat("", equalTo(mAnnotationReader.getAnnotation(Id.class).name()));
    }

    @Test
    public void should_return_not_null_when_get_name_annotation() throws SecurityException,
            NoSuchFieldException {
        initAnnotationReaderForField("name");
        assertNotNull(mAnnotationReader.getAnnotation(Column.class));
    }

    @Test
    public void should_return_demo_name_when_get_name_annotation_name() throws SecurityException,
            NoSuchFieldException {
        initAnnotationReaderForField("name");
        assertThat("demo_name", equalTo(mAnnotationReader.getAnnotation(Column.class).name()));
    }

}
