package com.example.bootproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootproject.model.Product;

@Service
public class ProductDaoImpl {
	@Autowired
	ProductDao productDao;
	@Autowired
	EntityManagerFactory emf;
	
	public void add(Product product) {
		productDao.save(product);
	}
	
	public List<Product> findall() {
		return (List<Product>) productDao.findAll();
	}
	
	public Product findone(Integer id){
		return productDao.findOne(id);
	}
	
	public void delete(Integer id) {
		productDao.delete(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> searchProduct(String keyword){
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();
		Query q=em.createNativeQuery("Select * from product where short_name LIKE '"+keyword+"%' LIMIT 10",Product.class);
		List<Product> pro=q.getResultList();
		em.getTransaction().commit();
		em.close();
		return pro;
	}
		
}
