package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.dao.ClientDiseaseSelfDao;
import com.bskcare.ch.dao.ClientMedicalHistoryDao;
import com.bskcare.ch.dao.rpt.RptDiseaseDao;
import com.bskcare.ch.service.ClientDiseaseSelfService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.RptDisease;
import com.bskcare.ch.vo.client.ClientDiseaseSelf;
import com.bskcare.ch.vo.client.ClientMedicalHistory;

@Service
@SuppressWarnings("unchecked")
public class ClientDiseaseSelfServiceImpl implements ClientDiseaseSelfService {

	@Autowired
	private ClientDiseaseSelfDao diseaseSelfDao;
	@Autowired
	private RptDiseaseDao diseaseDao;
	@Autowired
	private ClientMedicalHistoryDao clientMedicalHistoryDao;

	public void add(ClientDiseaseSelf clientDiseaseSelf) {
		diseaseSelfDao.add(clientDiseaseSelf);
	}

	public String queryDiseaseSelf(Integer clientId) {
		String diseaseSelf = "";
		List medicalList = new ArrayList();
		List<Object> lstObject = diseaseSelfDao.queryDiseaseSelf(clientId);
		if (!CollectionUtils.isEmpty(lstObject)) {
			for (Object object : lstObject) {
				Object[] objs = (Object[]) object;
				if (objs != null && objs.length > 0) {
					if (objs[0] != null) {
						ClientDiseaseSelf self = (ClientDiseaseSelf) objs[0];
						if (self != null) {
							Object oname = objs[1];
							if (oname != null) {
								String name = oname.toString();
								medicalList.add(name);
							}
						}
					}
				}
			}
			if (!CollectionUtils.isEmpty(medicalList)) {
				diseaseSelf = ArrayUtils.join(medicalList.toArray(), "、");
			}
		}
		return diseaseSelf;
	}

	public void addOrUpdateByJson(String json, Integer cid) {
		// 删除现有的疾病
		diseaseSelfDao.deleteDiseaseByCid(cid);
		JSONArray ja = JSONArray.fromObject(json);
		ClientDiseaseSelf clientDiseaseSelf;
		for (Object object : ja) {
			JSONObject jo = JSONObject.fromObject(object);
			clientDiseaseSelf = new ClientDiseaseSelf();
			clientDiseaseSelf.setType(jo.getInt("type"));
			clientDiseaseSelf.setDisease(jo.getString("disease"));
			clientDiseaseSelf.setClientId(cid);
			String time = jo.getString("diagTime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			if (!StringUtils.isEmpty(time)) {
				try {
					clientDiseaseSelf.setDiagTime(sdf.parse(time));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			diseaseSelfDao.saveDiseaseSelf(clientDiseaseSelf);
		}
	}
	
	public String queryDiseaseSelfByClientId(Integer cid) throws Exception {
		JSONArray ja = new JSONArray();
		JSONObject jsonObject = null;
		List list = diseaseSelfDao
				.queryInitDiseaseSelfByClientId(cid);//用户所有疾病
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
		for (Object obj : list) {
			Object[] objs = (Object[]) obj;
			jsonObject = new JSONObject();
			jsonObject.put("id", objs[0]);
			jsonObject.put("clientId", objs[1]);
			if(objs[2]!=null){
				jsonObject.put("disease", objs[2]);
			}else{
				jsonObject.put("disease", "");
			}
			if(objs[3]!=null){
				jsonObject.put("diagTime", format.format(format.parseObject(objs[3].toString())));
			}else{
				jsonObject.put("diagTime","");
			}
			jsonObject.put("type", objs[4]);
			if(objs[5]!=null){
				jsonObject.put("spell", objs[5]);
			}
			else{
				jsonObject.put("spell", "");
			}
			ja.add(jsonObject);
		}
		return ja.toString();
	}

	public void deleteDiseaseByCid(Integer cid) {
		diseaseSelfDao.deleteDiseaseByCid(cid);
	}

	public void updateDiseaseSelf(ClientDiseaseSelf diseaseSelf, int type) {
		String disease = "";
		Integer clientId = null;
		if (diseaseSelf != null) {
			disease = diseaseSelf.getDisease();
			clientId = diseaseSelf.getClientId();
			ClientDiseaseSelf self = diseaseSelfDao.queryDiseaseSelf(clientId,
					disease);
			if (type == 1) {//如果参数是1
				if (self == null) {	//并且数据库没有对应信息
					diseaseSelfDao.add(diseaseSelf);	//添加疾病
				}
			} else {
				if (self != null) {	//如果参数不是1，并且数据库有对应信息，删除疾病信息
					diseaseSelfDao.deleteDiseaseSelf(clientId, disease);
				}
			}
		}
	}

	public void updateDiseaseSelf(String[] self, Integer cid) {
		if(self==null||self.length<=0 || "[]".equals(self)){
			//如果页面传过来的数组为空，删除所有个人疾病
			diseaseSelfDao.deleteDiseaseByCid(cid);
			return ;
		}else{
			//否则将用户是否有个人疾病改成有
			ClientMedicalHistory medicalHistory=clientMedicalHistoryDao.getClientMedicalHistory(cid);
			if(medicalHistory!=null){
				medicalHistory.setIsHasMedical(ClientMedicalHistory.CLIENT_MEDICAL_HISTORY_YES);
				clientMedicalHistoryDao.update(medicalHistory);
			}else{
				medicalHistory =new ClientMedicalHistory();
				medicalHistory.setClientId(cid);
				medicalHistory.setIsHasMedical(ClientMedicalHistory.CLIENT_MEDICAL_HISTORY_YES);
				clientMedicalHistoryDao.add(medicalHistory);
			}
		}
		//获得用户已经保存的个人疾病类表
		List<ClientDiseaseSelf> list = diseaseSelfDao
				.queryDiseaseSelfByClientId(cid);
		// 删除数据库有，页面没有勾选的疾病
		List<String> diss = new ArrayList<String>();
		String[] strings = new String[diss.size()];
		for (ClientDiseaseSelf clientDiseaseSelf : list) {
			if (clientDiseaseSelf.getType() == ClientDiseaseSelf.DISEASE_YES
					&& !StringUtils.contains(clientDiseaseSelf.getDisease(),
							self)) {
				diseaseSelfDao.delete(clientDiseaseSelf);
			}
			diss.add(clientDiseaseSelf.getDisease());//保存数据库有所有的疾病
		}
		// 页面勾选，数据库没有的添加到数据库
		for (String string : self) {
			if (!StringUtils.contains(string, diss.toArray(strings))&&!StringUtils.isEmpty(string)) {//如果页面的数组有，数据库列表没有的数据添加到数据库
				ClientDiseaseSelf diseaseSelf = new ClientDiseaseSelf();
				diseaseSelf.setDisease(string);
				diseaseSelf.setClientId(cid);
				diseaseSelf.setType(ClientDiseaseSelf.DISEASE_YES);
				diseaseSelfDao.add(diseaseSelf);
			}
		}

	}

	public void updateDiseaseSelfOther(ClientDiseaseSelf clientDiseaseSelf,Integer cid){
		ClientDiseaseSelf self=diseaseSelfDao.quertDiseaseSelfOther(cid);//查询用户自定义的个人疾病
		if(clientDiseaseSelf==null){
			return;
		}
		if(self==null){
			clientDiseaseSelf.setClientId(cid);
			clientDiseaseSelf.setType(ClientDiseaseSelf.DISEASE_NO);
			diseaseSelfDao.add(clientDiseaseSelf);
		}else{
			if(!StringUtils.isEmpty(self.getDisease()) && !StringUtils.isEmpty(clientDiseaseSelf.getDisease())
				&& !self.getDisease().equals(clientDiseaseSelf.getDisease())){//如果页面接收的疾病名称与数据库不同，删除数据库数据，添加新的个人疾病
				diseaseSelfDao.delete(self);
				clientDiseaseSelf.setClientId(cid);
				clientDiseaseSelf.setType(ClientDiseaseSelf.DISEASE_NO);
				diseaseSelfDao.add(clientDiseaseSelf);
			}
		}
	}
	
	public ClientDiseaseSelf queryDiseaseSelfOther(Integer cid) {
		return diseaseSelfDao.quertDiseaseSelfOther(cid);
	}

	public String queryDiseaseSelfByClientIdMobile(Integer cid) throws Exception {
		JSONArray ja = new JSONArray();
		JSONObject jsonObject = null;
		List<RptDisease> diseases = diseaseDao.queryRptDiseaseSelf();
		List<ClientDiseaseSelf> clientDiseaseSelfs = diseaseSelfDao.queryDiseaseSelfByClientId(cid);
		for (RptDisease rptDisease : diseases) {
			jsonObject =new JSONObject();
			jsonObject.put("id", rptDisease.getId());
			jsonObject.put("name", rptDisease.getName());
			if(clientDiseaseSelfs!=null&&clientDiseaseSelfs.size()>0){
				for (ClientDiseaseSelf clientDiseaseSelf : clientDiseaseSelfs) {
					if(!StringUtils.isEmpty(clientDiseaseSelf.getDisease()) && clientDiseaseSelf.getDisease().equals(rptDisease.getId()+"")){
						jsonObject.put("isSelf","0");
						break;
					}else{
						jsonObject.put("isSelf","1");
					}
				}
			}else{
				jsonObject.put("isSelf","1");
			}
			ja.add(jsonObject);
		}
		
		return ja.toString();
	}

	public void deleteOtherDiseaseByCid(Integer cid) {
		diseaseSelfDao.deleteOtherDiseaseByCid(cid);
	}
}
