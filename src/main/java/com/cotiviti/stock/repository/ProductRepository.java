package com.cotiviti.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotiviti.stock.model.ProductDetails;

public interface ProductRepository extends JpaRepository<ProductDetails, Integer> {
	List<ProductDetails> findByIfDeleted(String product);

	List<ProductDetails> findByCategoryId(Integer id);

}
