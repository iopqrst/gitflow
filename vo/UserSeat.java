package com.bskcare.ch.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user_seat")
public class UserSeat implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	/**
	 * 管理员id
	 */
	private Integer userId;
	/**
	 * tq登录名
	 */
	private String uin;
	/**
	 * tq登录密码
	 */
	private String password;
	/**
	 * 管理员对应的坐席号
	 */
	private String seatid;
	
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUin() {
		return uin;
	}
	public void setUin(String uin) {
		this.uin = uin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSeatid() {
		return seatid;
	}
	public void setSeatid(String seatid) {
		this.seatid = seatid;
	}
}
