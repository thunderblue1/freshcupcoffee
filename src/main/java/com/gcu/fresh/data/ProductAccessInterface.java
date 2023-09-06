package com.gcu.fresh.data;

import java.util.List;

public interface ProductAccessInterface<T> {
	public T findProduct(Long id);
	public List<T> findProductsAsList(String searchcriteria);
	public List<T> getProducts();
	public void createProduct(T obj);
	public void deleteProduct(T obj);
}
