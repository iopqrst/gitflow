package com.bskcare.ch.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.UploadExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.constant.MonConstant;
import com.bskcare.ch.constant.NtgConstant;
import com.bskcare.ch.dao.BloodOxygenDao;
import com.bskcare.ch.dao.BloodPressureDao;
import com.bskcare.ch.dao.BloodSugarDao;
import com.bskcare.ch.dao.BloodSugarTargetDao;
import com.bskcare.ch.dao.ElectrocardiogramDao;
import com.bskcare.ch.dao.ExpertAdviseDao;
import com.bskcare.ch.dao.MonitoringDataDao;
import com.bskcare.ch.dao.TemperatureDao;
import com.bskcare.ch.dao.msport.MSportDao;
import com.bskcare.ch.dao.ntg.NTgSportDao;
import com.bskcare.ch.dao.ntg.NTimelineMealsDao;
import com.bskcare.ch.dao.ntg.NTimelineRuleCustomDao;
import com.bskcare.ch.dao.ntg.NTimelineTaskDao;
import com.bskcare.ch.dao.ntg.NTimelineWakeSleepDao;
import com.bskcare.ch.dao.rpt.RptMonitoringDataDao;
import com.bskcare.ch.dao.tg.MedicineReminderDao;
import com.bskcare.ch.dao.tg.TgRecordFoodDao;
import com.bskcare.ch.dao.timeLine.TimeLineTaskDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.BloodSugarTargetService;
import com.bskcare.ch.service.ManageLogService;
import com.bskcare.ch.service.MonitoringDataService;
import com.bskcare.ch.service.UnusualDataService;
import com.bskcare.ch.service.ntg.NTimelineContentService;
import com.bskcare.ch.util.DateJsonValueProcessor;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.MoneyFormatUtil;
import com.bskcare.ch.util.MonitoringDataResult;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.BloodOxygen;
import com.bskcare.ch.vo.BloodPressure;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.BloodSugarTarget;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.Electrocardiogram;
import com.bskcare.ch.vo.ExpertAdvise;
import com.bskcare.ch.vo.Temperature;
import com.bskcare.ch.vo.UnusualData;
import com.bskcare.ch.vo.msport.MSport;
import com.bskcare.ch.vo.ntg.NTgSport;
import com.bskcare.ch.vo.ntg.NTimelineContent;
import com.bskcare.ch.vo.ntg.NTimelineMeals;
import com.bskcare.ch.vo.ntg.NTimelineRuleCustom;
import com.bskcare.ch.vo.ntg.NTimelineWakeSleep;
import com.bskcare.ch.vo.rpt.RptMonitoringData;
import com.bskcare.ch.vo.tg.MedicineReminder;

@Service("monitoringDataService")
@SuppressWarnings("unchecked")
public class MonitoringDataServiceImpl implements MonitoringDataService {

	private transient final Logger log = Logger.getLogger(getClass());

	@Autowired
	private BloodPressureDao bloodPressureDao;
	@Autowired
	private BloodOxygenDao bloodOxygenDao;
	@Autowired
	private BloodSugarDao bloodSugarDao;
	@Autowired
	private ExpertAdviseDao expertAdviseDao;
	@Autowired
	private ElectrocardiogramDao electrocardiogramDao;
	@Autowired
	private RptMonitoringDataDao rptMonitoringDataDao;
	@Autowired
	private MSportDao mSportDao;
	@Autowired
	MonitoringDataDao monitoringDataDao;
	@Autowired
	ManageLogService mlogServic;
	@Autowired
	UnusualDataService unusualDataService;
	@Autowired
	TemperatureDao temperatureDao;
	@Autowired
	private TimeLineTaskDao lineTaskDao;
	@Autowired
	private BloodSugarTargetService bloodSugarTargetService;
	
	@Autowired
	private MedicineReminderDao medicineReminderDao;
	
	@Autowired
	private TgRecordFoodDao recordFoodDao;
	
	@Autowired
	private BloodSugarTargetDao bloodSugarTargetDao;
	
	@Autowired
	private NTimelineContentService ntimelineContentService ;
	@Autowired
	private NTimelineRuleCustomDao nTimelineRuleCustomDao ;
	
	@Autowired
	private NTimelineMealsDao nmealsDao;
	@Autowired
	private NTgSportDao nsportDao;
	@Autowired
	private NTimelineWakeSleepDao nsleepDao;
	@Autowired
	private NTimelineTaskDao ntaskDao;
	
	
	public String getBloodPressureList(BloodPressure bloodPressure,
			QueryInfo queryInfo, QueryCondition queryCondition) {
		// queryInfo.setSort("t.testDateTime") ;
		queryInfo.setSort("t.uploadDateTime");
		queryInfo.setOrder("desc");

		PageObject list = bloodPressureDao.getListByBloodPressure(
				bloodPressure, queryInfo, queryCondition);

		JSONObject jo = new JSONObject();
		jo.put("total", list.getTotalRecord());
		jo.put("listSize", list.getDatas().size());
		jo.put("type", "BloodPressure");
		jo.put("list", JsonUtils.getJsonString4JavaListDate(list.getDatas()));

		return jo.toString();
	}

	public PageObject getListByBloodPressure(BloodPressure bloodPressure,
			QueryInfo queryInfo, QueryCondition queryCondition) {
		return bloodPressureDao.getListByBloodPressure(bloodPressure,
				queryInfo, queryCondition);
	}

	public String getBloodPressureChart(BloodPressure bloodPressure,
			QueryInfo queryInfo, QueryCondition queryCondition, Integer interval) {
		JSONObject jo = new JSONObject();
		// 判断是否是时间段查询
		jo = this.getJSONObjectByQueryCondition(jo, queryCondition);

		// 获取最近一个月的画图开始时间和结束时间
		queryCondition = this.getSdateAndEdate(bloodPressure.getClientId(),
				"m_blood_pressure", queryCondition, -1, interval);

		// 因为后面要用到排序 所以使用queryInfo 设置一页大小150
		queryInfo.setPageSize(1000);
		queryInfo.setSort("t.testDateTime");
		// queryInfo.setSort("t.uploadDateTime") ;
		queryInfo.setOrder("desc");

		// 获得最近一个月的信息
		PageObject list = this.getListByBloodPressure(bloodPressure, queryInfo,
				queryCondition);
		List<BloodPressure> plist = list.getDatas();

		// 最后一次上传血压的数据
		BloodPressure eBloodPressure = null;
		// 最近一个月第一次上传血压的数据
		BloodPressure sBloodPressure = null;

		if (!CollectionUtils.isEmpty(plist)) {
			sBloodPressure = plist.get(plist.size() - 1);
			eBloodPressure = plist.get(0);

			Date sDate = sBloodPressure.getTestDateTime();
			Date eDate = eBloodPressure.getTestDateTime();

			jo.put("lastUploadDateTime", DateUtils.longDate(eDate));
			jo
					.put("sUploadDateTime", DateUtils.formatDate("yyyy-MM-dd",
							sDate));
			jo.put("days", DateUtils
					.getQuot(DateUtils.getDateByType(sDate, "yyyy-MM-dd"),
							DateUtils.getDateByType(eDate, "yyyy-MM-dd")));
		}

		// 如果判断为不是根据时间段查询，就不用修改最近一个月信息，和专家建议
		if (jo.get("queryType").equals("noByDate")) {
			this.disposeBloodPressureList(plist, jo);
			// 专家建议 ---血压
			this.getLastExpertAdvise(bloodPressure.getClientId(), 0, jo);
		}

		jo.put("total", list.getTotalRecord());
		jo.put("type", "BloodPressure");
		jo.put("list", JsonUtils.getJsonString4JavaListDate(plist));

		return jo.toString();
	}

	/**
	 * 处理血压结果
	 * 
	 * @param plist
	 * @param jo
	 * @param
	 * @param eBloodPressure
	 * @param bloodPressure
	 */
	public void disposeBloodPressureList(List<BloodPressure> plist,
			JSONObject jo) {
		// 异常次数
		int unusualCount = 0;
		// 理想血压次数
		int idealCount = 0;
		// 正常血压次数
		int normalCount = 0;
		// 正常血压偏高
		int normalMoreBig = 0;
		// 最高值
		int maxAcunt = 0;

		BloodPressure eBloodPressure = null;
		for (Iterator iterator = plist.iterator(); iterator.hasNext();) {
			BloodPressure bloodPressure2 = (BloodPressure) iterator.next();
			int tmp = MonitoringDataResult.getBloodPressureLevel(bloodPressure2
					.getSbp(), bloodPressure2.getDbp());
			if (tmp == 0) {
				unusualCount++;
			} else if (tmp == 1) {
				idealCount++;
			} else if (tmp == 2) {
				normalCount++;
			} else if (tmp == 3) {
				normalMoreBig++;
			} else if (tmp == 4) {
				unusualCount++;
			} else if (tmp == -1) {
				unusualCount++;
			}
			if (maxAcunt < bloodPressure2.getSbp()) {
				maxAcunt = bloodPressure2.getSbp();
			}
		}
		if (!CollectionUtils.isEmpty(plist)) {
			eBloodPressure = plist.get(plist.size()-1);
			jo.put("lastMonthData", eBloodPressure.getSbp() + "/"
					+ eBloodPressure.getDbp());
		} else {
			jo.put("lastMonthData", "0/0");
		}

		jo.put("idealCount", idealCount);
		jo.put("normalCount", normalCount);
		jo.put("normalMoreBig", normalMoreBig);
		jo.put("unusualCount", unusualCount);
		jo.put("maxAcunt", maxAcunt);
		jo.put("listSize", plist.size());
	}

	public void disposeRptBloodPressure(List<BloodPressure> plist, JSONObject jo) {

		int sbpMax = 0;
		int sbpMin = 0;
		int dbpMax = 0;
		int dbpMin = 0;

		for (Iterator iterator = plist.iterator(); iterator.hasNext();) {
			BloodPressure bloodPressure2 = (BloodPressure) iterator.next();
			if (sbpMax < bloodPressure2.getSbp()) {
				sbpMax = bloodPressure2.getSbp();
			}
			if (sbpMin > bloodPressure2.getSbp() || sbpMin == 0) {
				sbpMin = bloodPressure2.getSbp();
			}
			if (dbpMax < bloodPressure2.getDbp()) {
				dbpMax = bloodPressure2.getDbp();
			}
			if (dbpMin > bloodPressure2.getDbp() || dbpMin == 0) {
				dbpMin = bloodPressure2.getDbp();
			}

		}

		jo.put("sbpMax", sbpMax);
		jo.put("sbpMin", sbpMin);
		jo.put("dbpMax", dbpMax);
		jo.put("dbpMin", dbpMin);
	}

	public void getLastExpertAdvise(Integer clientId, Integer type,
			JSONObject jo) {
		// 专家建议 ---血压
		ExpertAdvise expertAdvise = new ExpertAdvise();
		expertAdvise.setClientId(clientId);
		expertAdvise.setTypeId(type);

		ExpertAdvise page = expertAdviseDao.getLastExpertAdvise(expertAdvise);
		jo.put("expertAdvise", JsonUtils.getJsonString4JavaPOJO(page,
				JsonUtils.LONG_DATE_PATTERN));
	}

	public String getBloodOxygenList(BloodOxygen bloodOxygen,
			QueryInfo queryInfo, QueryCondition queryCondition) {
		// queryInfo.setSort("t.testDateTime") ;
		queryInfo.setSort("t.uploadDateTime");
		queryInfo.setOrder("desc");

		PageObject list = bloodOxygenDao.getListByBloodOxygen(bloodOxygen,
				queryInfo, queryCondition);

		JSONObject jo = new JSONObject();
		jo.put("total", list.getTotalRecord());
		jo.put("type", "BloodOxygen");
		jo.put("listSize", list.getDatas().size());
		jo.put("list", JsonUtils.getJsonString4JavaListDate(list.getDatas()));

		return jo.toString();
	}

	public PageObject getListByBloodOxygen(BloodOxygen bloodOxygen,
			QueryInfo queryInfo, QueryCondition queryCondition) {
		return bloodOxygenDao.getListByBloodOxygen(bloodOxygen, queryInfo,
				queryCondition);
	}

	public String getBloodOxygenChart(BloodOxygen bloodOxygen,
			QueryInfo queryInfo, QueryCondition queryCondition, Integer interval) {
		JSONObject jo = new JSONObject();
		// 判断是否是时间段查询
		jo = this.getJSONObjectByQueryCondition(jo, queryCondition);
		queryCondition = this.getSdateAndEdate(bloodOxygen.getClientId(),
				"m_blood_oxygen", queryCondition, -1, interval);

		// 因为后面要用到排序 所以使用queryInfo 设置一页大小1000
		queryInfo.setPageSize(1000);
		queryInfo.setSort("t.testDateTime");
		// queryInfo.setSort("t.uploadDateTime") ;
		queryInfo.setOrder("desc");

		// 得到图表数据
		PageObject list = this.getListByBloodOxygen(bloodOxygen, queryInfo,
				queryCondition);
		List<BloodOxygen> plist = list.getDatas();

		BloodOxygen eBloodOxygen = null;
		BloodOxygen sBloodOxygen = null;
		if (!CollectionUtils.isEmpty(plist)) {
			sBloodOxygen = plist.get(plist.size() - 1);
			eBloodOxygen = plist.get(0);
			Date sDate = sBloodOxygen.getTestDateTime();
			Date eDate = eBloodOxygen.getTestDateTime();

			jo.put("lastUploadDateTime", DateUtils.longDate(eDate));
			jo
					.put("sUploadDateTime", DateUtils.formatDate("yyyy-MM-dd",
							sDate));
			jo.put("days", DateUtils
					.getQuot(DateUtils.getDateByType(sDate, "yyyy-MM-dd"),
							DateUtils.getDateByType(eDate, "yyyy-MM-dd")));
		} else {
			jo.put("lastMonthData", "0/0");

		}

		// 如果判断为不是根据时间段查询，就不用修改最近一个月信息，和专家建议
		if (jo.get("queryType").equals("noByDate")) {
			this.disposeBloodOxygenList(plist, jo);
			// 专家建议 ---血氧
			ExpertAdvise expertAdvise = new ExpertAdvise();
			expertAdvise.setClientId(bloodOxygen.getClientId());
			expertAdvise.setTypeId(1);
			ExpertAdvise page = expertAdviseDao
					.getLastExpertAdvise(expertAdvise);

			jo.put("expertAdvise", JsonUtils.getJsonString4JavaPOJO(page));
		}
		jo.put("total", list.getTotalRecord());
		jo.put("type", "BloodOxygen");
		jo.put("list", JsonUtils.getJsonString4JavaListDate(plist));

		return jo.toString();
	}

	public void disposeBloodOxygenList(List<BloodOxygen> plist, JSONObject jo) {
		// 正常血氧的次数
		int normalBloodOxygenCount = 0;
		// 正常脉率的次数
		int normalHeartbeaCount = 0;
		// 异常常血氧的次数
		int unusualBloodOxygenCount = 0;
		// 异常脉率的次数
		int unusualHeartbeaCount = 0;
		// 计算正常和异常次数
		for (Iterator iterator = plist.iterator(); iterator.hasNext();) {
			BloodOxygen bloodOxygen2 = (BloodOxygen) iterator.next();
			int tmp = MonitoringDataResult.getBloodOxygenState(bloodOxygen2
					.getBloodOxygen());
			if (tmp == 1) {
				normalBloodOxygenCount++;
			} else {
				unusualBloodOxygenCount++;
			}
			int tmp2 = MonitoringDataResult.getHeartbeatState(bloodOxygen2
					.getHeartbeat());
			if (tmp2 == 1) {
				normalHeartbeaCount++;
			} else {
				unusualHeartbeaCount++;
			}
		}
		BloodOxygen eBloodOxygen = new BloodOxygen();
		if (!CollectionUtils.isEmpty(plist)) {
			eBloodOxygen = plist.get(plist.size()-1);
		}

		jo.put("normalCount", normalBloodOxygenCount + "/"
				+ normalHeartbeaCount);
		jo.put("unusualCount", unusualBloodOxygenCount + "/"
				+ unusualHeartbeaCount);
		jo.put("maxAcunt", 150);
		jo.put("lastMonthData", eBloodOxygen.getBloodOxygen() + "/"
				+ eBloodOxygen.getHeartbeat());
		jo.put("listSize", plist.size());
	}

	public void disposeRptBloodOxygenList(List<BloodOxygen> plist, JSONObject jo) {
		int bloodOxygenMax = 0;
		int bloodOxygenMin = 0;

		int heartbeatMax = 0;
		int heartbeatMin = 0;

		for (Iterator iterator = plist.iterator(); iterator.hasNext();) {
			BloodOxygen bloodOxygen2 = (BloodOxygen) iterator.next();
			if (bloodOxygenMax < bloodOxygen2.getBloodOxygen()) {
				bloodOxygenMax = bloodOxygen2.getBloodOxygen();
			}
			if (bloodOxygenMin > bloodOxygen2.getBloodOxygen()
					|| bloodOxygenMin == 0) {
				bloodOxygenMin = bloodOxygen2.getBloodOxygen();
			}
			if (heartbeatMax < bloodOxygen2.getHeartbeat()) {
				heartbeatMax = bloodOxygen2.getHeartbeat();
			}
			if (heartbeatMin > bloodOxygen2.getHeartbeat() || heartbeatMin == 0) {
				heartbeatMin = bloodOxygen2.getHeartbeat();
			}
		}
		jo.put("bloodOxygenMax", bloodOxygenMax);
		jo.put("bloodOxygenMin", bloodOxygenMin);
		jo.put("heartbeatMax", heartbeatMax);
		jo.put("heartbeatMin", heartbeatMin);
	}

	public String getBloodSugarList(BloodSugar bloodSugar, QueryInfo queryInfo,
			QueryCondition queryCondition) {
		// queryInfo.setSort("t.testDateTime") ;
		queryInfo.setSort("t.uploadDateTime");
		queryInfo.setOrder("desc");

		PageObject list = bloodSugarDao.getListByBloodSugar(bloodSugar,
				queryInfo, queryCondition);

		JSONObject jo = new JSONObject();
		jo.put("total", list.getTotalRecord());
		// 如果类型==1为空腹血糖 否则 为餐后2小时血糖
		if (bloodSugar.getBloodSugarType() == 1) {
			jo.put("type", "BloodSugar");
		} else if(bloodSugar.getBloodSugarType() == 2) {
			jo.put("type", "BloodSugar2h");
		}else {
			jo.put("type", "BloodSugarOther");
		}
		jo.put("list", JsonUtils.getJsonString4JavaListDate(list.getDatas()));
		jo.put("listSize", list.getDatas().size());

		return jo.toString();
	}

	public PageObject getListByBloodSugar(BloodSugar bloodSugar,
			QueryInfo queryInfo, QueryCondition queryCondition) {
		return bloodSugarDao.getListByBloodSugar(bloodSugar, queryInfo,
				queryCondition);
	}

	public String getBloodSugarChart(BloodSugar bloodSugar,
			QueryInfo queryInfo, QueryCondition queryCondition, Integer interval) {
		JSONObject jo = new JSONObject();
		jo = this.getJSONObjectByQueryCondition(jo, queryCondition);
		// 画图 起始时间 和结束时间
		queryCondition = this.getSdateAndEdate(bloodSugar.getClientId(),
				"m_blood_sugar", queryCondition,
				bloodSugar.getBloodSugarType(), interval);
		// 因为后面要用到排序 所以使用queryInfo 设置一页大小1000
		queryInfo.setPageSize(1000);
		// 得到图表数据
		queryInfo.setSort("t.testDateTime");
		// queryInfo.setSort("t.uploadDateTime") ;
		queryInfo.setOrder("desc");
		PageObject list = this.getListByBloodSugar(bloodSugar, queryInfo,
				queryCondition);
		List<BloodSugar> plist = list.getDatas();

		BloodSugar eBloodSugar = null;
		BloodSugar sBloodSugar = null;
		if (!CollectionUtils.isEmpty(plist)) {
			sBloodSugar = plist.get(plist.size() - 1);
			eBloodSugar = plist.get(0);
			Date sDate = sBloodSugar.getTestDateTime();
			Date eDate = eBloodSugar.getTestDateTime();

			jo.put("lastUploadDateTime", DateUtils.longDate(eDate));
			jo
					.put("sUploadDateTime", DateUtils.formatDate("yyyy-MM-dd",
							sDate));
			jo.put("days", DateUtils
					.getQuot(DateUtils.getDateByType(sDate, "yyyy-MM-dd"),
							DateUtils.getDateByType(eDate, "yyyy-MM-dd")));
		}

		// 如果类型==1为空腹血糖 否则 为餐后2小时血糖
		if (bloodSugar.getBloodSugarType() == 1) {
			jo.put("type", "BloodSugar");
		} else if(bloodSugar.getBloodSugarType() == 2){
			jo.put("type", "BloodSugar2h");
		}else {
			jo.put("type", "BloodSugarOther");
		}

		if (jo.get("queryType").equals("noByDate")) {
			this.disposeBloodSugarList(plist, jo);
			// 专家建议 ---血糖
			ExpertAdvise expertAdvise = new ExpertAdvise();
			expertAdvise.setClientId(bloodSugar.getClientId());
			// 判断为空腹血糖
			if (bloodSugar.getBloodSugarType() == 1) {
				expertAdvise.setTypeId(2);
			} else {
				expertAdvise.setTypeId(3);
			}

			ExpertAdvise page = expertAdviseDao
					.getLastExpertAdvise(expertAdvise);
			jo.put("expertAdvise", JsonUtils.getJsonString4JavaPOJO(page));

		}
		jo.put("total", list.getTotalRecord());
		jo.put("list", JsonUtils.getJsonString4JavaListDate(plist));

		return jo.toString();
	}

	public PageObject getElectrocardiogramList(
			Electrocardiogram electrocardiogram, QueryInfo queryInfo,
			QueryCondition queryCondition) {
		return electrocardiogramDao.getListByElectrocardiogram(
				electrocardiogram, queryInfo, queryCondition);
	}

	public String getElectrocardiogram(Electrocardiogram electrocardiogram,
			QueryInfo queryInfo, QueryCondition queryCondition) {
		// queryInfo.setSort("t.testDateTime") ;
		queryInfo.setSort("t.uploadDateTime");
		queryInfo.setOrder("desc");
		PageObject list = this.getElectrocardiogramList(electrocardiogram,
				queryInfo, queryCondition);
		ExpertAdvise expertAdvise = new ExpertAdvise();
		expertAdvise.setClientId(electrocardiogram.getClientId());
		expertAdvise.setTypeId(4);

		ExpertAdvise page = expertAdviseDao.getLastExpertAdvise(expertAdvise);

		JSONObject jo = new JSONObject();
		jo.put("expertAdvise", JsonUtils.getJsonString4JavaPOJO(page));
		jo.put("total", list.getTotalRecord());
		jo.put("type", "Electrocardiogram");
		jo.put("listSize", list.getDatas().size());

		JSONArray ja = new JSONArray();
		String basePath = SystemConfig.getString("image_base_url");
		if (null != list && null != list.getDatas()
				&& list.getDatas().size() > 0) {
			for (int i = 0; i < list.getDatas().size(); i++) {
				Electrocardiogram e = (Electrocardiogram) list.getDatas()
						.get(i);
				e.getAttachmentUrl();

				JSONObject oecg = JsonUtils.getJsonObject4JavaPOJO(e,
						DateJsonValueProcessor.LONG_DATE_PATTERN);

				oecg.put("attachmentUrl", basePath + e.getAttachmentUrl());

				ja.add(oecg);
			}
		}

		jo.put("list", ja.toString());

		return jo.toString();
	}

	public String getElectrocardiogramChart(
			Electrocardiogram electrocardiogram, QueryInfo queryInfo) {
		// 得到图表数据
		PageObject list = electrocardiogramDao.getListByElectrocardiogram(
				electrocardiogram, null, null);
		List<BloodOxygen> plist = list.getDatas();

		JSONObject jo = new JSONObject();
		jo.put("total", list.getTotalRecord());
		jo.put("type", "Electrocardiogram");
		jo.put("lastUploadDateTime", DateUtils.longDate(plist.get(0)
				.getTestDateTime()));
		jo.put("sUploadDateTime", DateUtils.longDate(plist
				.get(plist.size() - 1).getTestDateTime()));
		jo.put("list", JsonUtils.getJsonString4JavaListDate(plist));

		return jo.toString();
	}

	public QueryCondition getSdateAndEdate(int clientId, String table,
			QueryCondition queryCondition, int type, Integer interval) {
		// 未按照时间段查询，查询全部信息
		if (queryCondition == null) {
			// 画图 起始时间 和结束时间
			/***
			 * 最近某个人的最后上传时间 Date[] myDate =
			 * bloodPressureDao.getLastMonthDate(clientId,table,type,interval) ;
			 **/
			Date[] myDate = new Date[2];
			Date now = new Date();
			myDate[0] = now;
			myDate[1] = DateUtils.getAppointDate(now, interval);

			if (myDate == null) {
				queryCondition = new QueryCondition();
				queryCondition.setBeginTime(null);
				queryCondition.setEndTime(null);
			} else {
				queryCondition = new QueryCondition();
				queryCondition.setBeginTime(myDate[1]);
				queryCondition.setEndTime(myDate[0]);
			}
		}
		return queryCondition;
	}

	// 如果查询时间为null 或者 开始日期和结束日期都是null 表示不是通过时间段查询
	public JSONObject getJSONObjectByQueryCondition(JSONObject jo,
			QueryCondition queryCondition) {
		if (queryCondition == null
				|| (queryCondition.getBeginTime() == null && queryCondition
						.getEndTime() == null)) {
			jo.put("queryType", "noByDate");
		} else {
			jo.put("queryType", "byDate");
		}
		return jo;
	}

	public String getTodayTask(ClientInfo clientInfo, QueryInfo queryInfo) {
		List<BloodPressure> bloodPressureList = bloodPressureDao
				.getTodayUploadDateTimeList(clientInfo.getId());
		List<BloodOxygen> bloodOxygensList = bloodOxygenDao
				.getTodayUploadDateTimeList(clientInfo.getId());
		List<BloodSugar> bloodSugarList = bloodSugarDao
				.getTodayUploadDateTimeList(clientInfo.getId(), 1);
		List<BloodSugar> bloodSugarList2h = bloodSugarDao
				.getTodayUploadDateTimeList(clientInfo.getId(), 2);
		List<Electrocardiogram> elecList = electrocardiogramDao
				.getTodayUploadDateTimeList(clientInfo.getId());
		List<MSport> mList = mSportDao.getTodayUploadDateTimeList(clientInfo
				.getId());

		BloodPressure bloodPressure = (CollectionUtils
				.isEmpty(bloodPressureList) == true ? null : bloodPressureList
				.get(0));
		BloodOxygen bloodOxygen = (CollectionUtils.isEmpty(bloodOxygensList) == true ? null
				: bloodOxygensList.get(0));
		BloodSugar bloodSugar = (CollectionUtils.isEmpty(bloodSugarList) == true ? null
				: bloodSugarList.get(0));
		BloodSugar bloodSugar2h = (CollectionUtils.isEmpty(bloodSugarList2h) == true ? null
				: bloodSugarList2h.get(0));
		Electrocardiogram electrocardiogram = (CollectionUtils
				.isEmpty(elecList) == true ? null : elecList.get(0));
		MSport msport = CollectionUtils.isEmpty(mList) == true ? null : mList
				.get(0);

		JSONObject jo = new JSONObject();
		if (bloodPressure != null) {
			jo.put("bloodPressure", "yes");
		} else {
			jo.put("bloodPressure", "no");
		}
		if (bloodOxygen != null) {
			jo.put("bloodOxygen", "yes");
		} else {
			jo.put("bloodOxygen", "no");
		}
		if (bloodSugar != null) {
			jo.put("bloodSugar", "yes");
		} else {
			jo.put("bloodSugar", "no");
		}
		if (bloodSugar2h != null) {
			jo.put("bloodSugar2h", "yes");
		} else {
			jo.put("bloodSugar2h", "no");
		}
		if (electrocardiogram != null) {
			jo.put("electrocardiogram", "yes");
		} else {
			jo.put("electrocardiogram", "no");
		}
		if (msport != null) {
			jo.put("msport", "yes");
		} else {
			jo.put("msport", "no");
		}

		return jo.toString();
	}

	public String monitoringLatUploadDate(ClientInfo clientInfo) {
		BloodPressure bloodPressure = bloodPressureDao
				.getLastUploadDateTime(clientInfo.getId());
		BloodOxygen bloodOxygen = bloodOxygenDao
				.getLastUploadDateTime(clientInfo.getId());
		BloodSugar bloodSugar = bloodSugarDao.getLastUploadDateTime(clientInfo
				.getId(), 1);
		BloodSugar bloodSugar2h = bloodSugarDao.getLastUploadDateTime(
				clientInfo.getId(), 2);
		Electrocardiogram electrocardiogram = electrocardiogramDao
				.getLastUploadDateTime(clientInfo.getId());

		JSONObject jo = new JSONObject();
		ArrayList<Date> list = new ArrayList<Date>();

		list
				.add(bloodPressure != null ? bloodPressure.getTestDateTime()
						: null);
		list.add(bloodOxygen != null ? bloodOxygen.getTestDateTime() : null);
		list.add(bloodSugar != null ? bloodSugar.getTestDateTime() : null);
		list.add(bloodSugar2h != null ? bloodSugar2h.getTestDateTime() : null);
		list.add(electrocardiogram != null ? electrocardiogram
				.getTestDateTime() : null);

		Date max = DateUtils.getMaxDate(list);
		jo.put("lastDate", max != null ? DateUtils
				.formatDate("yyyy年M月dd日", max) : "");

		return jo.toString();
	}

	public Object queryLimitPressure(QueryCondition qc, BloodPressure bp) {
		return bloodPressureDao.queryLimitPressure(qc, bp);
	}

	public Object queryLimiteSpO2(QueryCondition qc, BloodOxygen bo) {
		return bloodOxygenDao.queryLimiteSpO2(qc, bo);
	}

	public Object queryLimiteSugar(QueryCondition qc, BloodSugar bs) {
		return bloodSugarDao.queryLimiteSugar(qc, bs);
	}

	public List<RptMonitoringData> getReportMonitoringData(
			ClientInfo clientInfo, Integer rptId) {

		return rptMonitoringDataDao.getReportMonitoringData(clientInfo, rptId);
	}

	public String queryElectrocardiogramData(Integer clientId) {
		JSONObject json = new JSONObject();
		List<Electrocardiogram> list = electrocardiogramDao
				.queryElectrocardiogramData(clientId);
		if (!CollectionUtils.isEmpty(list)) {
			Electrocardiogram electrocardiogram = list.get(0);
			json.put("electrocardiogram", JsonUtils.getJsonString4JavaPOJO(
					electrocardiogram, JsonUtils.LONG_DATE_PATTERN));
		}
		return json.toString();
	}

	public void disposeBloodSugarList(List<BloodSugar> plist, JSONObject jo) {
		int normalCount = 0;
		int unusualCount = 0;
		/*if (!CollectionUtils.isEmpty(plist)
				&& plist.get(0).getBloodSugarType() == 1) {
			for (Iterator iterator = plist.iterator(); iterator.hasNext();) {
				BloodSugar bloodSugar2 = (BloodSugar) iterator.next();
				if (null != bloodSugar2.getState()
						&& bloodSugar2.getState() == 0) {
					normalCount++;
				} else {
					unusualCount++;
				}
			}
		}
		// 餐后2小时血糖
		if (!CollectionUtils.isEmpty(plist)
				&& plist.get(0).getBloodSugarType() == 2) {
			for (Iterator iterator = plist.iterator(); iterator.hasNext();) {
				BloodSugar bloodSugar2 = (BloodSugar) iterator.next();
				if (null != bloodSugar2.getState()
						&& bloodSugar2.getState() == 0) {
					normalCount++;
				} else {
					unusualCount++;
				}
			}
		}*/
		if (!CollectionUtils.isEmpty(plist)) {
			for (Iterator iterator = plist.iterator(); iterator.hasNext();) {
				BloodSugar bloodSugar = (BloodSugar) iterator.next();
				if (null != bloodSugar.getState()
						&& bloodSugar.getState() == 0) {
					normalCount++;
				} else {
					unusualCount++;
				}
			}
		}
		BloodSugar eBloodSugar = null;
		if (!CollectionUtils.isEmpty(plist)) {
			eBloodSugar = plist.get(plist.size()-1);
			jo.put("lastMonthData", eBloodSugar.getBloodSugarValue());
		} else {
			jo.put("lastMonthData", 0);
		}
		jo.put("normalCount", normalCount);
		jo.put("unusualCount", unusualCount);
		jo.put("listSize", plist.size());

	}

	public void disposeRptBloodSugarList(List<BloodSugar> plist, JSONObject jo) {
		double bloodSugarMax = 0;
		double bloodSugarMin = 0;

		for (Iterator iterator = plist.iterator(); iterator.hasNext();) {
			BloodSugar bloodSugar2 = (BloodSugar) iterator.next();

			if (bloodSugarMax < bloodSugar2.getBloodSugarValue()) {
				bloodSugarMax = bloodSugar2.getBloodSugarValue();
			}
			if (bloodSugarMin > bloodSugar2.getBloodSugarValue()
					|| bloodSugarMin == 0) {
				bloodSugarMin = bloodSugar2.getBloodSugarValue();
			}

		}
		jo.put("bloodSugarMax", bloodSugarMax);
		jo.put("bloodSugarMin", bloodSugarMin);
	}

	public void disposeElectrocardiogram(List<Electrocardiogram> pList,
			JSONObject jo) {
		int normalCount = 0;
		int unusualCount = 0;

		for (Iterator iterator = pList.iterator(); iterator.hasNext();) {
			Electrocardiogram electrocardiogram = (Electrocardiogram) iterator
					.next();
			if (null != electrocardiogram.getState()
					&& electrocardiogram.getState() == 0) {
				normalCount++;
			} else {
				unusualCount++;
			}
		}
		jo.put("normalCount", normalCount);
		jo.put("unusualCount", unusualCount);
		jo.put("listSize", pList.size());
	}

	public PageObject queryObject(UploadExtend uploadExtend, QueryCondition qc,
			QueryInfo qi) {
		return monitoringDataDao.queryObject(uploadExtend, qc, qi);
	}

	public Electrocardiogram getElectrocardiogram(Integer elecId) {
		if (elecId != null) {
			return electrocardiogramDao.load(elecId);
		}
		return null;
	}

	public void dealMonitoringData(Integer dataId, int type) {
		if (null != dataId && type != 0) {
			switch (type) {
			case UnusualData.BLOODPRESSURE:
				handlerBloodPressure(dataId, type);
				break;
			case UnusualData.BLOODOXYGEN:
				handlerBloodOxygen(dataId, type);
				break;
			case UnusualData.BLOODSUGAR:
			case UnusualData.BLOODSUGAR2H:
			case UnusualData.SUGAR_ZAOCAN_BEFORE:
			case UnusualData.SUGAR_ZAOCAN_AFTER:
			case UnusualData.SUGAR_WUCAN_BEFORRE:
			case UnusualData.SUGAR_WUCAN_AFTER:
			case UnusualData.SUGAR_WANCAN_BEFORE:
			case UnusualData.SUGAR_WANCAN_AFTER:
			case UnusualData.SUGAR_SLEEP_BEFORE:
			case UnusualData.SUGAR_LINGCHEN:
				handlerBloodSugar(dataId, type);
				break;
			case UnusualData.ELECTROCARDIO:
				handlerEcg(dataId, type);
				break;
			case UnusualData.TEMPERATURE:
				handlerTemperature(dataId, type);
				break;
			}
		}
	}

	/**
	 * 处理心电
	 */
	private void handlerEcg(Integer dataId, int type) {
		Electrocardiogram ecg = electrocardiogramDao.load(dataId);
		if (null != ecg && MonConstant.DISPOSED != ecg.getDispose()) {
			ecg.setDispose(MonConstant.DISPOSED);
			electrocardiogramDao.update(ecg);
		}
		// 如果这条数据为异常数据，则需要将异常数据表中的对应数据一同表示为已处理
		if (MonConstant.STATE_NORMAL != ecg.getState()) {
			flagUnusualData(ecg.getId(), type, ecg.getClientId());
		}
	}

	/**
	 * 处理血糖
	 */
	private void handlerBloodSugar(Integer dataId, int type) {
		BloodSugar bs = bloodSugarDao.load(dataId);
		if (null != bs && MonConstant.DISPOSED != bs.getDispose()) {
			bs.setDispose(MonConstant.DISPOSED);
			bloodSugarDao.update(bs);
		}

		// 如果这条数据为异常数据，则需要将异常数据表中的对应数据一同表示为已处理
		if (MonConstant.STATE_NORMAL != bs.getState()) {
			flagUnusualData(bs.getId(), type, bs.getClientId());
		}
	}

	/**
	 * 处理血氧
	 */
	private void handlerBloodOxygen(Integer dataId, int type) {
		BloodOxygen bo = bloodOxygenDao.load(dataId);
		if (null != bo && MonConstant.DISPOSED != bo.getDispose()) {
			bo.setDispose(MonConstant.DISPOSED);
			bloodOxygenDao.update(bo);
		}

		// 如果这条数据为异常数据，则需要将异常数据表中的对应数据一同表示为已处理
		if (MonConstant.STATE_NORMAL != bo.getState()) {
			flagUnusualData(bo.getId(), type, bo.getClientId());
		}
	}

	/**
	 * 处理血压
	 */
	private void handlerBloodPressure(Integer dataId, int type) {
		BloodPressure bp = bloodPressureDao.load(dataId);

		if (null != bp && MonConstant.DISPOSED != bp.getDispose()) {
			bp.setDispose(MonConstant.DISPOSED);
			bloodPressureDao.update(bp);
		}

		// 如果这条数据为异常数据，则需要将异常数据表中的对应数据一同表示为已处理
		if (MonConstant.STATE_NORMAL != bp.getState()) {
			flagUnusualData(bp.getId(), type, bp.getClientId());
		}
	}

	/**
	 * 处理体温
	 */
	private void handlerTemperature(Integer dataId, int type) {
		Temperature temp = temperatureDao.load(dataId);
		if (null != temp && temp.getDispose() != MonConstant.DISPOSED) {
			temp.setDispose(MonConstant.DISPOSED);
			temperatureDao.update(temp);
		}
		// 如果这条数据为异常数据，则需要将异常数据表中的对应数据一同表示为已处理
		if (temp.getState() != MonConstant.STATE_NORMAL) {
			flagUnusualData(temp.getId(), type, temp.getClientId());
		}
	}

	/**
	 * 标记异常数据表中对应数据为“已处理”
	 */
	private void flagUnusualData(Integer dataId, Integer type, Integer clientId) {
		unusualDataService.flagUnusualData(dataId, type, clientId,
				UnusualData.HANDLED);
	}

	public String queryTemperatureList(Temperature temperature,
			QueryInfo queryInfo, QueryCondition queryCondition) {
		// queryInfo.setSort("t.testDateTime") ;
		queryInfo.setSort("t.uploadDateTime");
		queryInfo.setOrder("desc");

		PageObject list = temperatureDao.queryTemperatureList(temperature,
				queryInfo, queryCondition);
		JSONObject jo = new JSONObject();
		jo.put("total", list.getTotalRecord());
		jo.put("listSize", list.getDatas().size());
		jo.put("type", "Temperature");
		jo.put("list", JsonUtils.getJsonString4JavaListDate(list.getDatas()));

		return jo.toString();
	}

	public String queryTemperatureChart(Temperature temperature,
			QueryInfo queryInfo, QueryCondition queryCondition, Integer interval) {
		JSONObject jo = new JSONObject();
		// 判断是否是时间段查询
		jo = this.getJSONObjectByQueryCondition(jo, queryCondition);
		queryCondition = this.getSdateAndEdate(temperature.getClientId(),
				"m_temperature", queryCondition, -1, interval);

		// 因为后面要用到排序 所以使用queryInfo 设置一页大小1000
		queryInfo.setPageSize(1000);
		queryInfo.setSort("t.testDateTime");
		// queryInfo.setSort("t.uploadDateTime") ;
		queryInfo.setOrder("desc");
		// 得到图表数据
		PageObject list = temperatureDao.queryTemperatureList(temperature,
				queryInfo, queryCondition);
		List<Temperature> plist = list.getDatas();
		Temperature sTemperature = null;
		Temperature eTemperature = null;
		if (!CollectionUtils.isEmpty(plist)) {
			sTemperature = plist.get(plist.size() - 1);
			eTemperature = plist.get(0);
			Date sDate = sTemperature.getTestDateTime();
			Date eDate = eTemperature.getTestDateTime();
			jo.put("lastUploadDateTime", DateUtils.longDate(eDate));
			jo
					.put("sUploadDateTime", DateUtils.formatDate("yyyy-MM-dd",
							sDate));
			jo.put("days", DateUtils
					.getQuot(DateUtils.getDateByType(sDate, "yyyy-MM-dd"),
							DateUtils.getDateByType(eDate, "yyyy-MM-dd")));
		} else {
			jo.put("lastMonthData", "0");
		}

		// 如果判断为不是根据时间段查询，就不用修改最近一个月信息，和专家建议
		if (jo.get("queryType").equals("noByDate")) {
			this.disposeTemperatureList(plist, jo);
		}

		jo.put("total", list.getTotalRecord());
		jo.put("type", "Temperature");
		jo.put("list", JsonUtils.getJsonString4JavaListDate(plist));
		return jo.toString();
	}

	public void disposeTemperatureList(List<Temperature> plist, JSONObject jo) {
		// 正常体温次数
		int normalTemperatureCount = 0;
		// 异常体温次数
		int unusualTemperatureCount = 0;
		// 偏高体温次数
		int heighTemperatureCount = 0;
		// 偏低体温次数
		int lowTemperatureCount = 0;
		for (Temperature temperature : plist) {
			if (35.8 < temperature.getTemperature()
					&& temperature.getTemperature() <= 37.4) {
				normalTemperatureCount++;
			} else if (temperature.getTemperature() >= 37.5) {
				unusualTemperatureCount++;
				heighTemperatureCount++;
			} else if (temperature.getTemperature() <= 35.8) {
				unusualTemperatureCount++;
				lowTemperatureCount++;
			}
		}

		Temperature temperature = null;
		if (!CollectionUtils.isEmpty(plist)) {
			temperature = plist.get(0);
		}
		if (temperature != null) {
			jo.put("latestData", temperature.getTemperature());
		}
		jo.put("normalCount", normalTemperatureCount);
		jo.put("unusualCount", unusualTemperatureCount);
		jo.put("heighCount", heighTemperatureCount);
		jo.put("lowCount", lowTemperatureCount);
		jo.put("listSize", plist.size());
	}

	public String queryTemperatureListAndroid(Temperature temperature,
			QueryInfo queryInfo, QueryCondition queryCondition) {
		queryInfo.setSort("t.uploadDateTime");
		queryInfo.setOrder("desc");
		PageObject list = temperatureDao.queryTemperatureList(temperature,
				queryInfo, queryCondition);
		JSONObject jo = new JSONObject();
		List<Temperature> lst = list.getDatas();
		if (!CollectionUtils.isEmpty(lst)) {
			JSONArray ja = new JSONArray();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Temperature temp : lst) {
				JSONObject json = new JSONObject();
				json.put("testDateTime", sdf.format(temp.getTestDateTime()));
				json.put("result", temp.getResult());
				ja.add(json);
			}
			jo.put("code", "1");
			jo.put("msg", "成功");
			jo.put("data", ja.toArray());
		} else {
			jo.put("code", "1");
			jo.put("msg", "您没有上传体温信息");
			jo.put("data", "");
		}
		return jo.toString();
	}

	public String queryTemperatureChartAndroid(Temperature temperature,
			QueryInfo queryInfo, QueryCondition queryCondition, Integer interval) {
		JSONObject jo = new JSONObject();
		queryCondition = this.getSdateAndEdate(temperature.getClientId(),
				"m_temperature", queryCondition, -1, interval);
		queryInfo.setPageSize(1000);
		queryInfo.setSort("t.testDateTime");
		queryInfo.setOrder("desc");
		PageObject list = temperatureDao.queryTemperatureList(temperature,
				queryInfo, queryCondition);
		List<Temperature> lst = list.getDatas();
		if (!CollectionUtils.isEmpty(lst)) {
			JSONObject jsono = new JSONObject();
			JSONArray ja = new JSONArray();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Temperature temp : lst) {
				JSONObject json = new JSONObject();
				json.put("testDateTime", sdf.format(temp.getTestDateTime()));
				json.put("temperature", temp.getTemperature());
				ja.add(json);
			}
			disposeTemperatureList(lst, jsono);
			jsono.put("list", ja.toArray());

			jo.put("code", "1");
			jo.put("msg", "成功");
			jo.put("data", jsono.toString());
		} else {
			jo.put("code", "1");
			jo.put("msg", "您没有上传体温数据");
			jo.put("data", "");
		}
		return jo.toString();
	}

	public String queryBloodSugar(Integer clientId, String testDate,
			QueryInfo queryInfo) {
		JSONObject jo = new JSONObject();

		BloodSugar sugar = new BloodSugar();
		sugar.setClientId(clientId);
		sugar.setBloodSugarType(BloodSugar.SUGAR_TYPE);

		queryInfo.setPageSize(999999);
		queryInfo.setSort("t.testDateTime");
		queryInfo.setOrder("asc");

		QueryCondition queryCondition = new QueryCondition();
		// 前两个月日期
		Date startDate = DateUtils.getBeforeMonth(testDate, -2);
		// 当前月日期
		Date endDate = DateUtils.getBeforeMonth(testDate, 0);
		queryCondition.setBeginTime(startDate);
		queryCondition.setEndTime(endDate);

		List<Double> lstSugarVal = new ArrayList<Double>();
		JSONObject jsonAll = new JSONObject();

		// 空腹血糖信息
		PageObject pagerBefore = bloodSugarDao.getListByBloodSugar(sugar,
				queryInfo, queryCondition);
		List<BloodSugar> lstBefore = pagerBefore.getDatas();
		setBloodSugar(lstBefore, jsonAll, "Before", lstSugarVal);

		// 餐后血糖信息
		sugar.setBloodSugarType(BloodSugar.SUGAR_TYPE_2H);
		PageObject pagerAfter = bloodSugarDao.getListByBloodSugar(sugar,
				queryInfo, queryCondition);
		List<BloodSugar> lstAfter = pagerAfter.getDatas();
		setBloodSugar(lstAfter, jsonAll, "After", lstSugarVal);

		jo.put("code", 1);
		jo.put("msg", "请求数据成功");
		jo.put("data", jsonAll.toString());
		return jo.toString();
	}

	public void setBloodSugar(List<BloodSugar> lstSugar, JSONObject jo,
			String type, List<Double> lstSugarVal) {
		if (!CollectionUtils.isEmpty(lstSugar)) {
			JSONArray ja = new JSONArray();
			for (BloodSugar bloodSugar : lstSugar) {
				JSONObject json = new JSONObject();
				json.put("val", bloodSugar.getBloodSugarValue());
				lstSugarVal.add(bloodSugar.getBloodSugarValue());
				json.put("testDate", DateUtils.longDate(bloodSugar
						.getTestDateTime()));
				ja.add(json);
			}
			jo.put("sugar" + type + "", ja.toArray());
		} else {
			jo.put("sugar" + type + "", "");
		}

		if (!CollectionUtils.isEmpty(lstSugar)) {
			Collections.sort(lstSugarVal);
			jo.put("sugarBig", lstSugarVal.get(lstSugarVal.size() - 1));
		} else {
			jo.put("sugarBig", 0);
		}
	}

	public String queryBloodByThreeMonth(Integer clientId, QueryInfo queryInfo,
			QueryCondition qc) {
		JSONObject ja = new JSONObject();
		PageObject<BloodPressure> list = bloodPressureDao
		.queryBloodByThreeMonth(clientId, queryInfo, qc);
		List<BloodPressure> bloodList = list.getDatas();
		setBloodPressure(ja, bloodList);
		return ja.toString();
	}
	
	
	public void setBloodPressure(JSONObject ja, List<BloodPressure> list){
		JSONArray jsrr = new JSONArray();
		JSONArray jbrr = new JSONArray();
		int sbpMax = 0;// 收缩压最大值
		int dbpMax = 0;// 收缩压最大值
		
		if (!CollectionUtils.isEmpty(list)) {
			for (BloodPressure bp : list) {
				JSONObject js = new JSONObject();
				js.put("val", bp.getSbp());
				js.put("testDate", DateUtils.formatDate(
						DateUtils.LONG_DATE_PATTERN, bp.getTestDateTime()));
				jsrr.add(js);

				JSONObject jd = new JSONObject();
				jd.put("val", bp.getDbp());
				jd.put("testDate", DateUtils.formatDate(
						DateUtils.LONG_DATE_PATTERN, bp.getTestDateTime()));
				jbrr.add(jd);

				if (sbpMax < bp.getSbp()) {
					sbpMax = bp.getSbp();
				}
				if (dbpMax < bp.getDbp()) {
					dbpMax = bp.getDbp();
				}
			}
		}
		ja.put("sbp", jsrr);
		ja.put("dbp", jbrr);

		if (sbpMax > dbpMax || sbpMax == dbpMax) {
			ja.put("bpMax", sbpMax);
		}
		if (sbpMax < dbpMax) {
			ja.put("bpMax", dbpMax);
		}
	}
	

	public String queryClientBloodSugar(Integer clientId, String testDate,
			QueryInfo queryInfo) {
		JSONObject jo = new JSONObject();
		BloodSugar sugar = new BloodSugar();
		sugar.setClientId(clientId);

		queryInfo.setPageSize(999999);
		queryInfo.setSort("t.testDateTime");
		queryInfo.setOrder("asc");

		QueryCondition queryCondition = new QueryCondition();
		// 前两个月日期
		Date startDate = DateUtils.getDateByType(DateUtils.getBeforeMonth(
				testDate, -2), "yyyy-MM");
		// 当前月日期
		Date endDate = DateUtils.getBeforeMonth(testDate, 0);

		queryCondition.setBeginTime(startDate);
		queryCondition.setEndTime(endDate);

		List<Double> lstDataVal = new ArrayList<Double>();

		PageObject pager = bloodSugarDao.getListByBloodSugar(sugar, queryInfo,
				queryCondition);
		// 查询所有血糖的信息
		List<BloodSugar> lst = pager.getDatas();
		if (!CollectionUtils.isEmpty(lst)) {
			JSONArray zaoBefore = new JSONArray(); // 早餐前血糖
			JSONArray zaoAfter = new JSONArray(); // 早餐后血糖
			JSONArray wuBeftre = new JSONArray(); // 午餐前血糖
			JSONArray wuAfter = new JSONArray(); // 午餐后血糖
			JSONArray wanBefore = new JSONArray(); // 晚餐前血糖
			JSONArray wanAfter = new JSONArray(); // 晚餐后血糖
			JSONArray sleepBefore = new JSONArray(); // 睡觉前血糖
			JSONArray lingchen = new JSONArray(); // 凌晨血糖
			JSONObject json = new JSONObject();
			for (BloodSugar bloodSugar : lst) {
				JSONObject jso = new JSONObject();
				if (bloodSugar.getBloodSugarType() == BloodSugar.SUGAR_ZAOCAN_BEFORE) {
					jso.put("val", bloodSugar.getBloodSugarValue());
					lstDataVal.add(bloodSugar.getBloodSugarValue());
					jso.put("testDate", DateUtils.longDate(bloodSugar
							.getTestDateTime()));
					zaoBefore.add(jso);
				} else if (bloodSugar.getBloodSugarType() == BloodSugar.SUGAR_ZAOCAN_AFTER) {
					jso.put("val", bloodSugar.getBloodSugarValue());
					lstDataVal.add(bloodSugar.getBloodSugarValue());
					jso.put("testDate", DateUtils.longDate(bloodSugar
							.getTestDateTime()));
					zaoAfter.add(jso);
				} else if (bloodSugar.getBloodSugarType() == BloodSugar.SUGAR_WUCAN_BEFORRE) {
					jso.put("val", bloodSugar.getBloodSugarValue());
					lstDataVal.add(bloodSugar.getBloodSugarValue());
					jso.put("testDate", DateUtils.longDate(bloodSugar
							.getTestDateTime()));
					wuBeftre.add(jso);
				} else if (bloodSugar.getBloodSugarType() == BloodSugar.SUGAR_WUCAN_AFTER) {
					jso.put("val", bloodSugar.getBloodSugarValue());
					lstDataVal.add(bloodSugar.getBloodSugarValue());
					jso.put("testDate", DateUtils.longDate(bloodSugar
							.getTestDateTime()));
					wuAfter.add(jso);
				} else if (bloodSugar.getBloodSugarType() == BloodSugar.SUGAR_WANCAN_BEFORE) {
					jso.put("val", bloodSugar.getBloodSugarValue());
					lstDataVal.add(bloodSugar.getBloodSugarValue());
					jso.put("testDate", DateUtils.longDate(bloodSugar
							.getTestDateTime()));
					wanBefore.add(jso);
				} else if (bloodSugar.getBloodSugarType() == BloodSugar.SUGAR_WANCAN_AFTER) {
					jso.put("val", bloodSugar.getBloodSugarValue());
					lstDataVal.add(bloodSugar.getBloodSugarValue());
					jso.put("testDate", DateUtils.longDate(bloodSugar
							.getTestDateTime()));
					wanAfter.add(jso);
				} else if (bloodSugar.getBloodSugarType() == BloodSugar.SUGAR_SLEEP_BEFORE) {
					jso.put("val", bloodSugar.getBloodSugarValue());
					lstDataVal.add(bloodSugar.getBloodSugarValue());
					jso.put("testDate", DateUtils.longDate(bloodSugar
							.getTestDateTime()));
					sleepBefore.add(jso);
				} else if (bloodSugar.getBloodSugarType() == BloodSugar.SUGAR_LINGCHEN) {
					jso.put("val", bloodSugar.getBloodSugarValue());
					lstDataVal.add(bloodSugar.getBloodSugarValue());
					jso.put("testDate", DateUtils.longDate(bloodSugar
							.getTestDateTime()));
					lingchen.add(jso);
				}
			}

			json.put("sugarZaoBefore", zaoBefore.toArray());
			json.put("sugarZaoAfter", zaoAfter.toArray());
			json.put("sugarWuBefore", wuBeftre.toArray());
			json.put("sugarWuAfter", wuAfter.toArray());
			json.put("sugarWanBefore", wanBefore.toArray());
			json.put("sugarWanAfter", wanAfter.toArray());
			json.put("sugarSleepBefore", sleepBefore.toArray());
			json.put("sugarLiengchen", lingchen.toArray());

			if (!CollectionUtils.isEmpty(lstDataVal)) {
				Collections.sort(lstDataVal);
				json.put("sugarBig", lstDataVal.get(lstDataVal.size() - 1));
			} else {
				json.put("sugarBig", 0);
			}

			jo.put("code", 1);
			jo.put("msg", "获取数据成功");
			jo.put("data", json.toString());
		} else {
			jo.put("code", 1);
			jo.put("msg", "您没有上传数据");
			jo.put("data", "");
		}

		return jo.toString();
	}

	public String queryBloodPressure(Integer clientId) {
		JSONObject jo = new JSONObject();
		JSONObject json = new JSONObject();

		// 当前日期所在星期星期一的日期
		Date weekStartDate = DateUtils.getDateByType(DateUtils.getThisWeekDay(
				1, false), "yyyy-MM-dd");
		// 当前日期
		Date weekEndDate = DateUtils.getDateByType(DateUtils.getAppointDate(
				new Date(), 1), DateUtils.DATE_PATTERN);
		// 当前日期上个星期星期一的日期
		Date lastStartDate = DateUtils.getDateByType(DateUtils.getAppointDate(
				weekStartDate, -7), "yyyy-MM-dd");
		// 上周日的日期
		// Date lastEndDate =
		// DateUtils.getDateByType(DateUtils.getAppointDate(weekStartDate, -1),
		// "yyyy-MM-dd");

		List lstWeek = bloodPressureDao.queryBloodPressure(clientId,
				weekStartDate, weekEndDate);
		List lstLastWeek = bloodPressureDao.queryBloodPressure(clientId,
				lastStartDate, lastStartDate);

		int weekCount = lineTaskDao.dietCount(clientId, weekStartDate,
				weekEndDate, 1, 2);
		int lastWeekCount = lineTaskDao.dietCount(clientId, lastStartDate,
				weekStartDate, 1, 2);
		setBloodPressure(lstWeek, json, "week", weekCount);
		setBloodPressure(lstLastWeek, json, "lastWeek", lastWeekCount);

		jo.put("code", 1);
		jo.put("msg", "获取数据成功");
		jo.put("data", json.toString());

		return jo.toString();
	}

	public void setBloodPressure(List lst, JSONObject json, String weekInfo,
			int count) {
		int lowPressure = 0; // 血压低
		int normalPressure = 0; // 血压正常
		int nhPressure = 0; // 血压正常偏高
		int onePressure = 0; // 一级高血压（轻度）
		int twoPressure = 0; // 二级高血压（中度）
		int threePressure = 0; // 三级高血压（重度）
		if (!CollectionUtils.isEmpty(lst)) {
			List<BloodPressure> lstPressure = (List<BloodPressure>) lst;
			for (BloodPressure bloodPressure : lstPressure) {
				// 血压低
				if (bloodPressure.getSbp() >= 180
						|| bloodPressure.getDbp() >= 110) {
					threePressure += 1;
				} else if ((bloodPressure.getSbp() >= 160 && bloodPressure
						.getSbp() <= 179)
						|| bloodPressure.getDbp() >= 100
						&& bloodPressure.getDbp() <= 109) {
					twoPressure += 1;
				} else if ((bloodPressure.getSbp() >= 140 && bloodPressure
						.getSbp() <= 159)
						|| bloodPressure.getDbp() >= 90
						&& bloodPressure.getDbp() <= 99) {
					onePressure += 1;
				} else if ((bloodPressure.getSbp() > 120 && bloodPressure
						.getSbp() <= 139)
						|| bloodPressure.getDbp() >= 80
						&& bloodPressure.getDbp() <= 89) {
					nhPressure += 1;
				} else if (bloodPressure.getSbp() < 90
						|| bloodPressure.getDbp() < 60) {
					lowPressure += 1;
				} else if ((bloodPressure.getSbp() >= 90 && bloodPressure
						.getSbp() <= 120)
						|| bloodPressure.getDbp() >= 60
						&& bloodPressure.getDbp() <= 80) {
					normalPressure += 1;
				}
			}

			// 总测量次数
			int sum = lowPressure + normalPressure + nhPressure + onePressure
					+ twoPressure + threePressure;

			json.put("" + weekInfo + "LowSum", lowPressure);
			json.put("" + weekInfo + "NormalSum", normalPressure);
			json.put("" + weekInfo + "NhSum", nhPressure);
			json.put("" + weekInfo + "OneSum", onePressure);
			json.put("" + weekInfo + "TwoSum", twoPressure);
			json.put("" + weekInfo + "ThreeSum", threePressure);
			json.put("" + weekInfo + "Sum", sum);
			if (count == 0) {
				json.put("" + weekInfo + "Finish", 0);
			} else {
				json.put("" + weekInfo + "Finish", MoneyFormatUtil
						.formatDouble((sum / count) * 100));
			}
		} else {
			json.put("" + weekInfo + "LowSum", 0);
			json.put("" + weekInfo + "NormalSum", 0);
			json.put("" + weekInfo + "NhSum", 0);
			json.put("" + weekInfo + "OneSum", 0);
			json.put("" + weekInfo + "TwoSum", 0);
			json.put("" + weekInfo + "ThreeSum", 0);
			json.put("" + weekInfo + "Sum", 0);
			json.put("" + weekInfo + "Finish", 0);
		}
	}

	public String queryTgBloodSugar(Integer clientId) {
		JSONObject jo = new JSONObject();
		JSONObject json = new JSONObject();

		BloodSugar bs = bloodSugarDao.queryTgBloodSugar(clientId);
		if (bs != null) {
			int bstype = bs.getBloodSugarType();
			// 上次血糖类型
			String type = getBloodType(bstype);
			// 上一次血糖值
			double val = bs.getBloodSugarValue();
			// 是否正常(0：正常 1：升 2：降)
			int isNormal = 0;
			// 需要升降的值
			double normalVal = 0;
			if (bstype == BloodSugar.SUGAR_TYPE
					|| bstype == BloodSugar.SUGAR_ZAOCAN_BEFORE
					|| bstype == BloodSugar.SUGAR_WUCAN_BEFORRE
					|| bstype == BloodSugar.SUGAR_WANCAN_BEFORE) {
				if (val < 4.4) {
					isNormal = 1;
					normalVal = 4.4 - val;
				} else if (val > 7.0) {
					isNormal = 2;
					normalVal = val - 7.0;
				}
			} else if (bstype == BloodSugar.SUGAR_TYPE_2H
					|| bstype == BloodSugar.SUGAR_ZAOCAN_AFTER
					|| bstype == BloodSugar.SUGAR_WUCAN_AFTER
					|| bstype == BloodSugar.SUGAR_WANCAN_AFTER) {
				if (val < 4.4) {
					isNormal = 1;
					normalVal = 4.4 - val;
				} else if (val > 10.0) {
					isNormal = 2;
					normalVal = val - 10.0;
				}
			} else if (bstype == BloodSugar.SUGAR_LINGCHEN
					|| bstype == BloodSugar.SUGAR_SLEEP_BEFORE) {
				if (val < 3.9) {
					isNormal = 1;
					normalVal = 3.9 - val;
				} else if (val > 11.1) {
					isNormal = 2;
					normalVal = val - 11.1;
				}
			}

			json.put("type", type);
			json.put("lastVal", val);
			json.put("isNormal", isNormal);
			json.put("normalVal", normalVal);
			
		}else{
			json.put("type", "");
			json.put("lastVal", 0);
			json.put("isNormal", 0);
			json.put("normalVal", 0);
		}
		getTgBloodSugar(clientId, 0, json);
		getTgBloodSugar(clientId, 1, json);
		BloodSugarTarget target=new BloodSugarTarget();
		target.setClientId(clientId);
		target=bloodSugarTargetService.quertTarget(target);
		if(target == null){
			target=new BloodSugarTarget();
			target.setPbgMax(8);
			target.setPbgMin(4.4);
			target.setFbgMax(6.1);
			target.setFbgMin(4.4);
		}
//		if(target.getPbgMax()==0){//c餐后最大
//			target.setPbgMax(8);
//		}
//		if(target.getPbgMin()==0){// 餐后最小
//			target.setPbgMin(4.4);
//		}
//		if(target.getFbgMax()==0){// 空腹最大
//			target.setFbgMax(6.1);
//		}
//		if(target.getFbgMin()==0){// 空腹最小
//			target.setFbgMin(4.4);
//		}
		json.put("PbgMax", target.getPbgMax());
		json.put("PbgMin", target.getPbgMin());
		json.put("FbgMax", target.getFbgMax());
		json.put("FbgMin", target.getFbgMin());
		jo.put("code", 1);
		jo.put("msg", "成功");
		jo.put("data", json.toString());
		return jo.toString();
	}

	public void getTgBloodSugar(Integer clientId, Integer type, JSONObject json) {
		JSONObject jo = new JSONObject();
		// 获取本周的血糖数据
		Date startDate = null;
		Date endDate = null;
		if (type == 0) {
			// 当前日期所在星期星期一的日期
			startDate = DateUtils.getDateByType(DateUtils.getThisWeekDay(1,
					false), "yyyy-MM-dd");
			endDate = DateUtils.getDateByType(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
		// 获取本月的血糖数据
		if (type == 1) {
			String testDate = DateUtils.formatDate("yyyy-MM-dd", new Date());
			startDate = DateUtils.parseDate(testDate, "yyyy-MM");
			endDate = DateUtils.getDateByType(new Date(), "yyyy-MM-dd HH:mm:ss");
		}

		// 理想次数
		double normalCount = 0;
		// 良好次数
		double goodCount = 0;
		// 控制不良次数
		double badCount = 0;
		// 低血糖次数
		int lowCount = 0;

		List<Double> lstVal = new ArrayList<Double>();
		// 血糖总值
		double sumVal = 0;
		List<BloodSugar> lstSugar = bloodSugarDao.queryTgBloodSugar(clientId,
				startDate, endDate);
		
		for (BloodSugar bloodSugar : lstSugar) {
			sumVal += bloodSugar.getBloodSugarValue();
		}
		
		if (!CollectionUtils.isEmpty(lstSugar)) {
			for (BloodSugar bloodSugar : lstSugar) {
				int bstype = bloodSugar.getBloodSugarType();
				double val = bloodSugar.getBloodSugarValue();
				if (bstype == BloodSugar.SUGAR_TYPE
						|| bstype == BloodSugar.SUGAR_ZAOCAN_BEFORE
						|| bstype == BloodSugar.SUGAR_WUCAN_BEFORRE
						|| bstype == BloodSugar.SUGAR_WANCAN_BEFORE) {
					if (val < 4.4) {
						lstVal.add(val);
						//sumVal += val;
						lowCount ++ ;
					}
					if(val >=4.4 && val <= 6.1){
						lstVal.add(val);
						//sumVal += val;
						normalCount ++ ;
					}
					if(val >=4.4 && val <= 7.0){
						lstVal.add(val);
						//sumVal += val;
						goodCount ++ ;
					}
					if(val > 7.0){
						lstVal.add(val);
						//sumVal += val;
						badCount++;
					}
				} else if (bstype == BloodSugar.SUGAR_TYPE_2H
						|| bstype == BloodSugar.SUGAR_ZAOCAN_AFTER
						|| bstype == BloodSugar.SUGAR_WUCAN_AFTER
						|| bstype == BloodSugar.SUGAR_WANCAN_AFTER) {
					if (val < 4.4) {
						lstVal.add(val);
						//sumVal += val;
						lowCount ++ ;
					}
					if(val >= 4.4 && val <= 8.0){
						lstVal.add(val);
						//sumVal += val;
						normalCount ++ ;
					}
					if(val >=4.4 && val <= 10.0){
						lstVal.add(val);
						//sumVal += val;
						goodCount ++ ;
					}
					if(val > 10.0){
						lstVal.add(val);
						//sumVal += val;
						badCount++;
					}
				} else if (bstype == BloodSugar.SUGAR_LINGCHEN
						|| bstype == BloodSugar.SUGAR_SLEEP_BEFORE) {
					if (val >= 4.4 && val <= 10) {
						lstVal.add(val);
						//sumVal += val;
						normalCount ++ ;
					}
					if(val >= 3.9 && val <= 11.1){
						lstVal.add(val);
						//sumVal += val;
						goodCount ++ ;
					}
					if(val <3.9 || val >11.1){
						lstVal.add(val);
						//sumVal += val;
						badCount ++ ;
					}
					if(val < 3.9){
						lstVal.add(val);
						//sumVal += val;
						lowCount++;
					}
				}
			}
			// 测量总数
			double sumCount = lstSugar.size();
			// 血糖平均值
			double avgVal = MoneyFormatUtil.formatDouble(sumVal / sumCount);
			Collections.sort(lstVal);
			// 最大值
			double bigVal = lstVal.get(lstVal.size() - 1);
			// 最小值
			double smalVal = lstVal.get(0);
			
			//任务总数
/*			int taskCount = lineTaskDao.dietCount(clientId, startDate, endDate,
					1, 2);
			// 任务完成率
			double finish = 0;
			if (taskCount != 0) {
				//完成数/任务总数，算出完成率
				finish = sumCount / taskCount;
			}
*/
			double finish = queryTaskFinish(clientId, startDate, endDate);

			// 测量总次数
			jo.put("sumCount", sumCount);
			// 理想值次数
			jo.put("normalCount", normalCount);
			// 良好次数
			jo.put("goodCount", goodCount);
			// 不良次数
			jo.put("badCount", badCount);
			// 低血糖数据
			jo.put("lowCount", lowCount);
			// 最高血糖
			jo.put("bigVal", bigVal);
			// 最低血糖
			jo.put("smalVal", smalVal);
			// 血糖平均值
			jo.put("avgVal", avgVal);
			// 任务完成率
			jo.put("finish", finish);

			if (type == 1) {
				// 理想概率
				double normalPro = Integer.parseInt(MoneyFormatUtil
						.formatToMoney(normalCount / sumCount * 100,
								new DecimalFormat("#0")));
				jo.put("normalPro", normalPro);
				// 良好概率
				double goodPro = Integer.parseInt(MoneyFormatUtil
						.formatToMoney(goodCount / sumCount * 100,
								new DecimalFormat("#0")));
				jo.put("goodPro", goodPro);
				// 良好概率
				double badPro = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						badCount / sumCount * 100, new DecimalFormat("#0")));
				jo.put("badPro", badPro);
				// 偏低概率
				double lowPro = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						lowCount / sumCount * 100, new DecimalFormat("#0")));
				jo.put("lowPro", lowPro);
			}
		} else {
			// 测量总次数
			jo.put("sumCount", 0);
			// 理想值次数
			jo.put("normalCount", 0);
			// 良好次数
			jo.put("goodCount", 0);
			// 不良次数
			jo.put("badCount", badCount);
			// 低血糖数据
			jo.put("lowCount", 0);
			// 最高血糖
			jo.put("bigVal", 0);
			// 最低血糖
			jo.put("smalVal", 0);
			// 血糖平均值
			jo.put("avgVal", 0);
			// 任务完成率
			jo.put("finish", 0);

			if (type == 1) {
				jo.put("normalPro", 0);
				jo.put("goodPro", 0);
				jo.put("badPro", 0);
				jo.put("lowPro", 0);
			}
		}

		if (type == 0) {
			json.put("weekSugar", jo.toString());
		}
		if (type == 1) {
			json.put("monthSugar", jo.toString());
		}
	}

	public String getBloodType(Integer bstype) {
		String type = "";
		if (bstype == BloodSugar.SUGAR_TYPE
				|| bstype == BloodSugar.SUGAR_ZAOCAN_BEFORE) {
			type = "早餐前血糖";
		} else if (bstype == BloodSugar.SUGAR_TYPE_2H) {
			type = "餐后血糖";
		} else if (bstype == BloodSugar.SUGAR_ZAOCAN_AFTER) {
			type = "早餐后血糖";
		} else if (bstype == BloodSugar.SUGAR_WUCAN_BEFORRE) {
			type = "午餐前血糖";
		} else if (bstype == BloodSugar.SUGAR_WUCAN_AFTER) {
			type = "午餐后血糖";
		} else if (bstype == BloodSugar.SUGAR_WANCAN_AFTER) {
			type = "晚餐前血糖";
		} else if (bstype == BloodSugar.SUGAR_WANCAN_BEFORE) {
			type = "晚餐后血糖";
		} else if (bstype == BloodSugar.SUGAR_LINGCHEN) {
			type = "凌晨血糖";
		} else if (bstype == BloodSugar.SUGAR_SLEEP_BEFORE) {
			type = "睡前血糖";
		}
		return type;
	}

	public String queryBloodSugarDetail(Integer cid, String type,
			String testDate) {
		JSONObject jc = new JSONObject();
		if (StringUtils.isEmpty(type)) {
			jc.put("code", Constant.INTERFACE_FAIL);
			jc.put("msg", "type不能为空");
			return jc.toString();
		} else {
			if (!(type.equals("week") == true ? true
					: (type.equals("month") == true ? true : false))) {
				jc.put("code", Constant.INTERFACE_FAIL);
				jc.put("msg", "type只能为week或month");
				return jc.toString();
			}
		}
		
		List<BloodSugar> list = bloodSugarDao.queryBloodSugarDetail(cid, type,
				testDate);
		setBloodSugarDetail(list, jc);
		
		return jc.toString();
	}
	
	public void setBloodSugarDetail(List<BloodSugar> list, JSONObject jc){
		List<String> keyValueList = new ArrayList<String>();// 血糖类型和值组成的键值list
		List<String> dateList = new ArrayList<String>();// 时间list
		List<BloodSugar> newList = new ArrayList<BloodSugar>();// 过滤后的list
		JSONArray jb = new JSONArray();
		Date extraDate = null;// 增加一个额外元素
		if (!CollectionUtils.isEmpty(list)) {
			// 获得当前list中testDateTime最大元素的时间
			extraDate = list.get(list.size() - 1).getTestDateTime();
			// 去掉一天内重复血糖值
			for (BloodSugar bs : list) {
				// 判断不同天
				if (!dateList.contains(DateUtils.formatDate(
						DateUtils.DATE_PATTERN, bs.getTestDateTime()))) {
					dateList.add(DateUtils.formatDate(DateUtils.DATE_PATTERN,
							bs.getTestDateTime()));
					newList.add(bs);
				} else {
					// 去掉重复血糖值
					// 是否包含相同键值对(key,value)例：(bs.getBloodSugarType() + "," +
					// bs.getBloodSugarValue())
					if (!keyValueList.contains(bs.getBloodSugarType() + ","
							+ bs.getBloodSugarValue())) {
						keyValueList.add(bs.getBloodSugarType() + ","
								+ bs.getBloodSugarValue());
						newList.add(bs);
					}
				}
			}
		}
		// 时间list2
		List<String> dateList2 = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(newList)) {
			// 向新的list添加一个额外BloodSugar对象，对象只设置时间并得到的测试时间加一天，时间是newList中所有对象中最大的测试时间
			BloodSugar bsSugar = new BloodSugar();
			bsSugar.setTestDateTime(DateUtils.getAppointDate(extraDate, 1));// 加一天
			bsSugar.setBloodSugarType(BloodSugar.SUGAR_LINGCHEN);// 
			bsSugar.setBloodSugarValue(3.3);//
			newList.add(bsSugar);
			String str = "";
			JSONObject ja = new JSONObject();
			// 一天内不同血糖类型的值放在一天内
			for (BloodSugar bl : newList) {
				String dateStr = DateUtils.formatDate(DateUtils.DATE_PATTERN,
						bl.getTestDateTime());
				// 当是不同一天（dateStr），比如，前面是10号，后面9号，则判断str，若str不为空，则解析str
				// 当是同一天（dateStr）,则将不同类型血糖值放入相应的拼接类型中
				if (!dateList2.contains(dateStr)) {
					dateList2.add(dateStr);
					if (!StringUtils.isEmpty(str)) {
						// 将str解析成json数据
						String[] temp = str.split(",");
						for (int i = 0; i < temp.length; i++) {
							int location = temp[i].indexOf("~");
							String key = temp[i].substring(0, location);
							double value = 0;
							String valStr = temp[i].substring(location + 1,
									temp[i].length());
							if (!StringUtils.isEmpty(valStr)
									&& !temp[i].contains("day")) {
								value = Double.valueOf(valStr).doubleValue();
							}
							if (temp[i].contains("day")) {
								ja.put(key, valStr);
							} else {
								ja.put(key, value);
							}
						}
						// 清空字符串值
						str = "";
					}
					if (null != bl.getBloodSugarType()) {
						if (BloodSugar.SUGAR_ZAOCAN_BEFORE == bl
								.getBloodSugarType()
								|| BloodSugar.SUGAR_TYPE == bl
										.getBloodSugarType()) {
							str += "zaocan_before~" + bl.getBloodSugarValue();
						} else {
							str += "zaocan_before~0";
						}
						if (BloodSugar.SUGAR_ZAOCAN_AFTER == bl
								.getBloodSugarType()) {
							str += ",zaocan_after~" + bl.getBloodSugarValue();
						} else {
							str += ",zaocan_after~0";
						}
						if (BloodSugar.SUGAR_WUCAN_BEFORRE == bl
								.getBloodSugarType()) {
							str += ",wucan_before~" + bl.getBloodSugarValue();
						} else {
							str += ",wucan_before~0";
						}
						if (BloodSugar.SUGAR_WUCAN_AFTER == bl
								.getBloodSugarType()) {
							str += ",wucan_after~" + bl.getBloodSugarValue();
						} else {
							str += ",wucan_after~0";
						}
						if (BloodSugar.SUGAR_WANCAN_BEFORE == bl
								.getBloodSugarType()) {
							str += ",wancan_before~" + bl.getBloodSugarValue();
						} else {
							str += ",wancan_before~0";
						}
						if (BloodSugar.SUGAR_WANCAN_AFTER == bl
								.getBloodSugarType()) {
							str += ",wancan_after~" + bl.getBloodSugarValue();
						} else {
							str += ",wancan_after~0";
						}
						if (BloodSugar.SUGAR_SLEEP_BEFORE == bl
								.getBloodSugarType()) {
							str += ",sleep_before~" + bl.getBloodSugarValue();
						} else {
							str += ",sleep_before~0";
						}
						if (BloodSugar.SUGAR_LINGCHEN == bl.getBloodSugarType()) {
							str += ",lingcheng~" + bl.getBloodSugarValue();
						} else {
							str += ",lingcheng~0";
						}
					}
					str += ",day~" + dateStr;
				} else {
					if (null != bl.getBloodSugarType()) {
						if (BloodSugar.SUGAR_ZAOCAN_BEFORE == bl
								.getBloodSugarType()
								|| BloodSugar.SUGAR_TYPE == bl
										.getBloodSugarType()) {
							str = str.replace("zaocan_before~0",
									"zaocan_before~" + bl.getBloodSugarValue());
						}
						if (BloodSugar.SUGAR_ZAOCAN_AFTER == bl
								.getBloodSugarType()) {
							str = str.replace("zaocan_after~0", "zaocan_after~"
									+ bl.getBloodSugarValue());
						}
						if (BloodSugar.SUGAR_WUCAN_BEFORRE == bl
								.getBloodSugarType()) {
							str = str.replace("wucan_before~0", "wucan_before~"
									+ bl.getBloodSugarValue());
						}
						if (BloodSugar.SUGAR_WUCAN_AFTER == bl
								.getBloodSugarType()) {
							str = str.replace("wucan_after~0", "wucan_after~"
									+ bl.getBloodSugarValue());
						}
						if (BloodSugar.SUGAR_WANCAN_BEFORE == bl
								.getBloodSugarType()) {
							str = str.replace("wancan_before~0",
									"wancan_before~" + bl.getBloodSugarValue());
						}
						if (BloodSugar.SUGAR_WANCAN_AFTER == bl
								.getBloodSugarType()) {
							str = str.replace("wancan_after~0", "wancan_after~"
									+ bl.getBloodSugarValue());
						}
						if (BloodSugar.SUGAR_SLEEP_BEFORE == bl
								.getBloodSugarType()) {
							str = str.replace("sleep_before~0", "sleep_before~"
									+ bl.getBloodSugarValue());
						}
						if (BloodSugar.SUGAR_LINGCHEN == bl.getBloodSugarType()) {
							str = str.replace("lingcheng~0", "lingcheng~"
									+ bl.getBloodSugarValue());
						}
					}
				}
				// json（即ja）包含key("day"),则放入jsonArray,并删除key("day"),避免重复添加
				if (ja.containsKey("day")) {
					jb.add(ja.toString());
					ja.remove("day");
				}
			}
		}
		jc.put("code", Constant.INTERFACE_SUCC);
		jc.put("msg", "查询成功");
		if (jb.toString().contains("[]")) {
			jc.put("data", "");
		} else {
			jc.put("data", jb);
		}
	}
	
	
	
	
	public double queryTaskFinish(Integer clientId, Date startDate, Date endDate){
		double finish = 0;
		//所有的任务
		List<Object> lstTask = lineTaskDao.getTaskIdentical(clientId, startDate, endDate, null);
		//完成的任务
		List<Object> lstTaskFinish = lineTaskDao.getTaskIdentical(clientId, startDate, endDate, 2);
		
		double finishAll = 0;
		
		Object[] allobjs;
		Object[] finishobjs;
		
		if(!CollectionUtils.isEmpty(lstTask) && !CollectionUtils.isEmpty(lstTaskFinish)){
			for (Object task : lstTask) {
				allobjs = (Object[]) task;
				double finishTask = 0;
				double taskPro = Double.parseDouble(String.valueOf(allobjs[0]));
				
				for (Object taskFinish : lstTaskFinish) {
					finishobjs = (Object[]) taskFinish;
					if(allobjs[1].equals(finishobjs[1])){
						finishTask = Integer.parseInt(String.valueOf(finishobjs[0]));
						double finishPro = finishTask/taskPro;
						finishAll += finishPro;
					}
				}
			}
			finish = MoneyFormatUtil.formatDouble(finishAll/lstTask.size());
		}
		
		return finish;
	}
	
	
	public String queryBloodSugarDetailInfo(Integer cid, String beginDate, String endDate){
		JSONObject jc = new JSONObject();
		List<BloodSugar> list = null;
		Date begDate = null;
		Date eDate = null;
		if(!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
			begDate = DateUtils.parseDate(beginDate, "yyyy-MM-dd");
			eDate = DateUtils.getDateByType(DateUtils.getBeforeDay(endDate, 1), "yyyy-MM-dd");
			list = bloodSugarDao.queryBloodSugarDetailInfo(cid, begDate, eDate);
		}else{
			eDate = DateUtils.getDateByType(DateUtils.getAppointDate(new Date(), 1), "yyyy-MM-dd");
			begDate = DateUtils.getDateByType(DateUtils.getThisWeekDay(1, false), "yyyy-MM-dd");
			list = bloodSugarDao.queryBloodSugarDetailInfo(cid, begDate, eDate);
		}
		
		setBloodSugarDetail(list, jc);
		return jc.toString();
	}
	
	
	public String queryBloodSugarWeek(Integer cid, String beginDate, String endDate){
		JSONObject jo = new JSONObject();
		
		Date begDate = null;
		Date eDate = null;
		
		BloodSugar sugar = new BloodSugar();
		sugar.setClientId(cid);
		
		List<Double> lstSugarVal = new ArrayList<Double>();
		JSONObject jsonAll = new JSONObject();
		
		if(!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
			begDate = DateUtils.parseDate(beginDate, "yyyy-MM-dd");
			eDate = DateUtils.parseDate(endDate,  "yyyy-MM-dd");
		}else{
			eDate = DateUtils.getDateByType(DateUtils.getAppointDate(new Date(), 1), "yyyy-MM-dd");
			begDate = DateUtils.getDateByType(DateUtils.getThisWeekDay(1, false), "yyyy-MM-dd");
		}
		
		sugar.setBloodSugarType(BloodSugar.SUGAR_TYPE);
		List<BloodSugar> lstBefore = bloodSugarDao.queryBloodSugar(sugar, begDate, eDate);
		setBloodSugar(lstBefore, jsonAll, "Before", lstSugarVal);
		
		sugar.setBloodSugarType(BloodSugar.SUGAR_TYPE_2H);
		List<BloodSugar> lstAfter = bloodSugarDao.queryBloodSugar(sugar, begDate, eDate);
		setBloodSugar(lstAfter, jsonAll, "After", lstSugarVal);
		
		jo.put("code", 1);
		jo.put("msg", "请求数据成功");
		jo.put("data", jsonAll.toString());
		return jo.toString();
	}

	public void setWeekBloodSugar(List<BloodSugar> lstSugar, JSONObject jo,
			String type, List<Double> lstSugarVal) {
		if (!CollectionUtils.isEmpty(lstSugar)) {
			JSONArray ja = new JSONArray();
			for (BloodSugar bloodSugar : lstSugar) {
				JSONObject json = new JSONObject();
				json.put("val", bloodSugar.getBloodSugarValue());
				lstSugarVal.add(bloodSugar.getBloodSugarValue());
				json.put("testDate", DateUtils.longDate(bloodSugar
						.getTestDateTime()));
				ja.add(json);
			}
			jo.put("sugar" + type + "", ja.toArray());
		} else {
			jo.put("sugar" + type + "", "");
		}
	}

	
	public String queryBloodPressureWeek(Integer clientId, String beginDate, String endDate){
		JSONObject jo = new JSONObject();
		
		Date begDate = null;
		Date eDate = null;
		
		if(!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
			begDate = DateUtils.parseDate(beginDate, "yyyy-MM-dd");
			eDate = DateUtils.getCurrentDayEndTime(DateUtils.parseDate(endDate,  "yyyy-MM-dd"));
		}else{
			eDate = DateUtils.getDateByType(DateUtils.getAppointDate(new Date(), 1), "yyyy-MM-dd");
			begDate = DateUtils.getDateByType(DateUtils.getThisWeekDay(1, false), "yyyy-MM-dd");
		}
		
		List<BloodPressure> list = bloodPressureDao.queryBloodPressure(clientId, begDate, eDate);
		setBloodPressure(jo, list);
		
		JSONObject json = new JSONObject();
		json.put("code", 1);
		if(!CollectionUtils.isEmpty(list)){
			json.put("msg", "查询成功");
		}else{
			json.put("msg", "您本周没有上次过血压数据");
		}
		
		json.put("data", jo.toString());
		
		return json.toString();
	}
	
	
	public int isUploadBloodSugar(ClientInfo clientInfo, String beginDate, String endDate){
		Date begDate = null;
		Date edate = null;
		if(!StringUtils.isEmpty(beginDate)){
			begDate = DateUtils.parseDate(beginDate, "yyyy-MM-dd");
		}
		if(!StringUtils.isEmpty(endDate)){
			edate = DateUtils.parseDate(endDate, "yyyy-MM-dd");
		}
		BloodSugar bloodSugar = new BloodSugar();
		bloodSugar.setClientId(clientInfo.getId());
		int count = bloodSugarDao.queryClientBloodSugar(bloodSugar, begDate, edate);
		if(count > 0){
			return BloodSugar.UPLOAD_BLOOD_SUGAR;
		}else{
			return BloodSugar.UNUPLOAD_BLOOD_SUGAR;
		}
	}
	
	
	public String queryUpRecord(Integer cid , String beginDate, String endDate){
		JSONObject jo = new JSONObject();
		if(cid != null){
			Date begDate = null;
			Date eDate = null;
			if(!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
				begDate = DateUtils.parseDate(beginDate, "yyyy-MM-dd");
				eDate = DateUtils.getBeforeDay(endDate, 1);
			}else{
				Date date = DateUtils.getDateByType(new Date(), "yyyy-MM");
				begDate = DateUtils.getDateByType(date, "yyyy-MM-dd");
				
				Date datee = DateUtils.getBeforeMonth(DateUtils.formatDate("yyyy-MM-dd", begDate), 1);
				eDate = DateUtils.getDateByType(datee, "yyyy-MM-dd");
			}
			
			List<Object> lst = bloodSugarDao.queryUploadDataRecord(cid, begDate, eDate);
			
			if(!CollectionUtils.isEmpty(lst)){
				jo.put("code", Constant.INTERFACE_SUCC);
				jo.put("msg", "查询成功");
				
				JSONObject json = new JSONObject();
				json.put("uploadDate", JsonUtils.getJsonString4JavaList(lst));
				json.put("today", this.queryRecordByDay(cid, new Date()));
				
				jo.put("data", json.toString());
		
			}else{
				jo.put("code", Constant.INTERFACE_SUCC);
				jo.put("msg", "查询数据为空");
				JSONObject json = new JSONObject();
				json.put("uploadDate", "");
				json.put("today", "");
				
				jo.put("data", json.toString());
			}
			
		}else{
			jo.put("code", Constant.INTERFACE_PARAM_ERROR);
			jo.put("msg", "参数有误");
			jo.put("data", "");
		}
		return jo.toString();
	}
	
	public String queryUpRecordByDay(Integer cid, Date day) {
		if(null == cid) return "";
		
		JSONObject jo = new JSONObject();
		jo.put("code", Constant.INTERFACE_SUCC);
		jo.put("msg", "查询成功");
		jo.put("data", queryRecordByDay(cid, day));
		return jo.toString();
	}
	
	private JSONObject queryRecordByDay(Integer cid, Date day) {
		JSONObject jo = new JSONObject();
		
		Date startDate = DateUtils.getCurrentDayStartTime(day);
		Date endDate = DateUtils.getCurrentDayEndTime(day);
		
		List<BloodSugar> bsList = bloodSugarDao.queryLatestBloodSugar(cid, startDate, endDate);//倒序排列
		List<BloodPressure> bpList = bloodPressureDao.queryBloodPressure(cid, startDate, endDate); //顺序
		
		List<Object> sportObj = mSportDao.queryClientMsport(cid, "calorie", startDate, endDate);
		
		List<MedicineReminder> mrList = medicineReminderDao.queryLastRecord(cid);
		
		List<Object> foodObj = recordFoodDao.queryClientRecord(cid, startDate, endDate);
		
		if(!CollectionUtils.isEmpty(bsList)) {
			BloodSugar bs = bsList.get(0);
			
			jo.put("bs", MonitoringDataResult.sugarType.get(bs.getBloodSugarType()) + " " 
					+ bs.getBloodSugarValue());
		}
		
		if(!CollectionUtils.isEmpty(bpList)) {
			BloodPressure bp = bpList.get(bpList.size() - 1);
			jo.put("bp", bp.getSbp() + "/" + bp.getDbp() + " mmHg");
		}
		
		if(!CollectionUtils.isEmpty(sportObj)) {
			Object obj = sportObj.get(0);
			Object[] objArr = (Object[]) obj;
			
			jo.put("ms", "消耗 " + Math.round((Double)objArr[0]) + " kcal 热量");
		}
		
		if(!CollectionUtils.isEmpty(mrList)) {
			MedicineReminder mr = mrList.get(mrList.size()-1);
			jo.put("mr", mr.getAlertTime() + " " + mr.getDrugName());
		}
		
		if(!CollectionUtils.isEmpty(foodObj)) {
			Object obj = foodObj.get(0);
			if(obj != null){
				Object[] objArr = (Object[]) obj;
				if(objArr != null && objArr[0] != null){
					jo.put("mf", "摄入" + Math.round((Double)objArr[0]) + " kcal 热量");
				}
			}
		}
		
		return jo;
	}
	
	
	public String queryAllBloodSugar(Integer cid, String beginDate, String endDate){
		JSONObject jo = new JSONObject();
		if(!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
			Date beginTime = DateUtils.parseDate(beginDate, "yyyy-MM-dd");
			Date endTime = DateUtils.getCurrentDayEndTime(DateUtils.getAppointDate(DateUtils.parseDate(endDate, "yyyy-MM-dd"), 1));
			
			List<BloodSugar> lst = bloodSugarDao.queryTgBloodSugar(cid, beginTime, endTime);
			List<Double> lstVal = new ArrayList<Double>();
			
			BloodSugarTarget btarget = new BloodSugarTarget();
			btarget.setClientId(cid);
			BloodSugarTarget target =  bloodSugarTargetDao.quertTarget(btarget);
			
			JSONObject jon = new JSONObject();
			JSONArray ja = new JSONArray();
			
			if(!CollectionUtils.isEmpty(lst)){
				
				for (BloodSugar bloodSugar : lst) {
					JSONObject json = new JSONObject();
					json.put("type", bloodSugar.getBloodSugarType());
					json.put("val", bloodSugar.getBloodSugarValue());
					Date date = bloodSugar.getTestDateTime();
					json.put("testDate", DateUtils.longDate(date));

					ja.add(json.toString());
					
					lstVal.add(bloodSugar.getBloodSugarValue());
				}
				jo.put("code", 1);
				jo.put("msg", "查询成功");
				jon.put("list", ja.toArray());
				
				Collections.sort(lstVal);
				jon.put("maxVal", lstVal.get(lstVal.size() - 1));
				
			}else{
				jo.put("code", 1);
				jo.put("msg", "您没有上传血糖数据");
				jon.put("list", "");
				jon.put("maxVal", 0);
			}
			
			if(target != null){
				jon.put("fbgMax", target.getFbgMax());
				jon.put("fbgMin", target.getFbgMin());
				jon.put("pbgMax", target.getPbgMax());
				jon.put("pbgMin", target.getPbgMin());
			}else{
				jon.put("fbgMax", 0);
				jon.put("fbgMin", 0);
				jon.put("pbgMax", 0);
				jon.put("pbgMin", 0);
			}
			
			jo.put("data", jon.toString());
			
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数有误");
			jo.put("data", "");
		}
		
		return jo.toString();
	}
	
	
	public String getLastBloodSugerForTimeLine(Integer cid){
		
		//查找今天的最后一次的血糖数据
		BloodSugar bs = bloodSugarDao.queryLastBloodSugarByCId(cid , 
									DateUtils.getCurrentDayStartTime(new Date()) ,
									DateUtils.getCurrentDayEndTime(new Date()) );
		
		//查找“上传”的时间轴内容
		NTimelineContent upload = ntimelineContentService
				.selectTimeLineContentFormCache(NtgConstant.CONTYPE_TESTBG,
				NtgConstant.CONSTATUS_UPLOADED) ;
		
		//查找“初始化”的时间轴内容
		NTimelineContent init = ntimelineContentService
				.selectTimeLineContentFormCache(NtgConstant.CONTYPE_TESTBG, 
				NtgConstant.CONSTATUS_INIT) ;
		
		NTimelineRuleCustom n = new NTimelineRuleCustom() ;
		n.setConType(NtgConstant.CONTYPE_TESTBG) ;
		n.setClientId(cid) ;
		//查找时间轴的时间
		NTimelineRuleCustom result = nTimelineRuleCustomDao
				.selectClientsTimeLineByType(n) ;
		
		JSONObject json = new JSONObject() ;
		JSONObject initJson  = new JSONObject() ;
		JSONObject initBodyJson  = new JSONObject() ;
		JSONObject uploadedJson = new JSONObject() ;
		JSONObject uploadedBodyJson = new JSONObject() ;
		
		//init对象参数
		initJson.put("header", init.getHeader() != null ? init.getHeader() : "") ;
		initBodyJson.put("msg", init.getBody() != null ? init.getBody() : "") ;
		initJson.put("body", initBodyJson) ;
		initJson.put("imgs", init.getImgs() != null ? init.getImgs() : "") ;
		initJson.put("footer", init.getFooter() != null ? init.getFooter() : "") ;
			
		//uploaded对象参数
		uploadedJson.put("header", upload.getHeader() != null ? upload.getHeader() : "") ;
		
		//血糖对象有可能为空
		if (bs != null){
			
			uploadedBodyJson.put("type", bs.getBloodSugarType() != null ? bs.getBloodSugarType() : "") ;
			uploadedBodyJson.put("val", bs.getBloodSugarValue() > 0 ? bs.getBloodSugarValue() : 0) ;
		}else{
			
			uploadedBodyJson.put("type", "") ;
			uploadedBodyJson.put("val",  0) ;
		}
		uploadedJson.put("body", uploadedBodyJson) ;
		uploadedJson.put("imgs", upload.getImgs() != null ? upload.getImgs() : "") ;
		uploadedJson.put("footer", upload.getFooter() != null ? upload.getFooter() : "") ;
		
		
		//返回的对象参数
		json.put("init", initJson) ;
		json.put("uploaded", uploadedJson) ;
		json.put("taskTime", result.getTaskTime()) ;
		json.put("isFinish", bs==null ? 0 : 1 ) ;
	
		return json.toString();
	}
	
	/**
	 * 查询最后一次的血糖数据
	 */
	public BloodSugar queryLastBloodSugarByCId(Integer clientId,
			Date beginDate, Date endDate) {
		
		return bloodSugarDao.queryLastBloodSugarByCId(clientId , 
						DateUtils.getCurrentDayStartTime(new Date()) ,
						DateUtils.getCurrentDayEndTime(new Date()) );
	}
	
	/**
	 * 最后一次血糖数据信息
	 */
	public String queryLastBloodSugar(Integer cid){
		JSONObject jo = new JSONObject();
		BloodSugar sugar = bloodSugarDao.queryLastBloodSugarByCId(cid, null, null);
		if(sugar != null){
			jo.put("type", sugar.getBloodSugarType());
			jo.put("val", sugar.getBloodSugarValue());
			String result = sugar.getResult();
			int index = result.indexOf("：");
			result = result.substring(index+1);
			jo.put("result", result);
			Date date = sugar.getTestDateTime();
			jo.put("testTime", DateUtils.longDate(date));
		}else{
			jo.put("type", 0);
			jo.put("val", 0);
			jo.put("result", "");
			jo.put("testTime", "");
		}
		return jo.toString();
	}
	
	/**
	 *  查询生成新版血糖报告的血糖信息
	 * @throws ParseException 
	 */
	public String queryBloodSugarForNtgRpt(Integer cid, Date date){
		
		JSONObject jo = new JSONObject();
		//本月开始日期
		Date beginDate = DateUtils.getDateByType(date, "yyyy-MM");
		//本月结束日期
		Date endDate = null;
		Date thisDate = DateUtils.getAppointDate(beginDate, -1);
		//上月开始日期
		Date lastBeginDate = null;
		//上月结束日期
		Date lastEndDate = null;
		
		try {
			endDate = DateUtils.getLastMonthDay(beginDate);
			lastBeginDate = DateUtils.getFirstMonthDay(thisDate);
			lastEndDate = DateUtils.getLastMonthDay(thisDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//本月的血糖信息
		List<BloodSugar> lstSugar = bloodSugarDao.queryTgBloodSugar(cid,
				beginDate, endDate);
		
		//上月的血糖信息
		List<BloodSugar> lstLastSugar = bloodSugarDao.queryTgBloodSugar(cid,
				lastBeginDate, lastEndDate);
		
		//低血糖
		List<BloodSugar> lstLowSugar = new ArrayList<BloodSugar>();
		
		//不良血糖
		List<BloodSugar> lstBadSugar = new ArrayList<BloodSugar>();
		
		//达标血糖
		List<BloodSugar> lstNormalSugar = new ArrayList<BloodSugar>();
		
		//本月餐前血糖所有值
		List<Double> lstSBVal = new ArrayList<Double>();
		
		//本月餐后血糖所有值
		List<Double> lstSAVal = new ArrayList<Double>();
		
		//上月餐前血糖所有值
		List<Double> lstLastSBVal = new ArrayList<Double>();
		
		//上月餐后血糖所有值
		List<Double> lstLastSAVal = new ArrayList<Double>();
		
		int lowCount = 0;
		int normalCount = 0;
		int badCount = 0;
		
		//本月餐前血糖总和
		double sbSum = 0;
		//本月餐后血糖总和
		double saSum = 0;
		
		//上月餐前血糖总和
		double lastSbSum = 0;
		//上月餐后血糖总和
		double lastSaSum = 0;
		
		//饼状图的数据
		JSONArray jaSugarPie = new JSONArray();
		
		if(!CollectionUtils.isEmpty(lstSugar)){
			for (BloodSugar bloodSugar : lstSugar) {
				int bstype = bloodSugar.getBloodSugarType();
				double val = bloodSugar.getBloodSugarValue();
				if (bstype == BloodSugar.SUGAR_TYPE
						|| bstype == BloodSugar.SUGAR_ZAOCAN_BEFORE
						|| bstype == BloodSugar.SUGAR_WUCAN_BEFORRE
						|| bstype == BloodSugar.SUGAR_WANCAN_BEFORE) {
					if (val < 4.4) {
						
						lstSBVal.add(val);
						lowCount ++ ;
						sbSum += val;
						lstLowSugar.add(bloodSugar);
						
					}else if(val >=4.4 && val <= 7.0){
						
						lstSBVal.add(val);
						normalCount ++ ;
						sbSum += val;
						lstNormalSugar.add(bloodSugar);
						
					}else if(val > 7.0){
						
						lstSBVal.add(val);
						badCount++;
						sbSum += val;
						lstBadSugar.add(bloodSugar);
					}
				} else if (bstype == BloodSugar.SUGAR_TYPE_2H
						|| bstype == BloodSugar.SUGAR_ZAOCAN_AFTER
						|| bstype == BloodSugar.SUGAR_WUCAN_AFTER
						|| bstype == BloodSugar.SUGAR_WANCAN_AFTER) {
					if (val < 4.4) {
						
						lstSAVal.add(val);
						lowCount ++ ;
						saSum += val;
						lstLowSugar.add(bloodSugar);
						
					}else if(val >= 4.4 && val <= 10.0){
						
						lstSAVal.add(val);
						normalCount ++ ;
						saSum += val;
						lstNormalSugar.add(bloodSugar);
						
					}else if(val > 10.0){
						
						lstSAVal.add(val);
						badCount++;
						saSum += val;
						lstBadSugar.add(bloodSugar);
					}
				} else if (bstype == BloodSugar.SUGAR_LINGCHEN
						|| bstype == BloodSugar.SUGAR_SLEEP_BEFORE) {
					if (val >= 4.4 && val <= 11.1) {
						
						normalCount ++ ;
						lstNormalSugar.add(bloodSugar);
						
					}else if(val <3.9 || val >11.1){
						
						badCount ++ ;
						lstBadSugar.add(bloodSugar);
						
					}else if(val < 3.9){
						lowCount++;
						lstLowSugar.add(bloodSugar);
					}
				}
			}
			
			JSONObject joNormal = new JSONObject();
			joNormal.put("name", "正常");
			joNormal.put("value", normalCount);
			jaSugarPie.add(joNormal);
			
			JSONObject joBad = new JSONObject();
			joBad.put("name", "不良");
			joBad.put("value", badCount);
			jaSugarPie.add(joBad);
			
			JSONObject joLow = new JSONObject();
			joLow.put("name", "低血糖");
			joLow.put("value", lowCount);
			jaSugarPie.add(joLow);
			
			if(!CollectionUtils.isEmpty(lstSBVal)){
				Collections.sort(lstSBVal);
				//本月餐前血糖最高值
				jo.put("sbBigVal", lstSBVal.get(lstSBVal.size()-1));
				//本月餐前血糖平均值
				jo.put("sbAvgVal", Math.round(sbSum/lstSBVal.size()));
			}
			
			if(!CollectionUtils.isEmpty(lstSAVal)){
				Collections.sort(lstSAVal);
				//本月餐后血糖最高值
				jo.put("saBigVal", lstSAVal.get(lstSAVal.size()-1));
				//本月餐后血糖平均值
				jo.put("saAvgVal", Math.round(saSum/lstSAVal.size()));
			}
		}
		
		
		if(!CollectionUtils.isEmpty(lstLastSugar)){
			for (BloodSugar bloodSugar : lstLastSugar) {
				int bstype = bloodSugar.getBloodSugarType();
				double val = bloodSugar.getBloodSugarValue();
				if (bstype == BloodSugar.SUGAR_TYPE
						|| bstype == BloodSugar.SUGAR_ZAOCAN_BEFORE
						|| bstype == BloodSugar.SUGAR_WUCAN_BEFORRE
						|| bstype == BloodSugar.SUGAR_WANCAN_BEFORE) {
					
					lstLastSBVal.add(val);
					lastSbSum += val;
					
				} else if (bstype == BloodSugar.SUGAR_TYPE_2H
						|| bstype == BloodSugar.SUGAR_ZAOCAN_AFTER
						|| bstype == BloodSugar.SUGAR_WUCAN_AFTER
						|| bstype == BloodSugar.SUGAR_WANCAN_AFTER) {
					
					lstLastSAVal.add(val);
					lastSaSum += val;
				}
			}
			
			if(!CollectionUtils.isEmpty(lstLastSBVal)){
				Collections.sort(lstLastSBVal);
				//本月餐前血糖最高值
				jo.put("sbLastBigVal", lstLastSBVal.get(lstLastSBVal.size()-1));
				//本月餐前血糖平均值
				jo.put("sbLastAvgVal", Math.round(lastSbSum/lstLastSBVal.size()));
			}
			
			if(!CollectionUtils.isEmpty(lstLastSAVal)){
				Collections.sort(lstLastSAVal);
				//本月餐后血糖最高值
				jo.put("saLastBigVal", lstLastSAVal.get(lstLastSAVal.size()-1));
				//本月餐后血糖平均值
				jo.put("saLastAvgVal", Math.round(lastSaSum/lstLastSAVal.size()));
			}
		}
		
		jo.put("bsData", jaSugarPie.toArray());
		
		jo.put("lowCount", lowCount);
		jo.put("normalCount", normalCount);
		jo.put("badCount", badCount);
		
		int sumCount = lowCount+normalCount+badCount;
		jo.put("sumCount", sumCount);
		
//		double lowPre = MoneyFormatUtil.formatDouble((double)lowCount/(double)sumCount) * 100;
//		double normalPre = MoneyFormatUtil.formatDouble((double)normalCount/(double)sumCount)* 100;
//		double badPre = 100 - normalPre - badCount;
//		jo.put("lowPre", lowPre );
//		jo.put("normalPre", normalPre);
//		jo.put("badPre", badPre);
		
		String badSugarDetail = queryBloodSugarDetail(lstBadSugar, cid, 2);
		jo.put("badSugarDetail", badSugarDetail);
		
		String lowSugarDetail = queryBloodSugarDetail(lstLowSugar, cid, 1);
		jo.put("lowSugarDetail", lowSugarDetail);
		
		System.out.println(jo.toString());
		
		return jo.toString();
	}
	
	
	/**
	 * @param type  血糖类型 1：低血糖  2：高血糖
	 * @return
	 */
	public String queryBloodSugarDetail(List<BloodSugar> lst, Integer cid, int type){
		
		JSONObject json = new JSONObject();
		JSONArray ja = new JSONArray();
		
		//饮食摄入不足次数
		int dietLittle = 0;
		//饮食摄入超标次数
		int dietMore = 0;
		//运动过量次数
		int sportLittle = 0;
		//运动不足次数
		int sportMore = 0;
		//睡眠不足次数
		int sleepLittle = 0;
		
		if(!CollectionUtils.isEmpty(lst)){
			
			for (BloodSugar bloodSugar : lst) {
				JSONObject jo = new JSONObject();
				
				//测试血糖时间
				jo.put("testDate", DateUtils.longDate(bloodSugar.getTestDateTime()));
				//血糖值信息
				if(type == 1){
					jo.put("bloodSugarVal", bloodSugar.getBloodSugarValue()+"（低血糖）");
				}else if(type == 2){
					jo.put("bloodSugarVal", bloodSugar.getBloodSugarValue()+"（血糖偏高）");
				}
				
				Date testDate = bloodSugar.getTestDateTime();
				Date date = DateUtils.getDateByType(testDate, "yyyy-MM-dd");
				
				//饮食
				NTimelineMeals nmeals = new NTimelineMeals();
				nmeals.setClientId(cid);
				NTimelineMeals meals = new NTimelineMeals();
				
				Integer conType = null;
				meals = nmealsDao.queryTimeLineMeals(nmeals, date);
				
				//如果这天没有饮食数据说明这天没有上传饮食数据
				
				if(meals != null){
					Integer bloodType = bloodSugar.getBloodSugarType();
					
					if(bloodType == BloodSugar.SUGAR_ZAOCAN_BEFORE || 
							bloodType == BloodSugar.SUGAR_TYPE){
						conType = 4;
					}else if(bloodType == BloodSugar.SUGAR_ZAOCAN_AFTER ||
							bloodType == BloodSugar.SUGAR_TYPE_2H){
						conType = 1;
					}else if(bloodType == BloodSugar.SUGAR_WUCAN_BEFORRE){
						conType = 1;
					}else if(bloodType == BloodSugar.SUGAR_WUCAN_AFTER){
						conType = 2;
					}else if(bloodType == BloodSugar.SUGAR_WANCAN_BEFORE){
						conType = 2;
					}else if(bloodType == BloodSugar.SUGAR_WANCAN_AFTER){
						conType = 3;
					}else if(bloodType == BloodSugar.SUGAR_LINGCHEN || 
							bloodType == BloodSugar.SUGAR_SLEEP_BEFORE){
						conType = 5;
					}
					
					if(conType != null){
						if(conType == 1){
							if(meals.getZaocanConsumed() != null)
								jo.put("dietInfo", "早餐"+meals.getZaocanConsumed()+"大卡（推荐"+meals.getZaocanCal()+"大卡）");
							else
								jo.put("dietInfo", "无数据");
						}else if(conType == 2){
							if(meals.getLunchConsumed() != null)
								jo.put("dietInfo", "午餐"+meals.getLunchConsumed()+"大卡（推荐"+meals.getLunchCal()+"大卡）");
							else
								jo.put("dietInfo", "无数据");
						}else if(conType == 3){
							if(meals.getLunchConsumed() != null)
								jo.put("dietInfo", "晚餐"+meals.getDinnerConsumed()+"大卡（推荐"+meals.getDinnerCal()+"大卡）");
							else
								jo.put("dietInfo", "无数据");
						}else if(conType == 4){
							jo.put("dietInfo", "空腹");
						}else if(conType == 5){
							jo.put("dietInfo", "无数据");
						}
					}
					//每餐次的推荐值
					double mealCal = meals.getZaocanCal();
					//每餐次推荐最高值
					double nmDiet = mealCal + mealCal * 0.1;
					//每餐次推荐最低值
					double nlDiet = mealCal - mealCal * 0.1;
					
					Double zaocanCal = meals.getZaocanConsumed();
					if(zaocanCal != null){
						if(zaocanCal < nlDiet){
							dietLittle += 1;
						}
						else if(zaocanCal > nmDiet){
							dietMore += 1;
						}
					}
					
					Double lunchCal = meals.getLunchConsumed();
					if(lunchCal != null){
						if(lunchCal < nlDiet){
							dietLittle += 1;
						}
						else if(lunchCal > nmDiet){
							dietMore += 1;
						}
					}
					
					Double dinnerCal = meals.getDinnerConsumed();
					if(dinnerCal != null){
						if(dinnerCal < nlDiet){
							dietLittle += 1;
						}
						else if(dinnerCal > nmDiet){
							dietMore += 1;
						}
					}
					
				}else{
					jo.put("dietInfo", "无数据");
				}
				
				//运动
				NTgSport nsport = new NTgSport();
				nsport.setClientId(cid);
				NTgSport sport = nsportDao.queryClientNTgSport(nsport, date);
				//当天运动信息
				if(sport != null){
					//运动消耗热量
					double sportCal = 0;
					if(sport.getCalorie() != null){
						sportCal = sport.getCalorie();
					}
					//运动推荐热量
					double sportConsumed = sport.getCalorieConsumed();
					
					jo.put("sportInfo", sportConsumed+"大卡（推荐"+sportCal+"大卡）");
					
					//运动正常值最高
					double nmSport = sportConsumed + sportConsumed * 0.5;
					//运动正常值最低
					double nlSport = sportConsumed - sportConsumed * 0.5;
					
					if(sportCal < nlSport){
						sportLittle += 1;
					}
					if(sportCal > nmSport){
						sportMore += 1;
					}
				}else{
					jo.put("sportInfo", "无数据");
				}
				
				//睡眠
				NTimelineWakeSleep nsleep = new NTimelineWakeSleep();
				nsleep.setClientId(cid);
				String yesterDay = DateUtils.getAppointDateString(date, -1);
				NTimelineWakeSleep sleep = nsleepDao.findWakeSleepRecord(cid, yesterDay, NtgConstant.CONTYPE_SLEEP);
				if(sleep != null){
					//睡眠数据（前一天）
					Integer duration = sleep.getDuration();
					if(duration != null){
						jo.put("sleepInfo", duration/60);
						double hours = duration/60;
						//睡眠不足
						if(hours < (8-8*0.1)){
							sleepLittle += 1;
						}
					}
				}else{
					jo.put("sleepInfo", "无数据");
				}
				
				ja.add(jo.toString());
			}
		}
		
		json.put("dietLittle", dietLittle);
		json.put("dietMore", dietMore);
		json.put("sportLittle", sportLittle);
		json.put("sportMore", sportMore);
		json.put("sleepLittle", sleepLittle);
		
		if(type == 1){
			json.put("lowSugarInfo", ja.toArray());
		}else if(type == 2){
			json.put("badSugarInfo", ja.toArray());
		}
		
		System.out.println(json.toString());
		
		return json.toString();
	}
	
	
	public int queryUploadBloodSugarCount(QueryCondition queryCondition){
		Date startDate = null;
		Date endDate = null;
		if(queryCondition != null){
			if(queryCondition.getBeginTime() != null){
				startDate = queryCondition.getBeginTime();
			}
			if(queryCondition.getEndTime() != null){
				endDate = DateUtils.getCurrentDayEndTime(queryCondition.getEndTime());
			}
		}
		
		return bloodSugarDao.queryUploadBloodSugarCount(startDate, endDate);
	}
	
	
	public int queryAvgBloodSugarCount(QueryCondition queryCondition){
		Date begTime = DateUtils.getAppointDate(new Date(), -6);
		Date startDate = DateUtils.getCurrentDayStartTime(begTime);
		Date endDate = DateUtils.getCurrentDayEndTime(new Date());
		if(queryCondition != null){
			if(queryCondition.getBeginTime() != null){
				startDate = queryCondition.getBeginTime();
			}
			if(queryCondition.getEndTime() != null){
				endDate = DateUtils.getCurrentDayEndTime(queryCondition.getEndTime());
			}
		}
		int allCount = bloodSugarDao.queryUploadBloodSugarCount(startDate, endDate);
		
		//一共多少天
		int days = (int)DateUtils.getQuot(startDate, endDate)+1;
		return (int)allCount/days;
	}
	
	public String queryUploadDays(Integer clientId, Date startDate, Date endDate) {
		if(null == clientId || null == startDate || null == endDate) {
			return StringUtils.encapsulationJSON(new JSONObject(), Constant.INTERFACE_PARAM_ERROR, "参数格式不正确", "");
		}
		int count = bloodSugarDao.queryUploadDays(clientId, startDate, endDate);
		return StringUtils.encapsulationJSON(new JSONObject(), Constant.INTERFACE_SUCC, "", "{\"count\":"+ count +"}");
	}
}
