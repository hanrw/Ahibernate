
package com.hrw.framework.ahibernate.cfg;

import org.apache.log4j.Logger;

import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class Log4jHelper {

    private final static LogConfigurator LOG_CONFIGURATOR = new LogConfigurator();

    /**
     * Configure Log4j
     * 
     * @param fileName Name of the log file
     * @param filePattern Output format of the log line
     * @param maxBackupSize Maximum number of backed up log files
     * @param maxFileSize Maximum size of log file until rolling
     */
    public static void configure(String fileName, String filePattern,
            int maxBackupSize, long maxFileSize) {

        // set the name of the log file
        LOG_CONFIGURATOR.setFileName(fileName);

        // set output format of the log line
        // see :
        // http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/PatternLayout.html
        LOG_CONFIGURATOR.setFilePattern(filePattern);

        // set immediateFlush = true, if you want output stream will be flushed
        // at the end of each append operation
        // default value is true
        // _logConfigurator.setImmediateFlush(immediateFlush);

        // set output format of the LogCat line
        // see :
        // http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/PatternLayout.html
        // _logConfigurator.setLogCatPattern(logCatPattern);

        // Maximum number of backed up log files
        LOG_CONFIGURATOR.setMaxBackupSize(maxBackupSize);

        // Maximum size of log file until rolling
        LOG_CONFIGURATOR.setMaxFileSize(maxFileSize);

        // set true to appends log events to a file, otherwise set false
        // default value is true
        // _logConfigurator.setUseFileAppender(useFileAppender);

        // set true to appends log events to a LogCat, otherwise set false
        // default value is true
        // _logConfigurator.setUseLogCatAppender(useLogCatAppender);

        // configure
        LOG_CONFIGURATOR.configure();

    }

    public static void configureLog4j() {
        // set file name
        String fileName = Environment.getExternalStorageDirectory() + "/" + "log4j.log";
        // set log line pattern
        String filePattern = "%d - [%c] - %p : %m%n";
        // set max. number of backed up log files
        int maxBackupSize = 10;
        // set max. size of log file
        long maxFileSize = 1024 * 1024;

        // configure
        configure(fileName, filePattern, maxBackupSize, maxFileSize);
    }

    /**
     * Get logging operations class
     * 
     * @param name The name of the logger to retrieve.
     * @return Logging operations class
     */
    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        return logger;
    }

}
