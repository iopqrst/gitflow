package com.bskcare.ch.service.medal;

public interface NTgMedalRecordService {
	
	public int addMedalRecord(Integer ruleId, Integer clientId);
	
	public String queryClientMedalDetail(Integer clientId, Integer medalId);
	
	public void batchUpdate(Integer clientId) ;
}
