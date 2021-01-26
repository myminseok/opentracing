package com.example.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableCircuitBreaker
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	RestTemplate restTemplate;


	@Autowired
	private WebClient.Builder webClientBuilder;


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
	@RequestMapping( value="/rt" )
	@HystrixCommand(fallbackMethod = "fallback")
	public String method_lb() throws InterruptedException {
		//log.info("calling");
		String response = restTemplate.getForObject("http://backend/",String.class);
		//log.info("Got response from [{}]", response);
		return new StringBuilder("Got response from: ").append(response).toString();
	}
	public String fallback() throws InterruptedException{
		return "fallback ";
	}

	@RequestMapping( value="/wc" )
	public String webclient() throws InterruptedException {

		WebClient webclient = webClientBuilder.build();

		Mono<String> response = webclient.get().uri("lb://backend/")
				.retrieve().bodyToMono(String.class);;

		return new StringBuilder("Got response from: ").append(response.block()).toString();
	}

}
