package com.example.demo.validation.config;

public interface MapperFactory<T,P> {
	
	P mapper(T t);

}
