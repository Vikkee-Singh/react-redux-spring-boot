package com.vapl.dialer.pojo;

import java.sql.Date;

public class DNC {
	private Integer dnc_id;
	private String email;
	private String mobile;
	private Boolean flag;
	private Integer user_id;
	private Date created_at;
	private String remark;
	private String company_name;

	public DNC() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DNC [dnc_id=" + dnc_id + ", email=" + email + ", mobile=" + mobile + ", flag=" + flag + ", user_id="
				+ user_id + ", created_at=" + created_at + ", remark=" + remark + ", company_name=" + company_name
				+ "]";
	}
	public DNC(Integer dnc_id, String email, String mobile, Boolean flag, Integer user_id, Date created_at,
			String remark, String company_name) {
		super();
		this.dnc_id = dnc_id;
		this.email = email;
		this.mobile = mobile;
		this.flag = flag;
		this.user_id = user_id;
		this.created_at = created_at;
		this.remark = remark;
		this.company_name = company_name;
	}
	public Integer getdnc_id() {
		return dnc_id;
	}
	public void setdnc_id(Integer dnc_id) {
		this.dnc_id = dnc_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
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
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
