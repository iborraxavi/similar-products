package com.xiborra.similarproducts.model;

import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product implements Comparable<Product> {

	private Integer id;
	private String name;
	private Double price;
	private Boolean availability;

	@Override
	public int compareTo(Product o) {
		return Comparator
				.comparing(Product::getAvailability).reversed()
				.thenComparing(Product::getPrice)
				.compare(this, o);
	}

}
