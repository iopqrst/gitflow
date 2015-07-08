package com.bskcare.ch.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.bo.AbnormalCondition;
import com.bskcare.ch.dao.BloodOxygenDao;
import com.bskcare.ch.dao.BloodPressureDao;
import com.bskcare.ch.dao.BloodSugarDao;
import com.bskcare.ch.dao.ElectrocardiogramDao;
import com.bskcare.ch.dao.UnusualDataDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.AbnormalDataService;
import com.bskcare.ch.vo.BloodOxygen;
import com.bskcare.ch.vo.BloodPressure;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.Electrocardiogram;
import com.bskcare.ch.vo.UnusualData;

@Service("abnormalDataService")
@SuppressWarnings("unchecked")
public class AbnormalDataServiceImpl implements AbnormalDataService {
	
	@Autowired	
	private BloodPressureDao  bloodPressureDao ;
	@Autowired	
	private BloodOxygenDao  bloodOxygenDao ;
	@Autowired	
	private BloodSugarDao  bloodSugarDao ;
	@Autowired
	private ElectrocardiogramDao electrocardiogramDao ;
	@Autowired
	private UnusualDataDao unusualDataDao;
	
	public PageObject getAbnormalBPList(String areaChain,AbnormalCondition abnormalCondition,QueryInfo queryInfo) {
		queryInfo.setSort("bp.uploadDateTime") ;
		return bloodPressureDao.getAbnormalBPList(areaChain,abnormalCondition,queryInfo) ;
	}
	
	public PageObject getAbnormalBOList(String areaChain,AbnormalCondition abnormalCondition,QueryInfo queryInfo) {
		queryInfo.setSort("bp.uploadDateTime") ;
		return bloodOxygenDao.getAbnormalBOList(areaChain,abnormalCondition,queryInfo) ;
	}

	public PageObject getAbnormalBSList(String areaChain,AbnormalCondition abnormalCondition,BloodSugar bloodSugar,QueryInfo queryInfo) {
		queryInfo.setSort("bp.uploadDateTime") ;
		return bloodSugarDao.getAbnormalBSList(areaChain,abnormalCondition,bloodSugar,queryInfo);
	}
	
	public int disposeBloodPressure(Object tmp,String table) {
		int result = -1;
		int type = 0;
		Integer clientId = null;
		Date testDate = null;
		if(table.equals("bloodPressure")){
			type = UnusualData.BLOODPRESSURE;
			BloodPressure bp = ((BloodPressure)tmp);
			clientId = bp.getClientId();
			testDate = bp.getTestDateTime();
			
			result = bloodPressureDao.updateDispose("m_blood_pressure",bp.getClientId(),bp.getTestDateTime(),-1) ;			
		}else if(table.equals("bloodOxygen")){
			type = UnusualData.BLOODOXYGEN;
			BloodOxygen bo = ((BloodOxygen)tmp);
			clientId = bo.getClientId();
			testDate = bo.getTestDateTime();
			
			result = bloodPressureDao.updateDispose("m_blood_oxygen", bo.getClientId(),bo.getTestDateTime(),-1) ;			
		}else if(table.equals("bloodSugar")){
			BloodSugar bs = ((BloodSugar)tmp);
			type = BloodSugar.SUGAR_TYPE == bs.getBloodSugarType() ? UnusualData.BLOODSUGAR : UnusualData.BLOODSUGAR2H;
			clientId = bs.getClientId();
			testDate = bs.getTestDateTime();
			
			result = bloodPressureDao.updateDispose("m_blood_sugar",bs.getClientId(),bs.getTestDateTime(),bs.getBloodSugarType()) ;			
		}else if(table.equals("electrocardiogram")){
			Electrocardiogram ecg = ((Electrocardiogram)tmp);
			type = UnusualData.ELECTROCARDIO;
			clientId = ecg.getClientId();
			testDate = ecg.getTestDateTime();
			
			result = bloodPressureDao.updateDispose("m_electrocardiogram",ecg.getClientId(),ecg.getTestDateTime(),-1) ;
		}
		
		if(result > 0 && null != clientId) { //修改异常数据表中的报警数据值
			result = unusualDataDao.disposeByDayTime(type, clientId, testDate);
		}
		
		return result ;
	}

	public PageObject getAbnormalEDList(String areaChain,AbnormalCondition abnormalCondition,
			Electrocardiogram electrocardiogram,QueryInfo queryInfo) {
		queryInfo.setSort("bp.uploadDateTime") ;
		return electrocardiogramDao.getAbnormalBSList(areaChain,abnormalCondition,electrocardiogram,queryInfo);
	}

	public PageObject getBPAlarmData(QueryInfo queryInfo) {
		return	bloodPressureDao.getBloodPressureAlarmData(queryInfo) ;		
	}

	public PageObject getBOAlarmData(QueryInfo queryInfo) {
		return	bloodOxygenDao.getBloodOxygenAlarmData(queryInfo) ;	
	}

	public PageObject getBSAlarmData(QueryInfo queryInfo) {
		return bloodSugarDao.getBloodSugarAlarmData(queryInfo);
	}

	public PageObject getEDAlarmData(QueryInfo queryInfo) {
		return electrocardiogramDao.getEDAlarmData(queryInfo);
	}
	
	
}
