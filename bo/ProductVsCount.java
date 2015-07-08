package com.bskcare.ch.bo;

public class ProductVsCount {

	private Integer pId;
	private int count;

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ProductVsCount [count=" + count + ", pId=" + pId + "]";
	}

}
