package com.bskcare.ch.service.impl.rpt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.dao.rpt.SrptDietDao;
import com.bskcare.ch.service.rpt.SrptDietService;
import com.bskcare.ch.vo.rpt.SrptDiet;

@Service
public class SrptDietServiceImpl implements SrptDietService{
	
	@Autowired
	private SrptDietDao srptDietDao;
	
	public SrptDiet querySrptDiet(Integer srptId){
		return srptDietDao.querySrptDiet(srptId);
	}
	
	public int updateDiets(String field,Integer dietId) {
		return srptDietDao.updateDiets(field,dietId);
	}
}
