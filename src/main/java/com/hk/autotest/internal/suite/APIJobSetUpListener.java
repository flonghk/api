package com.hk.autotest.internal.suite;

public class APIJobSetUpListener  extends JobSetUpListener {
    @Override
    public void initParams() {
        super.initParams();
        jobInfo.setBrowerName(null);
        jobInfo.setRunType("API");
    }

}
