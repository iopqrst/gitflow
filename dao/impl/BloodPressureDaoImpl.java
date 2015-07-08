package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.AbnormalCondition;
import com.bskcare.ch.bo.BloodPressureAD;
import com.bskcare.ch.bo.MonitoringDataExtends;
import com.bskcare.ch.dao.BloodPressureDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.BloodPressure;

@Repository("bloodPressureDao")
@SuppressWarnings("unchecked")
public class BloodPressureDaoImpl extends BaseDaoImpl<BloodPressure> implements
		BloodPressureDao {

	public PageObject<BloodPressure> getListByBloodPressure(
			BloodPressure bloodPressure, QueryInfo queryInfo,
			QueryCondition queryCondition) {
		StringBuffer sql = new StringBuffer(
				"select * from m_blood_pressure t where 1 = 1 and t.clientId = ?  ");
		ArrayList args = new ArrayList();

		args.add(bloodPressure.getClientId());

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
				queryInfo, BloodPressure.class);
	}

	public BloodPressure getLastUploadDateTime(int clientId) {
//		StringBuffer sql = new StringBuffer("select * from m_blood_pressure b ");
//		sql.append(" where b.clientId = ? and b.uploadDateTime = (SELECT MAX(uploadDateTime) FROM m_blood_pressure WHERE clientId = b.clientId) ");
		String sql = "select * from m_blood_pressure where clientId = ? order by uploadDateTime desc limit 1";
		ArrayList args = new ArrayList();
		args.add(clientId);
		List list = (List) executeNativeQuery(sql.toString(), null, null, args
				.toArray(), BloodPressure.class);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return (BloodPressure) list.get(0);
		}
	}

	public List<BloodPressure> getTodayUploadDateTimeList(int clientId) {
		StringBuffer sql = new StringBuffer("select * from m_blood_pressure b ");
		sql
				.append(" where b.clientId = ? and DATE(b.uploadDateTime) = DATE(NOW()) ");
		sql.append(" ORDER BY b.uploadDateTime DESC ");

		ArrayList args = new ArrayList();
		args.add(clientId);
		List list = (List) executeNativeQuery(sql.toString(), null, null, args
				.toArray(), BloodPressure.class);
		return list;
	}

	public synchronized Date[] getLastMonthDate(int clientId, String table,
			int type, Integer interval) {
		StringBuffer sql = new StringBuffer(
				" select MAX(t.testDateTime) maxDate,MIN(t.testDateTime) minDate from "
						+ table + " t ");
		sql.append(" where 1 = 1 and t.clientId = ?  ");
		// 如果type大于0 表示是血糖类型
		if (type > 0) {
			sql.append(" and  t.bloodSugarType = ?  ");
		}

		// t_blood_pressure
		ArrayList args = new ArrayList();
		args.add(clientId);
		if (type > 0) {
			args.add(type);
		}

		List list = executeNativeQuery(sql.toString(), args.toArray());
		if (((Object[]) list.get(0))[0] != null) {
			// 取出当前人的最后上传时间 和第一次上传时间
			Date eDate = DateUtils.parseDate(((Object[]) list.get(0))[0]
					.toString(), "yyyy-MM-dd HH:mm:ss");
			Date sDate = DateUtils.parseDate(((Object[]) list.get(0))[1]
					.toString(), "yyyy-MM-dd HH:mm:ss");
			Date lastMoth = DateUtils.getAppointDate(eDate, interval);

			Date[] d = new Date[2];
			// 如果第一次上传时间 大于最后一次上传时间的 上个月日期 就以最后一次上传时间的上月时间 为 画图起始时间
			if (sDate.before(lastMoth)) {
				d[0] = lastMoth;
				d[1] = eDate;
			} else {
				// 如果第一次上传时间 小于最后一次上传时间的 上个月日期 就以第一次上传时间的上月时间 为 画图起始时间
				d[0] = sDate;
				d[1] = eDate;
			}
			return d;
		}
		return null;
	}

	public boolean addBloodPressure(BloodPressure bloodpressure) {
		bloodpressure = this.add(bloodpressure);
		if (bloodpressure.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public PageObject getAbnormalBPList(String areaChain,
			AbnormalCondition abnormalCondition, QueryInfo queryInfo) {

		ArrayList args = new ArrayList();

		StringBuffer sql = new StringBuffer(
				"select ci.id,ci.`name` clientName, ");
		sql.append(" ci.type,ai.`NAME` areaName,ci.gender,ci.age,ci.mobile, ");
		sql
				.append(" bp.testDateTime,bp.uploadDateTime,bp.dispose,bp.state,bp.sbp,bp.dbp, ");
		sql
				.append(" ci.areaChain from m_blood_pressure bp LEFT JOIN t_clientinfo ci on ci.id = bp.clientId ");
		sql.append(" LEFT JOIN ");
		sql.append(" (select t2.id areaId,t2.name from t_areainfo t2 ");
		sql.append(" where t2.`status` = 0 AND t2.id IS NOT NULL) ai ");
		sql.append(" on ci.areaId = ai.areaId ");
		sql.append(" where 1=1 ");

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
		scalars.put("sbp", StandardBasicTypes.INTEGER);
		scalars.put("dbp", StandardBasicTypes.INTEGER);

		PageObject page = this.queryObjectsBySql(sql.toString(), null, scalars,
				args.toArray(), queryInfo, MonitoringDataExtends.class);
		return page;
	}

	public int updateDispose(String table, int clientId, Date testDate,
			int bloodSugarType) {
		StringBuffer sql = new StringBuffer(
				" UPDATE "
						+ table
						+ " SET dispose = 1 where clientId = ? AND testDateTime <= ? and testDateTime >= ? ");
		if (bloodSugarType > 0) {
			sql.append(" and bloodSugarType = ? ");
		}

		ArrayList args = new ArrayList();
		args.add(clientId);
		args.add(testDate);
		args.add(DateUtils.getDateByType(testDate, "yyyy-MM-dd"));
		if (bloodSugarType > 0) {
			args.add(bloodSugarType);
		}

		return this.updateBySql(sql.toString(), args.toArray());
	}

	public Object queryLimitPressure(QueryCondition qc, BloodPressure bp) {
		String sql = "select max(sbp) as sbp_max ,min(sbp) as sbp_min,"
				+ " max(dbp) as dbp_max,min(dbp) as dbp_min from m_blood_pressure where 1=1 ";

		ArrayList args = new ArrayList();
		if (null != bp) {
			if (null != bp.getClientId()) {
				sql += " and clientId = ?";
				args.add(bp.getClientId());
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

	public Object queryPressureAverage(QueryCondition qc, BloodPressure bp) {
		String sql = "select AVG(sbp),AVG(dbp) from m_blood_pressure  where 1=1";
		ArrayList args = new ArrayList();
		if (null != bp) {
			if (null != bp.getClientId()) {
				sql += " and clientId = ?";
				args.add(bp.getClientId());
			}
		}
		if (null != qc) {
			if (null != qc.getBeginTime()) {
				sql += " and uploadDateTime >= ?";
				args.add(qc.getBeginTime());
			}
			if (null != qc.getEndTime()) {
				sql += " and uploadDateTime <= ?";
				args.add(qc.getEndTime());
			}
		}
		return findUniqueResultByNativeQuery(sql, args.toArray());
	}

	public PageObject getBloodPressureAlarmData(QueryInfo queryInfo) {
		return this
				.queryObjectsBySql(
						"SELECT a.id ,a.sbp ,a.dbp ,a.testDateTime,a.uploadDateTime,b.name,b.mobile,b.id as clientId FROM m_blood_pressure as a,t_clientinfo as b where a.state<>2 and a.dispose = 0 and  (sbp>=170 or sbp<80 or dbp<50 or dbp>=100) and b.id=a.clientid order by uploadDateTime desc",
						null, null, null, queryInfo, BloodPressureAD.class);
	}

	public List<BloodPressure> queryBloodPressureCount(Integer clientId,
			Date date) {
		String sql = "select * from m_blood_pressure where clientId = ? and uploadDateTime > ? and uploadDateTime< NOW()";
		List args = new ArrayList();
		args.add(clientId);
		args.add(date);
		return executeNativeQuery(sql, args.toArray(), BloodPressure.class);
	}

	public int queryBloodPressure(Integer clientId, int sbpMin, int sbpMax,
			int dbpMin, int dbpMax, Date date) {
		List args = new ArrayList();
		String sql = "select count(*) from m_blood_pressure where clientId = ? and uploadDateTime > ? and uploadDateTime< NOW()";
		args.add(clientId);
		args.add(date);
		if (sbpMin != 0) {
			sql += " and sbp > ?";
			args.add(sbpMin);
		}
		if (sbpMax != 0) {
			sql += " and sbp < ?";
			args.add(sbpMax);
		}
		if (dbpMin != 0) {
			sql += " and dbp < ?";
			args.add(dbpMin);
		}
		if (dbpMax != 0) {
			sql += " and dbp < ?";
			args.add(dbpMax);
		}

		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		int count = 0;
		if (obj != null && !obj.equals("")) {
			count = Integer.parseInt(obj.toString());
		}
		return count;
	}

	public PageObject<BloodPressure> queryBloodByThreeMonth(Integer clientId,
			QueryInfo queryInfo, QueryCondition qc) {
		ArrayList args = new ArrayList();
		String hql = "from BloodPressure where 1=1 ";
		if (null != clientId) {
			hql += " and clientId = ?";
			args.add(clientId);
		}
		if (null != qc) {
			if (null != qc.getBeginTime()) {
				hql += " and testDateTime >= ? ";
				args.add(qc.getBeginTime());
			}
			if (null != qc.getEndTime()) {
				hql += " and testDateTime <= ?";
				args.add(qc.getEndTime());
			}
		}
		hql += " order by uploadDateTime desc";
		return queryObjects(hql, args.toArray(), queryInfo);
	}
	
	
	public List queryBloodPressure(Integer clientId, Date startDate, Date endDate){
		String sql = "select * from m_blood_pressure where clientId = ? and testDateTime >= ? and testDateTime <= ? order by testDateTime asc";
		List args = new ArrayList();
		args.add(clientId);
		args.add(startDate);
		args.add(endDate);
		return executeNativeQuery(sql, args.toArray(), BloodPressure.class);
	}
	
}
