package com.gcu.fresh.data;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gcu.fresh.mapper.ProductRowMapper;
import com.gcu.fresh.models.ProductModel;
import com.gcu.fresh.repository.ProductRepository;


@Service
public class ProductDAO implements ProductAccessInterface<ProductModel> {

	@Autowired
	JdbcTemplate jtemp;
	
	@Autowired
	ProductRepository prodRepo;
	
	@Override
	public ProductModel findProduct(Long id) {
		Optional<ProductModel> gotten = prodRepo.findById(id);
		if(gotten.isPresent()) {
			return gotten.get();
		} else {
			return null;	
		}
	}

	@Override
	public List<ProductModel> findProductsAsList(String searchcriteria) {
		String sql = "SELECT * FROM item WHERE LOWER(name) LIKE '%"+searchcriteria.toLowerCase()+"%' || LOWER(description) LIKE '%"+searchcriteria.toLowerCase()+"%'|| LOWER(price) LIKE '%"+searchcriteria.toLowerCase()+"%'";
		List<ProductModel> found = jtemp.query(sql, new ProductRowMapper());
		return found;
	}

	@Override
	public List<ProductModel> getProducts() {
		List<ProductModel> gotten = (List<ProductModel>)prodRepo.findAll();
		return gotten;
	}

	@Override
	public void createProduct(ProductModel obj) {
		try {
			prodRepo.save(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(ProductModel obj) {
		try {
			prodRepo.delete(obj);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
