
package com.hrw.framework.ahibernate.test.cfg;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.hrw.framework.ahibernate.cfg.Configuration;
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
    public void shoud_not_null_when_get_annotation_class() {
        assertThat(Demo.class, equalTo(mCfg.getEntityPersisters().get(Demo.class.getName())));
    }

    @Test
    public void should_return_column_builder() {
        assertNotNull(mCfg.getColumnBuilder());
    }

    @Test
    public void should_return_columns_not_empty() {
        assertEquals(2, mCfg.getColumnBuilder().getColumns().size());
    }

    @Test
    public void should_return_demo_name_when_get_name_field() {
        assertEquals("", mCfg.getColumnBuilder().getColumns().get(0).getName());
        assertEquals("demo_name", mCfg.getColumnBuilder().getColumns().get(1).getName());
    }
}
