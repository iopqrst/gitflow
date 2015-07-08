package com.bskcare.ch.service.impl.online;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.dao.online.OnlineSubscribeDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.online.OnlineSatisfactionService;
import com.bskcare.ch.service.online.OnlineSubscribeService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.BskExpert;
import com.bskcare.ch.vo.online.OnlineSubscribe;

@Service
@SuppressWarnings("unchecked")
public class OnlineSubscribeServiceImpl implements OnlineSubscribeService {

	@Autowired
	OnlineSubscribeDao osubscribeDao;
	
	@Autowired
	OnlineSatisfactionService osatisService;

	public void add(OnlineSubscribe osub) {
		osubscribeDao.add(osub);
		osatisService.calStatisAndSubCount(osub.getExpertId());
	}

	public String queryStringOfSubscribe(OnlineSubscribe osub,
			QueryInfo queryInfo) {
		PageObject obj = osubscribeDao.querySubscribe(osub, queryInfo);
		JSONArray ja = new JSONArray();
		if (null != obj && null != obj.getDatas() && obj.getDatas().size() > 0) {
			List<Object> list = obj.getDatas();
			
			for (Object objs : list) {
				Object[] oo = (Object[]) objs;
				OnlineSubscribe os = (OnlineSubscribe) oo[0];
				BskExpert be = (BskExpert) oo[1];

				JSONObject jo = new JSONObject();
				jo.accumulate("osid", os.getId()); //预约任务Id
				jo.accumulate("name", be.getName());
				jo.accumulate("expertise", be.getExpertise());
				
				jo.accumulate("satisfaction", be.getSatisfaction());
				jo.accumulate("subscribeCount", be.getSubscribeCount());
				
				//真的
//				jo.accumulate("satisfaction", be.getSatisfaction());
//				jo.accumulate("subscribeCount", be.getSubscribeCount());
				
				jo.accumulate("role", BskExpert.ROLE_DOCTOR == be.getRole() ? "医生":"健康管理师");
				jo.accumulate("mobile", os.getMobile());
				String imgPath = SystemConfig.getString("image_base_url");
				jo.accumulate("portrait", imgPath + be.getPortrait());
				jo.accumulate("userId", be.getUserId());//医生管理员对应Id
				jo.accumulate("expertId", be.getId()); //专家Id
				jo.accumulate("status", os.getStatus());
				jo.accumulate("hasSatisfaction", os.getHasSatisfaction()); // 是否已经评价
				jo.accumulate("subTime", DateUtils.formatDate(
						DateUtils.LONG_DATE_PATTERN, os.getSubTime())); // 已经预约时间
				jo.accumulate("desc", os.getRemark());// 症状描述
				jo.accumulate("createTime", DateUtils.formatDate(
						DateUtils.LONG_DATE_PATTERN, os.getCreateTime()));// 创建时间
				jo.accumulate("finishTime", null != os.getFinishTime() ? DateUtils.formatDate(
						DateUtils.LONG_DATE_PATTERN, os.getFinishTime()) : null);
				
				ja.add(jo);
			}
		}
		return ja.toString();
	}
	
	public PageObject<Object> querySubscribeInfo(OnlineSubscribe osub,
			QueryInfo queryInfo) {

		return null;
	}

	public PageObject querySubscribeByUserId(OnlineSubscribe osub,
			QueryCondition query, QueryInfo queryInfo){
		return osubscribeDao.querySubscribeByUserId(osub, query, queryInfo);
	}
	
	public void dealSubById(OnlineSubscribe osub){
		if(osub != null){
			Integer id = osub.getId();
			OnlineSubscribe sub = osubscribeDao.load(id);
			BeanUtils.copyProperties(osub, sub, new String []{"clientId","expertId","mobile","remark","hasSatisfaction","subTime","createTime"});
			osubscribeDao.update(sub);
		}
	}
}
