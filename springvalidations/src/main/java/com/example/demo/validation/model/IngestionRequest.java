package com.example.demo.validation.model;

import javax.validation.Valid;

import lombok.Data;

@Data
public class IngestionRequest {
	@Valid
	private User user;

}
