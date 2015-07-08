package com.bskcare.ch.bo;

import com.bskcare.ch.vo.order.OrderProduct;

/**
 * 订单产品扩展对象
 * @author Administrator
 *
 */
public class OrderProductExtend extends OrderProduct{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 产品生命周期
	 */
	private int cycle;
	
	private Integer clientId;
	/**产品类别*/
	private int category;
	/**产品名称*/
	private String pname;

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	@Override
	public String toString() {
		return "OrderProductExtend [category=" + category + ", clientId="
				+ clientId + ", cycle=" + cycle + ", pname=" + pname + "], OrderProduct ["+ super.toString()+"]";
	}
	
	
}
