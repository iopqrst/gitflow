package com.bskcare.ch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.service.ClientInfoForCMCCService;
import com.bskcare.ch.service.ClientInfoService;
import com.bskcare.ch.util.ApiInterfaceURL;
import com.bskcare.ch.util.HttpClientUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;

@Service
public class ClientInfoForCMCCServiceImpl implements ClientInfoForCMCCService {

	private transient final Logger log = Logger.getLogger(getClass());

	@Autowired
	ClientInfoDao clientInfoDao;
	@Autowired
	ClientInfoService clientInfoService;

	/**
	 */
	public String validateUser(String sessionData, String sign) {
		JSONObject jresult = new JSONObject();
		
		// 0、验证sign
		
		// 1、通过sessionData等信息获取用户的uid
		String uid = getUserIdFormCMCC(sessionData);

		if (StringUtils.isEmpty(uid)) {
			return StringUtils.encapsulationJSON(jresult,
					Constant.INTERFACE_FAIL, "从移动获取uid失败", "");
		}
		// 2、根据用户的uid查询是否在血糖高管中注册过
		ClientInfo ci = this.getClientInfoByUid(uid);

		if (null == ci) {
			// 获取移动用户数据，在血糖高管中重新注册，返回用户对象
			ci = registerXTGG(uid);
		}
		
		if(null != ci) {
			JSONObject jdata = new JSONObject();
			jdata.accumulate("cid", ci.getId());
			jdata.accumulate("mobile", ci.getMobile());
			
			return StringUtils.encapsulationJSON(jresult,
					Constant.INTERFACE_SUCC, "用户认证成功", jdata.toString());
		} else {
			return StringUtils.encapsulationJSON(jresult,
					Constant.INTERFACE_FAIL, "用户认证失败", "");
		}
		
	}
	
	/**
	 * 
	 * @param uid
	 * @return
	 */
	private ClientInfo registerXTGG(String uid) {
		ClientInfo clientFromCMCC = getCMCCUserByUid(uid); // 获取到手机号后要判断改手机号是否在血糖高管中存在，如果存在直接修改原有数据，否则需要新建一条数据
		
		if(null == clientFromCMCC) {
			return null;
		}
		
		//TODO 正式环境打开
//		if (null == clientFromCMCC || //手机号格式不正确，认证失败
//				(null != clientFromCMCC && !StringUtils.isMobile(clientFromCMCC.getMobile()))) {
//			return null;
//		}
		
		
		
		ClientInfo queryci = new ClientInfo();
		queryci.setMobile(clientFromCMCC.getMobile());
		
		List<ClientInfo> clientList = clientInfoDao.findClientInfo(queryci);
		
		if (CollectionUtils.isEmpty(clientList)) {
			// 调用注册
			return clientInfoService.createExperienceClient(clientFromCMCC, "cmcc", null);
		} else {
			// 调用修改
			return modifyClientForCMCC(uid, clientFromCMCC,
					clientList);
		}
	}

	private ClientInfo modifyClientForCMCC(String uid,
			ClientInfo clientFromCMCC, List<ClientInfo> clientList) {
		ClientInfo modifyClient = clientList.get(0);
		if (StringUtils.isEmpty(modifyClient.getName()) ) {
			modifyClient.setName(clientFromCMCC.getName());
		}
		
		if(null == modifyClient.getBirthday()) {
			modifyClient.setBirthday(clientFromCMCC.getBirthday());
		}
		
		modifyClient.setCompSource(ClientInfo.COMPANY_USER_CMCC);
		modifyClient.setUid(uid);
		
		clientInfoDao.update(modifyClient);
		return modifyClient;
	}
	
	/**
	 * 根据用户uid查询血糖高管用户表中是否存在移动用户
	 * @param uid
	 * @return
	 */
	private ClientInfo getClientInfoByUid(String uid) {
		ClientInfo queryci = new ClientInfo();
		queryci.setUid(uid);
		queryci.setCompSource(ClientInfo.COMPANY_USER_CMCC);

		List<ClientInfo> list = clientInfoDao.findClientInfo(queryci);

		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 从移动接口处获取用户信息
	 * @param uid
	 */
	private ClientInfo getCMCCUserByUid(String uid) {
		String url = ApiInterfaceURL.getString("cmcc_get_userbyuid");

		Map<String, String> map = new HashMap<String, String>();
		map.put("appKey", ApiInterfaceURL.getString("cmcc_appKey"));
		map.put("invokeKey", ApiInterfaceURL.getString("cmcc_invokeKey"));
		
		map.put("uid", uid);

		String content = HttpClientUtils.getContentByPost(url, map, "utf-8");
		log.info(LogFormat.f("根据sessionData获取用户信息：" + content));

		ClientInfo ci = null;
		
		if (!StringUtils.isEmpty(content)) {
			Map jmap = JsonUtils.getMap4Json(content);

			if (null != jmap && jmap.size() > 0) {

				Object jresult = jmap.get("jsonResult");

				if (null != jresult) { // 成功
					Map resultMap = JsonUtils.getMap4Json(jresult + "");

					Object resultCode = resultMap.get("resultCode");
					
					if ("0".equals(resultCode + "")) {
						Object data = resultMap.get("data");
						
						Map dataMap = JsonUtils.getMap4Json(data + "");
						
						if(null == dataMap.get("userPreferredMobile")) {
							return null;
						}
						
						String userName = null == dataMap.get("userName") ? "" : dataMap.get("userName") + "";

						log.info(LogFormat.f(dataMap.get("userPreferredMobile") + ""));
						log.info(LogFormat.f(userName));
						
						ci = new ClientInfo();
						ci.setMobile(dataMap.get("userPreferredMobile") + "");
						ci.setCompSource(ClientInfo.COMPANY_USER_CMCC);
						ci.setUid(uid);
						ci.setName(userName);
						ci.setPassword("888888");
					}

				}
			}
		}

		return ci;
	}

	/**
	 * 获取用户id
	 * 
	 * @param sessionData
	 *            用户加密信息
	 * @return
	 */
	private String getUserIdFormCMCC(String sessionData) {
		String url = ApiInterfaceURL.getString("cmcc_get_uid");

		Map<String, String> map = new HashMap<String, String>();
		map.put("appKey", ApiInterfaceURL.getString("cmcc_appKey"));
		map.put("invokeKey", ApiInterfaceURL.getString("cmcc_invokeKey"));
		map.put("remoteAppKey", ApiInterfaceURL.getString("cmcc_remoteAppKey"));
		map.put("sessionData", sessionData);

		String content = HttpClientUtils.getContentByPost(url, map, "utf-8");
		log.info(LogFormat.f("根据sessionData(" + sessionData + ")获取用户uid信息：" + content));

		if (!StringUtils.isEmpty(content)) {
			Map jmap = JsonUtils.getMap4Json(content);

			Object jresult = jmap.get("jsonResult");

			if (null != jresult) { // 成功
				Map resultMap = JsonUtils.getMap4Json(jresult + "");

				Object resultCode = resultMap.get("resultCode");
				
				if ("0".equals(resultCode + "")) {
					return resultMap.get("data") + "";
				}

			}
		}
		log.error(LogFormat.f(""));
		log.error(LogFormat.f(""));
		log.error(LogFormat.f("获取用户Id 失败， 请注意!!"));
		log.error(LogFormat.f(""));
		log.error(LogFormat.f(""));
		return null;
	}
}
