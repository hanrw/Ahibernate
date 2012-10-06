
package com.hrw.framework.ahibernate.test.cfg;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.hrw.framework.ahibernate.cfg.Log4jHelper;
import com.xtremelabs.robolectric.shadows.ShadowEnvironment;

public class Log4jHelperTest {
    private Logger mLog;

    private void configureLog4j() {
        // set file name
        String fileName = ShadowEnvironment.getExternalStorageDirectory() + "/" + "log4j.log";
        // set log line pattern
        String filePattern = "%d - [%c] - %p : %m%n";
        // set max. number of backed up log files
        int maxBackupSize = 10;
        // set max. size of log file
        long maxFileSize = 1024 * 1024;

        // configure
        Log4jHelper.configure(fileName, filePattern, maxBackupSize, maxFileSize);
    }

    @Before
    public void setUp() {
        configureLog4j();
    }

    @Test
    public void should_return_not_null() {
        mLog = Log4jHelper.getLogger("Log4jActivity");
        assertNotNull(mLog);
    }
}
