package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_physical_item")
public class PhysicalItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer physicalId;
	private Integer clientId;
	private Integer pdId;
	private String result;
	private int type;
	private Date physicalTime;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPhysicalId() {
		return physicalId;
	}

	public void setPhysicalId(Integer physicalId) {
		this.physicalId = physicalId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getPhysicalTime() {
		return physicalTime;
	}

	public void setPhysicalTime(Date physicalTime) {
		this.physicalTime = physicalTime;
	}

	@Override
	public String toString() {
		return "PhysicalItem [clientId=" + clientId + ", id=" + id + ", pdId="
				+ pdId + ", physicalId=" + physicalId + ", physicalTime="
				+ physicalTime + ", result=" + result + ", type=" + type + "]";
	}

}
