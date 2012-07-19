
package com.hrw.framework.ahibernate.test.table;

import org.junit.Assert;
import org.junit.Test;

import com.hrw.framework.ahibernate.table.TableInfo;
import com.hrw.framework.ahibernate.test.domain.Book;

public class TableInfoTest {
    @Test
    public void testExtractTableInfo() {
        TableInfo tableInfo = new TableInfo(Book.class);
        Assert.assertEquals("book", tableInfo.getTableName());
        Assert.assertEquals("book_name", tableInfo.getColumns().get("bookName"));
        Assert.assertEquals("String", tableInfo.getColumnsType().get("bookName"));
    }

}
