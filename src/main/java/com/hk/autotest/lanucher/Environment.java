package com.hk.autotest.lanucher;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hk.autotest.exception.CapException;

/**
 * 环境变量入口
 */

public class Environment {

	static final Logger logger = LoggerFactory.getLogger(Environment.class);

	// static final AtomicInteger counter = new AtomicInteger();

	private static final AtomicBoolean isLab = new AtomicBoolean(false);

	/********** CI Job 配置参数 ******************/
	public static String getAppPath() {
		return System.getenv("app");
	}

	/***********************/

	/********** Mock 本地Jenkins 环境变量 **************/

	static {
		if (isLocalJenkinsMock()) {
			System.setProperty("Params", "env=uat;runid=8367;runtype=APP");
			System.setProperty("JOB_NAME", "APP.AT.CIMobile4Java.Test");
			System.setProperty("JENKINS_HOME", "E:\\jenkins");
		}

	}

	/*************************/

	public static boolean isLab() {
		return isLab.get();
	}

	public static String isLabName() {
		return isLab() ? "Lab" : "Local";

	}

	public static boolean isLocalJenkinsMock() {
		String userName = System.getenv("USERNAME");

		return
		// "c.shi".equalsIgnoreCase(userName)
		"ltyao".equalsIgnoreCase(userName);
	}

	/**
	 * build appium log file name
	 * 
	 * @return
	 */
	public static String appiumLog(String udid) {

		File directory = appiumLogHome();
		try {
			String timestamp = FastDateFormat
					.getInstance("yyyy-MM-dd-HH-mm-ss").format(new Date());
			File log = new File(directory, timestamp + "-" + udid + ".log");
			log.createNewFile();
			return log.getAbsolutePath();
		} catch (Exception e) {
			throw new CapException("cann't set appium log name", e);
		}

	}

	public static File appiumLogHome() {
		return new File(logHome(), "appium");
	}

	public static File logHome() {
		return new File(capHome(), "log");
	}

	public static File getTempDirectory() {
		return new File(System.getProperty("java.io.tmpdir"));
	}

	/**
	 * 
	 * @return
	 */
	public static File capHome() {
		return new File(System.getProperty("user.home"), ".cap");
	}

	public static File assertsHome() {
		return new File(capHome(), "asserts");
	}

	public static File cacheHome() {
		return new File(capHome(), "cache");
	}

	public static String appiumHome() {
		return "";
	}

	public static File assertsCopyHome() {
		return new File(assertsHome(), "copy");
	}

	/** prepare appium home *********/
	static {

		try {
			FileUtils.forceMkdir(appiumLogHome());
			FileUtils.forceMkdir(assertsCopyHome());
			FileUtils.forceMkdir(cacheHome());
		} catch (IOException e) {
			logger.error("prepare directory", e);
			throw new CapException("prepare directory", e);
		}

	}

	private static PropertiesConfiguration config;

	static {
		try {

			config = new PropertiesConfiguration();
			config.load(new File(capHome(), "cap.properties"));

		} catch (ConfigurationException e) {
			config = null;
			logger.warn("No cap configration file,log message and http download maybe failure in lab mode");
		}

		if (config != null) {
			String runMode = String.valueOf(config.getProperty("runMode"));
			boolean r = StringUtils.equalsIgnoreCase("lab", runMode);
			isLab.set(r);
		}

		String userName = System.getenv("USERNAME");
		if (StringUtils.equalsIgnoreCase(userName, "tfsci")
				|| StringUtils.equalsIgnoreCase(userName, "c.shi")) {
			isLab.set(true);
		}
	}

	public static String getSvnUser() {
		if (config != null)
			return String.valueOf(config.getProperty("svn.user"));
		return "";
	}

	public static String getSvnPassword() {
		if (config != null)
			return String.valueOf(config.getProperty("svn.password"));
		return "";
	}

	public static String getDataURI() {
		if (config != null)
			return String.valueOf(config.getProperty("cap.data.url"));
		return null;
	}

	public static void clearWorkspace() {
		File assertCopy = Environment.assertsCopyHome();
		try {
			FileUtils.cleanDirectory(assertCopy);
		} catch (IOException e) {
			logger.warn("clear workspace ", e);
		}
	}

}
