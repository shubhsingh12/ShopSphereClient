package com.satyam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class ProductDto {

	private String id;
	private String name;
	private String description;
	private double price;
	private int stock;
	private String imageUrl;
}
