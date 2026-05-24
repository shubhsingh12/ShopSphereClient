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
public class OrderViewDto {

	private String userEmail;
	private String productId;
	private String orderId;
	private int quantity;
	private String status;
	private String productName;
    private double price;
    private String imageUrl;
    private LocalDateTime orderDateTime;
}
