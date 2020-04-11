package com.cotiviti.stock.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cotiviti.stock.model.Category;
import com.cotiviti.stock.model.CurrentStock;
import com.cotiviti.stock.model.MainStock;
import com.cotiviti.stock.model.ProductDetails;
import com.cotiviti.stock.model.Unit;
import com.cotiviti.stock.repository.CategoryRepository;
import com.cotiviti.stock.repository.CurrentStockRepository;
import com.cotiviti.stock.repository.MainStockRepository;
import com.cotiviti.stock.repository.ProductRepository;
import com.cotiviti.stock.repository.UnitRepository;

@Controller
public class MainStockController {

	@Autowired
	private MainStockRepository mainStockRepo;

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private ProductRepository prdctRepo;

	@Autowired
	private UnitRepository unitRepo;

	@Autowired
	private CurrentStockRepository currentStockRepo;

//	@GetMapping("/addStock")
//	public String GetStocks(Model model) {
//		LocalDate date = LocalDate.now();
//		String lDate = date.toString();
//		MainStock stock = new MainStock();
//		stock.setDate(lDate);
//		model.addAttribute("stocks", stock);
//		List<ProductDetails> products = prdctRepo.findAll();
//		model.addAttribute("products", products);
//		List<Category> categories = catRepo.findByIfDeleted("No");
//		model.addAttribute("categories", categories);
//		List<Unit> units = unitRepo.findAll();
//		model.addAttribute("units", units);
//
//		return "addStock";
//	}

	@GetMapping("/addStock")
	public String GetStocks(Model model, @RequestParam(required = false) Integer id) {
		LocalDate date = LocalDate.now();
		String lDate = date.toString();

		MainStock stock = new MainStock();
		stock.setDate(lDate);
		model.addAttribute("stocks", stock);
		if (id == null) {

//	            
//	            MainStock stock = new MainStock();
//	            stock.setDate(lDate);
//	            model.addAttribute("stocks", stock);
			List<Category> categories = catRepo.findAll();
			model.addAttribute("categories", categories);
			List<ProductDetails> products = prdctRepo.findAll();
			model.addAttribute("products", products);

		} else {
//	            MainStock stock = mainStockRepo.findById(id).get();
//	            stock.setDate(lDate);
//	            model.addAttribute("stocks", stock);
			List<Category> categories = catRepo.findAll();
//	                Category categories = catRepo.findById(id).get();
			model.addAttribute("categories", categories);
			List<ProductDetails> products = prdctRepo.findByCategoryId(id);
			model.addAttribute("products", products);
			model.addAttribute("selectedCategoryId", id);
			stock.setCategory(categories.stream().filter(c -> c.getId().equals(id)).findFirst().get());
		}

		List<Unit> units = unitRepo.findAll();
		model.addAttribute("units", units);

		model.addAttribute("idd", id);
//	            System.out.println("222222222222222   "+products);
		return "addStock";
	}

	@PostMapping("/addStock")
	public String AddStock(@ModelAttribute MainStock mainStock) {
		mainStock.setIfDeleted("No");
		mainStockRepo.save(mainStock);

		CurrentStock currentStock = new CurrentStock();
		boolean flag = false;
		List<CurrentStock> currentStocks = currentStockRepo.findAll();
		for (CurrentStock cs : currentStocks) {
			if (cs.getProduct().equals(mainStock.getProduct())) {
				int quantity = 0;
				quantity = Integer.parseInt(mainStock.getQuantity()) + Integer.parseInt(cs.getQuantity());
				cs.setQuantity(Integer.toString(quantity));
				currentStockRepo.save(cs);
				flag = true;
			}
		}
		if (!flag) {
			currentStock.setCategory(mainStock.getCategory());
			currentStock.setMainStock(mainStock);
			currentStock.setProduct(mainStock.getProduct());
			currentStock.setQuantity(mainStock.getQuantity());
			currentStock.setUnit(mainStock.getUnits());
			currentStockRepo.save(currentStock);
		}

		return "redirect:/main";
	}

	@GetMapping("/mainStockTable")
	public String GetStocksTable(Model model) {
		List<MainStock> stocks = mainStockRepo.findByIfDeleted("No");
		model.addAttribute("stocks", stocks);

		return "mainStockTable";
	}

}
