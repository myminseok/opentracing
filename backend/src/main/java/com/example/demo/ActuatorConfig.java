package com.example.demo;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActuatorConfig {

    @Autowired
    private MeterRegistry meterRegistry;
    public ActuatorConfig(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
        meterRegistry.gauge("custom_metric_alive", 1);
    }
}
