package com.example.bootproject.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class OrderHistory {
	@Id
	@GeneratedValue
	private Integer id;
	private Integer	table_no;
	private String order_type;
	private String mobileno;
	private String address;
	private String date;
	private String total_Amount;
	private Integer total_quantity;
	private String gst;
	private String gtotal;

	@OneToMany(mappedBy="orderHistory",cascade=CascadeType.ALL)
	private List<Items> items;
	
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
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}	
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTotal_Amount() {
		return total_Amount;
	}
	public void setTotal_Amount(String total_Amount) {
		this.total_Amount = total_Amount;
	}	
	public Integer getTotal_quantity() {
		return total_quantity;
	}
	public void setTotal_quantity(Integer total_quantity) {
		this.total_quantity = total_quantity;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getGtotal() {
		return gtotal;
	}
	public void setGtotal(String gtotal) {
		this.gtotal = gtotal;
	}	
	public List<Items> getItems() {
		return items;
	}
	public void setItems(List<Items> items) {
		this.items = items;
	}	
	
	@Override
	public String toString() {
		return "OrderHistory [id=" + id + ", table_no=" + table_no + ", order_type=" + order_type + ", mobileno="
				+ mobileno + ", address=" + address + ", date=" + date + ", total_Amount=" + total_Amount
				+ ", total_quantity=" + total_quantity + ", gst=" + gst + ", gtotal=" + gtotal + ", items=" + items
				+ "]";
	}
	
	public OrderHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public OrderHistory(Integer table_no, String order_type, String date, String total_Amount, Integer total_quantity,
			String gst, String gtotal, List<Items> items) {
		super();
		this.table_no = table_no;
		this.order_type = order_type;
		this.date = date;
		this.total_Amount = total_Amount;
		this.total_quantity = total_quantity;
		this.gst = gst;
		this.gtotal = gtotal;
		this.items = items;
	}	
}
