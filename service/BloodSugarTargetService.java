package com.bskcare.ch.service;

import com.bskcare.ch.vo.BloodSugarTarget;

public interface BloodSugarTargetService {
	
	BloodSugarTarget quertTarget(BloodSugarTarget target);
	
	String saveOrUpdate(BloodSugarTarget target);
	
	BloodSugarTarget get(int id);
}
