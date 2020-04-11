package com.cotiviti.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotiviti.stock.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
	List<Unit> findByIfDeleted(String string);
}
