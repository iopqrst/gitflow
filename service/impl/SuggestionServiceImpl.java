package com.bskcare.ch.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.SuggestionExtend;
import com.bskcare.ch.dao.BskExpertDao;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.SuggestionDao;
import com.bskcare.ch.dao.UserInfoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.SuggestionService;
import com.bskcare.ch.util.CompareSuggestTime;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.Suggestion;

@Service
public class SuggestionServiceImpl implements SuggestionService {

	@Autowired
	private SuggestionDao suggestionDao;
	@Autowired
	private ClientInfoDao clientInfoDao;
	@Autowired
	BskExpertDao bskExpertDao;
	@Autowired
	UserInfoDao userInfoDao;

	public Suggestion add(Suggestion suggestion) {
		return suggestionDao.add(suggestion);
	}

	public String queryClient(Suggestion su, QueryInfo queryInfo) {
		if (null == su) {
			su = new Suggestion();
			su.setStatus(Suggestion.STATUS_UNREAD);
		}
		PageObject<Suggestion> list = suggestionDao.queryClientSuggestion(su,queryInfo);
		JSONObject joo = new JSONObject();
		if (null != list && !CollectionUtils.isEmpty(list.getDatas())) {
			List<Suggestion> suList = list.getDatas();

			CompareSuggestTime cct = new CompareSuggestTime();
			Collections.sort(suList, cct); // 升序排序

			ClientInfo client = null; // 用户发送者

			if (su.getSender().startsWith("c_")) { // 发送者为用户
				client = getClientInfo(su.getSender());
			}

			joo.accumulate("soft", su.getSoft());
			String base = SystemConfig.getString("image_base_url");
			if (null != client) {
				joo.accumulate("cname", client.getName()); // 用户信息;
				joo.accumulate("cid", client.getId());
				joo.accumulate("mobile", client.getMobile());
				joo.accumulate("headPortrait", base + client.getHeadPortrait());
			}

			JSONArray jb = new JSONArray();
			for (int i = 0; i < suList.size(); i++) {
				Suggestion r = suList.get(i);
				JSONObject jo = new JSONObject();
				jo.accumulate("type", r.getType());
				jo.accumulate("suggestion", r.getSuggestion());
				jo.accumulate("createTime", DateUtils.formatDate(
						DateUtils.LONG_DATE_PATTERN, r.getCreateTime()));
				jb.add(jo);
			}
			joo.accumulate("list", jb);
		}
		return joo.toString();
	}

	private String getNumForString(String str) {
		if (!StringUtils.isEmpty(str)) {
			Pattern p = Pattern.compile("\\d{1,100}$");
			Matcher m = p.matcher(str);

			String num = null;
			while (m.find()) {
				num = m.group();
			}
			return num;
		}
		return null;
	}

	public PageObject<SuggestionExtend> queryList(Suggestion su,QueryInfo queryInfo, ClientInfo clientInfo) {
		return suggestionDao.queryList(su,queryInfo, clientInfo);
	}

	public String queryListBySender(Suggestion su, QueryInfo queryInfo) {
		PageObject<SuggestionExtend> poor = suggestionDao.queryDetailSuggestList(su,
				queryInfo);
		JSONObject joo = new JSONObject();
		if (null != poor && !CollectionUtils.isEmpty(poor.getDatas())) {
			List<SuggestionExtend> suList = poor.getDatas();
			
			CompareSuggestTime cct = new CompareSuggestTime();
			Collections.sort(suList, cct); // 升序排序

			ClientInfo client = null; // 用户发送者

			if (su.getSender().startsWith("c_")) { // 发送者为用户
				client = getClientInfo(su.getSender());
			}

			joo.accumulate("soft", su.getSoft());

			if (null != client) {
				joo.accumulate("cname", client.getName()); // 用户信息;
				joo.accumulate("cid", client.getId());
				joo.accumulate("mobile", client.getMobile());
			}

			JSONArray ja = new JSONArray();

			for (int i = 0; i < suList.size(); i++) {
				SuggestionExtend r = suList.get(i);
				JSONObject jo = new JSONObject();
				jo.accumulate("type", r.getType());

				jo.accumulate("msg", StringUtils.textToHtml(r.getSuggestion()));
				jo.accumulate("createTime", DateUtils.formatDate(
						DateUtils.LONG_DATE_PATTERN, r.getCreateTime()));
				jo.accumulate("ename", r.getEname());//
				jo.accumulate("roleName", null == r.getRole() ? "" : r.getRole() == 1 ? "百生康大夫" : "百生康健康管理师");
				ja.add(jo);
			}
			joo.accumulate("list", ja);
		}
		return joo.toString();
	}

	/**
	 * 获取客户信息
	 */
	private ClientInfo getClientInfo(String sender) {
		try {
			return clientInfoDao
					.load(Integer.parseInt(getNumForString(sender)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String replyClient(Suggestion su, Integer manageId) {
		// 将用户意见修改为已读状态
		su.setStatus(Suggestion.STATUS_READED);
		su.setReceiver("d_" + manageId);
		suggestionDao.updateBysql(su);
		// 新增回复用户反馈意见
		Suggestion replySu = new Suggestion();
		replySu.setSuggestion(su.getSuggestion());
		replySu.setCreateTime(new Date());
		replySu.setReceiver(su.getSender());
		replySu.setSoft(su.getSoft());
		replySu.setType(Suggestion.TYPE_SEND_TO_CLIENT);
		replySu.setSender("d_" + manageId);
		Suggestion sugg = suggestionDao.add(replySu);
		JSONObject json = new JSONObject();
		if (null != sugg) {
			json.put("status", "sucess");
		} else {
			json.put("status", "fail");
		}
		return json.toString();
	}
}
