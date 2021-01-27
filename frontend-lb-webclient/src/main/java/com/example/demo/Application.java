package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.reactive.RetryableLoadBalancerExchangeFilterFunction;
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

	//@Autowired
	//private ReactorLoadBalancerExchangeFilterFunction lbFunction; //==> WebClientResponseException$ServiceUnavailable: 503 Service Unavailable from UNKNOWN

	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}

	@Autowired
	private WebClient.Builder webClientBuilder;


	/*
	org.springframework.web.reactive.function.client.WebClientResponseException$ServiceUnavailable: 503 Service Unavailable from UNKNOWN
	=>
	If the ReactorLoadBalancerExchangeFilterFunction bean is defined and set it to WebClient.filter()  then, Spring initializes two RoundRobinLoadBalancer object and set  the serviceId  with values from environments with key spring.cloud.loadbalancer.client.name and  one of environments has wrong value(IP) which causes no matching with target instances.
	in summary, 1) remove ReactorLoadBalancerExchangeFilterFunction bean , 2) set `spring.cloud.loadbalancer.retry.enabled=true.
	LoadBalancerClientConfiguration
	LoadBalancerClientFactory
	RoundRobinLoadBalancer;
	ReactorLoadBalancerClientAutoConfiguration;
	RetryableLoadBalancerExchangeFilterFunction
	 */
	@RequestMapping( value="/" )
	public Mono<String> webclient() throws InterruptedException {

		return webClientBuilder.baseUrl("lb://backend")
				//.filter(lbFunction) // WebClientResponseException$ServiceUnavailable: 503 Service Unavailable from UNKNOWN
				.build()
				.get()
				.retrieve().bodyToMono(String.class);
	}







}
