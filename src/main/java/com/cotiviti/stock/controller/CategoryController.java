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
import com.cotiviti.stock.repository.CategoryRepository;
import com.cotiviti.stock.repository.CurrentStockRepository;

@Controller
public class CategoryController {

	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private CurrentStockRepository currentStockRepo;

	@GetMapping("/addCategory")
	public String getCategory(Model model) {
		model.addAttribute("categories", new Category());
		return "addCategory";
	}

	@PostMapping("/addCategory")
	public String addCategory(@ModelAttribute Category cat) {
		cat.setIfDeleted("No");
		catRepo.save(cat);
		return "redirect:/main";
	}

	@GetMapping("/editCategory")
	public String getEditCategory(Model model, @RequestParam Integer id) {
		model.addAttribute("categories", catRepo.findById(id).get());
		return "editCategory";
	}

	@PostMapping("/editCategory")
	public String postEditCategory(@ModelAttribute Category category) {
		category.setIfDeleted("No");
		catRepo.save(category);
		return "redirect:/main";
	}
	
	@GetMapping("/deleteCategory")
	public String deleteCategory(Model model, @RequestParam Integer id) {
		Category cat = catRepo.findById(id).get();
		boolean flag=false;
		List<CurrentStock> currentStocks = currentStockRepo.findAll();
		List<Category> categories = catRepo.findByIfDeleted("No");
		for(CurrentStock cs : currentStocks) {
			if(cat.getCategoryName().equals(cs.getCategory().getCategoryName())) {
				String message = "Category still exists in current stock";
				flag = true;
				model.addAttribute("categories",categories);
				model.addAttribute("message",message);
				return "categoryTable";
			}
		}
		if(!flag) {
			cat.setIfDeleted("Yes");
			catRepo.save(cat);
		}
			
		return  "redirect:/main";
	}
	
	@GetMapping("/categoryTable")
	public String GetCatTable(Model model)
	{
		List<Category> categories = catRepo.findByIfDeleted("No");
		model.addAttribute("categories", categories);
		
		return "categoryTable";
	}

}
