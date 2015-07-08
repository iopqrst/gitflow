package com.bskcare.ch.bo;

public class BloodSugarAD extends AlarmData {
	// 血糖值
	private double bloodSugarValue;
	private int bloodSugarType ;
	
	public double getBloodSugarValue() {
		return bloodSugarValue;
	}
	
	public int getBloodSugarType() {
		return bloodSugarType;
	}

	public void setBloodSugarType(int bloodSugarType) {
		this.bloodSugarType = bloodSugarType;
	}

	public void setBloodSugarValue(double bloodSugarValue) {
		this.bloodSugarValue = bloodSugarValue;
	}
	
	
}
