package com.bskcare.ch.service.impl.timeLine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.timeLine.TimeLineRuleCustomDao;
import com.bskcare.ch.service.timeLine.TimeLineRuleCustomService;
import com.bskcare.ch.service.timeLine.TimeLineRuleService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.timeLine.TimeLineRule;
import com.bskcare.ch.vo.timeLine.TimeLineRuleCustom;

@Service
@SuppressWarnings("unchecked")
public class TimeLineRuleCustomServiceImpl implements TimeLineRuleCustomService {
	@Autowired
	private TimeLineRuleCustomDao customDao;
	@Autowired
	private TimeLineRuleService ruleService;

	public void updateRuleCustom(Integer cid, String ruleCustoms) {
		List<TimeLineRuleCustom> ruleCustom = JsonUtils.getList4Json(
				ruleCustoms, TimeLineRuleCustom.class);
		if (!CollectionUtils.isEmpty(ruleCustom)) {

			// 新添加的自定义设置明天生效，删除用户之前的设置，统一更改
			customDao.deleteRuleByCid(cid);
			for (TimeLineRuleCustom timeLineRuleCustom : ruleCustom) {
				timeLineRuleCustom.setClientId(cid);
				timeLineRuleCustom.setSoftType(Constant.SOFT_GUAN_XUE_TANG);
				customDao.add(timeLineRuleCustom);
			}
		}
	}

	public String getRuleCustom(Integer cid, Integer softType) {
		TimeLineRuleCustom custom = new TimeLineRuleCustom();
		custom.setClientId(cid);
		JSONArray ja = new JSONArray();
		JSONObject jo = null;
		List<TimeLineRuleCustom> ruleCustom = customDao
				.getlineRuleCustoms(custom);
		//定义添加所有能修改的项目
		List<Integer> conTypeList = new ArrayList<Integer>();
		conTypeList.add(TimeLineRule.CONTYPE_BREAKFAST);
		conTypeList.add(TimeLineRule.CONTYPE_BREAKFASTJIA);
		conTypeList.add(TimeLineRule.CONTYPE_DINNER);
		conTypeList.add(TimeLineRule.CONTYPE_LUNCH);
		conTypeList.add(TimeLineRule.CONTYPE_LUNCHJIA);
		conTypeList.add(TimeLineRule.CONTYPE_GETUP);
		conTypeList.add(TimeLineRule.CONTYPE_SIESTA);
		conTypeList.add(TimeLineRule.CONTYPE_SLEEP);
		conTypeList.add(TimeLineRule.CONTYPE_NEW);
		
		if(!CollectionUtils.isEmpty(ruleCustom)){
			for (TimeLineRuleCustom timeLineRuleCustom : ruleCustom) {
				jo = new JSONObject();
				jo.put("status", timeLineRuleCustom.getStatus());
				jo.put("conType", timeLineRuleCustom.getConType());
				jo.put("taskTime", timeLineRuleCustom.getTaskTime());
				conTypeList.remove(timeLineRuleCustom.getConType());//每拿到一个符合的数据添加到jsonarray，就再定义的项目中删除一个，
				ja.add(jo);
			}
		}
		if (conTypeList.size()==0) {//如果定义的项目为空，说明所有的项目已经添加到返回的jsonarray
			System.out.println("直接返回用户自己定义的消息推送时间  , 返回数据jo = "+ja.toString());
			return ja.toString();
		}else{
			ruleCustom = null;
		}
		//如果遍历用户自己的没有达到要求， 再遍历系统默认的规则表(tg_time_line_rule)
		if (CollectionUtils.isEmpty(ruleCustom)) {
			// 如果用户自定义设置时空的，取得用户默认的时间轴
			System.out.println("用户自己定义的消息推送时间不完整");
			System.out.println("查询用户符合的时间轴规则列表。");
			List<TimeLineRule> lineRules = ruleService.queryList(cid, softType);
			if (CollectionUtils.isEmpty(lineRules)) {
				System.out.println("返回用户默认的时间轴时间信息失败");
				return null;
			} else {
				System.out.println("返回用户默认时间轴信息成功");
				// 初始化可以修改的项目
				for (TimeLineRule timeLineRule : lineRules) {// 找到所有的允许修改的任务的默认的时间的Time
					if(CollectionUtils.isEmpty(conTypeList)) break;//如果列表为空，说明已经拿到所有的信息
					for (Integer integer : conTypeList) {
						if (integer.equals(timeLineRule.getConType())) {// 取得可修改项目，如果有，将此项添加，删除已添加的，如果没有直接跳过
							jo = new JSONObject();
							jo.put("status", TimeLineRuleCustom.STAUTS_STAUTS);
							jo.put("conType", timeLineRule.getConType());
							jo.put("taskTime", timeLineRule.getTaskTime());
							ja.add(jo);//拿到一条后添加到jsonarray
							conTypeList.remove(integer);//从定义的项目集合删除这个项目
							break;//相同的类型拿到一条信息后返回，不再判断获取第二条。
						}
					}
				}
			}
		}
		int i = conTypeList.size();
		for (int j = 0; j < i; j++) {
			jo.put("status", TimeLineRuleCustom.STAUTS_STAUTS);
			jo.put("conType", conTypeList.get(j));
			jo.put("taskTime", "12:00");
			ja.add(jo);
			i -- ;
		}
		
		if (i==0) {
			System.out.println("返回包括默认的信息"+ja.toString());
			return ja.toString();
		}
		return null;
	}

}
