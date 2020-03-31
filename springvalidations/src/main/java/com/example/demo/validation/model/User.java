package com.example.demo.validation.model;

import java.util.List;

import javax.validation.Valid;

import lombok.Data;

@Data
public class User {

	private Integer age;

	private String firstName;
	
	private String lastName;

	 @Valid
	 private UserAddrInfo userAddrInfo;
	 
	 @Valid
	 private List<UserLaptop> laptops;
	 

}
