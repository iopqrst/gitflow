package com.bskcare.ch.bo;

import java.util.Date;

import com.bskcare.ch.vo.ClientInfo;

public class AbnormalCondition extends ClientInfo{
	private static final long serialVersionUID = 1L;
	//异常状态
	private Integer state ;
	//是否处理  0 未处理  1已处理
	private Integer dispose ;
	// 开始测试时间
	private Date stestDateTime;	
	// 结束测试时间
	private Date etestDateTime;	
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getDispose() {
		return dispose;
	}
	public void setDispose(Integer dispose) {
		this.dispose = dispose;
	}
	public Date getStestDateTime() {
		return stestDateTime;
	}
	public void setStestDateTime(Date stestDateTime) {
		this.stestDateTime = stestDateTime;
	}
	public Date getEtestDateTime() {
		return etestDateTime;
	}
	public void setEtestDateTime(Date etestDateTime) {
		this.etestDateTime = etestDateTime;
	}
	
	
}
