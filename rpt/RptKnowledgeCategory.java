package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rpt_knowledge_category")
public class RptKnowledgeCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 0:正常 1：删除
	 */
	public static final int KC_STATUS_NORMAL = 0;
	public static final int KC_STATUS_DELETE = 1;

	private Integer id;
	private String name;
	private Integer parentId;
	/**
	 * 层级：一级分类、二级分类...
	 */
	private Integer level;
	/**
	 * 0:正常 1：删除
	 */
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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

	@Override
	public String toString() {
		return "RptKnowledgeCategory [createTime=" + createTime + ", id=" + id
				+ ", level=" + level + ", name=" + name + ", parentId="
				+ parentId + ", status=" + status + "]";
	}

}
