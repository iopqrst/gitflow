package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.AreaInfoDao;
import com.bskcare.ch.service.AreaInfoService;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.AreaInfo;

@Service
public class AreaInfoServiceImpl implements AreaInfoService {

	@Autowired
	private AreaInfoDao areaInfoDao;

	// @Autowired
	// private UserAreaDao userAreaDao;

	public String getString4Ztree(AreaInfo areaInfo) {
		List<AreaInfo> list = areaInfoDao.executeFind(areaInfo);
		JSONArray ja = new JSONArray();
		for (AreaInfo ai : list) {
			JSONObject jo = new JSONObject();
			jo.put("id", ai.getId());
			jo.put("name", ai.getName());
			jo.put("pId", ai.getParentId());
			jo.put("level", ai.getLevel());
			jo.put("areaChain", ai.getAreaChain());
			ja.add(jo);
		}
		return ja.toString();
	}

	// public List<UserArea> findAreaByUserId(int userId){
	// return userAreaDao.findAreaByUserId(userId);
	// }

	// public String getAreaInfoForEditor(AreaInfo areaInfo, int userId) {
	// List<AreaInfo> list = areaInfoDao.executeFind(areaInfo);
	// List<UserArea> ownerArea = userAreaDao.findAreaByUserId(userId);
	// List<Integer> areaList = new ArrayList<Integer>();
	//
	// if (!CollectionUtils.isEmpty(ownerArea)) {
	// for (int i = 0; i < ownerArea.size(); i++) {
	// areaList.add(i, ownerArea.get(i).getAreaId());
	// }
	// }
	//
	// JSONArray ja = new JSONArray();
	// for (AreaInfo ai : list) {
	// JSONObject jo = new JSONObject();
	// jo.put("id", ai.getId());
	// jo.put("name", ai.getName());
	// jo.put("pId", ai.getParentId());
	// jo.put("level", ai.getLevel());
	//
	// if (areaList.contains(ai.getId())) {
	// jo.put("checked", "true");
	// }
	//
	// ja.add(jo);
	// }
	// return ja.toString();
	// }

	public String deleteAreaInfo(int id) {

		String msg = null;
		areaInfoDao.deleteById(id);
		msg = "success";
		return msg;
	}

	/**
	 * 更新节点
	 */
	public boolean updateByIdName(int id, String name) {
		try {
			areaInfoDao.updateByIdName(id, name);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 添加节点
	 */
	public int addAreaInfo(AreaInfo areaInfo) {
		AreaInfo ai = areaInfoDao.add(areaInfo);
		return ai.getId();
	}

	public int findMaxId() {
		int id = areaInfoDao.findMaxId();
		return id;
	}

	public AreaInfo findAreaByParentId(Integer parentId) {
		return areaInfoDao.load(parentId);
	}

	public void addAreaChain(Integer id) {
		String chain = getAreaChain(id).toString().substring(1);// 截取字符串前面的逗号
		if (!StringUtils.isEmpty(chain)) {
			AreaInfo areaInfo = new AreaInfo();
			areaInfo.setId(id);
			areaInfo.setAreaChain(chain + ",");
			areaInfoDao.updateAreaChain(areaInfo);
		}
		System.out.println("final chain:" + chain + ",");
	}

	// 递归
	public StringBuffer getAreaChain(Integer areaId) {
		StringBuffer chain = new StringBuffer();
		AreaInfo areaInfo = this.findAreaByParentId(areaId);// areaId=parentId
															// parentId=id
		if (null != areaInfo && areaInfo.getId() >= 1) {
			chain = getAreaChain(areaInfo.getParentId()).append("," + areaId);
		}
		return chain;
	}
	
	public String getAreaChainByAreaId(Integer areaId){
		
		AreaInfo ai = this.get(areaId);
		
		if(null != ai) {
			return ai.getAreaChain();
		}
		
		return null;
	}

	public List<AreaInfo> queryList(AreaInfo areaInfo) {
		return areaInfoDao.executeFind(areaInfo);
	}

	public String deleteAreaInfo(AreaInfo areaInfo) {
		String statusStr = "0";
		String hql = "update AreaInfo set status=? where id=?";
		Object[] obj = { areaInfo.getStatus(), areaInfo.getId() };
		int status = areaInfoDao.updateByHql(hql, obj);
		if (status > 0) {
			statusStr = "1";
		} else {
			statusStr = "0";
		}
		return statusStr;
	}

	public String updateAreaInfo(AreaInfo areaInfo) {
		String statusStr = "0";
		String hql = "update AreaInfo set name=? where id=?";
		Object[] obj = { areaInfo.getName(),areaInfo.getId() };
		int status = areaInfoDao.updateByHql(hql, obj);
		if (status > 0) {
			statusStr = "1";
		} else {
			statusStr = "0";
		}
		return statusStr;
	}
	
	public String getAdminArea(List<String> list) {
		List<AreaInfo> areaList = areaInfoDao.getAdminArea(list);
		JSONArray jArray = new JSONArray();
		for (AreaInfo ai : areaList) {
			JSONObject json = new JSONObject();
			json.put("id", ai.getId());
			json.put("name", ai.getName());
			json.put("pId", ai.getParentId());
			json.put("level", ai.getLevel());
			json.put("areaChain", ai.getAreaChain());
			jArray.add(json);
		}
		return jArray.toString();
	}

	public Map<String, String> getAllAreaName(String areaChain) {
		List list = Arrays.asList(areaChain.split("#"));
		List<AreaInfo> areaInfoList = areaInfoDao.getAdminArea(list);
		Map<String, String> map =  null;
		if(!CollectionUtils.isEmpty(areaInfoList)){
			map = new HashMap<String, String>();
			for (AreaInfo areaInfo : areaInfoList) {
				map.put(areaInfo.getId()+"", areaInfo.getName());
			}
		}
		return map;
	}

	public AreaInfo get(Integer areaInfo) {
		return areaInfoDao.load(areaInfo);
	}
}
