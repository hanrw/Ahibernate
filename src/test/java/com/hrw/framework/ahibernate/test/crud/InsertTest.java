
package com.hrw.framework.ahibernate.test.crud;

import org.junit.Assert;
import org.junit.Test;

import com.hrw.framework.ahibernate.sql.Insert;
import com.hrw.framework.ahibernate.test.domain.Book;

public class InsertTest {

    private String EXPECTED_INSERT_SQL = "INSERT INTO book (id, book_name) values (1, 'newbook')";

    private String EXPECTED_INSERT_SQL1 = "INSERT INTO book (id, book_name) values (1, null)";

    @Test
    public void testGetTableName() {
        Book book = new Book();
        Insert insert = new Insert(book);
        Assert.assertEquals("book", insert.getTableName());
    }

    @Test
    public void testGetInsertColumns() throws IllegalArgumentException, IllegalAccessException {
        Book book = new Book();
        book.setBookName("newbook");
        Insert insert = new Insert(book);
        Assert.assertEquals("newbook", insert.getInsertColumns().get("book_name"));
    }

    // Id 自动生成，暂时无法设置保存到数据
    @Test
    public void testToStatementString1() throws IllegalArgumentException, IllegalAccessException {
        Book book = new Book();
        book.setBookName("newbook");
        book.setId(1L);
        Insert insert = new Insert(book);
        Assert.assertEquals(EXPECTED_INSERT_SQL, insert.toStatementString());
    }

    @Test
    public void testToStatementString2() throws IllegalArgumentException, IllegalAccessException {
        Book book = new Book();
        book.setBookName(null);
        book.setId(1L);
        Insert insert = new Insert(book);
        Assert.assertEquals(EXPECTED_INSERT_SQL1, insert.toStatementString());
    }

}
