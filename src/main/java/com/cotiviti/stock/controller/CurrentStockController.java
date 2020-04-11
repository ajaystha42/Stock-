package com.cotiviti.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cotiviti.stock.model.CurrentStock;
import com.cotiviti.stock.repository.CurrentStockRepository;

@Controller
public class CurrentStockController {

	@Autowired
	CurrentStockRepository currentStockRepo;

	@GetMapping("/currentStockTable")
	public String GetCurrentStock(Model model) {
		List<CurrentStock> currentStocks = currentStockRepo.findAll();
		model.addAttribute("currentStocks", currentStocks);
		return "currentStockTable";
	}

}
