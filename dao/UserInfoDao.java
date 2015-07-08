package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo> {

	public void updateUserInfo(String name, int roleId, int status, int id);

	/**
	 * 根据用户姓名进行模糊查询，来查询数量，看是否有重复的用户名
	 * 
	 * @param name
	 * @return
	 */
	public int selectUserCountByName(String name);

	/**
	 * 根据用户account来查询,看是否有重复的账号
	 */
	public int selectUserCountByAccount(String account);

	/**
	 * 添加用户
	 * 
	 * @param userInfo
	 */
	public void addUserInfo(UserInfo userInfo);

	/**
	 * 查询当前最大的用户id
	 * 
	 * @return
	 */
	public int findMaxId();

	/***
	 * 登陆 验证账号密码 是否存在
	 * 
	 * @return
	 */
	public UserInfo getUser(String name, String pwd);

	/**
	 * 根据用户id删除用户(改变用户的状态)
	 */

	public void deleteUserById(int id);

	/**
	 * 根据用户id修改用户密码
	 */
	public void updatePwdById(int id, String pwd);

	/**
	 * 根据id查询密码
	 */
	public UserInfo selectPwdById(int id);
	
	/**
	 * 查询状态为正常的管理员信息
	 */
	public List<UserInfo> findUserInfo();
	
	public UserInfo findUserInfoByid(Integer id);
	
	/**
	 * 根据用户角色和区域id查找相应的用户
	 * @param roles 角色id (多个用,分割）
	 * @param areaIds 区域id(,)分割
	 * @return
	 */
	public List<UserInfo> queryUserByRoleOrAreaId(String roles, String areaIds);
	
	/**
	 * 代理商(推广)账户验证
	 */
	public UserInfo findUserInfoByAccount(String account);
	
}
