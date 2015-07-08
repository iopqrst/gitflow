package com.bskcare.ch.service;

public interface FileCompletionService {
	
	/**
	 * 用户档案完成率
	 */
	public double findClientFIleCompletion(Integer clientId);
	
	/**
	 * 更新用户档案完成率
	 */
	public void updateCompletion(Integer clientId);
}
