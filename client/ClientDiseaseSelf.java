package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户疾病
 */
@Entity
@Table(name = "t_client_disease_self")
public class ClientDiseaseSelf implements Serializable{

	/**
	 * type字段常量,DISEASE_YES为数据库已有疾病
	 */
	public final static int DISEASE_YES = 0;
	/**
	 * type字段常量,DISEASE_NO为数据库没有的疾病
	 */
	public final static int DISEASE_NO = 1;
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer clientId;// 用户编号
	private String disease;// 疾病编号或疾病名称
	private Date diagTime;// 疾病诊断时间
	private int type;// 类型,0为有疾病,1为没有疾病

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

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public Date getDiagTime() {
		return diagTime;
	}

	public void setDiagTime(Date diagTime) {
		this.diagTime = diagTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ClientDiseaseSelf [clientId=" + clientId + ", diagTime="
				+ diagTime + ", disease=" + disease + ", id=" + id + ", type="
				+ type + "]";
	}
}
