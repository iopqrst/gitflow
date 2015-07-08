//package com.bskcare.ch.vo;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**记录用户最后一次随访和上传数据的时间**/
//@Entity
//@Table(name="t_client_last_time")
//public class ClientLastTime implements Serializable{
//	private static final long serialVersionUID = 1L;
//	
//	private Integer id;
//	private Integer clientId;
//	/**最后一次上传数据时间**/
//	private Date lastFollowTime;
//	/**最后一次上次监测数据的时间**/
//	private Date lastUploadDateTime;
//	
//	@Id
//	@GeneratedValue
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
//	public Integer getClientId() {
//		return clientId;
//	}
//	public void setClientId(Integer clientId) {
//		this.clientId = clientId;
//	}
//	public Date getLastFollowTime() {
//		return lastFollowTime;
//	}
//	public void setLastFollowTime(Date lastFollowTime) {
//		this.lastFollowTime = lastFollowTime;
//	}
//	public Date getLastUploadDateTime() {
//		return lastUploadDateTime;
//	}
//	public void setLastUploadDateTime(Date lastUploadDateTime) {
//		this.lastUploadDateTime = lastUploadDateTime;
//	}
//	
//	
//}
