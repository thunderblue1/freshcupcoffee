package com.gcu.fresh.data;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gcu.fresh.mapper.PurchaseRowMapper;
import com.gcu.fresh.models.PurchaseModel;
import com.gcu.fresh.repository.PurchaseRepository;

import utility.MessUtil;

@Service
public class PurchaseDAO implements PurchaseAccessInterface<PurchaseModel> {

	public static Logger logger = LoggerFactory.getLogger(PurchaseDAO.class);
	
	@Autowired
	PurchaseRepository purchRepo;
	
	@Autowired
	JdbcTemplate jtemp;
	
	//Get purchase from database based on id	
	@Override
	public PurchaseModel findPurchase(Long id) {
		Optional<PurchaseModel> gotten = purchRepo.findById(id);
		if(gotten.isPresent()) {
			return gotten.get();
		} else {
			return null;	
		}
	}

	//Get purchases from database as a list based on search criteria
	@Override
	public List<PurchaseModel> findPurchasesAsList(String searchcriteria) {
		logger.info(MessUtil.enter("findPurchasesAsList"));
		String sql = "SELECT * FROM item WHERE LOWER(name) LIKE ? || LOWER(description) LIKE ? || LOWER(price) LIKE ? ";
		String searchParam = '%'+searchcriteria.toLowerCase()+'%';
		try {
			//If products were found based on SQL query then return them as a list
			List<PurchaseModel> found = jtemp.query(sql, new PurchaseRowMapper(),searchParam, searchParam, searchParam);
			logger.info(MessUtil.exit("findPurchasesAsList"));
			return found;	
		} catch (Exception e) {
			logger.error(MessUtil.errorLevel("findPurchasesAsList", "query from jtemp"));
			e.printStackTrace();
		}
		logger.info(MessUtil.exit("findPurchasesAsList"));
		return null;
	}

	//Get all purchases from database and return them as a list
	@Override
	public List<PurchaseModel> getPurchases() {
		logger.info(MessUtil.enter("getPurchases"));
		try {
			//Get all products from database and return them as a list
			List<PurchaseModel> gotten = (List<PurchaseModel>)purchRepo.findAll();
			logger.info(MessUtil.exit("getPurchases"));
			return gotten;
		} catch (Exception e) {
			logger.error(MessUtil.errorLevel("getPurchases", "find all from purchRepo"));
			e.printStackTrace();
		}
		logger.info(MessUtil.exit("getPurchases"));
		return null;
	}
	
	//Create a purchase
	@Override
	public void createPurchase(PurchaseModel obj) {
		logger.info(MessUtil.enter("createPurchase"));
		try {
			purchRepo.save(obj);
		} catch (Exception e) {
			logger.error(MessUtil.errorLevel("createPurchase", "save PurchaseModel to purchRepo"));
			e.printStackTrace();
		}
		logger.info(MessUtil.exit("createPurchase"));
	}
	
	//Delete a purchase
	@Override
	public void deletePurchase(PurchaseModel obj) {
		logger.info(MessUtil.enter("deletePurchase"));
		try {
			purchRepo.delete(obj);
		} catch(Exception e) {
			logger.error(MessUtil.errorLevel("deletePurchase", "delete PurchaseModel from purchRepo"));
			e.printStackTrace();
		}
		logger.info(MessUtil.exit("deletePurchase"));
	}

}
