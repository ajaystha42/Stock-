package com.cotiviti.stock.controller;

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
import com.cotiviti.stock.repository.CategoryRepository;
import com.cotiviti.stock.repository.CurrentStockRepository;
import com.cotiviti.stock.repository.ProductRepository;

@Controller
public class ProductController {

	@Autowired
	private ProductRepository prdctRepo;

	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private CurrentStockRepository currentStockRepo;

	@GetMapping("/addProduct")
	public String GetProduct(Model model) {

		model.addAttribute("products", new ProductDetails());
		List<Category> categories = catRepo.findByIfDeleted("No");
		model.addAttribute("categories", categories);
		return "addProduct";
	}

	@PostMapping("/addProduct")
	public String PostAddProduct(@ModelAttribute ProductDetails product) {
		product.setIfDeleted("No");
		prdctRepo.save(product);
		return "redirect:/main";
	}

	@GetMapping("/deleteProduct")
	public String deleteProduct(Model model, @RequestParam Integer id) {
		ProductDetails products = prdctRepo.findById(id).get();
		List<CurrentStock> currentStocks = currentStockRepo.findAll();
		boolean flag = false;
		for(CurrentStock cs : currentStocks) {
			if(cs.getProduct().getProductName().equals(products.getProductName())) {
				String message = "Product still exists in current stock";
				flag=true;
				List<ProductDetails> productsNew = prdctRepo.findByIfDeleted("No");
				model.addAttribute("products", productsNew);
				model.addAttribute("message",message);
				return "productTable";
			}
		}
		if(!flag) {
			products.setIfDeleted("Yes");
			prdctRepo.save(products);
		}
		return "redirect:/main";
	}

	@GetMapping("/editProduct")
	public String EditProduct(@RequestParam Integer id, Model model) {
		model.addAttribute("products", prdctRepo.findById(id).get());
		List<Category> categories = catRepo.findAll();
		model.addAttribute("categories", categories);
		return "editProduct";
	}

	@PostMapping("/editProduct")
	public String PostEditProduct(@ModelAttribute ProductDetails product, Model model) {
		product.setIfDeleted("No");
		prdctRepo.save(product);
		return "redirect:/main";
	}
	
	@GetMapping("/productTable")
	public String getProdTable(Model model) {
		
		List<ProductDetails> products = prdctRepo.findByIfDeleted("No");
		model.addAttribute("products", products);
		return "productTable";
	}
	
}
