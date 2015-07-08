package com.bskcare.ch.bo;

import java.io.Serializable;


public class NTgMedalRecordExtend implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int sumScore;
	private int clientId;
	
	public int getSumScore() {
		return sumScore;
	}
	public void setSumScore(int sumScore) {
		this.sumScore = sumScore;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
}
