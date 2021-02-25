package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.UserDetails;

public class UserDetailsMapper implements RowMapper<UserDetails> {
	public UserDetails mapRow(ResultSet rs, int rowNum) {
		try {
			UserDetails user = new UserDetails();
			user.setUserId(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			user.setName(rs.getString("name"));
			user.setMobile(rs.getString("mobile"));
			user.setEmail(rs.getString("email"));
			user.setAddress(rs.getString("address"));
			user.setPincode(rs.getInt("pincode"));
			user.setCompanyName(rs.getString("company_name"));
			user.setCreditsAvailable(rs.getFloat("credits_available"));
			user.setCreditsUsed(rs.getFloat("credits_used"));
			user.setType(rs.getString("type"));
			user.setParentCompany(rs.getString("parent_company"));
			return user;
		} catch (Exception ex) {
			return null;
		}
	}
}
