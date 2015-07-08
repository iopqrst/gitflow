package com.bskcare.ch.service.impl.rpt;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.dao.ClientHobbyDao;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.ClientLatestPhyDao;
import com.bskcare.ch.dao.ClientMedicalHistoryDao;
import com.bskcare.ch.dao.rpt.AuditRecordDao;
import com.bskcare.ch.dao.rpt.SrptBaseInfoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientDiseaseFamilyService;
import com.bskcare.ch.service.ClientDiseaseSelfService;
import com.bskcare.ch.service.UserInfoService;
import com.bskcare.ch.service.rpt.SrptBaseInfoService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.MoneyFormatUtil;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.client.ClientHobby;
import com.bskcare.ch.vo.client.ClientLatestPhy;
import com.bskcare.ch.vo.client.ClientMedicalHistory;
import com.bskcare.ch.vo.rpt.RptAuditRecord;
import com.bskcare.ch.vo.rpt.SrptBaseInfo;


@Service
@SuppressWarnings("unchecked")
public class SrptBaseInfoServiceImpl implements SrptBaseInfoService{
	
	
	@Autowired
	private ClientInfoDao clientInfoDao;
	@Autowired
	private ClientLatestPhyDao latestPhyDao;
	@Autowired
	private ClientHobbyDao hobbyDao;
	@Autowired
	private ClientDiseaseSelfService diseaseSelfService;
	@Autowired
	private ClientDiseaseFamilyService diseaseFamilyService;
	@Autowired
	private ClientMedicalHistoryDao medicalDao;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SrptBaseInfoDao rptFullDao;
	@Autowired
	private AuditRecordDao auditRecordDao;
	
	/**生成完整健康报告**/
	public SrptBaseInfo createRptFullBaseInfo(Integer clientId,int type){
		SrptBaseInfo rptFull = new SrptBaseInfo();
		rptFull.setCreateTime(new Date());
		addClientInfo(rptFull, clientId);
		addLatestPhy(rptFull, clientId);
		addDiseaseSelf(rptFull, clientId);
		addDiseaseFamily(rptFull, clientId);
		addMesicalHistroy(rptFull, clientId);
		addClientHobby(rptFull, clientId);
		addBskExpert(rptFull, clientId);
		rptFull.setType(type);
		rptFullDao.add(rptFull);
		return rptFull;
	} 
	
	
	/**添加客户基本信息**/
	public void addClientInfo(SrptBaseInfo rptFull,Integer clientId){
		ClientInfo clientInfo = clientInfoDao.load(clientId);
		if(clientInfo != null){
			rptFull.setClientId(clientId);
			rptFull.setName(clientInfo.getName());
			rptFull.setGender(clientInfo.getGender());
			rptFull.setAge(clientInfo.getAge());
			rptFull.setMobile(clientInfo.getMobile());
			String profession = clientInfo.getProfession();
			if(!StringUtils.isEmpty(profession)){
				String professionDetail = getProfession(profession);
				rptFull.setProfession(professionDetail);
			}
		}
	}
	
	
	/**添加客户最后一次体检记录信息**/
	public void addLatestPhy(SrptBaseInfo rptFull,Integer clientId){
		ClientLatestPhy latestPhy = latestPhyDao.getClientLastestPhy(clientId);
		if(latestPhy != null){
			rptFull.setHeight(latestPhy.getHeight());
			rptFull.setWeight(latestPhy.getWeight());
			rptFull.setBreech(latestPhy.getBreech());
			rptFull.setWaist(latestPhy.getWaist());
			rptFull.setHeartRate(latestPhy.getHeartRate());
			// BMI
			if (!StringUtils.isEmpty(latestPhy.getHeight())
					&& !StringUtils.isEmpty(latestPhy.getWeight())) {
				try {
					double height = Double.parseDouble(latestPhy.getHeight());
					double weight = Double.parseDouble(latestPhy.getWeight());

					double bmi = weight / ((height * height) / 10000); // BMI
					// 计算公式：体重（kg）÷身高^2（m）
					//计算BMI
					rptFull.setBmi(MoneyFormatUtil.formatToMoney(bmi,null));
				} catch (NumberFormatException e) {

				}
			}
			
		}
	}
	
	/**添加客户既往病史信息**/
	public void addDiseaseSelf(SrptBaseInfo rptFull,Integer clientId){
		String diseaseSelf = diseaseSelfService.queryDiseaseSelf(clientId);
		if(!StringUtils.isEmpty(diseaseSelf)){
			rptFull.setDiseaseSelf(diseaseSelf);
		}
	}
	
	/**添加客户家族病史信息**/
	public void addDiseaseFamily(SrptBaseInfo rptFull,Integer clientId){
		String diseaseFamily = diseaseFamilyService.queryDiseaseFamily(clientId);
		if(!StringUtils.isEmpty(diseaseFamily)){
			rptFull.setDiseaseFamily(diseaseFamily);
		}
	}
	
	
	/**添加客户过敏史，希望尽快解决的健康问题**/
	public void addMesicalHistroy(SrptBaseInfo rptFull,Integer clientId){
		ClientMedicalHistory medicalHistory = medicalDao.getClientMedicalHistory(clientId);
		if(medicalHistory != null){
			rptFull.setHopeSolveHealth(medicalHistory.getHopeSolveHealth());
			if(medicalHistory.getIsHasAllergy()!=null&&medicalHistory.getIsHasAllergy().equals(1)){
				List allergyList = new ArrayList();
				if(!StringUtils.isEmpty(medicalHistory.getXrAllergen())){
					//allergyList.add(medicalHistory.getXrAllergen());
					String[] xrs = medicalHistory.getXrAllergen().split(",");
					for (int i = 0; i < xrs.length; i++) {
						allergyList.add(xrs[i]);
					}
				}
				if(!StringUtils.isEmpty(medicalHistory.getSrAllergen())){
					//allergyList.add(medicalHistory.getSrAllergen());
					String[] srs = medicalHistory.getSrAllergen().split(",");
					for (int i = 0; i < srs.length; i++) {
						allergyList.add(srs[i]);
					}
				}
				if(!StringUtils.isEmpty(medicalHistory.getJcAllergen())){
					//allergyList.add(medicalHistory.getJcAllergen());
					String[] jcs = medicalHistory.getJcAllergen().split(",");
					for (int i = 0; i < jcs.length; i++) {
						allergyList.add(jcs[i]);
					}
				}
				if(!StringUtils.isEmpty(medicalHistory.getZsAllergen())){
					//allergyList.add(medicalHistory.getZsAllergen());
					String[] zss = medicalHistory.getZsAllergen().split(",");
					for (int i = 0; i < zss.length; i++) {
						allergyList.add(zss[i]);
					}
				}
				if(!StringUtils.isEmpty(medicalHistory.getSelfAllergen())){
					//allergyList.add(medicalHistory.getSelfAllergen());
					String[] selfs = medicalHistory.getSelfAllergen().split(",");
					for (int i = 0; i < selfs.length; i++) {
						allergyList.add(selfs[i]);
					}
				}
				
				if(!CollectionUtils.isEmpty(allergyList)){
					String allergyDetail = ArrayUtils.join(allergyList.toArray(),"、");
					rptFull.setAllergyHistroy(allergyDetail+"过敏");
				}
			}
		}
	}

	
	/**添加客户生活习惯信息**/
	public void addClientHobby(SrptBaseInfo rptFull,Integer clientId){
		ClientHobby hobby = hobbyDao.getClientHobby(clientId);
		if(hobby != null){
			/**体力活动系数**/
			rptFull.setPhysicalType(hobby.getPhysicalType());
			/**是否喝酒**/
			rptFull.setDrink(hobby.getDrink());
			/**是否抽烟**/
			rptFull.setSmoke(hobby.getSmoke());
			/**睡眠情况**/
			rptFull.setSleeping(hobby.getSleeping());
			/**工作情况  紧张 清闲**/
			rptFull.setWorking(hobby.getWorking());
			
			rptFull.setBeer(hobby.getBeer());
			rptFull.setWhite(hobby.getWhite());
			rptFull.setRed(hobby.getRed());
			
			/**锻炼方式**/
			rptFull.setSportCount(hobby.getSportCount());
			String sportType = hobby.getSportType();
			String sportSupply = hobby.getSportSupply();
			String sportTypeDetail = "";
			List<String> lstSport = new ArrayList<String>();
			if(!StringUtils.isEmpty(sportType)){
				sportTypeDetail = sportType;
				String[] sports = sportType.split(",");
				for (int i = 0; i < sports.length; i++) {
					lstSport.add(sports[i]);
				}
			}
			if(!StringUtils.isEmpty(sportSupply)){
				lstSport.add(sportSupply);
			}
			sportTypeDetail = ArrayUtils.join(lstSport.toArray(),"、");
			
			if(!StringUtils.isEmpty(sportTypeDetail)){
				rptFull.setSportType(sportTypeDetail);
			}
			/**运动时长**/
			rptFull.setSportTime(hobby.getSportTime());
			/**运动时间带**/
			String sportTimeZone = hobby.getSportTimeZone();
			String sportZoneOther = hobby.getSportZoneOther();
			if(!StringUtils.isEmpty(sportTimeZone)){
				rptFull.setSportTimeZone(getSportTimeZone(sportTimeZone, sportZoneOther));
			}
			
			/**生活嗜好**/
			String diet = hobby.getDiet();
			if(!StringUtils.isEmpty(diet)){
				rptFull.setDiet(getDietLike(diet,hobby.getSmoke(),hobby.getDrink()));
			}
			/**不想吃的食物**/
			String notEat = hobby.getNotEat();
			if(!StringUtils.isEmpty(notEat)){
				rptFull.setNotEat(getNotEat(notEat));
			}
			/**早餐**/
			String breakfast = hobby.getBreakfast();
			String breakfastOther = hobby.getBreakfastOther();
			String breakfastDetail = getDiet(breakfast, breakfastOther);
			if(!StringUtils.isEmpty(breakfastDetail)){
				rptFull.setBreakfast(breakfastDetail);
			}
			/**中餐**/
			String lunch = hobby.getLunch();
			String lunchOther = hobby.getLunchOther();
			String lunchDetail = getDiet(lunch, lunchOther);
			if(!StringUtils.isEmpty(lunchDetail)){
				rptFull.setLunch(lunchDetail);
			}
			/**晚餐**/
			String dinner = hobby.getDinner();
			String dinnerOther = hobby.getDinnerOther();
			String dinnerDetail = getDiet(dinner, dinnerOther);
			if(!StringUtils.isEmpty(dinnerDetail)){
				rptFull.setDinner(dinnerDetail);
			}
			
			/**加餐次数**/
			Integer jiacanCount = hobby.getJiacanCount();
			if(jiacanCount != null){
				rptFull.setJiacanCount(jiacanCount);
			}
			/**加餐种类**/
			String jiacanType = hobby.getJiacanType();
			if(!StringUtils.isEmpty(jiacanType)){
				rptFull.setJiacanType(getJianCanType(jiacanType));
			}
		}
	}
	
	
	/**出健康报告的管理员**/
	public void addBskExpert(SrptBaseInfo rptFull,Integer clientId){
		ClientInfo clientInfo = clientInfoDao.load(clientId);
		if(clientInfo != null){
			String docRoleId = SystemConfig.getString("role_type_doc"); //大夫
			String hmRoleId = SystemConfig.getString("role_type_health_manger"); //健康管理师
			String docIIRoleId = SystemConfig.getString("role_type_doc_ii");//咨询大夫
			JSONArray ja = userInfoService.getAdmChainByAreaIdAndUserTypeIds(clientInfo.getAreaId(),docRoleId+","+hmRoleId+","+docIIRoleId);
			if(null != ja) {
				for (Object object : ja) {
					JSONObject json=(JSONObject) object;
					if(docRoleId.equals(json.getInt("roleType")+"")){
						rptFull.setDocName(json.getString("name"));
						break;
					}
				}
				if(null == rptFull.getDocName()) {//如果大夫没有找到则查询咨询大夫
					for (Object object : ja) {
						JSONObject json=(JSONObject) object;
						if(docIIRoleId.equals(json.getInt("roleType")+"")){
							rptFull.setDocName(json.getString("name"));
							break;
						}
					}
				}
				for (Object object : ja) {
					JSONObject json=(JSONObject) object;
					if(hmRoleId.equals(json.getInt("roleType")+"")){ 
						rptFull.setHmName(json.getString("name"));
						break;
					}
				}
			}
		}
	}
	
	/**早餐  中餐  晚餐吃什么**/
	public String getDiet(String diet,String dietOther){
		String dietDetail = "";
		List<String> lstDiet = new ArrayList<String>();
		if(!StringUtils.isEmpty(diet)){
			String [] diets = diet.split(",");
			for(String dietString:diets){
				if(dietString.equals("1")){
					lstDiet.add("粥类");
				}
				if(dietString.equals("2")){
					lstDiet.add("米饭类");
				}
				if(dietString.equals("3")){
					lstDiet.add("面食类");
				}
				if(dietString.equals("4")){
					lstDiet.add("肉类");
				}
				if(dietString.equals("5")){
					lstDiet.add("果蔬类");
				}
				if(dietString.equals("6")){
					lstDiet.add("豆及豆制品类");
				}
				if(dietString.equals("7")){
					lstDiet.add("奶类");
				}
				if(dietString.equals("8")){
					lstDiet.add("蛋类");
				}	
			}
		}
		if(!StringUtils.isEmpty(dietOther)){
			lstDiet.add(dietOther);
		}
		if(!CollectionUtils.isEmpty(lstDiet)){
			dietDetail = ArrayUtils.join(lstDiet.toArray(), "、");
		}
		return dietDetail;
	}
	
	/**加餐类型**/
	public String getJianCanType (String jiacanType){
		String jiacanTypeDetail = "";
		List<String> lstJiacanType = new ArrayList<String>();
		String [] jiacanTypes = jiacanType.split(",");
		for(String jiacan : jiacanTypes){
			if(jiacan.equals("1")){
				lstJiacanType.add("坚果类");
			}
			if(jiacan.equals("2")){
				lstJiacanType.add("牛奶");
			}
			if(jiacan.equals("3")){
				lstJiacanType.add("酸奶");
			}
			if(jiacan.equals("4")){
				lstJiacanType.add("豆浆");
			}
			if(jiacan.equals("5")){
				lstJiacanType.add("水果");
			}
			if(jiacan.equals("6")){
				lstJiacanType.add("蒸薯类");
			}
			if(jiacan.equals("7")){
				lstJiacanType.add("饼干");
			}
			if(jiacan.equals("8")){
				lstJiacanType.add("面包");
			}
			if(jiacan.equals("9")){
				lstJiacanType.add("豆类");
			}
		}
		if(!CollectionUtils.isEmpty(lstJiacanType)){
			jiacanTypeDetail = ArrayUtils.join(lstJiacanType.toArray(), "、");
		}
		return jiacanTypeDetail;
	}
	
	/**获取运动时间带**/
	public String getSportTimeZone(String sportTimeZone,String sportZoneOther){
		String sportTimeZoneDetail = "";
		List<String> lstSportTimeZone = new ArrayList<String>();
		if(!StringUtils.isEmpty(sportTimeZone)){
			String [] sportTimeZoneString = sportTimeZone.split(",");
			for(String timeZone:sportTimeZoneString){
				if(timeZone.equals("1")){
					lstSportTimeZone.add("早上5：00-8:00");
				}
				if(timeZone.equals("2")){
					lstSportTimeZone.add("早上8：00-11:00");
				}
				if(timeZone.equals("3")){
					lstSportTimeZone.add("下午2：00-6:00");
				}
				if(timeZone.equals("4")){
					lstSportTimeZone.add("下午6：00-9:00");
				}
			}
		}
		if(!StringUtils.isEmpty(sportZoneOther)){
			lstSportTimeZone.add(sportZoneOther);
		}
		if(!CollectionUtils.isEmpty(lstSportTimeZone)){
			sportTimeZoneDetail = ArrayUtils.join(lstSportTimeZone.toArray(), "、");
		}
		
		return sportTimeZoneDetail;
	}
	
	/**饮食习惯**/
	public String getDietLike(String diet,Integer smoke,Integer drink){
		String dietDetail = "";
		String [] diets = diet.split(",");
		List<String> lstDiet = new ArrayList<String>();
		for (String sdiet : diets) {
			if(sdiet.equals("1")){
				lstDiet.add("三餐不规律 ");
			}
			if(sdiet.equals("2")){
				lstDiet.add("常不吃早饭");
			}
			if(sdiet.equals("3")){
				lstDiet.add("暴饮暴食");
			}
			if(sdiet.equals("4")){
				lstDiet.add("嗜咸");
			}
			if(sdiet.equals("5")){
				lstDiet.add("嗜甜");
			}
			if(sdiet.equals("6")){
				lstDiet.add("嗜油炸");
			}
			if(sdiet.equals("7")){
				lstDiet.add("嗜辣");
			}
			if(sdiet.equals("8")){
				lstDiet.add("嗜素食");
			}
			if(sdiet.equals("9")){
				lstDiet.add("嗜肉");
			}
			if(smoke != null && smoke.equals(1)){
				lstDiet.add("抽烟");
			}
			if(drink != null && drink.equals(1)){
				lstDiet.add("喝酒");
			}
		}
		if(!CollectionUtils.isEmpty(lstDiet)){
			dietDetail = ArrayUtils.join(lstDiet.toArray(), "、");
		}
		return dietDetail;
	}
	
	/**不喜欢吃的食物**/
	public String getNotEat(String notEat){
		String notEatDetail = "";
		String [] notEats = notEat.split(",");
		List<String> lstNotEat = new ArrayList<String>();
		for (String snot : notEats) {
			if(snot.equals("1")){
				lstNotEat.add("大米类");
			}
			if(snot.equals("2")){
				lstNotEat.add("薯类");
			}
			if(snot.equals("3")){
				lstNotEat.add("杂粮类");
			}
			if(snot.equals("4")){
				lstNotEat.add("畜禽肉类及制品");
			}
			if(snot.equals("5")){
				lstNotEat.add("鱼及水产品");
			}
			if(snot.equals("6")){
				lstNotEat.add("蛋类及制品");
			}
			if(snot.equals("7")){
				lstNotEat.add("奶及制品");
			}
			if(snot.equals("8")){
				lstNotEat.add("干豆及豆制品类");
			}
			if(snot.equals("9")){
				lstNotEat.add("坚果");
			}
			if(snot.equals("10")){
				lstNotEat.add("蔬菜");
			}
			if(snot.equals("11")){
				lstNotEat.add("水果");
			}
		}
		if(!CollectionUtils.isEmpty(lstNotEat)){
			notEatDetail = ArrayUtils.join(lstNotEat.toArray(), "、");
		}
		return notEatDetail;
	}
	
	
	/**锻炼方式**/
	public String getSportType(String sportType,String sportSupply){
		String sportTypeDetail = "";
		List<String> lstSportType= new ArrayList<String>();
		if(!StringUtils.isEmpty(sportType)){
			String [] sportTypes = sportType.split(",");
			for (String sport : sportTypes) {
				if(sport.equals("1")){
					lstSportType.add("跑步");
				}
				if(sport.equals("2")){
					lstSportType.add("游泳");
				}
				if(sport.equals("3")){
					lstSportType.add("球类");
				}
				if(sport.equals("4")){
					lstSportType.add("太极拳");
				}
				if(sport.equals("6")){
					lstSportType.add("羽毛球");
				}
				if(sport.equals("7")){
					lstSportType.add("乒乓球");
				}
				if(sport.equals("8")){
					lstSportType.add("网球");
				}
				if(sport.equals("9")){
					lstSportType.add("足球");
				}
				if(sport.equals("10")){
					lstSportType.add("排球");
				}
				if(sport.equals("11")){
					lstSportType.add("篮球");
				}
				if(sport.equals("12")){
					lstSportType.add("壁球");
				}
				if(sport.equals("13")){
					lstSportType.add("棒球");
				}
				if(sport.equals("14")){
					lstSportType.add("高尔夫球");
				}
				if(sport.equals("15")){
					lstSportType.add("气功");
				}
				if(sport.equals("16")){
					lstSportType.add("广场舞");
				}
				if(sport.equals("17")){
					lstSportType.add("有氧舞蹈");
				}
				if(sport.equals("18")){
					lstSportType.add("健身操");
				}
				if(sport.equals("19")){
					lstSportType.add("柔力球");
				}
				if(sport.equals("20")){
					lstSportType.add("瑜伽");
				}
				if(sport.equals("21")){
					lstSportType.add("散步");
				}
				if(sport.equals("22")){
					lstSportType.add("慢跑");
				}
				if(sport.equals("23")){
					lstSportType.add("健步走");
				}
				if(sport.equals("24")){
					lstSportType.add("骑车/踏车");
				}
				if(sport.equals("25")){
					lstSportType.add("轮滑/滑冰");
				}
				if(sport.equals("26")){
					lstSportType.add("滑雪");
				}
				if(sport.equals("27")){
					lstSportType.add("举重");
				}
				if(sport.equals("28")){
					lstSportType.add("健身器材");
				}
			}
		}
		if(!StringUtils.isEmpty(sportSupply)){
			lstSportType.add(sportSupply);
		}
		if(!CollectionUtils.isEmpty(lstSportType)){
			sportTypeDetail = ArrayUtils.join(lstSportType.toArray(), "、");
		}
		return sportTypeDetail;
	}
	
	/****/
	public String getProfession(String profession){
		String professionDetail = "";
		List<String> lstProfession = new ArrayList<String>();
		String[] professions = profession.split(",");
		for (String pro : professions) {
			if(pro.equals("1")){
				lstProfession.add("工人");
			}
			if(pro.equals("2")){
				lstProfession.add("干部");
			}
			if(pro.equals("3")){
				lstProfession.add("科技");
			}
			if(pro.equals("4")){
				lstProfession.add("金融");
			}
			if(pro.equals("5")){
				lstProfession.add("商业");
			}
			if(pro.equals("6")){
				lstProfession.add("教师");
			}
			if(pro.equals("7")){
				lstProfession.add("医生");
			}
			if(pro.equals("8")){
				lstProfession.add("学生");
			}
			if(pro.equals("9")){
				lstProfession.add("家务");
			}
			if(pro.equals("10")){
				lstProfession.add("退休");
			}
			if(pro.equals("10")){
				lstProfession.add("待业");
			}
			if(pro.equals("10")){
				lstProfession.add("其他");
			}
		}
		if(!CollectionUtils.isEmpty(lstProfession)){
			professionDetail = ArrayUtils.join(lstProfession.toArray(), "、");
		}
		return professionDetail;
	}
	
	public PageObject queryAll(String areaChain,SrptBaseInfo srptBaseInfo,QueryInfo queryInfo) {
		 return rptFullDao.queryAll(areaChain,srptBaseInfo,queryInfo);
	}
	
	public void updateStatus(Integer id) {
		rptFullDao.updateStatus(id);
	}
	
	public SrptBaseInfo querySrptBaseInfo(Integer id){
		return rptFullDao.load(id);
	}
	
	public void auditSrptBaseInfo(Integer id,int status,RptAuditRecord audit){
		int count = rptFullDao.auditSrptBaseInfo(id, status);
		if(count>0&&audit!=null){
			auditRecordDao.add(audit);
		}
	}
	
	public SrptBaseInfo queryLatestSrpt(Integer clientId){
		return rptFullDao.queryLatestSrpt(clientId);
	}
	/**
	 * 更新报告
	 */
	public void updateSrptBaseInfo(SrptBaseInfo baseInfo) {
		rptFullDao.updateSrptBaseInfo(baseInfo);
	}
}
