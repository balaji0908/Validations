package com.example.demo.validation.config;

public interface DomainResolver<T,P> {
	
	P resolve(T request);

}
