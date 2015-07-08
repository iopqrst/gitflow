package com.bskcare.ch.dao.impl.msport;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.ClientLocationExtend;
import com.bskcare.ch.dao.msport.MSportDao;
import com.bskcare.ch.vo.msport.MSport;

@Repository
@SuppressWarnings("unchecked")
public class MSportDaoImpl extends BaseDaoImpl<MSport> implements MSportDao{

	public MSport querySport(Integer clientId,Date testDate) {
		if(null == clientId || null == testDate) return null;
		String hql = "from MSport where clientId = ? and testDate = ?";
		ArrayList args = new ArrayList();
		args.add(clientId);
		args.add(testDate);
		
		List<MSport> list = this.executeFind(hql, args.toArray());
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	
	public List<MSport> querySportByCid(Integer cid){
		String hql = "from MSport where clientId = ? order by testDate asc";
		return executeFind(hql, cid);
	}
	
	
//	public List<MSport> querySportMonth(Integer cid,int year,int month){
//		
//		String hql = "from MSport where year(testDate)=? and month(testDate)=? and clientId = ?";
//		ArrayList args = new ArrayList();
//		args.add(year);
//		args.add(month);
//		args.add(cid);
//		return executeFind(hql, args.toArray());
//	}
	
	public List<MSport> querySportMonth(Integer cid,String testDate){
		String hql = "from MSport where date_format(testDate,'%Y-%m')=date_format(?,'%Y-%m') and clientId = ? order by testDate desc";
		ArrayList args = new ArrayList();
		args.add(testDate);
		args.add(cid);
		return executeFind(hql, args.toArray());
	}


	public List<MSport> getTodayUploadDateTimeList(Integer clientId) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append(" SELECT * FROM `m_sport` WHERE 1=1 ");
		if(null != clientId) {
			sql.append(" and clientId=? and DATE(uploadDate) = DATE(NOW()) ");
			args.add(clientId);
		}
		sql.append(" ORDER BY uploadDate DESC ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), MSport.class);
	}
	
	public Object queryTotalSport(Integer clientId){
		String sql = "select sum(deepSleep) deepSleepTotal,sum(lightSleep) lightSleepTotal," +
				"sum(lightestSleep) lightestSleepTotal,sum(poorSleep) poorSleepTotal from m_sport where clientId = ? order by testDate desc";
		
		return findUniqueResultByNativeQuery(sql, clientId);
	}

	
	public List<MSport> querySportByType(Integer cid,String type){
		List args = new ArrayList();
		String sql = "";
		if(type.equals("yesterday")){
			sql = "select * from m_sport where date_format(testDate,'%y-%m-%d') = date_format(DATE_SUB(curdate(), INTERVAL 1 day),'%y-%m-%d')";
		}
		if(type.equals("today")){
			sql = "select * from m_sport where date_format(testDate,'%y-%m-%d') = date_format(DATE_SUB(curdate(), INTERVAL 0 day),'%y-%m-%d')";
		}
		if(type.equals("week")){
			sql = "select * from m_sport where YEARWEEK(date_format(testDate,'%Y-%m-%d')) = YEARWEEK(NOW())-1";
		}
		if(type.equals("month")){
			sql = "select * from m_sport where date_format(testDate,'%Y-%m')=date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m') ";
		}
		if(cid != null){
			sql += " and clientId = ?";
			args.add(cid);
		}
		if(type.equals("yesterday")){
			sql += " order by calorie desc";
		}
		
		return executeNativeQuery(sql, null, null, args.toArray(), MSport.class);
	}
	

	public List<Object> querySportSort(String type){
		List args = new ArrayList();
		String sql = "";
		Map scalars = new LinkedHashMap();
		if(type.equals("week")){
			sql = "select sum(distance) distanceTotal,clientId from m_sport where YEARWEEK(date_format(testDate,'%Y-%m-%d')) = YEARWEEK(curdate(),'%Y-%m-%d')-1  GROUP BY(clientId) order by sum(distance) DESC";
			scalars.put("distanceTotal", StandardBasicTypes.DOUBLE);
		}
		if(type.equals("month")){
			sql = "select sum(steps) stepsTotal,clientId from m_sport where date_format(testDate,'%Y-%m')=date_format(DATE_SUB(curdate(), INTERVAL 1 MONTH),'%Y-%m')  GROUP BY(clientId) order by sum(steps) DESC";
			scalars.put("stepsTotal", StandardBasicTypes.DOUBLE);
		}
		scalars.put("clientId", StandardBasicTypes.INTEGER);
		return executeNativeQuery(sql, null, scalars, args.toArray(), null);
	}

	
	/**查询约跑成就榜总距离排名**/
	public List<Object> queryRunSport(int runCount){
		List args = new ArrayList();
		String sql = "select clientId,sum(distance) distanceTotal from m_sport group by clientId order by sum(distance) desc";
		if(runCount!=0){
			sql += "  limit ?";
			args.add(runCount);
		}
		Map scalars = new LinkedHashMap();
		scalars.put("clientId", StandardBasicTypes.INTEGER);
		scalars.put("distanceTotal",StandardBasicTypes.DOUBLE);
		
		return executeNativeQuery(sql, null, scalars, args.toArray(), null);
	}
	
	/**查询约跑所有的跑友成就榜**/
	public List<ClientLocationExtend> queryAllRunSport(int runCount){
		List args = new ArrayList();
//		String sql = "select n.*,sum(m.distance) distanceTotal,m.testDate testDate from " +
//				"(select l.clientId,l.location,l.longitude,l.latitude,c.name,c.nickName,c.gender,c.headPortrait,c.mobile from t_client_location l " +
//				"left join t_clientinfo c on l.clientId = c.id) n " +
//				"join (select clientId,distance,testDate from m_sport order by testDate) m on m.clientId = n.clientId " +
//				"group by m.clientId " +
//				"order by sum(m.distance) desc";
		
		String sql = "select n.*,sum(m.distance) distanceTotal,m.testDate testDate from " +
		"(select l.clientId,l.location,l.longitude,l.latitude,c.name,c.nickName,c.gender,c.headPortrait,c.mobile from t_client_location l " +
		"left join t_clientinfo c on l.clientId = c.id) n " +
		"join (select clientId,distance,testDate from m_sport where " +
		"date_format(uploadDate,'%y-%m-%d') = date_format(DATE_SUB(curdate(), INTERVAL 1 day),'%y-%m-%d') order by testDate) m on m.clientId = n.clientId " +
		"group by m.clientId " +
		"order by sum(m.distance) desc";
		
		if(runCount != 0){
			sql += " limit ?";
			args.add(runCount);
		}
		return executeNativeQuery(sql, null, null, args.toArray(), ClientLocationExtend.class);
	}
	
	public List<Object> queryMsportClient(Integer clientId,String type){
		String sql = "select "+type+" type,testDate from m_sport where clientId = ? order by testDate asc";
		List args = new ArrayList();
		args.add(clientId);
		Map scalars = new LinkedHashMap();
		scalars.put("type", StandardBasicTypes.DOUBLE);
		scalars.put("testDate", StandardBasicTypes.TIMESTAMP);
		return executeNativeQuery(sql, null, scalars, args.toArray(), null);
	}
	
	
	
	public List<Object> queryClientMsport(Integer clientId,String type,Date startDate,Date endDate){
		
		String sql = "select "+type+" typeVal,testDate from m_sport where clientId = ? and" +
		" testDate >= ? and testDate <= ?" +
		"order by testDate asc";
		
		List args = new ArrayList();
		args.add(clientId);
		args.add(startDate);
		args.add(endDate);
		
		Map scalars = new LinkedHashMap();
		scalars.put("typeVal", StandardBasicTypes.DOUBLE);
		scalars.put("testDate", StandardBasicTypes.TIMESTAMP);
		return executeNativeQuery(sql, null, scalars, args.toArray(), null);
	}
	
	public List<Object> queryMsportSleepSum(Integer clientId,Date startDate, Date endDate){
		String sql = "select sum(deepSleep) deep,sum(lightSleep) light,sum(lightestSleep) lightest,sum(poorSleep) poor" +
				  " from m_sport where clientId = ? and testDate>= ? and testDate<= ?";
		List args = new ArrayList();
		args.add(clientId);
		args.add(startDate);
		args.add(endDate);
		
		Map scalars = new LinkedHashMap();
		scalars.put("deep", StandardBasicTypes.DOUBLE);
		scalars.put("light", StandardBasicTypes.DOUBLE);
		scalars.put("lightest", StandardBasicTypes.DOUBLE);
		scalars.put("poor", StandardBasicTypes.DOUBLE);
		return executeNativeQuery(sql, null, scalars, args.toArray(), null);
	}
}
