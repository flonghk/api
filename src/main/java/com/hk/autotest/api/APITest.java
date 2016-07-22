package com.hk.autotest.api;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

import com.hk.autotest.internal.suite.APIJobSetUpListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.xml.XmlTest;

//import com.ctrip.cap.CapLogger;
//import com.hk.autotest.apirun.domain.APIRun;
import com.hk.autotest.internal.data.DataMoude.DataServiceHolder;
import com.hk.autotest.domain.Context;
import com.hk.autotest.internal.suite.JobSetUpListener;
import com.hk.autotest.internal.suite.ReporterSuiteListener;
import com.hk.autotest.internal.suite.SuiteManListener;
//import com.hk.autotest.internal.test.MethodFilterChain;
import com.hk.autotest.internal.util.NetUtils;
import com.hk.autotest.lanucher.AppiumConfig.DriverType;
import com.hk.autotest.lanucher.Config;
import com.hk.autotest.reporter.CapReporter;
//import com.ctrip.cap.template.TemplateRenderer;
//import com.ctrip.cap.template.TemplateRendererBuilder;

/**
 * Currently build on Http 1.1 ,API test has many types,but Http stand in the
 * breach
 * 
 * relieves you from having to deal with connection management and resource
 * deallocation.
 * 
 * This Class doesn't hold good for all cases
 * 
 * @author 
 *
 */
@Listeners(value = { CapReporter.class, ReporterSuiteListener.class,
		APIJobSetUpListener.class })
public class APITest {
	private static final ThreadLocal<Context> contextHolder = new InheritableThreadLocal<>();
	protected static final Map<String, String> suiteParameters = new TreeMap<>(
			String.CASE_INSENSITIVE_ORDER);

	private static final Logger LOGGER = LoggerFactory.getLogger(APITest.class);

	protected static APIDriver apiDriver;

	protected APIResponse execute(APIRequest request) {
		APIRun apiRun = new APIRun();
		long current = System.currentTimeMillis();
		URI uri = request.getServiceURI();
		String requestType = uri.getScheme() + "://" + uri.getHost()
				+ uri.getPath();
		apiRun.setRequestURI(requestType);
		Long caseInfoId = contextHolder.get().getCaseInfo().getCaseInfoId();
		Long caseId = contextHolder.get().getCaseInfo().getCaseId();
		String ip = NetUtils.getLocalIp();
		apiRun.setCallerIp(ip);
		apiRun.setCaseInfoId(caseInfoId);
		apiRun.setCaseId(caseId);
		try {
			APIResponse response = apiDriver.execute(request);
			return response;
		} finally {
			long cost = System.currentTimeMillis() - current;
			apiRun.setActionTime(String.valueOf(cost));
			//DataServiceHolder.avatarServiceClient().addAPIRun(apiRun);
		}

	}

	@BeforeSuite
	public void prepareHttpExecutor() {
		apiDriver = APIDriver.newInstance();
	}

	@AfterSuite
	public void releaseExecutor() {
	}
	/*
	@BeforeSuite
	
	public void setUpLogger(ITestContext context) {
		CapLogger.setContext(contextHolder);

		Config config = new Config();
		config.setDriverType(DriverType.HttpClient);
		config.setPlatformName("PC");

		Map<String, String> ps = context.getSuite().getXmlSuite()
				.getParameters();
		suiteParameters.putAll(ps);

		context.getSuite().setAttribute(SuiteManListener.CAP_CONFIG, config);

	}

	@BeforeSuite
	protected void prepareDataCenter() {
		TemplateRenderer render = TemplateRendererBuilder
				.createDefaultTemplateRenderer();
		TemplateRenderer.provideInstance(render);
	}
*/
	@BeforeMethod(alwaysRun = true)
	public void setUpAPIContext(ITestContext testContext, Object[] parameters,
			Method method, XmlTest xmlTest) {

		LOGGER.info("before {}", method.getName());
		APIContext context = new APIContext();
		context.setTestContext(testContext);
		context.setXmlTest(xmlTest);
		context.setMethod(method);
		contextHolder.set(context);

		//MethodFilterChain.beforeMethodAPI(context);
	}

	@AfterMethod(alwaysRun = true)
	public void destroyAPIContext(ITestContext testContext,
			ITestResult testResult, Method method) {

		contextHolder.remove();

	}

	protected String getSuiteParameter(String key) {
		return suiteParameters.get(key);
	}

}
