package com.bskcare.ch.vo.score;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 签到
 */
@Entity
@Table(name = "s_sign_in")
public class SignIn implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer clientId;
	/**
	 * 标记连续签到次数 ， 第一次签到为0，第二次签到为1... ，中间有间隔再次从0开始
	 */
	private int keepCount;
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getKeepCount() {
		return keepCount;
	}

	public void setKeepCount(int keepCount) {
		this.keepCount = keepCount;
	}

	@Override
	public String toString() {
		return "SignIn [clientId=" + clientId + ", createTime=" + createTime
				+ ", id=" + id + ", keepCount=" + keepCount + "]";
	}

}
