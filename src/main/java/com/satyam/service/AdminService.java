package com.satyam.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.satyam.dto.ProductDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	String URL="http://shopspherewebservice-production.up.railway.app/admin/api/";
	RestTemplate restTemplate=new RestTemplate();
	
	public boolean saveProduct(ProductDto addRequest, MultipartFile img) throws IOException {
		
		String API="saveProduct";
	HttpHeaders headers=new HttpHeaders();
	headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	
	MultiValueMap<String, Object> body=new LinkedMultiValueMap<>();
	
	body.add("name", addRequest.getName());
	body.add("description",addRequest.getDescription());
	body.add("price", addRequest.getPrice());
	body.add("stock", addRequest.getStock());
	 
	body.add("img", new ByteArrayResource(img.getBytes()) {
		
		@Override
		public String getFilename() {
			return img.getOriginalFilename();
		}
	});
	
	HttpEntity<MultiValueMap<String, Object>> requestEntity=new HttpEntity<MultiValueMap<String,Object>>(body,headers);
	ResponseEntity<Boolean> response=restTemplate.postForEntity(URL+API, requestEntity, Boolean.class);
		return response.getBody();
	}

	public List<ProductDto> getAllProduct() {
		String API="getAllProduct";
		
		ResponseEntity<List<ProductDto>> res=restTemplate.exchange(URL+API, HttpMethod.GET,null,new ParameterizedTypeReference<List<ProductDto>>() {});
		
		return res.getBody();
	}

	public boolean deleteProduct(String id) {
		String API="/delete/"+id;
		
		ResponseEntity<Boolean> responseEntity=restTemplate.exchange(URL+API, HttpMethod.DELETE,null,Boolean.class);
		
		return responseEntity.getBody();
	}

	public boolean updateProduct(ProductDto productDto, MultipartFile imageFile) {
		String API="/updateProduct";
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body=new LinkedMultiValueMap<>();
		body.add("id", productDto.getId());
		body.add("name", productDto.getName());
		body.add("description",productDto.getDescription());
		body.add("price", productDto.getPrice());
		body.add("stock", productDto.getStock());
		 
		try {
			body.add("img", new ByteArrayResource(imageFile.getBytes()) {
				
				@Override
				public String getFilename() {
					return imageFile.getOriginalFilename();
				}
			});
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		HttpEntity<MultiValueMap<String, Object>> httpEntity=new HttpEntity<>(body,headers);
		ResponseEntity<Boolean> responseEntity=restTemplate.exchange(URL+API, HttpMethod.PUT,httpEntity,Boolean.class);
		
		return responseEntity.getBody();
	}

}
