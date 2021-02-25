package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.UserInfo;

public class UserMapper implements RowMapper<UserInfo>
{
	public UserInfo mapRow(ResultSet rs, int rowNum)  
	{
		try
		{
			UserInfo user = new UserInfo();
			user.setUsername(rs.getString("name"));
			user.setPassword(rs.getString("pass"));
			user.setUserId(rs.getInt("userid"));
			user.setRole(rs.getString("role"));
			return user;
		}
		catch(Exception ex)
		{
			return null;
		}
    }
}
