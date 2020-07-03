package com.example.demo.validation.config.mappers;

import java.lang.annotation.ElementType;

import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.MinDef;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.SizeDef;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.validation.config.MapperFactory;
import com.example.demo.validation.model.IngestionRequest;
import com.example.demo.validation.model.User;
import com.example.demo.validation.model.UserAddrInfo;
import com.example.demo.validation.model.UserLaptop;

//@Component
//@Configuration
public class IngestionMapper implements MapperFactory<IngestionRequest,ConstraintMapping>{
	
//	@Autowired
//	ValidatorFactoryBean validator;
	
	@Autowired
	private ConstraintMapping mapping;

	@Override
	public ConstraintMapping mapper(IngestionRequest ingestionRequest) {
		mapping.type(IngestionRequest.class).property("user", ElementType.FIELD).valid()
				//mapping.type(clazz).property("user", ElementType.FIELD).valid()
				
				.type(User.class).property("firstName", ElementType.FIELD).constraint(new SizeDef().min(5).max(30))
				.property("lastName", ElementType.FIELD).constraint(new NotNullDef()).constraint(new SizeDef().max(2))
				
				.property("userAddrInfo", ElementType.FIELD).valid().property("laptops", ElementType.FIELD).valid()
				
				.type(UserAddrInfo.class).property("city", ElementType.FIELD).constraint(new SizeDef().min(8).max(30))
				
				.type(UserLaptop.class).property("name", ElementType.FIELD).constraint(new MinDef().value(8))
				.property("model", ElementType.FIELD).constraint(new NotNullDef()).constraint(new SizeDef().max(2));
			
				return mapping;
	}

	/*public <T> ConstraintMapping mapper(Class<T> clazz) {
		//ConstraintMapping mapping = validator.postProcessConfiguration();
		//mapping.type(IngestionRequest.class).property("user", ElementType.FIELD).valid()
		mapping.type(clazz).property("user", ElementType.FIELD).valid()
		
		.type(User.class).property("firstName", ElementType.FIELD).constraint(new SizeDef().min(5).max(30))
		.property("lastName", ElementType.FIELD).constraint(new NotNullDef()).constraint(new SizeDef().max(2))
		
		.property("userAddrInfo", ElementType.FIELD).valid().property("laptops", ElementType.FIELD).valid()
		
		.type(UserAddrInfo.class).property("city", ElementType.FIELD).constraint(new SizeDef().min(8).max(30))
		
		.type(UserLaptop.class).property("name", ElementType.FIELD).constraint(new MinDef().value(8))
		.property("model", ElementType.FIELD).constraint(new NotNullDef()).constraint(new SizeDef().max(2));
	
		return mapping;
		
		
	}
*/


}
