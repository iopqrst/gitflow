package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 推广来源用户
 */
@Entity
@Table(name = "t_agent_user")
public class TgAgentUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;// 推广来源用户姓名
	private String url;// 用户编号
	private Date createTime;// 创建时间
	private Integer type;// 推广来源信息类别

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TgAgentUser() {
		super();
	}

	public TgAgentUser(String name, String url, Date createTime, Integer type) {
		super();
		this.name = name;
		this.url = url;
		this.createTime = createTime;
		this.type = type;
	}

	@Override
	public String toString() {
		return "TgAgentUser [createTime=" + createTime + ", id=" + id
				+ ", name=" + name + ", type=" + type + ", url=" + url + "]";
	}

}
