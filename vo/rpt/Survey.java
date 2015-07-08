package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 问卷
 */
@Entity
@Table(name = "rpt_survey")
public class Survey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int STATUS_NO = 0;
	public static final int STATUS_YES = 1;

	private Integer id;
	private String name;// 问卷内容
	private Integer userId;// 用户主键编号
	private Integer status;// 状态
	private Date createTime;// 创建时间

	@Id
	@GeneratedValue
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Survery [createTime=" + createTime + ", id=" + id + ", name="
				+ name + ", status=" + status + ", userId=" + userId + "]";
	}

}
