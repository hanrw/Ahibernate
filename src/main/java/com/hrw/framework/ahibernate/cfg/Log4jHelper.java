package com.hrw.framework.ahibernate.cfg;

import org.apache.log4j.Logger;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class Log4jHelper {

	private final static LogConfigurator _logConfigurator = new LogConfigurator();

	/**
	 * Configure Log4j
	 * 
	 * @param fileName
	 *            Name of the log file
	 * @param filePattern
	 *            Output format of the log line
	 * @param maxBackupSize
	 *            Maximum number of backed up log files
	 * @param maxFileSize
	 *            Maximum size of log file until rolling
	 */
	public static void Configure(String fileName, String filePattern,
			int maxBackupSize, long maxFileSize) {

		// set the name of the log file
		_logConfigurator.setFileName(fileName);

		// set output format of the log line
		// see :
		// http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/PatternLayout.html
		_logConfigurator.setFilePattern(filePattern);

		// set immediateFlush = true, if you want output stream will be flushed
		// at the end of each append operation
		// default value is true
		// _logConfigurator.setImmediateFlush(immediateFlush);

		// set output format of the LogCat line
		// see :
		// http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/PatternLayout.html
		// _logConfigurator.setLogCatPattern(logCatPattern);

		// Maximum number of backed up log files
		_logConfigurator.setMaxBackupSize(maxBackupSize);

		// Maximum size of log file until rolling
		_logConfigurator.setMaxFileSize(maxFileSize);

		// set true to appends log events to a file, otherwise set false
		// default value is true
		// _logConfigurator.setUseFileAppender(useFileAppender);

		// set true to appends log events to a LogCat, otherwise set false
		// default value is true
		// _logConfigurator.setUseLogCatAppender(useLogCatAppender);

		// configure
		_logConfigurator.configure();

	}

	/**
	 * Get logging operations class
	 * 
	 * @param name
	 *            The name of the logger to retrieve.
	 * @return Logging operations class
	 */
	public static Logger getLogger(String name) {
		Logger logger = Logger.getLogger(name);
		return logger;
	}

}
