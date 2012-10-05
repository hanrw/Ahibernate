
package com.hrw.framework.ahibernate.test.crud;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hrw.framework.ahibernate.sql.Insert;

public class InsertTest {

    private static String EXPECTED_INSERT_SQL = "insert into book (id, book_name) values (1, 'newbook')";

    private static String EXPECTED_INSERT_SQL1 = "insert into book (id, book_name) values (1, null)";

    @Test
    public void should_return_expected_sql() {
        Insert insert = new Insert();
        insert.setTableName("book");
        insert.addColumn("id", 1);
        insert.addColumn("book_name", "newbook");
        assertEquals(insert.toStatementString(), EXPECTED_INSERT_SQL);
    }

    @Test
    public void should_return_expected_sql1() {
        Insert insert = new Insert();
        insert.setTableName("book");
        insert.addColumn("id", 1);
        insert.addColumn("book_name", null);
        assertEquals(insert.toStatementString(), EXPECTED_INSERT_SQL1);
    }
}
