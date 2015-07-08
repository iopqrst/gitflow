package com.bskcare.ch.vo.tg;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tg_activity_card")
public class TgActivityCard implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final int STATUS_NO_SEND = 0;
	public static final int STATUS_SEND = 1;
	
	private Integer id;
	private String productCard;
	/**
	 * 激活码是否已经发送给客户： 0： 未发送   1：已发送
	 */
	private int status;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductCard() {
		return productCard;
	}
	public void setProductCard(String productCard) {
		this.productCard = productCard;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
