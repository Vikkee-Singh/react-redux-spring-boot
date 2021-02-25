package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.AgentLeads;

public class AgentLeadsMapper implements RowMapper<AgentLeads>
{
   public AgentLeads mapRow(ResultSet rs, int rowNum) throws SQLException
   {
	   AgentLeads leads = new AgentLeads();
	   leads.setLeadId(rs.getLong("id"));
	   leads.setLeadNo(rs.getLong("mobile"));
	   leads.setRemarks(rs.getString("remarks"));
	   leads.setCompanyName(rs.getString("company_name"));
	   leads.setCompanyWebsite(rs.getString("company_website"));
	   leads.setCity(rs.getString("city"));
	   return leads;
   }
}

