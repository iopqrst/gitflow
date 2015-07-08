package com.bskcare.ch.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.dao.ClientDiseaseFamilyDao;
import com.bskcare.ch.dao.ClientMedicalHistoryDao;
import com.bskcare.ch.dao.rpt.RptDiseaseDao;
import com.bskcare.ch.service.ClientDiseaseFamilyService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.RptDisease;
import com.bskcare.ch.vo.client.ClientDiseaseFamily;
import com.bskcare.ch.vo.client.ClientMedicalHistory;

@Service
@SuppressWarnings("unchecked")
public class ClientDiseaseFamilyServiceImpl implements
		ClientDiseaseFamilyService {

	@Autowired
	ClientDiseaseFamilyDao clientDiseaseFamilyDao;
	@Autowired
	private RptDiseaseDao diseaseDao;
	@Autowired
	private ClientMedicalHistoryDao clientMedicalHistoryDao;

	public void add(ClientDiseaseFamily clientDiseaseFamily) {
		clientDiseaseFamilyDao.add(clientDiseaseFamily);
	}

	public String queryDiseaseFamily(Integer clientId) {
		String diseaseFamily = "";
		List familyList = new ArrayList();
		List<Object> lstObject = clientDiseaseFamilyDao
				.queryDiseaseFamily(clientId);
		if (!CollectionUtils.isEmpty(lstObject)) {
			for (Object object : lstObject) {
				Object[] objs = (Object[]) object;
				if (objs != null && objs.length > 0) {
					if (objs[0] != null) {
						ClientDiseaseFamily family = (ClientDiseaseFamily) objs[0];
						if (family != null && family.getType() == 0) {
							Object oname = objs[1];
							if (oname != null) {
								String name = oname.toString();
								if (family.getFamilyType() == 0) {
									familyList.add("父亲" + name);
								} else if (family.getFamilyType() == 1) {
									familyList.add("母亲" + name);
								}
							}
						} else if (family != null && family.getType() == 1) {
							String name = family.getDisease();
							if (!StringUtils.isEmpty(name)) {
								if (family.getFamilyType() == 0) {
									familyList.add("父亲" + name);
								} else {
									familyList.add("母亲" + name);
								}
							}
						}
					}
				}
			}

			if (!CollectionUtils.isEmpty(familyList)) {
				diseaseFamily = ArrayUtils.join(familyList.toArray(), "、");
			}
		}
		return diseaseFamily;
	}

	public void addOrUpdateByJson(String json, Integer cid) {
		JSONArray ja = JSONArray.fromObject(json);
		clientDiseaseFamilyDao.deleteDiseaseByCid(cid);
		ClientDiseaseFamily clientDiseaseFamily;
		for (Object object : ja) {
			if(object .equals(null)){
				continue;
			}
			JSONObject jo = JSONObject.fromObject(object);
			clientDiseaseFamily = new ClientDiseaseFamily();
			clientDiseaseFamily.setFamilyType(jo.getInt("familyType"));
			if (!StringUtils.isEmpty(jo.getString("diagAge"))) {
				clientDiseaseFamily.setDiagAge(jo.getInt("diagAge"));
			}
			clientDiseaseFamily.setDisease(jo.getString("disease"));
			clientDiseaseFamily.setClientId(cid);
			clientDiseaseFamily.setType(jo.getInt("type"));
			clientDiseaseFamilyDao.saveDiseaseFamily(clientDiseaseFamily);
		}
	}

	public String queryDiseaseFamilyByClientId(Integer cid) throws Exception {
		List list = clientDiseaseFamilyDao
				.queryInitDiseaseFamilyByClientId(cid);
		JSONArray ja = new JSONArray();
		JSONObject jsonObject = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		for (Object obj : list) {
			Object[] objs = (Object[]) obj;
			jsonObject = new JSONObject();
			jsonObject.put("id", objs[0]);
			jsonObject.put("clientId", objs[1]);
			jsonObject.put("familyType", objs[2]);
			jsonObject.put("disease", objs[3]);
			if (objs[4] != null) {
				jsonObject.put("diagTime", format.format(format
						.parseObject(objs[4].toString())));
			} else {
				jsonObject.put("diagTime", "");
			}
			if (objs[5] != null) {
				jsonObject.put("diagAge", objs[5]);
			} else {
				jsonObject.put("diagAge", "");
			}
			jsonObject.put("type", objs[6]);
			if (objs[7] != null) {
				jsonObject.put("spell", objs[7]);
			} else {
				jsonObject.put("spell", "");
			}
			ja.add(jsonObject);
		}
		return ja.toString();
	}

	public void deleteDiseaseByCid(Integer cid) {
		clientDiseaseFamilyDao.deleteDiseaseByCid(cid);
	}

	public void updateDiseaseFamily(ClientDiseaseFamily diseaseFamily, int type) {
		if (diseaseFamily != null) {
			ClientDiseaseFamily family = clientDiseaseFamilyDao
					.queryDiseaseFamily(diseaseFamily);
			if (type == 1) {
				if (family == null) {
					clientDiseaseFamilyDao.add(diseaseFamily);
				}
			} else {
				if (family != null) {
					clientDiseaseFamilyDao.deleteDiseaseFamily(diseaseFamily);
				}
			}
		}
	}

	public void updateDiseaseFamily(String[] diseases, Integer familyType,
			Integer cid) {
		if (diseases == null || diseases.length <= 0) {
			clientDiseaseFamilyDao.deleteOtherDisease(cid, familyType,
					ClientDiseaseFamily.DISEASE_CHECKED_YES);
			return;
		} else {
			ClientMedicalHistory medicalHistory = clientMedicalHistoryDao
					.getClientMedicalHistory(cid);
			if(medicalHistory!=null){
				medicalHistory
						.setIsHasFamilyHealth(ClientMedicalHistory.CLIENT_MEDICAL_HISTORY_YES);
				clientMedicalHistoryDao.update(medicalHistory);
			}else{
				medicalHistory =new ClientMedicalHistory();
				medicalHistory.setClientId(cid);
				medicalHistory
				.setIsHasFamilyHealth(ClientMedicalHistory.CLIENT_MEDICAL_HISTORY_YES);
				clientMedicalHistoryDao.add(medicalHistory);
			}
		}
		List<String> diss = new ArrayList<String>();
		List<ClientDiseaseFamily> list = clientDiseaseFamilyDao
				.queryDiseaseFamilyByClientId(cid, familyType);
		// 删除数据库有，页面没有勾选的疾病
		for (ClientDiseaseFamily clientDiseaseFamily : list) {
			if (clientDiseaseFamily.getFamilyType() == familyType
					&& clientDiseaseFamily.getType() == ClientDiseaseFamily.DISEASE_CHECKED_YES
					&& !StringUtils.contains(clientDiseaseFamily.getDisease(),
							diseases)) {
				clientDiseaseFamilyDao.delete(clientDiseaseFamily);
			}
			diss.add(clientDiseaseFamily.getDisease());
		}
		// 页面勾选，数据库没有的添加到数据库
		String[] strings = new String[diss.size()];
		for (String str : diseases) {
			if (!StringUtils.contains(str, diss.toArray(strings))&&!StringUtils.isEmpty(str)) {
				ClientDiseaseFamily clientDiseaseFamily = new ClientDiseaseFamily();
				clientDiseaseFamily.setClientId(cid);
				clientDiseaseFamily.setDisease(str);
				clientDiseaseFamily.setFamilyType(familyType);
				clientDiseaseFamily
						.setType(ClientDiseaseFamily.DISEASE_CHECKED_YES);
				clientDiseaseFamilyDao.add(clientDiseaseFamily);
			}
		}
	}

	public void updateDiseaseFamilyOther(String[] familyType,
			ClientDiseaseFamily diseaseFamily, Integer cid) {
		if (familyType == null || familyType.length <= 0 || cid == null) {
			clientDiseaseFamilyDao.deleteOtherDisease(cid,
					ClientDiseaseFamily.FAMILY_MO,
					ClientDiseaseFamily.DISEASE_CHECKED_NO);//先删除母亲的其他疾病
			clientDiseaseFamilyDao.deleteOtherDisease(cid,
					ClientDiseaseFamily.FAMILY_FA,
					ClientDiseaseFamily.DISEASE_CHECKED_NO);//先删除父亲的其他疾病
			return;
		} else {
			ClientMedicalHistory medicalHistory = clientMedicalHistoryDao
					.getClientMedicalHistory(cid);
			medicalHistory.setIsHasFamilyHealth(1);
			clientMedicalHistoryDao.update(medicalHistory);
		}
		if (diseaseFamily != null
				&& !StringUtils.isEmpty(diseaseFamily.getDisease())) {
			diseaseFamily.setClientId(cid);
			diseaseFamily.setType(ClientDiseaseFamily.DISEASE_CHECKED_NO);
		} else {
			return;
		}
		for (String string : familyType) {
			if (string!=null&&string.equals(ClientDiseaseFamily.FAMILY_FA + "")) {// 如果有父亲的选项
				diseaseFamily.setFamilyType(ClientDiseaseFamily.FAMILY_FA);
				ClientDiseaseFamily family = clientDiseaseFamilyDao
						.queryDiseaseFamily(diseaseFamily);
				if (family == null) {
					clientDiseaseFamilyDao.deleteOtherDisease(cid,
							ClientDiseaseFamily.FAMILY_FA,
							ClientDiseaseFamily.DISEASE_CHECKED_NO);// 数据库没有，先删除父亲的其他疾病
					clientDiseaseFamilyDao.add(diseaseFamily);
				}
			}
			if (string!=null&&string.equals(ClientDiseaseFamily.FAMILY_MO + "")) {// 如果有母亲的选项
				diseaseFamily.setFamilyType(ClientDiseaseFamily.FAMILY_MO);
				ClientDiseaseFamily family = clientDiseaseFamilyDao
						.queryDiseaseFamily(diseaseFamily);
				if (family == null) {
					clientDiseaseFamilyDao.deleteOtherDisease(cid,
							ClientDiseaseFamily.FAMILY_MO,
							ClientDiseaseFamily.DISEASE_CHECKED_NO);// 数据库没有，先删除母亲的其他疾病
					clientDiseaseFamilyDao.add(diseaseFamily);
				}
			}
		}

	}

	public String queryDiseaseFamilyByClientIdMobile(Integer cid)
			throws Exception {
		JSONArray ja = new JSONArray();
		JSONObject jsonObject = null;
		List<RptDisease> diseases =  diseaseDao.queryRptDiseaseFamily();
		List<ClientDiseaseFamily> clientDiseaseFamilies = clientDiseaseFamilyDao.queryDiseaseFamilyByClientId(cid);
		for (RptDisease rptDisease : diseases) {
			jsonObject = new JSONObject();
			jsonObject.put("id", rptDisease.getId());
			jsonObject.put("name", rptDisease.getName());
			if(clientDiseaseFamilies!=null&&clientDiseaseFamilies.size()>0){
				for (ClientDiseaseFamily clientDiseaseFamily : clientDiseaseFamilies) {
					if(!StringUtils.isEmpty(clientDiseaseFamily.getDisease())&& clientDiseaseFamily.getDisease().equals(rptDisease.getId()+"")&&clientDiseaseFamily.getFamilyType()==1){
						jsonObject.put("isMO", "0");
						break;
					}else{
						jsonObject.put("isMO", "1");
					}
				}
				for (ClientDiseaseFamily clientDiseaseFamily : clientDiseaseFamilies) {
					if(!StringUtils.isEmpty(clientDiseaseFamily.getDisease())){
						if( !StringUtils.isEmpty(clientDiseaseFamily.getDisease())&&clientDiseaseFamily.getDisease().equals(rptDisease.getId()+"")&&clientDiseaseFamily.getFamilyType()==0){
							jsonObject.put("isFA", "0");
							break;
						}else{
							jsonObject.put("isFA", "1");
						}
					}
				}
			}else{
				jsonObject.put("isMO", "1");
				jsonObject.put("isFA", "1");
			}
			ja.add(jsonObject);
		}
		return ja.toString();
	}

	public void deletefamilyOther(Integer id, Integer familyType,Integer type) {
		clientDiseaseFamilyDao.deleteOtherDisease(id, familyType, type);
	}

}
