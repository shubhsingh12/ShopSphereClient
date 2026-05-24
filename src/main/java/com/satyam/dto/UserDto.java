package com.satyam.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String id;

    private String name;

    private String email;

    private String phone;

    private Integer age;

    private String role;

    private String status;

    private LocalDateTime creationTime;

    private LocalDateTime lastLogin;

    private Integer totalOrders;

}