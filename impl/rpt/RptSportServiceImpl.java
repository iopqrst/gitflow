package com.bskcare.ch.service.impl.rpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.dao.rpt.RptSportDao;
import com.bskcare.ch.service.rpt.RptSportService;
import com.bskcare.ch.vo.rpt.RptSport;

@Service
public class RptSportServiceImpl implements RptSportService {

	@Autowired
	RptSportDao rptSportDao;

	public RptSport save(RptSport rptSport) {
		return rptSportDao.add(rptSport);
	}

	public RptSport findRptSport(Integer rptId, Integer clientId) {
		return rptSportDao.findRptSport(rptId, clientId);
	}

	public int updateSportByFields(String field, Integer id) {
		return rptSportDao.updateSportByFields(field, id);
	}

}
