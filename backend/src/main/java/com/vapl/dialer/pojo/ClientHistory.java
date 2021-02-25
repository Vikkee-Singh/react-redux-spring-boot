package com.vapl.dialer.pojo;

public class ClientHistory {

	
	 private Long userId;
	 private String emailId;
	 private Long leadNo;
	 private String event;
	 private String data;
	 private String reqDate;
	 private String status;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Long getLeadNo() {
		return leadNo;
	}
	public void setLeadNo(Long leadNo) {
		this.leadNo = leadNo;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
