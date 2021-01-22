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
public class LoadBalancerConfiguration {

    @Bean
    @Primary
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
            ConfigurableApplicationContext context) {

        ServiceInstanceListSupplier instanceListSupplier= ServiceInstanceListSupplier.builder()
                .withDiscoveryClient()
                .build(context);

        return instanceListSupplier;
    }

    @Bean

    public ServiceInstanceListSupplier clientServiceInstanceListSupplier(
            ConfigurableApplicationContext context) {
        return new DemoServiceInstanceListSuppler("backend");
    }


}


class DemoServiceInstanceListSuppler implements ServiceInstanceListSupplier {

    private final String serviceId;

    DemoServiceInstanceListSuppler(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(Arrays
                .asList(new DefaultServiceInstance(serviceId + "1", serviceId, "localhost", 8083, false),
                        new DefaultServiceInstance(serviceId + "2", serviceId, "localhost", 8083, false)));
    }
}
