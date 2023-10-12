package com.gcu.fresh.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gcu.fresh.models.OrderModel;
import com.gcu.fresh.models.OrderTransferModel;
import com.gcu.fresh.models.ProductModel;
import com.gcu.fresh.models.PurchaseModel;
import com.gcu.fresh.repository.OrderRepository;
import com.gcu.fresh.repository.ProductRepository;
import com.gcu.fresh.repository.PurchaseRepository;

import utility.MessUtil;

@Service
public class OrderDAO implements OrderAccessInterface<OrderModel> {
	
	public static Logger logger = LoggerFactory.getLogger(OrderDAO.class);
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	PurchaseRepository purchRepo;
	
	//Get an order by the id
	@Override
	public OrderModel getOrderById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	//Return all orders in database
	@Override
	public List<OrderModel> getOrders() {
		logger.info(MessUtil.enter("getOrders"));
		List<OrderModel> made = new ArrayList<OrderModel>();
		try {
			//Get orders from database as OrderTransferModel
			List<OrderTransferModel> gotten = (List<OrderTransferModel>) orderRepo.findAll();
			
			//Make the OrderTransferModel an OrderModel
			for (OrderTransferModel g: gotten) {
				Optional<ProductModel> details = productRepo.findById(g.getItemNumber());
				if(details.isPresent()) {
					ProductModel d = details.get();
					OrderModel composed = new OrderModel(g.getItemNumber(),g.getPurchaseNumber(),g.getQuantity(),d.getName(),d.getDescription(),d.getPrice(),d.getPhoto());	
					composed.setOrderNumber(g.getOrderNumber());
					Optional<PurchaseModel> purchase = purchRepo.findById(g.getPurchaseNumber());
					if(purchase.isPresent()) {
						PurchaseModel purch = purchase.get();
						composed.setTableNumber(purch.getTableNumber());
					}
					made.add(composed);
				}
			}
			logger.info(MessUtil.exit("getOrders"));
		} catch (Exception e) {
			logger.error(MessUtil.errorLevel("getOrders", "find by id from productRepo"));
			e.printStackTrace();
		}
		return made;
	}

	//Get the orders in database by purchase number
	@Override
	public List<OrderModel> getOrdersByPurchaseNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	//Find the orders by based on searchterm and return then as a list
	@Override
	public List<OrderModel> findOrdersAsList(String searchterm) {
		logger.info(MessUtil.enter("findOrdersAsList"));
		List<OrderModel> made = new ArrayList<OrderModel>();
		try {
			//Get all orders from the database as OrderTransferModel
			List<OrderTransferModel> gotten = (List<OrderTransferModel>) orderRepo.findAll();
			
			//Make the OrderTransferModel an OrderModel and check to see if it matches the search criteria
			for (OrderTransferModel g: gotten) {
				Optional<ProductModel> details = productRepo.findById(g.getItemNumber());
				if(details.isPresent()) {
					ProductModel d = details.get();
					OrderModel composed = new OrderModel(g.getItemNumber(),g.getPurchaseNumber(),g.getQuantity(),d.getName(),d.getDescription(),d.getPrice(),d.getPhoto());	
					composed.setOrderNumber(g.getOrderNumber());
					Optional<PurchaseModel> purchase = purchRepo.findById(g.getPurchaseNumber());
					if(purchase.isPresent()) {
						PurchaseModel purch = purchase.get();
						composed.setTableNumber(purch.getTableNumber());
					}
					//If the order matches the search criteria then add it to be returned
					if(composed.getItemNumber().toString().toLowerCase().matches("(.*)"+searchterm.toLowerCase()+"(.*)")||
						composed.getPurchaseNumber().toString().toLowerCase().matches("(.*)"+searchterm.toLowerCase()+"(.*)")||
						composed.getQuantity().toString().toLowerCase().matches("(.*)"+searchterm.toLowerCase()+"(.*)")||
						composed.getName().toString().toLowerCase().matches("(.*)"+searchterm.toLowerCase()+"(.*)")||
						composed.getDescription().toLowerCase().toString().matches("(.*)"+searchterm.toLowerCase()+"(.*)")||
						composed.getPrice().toString().toLowerCase().matches("(.*)"+searchterm.toLowerCase()+"(.*)")
						) {
						
						made.add(composed);					
					}
				}
			}			
		} catch (Exception e) {
			logger.error(MessUtil.errorLevel("findOrdersAsList", " access repository."));
			e.printStackTrace();
		}
		logger.info(MessUtil.exit("findOrdersAsList"));
		return made;
	}

	//Delete an order by the order number
	@Override
	public void deleteOrder(Long orderNumber) {
		logger.info(MessUtil.enter("deleteOrder"));
		try {
			orderRepo.deleteById(orderNumber);			
		} catch (Exception e) {
			logger.error(MessUtil.errorLevel("deleteOrder", " delete by id for orderRepo."));
			e.printStackTrace();
		}
		logger.info(MessUtil.exit("deleteOrder"));
	}

}
