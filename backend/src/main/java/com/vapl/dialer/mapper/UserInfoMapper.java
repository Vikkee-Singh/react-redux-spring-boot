package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.UserInfo;

public class UserInfoMapper implements RowMapper<UserInfo>
{
	public UserInfo mapRow(ResultSet rs, int rowNum)  
	{
		try
		{
			UserInfo user = new UserInfo();
			user.setUserId(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			user.setName(rs.getString("name"));
			user.setNumber(rs.getString("mobile"));
//			user.setEmailid(rs.getString("email"));
//			user.setAddress(rs.getString("address"));
//			user.setPincode(rs.getInt("pincode"));
//			user.setCompany(rs.getString("company_name"));
//			user.setCredits(rs.getFloat("credits_available"));
//			user.setCreditsUsed(rs.getFloat("credits_used"));
//			user.setType(rs.getString("type"));
//			user.setParentCompany(rs.getString("parent_company"));
			return user;
		}
		catch(Exception ex)
		{
			return null;
		}
    }
}
