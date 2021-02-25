package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.Dashboard;

public class DashboardDetailsMapper implements RowMapper<Dashboard>
{
   public Dashboard mapRow(ResultSet rs, int rowNum) throws SQLException
   {
	   Dashboard db = new Dashboard();
	   db.setTotalUsers(rs.getInt("totalUsers"));
	   db.setTotalCalls(rs.getInt("totalCalls"));
	   db.setConnectedCalls(rs.getInt("connectedCalls"));
	   db.setFailedCalls(rs.getInt("failedCalls"));
	   db.setRegisteredAgents(rs.getInt("registeredAgents"));
	   db.setActiveAgents(rs.getInt("activeAgents"));
	   db.setCreditsAvailable(rs.getFloat("creditsAvailable"));
	   db.setCreditsUsed(rs.getFloat("creditsUsed"));
	   return db;
   }
}

