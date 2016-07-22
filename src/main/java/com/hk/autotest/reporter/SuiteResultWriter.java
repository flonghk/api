package com.hk.autotest.reporter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

//import com.ctrip.cap.CapLogger;
import com.hk.autotest.common.NestedExceptionUtils;
import com.hk.autotest.dataprovider.DataParameter;
import com.hk.autotest.internal.suite.ReporterSuiteListener;

//import junit.framework.TestSuite;

public class SuiteResultWriter {

	private static final Logger logger = LoggerFactory
			.getLogger(SuiteResultWriter.class);

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final FastDateFormat dateFormat = FastDateFormat
			.getInstance(DATE_FORMAT);
	private static final int SKIP = ITestResult.SKIP;
	private static final int PASS = ITestResult.SUCCESS;
	private static final int FAIL = ITestResult.FAILURE;

	public static final FastDateFormat logDirectoryName = FastDateFormat
			.getInstance("yyyy-MM-dd-HH-mm-ss");
	public static final String OLD_LOG_DIRECTORY = "old-cap-logger";
	public static final String REPORTER_DIRECTORY = "cap-reporter";
	public static final String REPORTER_IMAGES = "images";

	public TestSuite getPropertyToSuite(ISuite suite,
			List<TestClass> jsaTestClasses) {
		int passed = 0;
		int failed = 0;
		int skipped = 0;
		for (ISuiteResult sr : suite.getResults().values()) {
			ITestContext testContext = sr.getTestContext();
			passed += testContext.getPassedTests().size();
			failed += testContext.getFailedTests().size();
			skipped += testContext.getSkippedTests().size();
		}
		Date start = (Date) suite
				.getAttribute(ReporterSuiteListener.SUITE_START_DATE);
		Date end = (Date) suite
				.getAttribute(ReporterSuiteListener.SUITE_END_DATE);

		TestSuite testSuite = new TestSuite();
		testSuite.setSuitname(suite.getName());
		testSuite.setSuccess(passed);
		testSuite.setFail(failed);
		testSuite.setSkip(skipped);
		testSuite.setTotal(passed + failed + skipped);
		testSuite.setStarttime(dateFormat.format(start));
		testSuite.setEndtime(dateFormat.format(end));
		testSuite.setTestclass(jsaTestClasses); // Add Cases to Suite

		return testSuite;
	}

	public List<ITestResult> getTestMethods(ISuite suite) {
		List<ITestResult> allResults = new ArrayList<ITestResult>();
		Map<String, ISuiteResult> suiteResults = suite.getResults();
		for (Map.Entry<String, ISuiteResult> suiteResult : suiteResults
				.entrySet()) {
			ISuiteResult sr = suiteResult.getValue();
			allResults.addAll(sr.getTestContext().getPassedTests()
					.getAllResults());
			allResults.addAll(sr.getTestContext().getFailedTests()
					.getAllResults());
			allResults.addAll(sr.getTestContext().getSkippedTests()
					.getAllResults());
		}
		return allResults;
	}

	public TestMethod getTestMethod(ITestResult result) {
		TestMethod testCase = new TestMethod();

		Throwable error = result.getThrowable();
		if (error != null) {
			String eroMsg = NestedExceptionUtils.buildStackTrace(error);
			testCase.setException(eroMsg);
		} else {
			testCase.setException("");
		}

		String suffix = "";
		if (result.getParameters() != null && result.getParameters().length > 0) {
			for (Object o : result.getParameters()) {
				if (o instanceof DataParameter) {
					//suffix += ((DataParameter) o).title();
				}
			}
		}
		String caseName = StringUtils.isNotEmpty(suffix) ? result.getName()
				+ "-" + suffix : result.getName();
		testCase.setCasename(caseName);

		testCase.setResult(result.getStatus());
		testCase.setStarttime(dateFormat.format(new Date(result
				.getStartMillis())));
		testCase.setEndtime(dateFormat.format(new Date(result.getEndMillis())));
		//testCase.setLog(parseReportLog(result));

		return testCase;
	}
/*
	private List<TestMethodLog> parseReportLog(ITestResult testMethodResult) {
		List<TestMethodLog> logs = new ArrayList<TestMethodLog>();
		List<String> logOutput = Reporter.getOutput(testMethodResult);
		for (String line : logOutput) {
			TestMethodLog tml = new TestMethodLog();
			String[] log = line.split(CapLogger.SplitChar);
			if (log.length != 3) {
				continue;
			}
			tml.setType(log[0]);
			tml.setMessage(log[1]);
			tml.setTime(log[2]);
			logs.add(tml);
		}
		Set<ITestResult> allMethodResult = testMethodResult.getTestContext()
				.getPassedConfigurations().getAllResults();
		for (ITestResult result : allMethodResult) {
			List<String> confiMethodLog = Reporter.getOutput(result);
			for (String line : confiMethodLog) {
				String[] log = line.split(CapLogger.SplitChar);
				if (log.length != 3) {
					continue;
				}
				if (log[1].contains(CapLogger.CaptureChar)) {
					String[] detail = log[1].split(CapLogger.CaptureChar);
					if (detail[1].equals(testMethodResult.getTestClass()
							.getName() + "." + testMethodResult.getName())) {
						TestMethodLog tml = new TestMethodLog();
						tml.setType(log[0]);
						tml.setMessage(detail[0]);
						tml.setTime(log[2]);
						logs.add(tml);
					}
				}
			}
		}
		return logs;
	}
*/
	public Map<String, List<ITestResult>> getTestClassGroups(
			List<ITestResult> testResults) {
		Map<String, List<ITestResult>> map = Maps.newHashMap();
		for (ITestResult result : testResults) {
			String className = result.getTestClass().getName();
			List<ITestResult> list = map.get(className);
			if (list == null) {
				list = Lists.newArrayList();
				map.put(className, list);
			}
			list.add(result);
		}
		return map;
	}

	public int getTestResult(ITestContext context, int label) {
		switch (label) {
		case SuiteResultWriter.PASS:
			return context.getPassedTests().size();
		case SuiteResultWriter.FAIL:
			return context.getFailedTests().size();
		case SuiteResultWriter.SKIP:
			return context.getSkippedTests().size();
		default:
			return -1;
		}
	}

	public int getTestResults(Collection<ISuiteResult> results, int label) {
		int num = 0;
		for (ISuiteResult result : results) {
			num += getTestResult(result.getTestContext(), label);
		}
		return num;
	}

	public void generateOldDirectory(String outputDirectory) {
		try {
			File oldDirectory = FileUtils.getFile(outputDirectory
					+ File.separator + OLD_LOG_DIRECTORY);
			if (!oldDirectory.exists()) {
				oldDirectory.mkdirs();
			}
			File oldReporterDirectory = new File(oldDirectory.getAbsolutePath()
					+ File.separator
					+ logDirectoryName.format(new Date()).toString());
			if (oldReporterDirectory.exists()) {
				FileUtils.forceDelete(oldReporterDirectory);
			}
			FileUtils.copyDirectory(new File(outputDirectory + File.separator
					+ REPORTER_DIRECTORY), oldReporterDirectory);
		} catch (IOException e) {
			logger.warn("", e);
		}
	}
}
