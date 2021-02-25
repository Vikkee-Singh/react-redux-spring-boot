package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.Agents;

public class AgentDetailsMapper implements RowMapper<Agents>
{
   public Agents mapRow(ResultSet rs, int rowNum) throws SQLException
   {
	   Agents agents = new Agents();
	   agents.setAgentId(rs.getInt("agent_id"));
	   agents.setAgentName(rs.getString("agent_name"));
	   agents.setAgentNumber(rs.getString("phone_no"));
	   //agents.setLanguage(rs.getString("language_code"));
	   agents.setAgentStatus(rs.getInt("status"));
	   //agents.setAllocationTime(rs.getString("last_allocated"));
	   /*if(agents.getAgentStatus().equals("1"))
	   {
		   agents.setAgentStatus("");
	   }
	   else
	   {
		   agents.setAgentStatus("Disabled");
	   }*/
	   
	   return agents;
   }
}

