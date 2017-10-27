package com.example.bootproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderItems {
	
	@Id
	@GeneratedValue
	private Integer id;	
	private Integer product_id;
	private String product_name;
	private String comment;
	private Integer quantity;
	private Integer tableno;
	private String order_type;
	private Float price;
	private String date;
	private Integer kot=0;
	private String mobile;
	private String address;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getTableno() {
		return tableno;
	}
	public void setTableno(Integer tableno) {
		this.tableno = tableno;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getKot() {
		return kot;
	}
	public void setKot(Integer kot) {
		this.kot = kot;
	}	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@Override
	public String toString() {
		return "OrderItems [id=" + id + ", product_id=" + product_id + ", product_name=" + product_name + ", comment="
				+ comment + ", quantity=" + quantity + ", tableno=" + tableno + ", order_type=" + order_type
				+ ", price=" + price + ", date=" + date + ", kot=" + kot + ", mobile=" + mobile + ", address=" + address
				+ "]";
	}
	
	public OrderItems() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderItems(Integer product_id, String product_name, String comment, Integer quantity, Integer tableno,
			String order_type, Float price, String date, Integer kot, String mobile, String address) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.comment = comment;
		this.quantity = quantity;
		this.tableno = tableno;
		this.order_type = order_type;
		this.price = price;
		this.date = date;
		this.kot = kot;
		this.mobile = mobile;
		this.address = address;
	}
	
		
}
