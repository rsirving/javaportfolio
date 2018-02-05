package com.project.javaportfolio.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.project.javaportfolio.models.Option;

@Component
public class OptionValidator implements Validator{	
	@Override
	public boolean supports(Class<?> clazz) {
		return Option.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		Option option = (Option) object;

		if (option.getName().length() < 2){
			errors.rejectValue("name", "option.shortName");
		}
		if (option.getName().length() >= 20){
			errors.rejectValue("name", "option.longName");
		}
		if (option.getCost() <= 0){
			errors.rejectValue("cost", "option.invalidCost");
		}
	}
}