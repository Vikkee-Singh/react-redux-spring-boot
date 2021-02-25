package com.vapl.dialer.pojo;

public class Leads {

	 private Long leadId;
	 
	 private Long widgetId;
	 private Long userId;
	 private String key;
	 private Integer countryCode;
	 private Long leadNo;
	 private String reqTime;
	 private String scheduleTime;
	 private String attemptTime;
	 private String initTime;
	 private String callUID;
	 private String callSID;
	 private Integer agentStatus;
	 private Integer leadStatus;
	 private Integer status;
	 private Integer attemptCount;
	 private Integer pickId;
	 private String type;

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

	public Long getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(Long widgetId) {
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
	public String getAttemptTime() {
		return attemptTime;
	}

	public void setAttemptTime(String attemptTime) {
		this.attemptTime = attemptTime;
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
}
