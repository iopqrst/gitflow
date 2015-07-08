package com.bskcare.ch.tg;
import com.bskcare.ch.bo.RiskResultBean;
import com.bskcare.ch.dao.BloodSugarDao;
import com.bskcare.ch.dao.tg.TgCookedFoodDao;
import com.bskcare.ch.dao.timeLine.TimeLineTaskDao;

public class PickerCondition {

	/**
	 * 总消耗的热卡
	 */
	private double calories;

	/**是不是可以每天都吃鸡蛋的并和症 0：一周3天能吃   1：每天 */
	private int eggEvery;

	/**
	 * 是否可以喝粥
	 */
	private boolean hasGruel;
	/**
	 * 是否可以吃鸡蛋
	 */
	private boolean hasEggs;

	private Integer clientId;
	
	
	/**主食部分计算系数*/
	private double zhushixishu = 0.6;
	/**肉部分计算系数*/
	private double rouxishu = 0.18;
	/**奶类部分计算系数*/
	private double milkxishu = 0.18;
	/**豆制品部分计算系数*/
	private double douxishu = 0.18;
	/**坚果部分计算系数*/
	private double nutsxishu = 0.22;
	
	
	/**疾病：tangniaobing:糖尿病   gaoxueya:高血压*/
	private String disease = "tangniaobing";
	/**合并症*/
	private String hebingDisease ;
	
	private TgCookedFoodDao cookedFoodDao;
	private TimeLineTaskDao lineTaskDao;
	private BloodSugarDao bloodSugarDao;
	
	private RiskResultBean risk;
	
	
	
	public RiskResultBean getRisk() {
		return risk;
	}

	public void setRisk(RiskResultBean risk) {
		this.risk = risk;
	}

	public TimeLineTaskDao getLineTaskDao() {
		return lineTaskDao;
	}

	public void setLineTaskDao(TimeLineTaskDao lineTaskDao) {
		this.lineTaskDao = lineTaskDao;
	}

	public String getHebingDisease() {
		return hebingDisease;
	}

	public void setHebingDisease(String hebingDisease) {
		this.hebingDisease = hebingDisease;
	}

	public double getDouxishu() {
		return douxishu;
	}

	public void setDouxishu(double douxishu) {
		this.douxishu = douxishu;
	}

	
	public TgCookedFoodDao getCookedFoodDao() {
		return cookedFoodDao;
	}

	public void setCookedFoodDao(TgCookedFoodDao cookedFoodDao) {
		this.cookedFoodDao = cookedFoodDao;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public double getZhushixishu() {
		return zhushixishu;
	}

	public void setZhushixishu(double zhushixishu) {
		this.zhushixishu = zhushixishu;
	}

	public double getRouxishu() {
		return rouxishu;
	}

	public void setRouxishu(double rouxishu) {
		this.rouxishu = rouxishu;
	}

	public double getNutsxishu() {
		return nutsxishu;
	}

	public void setNutsxishu(double nutsxishu) {
		this.nutsxishu = nutsxishu;
	}

	public double getMilkxishu() {
		return milkxishu;
	}

	public void setMilkxishu(double milkxishu) {
		this.milkxishu = milkxishu;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public boolean isHasGruel() {
		return hasGruel;
	}

	public void setHasGruel(boolean hasGruel) {
		this.hasGruel = hasGruel;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public boolean isHasEggs() {
		return hasEggs;
	}

	public void setHasEggs(boolean hasEggs) {
		this.hasEggs = hasEggs;
	}
	public int getEggEvery() {
		return eggEvery;
	}

	public void setEggEvery(int eggEvery) {
		this.eggEvery = eggEvery;
	}

	public BloodSugarDao getBloodSugarDao() {
		return bloodSugarDao;
	}

	public void setBloodSugarDao(BloodSugarDao bloodSugarDao) {
		this.bloodSugarDao = bloodSugarDao;
	}
}
