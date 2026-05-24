package com.satyam.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

	private String userEmail;
	private String productId;
	private String id;
	private int quantity;
	private String status;
	private LocalDateTime orderDateTime;
}
