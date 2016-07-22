package com.hk.autotest.reporter;
import java.util.List;
public class TestMethod {
	private String casename;
	private int result;
	private List<TestMethodLog> log;
	private String exception;
	private String starttime;
	private String endtime;

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

	public String getCasename() {
		return casename;
	}

	public void setCasename(String casename) {
		this.casename = casename;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public List<TestMethodLog> getLog() {
		return log;
	}

	public void setLog(List<TestMethodLog> log) {
		this.log = log;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
}
