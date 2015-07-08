package com.bskcare.ch.dao.impl.msport;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.MsportActivityExtend;
import com.bskcare.ch.dao.msport.MSportActivityDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.msport.MSportActivity;

@Repository
@SuppressWarnings("unchecked")
public class MSportActivityDaoImpl extends BaseDaoImpl<MSportActivity>
		implements MSportActivityDao {
	
	public PageObject<MsportActivityExtend> queryActivityInfo(MSportActivity activity, QueryInfo queryInfo){
		String sql = "select m.*, n.* from (select a.* from m_sport_activity a where NOT EXISTS" +
				" (select aid from m_sport_activity_vs_client ac where ac.aid = a.aid and ac.cid = ?) and a.creator != ?) m" +
				" join (select id,name cname,gender,nickName,mobile,headPortrait from t_clientinfo) n on m.creator = n.id";
		
		List args = new ArrayList();
		args.add(activity.getCreator());
		args.add(activity.getCreator());
		
		return queryObjectsBySql(sql, null, null, args.toArray(), queryInfo, MsportActivityExtend.class);
	}
	
	public PageObject<Object> queryActivityClient(MSportActivity activity, QueryInfo queryInfo){
		/*String sql = "select m.*,n.sumDistance,n.maxTestDate,n.minTestDate from (select c.id,c.name,c.gender,c.mobile,c.nickName," +
				"c.headPortrait,l.location,l.longitude,l.latitude from t_clientinfo c  join t_client_location l" +
				" on c.id = l.clientId) m join (select m.*,n.* from (select * from m_sport_activity_vs_client where aid = ?) m" +
				" left join (select sum(distance) sumDistance,clientId,max(testDate) maxTestDate,min(testDate) minTestDate from m_sport GROUP BY clientId) n " +
				" on m.cid = n.clientId) n on m.id = n.cid order by n.sumDistance desc";*/
		
		String sql = "select m.*,n.sumDistance,n.maxTestDate,n.minTestDate,n.sumSteps,n.sumCalorie from (select c.id,c.name,c.gender,c.mobile,c.nickName," +
		"c.headPortrait,l.location,l.longitude,l.latitude from t_clientinfo c  join t_client_location l" +
		" on c.id = l.clientId) m join (select m.*,n.* from (select * from m_sport_activity_vs_client where aid = ?) m" +
		" left join (select sum(distance) sumDistance,sum(steps) sumSteps,sum(calorie) sumCalorie,clientId,max(testDate) maxTestDate,min(testDate) minTestDate from m_sport" +
		" where testDate >= ? and testDate<= ? GROUP BY clientId) n " +
		" on m.cid = n.clientId) n on m.id = n.cid order by n.sumDistance desc"; 
		
		List args = new ArrayList();
		args.add(activity.getAid());
		Date endDate = DateUtils.getDateByType(new Date(), "yyyy-MM-dd");
		Date startDate = DateUtils.getDateByType(endDate, "yyyy-MM");
		args.add(startDate);
		args.add(endDate);
		
		Map scalars = new LinkedHashMap();
		scalars.put("id", StandardBasicTypes.INTEGER);
		scalars.put("name", StandardBasicTypes.STRING);
		scalars.put("gender", StandardBasicTypes.INTEGER);
		scalars.put("mobile", StandardBasicTypes.STRING);
		scalars.put("nickName", StandardBasicTypes.STRING);
		scalars.put("headPortrait", StandardBasicTypes.STRING);
		scalars.put("location", StandardBasicTypes.STRING);
		scalars.put("longitude", StandardBasicTypes.DOUBLE);
		scalars.put("latitude", StandardBasicTypes.DOUBLE);
		scalars.put("sumDistance", StandardBasicTypes.DOUBLE);
		scalars.put("maxTestDate", StandardBasicTypes.TIMESTAMP);
		scalars.put("minTestDate", StandardBasicTypes.TIMESTAMP);
		scalars.put("sumSteps", StandardBasicTypes.DOUBLE);
		scalars.put("sumCalorie", StandardBasicTypes.DOUBLE);
		
		return queryObjectsBySql(sql, null, scalars, args.toArray(), queryInfo, null);
	}
	
	public ClientInfo queryClientByaid(MSportActivity activity){
		String sql = "select c.* from (select * from m_sport_activity a where a.aid = ?) m join t_clientinfo c on m.creator = c.id";
		List args = new ArrayList();
		args.add(activity.getAid());
		
		List<ClientInfo> lst = executeNativeQuery(sql, args.toArray(), ClientInfo.class);
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}
	
	public List<MSportActivity> selfCreatedActivity(Integer cid) {
		List args = new ArrayList();
		String sql = "select * from m_sport_activity where 1=1 ";
		if (null != cid) {
			sql += " and creator=? ";
			args.add(cid);
		}
		return executeNativeQuery(sql, args.toArray(), MSportActivity.class);
	}

	public List<MSportActivity> seselfJoinedActivity(Integer cid) {
		List args = new ArrayList();
		String sql = "select msa.* from (SELECT * FROM m_sport_activity_vs_client ";
		sql += "  where 1=1 ";
		if (null != cid) {
			sql += " and cid=? and aid not in(select aid from m_sport_activity where creator = ?)";
			args.add(cid);
			args.add(cid);
		}
		sql += " ) mav LEFT JOIN m_sport_activity msa on mav.aid=msa.aid ";
		return executeNativeQuery(sql, args.toArray(), MSportActivity.class);
	}

	public int applyJoinActivity(Integer cid, Integer aid) {
		String sql = "insert into m_sport_activity_vs_client (aid,cid,createTime) values(?,?,?)";
		Object[] obj = {aid,cid,new Date()};
		return updateBySql(sql, obj);
	}

	public int isJoinActivity(Integer cid, Integer aid) {
		List args = new ArrayList();
		String sql = "select * from m_sport_activity_vs_client where 1=1 ";
		if (null != cid) {
			sql += " and cid=? ";
			args.add(cid);
		}
		if (null != aid) {
			sql += " and aid=? ";
			args.add(aid);
		}
		List list = executeNativeQuery(sql, args.toArray());
		if (!CollectionUtils.isEmpty(list)) {
			return 1;
		} else {
			return 0;
		}
	}

	
	public MSportActivity queryActivityByPwd(MSportActivity activity){
		List args = new ArrayList();
		String sql = "select * from m_sport_activity where 1 = 1";
		if(activity != null){
			sql += " and aid = ?";
			args.add(activity.getAid());
		}
		if(!StringUtils.isEmpty(activity.getPwd())){
			sql += " and pwd = ?";
			args.add(activity.getPwd());
		}
		
		List<MSportActivity> lst = executeNativeQuery(sql, args.toArray(), MSportActivity.class);
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		
		return null;
	}
}
