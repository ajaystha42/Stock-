package com.cotiviti.stock.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cotiviti.stock.model.CurrentStock;
import com.cotiviti.stock.model.Sales;
import com.cotiviti.stock.repository.CurrentStockRepository;
import com.cotiviti.stock.repository.SalesRepository;

@Controller
public class DashboardController {

	@Autowired
	private SalesRepository salesRepo;

	@Autowired
	private CurrentStockRepository currentStockRepo;

	@GetMapping("/main")
	public String GetAllProducts(Model model) {
		int quantity = 0;
		List<Sales> sales = salesRepo.findAll();
		ArrayList salesList = new ArrayList();
		ArrayList<String> head = new ArrayList<String>();
		head.add("Date");
		head.add("Quantity");
		salesList.add(head);
		Set<String> check = new HashSet<String>();
		for (Sales s : sales) {
			ArrayList salesL = new ArrayList();
			boolean flag = true;
			if (!check.contains(s.getDate())) {
				salesL.add(s.getDate());
				for (Sales sa : sales) {
					if (salesL.contains(sa.getDate())) {
						quantity = quantity + Integer.parseInt(sa.getQuantity());
						System.out.println("quantity " + quantity);
					}
				}
				salesL.add(quantity);
				flag = false;
			}
			salesList.add(salesL);
			check.add(s.getDate());
		}
		salesList.remove((salesList.size() - 1));
		System.out.println("00000000000000000000000" + salesList);
//		System.out.println("00000000000000000000000000000000000000000000");
//		System.out.println(sales.size());
//		System.out.println(salesList);
		List<CurrentStock> currentStocks = currentStockRepo.findAll();

		for (CurrentStock cs : currentStocks) {
			if (Integer.parseInt(cs.getQuantity()) == 0) {
				currentStockRepo.delete(cs);
			}
		}
		List<CurrentStock> currentStocksUpdated = currentStockRepo.findAll();
		model.addAttribute("currentStocks", currentStocksUpdated);
		LocalDate date = LocalDate.now();
		String lDate = date.toString();
		List<Sales> salesByDate = salesRepo.findByDate(lDate);
		model.addAttribute("salesByDate", salesByDate);
		model.addAttribute("saleList", salesList);
		return "dashboard";
	}
}
