package com.example.bootproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Configuration {

	@Id
	Integer id=1;
	String restaurent_name;
	String address;
	String address_linr1;
	String mobile_no;
	String gst_no;
	String file_name;
	Float cgst;
	Float sgst;
	String tax_type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRestaurent_name() {
		return restaurent_name;
	}
	public void setRestaurent_name(String restaurent_name) {
		this.restaurent_name = restaurent_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress_linr1() {
		return address_linr1;
	}
	public void setAddress_linr1(String address_linr1) {
		this.address_linr1 = address_linr1;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getGst_no() {
		return gst_no;
	}
	public void setGst_no(String gst_no) {
		this.gst_no = gst_no;
	}	
	public Float getCgst() {
		return cgst;
	}
	public void setCgst(Float cgst) {
		this.cgst = cgst;
	}
	public Float getSgst() {
		return sgst;
	}
	public void setSgst(Float sgst) {
		this.sgst = sgst;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}	
	public String getTax_type() {
		return tax_type;
	}
	public void setTax_type(String tax_type) {
		this.tax_type = tax_type;
	}
	
	@Override
	public String toString() {
		return "Configuration [id=" + id + ", restaurent_name=" + restaurent_name + ", address=" + address
				+ ", address_linr1=" + address_linr1 + ", mobile_no=" + mobile_no + ", gst_no=" + gst_no
				+ ", file_name=" + file_name + ", cgst=" + cgst + ", sgst=" + sgst + ", tax_type=" + tax_type + "]";
	}	
}
