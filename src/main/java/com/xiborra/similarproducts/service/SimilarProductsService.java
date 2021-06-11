package com.xiborra.similarproducts.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xiborra.similarproducts.exception.CustomExtractDataException;
import com.xiborra.similarproducts.properties.EndpointProperties;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SimilarProductsService {

	// Flag para substituir el id del producto en la llamada para obtener los
	// productos similares
	private static final String SIMILAR_PRODUCTS_ENDPOINT_FLAG = "{productId}";

	@Autowired
	private EndpointProperties endpointProperties;

	@Autowired
	private RestTemplate restTemplate;

	public void findSimilarProductsById(Integer productId) throws CustomExtractDataException {
		Integer[] similarProductIds = sendSimilarProductsRequest(productId);
		log.info("XAVI - findSimilarProductsById, similarProductIds: " + Arrays.asList(similarProductIds));
		
	}

	private Integer[] sendSimilarProductsRequest(Integer productId) throws CustomExtractDataException {
		ResponseEntity<Integer[]> similarProductsResponse = restTemplate.getForEntity(
				endpointProperties.getSimilarProducts().replace(SIMILAR_PRODUCTS_ENDPOINT_FLAG, productId.toString()),
				Integer[].class);
		if (similarProductsResponse.getStatusCode().equals(HttpStatus.OK)) {
			return similarProductsResponse.getBody();
		}
		throw new CustomExtractDataException(
				"Send similar products request with response code: " + similarProductsResponse.getStatusCodeValue());
	}
	
}
