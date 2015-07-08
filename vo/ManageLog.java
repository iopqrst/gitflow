package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_manage_log")
public class ManageLog implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//增加
	public static final Integer USER_add=1;
	//修改
	public static final Integer USER_update=2;
	//删除
	public static final Integer USER_delete=3;
	//下载
	public static final Integer USER_download=4;
	//针对系统操作
	public static final Integer USER_systemAllUser=5;
	
	/**
	 * 分配产品卡
	 */
	public static final Integer USER_ASSIGN_CARD = 6;
	//系统管理员处理 查看数据
	public static final Integer USER_disposeData=7;
	
	private Integer id ; //id
	
	private Integer userId ; 
	
	private Date operateDate ;  //操作时间
	
	private Integer type ; //操作类型
	
	private String content ; //内容
	
	private String userIp ; //用户IP地址
	
	private Integer clientId ; //用户ID
	
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

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public ManageLog() {}
	
	public ManageLog(Integer type , String content) {
		this.type = type;
		this.content = content;
	}
	
}
