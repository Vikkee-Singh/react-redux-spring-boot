package com.vapl.dialer.pojo;

import java.sql.Date;

public class Tasks {
	
	private Integer task_id;
	private String name;
	private String description;
	private Integer status;
	private Integer remaining_call;
	private Integer total_call;
	private Integer user_id;
	private Date created_at;
	
	public Tasks() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Tasks(Integer task_id, String name, String description, Integer status, Integer remaining_call,
			Integer total_call, Integer user_id, Date created_at) {
		super();
		this.task_id = task_id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.remaining_call = remaining_call;
		this.total_call = total_call;
		this.user_id = user_id;
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Tasks [task_id=" + task_id + ", name=" + name + ", description=" + description + ", status=" + status
				+ ", remaining_call=" + remaining_call + ", total_call=" + total_call + ", user_id=" + user_id
				+ ", created_at=" + created_at + "]";
	}

	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRemaining_call() {
		return remaining_call;
	}
	public void setRemaining_call(Integer remaining_call) {
		this.remaining_call = remaining_call;
	}
	public Integer getTotal_call() {
		return total_call;
	}
	public void setTotal_call(Integer total_call) {
		this.total_call = total_call;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
}
