package com.bskcare.ch.bo;

/**
 * 产品分配返回数据
 * 
 * @author houzhiqing
 */
public class ProductAssignObject {

	/**
	 * 影响条数
	 */
	private int effectCount;

	/**
	 * 档次分配Id
	 */
	private Integer assignId;

	public int getEffectCount() {
		return effectCount;
	}

	public void setEffectCount(int effectCount) {
		this.effectCount = effectCount;
	}

	public Integer getAssignId() {
		return assignId;
	}

	public void setAssignId(Integer assignId) {
		this.assignId = assignId;
	}

	@Override
	public String toString() {
		return "ProductAssignObject [assignId=" + assignId + ", effectCount="
				+ effectCount + "]";
	}
	
	

}
