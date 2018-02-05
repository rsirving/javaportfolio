package com.project.javaportfolio.controllers;
import com.project.javaportfolio.repositories.*;
import com.project.javaportfolio.models.*;
import com.project.javaportfolio.validators.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ScriptionOptContr{
	private final OptionRepo optionRepo;
	private UserRepo userRepo;
	private OptionValidator optionValidator;
	public ScriptionOptContr(OptionRepo optionRepo, UserRepo userRepo, OptionValidator optionValidator){
		this.optionRepo = optionRepo;
		this.userRepo = userRepo;
		this.optionValidator = optionValidator;
	}
	
	@PostMapping("/scriptions/packages/new")
	public String newOption(@Valid @ModelAttribute("option") Option option, Model model, BindingResult result, RedirectAttributes err){
		optionValidator.validate(option, result);
		System.out.println(result);
		if (result.hasErrors()){
			err.addFlashAttribute("optionFlash", "Package submission failed.");
			return "redirect:/scriptions/admin";
		} else {
			this.optionRepo.saveOption(option);
			err.addFlashAttribute("optionFlash", "Package submitted!");
			return "redirect:/scriptions/admin";
		}
	}

	@RequestMapping("/scriptions/selection")
	public String selection(Model model, Principal principal){
		String username = principal.getName();
		User currentUser = userRepo.findByUsername(username);
		if (currentUser == null) {
			return "redirect:/scriptions/login";
		} else {
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("options", optionRepo.findAll());
			return "selection";
		}
	}

	@PostMapping("/scriptions/selection")
	public String addSelection(Model model, Principal principal, @RequestParam(value="option") long id, @RequestParam(value="dueDate") int dueDate){
		User currentUser = userRepo.findByUsername(principal.getName());
		Option option = optionRepo.findOne(id);
		currentUser.setDueDate(dueDate);
		currentUser.setOption(option);
		List<User> users = option.getUsers();
		users.add(currentUser);
		option.setUsers(users);
		optionRepo.save(option);
		userRepo.save(currentUser);
		return "redirect:/scriptions/profile"; // in users controller.
	}

	@RequestMapping("/scriptions/packages/{id}/delete")
	public String deleteOption(@PathVariable("id") long id, Model model, Principal principal){
		Option option = optionRepo.findOne(id);
		String username = principal.getName();
		User currentUser = userRepo.findByUsername(username);
		if (currentUser == null ){
			return "redirect:/scriptions/login";
		}
		if ( currentUser.getPermissionLevel() == 1 ){
			return "redirect:/scriptions/profile"; // in users controller
		} 
		if ( option.getUsers().size() != 0 ) {
			return "redirect:/scriptions/admin"; // in users controller
		} else {
			optionRepo.delete(option);
			return "redirect:/scriptions/admin"; // in users controller
		}
	}

	@RequestMapping("/scriptions/packages/{id}/toggle")
	public String packageToggle(@PathVariable("id") long id) {
		Option option = optionRepo.findOne(id);
		if (option != null){
			option.setActive(!option.isActive());
			optionRepo.save(option);
		}
		return "redirect:/scriptions/admin"; // in users controller
	}
}
