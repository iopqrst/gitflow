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
///**保存注册用户的来源**/
//@Entity
//@Table(name="t_reg_source")
//public class RegSource implements Serializable{
//	private static final long serialVersionUID = 1L;
//	
//	private Integer id;
//	private Integer clientId;
//	/**注册来源**/
//	private String source;
//	/**版本号**/
//	private String version;
//	private Date createTime;
//	
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
//	public String getSource() {
//		return source;
//	}
//	public void setSource(String source) {
//		this.source = source;
//	}
//	public Date getCreateTime() {
//		return createTime;
//	}
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//	public String getVersion() {
//		return version;
//	}
//	public void setVersion(String version) {
//		this.version = version;
//	}
//}
