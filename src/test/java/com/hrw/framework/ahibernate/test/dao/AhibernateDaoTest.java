
package com.hrw.framework.ahibernate.test.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.database.sqlite.SQLiteDatabase;

import com.hrw.framework.ahibernate.dao.AhibernateDao;
import com.hrw.framework.ahibernate.table.TableUtils;
import com.hrw.framework.ahibernate.test.domain.Demo;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowSQLiteDatabase;

@RunWith(RobolectricTestRunner.class)
public class AhibernateDaoTest {
    private AhibernateDao<Demo> dao;

    protected SQLiteDatabase database;

    protected ShadowSQLiteDatabase shDatabase;

    @Before
    public void setUp() {
        database = SQLiteDatabase.openDatabase("path", null, 0);
        dao = new AhibernateDao<Demo>(database);
    }

    @After
    public void tearDown() {
        database = null;
        dao = null;
    }

    @Test
    public void should_not_null() throws Exception {
        assertNotNull(dao);
    }

    @Test
    public void should_return_sqlitedatabase() throws Exception {
        assertThat(dao.getSQLiteDatabase(), notNullValue());
    }

    @Test
    public void should_return_true_when_create_table_sql() throws Exception {
        TableUtils.createTable(database, true, Demo.class);
        Demo entity = new Demo();
        dao.insert(entity);
        assertThat(1, equalTo(dao.queryList(entity).size()));
    }
}
