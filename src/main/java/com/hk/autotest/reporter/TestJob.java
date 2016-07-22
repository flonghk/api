package com.hk.autotest.reporter;

import java.util.List;
import java.util.Map;

//import com.ctrip.cap.device.model.AppInfo;
//import com.ctrip.cap.device.model.DeviceInfo;


public class TestJob {
	private int success;
	private int fail;
	private int skip;
	private int total;
//	private DeviceInfo deviceInfo;
//	private AppInfo appInfo;
	private String starttime;
	private String endtime;
	private String driverType;
	private String platform;
	private List<TestSuite> suits;

	private Map<String, Object> filesMap;

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<TestSuite> getSuits() {
		return suits;
	}

	public void setSuits(List<TestSuite> suits) {
		this.suits = suits;
	}
/*
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public AppInfo getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}
*/
	public String getDriverType() {
		return driverType;
	}

	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Map<String, Object> getFilesMap() {
		return filesMap;
	}

	public void setFilesMap(Map<String, Object> filesMap) {
		this.filesMap = filesMap;
	}

}
