package com.vapl.dialer.pojo;

public class Dashboard {
	
private int totalUsers;
private int totalCalls;
private int connectedCalls;
private int failedCalls;
private int registeredAgents;
private int activeAgents;
private Float creditsAvailable;
private Float creditsUsed;

public int getTotalUsers() {
	return totalUsers;
}
public void setTotalUsers(int totalUsers) {
	this.totalUsers = totalUsers;
}
public int getTotalCalls() {
	return totalCalls;
}
public void setTotalCalls(int totalCalls) {
	this.totalCalls = totalCalls;
}
public int getConnectedCalls() {
	return connectedCalls;
}
public void setConnectedCalls(int connectedCalls) {
	this.connectedCalls = connectedCalls;
}
public int getFailedCalls() {
	return failedCalls;
}
public void setFailedCalls(int failedCalls) {
	this.failedCalls = failedCalls;
}
public int getRegisteredAgents() {
	return registeredAgents;
}
public void setRegisteredAgents(int registeredAgents) {
	this.registeredAgents = registeredAgents;
}
public int getActiveAgents() {
	return activeAgents;
}
public void setActiveAgents(int activeAgents) {
	this.activeAgents = activeAgents;
}
public Float getCreditsAvailable() {
	return creditsAvailable;
}
public void setCreditsAvailable(Float creditsAvailable) {
	this.creditsAvailable = creditsAvailable;
}
public Float getCreditsUsed() {
	return creditsUsed;
}
public void setCreditsUsed(Float creditsUsed) {
	this.creditsUsed = creditsUsed;
}
}
