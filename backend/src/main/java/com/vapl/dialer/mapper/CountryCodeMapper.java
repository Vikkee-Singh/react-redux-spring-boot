package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.CountryCode;

public class CountryCodeMapper implements RowMapper<CountryCode> 
{
   public CountryCode mapRow(ResultSet rs, int rowNum) throws SQLException
   {
	  CountryCode cc = new CountryCode();
	  cc.setCallingCode(rs.getInt("calling_code")); 
	  cc.setCountryCode(rs.getString("country_code"));
	  cc.setCountry(rs.getString("country"));
	  cc.setMobile(rs.getInt("mobile"));
	  cc.setCredits(rs.getInt("credits"));
	  
	  return cc;
   }
}

