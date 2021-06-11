package com.xiborra.similarproducts.model;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class Product {

	private Integer id;
	private String name;
	private Double price;
	private Boolean availability;
	
}
