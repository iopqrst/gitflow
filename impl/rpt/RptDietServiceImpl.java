package com.bskcare.ch.service.impl.rpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.dao.rpt.RptDietDao;
import com.bskcare.ch.service.rpt.RptDietService;
import com.bskcare.ch.vo.rpt.RptDiet;

@Service
public class RptDietServiceImpl implements RptDietService {

	@Autowired
	RptDietDao dietDao;

	public RptDiet finById(Integer id) {
		return dietDao.load(id);
	}

	public RptDiet save(RptDiet diet) {
		return dietDao.add(diet);
	}

	public RptDiet findRptDiet(Integer rptId, Integer clientId) {
		return dietDao.findRptDiet(rptId, clientId);
	}

	public int updateDietBySingleField(String field, String content,
			Integer dietId) {
		return dietDao.updateDietBySingleField(field, content, dietId);
	}

	public int updateDietByFields(String field,Integer dietId) {
		return dietDao.updateDietByFields(field, dietId);
	}
	
	public RptDiet findRptDietByType(Integer rptId, Integer clientId,int type) {
		return dietDao.findRptDietByType(rptId, clientId, type);
	}
	
}
