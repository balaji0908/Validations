package com.example.demo.validation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultDomainResolver<T,P> implements DomainResolver<T, P> {
	
	@Autowired
	private MapperFactory<T, P> mapperFactory;

	@SuppressWarnings("unchecked")
	@Override
	public P resolve(T request) {
		return (P) mapperFactory.mapper(request);
	}


}
