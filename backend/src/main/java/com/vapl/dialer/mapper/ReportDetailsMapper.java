package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.Reports;
import com.vapl.dialer.util.Util;

public class ReportDetailsMapper implements RowMapper<Reports>
{
	public Reports mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Reports reports = new Reports();
		reports.setAttemptId(rs.getInt("attempt_id"));
		reports.setCallID(rs.getString("call_sid"));
		reports.setUuID(rs.getString("uuid"));
		//reports.setStartTime(rs.getString("lead_init_time"));
		String endTime = rs.getString("lead_end_time");
		String ansTime = rs.getString("lead_ans_time");
		if(endTime !=null)
		{
			try {
				reports.setEndTime(Util.changeDateTime(endTime, "Asia/Kolkata"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if(ansTime !=null)
		{
			try {
				reports.setAnsTime(Util.changeDateTime(ansTime, "Asia/Kolkata"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		reports.setLeadTalkDuration(rs.getInt("lead_talk_duration"));
		reports.setCustomerNo(rs.getString("lead_no"));
		reports.setAgentNo(rs.getString("agent_no"));
		reports.setRemarks(rs.getString("remarks"));
		//reports.setUnlockStatus(rs.getInt("unlock_status"));
		/* if(reports.getAnsTime()!=null)
	   {
		   reports.setStatus("Success");
	   }
	   else
	   {
		   reports.setStatus("Fail");
	   }*/
		return reports;
	}
}

