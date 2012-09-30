
package com.hrw.framework.ahibernate.test.cfg;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.hrw.framework.ahibernate.cfg.Configuration;
import com.hrw.framework.ahibernate.mapping.Table;
import com.hrw.framework.ahibernate.test.domain.Book;
import com.hrw.framework.ahibernate.test.domain.Demo;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ConfigurationTest {
    Configuration mCfg;

    @Before
    public void setUp() {
        mCfg = Configuration.getInstance().configure();
    }

    @After
    public void tearDown() {
        mCfg = null;
    }

    @Test
    public void shoud_return_not_null_when_get_annotation_class() {
        assertThat(Demo.class, equalTo(mCfg.getEntityPersisters().get(Demo.class.getName())));
    }

    @Test
    public void shoud_return_2_when_get_tables() {
        assertEquals(2, mCfg.getTables().size());
    }

    @Test
    public void shoud_return_not_null_when_get_table_demo() {
        assertNotNull(mCfg.getTable(Demo.class.getName()));
    }

    @Test
    public void shoud_return_not_null_when_get_table_book() {
        assertNotNull(mCfg.getTable(Book.class.getName()));
    }

    @Test
    public void shoud_return_2_when_get_columns_size() {
        Table table = mCfg.getTable(Book.class.getName());
        assertEquals(2, table.getColumns().size());
    }

    @Test
    public void shoud_return_book_name_when_get_column_book_name() {
        Table table = mCfg.getTable(Book.class.getName());
        assertEquals("book_name", table.getColumn("bookName").getName());
    }

    @Test
    public void shoud_return_id_when_get_column_id() {
        Table table = mCfg.getTable(Book.class.getName());
        assertEquals("id", table.getColumn("id").getName());
    }

}
