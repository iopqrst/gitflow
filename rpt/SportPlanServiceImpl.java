package com.bskcare.ch.service.impl.rpt;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.rpt.RptSportDao;
import com.bskcare.ch.dao.rpt.SportPlanDao;
import com.bskcare.ch.service.rpt.SportPlanService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.vo.rpt.SportPlan;

@Service
public class SportPlanServiceImpl implements SportPlanService {

	@Autowired
	private SportPlanDao sportPlanDao;
	
	@Autowired
	private RptSportDao sportDao;


	public SportPlan addSportPlan(SportPlan plan) {
		return sportPlanDao.add(plan);
	}

	public SportPlan findSportPlan(Integer rptId, Integer clientId) {
		return sportPlanDao.findSportPlan(rptId, clientId);
	}

	public void updateSportPlanByField(String field, Integer planId) {
		sportPlanDao.updateSportPlanByField(field, planId);
	}

	public void updateSportPlanTime(String field,Integer planId,String fields,Integer sportId){
		sportPlanDao.updateSportPlanByField(field, planId);
		sportDao.updateSportByFields(fields, sportId);
	}
	
	public String findSportPlanByClientId(Integer clientId,Date currentTime) {
		String str = "";
		List<SportPlan> list = sportPlanDao.querSportPlanByClientId(clientId,currentTime);
		if (null != list && list.size() > 0) {
			SportPlan sp = list.get(0);
			int weekday = DateUtils.getWeekOfDate(new Date());
			if(weekday == 0) {
				str = sp.getSunday();
			} else if (weekday == 1) {
				str = sp.getMonday();
			} else if (weekday == 2) {
				str = sp.getTuesday();
			} else if (weekday == 3) {
				str = sp.getWednesday();
			} else if (weekday == 4) {
				str = sp.getThursday();
			} else if (weekday == 5) {
				str = sp.getFriday();
			} else if (weekday == 6) {
				str = sp.getSaturday();
			}
		}
		return str;
	}
	
}
