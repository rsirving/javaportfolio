package com.project.javaportfolio.controllers;
import com.project.javaportfolio.models.*;
import com.project.javaportfolio.repositories.*;
import com.project.javaportfolio.validators.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class ScriptionUserContr{
	private UserRepo userRepo;
	private OptionRepo optionRepo;
	private UserValidator userValidator;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public ScriptionUserContr(UserRepo userRepo, OptionRepo optionRepo, UserValidator userValidator, 
			BCryptPasswordEncoder bCryptPasswordEncoder){
		this.userRepo = userRepo;
		this.optionRepo = optionRepo;
		this.userValidator = userValidator;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@RequestMapping("/scriptions/login")
	public String entrance(@ModelAttribute("user") User user, 
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, 
			Model model){
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
		return "entrance";
	}
	
	@RequestMapping("/scriptions/registration")
	public String registerRedir() {
		return "redirect:/scriptions/login";
	}

	@PostMapping("/scriptions/registration")
	public String register(@ModelAttribute("user") User user, Model model, 
			BindingResult result, RedirectAttributes err) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "entrance";
		} else {
			System.out.println(user.getPermissionLevel());
			List<User> admins = userRepo.findByPermissionLevel(2);
			if (admins.size() == 0 || user.getPermissionLevel() == 2) {
				userRepo.saveUser(user, bCryptPasswordEncoder, 2);
			} else {
				userRepo.saveUser(user, bCryptPasswordEncoder, 1);
			}
			return "redirect:/scriptions/login";
		}
	}

	@RequestMapping(value={"/scriptions", "/scriptions/home"})
	public String home(Model model, Principal principal) {
		String username = principal.getName();
		User currentUser = userRepo.findByUsername(username);
		if (currentUser.getPermissionLevel() == 2){
			return "redirect:/scriptions/admin";
		} else if (currentUser.getPermissionLevel() == 1 && currentUser.getOption() == null){
			return "redirect:/scriptions/selection"; // In options controller.
		} else {
			return "redirect:/scriptions/profile";
		}
	}
	
	@RequestMapping("/scriptions/profile")
	public String profile(Model model, Principal principal) {
		User currentUser = userRepo.findByUsername(principal.getName());
		if (currentUser == null) {
			return "redirect:/scriptions/login";
		} else if (currentUser.getOption() == null) {
			return "redirect:/scriptions/selection";
		} else {
			model.addAttribute("currentUser", currentUser);
			int dueDate = currentUser.getDueDate();
			LocalDate nextDue = LocalDate.now();
			if (nextDue.getDayOfMonth() >= dueDate){
				nextDue = nextDue.plusMonths(1);
			}
			if (dueDate <= nextDue.lengthOfMonth()){
				nextDue = nextDue.withDayOfMonth(dueDate);
			} else {
				nextDue = nextDue.withDayOfMonth(nextDue.lengthOfMonth());
			}
			model.addAttribute("dueDate", nextDue);
			return "profile";
		}
	}

	@RequestMapping("/scriptions/admin")
	public String admin(Model model, Principal principal, @ModelAttribute("option") Option option, 
			@ModelAttribute("optionFlash") String optionFlash) {
		String username = principal.getName();
		User currentUser = userRepo.findByUsername(username);
		if (currentUser.getPermissionLevel() == 1) {
			return "redirect:/scriptions/profile";
		} else {
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("options", optionRepo.findAll());
			model.addAttribute("users", userRepo.findAll());
			model.addAttribute("optionFlash", optionFlash);
			return "admin";
		}
	}
}