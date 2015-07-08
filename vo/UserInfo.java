package com.bskcare.ch.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.bskcare.ch.util.chat.OnLineUserUtils;

/**
 * 用户
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_userinfo")
public class UserInfo implements HttpSessionBindingListener {

	private static final long serialVersionUID = -6499174183408466842L;

	public static final int USER_NORMAL=0;
	public static final int USER_NOTNORMAL=1;
	public static final int USER_DELETE=2;
	
	private Integer id;
	private String name;
	private String account;
	private String pwd;
	private int roleId;
	private Date createTime;
	private int status;
	
	public int getRoleId() {
		return roleId;
	}

	public UserInfo() {
	}

	public UserInfo(String name, String account, String pwd, int roleId,
			Date createTime, int status) {
		this.roleId = roleId;
		this.account = account;
		this.name = name;
		this.pwd = pwd;
		this.createTime = createTime;
		this.status = status;

	}

	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int roleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pwd")
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "status")
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void valueBound(HttpSessionBindingEvent e) {
		OnLineUserUtils.getOnLineUserUtils().addUser(this);
	}

	public void valueUnbound(HttpSessionBindingEvent e) {
		OnLineUserUtils.getOnLineUserUtils().deleteUser(this);

	}
	
}