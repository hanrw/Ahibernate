
package com.hrw.framework.ahibernate.test.table;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import com.hrw.framework.ahibernate.table.TableInfo;
import com.hrw.framework.ahibernate.test.domain.Book;
import com.hrw.framework.ahibernate.test.domain.Demo;
import com.hrw.framework.ahibernate.test.domain.DemoWithNoAnnotation;

public class TableInfoTest {
    @Test
    public void testExtractTableInfo() {
        TableInfo tableInfo = new TableInfo(Book.class);
        assertEquals("book", tableInfo.getTableName());
        assertEquals("book_name", tableInfo.getColumns().get("bookName"));
        assertEquals("String", tableInfo.getColumnsType().get("bookName"));
    }

    @Test
    public void should_return_0_when_entity_no_annotation() {
        assertThat(0, equalTo(TableInfo.getAnnotations(DemoWithNoAnnotation.class).size()));
    }

    @Test
    public void should_return_2_when_entity_has_2_annotation() {
        assertThat(3, equalTo(TableInfo.getAnnotations(Demo.class).size()));
    }

}
