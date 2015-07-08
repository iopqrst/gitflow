//package com.bskcare.ch.service.impl;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.bskcare.ch.dao.RegSourceDao;
//import com.bskcare.ch.service.RegSourceService;
//import com.bskcare.ch.util.StringUtils;
//import com.bskcare.ch.vo.RegSource;
//
//@Service
//public class RegSourceServiceImpl implements RegSourceService{
//
//	@Autowired
//	private RegSourceDao regSourceDao;
//	
//	public RegSource addRegSource(Integer cid,String source){
//		RegSource rs = new RegSource();
//		if(cid != null){
//			rs.setClientId(cid);
//			if(!StringUtils.isEmpty(source)){
//				int index = source.indexOf("-");
//				if(index != -1){
//					String ver = source.substring(index+1);
//					rs.setVersion(ver);
//					String sour = source.substring(0, index);
//					rs.setSource(sour);
//				}else{
//					rs.setSource(source);
//				}
//			}
//			rs.setCreateTime(new Date());
//			
//			return regSourceDao.add(rs);
//		}
//		return null;
//	}
//
//	public List queryStatisticList(String timeType) {
//		return regSourceDao.queryStatisticList(timeType);
//	}
//}
