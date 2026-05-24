package com.satyam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.satyam.dto.LoginDTO;
import com.satyam.dto.RegisterDTO;
import com.satyam.service.AuthService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	@PostMapping("/register")
	public String register( @ModelAttribute RegisterDTO request,Model m) {
	
		
		if(authService.registerUser(request)) {
			m.addAttribute("msg", "Registration Successfully!");
			return "login";
		}
		m.addAttribute("msg", "Email Already Exist!");
		return "signup" ;
	}
	
	@GetMapping("/register")
	public String register() {
		return "signup";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute LoginDTO loginDTO, Model m, HttpSession session ) {
	
		LoginDTO obj=authService.checkLogin(loginDTO);
		if(obj!=null && obj.getName()!=null) {
			m.addAttribute("username", obj.getName());
			session.setAttribute("user", obj);
			session.setAttribute("userEmail", obj.getEmail());
			if(obj.getRole().equals("USER"))
			return "userDashboard";
			else return "adminDashboard";
		}
		m.addAttribute("msg", "Wrong Credentials!");
		return "login";
	}
	@GetMapping("/dashboard")
	public String dashboard(HttpSession httpSession,RedirectAttributes redirectAttributes,Model m) {
		LoginDTO loginDTO=(LoginDTO)httpSession.getAttribute("user");
		if(loginDTO ==null) {
			redirectAttributes.addFlashAttribute("msg", "Plz Login First");
			return "redirect:/auth/login";
		}
		m.addAttribute("username", loginDTO.getName());
		if(loginDTO.getRole().equals("USER")) {
			return "userDashboard";
		}
		
		else {
			return "adminDashboard";
		}
	}
	
	
	@PostMapping("/logout")
	public String postMethodName(HttpSession session) {
		 session.invalidate();
		
		return "login";
	}
	
	
	
	
}
