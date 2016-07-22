package com.hk.autotest.reporter;
import java.util.ArrayList;
import java.util.List;
public class TestClass {
	private String classname;
	private List<TestMethod> cases = new ArrayList<TestMethod>();

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public List<TestMethod> getCases() {
		return cases;
	}

	public void setCases(List<TestMethod> testclass) {
		this.cases = testclass;
	}
}
