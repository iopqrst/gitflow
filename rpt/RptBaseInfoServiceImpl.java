package com.bskcare.ch.service.impl.rpt;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.BloodOxygenDao;
import com.bskcare.ch.dao.BloodPressureDao;
import com.bskcare.ch.dao.ClientDiseaseFamilyDao;
import com.bskcare.ch.dao.ClientDiseaseSelfDao;
import com.bskcare.ch.dao.ClientFamilyHistoryDao;
import com.bskcare.ch.dao.ClientHobbyDao;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.ClientLatestPhyDao;
import com.bskcare.ch.dao.ClientMedicalHistoryDao;
import com.bskcare.ch.dao.ConstitutionDao;
import com.bskcare.ch.dao.ElectrocardiogramDao;
import com.bskcare.ch.dao.rpt.AuditRecordDao;
import com.bskcare.ch.dao.rpt.RptBaseInfoDao;
import com.bskcare.ch.dao.rpt.RptDietDao;
import com.bskcare.ch.dao.rpt.RptMonitoringDataDao;
import com.bskcare.ch.dao.rpt.RptSportDao;
import com.bskcare.ch.dao.rpt.SportPlanDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.UserInfoService;
import com.bskcare.ch.service.rpt.RptBaseInfoService;
import com.bskcare.ch.service.rpt.SurveyQuestionService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.MoneyFormatUtil;
import com.bskcare.ch.util.RptUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.BloodOxygen;
import com.bskcare.ch.vo.BloodPressure;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.Electrocardiogram;
import com.bskcare.ch.vo.client.ClientDiseaseFamily;
import com.bskcare.ch.vo.client.ClientDiseaseSelf;
import com.bskcare.ch.vo.client.ClientFamilyHistory;
import com.bskcare.ch.vo.client.ClientHobby;
import com.bskcare.ch.vo.client.ClientLatestPhy;
import com.bskcare.ch.vo.client.ClientMedicalHistory;
import com.bskcare.ch.vo.rpt.RptAuditRecord;
import com.bskcare.ch.vo.rpt.RptBaseInfo;
import com.bskcare.ch.vo.rpt.RptSport;

@Service
@SuppressWarnings("unchecked")
public class RptBaseInfoServiceImpl implements RptBaseInfoService{
	//用户的基本信息
	@Autowired
	private ClientInfoDao clientInfoDao;
	//用户的最后一次体检
	@Autowired
	private ClientLatestPhyDao latestPhyDao;
	//用户的既往病史
	@Autowired
	private ClientMedicalHistoryDao medicalHistoryDao;
	//用户的家族病史
	@Autowired
	private ClientFamilyHistoryDao familyHistoryDao;
	//用户的生活方式
	@Autowired
	private ClientHobbyDao hobbyDao;
	@Autowired
	private RptBaseInfoDao rptBaseInfoDao;
	
	//获取主体质
	@Autowired
	private ConstitutionDao cmcDao;
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private ClientDiseaseSelfDao diseaseSelfDao;
	
	@Autowired
	private ClientDiseaseFamilyDao diseaseFamilyDao;
	
//	private BskExpertService bskExpertService;
	
	@Autowired
	private SurveyQuestionService surveyQuestionService;
	
	//血压
	@Autowired
	private BloodPressureDao bpDao;
	//心率
	@Autowired
	private ElectrocardiogramDao edDao;
	
	@Autowired
	private BloodOxygenDao oxygenDao;
	
	@Autowired
	private AuditRecordDao auditDao;
	@Autowired
	private RptSportDao rptSportDao;
	
	/**运动计划**/
	@Autowired
	private SportPlanDao planDao;
	/**运动**/
	@Autowired
	private RptSportDao sportDao;
	/**膳食**/
	@Autowired
	private RptDietDao dietDao;
	/**监测数据**/
	@Autowired
	private RptMonitoringDataDao monDateDao;
	
	public RptBaseInfo addRptBaseInfoByClientId(Integer clientId,String subTime){
		//健康报告个人基本信息
		RptBaseInfo rptBaseInfo = new RptBaseInfo();
		rptBaseInfo.setCreateTime(new Date());
		rptBaseInfo.setDocStatus(RptBaseInfo.RPT_STATUS_AUTO);
		rptBaseInfo.setDietStatus(RptBaseInfo.RPT_STATUS_AUTO);
		rptBaseInfo.setSportStatus(RptBaseInfo.RPT_STATUS_AUTO);
		rptBaseInfo.setStatus(RptBaseInfo.RPT_STATUS_AUTO);
		
		String survey = surveyQuestionService.saveAnswerTipMsg();
		rptBaseInfo.setSurveyMsg(survey);
		
		//主体质
		Object mCmc = cmcDao.findLatestCmcByClientId(clientId);
		if(mCmc != null){
			rptBaseInfo.setMainCmc(mCmc.toString());
		}
		
		addClientInfo(rptBaseInfo,clientId);
		addLatestPhy(rptBaseInfo,clientId);
		addHobby(rptBaseInfo,clientId);
		addMedicalHistory(rptBaseInfo,clientId);
		addFamilyHistory(rptBaseInfo,clientId);
		addBskExpert(rptBaseInfo,clientId);
		addBloodPressureAverage(rptBaseInfo,clientId,subTime);
		addElectrocaedingramAverage(rptBaseInfo,clientId,subTime);
		addOxygenSaturation(rptBaseInfo,clientId,subTime);
		
		rptBaseInfoDao.add(rptBaseInfo);
		return rptBaseInfo;
	}
	
	/**
	 * 设置血氧饱和度
	 */
	private void addOxygenSaturation(RptBaseInfo rptBaseInfo, Integer clientId,
			String subTime) {
		
		BloodOxygen bo = new BloodOxygen();
		bo.setClientId(clientId);

		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setBeginTime(DateUtils.getBeforeMonth(subTime,-3));
		queryCondition.setEndTime(DateUtils.getBeforeMonth(subTime,0));

		Object obj = oxygenDao.queryLimiteSpO2(queryCondition, bo);
		Object[] oarray = (Object[]) obj;

		if (oarray.length == 4) {
			if (null != oarray[0] && null != oarray[1]) {
				Integer v0 = Integer.parseInt(oarray[0] + ""); // avg(bloodOxygen)
//				Integer v1 = Integer.parseInt(oarray[1] + ""); // avg(heartbeat)
				rptBaseInfo.setOxygenAverage(v0+"");
			}
		}
	}

	//个人基本信息
	private void addClientInfo(RptBaseInfo rptBaseInfo,Integer clientId){
		ClientInfo clientInfo  = clientInfoDao.load(clientId);;
		if(clientInfo != null){
			rptBaseInfo.setClientId(clientInfo.getId());
			rptBaseInfo.setName(clientInfo.getName());
			if(clientInfo.getGender()!=null){
				rptBaseInfo.setGender(clientInfo.getGender());
			}
			
			rptBaseInfo.setMobile(clientInfo.getMobile());
			Integer age = clientInfo.getAge();
			if(age != null){
				rptBaseInfo.setAge(age);
			}
		}
	}
	
	//最后一次添加记录
	private void addLatestPhy(RptBaseInfo rptBaseInfo,Integer clientId){
		ClientLatestPhy latestPhy = latestPhyDao.getClientLastestPhy(clientId);
		if(latestPhy != null){
			rptBaseInfo.setHeight(latestPhy.getHeight());
			rptBaseInfo.setWeight(latestPhy.getWeight());
			
			//设置BMI
			rptBaseInfo.setBmi(RptUtils.getStringBMI(latestPhy.getHeight(), 
					latestPhy.getWeight()));
			
			rptBaseInfo.setSbp(latestPhy.getSbp());
			rptBaseInfo.setDbp(latestPhy.getDbp());
			rptBaseInfo.setGlu(latestPhy.getGlu());
			rptBaseInfo.setTc(latestPhy.getTc());
			rptBaseInfo.setTg(latestPhy.getTlc());
			rptBaseInfo.setHdl(latestPhy.getHdl());
			rptBaseInfo.setLdl(latestPhy.getLdl());
		}
	}
	
	//个人生活方式
	private void addHobby(RptBaseInfo rptBaseInfo,Integer clientId){
		ClientHobby hobby = hobbyDao.getClientHobby(clientId);
		if(hobby != null){
			Integer smoke = hobby.getSmoke();
			//1：抽烟   2：不抽烟
			rptBaseInfo.setSmoke(smoke);
			if(hobby.getAverage() != null){
				rptBaseInfo.setAverage(hobby.getAverage());
			}
			rptBaseInfo.setSyear(hobby.getSyear());
			Integer drink = hobby.getDrink();
			//1：饮酒   2：不饮酒
			rptBaseInfo.setDrink(drink);
			ClientInfo clientInfo = clientInfoDao.load(clientId);
			//开始喝酒年龄
			Integer dage = hobby.getDage();
			Integer age = clientInfo.getAge();
			if(dage != null&&dage<age){
				Integer drinkYear = age - hobby.getDage();
				rptBaseInfo.setDyear(drinkYear);
			}
			rptBaseInfo.setWhite(hobby.getWhite());
			rptBaseInfo.setBeer(hobby.getBeer());
			rptBaseInfo.setRed(hobby.getRed());
			//工作
			rptBaseInfo.setWorking(hobby.getWorking());
			//睡觉
			rptBaseInfo.setSleeping(hobby.getSleeping());
			//饮食习惯
			if(!StringUtils.isEmpty(hobby.getDiet())){
				rptBaseInfo.setDiet(hobby.getDiet());
			}
			rptBaseInfo.setSportCount(hobby.getSportCount());
			rptBaseInfo.setSportTime(hobby.getSportTime());
			//运动类型
			rptBaseInfo.setSportType(hobby.getSportType());
			
			if(!StringUtils.isEmpty(hobby.getSportSupply())){
				rptBaseInfo.setSportSupply(hobby.getSportSupply());
			}
			rptBaseInfo.setPhysicalType(hobby.getPhysicalType()); //体力活动
		}
	}
	
	//既往健康病史
	private void addMedicalHistory(RptBaseInfo rptBaseInfo,Integer clientId){
		ClientMedicalHistory  medicalHistory = medicalHistoryDao.getClientMedicalHistory(clientId);
		//既往健康病史
		if(medicalHistory != null){
			List medicalList = new ArrayList();
			Integer isHasMedical = medicalHistory.getIsHasMedical();
			if(isHasMedical != null){
				//1：有既往病史
				if(isHasMedical.equals(1)){
					List<Object> lstObject = diseaseSelfDao.queryDiseaseSelf(clientId);
					if(!CollectionUtils.isEmpty(lstObject)){
						for (Object object : lstObject) {
							Object [] objs = (Object[])object;
							if(objs!=null&&objs.length>0){
								if(objs[0] != null){
									ClientDiseaseSelf self = (ClientDiseaseSelf)objs[0];
									if(self != null){
										Object oname = objs[1];
										if(oname != null){
											String name = oname.toString();
											medicalList.add(name);
										}
									}
								}
							}
						}
					}
				}
			}
			if(!CollectionUtils.isEmpty(medicalList)){
				rptBaseInfo.setMedicalHistory(ArrayUtils.join(medicalList.toArray(),","));
			}
			//手术史  1：有手术史
			if(medicalHistory.getSurgery()!=null&&medicalHistory.getSurgery().equals(1)){
				rptBaseInfo.setSurgicalHistory(medicalHistory.getSurgeryDetail());
			}
			//输血史   2：有输血史
			if(medicalHistory.getTransfusionOfBlood()!= null&&medicalHistory.getTransfusionOfBlood().equals(1)){
				rptBaseInfo.setTransfusionOfBlood(medicalHistory.getSupply());
			}
			//外伤史   3：有外伤史
			if(medicalHistory.getTrauma()!= null&&medicalHistory.getTrauma().equals(1)){
				rptBaseInfo.setTraumaHistory(medicalHistory.getDetail());
			}
			List allergyList = new ArrayList();
			//过敏史    1：有过敏史
			if(medicalHistory.getIsHasAllergy()!=null&&medicalHistory.getIsHasAllergy().equals(1)){
				if(!StringUtils.isEmpty(medicalHistory.getXrAllergen())){
					allergyList.add(medicalHistory.getXrAllergen());
				}
				if(!StringUtils.isEmpty(medicalHistory.getSrAllergen())){
					allergyList.add(medicalHistory.getSrAllergen());
				}
				if(!StringUtils.isEmpty(medicalHistory.getJcAllergen())){
					allergyList.add(medicalHistory.getJcAllergen());
				}
				if(!StringUtils.isEmpty(medicalHistory.getZsAllergen())){
					allergyList.add(medicalHistory.getZsAllergen());
				}
				if(!StringUtils.isEmpty(medicalHistory.getSelfAllergen())){
					allergyList.add(medicalHistory.getSelfAllergen());
				}
			}
			if(!CollectionUtils.isEmpty(allergyList)){
				String allergyDetail = ArrayUtils.join(allergyList.toArray(),",");
				rptBaseInfo.setAllergyHistory(allergyDetail+"过敏");
			}
		}
	}
	
	//家族健康史
	private void addFamilyHistory(RptBaseInfo rptBaseInfo,Integer clientId){
		ClientFamilyHistory familyHistory = familyHistoryDao.getClientFamilyHistory(clientId);
		//家族病史
		if(familyHistory != null){
			List familyList = new ArrayList();
			Integer isHasFamily = familyHistory.getIsHasFamilyHealth();
			if(isHasFamily != null){
				//1：有家族病史
				if(isHasFamily.equals(1)){
					List<Object> lstObject = diseaseFamilyDao.queryDiseaseFamily(clientId);
					if(!CollectionUtils.isEmpty(lstObject)){
						for (Object object : lstObject) {
							Object [] objs = (Object[])object;
							if(objs!=null&&objs.length>0){
								if(objs[0] != null){
									ClientDiseaseFamily family = (ClientDiseaseFamily)objs[0];
									if(family != null&&family.getType()==0){
										Object oname = objs[1];
										if(oname != null){
											String name = oname.toString();
											if(family.getFamilyType()==0){
												familyList.add("父亲"+name);
											}else if(family.getFamilyType()==1){
												familyList.add("母亲"+name);
											}
										}
									}else if(family != null&&family.getType()==1){
										String name = family.getDisease();
										if(!StringUtils.isEmpty(name)){
											if(family.getFamilyType()==0){
												familyList.add("父亲"+name);
											}else{
												familyList.add("母亲"+name);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if(!CollectionUtils.isEmpty(familyList)){
				rptBaseInfo.setFamilyHistory(ArrayUtils.join(familyList.toArray(),","));
			}
		}
	}
	
	//出健康报告的管理员
	private void addBskExpert(RptBaseInfo rptBaseInfo,Integer clientId){
		
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
						rptBaseInfo.setDocName(json.getString("name"));
						break;
					}
				}
				
				if(null == rptBaseInfo.getDocName()) {//如果大夫没有找到则查询咨询大夫
					for (Object object : ja) {
						JSONObject json=(JSONObject) object;
						if(docIIRoleId.equals(json.getInt("roleType")+"")){
							rptBaseInfo.setDocName(json.getString("name"));
							break;
						}
					}
				}
				
				for (Object object : ja) {
					JSONObject json=(JSONObject) object;
					if(hmRoleId.equals(json.getInt("roleType")+"")){ 
						rptBaseInfo.setHmName(json.getString("name"));
						break;
					}
				}
			}
			
			/*
			List<BskExpert> lstBskExpert = bskExpertService.queryExpertByAreaId(clientInfo.getAreaId());
			if(!CollectionUtils.isEmpty(lstBskExpert)){
				for (BskExpert bskExpert : lstBskExpert) {
					//1：医生
					if(bskExpert.getRole()==1){
						rptBaseInfo.setDocName(bskExpert.getName());
					}
					//2：健康管理师
					if(bskExpert.getRole()==2){
						rptBaseInfo.setHmName(bskExpert.getName());
					}
				}
			}*/
		}
	}
	
	//血压的平均值
	private void addBloodPressureAverage(RptBaseInfo rptBaseInfo,Integer clientId,String subTime){
		BloodPressure bp = new BloodPressure();
		bp.setClientId(clientId);
		
		QueryCondition qc = new QueryCondition();
		qc.setBeginTime(DateUtils.getBeforeMonth(subTime,-3));
		qc.setEndTime(DateUtils.getBeforeMonth(subTime,0));
		
		Object obj = bpDao.queryPressureAverage(qc, bp);
		Object[] oarray = (Object[]) obj;
		
		if(oarray.length == 2) {
			if(oarray[0]!=null && oarray[1]!=null){
				int sdp = (int) Math.round(Double.parseDouble(oarray[0].toString()));
				int dbp = (int) Math.round(Double.parseDouble(oarray[1].toString()));
				rptBaseInfo.setBloodPressureAverage(sdp+"/"+dbp);
			}
		}
	}
	
	//心率平均值
	private void addElectrocaedingramAverage(RptBaseInfo rptBaseInfo,Integer clientId,String subTime){
		Electrocardiogram ed = new Electrocardiogram();
		ed.setClientId(clientId);
		
		QueryCondition qc = new QueryCondition();
		qc.setBeginTime(DateUtils.getBeforeMonth(subTime,-3));
		qc.setEndTime(DateUtils.getBeforeMonth(subTime,0));
		
		Object obj = edDao.queryElectrocardiogramAverage(qc, ed);
		if(obj!=null){
			Double heart = Double.parseDouble(obj.toString());
			String heartReat = ""+Math.round(heart);
			rptBaseInfo.setHeartReatAverage(heartReat);
		}
		
	}
	
	public RptBaseInfo findRptBaseInfo(Integer rptId){
		return rptBaseInfoDao.load(rptId);
	}
	
	public int updateRptBaseByField(String field,String content, Integer rptId){
		return rptBaseInfoDao.updateRptBaseByField(field, content,rptId);
	}
	
	
	public PageObject findAutoRptByUserId(String areaChain,QueryInfo info, RptBaseInfo rpt,Integer userId,QueryCondition queryCondition ,Date beginTime ,Date endTime){
		return rptBaseInfoDao.findAutoRptByUserId(areaChain,info, rpt, userId, queryCondition , beginTime ,endTime);
	}
	
	public PageObject findRptList(RptBaseInfo rptBaseInfo,Integer clientId,QueryInfo queryInfo){
		return rptBaseInfoDao.findRptList(rptBaseInfo, clientId, queryInfo);
	} 
	
	
	public String findRptListToJson(RptBaseInfo rptBaseInfo,Integer clientId,QueryInfo queryInfo){
		PageObject list = rptBaseInfoDao.findRptList(rptBaseInfo, clientId, queryInfo);
		JSONObject jo = new JSONObject();
		jo.put("total",list.getTotalRecord()); 
		jo.put("list", JsonUtils.getJsonString4JavaListDate(list.getDatas()));
		
		return jo.toString();
	}
	
	public int updateRptRead(Integer rptId){
		return rptBaseInfoDao.updateRptRead(rptId);
	}
	
	public void updateRptStatus(String content,String field ,Integer rptId,RptAuditRecord audit){
		if(content.equals("noLatest")){
			rptBaseInfoDao.updateRptStatus(field, rptId);
		}else{
			rptBaseInfoDao.updateRptStatus(field, rptId);
			rptBaseInfoDao.updateRptStatus("status", rptId);
		}
		if(audit != null){
			auditDao.add(audit);
		}
	}
	
	public int latestAudit(String field,Integer rptId){
		Object obj = rptBaseInfoDao.latestAudit(field, rptId);
		int count = 0;
		if(obj != null){
			count = Integer.parseInt(obj.toString());
		}
		return count;
	}
	
	public int updateRptByField(String field,Integer content, Integer rptId){
		return rptBaseInfoDao.updateRptByField(field, content, rptId);
	}

	public String findRptBaseInfoByClientId(Integer clientId) {
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		List<RptBaseInfo> list = rptBaseInfoDao.findRptBaseInfoByClientId(clientId);
		if(!CollectionUtils.isEmpty(list)) {
			RptBaseInfo baseInfo = list.get(0);
			json2.put("bloodPressureAverage", baseInfo.getBloodPressureAverage());
			json2.put("heartReatAverage", baseInfo.getHeartReatAverage());
			RptSport rptSport = rptSportDao.findRptSport(baseInfo.getRptId(), clientId);
			if(null != rptSport) {
				json2.put("sportType", rptSport.getSportType());
				json2.put("sportIntensity", rptSport.getSportIntensity());
				json2.put("sportTime", rptSport.getSportTime());
				json2.put("sportFrequency", rptSport.getSportFrequency());
				json1.put("code", 1);
				json1.put("msg", "成功");
				json1.put("data", json2);
			} else {
				json1.put("code", 0);
				json1.put("msg", "失败");
				json1.put("data", "");	
			}
		} else {
			json1.put("code", 0);
			json1.put("msg", "失败");
			json1.put("data", "");	
		}
		return json1.toString();
	}
	
	
	public RptBaseInfo queryLatestRptBaseInfo(Integer clientId){
		return rptBaseInfoDao.queryLatestRptBaseInfo(clientId);
	}
	
	public void deleteRptById(Integer rptId){
		RptBaseInfo baseInfo = rptBaseInfoDao.load(rptId);
		if(baseInfo != null){
			rptBaseInfoDao.updateRptByField("status", RptBaseInfo.RPT_STATUS_DELETE, rptId);
		}
	}
	
	
	public void deleteRptByIdRel(Integer rptId){
		RptBaseInfo baseInfo = rptBaseInfoDao.load(rptId);
		if(baseInfo != null){
			if(baseInfo.getStatus() != RptBaseInfo.RPT_STATUS_COMMIT){
				//删除健康报告基本信息部分
				rptBaseInfoDao.delete(rptId);
				dietDao.deleteDietByRptId(rptId);
				monDateDao.deleteMonDataByRptId(rptId);
				sportDao.deleteSportByRptId(rptId);
				planDao.deletePlanByRptId(rptId);
				auditDao.deleteAuditByRptId(rptId,RptAuditRecord.RPT_TYPE_ALL);
			}else{
				rptBaseInfoDao.updateRptByField("status", RptBaseInfo.RPT_STATUS_DELETE, rptId);
			}
		}
	}
	/**
	 * 返回报告列表 ， type 1：是完整报告 2： 10天简版报告 3： 月度简版报告
	 */
	public PageObject findRptListByUserId(Integer cid, QueryInfo info, Integer type,
			String status) {
		return rptBaseInfoDao.findRptListByUserId(cid, info, type, status);//
	}
	
	

	public String findRptListToJson(Integer cid, QueryInfo info, Integer type,
			String status) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		PageObject list = rptBaseInfoDao.findRptListByUserId(cid, info, type, status);
		JSONObject jo = new JSONObject();
		jo.put("total",list.getTotalRecord()); 
		JSONObject joitem;
		JSONArray ja =new JSONArray();
		for (Object obj : list.getDatas()) {
			Object[] objs=(Object[]) obj;
			joitem =new JSONObject();
			joitem.put("id", objs[0]);
			joitem.put("clientId", objs[1]);
			joitem.put("name", objs[2]);
			joitem.put("type", objs[3]);
			joitem.put("isRead", objs[4]);
			joitem.put("status", objs[5]);
			joitem.put("createTime",  format.format( objs[6]));
			joitem.put("beginTime",  format.format( objs[7]));
			ja.add(joitem);
		}
		jo.put("list", ja.toString());
		return jo.toString();
	}

}
