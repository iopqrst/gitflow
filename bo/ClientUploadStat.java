package com.bskcare.ch.bo;

import java.io.Serializable;
import java.util.Date;

public class ClientUploadStat implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer clientId;
	private Date testDateTime;
	private Date uploadDateTime;
	private double val1;
	private String val2;
	private Integer state;
	private Integer dispose;
	private Integer type;
	private Integer stype;
	private String name;
	private String mobile;
	private String source;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Date getTestDateTime() {
		return testDateTime;
	}
	public void setTestDateTime(Date testDateTime) {
		this.testDateTime = testDateTime;
	}
	public Date getUploadDateTime() {
		return uploadDateTime;
	}
	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}
	
	public double getVal1() {
		return val1;
	}
	public void setVal1(double val1) {
		this.val1 = val1;
	}
	public String getVal2() {
		return val2;
	}
	public void setVal2(String val2) {
		this.val2 = val2;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getDispose() {
		return dispose;
	}
	public void setDispose(Integer dispose) {
		this.dispose = dispose;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStype() {
		return stype;
	}
	public void setStype(Integer stype) {
		this.stype = stype;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}
