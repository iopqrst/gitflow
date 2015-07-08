package com.bskcare.ch.bo;

import java.io.Serializable;

import com.bskcare.ch.vo.score.ScoreRecord;

public class ScoreRecordExtend extends ScoreRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String module;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
}
