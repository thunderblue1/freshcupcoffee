package com.gcu.fresh.repository;

import org.springframework.data.repository.CrudRepository;

import com.gcu.fresh.models.ProductModel;

public interface ProductRepository extends CrudRepository<ProductModel, Long> {

}
