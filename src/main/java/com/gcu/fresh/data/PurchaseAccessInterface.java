package com.gcu.fresh.data;

import java.util.List;

public interface PurchaseAccessInterface<T> {
	//Get purchase from database based on id
	public T findPurchase(Long id);
	//Get purchases from database as a list based on search criteria
	public List<T> findPurchasesAsList(String searchcriteria);
	//Get all purchases from database and return them as a list
	public List<T> getPurchases();
	//Create a purchase
	public void createPurchase(T obj);
	//Delete a purchase
	public void deletePurchase(T obj);
}
