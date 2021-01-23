package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
//@EnableCircuitBreaker
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    private static final Logger log = LoggerFactory.getLogger(Application.class);


	@Autowired
	private WebClient.Builder webClientBuilder;

	// it works with eureka. but takes time to drop the failed instance.
	@RequestMapping( value="/webclient" )
	public Mono<String> webclient() throws InterruptedException {
		Mono<String> response = webClientBuilder.build().get().uri("http://backend/")
				.retrieve().bodyToMono(String.class);
		return response;
	}

}
