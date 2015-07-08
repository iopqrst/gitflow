package com.bskcare.ch.tg;

public class TgMeals {

	/** 早餐 */
	private String zaocan;
	/** 早加 */
	private String zaojia;
	/** 午餐 */
	private String wucan;
	/** 午加 */
	private String wujia;
	/** 晚餐 */
	private String wancan;
	/** 晚加 */
	private String wanjia;

	public String getZaocan() {
		return zaocan;
	}
	
	public TgMeals() {
		super();
	}

	public TgMeals(String zaocan, String zaojia, String wucan, String wujia,
			String wancan, String wanjia) {
		super();
		this.zaocan = zaocan;
		this.zaojia = zaojia;
		this.wucan = wucan;
		this.wujia = wujia;
		this.wancan = wancan;
		this.wanjia = wanjia;
	}


	public void setZaocan(String zaocan) {
		this.zaocan = zaocan;
	}

	public String getZaojia() {
		return zaojia;
	}

	public void setZaojia(String zaojia) {
		this.zaojia = zaojia;
	}

	public String getWucan() {
		return wucan;
	}

	public void setWucan(String wucan) {
		this.wucan = wucan;
	}

	public String getWujia() {
		return wujia;
	}

	public void setWujia(String wujia) {
		this.wujia = wujia;
	}

	public String getWancan() {
		return wancan;
	}

	public void setWancan(String wancan) {
		this.wancan = wancan;
	}

	public String getWanjia() {
		return wanjia;
	}

	public void setWanjia(String wanjia) {
		this.wanjia = wanjia;
	}

}
