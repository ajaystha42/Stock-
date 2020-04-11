package com.cotiviti.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotiviti.stock.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> findByIfDeleted(String category) ;
}
