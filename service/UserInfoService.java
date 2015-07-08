package com.bskcare.ch.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bskcare.ch.vo.UserInfo;

public interface UserInfoService {

	/**
	 * 更新用户信息,更新用户信息需要涉及到更改UserInfo表,UserArea表
	 * 
	 * @param name
	 * @param account
	 * @param pwd
	 * @param roleId
	 * @param status
	 * @param id
	 * @param areaId
	 * @param userId
	 */
	public void updateUserInfo(String name, int roleId, int status, int id);

	/**
	 * 根据用户姓名查询用户已存在用户中是否存在此用户,如果存在,就不能注册
	 * 
	 * @param name
	 *            用户姓名
	 * @return 用户此用户姓名的数量
	 */
	public int selectUserCountByName(String name);

	/**
	 * 根据用户account来查询,看是否有重复的账号
	 */
	public int selectUserCountByAccount(String account);

	/**
	 * 查询当前最大的用户id
	 * 
	 * @return
	 */
	public int findMaxId();

	/**
	 * 登陆验证 使用账号和密码
	 * 
	 * @param userInfo
	 * @return
	 */
	public UserInfo userLogin(UserInfo userInfo);

	/**
	 * 根据用户id修改用户密码
	 */
	public void updatePwdById(int id, String pwd);

	/**
	 * 重置密码
	 */

	public String resetPwd(int id, String pwd, String newPwd);

	/**
	 * 查询状态为正常的管理员信息
	 */
	public List<UserInfo> findUserInfo();

	public UserInfo findUserInfoByid(Integer id);

	/**
	 * 推广代理商信息验证
	 */
	public UserInfo findUserInfoByAccount(String account);

	/**
	 * 查询代理商区域下最小区域
	 */
	 public Integer getMinAreaId(String account);

	/**
	 * 增加
	 */
	public String addUserInfo(UserInfo userInfo);

	/**
	 * 删除
	 */
	public String delUserInfo(UserInfo userInfo);

	/**
	 * 修改
	 */
	public String updateUserInfo(UserInfo userInfo);
	/**
	 * 根据区域id获得管理链
	 * @param areaId 区域id userType 角色类型id 多个用  , 隔开 如 String usertype = "1,2,3";
	 * @return	返回接口数据
	 */
	public JSONArray getAdmChainByAreaIdAndUserTypeIds(Integer areaId,String userType);
	/**
	 * 根据用户id获得用户信息
	 * @param userId 用户id
	 * @return	返回接口数据
	 */
	public JSONObject getUserinfoByUserId(Integer userId);
	/**
	 * 根据区域id获得地区所有地面管理员
	 * @param areaId 区域id
	 * @return 返回接口数据
	 */
	public JSONArray getAgentAdminByAreaId(Integer areaId);
	/**
	 * 根据区域id和管理员类型，查询用户id，map作为临时保存
	 */
	public int getUserId(Map<String, JSONArray> map,Integer roleType , Integer areaId);
	/**
	 * 根据区域id获得区域下所有地区信息
	 * @param area 区域id
	 * @return
	 */
	public JSONArray getAreaInfoByAreaId(Integer area);
}
