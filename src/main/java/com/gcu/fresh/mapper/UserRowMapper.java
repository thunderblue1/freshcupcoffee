package com.gcu.fresh.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gcu.fresh.models.UserModel;

public class UserRowMapper implements RowMapper<UserModel> {

	@Override
	public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new UserModel(rs.getLong("user_id"),rs.getString("username"),rs.getString("password"));
	}

}
