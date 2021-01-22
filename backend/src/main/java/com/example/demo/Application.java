package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Date;


@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Value("${spring.application.name}")
	String applicationName;

	@RequestMapping( value="/" )
	public String method1() throws InterruptedException {
		log.info("called");
		//String response = restTemplate.getForObject("http://localhost:9000",String.class);
		return new StringBuilder().append(applicationName).append(" ").append(new Date()).toString();
	}


}
