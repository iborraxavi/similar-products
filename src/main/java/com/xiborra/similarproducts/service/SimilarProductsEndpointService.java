package com.xiborra.similarproducts.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xiborra.similarproducts.exception.CustomExtractDataException;
import com.xiborra.similarproducts.model.Product;
import com.xiborra.similarproducts.properties.EndpointProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * Gestión de las llamadas a los endpoints de productos similares
 * 
 * @author xiborra
 *
 */
@Service
@Slf4j
public class SimilarProductsEndpointService {

	// Flag para substituir el id del producto en la llamada para obtener los
	// productos similares
	private static final String SIMILAR_PRODUCTS_ENDPOINT_FLAG = "{productId}";

	@Autowired
	private EndpointProperties endpointProperties;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Envia la petición al endpoint en el que se obtienen los IDs de los productos
	 * similares
	 * 
	 * @param productId ID del producto del que queremos obtener los productos
	 *                  similares
	 * @return Lista de productos similares
	 * @throws CustomExtractDataException Error de conexión con el Endpoint
	 */
	public Integer[] sendSimilarProductsRequest(Integer productId) throws CustomExtractDataException {
		ResponseEntity<Integer[]> similarProductsResponse = restTemplate.getForEntity(
				endpointProperties.getSimilarProducts().replace(SIMILAR_PRODUCTS_ENDPOINT_FLAG, productId.toString()),
				Integer[].class);
		if (similarProductsResponse.getStatusCode().equals(HttpStatus.OK)) {
			return similarProductsResponse.getBody();
		}
		throw new CustomExtractDataException(
				"Send similar products request with response code: " + similarProductsResponse.getStatusCodeValue());
	}

	/**
	 * Envia una petición al endpoint por cada uno de los IDs que vienen en el
	 * parametro de entrada, se hace de forma asíncrona para mejorar el rendimiento
	 * 
	 * @param productIds IDs de los productos sobre los que queremos obtener la
	 *                   información
	 * @return Lista de productos con la información requerida y ordenados por
	 *         disponibilidad y precio
	 */
	public List<Product> sendProductInfoRequests(Integer[] productIds) {
		// Realizamos las peticiones de forma concurrente, las guardamos en una lista
		// para poder controlar cuando han terminado todas para parsear la respuesta
		List<CompletableFuture<ResponseEntity<Product>>> allFutures = new ArrayList<>();
		for (Integer productId : productIds) {
			allFutures.add(callOtherService(productId));
		}
		// Añadimos el control que nos avisará cuando terminen todas las peticiones
		CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();
		// Extraemos los productos de la respuesta
		return extractProductsFromResponses(allFutures);
	}

	@Async
	private CompletableFuture<ResponseEntity<Product>> callOtherService(Integer productId) {
		ResponseEntity<Product> responseObj = restTemplate.getForEntity(
				endpointProperties.getProductInfo().replace(SIMILAR_PRODUCTS_ENDPOINT_FLAG, productId.toString()),
				Product.class);
		return CompletableFuture.completedFuture(responseObj);
	}

	private List<Product> extractProductsFromResponses(
			List<CompletableFuture<ResponseEntity<Product>>> allResponseFutures) {
		List<Product> responseProducts = new ArrayList<>();
		for (CompletableFuture<ResponseEntity<Product>> completableFuture : allResponseFutures) {
			try {
				if (completableFuture.get().getStatusCode().equals(HttpStatus.OK)) {
					responseProducts.add(completableFuture.get().getBody());
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		Collections.sort(responseProducts);
		return responseProducts;
	}

}
