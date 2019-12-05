package com.example.servicea.controller;

import java.net.URI;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.servicea.dto.Student;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RequestMapping("/servicea")
@RestController
public class ServiceAController {

	@Autowired
    private LoadBalancerClient loadBalancer;

	@GetMapping("/greet")
	@HystrixCommand(fallbackMethod = "serviceBFallback")
	public String greetAService() {
		ServiceInstance instance = loadBalancer.choose("serviceb");
        URI storesUri = URI.create(String.format("http://%s:%s/serviceb/greet", instance.getHost(), instance.getPort()));
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(storesUri, String.class);

        return "Hello From Service A and " + result;
	}

	@GetMapping("/echoStudentName/{name}")
	public String echoStudentName(@PathVariable(name = "name") String name) {
		return "Hello  <strong style=\"color: red;\">" + name + " </strong> Responsed on : " + new Date();
	}

	@GetMapping("/getStudentDetails/{name}")
	public Student getStudentDetails(@PathVariable(name = "name") String name) {
		return new Student(name, "Kandy", "MCA");
	}

	public String serviceBFallback() {
	    return "Service B is unavailable";
	}
}
