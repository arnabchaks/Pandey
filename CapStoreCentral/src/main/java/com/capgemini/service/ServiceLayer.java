package com.capgemini.service;

import java.util.List;

import com.capgemini.dto.Admin;
import com.capgemini.dto.CustomerAddress;
import com.capgemini.dto.CustomerOrders;
import com.capgemini.dto.Customers;
import com.capgemini.dto.Products;
import com.capgemini.exception.CapStoreException;

public interface ServiceLayer
{
	public Admin validateAdmin(Admin admin);
	public Admin findById(int id);
	public Customers createAccount(Customers cusDTO);
	public Customers validateCustomerForLogin(Customers customer) throws CapStoreException;
	public String check();
	List<Products> findAll();
	List<CustomerAddress> getAddresses(int customerId);
	Customers findCById(int id);
	public List<Products> findByCategory(Enum cat);
	public List<CustomerOrders> getOrders(Integer cust);
	Products findPById(int id);
}