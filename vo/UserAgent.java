package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user_agent")
public class UserAgent implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int AGENT_NORMAL = 0;
	public static final int AGENT_CANCLE = 1;
	
	
	private Integer id;
	/**管理员id**/
	private Integer userId;
	/**代理人id**/
	private Integer agentUserId; 
	/**状态 0：代理   1：取消代理**/
	private int status;
	private Date createTime;
	
	
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
	public Integer getAgentUserId() {
		return agentUserId;
	}
	public void setAgentUserId(Integer agentUserId) {
		this.agentUserId = agentUserId;
	}
	public int getStatus() {
		return status;
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
}
