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
	
	@Override
	public PurchaseModel findPurchase(Long id) {
		Optional<PurchaseModel> gotten = purchRepo.findById(id);
		if(gotten.isPresent()) {
			return gotten.get();
		} else {
			return null;	
		}
	}

	@Override
	public List<PurchaseModel> findPurchasesAsList(String searchcriteria) {
		logger.info(MessUtil.enter("findPurchasesAsList"));
		String sql = "SELECT * FROM item WHERE LOWER(name) LIKE ? || LOWER(description) LIKE ? || LOWER(price) LIKE ? ";
		String searchParam = '%'+searchcriteria.toLowerCase()+'%';
		try {
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

	@Override
	public List<PurchaseModel> getPurchases() {
		logger.info(MessUtil.enter("getPurchases"));
		try {
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
