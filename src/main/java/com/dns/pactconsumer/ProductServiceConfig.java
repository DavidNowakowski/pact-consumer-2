package com.dns.pactconsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class ProductServiceConfig {

    @Bean
    RestTemplate productRestTemplate(@Value("${provider.url:http://defaultValue:8085}") String url) {
    	log.info("Initializing rest template with url: "+url);
        return new RestTemplateBuilder().rootUri(url).build();
    }
	
}
