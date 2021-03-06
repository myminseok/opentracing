package com.example.demo;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CustomLoadBalancerConfiguration {

    //https://docs.spring.io/spring-cloud-commons/docs/3.0.0/reference/html/#custom-loadbalancer-configuration
    @Bean
    @Primary
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
            ConfigurableApplicationContext context) {

        ServiceInstanceListSupplier instanceListSupplier= ServiceInstanceListSupplier.builder()
                .withDiscoveryClient() // creates a new RoundRobinLoadBalancer with serviceId 'backend' from OritialRequestUrl: lb://backend
                .withHealthChecks() //it fails because, it creates a new RoundRobinLoadBalancer for healthcheck with serviceId '192.168.0.44' from OritialRequestUrl(http://192.168.0.44:8092/actuator/health)
                .build(context);

        return instanceListSupplier;
    }

//    @Bean
//    public ServiceInstanceListSupplier clientServiceInstanceListSupplier(
//            ConfigurableApplicationContext context) {
//        return new DemoServiceInstanceListSuppler("backend");
//    }

}

////  local supplier.
//class DemoServiceInstanceListSuppler implements ServiceInstanceListSupplier {
//
//    private final String serviceId;
//
//    DemoServiceInstanceListSuppler(String serviceId) {
//        this.serviceId = serviceId;
//    }
//
//    @Override
//    public String getServiceId() {
//        return serviceId;
//    }
//
//    @Override
//    public Flux<List<ServiceInstance>> get() {
//        return Flux.just(Arrays
//                .asList(new DefaultServiceInstance(serviceId + "1", serviceId, "localhost", 8083, false),
//                        new DefaultServiceInstance(serviceId + "2", serviceId, "localhost", 8083, false)));
//    }
//}