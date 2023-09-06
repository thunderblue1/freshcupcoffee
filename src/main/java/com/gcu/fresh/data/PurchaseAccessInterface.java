package com.gcu.fresh.data;

import java.util.List;

public interface PurchaseAccessInterface<T> {
	public T findPurchase(Long id);
	public List<T> findPurchasesAsList(String searchcriteria);
	public List<T> getPurchases();
	public void createPurchase(T obj);
	public void deletePurchase(T obj);
}
