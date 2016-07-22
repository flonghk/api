package com.hk.autotest.api;

import java.util.Date;

public class APIRun {
	  private Long apiRunId;
	  private String userId;
	  private String requestType;
	  private String serverIp;
	  private String callerIp;
	  private String actionTime;
	  private String resultCode = "Success";
	  private Date callTime = new Date();
	  private String env;
	  private Long caseInfoId;
	  private Long caseId;
	  private Integer caseResult = Integer.valueOf(0);
	  private String requestURI;
	  
	  public Long getApiRunId()
	  {
	    return this.apiRunId;
	  }
	  
	  public void setApiRunId(Long apiRunId)
	  {
	    this.apiRunId = apiRunId;
	  }
	  
	  public String getUserId()
	  {
	    return this.userId;
	  }
	  
	  public void setUserId(String userId)
	  {
	    this.userId = userId;
	  }
	  
	  public String getRequestType()
	  {
	    return this.requestType;
	  }
	  
	  public void setRequestType(String requestType)
	  {
	    this.requestType = requestType;
	  }
	  
	  public String getServerIp()
	  {
	    return this.serverIp;
	  }
	  
	  public void setServerIp(String serverIp)
	  {
	    this.serverIp = serverIp;
	  }
	  
	  public String getCallerIp()
	  {
	    return this.callerIp;
	  }
	  
	  public void setCallerIp(String callerIp)
	  {
	    this.callerIp = callerIp;
	  }
	  
	  public String getActionTime()
	  {
	    return this.actionTime;
	  }
	  
	  public void setActionTime(String actionTime)
	  {
	    this.actionTime = actionTime;
	  }
	  
	  public String getResultCode()
	  {
	    return this.resultCode;
	  }
	  
	  public void setResultCode(String resultCode)
	  {
	    this.resultCode = resultCode;
	  }
	  
	  public Date getCallTime()
	  {
	    return this.callTime;
	  }
	  
	  public void setCallTime(Date callTime)
	  {
	    this.callTime = callTime;
	  }
	  
	  public String getEnv()
	  {
	    return this.env;
	  }
	  
	  public void setEnv(String env)
	  {
	    this.env = env;
	  }
	  
	  public Long getCaseInfoId()
	  {
	    return this.caseInfoId;
	  }
	  
	  public void setCaseInfoId(Long caseInfoId)
	  {
	    this.caseInfoId = caseInfoId;
	  }
	  
	  public Long getCaseId()
	  {
	    return this.caseId;
	  }
	  
	  public void setCaseId(Long caseId)
	  {
	    this.caseId = caseId;
	  }
	  
	  public Integer getCaseResult()
	  {
	    return this.caseResult;
	  }
	  
	  public void setCaseResult(Integer caseResult)
	  {
	    this.caseResult = caseResult;
	  }
	  
	  public String getRequestURI()
	  {
	    return this.requestURI;
	  }
	  
	  public void setRequestURI(String requestURI)
	  {
	    this.requestURI = requestURI;
	  }
	  
	  public String toString()
	  {
	    return 
	    
	      "APIRun [apiRunId=" + this.apiRunId + ", userId=" + this.userId + ", requestType=" + this.requestType + ", serverIp=" + this.serverIp + ", callerIp=" + this.callerIp + ", actionTime=" + this.actionTime + ", resultCode=" + this.resultCode + ", callTime=" + this.callTime + ", env=" + this.env + ", caseInfoId=" + this.caseInfoId + ", caseId=" + this.caseId + ", caseResult=" + this.caseResult + "]";
	  }
}
