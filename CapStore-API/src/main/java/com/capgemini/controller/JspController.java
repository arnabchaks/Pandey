package com.capgemini.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.capgemini.dto.AddressList;
import com.capgemini.dto.Admin;
import com.capgemini.dto.CustomerAddress;
import com.capgemini.dto.CustomerOrders;
import com.capgemini.dto.Customers;
import com.capgemini.dto.OrderList;
import com.capgemini.dto.ProductListDummy;
import com.capgemini.dto.Products;

@Controller
public class JspController {

	Customers obj;
	
	@RequestMapping("/")
	public String showWelcomePage(ModelMap map){
		
		return "welcome";
	}
	
	@RequestMapping("/adminlogin")
	public String showAdminLoginPage(ModelMap map,@ModelAttribute("admin") Admin admin){
		
		map.addAttribute("admin", admin);
		return "adminlogin";
	}
	@RequestMapping("/signUpPage")
	public String showSignUpPage(ModelMap map, @ModelAttribute("customer") Customers cus){
		
		map.addAttribute("customer", cus);
		return "signUpPage";
	}
	@RequestMapping("/signUp")
	public String signUpCostumer(ModelMap map,@Valid @ModelAttribute("customer") Customers cus, BindingResult error){
		
		if(error.hasErrors())
		{
			System.out.println(error.toString());
			map.addAttribute("customer", cus);
			return "signUpPage";
		}
	
		RestTemplate restTemplate = new RestTemplate();
		Customers message = restTemplate.postForObject("http://localhost:9090/signUpCustomer",cus, Customers.class);
		if(message.getCustomerName().equals("Dummy"))
		{	
			map.addAttribute("errorSignUp", "Credentials are Wrong");
			map.addAttribute("custDetails", cus); 
			return "signUpPage";
		}
		else {
			System.out.println("Tes");
			map.addAttribute("custDetails", cus);
			return "customerHomePage";
		}
	}
	
	@RequestMapping("/merchantlogin")
	public String showMerchantLoginPage(ModelMap map){
		
		return "welcome";
	}
	
	@RequestMapping("/customerlogin")
	public String showConsumerLoginPage(ModelMap map){
		
		return "customerLogin";
	}
	@RequestMapping("/adminlogincheck")
	public String  adminLoginCheck(ModelMap map,@ModelAttribute("admin") Admin admin){
		
		RestTemplate restTemplate = new RestTemplate();
		
		Admin message = restTemplate.postForObject("http://localhost:9090/loginAdmin",admin, Admin.class);
		map.addAttribute("adminDetails", message);
		 //check
		 return "adminHomePage";
	}
	@RequestMapping("/customerlogincheck")
	public String  customerLoginCheck(ModelMap map,@ModelAttribute("customer") Customers customer){
		
		RestTemplate restTemplate = new RestTemplate();
		
		obj = restTemplate.postForObject("http://localhost:9090/loginCustomer",customer, Customers.class);
		map.addAttribute("custDetails", obj);
		
		ProductListDummy response = restTemplate.getForObject("http://localhost:9090/productList?category=all", ProductListDummy.class);
		List<Products> products = response.getProducts();
		map.addAttribute("productList", products);
		
		return "customerHomePage";
	}
	@RequestMapping("/homePage")
	public String showHomePage(ModelMap map, String category){
		RestTemplate restTemplate = new RestTemplate();
		
		ProductListDummy response = restTemplate.getForObject("http://localhost:9090/productList?category="+category, ProductListDummy.class);
		List<Products> products = response.getProducts();
		
		map.addAttribute("custDetails", obj);
		map.addAttribute("productList", products);
		return "customerHomePage";
	}
	
	@RequestMapping("/custDisplay")
	public String showCustDetails(ModelMap map, Integer id) {
		
		System.out.println("Tes"+id);
		
		map.addAttribute("custId",id);
		return "custDisplay";
	}
	
	@RequestMapping("/custInfo")
	public String showCustInfo(ModelMap map, Integer id) {
		RestTemplate restTemplate = new RestTemplate();
		
		AddressList response = restTemplate.getForObject("http://localhost:9090/addresses?cust="+id, AddressList.class);
		List<CustomerAddress> addresses = response.getAdd();
		map.addAttribute("addresses", addresses);
		
		Customers obj = restTemplate.getForObject("http://localhost:9090/findById?id="+id, Customers.class);
		map.addAttribute("custDetails", obj);
		
		return "custInfo";
	}
	@RequestMapping("/orderInfo")
	public String showOrderInfo(ModelMap map, Integer id) {
		RestTemplate restTemplate = new RestTemplate();
		OrderList response = restTemplate.getForObject("http://localhost:9090/orders?cust="+id, OrderList.class);
		List<CustomerOrders> orders = response.getAdd();
		System.out.println("addr"+orders);
		map.addAttribute("custOrders", orders);
		Customers obj = restTemplate.getForObject("http://localhost:9090/findById?id="+id, Customers.class);
		System.out.println("after Central***** check JspHandler***"+obj.getCustomerName());
		 map.addAttribute("custDetails", obj);
		return "CustOrders";
	}

	@RequestMapping("pdetails")
	public String pubDeets(ModelMap map, Integer id) {
		RestTemplate restTemplate = new RestTemplate();
		Products response = restTemplate.getForObject("http://localhost:9090/pdetails?id="+id, Products.class);
		map.addAttribute("product", response);
		return "pdetails";
	}
	
	@ModelAttribute("admin")
	Admin getCar() {
		Admin ac = new Admin();
		
		return ac;
	}
	@ModelAttribute("customer")
	Customers getCustomer() {
		Customers ac = new Customers();
		
		return ac;
	}
	
	
}