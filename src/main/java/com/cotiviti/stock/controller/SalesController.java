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
import com.cotiviti.stock.model.ProductDetails;
import com.cotiviti.stock.model.Sales;
import com.cotiviti.stock.model.Unit;
import com.cotiviti.stock.repository.CategoryRepository;
import com.cotiviti.stock.repository.CurrentStockRepository;
import com.cotiviti.stock.repository.ProductRepository;
import com.cotiviti.stock.repository.SalesRepository;
import com.cotiviti.stock.repository.UnitRepository;

@Controller
public class SalesController {

	@Autowired
	private SalesRepository salesRepo;

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private ProductRepository prdctRepo;

	@Autowired
	private UnitRepository unitRepo;

	@Autowired
	private CurrentStockRepository currentStockRepo;

	@GetMapping("/sales")
	public String GetSales(Model model, @RequestParam(required = false) Integer id) {
		LocalDate date = LocalDate.now();
		String lDate = date.toString();
		Sales sale = new Sales();
		List<Category> categories = catRepo.findByIfDeleted("No");
		List<Unit> units = unitRepo.findAll();

		if (id == null) {

			List<ProductDetails> products = prdctRepo.findAll();
			sale.setDate(lDate);

			model.addAttribute("products", products);
			model.addAttribute("sales", sale);
			model.addAttribute("categories", categories);

			model.addAttribute("units", units);
		}

		else {
			List<ProductDetails> products = prdctRepo.findByCategoryId(id);
			sale.setDate(lDate);

			model.addAttribute("sales", sale);
			model.addAttribute("categories", categories);

			model.addAttribute("units", units);
			model.addAttribute("products", products);
            sale.setCategory(categories.stream().filter(c -> c.getId().equals(id)).findFirst().get());

		}

		return "sales";
	}

	@PostMapping("/sales")
	public String PostSales(Model model, @ModelAttribute Sales sales) {
		int a = 0;
		String message = "error";
		Integer id = 0;
		List<CurrentStock> currentStocks = currentStockRepo.findAll();
		List<Category> categories = catRepo.findByIfDeleted("No");
		List<ProductDetails> products = prdctRepo.findAll();
		List<Unit> units = unitRepo.findAll();
		List<Sales> allSales = salesRepo.findAll();

		for (Sales s : allSales) {
			if (sales.getProduct().getProductName().equals(s.getProduct().getProductName())) {
				id = s.getId();
			}
		}

		for (CurrentStock cs : currentStocks) {
			if (cs.getProduct().equals(sales.getProduct())) {
				int quantity = 0;
				quantity = Integer.parseInt(cs.getQuantity()) - Integer.parseInt(sales.getQuantity());
				System.out.println("2222222222222222222222222"+quantity);
				String availQuantity = cs.getQuantity();
				cs.setQuantity(Integer.toString(quantity));

				if (quantity >= 0) {
					currentStockRepo.save(cs);
				} else {
					message = "Available quantity " + availQuantity;
					model.addAttribute("message", message);
					model.addAttribute("categories", categories);
					model.addAttribute("products", products);
					model.addAttribute("units", units);
					return "sales";
				}
				a = 2;
			}
		}
		boolean flag = false;
		if (a != 2) {
			message = "Item doesn't exist in stock";
			model.addAttribute("categories", categories);
			model.addAttribute("products", products);
			model.addAttribute("units", units);
			model.addAttribute("message", message);
			return "sales";
		} else {
			for (Sales s : allSales) {
				if (s.getProduct().equals(sales.getProduct()) && s.getDate().equals(sales.getDate())) {
					int quantity = 0;
					quantity = Integer.parseInt(s.getQuantity()) + Integer.parseInt(sales.getQuantity());
					s.setQuantity(Integer.toString(quantity));
					salesRepo.save(s);
					flag = true;
					return "redirect:/main";
				}
			}
			if (!flag) {
				salesRepo.save(sales);
				return "redirect:/main";
			}
			return "redirect:/main";
		}

	}

	@GetMapping("/salesTable")
	public String GetSalesTable(Model model) {

		List<Sales> sales = salesRepo.findAll();

		model.addAttribute("sales", sales);

		return "salesTable";
	}

}
