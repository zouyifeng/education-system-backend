package com.laboratory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractController {
	
	@Autowired
	protected ObjectMapper objectMapper;
	
}
