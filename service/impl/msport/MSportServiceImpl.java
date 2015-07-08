package com.bskcare.ch.service.impl.msport;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.dao.ClientLocationDao;
import com.bskcare.ch.dao.msport.MSportDao;
import com.bskcare.ch.dao.msport.MSportPlanDao;
import com.bskcare.ch.service.ClientInfoService;
import com.bskcare.ch.service.ClientLatestPhyService;
import com.bskcare.ch.service.msport.MSportPlanService;
import com.bskcare.ch.service.msport.MSportService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.MoneyFormatUtil;
import com.bskcare.ch.util.RptUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ClientLocation;
import com.bskcare.ch.vo.client.ClientLatestPhy;
import com.bskcare.ch.vo.msport.MSport;
import com.bskcare.ch.vo.msport.MSportPlan;

@Service
public class MSportServiceImpl implements MSportService{
	@Autowired
	private MSportDao msportDao;
	
	@Autowired
	private MSportPlanDao planDao;
	
	@Autowired
	private ClientLocationDao locationDao;
	@Autowired
	private ClientLatestPhyService phyService;
	@Autowired
	private ClientInfoService clientInfoService;
	@Autowired
	private MSportPlanService planService;
	
	public String addMSport(List<MSport> list){
		JSONObject json = new JSONObject();
		if(!CollectionUtils.isEmpty(list)){
			Integer clientId = list.get(0).getClientId();
			
			for (MSport msport : list) {
				MSport ms = msportDao.querySport(msport.getClientId(), msport.getTestDate());
				if(ms != null) {//已经存在
					BeanUtils.copyProperties(msport, ms, new String[]{"id"});
					ms.setUploadDate(new Date());
					msportDao.update(ms);
				} else { //不存在
					msport.setUploadDate(new Date());
					msportDao.add(msport);
				}
			}
			
			json.put("code","1");
			json.put("msg", "数据上传成功");
			json.put("data", "");
			
			
			
		}else{
			json.put("code","0");
			json.put("msg", "上传数据为空");
			json.put("data", "");
		}
		
		return json.toString();
	}
	
	/**计算两个日期之间相差的天数**/
	 public static int daysBetween(Date smdate,Date bdate) throws ParseException { 
		 long between_days = 0;
		 if(smdate != null && bdate != null){
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        smdate=sdf.parse(sdf.format(smdate));  
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        between_days=(time2-time1)/(1000*3600*24);  
		 }
	     return Integer.parseInt(String.valueOf(between_days));           
	 }    
	
	
	public String querySportTotal(Integer cid) throws ParseException{
		JSONObject jo = new JSONObject();
		List<MSport> list = msportDao.querySportByCid(cid);
		
		MSportPlan plan = planDao.queryMSportPlan(cid);
		
		//步数总和
		double stepsTotal = 0;
		//卡路里总和
		double calorieTotal = 0;
		//深睡眠时间总和
		double deepSleepTotal = 0;
		//距离总和
		double distanceTotal = 0;
		//计划步数总和
		double planStepTotal = 0;
		
		//浅睡眠时间总和
		double lightSleepTotal = 0;
		//极浅睡眠时间总和
		double lightestSleepTotal = 0;
		//查睡眠时间总和
		double poorSleepTotal = 0;
		
		//总和最高
		double maxTotal = 0;
		
		if(!CollectionUtils.isEmpty(list)){
			for (MSport mSport : list) {
				stepsTotal += mSport.getSteps();
				calorieTotal += mSport.getCalorie();
				deepSleepTotal += mSport.getDeepSleep();
				distanceTotal += mSport.getDistance();
				//planStepTotal += mSport.getStepPlan();
				lightSleepTotal += mSport.getLightSleep();
				lightestSleepTotal += mSport.getLightestSleep();
				poorSleepTotal += mSport.getPoorSleep();
			}
			
			List<Double> total = new ArrayList<Double>();
			total.add(deepSleepTotal);
			total.add(lightSleepTotal);
			total.add(lightestSleepTotal);
			total.add(poorSleepTotal);
			
			Collections.sort(total);
			
			maxTotal = total.get(3);
			
			JSONObject json = new JSONObject();
			json.put("stepsTotal", stepsTotal);
			json.put("calorieTotal", calorieTotal);
			json.put("deepSleepTotal", deepSleepTotal);
			json.put("distanceTotal", MoneyFormatUtil.formatDouble(distanceTotal/1000));
			//json.put("planStepTotal", planStepTotal);
			json.put("lightSleepTotal", lightSleepTotal);
			json.put("lightestSleepTotal", lightestSleepTotal);
			json.put("poorSleepTotal", poorSleepTotal);
			json.put("maxTotal", maxTotal);
			
			
			//最早的测试时间
			Date testDate = list.get(0).getTestDate();
			//最早的测试时间和当前时间相差的天数
			int days = daysBetween(testDate, new Date());
			json.put("day", days);
			
			if(plan != null){
				planStepTotal = days*plan.getSteps();
			}
			
			json.put("planStepTotal", planStepTotal);
			
			jo.put("code", "1");
			jo.put("msg", "获取信息成功");
			jo.put("data", json.toString());
			return jo.toString();
			
		}else{
			
			jo.put("code", "1");
			jo.put("msg", "信息为空");
			jo.put("data", "");
			return jo.toString();
		}
		
	}
	
	public String querySportMonth(Integer cid,String testDate){
	//public String querySportMonth(Integer cid,int year,int month){
		//List<MSport> list = msportDao.querySportMonth(cid, year, month);
		List<MSport> list = msportDao.querySportMonth(cid, testDate);
		JSONObject json = new JSONObject();
		if(!CollectionUtils.isEmpty(list)){
			//把List集合转换成Json格式的数据
			String result = JsonUtils.getJsonString4JavaListDate(list, DateUtils.DATE_PATTERN,null);
			json.put("code", "1");
			json.put("msg", "获取信息成功");
			json.put("data", result);
			return json.toString();
			
		}else{
			json.put("code", "1");
			json.put("msg", "信息为空");
			json.put("data", "");
			return json.toString();
		}
	}
		
	
	public String queryTotalSport(Integer clientId){
		Object obj = msportDao.queryTotalSport(clientId);
		
		JSONObject jo = new JSONObject();
		
		Object[] oarray = (Object[]) obj;
		if(oarray.length == 4){
			if(null != oarray[0] && null != oarray[1] && null != oarray[2]
					&& null != oarray[3]){
				//深睡眠时间总和
				double deepSleepTotal = Double.parseDouble(oarray[0]+"");
				//浅睡眠时间总和
				double lightSleepTotal = Double.parseDouble(oarray[1]+"");
				//极浅睡眠时间总和
				double lightestSleepTotal = Double.parseDouble(oarray[2]+"");
				//查睡眠时间总和
				double poorSleepTotal = Double.parseDouble(oarray[3]+"");
				
				List<Double> total = new ArrayList<Double>();
				total.add(deepSleepTotal);
				total.add(lightSleepTotal);
				total.add(lightestSleepTotal);
				total.add(poorSleepTotal);
				
				Collections.sort(total);
				//总和最高
				double maxTotal = total.get(3);
				
				JSONObject json = new JSONObject();
				json.put("deepSleepTotal", deepSleepTotal);
				json.put("lightSleepTotal", lightSleepTotal);
				json.put("lightestSleepTotal", lightestSleepTotal);
				json.put("poorSleepTotal", poorSleepTotal);
				json.put("maxTotal", maxTotal);
				
				jo.put("code", 1);
				jo.put("msg","请求运动数据成功");
				jo.put("data", json.toString());
			}else{
				jo.put("code", 1);
				jo.put("msg", "运动数据为空");
				jo.put("data", "");
			}
		}
		return jo.toString();
	}
	
	
	public String dongDongQuerySport(Integer clientId) throws ParseException{
		JSONObject jo = new JSONObject();
		List<MSport> list = msportDao.querySportByCid(clientId);
		
		ClientLocation location = locationDao.queryLocationByClientId(clientId);
		MSportPlan plan = planDao.queryMSportPlan(clientId);
		
		//步数总和
		double stepsTotal = 0;
		//卡路里总和
		double calorieTotal = 0;
		//距离总和
		double distanceTotal = 0;
		//活跃度数
		int liveness = 0;
		
		if(!CollectionUtils.isEmpty(list)&&plan!=null){
			for (MSport mSport : list) {
				stepsTotal += mSport.getSteps();
				calorieTotal += mSport.getCalorie();
				distanceTotal += mSport.getDistance();
				if(mSport.getDistance()>plan.getDistance()){
					liveness += 1;
				}
			}
			
			JSONObject json = new JSONObject();
			json.put("stepsTotal", stepsTotal);
			json.put("calorieTotal", calorieTotal);
			json.put("distanceTotal", MoneyFormatUtil.formatDouble(distanceTotal/1000));
			json.put("liveness", liveness);
			json.put("goodReputation", location.getGoodReputation());
			json.put("complain",location.getComplain());
			
			//最早的测试时间
			Date testDate = list.get(0).getTestDate();
			//最早的测试时间和当前时间相差的天数
			int days = daysBetween(testDate, new Date());
			json.put("day", days);
			
			if(!StringUtils.isEmpty(location.getLocation())){
				json.put("location", location.getLocation());
			}else{
				json.put("location", "");
			}
			
			json.put("longitude", location.getLongitude());
			json.put("latitude", location.getLatitude());
			
			jo.put("code", 1);
			jo.put("msg", "成功");
			jo.put("data", json.toString());
		}else{
			JSONObject json = new JSONObject();
			json.put("stepsTotal", 0);
			json.put("calorieTotal", 0);
			json.put("distanceTotal", 0);
			json.put("liveness", liveness);
			json.put("goodReputation", location.getGoodReputation());
			json.put("complain",location.getComplain());
			//最早的测试时间和当前时间相差的天数
			json.put("day", 0);
			if(!StringUtils.isEmpty(location.getLocation())){
				json.put("location", location.getLocation());
			}else{
				json.put("location", "");
			}
			json.put("longitude", location.getLongitude());
			json.put("latitude", location.getLatitude());
			jo.put("code", 1);
			jo.put("msg", "数据为空");
			jo.put("data", "");
		}
		
		return jo.toString();
	}
	
	
	public String querySportYesterday(Integer cid){
		
		/**sortInfo排名*/
		int sortInfo = 0;
		
		JSONObject jo = new JSONObject();
		MSport sport = null;
		//List<MSport> lst = msportDao.querySportYesterday(cid, testDate);
		List<MSport> lst = msportDao.querySportByType(cid,"yesterday");

		MSportPlan plan = planDao.queryMSportPlan(cid);
		
		if(!CollectionUtils.isEmpty(lst)){
			sport = new MSport();
			sport = lst.get(0);
		}
		if(plan != null && sport != null){
			JSONObject json = new JSONObject();
			json.put("stepsTotal", sport.getSteps());
			json.put("planStepsTotal", plan.getSteps());
			json.put("distanceTotal", MoneyFormatUtil.formatDouble(sport.getDistance()/1000));
			json.put("planDistanceTotal", plan.getDistance());
			json.put("calorieTotal", sport.getCalorie());
			json.put("planCalorieTotal", plan.getCalorie());
			
			List<MSport> lstSport = msportDao.querySportByType(null,"yesterday");
			if(!CollectionUtils.isEmpty(lstSport)){
				for(int i=0;i<lstSport.size();i++){
					MSport msport = lstSport.get(i);
					if(msport.getClientId().equals(sport.getClientId())){
						sortInfo = i+1;
					}
				}
				json.put("sortInfo", sortInfo);
			}
			jo.put("code", 1);
			jo.put("msg", "成功");
			jo.put("data", json.toString());
			
		}else{
			jo.put("code", 1);
			jo.put("msg", "您昨天没有上传运动数据");
			jo.put("data", "");
		}
		return jo.toString();
	}
	
	
	public String querySportWeek(Integer cid){
		
		return getSportInfo(cid, "week");
	}
	
	
	public String querySportLastMonth(Integer cid){
		
		return getSportInfo(cid, "month");
	}
	
	
	
	public String getSportInfo(Integer cid,String type){
		JSONObject jo = new JSONObject();
		List<MSport> lst = null;
		if(type.equals("week")){
			lst = msportDao.querySportByType(cid,"week");
		}
		if(type.equals("month")){
			lst = msportDao.querySportByType(cid,"month");
		}
		MSportPlan plan = planDao.queryMSportPlan(cid);
		double stepsTotal = 0;
		double planStepsTotal = 0;
		double distanceTotal = 0;
		double planDistanceTotal = 0;
		double calorieTotal = 0;
		double planCalorieTotal = 0;
		JSONObject json = new JSONObject();
		
		if(!CollectionUtils.isEmpty(lst)||plan!=null){
			
			if(!CollectionUtils.isEmpty(lst)){
				for (MSport mSport : lst) {
					stepsTotal += mSport.getSteps();
					distanceTotal += mSport.getDistance();
					calorieTotal += mSport.getCalorie();
				}
				json.put("stepsTotal", stepsTotal);
				json.put("distanceTotal", MoneyFormatUtil.formatDouble(distanceTotal/1000));
				json.put("calorieTotal", calorieTotal);
			}else{
				json.put("stepsTotal", 0);
				json.put("distanceTotal", 0);
				json.put("calorieTotal", 0);
			}
			if(plan!= null&&type.equals("week")){
				planStepsTotal = plan.getSteps()*7;
				planDistanceTotal = plan.getDistance()*7;
				planCalorieTotal = plan.getCalorie()*7;
				
				json.put("planStepsTotal", planStepsTotal);
				json.put("planDistanceTotal", MoneyFormatUtil.formatDouble(planDistanceTotal/1000));
				json.put("planCalorieTotal", planCalorieTotal);
			}else if(plan!= null&&type.equals("month")){
				planStepsTotal = plan.getSteps()*30;
				planDistanceTotal = plan.getDistance()*30;
				planCalorieTotal = plan.getCalorie()*30;
				
				json.put("planStepsTotal", planStepsTotal);
				json.put("planDistanceTotal", MoneyFormatUtil.formatDouble(planDistanceTotal/1000));
				json.put("planCalorieTotal", planCalorieTotal);
			}else{
				json.put("planStepsTotal", 0);
				json.put("planDistanceTotal", 0);
				json.put("planCalorieTotal", 0);
			}
			int sortInfo = 0;
			List<Object> lstObject = null;
			if(type.equals("week")){
				lstObject = msportDao.querySportSort("week");
			}
			if(type.equals("month")){
				lstObject = msportDao.querySportSort("month");
			}
			if(!CollectionUtils.isEmpty(lstObject)){
				for (int i = 0; i < lstObject.size(); i++) {
					Object[] lstObj = (Object[])lstObject.get(i);
					if(lstObj != null){
						Integer clientId = Integer.parseInt(lstObj[1].toString());
						if(cid.equals(clientId)){
							sortInfo = i+1;
						}
					}
				}
				json.put("sortInfo", sortInfo);
			}else{
				json.put("sortInfo", sortInfo);
			}
			
			jo.put("code", "1");
			jo.put("msg", "成功");
			jo.put("data", json.toString());
		}else{
			jo.put("code", "1");
			jo.put("msg", "没有上传数据");
			jo.put("data", "");
		}
		return jo.toString();
		
	}
	
	
	/**运动体质评估部分**/
	public String queryHealthSport(Integer clientId){
		JSONObject jo = new JSONObject();
		ClientInfo clientInfo = clientInfoService.get(clientId);
		Integer gender = clientInfo.getGender();
		Integer age = clientInfo.getAge();
		ClientLatestPhy phy = phyService.getClientLatestPhy(clientId);
		JSONObject json = new JSONObject();
		if(phy != null){
			String height = phy.getHeight();
			String weight = phy.getWeight();
			if(!StringUtils.isEmpty(height) && !StringUtils.isEmpty(weight)){
				//身高
				json.put("height", height);
				//体重
				json.put("weight", weight);
				
				//身体指数BMI
				String bmi = RptUtils.getStringBMI(phy.getHeight(), phy.getWeight());
				json.put("bmi", bmi);
				
				//健康体重范围
				double heig = Double.parseDouble(height);
				double lheight = (heig/100)*(heig/100)*18.5;
				double hheight = (heig/100)*(heig/100)*24;
				double lowHeight = Double.parseDouble(MoneyFormatUtil.formatToMoney(lheight, new DecimalFormat("#.0")));
				double heigHeight = Double.parseDouble(MoneyFormatUtil.formatToMoney(hheight, new DecimalFormat("#.0")));
				json.put("heightScop",lowHeight+"-"+heigHeight);
				
				//标准体重
				double weightStandard = getWeightStandard(gender, phy.getHeight());
				json.put("weightStandard", weightStandard);
				
				//基础代谢率
				int BMR = getBMR(gender, height, weight, age);
				json.put("bmr", BMR);
				
				List<Integer> lst = getHeartRate(age);
				//运动心率
				if(!CollectionUtils.isEmpty(lst)){
					json.put("heartRateScop", lst.get(0)+"-"+lst.get(1));
				}
				//体重评估结果
				String bmiResult = getBmiResult(bmi);
				json.put("bmiResult", bmiResult);
			}
		}
		jo.put("data", json.toString());
		return jo.toString();
	}
	
	/**根据身高和性别获取标准体重**/
	public double getWeightStandard(Integer gender,String height){
		double weightStandard = 0;
		if(gender != null && !StringUtils.isEmpty(height)){
			double heig = Double.parseDouble(height);
			if(gender.equals(0)){
				weightStandard = (heig-80)*0.7;
			}else{
				weightStandard = (heig-70)*0.6;
			}
		}
		return weightStandard;
	}
	
	/**根据身高，体重，年龄，性别计算基础代谢率**/
	public int getBMR(Integer gender,String height,String weight,Integer age){
		double BMR = 0;
		if(gender!=null&&!StringUtils.isEmpty(height)&&!StringUtils.isEmpty(weight)&&age!=null){
			double heig = Double.parseDouble(height);
			double weig = Double.parseDouble(weight);
			if(gender.equals("1")){
				BMR = 655+(9.6*weig)+(1.8*heig)-(4.7*age);
			}else{
				BMR = 66+(13.7*weig)+(5.0*heig)-(6.8*age);
			}
		}
		int bmrVal = Integer.parseInt(MoneyFormatUtil.formatToMoney(BMR, new DecimalFormat("#0")));
		return bmrVal;
	}
	
	/**根据年龄计算心率**/
	public List<Integer> getHeartRate(Integer age){
		List<Integer> heartRate = new ArrayList<Integer>();
		int lowHeart = 0;
		int heigHeart = 0;
		if(age != null){
			lowHeart = Integer.parseInt(MoneyFormatUtil.formatToMoney((220-age)*0.6, new DecimalFormat("#0")));
			heigHeart = Integer.parseInt(MoneyFormatUtil.formatToMoney((220-age)*0.8, new DecimalFormat("#0")));
			heartRate.add(lowHeart);
			heartRate.add(heigHeart);
		}
		return heartRate;
	}
	
	/**根据bmi计算体重评估结果**/
	/**BMI 18.5～24.9 正常， 25～29.9 超重, &amp;gt;30 肥胖
	适合亚洲人的BMI值为: 18.5～22.9 正常, 23～24.9 超重 &amp;gt;25 肥胖**/
	public String getBmiResult(String bmi){
		String bmiResult = "";
		if(!StringUtils.isEmpty(bmi)){
			double thisBmi = Double.parseDouble(bmi);
			if(thisBmi >= 18.5 && thisBmi <= 22.9){
				bmiResult = "正常";
			}else if(thisBmi >= 23 && thisBmi <= 24.9){
				bmiResult = "超重";
			}else if(thisBmi >= 25){
				bmiResult = "肥胖";
			}else if(thisBmi < 18.5){
				bmiResult = "偏瘦";
			}
		}
		return bmiResult;
	}
	
	
	/**查询今日运动成就**/
	public String queryTodaySport(){
		JSONObject jo = new JSONObject();
		
		return jo.toString();
	}

	public String queryTodaySport(Integer clientId) {
		/**sortInfo排名*/
		int sortInfo = 0;
		
		JSONObject jo = new JSONObject();
		MSport sport = null;
		//List<MSport> lst = msportDao.querySportYesterday(cid, testDate);
		List<MSport> lst = msportDao.querySportByType(clientId,"today");

		MSportPlan plan = planDao.queryMSportPlan(clientId);
		
		if(!CollectionUtils.isEmpty(lst)){
			sport = lst.get(0);
		}
		if(sport != null && plan != null){
			JSONObject json = new JSONObject();
			json.put("stepsTotal", sport.getSteps());
			json.put("planStepsTotal", plan.getSteps());
			json.put("distanceTotal", MoneyFormatUtil.formatDouble(sport.getDistance()/1000));
			json.put("planDistanceTotal", MoneyFormatUtil.formatDouble(plan.getDistance()/1000));
			json.put("calorieTotal", sport.getCalorie());
			json.put("planCalorieTotal", plan.getCalorie());
			
			List<MSport> lstSport = msportDao.querySportByType(null,"today");
			if(!CollectionUtils.isEmpty(lstSport)){
				for(int i=0;i<lstSport.size();i++){
					MSport msport = lstSport.get(i);
					if(msport.getClientId().equals(sport.getClientId())){
						sortInfo = i+1;
					}
				}
				json.put("sortInfo", sortInfo);
			}
			jo.put("code", 1);
			jo.put("msg", "成功");
			jo.put("data", json.toString());
			
		}else{
			jo.put("code", 1);
			jo.put("msg", "您今天没有上传运动数据");
			jo.put("data", "");
		}
		return jo.toString();
	}
	
	/**查询运动计划**/
	public String querySportPlan(Integer clientId){
		JSONObject jo = new JSONObject();
		MSportPlan plan = planDao.queryMSportPlan(clientId);
		ClientInfo clientInfo = clientInfoService.findClientById(clientId);
		Integer gender = clientInfo.getGender();
		ClientLatestPhy phy = phyService.getClientLatestPhy(clientId);
		JSONObject json = new JSONObject();
		if(phy != null){
			String height = phy.getHeight();
			if(!StringUtils.isEmpty(height) && !StringUtils.isEmpty(phy.getWeight())){
				if(!StringUtils.isEmpty(clientInfo.getName())){
					json.put("name", clientInfo.getName());
				}else if(!StringUtils.isEmpty(clientInfo.getNickName())){
					json.put("name",clientInfo.getNickName());
				}else{
					json.put("name", clientInfo.getMobile());
				}
				double weight = Double.parseDouble(phy.getWeight());
				double weightStandard = getWeightStandard(gender, height);
				//需要减的体重
				double jianWeight = weight - weightStandard;
				double rou = 0;
				double rice = 0;
				double jianHot = 0;
				if(jianWeight>0){
					//减的热量
					jianHot = jianWeight*404;
					rou = jianHot/404;
					rice = jianHot/58;
					//减的距离
					double jianDistance = jianHot/1.036/weight;
					//累计步数
					int steps = 0;
					if(plan != null){
						//步长
						double stepWidth = plan.getStepWidth();
						if(stepWidth != 0){
							steps = Integer.parseInt(MoneyFormatUtil.formatToMoney(jianDistance*100000/stepWidth, new DecimalFormat("#0")));
						}else{
							if(gender.equals(0)){
								steps = Integer.parseInt(MoneyFormatUtil.formatToMoney(jianDistance*100000/35, new DecimalFormat("#0")));
							}else{
								steps = Integer.parseInt(MoneyFormatUtil.formatToMoney(jianDistance*100000/30, new DecimalFormat("#0")));
							}
						}
						//计划天数
						int planDay = plan.getPlanDay();
						json.put("stepWidth", stepWidth);
						if(planDay!=0){
							json.put("planDay", planDay);
							json.put("rouDay", MoneyFormatUtil.formatDouble(rou/planDay));
							json.put("riceDay", MoneyFormatUtil.formatToMoney(rice/planDay, new DecimalFormat("#0")));
							json.put("stepsDay", MoneyFormatUtil.formatDouble(steps/planDay));
							json.put("hotDay", MoneyFormatUtil.formatDouble(jianHot/planDay));
						}else{
							json.put("planDay", 90);
							json.put("rouDay", MoneyFormatUtil.formatDouble(rou/90));
							json.put("riceDay", MoneyFormatUtil.formatToMoney(rice/90, new DecimalFormat("#0")));
							json.put("stepsDay", MoneyFormatUtil.formatDouble(steps/90));
							json.put("hotDay", MoneyFormatUtil.formatDouble(jianHot/90));
						}
					}else{
						if(gender.equals(0)){
							steps = Integer.parseInt(MoneyFormatUtil.formatToMoney(jianDistance*100000/35, new DecimalFormat("#0")));
						}else{
							steps = Integer.parseInt(MoneyFormatUtil.formatToMoney(jianDistance*100000/30, new DecimalFormat("#0")));
						}
						json.put("planDay", 90);
						json.put("rouDay", MoneyFormatUtil.formatDouble(rou/90));
						json.put("riceDay", MoneyFormatUtil.formatToMoney(rice/90, new DecimalFormat("#0")));
						json.put("stepsDay", MoneyFormatUtil.formatDouble(steps/90));
						json.put("hotDay", MoneyFormatUtil.formatDouble(jianHot/90));
					}
					json.put("type", "jian");
					json.put("steps", steps);
					json.put("hot", MoneyFormatUtil.formatDouble(jianHot));
					json.put("rou", MoneyFormatUtil.formatDouble(rou));
					json.put("rice", MoneyFormatUtil.formatToMoney(rice, new DecimalFormat("#0")));
				}else{
					jianWeight = weightStandard - weight;
					jianHot = jianWeight*404;
					rou = jianHot/404;
					rice = jianHot/58;
					if(plan != null){
						//计划天数
						int planDay = plan.getPlanDay();
						if(planDay != 0){
							json.put("planDay", planDay);
							json.put("rouDay", MoneyFormatUtil.formatDouble(rou/planDay));
							json.put("riceDay", MoneyFormatUtil.formatToMoney(rice/planDay, new DecimalFormat("#0")));
							json.put("hotDay", MoneyFormatUtil.formatDouble(jianHot/planDay));
						}else{
							json.put("planDay", 90);
							json.put("rouDay", MoneyFormatUtil.formatDouble(rou/90));
							json.put("riceDay", MoneyFormatUtil.formatToMoney(rice/90, new DecimalFormat("#0")));
							json.put("hotDay", MoneyFormatUtil.formatDouble(jianHot/90));
						}
					}else{
						json.put("planDay", 90);
						json.put("rouDay", MoneyFormatUtil.formatDouble(rou/90));
						json.put("riceDay", MoneyFormatUtil.formatToMoney(rice/90, new DecimalFormat("#0")));
						json.put("hotDay", MoneyFormatUtil.formatDouble(jianHot/90));
					}
					if(plan != null){
						//步长
						double stepWidth = plan.getStepWidth();
						json.put("stepWidth", stepWidth);
					}
					json.put("type","jia");
					json.put("rou", MoneyFormatUtil.formatDouble(rou));
					json.put("rice", MoneyFormatUtil.formatToMoney(rice, new DecimalFormat("#0")));
					json.put("hot", jianHot);
				}
			}
		}else{
			if(plan != null){
				if(plan.getPlanDay() != 0){
					json.put("planDay", plan.getPlanDay());
				}
				if(plan.getStepWidth() != 0){
					json.put("stepWidth", plan.getStepWidth());
				}else{
					if(gender != null && gender.equals(0)){
						json.put("stepWidth", 35);
					}else{
						json.put("stepWidth", 30);
					}
				}
			}
		}
		jo.put("data", json.toString());
		return jo.toString();
	}
	
	
	/**保存运动时间**/
	public String savePlanDay(MSportPlan plan){
		planService.savePlanDay(plan.getClientId(), plan.getPlanDay());
		String result = this.querySportPlan(plan.getClientId());
		return result;
	}
	
	
	/**保存步长值**/
	public String saveStepWidth(MSportPlan plan){
		planService.saveStepWidth(plan.getClientId(), plan.getStepWidth());
		String result = this.querySportPlan(plan.getClientId());
		return result;
	}
	
	
	public String queryMsportClient(Integer clientId,String type){
		JSONObject jo = new JSONObject();
		List<Object> lstObj = msportDao.queryMsportClient(clientId, type);
		JSONArray ja = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Double> lstVal = new ArrayList<Double>();
		if(!CollectionUtils.isEmpty(lstObj)){
			for (Object obj : lstObj) {
				Object[] objects = (Object[])obj;
				JSONObject json = new JSONObject();
				json.put("uploadDate", sdf.format(objects[1]));
				json.put("val", Double.parseDouble(objects[0]+""));
				lstVal.add(Double.parseDouble(objects[0]+""));
				ja.add(json);
			}
			
			jo.put("type", type);
			jo.put("data", ja.toArray());
			Collections.sort(lstVal);
			jo.put("lastVal", lstVal.get(lstVal.size()-1));
		}
		return jo.toString();
	}
	
	
	public String queryClientMsport(Integer clientId, int type, Date testDate){
		JSONObject jo = new JSONObject();
		if(clientId != null && type != 0){
			String typeInfo = "";
			if(type == 1){
				typeInfo = "deepSleep";
			}else{
				typeInfo = "calorie";
			}
			String date = DateUtils.formatDate("yyyy-MM-dd", testDate);
			Date startDate = DateUtils.getBeforeMonth(date, -2);
			Date endDate = new Date();
			
			List<Object> lst = msportDao.queryClientMsport(clientId, typeInfo, startDate, endDate);
			JSONArray ja = new JSONArray();
			if(!CollectionUtils.isEmpty(lst)){
				for (Object object : lst) {
					JSONObject json = new JSONObject();
					if(object != null){
						Object[] objs = (Object[])object;
						json.put("val", objs[0]);
						json.put("testDate", DateUtils.format(objs[1]));
					}
					ja.add(json);
				}
				jo.put("code", 1);
				jo.put("msg", "获取信息成功");
				jo.put("data", ja.toArray());
			}else{
				jo.put("code", 1);
				jo.put("msg", "您没有上传数据");
				jo.put("data", "");
			}
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数有误");
			jo.put("data", "");
		}
		return jo.toString();
	}
	
	public String queryMsportSleep(Integer clientId){
		JSONObject jo = new JSONObject();
		//当前日期所在星期星期一的日期
		Date weekStartDate = DateUtils.getDateByType(DateUtils.getThisWeekDay(1, false),"yyyy-MM-dd");
		//当前日期
		Date weekEndDate = DateUtils.getDateByType(new Date(), "yyyy-MM-dd");
		//当前日期上个星期星期一的日期
		Date lastStartDate = DateUtils.getDateByType(DateUtils.getAppointDate(weekStartDate, -7), "yyyy-MM-dd");
		//上周日的日期
		Date lastEndDate = DateUtils.getDateByType(DateUtils.getAppointDate(weekStartDate, -1), "yyyy-MM-dd");
		
		List<Object> lstWeek = msportDao.queryMsportSleepSum(clientId, weekStartDate, weekEndDate);
		List<Object> lstLastWeek = msportDao.queryMsportSleepSum(clientId, lastStartDate, lastEndDate);
		
		JSONObject json = new JSONObject();
		if(!CollectionUtils.isEmpty(lstWeek)){
			Object[] objs = (Object[])lstWeek.get(0);
			double deepSum = 0;
			if(objs[0] != null){
				deepSum = Double.parseDouble(objs[0].toString());
			}
			double lightSum = 0;
			if(objs[1] != null){
				lightSum = Double.parseDouble(objs[1].toString());
			}
			double lightestSum = 0;
			if(objs[2] != null){
				lightestSum = Double.parseDouble(objs[2].toString());
			}
			double poorSum = 0;
			if(objs[3] != null){
				poorSum = Double.parseDouble(objs[3].toString());
			}
			double sleepSum = deepSum+lightSum+lightestSum+poorSum;
			json.put("deepSum", deepSum);
			json.put("lightSum", lightSum);
			json.put("lightestSum", lightestSum);
			json.put("poorSum", poorSum);
			json.put("sleepSum", sleepSum);
		}else{
			json.put("deepSum", 0);
			json.put("lightSum", 0);
			json.put("lightestSum", 0);
			json.put("poorSum", 0);
			json.put("sleepSum", 0);
		}
		if(!CollectionUtils.isEmpty(lstLastWeek)){
			Object[] objs = (Object[])lstLastWeek.get(0);
			double deepSum = 0;
			if(objs[0] != null){
				deepSum = Double.parseDouble(objs[0].toString());
			}
			double lightSum = 0;
			if(objs[1] != null){
				lightSum = Double.parseDouble(objs[1].toString());
			}
			double lightestSum = 0;
			if(objs[2] != null){
				lightestSum = Double.parseDouble(objs[2].toString());
			}
			double poorSum = 0;
			if(objs[3] != null){
				poorSum = Double.parseDouble(objs[3].toString());
			}
			double sleepSum = deepSum+lightSum+lightestSum+poorSum;
			json.put("lastWeekDeepSum", deepSum);
			json.put("lastWeekLightSum", lightSum);
			json.put("lastWeekLightestSum", lightestSum);
			json.put("lastWeekPoorSum", poorSum);
			json.put("lastWeekSleepSum", sleepSum);
		}else{
			json.put("lastWeekDeepSum", 0);
			json.put("lastWeekLightSum", 0);
			json.put("lastWeekLightestSum", 0);
			json.put("lastWeekPoorSum", 0);
			json.put("lastWeekSleepSum", 0);
		}
		
		jo.put("code", 1);
		jo.put("msg", "获取信息成功");
		jo.put("data", json.toString());
		System.out.println(jo.toString());
		return jo.toString();
	}
	
	public void test() {
		MSport m = new MSport();
		m.setCalorie(38);
		m.setClientId(-1);
		m.setUploadDate(new Date());
		
		msportDao.add(m);
		
		m.setCalorie(40);
		msportDao.update(m);
		
		msportDao.delete(-20);
	}

	public String queryMsportCalorie(MSport msport){ 
		JSONObject jo = new JSONObject();
		MSport ms = msportDao.querySport(msport.getClientId(), msport.getTestDate());
		JSONObject json = new JSONObject();
		if(ms != null){
			jo.put("code", 1);
			jo.put("msg", "查询成功");
			json.put("calorie", ms.getCalorie());
			jo.put("data", json.toString());
		}else{
			jo.put("code", 1);
			jo.put("msg", "您没有上传数据");
			json.put("calorie", 0);
			jo.put("data", json.toString());
		}
		return jo.toString();
	}
		
}