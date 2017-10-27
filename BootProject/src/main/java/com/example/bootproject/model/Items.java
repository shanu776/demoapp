package com.example.bootproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Items {

	@Id
	@GeneratedValue
	private Integer id;
	private Integer table_no;
	private Integer product_id;
	private String prodoct;
	private Integer quantity;
	private Float price;
	private String comment;
	@ManyToOne	
	@JoinColumn(name = "order_history_id")
	private OrderHistory orderHistory;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTable_no() {
		return table_no;
	}
	public void setTable_no(Integer table_no) {
		this.table_no = table_no;
	}	
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProdoct() {
		return prodoct;
	}
	public void setProdoct(String prodoct) {
		this.prodoct = prodoct;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public OrderHistory getOrderHistory() {
		return orderHistory;
	}
	public void setOrderHistory(OrderHistory orderHistory) {
		this.orderHistory = orderHistory;
	}	
	
	@Override
	public String toString() {
		return "Items [id=" + id + ", table_no=" + table_no + ", product_id=" + product_id + ", prodoct=" + prodoct
				+ ", quantity=" + quantity + ", price=" + price + ", comment=" + comment + ", orderHistory="
				+ orderHistory + "]";
	}
	
	public Items() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Items(Integer table_no, String prodoct, Integer quantity, Float price) {
		super();
		this.table_no = table_no;
		this.prodoct = prodoct;
		this.quantity = quantity;
		this.price = price;
	}
	public Items(Integer table_no, Integer product_id, String prodoct, Integer quantity, Float price, String comment,
			OrderHistory orderHistory) {
		super();
		this.table_no = table_no;
		this.product_id = product_id;
		this.prodoct = prodoct;
		this.quantity = quantity;
		this.price = price;
		this.comment = comment;
		this.orderHistory = orderHistory;
	}
	
	
	
/*	public Items(Integer table_no, Integer product_id, String prodoct, Integer quantity, Float price,
			OrderHistory orderHistory) {
		super();
		this.table_no = table_no;
		this.product_id = product_id;
		this.prodoct = prodoct;
		this.quantity = quantity;
		this.price = price;
		this.orderHistory = orderHistory;
	}*/
	
	
	
	
}
