package com.xiborra.similarproducts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiborra.similarproducts.exception.CustomExtractDataException;
import com.xiborra.similarproducts.model.Product;

@Service
public class SimilarProductsService {

	@Autowired
	private SimilarProductsEndpointService similarProductsEndpointService;

	public List<Product> findSimilarProductsById(Integer productId) throws CustomExtractDataException {
		Integer[] similarProductIds = similarProductsEndpointService.sendSimilarProductsRequest(productId);
		if (similarProductIds == null || similarProductIds.length == 0) {
			return new ArrayList<>();
		}
		return similarProductsEndpointService.sendProductInfoRequests(similarProductIds);
	}

}
