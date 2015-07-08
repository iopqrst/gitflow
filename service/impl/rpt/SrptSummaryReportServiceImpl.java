package com.bskcare.ch.service.impl.rpt;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.SrptTarget;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.BloodOxygenDao;
import com.bskcare.ch.dao.BloodPressureDao;
import com.bskcare.ch.dao.BloodSugarDao;
import com.bskcare.ch.dao.ElectrocardiogramDao;
import com.bskcare.ch.dao.rpt.SportPrescriptionDao;
import com.bskcare.ch.dao.rpt.SrptDietDao;
import com.bskcare.ch.dao.rpt.SrptDietPrincipleDao;
import com.bskcare.ch.dao.rpt.SrptDietSprescriptionDao;
import com.bskcare.ch.dao.rpt.SrptLifePrincipleDao;
import com.bskcare.ch.dao.rpt.SrptSummaryReportDao;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.service.rpt.SportDiseaseService;
import com.bskcare.ch.service.rpt.SportPrescriptionService;
import com.bskcare.ch.service.rpt.SrptBaseInfoService;
import com.bskcare.ch.service.rpt.SrptSummaryReportService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.CriteriaUtil;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.ListUtils;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.util.RptUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.BloodPressure;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.Electrocardiogram;
import com.bskcare.ch.vo.rpt.SportDisease;
import com.bskcare.ch.vo.rpt.SportPrescription;
import com.bskcare.ch.vo.rpt.SrptBaseInfo;
import com.bskcare.ch.vo.rpt.SrptDiet;
import com.bskcare.ch.vo.rpt.SrptDietPrinciple;
import com.bskcare.ch.vo.rpt.SrptDietSprescription;
import com.bskcare.ch.vo.rpt.SrptLifePrinciple;
import com.bskcare.ch.vo.rpt.SrptSummaryReport;

@Service
public class SrptSummaryReportServiceImpl implements SrptSummaryReportService{
	
	@Autowired
	SrptSummaryReportDao ssrdDao;
	@Autowired
	SrptBaseInfoService sbaseInfoService;
	@Autowired
	private SrptDietPrincipleDao dietPrincipleDao;
	@Autowired
	private SportDiseaseService sportDiseaseService;
	@Autowired
	private SrptLifePrincipleDao lifePrincipleDao;
	@Autowired
	private SportPrescriptionService prescriptionService;
	//血压
	@Autowired
	private BloodPressureDao bpDao;
	//心率
	@Autowired
	private ElectrocardiogramDao edDao;
	@Autowired
	private BloodSugarDao bsDao;
	@Autowired
	private BloodOxygenDao oxygenDao;
	@Autowired
	private SrptDietDao srptDietDao;
	@Autowired
	private SrptDietSprescriptionDao sdietDao;
	@Autowired
	private SportPrescriptionDao sportDao;
	
	public void generateReport(Integer clientId, int type) {
		// 根据传入的类型计算时间范围
		Date beginTime = cacluateBeginTime(type);
		// 保存简易基本信息
		SrptBaseInfo sbi = sbaseInfoService.createRptFullBaseInfo(clientId,type);
		sbi.setBeginTime(beginTime);
		// 保存报告的详细信息
		saveSummaryReport(sbi, beginTime);
		// 保存报告膳食量信息
		saveSrptDiet(sbi);
	}

	private void saveSrptDiet(SrptBaseInfo sbi) {
		
		//获取计算热卡基数
		if(sbi.getPhysicalType() != null){
			int cardinality = RptUtils.getCalCardinality(sbi.getBmi(), sbi.getPhysicalType());
			String diseaseSelf = sbi.getDiseaseSelf();
			double dcal = StringUtils.isEmpty(diseaseSelf) ? 2.5 : 2;
			double cal = RptUtils.getDailyCalorie(sbi.getHeight(), sbi.getAge(), 
					sbi.getGender(), cardinality);
			SrptDiet sd = new SrptDiet();
			sd.setCalorie(cal);
			sd.setClientId(sbi.getClientId());
			sd.setSrptId(sbi.getId());
			sd.setVegetables(Math.round(((float) cal / 90 - dcal) * 0.07 * 500));// 每天蔬菜
			sd.setGulei(Math.round(((float) cal / 90 - dcal) * 0.58 * 25)); // 谷类
			sd.setYudan(Math.round(((float) cal / 90 - dcal) * 0.15 * 50)); // 肉蛋
			sd.setMilk(Math.round(((float) cal / 90 - dcal) * 0.09 * 160)); // 牛奶
			sd.setBean(Math.round(((float) cal / 90 - dcal) * 0.06 * 25)); // 豆类
			sd.setFruit(Math.round(((float) cal / 90 - dcal) * 0.05 * 200)); //水果
			
			//正常时：a=25克,疾病：a=20克
			if(StringUtils.isEmpty(diseaseSelf)){
				sd.setOil(25);
			}else{
				sd.setOil(20);
			}
			
			//正常/低血压：6克以下,疾病人：5克以下
			if(StringUtils.isEmpty(diseaseSelf)||(!StringUtils.isEmpty(diseaseSelf)&&diseaseSelf.contains("低血压"))){
				sd.setSalt(6);
			}else{
				sd.setSalt(5);
			}
			
			if(!StringUtils.isEmpty(sbi.getHeight())){
				sd.setWater(RptUtils.getDailyWater(sbi.getHeight()));
			}
			srptDietDao.add(sd);
		}
	}

	private SrptSummaryReport saveSummaryReport(SrptBaseInfo sbi, Date beginTime) {
		// 保存报告其他信息
		SrptSummaryReport ssr = new SrptSummaryReport();
		SrptTarget srptTarget = new SrptTarget();
		ssr.setClientId(sbi.getClientId());
		ssr.setSrptId(sbi.getId()); //建议报告base id
		
		SrptDietPrinciple dietPrinciple = queryDietPrinciple(sbi);
		if(dietPrinciple != null){
			ssr.setPrinciple(dietPrinciple.getPrinciple());  //饮食原则
			ssr.setEatingMore(dietPrinciple.getEatingMore());// 多吃
			ssr.setEatingLess(dietPrinciple.getEatingLess());// 少吃
			ssr.setKeeping(dietPrinciple.getKeeping());  //心理原则需要保持的
			ssr.setAvoid(dietPrinciple.getAvoid());  //心理原则需要避免的
			String shortTip = queryShortTip(dietPrinciple);  //温馨提示（精简的）
			if(!StringUtils.isEmpty(shortTip)){
				ssr.setShortTip(shortTip);
			}
			String totalTip = queryTotalTip(dietPrinciple);  // 温馨提示（全）
			if(!StringUtils.isEmpty(totalTip)){
				ssr.setTotalTip(totalTip);
			}
			String small = dietPrinciple.getSmallPrescription();  // 日常原则
			if(!StringUtils.isEmpty(small)){
				String sdiet = querySDiet(small);
				ssr.setDietPrinciple(sdiet);
			}
		}
		
		String intro = getIntro(sbi); // 身体状况简介
		if(!StringUtils.isEmpty(intro)){
			ssr.setIntro(intro);
		}
		//运动部分开始
		ssr.setSportOpenings(getUserOpenings(sbi));
		//运动强度
		String heart = queryElectrocaedingramAverage(sbi.getClientId(), beginTime);
		
		ssr.setSportIntensity("心率控制在"
				+ RptUtils.getUserHeartRate(sbi.getAge(), heart, RptUtils.MIN_HEART_REATE)
				+ "~"
				+ RptUtils.getUserHeartRate(sbi.getAge(), heart, RptUtils.BEST_HEART_RATE) + "次/分，" +
				"建议以中低强度为宜。（低强度：运动过程中可连续交谈；中强度：运动过程中交谈有喘息；高强度：运动过程中喘息不能交谈。" +
				"运动过程中如有不适，请科学调整，降低强度或咨询您的健康管理师。）");
		
		SportDisease sportDisease = querySportDisease(sbi);
		if(sportDisease != null){
			String bestSuitSportId = sportDisease.getBestSuitSport();
			if(!StringUtils.isEmpty(bestSuitSportId)){
				String bestSuitSport = queryBestSuitSport(bestSuitSportId);
				if(!StringUtils.isEmpty(bestSuitSport)){
					ssr.setBestSportType(bestSuitSport);
				}
			}
			String sportType = getUserSportType(sbi,sportDisease);
			ssr.setSportType(sportType);
			ssr.setSportPlanning(sportDisease.getSportPlanning());
			ssr.setSportTimeSlot(sportDisease.getSportTimeSlot());
		}else {//没有匹配上默认添加
			ssr.setSportPlanning("持续性或周期性运动，每次40分钟；非周期性或间歇性运动，每天应达到40-60分钟。");
			ssr.setSportTimeSlot("避免在清晨运动，建议在上午9：00后运动；空腹及进餐2小时内不建议运动！");
		}
		String singelData = querySingleData(sbi, beginTime);
		if(!StringUtils.isEmpty(singelData)){
			ssr.setSingleData(singelData);
		}
		//所有检测数据值
		String allData = queryAllData(sbi, beginTime,srptTarget);
		if(!StringUtils.isEmpty(allData)){
			ssr.setAllData(allData);
		}
		//目标
		String target = queryTarget(srptTarget);
		if(!StringUtils.isEmpty(target)){
			ssr.setTarget(target);
		}
		ssr = ssrdDao.add(ssr);
		
		return ssr;
	}
	
	//获取最适合的运动
	private String queryBestSuitSport(String bestSuitSport){
		String bestSport = "";
		String bestObj[] = bestSuitSport.split(",");
		if(bestObj.length==1){
			bestSport = bestSuitSport;
		}else{
			int index = RandomUtils.getRandomIndex(bestObj.length);
			List<String> chooseSportList = new ArrayList<String>();
			for(int i = 0;i<bestObj.length;i++){
				if(i != index){
					chooseSportList.add(bestObj[i]);
				}
			}
			if(!CollectionUtils.isEmpty(chooseSportList)){
				String chooseSport = ArrayUtils.join(chooseSportList.toArray(), "、");
				bestSport = bestObj[index]+"（如果不喜欢本项运动，可更换为："+chooseSport+"等）";
			}
		}
		return bestSport;
	}
	
	//获取目标信息
	private String queryTarget (SrptTarget srptTarget){
		String target = "";
		List<String> lstTarget = new ArrayList<String>();
		if(!StringUtils.isEmpty(srptTarget.getBpTarget())){
			String bpTarget= "血压："+srptTarget.getBpTarget();
			lstTarget.add(bpTarget);
		}
		if(!StringUtils.isEmpty(srptTarget.getBoTarget())){
			String boTarget= "血氧："+srptTarget.getBoTarget();
			lstTarget.add(boTarget);
		}
		if(!StringUtils.isEmpty(srptTarget.getKongfuTarget())){
			String bs1Target = "空腹血糖："+srptTarget.getKongfuTarget();
			lstTarget.add(bs1Target);
		}
		if(!StringUtils.isEmpty(srptTarget.getCanhouTarge())){
			String bs2Target = "餐后两个小时血糖："+srptTarget.getCanhouTarge();
			lstTarget.add(bs2Target);
		}
		if(!CollectionUtils.isEmpty(lstTarget)){
			target = ArrayUtils.join(lstTarget.toArray())+"。";
		}
		return target;
	}
	
	/**获取日常原则**/
	private String querySDiet(String ids){
		JSONObject json = new JSONObject();
		List<SrptDietSprescription> lstSdiet = sdietDao.queryDiet(ids);
		if(!CollectionUtils.isEmpty(lstSdiet)){
			//随机取一条数据
			int index = RandomUtils.getRandomIndex(lstSdiet.size());
			SrptDietSprescription sdiet = lstSdiet.get(index);
			if(sdiet != null){
				json.put("data", JsonUtils.getJsonObject4JavaPOJO(sdiet));
			}
		}else{
			json.put("data", "");
		}
		return json.toString();
	}
	
	/**获取所有的监测信息**/
	private String queryAllData(SrptBaseInfo sbi,Date date,SrptTarget srptTarget){
		
		String allData = "";
		String bp = queryBloodPressure(sbi, date,srptTarget);
		String kongfu = queryBloodSugar(sbi, date, 1,srptTarget);
		String canhou = queryBloodSugarCanhou(sbi, date, 2,srptTarget);
		String bo = queryBloodOxygen(sbi.getClientId(), date,srptTarget);
		
		if(!StringUtils.isEmpty(bp) || !StringUtils.isEmpty(kongfu) || !StringUtils.isEmpty(canhou) || !StringUtils.isEmpty(bo)){
			allData += "从您的数据监测情况来看，";
			if(!StringUtils.isEmpty(bp)){
				allData += bp;
			}
			if(!StringUtils.isEmpty(kongfu)){
				allData += kongfu;
			}
			if(!StringUtils.isEmpty(canhou)){
				allData += canhou;
			}
			if(!StringUtils.isEmpty(bo)){
				allData += bo;
			}
		}
		
		return allData;
	}
	
	private String querySingleData(SrptBaseInfo sbi,Date date){
		String criteria = "";
		String hopeSolve = sbi.getHopeSolveHealth();
		if(!StringUtils.isEmpty(hopeSolve)){
			if(hopeSolve.equals("糖尿病")){
				String kongfu = queryBloodSugar(sbi, date, 1,null);
				String canhou = queryBloodSugarCanhou(sbi, date, 2,null);
				criteria = kongfu +"，"+canhou;
			}else if(hopeSolve.equals("高血压")){
				criteria = queryBloodPressure(sbi, date,null);
			}else{
				criteria = CriteriaUtil.getCriteriaUtil().criteria(hopeSolve);
			}
		}
		return criteria;
	}
	
	/**获取温馨提示（精简）**/
	private String queryShortTip(SrptDietPrinciple dietPrinciple){
		String shortTip = "";
		if(dietPrinciple != null){
			String life = dietPrinciple.getLifePrinciple();
			List<String> lstLife = new ArrayList<String>();
			if(!StringUtils.isEmpty(life)){
				List<SrptLifePrinciple> lst = lifePrincipleDao.queryListLifePriciple(life);
				if(!CollectionUtils.isEmpty(lst)){
					for (SrptLifePrinciple lifePrinciple : lst) {
						lstLife.add(lifePrinciple.getIntro());
					}
				}
				if(!CollectionUtils.isEmpty(lstLife)){
					shortTip = ArrayUtils.join(lstLife.toArray(),"、");
				}
			}
		}
		return shortTip;
	}
	
	
	/**获取温馨提示（精简）**/
	private String queryTotalTip(SrptDietPrinciple dietPrinciple){
		JSONArray ja = new JSONArray();
		JSONObject json = new JSONObject();
		if(dietPrinciple != null){
			String life = dietPrinciple.getLifePrinciple();
			if(!StringUtils.isEmpty(life)){
				List<SrptLifePrinciple> lst = lifePrincipleDao.queryListLifePriciple(life);
				if(!CollectionUtils.isEmpty(lst)){
					for (SrptLifePrinciple lifePrinciple : lst) {
						JSONObject jo = new JSONObject();
						jo.put("intro", lifePrinciple.getIntro());
						jo.put("detail", lifePrinciple.getDetail());
						ja.add(jo);
					}
				}
				
			}
		}
		json.put("data", ja);
		return json.toString();
	}
	
	// 身体状况简介
	private String getIntro(SrptBaseInfo sbi){
		StringBuffer intro = new StringBuffer();
		if (null != sbi) {
			List<String> tags = new ArrayList<String>();
			// BMI
			String bmiTag = getBMITag(sbi);
			if (!StringUtils.isEmpty(bmiTag)&&!bmiTag.equals("正常")) {
				tags.add(bmiTag);
			}
			// 缺乏锻炼
			if (sbi.getSportCount()!=null&&sbi.getSportCount() == 4 && sbi.getSportTime() > 1) { // 每天运动30分钟，每周5天，为适量运动
				tags.add("适量运动");
			} else {
				tags.add("缺乏锻炼");
			}
			// 饮酒
			if (!StringUtils.isEmpty(sbi.getWhite())
					|| !StringUtils.isEmpty(sbi.getBeer())
					|| !StringUtils.isEmpty(sbi.getRed())) {
				float white = 0f;
				float beer = 0f;
				float red = 0f;

				try {
					white = Float.parseFloat(sbi.getWhite());
				} catch (NumberFormatException e) {

				}

				try {
					beer = Float.parseFloat(sbi.getBeer());
				} catch (NumberFormatException e) {

				}

				try {
					red = Float.parseFloat(sbi.getRed());
				} catch (NumberFormatException e) {

				}

				if (white > 1 || beer > 750 || red > 100) {
					tags.add("饮酒");
				}
			}

			// 吸烟
			if (null != sbi.getSmoke()&&sbi.getSmoke().equals(1)) {
				if (1 == sbi.getSmoke()) {// 吸烟状况1：是 2:否
					tags.add("吸烟");
				}
			}

			// 精神紧张
			if (sbi.getWorking()!=null&&sbi.getWorking().equals(1)) {
				tags.add("精神紧张");
			}
			
			//既往病史
			String diseaseSelf = sbi.getDiseaseSelf();
			if(!StringUtils.isEmpty(diseaseSelf)){
				intro.append("根据您提供的基本信息提示既往存在"+diseaseSelf+"病史");
			}else{
				intro.append("根据您提供的基本信息提示您没有既往病史");
			}
			//家族病史
			String diseaseFamily = sbi.getDiseaseFamily();
			
			if(!StringUtils.isEmpty(diseaseFamily)){
				tags.add(diseaseFamily+"家族史");
			}
			if(!CollectionUtils.isEmpty(tags)){
				String tag = ArrayUtils.join(tags.toArray(),"、");
				intro.append("，存在的危险因素有"+tag+"。");
			}
		}
		return intro.toString();
	}
	
	/**获取饮食原则**/
	private SrptDietPrinciple queryDietPrinciple(SrptBaseInfo srpt){
		SrptDietPrinciple dietPrinciple = null;
		if(srpt != null){
			String disease = srpt.getHopeSolveHealth();
			if(!StringUtils.isEmpty(disease)){
				String diseaseQuery = getDiseaseQuery(disease);
				dietPrinciple = dietPrincipleDao.queryDietPrincipleByName(diseaseQuery);
			}else{
				disease = "正常";
				dietPrinciple = dietPrincipleDao.queryDietPrincipleByName(disease);
			}
			
//			String relDisease = getDiseaseQuery(disease);
//			if(!StringUtils.isEmpty(relDisease)){
//				dietPrinciple = dietPrincipleDao.queryDietPrincipleByName(relDisease);
//			}
			
		}
		return dietPrinciple;
	}
	
	
	/**获取运动疾病**/
	private SportDisease querySportDisease(SrptBaseInfo srpt){
		if(srpt != null){
			SportDisease sd = new SportDisease();
			sd.getAgeBracketByAge(srpt.getAge());
			String hopeSolve = srpt.getHopeSolveHealth();
			String relDisease = getDiseaseQuery(hopeSolve);
			sd.setDiseaseName(relDisease);
			SportDisease disease = sportDiseaseService.querySportDiseaseForRpt(sd);
			return disease;
		}
		return null;
	}
	
	
	/**
	 * 获取运动类型
	 */
	private String getUserSportType(SrptBaseInfo sbi,SportDisease disease) {
		Integer age = sbi.getAge();
		StringBuffer sb = null;
		if(age != null&&age > 55){
			sb = new StringBuffer("以易坚持的有氧代谢运动为原则，避免在运动中做推、拉、"
					+ "举之类的静力性力量练习或憋气练习。选择全身性的、有节奏的、容易放松、便于全面监测的运动项目。");
		}
		if(age != null&&age>=18&&age<=55){
			sb = new StringBuffer("以易坚持的有氧代谢运动为原则。可以有计划的在运动中加入器械等静力性锻炼。" +
					"建议选择全身性的、有节奏的、易放松的、便于全面监测的运动项目。");
		}
		String sportType = sbi.getSportType();
		
		if (null != disease && !StringUtils.isEmpty(disease.getSportType())) {
			List<SportPrescription> spList = prescriptionService
					.queryPrescript(null, disease.getSportType());
			if (!CollectionUtils.isEmpty(spList)) {
				List<String> nameList = ListUtils.getFiledList(spList, "name");
				
				List<String> lstSportSelf = new ArrayList<String>();
				if(!StringUtils.isEmpty(sportType)){
					String [] sportTypes = sportType.split(",");
					for (String st : sportTypes) {
						if(!nameList.contains(st)){
							lstSportSelf.add(st);
						}
					}
				}
				
				if(!CollectionUtils.isEmpty(lstSportSelf)){
					if(sb != null){
						sb.append("您当前的运动"+ArrayUtils.join(lstSportSelf.toArray(),"、")+"已不适合您现在的身体状况，建议您做以下运动：");
					}else{
						sb = new StringBuffer("您当前的运动"+ArrayUtils.join(lstSportSelf.toArray(),"、")+"已不适合您现在的身体状况，建议您做以下运动：");
					}
				}
				
				String msg = ArrayUtils.join(nameList.toArray(),"、");
				if(sb != null){
					sb.append("如" + msg + "等。");
				}else{
					sb = new StringBuffer("如" + msg + "等。");
				}
			}
		} else {
			sb.append("如散步、快走、慢跑、太极拳/剑/扇/球，游泳、跳舞、健身操、柔力球、踏车、低强度的球类如健身球、健身操、木球、气功、垂钓、五禽戏、易筋经、爬行、倒步走、广场舞。");
		}
		return sb.toString();
	}

	/**
	 * 计算开始时间
	 */
	private static Date cacluateBeginTime(int type) {
		Date beginTime = null;

		if(type!=0){
			String currentTime = DateUtils.format(new Date());
			if(Constant.GENERATE_SRPT_TYPE_DAY == type) {
				beginTime = DateUtils.getBeforeDay(currentTime, -Constant.GENERATE_SRPT_TEN_DAY);
			} else if (Constant.GENERATE_SRPT_TYPE_MONTH == type) {
				beginTime = DateUtils.getBeforeMonth(currentTime, -Constant.GENERATE_SRPT_ONE_MONTH);	
			}
		}
		return beginTime;
	}
	/**
	 * 计算开始时间
	 */
	private static Date cacluateBeginTime(int type,int Const) {
		Date beginTime = null;
		if(type!=0 && Const!=0){
			String currentTime = DateUtils.format(new Date());
			if(Constant.GENERATE_SRPT_TYPE_DAY == type) {
				beginTime = DateUtils.getBeforeDay(currentTime, -Const);
			} else if (Constant.GENERATE_SRPT_TYPE_MONTH == type) {
				beginTime = DateUtils.getBeforeMonth(currentTime, -Const);	
			}
		}
		return beginTime;
	}
	
	/**
	 * 获取用户开篇时总结
	 */
	private String getUserOpenings(SrptBaseInfo base) {
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
		//最后一次血压值
		BloodPressure bp = queryLatestBloodPressure(base.getClientId());
		if (bp != null) {
			sb.append("最后一次测量检测的血压值为" + bp.getSbp()+"/"+bp.getDbp()+ "mmHg，");
		}
		//最后一次心率值
		Electrocardiogram ed = queryLatestElectrocardiogram(base.getClientId());
		if (ed != null) {
			sb.append("心率为" + ed.getAverageHeartRate() + "次/min，");
		}
		if (!StringUtils.isEmpty(base.getDiseaseSelf())) {
			sb.append("患有" + base.getDiseaseSelf() + "等疾病，");
		}
		if (!StringUtils.isEmpty(getBMITag(base))) {
			sb.append("体质质量指数BMI为" + base.getBmi() + "kg/㎡ ，为"
					+ getBMITag(base) + "，");
		}
		sb.append("以下是健康安全的运动建议：");

		return sb.toString();
	}
	
	/**获取最后一次测量血压**/
	private BloodPressure queryLatestBloodPressure(Integer clientId){
		BloodPressure bp = bpDao.getLastUploadDateTime(clientId);
		return bp;
	}
	
	/**获取最后一个心率**/
	private Electrocardiogram queryLatestElectrocardiogram(Integer clientId){
		Electrocardiogram ed = edDao.getLastUploadDateTime(clientId);
		return ed;
	}
	
	/**
	 * 根据bmi获取相应的tag
	 */
	private String getBMITag(SrptBaseInfo sbi) {
		return RptUtils.getBMITag(sbi.getBmi());
	}

	
	/**获取血氧的相关信息**/
	public String queryBloodOxygen(Integer clientId,Date date,SrptTarget srptTarget){
		String aboutBO = "";
		int count = oxygenDao.queryBloodOxygenCount(clientId, date);
		if(count >0 ){
			int thisCount = 0;
			//判断血氧在94-99之间的值是否大于总数的50%
			thisCount = oxygenDao.queryBloodOxygen(clientId, date, 99, 94);
			double probability = 0;
			DecimalFormat df = new DecimalFormat("#.00");
			probability = Double.parseDouble(df.format(thisCount*100/count));
			if(thisCount > 0&&probability>50){
				aboutBO = "您的血氧波动在94-99%之间，基本在正常范围。";
				if(srptTarget != null){
					srptTarget.setBoTarget("94-99%");
				}
				return aboutBO;
			}else{//判断血氧在90-94之间的值是否大于总数的50%
				thisCount = oxygenDao.queryBloodOxygen(clientId, date, 90, 94);
				probability = Double.parseDouble(df.format(thisCount*100/count));
				if(probability>50){
					aboutBO = "您的血氧波动在90-94%之间，属于供氧不足。";
					if(srptTarget != null){
						srptTarget.setBoTarget("94-99%");
					}
					return aboutBO;
				}else{//判断血氧在80-90之间的值是否大于总数的50%
					thisCount = oxygenDao.queryBloodOxygen(clientId, date, 90, 80);
					probability = Double.parseDouble(df.format(thisCount*100/count));
					if(probability>50){
						aboutBO = "您的血氧波动在80-90%之间，属于轻度缺氧。";
						if(srptTarget != null){
							srptTarget.setBoTarget("94-99%");
						}
						return aboutBO;
					}else{//判断血氧在60-80之间的值是否大于总数的50%
						thisCount = oxygenDao.queryBloodOxygen(clientId, date, 80, 60);
						probability = Double.parseDouble(df.format(thisCount*100/count));
						if(probability>50){
							aboutBO = "您的血氧波动在60-80%之间，属于中度缺氧。";
							if(srptTarget != null){
								srptTarget.setBoTarget("94-99%");
							}
							return aboutBO;
						}else{//判断血氧在小于60之间的值是否大于总数的50%
							thisCount = oxygenDao.queryBloodOxygen(clientId, date, 60, 0);
							probability = Double.parseDouble(df.format(thisCount*100/count));
							if(probability>50){
								aboutBO = "您的血氧波动在60%以下，属于重度缺氧。";
								if(srptTarget != null){
									srptTarget.setBoTarget("94-99%");
								}
								return aboutBO;
							}
						}
					}
				}
			}
		}
		return aboutBO;
	}
	
	
	/**查询空腹血糖的相关信息**/
	public String queryBloodSugar(SrptBaseInfo sbi,Date date,int type,SrptTarget srptTarget){
		String aboutBS = "";
		Integer clientId = sbi.getClientId();
		//查询某段时间的空腹血糖数据
		List<BloodSugar> lstBS = bsDao.queryBloodSugarCount(clientId, date, type);
		if(!CollectionUtils.isEmpty(lstBS)){
			int count = lstBS.size();
			int thisCount = 0;
			String diseaseSelf = sbi.getDiseaseSelf();
			//List<String> lstDiseaseString = null;
			if(!StringUtils.isEmpty(diseaseSelf)){
				/*String [] diseaseString = diseaseSelf.split(",");
				lstDiseaseString = new ArrayList<String>();
				for (String disease : diseaseString) {
					lstDiseaseString.add(disease);
				}*/
				//判断是否有糖尿病，糖尿病和非糖尿病的标准不一样
				//if(!lstDiseaseString.contains("糖尿病")){
				if(!diseaseSelf.contains("糖尿病")){
					double probability = 0;
					DecimalFormat df = new DecimalFormat("#.00");
					int first = 0;
					int second = 0;
					//血糖在6.0以下的数据条数
					thisCount = bsDao.queryBloodSugar(clientId, date, 6.0, 0, type);
					//在这个数据期间的百分比
					probability = Double.parseDouble(df.format(thisCount*100/count));
					if(thisCount >0&&probability > 50){
						for (BloodSugar bloodSugar : lstBS) {
							if(bloodSugar.getBloodSugarValue()>6.1&&bloodSugar.getBloodSugarValue()<7.0){
								first += 1;
							}else if(bloodSugar.getBloodSugarValue()>7.0){
								second += 1;
							}
						}
						//根据条件的结果判断
						if(first == 1){
							aboutBS = "您的空腹血糖波动在6.0以下，基本在正常范围，偶有偏高。";
						}else if(first >2){
							aboutBS = "您的空腹血糖波动在6.0以下，基本在正常范围，偶有偏高，考虑可能是空腹血糖受损。";
						}else if(second == 1){
							aboutBS = "您的空腹血糖波动在6.0以下，基本在正常范围，偶有偏高。";
						}else if(second > 2){
							aboutBS = "您的空腹血糖波动在6.0以下，基本在正常范围，偶有偏高，考虑可能为糖尿病。";
						}
						if(srptTarget != null){
							srptTarget.setKongfuTarget("3.9-6.1mmol/l");
						}
						return aboutBS;
					}else{
						thisCount = bsDao.queryBloodSugar(clientId, date, 7.0, 6.1, type);
						probability = Double.parseDouble(df.format(thisCount*100/count));
						if(thisCount >0&&probability > 50){
							for (BloodSugar bloodSugar : lstBS) {
								if(bloodSugar.getBloodSugarValue()>7.0){
									second += 1;
								}
							}
							if(second == 1){
								aboutBS = "您的空腹血糖波动在6.1-7.0之间，可能为空腹血糖受损，偶有偏高。";
							}else if(second > 2){
								aboutBS = "您的空腹血糖波动在6.1-7.0之间，偶有偏高，考虑可能为糖尿病。";
							}
							if(srptTarget != null){
								srptTarget.setKongfuTarget("3.9-6.1mmol/l");
							}
							return aboutBS;
						}else{
							thisCount = bsDao.queryBloodSugar(clientId, date, 0, 7.0, type);
							probability = Double.parseDouble(df.format(thisCount*100/count));
							if(thisCount > 0&&probability > 50){
								aboutBS = "您的空腹血糖波动在7.0以上，考虑可能为糖尿病。";
							}
							//空腹血糖的目标
							if(srptTarget != null){
								srptTarget.setKongfuTarget("3.9-7.0mmol/l");
							}
							return aboutBS;
						}
					}
				}else{
					thisCount = bsDao.queryBloodSugar(clientId, date, 10.0, 7.1, type);
					double probability = 0;
					DecimalFormat df = new DecimalFormat("#.00");
					int first = 0;
					int second = 0;
					int third = 0;
					probability = Double.parseDouble(df.format(thisCount*100/count));
					if(thisCount > 0&&probability > 50){
						for (BloodSugar bloodSugar : lstBS) {
							if(bloodSugar.getBloodSugarValue() > 10.0){
								second += 1;
							}
						}
						if(second == 1){
							aboutBS = "您的空腹血糖波动在7.1-10.0之间，偶有偏高，控制不良。";
						}else if(second > 2){
							aboutBS = "您的空腹血糖波动在7.1-10.0之间，偶有偏高，控制不良。 ";
						}
						return aboutBS;
					}else{
						thisCount = bsDao.queryBloodSugar(clientId, date, 7.0, 6.1, type);
						probability = Double.parseDouble(df.format(thisCount*100/count));
						if(thisCount >0 &&probability > 50){
							for (BloodSugar bloodSugar : lstBS) {
								if(bloodSugar.getBloodSugarValue()>7.0&&bloodSugar.getBloodSugarValue()<10.0){
									first += 1;
								}else if(bloodSugar.getBloodSugarValue()>10.1){
									second += 1;
								}
							}
							if(first == 1){
								aboutBS = "您的空腹血糖波动在6.1-7.0之间，偶有偏高，控制良好。";
							}else if(first > 2){
								aboutBS = "您的空腹血糖波动在6.1-7.0之间，偶有偏高，控制欠佳。";
							}else if(second == 1){
								aboutBS = "您的空腹血糖波动在6.1-7.0之间，偶有偏高，控制良好。";
							}else if(second > 2){
								aboutBS = "您的空腹血糖波动在6.1-7.0之间，偶有偏高，控制欠佳。";
							}
							if(srptTarget != null){
								srptTarget.setKongfuTarget("4.4-6.1mmol/l");
							}
							return aboutBS;
						}else{
							thisCount = bsDao.queryBloodSugar(clientId, date, 6.1, 0, type);
							probability = Double.parseDouble(df.format(thisCount*100/count));
							if(thisCount >0&&probability > 50){
								for (BloodSugar bloodSugar : lstBS) {
									//血糖在6.1-7.0之间次数1次
									//血糖在7.1-10.0之间1次
									//血糖在10.1以上次数1次
									if(bloodSugar.getBloodSugarValue()>10.1){
										third += 1;
									}else if(bloodSugar.getBloodSugarValue()>7.1&&bloodSugar.getBloodSugarValue()<10.0){
										second += 1;
									}else if(bloodSugar.getBloodSugarValue()>6.1&&bloodSugar.getBloodSugarValue()<7.0){
										first += 1;
									}
								}
								if(first == 1){
									aboutBS = "您的空腹血糖波动在6.0以下，偶有偏高，控制较理想。";
								}else if(first > 2){
									aboutBS = "您的空腹血糖波动在6.0以下，偶有偏高，控制良好。";
								}else if(second == 1){
									aboutBS = "您的空腹血糖波动在6.0以下，偶有偏高，控制理想。";
								}else if(second > 2){
									aboutBS = "您的空腹血糖波动在6.0以下，偶有偏高，控制欠佳。";
								}else if(third == 1){
									aboutBS = "您的空腹血糖波动在6.0以下，偶有偏高，控制较理想。";
								}else if(third > 2){
									aboutBS = "您的空腹血糖波动在6.0以下，偶有偏高，控制良好。";
								}
								if(srptTarget != null){
									srptTarget.setKongfuTarget("4.4-6.1mmol/l");
								}
							}else{
								thisCount = bsDao.queryBloodSugar(clientId, date, 0, 10.1, type);
								probability = Double.parseDouble(df.format(thisCount*100/count));
								if(thisCount >0&&probability > 50){
									aboutBS = "您的空腹血糖波动在10.0以上，血糖控制不良。";
								}
							}
						}
					}
				}
			}
		}
		return aboutBS;
	}
	
	/**查询餐后两小时血糖的相关信息**/
	public String queryBloodSugarCanhou(SrptBaseInfo sbi,Date date,int type,SrptTarget srptTarget){
		String aboutBS = "";
		Integer clientId = sbi.getClientId();
		//餐后两小时血糖在某个时间段的条数
		List<BloodSugar> lstBS = bsDao.queryBloodSugarCount(clientId, date, type);
		if(!CollectionUtils.isEmpty(lstBS)){
			int count = lstBS.size();
			int thisCount = 0;
			//疾病史
			String diseaseSelf = sbi.getDiseaseSelf();
			//List<String> lstDiseaseString = null;
			if(!StringUtils.isEmpty(diseaseSelf)){
				/*String [] diseaseString = diseaseSelf.split(",");
				lstDiseaseString = new ArrayList<String>();
				for (String disease : diseaseString) {
					lstDiseaseString.add(disease);
				}*/
				//判断疾病史中是否有糖尿病
				//if(!lstDiseaseString.contains("糖尿病")){
				if(!diseaseSelf.contains("糖尿病")){
					//判断餐后血糖在7.8以下的数据条数
					thisCount = bsDao.queryBloodSugar(clientId, date, 7.8, 0, type);
					double probability = 0;
					DecimalFormat df = new DecimalFormat("#.00");
					int first = 0;
					int second = 0;
					//餐后血糖的百分比
					probability = Double.parseDouble(df.format(thisCount*100/count));
					if(thisCount >0&&probability > 50){
						for (BloodSugar bloodSugar : lstBS) {
							if(bloodSugar.getBloodSugarValue()>7.8&&bloodSugar.getBloodSugarValue()<11.1){
								first += 1;
							}else if(bloodSugar.getBloodSugarValue()>11.1){
								second += 1;
							}
						}
						if(first == 1){
							aboutBS = "您的餐后血糖波动在7.8以下，基本在正常范围，偶有偏高。";
						}else if(first >2){
							aboutBS = "您的餐后血糖波动在7.8以下，基本在正常范围，偶有偏高，考虑可能为糖耐量减低。";
						}else if(second == 1){
							aboutBS = "您的餐后血糖波动在7.8以下，基本在正常范围，偶有偏高。";
						}else if(second > 2){
							aboutBS = "您的餐后血糖波动在7.8以下，基本在正常范围，偶有偏高，考虑可能是糖耐量减低。";
						}
						if(srptTarget != null){
							srptTarget.setCanhouTarge("3.9-7.8mmol/l");
						}
						return aboutBS;
					}else{
						//判断餐后血糖在7.8-11.1之间的数据条数
						thisCount = bsDao.queryBloodSugar(clientId, date, 11.1, 7.8, type);
						//餐后血糖的百分比
						probability = Double.parseDouble(df.format(thisCount*100/count));
						//如果大于50，根据条件查询返回数据，反之执行else
						if(thisCount >0&&probability > 50){
							for (BloodSugar bloodSugar : lstBS) {
								if(bloodSugar.getBloodSugarValue()>11.1){
									second += 1;
								}
							}
							if(second == 1){
								aboutBS = "您的餐后血糖波动在7.8-11.1之间，偶有偏高，考虑可能是糖耐量减低。";
							}else if(second > 2){
								aboutBS = "您的餐后血糖波动在7.8-11.1之间，偶有偏高，考虑可能为糖尿病。";
							}
							if(srptTarget != null){
								srptTarget.setCanhouTarge("3.9-7.8mmol/l");
							}
							return aboutBS;
						}else{
							thisCount = bsDao.queryBloodSugar(clientId, date, 0, 11.1, type);
							probability = Double.parseDouble(df.format(thisCount*100/count));
							if(thisCount > 0&&probability > 50){
								aboutBS = "您的餐后血糖波动在11.1以上，考虑为糖尿病。";
							}
							if(srptTarget != null){
								srptTarget.setCanhouTarge("3.9-7.8mmol/l");
							}
							return aboutBS;
						}
					}
				}else{
					thisCount = bsDao.queryBloodSugar(clientId, date, 10.0, 8.1, type);
					double probability = 0;
					DecimalFormat df = new DecimalFormat("#.00");
					int first = 0;
					int second = 0;
					probability = Double.parseDouble(df.format(thisCount*100/count));
					if(thisCount > 0&&probability > 50){
						for (BloodSugar bloodSugar : lstBS) {
							if(bloodSugar.getBloodSugarValue() > 10.1){
								second += 1;
							}
						}
						if(second == 1){
							aboutBS = "您的餐后血糖波动在8.1-10.0之间，偶有偏高，餐后血糖控制良好。";
						}else if(second > 2){
							aboutBS = "您的餐后血糖波动在8.1-10.0之间，偶有偏高，餐后血糖控制不良。";
						}
						if(srptTarget != null){
							srptTarget.setCanhouTarge("4.4-8.0mmol/l");
						}
						return aboutBS;
					}else{
						thisCount = bsDao.queryBloodSugar(clientId, date, 8.0, 0, type);
						probability = Double.parseDouble(df.format(thisCount*100/count));
						if(thisCount >0&&probability > 50){
							for (BloodSugar bloodSugar : lstBS) {
								if(bloodSugar.getBloodSugarValue()>8.1&&bloodSugar.getBloodSugarValue()<10.0){
									first += 1;
								}else if(bloodSugar.getBloodSugarValue()>10.1){
									second += 1;
								}
							}
							if(first == 1){
								aboutBS = "您的餐后血糖波动在8.0以下，偶有偏高，控制较理想。";
							}else if(first > 2){
								aboutBS = "您的餐后血糖波动在8.0以下，偶有偏高，控制良好。";
							}else if(second == 1){
								aboutBS = "您的餐后血糖波动在8.0以下，偶有偏高，控制较理想。";
							}else if(second > 2){
								aboutBS = "您的餐后血糖波动在8.0以下，偶有偏高，控制欠佳。";
							}
							if(srptTarget != null){
								srptTarget.setCanhouTarge("4.4-8.0mmol/l");
							}
							return aboutBS;
						}else{
							thisCount = bsDao.queryBloodSugar(clientId, date, 0, 10.1, type);
							probability = Double.parseDouble(df.format(thisCount*100/count));
							if(thisCount >0&&probability > 50){
								aboutBS = "您的餐后血糖波动在10.0以上，偶有偏高，餐后血糖控制较差。";
							}
							if(srptTarget != null){
								srptTarget.setCanhouTarge("8.1-10.1mmol/l");
							}
							return aboutBS;
						}
					}
				}
			}
		}
		return aboutBS;
	}
	
	/**查询有关血压的相关信息**/
	public String queryBloodPressure(SrptBaseInfo sbi,Date date,SrptTarget srptTarget){
		String aboutBP = "";
		Integer clientId = sbi.getClientId();
		List<BloodPressure> lstBP = bpDao.queryBloodPressureCount(clientId, date);
		if(!CollectionUtils.isEmpty(lstBP)){
			int count = lstBP.size();
			int thisCount = 0;
			String diseaseSelf = sbi.getDiseaseSelf();
			//List<String> lstDiseaseString = null;
			if(!StringUtils.isEmpty(diseaseSelf)){
				/*String [] diseaseString = diseaseSelf.split(",");
				lstDiseaseString = new ArrayList<String>();
				for (String disease : diseaseString) {
					lstDiseaseString.add(disease);
				}*/
				if(diseaseSelf.contains("高血压")){
					double probability = 0;
					DecimalFormat df = new DecimalFormat("#.00");
					int first = 0;
					int second = 0;
					/*血压大部分在100-139/60-89mmhg，a/b*/
					thisCount = bpDao.queryBloodPressure(clientId, 100, 139, 60, 89, date);
					probability = Double.parseDouble(df.format(thisCount*100/count));
					if(thisCount >0&&probability > 50){
						//a和，或b偏高次数1次
						for (BloodPressure bloodPressure : lstBP) {
							if(bloodPressure.getSbp() >139){
								first += 1;
							}else if(bloodPressure.getDbp() < 60){
								second += 1;
							}
						}
						//a和，或b偏高次数1次
						if((first==1||second==1)||(first==1&&second==1)){
							aboutBP = "您的血压波动在100-139/60-89mmhg，偶有偏高，血压控制满意。";
						}else if((first >2 ||second >2)||(first >2&&second >2)){//a和，或b偏高次数2次以上
							aboutBP = "您的血压波动在100-139/60-89mmhg偶有偏高，血压控制较好。";
						}
						//血压大部分在100-139/60-89mmhg，a/b
						if(srptTarget != null){
							srptTarget.setBpTarget("120/80mmhg");
						}
						return aboutBP;
					}else{
						/*血压大部分在100-139/90-99mmhg，a/b*/
						thisCount = bpDao.queryBloodPressure(clientId, 100, 139, 90, 99, date);
						probability = Double.parseDouble(df.format(thisCount*100/count));
						if(thisCount >0&&probability > 50){
							//a和，或b偏高次数1次
							for (BloodPressure bloodPressure : lstBP) {
								if(bloodPressure.getSbp() >139){
									first += 1;
								}else if(bloodPressure.getDbp() < 90){
									second += 1;
								}
							}
							//a和，或b偏高次数1次
							if((first==1||second==1)||(first==1&&second==1)){
								aboutBP = "您的血压波动在100-139/90-99mmhg，偶有偏高，血压控制较好。";
							}else if((first >2 ||second >2)||(first >2&&second >2)){//a和，或b偏高次数2次以上
								aboutBP = "您的血压波动在100-139/90-99mmhg，偶有偏高，血压控制尚可。";
							}
							//血压大部分在100-139/90-99mmhg，a/b
							if(srptTarget != null){
								srptTarget.setBpTarget("120/80mmhg");
							}
							return aboutBP;
						}else{
							/*血压大部分在100-139/100-109mmhg，a/b*/
							thisCount = bpDao.queryBloodPressure(clientId, 100, 139, 100, 109, date);
							probability = Double.parseDouble(df.format(thisCount*100/count));
							if(thisCount >0&&probability > 50){
								//a和，或b偏高次数1次
								for (BloodPressure bloodPressure : lstBP) {
									if(bloodPressure.getSbp() >139){
										first += 1;
									}else if(bloodPressure.getDbp() < 100){
										second += 1;
									}
								}
								//a和，或b偏高次数1次
								if((first==1||second==1)||(first==1&&second==1)){
									aboutBP = "您的血压波动在100-139/100-109mmhg，偶有偏高，血压控制尚可。";
								}else if((first >2 ||second >2)||(first >2&&second >2)){//a和，或b偏高次数2次以上
									aboutBP = "您的血压波动在100-139/100-109mmhg，偶有偏高，血压控制不满意。";
								}
								if(srptTarget != null){
									srptTarget.setBpTarget("120/80mmhg");
								}
								return aboutBP;
							}else{
								/*血压大部分在100-139/110以上mmhg，a/b*/
								thisCount = bpDao.queryBloodPressure(clientId, 100, 139, 110, 0, date);
								probability = Double.parseDouble(df.format(thisCount*100/count));
								if(thisCount >0&&probability > 50){
									aboutBP = "您的血压偶有偏高，血压控制较差";
									if(srptTarget != null){
										srptTarget.setBpTarget("120/90mmhg");
									}
									return aboutBP;
								}else{
									/*血压大部分在140-159/60-99mmhg，a/b*/
									thisCount = bpDao.queryBloodPressure(clientId, 140, 159, 60, 99, date);
									probability = Double.parseDouble(df.format(thisCount*100/count));
									if(thisCount >0&&probability > 50){
										//a和，或b偏高次数1次
										for (BloodPressure bloodPressure : lstBP) {
											if(bloodPressure.getSbp() >159){
												first += 1;
											}else if(bloodPressure.getDbp() < 60){
												second += 1;
											}
										}
										//a和，或b偏高次数1次
										if((first==1||second==1)||(first==1&&second==1)){
											aboutBP = "您的血压波动在140-159/60-99mmhg，偶有偏高，血压控制尚可。";
										}else if((first >2 ||second >2)||(first >2&&second >2)){//a和，或b偏高次数2次以上
											aboutBP = "您的血压波动在140-159/60-99mmhg，偶有偏高，血压控制不满意。";
										}
										if(srptTarget != null){
											srptTarget.setBpTarget("100-139/60-89mmhg");
										}
										return aboutBP;
									}else{
										/*血压大部分在140-159/100-109mmhg，a/b*/
										thisCount = bpDao.queryBloodPressure(clientId, 140, 159, 100, 109, date);
										probability = Double.parseDouble(df.format(thisCount*100/count));
										if(thisCount >0&&probability > 50){
											//a和，或b偏高次数1次
											for (BloodPressure bloodPressure : lstBP) {
												if(bloodPressure.getSbp() >159){
													first += 1;
												}else if(bloodPressure.getDbp() < 100){
													second += 1;
												}
											}
											//a和，或b偏高次数1次
											if((first==1||second==1)||(first==1&&second==1)){
												aboutBP = "您的血压波动在140-159/100-109mmhg，偶有偏高，血压控制尚可。";
											}else if((first >2 ||second >2)||(first >2&&second >2)){//a和，或b偏高次数2次以上
												aboutBP = "您的血压波动在140-159/100-109mmhg，偶有偏高，血压控制欠佳。";
											}
											if(srptTarget != null){
												srptTarget.setBpTarget("120-139/60-89mmhg");
											}
											return aboutBP;
										}else{
											/*血压大部分在140-159/110以上mmhg，a/b*/
											thisCount = bpDao.queryBloodPressure(clientId, 140, 159, 110, 0, date);
											probability = Double.parseDouble(df.format(thisCount*100/count));
											if(thisCount >0&&probability > 50){
												aboutBP = "您的血压波动在140-159/110以上mmhg，偶有偏高，血压控制较差。";
												if(srptTarget != null){
													srptTarget.setBpTarget("120-139/60-99mmhg");
												}
												return aboutBP;
											}else{
												/*血压大部分在160-179/60-109mmhg，a/b*/
												thisCount = bpDao.queryBloodPressure(clientId, 160, 179, 60, 109, date);
												probability = Double.parseDouble(df.format(thisCount*100/count));
												if(thisCount >0&&probability > 50){
													//a和，或b偏高次数1次
													for (BloodPressure bloodPressure : lstBP) {
														if(bloodPressure.getSbp() >179){
															first += 1;
														}else if(bloodPressure.getDbp() < 60){
															second += 1;
														}
													}
													//a和，或b偏高次数1次
													if((first==1||second==1)||(first==1&&second==1)){
														aboutBP = "您的血压波动在160-179/60-109mmhg，偶有偏高，血压控制尚可。";
													}else if((first >2 ||second >2)||(first >2&&second >2)){//a和，或b偏高次数2次以上
														aboutBP = "您的血压波动在160-179/60-109mmhg，偶有偏高，血压控制较差。";
													}
													if(srptTarget != null){
														srptTarget.setBpTarget("140-159/60-99mmhg");
													}
													return aboutBP;
												}else{
													/*血压大部分在180/110以上mmhg，a/b*/
													thisCount = bpDao.queryBloodPressure(clientId, 180, 0, 110, 0, date);
													probability = Double.parseDouble(df.format(thisCount*100/count));
													if(thisCount >0&&probability > 50){
														aboutBP = "您的血压波动在180以上/110以下mmhg，偶有偏高，血压控制较差。";
														if(srptTarget != null){
															srptTarget.setBpTarget("160-179/60-109mmhg");
														}
														return aboutBP;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}else{
					double probability = 0;
					/*血压大部分在90-140/60-89mmhg,a/b*/
					thisCount = bpDao.queryBloodPressure(clientId, 90, 140, 60, 89, date);
					DecimalFormat df = new DecimalFormat("#.00");
					probability = Double.parseDouble(df.format(thisCount*100/count));
					int first = 0;
					int second = 0;
					if(thisCount>0&&probability >50){
						//a和，或b偏高次数1次
						for (BloodPressure bloodPressure : lstBP) {
							if(bloodPressure.getSbp() >140){
								first += 1;
							}else if(bloodPressure.getDbp() < 60){
								second += 1;
							}
						}
						//a和，或b偏高次数1次
						if((first==1||second==1)||(first==1&&second==1)){
							aboutBP = "您的血压波动在90-140/60-89mmhg，偶有偏高，血压基本在正常范围。";
						}else if((first >2 ||second >2)||(first >2&&second >2)){//a和，或b偏高次数2次以上
							aboutBP = "您的血压波动在90-140/60-89mmhg，偶有偏高，考虑可能为高血压。";
						}
						if(srptTarget != null){
							srptTarget.setBpTarget("120/80mmhg");
						}
						return aboutBP;
					}
				}
			}
		}
		return aboutBP;
	}

	//心率平均值
	private String queryElectrocaedingramAverage(Integer clientId,Date date){
		Electrocardiogram ed = new Electrocardiogram();
		ed.setClientId(clientId);
		QueryCondition qc = new QueryCondition();
		qc.setBeginTime(date);
		qc.setEndTime(new Date());
		
		Object obj = edDao.queryElectrocardiogramAverage(qc, ed);
		String heartReat = "";
		if(obj!=null){
			Double heart = Double.parseDouble(obj.toString());
			heartReat = ""+Math.round(heart);
		}
		return heartReat;
	}

	
	/**根据简易报告id查询简易报告运动部分信息**/
	public SrptSummaryReport querySSRBySrptId(Integer srptId){
		return ssrdDao.querySSRBySrptId(srptId);
	}
	
	public int updateReportProperty(String content,String field,Integer dietId) {
		return ssrdDao.updateReportProperty(content,field,dietId);
	}

	/**
	 * 根据天数创建建议报告 type 1 按天计算。 2 按月计算， const 数量
	 */
	public void generateReportByDay(Integer clientId, int type, int Const) {
		// 根据传入的类型计算时间范围
		Date beginTime = cacluateBeginTime(type,Const);
		// 保存简易基本信息
		SrptBaseInfo sbi = sbaseInfoService.createRptFullBaseInfo(clientId,type);
		// 保存报告的详细信息
		saveSummaryReport(sbi, beginTime);
		// 保存报告膳食量信息
		saveSrptDiet(sbi);
		
	}
	
	public String getDiseaseQuery(String disease){
		String diseaseQuery = "";
		if(!StringUtils.isEmpty(disease)){
			if(disease.equals("心率失常")){
				diseaseQuery = "冠心病";
			}else if(disease.equals("高尿酸症")){
				diseaseQuery = "痛风";
			}else{
				diseaseQuery = disease;
			}
		}
//		}else{
//			diseaseQuery = "正常";
//		}
		return diseaseQuery;
	}
}
