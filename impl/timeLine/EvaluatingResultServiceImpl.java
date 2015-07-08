package com.bskcare.ch.service.impl.timeLine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.bo.RiskResultBean;
import com.bskcare.ch.bo.RiskResultExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.constant.ScoreConstant;
import com.bskcare.ch.dao.ClientDiseaseFamilyDao;
import com.bskcare.ch.dao.ClientHobbyDao;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.ClientLatestPhyDao;
import com.bskcare.ch.dao.ClientMedicalHistoryDao;
import com.bskcare.ch.dao.rpt.RptDiseaseDao;
import com.bskcare.ch.dao.timeLine.EvaluatingResultDao;
import com.bskcare.ch.dao.timeLine.TimeLineTaskDao;
import com.bskcare.ch.service.EvaluatingResultService;
import com.bskcare.ch.service.ntg.NTimelineTaskService;
import com.bskcare.ch.service.score.ScoreRecordService;
import com.bskcare.ch.service.tg.TgActivityService;
import com.bskcare.ch.service.tg.TgRptBaseInfoService;
import com.bskcare.ch.service.timeLine.TimeLineRuleService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.client.ClientDiseaseFamily;
import com.bskcare.ch.vo.client.ClientHobby;
import com.bskcare.ch.vo.client.ClientLatestPhy;
import com.bskcare.ch.vo.client.ClientMedicalHistory;
import com.bskcare.ch.vo.timeLine.EvaluatingResult;
import com.bskcare.ch.vo.timeLine.TimeLineRule;

@Service
public class EvaluatingResultServiceImpl implements EvaluatingResultService {
	
	/**
	 * 评测结果，1:有结果
	 */
	public static final int EVALUAT_YES = 1;
	/**
	 * 评测结果，0：没有结果
	 */
	public static final int EVALUAT_NO = 0;

	@Autowired
	private EvaluatingResultDao evaluatingResultDao;
	@Autowired
	private ClientInfoDao clientInfoDao;
	@Autowired
	private ClientHobbyDao clientHobbyDao;
	@Autowired
	private ClientLatestPhyDao latestPhyDao;
	@Autowired
	private ClientMedicalHistoryDao historyDao;
	@Autowired
	private ClientDiseaseFamilyDao diseaseFamilyDao;
	@Autowired
	private RptDiseaseDao diseaseDao;
	@Autowired
	private TimeLineTaskDao lineTaskDao;
	@Autowired
	private TimeLineRuleService lineRuleService;
	
	@Autowired
	private ScoreRecordService scoreService;
	@Autowired
	private TgActivityService activityService;
	
	@Autowired
	private NTimelineTaskService timeLineTaskService;
	@Autowired
	private TgRptBaseInfoService baseInfoService;
	

	public void saveOrUpdate(EvaluatingResult result) {
		if (result != null&&result!=null) {
			String mobile = "";
			if (!StringUtils.isEmpty(result.getResults())) {
				// 保存黄金档案
				RiskResultBean bean = (RiskResultBean) JsonUtils
						.getObject4JsonString(result.getResults(),
								RiskResultBean.class);
				ClientInfo cInfo = clientInfoDao.load(result.getClientId());
				mobile = cInfo.getMobile();
				if (cInfo != null) {
					int age = 0;
					if(!StringUtils.isEmpty(bean.getBirthday())){
						Date brithday = DateUtils.parseDate(bean.getBirthday(), "yyyy-MM-dd");
						age = DateUtils.getAgeByBirthday(brithday);
						cInfo.setAge(age);
					}
					cInfo.setGender(bean.getGender());
					if(!StringUtils.isEmpty(bean.getBirthday())){
						cInfo.setBirthday(DateUtils.parseDate(bean.getBirthday(), DateUtils.DATE_PATTERN));
					}
					clientInfoDao.update(cInfo);

					ClientHobby hobby = clientHobbyDao.getClientHobby(cInfo
							.getId());
					if (hobby == null) {
						hobby = new ClientHobby();
						hobby.setClientId(cInfo.getId());
						hobby.setPhysicalType(bean.getPhysicalType());
						hobby.setSleeping(bean.getSleep());
						clientHobbyDao.add(hobby);
					} else {
						hobby.setPhysicalType(bean.getPhysicalType());
						hobby.setSleeping(bean.getSleep());
						clientHobbyDao.update(hobby);
					}
					ClientLatestPhy latestPhy = latestPhyDao
							.getClientLastestPhy(cInfo.getId());
					if (latestPhy == null) {
						latestPhy = new ClientLatestPhy();
						latestPhy.setClientId(cInfo.getId());
						latestPhy.setWeight(bean.getWeight() + "");
						latestPhy.setHeight(bean.getHeight() + "");
						latestPhy.setWaist(bean.getWaistline() + "");
						latestPhyDao.add(latestPhy);
					} else {
						latestPhy.setWeight(bean.getWeight() + "");
						latestPhy.setHeight(bean.getHeight() + "");
						latestPhy.setWaist(bean.getWaistline() + "");
						latestPhyDao.update(latestPhy);
					}
					String disid = diseaseDao.getIdByDiseaseName("糖尿病");
					// 是否有家族糖尿病
					// 删除用户的家族糖尿病
					ClientDiseaseFamily diseaseFamily = new ClientDiseaseFamily();
					diseaseFamily.setClientId(cInfo.getId());
					diseaseFamily.setDisease(disid);
					diseaseFamily.setFamilyType(ClientDiseaseFamily.FAMILY_FA);
					diseaseFamilyDao.deleteDiseaseFamily(diseaseFamily);
					diseaseFamily.setFamilyType(ClientDiseaseFamily.FAMILY_MO);
					diseaseFamilyDao.deleteDiseaseFamily(diseaseFamily);

					// 如果用户在管血糖评测中勾选了 家族中有患有糖尿病 则把其父母都设置为糖尿病 
					
					if (bean.getIsFhistory() == 1) {
						// 添加父母的家族糖尿病
						ClientMedicalHistory history = historyDao
								.getClientMedicalHistory(cInfo.getId());
						if (history == null) {
							history = new ClientMedicalHistory();
							history.setClientId(cInfo.getId());
							history
									.setIsHasFamilyHealth(ClientMedicalHistory.CLIENT_MEDICAL_HISTORY_YES);
							historyDao.add(history);
						} else {
							history
									.setIsHasFamilyHealth(ClientMedicalHistory.CLIENT_MEDICAL_HISTORY_YES);
							historyDao.update(history);
						}
						diseaseFamily.setClientId(cInfo.getId());
						diseaseFamily
								.setType(ClientDiseaseFamily.DISEASE_CHECKED_YES);
						diseaseFamily.setDisease(disid);
						diseaseFamily
								.setFamilyType(ClientDiseaseFamily.FAMILY_MO);
						diseaseFamilyDao.add(diseaseFamily);
						diseaseFamily
								.setFamilyType(ClientDiseaseFamily.FAMILY_FA);
						diseaseFamilyDao.add(diseaseFamily);
					}
				}
			}
			if (result.getId() != null) {
				evaluatingResultDao.update(result);
			} else {
				result=evaluatingResultDao.add(result);
				/*
				 * 评估完成发送激活码
					
					if(!StringUtils.isEmpty(mobile)){
						String mobileKey = Base64.encode(mobile);
						activityService.tgActivitySendCard(mobile, mobileKey);
					}
				
				if(!DateUtils.isOutNowTime("03:00:00")){//判断用户上传时间在3点以后，重新生成时间轴
					//删除用户时间轴，为用户重新生成
					lineTaskDao.deleteTaskByUpevaluating(result.getClientId(),TimeLineRule.CONTYPE_EATMEDICINE,1);
					lineRuleService.addTaskByEvaluating(result.getSoftType(), result,null);
					//如果自动生成之后，重新生成
				}
				*/
				
				
				//新版血糖高管时间轴
				timeLineTaskService.createTimeLine(result.getClientId(), result, Constant.SOFT_GUAN_XUE_TANG);
				//如果自动生成之后，重新生成
				
				//生成健康报告
				RiskResultBean bean = (RiskResultBean) JsonUtils.getObject4JsonString(
						result.getResults(), RiskResultBean.class);
				baseInfoService.createTgRptBaseInfo(result.getClientId(), bean, result.getId());
			}
		}

	}

	/**
	 * 查询用户是否参与相关软件评测
	 */
	public int evaluateType(int evalType, ClientInfo qci) {
		EvaluatingResult er = new EvaluatingResult();
		er.setClientId(qci.getId());
		er.setSoftType(evalType);
		List<EvaluatingResult> list = evaluatingResultDao
				.queryResultsByClientId(er);
		if (!CollectionUtils.isEmpty(list)) {
			return EVALUAT_YES;
		} 
		return EVALUAT_NO;
	}

	public JSONObject whetherCanEvaluate(Integer cid , int softType) {
		/*
		 * 用户首次登陆后可以评估三次，三次以后每月可以评估一次
		 * 首次登陆三次机会不包括当月的那一次机会
		 * */
		JSONObject type = new JSONObject();
		EvaluatingResult er = new EvaluatingResult();
		er.setClientId(cid);
		er.setSoftType(softType);
		List<EvaluatingResult> list = evaluatingResultDao
				.queryResultsByClientId(er);//查询用户的评测记录
		if(list.size() < 3){//如果评测次数小于3 ， 返回可评测
			type.put("type", 1);
		}else if(list.size() == 3){//如果评测3次，说明还能进行评测
			type.put("type", 2);
		}else{//如果大于三次
			er = list.get(0);
			er.getTestDate();
			if(!DateUtils.formatDate("yyyy-MM", er.getTestDate()).equals(DateUtils.formatDate("yyyy-MM", new Date()))){//判断最后一次评估是不是当月
				type.put("type", 2);//如果不是当月，可以评测
			}else{
				type.put("type", 3);//如果是当月，不可评测
			}
		}
		return type;
	}
	
	
	public String queryEvaluatingResult(Integer cid , int softType){
		JSONObject jo = new JSONObject();
		if(cid != null){
			EvaluatingResult er = new EvaluatingResult();
			er.setClientId(cid);
			er.setSoftType(softType);
			RiskResultExtend risk = new RiskResultExtend();
			ClientInfo client = clientInfoDao.load(cid);
			if(client != null && !StringUtils.isEmpty(client.getName())){
				risk.setName(client.getName());
			}
			//臀围
			ClientLatestPhy phy = latestPhyDao.getClientLastestPhy(cid);
			if(phy != null && !StringUtils.isEmpty(phy.getBreech())){
				risk.setBreech(phy.getBreech());
			}
			EvaluatingResult eval = evaluatingResultDao.queryLastEval(er);
			if(eval != null && !StringUtils.isEmpty(eval.getResults())){
				RiskResultBean bean = (RiskResultBean) JsonUtils.getObject4JsonString(
						eval.getResults(), RiskResultBean.class);
				risk.setGender(bean.getGender());
				risk.setBirthday(bean.getBirthday());
				risk.setWeight(bean.getWeight());
				risk.setHeight(bean.getHeight());
				//腰围
				risk.setWaistline(bean.getWaistline());
				risk.setPhysicalType(bean.getPhysicalType());
				risk.setIsSport(bean.getIsSport());
				risk.setSleep(bean.getSleep());
				risk.setIsFhistory(bean.getIsFhistory());
				risk.setBloodPressure(bean.getBloodPressure());
				risk.setLipids(bean.getLipids());
				risk.setGestation(bean.getGestation());
				
				if(StringUtils.isEmpty(bean.getComplications())){
					risk.setComplications("无");
				}else{
					String comp = setComplications(bean.getComplications());
					risk.setComplications(comp);
				}
			}
			
			jo.put("code", 1);
			jo.put("msg", "查询成功");
			jo.put("data", JsonUtils.getJsonString4JavaPOJO(risk));
			
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数有误");
			jo.put("data", "");
		}
		
		return jo.toString();
	}
	
	
	public String setComplications(String complications){
		String complication = "";
		String[] comps = complications.split(",");
		List<String> lstComp = new ArrayList<String>();
		if(comps != null){
			for (String comp : comps) {
				if(comp.equals("1")){//糖尿病合并冠心病
					lstComp.add("糖尿病冠心病");
				}else if(comp.equals("2")){//糖尿病合并高血压
					lstComp.add("高血压");
				}else if(comp.equals("3")){//糖尿病合并肾病
					lstComp.add("糖尿病肾病");
				}else if(comp.equals("4")){//糖尿病合并脑梗
					lstComp.add("糖尿病脑梗");
				}else if(comp.equals("5")){//糖尿病合并视网膜病变
					lstComp.add("糖尿病视网膜病变");
				}else if(comp.equals("6")){//糖尿病合并下肢静脉栓塞
					lstComp.add("下肢静脉栓塞");
				}else if(comp.equals("7")){//糖尿病足
					lstComp.add("糖尿病足");
				}
			}
		}
		
		if(!CollectionUtils.isEmpty(lstComp)){
			complication = ArrayUtils.join(lstComp.toArray(), ",");
		}
		return complication;
	}
	
}
