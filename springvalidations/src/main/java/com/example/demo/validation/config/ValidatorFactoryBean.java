package com.example.demo.validation.config;

import javax.validation.Validation;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorFactoryBean /*implements MapperFactory*/ {

	//@Override
	@Bean
	public ConstraintMapping getHibernateConstraintMapping() {
		HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
		return configuration.createConstraintMapping();
	}

  /*  public  ConstraintMapping postProcessConfiguration() {
    	HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
		return configuration.createConstraintMapping();
    }*/
	
	/*@Bean
	@Primary
	public DomainResolver domainResolver() {
		return new DefaultDomainResolver();
	}*/
 
}
