package com.satyam.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.satyam.dto.ProductDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	String URL="http://localhost:1998/product/api/";
	RestTemplate restTemplate=new RestTemplate();
	
	
	public ProductDto getProduct(String productId) {
		
		String API="getProduct/"+productId;
		
		return restTemplate.getForObject(URL+API, ProductDto.class);
		
	}


	public List<ProductDto> searchProduct(String productName) {
		String API="/searchProduct/"+productName;
		HttpHeaders headers= new HttpHeaders();
		HttpEntity<?> httpEntity=new HttpEntity<>(headers);
		ResponseEntity<List<ProductDto>> responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,new ParameterizedTypeReference<List<ProductDto>>() {
		});
		return responseEntity.getBody();
	}

}
