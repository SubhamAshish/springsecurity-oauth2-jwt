package com.example.springsecurity5.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	@GetMapping("/hello")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String welcome() {
		return "hello world";
	}
	
	
}
