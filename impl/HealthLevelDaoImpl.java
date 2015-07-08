package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.HealthLevelDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.HealthLevel;

@Repository
public class HealthLevelDaoImpl extends BaseDaoImpl<HealthLevel> implements
		HealthLevelDao {

	public PageObject<HealthLevel> getHealhtLevelList(QueryInfo queryInfo,
			HealthLevel level) {
		List args = new ArrayList();
		String hql = " from HealthLevel where 1 = 1";
		if (level != null) {
			if (level.getStatus() != null) {
				hql += " and status = ? ";
				args.add(level.getStatus());
			}
		}
		return queryObjects(hql, args.toArray(), queryInfo);
	}

	public void saveOrUpdateHL(HealthLevel healthLevel) {
		if (healthLevel.getId() == null) {
			add(healthLevel);
		} else {
			update(healthLevel);
		}
	}

	public HealthLevel getHealthLevel(int id) {
		return load(id);
	}

	public void delHL(int id) {
		delete(id);
	}

	public HealthLevel getHealthLevelByHealthIndex(Integer HealthIndex) {
		List<HealthLevel> healthLevels=new ArrayList<HealthLevel>();
		if(HealthIndex!=null){
			List args = new ArrayList();
			String hql="from HealthLevel where maxGrade>= ? and minGrade <= ? and status = ?";
			args.add(HealthIndex);
			args.add(HealthIndex);
			args.add(HealthLevel.HEALTH_LEVEL_TASK_STATUS_ON);
			healthLevels=executeFind(hql, args.toArray());
		}
		if(healthLevels.size()==1)
			return healthLevels.get(0);
		else
			return null;
	}

}
