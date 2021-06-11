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

@RestController
@Slf4j
public class SimilarProductsController {

	@Autowired
	private SimilarProductsService similarProductsService;

	@GetMapping("/product/{productId}/similar")
	public List<Product> findSimilarProducts(@PathVariable("productId") Integer productId) {
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
