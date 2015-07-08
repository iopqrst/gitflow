package com.bskcare.ch.bo;

import java.math.BigInteger;

import com.bskcare.ch.vo.BskExpert;

public class BskExpertExtend extends BskExpert {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 评价类型：0 服务评价， 1 医疗评价
	 */
	public static final int TYPE_OF_SERVICE = 0; 
	/**
	 * 评价类型：0 服务评价， 1 医疗评价
	 */
	public static final int TYPE_OF_MEDICAL = 1; 
	

	private Integer roleId;

	/**
	 * 评论总数（糖尿病高管）
	 */
	private BigInteger total;

	/**
	 * 好评数（糖尿病高管）
	 */
	private BigInteger hp;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public BigInteger getTotal() {
		return total;
	}

	public void setTotal(BigInteger total) {
		this.total = total;
	}

	public BigInteger getHp() {
		return hp;
	}

	public void setHp(BigInteger hp) {
		this.hp = hp;
	}

	@Override
	public String toString() {
		return "BskExpertExtend [hp=" + hp + ", roleId=" + roleId + ", total="
				+ total + "," + super.toString() +"]";
	}



}
