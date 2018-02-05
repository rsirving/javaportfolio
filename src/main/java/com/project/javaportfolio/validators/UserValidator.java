package com.project.javaportfolio.validators;
import com.project.javaportfolio.repositories.*;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.javaportfolio.models.User;

@Component
public class UserValidator implements Validator{
	private UserRepo userRepo;

	@Autowired
	public UserValidator(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		User user = (User) object;
		if (user.getUsername().length() != 0){
			if (!user.getUsername().matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")){
				errors.rejectValue("username", "user.invalidEmail");
			}
			if (userRepo.findByUsername(user.getUsername()) != null){
				errors.rejectValue("username", "user.dupeUser");
			}
		} else {
			ValidationUtils.rejectIfEmpty(errors, "username", "user.emptyEmail");
		}
		if (user.getFirstname().length() < 2){
			errors.rejectValue("firstname", "user.shortFirst");
		}
		if (user.getLastname().length() < 2){
			errors.rejectValue("lastname", "user.shortLast");
		}
		if (user.getPassword().length() < 8){
			errors.rejectValue("password", "user.passwordSize");
		}
		if (!user.getPasswordConfirmation().equals(user.getPassword())){
			errors.rejectValue("passwordConfirmation", "user.passwordMatch");
		}
	}
}