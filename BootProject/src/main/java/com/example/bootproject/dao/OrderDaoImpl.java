package com.example.bootproject.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootproject.model.DailySoldItems;
import com.example.bootproject.model.Items;
import com.example.bootproject.model.OrderHistory;
import com.example.bootproject.model.OrderItems;

@Service
public class OrderDaoImpl {
@Autowired
OrderDao orderDao;
@Autowired
OrderHistoryDao orderHistoryDao;
@Autowired
ItemsDao itemsDao;
@Autowired
EntityManagerFactory emf;

	public void add(OrderItems order) {
		orderDao.save(order);
	}

	public List<OrderItems> findall() {
		return (List<OrderItems>) orderDao.findAll();
	}
	
	public OrderItems findone(Integer id) {
		return orderDao.findOne(id);
	}
	
	public List<OrderItems> findallAccTable(Integer table) {
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();
		Query q=em.createNativeQuery("Select * from order_items where tableno="+table,OrderItems.class);
		List<OrderItems> pro=q.getResultList();
		em.getTransaction().commit();
		em.close();
		return pro;
	}

	public boolean isTableAvailable(Integer table) {
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();
		Query q=em.createNativeQuery("Select * from order_items where tableno="+table,OrderItems.class);
		List<OrderItems> pro=q.getResultList();
		em.getTransaction().commit();
		em.close();
		if(pro.size()>0)
			return false;
		else
			return true;		
	}
	
	public void deleteOrderAccTable(Integer table) {
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();
		Query q=em.createNativeQuery("delete from order_items where tableno="+table);
		int pro=q.executeUpdate();
		em.getTransaction().commit();
		em.close();
		System.out.println(pro);	
	}
	
	public void delete(int id){
		orderDao.delete(id);
	}
	
	public OrderHistory saveOrderHistory(OrderHistory orderHistory) {
		return orderHistoryDao.save(orderHistory);
	}
	
	public List<OrderHistory> getOrderHistory() {
		return (List<OrderHistory>) orderHistoryDao.findAll();
	}	
	
	public OrderHistory getOneOrderHistory(Integer id) {
		return orderHistoryDao.findOne(id);
	}
	
	public List<OrderHistory> getCurrentDateHistory(String date) {
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();
		Query q=em.createNativeQuery("Select * from order_history where date='"+date+"'",OrderHistory.class);
		List<OrderHistory> pro=q.getResultList();
		em.getTransaction().commit();
		em.close();
		return pro;
	}
	
	public List<OrderHistory> findTakeayayAccCDate(String date) {
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();
		Query q=em.createNativeQuery("Select * from order_history where order_type='takeaway' and date='"+date+"'",OrderHistory.class);
		List<OrderHistory> pro=q.getResultList();
		em.getTransaction().commit();
		em.close();
		return pro;
	}
	
	public List<OrderHistory> findDiliveryAccCDate(String date) {
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();
		Query q=em.createNativeQuery("Select * from order_history where order_type='delivery' and date='"+date+"'",OrderHistory.class);
		List<OrderHistory> pro=q.getResultList();
		em.getTransaction().commit();
		em.close();
		return pro;
	}
	
	public void deleteOrderHistory(OrderHistory orderHistory) {
		orderHistoryDao.delete(orderHistory);
	}
	
	public List<DailySoldItems> dailysoldItems(String date){
		List<DailySoldItems> dailyoldItem = new ArrayList<>();
		EntityManager em=emf.createEntityManager();
		em.getTransaction().begin();
		Query q=em.createNativeQuery("SELECT i.prodoct,SUM(i.quantity) quantity "
				+ "FROM order_history o INNER JOIN items i ON o.id=i.order_history_id "
				+ "group by i.product_id");
		List<Object[]> list = q.getResultList();
		for (Object[] obj : list){
		    dailyoldItem.add(new DailySoldItems(obj[0].toString(), Integer.parseInt(obj[1].toString())));
		}		
		em.getTransaction().commit();
		em.close();
		return dailyoldItem;
	}
		
}
