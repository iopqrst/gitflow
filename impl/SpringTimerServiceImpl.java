package com.bskcare.ch.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.OrderProductDao;
import com.bskcare.ch.service.SpringTimerService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.SystemConfig;

@Service
public class SpringTimerServiceImpl implements SpringTimerService {

	private Logger log = Logger.getLogger(SpringTimerServiceImpl.class);
	
	/** 订单产品等级对应最高等级临时表 */
	private static final String PREFIX_OPL_TABLE = "tmp_opl_";
	/** 产品订单服务提醒临时信息表 */
	private static final String PREFIX_REMIND_TABLE = "tmp_remind_";
	
	@Autowired
	private ClientInfoDao clientInfoDao;
	@Autowired
	private OrderProductDao orderProductDao;
	

	public String updateClientBirthdayToAge() {
		int effectRow = clientInfoDao.updateBirthdayToAge();
		log.info(LogFormat.f("update birthday to age effect rows ----->" + effectRow));
		return null;
	}
	
	public String updateClientInfoLevel() {
		String currentTime = DateUtils.formatDate(
				DateUtils.LONG_DATE_PATTERN_PLAIN, new Date());
		
		String tmpTableName = PREFIX_OPL_TABLE + currentTime; //临时表名称
		
		int effect1 = orderProductDao.createOplTmpTable(tmpTableName);
		int effect2 = clientInfoDao.updateLevel(tmpTableName);
		int effect3 = orderProductDao.dropTmpTable(tmpTableName);
		System.out.println("effect1=" + effect1 + ",effect2=" + effect2 
				+ ",effect3=" + effect3);
		return null;
	}
	
	public int createServiceRemindRecord() {
		String currentTime = DateUtils.formatDate(
				DateUtils.LONG_DATE_PATTERN_PLAIN, new Date());
		String tmpName = PREFIX_REMIND_TABLE + currentTime;
		String days = SystemConfig.getString("remind_countdown");
		orderProductDao.createServiceRemindRecord(Integer.parseInt(days), tmpName);
		return 0;
	}
	

	
	
}
