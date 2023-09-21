package com.gcu.fresh.data;

import java.util.List;

public interface OrderAccessInterface<T> {
	public T getOrderById(Long id);
	public List<T> getOrders();
	public List<T> getOrdersByPurchaseNumber();
	public List<T> findOrdersAsList(String searchterm);
	public void deleteOrder(Long orderNumber);
}
