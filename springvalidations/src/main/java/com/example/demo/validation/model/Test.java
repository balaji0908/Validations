package com.example.demo.validation.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		IngestionRequest ingestionRequest = new IngestionRequest();
		User user = new User();
		user.setFirstName("Amar");
		UserAddrInfo userAddrInfo = new UserAddrInfo();
		userAddrInfo.setCity("DOMBi");
		userAddrInfo.setState("Maharashtra");
		user.setUserAddrInfo(userAddrInfo);
		ingestionRequest.setUser(user);
		
		List<UserLaptop> laptops = new ArrayList<>();
		UserLaptop userLaptop = new UserLaptop();
		userLaptop.setName("Dell");
		userLaptop.setModel("Inspiron");
		laptops.add(userLaptop);
		
		user.setLaptops(laptops);
		
		String jsonInString = mapper.writeValueAsString(ingestionRequest);
		System.out.println("user = " + jsonInString);

	}

}
