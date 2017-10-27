package com.example.bootproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootproject.model.MobileAndAddress;
import com.example.bootproject.model.Product;

@Service
public class MobileAndAddrDaoImpl {

	@Autowired
	MobileAndAddrDao moAddrDao;
	@Autowired
	EntityManagerFactory emf;
	
	public void add(MobileAndAddress moAddress){
		moAddrDao.save(moAddress);
	}
	
	public List<MobileAndAddress> findall(){
		return (List<MobileAndAddress>) moAddrDao.findAll();
	}
	
	@SuppressWarnings("unchecked")
	public List<MobileAndAddress> searchMobile(String keyword){
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();
		Query q=em.createNativeQuery("Select * from mobile_and_address where mobileno LIKE '"+keyword+"%' LIMIT 10",MobileAndAddress.class);
		List<MobileAndAddress> pro=q.getResultList();
		em.getTransaction().commit();
		em.close();
		return pro;
	}
}
