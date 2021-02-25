package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.ClientHistory;

public class HistoryDetailsMapper implements RowMapper<ClientHistory>
{
   public ClientHistory mapRow(ResultSet rs, int rowNum) throws SQLException
   {
	   ClientHistory ch = new ClientHistory();
	   
	   ch.setEvent(rs.getString("event"));
	   ch.setData(rs.getString("data"));
	   ch.setLeadNo(rs.getLong("mobile"));
	   ch.setEmailId(rs.getString("email_id"));
	   ch.setReqDate(rs.getString("req_date"));
	   ch.setStatus(rs.getString("status"));
	   
	   return ch;
   }
}

