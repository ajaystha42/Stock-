package com.cotiviti.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotiviti.stock.model.MainStock;

public interface MainStockRepository extends JpaRepository<MainStock , Integer>{
	List<MainStock> findByIfDeleted(String string);
}
