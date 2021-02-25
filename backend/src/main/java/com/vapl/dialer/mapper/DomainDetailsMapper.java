package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.UserInfo;

public class DomainDetailsMapper implements RowMapper<UserInfo>
{
	public UserInfo mapRow(ResultSet rs, int rowNum)  
	{
		try
		{
			UserInfo user = new UserInfo();
//			user.setLogo(rs.getString("logo"));
//			user.setCompany(rs.getString("company_name"));
			return user;
		}
		catch(Exception ex)
		{
			return null;
		}
    }
}
