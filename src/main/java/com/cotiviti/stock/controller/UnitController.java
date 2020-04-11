package com.cotiviti.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cotiviti.stock.model.Unit;
import com.cotiviti.stock.repository.UnitRepository;

@Controller
public class UnitController {

	@Autowired
	private UnitRepository unitRepo;

	@GetMapping("/addUnit")
	public String GetAddUnit(Model model) {
		model.addAttribute("unit", new Unit());
		return "addUnit";
	}

	@PostMapping("/addUnit")
	public String PostAddUnit(@ModelAttribute Unit unit) {
		unit.setIfDeleted("No");
		unitRepo.save(unit);
		return "redirect:/main";
	}
	
	@GetMapping("/unitTable")
	public String GetUnitTable(Model model) {
		List<Unit> units = unitRepo.findByIfDeleted("No");
		model.addAttribute("units", units);
		return "unitTable";
	}

	@GetMapping("/editUnit")
	public String EditUnit(Model model,@RequestParam Integer id) {
		model.addAttribute("units", unitRepo.findById(id).get());
		return "editUnit";
	}
	
	@PostMapping("/editUnit")
	public String postEdit(@RequestParam Integer id,@ModelAttribute Unit unit) {
		unit.setIfDeleted("No");
		unitRepo.save(unit);
		return "redirect:/main";
	}
	
	@GetMapping("/deleteUnit")
	public String deleteUnit(@RequestParam Integer id) {
		Unit unit = unitRepo.findById(id).get();
		unit.setIfDeleted("Yes");
		unitRepo.save(unit);
		return "redirect:/main";
	}

	
}
