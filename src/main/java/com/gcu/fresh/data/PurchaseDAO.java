package com.gcu.fresh.data;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gcu.fresh.mapper.PurchaseRowMapper;
import com.gcu.fresh.models.PurchaseModel;
import com.gcu.fresh.repository.PurchaseRepository;

@Service
public class PurchaseDAO implements PurchaseAccessInterface<PurchaseModel> {

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
		String sql = "SELECT * FROM item WHERE LOWER(name) LIKE ? || LOWER(description) LIKE ? || LOWER(price) LIKE ? ";
		String searchParam = '%'+searchcriteria.toLowerCase()+'%';
		List<PurchaseModel> found = jtemp.query(sql, new PurchaseRowMapper(),searchParam, searchParam, searchParam);
		return found;
	}

	@Override
	public List<PurchaseModel> getPurchases() {
		List<PurchaseModel> gotten = (List<PurchaseModel>)purchRepo.findAll();
		return gotten;
	}

	@Override
	public void createPurchase(PurchaseModel obj) {
		try {
			purchRepo.save(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletePurchase(PurchaseModel obj) {
		try {
			purchRepo.delete(obj);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
