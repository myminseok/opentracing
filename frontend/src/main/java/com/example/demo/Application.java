package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){ return new RestTemplate();}

	@RequestMapping( value="/" )
	public String method1() throws InterruptedException {
		log.info("calling");
		String response = restTemplate.getForObject("http://localhost:8083",String.class);
		log.info("Got response from backend [{}]", response);
		return new StringBuilder("Got response from backend: ").append(response).toString();
	}


	// client side loadbalancing using Ribbon.
	// https://github.com/spring-guides/gs-client-side-load-balancing
	// https://sabarada.tistory.com/54
	@RequestMapping( value="/lb" )
	public String method_lb() throws InterruptedException {
		log.info("calling");
		String response = restTemplate.getForObject("http://backend-service/",String.class);
		log.info("Got response from backend [{}]", response);
		return new StringBuilder("Got response from backend: ").append(response).toString();
	}
}
