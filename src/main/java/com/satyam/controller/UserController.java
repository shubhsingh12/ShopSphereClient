package com.satyam.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.satyam.ShopSphereClientApplication;
import com.satyam.dto.CartDto;
import com.satyam.dto.CartViewDto;
import com.satyam.dto.LoginDTO;
import com.satyam.dto.OrderDto;
import com.satyam.dto.OrderViewDto;
import com.satyam.dto.ProductDto;
import com.satyam.service.CartService;
import com.satyam.service.OrderService;
import com.satyam.service.ProductService;
import com.satyam.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

   
	private final UserService userService;
	private final OrderService orderService;
	private final ProductService productService;
	private final CartService cartService;

    
	@GetMapping("/userDashboard")
	public String userDashboard(HttpSession session, Model m ) {
		
		return "redirect:/auth/dashboard";
		
	}
	
	@GetMapping("/view-products")
	public String viewProducts(Model m) {
		
		m.addAttribute("products", userService.getAllProduct()) ;
		
		return "UserProduct";
	}
	@GetMapping("/view-orders")
	public String viewOrders(HttpSession session , Model m) {
		String userEmail=(String) session.getAttribute("userEmail");
		List<OrderDto> orderDto=userService.getAllOrders(userEmail);
		System.out.println(orderDto);
		
		List<OrderViewDto> finalList=new ArrayList<>();
		
		for(OrderDto dto: orderDto) {
			ProductDto productDto=productService.getProduct(dto.getProductId());
			OrderViewDto orderViewDto=OrderViewDto.builder()
					.quantity(dto.getQuantity())
					.userEmail(dto.getUserEmail())
					.productId(dto.getProductId())
					.orderDateTime(dto.getOrderDateTime())
					.orderId(dto.getId())
					.status(dto.getStatus())
					.productName(productDto.getName())
					.price(productDto.getPrice())
					.imageUrl(productDto.getImageUrl())
					.build();
			
			finalList.add(orderViewDto);
		}
		
		
		
		
		m.addAttribute("orders",finalList );
		return "viewOrder";
	}
	
	
	
	
	@PostMapping("/order")
	public String createOrder(@ModelAttribute OrderDto orderDto, HttpSession session,RedirectAttributes rd) {
		
		
		String userEmail=(String)session.getAttribute("userEmail");
		if(userEmail==null) {
			return "redirect:/login";
		}
		
		orderDto.setUserEmail(userEmail);
	boolean result=	orderService.createOrder(orderDto,userEmail);
		
		if(result) {
			rd.addFlashAttribute("msg","Order done!");
			cartService.removeFromCart(userEmail, orderDto.getProductId());
		return "redirect:/user/cartView";
		
		}else {
			
			rd.addFlashAttribute("msg","Order Failed Try Again!");
			return "redirect:/user/cartView";
		}
	}
	
	@PostMapping("/cartAdd")
	public String addInCart(@RequestParam String productId, @RequestParam int quantity, HttpSession session, RedirectAttributes ra) {
		
		String userEmail=(String) session.getAttribute("userEmail");
		System.out.println(userEmail);
		if (userEmail == null) {
	        return "redirect:/auth/login";
	    }
		boolean result=	cartService.addToCart(userEmail, productId ,quantity);
		if(result) {
			ra.addFlashAttribute("msg","Product added to cart!");
		}else {
			ra.addFlashAttribute("msg","Product Already Exist !");
		}
		
		return "redirect:/user/view-products";
	}
	
	@GetMapping("/cartView")
	public String viewCart(HttpSession session , Model m) {
		
		String userEmail=(String)session.getAttribute("userEmail");
		if(userEmail==null)return "redirect:/auth/login";
		
		List<CartDto> cartDtos= cartService.getCart(userEmail);
		
		List<CartViewDto> finalList=new  ArrayList<>();
		
		for(CartDto dtos: cartDtos) {
			
			ProductDto dto=productService.getProduct(dtos.getProductId());
			
			CartViewDto cartViewDto=CartViewDto.builder()
					.userEmail(dtos.getUserEmail())
					.productId(dtos.getProductId())
					.addTime(dtos.getAddTime())
					
					.productName(dto.getName())
					.price(dto.getPrice())
					.imageUrl(dto.getImageUrl())
					.stock(dto.getStock())
					.build();
			finalList.add(cartViewDto);
		}
		m.addAttribute("cartItems", finalList);
		
		return "ViewCart";
	}
	
	@GetMapping("/removeFromCart/{productId}")
	public String removeFromCart( @PathVariable String productId,HttpSession session, RedirectAttributes rd) {
		
		String userEmail=(String)session.getAttribute("userEmail");
		if(userEmail==null) return "redirect:/auth/login";
		System.out.println(userEmail+productId);
		
		cartService.removeFromCart(userEmail,productId);
		
		rd.addFlashAttribute("msg","Product Removed From Cart");
		
		System.out.println("Done!");
		return "redirect:/user/cartView";
	}
	@GetMapping("/cancelOrder/{orderId}")
	public String cancelOrder(@PathVariable String orderId) {
		boolean flag= orderService.cancleOrder(orderId);
		
		return "redirect:/user/view-orders";
	}
	
	
	@GetMapping("/search-products")
	public String searchProducts(@RequestParam String productName,Model m) {
		List<ProductDto> products= productService.searchProduct(productName);
		m.addAttribute("searchProducts", products);
		return "searchProduct";
	}
	
	
	
}
