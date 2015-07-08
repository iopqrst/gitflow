package com.bskcare.ch.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.type.IntegerType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientMedicalHistoryDao;
import com.bskcare.ch.service.ClientMedicalHistoryService;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.client.ClientMedicalHistory;

import freemarker.template.utility.StringUtil;

@Service
public class ClientMedicalHistoryServiceImpl implements ClientMedicalHistoryService {

	@Autowired
	private ClientMedicalHistoryDao cmhDao;
	
	public ClientMedicalHistory getClientMedicalHistory(Integer clientId) {
		if(null == clientId) return null;
		return cmhDao.getClientMedicalHistory(clientId);
	}

	public void saveOrUpdate(ClientMedicalHistory cd) {
		if(null != cd && null != cd.getClientId()) {
			ClientMedicalHistory clp = getClientMedicalHistory(cd.getClientId());
			if(null != clp) {
				BeanUtils.copyProperties(cd, clp, new String[]{"createTime"});
				cmhDao.update(clp);
			} else {
				cd.setCreateTime(new Date());
				cmhDao.add(cd);
			}
		} 
	}

	
	public void saveOrUpdateAndroid(ClientMedicalHistory cd) {
		if(null != cd && null != cd.getClientId()) {
			ClientMedicalHistory clp = getClientMedicalHistory(cd.getClientId());
			if(null != clp) {
				BeanUtils.copyProperties(cd, clp, new String[]{"id","deformity","createTime","xrAllergy","xrAllergen",
						"srAllergy","srAllergen","jcAllergy","jcAllergen","zsAllergy","zsAllergen","selfAllergy",
						"selfAllergen","menstrual","bear","bearSupply","surgery","","surgeryDetail","trauma",
						"detail","transfusionOfBlood","supply","isHasAllergy"});
				cmhDao.update(clp);
			} else {
				cd.setCreateTime(new Date());
				cmhDao.add(cd);
			}
		} 
	}
	
	public List<ClientMedicalHistory> queryAll() {
		return cmhDao.queryAll();
	}
	
	public ClientMedicalHistory findById(String diseaseName) {
		String hql = " from ClientMedicalHistory where name";
		return (ClientMedicalHistory) cmhDao.findUniqueResult(hql,diseaseName);
	}

	public void saveArchiveAllergy(String[] strs, Integer cid) {
		//更新黄金页面的过敏信息
		ClientMedicalHistory history=cmhDao.getClientMedicalHistory(cid);
		if(history==null){
			history=new ClientMedicalHistory();
			history.setClientId(cid);
		}
		history.setIsHasAllergy(1);//是否有过敏项
		history.setXrAllergy("1");//有吸入式过敏
		history.setSrAllergy("1");//有食入式过敏
		history.setJcAllergy("1");//有
		//验证黄金档案吸入式过敏 花粉，粉尘
		history.setXrAllergen(disposeAllergen(strs,history.getXrAllergen(),"花粉"));
		history.setXrAllergen(disposeAllergen(strs,history.getXrAllergen(),"粉尘"));
		history.setSrAllergen(disposeAllergen(strs,history.getSrAllergen(),"奶类"));
		history.setSrAllergen(disposeAllergen(strs,history.getSrAllergen(),"海鲜类"));
		history.setSrAllergen(disposeAllergen(strs,history.getSrAllergen(),"牛羊肉"));
		history.setSrAllergen(disposeAllergen(strs,history.getSrAllergen(),"水果类"));
		history.setJcAllergen(disposeAllergen(strs,history.getJcAllergen(),"螨虫"));
		history.setJcAllergen(disposeAllergen(strs,history.getJcAllergen(),"冷空气"));
		history.setJcAllergen(disposeAllergen(strs,history.getJcAllergen(),"化妆品"));
		if(history.getId()!=null){
			cmhDao.update(history);
		}else{
			cmhDao.add(history);
		}
	}
	public String disposeAllergen(String[] array,String longString,String shortString){
		String dbString="";
		if(longString!=null){
			dbString = longString;
		}
		//判断，页面是否选择shortString项
		if(StringUtils.contains(shortString, array)){//如果选了shortString
			if(!StringUtils.contains(dbString, shortString)){//并且数据库字段没有shortString
				if(dbString.length()<1){
					dbString = longString+shortString;
				}else{
					dbString = longString+ "," +shortString;
				}
			}
		}
		if(!StringUtils.contains(shortString, array)){//如果页面没有选择shortString项
			if(StringUtils.contains(dbString, shortString)){//并且数据库有shortString项
				dbString = StringUtils.replace(dbString, shortString+",", "");
				dbString = StringUtils.replace(dbString, ","+shortString, "");
				dbString = StringUtils.replace(dbString, shortString, "");
			}
		}
		return dbString;
	}
	
}
