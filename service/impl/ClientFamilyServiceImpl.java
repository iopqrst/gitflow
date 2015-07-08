package com.bskcare.ch.service.impl;

import java.io.UnsupportedEncodingException;

import java.util.Iterator;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.FamilyRelationDao;
import com.bskcare.ch.dao.ShortMessageDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientFamilyService;
import com.bskcare.ch.service.CrmClientInfoService;
import com.bskcare.ch.service.OrderMasterService;
import com.bskcare.ch.service.OrderServiceItemService;
import com.bskcare.ch.service.ShortMessagService;
import com.bskcare.ch.util.MD5Util;
import com.bskcare.ch.util.Message;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.FamilyRelation;
import com.bskcare.ch.vo.ShortMessage;
import com.bskcare.ch.vo.order.ServiceExpense;

@Service
@SuppressWarnings("unchecked")
public class ClientFamilyServiceImpl implements ClientFamilyService{
	
	@Autowired
	private ClientInfoDao clientInfoDao ; 
	@Autowired
	private FamilyRelationDao familyRelationDao ; 
	@Autowired
	private ShortMessagService shortMessagService ;
	@Autowired
	private OrderServiceItemService serviceItemService;
	@Autowired
	private OrderMasterService orderService;
	@Autowired
	private CrmClientInfoService crmClientService;
	
	public List getFamilyList(FamilyRelation familyRelation, QueryInfo queryInfo) {
		PageObject page = familyRelationDao.getFamilyList(familyRelation,queryInfo);
		return page.getDatas() ;
	}
	
	public int addFamily(FamilyRelation familyRelation, ClientInfo clientInfo,ClientInfo myInfo) throws UnsupportedEncodingException {
		String cmcItem = Constant.ITEM_FAMILY_SMS;
		//判断是否有服务
		int result = serviceItemService.expenseService(cmcItem, myInfo.getId(), 1, ServiceExpense.NEED_RECORD);
		if( Constant.EXPENSE_SERVICE_SUC != result) {
			//return 2;
		}
		
		//取出添加的所有亲情账号  如果重复添加 返回为3
		familyRelation.setClientId(myInfo.getId()) ;
		PageObject page = familyRelationDao.getFamilyList(familyRelation,new QueryInfo(20, null, null, null));
		List list = page.getDatas() ;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			ClientInfo tmp0 = (ClientInfo) object[0] ;
			if(tmp0.getMobile().equals(clientInfo.getMobile())){
				return 3 ; 
			}
			
		}
		
		//判断clientInfo表中  是否存在增加的用户
		List tmpList = clientInfoDao.findClientInfo(clientInfo) ;
		
		ShortMessage shortMessage = new ShortMessage() ;
		String smsType = Message.getString("family_title");
		shortMessage.setType(smsType) ;		
		String userMsg = StringUtils.isEmpty(myInfo.getName()) ? myInfo.getMobile() : myInfo.getName();
		
		if(!CollectionUtils.isEmpty(tmpList)){
			clientInfo =(ClientInfo) tmpList.get(0) ;
			String sms_content = Message.getString("exist_user_sms_content");
			shortMessage.setContent(MessageFormat.format(sms_content, userMsg)) ;
			shortMessage.setType(smsType) ;
			familyRelation.setFamilyId(clientInfo.getId()) ;
		}else{
			clientInfo.setType(ClientInfo.TYPE_EXPERIENCE) ;
			String password = RandomUtils.getRandomNumber(6);
			clientInfo.setPassword(MD5Util.digest(password+"")) ;
			clientInfo.setCreateTime(new Date()) ;
			
			clientInfo.setAreaId(myInfo.getAreaId()) ;
			clientInfo.setAreaChain(myInfo.getAreaChain());
			
			clientInfo = clientInfoDao.add(clientInfo) ;
			
			orderService.createRegisterOrder(ClientInfo.TYPE_EXPERIENCE, clientInfo);
			
			if(clientInfo != null){
				crmClientService.addCrmClientInfo(clientInfo);
			}
			
			String sms_content = Message.getString("unexist_user_sms_content");
			sms_content = MessageFormat.format(sms_content, new Object[]{userMsg,clientInfo.getMobile(),password});
			shortMessage.setContent(sms_content) ;
			
			shortMessage.setClientId(clientInfo.getId());
			shortMessage.setMobile(clientInfo.getMobile());
			
			familyRelation.setFamilyId(clientInfo.getId()) ;
		}

		int statu = shortMessagService.sendMessage(shortMessage, "more") ;			
		
		familyRelation.setClientId(myInfo.getId()) ;
		familyRelation = familyRelationDao.addFamilyRelation(familyRelation) ;
		if(familyRelation.getId()!=null){
			return 1 ; 
		}else{
			return 0 ;
		}
	}
	public void updateFamily(FamilyRelation familyRelation) {
		familyRelationDao.updateFamily(familyRelation) ;
	}
	
	public void deleteFamily(FamilyRelation familyRelation) {
		familyRelationDao.deleteFamily(familyRelation) ;
	}

	public PageObject getManegeFamily(FamilyRelation familyRelation,QueryInfo queryInfo) {
		return familyRelationDao.getFamilyList(familyRelation,queryInfo);
	}

	public String getFamilyList(List list) {
		JSONArray pArray = new JSONArray();
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			ClientInfo tmp0 = (ClientInfo) object[0] ;
			FamilyRelation tmp = (FamilyRelation) object[1] ;

			JSONObject datajson = new JSONObject();
			datajson.put("familyName", tmp.getFamilyName()) ;
			datajson.put("shortMessage", tmp.getShortMessage()) ;
			datajson.put("familyRelation", tmp.getFamilyRelation()) ;
			datajson.put("clientId", tmp.getClientId()) ;
			datajson.put("id", tmp.getId()) ;
			datajson.put("familyId", tmp.getFamilyId()) ;
			datajson.put("mobile", tmp0.getMobile()) ;
			
			pArray.add(datajson) ;
		}
		
		JSONObject json = new JSONObject();
		json.put("code","1") ;
		json.put("msg", Message.getString("getFamilyList_ok")) ;
		json.put("data",pArray);
		
		return json.toString() ;
	}

}
