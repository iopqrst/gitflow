package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.UserInfoDao;
import com.bskcare.ch.vo.UserInfo;

@Repository("userInfoDao")
@SuppressWarnings("unchecked")
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements
		UserInfoDao {
	/**
	 * 根据id来更新
	 */
	public void updateUserInfo(String name, int roleId, int status, int id) {
		String hql = "update UserInfo set name=?,roleId=?,status=? where id=?";
		Object[] obj = { name, roleId, status, id };
		updateByHql(hql, obj);

	}

	/**
	 * 根据用户姓名进行查询，来查询数量，看是否有重复的用户名
	 */
	public int selectUserCountByName(String name) {
		String hql = "select count(name) from UserInfo where name=?";
		Object obj = findUniqueResult(hql, name);
		int count = -1;
		if (null != obj) {
			count = Integer.parseInt(obj.toString());
		}
		return count;
	}

	/**
	 * 根据用户account来查询,看是否有重复的账号
	 */
	public int selectUserCountByAccount(String account) {
		String hql = "select count(account) from UserInfo where account=? and status = ?";
		
		Object[] ob = {account,UserInfo.USER_NORMAL};
		Object obj = findUniqueResult(hql, ob);
		int count = -1;
		if (null != obj) {
			count = Integer.parseInt(obj.toString());
		}
		return count;
	}

	/**
	 * 添加用户
	 */
	public void addUserInfo(UserInfo userInfo) {
		add(userInfo);
	}

	/**
	 * 查询最大的当前最大的id
	 */
	public int findMaxId() {
		String hql = "select max(id) from t_userinfo";
		int maxId = -1;
		Object obj = findUniqueResultByNativeQuery(hql);
		if (null != obj) {
			maxId = Integer.parseInt(obj.toString());
		}
		return maxId;
	}

	public UserInfo getUser(String name, String pwd) {
		String hql = "from UserInfo where account = ? and pwd = ? and status = ?";
		List list = executeFind(hql, new Object[] { name, pwd, 0});
		if (CollectionUtils.isEmpty(list)) {
			return null ;
		}else{
			return (UserInfo)list.get(0) ;
		}
	}

	/**
	 * 根据用户id删除用户(改变用户的状态)
	 */
	public void deleteUserById(int id) {
		String hql = "update UserInfo set status=? where id=?";
		Object[] obj = { UserInfo.USER_DELETE, id };
		updateByHql(hql, obj);
	}

	/**
	 * 根据用户id修改用户密码
	 */
	public void updatePwdById(int id, String pwd) {
		String hql = "update UserInfo set pwd=? where id=?";
		Object[] obj = { pwd, id };
		updateByHql(hql, obj);
	}

	/**
	 * 根据id查询用户
	 */
	public UserInfo selectPwdById(int id) {
		return load(id);
	}

	
	public List<UserInfo> findUserInfo(){
		String hql="from UserInfo where status = ?";
		return executeFind(hql, UserInfo.USER_NORMAL);
	}
	
	public UserInfo findUserInfoByid(Integer id){
		String hql="from UserInfo where id=?";
		List<UserInfo> lst = executeFind(hql , id);
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}
	
	
	public List<UserInfo> queryUserByRoleOrAreaId(String roles, String areaIds) {
		String sql = "select {t1.*} from t_userinfo t1 left join t_user_area t2 on t1.id = t2.userId " +
				" where t1.status = ? ";
		
		if(!StringUtils.isEmpty(roles)) {
			sql += " and t1.roleId in(" + roles + ")";
		}
		if(!StringUtils.isEmpty(areaIds)) {
			sql += " and t2.areaId in (" + areaIds + ")";
		}
		Map entities = new HashMap();
		entities.put("t1", UserInfo.class);
		return this.executeNativeQuery(sql, entities, new Object[]{UserInfo.USER_NORMAL});
	}

	public UserInfo findUserInfoByAccount(String account) {
		ArrayList arg = new ArrayList();
		//TODO 这个是专门查询数据库的代理商1052是代理商的角色，有点恶心
		String hql = " from UserInfo where 1=1 ";
		hql += " and status=0 and roleid=1052 ";
		hql += " and account=? ";
		arg.add(account);
		return (UserInfo)this.findUniqueResult(hql, arg.toArray());
	}

}
