package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.AbnormalCondition;
import com.bskcare.ch.bo.BloodSugarAD;
import com.bskcare.ch.bo.MonitoringDataExtends;
import com.bskcare.ch.dao.BloodSugarDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.BloodSugar;

@Repository("bloodSugarDao")
@SuppressWarnings("unchecked")
public class BloodSugarDaoImpl extends BaseDaoImpl<BloodSugar> implements
		BloodSugarDao {

	//TODO
	public PageObject getListByBloodSugar(BloodSugar bloodSugar,
			QueryInfo queryInfo, QueryCondition queryCondition) {
/*
		StringBuffer sql = new StringBuffer(
				"select * from m_blood_sugar t where 1 = 1 and t.clientId = ? ");
		ArrayList args = new ArrayList();
		args.add(bloodSugar.getClientId());
		if (bloodSugar.getBloodSugarType() != null) {
			if (bloodSugar.getBloodSugarType() == 1) {
				sql.append(" and t.bloodSugarType in("
						+ BloodSugar.SUGAR_BEFORE + ")");
			} else if (bloodSugar.getBloodSugarType() == 2) {
				sql.append(" and t.bloodSugarType in(" + BloodSugar.SUGAR_AFTER
						+ ")");
			}else if(bloodSugar.getBloodSugarType() == 3){
				sql.append(" and t.bloodSugarType in(" + BloodSugar.SUGAR_OTHER
						+ ")");
			}
		}
		// args.add(bloodSugar.getBloodSugarType()) ;

		Date sDate = null;
		Date eDate = null;
		if (queryCondition != null) {
			sDate = queryCondition.getBeginTime();
			eDate = queryCondition.getEndTime();
		}
		if (sDate != null && eDate != null) {
			sql.append(" and t.testDateTime >= ?  ");
			sql.append(" and t.testDateTime <= ? ");

		}

		if (sDate != null && eDate != null) {
			// 如果选择的开始日小于结束日期，开始日期和结束日期 对换
			if (!sDate.before(eDate)) {
				Date d = sDate;
				sDate = eDate;
				eDate = d;
			}
			args.add(sDate);
			args.add(DateUtils.getAppointDate(eDate, 1));
		}

		return queryObjectsBySql(sql.toString(), null, null, args.toArray(),
				queryInfo, BloodSugar.class);
*/
		
		
		List args = new ArrayList();
		String sql = "select t.* from (select * from m_blood_sugar where 1 = 1";
		if(bloodSugar != null){
			if (bloodSugar.getClientId() != null){
				sql += " and clientId = ?";
				args.add(bloodSugar.getClientId());
			}
			if (bloodSugar.getBloodSugarType() != null) {
				if (bloodSugar.getBloodSugarType() == 1) {
					sql += " and bloodSugarType in("
							+ BloodSugar.SUGAR_BEFORE + ")";
				} else if (bloodSugar.getBloodSugarType() == 2) {
					sql += " and bloodSugarType in(" + BloodSugar.SUGAR_AFTER
							+ ")";
				}else if(bloodSugar.getBloodSugarType() == 3){
					sql += " and bloodSugarType in(" + BloodSugar.SUGAR_OTHER
							+ ")";
				}
			}
			Date sDate = null;
			Date eDate = null;
			if (queryCondition != null) {
				sDate = queryCondition.getBeginTime();
				eDate = queryCondition.getEndTime();
			}
			if (sDate != null && eDate != null) {
				sql += " and testDateTime >= ? ";
				sql += " and testDateTime <= ? ";
			}
			if (sDate != null && eDate != null) {
				// 如果选择的开始日小于结束日期，开始日期和结束日期 对换
				if (!sDate.before(eDate)) {
					Date d = sDate;
					sDate = eDate;
					eDate = d;
				}
				args.add(sDate);
				args.add(DateUtils.getAppointDate(eDate, 1));
			}
		}
		
		sql += " order by testDateTime desc, id desc) t group by DATE_FORMAT(t.testDateTime,'%Y-%m-%d'),t.bloodSugarType";
		sql += " HAVING MAX(t.testDateTime)";
		
		return queryObjectsBySql(sql.toString(), null, null, args.toArray(),
				queryInfo, BloodSugar.class);
	}

	public void addBloodSugar(BloodSugar bloodSugar) {
		this.add(bloodSugar);
	}

	public PageObject getAbnormalBSList(String areaChain,
			AbnormalCondition abnormalCondition, BloodSugar bloodSugar,
			QueryInfo queryInfo) {
		StringBuffer sql = new StringBuffer(
				"select ci.id,ci.`name` clientName, ");
		sql.append(" ci.type,ai.`NAME` areaName,ci.gender,ci.age,ci.mobile, ");
		sql
				.append(" bp.testDateTime,bp.uploadDateTime,bp.dispose,bp.state,bp.bloodSugarValue, ");
		sql
				.append(" ci.areaChain from m_blood_sugar bp LEFT JOIN t_clientinfo ci on ci.id = bp.clientId ");
		sql.append(" LEFT JOIN ");
		sql.append(" (select t2.id areaId,t2.name from t_areainfo t2 ");
		sql.append(" where t2.`status` = 0 AND t2.id IS NOT NULL) ai ");
		sql.append(" on ci.areaId = ai.areaId ");
		sql.append(" where 1=1 ");
		sql.append(" and bp.bloodSugarType = ? ");

		ArrayList args = new ArrayList();
		args.add(bloodSugar.getBloodSugarType());

		if (!StringUtils.isEmpty(areaChain)) {
			String[] areaChains = areaChain.split("#");
			sql.append(" and ( ");

			if (areaChains.length > 1) {
				for (int i = 0; i < areaChains.length; i++) {
					sql.append(" ci.areaChain like ? ");
					args.add(areaChains[i] + "%");
					if (i != areaChains.length - 1) {
						sql.append(" or");
					}
				}
			} else {
				sql.append(" ci.areaChain like ? ");
				args.add(areaChains[0] + "%");
			}
			sql.append(")");
		}

		if (abnormalCondition != null) {
			if (abnormalCondition.getDispose() != null
					&& abnormalCondition.getDispose() > -1) {
				sql.append(" and bp.dispose = ? ");
				args.add(abnormalCondition.getDispose());
			}
			if (abnormalCondition.getState() != null
					&& abnormalCondition.getState() > -1) {
				sql.append(" and bp.state = ? ");
				args.add(abnormalCondition.getState());
			}
			if (StringUtils.hasLength(abnormalCondition.getName())) {
				sql.append(" and ci.name like ? ");
				args.add("%" + abnormalCondition.getName().trim() + "%");
			}
			if (abnormalCondition.getStestDateTime() != null
					&& abnormalCondition.getEtestDateTime() != null) {
				sql.append(" and bp.testDateTime >= ?  ");
				sql.append(" and bp.testDateTime <= ? ");
				args.add(abnormalCondition.getStestDateTime());
				args.add(abnormalCondition.getEtestDateTime());
			}
		}

		Map scalars = new LinkedHashMap();
		scalars.put("id", StandardBasicTypes.INTEGER);
		scalars.put("clientName", StandardBasicTypes.STRING);
		scalars.put("type", StandardBasicTypes.INTEGER);
		scalars.put("areaName", StandardBasicTypes.STRING);
		scalars.put("gender", StandardBasicTypes.INTEGER);
		scalars.put("age", StandardBasicTypes.INTEGER);
		scalars.put("mobile", StandardBasicTypes.STRING);
		scalars.put("testDateTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("uploadDateTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("dispose", StandardBasicTypes.INTEGER);
		scalars.put("state", StandardBasicTypes.INTEGER);
		scalars.put("areaChain", StandardBasicTypes.STRING);
		scalars.put("bloodSugarValue", StandardBasicTypes.DOUBLE);

		PageObject page = this.queryObjectsBySql(sql.toString(), null, scalars,
				args.toArray(), queryInfo, MonitoringDataExtends.class);

		return page;
	}

	public BloodSugar getLastUploadDateTime(int clientId, Integer type) {
		StringBuffer sql = new StringBuffer("select * from m_blood_sugar b ");
		sql
				.append(" where b.clientId = ? and b.bloodSugarType = ? and  b.uploadDateTime = (SELECT MAX(uploadDateTime) FROM m_blood_sugar WHERE clientId = b.clientId and bloodSugarType = b.bloodSugarType) ");

		ArrayList args = new ArrayList();
		args.add(clientId);
		args.add(type);
		List list = (List) executeNativeQuery(sql.toString(), null, null, args
				.toArray(), BloodSugar.class);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return (BloodSugar) list.get(0);
		}
	}

	public List<BloodSugar> getTodayUploadDateTimeList(int clientId,
			Integer type) {
		StringBuffer sql = new StringBuffer("select * from m_blood_sugar b ");
		sql
				.append(" where b.clientId = ? and b.bloodSugarType = ? and DATE(b.uploadDateTime) = DATE(NOW()) ");
		sql.append(" ORDER BY b.uploadDateTime DESC ");

		ArrayList args = new ArrayList();
		args.add(clientId);
		args.add(type);
		List list = (List) executeNativeQuery(sql.toString(), null, null, args
				.toArray(), BloodSugar.class);
		return list;
	}

	public Object queryLimiteSugar(QueryCondition qc, BloodSugar bs) {
		String sql = "select max(bloodSugarValue),min(bloodSugarValue) from m_blood_sugar where 1=1";

		ArrayList args = new ArrayList();
		if (null != bs) {
			if (null != bs.getBloodSugarType()) {
				sql += " and bloodSugarType = ?";
				args.add(bs.getBloodSugarType());
			}
			if (null != bs.getClientId()) {
				sql += " and clientId = ?";
				args.add(bs.getClientId());
			}
		}

		if (null != qc) {
			if (null != qc.getBeginTime()) {
				sql += " and testDateTime >= ?";
				args.add(qc.getBeginTime());
			}
			if (null != qc.getEndTime()) {
				sql += " and testDateTime <= ?";
				args.add(qc.getEndTime());
			}
		}

		return findUniqueResultByNativeQuery(sql, args.toArray());
	}

	public PageObject getBloodSugarAlarmData(QueryInfo queryInfo) {
		return this
				.queryObjectsBySql(
						"SELECT a.id,b.id as clientId,a.bloodSugarValue,a.bloodSugarType,a.testDateTime,a.uploadDateTime,b.name,b.mobile  FROM m_blood_sugar as a,t_clientinfo as b where a.state<>2 and  (a.bloodSugarValue>16 or a.bloodSugarValue<3) and a.dispose = 0 and b.id=a.clientid order by uploadDateTime desc",
						null, null, null, queryInfo, BloodSugarAD.class);
	}

	/** 查询某个用户在某个时间段测量数据的条数 **/
	public List<BloodSugar> queryBloodSugarCount(Integer clientId, Date date,
			int type) {
		String sql = "select * from m_blood_sugar where clientId = ? and bloodSugarType = ? and uploadDateTime > ? and uploadDateTime< NOW()";
		List args = new ArrayList();
		args.add(clientId);
		args.add(type);
		args.add(date);
		return executeNativeQuery(sql, args.toArray(), BloodSugar.class);
	}

	/** 查询某个用户某个时间端的血糖值情况 **/
	public int queryBloodSugar(Integer clientId, Date date, double maxVal,
			double minVal, int type) {
		List args = new ArrayList();
		String sql = "select count(*) from m_blood_sugar where clientId = ? and bloodSugarType = ? and uploadDateTime > ? and uploadDateTime< NOW()";
		args.add(clientId);
		args.add(type);
		args.add(date);
		if (minVal != 0) {
			sql += " and bloodSugarValue > ?";
			args.add(minVal);
		}
		if (maxVal != 0) {
			sql += " and bloodSugarValue< ?";
			args.add(maxVal);
		}
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		int count = 0;
		if (obj != null && !obj.equals("")) {
			count = Integer.parseInt(obj.toString());
		}
		return count;
	}

	public int quertBloodSugar(Integer cid, Date beginDate, Date EndDate,
			Integer stauts) {
		List args = new ArrayList();
		
		String sql = "select count(n.id) from (select id from (SELECT * from m_blood_sugar where 1 = 1";
//		String sql = "SELECT COUNT(id) from m_blood_sugar where 1=1 ";
		sql += " and clientId = ?  ";
		args.add(cid);
		sql += " and testDateTime between ? and ?";
		args.add(beginDate);
		args.add(EndDate);
		if (stauts != null) {
			if (BloodSugar.IDEAL_STAUTS == stauts) {// 理想
				sql += " AND ((	bloodSugarType IN (?, ?, ?, ?) AND ( bloodSugarValue >= 4.4 and bloodSugarValue < 6.1)) or "
						+ "	(	bloodSugarType IN (?, ?, ?, ?) AND ( bloodSugarValue >= 4.4 and bloodSugarValue <8.0)) or "
						+ "	(	bloodSugarType IN (?, ?) AND ( bloodSugarValue >= 4.4 and bloodSugarValue <= 10)))";
			}
			if (stauts == BloodSugar.WELL_STAUTS) {// 良好
				sql += " AND ((	bloodSugarType IN (?, ?, ?, ?) AND ( bloodSugarValue >= 6.1 and bloodSugarValue <= 7.0)) or "
						+ "	(	bloodSugarType IN (?, ?, ?, ?) AND ( bloodSugarValue >= 8.0 and bloodSugarValue <=10.0)) or "
						+ "	(	bloodSugarType IN (?, ?) AND (( bloodSugarValue >= 3.9 and bloodSugarValue < 4.4)or(bloodSugarValue > 10 and bloodSugarValue <= 11.1))))";
			}
			if (stauts == BloodSugar.BADNESS_STAUTS) {// 不良
				sql += " AND ((	bloodSugarType IN (?, ?, ?, ?) AND ( bloodSugarValue > 7 or bloodSugarValue < 4.4 )) or "
						+ "	(	bloodSugarType IN (?, ?, ?, ?) AND ( bloodSugarValue >10 or bloodSugarValue < 4.4  )) or "
						+ "	(	bloodSugarType IN (?, ?) AND ( bloodSugarValue >11.1 OR bloodSugarValue < 3.9)))";
			}
			args.add(BloodSugar.SUGAR_TYPE);
			args.add(BloodSugar.SUGAR_ZAOCAN_BEFORE);
			args.add(BloodSugar.SUGAR_WUCAN_BEFORRE);
			args.add(BloodSugar.SUGAR_WANCAN_BEFORE);
			args.add(BloodSugar.SUGAR_TYPE_2H);
			args.add(BloodSugar.SUGAR_ZAOCAN_AFTER);
			args.add(BloodSugar.SUGAR_WUCAN_AFTER);
			args.add(BloodSugar.SUGAR_WANCAN_AFTER);
			args.add(BloodSugar.SUGAR_SLEEP_BEFORE);
			args.add(BloodSugar.SUGAR_LINGCHEN);
		}

		sql += " order by testDateTime desc, id desc) m " +
			   " group by DATE_FORMAT(m.testDateTime,'%Y-%m-%d'),m.bloodSugarType" +
			   " HAVING MAX(m.testDateTime)) n";
		
		List list = this.executeNativeQuery(sql, args.toArray());
		int num = Integer.parseInt(list.get(0).toString());
		return num;
	}

	public List<BloodSugar> queryBloodSugar(Integer clientId) {
		List args = new ArrayList();
		String msql = "select * from m_blood_sugar m where 1=1 ";
		if (null != clientId) {
			String csql = " and bloodSugarType =(select mb.bloodSugarType from m_blood_sugar mb ";
			csql += " where mb.clientId=? and mb.bloodSugarType in ("
					+ BloodSugar.SUGAR_BEFORE
					+ ","
					+ BloodSugar.SUGAR_AFTER
					+ ") order by mb.id desc limit 1 ) and clientId=? order by id desc limit 2 ";
			args.add(clientId);
			args.add(clientId);
			msql += csql;
		}
		return executeNativeQuery(msql, args.toArray(), BloodSugar.class);
	}
	
	public List<BloodSugar> queryTgBloodSugar(Integer clientId, Date startDate, Date endDate){
		List args = new ArrayList();
		String sql = "select m.* from (select * from m_blood_sugar where 1 = 1";
		if (clientId != null){
			sql += " and clientId = ?";
			args.add(clientId);
		}
		if(startDate != null){
			sql += " and testDateTime >= ?";
			args.add(startDate);
		}
		if(endDate != null){
			sql += " and testDateTime <= ?";
			args.add(endDate);
		}
		sql += " order by testDateTime desc, id desc) m group by DATE_FORMAT(m.testDateTime,'%Y-%m-%d'),m.bloodSugarType";
		sql += " HAVING MAX(m.testDateTime) order by m.testDateTime asc";
		
		return executeNativeQuery(sql, args.toArray(), BloodSugar.class);
	}
	
	public BloodSugar queryTgBloodSugar(Integer clientId){
		String sql = "select * from m_blood_sugar where clientId = ? order by id desc limit 1";
		List args = new ArrayList();
		args.add(clientId);
		List<BloodSugar> lst = executeNativeQuery(sql, args.toArray(), BloodSugar.class);
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}
	

	public List<BloodSugar> queryBloodSugarDetail(Integer cid,String type,String testDate) {
		List args = new ArrayList();
		String sql = "select m.* from (select * from m_blood_sugar where 1 = 1";
		if (null != cid) {
			sql += " and clientId=? ";
			args.add(cid);
		}
		sql += " and bloodSugarType not in("+ BloodSugar.SUGAR_TYPE_2H + ") ";
		if (!StringUtils.isEmpty(type)) {
			if (type.equals("week")) {
				sql += " and DATEDIFF(DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 1 DAY),'%Y%m%d'),testDateTime) <=7 and testDateTime < NOW() ";
			}
			if (type.equals("month")) {
				if (!StringUtils.isEmpty(testDate)) {
					sql += " and DATE_FORMAT(testDateTime, '%Y%m') = DATE_FORMAT(? ,'%Y%m')";
					args.add(testDate);
				} else {
					sql += " and DATE_FORMAT(testDateTime, '%Y%m') = DATE_FORMAT(CURDATE() ,'%Y%m')";
				}
			}
		}
		sql += " order by testDateTime desc, id desc) m group by DATE_FORMAT(m.testDateTime,'%Y-%m-%d'),m.bloodSugarType";
		sql += " HAVING MAX(m.testDateTime) order by m.testDateTime asc";
		
		return executeNativeQuery(sql, args.toArray(), BloodSugar.class);
	}
	
	public List<BloodSugar> queryBloodSugarDetailInfo(Integer cid, Date beginDate, Date endDate){
/*		List args = new ArrayList();
		String sql = "SELECT * FROM m_blood_sugar where 1=1 ";
		if (null != cid) {
			sql += " and clientId=? ";
			args.add(cid);
		}
		sql += " and bloodSugarType not in("+ BloodSugar.SUGAR_TYPE_2H + ") ";
		if(beginDate != null && endDate != null){
			sql += " and testDateTime >= ? and testDateTime <= ?";
			args.add(beginDate);
			args.add(endDate);
		}
		sql += " order by testDateTime asc ";
*/
		List args = new ArrayList();
		String sql = "select m.* from (select * from m_blood_sugar where 1 = 1";
		if (cid != null){
			sql += " and clientId = ?";
			args.add(cid);
		}
		sql += " and bloodSugarType not in("+ BloodSugar.SUGAR_TYPE_2H + ") ";
		if(beginDate != null){
			sql += " and testDateTime >= ?";
			args.add(beginDate);
		}
		if(endDate != null){
			sql += " and testDateTime <= ?";
			args.add(endDate);
		}
		sql += " order by testDateTime desc, id desc) m group by DATE_FORMAT(m.testDateTime,'%Y-%m-%d'),m.bloodSugarType";
		sql += " HAVING MAX(m.testDateTime) order by m.testDateTime asc";
		return executeNativeQuery(sql, args.toArray(), BloodSugar.class);
	}
	
	public List<BloodSugar> queryBloodSugar(BloodSugar bloodSugar, Date beginDate, Date endDate){
		/**
		    select m.* from (select * from m_blood_sugar where clientId = 10033 and bloodSugarType in (1,10) order by testDateTime desc) m
			group by DATE_FORMAT(m.testDateTime,'%Y-%m-%d'),m.bloodSugarType HAVING MAX(m.testDateTime) order by m.testDateTime asc
		 */
		List args = new ArrayList();
		String sql = "select m.* from (select * from m_blood_sugar where 1 = 1";
		if(bloodSugar != null){
			if (bloodSugar.getClientId() != null){
				sql += " and clientId = ?";
				args.add(bloodSugar.getClientId());
			}
			if (bloodSugar.getBloodSugarType() != null) {
				if (bloodSugar.getBloodSugarType() == 1) {
					sql += " and bloodSugarType in("
							+ BloodSugar.SUGAR_BEFORE + ")";
				} else if (bloodSugar.getBloodSugarType() == 2) {
					sql += " and bloodSugarType in(" + BloodSugar.SUGAR_AFTER
							+ ")";
				}else if(bloodSugar.getBloodSugarType() == 3){
					sql += " and bloodSugarType in(" + BloodSugar.SUGAR_OTHER
							+ ")";
				}
			}
			if(beginDate != null){
				sql += " and testDateTime >= ?";
				args.add(beginDate);
			}
			if(endDate != null){
				sql += " and testDateTime <= ?";
				args.add(endDate);
			}
		}
		
		sql += " order by testDateTime desc, id desc) m group by DATE_FORMAT(m.testDateTime,'%Y-%m-%d'),m.bloodSugarType";
		sql += " HAVING MAX(m.testDateTime) order by m.testDateTime asc";
		
		return executeNativeQuery(sql, args.toArray(), BloodSugar.class);
	}
	
	
	public int queryClientBloodSugar(BloodSugar bloodSugar, Date beginDate, Date endDate){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from m_blood_sugar where 1 = 1");
		List args = new ArrayList();
		if(bloodSugar != null){
			if(bloodSugar.getClientId() != null){
				sql.append(" and clientId = ?");
				args.add(bloodSugar.getClientId());
			}
			if(beginDate != null){
				sql.append(" and testDateTime >= ?");
				args.add(beginDate);
			}
			if(endDate != null){
				sql.append(" and testDateTime <= ?");
				args.add(endDate);
			}
		}
		
		Object obj = findUniqueResultByNativeQuery(sql.toString(), args.toArray());
		if(obj != null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	
	
	public List<Object> queryUploadDataRecord(Integer cid, Date beginDate, Date endDate){
		if(cid != null){
			List args = new ArrayList();
			String sql = "select testDate from (";
				  sql += "	select DATE_FORMAT(testDateTime,'%Y-%m-%d') testDate from m_blood_sugar where clientId = ?";
				  args.add(cid);
				  //只计算餐前的和餐后的，其他的不算
				  sql += " and bloodSugarType in ("+ BloodSugar.SUGAR_BEFORE + "," + BloodSugar.SUGAR_AFTER +")";
				  
				  if(beginDate != null){
					  sql += "  and testDateTime >= ?";
					  args.add(beginDate);
				  }
				  if(endDate != null){
					  sql += " and testDateTime< ?";
					  args.add(endDate);
				  }
				  
				 sql += " UNION";
				 sql += "	select DATE_FORMAT(testDateTime,'%Y-%m-%d') testDate from m_blood_pressure where clientId = ?";
				  args.add(cid);
				  if(beginDate != null){
					  sql += "  and testDateTime >= ?";
					  args.add(beginDate);
				  }
				  if(endDate != null){
					  sql += " and testDateTime< ?";
					  args.add(endDate);
				  }
				  
				 sql += " UNION";
					 
				 sql += "	select testDate from m_sport where clientId = ?";
				  args.add(cid);
				  if(beginDate != null){
					  sql += "  and testDate >= ?";
					  args.add(beginDate);
				  }
				  if(endDate != null){
					  sql += " and testDate< ?";
					  args.add(endDate);
				  }
				  
				 sql += " UNION";
					 
				 sql += "	select createTime testDate from tg_record_food where clientId = ?";
				  args.add(cid);
				  if(beginDate != null){
					  sql += "  and createTime >= ?";
					  args.add(beginDate);
				  }
				  if(endDate != null){
					  sql += " and createTime< ?";
					  args.add(endDate);
				  }
				  
//				 sql += " UNION";
//					 
//				 sql += "	select DATE_FORMAT(taskDate,'%Y-%m-%d') testDate from tg_timeline_task where clientId = ? and conType = 12";
//				  args.add(cid);
//				  if(beginDate != null){
//					  sql += "  and taskDate >= ?";
//					  args.add(beginDate);
//				  }
//				  if(endDate != null){
//					  sql += " and taskDate< ?";
//					  args.add(endDate);
//				  }
			
			sql += ") m order by testDate asc";
			return executeNativeQuery(sql, args.toArray());
		}
		return null;
	}
	
	public List<BloodSugar> queryLatestBloodSugar(Integer cid, Date beginDate, Date endDate){
		StringBuffer hql = new StringBuffer();
		hql.append("from BloodSugar where 1 = 1");
		List args = new ArrayList();

		hql.append(" and bloodSugarType in ("+ BloodSugar.SUGAR_BEFORE + "," + BloodSugar.SUGAR_AFTER +")");
		
		if(null != cid){
			hql.append(" and clientId = ?");
			args.add(cid);
		}
		if(beginDate != null){
			hql.append(" and testDateTime >= ?");
			args.add(beginDate);
		}
		if(endDate != null){
			hql.append(" and testDateTime <= ?");
			args.add(endDate);
		}
		
		hql.append(" order by testDateTime desc");
		return executeFind(hql.toString(), args.toArray(), 1);
	}
	

	/**
	 * 查询最后一次的血糖数据，如果时间参数为空，那么将查找该用户的最后一次血糖数据
	 * @author sun
	 * @version 2014-12-22 下午04:17:47
	 * @param clientId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public BloodSugar queryLastBloodSugarByCId(Integer clientId , Date beginDate, Date endDate){
		
		//判断参数是否正确
 		if(clientId == null ){
			System.err.println("*************参数异常**********返回null") ;
			return null ;
		}
		
		//hql语句
		StringBuffer hql = new StringBuffer() ;
		//参数集合
		List args = new ArrayList();
		
		hql.append(" from BloodSugar where 1 = 1") ;
		hql.append(" and clientId = ?") ;
		args.add(clientId) ;
		
		if (beginDate != null){
			
			hql.append(" and testDateTime >= ?");
			args.add(beginDate);
		}
		if (endDate != null){
			 
			hql.append(" and testDateTime <= ?");
			args.add(endDate);
		}
		
		hql.append(" ORDER BY testDateTime DESC, id DESC") ;
		
		
		List <BloodSugar> list = executeFind(hql.toString(), args.toArray(), 1) ;
		
		if (list != null && list.size() != 0){
			
			//返回一条数据
			return list.get(0) ;
		}
		
		return null ;
	}
	
	
	public int queryUploadBloodSugarCount(Date startDate, Date endDate){
		String sql = "select count(DISTINCT(clientId)) from bskcare.m_blood_sugar where 1 = 1 ";
		List args = new ArrayList();
		if(startDate != null){
			sql += " and uploadDateTime >= ?";
			args.add(startDate);
		}
		if(endDate != null){
			sql += " and uploadDateTime <= ?";
			args.add(endDate);
		}
		
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	
	
	public Integer queryAvgBloodSugar(Date startDate, Date endDate){
		List args = new ArrayList();
		String sql = "select AVG(m.count) from (select count(DISTINCT(clientId)) count from bskcare.m_blood_sugar where 1 = 1";
		if(startDate != null){
			sql += " and uploadDateTime >= ?";
			args.add(startDate);
		}
		if(endDate != null){
			sql += " and uploadDateTime <= ?";
			args.add(endDate);
		}
		
		sql += " GROUP BY DATE_FORMAT(uploadDateTime,'%Y-%m-%d')) m";
		
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null){
			double count = Double.parseDouble(obj.toString());
			return (int)count;
		}
		return 0;
	}
	
	public int queryUploadDays(Integer clientId, Date startDate, Date endDate) {
		String sql = "select COUNT(DISTINCT(DATE_FORMAT(uploadDateTime,'%Y-%m-%d'))) from m_blood_sugar t"
				+ " where clientId = ? and uploadDateTime >= ? and uploadDateTime <= ?";
		
		Object obj = findUniqueResultByNativeQuery(sql, new Object[]{clientId, startDate, endDate});
		if(obj != null){
			double count = Double.parseDouble(obj.toString());
			return (int)count;
		}
		return 0;
	}
}
