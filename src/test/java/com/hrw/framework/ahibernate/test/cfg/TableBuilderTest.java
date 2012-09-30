
package com.hrw.framework.ahibernate.test.cfg;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.hrw.framework.ahibernate.cfg.TableBuilder;
import com.hrw.framework.ahibernate.test.domain.Demo;

public class TableBuilderTest {
    @Test
    public void should_return_not_null_when_build() {
        assertNotNull(TableBuilder.build(Demo.class));
    }

}
