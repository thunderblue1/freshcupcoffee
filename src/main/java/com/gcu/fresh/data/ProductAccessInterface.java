package com.gcu.fresh.data;

import java.util.List;

public interface ProductAccessInterface<T> {
	//Get a product as a ProductModel from database based on the id
	public T findProduct(Long id);
	//Get a list of products matching the search criteria
	public List<T> findProductsAsList(String searchcriteria);
	//Get all products from the database
	public List<T> getProducts();
	//Create a product
	public void createProduct(T obj);
	//Delete a product
	public void deleteProduct(T obj);
}
