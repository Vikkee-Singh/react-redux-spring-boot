package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.DID;

public class DID_WidgetMapper implements RowMapper<DID> 
{
   public DID mapRow(ResultSet rs, int rowNum) throws SQLException
   {
	   DID did = new DID();
	   //phonebook.setId(rs.getInt("id"));
	   did.setId(rs.getInt("id"));
	   did.setDid(rs.getInt("did"));
	  
	   return did;
   }
}

