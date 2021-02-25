package com.vapl.dialer.pojo;

import java.util.Set;

public class TokenResponse<T> {

	private String message;
    private String token;
    private String userid;
    private Set<String> role;

    /*public ApiResponse(int status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }*/
    
    public TokenResponse(String message,String token, String userid, Set<String> role) {
        this.message = message;
        this.token = token;
        this.userid= userid;
        this.role = role;
    }
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
}
