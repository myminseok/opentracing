package com.example.demo;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class LoadBalancerConfiguration {
    @Bean
    public IPing ribbonPing() {
        return new PingUrl();

        // for https
//        PingUrl pingUrl = new PingUrl();
//        pingUrl.setSecure(true);
//        return pingUrl;
    }

    @Bean
    public IRule ribbonRule() {
        return new RoundRobinRule();
        //return new AvailabilityFilteringRule();
    }

}
