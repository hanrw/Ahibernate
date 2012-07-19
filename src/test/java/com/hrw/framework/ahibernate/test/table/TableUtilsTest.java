
package com.hrw.framework.ahibernate.test.table;

import org.junit.Assert;
import org.junit.Test;

import com.hrw.framework.ahibernate.table.TableInfo;
import com.hrw.framework.ahibernate.table.TableUtils;
import com.hrw.framework.ahibernate.test.domain.Book;

public class TableUtilsTest {
    @Test
    public void testGetTableNameNotEmpty() {
        Assert.assertEquals("book", TableUtils.getTableName(Book.class));
    }

    @Test
    public void testGetTableNameEmpty() {
        Assert.assertEquals("string", TableUtils.getTableName(String.class));
    }

    @Test
    public void testGetTableColumns() {
        Assert.assertEquals("book_name", TableUtils.getTableColumns(Book.class).get("bookName"));
    }

    @Test
    public void testGetTableColumnsType() {
        Assert.assertEquals("String", TableUtils.getTableColumnsType(Book.class).get("bookName"));
        Assert.assertEquals("Long", TableUtils.getTableColumnsType(Book.class).get("id"));
    }

    @Test
    public void testCreateTable() {
        TableInfo tableInfo = new TableInfo(Book.class);
        Assert.assertEquals("CREATE TABLE IF NOT EXISTS book (id INTEGER PRIMARY KEY, book_name TEXT)", TableUtils.buildCreateTableStatement(tableInfo, true));
    }

    @Test
    public void testDropTable() {
        TableInfo tableInfo = new TableInfo(Book.class);
        Assert.assertEquals("DROP TABLE IF EXISTS book", TableUtils.buildDropTableStatement(tableInfo));
    }
}
