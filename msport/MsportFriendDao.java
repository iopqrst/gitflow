package com.bskcare.ch.dao.msport;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.msport.MsportFriend;

public interface MsportFriendDao extends BaseDao<MsportFriend>{
	
	/**根据客户id查询客户跑友**/
	public List<Object> queryFriendSort(Integer cid);
	
	/**查询自己的运动总距离**/
	public List<Object> queryMySportSort(Integer cid);
	
	/**根据id和好友id查询是否已经添加**/
	public int queryFriendById(Integer cid,Integer pid);
	
	/**根据用户id和好友id删除好友**/
	public int deleteMsportFriend(Integer cid,Integer pid);
}
