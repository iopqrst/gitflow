package com.bskcare.ch.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 区域
 */
@Entity
@Table(name = "t_areainfo")
public class AreaInfo implements java.io.Serializable {

	private static final long serialVersionUID = -8918366947772467290L;

	private Integer id;
	private String name;
	private Integer level;
	private Integer parentId;
	private String areaChain;
	private int status;

	/**
	 * 区域是正常的状态(status=0)
	 */
	public static final int AREA_NORMAL = 0;

	/**
	 * 区域是非正常的状态(status=1)
	 */
	public static final int AREA_NOTNORMAL = 1;

	public AreaInfo() {
	}

	public AreaInfo(String name, Integer level, Integer parentId, Integer status) {
		this.name = name;
		this.level = level;
		this.parentId = parentId;
		this.status = status;
	}

	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "parentId")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "status")
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "areaChain")
	public String getAreaChain() {
		return areaChain;
	}

	public void setAreaChain(String areaChain) {
		this.areaChain = areaChain;
	}

	@Override
	public String toString() {
		return "AreaInfo [areaChain=" + areaChain + ", id=" + id + ", level="
				+ level + ", name=" + name + ", parentId=" + parentId
				+ ", status=" + status + "]";
	}

}