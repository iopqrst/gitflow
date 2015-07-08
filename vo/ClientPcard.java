package com.bskcare.ch.vo;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_client_pcard")
public class ClientPcard implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String pcCode;// 产品卡code

	private int clientId;// 用户id

	private Date createTime;// 创建（绑定）时间

	private String type; // 用户类型(0：会员账号 1:亲情账号、2：游客账号)

	private int status; // 亲情账号状态 （0：正常 1：删除）
	
	/**
	 * 亲情被删除状态(status=1)
	 */
	public static final int STATUS_DELETE = 1;

	/**
	 * 亲情是正常的状态(status=0)
	 */
	public static final int STATUS_NORMAL = 0;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "clientId")
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Column(name = "pcCode")
	public String getPcCode() {
		return pcCode;
	}

	public void setPcCode(String pcCode) {
		this.pcCode = pcCode;
	}

	@Column(name = "createTime")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ClientsPcard [clientId=" + clientId + ", createTime="
				+ createTime + ", id=" + id + ", pcCode=" + pcCode
				+ ", status=" + status + ", type=" + type + "]";
	}
}
