package com.hk.autotest.reporter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

//import com.ctrip.cap.CapLogger;
//import com.ctrip.cap.device.model.AppInfo;
//import com.ctrip.cap.device.model.DeviceInfo;
import com.hk.autotest.internal.suite.ReporterSuiteListener;
import com.hk.autotest.internal.suite.SuiteManListener;
import com.hk.autotest.lanucher.Config;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ResourceInfo;
import com.google.gson.GsonBuilder;

public class CapReporter implements IReporter {
	private static final Logger logger = LoggerFactory
			.getLogger(CapReporter.class);

	private static final String relativeDir = "cap-reporter";

	private SuiteResultWriter writer = new SuiteResultWriter();

	protected TestClass parseTestClass(
			Map.Entry<String, List<ITestResult>> classMethods) {
		TestClass testClass = new TestClass();
		for (ITestResult testResult : classMethods.getValue()) {
			testClass.getCases().add(writer.getTestMethod(testResult));
		}
		testClass.setClassname(classMethods.getKey());
		return testClass;
	}

	protected TestSuite parseTestSuit(ISuite suite) {
		List<TestClass> testClasses = new ArrayList<TestClass>();
		List<ITestResult> listTR = writer.getTestMethods(suite);
		Map<String, List<ITestResult>> classGroupMethod = writer
				.getTestClassGroups(listTR);
		for (Map.Entry<String, List<ITestResult>> classMethods : classGroupMethod
				.entrySet()) {
			testClasses.add(parseTestClass(classMethods));
		}
		return writer.getPropertyToSuite(suite, testClasses);
	}

	protected String parseTestResultToString(List<ISuite> suites)
			throws IOException {
		TestJob testJob = new TestJob();
		List<TestSuite> testSuites = new ArrayList<TestSuite>();
		int total = 0;
		int success = 0;
		int fail = 0;
		int skip = 0;
		String startTime = null;
		String endTime = null;
		for (ISuite suit : suites) {
			TestSuite testSuite = parseTestSuit(suit);
			testSuites.add(testSuite);// Add Suit to JSON Array

			total += testSuite.getTotal();
			success += testSuite.getSuccess();
			fail += testSuite.getFail();
			skip += testSuite.getSkip();

			// get job start/end time.
			String suiteStartTime = testSuite.getStarttime();
			String suiteEndTime = testSuite.getEndtime();
			if (startTime == null || endTime == null) {
				startTime = suiteStartTime;
				endTime = suiteEndTime;
			}
			if (startTime.compareTo(suiteStartTime) > 0) {
				startTime = suiteStartTime;
			}
			if (endTime.compareTo(suiteEndTime) < 0) {
				endTime = suiteEndTime;
			}
		}
		ISuite suit = suites.get(0);

		Config config = (Config) suit
				.getAttribute(SuiteManListener.CAP_CONFIG);

		switch (config.getDriverType()) {
		case HttpClient:

			break;

		default:
			/*
			AppInfo appInfo = (AppInfo) suit
					.getAttribute(ReporterSuiteListener.APP_INFO);
			DeviceInfo deviceInfo = (DeviceInfo) suit
					.getAttribute(ReporterSuiteListener.DEVICE_INFO);

			testJob.setAppInfo(appInfo);
			testJob.setDeviceInfo(deviceInfo);
			*/
			break;
		}

		testJob.setDriverType(config.getDriverType().toString());
		testJob.setPlatform(config.getPlatformName());

		testJob.setTotal(total);
		testJob.setSuccess(success);
		testJob.setFail(fail);
		testJob.setSkip(skip);
		testJob.setStarttime(startTime);
		testJob.setEndtime(endTime);
		testJob.setSuits(testSuites);
		/*
		Map<String, Object> filesMap = CapLogger.filesMap;
		if (!filesMap.isEmpty()) {
			testJob.setFilesMap(filesMap);
		}*/

		return new GsonBuilder().setPrettyPrinting().create().toJson(testJob);
	}

	protected void generateReportFromJSONArray(List<ISuite> suites,
			String outputDirectory) throws IOException {
		String result = parseTestResultToString(suites);
		copyFiles(new File(outputDirectory, "cap-reporter"));

		FileUtils.write(new File(outputDirectory + "/cap-reporter/js/data.js"),
				"var testSuits = " + result.toString(), "UTF-8");
		writer.generateOldDirectory(outputDirectory);
	}

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		try {
			generateReportFromJSONArray(suites, outputDirectory);
		} catch (IOException e) {
			logger.warn("", e);
		}
	}

	private void copyFiles(File descDir) throws IOException {
		ClassPath classPath = ClassPath
				.from(CapReporter.class.getClassLoader());

		Set<ResourceInfo> resourceInfos = classPath.getResources();
		String dir = CapReporter.class.getPackage().getName().replace('.', '/')
				+ "/" + relativeDir;

		for (ResourceInfo info : resourceInfos) {
			String resourceName = info.getResourceName();
			if (info.getResourceName().startsWith(dir)
					&& !StringUtils.endsWith(resourceName, ".class")) {

				InputStream stream = CapReporter.class.getClassLoader()
						.getResourceAsStream(resourceName);

				FileUtils.copyInputStreamToFile(stream, new File(descDir,
						StringUtils.replace(resourceName, dir + "/", "")));
			}
		}

	}
}
