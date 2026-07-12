package com.edsonsarmiento.reservacioncoworking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.coworking")
public class CoworkingProperties {
    
    private int horasOperativas;
    private String zontaHoraria;
}
