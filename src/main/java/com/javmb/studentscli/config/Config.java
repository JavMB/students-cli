package com.javmb.studentscli.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "notas")
public class Config {

    @Min(0)
    private float min = 0;

    @Max(10)
    private float max = 10;

    public float getMin() { return min; }
    public void setMin(float min) { this.min = min; }

    public float getMax() { return max; }
    public void setMax(float max) { this.max = max; }
}