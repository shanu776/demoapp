package com.example.bootproject.model;

public class DailySoldItems {
	String product;
	Integer total_quantity;	
			
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Integer getTotal_quantity() {
		return total_quantity;
	}
	public void setTotal_quantity(Integer total_quantity) {
		this.total_quantity = total_quantity;
	}
	
	public DailySoldItems(String product, Integer total_quantity) {
		super();
		this.product = product;
		this.total_quantity = total_quantity;
	}
	@Override
	public String toString() {
		return "DailySoldItems [product=" + product + ", total_quantity=" + total_quantity + "]";
	}
		
}
