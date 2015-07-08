package com.bskcare.ch.bo;

import java.io.Serializable;

import com.bskcare.ch.vo.ClientExtend;

public class ClientExtendExtend extends ClientExtend implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String mobile;
	private Integer areaId;
	
	
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
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
}
