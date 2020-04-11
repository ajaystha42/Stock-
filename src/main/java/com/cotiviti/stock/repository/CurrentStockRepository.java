package com.cotiviti.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotiviti.stock.model.CurrentStock;

public interface CurrentStockRepository extends JpaRepository<CurrentStock, Integer> {
//findbyName
}
