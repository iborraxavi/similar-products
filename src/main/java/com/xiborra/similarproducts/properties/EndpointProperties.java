package com.xiborra.similarproducts.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource("classpath:properties/endpoint.properties")
@Getter
public class EndpointProperties {

	@Value("${similar.products}")
	private String similarProducts;
	
	@Value("${products.info}")
	private String productInfo;
	
}
