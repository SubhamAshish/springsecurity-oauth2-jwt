package com.example.springsecurity5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity5.service.ConfigurationService;

@RestController
@RequestMapping("/bypass")
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService;
	
	@GetMapping("/config")
	public boolean config() {
		return configurationService.config();
	}
}
