package com.xiborra.similarproducts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.xiborra.similarproducts.exception.CustomExtractDataException;
import com.xiborra.similarproducts.model.Product;
import com.xiborra.similarproducts.service.SimilarProductsService;

import lombok.extern.slf4j.Slf4j;

/**
 * Endpoints para obtener los productos similares de un producto
 * 
 * @author xiborra
 *
 */
@RestController
@Slf4j
public class SimilarProductsController {

	@Autowired
	private SimilarProductsService similarProductsService;

	/**
	 * Endpoint a partir del que se obtiene la información relativa a los productor
	 * similares a partir del id del parámetro
	 * 
	 * @param productId ID del producto del que se quiere obtener los productos
	 *                  similares
	 * @return Lista de productos similares al del que se indica en el parametro
	 */
	@GetMapping("/product/{productId}/similar")
	public List<Product> findSimilarProducts(@PathVariable(name = "productId", required = true) Integer productId) {
		List<Product> products = null;
		try {
			products = similarProductsService.findSimilarProductsById(productId);
		} catch (CustomExtractDataException e) {
			products = new ArrayList<>();
			log.error(e.getMessage());
		} catch (Exception e) {
			products = new ArrayList<>();
			log.error(e.getMessage(), e);
		}
		log.info("Find similar products of: {}, found: {}", productId, products.size());
		return products;
	}

}
