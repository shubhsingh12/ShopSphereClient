package com.satyam.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.satyam.dto.LoginDTO;
import com.satyam.dto.OrderDto;
import com.satyam.dto.ProductDto;
import com.satyam.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	String URL="http://shopspherewebservice-production.up.railway.app/user/api/";
	RestTemplate restTemplate=new RestTemplate();
	
	public List<ProductDto> getAllProduct() {
		String API="getAllProduct";
		
		ResponseEntity<List<ProductDto>> res=restTemplate.exchange(URL+API, HttpMethod.GET,null,new ParameterizedTypeReference<List<ProductDto>>() {});
		
		return res.getBody();
	}

	public List<OrderDto> getAllOrders(String userEmail) {
		String API="getAllOrder";
		OrderDto orderDto=new OrderDto();
		orderDto.setUserEmail(userEmail);
		HttpEntity<OrderDto> headers=new HttpEntity<OrderDto>(orderDto);
		ResponseEntity<List<OrderDto>> res=restTemplate.exchange(URL+API, HttpMethod.POST,headers, new ParameterizedTypeReference<List<OrderDto>>() {});
		
		
		return res.getBody();
	}

	public LoginDTO getUser(String userEmail) {
		String API="getUser/"+userEmail;
		return restTemplate.getForObject(URL+API, LoginDTO.class);
	}

	public List<UserDto> getAllUser() {
		String API="/getAllUser";
		HttpHeaders headers=new HttpHeaders();
		HttpEntity<?> httpEntity=new HttpEntity<>(headers);
		ResponseEntity<List<UserDto>>responseEntity=restTemplate.exchange(URL+API, HttpMethod.GET,httpEntity,new ParameterizedTypeReference<List<UserDto>>() {});
		
		return responseEntity.getBody();
	}

	public boolean toggleStatus(String userId) {
		String API="/toggleStatus/"+userId;
		System.out.println(userId);
		HttpHeaders headers=new HttpHeaders();
		HttpEntity<?> httpEntity=new HttpEntity<>(headers);
		ResponseEntity<Boolean> responseEntity=restTemplate.exchange(URL+API, HttpMethod.PUT,httpEntity,Boolean.class);
		
		return responseEntity.getBody();
	}
}
