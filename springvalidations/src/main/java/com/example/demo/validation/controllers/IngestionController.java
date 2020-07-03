package com.example.demo.validation.controllers;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.MinDef;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.SizeDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.validation.config.DomainResolver;
import com.example.demo.validation.config.ValidatorFactoryBean;
import com.example.demo.validation.model.IngestionRequest;
import com.example.demo.validation.model.IngestionResponse;
import com.example.demo.validation.model.User;
import com.example.demo.validation.model.UserAddrInfo;
import com.example.demo.validation.model.UserLaptop;

@RestController
public class IngestionController {
	
	@Autowired
	private ValidatorFactoryBean validator;
	
	/*@Autowired
	private IngestionMapper ingestionMapper;*/
	
	@Autowired
	private DomainResolver<IngestionRequest,ConstraintMapping> ingestionResolver;
	
	@Autowired
	private DomainResolver<User,ConstraintMapping> userResolver;
	
	@RequestMapping(value = "/validateJsr", method = RequestMethod.POST)
	public IngestionResponse validateJsr(@Valid @RequestBody IngestionRequest ingestionRequest) {
		IngestionResponse ingestionResponse= new IngestionResponse();
		return ingestionResponse;
	}

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

		return getIngestionResponse(ingestionRequest, ingestionResponse, validator);
	}
	
	
	@RequestMapping(value = "/testing", method = RequestMethod.POST)
	public IngestionResponse testing(@Valid @RequestBody IngestionRequest ingestionRequest) {
		IngestionResponse ingestionResponse= new IngestionResponse();
		HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
		//ConstraintMapping mapping = validator.postProcessConfiguration();
		ConstraintMapping mapping = configuration.createConstraintMapping();
		
		/**There are two ways in which Validation of Objects can be initiated
		 * 1)Have to mark @Valid on the Objects to be validated
		 * 2)Using .valid() i.e doing it in a programmatic way**/
		
		mapping.type(IngestionRequest.class).property("user", ElementType.FIELD).valid()
		
		.type(User.class).property("firstName", ElementType.FIELD).constraint(new SizeDef().min(5).max(30))
		.property("lastName", ElementType.FIELD).constraint(new NotNullDef()).constraint(new SizeDef().max(2))
		
		.property("userAddrInfo", ElementType.FIELD).valid().property("laptops", ElementType.FIELD).valid()
		
		.type(UserAddrInfo.class).property("city", ElementType.FIELD).constraint(new SizeDef().min(8).max(30))
		
		.type(UserLaptop.class).property("name", ElementType.FIELD).constraint(new MinDef().value(8))
		.property("model", ElementType.FIELD).constraint(new NotNullDef()).constraint(new SizeDef().max(2));
		
		
		Validator validator = configuration.addMapping(mapping).buildValidatorFactory().getValidator();

		return getIngestionResponse(ingestionRequest, ingestionResponse, validator);
	}
	
	/*@RequestMapping(value = "/genericValidations", method = RequestMethod.POST)
	public IngestionResponse genericValidations(@Valid @RequestBody IngestionRequest ingestionRequest) {
		System.out.println("Inside genericValidations::");
		IngestionResponse ingestionResponse= new IngestionResponse();
		HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
		ConstraintMapping mapping = ingestionMapper.mapper();
		Validator validator = configuration.addMapping(mapping).buildValidatorFactory().getValidator();

		Set<ConstraintViolation<IngestionRequest>> validationErrors = validator.validate(ingestionRequest);
		List<String> messages = new ArrayList<>();
		if (!validationErrors.isEmpty()) {
			for (ConstraintViolation<IngestionRequest> error : validationErrors) {
				System.out.println(error.getPropertyPath() + "::" + error.getMessage());

			}
			messages = validationErrors.stream().map(x -> x.getPropertyPath()+"::"+ x.getMessage()).collect(Collectors.toList());
		}
		ingestionResponse.setErrorMessages(messages);
		return ingestionResponse;
	}*/
	
	@PostMapping("/frameworkValidation")
	public IngestionResponse frameworkValidation(@RequestBody IngestionRequest ingestionRequest) {
		IngestionResponse ingestionResponse= new IngestionResponse();
		HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
		ConstraintMapping mapping = ingestionResolver.resolve(ingestionRequest);
		Validator validator = configuration.addMapping(mapping).buildValidatorFactory().getValidator();
		return getIngestionResponse(ingestionRequest, ingestionResponse, validator);
	}
	
	@PostMapping("/userValidation")
	public IngestionResponse userValidation(@RequestBody User user) {
		IngestionResponse ingestionResponse= new IngestionResponse();
		HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
		ConstraintMapping mapping = userResolver.resolve(user);
		Validator validator = configuration.addMapping(mapping).buildValidatorFactory().getValidator();
		Set<ConstraintViolation<User>> validationErrors = validator.validate(user);
		List<String> messages = new ArrayList<>();
		if (!validationErrors.isEmpty()) {
			for (ConstraintViolation<User> error : validationErrors) {
				System.out.println(error.getPropertyPath() + "::" + error.getMessage());

			}
			messages = validationErrors.stream().map(x -> x.getPropertyPath()+"::"+ x.getMessage()).collect(Collectors.toList());
		}
		ingestionResponse.setErrorMessages(messages);
		return ingestionResponse;
	}

	private IngestionResponse getIngestionResponse(IngestionRequest ingestionRequest,
			IngestionResponse ingestionResponse, Validator validator) {
		Set<ConstraintViolation<IngestionRequest>> validationErrors = validator.validate(ingestionRequest);
		List<String> messages = new ArrayList<>();
		if (!validationErrors.isEmpty()) {
			for (ConstraintViolation<IngestionRequest> error : validationErrors) {
				System.out.println(error.getPropertyPath() + "::" + error.getMessage());

			}
			messages = validationErrors.stream().map(x -> x.getPropertyPath()+"::"+ x.getMessage()).collect(Collectors.toList());
		}
		ingestionResponse.setErrorMessages(messages);
		return ingestionResponse;
	}
	
}
