package com.satyam.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.satyam.controller.AdminController;
import com.satyam.dto.CartDto;
import com.satyam.dto.OrderDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final AdminController adminController;
	
	String URL="http://shopspherewebservice-production.up.railway.app/cart/api/";
	RestTemplate restTemplate=new RestTemplate();

   
	
	public boolean addToCart(String userEmail, String productId, int quantity) {
		
		CartDto cartDto=CartDto.builder()
				.userEmail(userEmail)
				.productId(productId)
				.quantity(quantity).build();
		
		String API="addToCart";
		
		HttpEntity<CartDto> httpEntity=new HttpEntity<CartDto>(cartDto);
		
		ResponseEntity<Boolean> res=restTemplate.exchange(URL+API, HttpMethod.POST,httpEntity,Boolean.class);
		
		return res.getBody();
		
	}

	public List<CartDto> getCart(String userEmail) {
	
		String API="getCart/"+userEmail;
		
		ResponseEntity<List<CartDto>> response=restTemplate.exchange(
				
				URL+API, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CartDto>>() {}
				
				
				);
		return response.getBody();
		
	}

	public boolean removeFromCart(String userEmail, String ProductId) {
		String API="removeFromCart/"+userEmail+"/"+ProductId;
		
		ResponseEntity<Boolean> res=restTemplate.exchange(URL+API, HttpMethod.DELETE,null, Boolean.class);
		
		return res.getBody();
	}

}
