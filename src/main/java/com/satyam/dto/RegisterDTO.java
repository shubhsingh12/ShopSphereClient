package com.satyam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

	private String email;
	private String name;
	private String phone;
		private int age;
	private String address;
	private String password;
}
