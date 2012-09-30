
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

import com.hrw.framework.ahibernate.exceptions.InsertException;
import com.hrw.framework.ahibernate.exceptions.MappingException;
import com.hrw.framework.ahibernate.table.TableUtils;
import com.hrw.framework.ahibernate.test.domain.Demo;
import com.hrw.framework.ahibernate.test.domain.DemoWithNoAnnotation;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowSQLiteDatabase;

@RunWith(RobolectricTestRunner.class)
public class AhibernateDaoTest {
    private DemoAhibernateDao dao;

    protected SQLiteDatabase database;

    protected ShadowSQLiteDatabase shDatabase;

    private DemoDaoWithNoAnnotation demDaoWithNoAnnotation;

    @Before
    public void setUp() {
        database = SQLiteDatabase.openDatabase("path", null, 0);
        dao = new DemoAhibernateDao(database);
        demDaoWithNoAnnotation = new DemoDaoWithNoAnnotation(database);
        TableUtils.createTable(database, true, Demo.class);
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
    public void should_return_generic_type_demo() {
        assertThat(Demo.class, equalTo(dao.getGenricTypeClass()));
    }

    @Test
    public void should_return_1_demo_item_if_query_null() {
        Demo entity = new Demo();
        dao.insert(entity);
        dao.insert(entity);
        dao.insert(entity);
        assertThat(3, equalTo(dao.queryList(null).size()));
    }

    @Test(expected = AssertionError.class)
    public void should_throw_exception_when_insert_null() {
        dao.insert(null);
    }

    @Test
    public void should_return_not_null_when_get_cfg() {
        assertNotNull(dao.getConfiguration());
    }

    @Test
    public void should_return_id_1() {
        Demo demo = new Demo();
        demo.setId(1l);
        demo.setName("demo");
        assertThat(1, equalTo(dao.insert(demo)));
    }

    @Test(expected = MappingException.class)
    public void should_return_mappingException() {
        dao.insert(new DemoWithNoAnnotation());
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_no_annotation() {
        demDaoWithNoAnnotation.insert(new DemoWithNoAnnotation());
    }

}
