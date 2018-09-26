package com.capgemini.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.DAOLayerAdmin;
import com.capgemini.dao.DAOLayerCustomer;
import com.capgemini.dao.DAOLayerMerchant;
import com.capgemini.dto.*;
import com.capgemini.exception.CapStoreException;

@Service
public class ServiceLayerImpl implements ServiceLayer
{
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DAOLayerAdmin daorefAdmin;
	@Autowired
	private DAOLayerCustomer daorefCustomer;
	@Autowired
	private DAOLayerMerchant daorefMerchant;

	@Override
	public Admin validateAdmin(Admin admin)
	{
		return daorefAdmin.validateAdmin(admin);
	}

	
	@Override
	public Admin findById(int id) {
		return daorefAdmin.findById(id);
	}


	@Override
	public Customers createAccount(Customers cusDTO) {
		return daorefCustomer.createAccount(cusDTO);
	}


	@Override
	public Customers validateCustomerForLogin(Customers customer) throws CapStoreException {
		return daorefCustomer.validateCustomerForLogin(customer);
	}


	@Override
	public String check() {
		return daorefCustomer.check();
	}
	
	@Override
	public List<Products> findAll() {
		String sql="select product from Products product";
		TypedQuery<Products> tq=em.createQuery(sql,Products.class);
		List<Products> lm=tq.getResultList();
		return lm;
	}


	@Override
	public List<CustomerAddress> getAddresses(int customerId) {
		System.out.println("Service");
		return daorefCustomer.getAddresses(customerId);
	}
	@Override
	public Customers findCById(int id) {
		return daorefCustomer.findByID(id);
	}
	
	@Override
	public Products findPById(int id) {
		String sql="select pd from Products pd where pd.productId =:id" ;
		TypedQuery<Products> tq=em.createQuery(sql,Products.class);
		tq.setParameter("id", id);
		
		return tq.getSingleResult();
	}

	@Override
	public List<Products> findByCategory(Enum cat) {
		
		String sql="select product from Products product where productCategory=:cat";
		TypedQuery<Products> tq=em.createQuery(sql,Products.class);
		tq.setParameter("cat", cat);
		List<Products> lm=tq.getResultList();
		return lm;
	}


	@Override
	public List<CustomerOrders> getOrders(Integer cust) {
		
		return daorefCustomer.getOrders(cust);
	}


}