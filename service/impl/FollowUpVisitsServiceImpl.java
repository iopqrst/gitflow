package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.FollowUpVisitsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientExtendService;
import com.bskcare.ch.service.FollowUpVisitsService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.FollowUpVisits;
import com.bskcare.ch.vo.UserInfo;

@Service
@SuppressWarnings("unchecked")
public class FollowUpVisitsServiceImpl implements FollowUpVisitsService {
	@Autowired
	private FollowUpVisitsDao followUpVisitsDao;
	@Autowired
	private ClientExtendService extendService;

	public FollowUpVisits addFollowUpVisits(FollowUpVisits followUpVisits,
			UserInfo userInfo) {
		followUpVisits.setStatus(followUpVisits.getStatus());
		return followUpVisitsDao.addFollowUpVisits(followUpVisits, userInfo);
	}

	public String getFollowUpVisitsList(FollowUpVisits followUpVisits,
			QueryInfo queryInfo) {
		followUpVisits.setStatus(1);
		PageObject list = followUpVisitsDao.getFollowUpVisitsList(
				followUpVisits, queryInfo);

		JSONObject jo = new JSONObject();
		jo.put("total", list.getTotalRecord());
		jo.put("list", JsonUtils.getJsonString4JavaListDate(list.getDatas()));

		return jo.toString();
	}

	public PageObject myFollowUpVisitsList(FollowUpVisits followUpVisits,
			QueryInfo queryInfo) {
		return followUpVisitsDao.getFollowUpVisitsList(followUpVisits,
				queryInfo);
	}

	public void updateVisits(FollowUpVisits followUpVisits, UserInfo userInfo) {
		followUpVisitsDao.updateVisits(followUpVisits, userInfo);
	}

	public String getFollowUpVisitsListFacade(FollowUpVisits followUpVisits,
			QueryInfo queryInfo) {
		followUpVisits.setStatus(1);
		PageObject list = followUpVisitsDao.getFollowUpVisitsList(
				followUpVisits, queryInfo);

		ArrayList alist = (ArrayList) list.getDatas();

		JSONArray josnList = new JSONArray();

		for (Iterator iterator = alist.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			FollowUpVisits tmp = (FollowUpVisits) object[1];
			UserInfo userInfo = (UserInfo) object[0];

			JSONObject datajson = new JSONObject();
			datajson.put("name", userInfo.getName());
			datajson.put("date", DateUtils.format(tmp.getFollowUpDate()));
			datajson.put("content", tmp.getContent());

			josnList.add(datajson);
		}
		JSONObject jo = new JSONObject();

		jo.put("total", list.getTotalRecord());
		jo.put("list", josnList);

		return jo.toString();
	}

	public void addFollowUp(FollowUpVisits followUpVisits) {
		followUpVisitsDao.addFollowUp(followUpVisits);
		extendService.updateLastTime(followUpVisits.getClientId(), "lastFollowTime");
	}

	/**
	 * 根据任务id获得随访列表
	 */
	public PageObject getFollowUpVisitsByTaskId(int taskid, QueryInfo queryInfo) {
		return followUpVisitsDao.getFollowUpVisitsByTaskId(taskid, queryInfo);
	}
	/**
	 * 根据用户id获得随访列表
	 */
	public PageObject queryFollowList(FollowUpVisits follow, QueryInfo queryInfo,QueryCondition condition) {
		return followUpVisitsDao.queryFollowList(follow, queryInfo,condition);
	}
	/**
	 * 获得随访信息
	 */
	public List queryFollowUpList(Integer clientId, FollowUpVisits follow) {
		return followUpVisitsDao.queryFollowUpList(clientId, follow);
	}
	
	

	public String getFollowUpUserByClientId(Integer clientId) {
		PageObject list= followUpVisitsDao.getFollowUpUserByClientId(clientId);
		ArrayList alist = (ArrayList) list.getDatas();
		JSONArray josnList = new JSONArray();
		for (Iterator iterator = alist.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			JSONObject datajson = new JSONObject();
			datajson.put("id", object[1]);
			datajson.put("name", object[0]);
			josnList.add(datajson);
		};
		
		return josnList.toString();
	}
	public void deleteFollowUp(FollowUpVisits followUp){
		if(followUp!=null&&followUp.getId()!=null){
			followUpVisitsDao.deleteFollowUp(followUp.getId());
		}
	}

	public FollowUpVisits getFollowById(Integer id) {
		return followUpVisitsDao.getFollowById(id);
	}
	
	public String queryFollowUp(FollowUpVisits follow){
		JSONObject jo = new JSONObject();
		if(follow != null && follow.getClientId() != null){
			List<FollowUpVisits> lstFollow = followUpVisitsDao.queryFollowUp(follow);
			if(!CollectionUtils.isEmpty(lstFollow)){
				String data = JsonUtils.getJsonString4JavaListDate(lstFollow, "yyyy-MM-dd HH:mm:ss");
				jo.put("data", data);
			}
		}else{
			jo.put("data", "");
		}
		return jo.toString();
	}
}
