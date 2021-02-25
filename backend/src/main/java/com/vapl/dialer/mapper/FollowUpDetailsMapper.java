package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.FollowUp;

public class FollowUpDetailsMapper implements RowMapper<FollowUp>
{
   public FollowUp mapRow(ResultSet rs, int rowNum) throws SQLException
   {
	   FollowUp flead = new FollowUp();
	   flead.setUserId(rs.getLong("user_id"));
	   flead.setCountryCode(rs.getInt("country_code"));
	   flead.setLeadNo(rs.getLong("lead_no"));
	   flead.setAgentNo(rs.getLong("agent_no"));
	   flead.setCli(rs.getLong("cli"));
	   return flead;
   }
}

