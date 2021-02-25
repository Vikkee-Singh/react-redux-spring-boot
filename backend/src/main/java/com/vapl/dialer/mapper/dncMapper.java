package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.DNC;

public class dncMapper implements RowMapper<DNC>  {
	   public DNC mapRow(ResultSet rs, int rowNum) throws SQLException
	   {
		   DNC dnc = new DNC();
		   dnc.setdnc_id(rs.getInt("dnc_id"));
		   dnc.setEmail(rs.getString("email"));
		   dnc.setMobile(rs.getString("mobile"));
		   dnc.setFlag(rs.getBoolean("flag"));
		   dnc.setUser_id(rs.getInt("user_id"));
		   dnc.setCreated_at(rs.getDate("created_at"));
		   dnc.setRemark(rs.getString("remark"));
		   dnc.setCompany_name(rs.getString("company_name"));
		   return dnc;
	   }
}
