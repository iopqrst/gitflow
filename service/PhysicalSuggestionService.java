package com.bskcare.ch.service;

import com.bskcare.ch.vo.client.PhysicalSuggestion;

public interface PhysicalSuggestionService {
	
	/**
	 * 修改或保存给用户的体检指标分析建议
	 */
	public void saveOrUpdataSuggestion(PhysicalSuggestion phySuggestion,Integer userId,String suggestions);
}
