package com.bskcare.ch.service.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.bo.BskExpertExtend;
import com.bskcare.ch.dao.BskExpertDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.BskExpertService;
import com.bskcare.ch.service.UserInfoService;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.BskExpert;

@Service
@SuppressWarnings("unchecked")
public class BskExpertServiceImpl implements BskExpertService {

	@Autowired
	private BskExpertDao bskExpertDao;
	@Autowired
	UserInfoService userInfoService;

	public List<BskExpert> findBskExpertAll(){
		return bskExpertDao.findBskExpertAll();
	}
	
	public PageObject findBskExpert(BskExpert expert, QueryInfo queryInfo) {
		return bskExpertDao.findBskExpert(expert, queryInfo);
	}

	public BskExpert addBskExport(BskExpert exprt) {
		return bskExpertDao.add(exprt);
	}
	
	public void updateBskExport(BskExpert exprt) {
		BskExpert bsk = bskExpertDao.load(exprt.getId());
		BeanUtils.copyProperties(exprt, bsk, new String[] {"portrait","sort","satisfaction","subscribeCount","availableTime"});
		if(!StringUtils.isEmpty(exprt.getPortrait())){
			bsk.setPortrait(exprt.getPortrait());
		}
		
		bskExpertDao.update(bsk);
	}

	public BskExpert findBskExpertById(Integer id) {
		return bskExpertDao.load(id);
	}

	public void deleteBskExpert(Integer id) {
		bskExpertDao.delete(id);
	}
	
	public List<BskExpert> queryExpertByAreaId(Integer areaId) {
		String qid = getDocAndHealthId(areaId);
		if(StringUtils.isEmpty(qid)) return null;
		return bskExpertDao.queryExpertByUserIds(qid);
	}
	
	public List<BskExpertExtend> queryExpertExtendByAreaId(Integer areaId) {
		String qid = getDocAndHealthId(areaId);
		if(StringUtils.isEmpty(qid)) return null;
		return bskExpertDao.queryExpertByUids(qid);
	}
	
	public String getDocAndHealthId(Integer areaId) {
		String docRoleId = SystemConfig.getString("role_type_doc"); //大夫
		String hmRoleId = SystemConfig.getString("role_type_health_manger"); //健康管理师
		String docIIRoleId = SystemConfig.getString("role_type_doc_ii");//咨询大夫
		
		JSONArray ja = userInfoService.getAdmChainByAreaIdAndUserTypeIds(areaId,docIIRoleId+","+docRoleId+","+hmRoleId);
		if(null != ja) {
			Integer docId = 0;
			Integer hmId = 0;
			for (Object object : ja) {
				JSONObject json=(JSONObject) object;
				if(docRoleId.equals(json.getInt("roleType")+"")){
					docId = json.getInt("id");
					break;
				}
			}
			
			if(docId ==0 ) {//如果大夫没有找到则查询咨询大夫
				for (Object object : ja) {
					JSONObject json=(JSONObject) object;
					if(docIIRoleId.equals(json.getInt("roleType")+"")){
						docId = json.getInt("id");
						break;
					}
				}
			}
			
			for (Object object : ja) {
				JSONObject json=(JSONObject) object;
				if(hmRoleId.equals(json.getInt("roleType")+"")){ 
					hmId = json.getInt("id");
					break;
				}
			}
			return docId + "," + hmId;
		} else {
			return null;
		}
	}
	
	public PageObject queryBskExpert(String type, QueryInfo queryInfo){
		return bskExpertDao.queryBskExpert(type, queryInfo);
	}

	public List<BskExpert> findBskExpertByuserId(String userids) {
		return  bskExpertDao.queryExpertByUserIds(userids);
	}

	public BskExpert quertBskExpertByMobile(String mobile) {
		return null;
	}

	public int addEvalution(Integer cid, int serviceScore, int medicalScore,
			Integer expertId) {
		return bskExpertDao.addEvalution(cid, serviceScore, medicalScore, expertId);
	}

}
