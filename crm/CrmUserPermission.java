package com.bskcare.ch.bo.crm;

import java.io.Serializable;

/**
 * CRM 用户权限
 */
public class CrmUserPermission implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	/**
	 * 链接显示名称
	 */
	private String name;
	/**
	 * 链接URL
	 */
	private String url;
	/**
	 * 是否为按钮
	 */
	private Integer isbtn;
	/**
	 * 菜单层级
	 */
	private Integer level;

	private Integer parentId;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIsbtn() {
		return isbtn;
	}

	public void setIsbtn(Integer isbtn) {
		this.isbtn = isbtn;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "CrmUserPermission [id=" + id + ", isbtn=" + isbtn + ", level="
				+ level + ", name=" + name + ", parentId=" + parentId
				+ ", url=" + url + "]";
	}

}
