package com.gcu.fresh.data;

import java.util.List;

public interface OrderAccessInterface<T> {
	//Get an order by the id
	public T getOrderById(Long id);
	//Return all orders in database
	public List<T> getOrders();
	//Get the orders in database by purchase number
	public List<T> getOrdersByPurchaseNumber();
	//Get the orders in database by purchase number
	public List<T> findOrdersAsList(String searchterm);
	//Delete an order by the order number
	public void deleteOrder(Long orderNumber);
}
