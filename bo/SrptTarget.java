package com.bskcare.ch.bo;

public class SrptTarget {
	
	/**血压目标**/
	private String bpTarget;
	/**血氧目标**/
	private String boTarget;
	/**空腹血糖目标**/
	private String kongfuTarget;
	/**餐后血糖目标**/
	private String canhouTarge;
	
	public String getBpTarget() {
		return bpTarget;
	}
	public void setBpTarget(String bpTarget) {
		this.bpTarget = bpTarget;
	}
	public String getBoTarget() {
		return boTarget;
	}
	public void setBoTarget(String boTarget) {
		this.boTarget = boTarget;
	}
	public String getKongfuTarget() {
		return kongfuTarget;
	}
	public void setKongfuTarget(String kongfuTarget) {
		this.kongfuTarget = kongfuTarget;
	}
	public String getCanhouTarge() {
		return canhouTarge;
	}
	public void setCanhouTarge(String canhouTarge) {
		this.canhouTarge = canhouTarge;
	}
}
