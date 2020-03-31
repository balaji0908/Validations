package com.example.demo.validation.controllers;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.MinDef;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.SizeDef;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.validation.model.IngestionRequest;
import com.example.demo.validation.model.IngestionResponse;
import com.example.demo.validation.model.User;
import com.example.demo.validation.model.UserAddrInfo;
import com.example.demo.validation.model.UserLaptop;

@RestController
public class IngestionController {

	@RequestMapping(value = "/validateUser", method = RequestMethod.POST)
	public IngestionResponse validateUser(@RequestBody IngestionRequest ingestionRequest) {
		IngestionResponse ingestionResponse= new IngestionResponse();
		HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
		ConstraintMapping mapping = configuration.createConstraintMapping();
		
		mapping.type(User.class).property("firstName", ElementType.FIELD).constraint(new SizeDef().min(5).max(30))
		.property("lastName", ElementType.FIELD).constraint(new NotNullDef()).constraint(new SizeDef().max(2))
		
		.type(UserAddrInfo.class).property("city", ElementType.FIELD).constraint(new SizeDef().min(8).max(30))
		
		.type(UserLaptop.class).property("name", ElementType.FIELD).constraint(new MinDef().value(8))
		.property("model", ElementType.FIELD).constraint(new NotNullDef()).constraint(new SizeDef().max(2));
		
		
		Validator validator = configuration.addMapping(mapping).buildValidatorFactory().getValidator();

		Set<ConstraintViolation<IngestionRequest>> validationErrors = validator.validate(ingestionRequest);
		List<String> messages = new ArrayList<>();
		if (!validationErrors.isEmpty()) {
			for (ConstraintViolation<IngestionRequest> error : validationErrors) {
				/*System.out.println(
						error.getMessageTemplate() + "::" + error.getPropertyPath() + "::" + error.getMessage());*/
				System.out.println(error.getPropertyPath() + "::" + error.getMessage());

			}
			messages = validationErrors.stream().map(x -> x.getPropertyPath()+"::"+ x.getMessage()).collect(Collectors.toList());
			//Collections.reverse(messages);
		}
		ingestionResponse.setErrorMessages(messages);
		return ingestionResponse;
	}
	
}
