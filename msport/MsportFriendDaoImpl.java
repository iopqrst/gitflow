package com.bskcare.ch.dao.impl.msport;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.msport.MsportFriendDao;
import com.bskcare.ch.vo.msport.MsportFriend;

@Repository
@SuppressWarnings("unchecked")
public class MsportFriendDaoImpl extends BaseDaoImpl<MsportFriend> implements MsportFriendDao{
	
	/**根据客户id查询客户跑友**/
	public List<Object> queryFriendSort(Integer cid){
		String sql = "select sum(m.distance) distanceTotal,n.clientId clientId from (" +
				"(select * from m_sport) m join (select friendId clientId from m_sport_friend where clientId = ?) n " +
				"on m.clientId = n.clientId) group by clientId order by sum(m.distance) desc";
		List args = new ArrayList();
		args.add(cid);
		Map scalars = new LinkedHashMap();
		scalars.put("clientId", StandardBasicTypes.INTEGER);
		scalars.put("distanceTotal",StandardBasicTypes.DOUBLE);
		return executeNativeQuery(sql, null, scalars, args.toArray(), null);
	}
	
	/**查询自己的运动总距离**/
	public List<Object> queryMySportSort(Integer cid){
		String sql = "select sum(distance) distanceTotal,clientId from m_sport where clientId = ?";
		List args = new ArrayList();
		args.add(cid);
		Map scalars = new LinkedHashMap();
		scalars.put("clientId", StandardBasicTypes.INTEGER);
		scalars.put("distanceTotal",StandardBasicTypes.DOUBLE);
		return executeNativeQuery(sql, null, scalars, args.toArray(), null);
	}
	
	public int queryFriendById(Integer cid,Integer pid){
		String sql = "select count(id) from m_sport_friend where clientId = ? and friendId = ?";
		List args = new ArrayList();
		args.add(cid);
		args.add(pid);
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		int count = -1;
		if (null != obj) {
			count = Integer.parseInt(obj.toString());
		}
		return count;
	}
	
	public int deleteMsportFriend(Integer cid,Integer pid){
		String sql = "delete from m_sport_friend where clientId = ? and friendId = ?";
		List args = new ArrayList();
		args.add(cid);
		args.add(pid);
		return updateBySql(sql, args.toArray());
	}
}
