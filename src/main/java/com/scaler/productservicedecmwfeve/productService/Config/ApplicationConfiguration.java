package com.scaler.productservicedecmwfeve.productService.Config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {
    // here we will define a bean namely restTemplate to call thirdPartyApi's from multiple classes
    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplateBuilder().build();
    }

}
