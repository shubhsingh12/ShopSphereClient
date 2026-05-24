package com.satyam.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Backend returned 4xx / 5xx
    @ExceptionHandler(HttpClientErrorException.class)
    public String handleHttpClientError(HttpClientErrorException ex, Model model) {
    	String msg=ex.getResponseBodyAsString();
    	if(msg!=null) {
    		 model.addAttribute("error", msg);
    	}else {
    		 model.addAttribute("error", "Somthing went Wrong!");
    	}
       
        return "error";
    }

    // Backend not reachable
    @ExceptionHandler(ResourceAccessException.class)
    public String handleConnectionError(ResourceAccessException ex, Model model) {
    	String msg=ex.getMessage();
    	if(msg!=null) {
    		 model.addAttribute("error", msg);
    	}else {
    		 model.addAttribute("error", "Somthing went Wrong!");
    	}
        return "error";
    }

    // Any unknown error
    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
    	String msg=ex.getMessage();
    	if(msg!=null) {
    		 model.addAttribute("error", msg);
    	}else {
    		 model.addAttribute("error", "Somthing went Wrong!");
    	}
        return "error";
    }
}