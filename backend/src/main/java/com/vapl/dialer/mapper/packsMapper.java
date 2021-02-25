
package com.vapl.dialer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vapl.dialer.pojo.Packs;

public class packsMapper implements RowMapper<Packs> {
	public Packs mapRow(ResultSet rs, int rowNum) throws SQLException {
		Packs packs = new Packs();

		packs.setId(rs.getInt("id"));
		packs.setPackId(rs.getInt("pack_id"));
		packs.setPackName(rs.getString("pack_name"));
		packs.setPackPrice(rs.getInt("pack_price"));
		packs.setCredits(rs.getInt("credits"));
		packs.setAgents(rs.getInt("agents"));
		packs.setDomains(rs.getInt("domains"));
		packs.setValidity(rs.getString("validity"));

		return packs;
	}
}
