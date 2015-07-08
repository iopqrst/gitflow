package com.bskcare.ch.service.impl;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.ClientRegEval;
import com.bskcare.ch.bo.FamilyName;
import com.bskcare.ch.bo.JpushList;
import com.bskcare.ch.bo.UploadED;
import com.bskcare.ch.bo.ntg.NTgSportExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.constant.LogType;
import com.bskcare.ch.constant.MonConstant;
import com.bskcare.ch.dao.BloodOxygenDao;
import com.bskcare.ch.dao.BloodPressureDao;
import com.bskcare.ch.dao.BloodSugarDao;
import com.bskcare.ch.dao.ClientExtendDao;
import com.bskcare.ch.dao.ElectrocardiogramDao;
import com.bskcare.ch.dao.FamilyRelationDao;
import com.bskcare.ch.dao.HealthTipsDao;
import com.bskcare.ch.dao.MonitoringDataDao;
import com.bskcare.ch.dao.TemperatureDao;
import com.bskcare.ch.dao.ntg.NTgSportDao;
import com.bskcare.ch.dao.score.ScoreRecordDao;
import com.bskcare.ch.service.ClientExtendService;
import com.bskcare.ch.service.ManageLogService;
import com.bskcare.ch.service.UnusualDataService;
import com.bskcare.ch.service.UploadMonitoringDataService;
import com.bskcare.ch.service.medal.NTgMedalRecordService;
import com.bskcare.ch.service.score.ScoreRecordService;
import com.bskcare.ch.util.CMCCResponse;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.FileUtils;
import com.bskcare.ch.util.HttpClientUtils4CMCC;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.MonitoringDataResult;
import com.bskcare.ch.util.ReadFromFileDrawImage;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.util.jpush.AddQueue;
import com.bskcare.ch.util.jpush.JpushParameter;
import com.bskcare.ch.util.jpush.JpushUtil;
import com.bskcare.ch.util.jpush.Objective;
import com.bskcare.ch.vo.BloodOxygen;
import com.bskcare.ch.vo.BloodPressure;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.Electrocardiogram;
import com.bskcare.ch.vo.HealthTips;
import com.bskcare.ch.vo.ManageLog;
import com.bskcare.ch.vo.Temperature;
import com.bskcare.ch.vo.UnusualData;
import com.bskcare.ch.vo.medal.NTgMedalModule;
import com.bskcare.ch.vo.medal.NTgMedalRule;
import com.bskcare.ch.vo.score.ScoreRecord;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 上传监测数据(该类仅仅为上传数据及相关的方法，其他绕道）
 */
@Service
@SuppressWarnings("unchecked")
public class UploadMonitoringDataServiceImpl implements UploadMonitoringDataService {

	private transient final Logger log = Logger.getLogger(getClass());

	@Autowired
	private BloodPressureDao bloodPressureDao;
	@Autowired
	private BloodOxygenDao bloodOxygenDao;
	@Autowired
	private BloodSugarDao bloodSugarDao;
	@Autowired
	private ElectrocardiogramDao electrocardiogramDao;
	@Autowired
	private FamilyRelationDao familyRelationDao;
//	@Autowired
//	private OrderServiceItemService osIterService;
	@Autowired
	private HealthTipsDao healthTipsDao;
	@Autowired
	MonitoringDataDao monitoringDataDao;
	@Autowired
	ManageLogService mlogServic;
	@Autowired
	UnusualDataService unusualDataService;
	@Autowired
	TemperatureDao temperatureDao; //体温
	@Autowired
	private ClientExtendService extendService;
	
	@Autowired
	private ScoreRecordService scoreService;
	@Autowired
	private ClientExtendDao clientExtendDao;
	@Autowired
	private NTgSportDao sportDao;
	@Autowired
	private ScoreRecordDao scoreRecordDao;
	@Autowired
	private NTgMedalRecordService medalRecordService;
	
	/**
	 * 上传体温
	 */
	public String uploadTemperature(ClientInfo ci, String tp, String ip) {
		JSONObject jo = new JSONObject();
		try {
			if(null != ci && !StringUtils.isEmpty(tp)) {
				log.debug(">>>>>>>>>>>>>>>>>>>>>> [体温]验证用户上传数据服务项目开始");
				String sresult = validateServiceItem(ci.getId());
				log.debug(">>>>>>>>>>>>>>>>>>>>>> [体温]验证用户上传数据服务项目结束, clientId = "
						+ ci.getId() + " ,sresult = " + sresult);
				
				if (!StringUtils.isEmpty(sresult)) //没有对应服务项
					return sresult;

				log.debug(">>>>>>>>>>>>>>>>>>>>>> 体温上传数据转换开始");
				List<Temperature> list = JsonUtils.getListJsonDate(tp, Temperature.class, null);
				log.debug(">>>>>>>>>>>>>>>>>>>>>> 体温上传数据转换结束");

				if(!CollectionUtils.isEmpty(list)){
				
					for (Temperature temp : list) {
						//获取温度等级
						int tLevel = MonitoringDataResult.getTemperatureLevel(temp.getTemperature());
						
						String result = MonitoringDataResult
								.getTemperatureTips(tLevel, temp.getTemperature());
						
						temp.setClientId(ci.getId());
						temp.setResult(result);
						temp.setDispose(MonConstant.UNDISPOSE);
						temp.setUploadDateTime(new Date());
						
						//tLevel == 2 正常，其余不正常
						if (tLevel != 2) {
							temp.setState(MonConstant.STATE_ABNORMAL);
							temperatureDao.add(temp);
							
							//TODO 将异常数据添加到另外一张表中
							addTemperatureUnusuallData(temp);
							
						} else {
							temp.setState(MonConstant.STATE_NORMAL);
							temperatureDao.add(temp);
						}
						
						//给亲情账号推送消息
						pushPressureMessage(ci, result, temp.getUploadDateTime(),
								temp.getTestDateTime(), JpushList.TYPE_TEMPERATURE);
						
						// 获取健康小贴士
						jo.put("healthTips", "");
				
						// 报警信息
						if (temp.getTemperature() >= 39) {
							JpushUtil ju = new JpushUtil();
							ju.alarmJpushUtil("警报");
						}
				
						//记录上传数据日志
						ManageLog mlog = new ManageLog();
						mlog.setContent("上传体温数据数据：" + temp.getTemperature());
						mlog.setOperateDate(new Date());
						mlog.setType(LogType.CLIENT_UPLOAD_TEMPERATURE);
						mlog.setClientId(ci.getId());
						mlog.setUserIp(ip);
						mlogServic.addLog(mlog);
					}
					
					log.debug(">>>>>>>>>>>>>>>>>>>>>> 体温上传结束，本次共上传：" + list.size() + "条体温数据。");
					extendService.updateLastTime(ci.getId(), "lastUploadDateTime");
					jo.put("status", "ok");
					jo.put("data", JsonUtils.getJsonString4JavaListDate(list));
				} else {
					jo.put("status", "err");
					jo.put("data", "上传数据为空");
				}
				return jo.toString();
			} else {
				jo.put("status", "err");
				jo.put("data", "获取账号信息失败或者上传数据为空");
				return jo.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("status", "err");
			jo.put("data", "上传数据出现问题，请稍后再试");
			return jo.toString();
		}
		
	}


	/**
	 * 上传血压监测数据
	 */
	public String uploadBloodPressure(ClientInfo ci, String msg, String ip, String version) {
		JSONObject jo = new JSONObject();
		try {
			if(null != ci && !StringUtils.isEmpty(msg)) {
				log.debug(">>>>>>>>>>>>>>>>>>>>>> [血压]验证用户上传数据服务项目开始");
				String sresult = validateServiceItem(ci.getId());
				log.debug(">>>>>>>>>>>>>>>>>>>>>> [血压]验证用户上传数据服务项目结束, clientId = "
						+ ci.getId() + " ,sresult = " + sresult);
				
				if (!StringUtils.isEmpty(sresult)) //没有对应服务项
					return sresult;

				log.debug(">>>>>>>>>>>>>>>>>>>>>> 血压上传数据转换开始");
				List<BloodPressure> list = JsonUtils.getListJsonDate(msg, BloodPressure.class, null);
				log.debug(">>>>>>>>>>>>>>>>>>>>>> 血压上传数据转换结束");

				if(!CollectionUtils.isEmpty(list)){
				
					for (BloodPressure bloodPressure : list) {
						//根据sbp和dbp获取相应的等级
						int bpLevel = MonitoringDataResult
										.getBloodPressureLevel(bloodPressure.getSbp(), bloodPressure.getDbp());
						
						String result = MonitoringDataResult
								.getHashMapValue(bpLevel, bloodPressure.getSbp(), bloodPressure.getDbp());
						
						bloodPressure.setResult(result);
						bloodPressure.setClientId(ci.getId());
						bloodPressure.setUploadDateTime(new Date());
				
						bloodPressure.setDispose(MonConstant.UNDISPOSE); //未处理
						
						//bpLevel(0:低血压,4：高血压，其他为正常或者趋于正常)
						if (bpLevel == 0 || bpLevel == 4) {
							bloodPressure.setState(MonConstant.STATE_ABNORMAL);
							bloodPressureDao.addBloodPressure(bloodPressure);// 增加一条血压数据
							
							//TODO 将异常数据添加到另外一张表中
							addPressureUnusuallData(bloodPressure);
							
						} else {
							bloodPressure.setState(MonConstant.STATE_NORMAL);
							bloodPressureDao.addBloodPressure(bloodPressure);// 增加一条血压数据
						}
						
						//给亲情账号推送消息
						pushPressureMessage(ci, result, bloodPressure.getUploadDateTime(),
								bloodPressure.getTestDateTime(), JpushList.TYPE_PRESSURE);
						
						String tips = getHealthTips(1, bpLevel);
						// 获取健康小贴士
						jo.put("healthTips", tips);
				
						// 报警信息
						if (bloodPressure.getSbp() >= 170
								|| bloodPressure.getDbp() >= 100
								|| bloodPressure.getSbp() < 80
								|| bloodPressure.getDbp() < 50) {
							JpushUtil ju = new JpushUtil();
							ju.alarmJpushUtil("警报");
						}
				
						//记录上传数据日志
						ManageLog mlog = new ManageLog();
						mlog.setContent("上传血压数据数据：dbp=" + bloodPressure.getDbp() + ",sbp="+bloodPressure.getSbp());
						mlog.setOperateDate(new Date());
						mlog.setType(LogType.CLIENT_UPLOAD_PRESS);
						mlog.setClientId(ci.getId());
						mlog.setUserIp(ip);
						mlogServic.addLog(mlog);
						
						noticeCmccForBloodPressure(ci, bloodPressure, tips);
					}
					
					log.debug(">>>>>>>>>>>>>>>>>>>>>> 血压上传结束，本次共上传：" + list.size() + "条血压数据。");
					extendService.updateLastTime(ci.getId(), "lastUploadDateTime");
					
					//上传血压数据成功，添加健康报告成就    4：上传血压数据
					extendService.updateDetailScore(ci.getId(), 4);
					
					//客户勋章升级提醒
					int isUpgrade = medalRecordService.addMedalRecord(NTgMedalRule.RULE_UPLOAD_SLEEP, ci.getId());
					
					if(isV3(version)) {
						jo.put("list", JsonUtils.getJsonString4JavaListDate(list));
						jo.put("isUpgrade", isUpgrade);
						//根据客户id查询勋章信息
						if(isUpgrade != 0){
							String data = medalRecordService.queryClientMedalDetail(ci.getId(), NTgMedalModule.MEDAL_HARD);
							jo.put("medal", data);
						}else{
							jo.put("medal", "");
						}
						return JsonUtils.encapsulationJSON(0, "上传数据成功", jo.toString()).toString();
					} else{
						jo.put("status", "ok");
						jo.put("data", JsonUtils.getJsonString4JavaListDate(list));
						return jo.toString();
					}
				} else {
					if(isV3(version)) {
						return JsonUtils.encapsulationJSON(0, "上传数据为空", "").toString();
					} else{
						jo.put("status", "err");
						jo.put("data", "上传数据为空");
						return jo.toString();
					}
				}
			} else {
				if(isV3(version)) {
					return JsonUtils.encapsulationJSON(0, "获取账号信息失败或者上传数据为空", "").toString();
				} else{
					jo.put("status", "err");
					jo.put("data", "获取账号信息失败或者上传数据为空");
					return jo.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(isV3(version)) {
				return JsonUtils.encapsulationJSON(0, "上传数据出现问题，请稍后再试", "").toString();
			} else{
				jo.put("status", "err");
				jo.put("data", "上传数据出现问题，请稍后再试");
				return jo.toString();
			}
		}
		
	}
	
	public String uploadBloodOxygen(ClientInfo ci, String msg, String ip) {
		JSONObject jo = new JSONObject();
		
		try {
			if(null != ci && !StringUtils.isEmpty(msg)) {
				log.debug(">>>>>>>>>>>>>>>>>>>>>> [血氧]验证用户上传数据服务项目开始");
				String sresult = validateServiceItem(ci.getId());
				log.debug(">>>>>>>>>>>>>>>>>>>>>> [血氧]验证用户上传数据服务项目结束, clientId = "
						+ ci.getId() + " ,sresult = " + sresult);
				if (!StringUtils.isEmpty(sresult))
					return sresult;

				log.debug(">>>>>>>>>>>>>>>>>>>>>> 血氧上传数据转换结束");
				List<BloodOxygen> list = JsonUtils.getListJsonDate(msg,
						BloodOxygen.class, null);
				log.debug(">>>>>>>>>>>>>>>>>>>>>> 血氧上传数据转换结束");

				if(!CollectionUtils.isEmpty(list)) {
					for (BloodOxygen bloodOxygen : list) {
						//血氧级别
						int olevel = MonitoringDataResult.getBloodOxygenLevel(
								bloodOxygen.getBloodOxygen(), bloodOxygen
										.getHeartbeat());
						
						String result = MonitoringDataResult.getBloodOxygenResult(
								bloodOxygen.getBloodOxygen(), bloodOxygen
										.getHeartbeat());
						bloodOxygen.setResult(result);
						
						if (olevel == 0) {//正常
							bloodOxygen.setState(MonConstant.STATE_NORMAL);
						} else {//不正常
							bloodOxygen.setState(MonConstant.STATE_ABNORMAL);
						}

						bloodOxygen.setClientId(ci.getId());
						bloodOxygen.setUploadDateTime(new Date());
						bloodOxygen.setDispose(MonConstant.UNDISPOSE);
						// 手机端会上传测试间（因为数据可能为之前测试的的数据）
						bloodOxygenDao.addBloodOxygen(bloodOxygen);
						
						//添加异常数据
						if(0!= olevel) {
							addOxygenUnusuallData(bloodOxygen);
						}
						
						//给亲情账号推送消息
						pushPressureMessage(ci, result, bloodOxygen.getUploadDateTime(),
								bloodOxygen.getTestDateTime(), JpushList.TYPE_OXYGEN);
						
						// 亲情账号人员 推送消息
						jo.put("healthTips", getHealthTips(2, olevel));

						// 报警信息
						if (bloodOxygen.getBloodOxygen() < 90
								|| bloodOxygen.getHeartbeat() < 50
								|| bloodOxygen.getHeartbeat() > 120) {
							JpushUtil ju = new JpushUtil();
							ju.alarmJpushUtil("警报");
						}
						
						ManageLog mlog = new ManageLog();
						mlog.setContent("上传血氧数据数据:oxygen=" + bloodOxygen.getBloodOxygen() 
								+ ",heart=" + bloodOxygen.getHeartbeat());
						mlog.setOperateDate(new Date());
						mlog.setType(LogType.CLIENT_UPLOAD_OXYGEN);
						mlog.setClientId(ci.getId());
						mlog.setUserIp(ip);
						mlogServic.addLog(mlog);

					}

					log.debug(">>>>>>>>>>>>>>>>>>>>>> 血氧上传结束，本次共上传：" + list.size() + "条血氧数据。");
					extendService.updateLastTime(ci.getId(), "lastUploadDateTime");
					
					jo.put("status", "ok");
					jo.put("data", JsonUtils.getJsonString4JavaListDate(list));
					
				}  else {
					jo.put("status", "err");
					jo.put("data", "上传数据为空");
				}
				return jo.toString();
			} else {
				jo.put("status", "err");
				jo.put("data", "获取账号信息失败或者上传数据为空");
				return jo.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("status", "err");
			jo.put("data", "上传数据出现问题，请稍后再试");
			return jo.toString();
		}
		
	}
	

	
	public String uploadBloodSugar(ClientInfo ci, String msg, String ip, String version) {
		JSONObject jo = new JSONObject();
		try {
			if(null != ci && !StringUtils.isEmpty(msg)) {
				log.debug(">>>>>>>>>>>>>>>>>>>>>> [血糖]验证用户上传数据服务项目开始");
				String sresult = validateServiceItem(ci.getId());
				log.debug(">>>>>>>>>>>>>>>>>>>>>> [血糖]验证用户上传数据服务项目结束, clientId = "
						+ ci.getId() + " ,sresult = " + sresult);

				if (!StringUtils.isEmpty(sresult))
					return sresult;

				log.debug(">>>>>>>>>>>>>>>>>>>>>> 血糖上传数据转换开始");
				List<BloodSugar> list = JsonUtils.getListJsonDate(msg,
						BloodSugar.class, null);

				if(!CollectionUtils.isEmpty(list)) {
					log.debug(">>>>>>>>>>>>>>>>>>>>>> 血糖类型为：" 
							+ (BloodSugar.SUGAR_TYPE == list.get(0).getBloodSugarType() ? "空腹" : "餐后2h"));
					int validIsUpgrade = 0;
					int hardIsUpgrade = 0;
					for (BloodSugar bloodSugar : list) {
						//血糖值等级
						
						int btype = bloodSugar.getBloodSugarType();
						
						int sugarLevel = MonitoringDataResult.getBloodSugarLevel(
								bloodSugar.getBloodSugarValue(), btype);
						
						String result = MonitoringDataResult.getBloodSugarResult(
								bloodSugar.getBloodSugarValue(), btype);
						
						bloodSugar.setResult(result);
						bloodSugar.setClientId(ci.getId());
						bloodSugar.setUploadDateTime(new Date());
						
						if (sugarLevel == 0 || sugarLevel == 1) {
							bloodSugar.setState(MonConstant.STATE_NORMAL); //正常
						} else {
							bloodSugar.setState(MonConstant.STATE_ABNORMAL); //异常
						}

						bloodSugar.setDispose(MonConstant.UNDISPOSE);
						bloodSugarDao.addBloodSugar(bloodSugar);
						
						//TODO 添加血糖异常数据
						if(sugarLevel !=0 && sugarLevel != 1){
							addSugarUnusuallData(bloodSugar);
						}
						
						int messageType = JpushList.TYPE_SUGAR;
						if (bloodSugar.getBloodSugarType() == BloodSugar.SUGAR_TYPE_2H) {
							messageType = JpushList.TYPE_SUGAR_2H;
						} 
						//给亲情账号推送消息
						pushPressureMessage(ci, result, bloodSugar.getUploadDateTime(),
								bloodSugar.getTestDateTime(), messageType);
						
						
						int htype = 3; //空腹血糖
						if (BloodSugar.SUGAR_TYPE_2H == bloodSugar.getBloodSugarType()) {
							htype = 4; //2h血糖
						} else if(BloodSugar.SUGAR_ZAOCAN_BEFORE == btype
								|| BloodSugar.SUGAR_WUCAN_BEFORRE == btype
								|| BloodSugar.SUGAR_WANCAN_BEFORE == btype
								|| BloodSugar.SUGAR_SLEEP_BEFORE == btype
								|| BloodSugar.SUGAR_LINGCHEN == btype) {
							htype = 6; //后来添加的8中分类
						} else if(BloodSugar.SUGAR_ZAOCAN_AFTER == btype
								|| BloodSugar.SUGAR_WUCAN_AFTER == btype
								|| BloodSugar.SUGAR_WANCAN_AFTER == btype) {
							htype = 7;
						}
						
						String tips = getHealthTips(htype, sugarLevel);
						jo.put("healthTips", tips);

						// 报警信息
						if (bloodSugar.getBloodSugarValue() > 16
								|| bloodSugar.getBloodSugarValue() < 3) {
							JpushUtil ju = new JpushUtil();
							ju.alarmJpushUtil("警报");
							log.debug(">>>>>>>>>>>>>>>>>>>>>> 血糖报警：" + bloodSugar.getBloodSugarValue());
						}
						
						ManageLog mlog = new ManageLog();
						mlog.setContent("上传血糖数据数据:" + bloodSugar.getBloodSugarValue());
						mlog.setOperateDate(new Date());
						Integer logType = getManageLogType(bloodSugar.getBloodSugarType());
						mlog.setType(logType);

						mlog.setClientId(ci.getId());
						mlog.setUserIp(ip);
						mlogServic.addLog(mlog);
						
						noticeCmccForBloodSugar(ci, bloodSugar, tips); // just for CMCC

						//上传血糖数据，添加成就信息
						if (sugarLevel == 0 || sugarLevel == 1) {
							extendService.updateDetailScore(ci.getId(), 1);  //正常
						} else {
							extendService.updateDetailScore(ci.getId(), 7); //异常
						}
						
						//如果上传的数据正常  添加"有效数"据勋章记录
						if (sugarLevel == 0 || sugarLevel == 1) {
							validIsUpgrade = medalRecordService.addMedalRecord(NTgMedalRule.RULE_BLOODSUGAR_GOOD, ci.getId());
						} else if(sugarLevel == -1){
							validIsUpgrade = medalRecordService.addMedalRecord(NTgMedalRule.RULE_BLOODSUGAR_LOW, ci.getId());
						}else if(sugarLevel == 2){
							validIsUpgrade = medalRecordService.addMedalRecord(NTgMedalRule.RULE_BLOODSUGAR_HIGH, ci.getId());
							System.out.println("===============");
						}
						validIsUpgrade = validIsUpgrade != 0 ? validIsUpgrade : 0;
						
						//上传血糖添加勋章  "最努力"
						// 因为在定义MedalRuleId 时将id 于 上传血糖类型值定义成一样的，所以这里直接可以使用
						hardIsUpgrade = medalRecordService.addMedalRecord(btype, ci.getId());
						hardIsUpgrade = hardIsUpgrade != 0 ? hardIsUpgrade : 0;
						
						medalRecordService.batchUpdate(ci.getId());
						
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
					}
					
					log.debug(">>>>>>>>>>>>>>>>>>>>>> 血糖上传结束，本次共上传：" + list.size() + "条血糖数据。");
					extendService.updateLastTime(ci.getId(), "lastUploadDateTime");
					//上传血糖返回值信息
					String data = "";
					if(isV3(version)) {	// 新版
						data = this.uploadMonitoringBack(ci.getId(), hardIsUpgrade, validIsUpgrade);
						return JsonUtils.encapsulationJSON(1, "上传成功", data).toString();
					} else {
						jo.put("status", "ok");
						jo.put("data", JsonUtils.getJsonString4JavaListDate(list)); 
						return jo.toString();
					}
				} else {
					if(isV3(version)) {	// 新版
						return JsonUtils.encapsulationJSON(0, "上传数据为空", "").toString();
					} else {
						jo.put("status", "err");
						jo.put("data", "上传数据为空");
						return jo.toString();
					}
				}
			} else {
				if(isV3(version)) {
					return JsonUtils.encapsulationJSON(0, "获取账号信息失败或者上传数据为空", "").toString();
				} else {
					jo.put("status", "err");
					jo.put("data", "获取账号信息失败或者上传数据为空");
					return jo.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if( isV3(version) ) {
				return JsonUtils.encapsulationJSON(0, "上传数据出现问题，请稍后再试", "").toString();
			} else {
				jo.put("status", "err");
				jo.put("data", "上传数据出现问题，请稍后再试");
				return jo.toString();
			}
		}
		
	}

	/**
	 * 
	 * @param upfile
	 * @param clientInfo
	 * @param uploadED 
	 * @return
	 */
	public String uploadElectrocardiogram(ClientInfo clientInfo, 
			UploadED uploadED, String ip) {
		
		JSONObject jo = new JSONObject();
		if(null == clientInfo) {
			jo.put("status", "err");
			jo.put("data", "获取账号信息失败");
			return jo.toString();
		}
		
		try {
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>> [心电图]验证用户上传数据服务项目开始");
			String sresult = validateServiceItem(clientInfo.getId());
			log.debug(">>>>>>>>>>>>>>>>>>>>>> [心电图]验证用户上传数据服务项目结束, clientId = "
					+ clientInfo.getId() + " ,sresult = " + sresult);

			if (!StringUtils.isEmpty(sresult))
				return sresult;
			
			// 进行64位解码
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 心电图数据流生成到临时目录开始");
			byte[] tmp = Base64.decode(uploadED.getMyfile());
			String tempScp = SystemConfig.getString("scpTmpFile"); //临时scp文件存放位置
			String tmpFileUrl = tempScp + clientInfo.getId()
					+ uploadED.getUpfileFileName();
			
			File upfile = FileUtils.getFileFromBytes(tmp, tmpFileUrl); //保存临时文件
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 心电图数据流生成到临时目录结束");

			String scpBasePath = SystemConfig.getString("client_upload_base");//真正放scp文件根目录（前半部分）
			String scpRealPath = SystemConfig.getString("scpFile"); //解析scp是scp的目录
			String scpDisFilePath = scpBasePath + scpRealPath; //scp存放地址的绝对目录
			
			// 获取上传文件日期
			String upfileFileName = uploadED.getUpfileFileName(); //上传文件名称
			Date uplodDate = uploadED.getUpLoadDate(); //上传文件时间
			
			String year = upfileFileName.substring(0, 4);
			String month = upfileFileName.substring(5, 7);
			
			scpDisFilePath = MessageFormat.format(scpDisFilePath, new Object[] {
					clientInfo.getId() + "", year, month });//将路径中的占位符改为真正的id和时间
			
			// 路径转义符号
			scpDisFilePath.replace("\\", "/");
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 心电图数据流生成到<正式>目录开始");
			
			File path = new File(scpDisFilePath);
			if (!path.exists()) // 目录不存在则创建
				path.mkdirs();
			
			String fileType = upfileFileName.substring(upfileFileName
					.lastIndexOf("."), upfileFileName.length());// 附件类型
			// 生成scp文件在系统下的路径
			upfileFileName = upfileFileName.replaceAll("-", "").replaceAll(" ", "");

			String tmppath = FileUtils.writeFile(upfile, scpDisFilePath, fileType,
					upfileFileName);
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 心电图数据流生成到<正式>目录结束");

			log.debug(">>>>>>>>>>>>>>>>>>>>>> 解析scp文件开始");

			List<Electrocardiogram> list = new ArrayList<Electrocardiogram>();
			Electrocardiogram electrocardiogram = null;

			if (uploadED.getType() != null && uploadED.getType().equals("PC80A")) {
				electrocardiogram = ReadFromFileDrawImage.readFileByPCb(tmppath);
				// 此处为测试时间 由于旧版软件需要上传时间
				electrocardiogram.setTestDateTime(uplodDate);
			} else {
				electrocardiogram = ReadFromFileDrawImage.readFileByBytes(tmppath);
			}

			log.debug(">>>>>>>>>>>>>>>>>>>>>> 解析scp文件结束");

			electrocardiogram.setClientId(clientInfo.getId());
			electrocardiogram.setUploadDateTime(new Date());
			electrocardiogramDao.addElectrocardiogram(electrocardiogram);
			//TODO 心电异常状态 state == 0 正常 其他异常
			if(null != electrocardiogram && 0 != electrocardiogram.getState()) {
				addEcgUnusuallData(electrocardiogram);
			}
			
			list.add(electrocardiogram);
			
			//给亲情账号推送消息
			pushPressureMessage(clientInfo, electrocardiogram.getResult(), 
					electrocardiogram.getUploadDateTime(), electrocardiogram.getTestDateTime(),
					JpushList.TYPE_PRESSURE);

			jo.put("healthTips", "");

			// 报警信息
			if (electrocardiogram.getAverageHeartRate() < 50
					|| electrocardiogram.getAverageHeartRate() > 120) {
				JpushUtil ju = new JpushUtil();
				ju.alarmJpushUtil("警报");
			}
			
			ManageLog mlog = new ManageLog();
			mlog.setContent("上传心电图数据:" + electrocardiogram.getId());
			mlog.setOperateDate(new Date());
			mlog.setType(LogType.CLIENT_UPLOAD_ECG);					
			mlog.setClientId(clientInfo.getId());
			mlog.setUserIp(ip);
			mlogServic.addLog(mlog);
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 心电图上传结束");
			extendService.updateLastTime(clientInfo.getId(), "lastUploadDateTime");

			jo.put("status", "ok");
			jo.put("data", JsonUtils.getJsonString4JavaListDate(list));
		} catch (Exception e) {
			jo.put("status", "err");
			jo.put("data", "上传心电图出现问题");
		}

		return jo.toString();

	}
	
	public String uploadAllMessage(ClientInfo clientInfo, String bp, String bo,
			String bs, UploadED uploadED, String tp, String ip) {
		
		JSONObject jo = new JSONObject();
		
		if(clientInfo == null) {
			jo.put("code", Constant.INTERFACE_CM_EMPTY);
			jo.put("msg", "用户手机号或者cid为空");
			return jo.toString();
		}
		
		if(StringUtils.isEmpty(bp) && StringUtils.isEmpty(bo) 
				&& StringUtils.isEmpty(bs) && StringUtils.isEmpty(tp)
				&& null == uploadED) {
			//如果上传的数据都为空
			jo.put("code", Constant.INTERFACE_PARAM_ERROR);
			jo.put("msg", "参数格式不正确");
			return jo.toString();
		}
		
		long s = System.currentTimeMillis();
		log.debug(">>>>>>>>>>>>>>>>>>>>>> 一体机上传数据开始");
		
		boolean isAllUploadSucc = true; //是否全部上传成功的标识
		
		if(!StringUtils.isEmpty(tp)) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 一键上传体温开始");
			String result = this.uploadTemperature(clientInfo, tp, ip);
			
			int code = getUpStatus(result);
			if(code == 2) {
				isAllUploadSucc = false;
			}
			
			jo.put("tpCode", code);
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 一键上传体温结束" + code);
		} else {
			jo.put("tpCode", 0); //没有上传该项数据
		}
		
		if(!StringUtils.isEmpty(bp)) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 一键上传血压开始");
			String result = this.uploadBloodPressure(clientInfo, bp, ip, null);
			
			int code = getUpStatus(result);
			if(code == 2) {
				isAllUploadSucc = false;
			}
			
			jo.put("bpCode", code);
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 一键上传血压结束");
		} else {
			jo.put("bpCode", 0); //没有上传该项数据
		}
		
		if(!StringUtils.isEmpty(bo)) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 一键上传血氧开始");
			String result = this.uploadBloodOxygen(clientInfo, bo, ip);
			
			int code = getUpStatus(result);
			if(code == 2) {
				isAllUploadSucc = false;
			}
			
			jo.put("boCode", code);
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 一键上传血氧结束");
		} else {
			jo.put("boCode", 0); //没有上传该项数据
		}
		
		if(!StringUtils.isEmpty(bs)) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 一键上传血糖开始");
			String result = this.uploadBloodSugar(clientInfo, bs, ip, null);
			
			int code = getUpStatus(result);
			if(code == 2) {
				isAllUploadSucc = false;
			}
			
			jo.put("bsCode", code);
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 一键上传血糖结束");
		}  else {
			jo.put("bsCode", 0); //没有上传该项数据
		}
		

		if (uploadED != null) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 一键上传心电图开始");
			String result = this.uploadElectrocardiogram(clientInfo, uploadED, ip);
			int code = getUpStatus(result);
			if(code == 2) {
				isAllUploadSucc = false;
			}
			
			jo.put("edCode", code);
			log.debug(">>>>>>>>>>>>>>>>>>>>>> 一键上传心电图结束");
		}  else {
			jo.put("edCode", 0); //没有上传该项数据
		}
		
		if(isAllUploadSucc){//所有上传的都成功
			jo.put("code", Constant.INTERFACE_SUCC);
			jo.put("msg", "上传成功");
		} else {//有一个或多个失败
			jo.put("code", Constant.INTERFACE_FAIL);
			jo.put("msg", "上传失败");
		}
		
		long e = System.currentTimeMillis();
		log.debug(">>>>>>>>>>>>>>>>>>>>>> 一体机上传数据结束，共用:" + (e - s));

		return jo.toString();
	}
	
	/**
	 * 推送给亲情信息
	 * 
	 * @param flist  亲情帐号list
	 * @param ci     客户信息
	 * @param testDate
	 * @param result
	 * @param type
	 * @param result2
	 */
	private void sendJpush(List<FamilyName> flist, ClientInfo ci,
			JpushList model, String title) {
		// 亲情账号人员 推送消息
		JpushParameter jpushParameter = new JpushParameter(0, null, title, null, JpushParameter.TYPE_SENDFAMILYJPUSH, flist, ci, model);
		Objective objective = new Objective(jpushParameter);
		AddQueue.addqueue(objective);
		log.info("给亲情账号推送通知，已经加入队列。已经加入");
	}
	
	/**
	 * 转化单项上传数据结果
	 * 1：成功 2：失败
	 */
	private int getUpStatus(String msg) {
		if(!StringUtils.isEmpty(msg)) {
			Map jmap = JsonUtils.getMap4Json(msg);
			
			if(null != jmap && jmap.size() > 0 && null != jmap.get("status")) {
					
				if("ok".equals(jmap.get("status")+"")) {
					return 1; //成功
				} else {
					return 2; //失败
				}
			}
		}
		return 2;
	}
	
	private Integer getManageLogType(Integer bloodSugarType){
		Integer logType = 0;
		if(bloodSugarType == BloodSugar.SUGAR_TYPE){
			logType = LogType.CLIENT_UPLOAD_SUGAR;
		}else if(bloodSugarType == BloodSugar.SUGAR_TYPE_2H){
			logType = LogType.CLIENT_UPLOAD_SUGAR2H;
		}else if(bloodSugarType == BloodSugar.SUGAR_ZAOCAN_BEFORE){
			logType = LogType.CLIENT_UPLOAD_SUGAR_ZAOCAN_BEFORE;
		}else if(bloodSugarType == BloodSugar.SUGAR_ZAOCAN_AFTER){
			logType = LogType.CLIENT_UPLOAD_SUGAR_ZAOCAN_AFTER;
		}else if(bloodSugarType == BloodSugar.SUGAR_WUCAN_BEFORRE){
			logType = LogType.CLIENT_UPLOAD_SUGAR_WUCAN_BEFORE;
		}else if(bloodSugarType == BloodSugar.SUGAR_WUCAN_AFTER){
			logType = LogType.CLIENT_UPLOAD_SUGAR_WUCAN_AFTER;
		}else if(bloodSugarType == BloodSugar.SUGAR_WANCAN_BEFORE){
			logType = LogType.CLIENT_UPLOAD_SUGAR_WANCAN_BEFORE;
		}else if(bloodSugarType == BloodSugar.SUGAR_WANCAN_AFTER){
			logType = LogType.CLIENT_UPLOAD_SUGAR_WANCAN_AFTER;
		}else if(bloodSugarType == BloodSugar.SUGAR_SLEEP_BEFORE){
			logType = LogType.CLIENT_UPLOAD_SUGAR_BEFORE_SLEEP;
		}else if(bloodSugarType == BloodSugar.SUGAR_LINGCHEN){
			logType = LogType.CLIENT_UPLOAD_SUGAR_LINGCHEN;
		}
		return logType;
	}
	
	/**
	 * 血压异常数据
	 * @param bloodPressure
	 */
	private void addPressureUnusuallData(BloodPressure bloodPressure) {
		log.info(">>>>>>>>>>>>>>>>>>>>>> 添加一条血压异常数据：cientId = "+bloodPressure.getClientId() +
				",id = " + bloodPressure.getId() +
				",sdp = " + bloodPressure.getSbp() + 
				",dbp = " + bloodPressure.getDbp());
		UnusualData ud = new UnusualData();
		ud.setClientId(bloodPressure.getClientId());
		ud.setDataId(bloodPressure.getId());
		ud.setUploadTime(bloodPressure.getUploadDateTime());
		ud.setTestTime(bloodPressure.getTestDateTime());
		ud.setStatus(UnusualData.UNHANDLE);
		ud.setType(UnusualData.BLOODPRESSURE);
		ud.setVal1(bloodPressure.getSbp()+"");
		ud.setVal2(bloodPressure.getDbp()+"");
		unusualDataService.saveAbnormalData(ud);
	}
	
	/**
	 * 根据条件获取健康小贴士
	 * @param htype 小贴士类型 1 血压, 2血氧, 3空腹血糖 ,4 餐后2小时血糖 , 5心电图
	 *  6\7新增的血糖
	 * @param degree 上传数据对应的等级
	 * @return 小贴士
	 */
	private String getHealthTips(int htype, int degree){
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>> 获取小贴士成功");
		
		HealthTips healthTips = new HealthTips();
		healthTips.setDegree(degree);
		healthTips.setType(htype); 
		healthTips.setSeason(DateUtils.getSeasonInt(new Date()));
		HealthTips healthTipsRs = healthTipsDao.getHealthTips(healthTips);
		
		return healthTipsRs != null ? healthTipsRs
				.getMessage() : "";
	}
	
	/**
	 * 给用户亲情账号推送数据
	 */
	private void pushPressureMessage(ClientInfo ci,String result, 
			Date uploadTime, Date testTime, int messageType) {
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>> 为亲情账号推送数据开始");
	
		try {
			String current = "";
			if(JpushList.TYPE_OXYGEN == messageType) {
				current = "血氧";
			} else if(JpushList.TYPE_ECG == messageType){
				current = "心电图";
			} else if(JpushList.TYPE_SUGAR == messageType){
				current = "血糖";
			} else if(JpushList.TYPE_PRESSURE == messageType){
				current = "血压";
			} else if(JpushList.TYPE_TEMPERATURE == messageType){
				current = "体温";
			}
			
			String nickName = ci.getName() != null ? ci.getName() : ci.getMobile();  
			String title = "尊敬的用户您好：您的亲友" + nickName + "于" + DateUtils.longDate(uploadTime) + "上传"+ current +"信息";
			String sendmsg = "您的亲友" + ci.getName() + "于" + DateUtils.longDate(uploadTime) + "上传"+ current +"数据，测量结果:"
						+ result.replace("尊敬的用户：您好！", "");
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>> " + current +"上传,获取亲情账号开始");
			List<FamilyName> flist = familyRelationDao.getFamilyByClientInfo(ci);
			log.debug(">>>>>>>>>>>>>>>>>>>>>> " + current +"上传,获取亲情账号结束");
			
			// 亲情账号人员 推送消息
			JpushList model = new JpushList();
			model.setMoblie(ci.getMobile());
			model.setTestdate(testTime);
			model.setContent(sendmsg);
			model.setType(messageType);

			sendJpush(flist,ci, model, title);
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>>>>>> 为亲情账号推送数据出现异常");
			e.printStackTrace();
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>> 为亲情账号推送数据结束");
	}
	

	/**
	 * 心电异常数据
	 */
	private void addEcgUnusuallData(Electrocardiogram ecg) {
		log.info("添加心电异常数据：clientId = "+ecg.getClientId() +
				",id = " + ecg.getId() +
				",AverageHeartRate = " + ecg.getAverageHeartRate());
		
		UnusualData ud = new UnusualData();
		ud.setClientId(ecg.getClientId());
		ud.setDataId(ecg.getId());
		ud.setUploadTime(ecg.getUploadDateTime());
		ud.setTestTime(ecg.getTestDateTime());
		ud.setStatus(UnusualData.UNHANDLE);
		ud.setType(UnusualData.ELECTROCARDIO);
		ud.setVal1(ecg.getAttachmentUrl());
		ud.setVal2(ecg.getAverageHeartRate()+"");
		unusualDataService.saveAbnormalData(ud);
	}

	/**
	 * 验证用户是否有上传数据服务项目
	 */
	private String validateServiceItem(Integer clientId) {
		return "";
		/**
		 * String item = Constant.ITEM_MONITORING; int sitem =
		 * osIterService.isServiceItemEnough(item, clientId); JSONObject jo =
		 * new JSONObject(); if(Constant.SERVICE_EXPIRE == sitem) { //服务过期
		 * jo.put("status", "service_expire") ; jo.put("data", "服务过期") ; return
		 * jo.toString(); } else if (Constant.NO_SELLING_SERVICE == sitem) {
		 * //没有购买过任何产品服务 jo.put("status", "service_no_selling") ; jo.put("data",
		 * "没有购买服务") ; return jo.toString(); } else if
		 * (Constant.EXPENSE_SERVICE_SUC == sitem) { //存在可用服务
		 * log.info(">>>>>>>>>>>>>congratulation on you , clientId = " +
		 * clientId + " passed monitoring validate !"); int exp =
		 * osIterService.expenseService(item, clientId, 1,
		 * ServiceExpense.NEED_RECORD); if(Constant.EXPENSE_SERVICE_SUC == exp)
		 * { log.info(">>>>>>>>>>>>> service expense success [item = " + item +
		 * ",clientId = " +clientId + "]"); } else {
		 * log.info(">>>>>>>>>>>>> service expense fail [item = " + item +
		 * ",clientId = " +clientId + "]"); } } return "";
		 */
	}
	
	/**
	 * 血氧异常数据
	 * @param bloodPressure
	 */
	private void addOxygenUnusuallData(BloodOxygen bo) {
		log.info("添加一条血氧异常数据：cientId = "+bo.getClientId() +
				",id = " + bo.getId() +
				",bloodOxygen = " + bo.getBloodOxygen() + 
				",heartbeat = " + bo.getHeartbeat());
		
		UnusualData ud = new UnusualData();
		ud.setClientId(bo.getClientId());
		ud.setDataId(bo.getId());
		ud.setUploadTime(bo.getUploadDateTime());
		ud.setTestTime(bo.getTestDateTime());
		ud.setStatus(UnusualData.UNHANDLE);
		ud.setType(UnusualData.BLOODOXYGEN);
		ud.setVal1(bo.getBloodOxygen()+"");
		ud.setVal2(bo.getHeartbeat()+"");
		unusualDataService.saveAbnormalData(ud);
	}

	/**
	 * 添加体温异常数据
	 */
	private void addTemperatureUnusuallData(Temperature temp) {
		log.info(">>>>>>>>>>>>>>>>>>>>>> 添加一条体温异常数据：clientId = "+temp.getClientId() +
				",id = " + temp.getId() +
				",temperature = " + temp.getTemperature()); 
		
		UnusualData ud = new UnusualData();
		ud.setClientId(temp.getClientId());
		ud.setDataId(temp.getId());
		ud.setUploadTime(temp.getUploadDateTime());
		ud.setTestTime(temp.getTestDateTime());
		ud.setStatus(UnusualData.UNHANDLE);
		ud.setType(UnusualData.TEMPERATURE);
		ud.setVal1(temp.getTemperature()+"");
		unusualDataService.saveAbnormalData(ud);
	}
	
	/**
	 * 血糖异常数据
	 */
	private void addSugarUnusuallData(BloodSugar bs) {
		log.info("添加一条血糖异常数据：cientId = "+bs.getClientId() +
				",id = " + bs.getId() +
				",BloodSugarValue = " + bs.getBloodSugarValue());
		
		UnusualData ud = new UnusualData();
		ud.setClientId(bs.getClientId());
		ud.setDataId(bs.getId());
		ud.setUploadTime(bs.getUploadDateTime());
		ud.setTestTime(bs.getTestDateTime());
		ud.setStatus(UnusualData.UNHANDLE);
//		ud.setType(BloodSugar.SUGAR_TYPE == bs.getBloodSugarType() 
//				? UnusualData.BLOODSUGAR : UnusualData.BLOODSUGAR2H);
		
		Integer type = 0;
		if(bs.getBloodSugarType() == BloodSugar.SUGAR_TYPE){
			type = UnusualData.BLOODSUGAR;
		}else if(bs.getBloodSugarType() == BloodSugar.SUGAR_TYPE_2H){
			type = UnusualData.BLOODSUGAR2H;
		}else{
			type = bs.getBloodSugarType();
		}
		ud.setType(type);
		
		ud.setVal1(bs.getBloodSugarValue()+"");
		unusualDataService.saveAbnormalData(ud);
	}
	
	/**
	 * 通知移动接口上传了血压数据
	 * @param ci
	 * @param bloodPressure
	 * @throws Exception 
	 */
	private void noticeCmccForBloodPressure(ClientInfo ci,
			BloodPressure bloodPressure, String tips)  {
		
		try {
			if( null != ci && null != bloodPressure ) {
				if( null != ci.getCompSource() && ClientInfo.COMPANY_USER_CMCC == ci.getCompSource() ) { //如果是移动用户推送信息
					JSONObject jresult = new JSONObject();
					
					jresult.accumulate("userType", "0"); // 用户类型, 移动健康平台注册用户0、血糖高管注册用户1
					jresult.accumulate("userId", ci.getUid()); // 用户类型为0：填写和健康用户Id 用户类型为1：填写血糖高管用户Id
					jresult.accumulate("checkTime", DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN, 
							bloodPressure.getTestDateTime()));
					jresult.accumulate("sistolicPressure", bloodPressure.getSbp());
					jresult.accumulate("diastolicPressure", bloodPressure.getDbp());
					jresult.accumulate("sPStandard", "90~140"); //收缩压
					jresult.accumulate("dPStandard", "60~90");//舒张压
					jresult.accumulate("autoAdvise", bloodPressure.getResult() + "" + StringUtils.null2str(tips, null));
					jresult.accumulate("doctorAdvise", "");
					
					CMCCResponse cmcc = HttpClientUtils4CMCC.getContentByPostForCmcc(
							"BloodPressureReq", jresult.toString());
					
					if(cmcc.isSuccessed()) {
						log.info(LogFormat.b("血压值同步成功【xtgg -> cmcc】"));
					} else {
						//TODO 处理失败
						log.error(LogFormat.f("血压数据同步失败!"));
					}
				}
			}
		} catch (Exception e) {
			log.error(LogFormat.f("血压数据同步失败!"));
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 血糖
	 * @throws Exception 
	 */
	private void noticeCmccForBloodSugar(ClientInfo ci,
			BloodSugar bs, String tips)  {
		
		try {
			if( null != ci && null != bs ) {
				if( null != ci.getCompSource() && ClientInfo.COMPANY_USER_CMCC == ci.getCompSource() ) { //如果是移动用户推送信息
					JSONObject jresult = new JSONObject();
					
					jresult.accumulate("userType", "0"); // 用户类型, 移动健康平台注册用户0、血糖高管注册用户1
					jresult.accumulate("userId", ci.getUid()); // 用户类型为0：填写和健康用户Id 用户类型为1：填写血糖高管用户Id
					jresult.accumulate("checkTime", DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN, 
							bs.getTestDateTime()));
					
					List<String> cmccSugarInfos = MonitoringDataResult.getSugarInfoForCmcc(bs.getBloodSugarValue(), bs.getBloodSugarType());
					jresult.accumulate("score", bs.getBloodSugarValue());
					
					if(cmccSugarInfos.size() == 3) {
						jresult.accumulate("checkType", cmccSugarInfos.get(0)); // 查看MonitoringDataResult 移动规定的血糖类型
						jresult.accumulate("standard", cmccSugarInfos.get(1));  //标准值Mmol/L  (例如6.7~9.4，≤7.8)
						jresult.accumulate("contrastResults", cmccSugarInfos.get(2)); // 与标准值对比（正常0 高于1 低于2 ）
					} else {
						jresult.accumulate("checkType", "-1");
						jresult.accumulate("standard", "-1");
						jresult.accumulate("contrastResults", "-1");
					}
					jresult.accumulate("autoAdvise", bs.getResult() + "" + StringUtils.null2str(tips, null));
					jresult.accumulate("doctorAdvise", "");
					
					System.out.println(">>>>> 血糖请求数据：" + jresult.toString());

					CMCCResponse cmcc = HttpClientUtils4CMCC.getContentByPostForCmcc(
							"BloodGlucoseReq", jresult.toString());
					
					if(cmcc.isSuccessed()) {
						log.info(LogFormat.b("血糖值同步成功【xtgg -> cmcc】"));
					} else {
						//TODO 处理失败
						log.error(LogFormat.f("血糖数据同步失败!"));
					}
				}
			}
		} catch (Exception e) {
			log.error("同步数据出现问题-血糖");
			e.printStackTrace();
		}
		
	}
	
	
	public String uploadMonitoringBack(Integer cid, int hardIsUpgrade, int validIsUpgrade){
		JSONObject jo = new JSONObject();
		Date date = new Date();
		Date beginDate = DateUtils.getCurrentDayStartTime(DateUtils.getAppointDate(date, -1));
		Date endDate = DateUtils.getCurrentDayEndTime(DateUtils.getAppointDate(date, -1));
		
		Date beginTime = DateUtils.getCurrentDayStartTime(DateUtils.getAppointDate(date, -6));
		Date endTime = DateUtils.getCurrentDayEndTime(date);
		
		List<ClientRegEval> lst = clientExtendDao.queryUploadMonitoringBack(cid, beginDate, endDate,
				beginTime, endTime);
		
		int integral = 0;
		BloodSugar bloodSugar = new BloodSugar();
		bloodSugar.setClientId(cid);
		Date sDate = DateUtils.getCurrentDayStartTime(new Date());
		Date eDate = DateUtils.getCurrentDayEndTime(new Date());
		int count = bloodSugarDao.queryClientBloodSugar(bloodSugar, sDate, eDate);
		
		int score = 60;
		if(!CollectionUtils.isEmpty(lst)){
			for (ClientRegEval ce : lst) {
				if(ce.getType() == 1){
					
					jo.put("diet", ce.getCount() >0 ? 1 : 0);
					score += ce.getCount() > 0 ? 10 : 0;
					integral += ce.getCount() > 0 ? 10 : 0;
				}else if(ce.getType() == 2){
					 
					jo.put("sleep", ce.getCount() >0 ? 1 : 0);
					score += ce.getCount() > 0 ? 10 : 0;
					integral += ce.getCount() > 0 ? 10 : 0;
				}else if(ce.getType() == 3){
					
					jo.put("sugar", ce.getCount() >0 ? 1 : 0);
					score += ce.getCount() > 0 ? 10 : 0;
					integral += ce.getCount() > 0 ? 10 : 0;
				}
			}
			
			List<NTgSportExtend> lstSport = sportDao.queryClientNtgSportList(cid, beginDate, endDate);
			if(!CollectionUtils.isEmpty(lstSport)){
				NTgSportExtend sport = lstSport.get(0);
				if(sport.getCalorie() != null || sport.getStepCalorie() != null){
					jo.put("sport", 1);
					score = score + 10;
					integral += 10;
				}else{
					jo.put("sport", 0);
				}
			}else{
				jo.put("sport", 0);
			}
			jo.put("score", score);
		}
		uploadBloodSugarAddScore(cid, integral + 10);
		integral = count == 0 ? integral + 10 : 0;
		jo.put("integral", integral);
		
		//勋章升级提醒信息
		jo.put("hardIsUpgrade", hardIsUpgrade);
		jo.put("validIsUpgrade", validIsUpgrade);
		//根据客户id查询勋章信息
		if(hardIsUpgrade != 0){
			String data = medalRecordService.queryClientMedalDetail(cid, NTgMedalModule.MEDAL_HARD);
			jo.put("hardMedal", data);
		}else{
			jo.put("hardMedal", "");
		}
		if(validIsUpgrade != 0){
			String data = medalRecordService.queryClientMedalDetail(cid, NTgMedalModule.MEDAL_VALID);
			jo.put("validMedal", data);
		}else{
			jo.put("validMedal", "");
		}
		
		return jo.toString();
	}
	
	//上传血糖数据添加积分
	public void uploadBloodSugarAddScore(Integer cid, int score){
		Date beginTime = DateUtils.getCurrentDayStartTime(new Date());
		Date endTime = DateUtils.getCurrentDayEndTime(new Date());
		int count = scoreRecordDao.queryScoreRecordCount(ScoreRecord.MODULE_UPLOAD_BLOOD_SUGAR, beginTime, endTime, cid);
		if(count == 0){
			ScoreRecord record = new ScoreRecord();
			record.setClientId(cid);
			record.setModuleId(ScoreRecord.MODULE_UPLOAD_BLOOD_SUGAR);
			record.setCreateTime(new Date());
			record.setScore(score);
			record.setType(1);  //1：收入
			record.setCategory(ScoreRecord.CATEGORY_SCORE);
			
			scoreRecordDao.add(record);
			// 重新计算总数
			clientExtendDao.updateScoreAndCoins(score, ScoreRecord.CATEGORY_SCORE, cid);
		}
	}
	
	/**
	 * 判断当前版本是否是v3版本
	 * @param version
	 * @return
	 */
	private boolean isV3(String version) {
		if (!StringUtils.isEmpty(version) && "v3".equals(version)) {
			return true;
		}
		
		return false;
	}
}
