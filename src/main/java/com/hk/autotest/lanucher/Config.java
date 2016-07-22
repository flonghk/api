package com.hk.autotest.lanucher;
import com.hk.autotest.lanucher.AppiumConfig.DriverType;

public class Config {
	protected DriverType driverType = DriverType.UIAutomator;
	protected String platformName;

	public Config() {
		super();
	}

	public DriverType getDriverType() {
		return driverType;
	}

	public void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
}
