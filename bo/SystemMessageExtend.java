package com.bskcare.ch.bo;

import com.bskcare.ch.vo.SystemMessage;

public class SystemMessageExtend extends SystemMessage{
		
	private static final long serialVersionUID = 1L;
	private Integer mrId;
	private Integer mStatus;
	
	
	public Integer getMrId() {
		return mrId;
	}
	public void setMrId(Integer mrId) {
		this.mrId = mrId;
	}
	public Integer getmStatus() {
		return mStatus;
	}
	public void setmStatus(Integer mStatus) {
		this.mStatus = mStatus;
	}
	@Override
	public String toString() {
		return "SystemMessageExtend [mStatus=" + mStatus + ", mrId=" + mrId
				+ "]";
	}
	
}
