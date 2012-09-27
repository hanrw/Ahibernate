
package com.hrw.framework.ahibernate.test.cfg;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

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

    @Test
    public void shoud_not_null_when_get_annotation_class() {
        assertThat(Demo.class, equalTo(mCfg.getEntityPersisters().get(Demo.class.getName())));
    }
}
