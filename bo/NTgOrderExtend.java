package com.bskcare.ch.bo;

import java.util.Date;

import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ntg.NTgOrderMaster;

/**
 * 血糖高管订单bo
 */
public class NTgOrderExtend extends NTgOrderMaster {

	private static final long serialVersionUID = 1L;

	private Integer pid; // 产品id/充值相当于一种产品

	private String goods; // 产品的jsonArray字符串

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

}
