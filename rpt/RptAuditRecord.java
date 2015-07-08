package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 健康报告审核
 */
@Entity
@Table(name="rpt_audit_record")
public class RptAuditRecord implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int RPT_TYPE_ALL = 0;
	public static final int RPT_TYPE_SIMPLE = 1;
	
	private Integer id;
	private Integer userId;
	private Integer rptId;
	
	/**
	 * 处理健康报告的哪一部分
	 */
	private String type;
	private Date createTime;
	/**健康报告类型 0：健康报告   1：简易报告**/
	private int rptType;
	
	
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
	public Integer getRptId() {
		return rptId;
	}
	public void setRptId(Integer rptId) {
		this.rptId = rptId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getRptType() {
		return rptType;
	}
	public void setRptType(int rptType) {
		this.rptType = rptType;
	}
}
