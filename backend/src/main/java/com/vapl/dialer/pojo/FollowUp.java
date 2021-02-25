package com.vapl.dialer.pojo;

public class FollowUp {

	 private Long leadId;
	 private String widgetId;
	 private Long userId;
	 private String key;
	 private Integer countryCode;
	 private Long leadNo;
	 private Long agentNo;
	 private String reqTime;
	 private String scheduleTime;
	 private String initTime;
	 private String callUID;
	 private String callSID;
	 private Integer agentStatus;
	 private Integer leadStatus;
	 private Integer status;
	 private Integer attemptCount;
	 private Integer pickId;
	 private String type;
	 private long cli;

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public Integer getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}

	public Long getLeadNo() {
		return leadNo;
	}

	public void setLeadNo(Long leadNo) {
		this.leadNo = leadNo;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public String getInitTime() {
		return initTime;
	}

	public void setInitTime(String initTime) {
		this.initTime = initTime;
	}

	public String getCallUID() {
		return callUID;
	}

	public void setCallUID(String callUID) {
		this.callUID = callUID;
	}

	public Integer getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(Integer agentStatus) {
		this.agentStatus = agentStatus;
	}

	public Integer getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(Integer leadStatus) {
		this.leadStatus = leadStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAttemptCount() {
		return attemptCount;
	}

	public void setAttemptCount(Integer attemptCount) {
		this.attemptCount = attemptCount;
	}

	public Integer getPickId() {
		return pickId;
	}

	public void setPickId(Integer pickId) {
		this.pickId = pickId;
	}

	public String getCallSID() {
		return callSID;
	}

	public void setCallSID(String callSID) {
		this.callSID = callSID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Long getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(Long agentNo) {
		this.agentNo = agentNo;
	}

	public long getCli() {
		return cli;
	}

	public void setCli(long cli) {
		this.cli = cli;
	}
}
