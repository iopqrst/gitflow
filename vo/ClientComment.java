package com.bskcare.ch.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *用户备注表
 */
@Entity
@Table(name = "t_client_comment")
public class ClientComment {

	/** 备注等级 **/

	/** 普通 **/
	public static final int LEVEL_NORMAL = 0;

	/** 特殊 **/
	public static final int LEVEL_SPECIAL = 1;

	private Integer id;
	private Integer clientId;
	/** 备注信息 **/
	private String comment;
	private Integer userId;
	/** 备注等级 0：普通 1：特殊 **/
	private int level;

	private Date createTime;
	private String title;

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
