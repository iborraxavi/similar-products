package com.xiborra.similarproducts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.xiborra.similarproducts.exception.CustomExtractDataException;
import com.xiborra.similarproducts.service.SimilarProductsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SimilarProductsController {

	@Autowired
	private SimilarProductsService similarProductsService;

	@GetMapping("/product/{productId}/similar")
	public void findSimilarProducts(@PathVariable("productId") Integer productId) {
		try {
			similarProductsService.findSimilarProductsById(productId);
		} catch (CustomExtractDataException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
