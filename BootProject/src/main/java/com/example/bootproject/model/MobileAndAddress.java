package com.example.bootproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MobileAndAddress {

	@Id
	@GeneratedValue
	private Integer id;	
	private String mobileno;
	private String address;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	@Override
	public String toString() {
		return "MobileAndAddress [id=" + id + ", mobileno=" + mobileno + ", address=" + address + "]";
	}
	public MobileAndAddress() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MobileAndAddress(String mobileno, String address) {
		super();
		this.mobileno = mobileno;
		this.address = address;
	}	
	
	
	
}
