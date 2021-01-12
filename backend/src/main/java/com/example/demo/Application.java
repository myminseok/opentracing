package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    private static final Logger log = LoggerFactory.getLogger(Application.class);


	@RequestMapping( value="/" )
	public String method1() throws InterruptedException {
		log.info("called");
		//String response = restTemplate.getForObject("http://localhost:9000",String.class);
		return new Date().toString();
	}


}