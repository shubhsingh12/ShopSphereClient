package com.satyam.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.satyam.controller.AuthController;
import com.satyam.dto.LoginDTO;
import com.satyam.dto.RegisterDTO;

@Service
public class AuthService {

   

	String URL="http://shopspherewebservice-production.up.railway.app/auth/";
	RestTemplate restTemplate=new RestTemplate();

   
	public boolean registerUser(RegisterDTO request) {
	
			String API="register";
			
		System.out.println(request.getEmail()+": Service Client");
			
			HttpEntity<RegisterDTO> requEntity = new HttpEntity<RegisterDTO>(request);
			
			ResponseEntity<Boolean> r=restTemplate.exchange(URL+API, HttpMethod.POST,requEntity,Boolean.class);
			
			return r.getBody();
		
	}


	public LoginDTO checkLogin(LoginDTO loginDTO) {
		String API="login";
		HttpEntity<LoginDTO> requEntity=new HttpEntity<LoginDTO>(loginDTO);
		
		ResponseEntity<LoginDTO> r=restTemplate.exchange(URL+API, HttpMethod.POST,requEntity,LoginDTO.class);
		
		return r.getBody();
	}

}
