package com.gcu.fresh.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gcu.fresh.models.PurchaseModel;

public class PurchaseRowMapper implements RowMapper<PurchaseModel> {

	@Override
	public PurchaseModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new PurchaseModel(rs.getLong("purchase_number"),rs.getString("name_on_card"),rs.getString("credit_card"),rs.getString("email"),rs.getInt("total_price"),rs.getInt("table_number"));
	}

}
