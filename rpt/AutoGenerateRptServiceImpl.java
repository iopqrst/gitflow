package com.bskcare.ch.service.impl.rpt;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.rpt.RptDietDao;
import com.bskcare.ch.dao.rpt.RptMonitoringDataDao;
import com.bskcare.ch.dao.rpt.RptSportDao;
import com.bskcare.ch.dao.rpt.RptSubscribeDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientHobbyService;
import com.bskcare.ch.service.ClientInfoService;
import com.bskcare.ch.service.ClientLatestPhyService;
import com.bskcare.ch.service.ClientMedicalHistoryService;
import com.bskcare.ch.service.MonitoringDataService;
import com.bskcare.ch.service.TaskListService;
import com.bskcare.ch.service.UserInfoService;
import com.bskcare.ch.service.rpt.AutoGenerateRptService;
import com.bskcare.ch.service.rpt.RptBaseInfoService;
import com.bskcare.ch.service.rpt.RptFoodService;
import com.bskcare.ch.service.rpt.RptMaterialService;
import com.bskcare.ch.service.rpt.RptNutritiousMealsService;
import com.bskcare.ch.service.rpt.SportDiseaseService;
import com.bskcare.ch.service.rpt.SportPlanService;
import com.bskcare.ch.service.rpt.SportPrescriptionService;
import com.bskcare.ch.service.rpt.SrptDietPrincipleService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.ListUtils;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.util.RptUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.BloodOxygen;
import com.bskcare.ch.vo.BloodPressure;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.Electrocardiogram;
import com.bskcare.ch.vo.TaskList;
import com.bskcare.ch.vo.client.ClientHobby;
import com.bskcare.ch.vo.client.ClientLatestPhy;
import com.bskcare.ch.vo.client.ClientMedicalHistory;
import com.bskcare.ch.vo.rpt.RptBaseInfo;
import com.bskcare.ch.vo.rpt.RptDiet;
import com.bskcare.ch.vo.rpt.RptFood;
import com.bskcare.ch.vo.rpt.RptMaterial;
import com.bskcare.ch.vo.rpt.RptMonitoringData;
import com.bskcare.ch.vo.rpt.RptNutritiousMeals;
import com.bskcare.ch.vo.rpt.RptSport;
import com.bskcare.ch.vo.rpt.RptSubscribe;
import com.bskcare.ch.vo.rpt.SportDisease;
import com.bskcare.ch.vo.rpt.SportPlan;
import com.bskcare.ch.vo.rpt.SportPrescription;
import com.bskcare.ch.vo.rpt.SrptDietPrinciple;

@Service
public class AutoGenerateRptServiceImpl implements AutoGenerateRptService {

	@Autowired
	private RptMonitoringDataDao monitoringDao;
	@Autowired
	private MonitoringDataService monitoringService;
	@Autowired
	private RptSubscribeDao rptSubscribeDao;
	@Autowired
	private RptBaseInfoService rptBaseInfoService;
	@Autowired
	private RptMaterialService materialService;
	@Autowired
	private MonitoringDataService monitoringDataService;
	@Autowired
	private RptFoodService foodService;
	@Autowired
	private RptDietDao rptDietDao;
	@Autowired
	private RptNutritiousMealsService mealsService;
	@Autowired
	private SportDiseaseService sportDiseaseService;
	@Autowired
	private SportPrescriptionService prescriptionService; // 运动处方
	@Autowired
	private RptSportDao rptSportDao;
	@Autowired
	private SportPlanService planService; // 运动课程表
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ClientInfoService clientInfoService;
	@Autowired
	private ClientHobbyService clientHobbyService;
	@Autowired
	private TaskListService listService;
	@Autowired
	private ClientLatestPhyService clientLatestPhyService;
	@Autowired
	private ClientMedicalHistoryService historyService;
	
	@Autowired
	private SrptDietPrincipleService dietPrincipleService;

	public RptBaseInfo generateRpt(Integer clientId) {
		String subTime = DateUtils.format(new Date());
		if (clientId != null) {
			return saveRptData(clientId, subTime);
		} else {
			// 1. 查询所有要生成报告的用户
			List<RptSubscribe> rptList = getClients(subTime);
			if (!CollectionUtils.isEmpty(rptList)) {
				Map<String, JSONArray> map = new HashMap<String, JSONArray>();
				ClientInfo clientInfo = null;
				for (RptSubscribe rpts : rptList) {
					// saveRptData(rpts.getClientId(), rpts.getSubscribeTime());
					clientInfo = clientInfoService.get(rpts.getClientId());
//					//
//					Integer userid = userInfoService.getUserId(map, Integer
//							.parseInt(SystemConfig
//									.getString("role_type_doc_ii")), clientInfo
//							.getAreaId());//助理医生
					if(clientInfo==null) continue;
//					System.out.println("用户生成报告，为医生添加提醒。");
//					int userId = userInfoService.getUserId(map,Integer.parseInt(SystemConfig.getString("role_type_doc")),clientInfo.getAreaId());
//					addTaskFromRpt(clientInfo.getId(), userId);//医生的提醒

					System.out.println("用户生成报告，为健康管理师添加提醒。");
					int userId = userInfoService.getUserId(map,Integer.parseInt(SystemConfig.getString("role_type_health_manger")),clientInfo.getAreaId());
					addTaskFromRpt(clientInfo.getId(), userId);//医生的提醒
					changeSubscribeStatus(rpts);// 修改报告状态
				}
			}
		}
		return null;
	}

	// 为客户生成报告的任务
	public void addTaskFromRpt(Integer cid, Integer uid) {
		TaskList taskList = new TaskList();
		taskList.setClientId(cid);
		taskList.setTparticulars("[健康报告生成]请为此用户生成健康报告");
		taskList.setType(TaskList.TASK_TYPE_REPORT);
		taskList.setCreationUser(-1);
		taskList.setCreationTime(new Date());
		taskList.setReceiveUser(uid);
		taskList.setStatus(TaskList.TASK_STATUS_UNTREATED);
		listService.addTask(taskList);
	}

	private RptBaseInfo saveRptData(Integer clientId, String subTime) {
		RptBaseInfo baseInfo = saveBaseInfo(clientId, subTime); // 基本信息

		if (baseInfo != null) {
			Date[] myDate = setDateTime(subTime); // 报告时间段具体月份时间

			savePressureData(clientId, baseInfo, subTime, myDate); // 血压
			saveSugarData(clientId, baseInfo, subTime, myDate); // 血糖
			saveSugar2hData(clientId, baseInfo, subTime, myDate);// 餐后血糖
			saveSpO2Data(clientId, baseInfo, subTime, myDate); // 血氧
			saveECGData(clientId, baseInfo, myDate); // 心电图

			SportPlanReturn spr = saveSportPlan(clientId, baseInfo);// 运动课程表

			RptSport rptSport = saveSportData(clientId, baseInfo, myDate, spr); // 运动
			saveDietData(clientId, baseInfo); // 膳食部分（非运动）
			baseInfo.setBeginTime(myDate[0]);
			float sportCalorie = getSportCalorie(rptSport, spr.getConsume());
			saveSportDietData(sportCalorie, clientId, baseInfo);// 膳食部分（运动）
		}
		return baseInfo;
	}

	/**
	 * 运动所需要消耗的热卡
	 * 
	 * @param rptSport
	 *            可以活动运动时间
	 * @param consume
	 *            每天单项运动每分钟可以消耗多少热卡
	 * @return 返回运动一天可以消耗多少热卡
	 */
	private float getSportCalorie(RptSport rptSport, Double consume) {
		float sportCalorie = 0;
		if (null != rptSport && null != rptSport.getSportTime()) {
			sportCalorie = Math.round(consume * rptSport.getSportTime());
		}
		return sportCalorie;
	}

	/**
	 * 运动部分膳食
	 */
	private void saveSportDietData(float sportCalorie, Integer clientId,
			RptBaseInfo baseInfo) {
		if (baseInfo != null) {
			RptDiet diet = new RptDiet();
			diet.setRptId(baseInfo.getRptId());
			diet.setCreateTime(new Date());
			diet.setClientId(clientId);

			// 获取计算热卡基数
			int cardinality = RptUtils.getCalCardinality(baseInfo.getBmi(),
					baseInfo.getPhysicalType());
			diet.setCardinality(cardinality);

			diet.setCalorieOfDay(RptUtils.getDailyCalorie(baseInfo.getHeight(),
					baseInfo.getAge(), baseInfo.getGender(), cardinality)
					+ sportCalorie);

			getQuantityOfFood(diet, baseInfo.getMedicalHistory());
			diet.setWaterOfDay(RptUtils.getDailyWater(baseInfo.getHeight()));

			diet.setType(RptDiet.TYPE_SPORT);
			rptDietDao.add(diet);
		}
	}

	/**
	 * 修改运动膳食数据
	 */
	public void updateSportDietData(Integer unSportDietId, RptDiet sdiet) {
		RptDiet unSportDiet = rptDietDao.load(unSportDietId); // 获取非运动膳食部分，并取得消耗的热卡
		RptDiet sportDiet = rptDietDao.load(sdiet.getId()); // 获取运动部分膳食，以便修改相应的数据
		RptBaseInfo base = rptBaseInfoService.findRptBaseInfo(sportDiet
				.getRptId());// 获取当前用户的疾病信息

		sportDiet.setCalorieOfDay(Math.round(unSportDiet.getCalorieOfDay()
				+ sdiet.getCalorieOfDay()));
		getQuantityOfFood(sportDiet, base.getMedicalHistory());

		rptDietDao.update(sportDiet);
	}

	/**
	 * 修改计算热卡基数
	 * 
	 * @param sportDietId
	 *            运动膳食数据Id
	 * @param unSportDietId
	 *            非运动膳食数据Id
	 * @param cardinality
	 *            热卡计算基数
	 */
	public void updateDietCardinality(Integer sportDietId,
			Integer unSportDietId, int cardinality) {

		if (cardinality == 0)
			return;

		RptDiet unSportDiet = rptDietDao.load(unSportDietId); // 获取非运动膳食部分，并取得消耗的热卡

		double primaryCalorieOfDay = unSportDiet.getCalorieOfDay(); // 没有修改前的非运动每天需要消耗的热卡

		RptDiet sportDiet = rptDietDao.load(sportDietId); // 获取运动部分膳食，以便修改相应的数据
		RptBaseInfo base = rptBaseInfoService.findRptBaseInfo(sportDiet
				.getRptId());// 获取当前用户的疾病信息

		// 重新计算非运动部分的热卡和对应的的食物
		cardinality = cardinality == 0 ? RptUtils.getCalCardinality(base
				.getBmi(), base.getPhysicalType()) : cardinality;
		unSportDiet.setCardinality(cardinality);

		unSportDiet.setCalorieOfDay(RptUtils.getDailyCalorie(base.getHeight(),
				base.getAge(), base.getGender(), cardinality));

		getQuantityOfFood(unSportDiet, base.getMedicalHistory());

		// 重新计算运动部分的热卡和对应的的食物

		// (运动膳食消耗热卡-非运动=运动消耗热卡） + 新非运动膳食热卡 = 新运动膳食消耗热卡
		double newCal = (sportDiet.getCalorieOfDay() - primaryCalorieOfDay)
				+ unSportDiet.getCalorieOfDay();

		sportDiet.setCalorieOfDay(Math.round(newCal));
		getQuantityOfFood(sportDiet, base.getMedicalHistory());
		sportDiet.setCardinality(cardinality);

		rptDietDao.update(unSportDiet);
		rptDietDao.update(sportDiet);
	}

	private Date[] setDateTime(String subTime) {
		Date[] myDate = new Date[4];
		Date Date1 = DateUtils.getBeforeMonth(subTime, -3);// 3个月前的今天
		Date Date2 = DateUtils.getBeforeMonth(subTime, -2);// 2个月以前的今天
		Date Date3 = DateUtils.getBeforeMonth(subTime, -1);// 1个月以前的今天
		Date Date4 = DateUtils.getBeforeMonth(subTime, 0);// 今天的日期
		myDate[0] = Date1;
		myDate[1] = Date2;
		myDate[2] = Date3;
		myDate[3] = Date4;
		return myDate;
	}

	/**
	 * 保存运动部分数据
	 * 
	 * @param spreturn
	 *            运动计划的返回值对象，因为要用到部分数据进行运算。
	 */
	private RptSport saveSportData(Integer clientId, RptBaseInfo baseInfo,
			Date[] myDate, SportPlanReturn spreturn) {

		RptSport sport = new RptSport();
		sport.setClientId(clientId);
		sport.setRptId(baseInfo.getRptId());
		sport.setOpenings(getUserOpenings(baseInfo));

		SportDisease sportDisease = querySportDisease(baseInfo);

		sport.setSportType(getUserSportType(sportDisease));
		sport.setSportIntensity("心率控制在"
				+ RptUtils.getUserHeartRate(baseInfo.getAge(), baseInfo
						.getHeartReatAverage(), RptUtils.MIN_HEART_REATE)
				+ "~"
				+ RptUtils.getUserHeartRate(baseInfo.getAge(), baseInfo
						.getHeartReatAverage(), RptUtils.BEST_HEART_RATE)
				+ "次/分。");

		if (null != sportDisease) {
			sport.setSportPlanning(sportDisease.getSportPlanning());
			sport.setSportTimeSlot(sportDisease.getSportTimeSlot());
			sport.setSportFrequency(sportDisease.getSportFrequency());
			sport.setSportTime(sportDisease.getSportTime());
		} else {// 没有匹配上默认添加
			sport.setSportPlanning("持续性或周期性运动，每次40分钟；非周期性或间歇性运动，每天应达到40-60分钟。");
			sport.setSportTimeSlot("避免在清晨运动，建议在上午9：00后运动；空腹及进餐2小时内不建议运动！");
			sport.setSportFrequency("4-6次/周。");
			sport.setSportTime(0d);
		}
		// TODO 需要根据用户填写的运动情况判断一下用户做什么运动
		sport.setDailySuggestion("建议您坚持每日上午8： 00---9： 00选择一项喜欢的可靠的舒缓有氧运动，例如"
				+ spreturn.getBigPrescription()
				+ "！ （做好准备活动，热身以及拉伸，防止运动意外发生） 。");
		// sport.setPrescription(getUserPrescription());//选择2个小处方运动
		sport.setPrescription("每日中午或睡前，做一套" + spreturn.getSmallPrescription()
				+ " 。 ");

		sport.setSummary(getUserSummary(baseInfo));// 客户总结
		sport.setCreateTime(new Date());

		return rptSportDao.add(sport);
	}

	/**
	 * 保存运动课程表
	 * 
	 * @param 返回每天的运动每分钟能消耗多少热卡
	 */
	private SportPlanReturn saveSportPlan(Integer clientId, RptBaseInfo base) {
		SportPrescription sp = new SportPrescription();
		sp.setType(SportPrescription.TYPE_BIG_PRESCRIPTION);
		List<SportPrescription> bigList = prescriptionService
				.queryPrescript(sp);
		sp.setType(SportPrescription.TYPE_SMALL_PRESCRIPTION);
		List<SportPrescription> smallList = prescriptionService
				.queryPrescript(sp);

		SportPrescription bigPre = getPrescription(bigList);
		// SportPrescription bigPre2 = getPrescription(bigList);

		SportPlan plan = new SportPlan();
		JSONArray ja = null;

		List<String> smallPreList = new ArrayList<String>();// 存放小处方集合

		for (int i = 0; i < 7; i++) {

			SportPrescription smallPre = getPrescription(smallList);
			SportPrescription smallPre2 = getPrescription(smallList);

			ja = new JSONArray();

			JSONObject shangwu = new JSONObject();
			shangwu.put("time", "shangwu");
			shangwu.put("item", null == bigPre ? "" : bigPre.getName());
			shangwu.put("pic", null == bigPre ? "" : bigPre.getImage());
			ja.add(shangwu);

			JSONObject zhongwu = new JSONObject();
			zhongwu.put("time", "zhongwu");
			zhongwu.put("item", null == smallPre ? "" : smallPre.getName());
			zhongwu.put("pic", null == smallPre ? "" : smallPre.getImage());
			ja.add(zhongwu);

			JSONObject xiawu = new JSONObject();
			xiawu.put("time", "xiawu");
			xiawu.put("item", "");
			xiawu.put("pic", "");
			ja.add(xiawu);

			JSONObject wanshang = new JSONObject();
			wanshang.put("time", "wanshang");
			wanshang.put("item", null == smallPre2 ? "" : smallPre2.getName());
			wanshang.put("pic", null == smallPre2 ? "" : smallPre2.getImage());
			ja.add(wanshang);

			if (i == 0) {
				plan.setMonday(ja.toString());
			} else if (i == 1) {
				plan.setTuesday(ja.toString());
			} else if (i == 2) {
				plan.setWednesday(ja.toString());
			} else if (i == 3) {
				plan.setThursday(ja.toString());
			} else if (i == 4) {
				plan.setFriday(ja.toString());
			} else if (i == 5) {
				plan.setSaturday(ja.toString());
			} else if (i == 6) {
				plan.setSunday(ja.toString());
			}

			smallPreList.add(null == smallPre ? "" : smallPre.getName());
			smallPreList.add(null == smallPre2 ? "" : smallPre2.getName());
		}

		plan.setClientId(clientId);
		plan.setRptId(base.getRptId());
		plan.setCreateTime(new Date());
		plan.setEndTime(DateUtils.getBeforeMonth(DateUtils.format(new Date()),
				3)); // 截止时间为当前时间后3个月
		plan = planService.addSportPlan(plan);

		Double total = 0d;
		Double cal1 = 0d;

		if (null != bigPre && (cal1 = bigPre.getCalories()) != null) {
			total += cal1;
		}

		ListUtils.removeDuplicate(smallPreList); // 去除重复数据

		SportPlanReturn spr = new SportPlanReturn();
		spr.setBigPrescription(null != bigPre ? bigPre.getName() : "");
		spr.setSmallPrescription(ArrayUtils.join(smallPreList.toArray(), "、"));
		spr.setConsume(total);
		return spr;
	}

	/**
	 * 随机获取处方中的某一条数据
	 */
	private SportPrescription getPrescription(List<SportPrescription> list) {
		if (!CollectionUtils.isEmpty(list)) {
			int index = RandomUtils.getRandomIndex(list.size());
			return list.get(index);
		}
		return null;
	}

	/**
	 * 运动部分-用户总结
	 */
	private String getUserSummary(RptBaseInfo base) {
		if (null != base) {
			return "顾客平均心率为"
					+ StringUtils.null2str(base.getHeartReatAverage(), "")
					+ "次/分，近阶段平均血压为"
					+ StringUtils.null2str(base.getBloodPressureAverage(),"")
					+ "mmHg，血氧饱和度为"
					+ StringUtils.null2str(base.getOxygenAverage(),"")
					+ "%。建议在心率监视下做步行锻炼，运动最适心率为"
					+ StringUtils.null2str(RptUtils.getUserHeartRate(base.getAge(), base
							.getHeartReatAverage(), RptUtils.BEST_HEART_RATE),"")
					+ "次/分，"
					+ "但是有老年疾病最适心率建议170—年龄，即为"
					+ StringUtils.null2str(RptUtils.getUserHeartRate(base.getAge(), base
							.getHeartReatAverage(), RptUtils.TARGET_HEART_RATE),"")
					+ "次/分左右（做好准备活动，热身以及拉伸，防止运动意外发生）";
		}
		return "";
	}

	/**
	 * 获取运动类型
	 */
	private String getUserSportType(SportDisease disease) {
		StringBuffer sb = new StringBuffer("以易坚持的有氧代谢运动为原则，避免在运动中做推、拉、"
				+ "举之类的静力性力量练习或憋气练习。选择全身性的、有节奏的、容易放松、便于全面监测的运动项目。");

		if (null != disease && !StringUtils.isEmpty(disease.getSportType())) {
			List<SportPrescription> spList = prescriptionService
					.queryPrescript(null, disease.getSportType());
			if (!CollectionUtils.isEmpty(spList)) {
				List<String> nameList = ListUtils.getFiledList(spList, "name");
				String msg = ArrayUtils.join(nameList.toArray());
				sb.append("如" + msg + "等");
			}
		} else {
			sb
					.append("如散步、快走、慢跑、太极拳/剑/扇/球，游泳、跳舞、健身操、柔力球、踏车、低强度的球类如健身球、健身操、木球、气功、垂钓、五禽戏、易筋经、爬行、倒步走、广场舞。");
		}
		return sb.toString();
	}

	/**
	 * 根据疾病、年龄信息查询用户运动适合运动
	 */
	private SportDisease querySportDisease(RptBaseInfo base) {
		SportDisease sd = new SportDisease();
		sd.getAgeBracketByAge(base.getAge());
		sd.setDiseaseName(base.getMedicalHistory());
		SportDisease disease = sportDiseaseService.querySportDiseaseForRpt(sd);
		return disease;
	}

	/**
	 * 获取用户开篇时总结
	 */
	private String getUserOpenings(RptBaseInfo base) {
		StringBuffer sb = new StringBuffer();
		if (null != base && !StringUtils.isEmpty(base.getName())) {
			sb.append(base.getName());
		}
		// 何先生76岁，血压平均值142/73mmHg，心率平均值为73次/Min
		// ,每天步行60分钟，静坐时间每天4小时左右，高血压患者，以下是健康安全的运动建议：
		if (base.getGender() != null) {
			if (0 == base.getGender()) {// 0:男 1:女
				sb.append("先生");
			} else if (1 == base.getGender()) {
				sb.append("女士");
			} else {
				sb.append("先生/女士");
			}
		}
		if (null != base.getAge()) {
			sb.append(base.getAge() + "岁");
		}
		sb.append("，");
		// 血压平均值
		if (!StringUtils.isEmpty(base.getBloodPressureAverage())) {
			sb.append("血压平均值" + base.getBloodPressureAverage() + "mmHg，");
		}
		if (!StringUtils.isEmpty(base.getHeartReatAverage())) {
			sb.append("心率平均值为" + base.getHeartReatAverage() + "次/min，");
		}
		if (!StringUtils.isEmpty(base.getMedicalHistory())) {
			sb.append("患有" + base.getMedicalHistory() + "等疾病，");
		}
		if (!StringUtils.isEmpty(getBMITag(base))) {
			sb.append("体质质量指数BMI为" + base.getBmi() + "kg/㎡ ，为"
					+ getBMITag(base) + "，");
		}
		sb.append("以下是健康安全的运动建议：");

		return sb.toString();
	}

	/**
	 * 膳食部分
	 * 
	 * @param clientId
	 * @param baseInfo
	 */
	private RptDiet saveDietData(Integer clientId, RptBaseInfo baseInfo) {
		RptDiet diet = new RptDiet();
		diet.setRptId(baseInfo.getRptId());
		diet.setCreateTime(new Date());
		diet.setClientId(clientId);
//		diet
//				.setDietPrinciple("饮食宜清谈，油20g/天，盐5g/天；每天注意生熟搭配、颜色搭配根茎叶类搭配、荤素搭配、粗细搭配、干稀搭配，注意均衡饮食。");
		
//		diet.setUnsuitable(getRptFoods(null, baseInfo.getMedicalHistory(),
//				RptFood.EATING_LESS)
//				+ "等");
		
		String disease = baseInfo.getMedicalHistory();
		List<SrptDietPrinciple> lstPrinciple = dietPrincipleService.queryComplexDisease(disease);
		if(!CollectionUtils.isEmpty(lstPrinciple)){
			List<String> lstMore = new ArrayList<String>();
			List<String> lstLess = new ArrayList<String>();
			for (SrptDietPrinciple srptDietPrinciple : lstPrinciple) {
				lstMore.add(srptDietPrinciple.getEatingMore());
				lstLess.add(srptDietPrinciple.getEatingLess());
			}
			if(!CollectionUtils.isEmpty(lstMore)){
				diet.setDietPrinciple(ArrayUtils.join(lstMore.toArray(), ""));
			}
			if(!CollectionUtils.isEmpty(lstLess)){
				diet.setUnsuitable(ArrayUtils.join(lstLess.toArray(), ""));
			}
		}

		// 获取计算热卡基数
		int cardinality = RptUtils.getCalCardinality(baseInfo.getBmi(),
				baseInfo.getPhysicalType());
		diet.setCardinality(cardinality);

		diet.setCalorieOfDay(RptUtils.getDailyCalorie(baseInfo.getHeight(),
				baseInfo.getAge(), baseInfo.getGender(), cardinality));

		getQuantityOfFood(diet, baseInfo.getMedicalHistory());
		diet.setWaterOfDay(RptUtils.getDailyWater(baseInfo.getHeight()));

		// 一天参考食谱
		getNutritiousMeals(diet, baseInfo.getMedicalHistory());

		// 药膳
		diet.setYaoshan("");
		diet.setYaoshangongneng("");
		// 疾病不适合吃的东西
//		diet.setUnsuitable(getRptFoods(null, baseInfo.getMedicalHistory(),
//				RptFood.EATING_LESS)
//				+ "等");
		diet.setType(RptDiet.TYPE_UNSPORT);
		return rptDietDao.add(diet);
	}

	/**
	 * 一天参考食谱
	 */
	private void getNutritiousMeals(RptDiet diet, String medical) {
		RptNutritiousMeals rnm = mealsService.findNutritiousByDisease(null,
				medical);
		if (rnm != null) {
			diet.setBreakfast(rnm.getBreakfast());
			diet.setJiancan(rnm.getZaojia());
			diet.setLunch(rnm.getLunch());
			diet.setJiacan(rnm.getWujia());
			diet.setDinner(rnm.getDinner());
			diet.setShuiqianjiacan(rnm.getWanjia());
		}
	}

	/**
	 * 获取所需食物份量
	 * 
	 * @param medical
	 *            疾病史
	 */
	private void getQuantityOfFood(RptDiet diet, String medical) {
		if (null != diet && diet.getCalorieOfDay() > 0) {
			// 热卡计算系数，正常时为2.5，包含疾病史为2 time:2014.03.31 调整了对疾病的判断，之前该值为2
			double dcal = StringUtils.isEmpty(medical) ? 2.5 : 2;

			double cal = diet.getCalorieOfDay();

			diet.setVegetableOfDay(Math
					.round(((float) cal / 90 - dcal) * 0.07 * 500));// 每天蔬菜
			diet.setGuleiOfDay(Math
					.round(((float) cal / 90 - dcal) * 0.54 * 25)); // 谷类
			diet.setFruitOfDay(Math
					.round(((float) cal / 90 - dcal) * 0.05 * 200)); // 水果
			diet.setRoudanOfDay(Math
					.round(((float) cal / 90 - dcal) * 0.15 * 50)); // 肉蛋
			diet.setMilkOfDay(Math
					.round(((float) cal / 90 - dcal) * 0.09 * 160)); // 牛奶
			diet
					.setBeanOfDay(Math
							.round(((float) cal / 90 - dcal) * 0.06 * 25)); // 豆类
			diet.setNutOfDay(Math.round(((float) cal / 90 - dcal) * 0.04 * 25)); // 坚果
			diet.setOilOfDay(20);

			// }

			int season = DateUtils.getSeasonInt(new Date());

			RptFood food = null;

			/*
			 * 计算三个月的饮食安排
			 */

			// 早餐
			diet.setGulei7(Math.round(diet.getGuleiOfDay() / 10 * 3));
			food = new RptFood();
			food.setBreakfast(1);
			food.setType(RptFood.TYPE_GULEI);
			getSeason(season, food);
			diet.setGulei7Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL)
					+ "等杂粮");

			// 早餐蔬菜
			diet.setVegetable7(Math.round(diet.getVegetableOfDay() / 10 * 3));
			food = new RptFood();
			food.setBreakfast(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_SHUCAI);
			diet.setVegetable7Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL)
					+ "等任选2种或以上，少许盐，橄榄油或亚麻油，苹果汁或柠檬汁少许。");
			diet.setEgg7(50);
			diet.setEgg7Detail("每天一个水煮鸡蛋");

			// 早加水果
			diet.setFruit10(Math.round(diet.getFruitOfDay() * 0.5));
			food = new RptFood();
			food.setZaojia(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_FRUIT);
			diet.setFruit10Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL));

			diet.setMilk10(Math.round(diet.getMilkOfDay() * 0.4));
			food = new RptFood();
			food.setZaojia(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_MILK);
			diet.setMilk10Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL));

			diet.setGulei12(Math.round((float) diet.getGuleiOfDay() / 10 * 4));
			food = new RptFood();
			food.setLunch(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_GULEI);
			diet.setGulei12Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL));

			diet.setVegetable12(Math
					.round((float) diet.getVegetableOfDay() / 10 * 4));
			food = new RptFood();
			food.setLunch(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_SHUCAI);
			diet.setVegetable12Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL)
					+ "等任选4种以上");
			diet
					.setRoudan12(Math.round(diet.getRoudanOfDay()
							- diet.getEgg7()));

			food = new RptFood();
			food.setLunch(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_ROUDAN);
			diet.setRoudan12Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL)
					+ "等任选（以清蒸或炖为主）");

			diet.setFruit15(Math.round(diet.getFruitOfDay() * 0.5));
			food = new RptFood();
			food.setWujia(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_FRUIT);
			diet.setFruit15Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL));

			diet.setGulei18(Math.round((float) diet.getGuleiOfDay() / 10 * 3));
			food = new RptFood();
			food.setDinner(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_GULEI);
			diet.setGulei18Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL)
					+ "等");

			diet.setVegetable18(Math
					.round((float) diet.getVegetableOfDay() / 10 * 3));
			food = new RptFood();
			food.setDinner(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_SHUCAI);
			diet.setVegetable18Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL)
					+ "等绿叶菜任选2种或以上");

			diet.setBean18(Math.round(diet.getBeanOfDay()));
			food = new RptFood();
			food.setDinner(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_BEAN);
			diet.setBean18Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL));

			diet.setMilk19(Math.round(diet.getMilkOfDay() * 0.6));
			food = new RptFood();
			food.setWanjia(1);
			getSeason(season, food);
			food.setType(RptFood.TYPE_MILK);
			diet.setMilk19Detail(getRptFoods(food, medical,
					RptFood.EATING_NORMAL));

			food = null; // 销毁对象
		}
	}

	private void getSeason(int season, RptFood food) {
		if (DateUtils.SEASON_SPRING == season) {
			food.setSpring(1);
		} else if (DateUtils.SEASON_SUMMER == season) {
			food.setSummer(1);
		} else if (DateUtils.SEASON_AUTUMN == season) {
			food.setAutumn(1);
		} else if (DateUtils.SEASON_WINTER == season) {
			food.setWinter(1);
		}
	}

	/**
	 * 根据条件查询对应的食物
	 */
	private String getRptFoods(RptFood food, String medical, int eatMoreOrless) {
		// (早餐/中餐/晚餐, 主食/蔬菜/肉蛋/, String medical, eatMoreOrless)
		return foodService.queryRptFood(food, medical, eatMoreOrless);
	}

	/**
	 * 修改预约报表状态为已生成报表
	 * 
	 * @param rpts
	 */
	private void changeSubscribeStatus(RptSubscribe rpts) {
		if (RptSubscribe.FLAG_UNGENERATE == rpts.getFlag()) {
			rpts.setFlag(RptSubscribe.FLAG_GENERATED);
			rpts.setGenerateTime(new Date());
			rptSubscribeDao.update(rpts);
		}
	}

	/**
	 * 获取所有需要生成报告的用户信息
	 */
	private List<RptSubscribe> getClients(String subscribeTime) {
		return rptSubscribeDao.queryClientsByTime(subscribeTime);
	}

	/**
	 * 获取基本信息，并且保存到数据库
	 * 
	 * @throws ParseException
	 */
	private RptBaseInfo saveBaseInfo(Integer clientId, String subTime) {
		return rptBaseInfoService.addRptBaseInfoByClientId(clientId, subTime);
	}

	/**
	 * 保存血压数据
	 */
	private void savePressureData(Integer clientId, RptBaseInfo rbi,
			String subTime, Date[] myDate) {
		RptMonitoringData rmd = new RptMonitoringData();
		rmd.setClientId(clientId);
		rmd.setCreateTime(new Date());
		rmd.setStatus(RptMonitoringData.STATUS_AUTO);
		rmd.setType(RptMonitoringData.TYPE_PRESSURE);
		rmd.setRptId(rbi.getRptId());
		rmd.setConclusion(getPressureConclusion(clientId, subTime, rbi));
		rmd.setHazards(getPressureHazards(rbi));
		rmd.setPic1(getPressureData(clientId, DateUtils.getBeforeMonth(subTime,
				-3), DateUtils.getBeforeMonth(subTime, -2)));
		rmd.setPic2(getPressureData(clientId, DateUtils.getBeforeMonth(subTime,
				-2), DateUtils.getBeforeMonth(subTime, -1)));
		rmd.setPic3(getPressureData(clientId, DateUtils.getBeforeMonth(subTime,
				-1), DateUtils.getBeforeMonth(subTime, 0)));
		rmd.setDefaultPic("1");// 将3个月中第一个月设置为默认显示月

		JSONObject jo = getPressureStatistics(clientId, myDate);

		rmd.setStatistics(jo.toString());

		monitoringDao.add(rmd);
	}

	/**
	 * 获取血压统计数据
	 */
	private JSONObject getPressureStatistics(Integer clientId, Date[] myDate) {
		BloodPressure bloodPressure = new BloodPressure();
		bloodPressure.setClientId(clientId);
		QueryInfo queryInfo = new QueryInfo(1000, null, null, null);
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setBeginTime(myDate[0]);
		queryCondition.setEndTime(myDate[3]);

		PageObject page = monitoringDataService.getListByBloodPressure(
				bloodPressure, queryInfo, queryCondition);
		JSONObject jo = new JSONObject();
		// 统计正常和异常次数
		monitoringDataService.disposeBloodPressureList(page.getDatas(), jo);
		// 统计最大值 和最小值
		monitoringDataService.disposeRptBloodPressure(page.getDatas(), jo);
		return jo;
	}

	/**
	 * 获取血压的危险因素
	 * 
	 * @return
	 */
	private String getPressureHazards(RptBaseInfo rbi) {
		if (null != rbi) {
			List<String> tags = new ArrayList<String>();
			// BMI
			String bmiTag = getBMITag(rbi);
			if (!StringUtils.isEmpty(bmiTag)&&!bmiTag.equals("正常")) {
				tags.add(bmiTag);
			}

			// 缺乏锻炼
			if (null != rbi.getSportCount()) {
				if (rbi.getSportCount() == 4 && rbi.getSportTime() > 1) { // 每天运动30分钟，每周5天，为适量运动
					tags.add("适量运动");
				} else {
					tags.add("缺乏锻炼");
				}
			}

			// 饮酒
			if (!StringUtils.isEmpty(rbi.getWhite())
					|| !StringUtils.isEmpty(rbi.getBeer())
					|| !StringUtils.isEmpty(rbi.getRed())) {
				float white = 0f;
				float beer = 0f;
				float red = 0f;

				try {
					white = Float.parseFloat(rbi.getWhite());
				} catch (NumberFormatException e) {

				}

				try {
					beer = Float.parseFloat(rbi.getBeer());
				} catch (NumberFormatException e) {

				}

				try {
					red = Float.parseFloat(rbi.getRed());
				} catch (NumberFormatException e) {

				}

				if (white > 1 || beer > 750 || red > 100) {
					tags.add("饮酒");
				}
			}

			// 吸烟
			if (null != rbi.getSmoke()) {
				if (1 == rbi.getSmoke()) {// 吸烟状况1：是 2:否
					tags.add("吸烟");
				}
			}

			// 精神紧张
			if (rbi.getWorking() == 1) {
				tags.add("精神紧张");
			}

			String tag = ArrayUtils.join(tags.toArray());

			return getHazardsByKeywords(tag, Constant.RPT_NODE_PRESSURE_HAZARDS);

		}
		return "";
	}

	/**
	 * 根据bmi获取相应的tags
	 * 
	 * @param rbi
	 * @param tags
	 */
	private String getBMITag(RptBaseInfo rbi) {
		return RptUtils.getBMITag(rbi.getBmi());
	}

	/**
	 * 获取血压结论
	 * 
	 * @return
	 */
	private String getPressureConclusion(Integer clientId, String subTime,
			RptBaseInfo rbi) {
		BloodPressure bp = new BloodPressure();
		bp.setClientId(clientId);

		QueryCondition qc = new QueryCondition();
		qc.setBeginTime(DateUtils.getBeforeMonth(subTime, -3));
		qc.setEndTime(DateUtils.getBeforeMonth(subTime, 0));

		Object obj = monitoringService.queryLimitPressure(qc, bp);
		Object[] oarray = (Object[]) obj;

		StringBuffer ccsb = new StringBuffer();
		if (null != rbi && !StringUtils.isEmpty(rbi.getMedicalHistory())) {
			ccsb.append("您有" + rbi.getMedicalHistory() + "病史。");
		}

		if (oarray.length == 4) {
			if (null != oarray[0] && null != oarray[1] && null != oarray[2]
					&& null != oarray[3]) {
				Integer v0 = Integer.parseInt(oarray[0] + ""); // 高压高值
				Integer v1 = Integer.parseInt(oarray[1] + ""); // 高压低值
				Integer v2 = Integer.parseInt(oarray[2] + ""); // 低压高值
				Integer v3 = Integer.parseInt(oarray[3] + ""); // 低压低值

				ccsb.append("从您三个月来动态血压监测的情况看 ，血压波动在" + v1 + "-" + v0 + "/"
						+ v3 + "-" + v2 + "mmHg");

				if (v1 >= 90 && v0 <= 150 && v3 >= 60 && v2 <= 90) {
					ccsb.append("，控制满意");
				} else {
					ccsb.append("，控制不满意");
				}

				ccsb.append("。");
			}
		}

		return ccsb.toString();
	}

	/**
	 * 获取血压数据
	 */
	private String getPressureData(Integer clientId, Date beginTime,
			Date endTime) {
		BloodPressure bp = new BloodPressure();
		bp.setClientId(clientId);

		QueryInfo queryInfo = new QueryInfo(999999, null, null, null);
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setBeginTime(beginTime);
		queryCondition.setEndTime(endTime);
		return monitoringService.getBloodPressureChart(bp, queryInfo,
				queryCondition, 0);
	}

	/**
	 * 保存血糖数据
	 */
	private void saveSugarData(Integer clientId, RptBaseInfo rbi,
			String subTime, Date[] myDate) {
		RptMonitoringData rmd = new RptMonitoringData();
		rmd.setClientId(clientId);
		rmd.setCreateTime(new Date());
		rmd.setStatus(RptMonitoringData.STATUS_AUTO);
		rmd.setType(RptMonitoringData.TYPE_SUGAR);
		rmd.setRptId(rbi.getRptId());

		BloodSugar bs = new BloodSugar();
		bs.setClientId(clientId);
		// 1：空腹血糖 2：餐后两小时血糖
		bs.setBloodSugarType(1);

		// 获取血糖结论
		rmd.setConclusion(getSugerConclusion(bs, clientId, subTime, rbi));
		rmd.setHazards(getSugarDataHazards(bs, rbi));
		rmd.setPic1(getSugarData(bs, clientId, DateUtils.getBeforeMonth(
				subTime, -3), DateUtils.getBeforeMonth(subTime, -2)));
		rmd.setPic2(getSugarData(bs, clientId, DateUtils.getBeforeMonth(
				subTime, -2), DateUtils.getBeforeMonth(subTime, -1)));
		rmd.setPic3(getSugarData(bs, clientId, DateUtils.getBeforeMonth(
				subTime, -1), DateUtils.getBeforeMonth(subTime, 0)));
		rmd.setDefaultPic("1");// 将3个月中第一个月设置为默认显示月

		JSONObject jo = getBloodSugarStatistics(clientId, myDate, 1);
		rmd.setStatistics(jo.toString());

		monitoringDao.add(rmd);
	}

	/**
	 * 获取血糖的危险因素
	 * 
	 * @return
	 */
	private String getSugarDataHazards(BloodSugar bs, RptBaseInfo rbi) {
		if (null != rbi) {
			List<String> tags = new ArrayList<String>();
			// BMI
			String bmiTag = getBMITag(rbi);
			if (!StringUtils.isEmpty(bmiTag)) {
				tags.add(bmiTag);
			}

			// 缺乏锻炼
			if (null != rbi.getSportCount()) {
				if (rbi.getSportCount() >= 5 && rbi.getSportTime() > 1) { // 每天运动30分钟，每周5天，为适量运动
					tags.add("适量运动");
				} else {
					tags.add("缺乏锻炼");
				}
			}

			// 精神紧张
			if (rbi.getWorking() == 1) {
				tags.add("精神紧张");
			}

			if (bs.getBloodSugarType() == 1) {
				return getHazardsByKeywords(ArrayUtils
						.join(tags.toArray(), ","),
						Constant.RPT_NODE_SUGAR_HAZARDS);
			}

			if (bs.getBloodSugarType() == 2) {
				return getHazardsByKeywords(ArrayUtils
						.join(tags.toArray(), ","),
						Constant.RPT_NODE_SUGAR2_HAZARDS);
			}

		}
		return "";
	}

	/**
	 * 获取血糖结论
	 * 
	 * @return
	 */
	private String getSugerConclusion(BloodSugar bs, Integer clientId,
			String subTime, RptBaseInfo rbi) {
		QueryCondition qc = new QueryCondition();
		qc.setBeginTime(DateUtils.getBeforeMonth(subTime, -3));
		qc.setEndTime(DateUtils.getBeforeMonth(subTime, 0));
		Object obj = monitoringService.queryLimiteSugar(qc, bs);
		StringBuffer ccsb = new StringBuffer();
		Object[] oarray = (Object[]) obj;
		if (oarray.length == 2) {
			if (null != oarray[0] && null != oarray[1]) {
				Double sugarH = Double.parseDouble(oarray[0].toString()); // 血糖大值
				Double sugarL = Double.parseDouble(oarray[1].toString()); // 血糖低值
				if (bs.getBloodSugarType() == 1) {
					ccsb.append("三个月来您的空腹血糖动态监测值在" + sugarL + "-" + sugarH
							+ "mmol/L");
				}
				if (bs.getBloodSugarType() == 2) {
					ccsb.append("三个月来您的餐后两小时血糖动态监测值在" + sugarL + "-" + sugarH
							+ "mmol/L");
				}
				if (sugarL > 3.9 && sugarL < 6.1 && sugarH > 3.9
						&& sugarH < 6.1) {
					ccsb.append("，控制满意");
				} else {
					ccsb.append("，控制不满意");
				}
				ccsb.append("。");
			} else {
				ccsb.append("您近三个月没有测量");
			}
		}
		return ccsb.toString();
	}

	/**
	 * 获取血糖数据
	 */
	private String getSugarData(BloodSugar bs, Integer clientId,
			Date beginTime, Date endTime) {
		QueryInfo queryInfo = new QueryInfo(999999, null, null, null);
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setBeginTime(beginTime);
		queryCondition.setEndTime(endTime);
		return monitoringService.getBloodSugarChart(bs, queryInfo,
				queryCondition, 0);
	}

	/**
	 * 保存餐后两小时血糖
	 * 
	 * @param myDate
	 */
	private void saveSugar2hData(Integer clientId, RptBaseInfo rbi,
			String subTime, Date[] myDate) {
		RptMonitoringData rmd = new RptMonitoringData();
		rmd.setClientId(clientId);
		rmd.setCreateTime(new Date());
		rmd.setStatus(RptMonitoringData.STATUS_AUTO);
		rmd.setType(RptMonitoringData.TYPE_SUGAR2H);
		rmd.setRptId(rbi.getRptId());

		BloodSugar bs = new BloodSugar();
		bs.setClientId(clientId);
		// 1：空腹血糖 2：餐后两小时血糖
		bs.setBloodSugarType(2);
		// 获取血糖结论
		rmd.setConclusion(getSugerConclusion(bs, clientId, subTime, rbi));
		rmd.setHazards(getSugarDataHazards(bs, rbi));
		rmd.setPic1(getSugarData(bs, clientId, DateUtils.getBeforeMonth(
				subTime, -3), DateUtils.getBeforeMonth(subTime, -2)));
		rmd.setPic2(getSugarData(bs, clientId, DateUtils.getBeforeMonth(
				subTime, -2), DateUtils.getBeforeMonth(subTime, -1)));
		rmd.setPic3(getSugarData(bs, clientId, DateUtils.getBeforeMonth(
				subTime, -1), DateUtils.getBeforeMonth(subTime, 0)));
		rmd.setDefaultPic("1");// 将3个月中第一个月设置为默认显示月

		JSONObject jo = getBloodSugarStatistics(clientId, myDate, 2);
		rmd.setStatistics(jo.toString());

		monitoringDao.add(rmd);
	}

	/**
	 * 获取血糖统计部分数据
	 */
	private JSONObject getBloodSugarStatistics(Integer clientId, Date[] myDate,
			int type) {
		JSONObject jo = new JSONObject();
		BloodSugar bloodSugar = new BloodSugar();
		bloodSugar.setClientId(clientId);
		bloodSugar.setBloodSugarType(type); // 1：空腹血糖 2：餐后两小时血糖

		QueryInfo queryInfo = new QueryInfo(1000, null, null, null);
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setBeginTime(myDate[0]);
		queryCondition.setEndTime(myDate[3]);

		PageObject page = monitoringDataService.getListByBloodSugar(bloodSugar,
				queryInfo, queryCondition);
		monitoringDataService.disposeBloodSugarList(page.getDatas(), jo);
		monitoringDataService.disposeRptBloodSugarList(page.getDatas(), jo);
		return jo;
	}

	/**
	 * 保存血氧数据
	 * 
	 * @param myDate
	 */
	private void saveSpO2Data(Integer clientId, RptBaseInfo rbi,
			String subTime, Date[] myDate) {
		RptMonitoringData rmd = new RptMonitoringData();
		rmd.setClientId(clientId);
		rmd.setCreateTime(new Date());
		rmd.setStatus(RptMonitoringData.STATUS_AUTO);
		rmd.setType(RptMonitoringData.TYPE_SPO2);
		rmd.setRptId(rbi.getRptId());
		rmd.setConclusion(getSpO2Conclusion(clientId, subTime, rbi));
		rmd.setPic1(getSpo2Data(clientId,
				DateUtils.getBeforeMonth(subTime, -3), DateUtils
						.getBeforeMonth(subTime, -2)));
		rmd.setPic2(getSpo2Data(clientId,
				DateUtils.getBeforeMonth(subTime, -2), DateUtils
						.getBeforeMonth(subTime, -1)));
		rmd.setPic3(getSpo2Data(clientId,
				DateUtils.getBeforeMonth(subTime, -1), DateUtils
						.getBeforeMonth(subTime, 0)));
		rmd.setDefaultPic("1");// 将3个月中第一个月设置为默认显示月

		JSONObject jo = getOxygenStatistic(clientId, myDate);
		rmd.setStatistics(jo.toString());

		monitoringDao.add(rmd);
	}

	/**
	 * 获取血氧统计信息
	 */
	private JSONObject getOxygenStatistic(Integer clientId, Date[] myDate) {
		JSONObject jo = new JSONObject();
		BloodOxygen bloodOxygen = new BloodOxygen();
		bloodOxygen.setClientId(clientId);

		QueryInfo queryInfo = new QueryInfo(1000, null, null, null);
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setBeginTime(myDate[0]);
		queryCondition.setEndTime(myDate[3]);

		PageObject page = monitoringDataService.getListByBloodOxygen(
				bloodOxygen, queryInfo, queryCondition);
		monitoringDataService.disposeBloodOxygenList(page.getDatas(), jo);
		monitoringDataService.disposeRptBloodOxygenList(page.getDatas(), jo);
		return jo;
	}

	/**
	 * 血氧结论
	 */
	private String getSpO2Conclusion(Integer clientId, String subTime,
			RptBaseInfo rbi) {
		BloodOxygen bp = new BloodOxygen();
		bp.setClientId(clientId);

		QueryCondition qc = new QueryCondition();
		qc.setBeginTime(DateUtils.getBeforeMonth(subTime, -3));
		qc.setEndTime(DateUtils.getBeforeMonth(subTime, 0));

		Object obj = monitoringService.queryLimiteSpO2(qc, bp);
		Object[] oarray = (Object[]) obj;

		if (oarray.length == 4) {
			if (null != oarray[0] && null != oarray[1] && null != oarray[2]
					&& null != oarray[3]) {
				String msg = "三个月来您的血氧饱和度波动在{0}%之间，{1} 。脉率波动在{2}次/分之间，{3}。";
				Integer v0 = Integer.parseInt(oarray[0] + ""); // max(bloodOxygen)
				Integer v1 = Integer.parseInt(oarray[1] + ""); // min(bloodOxygen)
				Integer v2 = Integer.parseInt(oarray[2] + ""); // max(heartbeat)
				Integer v3 = Integer.parseInt(oarray[3] + ""); // min(heartbeat)

				// 血氧饱和度
				String tip1 = "";
				if (v1 >= 95 && v0 <= 100) {
					System.out.println("血氧饱和度正常");
					tip1 = "属正常范围";
				} else {
					tip1 = "有异常数据";
				}

				// 脉动
				String tip2 = "";
				if (v3 >= 60 && v2 <= 100) {
					tip2 = "属正常范围";
				} else {
					tip2 = "有异常数据";
				}

				return MessageFormat.format(msg, new Object[] {
						(v1 + "-" + v0), tip1, (v3 + "-" + v2), tip2 });
			}
		}
		return "";
	}

	/**
	 * 获取血氧数据
	 */
	private String getSpo2Data(Integer clientId, Date beginTime, Date endTime) {
		BloodOxygen bloodOxygen = new BloodOxygen();
		bloodOxygen.setClientId(clientId);

		QueryInfo queryInfo = new QueryInfo(999999, null, null, null);
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setBeginTime(beginTime);
		queryCondition.setEndTime(endTime);
		String josnString = monitoringService.getBloodOxygenChart(bloodOxygen,
				queryInfo, queryCondition, 0);
		return josnString;
	}

	/**
	 * 保存心电数据
	 */
	private void saveECGData(Integer clientId, RptBaseInfo rbi, Date[] myDate) {
		RptMonitoringData rmd = new RptMonitoringData();
		rmd.setClientId(clientId);
		rmd.setCreateTime(new Date());
		rmd.setStatus(RptMonitoringData.STATUS_AUTO);
		rmd.setType(RptMonitoringData.TYPE_ECG);
		rmd.setRptId(rbi.getRptId());
		rmd.setPic1(getQueryqueryElectrocardiogramData(clientId));
		rmd.setDefaultPic("pic1");

		JSONObject jo = new JSONObject();
		Electrocardiogram electrocardiogram = new Electrocardiogram();
		electrocardiogram.setClientId(clientId);

		QueryInfo queryInfo = new QueryInfo(1000, null, null, null);
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setBeginTime(myDate[0]);
		queryCondition.setEndTime(myDate[3]);

		PageObject page = monitoringService.getElectrocardiogramList(
				electrocardiogram, queryInfo, queryCondition);
		monitoringService.disposeElectrocardiogram(page.getDatas(), jo);
		rmd.setStatistics(jo.toString());

		monitoringDao.add(rmd);
	}

	/**
	 * 获取危险因素
	 * 
	 * @return
	 */
	private String getHazardsByKeywords(String tag, String node) {
		RptMaterial rm = new RptMaterial();
		rm.setNode(node);
		rm.setTag(tag);
		rm = materialService.queryMaterialByKeywords(rm);
		return null == rm ? "" : rm.getContent();
	}

	/**
	 * 获取心电数据 查询距离当前3个月的数据最后一次异常和正常的数据
	 */
	private String getQueryqueryElectrocardiogramData(Integer clientId) {
		return monitoringService.queryElectrocardiogramData(clientId);
	}

	@SuppressWarnings("unchecked")
	public String verifyClientInfoForRpt(Integer clientId, Integer rptType) {
		if (clientId != null) {
			ClientInfo clientInfo = clientInfoService.get(clientId);
			ClientHobby clientHobby = clientHobbyService
					.getClientHobby(clientId);
			ClientLatestPhy clientLatestPhy = clientLatestPhyService
					.getClientLatestPhy(clientId);
			ClientMedicalHistory history = historyService
					.getClientMedicalHistory(clientId);
			ArrayList msgList=new ArrayList();
			if (clientInfo != null && clientLatestPhy != null
					&& clientHobby != null ) {
				if (clientInfo.getAge() == null) {
					msgList.add("年龄");
				}
				if (clientInfo.getGender() == null) {
					msgList.add("性别");
				}
				if ( clientLatestPhy.getHeight() == null) {
					msgList.add("身高");
				}
				if (clientLatestPhy.getWeight() == null) {
					msgList.add("体重");
				}
				if (clientHobby.getPhysicalType() == 0) {
					msgList.add("职业劳动强度");
				}
				if (rptType != null && rptType != RptBaseInfo.RPT_TYPE && history != null) {
					if (history.getHopeSolveHealth() == null) {
						msgList.add("最希望解决的疾病");
					}
				}
			}else{
				if(clientInfo == null){
					msgList.add("用户信息");
				}
 				if(clientLatestPhy == null){
 					msgList.add("体检信息");
 				}
 				if(clientHobby == null){
 					msgList.add("用户个人习惯嗜好");
 				}
			}
			if(CollectionUtils.isEmpty(msgList)){
				return "success";
			}else{
				return ArrayUtils.join(msgList.toArray(),",");
			}
		}
		return "null";
	}

}

/**
 * 运动计划返回值
 */
class SportPlanReturn {
	/**
	 * 返回每天的运动每分钟能消耗多少热卡
	 */
	private Double consume;
	/** 大处方 */
	private String bigPrescription;
	/** 小处方(s) */
	private String smallPrescription;

	public Double getConsume() {
		return consume;
	}

	public void setConsume(Double consume) {
		this.consume = consume;
	}

	public String getBigPrescription() {
		return bigPrescription;
	}

	public void setBigPrescription(String bigPrescription) {
		this.bigPrescription = bigPrescription;
	}

	public String getSmallPrescription() {
		return smallPrescription;
	}

	public void setSmallPrescription(String smallPrescription) {
		this.smallPrescription = smallPrescription;
	}

}
