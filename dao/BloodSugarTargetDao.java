package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.BloodSugarTarget;

public interface BloodSugarTargetDao extends BaseDao<BloodSugarTarget> {
	BloodSugarTarget quertTarget(BloodSugarTarget target);
}
