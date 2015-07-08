package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.constant.MonConstant;
import com.bskcare.ch.dao.BloodOxygenDao;
import com.bskcare.ch.dao.BloodPressureDao;
import com.bskcare.ch.dao.BloodSugarDao;
import com.bskcare.ch.dao.ElectrocardiogramDao;
import com.bskcare.ch.dao.TemperatureDao;
import com.bskcare.ch.dao.UnusualDataDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.UnusualDataService;
import com.bskcare.ch.vo.BloodOxygen;
import com.bskcare.ch.vo.BloodPressure;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.Electrocardiogram;
import com.bskcare.ch.vo.Temperature;
import com.bskcare.ch.vo.UnusualData;
import com.bskcare.ch.vo.UnusualDataExtends;
@Service
public class UnusualDataServiceImpl implements UnusualDataService{

	@Autowired
	UnusualDataDao unusualDataDao;
	@Autowired
	private BloodPressureDao bloodPressureDao;
	@Autowired
	private BloodOxygenDao bloodOxygenDao;
	@Autowired
	private BloodSugarDao bloodSugarDao;
	@Autowired
	private ElectrocardiogramDao electrocardiogramDao;
	@Autowired
	private TemperatureDao temperatureDao;

	public void saveAbnormalData(UnusualData unusualData) {
		unusualDataDao.add(unusualData);
	}

	public PageObject<UnusualDataExtends> queryUnusualDataList(UnusualDataExtends dataExtends,QueryCondition queryCondition,QueryInfo queryInfo) {
		return unusualDataDao.queryUnusualDataList(dataExtends,queryCondition,queryInfo);
	}

	public UnusualData findUnustalDataById(Integer id) {
		return unusualDataDao.load(id);
	}

	public void updateStatus(UnusualData unusualData) {
		unusualDataDao.update(unusualData);
	}

	public List<UnusualData> queryRecentOnceDataList(Integer clientId) {
		return unusualDataDao.queryRecentOnceDataList(clientId);
	}
	
	public void updateUnusualData(UnusualData ud) {
		if(null != ud && null != ud.getClientId()
				&& null != ud.getDataId() && null != ud.getType()) {
			UnusualData nud = unusualDataDao.load(ud.getId());
			
			if(null != nud && ud.getClientId().equals(nud.getClientId())
					&& UnusualData.HANDLED != nud.getStatus()) {
				nud.setStatus(UnusualData.HANDLED);
				unusualDataDao.update(nud);
				
				switch(ud.getType()){
					case UnusualData.BLOODPRESSURE :
						BloodPressure bp = bloodPressureDao.load(ud.getDataId());
						
						if(null != bp && MonConstant.DISPOSED != bp.getDispose()) {
							bp.setDispose(MonConstant.DISPOSED);
							bloodPressureDao.update(bp);
						}
						
						break;
					case UnusualData.BLOODOXYGEN :
						
						BloodOxygen bo = bloodOxygenDao.load(ud.getDataId());
						if(null != bo && MonConstant.DISPOSED != bo.getDispose()) {
							bo.setDispose(MonConstant.DISPOSED);
							bloodOxygenDao.update(bo);
						}
	
						break;
					case UnusualData.BLOODSUGAR :
					case UnusualData.BLOODSUGAR2H:
					case UnusualData.SUGAR_ZAOCAN_BEFORE:
					case UnusualData.SUGAR_ZAOCAN_AFTER:
					case UnusualData.SUGAR_WUCAN_BEFORRE:
					case UnusualData.SUGAR_WUCAN_AFTER:
					case UnusualData.SUGAR_WANCAN_BEFORE:
					case UnusualData.SUGAR_WANCAN_AFTER:
					case UnusualData.SUGAR_SLEEP_BEFORE:
					case UnusualData.SUGAR_LINGCHEN:
						BloodSugar bs = bloodSugarDao.load(ud.getDataId());
						if(null != bs && MonConstant.DISPOSED != bs.getDispose()) {
							bs.setDispose(MonConstant.DISPOSED);
							bloodSugarDao.update(bs);
						}

						break;
					case UnusualData.ELECTROCARDIO:
						Electrocardiogram ecg = electrocardiogramDao.load(ud.getDataId());
						if(null != ecg && MonConstant.DISPOSED != ecg.getDispose()) {
							ecg.setDispose(MonConstant.DISPOSED);
							electrocardiogramDao.update(ecg);
						}
						break;
					case UnusualData.TEMPERATURE:
						Temperature temperature = temperatureDao.load(ud.getDataId());
						if(null != temperature && temperature.getDispose() != 1) {
							temperature.setDispose(1);
							temperatureDao.update(temperature);
						}
						break;
				}
			}
		}
	}

	public void flagUnusualData(Integer dataId, Integer type, Integer clientId,
			int handled) {
		unusualDataDao.flagUnusualData(dataId, type, clientId, handled);
	}
	
}
