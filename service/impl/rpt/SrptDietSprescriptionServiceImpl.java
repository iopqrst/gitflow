package com.bskcare.ch.service.impl.rpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.rpt.SrptDietSprescriptionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.SrptDietSprescriptionService;
import com.bskcare.ch.vo.rpt.SrptDietSprescription;

@Service
public class SrptDietSprescriptionServiceImpl implements
		SrptDietSprescriptionService {
	
	@Autowired
	SrptDietSprescriptionDao dietSprescriptionDao;
	
	public void add(SrptDietSprescription dietSprescription) {
		dietSprescriptionDao.add(dietSprescription);
	}

	public void delete(Integer id) {
		dietSprescriptionDao.delete(id);
	}

	public SrptDietSprescription findById(Integer id) {
		return dietSprescriptionDao.load(id);
	}

	public void update(SrptDietSprescription dietSprescription) {
		dietSprescriptionDao.update(dietSprescription);
	}
	
	public PageObject<SrptDietSprescription> queryAll(SrptDietSprescription dietSprescription,QueryInfo queryInfo) {
		return dietSprescriptionDao.queryAll(dietSprescription,queryInfo);
	}

}
