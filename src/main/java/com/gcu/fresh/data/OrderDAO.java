package com.gcu.fresh.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gcu.fresh.models.OrderModel;
import com.gcu.fresh.models.OrderTransferModel;
import com.gcu.fresh.models.ProductModel;
import com.gcu.fresh.repository.OrderRepository;
import com.gcu.fresh.repository.ProductRepository;

@Service
public class OrderDAO implements OrderAccessInterface<OrderModel> {
	@Autowired
	JdbcTemplate jdbctemp;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Override
	public OrderModel getOrderById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderModel> getOrders() {
		List<OrderTransferModel> gotten = (List<OrderTransferModel>) orderRepo.findAll();
		List<OrderModel> made = new ArrayList<OrderModel>();
		for (OrderTransferModel g: gotten) {
			Optional<ProductModel> details = productRepo.findById(g.getItemNumber());
			if(details.isPresent()) {
				ProductModel d = details.get();
				OrderModel composed = new OrderModel(g.getItemNumber(),g.getPurchaseNumber(),g.getQuantity(),d.getName(),d.getDescription(),d.getPrice(),d.getPhoto());	
				composed.setOrderNumber(g.getOrderNumber());
				made.add(composed);
			}
		}
		return made;
	}

	@Override
	public List<OrderModel> getOrdersByPurchaseNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderModel> findOrdersAsList(String searchterm) {
		List<OrderTransferModel> gotten = (List<OrderTransferModel>) orderRepo.findAll();
		List<OrderModel> made = new ArrayList<OrderModel>();
		for (OrderTransferModel g: gotten) {
			Optional<ProductModel> details = productRepo.findById(g.getItemNumber());
			if(details.isPresent()) {
				ProductModel d = details.get();
				OrderModel composed = new OrderModel(g.getItemNumber(),g.getPurchaseNumber(),g.getQuantity(),d.getName(),d.getDescription(),d.getPrice(),d.getPhoto());	
				composed.setOrderNumber(g.getOrderNumber());
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
		return made;
	}

	@Override
	public void deleteOrder(Long orderNumber) {
		orderRepo.deleteById(orderNumber);
	}

}
