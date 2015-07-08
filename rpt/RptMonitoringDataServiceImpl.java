package com.bskcare.ch.service.impl.rpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.rpt.RptMonitoringDataDao;
import com.bskcare.ch.service.rpt.RptMonitoringDataService;
import com.bskcare.ch.vo.rpt.RptMonitoringData;

@Service
public class RptMonitoringDataServiceImpl implements RptMonitoringDataService {

	@Autowired
	private RptMonitoringDataDao monitoringDataDao;

	public RptMonitoringData add(RptMonitoringData m) {
		return monitoringDataDao.add(m);
	}

	public RptMonitoringData load(Integer id) {
		return monitoringDataDao.load(id);
	}

	public int updateRptBySingleField(String field, String content, Integer mid) {
		return monitoringDataDao.updateRptBySingleField(field, content, mid);
	}

	public void updateDefaultPic(String bloodPressureChart,
			String bloodSugarChart, String bloodSugar2hChart,
			String bloodOxygenChart, String electrocardiogram, String rptId,String type) {
		
		monitoringDataDao.updateDefaultPic(bloodPressureChart, bloodSugarChart,
				bloodSugar2hChart, bloodOxygenChart, electrocardiogram, rptId,type);
	}

}
