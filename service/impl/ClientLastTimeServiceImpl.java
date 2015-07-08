//package com.bskcare.ch.service.impl;
//
//import java.util.Date;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.bskcare.ch.dao.ClientLastTimeDao;
//import com.bskcare.ch.service.ClientLastTimeService;
//import com.bskcare.ch.util.StringUtils;
//import com.bskcare.ch.vo.ClientLastTime;
//
//@Service
//public class ClientLastTimeServiceImpl implements ClientLastTimeService{
//	
//	@Autowired
//	private ClientLastTimeDao lastTimeDao;
//	
//	/**根据clientId修改指定字段的信息**/
//	public void updateLastTime(Integer clientId,String type){
//		if(clientId != null&!StringUtils.isEmpty(type)){
//			ClientLastTime lastTime = lastTimeDao.queryLastTimeByClientId(clientId);
//			//不等于null说明已经存在该客户的记录，修改。反之添加记录
//			if(lastTime != null){
//				lastTimeDao.updateLastTime(clientId, type);
//			}else{
//				ClientLastTime lt = new ClientLastTime();
//				lt.setClientId(clientId);
//				if(type.equals("lastFollowTime")){
//					lt.setLastFollowTime(new Date());
//				}else{
//					lt.setLastUploadDateTime(new Date());
//				}
//				lastTimeDao.add(lt);
//			}
//		}
//	}
//}
