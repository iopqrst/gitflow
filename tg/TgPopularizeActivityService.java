package com.bskcare.ch.service.tg;

import com.bskcare.ch.vo.tg.TgPopularizeActivity;

public interface TgPopularizeActivityService {
	
	public TgPopularizeActivity queryPopularizeByClientId(Integer clientId);
	
	public void addClientPopularize(TgPopularizeActivity popu);
}
