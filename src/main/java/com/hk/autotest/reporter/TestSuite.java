package com.hk.autotest.reporter;
import java.util.ArrayList;
import java.util.List;
public class TestSuite {
	private String suitname;
	private int success;
	private int fail;
	private int skip;
	private int total;
	private String starttime;
	private String endtime;
	private List<TestClass> testclass = new ArrayList<TestClass>();

	public String getSuitname() {
		return suitname;
	}

	public void setSuitname(String suitname) {
		this.suitname = suitname;
	}

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

	public List<TestClass> getTestclass() {
		return testclass;
	}

	public void setTestclass(List<TestClass> testclass) {
		this.testclass = testclass;
	}
}
