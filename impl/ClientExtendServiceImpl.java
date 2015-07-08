package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.ClientExtendExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ClientExtendDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientExtendService;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientExtend;
import com.bskcare.ch.vo.ClientInfo;
@Service
@SuppressWarnings("unchecked")
public class ClientExtendServiceImpl implements ClientExtendService {
	
	@Autowired
	private ClientExtendDao extendDao;
	
	public void updateLastTime(Integer clientId, String type) {
		if(clientId != null&!StringUtils.isEmpty(type)){
			ClientExtend lastTime = new ClientExtend();
			lastTime.setClientId(clientId);
			lastTime = extendDao.queryLastTimeByClientId(lastTime);
			//不等于null说明已经存在该客户的记录，修改。反之添加记录
			if(lastTime != null){
				extendDao.updateLastTime(clientId, type);
			}else{
				ClientExtend lt = new ClientExtend();
				lt.setClientId(clientId);
				if(type.equals("lastFollowTime")){
					lt.setLastFollowTime(new Date());
				}else{
					lt.setLastUploadDateTime(new Date());
				}
				extendDao.add(lt);
			}
		}
	}

	public ClientExtend addRegSource(Integer cid, String source,String passivityinvite) {
		ClientExtend rs = new ClientExtend();
		if(cid != null){
			rs.setClientId(cid);
			if(!StringUtils.isEmpty(source)){
				int index = source.indexOf("-");
				if(index != -1){
					String ver = source.substring(index+1);
					rs.setVersion(ver);
					String sour = source.substring(0, index);
					rs.setSource(sour);
				}else{
					rs.setSource(source);
				}
			}
			rs.setRegTime(new Date());
			//生成邀请码
			rs.setInitiativeinvite(getinitiativeinvite());
			if(!StringUtils.isEmpty(passivityinvite)){//如果用户填写了邀请码
				rs.setPassivityinvite(passivityinvite);
			}
			return extendDao.add(rs);
		}
		return null;
	}

	public List queryStatisticList(String timeType) {
		return extendDao.queryStatisticList(timeType);
	}

	public List queryRegSource(ClientInfo clientInfo, QueryCondition queryCondition, String areaChain){
		List lst = extendDao.queryRegSource(clientInfo, queryCondition, areaChain);
		List lstObj = new ArrayList();
		int webCount = 0;
		if(!CollectionUtils.isEmpty(lst)){
			for (Object object : lst) {
				Object[] objs = (Object[])object;
				//null和web都是web端注册的客户
				if(objs[0] == null || (objs[0] != null && objs[0].equals("web"))){
					int count = Integer.parseInt(objs[1].toString());
					webCount += count;
				}else{
					Object[] obj = new Object[100];
					obj[0] = objs[0];
					obj[1] = objs[1];
					lstObj.add(obj);
				}
			}
			if(webCount > 0){
				Object[] obj = new Object[100];
				obj[0]="web";
				obj[1] = webCount;
				lstObj.add(obj);
			}
			return lstObj;
		}
		return null;
	}

	public ClientExtend modifyLastTimeByClientId(Integer clientId) {
		ClientExtend extend = new ClientExtend();
		extend.setClientId(clientId);
		extend = extendDao.queryLastTimeByClientId(extend);
		if(extend == null){
			extend = new ClientExtend();
			extend.setClientId(clientId);
		}
		if(StringUtils.isEmpty(extend.getInitiativeinvite())){//如果邀请码为空
			extend.setInitiativeinvite(getinitiativeinvite());
			if(extend.getId()==null){
				extendDao.add(extend);
			}else{
				extendDao.updateInviteCount(clientId, getinitiativeinvite());
			}
		}
		return extend;
	}
	//返回邀请码(用户)
	public String getinitiativeinvite(){
		String initiativeinvite = "";
		do {
			initiativeinvite = ClientInfo.CLIENT_INVITE+ RandomUtils.getRandomNumber(7);
			//查询邀请码是否存在
			if(extendDao.queryInviteCount(initiativeinvite)==0)
				break;
		} while (true);
		return initiativeinvite;
	}
	
	public String markClientFlag(Integer clientId, int flag) {
		int count = extendDao.markClientFlag(clientId, flag);
		
		if(count > 0) {
			return StringUtils.encapsulationJSON(new JSONObject(), Constant.INTERFACE_SUCC, "success", "");
		} else {
			return StringUtils.encapsulationJSON(new JSONObject(), Constant.INTERFACE_FAIL, "fail", "");
		}
	}
	
	public int updateDetailScore(Integer cid, int type){
		ClientExtend cx = new ClientExtend();
		cx.setClientId(cid);
		ClientExtend ce = extendDao.queryLastTimeByClientId(cx);
		
		int reportScore = ce.getReportScore();
		
		if(ce != null){
			//1：上传血糖数据   3：上传睡眠数据  4：上传血压数据
			if(type == 1 || type == 3 || type == 4){  
				reportScore = reportScore + 10 > 100 ? 100 : reportScore + 10;
				
			}else if(type == 2){ //上传饮食
				
				//上传饮食需要添加饮食成就、健康报告成就
				int dietScore = ce.getDietScore();
				dietScore = dietScore + 20 > 100 ? 100 :dietScore + 20;
				reportScore = reportScore + 10 > 100 ? 100 : reportScore + 10;
				ce.setDietScore(dietScore);
				
			}else if(type == 5){ //上传运动数据
				
				//上传运动数据需要添加运动数据、健康报告成就
				int sportScore = ce.getSportScore();
				sportScore = sportScore + 20 > 100 ? 100 : sportScore + 20;
				reportScore = reportScore + 10 > 100 ? 100 : reportScore + 10;
				ce.setSportScore(sportScore);
				
			}else if(type == 6){ //添加服药提醒
				reportScore = reportScore + 50 > 100 ? 100 : reportScore + 50;
			}else if(type == 7){  //上传异常血糖数据
				//上传异常数据需要添加健康报告、医生指导
				int doctorScore = 100;
				reportScore = reportScore + 10 > 100 ? 100 : reportScore + 10;
				ce.setDoctorScore(doctorScore);
			}
			ce.setReportScore(reportScore);
			
			return extendDao.updateScoreInfo(ce);
		}
		return 0;
	}
	
	
	public void autoChangeScore(){
		// 1：健康报告    2：饮食指导   3：运动指导
		extendDao.updateClientExtend(1);
		extendDao.updateClientExtend(2);
		extendDao.updateClientExtend(3);
	}
	
	
	public PageObject<ClientExtendExtend> queryClientExtend(QueryCondition qc, QueryInfo queryInfo){
		return extendDao.queryClientExtend(qc, queryInfo);
	}
}
