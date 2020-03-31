package com.example.demo.validation.model;

import java.util.List;

import lombok.Data;

@Data
public class IngestionResponse {
	private List<String> errorMessages;

}
