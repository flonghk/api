package com.hk.autotest.lanucher;

import static com.hk.autotest.lanucher.AppiumConfig.DriverType.Selendroid;
import static com.hk.autotest.lanucher.AppiumConfig.DriverType.UIAutomator;

import io.appium.java_client.remote.MobilePlatform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.hk.autotest.exception.CapException;
import com.hk.autotest.exception.AppiumParameterException;

/**
 * Context parameter to appium Capability
 * https://github.com/appium/appium/blob/master
 * /docs/en/writing-running-appium/caps.md
 *
 * @author 
 */

public class AppiumConfig extends Config {
	// appium parameter

    // public static final String autoLaunch = "autoLaunch";
    // public static final String autoWebview = "autoWebview";
    // public static final String noReset = "noReset";
    // public static final String fullReset = "fullReset";
    // // appium android
    // public static final String deviceReadyTimeout = "deviceReadyTimeout";
    // public static final String androidCoverage = "androidCoverage";
    // public static final String enablePerformanceLogging =
    // "enablePerformanceLogging";
    // public static final String androidDeviceReadyTimeout =
    // "androidDeviceReadyTimeout";
    // public static final String androidDeviceSocket = "androidDeviceSocket";
    // public static final String avd = "avd";
    // public static final String avdLaunchTimeout = "avdLaunchTimeout";
    // public static final String avdReadyTimeout = "avdReadyTimeout";
    // public static final String avdArgs = "avdArgs";
    // public static final String chromedriverExecutable =
    // "chromedriverExecutable";
    // public static final String autoWebviewTimeout = "autoWebviewTimeout";
    // public static final String intentAction = "intentAction";
    // public static final String intentCategory = "intentCategory";
    // public static final String intentFlags = "intentFlags";
    // public static final String optionalIntentArguments =
    // "optionalIntentArguments";
    // public static final String unicodeKeyboard = "unicodeKeyboard";
    // public static final String resetKeyboard = "resetKeyboard";
    // public static final String noSign = "noSign";
    // public static final String ignoreUnimportantViews =
    // "ignoreUnimportantViews";
    // appium ios
    // public static final String calendarFormat = "calendarFormat";

    private String automationName = "Appium";
    private String platformVersion; // OS
    private String deviceName; //

    // @NotBlank(groups = { DriverType.UIAutomation, DriverType.UIAutomation,
    // DriverType.Selendroid }, message = "App path cann't be empty")
    private String app;
    private String browserName;
    private String udid;

    private String autoWebview;
    private Long autoWebviewTimeout;

    private boolean noReset;
    private boolean fullReset = true;
    private Integer newCommandTimeout;

    /**
     * android
     */
    private String appActivity;
    private String appPackage;
    private String appWaitActivity;
    private String appWaitPackage;

    private boolean unicodeKeyboard;
    private boolean resetKeyboard;

    /**
     *
     */
    private boolean noSign;
    private boolean useKeystore;
    private String keystorePath;
    private String keystorePassword;
    private String keyAlias;
    private String keyPassword;

    /**
     * ios
     */
    private String bundleId;

    private transient String appFileName;

    private boolean caseLevelRestart = false;

    private static final Set<String> transientKeys = new TreeSet<String>(
            Arrays.asList("transientKeys", "class", "appFileName", "driverType", "caseLevelRestart"));

    /**
     * 转化suiteparameter 必要校验
     *
     * @param context
     */
    public AppiumConfig(Map<String, String> context) {
        BeanMap beanMap = new BeanMap(this);
        for (String k : context.keySet()) {
            String v = context.get(k);
            if (StringUtils.isNotBlank(v)) {
                beanMap.put(k, v);
            }
        }
    }

    public void validate() {
        if (StringUtils.isBlank(platformName)) {
            throw new AppiumParameterException(String.format(
                    "platformName Cann't be Null,may be %s,%s",
                    MobilePlatform.ANDROID, MobilePlatform.IOS));
        }

        switch (platformName.toLowerCase()) {
            case "android":
                if (StringUtils.isNotBlank(browserName)) {
                    this.driverType = DriverType.ChromeDriver;
                } else if ("Selendroid".equalsIgnoreCase(automationName)) {
                    this.driverType = DriverType.Selendroid;
                }

                if (StringUtils.isBlank(deviceName)) {
                    deviceName = "Android Phone";
                }

                break;
            case "ios":
                if (StringUtils.isNotBlank(browserName)) {
                    this.driverType = DriverType.IOSBrower;
                } else {
                    this.driverType = DriverType.IOSApp;
                    this.fullReset = false;
                }

                break;
            default:
                break;
        }

        if (this.driverType == Selendroid || this.driverType == UIAutomator) {
            if (StringUtils.isBlank(app)) {
                throw new AppiumParameterException("app path can't be null");
            }

        }

    }

    public Capabilities toCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        BeanMap beanMap = new BeanMap(this);
        for (Object key : beanMap.keySet()) {
            if (transientKeys.contains(key)) {
                continue;
            }
            if (beanMap.get(key) == null) {
                continue;
            }
            String key2 = key.toString();
            capabilities.setCapability(key2, beanMap.get(key));
        }
        return capabilities;
    }

    public String getAutomationName() {
        return automationName;
    }

    public void setAutomationName(String automationName) {
        this.automationName = automationName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getAutoWebview() {
        return autoWebview;
    }

    public void setAutoWebview(String autoWebview) {
        this.autoWebview = autoWebview;
    }

    public boolean isNoReset() {
        return noReset;
    }

    public void setNoReset(boolean noReset) {
        this.noReset = noReset;
    }

    public boolean isFullReset() {
        return fullReset;
    }

    public void setFullReset(boolean fullReset) {
        this.fullReset = fullReset;
    }

    public String getAppActivity() {
        return appActivity;
    }

    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppWaitActivity() {
        return appWaitActivity;
    }

    public void setAppWaitActivity(String appWaitActivity) {
        this.appWaitActivity = appWaitActivity;
    }

    public String getAppWaitPackage() {
        return appWaitPackage;
    }

    public void setAppWaitPackage(String appWaitPackage) {
        this.appWaitPackage = appWaitPackage;
    }

    public boolean isUnicodeKeyboard() {
        return unicodeKeyboard;
    }

    public void setUnicodeKeyboard(boolean unicodeKeyboard) {
        this.unicodeKeyboard = unicodeKeyboard;
    }

    public boolean isResetKeyboard() {
        return resetKeyboard;
    }

    public void setResetKeyboard(boolean resetKeyboard) {
        this.resetKeyboard = resetKeyboard;
    }

    public boolean isNoSign() {
        return noSign;
    }

    public void setNoSign(boolean noSign) {
        this.noSign = noSign;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public String getAppFileName() {
        return appFileName;
    }

    public void setAppFileName(String appFileName) {
        this.appFileName = appFileName;
    }

    public boolean isUseKeystore() {
        return useKeystore;
    }

    public void setUseKeystore(boolean useKeystore) {
        this.useKeystore = useKeystore;
    }

    public Integer getNewCommandTimeout() {
        return newCommandTimeout;
    }

    public void setNewCommandTimeout(Integer newCommandTimeout) {
        this.newCommandTimeout = newCommandTimeout;
    }

    public Long getAutoWebviewTimeout() {
        return autoWebviewTimeout;
    }

    public void setAutoWebviewTimeout(Long autoWebviewTimeout) {
        this.autoWebviewTimeout = autoWebviewTimeout;
    }

    public boolean isCaseLevelRestart() {
        return caseLevelRestart;
    }

    public void setCaseLevelRestart(boolean caseLevelRestart) {
        this.caseLevelRestart = caseLevelRestart;
    }

    public AppiumConfig clone() {
        AppiumConfig config = new AppiumConfig(new HashMap<String, String>());
        try {
            BeanUtils.copyProperties(config, this);
        } catch (Exception e) {
            throw new CapException("Appium config copy ", e);
        }
        return config;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public enum DriverType {
        /**
         * Android uiautomator
         */
        UIAutomator,
        /**
         * uiautomator > android api 17(4.2)
         */
        Selendroid,
        /**
         * android browser,chrome,chromebeta
         */
        ChromeDriver,
        /**
         * ios APP (uiautomation & ios_webkit_debug_proxy)
         */
        IOSApp,
        /**
         * ios Brower (ios_webkit_debug_proxy)
         */
        IOSBrower,

        /**
         * API
         */
        HttpClient,

        /**
         * extension need to Modify code XX
         */
        Benchmark;

        public static boolean isApp(DriverType t) {
            return !isH5(t);
        }

        public static boolean isH5(DriverType t) {
            return t == IOSBrower || t == ChromeDriver;
        }

        public boolean isApp() {
            return DriverType.isApp(this);
        }

        public boolean isH5() {
            return DriverType.isH5(this);
        }

        public boolean isAndroidPlatform() {
            return Arrays.asList(UIAutomator, Selendroid, ChromeDriver)
                    .contains(this);
        }

        public boolean isIOSPlatform() {
            return Arrays.asList(IOSApp, IOSBrower).contains(this);
        }

    }

}
