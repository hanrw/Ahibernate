
package com.hrw.framework.ahibernate.test.crud;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.hrw.framework.ahibernate.sql.Delete;
import com.hrw.framework.ahibernate.test.domain.Book;

public class DeleteTest {
    private String EXPECTED_DELETE_SQL1 = "DELETE FROM book";

    private String EXPECTED_DELETE_SQL2 = "DELETE FROM book WHERE id = 1";

    private String EXPECTED_DELETE_SQL3 = "DELETE FROM book WHERE id = 1 AND book_name = 'newbook'";

    @Test
    public void testGetTableName() throws Exception {
        Book book = new Book();
        book.setBookName("testBook");
        Delete update = new Delete(book);
        Assert.assertEquals("book", update.getTableName());
    }

//    @Test
//    public void toStatementString1() throws Exception {
//        Book book = new Book();
//        book.setBookName("testBook");
//        Delete update = new Delete(book);
//        Assert.assertEquals(EXPECTED_DELETE_SQL1, update.toStatementString());
//    }

    @Test
    public void toStatementString2() {
        Book book = new Book();
        HashMap<String, String> where = new HashMap<String, String>();
        where.put("id", "1");
        Delete update = new Delete(Book.class, where);
        Assert.assertEquals(EXPECTED_DELETE_SQL2, update.toStatementString());
    }

    @Test
    public void toStatementString3() {
        HashMap<String, String> where = new HashMap<String, String>();
        where.put("id", "1");
        where.put("book_name", "newbook");
        Delete update = new Delete(Book.class, where);
        Assert.assertEquals(EXPECTED_DELETE_SQL3, update.toStatementString());
    }

    @Test
    public void buildDeleteFail() {
        Book book = new Book();
        try {
            Delete delete = new Delete(book);
        } catch (Exception e) {
            Assert.assertEquals("can't delete,entity is illegal", e.getMessage());
        }
    }

    @Test
    public void buildDeleteSuccess1() {
        Book book = new Book();
        book.setId(1l);
        try {
            Delete delete = new Delete(book);
            Assert.assertEquals(EXPECTED_DELETE_SQL2, delete.toStatementString());
        } catch (Exception e) {
        }

    }
    
    @Test
    public void buildDeleteSuccess2() {
        Book book = new Book();
        book.setId(1l);
        book.setBookName("newbook");
        try {
            Delete delete = new Delete(book);
            Assert.assertEquals(EXPECTED_DELETE_SQL3, delete.toStatementString());
        } catch (Exception e) {
        }

    }
}
