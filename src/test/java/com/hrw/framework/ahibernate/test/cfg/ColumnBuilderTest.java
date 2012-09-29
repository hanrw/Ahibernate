
package com.hrw.framework.ahibernate.test.cfg;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hrw.framework.ahibernate.cfg.ColumnsBuilder;
import com.hrw.framework.ahibernate.mapping.Column;

public class ColumnBuilderTest {
    private ColumnsBuilder columnBuilder;

    @Before
    public void setUp() {
        columnBuilder = new ColumnsBuilder().addColumn(new Column());
    }

    @Test
    public void should_return_column() {
        assertEquals(1, columnBuilder.getColumns().size());
    }
}
