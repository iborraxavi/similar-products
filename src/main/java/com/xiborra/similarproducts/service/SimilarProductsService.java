package com.xiborra.similarproducts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiborra.similarproducts.exception.CustomExtractDataException;
import com.xiborra.similarproducts.model.Product;

/**
 * Gestión de las llamadas a los enpoints para obtener la información de los
 * productos similares
 * 
 * @author xiborra
 *
 */
@Service
public class SimilarProductsService {

	@Autowired
	private SimilarProductsEndpointService similarProductsEndpointService;

	/**
	 * Se hacen dos llamadas a dos endpoints diferentes, la primera obtenemos los
	 * IDs de los productos similares y en la siguiente obtenemos la información
	 * relativa a estos productos
	 * 
	 * @param productId ID del producto de los que vamos a obtener los productos similares
	 * @return Lista de productos similares ordenados por disponibilidad y precio
	 * @throws CustomExtractDataException
	 */
	public List<Product> findSimilarProductsById(Integer productId) throws CustomExtractDataException {
		Integer[] similarProductIds = similarProductsEndpointService.sendSimilarProductsRequest(productId);
		if (similarProductIds == null || similarProductIds.length == 0) {
			return new ArrayList<>();
		}
		return similarProductsEndpointService.sendProductInfoRequests(similarProductIds);
	}

}
