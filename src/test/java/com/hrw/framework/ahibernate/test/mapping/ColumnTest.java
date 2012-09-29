
package com.hrw.framework.ahibernate.test.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hrw.framework.ahibernate.mapping.Column;

public class ColumnTest {
    private static final String EXPECTED_VALUE = "demovalue";
    private static final String EXPECTED_NAME = "demo";
    private Column column;

    @Before
    public void setUp() {
        column = new Column();
        column.setName(EXPECTED_NAME);
        column.setValue(EXPECTED_VALUE);
    }

    @Test
    public void should_return_column_name() {
        assertEquals(EXPECTED_NAME, column.getName());
    }

    @Test
    public void should_return_column_value() {
        assertEquals(EXPECTED_VALUE, column.getValue());
    }
}
