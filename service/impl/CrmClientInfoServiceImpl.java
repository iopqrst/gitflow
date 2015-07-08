package com.bskcare.ch.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.bo.crm.CrmClientInfo;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.SyncDataDao;
import com.bskcare.ch.service.CrmClientInfoService;
import com.bskcare.ch.util.CrmResponse;
import com.bskcare.ch.util.CrmURLConfig;
import com.bskcare.ch.util.HttpClientUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.SyncData;
import com.bskcare.ch.vo.UserInfo;
import com.opensymphony.xwork2.ActionContext;

@Service
public class CrmClientInfoServiceImpl implements CrmClientInfoService{
	
	@Autowired
	private SyncDataDao syncDataDao;
	
	/**
	 * 增加用户和crm同步
	 */
	public void addCrmClientInfo(ClientInfo clientInfo){
		CrmClientInfo crmClient = null;
		if(clientInfo != null){
			crmClient = new CrmClientInfo();
			//用户id
			if(clientInfo.getId() != null){
				crmClient.setClientId(clientInfo.getId());
			}
			//会员卡号
			if(!StringUtils.isEmpty(clientInfo.getVipCard())){
				crmClient.setCardid(clientInfo.getVipCard());
			}
			//用户区域id
			if(clientInfo.getAreaId() != null){
				crmClient.setAreaId(clientInfo.getAreaId());
			}
			//用户姓名
			if(!StringUtils.isEmpty(clientInfo.getName())){
				crmClient.setName(clientInfo.getName());
			}
			//用户性别
			if(clientInfo.getGender() != null){
				crmClient.setSex(clientInfo.getGender());
			}
			//用户年龄
			if(clientInfo.getAge() != null){
				crmClient.setAge(clientInfo.getAge());
			}
			//用户生日
			if(clientInfo.getBirthday() != null){
				crmClient.setBirthday(clientInfo.getBirthday());
			}
			//用户身份证号
			if(!StringUtils.isEmpty(clientInfo.getIdCards())){
				crmClient.setPid(clientInfo.getIdCards());
			}
			//用户邮箱
			if(!StringUtils.isEmpty(clientInfo.getEmail())){
				crmClient.setEmail(clientInfo.getEmail());
			}
			//用户手机号码
			if(!StringUtils.isEmpty(clientInfo.getMobile())){
				crmClient.setMobile(clientInfo.getMobile());
			}
			//用户电话
			if(!StringUtils.isEmpty(clientInfo.getPhone())){
				crmClient.setTelephone(clientInfo.getPhone());
			}
			//用户住址
			if(!StringUtils.isEmpty(clientInfo.getAddress())){
				crmClient.setAddress(clientInfo.getAddress());
			}
			//用户工作单位
			if(!StringUtils.isEmpty(clientInfo.getWorkUnits())){
				crmClient.setWorkAddress(clientInfo.getWorkUnits());
			}
		}
		
		if(crmClient != null){
			// 获得链接crm项目url
			String url = CrmURLConfig.getString("crm_base_url")
					+ CrmURLConfig.getString("crm_add_clientInfo");
			
			HashMap<String, String> parmas = new HashMap<String, String>();
			parmas.put("clientInfoStr", JsonUtils.getJsonString4JavaPOJO(crmClient));
			
			// 获得返回（跟根据url 和 参数）
			CrmResponse crs = HttpClientUtils.getCrmResponse(url, parmas);
			syncData(crs,crmClient,SyncData.DEAL_TYPE_ADD);
		}
	}

	
	/**
	 * 修改用户信息是和crm同步
	 */
	public void updateCrmClientInfo(ClientInfo clientInfo,String source){
		CrmClientInfo crmClient = null;
		if(clientInfo != null){
			crmClient = new CrmClientInfo();
			//用户id
			if(clientInfo.getId() != null){
				crmClient.setClientId(clientInfo.getId());
			}
			
			//会员卡号
			if(!StringUtils.isEmpty(clientInfo.getVipCard())){
				crmClient.setCardid(clientInfo.getVipCard());
			}
			//用户区域id
			if(clientInfo.getAreaId() != null){
				crmClient.setAreaId(clientInfo.getAreaId());
			}
			//用户姓名
			if(!StringUtils.isEmpty(clientInfo.getName())){
				crmClient.setName(clientInfo.getName());
			}
			//用户性别
			if(clientInfo.getGender() != null){
				crmClient.setSex(clientInfo.getGender());
			}
			//用户年龄
			if(clientInfo.getAge() != null){
				crmClient.setAge(clientInfo.getAge());
			}
			//用户生日
			if(clientInfo.getBirthday() != null){
				crmClient.setBirthday(clientInfo.getBirthday());
			}
			//用户身份证号
			if(!StringUtils.isEmpty(clientInfo.getIdCards())){
				crmClient.setPid(clientInfo.getIdCards());
			}
			//用户邮箱
			if(!StringUtils.isEmpty(clientInfo.getEmail())){
				crmClient.setEmail(clientInfo.getEmail());
			}
			//用户手机号码
			if(!StringUtils.isEmpty(clientInfo.getMobile())){
				crmClient.setMobile(clientInfo.getMobile());
			}
			//用户电话
			if(!StringUtils.isEmpty(clientInfo.getPhone())){
				crmClient.setTelephone(clientInfo.getPhone());
			}
			//用户住址
			if(!StringUtils.isEmpty(clientInfo.getUsualAddress())){
				crmClient.setAddress(clientInfo.getUsualAddress());
			}
			//用户工作单位
			if(!StringUtils.isEmpty(clientInfo.getWorkUnits())){
				crmClient.setWorkAddress(clientInfo.getWorkUnits());
			}
			
			//判断是用户发送的请求还是定时器发送的请求
			if(source.equals("web")){
				//管理员id
				UserInfo userInfo = (UserInfo)ActionContext.getContext().getSession().get(Constant.MANAGE_USER);
				if(userInfo != null){
					Integer userId = userInfo.getId();
					crmClient.setUserid(userId);
				}
			}
			
		}
		if(crmClient != null){
			// 获得链接crm项目url
			String url = CrmURLConfig.getString("crm_base_url")
					+ CrmURLConfig.getString("crm_update_clientInfo");
			
			HashMap<String, String> parmas = new HashMap<String, String>();
			parmas.put("clientInfoStr", JsonUtils.getJsonString4JavaPOJO(crmClient, "yyyy-MM-dd"));
			
			// 获得返回（跟根据url 和 参数）
			CrmResponse crs = HttpClientUtils.getCrmResponse(url, parmas);
			
			syncData(crs,crmClient,SyncData.DEAL_TYPE_UPDATE);
		}
	}
	
	
	public void syncData(CrmResponse crs,CrmClientInfo crmClient,Integer dealType){
		SyncData sync = new SyncData();
		sync.setType(SyncData.TYPE_CLIENTINFO);
		sync.setDataId(crmClient.getClientId());
		sync.setDealType(dealType);
		
		if(crs.getCode().equals(CrmResponse.CODE_FAIL)){
			if(sync != null){
				SyncData syncData = syncDataDao.querySyncByTypeAndDataId(sync);
				if(syncData != null){
					syncData.setStatus(SyncData.STATUS_NOT_SYNC);
					syncDataDao.update(syncData);
				}else{
					sync.setStatus(SyncData.STATUS_NOT_SYNC);
					syncDataDao.add(sync);
				}
			}
		}else{
			SyncData syncData = syncDataDao.querySyncByTypeAndDataId(sync);
			if(null != syncData){
				syncData.setStatus(SyncData.STATUS_SYNC);
				syncDataDao.update(syncData);
			}
		}
	}
}
