package com.satyam.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.satyam.dto.OrderDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	RestTemplate restTemplate=new RestTemplate();
	String URL="https://shopspherewebservice-production.up.railway.app/order/api/";
	
	
	public boolean createOrder(OrderDto orderDto, String userEmail) {
		
		String API="order";
		
		HttpEntity<OrderDto> res=new HttpEntity<OrderDto>(orderDto);
		
		ResponseEntity<Boolean> resEntity= restTemplate.exchange(URL+API, HttpMethod.POST,res,Boolean.class);
		
		return resEntity.getBody();
	}


	public boolean cancleOrder(String orderId) {
		String API="/cancleOrder/"+orderId;
		HttpHeaders headers=new HttpHeaders();
		HttpEntity<?>httpEntity=new HttpEntity<>(headers);
		
		ResponseEntity<Boolean> responseEntity=restTemplate.exchange(URL+API, HttpMethod.DELETE,httpEntity,Boolean.class);
		return responseEntity.getBody();
	}

}
