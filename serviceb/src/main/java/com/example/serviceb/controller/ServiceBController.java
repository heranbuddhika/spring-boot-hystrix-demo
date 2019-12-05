package com.example.serviceb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RequestMapping("serviceb")
@RestController
public class ServiceBController {

	@GetMapping("/greet")
	@HystrixCommand
	public String greetBService() {
		return "Hello From Service B";
	}
}
