package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    private static final Logger log = LoggerFactory.getLogger(Application.class);

	 @Autowired
	 private ReactorLoadBalancerExchangeFilterFunction lbFunction;

	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
	@Autowired
	private WebClient.Builder webClientBuilder;
	

	@RequestMapping( value="/" )
	public Mono<String> webclient() throws InterruptedException {
		return webClientBuilder.baseUrl("lb://backend")
				//.filter(lbFunction) //org.springframework.web.reactive.function.client.WebClientResponseException$ServiceUnavailable: 503 Service Unavailable from UNKNOWN
				.build()
				.get()
				.retrieve().bodyToMono(String.class);
	}







}
