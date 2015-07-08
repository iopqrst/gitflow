package com.bskcare.ch.vo.tg;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tg_popularize_activity")
public class TgPopularizeActivity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer clientId;
	private String popularize;
	
	@Id
	@GeneratedValue
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
	public String getPopularize() {
		return popularize;
	}
	public void setPopularize(String popularize) {
		this.popularize = popularize;
	}
	
	
}
