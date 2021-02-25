
package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.Tasks;

public class tasksMapper implements RowMapper<Tasks>  {
	   public Tasks mapRow(ResultSet rs, int rowNum) throws SQLException
	   {
		   Tasks tasks = new Tasks();
		   
		   tasks.setTask_id(rs.getInt("task_id"));
		   tasks.setName(rs.getString("name"));
		   tasks.setDescription(rs.getString("description"));
		   tasks.setStatus(rs.getInt("status"));
		   tasks.setRemaining_call(rs.getInt("remaining_call"));
		   tasks.setTotal_call(rs.getInt("total_call"));
		   tasks.setUser_id(rs.getInt("user_id"));
		   tasks.setCreated_at(rs.getDate("created_at"));
		   return tasks;
	   }
}
