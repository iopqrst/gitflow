package com.bskcare.ch.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.UserInfoDao;
import com.bskcare.ch.service.UserInfoService;
import com.bskcare.ch.util.CrmResponse;
import com.bskcare.ch.util.CrmURLConfig;
import com.bskcare.ch.util.HttpClientUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.MD5Util;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.util.Secretkey;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.UserInfo;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	
	private static Logger log = Logger.getLogger(UserInfoServiceImpl.class);

	@Autowired
	private UserInfoDao userInfoDao;

	// public void deleteUserAreaByUserId(int userId) {
	// userAreaDao.deleteUserAreaByUserId(userId);
	// }

	public void updateUserInfo(String name, int roleId, int status, int id) {

		userInfoDao.updateUserInfo(name, roleId, status, id);

	}

	// public void updateUserArea(String userName, int roleId, int status, int
	// id,
	// String areaIds) {
	// if (StringUtils.isEmpty(areaIds))
	// return;
	//
	// userInfoDao.updateUserInfo(userName, roleId, status, id);
	// userAreaDao.deleteUserAreaByUserId(id);
	//
	// String[] params = areaIds.split(",");
	//
	// if (!StringUtils.isEmpty(userName)) {
	// if (params.length > 0) {
	// for (int i = 0; i < params.length; i++) {
	// int areaId = Integer.parseInt(params[i]);
	// UserArea userArea = new UserArea(areaId, id);
	// userAreaDao.addUserArea(userArea);
	// }
	// }
	// }
	//
	// }

	public int selectUserCountByName(String name) {
		int count = userInfoDao.selectUserCountByName(name);
		return count;
	}

	/**
	 * 根据用户account来查询,看是否有重复的账号
	 */
	public int selectUserCountByAccount(String account) {
		int count = userInfoDao.selectUserCountByAccount(account);
		return count;
	}

	/**
	 * 添加用户
	 */
	// public void addUserInfo(UserInfo userInfo, String areaIds) {
	// if (StringUtils.isEmpty(areaIds))
	// return;
	//
	// UserInfo ui = userInfoDao.add(userInfo);
	//
	// String[] params = areaIds.split(",");
	// if (params.length > 0) {
	// for (int i = 0; i < params.length; i++) {
	// int areaId = Integer.parseInt(params[i]);
	// UserArea userArea = new UserArea(areaId, ui.getId());
	// userAreaDao.addUserArea(userArea);
	// }
	// }
	// }

	public int findMaxId() {
		int maxId = userInfoDao.findMaxId();
		return maxId;
	}

	public UserInfo userLogin(UserInfo user) {
		return userInfoDao.getUser(user.getName(), MD5Util
				.digest(user.getPwd()));
	}

	// public PageObject findAreaUserRole(QueryInfo queryInfo) {
	// return areaUserRoleDao.selectAreaUserRole(queryInfo);
	// }

	// public List getUserInfoMenu(int roleId) {
	// return roleMenuDao.getMenuByRoleId(roleId);
	// }

	/**
	 * 根据用户id修改用户密码
	 */
	public void updatePwdById(int id, String pwd) {
		userInfoDao.updatePwdById(id, pwd);
	}

	/**
	 * 重置密码
	 */
	public String resetPwd(int id, String pwd, String newPwd) {
		// 首先根据登陆用户的id查询用户信息
		UserInfo userInfo = userInfoDao.selectPwdById(id);
		if (userInfo != null) {
			String MD5Pwd = MD5Util.digest(pwd);
			String MD5NewPwd = MD5Util.digest(newPwd);
			if (userInfo.getPwd().equals(MD5Pwd)) {
				userInfoDao.updatePwdById(id, MD5NewPwd);
				return "success";
			} else {
				return "pwdFail";// 用户名和密码不一致
			}
		} else {
			// 用户登录已失效,请重新登录后修改密码
			return "userFail";
		}
	}

	/**
	 * 查询状态为正常的管理员信息
	 */
	public List<UserInfo> findUserInfo() {
		List<UserInfo> lst = userInfoDao.findUserInfo();
		if (!CollectionUtils.isEmpty(lst)) {
			return lst;
		} else {
			return null;
		}
	}

	public UserInfo findUserInfoByid(Integer id) {
		return userInfoDao.findUserInfoByid(id);
	}

	public UserInfo findUserInfoByAccount(String account) {
		return userInfoDao.findUserInfoByAccount(account);
	}

	public String addUserInfo(UserInfo userInfo) {
		String status = "0";
		userInfo.setPwd("");
		userInfo.setCreateTime(new Date());
		userInfo.setStatus(0);
		UserInfo u = userInfoDao.add(userInfo);
		if (null != u) {
			status = "1";
		} else {
			status = "0";
		}
		return status;
	}

	public String delUserInfo(UserInfo userInfo) {
		String delStatus = "";
		String hql = "update UserInfo set status=? where id=? ";
		Object[] obj = { userInfo.getStatus(), userInfo.getId() };
		int status = userInfoDao.updateByHql(hql, obj);
		if (status >= 0) {
			delStatus = "1";
		} else {
			delStatus = "0";
		}
		return delStatus;
	}

	public String updateUserInfo(UserInfo userInfo) {
		String updateStatus = "";
		String hql = "update UserInfo set name=?,roleId=?,status=? where id=? ";
		Object[] obj = { userInfo.getName(), userInfo.getRoleId(),
				userInfo.getStatus(), userInfo.getId() };
		int status = userInfoDao.updateByHql(hql, obj);
		if (status >= 0) {
			updateStatus = "1";
		} else {
			updateStatus = "0";
		}
		return updateStatus;
	}

	public JSONArray getAdmChainByAreaIdAndUserTypeIds(Integer areaId,String userType) {
		String chain_url = CrmURLConfig.getString("crm_base_url")
				+ CrmURLConfig.getString("crm_query_manager_chain");
		HashMap<String, String> cparmas = new HashMap<String, String>();
		cparmas.put("areaId", areaId.toString());
		cparmas.put("roleTypeArray",userType );
		CrmResponse acResp = HttpClientUtils.getCrmResponse(chain_url, cparmas);
		JSONArray jo = null;
		if (acResp != null && acResp.isSuccessed() && !acResp.isDataEmpty()) {
			if (acResp.getData().has("manageClain")) {
				jo = acResp.getData().getJSONArray("manageClain");
			}
		}
		
		log.info(LogFormat.f("request CRM interface start."));
		log.info("request url : " + chain_url);
		log.info("request params : areaId = " + areaId + ", roleTypeArray = " + userType);
		log.info("response result: " + ((null == jo) ? "" : jo.toString()));
		log.info(LogFormat.f("request CRM interface end."));
		return jo;
	}

	public Integer getMinAreaId(String account) {
		Integer minAreaId = null;
		// 获得链接crm项目url
		String url = CrmURLConfig.getString("crm_base_url")
				+ CrmURLConfig.getString("crm_agent_areaid");
		// map参数列表
		HashMap<String, String> parmas = new HashMap<String, String>();
		parmas.put("account", account);
		CrmResponse acResp = HttpClientUtils.getCrmResponse(url, parmas);

		if (acResp.getCode().equals(CrmResponse.CODE_SUCCESS)) {
			for (int i = 0; i < acResp.getArrayData().size(); i++) {
				minAreaId = (Integer) acResp.getArrayData().getJSONObject(0)
						.get("id");
				System.out.println("minAreaId:" + minAreaId);
			}
		}
		return minAreaId;
	}

	public JSONObject getUserinfoByUserId(Integer userId) {
		JSONObject jsonObject = new JSONObject();
		String chain_url = CrmURLConfig.getString("crm_base_url")
				+ CrmURLConfig.getString("crm_userid_userinfo");
		HashMap<String, String> cparmas = new HashMap<String, String>();
		cparmas.put("userInfo.userId", userId.toString());
		CrmResponse acResp = HttpClientUtils.getCrmResponse(chain_url, cparmas);
		jsonObject = acResp.getArrayData().getJSONObject(0);
		return jsonObject;
	}

	public JSONArray getAgentAdminByAreaId(Integer areaId) {
		String chain_url = CrmURLConfig.getString("crm_base_url")
				+ CrmURLConfig.getString("crm_areaid_principalid");
		HashMap<String, String> cparmas = new HashMap<String, String>();
		cparmas.put("areaId", areaId.toString());
		CrmResponse acResp = HttpClientUtils.getCrmResponse(chain_url, cparmas);
		 if (acResp.getCode().equals(CrmResponse.CODE_SUCCESS)) {
			return acResp.getArrayData();
		}else{
			return null;
		}
	}

	public int getUserId(Map<String, JSONArray> map, Integer roleType,
			Integer areaId) {
		String key = roleType+""+areaId;
		List<Integer> userList=new ArrayList<Integer>();
		if(map==null){
			map = new HashMap<String, JSONArray>();
		}
		int i=0;
		if (map.containsKey(key)) {
				JSONArray jo=map.get(key);
				for (Object object : jo) {
					JSONObject json=(JSONObject) object;
					if(json.getInt("roleType")== roleType){
						userList.add(Integer.parseInt(json.get("id").toString()));
					}
				}
		} 
		i=userList.size();
		if(i==0||map==null) {
			JSONArray jo = this.getAdmChainByAreaIdAndUserTypeIds(areaId,roleType.toString());
			if(null != jo) {
				map.put(key, jo);
				for (Object object : jo) {
					JSONObject json=(JSONObject) object;
					if(!StringUtils.isEmpty(json.getString("roleType")) && json.getInt("roleType")== roleType){
						userList.add(Integer.parseInt(json.get("id").toString()));
					}
				}
			}
		}
		
		if(userList.size() == 0) {
			log.warn(LogFormat.f("用户list为空，无法找到具体的健康管理师，任务将转发到指定的超级管理员账号下【临时】。"));
			return 1;
		}
//		i=userList.get(RandomUtils.getRandomIndex(userList.size())); 随机
		i=userList.get(0);
		log.info(LogFormat.f("地区编号areaId："+areaId +"，角色类型编号roleType：" + roleType + "，返回的角色id：" + i));
		return i;
	}

	public JSONArray getAreaInfoByAreaId(Integer areaId) {
//		String chain_url = "http://192.168.1.167:8080/kfcrm/"
		String chain_url = CrmURLConfig.getString("crm_base_url")
			+ CrmURLConfig.getString("crm_areaid_arealine");
		HashMap<String, String> cparmas = new HashMap<String, String>();
		cparmas.put("areaInfo.id", areaId.toString());
		CrmResponse acResp = HttpClientUtils.getCrmResponse(chain_url, cparmas);
		 if (acResp.getCode().equals(CrmResponse.CODE_SUCCESS)) {
			return acResp.getArrayData();
		}else{
			return null;
		}
	}
}
