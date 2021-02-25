package com.vapl.dialer.pojo;

public class ErrorDetails {
	//private Date timestamp;
	private String message;
	//private String details;

	public ErrorDetails(String message) {
		super();
		//this.timestamp = timestamp;
		this.message = message;
		//this.details = details;
	}
	public String getMessage() {
	    return message;
	  }
}