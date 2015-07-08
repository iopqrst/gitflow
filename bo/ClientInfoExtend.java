package com.bskcare.ch.bo;

import java.io.Serializable;

import com.bskcare.ch.vo.ClientInfo;

public class ClientInfoExtend extends ClientInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private double balance;
	private int totalScore;
	private int remainScore;

	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getRemainScore() {
		return remainScore;
	}
	public void setRemainScore(int remainScore) {
		this.remainScore = remainScore;
	}
	
}
