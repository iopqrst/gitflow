package com.bskcare.ch.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_sync_data")
public class SyncData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_CLIENTINFO = 1;
	
	public static final int STATUS_SYNC = 1;
	public static final int STATUS_NOT_SYNC = 0;
	
	public static final int DEAL_TYPE_ADD = 0;
	public static final int DEAL_TYPE_UPDATE = 1;
	
	private Integer id;
	/**数据类型  1：前台客户 **/
	private Integer type;
	private Integer dataId;
	/**操作类型*/
	private Integer dealType;
	/**同步状态0：未同步   1：同步**/
	private int status;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getDataId() {
		return dataId;
	}
	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getDealType() {
		return dealType;
	}
	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
}
