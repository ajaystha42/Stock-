package com.cotiviti.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotiviti.stock.model.Sales;

public interface SalesRepository extends JpaRepository<Sales, Integer>{
	
    List<Sales> findByDate(String date) ;


}
