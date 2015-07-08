package com.bskcare.ch.bo;

public class BloodOxygenAD extends AlarmData{
	// 血氧
	private int bloodOxygen;
	// 脉率
	private int heartbeat;
	
	public int getBloodOxygen() {
		return bloodOxygen;
	}
	public void setBloodOxygen(int bloodOxygen) {
		this.bloodOxygen = bloodOxygen;
	}
	public int getHeartbeat() {
		return heartbeat;
	}
	public void setHeartbeat(int heartbeat) {
		this.heartbeat = heartbeat;
	}
	
	
}
