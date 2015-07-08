package com.bskcare.ch.bo.crm;

import java.io.Serializable;

/**
 * CRM 用户
 */
public class CrmUserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String account;
	private String password;
	private String areaId;
	private String areaName;
	private Integer roleId;
	private String roleName;
	private Integer roleTypeId;
	private String roleTypeName;

	/**
	 * 区域链
	 */
	private String areaChain;

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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAreaChain() {
		return areaChain;
	}

	public void setAreaChain(String areaChain) {
		this.areaChain = areaChain;
	}

	public Integer getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Integer roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	@Override
	public String toString() {
		return "CrmUserInfo [account=" + account + ", areaChain=" + areaChain
				+ ", areaId=" + areaId + ", areaName=" + areaName + ", id="
				+ id + ", name=" + name + ", password=" + password
				+ ", roleId=" + roleId + ", roleName=" + roleName +", roleTypeId="+roleTypeId+",roleTypeName="+roleTypeName+ "]";
	}

}
