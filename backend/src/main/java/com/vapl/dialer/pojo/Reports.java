package com.vapl.dialer.pojo;

public class Reports {
	private Integer attemptId;
	private String callID;
	private String uuID;
	private String startTime;
	private String ansTime;
	private String endTime;
	private String agentName;
	private String customerNo;
	private Integer unlockStatus;
	private String agentNo;
	private String status;
	private Float credits;
	private Integer leadTalkDuration;
	private String remarks;

	public Integer getAttemptId() {
		return attemptId;
	}
	public void setAttemptId(Integer attemptId) {
		this.attemptId = attemptId;
	}
	public String getCallID() {
		return callID;
	}
	public void setCallID(String callID) {
		this.callID = callID;
	}
	public String getUuID() {
		return uuID;
	}
	public void setUuID(String uuID) {
		this.uuID = uuID;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getAnsTime() {
		return ansTime;
	}
	public void setAnsTime(String ansTime) {
		this.ansTime = ansTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Float getCredits() {
		return credits;
	}
	public void setCredits(Float credits) {
		this.credits = credits;
	}
	public Integer getUnlockStatus() {
		return unlockStatus;
	}
	public void setUnlockStatus(Integer unlockStatus) {
		this.unlockStatus = unlockStatus;
	}
	public Integer getLeadTalkDuration() {
		return leadTalkDuration;
	}
	public void setLeadTalkDuration(Integer leadTalkDuration) {
		this.leadTalkDuration = leadTalkDuration;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
