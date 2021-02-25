package com.vapl.dialer.pojo;

import java.util.Set;

public class AuthToken {

    private String token;
    private String userid;
    private Set<String> role;

    public AuthToken(){

    }

    public AuthToken(String token, String userid, Set<String> role){
        this.token = token;
        this.userid = userid;
        this.role = role;
    }

    public AuthToken(String token){
        this.token = token;
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
