package com.gcu.fresh.data;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gcu.fresh.mapper.ProductRowMapper;
import com.gcu.fresh.models.ProductModel;
import com.gcu.fresh.repository.ProductRepository;

import utility.MessUtil;


@Service
public class ProductDAO implements ProductAccessInterface<ProductModel> {

	public static Logger logger = LoggerFactory.getLogger(ProductDAO.class);
	
	@Autowired
	JdbcTemplate jtemp;
	
	@Autowired
	ProductRepository prodRepo;
	
	//Get a product as a ProductModel from database based on the id
	@Override
	public ProductModel findProduct(Long id) {
		logger.info(MessUtil.enter("findProduct"));
		try {
			Optional<ProductModel> gotten = prodRepo.findById(id);
			//If we found the product by id then return it
			if(gotten.isPresent()) {
				logger.info(MessUtil.exit("findProduct"));
				return gotten.get();
			} else {
				logger.info(MessUtil.exit("findProduct"));
				return null;	
			}	
		} catch(Exception e) {
			logger.error(MessUtil.errorLevel("findProduct", "find by id from prodRepo"));
		}
		return null;
	}
	
	//Get a list of products matching the search criteria
	@Override
	public List<ProductModel> findProductsAsList(String searchcriteria) {
		logger.info(MessUtil.enter("findProductAsList"));
		String sql = "SELECT * FROM item WHERE LOWER(name) LIKE ? || LOWER(description) LIKE ? || LOWER(price) LIKE ?";
		String searchParam = '%'+searchcriteria.toLowerCase()+'%';
		try {
			//If we find products as list using query then return them
			List<ProductModel> found = jtemp.query(sql, new ProductRowMapper(),searchParam, searchParam, searchParam);	
			logger.info(MessUtil.exit("findProductsAsList"));
			return found;
		} catch (Exception e) {
			logger.error(MessUtil.errorLevel("findProductsAsList", "execute query from jtemp."));
			e.printStackTrace();
		}
		return null;
	}

	//Get all products from the database
	@Override
	public List<ProductModel> getProducts() {
		logger.info(MessUtil.enter("getProducts"));
		try {
			List<ProductModel> gotten = (List<ProductModel>)prodRepo.findAll();			
			return gotten;
		} catch (Exception e) {
			logger.error("getProducts");
			e.printStackTrace();
		}
		return null;
	}

	//Create a product
	@Override
	public void createProduct(ProductModel obj) {
		logger.info(MessUtil.enter("createProduct"));
		try {
			prodRepo.save(obj);
		} catch (Exception e) {
			logger.error(MessUtil.errorLevel("createProduct", "save ProductModel to prodRepo"));
			e.printStackTrace();
		}
	}

	//Delete a product
	@Override
	public void deleteProduct(ProductModel obj) {
		logger.info(MessUtil.enter("deleteProduct"));
		try {
			prodRepo.delete(obj);
		} catch(Exception e) {
			logger.error("deleteProduct", "delete ProductModel from prodRepo");
			e.printStackTrace();
		}
		logger.info(MessUtil.exit("deleteProduct"));
	}
}
