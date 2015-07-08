package com.bskcare.ch.bo;

import java.io.Serializable;

public class DoctorInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String mobile;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
