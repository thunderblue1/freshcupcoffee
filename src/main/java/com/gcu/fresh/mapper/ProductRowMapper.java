package com.gcu.fresh.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gcu.fresh.models.ProductModel;

public class ProductRowMapper implements RowMapper<ProductModel> {

	@Override
	public ProductModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ProductModel(rs.getLong("item_number"),rs.getString("name"),rs.getString("description"),rs.getInt("price"),rs.getString("photo"));
	}

}
