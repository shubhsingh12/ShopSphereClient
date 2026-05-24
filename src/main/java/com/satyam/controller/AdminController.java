package com.satyam.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.satyam.dto.LoginDTO;
import com.satyam.dto.ProductDto;
import com.satyam.dto.UserDto;
import com.satyam.service.AdminService;
import com.satyam.service.ProductService;
import com.satyam.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;






@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	private final AdminService adminService;
	private final ProductService productService;
	private final UserService userService;
	
	@GetMapping("/add-product")
	public String addProduct() {
		return "uploadProduct";
	}
	
	@PostMapping("/add-product")
	public String addProduct(@ModelAttribute ProductDto addRequest, @RequestPart MultipartFile img , Model m) throws IOException {
		
		 boolean result=adminService.saveProduct(addRequest, img);
		
		if(result) {
			m.addAttribute("msg", "Product Add SuccessFully!");
			return "uploadProduct";
			}else { 
				m.addAttribute("msg", "Product Already Exist!");
				return "uploadProduct";
				}
	}
	@PostMapping("/update")
	public String updateProduct(@ModelAttribute ProductDto productDto,@RequestPart MultipartFile imageFile , RedirectAttributes redirectAttributes ) {
		
		System.out.println(productDto);
		boolean result=adminService.updateProduct(productDto,imageFile);
		if(result) {
			redirectAttributes.addFlashAttribute("msg", "Product Updated Successfully!");
			
		}else {
			redirectAttributes.addFlashAttribute("msg", "Product Updation Failed!");
		}
		return "redirect:/admin/view-products";
	}
	
	@GetMapping("/view-products")
	public String viewProducts(Model m) {
		
		m.addAttribute("products", adminService.getAllProduct()) ;
		
		return "viewProduct";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable String id, RedirectAttributes redirectAttributes) {
		
		boolean result=adminService.deleteProduct(id);
		if(result) {
			redirectAttributes.addFlashAttribute("msg","Product Deleted Successfully!");
			
		}else {
			redirectAttributes.addFlashAttribute("msg","Product Not Found!");
		}
		return "redirect:/admin/view-products";
	}
	
	@GetMapping("/edit/{id}")
	public String editProduct(@PathVariable String id,Model m,RedirectAttributes redirectAttributes) {
		ProductDto productDto=productService.getProduct(id);
		
		if(productDto!=null) {
			m.addAttribute("product", productDto);
			return "productEdit";
		}else {
			redirectAttributes.addFlashAttribute("msg","Product Not Found!");
		}
		return "redirect:/admin/view-products";
	}
	
	@GetMapping("/users")
	public String getUsers(HttpSession session ,Model m) {
		List<UserDto> users=userService.getAllUser();
		m.addAttribute("users", users);
		 
		return "UserDetails";
	}
	
	@GetMapping("/toggle-status/{userId}")
	public String toggleStatus(@PathVariable String userId,HttpSession session,RedirectAttributes redirectAttributes) {
		LoginDTO logedUser=(LoginDTO)session.getAttribute("user");
		if(logedUser==null) {
			redirectAttributes.addFlashAttribute("msg","Plz Login !");
			return "redirect:/auth/login";
		}else if(!logedUser.getRole().equals("ADMIN")) {
			redirectAttributes.addFlashAttribute("msg","You Are Not Authorized!");
			return "redirect:/auth/login";
		}
		System.out.println(userId);
		boolean flag=userService.toggleStatus(userId);
		
		return "redirect:/auth/dashboard";
	}
	
	
}
