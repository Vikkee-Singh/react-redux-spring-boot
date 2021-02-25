package com.vapl.dialer.pojo;

public class UserDetails {
    public Integer user_id;
    public String username;
    public String name;
    public String mobile;
    public String email;
    public String address;
    public Integer pincode;
    public String company_name;
    public Float credits_available;
    public Float credits_used;
    public String type;
    public String parent_company;

    public String getParentCompany() {
        return parent_company;
    }

    public void setParentCompany(String parent_company) {
        this.parent_company = parent_company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getCreditsUsed() {
        return credits_used;
    }

    public void setCreditsUsed(Float credits_used) {
        this.credits_used = credits_used;
    }

    public Float getCreditsAvailable() {
        return credits_available;
    }

    public void setCreditsAvailable(Float credits_available) {
        this.credits_available = credits_available;
    }

    public String getCompanyName() {
        return company_name;
    }

    public void setCompanyName(String company_name) {
        this.company_name = company_name;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }
}
