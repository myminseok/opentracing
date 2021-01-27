package com.example.demo;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Value("${spring.profiles.active}")
	String profiles;

	@RequestMapping( value="/" )
	public String main() throws InterruptedException {
		log.info("called");
		return new StringBuilder().append(profiles).append(" ").append(new Date()).toString();
	}


	@RequestMapping( value="/timeout" )
	public String timeout() throws InterruptedException {
		if("backend01".equals(profiles)) {
			Thread.sleep(10000);
		}
		return new StringBuilder().append(profiles).append(" ").append(new Date()).toString();

	}



}
