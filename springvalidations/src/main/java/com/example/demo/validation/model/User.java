package com.example.demo.validation.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class User {

	private Integer age;

	private String firstName;
	
	@Min(value=5, message="Please insert at least 5 characters")
	private String lastName;

	 @Valid
	 private UserAddrInfo userAddrInfo;
	 
	 @Valid
	 private List<UserLaptop> laptops;
	 

}
