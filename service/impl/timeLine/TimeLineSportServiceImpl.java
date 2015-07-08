package com.bskcare.ch.service.impl.timeLine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.RiskResultBean;
import com.bskcare.ch.dao.ClientHobbyDao;
import com.bskcare.ch.dao.timeLine.TimeLineSportDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.timeLine.TimeLineSportService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.client.ClientHobby;
import com.bskcare.ch.vo.timeLine.EvaluatingResult;
import com.bskcare.ch.vo.timeLine.TimeLineSport;

@Service
@SuppressWarnings("unused")
public class TimeLineSportServiceImpl implements TimeLineSportService {
	@Autowired
	private TimeLineSportDao timeLineSportDao;
	@Autowired
	private ClientHobbyDao clientHobbyDao;

	public PageObject<TimeLineSport> getPageObject(TimeLineSport lineSport,
			QueryInfo queryInfo) {
		return timeLineSportDao.getLineSport(lineSport, queryInfo);
	}

	public void add(TimeLineSport lineSport) {
		if(lineSport != null){
			if(lineSport.getId() != null){
				timeLineSportDao.update(lineSport);
			}else{
				timeLineSportDao.add(lineSport);
			}
		}
	}
	public String getTimeLineSportType(String sportType,Integer cid){
		String sport="";
		//运动库类型
		String[] linesport=sportType.split(",");
		
		String[] lastsport = null;
		ClientHobby clientHobby = clientHobbyDao.getClientHobby(cid);
		
		if(clientHobby!=null){
			if(!StringUtils.isEmpty(clientHobby.getSportType())){//如果用户自己选择的有运动类型（习惯嗜好）
				if(!StringUtils.isEmpty(sportType)){	//如果运动库不为空
					lastsport = StringUtils.arrayIntersection(linesport, clientHobby.getSportType().split(","),true);//查找用户与运动库的交集，返回最后结果
				}else{
					lastsport = clientHobby.getSportType().split(",");//如果运动库为空，获得用户自己选的
				}
			}else{//如果用户没有自己选择
				if(!StringUtils.isEmpty(sportType)){//如果运动库不为空，返回运动库
					lastsport = linesport;
				}else{
					return "";//如果都是空返回空
				}
			}
		}else{
			lastsport = linesport;
		}
		if(lastsport==null)
			return "";
		int i= RandomUtils.getRandomIndex(lastsport.length);
		sport = lastsport[i];
		//在结果中随机取一个返回
		return sport;
	}

	public void delete(Integer id) {
		timeLineSportDao.delete(id);
	}

	public TimeLineSport getLineSportById(Integer id) {
		return timeLineSportDao.load(id);
	}

	public TimeLineSport getLineSportByEvalResult(RiskResultBean bean, int soft) {
		if (bean != null) {
			TimeLineSport lineSport = new TimeLineSport();
			String complications = bean.getComplications();
			int complication = 0;

			if (!StringUtils.isEmpty(complications)) {
				String[] strs = complications.split(",");
				if (strs.length > 1) {
					complication = 10;// 多种并发症
				} else {
					complication = Integer.parseInt(strs[0]);
				}
			}
			
			lineSport.setComplication(complication);
			lineSport.setResult(bean.getResult());
			lineSport.setSoftType(soft);
			
			// 用户bmi
			double height = bean.getHeight();
			double weight = bean.getWeight();
			// 计算公式：体重（kg）÷身高^2（m）
			double bmi = weight / ((height * height) / 10000); // BMI
			
			if(bmi > 23.9){
				lineSport.setOverweight(TimeLineSport.overweight_YES);
			}else{
				lineSport.setOverweight(TimeLineSport.overweight_ON);
			}
			
			List<TimeLineSport> lineSports = timeLineSportDao.getLineSport(lineSport);
			if (!CollectionUtils.isEmpty(lineSports)) {
				return lineSports.get(RandomUtils.getRandomIndex(lineSports
						.size()));
			}
		}
		return null;
	}

	public String getTimeLineConBySport(RiskResultBean bean,
			TimeLineSport timeLineSport, Integer clientId) {
		String coutent = "";
//		RiskResultBean bean = (RiskResultBean) JsonUtils.getObject4JsonString(
//				evaluatingResult.getResults(), RiskResultBean.class);
		//心率公式
		//最小值：（220-年龄）*60%
		//最适值：170-年龄
		double suitHR = ((double) 220 - bean.getAge()) * 0.6;// 最适合的心率
		String sportStr = getTimeLineSportType(timeLineSport.getSportType(), clientId);
		if(!StringUtils.isEmpty(sportStr)){
			coutent = "适合您的运动是" + sportStr+"，";
		}
		coutent += "您的运动消耗的热量应该略大于" + timeLineSport.getSuitCalorie()+"千卡";
		coutent += "，最多不大于" + timeLineSport.getMaxCalorie()+"千卡";
		coutent += "，运动时您的心率应保持在" + (int) suitHR + "/m至"
				+ (170 - bean.getAge()) + "/m之间";
		return coutent;
	} 

}
