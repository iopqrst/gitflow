package com.bskcare.ch.service.impl.tg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.test.JSONAssert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.bo.RiskResultBean;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.tg.TgRptBaseInfoDao;
import com.bskcare.ch.dao.timeLine.EvaluatingResultDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientInfoService;
import com.bskcare.ch.service.tg.TgRptBaseInfoService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.MoneyFormatUtil;
import com.bskcare.ch.util.RptUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.tg.TgRptBaseInfo;
import com.bskcare.ch.vo.timeLine.EvaluatingResult;

@Service
@SuppressWarnings("unchecked")
public class TgRptBaseInfoServiceImpl implements TgRptBaseInfoService{
	
	@Autowired
	private ClientInfoService clientInfoService;
	@Autowired
	private EvaluatingResultDao evalResultDao;
	@Autowired
	private TgRptBaseInfoDao tgRptDao;
	
	public String createTgRptBaseInfo(Integer clientId, RiskResultBean riskResult, Integer evalId){
		if(clientId != null && riskResult != null){
			TgRptBaseInfo tgRpt = new TgRptBaseInfo();
			//糖尿病评测结果id
			tgRpt.setEvalId(evalId);
			tgRpt.setClientId(clientId);
			ClientInfo clientInfo = clientInfoService.findClientById(clientId);
			if(clientInfo == null) return "";
			//客户姓名
			if(!StringUtils.isEmpty(clientInfo.getName())){
				tgRpt.setName(clientInfo.getName());
			}else if(!StringUtils.isEmpty(clientInfo.getMobile())){
				tgRpt.setName(clientInfo.getMobile());
			}
			//发病概率
			if(riskResult.getResult().equals("低危") || riskResult.getResult().equals("高危")){
				tgRpt.setProbability(MoneyFormatUtil.formatDouble((riskResult.getProbability()/0.7)));
			}else{
				tgRpt.setProbability(MoneyFormatUtil.formatDouble(riskResult.getProbability()));
			}
			
			//上次评测的结果信息
			RiskResultBean lastRiskResult = null;
			
			EvaluatingResult eval = new EvaluatingResult();
			eval.setClientId(clientId);
			eval.setSoftType(Constant.SOFT_GUAN_XUE_TANG);
	
			//客户的评测信息
			List<EvaluatingResult> lstEval = evalResultDao.queryResultsByClientId(eval);
			int tgrptCount = tgRptDao.queryTgRptCount(clientId);
			//如果如果只有一条数据说明是第一次评测，按照第一次评测报告的模板
			if(!CollectionUtils.isEmpty(lstEval) && lstEval.size() > 1 && tgrptCount > 1){
				String result = lstEval.get(1).getResults();
				lastRiskResult = (RiskResultBean)JsonUtils.getObject4JsonString(result, RiskResultBean.class);
				//上次的评测结果
				tgRpt.setLastResult(lastRiskResult.getResult());
			}
			//第几次评估报告
			tgRpt.setNumber(tgrptCount+1);
			//糖尿病家族病史信息
			tgRpt.setBsFamilyInfo(riskResult.getIsFhistory()+"");
			
			//目标完成信息
			List<String> lstTargetFinish = new ArrayList<String>();
			//目标没有完成
			List<String> lstTargetNoFinish = new ArrayList<String>();
			//获取评测结果部分信息
			getResultInfo(tgRpt,riskResult, lastRiskResult);
			//获取危险因素部分信息（可控危险因素、不可控危险因素）
			getRisks(tgRpt, riskResult);
			//获取年龄部分信息
			getAgeInfo(tgRpt, riskResult);
			//获取运动部分信息
			getSportInfo(tgRpt, riskResult, lastRiskResult, lstTargetFinish, lstTargetNoFinish);
			//获取睡眠部分信息
			getSleepInfo(tgRpt, riskResult, lastRiskResult, lstTargetFinish, lstTargetNoFinish);
			//获取血压、血脂部分信息
			getLipidsInfo(tgRpt, riskResult, lastRiskResult, lstTargetFinish, lstTargetNoFinish);
			//获取体重部分信息
			getWeightInfo(tgRpt, riskResult, lastRiskResult, lstTargetFinish, lstTargetNoFinish);
			//获取目标部分信息（包括近期目标、远期目标）
			getTarget(tgRpt, riskResult);
			//获取合并症部分信息
			getCompliation(tgRpt, riskResult);
			//获取同龄正常人的发病概率
			getAgeProbability(riskResult, tgRpt);
			
			//目标完成情况信息
			if(tgrptCount > 1){
				if(!CollectionUtils.isEmpty(lstTargetFinish)){
					String targetFinish = ArrayUtils.join(lstTargetFinish.toArray(), "、");
					tgRpt.setTargetFinish(targetFinish);
				}
				if(!CollectionUtils.isEmpty(lstTargetNoFinish)){
					String targetNoFinish = ArrayUtils.join(lstTargetNoFinish.toArray(), "、");
					tgRpt.setTargetNoFinish(targetNoFinish);
				}
			}
			
			if(lastRiskResult != null){
				if(lastRiskResult.getResult().equals("高危") || lastRiskResult.getResult().equals("低危")){
					tgRpt.setLastProbability(MoneyFormatUtil.formatDouble(lastRiskResult.getProbability()/0.7));
				}else{
					tgRpt.setLastProbability(MoneyFormatUtil.formatDouble(lastRiskResult.getProbability()));
				}
			}
			
			//评估报告结果
			tgRpt.setResult(riskResult.getResult());
			//创建时间
			tgRpt.setCreateTime(new Date());
			
			tgRptDao.add(tgRpt);
			
			return "/atgRpt_redirectTgRpt.do?evalId="+evalId;
		}
		return "";
	}
	
	//获取评测结果部分信息
	public void getResultInfo(TgRptBaseInfo tgRpt, RiskResultBean riskResult, RiskResultBean lastRiskResult){
		String resultInfo = "";
		String result = riskResult.getResult();
		if(!StringUtils.isEmpty(result)){
			if(result.equals("低危")){
				resultInfo = "结果显示您的生活、工作和膳食习惯比较健康，请继续保持。但仍然需要联系您的" +
						"健康管理师调整生活习惯从运动、营养、生命指数调控和心理等多方面调整，降低发病概率。";
			}else if(result.equals("高危")){
				resultInfo = "您目前的生活和饮食习惯，不利于糖尿病风险控制，患病风险增加。您需要在专业人士的指导下，" +
						"改善生活习惯，进行饮食和运动调理，来降低糖尿病患病风险，并把相关指标调控在正常范围之内。";
			}else{
				resultInfo = "结果显示您目前疑似患有糖尿病，生活和饮食习惯严重影响着糖尿病的管理效果。您需要在专业人士的指导下，" +
						"改善生活习惯，进行饮食和运动调理，努力把相关指标调控在正常范围内！";
			}
		}
		tgRpt.setResultInfo(resultInfo);
	}
	
	//获取年龄部分信息
	public void getAgeInfo(TgRptBaseInfo tgRpt, RiskResultBean riskResult){
		String ageInfo = "";
		if(riskResult != null){
			//40岁以上处于糖尿病高发年龄段
			int age = riskResult.getAge();
			if(age > 40){
				ageInfo = "您今年"+age+"岁，正处于糖尿病高发年龄段，以后每增加10岁，患病率增加68%。这就要求您更应控制运动、饮食等可控危险因素，以降低患病风险！";
				tgRpt.setAgeInfo(ageInfo);
			}
		}
	}
	
	//获取运动部分信息
	public void getSportInfo(TgRptBaseInfo tgRpt, RiskResultBean riskResult, RiskResultBean lastRiskResult, List<String> lstTargetFinish, List<String> lstTargetNoFinish){
		String sportInfo = "";
		int sport = riskResult.getIsSport();
		if(lastRiskResult != null){
			int lastSport = lastRiskResult.getIsSport();
			if(lastSport == 0 && sport == 1){
				sportInfo = "您上次运动量不足，本次运动量达标，并不是每个人都有坚持运动的毅力哦，为你骄傲，请继续保持！";
				lstTargetFinish.add("增加运动量");
			}else if(lastSport == 0 && sport == 0){
				sportInfo = "您上次运动量不足，本次仍不足，长此下去，糖尿病患病风险会增高，发展速度会加快。" +
						"建议您增加运动量，提高对糖尿病管理！";
				lstTargetNoFinish.add("增加运动量");
			}else if(lastSport == 1 && sport == 0){
				sportInfo = "您上次运动量达标，本次不足，运动量改善不理想，糖尿病患病风险增高，发展速度会加快。" +
						"建议您增加运动量，提高对糖尿病管理！";
				lstTargetNoFinish.add("增加运动量");
			}		
		}else{
			if(sport == 0){
				sportInfo = "您需要增加运动量，建议每周进行不低于3次每次不低于30分钟运动，" +
				"消除胰岛素抵抗，促进胰岛功能，有效预防糖尿病。运动方式可请血糖高管一同制定。";
			}
		}
		
		tgRpt.setSportInfo(sportInfo);
	}
	
	//获取睡眠部分信息
	public void getSleepInfo(TgRptBaseInfo tgRpt, RiskResultBean riskResult, RiskResultBean lastRiskResult, List<String> lstTargetFinish,List<String> lstTargetNoFinish){
		String sleepInfo = "";
		int sleep = riskResult.getSleep();
		if(lastRiskResult != null){
			int lastSleep = lastRiskResult.getSleep();
			if((lastSleep == 2 || lastSleep == 3) && sleep == 1){
				sleepInfo = "您上次睡眠不足，本次睡眠量达标，恭喜您，良好的睡眠是迈向健康的第一步，继续加油哦！" +
						"您睡眠充足，恭喜您，您离患2型糖尿病又远了一步，请您继续保持！";
				lstTargetFinish.add("改善睡眠");
			}else if((lastSleep == 2 || lastSleep == 3) && (lastSleep == 2 || lastSleep == 3)){
				sleepInfo = "您上次睡眠不足，本次仍不足，长此下去，糖尿病患病风险会增高，发展速度会加快。" +
						"建议您改善睡眠质量，提高对糖尿病管理！";
				lstTargetNoFinish.add("改善睡眠");
			}else if(lastSleep == 1 && (lastSleep == 2 || lastSleep == 3)){
				sleepInfo = "您上次睡眠充足，本次不足，您的睡眠质量下降，糖尿病患病风险增高，发展速度会加快。" +
						"建议您务必调整睡眠，提高对糖尿病管理！";
				lstTargetNoFinish.add("改善睡眠");
			}		
		}else{
			if(sleep == 2 || sleep == 3){
				sleepInfo = "长期睡眠不足可引起胰岛素敏感性下降，研究表明：睡眠时间≤5h/晚及≥9h/晚，" +
						"会增加患2型糖尿病的风险。";
			}
		}
		
		tgRpt.setSleepInfo(sleepInfo);
	}
	
	
	//获取血压、血脂部分信息
	public void getLipidsInfo(TgRptBaseInfo tgRpt, RiskResultBean riskResult, RiskResultBean lastRiskResult, List<String> lstTargetFinish, List<String> lstTargetNoFinish){
		String lipidsInfo = "";
		String lipidsTitle = "";
		int bloodPressure = riskResult.getBloodPressure();
		int lipids = riskResult.getLipids();
		
		if(lastRiskResult != null){
			//上次血压信息
			int lastBloodPressure = lastRiskResult.getBloodPressure();
			//上次血脂信息
			int lastLipids = lastRiskResult.getLipids();
			//如果上次两都异常
			if(lastBloodPressure == 0 && lastLipids == 0){
				if(bloodPressure == 0 && lipids == 0){ //上次血压、血脂异常，本次血压、血压异常
					lipidsInfo = "两次评测，您血压血脂均不正常，糖尿病患病风险会增高，发展速度会加快。" +
							"建议您尽快调脂降压，提高对糖尿病管理，切勿掉以轻心！";
					lipidsTitle = "血压、血脂";
					lstTargetNoFinish.add("调脂降压");
				}else if(bloodPressure == 0){  //上次血压、血脂都异常，本次血压异常，血脂正常
					lipidsTitle = "血压、血脂";
					lipidsInfo = "与上次相比，您上次血压、血脂都异常，本次血脂已经正常，血糖高管团队为您的调理效果感到骄傲，强者加油哦！";
					lstTargetFinish.add("调脂");
					lstTargetNoFinish.add("降压");
				}else if(lipids == 0){  //上次血压、血脂都异常，本次血压正常，血脂异常
					lipidsTitle = "血压、血脂";
					lipidsInfo = "与上次相比，您上次血压、血脂都异常，本次血压已经正常，血糖高管团队为您的调理效果感到骄傲，强者加油哦！";
					lstTargetFinish.add("降压");
					lstTargetNoFinish.add("调脂");
				}
			}else if(lastBloodPressure == 0){//上次血压异常，血脂正常
				if(bloodPressure == 0 && lipids == 0){  //上次血压异常，血脂正常，本次血压、血脂都异常
					lipidsInfo = "与上次相比，您上次血压异常，血脂正常，本次血压、血脂都异常。建议您尽快调脂降压，提高对糖尿病管理，切勿掉以轻心！";
					lipidsTitle = "血压、血脂";
					lstTargetNoFinish.add("降压调脂");
				}else if(bloodPressure == 0){ //上次血压异常，血脂正常，本次血压异常，血脂正常
					lipidsTitle = "血压";
					lipidsInfo = "与上次相比，您本次血压仍然异常，建议您尽快降压，提高对糖尿病管理，切勿掉以轻心！";
					lstTargetNoFinish.add("降压");
				}else if(lipids == 0){  //上次血压异常，血脂正常，本次血压正常，血脂异常
					lipidsTitle = "血脂";
					lipidsInfo = "与上次相比，您上次血压异常，本次血压正常，上次血脂正常，本次血脂异常，建议您尽快调脂，提高对糖尿病管理，切勿掉以轻心！";
					lstTargetNoFinish.add("调脂");
					lstTargetFinish.add("降压");
				}
			}else if(lastLipids == 0){//上次血脂异常，血压正常
				if(bloodPressure == 0 && lipids == 0){  //上次血压正常，血脂异常，本次血压、血脂都异常
					lipidsInfo = "与上次相比，您上次血压正常，血脂异常，本次血压、血脂都异常。建议您尽快调脂降压，提高对糖尿病管理，切勿掉以轻心！";
					lipidsTitle = "血压、血脂";
					lstTargetNoFinish.add("降压调脂");
				}else if(bloodPressure == 0){ //上次血脂异常，血压正常，本次血压异常，血脂正常
					lipidsTitle = "血压";
					lipidsInfo = "与上次相比，您上次血脂异常，本次血脂正常，上次血压正常，本次血压异常。建议您尽快降压，提高对糖尿病管理，切勿掉以轻心！";
					lstTargetNoFinish.add("降压");
					lstTargetFinish.add("调脂");
				}else if(lipids == 0){  //上次血脂异常，血压正常，本次血压正常，血脂正常
					lipidsTitle = "血脂";
					lipidsInfo = "与上次相比，您本次血脂仍然异常。建议您尽快调脂，提高对糖尿病管理，切勿掉以轻心！";
					lstTargetNoFinish.add("调脂");
				}
			}else if(lastBloodPressure == 1 && lastLipids == 1){  //如果上次两都正常
				if(bloodPressure == 1 && lipids == 1){
					lipidsTitle = "";
					lipidsInfo = "";
				}else if(bloodPressure == 1){ //上次血压、血脂都正常，本次血压正常，血脂异常
					lipidsTitle = "血脂";
					lipidsInfo = "与上次相比，您上次血脂正常，本次血脂异常。建议您尽快调脂，提高对糖尿病管理，切勿掉以轻心！";
					lstTargetNoFinish.add("调脂");
				}else if(lipids == 1){
					lipidsTitle = "血压";  //上次血压、血脂都正常，本次血压异常，血脂正常
					lipidsInfo = "与上次相比，您上次血压正常，本次血压异常。建议您尽快降压，提高对糖尿病管理，切勿掉以轻心！";
					lstTargetNoFinish.add("降压");
				}
			}else if(lastBloodPressure == 1){
				if(bloodPressure == 1 && lipids == 1){//上次血压正常，血脂异常，本次血压、血脂都正常
					lipidsTitle = "血脂";  
					lipidsInfo = "与上次相比，您上次血脂异常，本次血脂正常。血糖高管团队为您的调理效果感到骄傲，强者加油哦！";
					lstTargetFinish.add("调脂");
				}else if(bloodPressure == 1){  //上次血压正常，血脂异常，本次血压正常，血脂异常
					lipidsTitle = "血脂";
					lipidsInfo = "与上次相比，您的血脂仍然异常。建议您尽快调脂，提高对糖尿病管理，切勿掉以轻心！";
					lstTargetNoFinish.add("调脂");
				}else if(lipids == 1){  //上次血压正常，血脂异常，本次血压异常，血脂正常
					lipidsTitle = "血压";  
					lipidsInfo = "与上次相比，您上次血压正常，本次血压异常，上次血脂异常，本次血脂正常。建议您尽快降压，提高对糖尿病管理，切勿掉以轻心！";
					lstTargetFinish.add("调脂");
					lstTargetNoFinish.add("降压");
				}
			}else if(lastLipids == 1){
				if(bloodPressure == 1 && lipids == 1){   //上次血压异常，血脂正常，本次血压、血脂都正常
					lipidsTitle = "血压";  
					lipidsInfo = "与上次相比，您上次血压异常，本次血压正常。血糖高管团队为您的调理效果感到骄傲，强者加油哦！";
					lstTargetFinish.add("降压 ");
				}else if(bloodPressure == 1){  //上次血压异常，血脂正常，本次血压正常，血脂异常
					lipidsTitle = "血脂";
					lipidsInfo = "与上次相比，您上次血压异常，本次血压正常，上次血脂正常，本次血脂异常。建议您尽快调脂，提高对糖尿病管理，切勿掉以轻心！";
					lstTargetFinish.add("降压");
					lstTargetNoFinish.add("调脂");
				}else if(lipids == 1){  //上次血压异常，血脂正常，本次血压异常，血脂正常
					lipidsTitle = "血压";  
					lipidsInfo = "与上次相比，您的血压仍然异常。建议您尽快降压，提高对糖尿病管理，切勿掉以轻心！";
					lstTargetNoFinish.add("降压 ");
				}
			}
		}else{
			if(bloodPressure == 0 && lipids == 0){
				lipidsTitle = "血压、血脂";
				lipidsInfo = "高血压病与肥胖、血脂异常、糖尿病互为因果。血压高、血脂高会增加糖尿病的发病风险，" +
				"建议调脂、降压、控制体重，降低糖尿病发生概率！";
			}else if(bloodPressure == 0){
				lipidsTitle = "血压";
				lipidsInfo = "高血压病与肥胖、血脂异常、糖尿病互为因果。血压高会增加糖尿病的发病风险，" +
				"建议降压、控制体重，降低糖尿病发生概率！";
			}else if(lipids == 0){
				lipidsTitle = "血脂";
				lipidsInfo = "高血压病与肥胖、血脂异常、糖尿病互为因果。血脂高会增加糖尿病的发病风险，" +
				"建议调脂、控制体重，降低糖尿病发生概率！";
			}
		}
		tgRpt.setLipidsTitle(lipidsTitle);
		tgRpt.setLipids(lipidsInfo);
	}
	
	//获取体重部分信息
	public void getWeightInfo(TgRptBaseInfo tgRpt, RiskResultBean riskResult, RiskResultBean lastRiskResult, List<String> lstTargetFinish, List<String> lstTargetNoFinish){
		String weightInfo = "";
		//身高
		double height = riskResult.getHeight();
		//体重
		double weight = riskResult.getWeight();
		
		String bmiString = RptUtils.getStringBMI(height+"", weight+"");
		String bmiTag = "";
		
		//健康体重范围
		double lweight = (height/100)*(height/100)*18.5;
		double hweight = (height/100)*(height/100)*24;
		double lowWeight = MoneyFormatUtil.formatDouble(lweight);
		double higWeight = MoneyFormatUtil.formatDouble(hweight);
		
		double bmi = 0;
		if(!StringUtils.isEmpty(bmiString)){
			bmi = Double.parseDouble(bmiString);
			bmiTag = getBMITag(bmi);
		}
		if(lastRiskResult != null){
			double lastHeight = lastRiskResult.getHeight();
			double lastWeight = lastRiskResult.getWeight();
			String lastBmiString = RptUtils.getStringBMI(lastHeight+"", lastWeight+"");
			double lastBmi = Double.parseDouble(lastBmiString);
			
			if(lastBmi >= 24 || bmi >= 24){
				//两次体重减轻
				if(lastWeight > weight){
					weightInfo = "您标准体重为"+lowWeight+"-"+higWeight+"kg，您上次评测时体重为"+lastWeight+"kg，本次体重为"+weight+"kg，" +
							"已减重"+(lastWeight-weight)+"kg,效果不错，请您继续保持！";
					lstTargetFinish.add("减肥减体重");
				}else{//两次体重增加
					weightInfo = "您标准体重为"+lowWeight+"-"+higWeight+"kg，您上次评测时体重为"+lastWeight+"kg，本次体重为"+weight+"kg，" +
					"增重"+(weight-lastWeight)+"kg,，糖尿病患病风险增高，请您务必制定运动计划，控制体重！";
					lstTargetNoFinish.add("减肥减体重");
				}
			}
		}else{
			if(bmi > 27){
				weightInfo = "您身高"+height+"cm，体重"+weight+"kg，BMI为"+bmi+"，属于"+bmiTag+"。您需要和血糖高管一起调节营养结构，制定运动计划，" +
						"控制体重在（"+lowWeight+"-"+higWeight+"）kg之间。以降低腹部脂肪堆积，消除胰岛素抵抗和降低2型糖尿病的发生。";
			}
		}
		tgRpt.setWeightInfo(weightInfo);
	}
	
	
	
	//获取合并症
	public void getCompliation(TgRptBaseInfo tgRpt, RiskResultBean riskResult){
		if(riskResult != null){
			String compliation = riskResult.getComplications();
			if(!StringUtils.isEmpty(compliation)){
				List<String> lstComp = new ArrayList<String>();
				String [] strComp = compliation.split(",");
				for (String comp : strComp) {
					int compInfo = Integer.parseInt(comp);
					if(compInfo == 1){
						lstComp.add("糖尿病冠心病");
					}else if(compInfo == 2){
						lstComp.add("高血压");
					}else if(compInfo == 3){
						lstComp.add("糖尿病肾病");
					}else if(compInfo == 4){
						lstComp.add("糖尿病脑梗");
					}else if(compInfo == 5){
						lstComp.add("糖尿病视网膜病变");
					}else if(compInfo == 6){
						lstComp.add("下肢静脉栓塞");
					}else if(compInfo == 7){
						lstComp.add("糖尿病足");
					}else if(compInfo == 8){
						lstComp.add("糖尿病周围神经病变");
					}else if(compInfo == 9){
						lstComp.add("糖尿病植物神经病变");
					}
				}
				
				if(!CollectionUtils.isEmpty(lstComp)){
					tgRpt.setCompliation(ArrayUtils.join(lstComp.toArray(), "合并"));
				}
			}
		}
	}
	
	//获取危险因素信息（第一次报告没有此部分）
	public void getRisks(TgRptBaseInfo tgRpt, RiskResultBean riskResult){
		
		if(riskResult != null){
			List<String> lstRisks = new ArrayList<String>();
			List<String> lstUnrisks = new ArrayList<String>();
			
			if(riskResult.getAge() > 40){
				lstUnrisks.add("年龄");
			}
			if(riskResult.getIsFhistory() == 1){
				lstUnrisks.add("家族史");
			}
			//糖尿病合并症
			if(!StringUtils.isEmpty(riskResult.getComplications())){
				lstUnrisks.add("已患糖尿病和并发症");
			}else if(!riskResult.getResult().equals("低危")
				&& !riskResult.getResult().equals("高危")){
				lstUnrisks.add("已患糖尿病");
			}
			String bmiStr = RptUtils.getStringBMI(riskResult.getHeight()+"", riskResult.getWeight()+"");
			if(!StringUtils.isEmpty(bmiStr)){
				if(Double.parseDouble(bmiStr) > 27){
					lstRisks.add("体重");
				}
			}
			if(riskResult.getIsSport() == 0){
				lstRisks.add("运动");
			}
			if(riskResult.getSleep() == 2 || riskResult.getSleep() == 3){
				lstRisks.add("睡眠");
			}
			if(riskResult.getBloodPressure() == 0){
				lstRisks.add("血压");
			}
			if(riskResult.getLipids() == 0){
				lstRisks.add("血脂");
			}
			//可控因素
			if(!CollectionUtils.isEmpty(lstRisks)){
				tgRpt.setRisks(ArrayUtils.join(lstRisks.toArray(), "、"));
				tgRpt.setIsRisk(1);
			}
			//不可控因素
			if(!CollectionUtils.isEmpty(lstUnrisks)){
				tgRpt.setUnrisks(ArrayUtils.join(lstUnrisks.toArray(), "、"));
				tgRpt.setIsRisk(1);
			}
		}
	}
	
	//获取目标部分信息（包括近期目标和远期目标）
	public void getTarget(TgRptBaseInfo tgRpt, RiskResultBean riskResult){
		//结果为低危/高危且有风险因素
		if((riskResult.getResult().equals("低危")|| riskResult.getResult().equals("高危"))
			&& !StringUtils.isEmpty(tgRpt.getRisks())){
			//获取近期目标
			getTargetInfo(tgRpt, riskResult);
			//长期目标
			getLongTarget(tgRpt, riskResult);
		}else if((riskResult.getResult().equals("低危")|| riskResult.getResult().equals("高危"))
				&& StringUtils.isEmpty(tgRpt.getRisks())){//结果为低危/高危且没有风险因素
			//长期目标
			getLongTarget(tgRpt, riskResult);
		}else if(!StringUtils.isEmpty(riskResult.getComplications())){
			//获取近期目标
			getTargetInfo(tgRpt, riskResult);
			//长期目标
			getLongTarget(tgRpt, riskResult);
		}else if(StringUtils.isEmpty(riskResult.getComplications())){
			//获取近期目标
			getTargetInfo(tgRpt, riskResult);
			//长期目标
			getLongTarget(tgRpt, riskResult);
		}
	}
	
	
	//获取近期目标
	public void getTargetInfo(TgRptBaseInfo tgRpt,RiskResultBean riskResult){
		if(!StringUtils.isEmpty(tgRpt.getRisks())){
			String risks = tgRpt.getRisks();
			JSONObject jo = new JSONObject();
			
			if((riskResult.getResult().equals("低危")|| riskResult.getResult().equals("高危"))
					&& !StringUtils.isEmpty(tgRpt.getRisks())){
				jo.put("openInfo", "通过改变生活方式，调整饮食习惯，逐渐改善糖尿病的可控风险因素：");
			}else{
				jo.put("openInfo", "将血糖值调至正常水平，逐渐改善糖尿病的可控风险因素：");
			}
			
			JSONObject targetJson = new JSONObject();
			JSONArray ja = new JSONArray();
			if((!riskResult.getResult().equals("低危")&& !riskResult.getResult().equals("高危"))){
				targetJson.put("risk", "科学降血糖");
				targetJson.put("riskInfo", "按医嘱用药，配合运动、饮食调整，将血糖降至正常范围水平；");
				ja.add(targetJson);
			}
			if(risks.contains("体重")){
				targetJson.put("risk", "减肥减体重");
				targetJson.put("riskInfo", "循序渐进减肥，目标每周减1-2斤，每月不超过4斤，直至标准体重；");
				ja.add(targetJson);
			}
			if(risks.contains("运动")){
				targetJson.put("risk", "增加运动量");
				targetJson.put("riskInfo", "逐步增加运动量，做好运动保护，目标每周至少3次30分钟中等强度运动；");
				ja.add(targetJson);
			}
			if(risks.contains("睡眠")){
				targetJson.put("risk", "改善睡眠");
				targetJson.put("riskInfo", "找出影响睡眠的原因，调整心态，多手段提高睡眠质量，改善身心疲惫的状态；");
				ja.add(targetJson);
			}
			if(risks.contains("血压") && risks.contains("血脂")){
				targetJson.put("risk", "降压调脂");
				targetJson.put("riskInfo", "通过运动、饮食调整、药物干预等方式将血压、血脂在医嘱下调到正常范围；");
				ja.add(targetJson);
			}else if(risks.contains("血压")){
				targetJson.put("risk", "降压");
				targetJson.put("riskInfo", "通过运动、饮食调整、药物干预等方式将血压在医嘱下调到正常范围；");
				ja.add(targetJson);
			}else if(risks.contains("血脂")){
				targetJson.put("risk", "调脂");
				targetJson.put("riskInfo", "通过运动、饮食调整、药物干预等方式将血脂在医嘱下调到正常范围；");
				ja.add(targetJson);
			}
			jo.put("data", ja.toArray());
			
			tgRpt.setTarget(jo.toString());
		}
	}
	
	//获取远期目标
	public void getLongTarget(TgRptBaseInfo tgRpt,RiskResultBean riskResult){
		JSONObject jo = new JSONObject();
		JSONObject json = new JSONObject();
		
		//身高
		double height = riskResult.getHeight();
		//健康体重范围
		double lweight = (height/100)*(height/100)*18.5;
		double hweight = (height/100)*(height/100)*24;
		double lowWeight = MoneyFormatUtil.formatDouble(lweight);
		double higWeight = MoneyFormatUtil.formatDouble(hweight);
		
		String heightStan = lowWeight+"-"+higWeight;
		
		if((riskResult.getResult().equals("低危")|| riskResult.getResult().equals("高危"))
				&& !StringUtils.isEmpty(tgRpt.getRisks())){
			jo.put("openInfo", "通过消除糖尿病的可控风险因素，降低糖尿病发生风险概率：");
		}else if((riskResult.getResult().equals("低危")|| riskResult.getResult().equals("高危"))
				&& StringUtils.isEmpty(tgRpt.getRisks())){//结果为低危/高危且没有风险因素
			jo.put("openInfo", " 恭喜您目前为糖尿病患病低危人群，随着年龄和生活习惯的改变，糖尿病患病风险也会变化，为了您永久性远离糖尿病，您需要：");
		}else if(!StringUtils.isEmpty(riskResult.getComplications())){
			jo.put("openInfo", "通过长期使血糖维持在正常水平，及消除糖尿病的可控风险因素，预防糖尿病并发症；");
		}else if(StringUtils.isEmpty(riskResult.getComplications())){
			jo.put("openInfo", "通过严格的血糖控制，延缓或降低糖尿病并发症的发生和发展，改进患者的生命质量和寿命；");
		}
		
		JSONArray ja = new JSONArray();
		if((!riskResult.getResult().equals("低危")&& !riskResult.getResult().equals("高危"))){
			json.put("risk", "长期稳糖");
			json.put("riskInfo", "按医嘱用药，配合运动、饮食调整等，将血糖长期稳定在正常范围水平；");
			ja.add(json);
		}
		json.put("risk", "保持体重");
		json.put("riskInfo", "通过健康的运动、饮食等将体重控制在您的标准体重"+heightStan+"kg之间；");
		ja.add(json);
		json.put("risk", "定时定量运动");
		json.put("riskInfo","选择自己喜欢的运动方式，每周三次以上30分钟中等强度运动；");
		ja.add(json);
		json.put("risk", "保持良好睡眠");
		json.put("riskInfo","保持愉快心情，健康饮食，科学作息，每天将睡眠保持在6-8小时；");
		ja.add(json);
		
		//低危无风险因素
		if(riskResult.getResult().equals("低危") && StringUtils.isEmpty(riskResult.getComplications())){
			json.put("risk", "稳压稳脂");
			json.put("riskInfo","通过平衡蔬菜、水果及肉类等的摄入，保持血压血脂正常稳定；");
			ja.add(json);
		}else{
			json.put("risk", "稳压稳脂");
			json.put("riskInfo","通过平衡蔬菜、水果及肉类等的摄入，必要时借助药物干预保持血压血脂正常稳定；");
			ja.add(json);
		}
		
		jo.put("data", ja.toArray());
		
		tgRpt.setLongTarget(jo.toString());
	}

	//获取同龄正常人的发病概率
	public void getAgeProbability(RiskResultBean riskResult, TgRptBaseInfo tgRpt) {
		
		int age = riskResult.getAge();
		int mAge = 0;
		if (age >= 20 && age < 30) {
			mAge = 1;
		} else if (age >= 30 && age < 40) {
			mAge = 2;
		} else if (age >= 40 && age < 50) {
			mAge = 3;
		} else if (age >= 50 && age < 60) {
			mAge = 4;
		} else if (age >= 60) {
			mAge = 5;
		}
		
		int mFhistory = 1;  //没有家族史
		int mBmi = 2;   //正常的BMI
		int mPress = 1;  // 无高血压
		
		int gender = riskResult.getGender();
		double waistline = 0;
		double height = riskResult.getHeight();
		int mWaistline = 0;
		if (gender == 0) {// 男
			waistline = height *0.47;
			if (waistline < 65) {
				mWaistline = 1;
			} else if (waistline >= 65 && waistline < 75) {
				mWaistline = 2;
			} else if (waistline >= 75 && waistline < 85) {
				mWaistline = 3;
			} else if (waistline >= 85 && waistline < 95) {
				mWaistline = 4;
			} else if (waistline >= 95 && waistline < 105) {
				mWaistline = 5;
			} else if (waistline >= 105) {
				mWaistline = 6;
			}
		} else if (gender == 1) {// 女
			waistline = height *0.34;
			if (waistline < 60) {
				mWaistline = 1;
			} else if (waistline >= 60 && waistline < 70) {
				mWaistline = 2;
			} else if (waistline >= 70 && waistline < 80) {
				mWaistline = 3;
			} else if (waistline >= 80 && waistline < 90) {
				mWaistline = 4;
			} else if (waistline >= 90 && waistline < 100) {
				mWaistline = 5;
			} else if (waistline >= 100) {
				mWaistline = 6;
			}
		}
		
		int mLipids = 1;  // 正常血脂
		int mSugarValue = 1; //正常血糖

		double dSimplie = SimpleCalculate(mAge, mFhistory, mPress, mWaistline,
				mBmi);
		if (dSimplie > 0.04) {
			double dComplex = ComplexCalculate(mAge, mFhistory, mPress,
					mWaistline, mBmi, mLipids, mSugarValue);
			double d = MoneyFormatUtil.formatDouble(dComplex * 100);
			tgRpt.setAgeProbability(d);
		}else{
			double d = MoneyFormatUtil.formatDouble(dSimplie * 100);
			tgRpt.setAgeProbability(d);
		}
	}
	
	
	/** 简单计算 */
	public double SimpleCalculate(int mAge, int mFhistory, int mPress,
			int mWaistline, int mBmi) {
		double d = Math.exp(0.58 * mAge + 0.85 * mFhistory + 0.83 * mBmi + 0.53
				* mPress + 0.02 * mWaistline - 8.34)
				/ (1 + Math.exp(0.58 * mAge + 0.85 * mFhistory + 0.83 * mBmi
						+ 0.53 * mPress + 0.02 * mWaistline - 8.34));
		return d;
	}
	
	/** 复杂计算 */
	public double ComplexCalculate(int mAge, int mFhistory, int mPress,
			int mWaistline, int mBmi, int mLipids, int mSugarValue) {
		double d = Math.exp(0.58 * mAge + 0.83 * mBmi + 0.53 * mPress + 0.85
				* mFhistory + 0.02 * mWaistline + 0.46 * mLipids + 1.13
				* mSugarValue - 10.15)
				/ (1 + Math.exp(0.58 * mAge + 0.83 * mBmi + 0.53 * mPress
						+ 0.85 * mFhistory + 0.02 * mWaistline + 0.46 * mLipids
						+ 1.13 * mSugarValue - 10.15));
		return d;
	}
	
	
	public TgRptBaseInfo queryTgRptBaseInfo(TgRptBaseInfo tgRpt){
		return tgRptDao.queryTgRptBaseInfo(tgRpt);
	}
	
	
	public TgRptBaseInfo queryLatestTgRptInfo(TgRptBaseInfo tgRpt){
		TgRptBaseInfo tgRptInfo = tgRptDao.queryTgRptBaseInfo(tgRpt);
		return tgRptInfo;
	}
	
	public String getBMITag(double bmi){
		String bmiTag = "";
		if(bmi >= 24 && bmi < 27){
			bmiTag = "过重";
		}else if(bmi >= 27 && bmi < 30){
			bmiTag = "轻度肥胖";
		}else if(bmi >= 30 && bmi < 35){
			bmiTag = "中度肥胖";
		}else if(bmi >= 35){
			bmiTag = "重度肥胖";
		}
		return bmiTag;
	}
	
	
	public String queryTgRpt(TgRptBaseInfo tgRpt, Integer softType, QueryInfo queryInfo){
		JSONObject jo = new JSONObject();
		PageObject pager = tgRptDao.queryTgRpt(tgRpt, softType, queryInfo);
		List<TgRptBaseInfo> lstTgRpt = pager.getDatas();
		JSONObject jsonAll = new JSONObject();
		jsonAll.put("urlBase", "atgRpt_redirectTgRpt.do?evalId=");
		if(!CollectionUtils.isEmpty(lstTgRpt)){
			JSONArray ja = new JSONArray();
			for (TgRptBaseInfo tgRptBaseInfo : lstTgRpt) {
				JSONObject json = new JSONObject();
				json.put("evalId", tgRptBaseInfo.getEvalId());
				json.put("intro", "第"+tgRptBaseInfo.getNumber()+"份评估报告");
				json.put("createTime", DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", tgRptBaseInfo.getCreateTime()));
				ja.add(json.toString());
			}
			jsonAll.put("list", ja.toArray());
			jo.put("code", 1);
			jo.put("msg", "获取信息成功");
			jo.put("data", jsonAll.toString());
		}else{
			jo.put("code", 1);
			jo.put("msg", "您没有评估报告信息");
			jo.put("data", "");
		}
		return jo.toString();
	}
	
	
	public void createTgRptBaseInfo(){
		List<EvaluatingResult> lstEval = evalResultDao.queryEvalNoCreateRpt();
		if(!CollectionUtils.isEmpty(lstEval)){
			for (EvaluatingResult result : lstEval) {
				//生成健康报告
				RiskResultBean bean = (RiskResultBean) JsonUtils.getObject4JsonString(
						result.getResults(), RiskResultBean.class);
				createTgRptBaseInfo(result.getClientId(), bean, result.getId());
			}
		}
	}
}
