package com.bskcare.ch.service.online;

import com.bskcare.ch.vo.online.OnlineSatisfaction;

public interface OnlineSatisfactionService {

	OnlineSatisfaction add(OnlineSatisfaction os);

	public void calStatisAndSubCount(Integer expertId);
}
