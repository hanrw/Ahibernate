
package com.hrw.framework.ahibernate.test.annotations;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import com.hrw.framework.ahibernate.annotation.AnnotationReader;
import com.hrw.framework.ahibernate.annotation.Table;
import com.hrw.framework.ahibernate.test.domain.Demo;

public class AnnotationReaderTest {

    private AnnotationReader mAnnotationReader;

    @Before
    public void setUp() {

    }

    @Test
    public void should_return_null_when_get_annotations() {
        mAnnotationReader = new AnnotationReader(Demo.class);
        assertNotNull(mAnnotationReader.getJavaAnnotations());
    }

    @Test
    public void should_return_null_when_get_annotation() {
        mAnnotationReader = new AnnotationReader(Demo.class);
        assertNotNull(mAnnotationReader.getAnnotation(Table.class));
    }
}
