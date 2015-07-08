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
import com.bskcare.ch.bo.ClientExtendExtend;
import com.bskcare.ch.bo.ClientRegEval;
import com.bskcare.ch.constant.ScoreConstant;
import com.bskcare.ch.dao.ClientExtendDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientExtend;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ntg.NTgBalanceRecord;

@Repository
@SuppressWarnings("unchecked")
public class ClientExtendDaoImpl extends BaseDaoImpl<ClientExtend> implements
		ClientExtendDao {

	public ClientExtend queryLastTimeByClientId(ClientExtend cx) {
		String hql = "from ClientExtend where 1 = 1 ";
		List args = new ArrayList();
		if (cx != null) {
			if (cx.getClientId() != null) {
				hql += " and clientId = ? ";
				args.add(cx.getClientId());
			}
			if (!StringUtils.isEmpty(cx.getPassivityinvite())) {
				hql += " and initiativeinvite = ?";
				args.add(cx.getPassivityinvite());
			}
		}
		List<ClientExtend> lst = executeFind(hql, args.toArray());
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}

	public void updateLastTime(Integer clientId, String type) {
		String sql = "update t_client_extend set " + type
				+ " = ? where clientId = ?";
		List args = new ArrayList();
		args.add(new Date());
		args.add(clientId);
		updateBySql(sql, args.toArray());
	}

	public List queryStatisticList(String timeType) {
		String mSql = "select rs.source,count(*) count from ( ";
		String cSql = "select * from t_client_extend where 1=1 ";
		if (!StringUtils.isBlank(timeType)) {
			if (timeType.equals("1")) {
				cSql += " and date(date_format(regTime,'%Y-%m-%d'))=date(now()) ";// 今天
			}
			if (timeType.equals("2")) {
				cSql += " and WEEK(date_format(regTime,'%Y-%m-%d'), 1) = YEARWEEK(now()) ";// 本周
			}
			if (timeType.equals("3")) {
				cSql += " and DATE_FORMAT(regTime, '%Y%m') = DATE_FORMAT(CURDATE() ,'%Y%m') ";// 当月
			}
			if (timeType.equals("4")) {
				cSql += " and YEAR(regTime)=YEAR(NOW()) ";// 当年
			}
		}
		mSql += cSql + ") rs ";
		mSql += " group by rs.source";
		return executeNativeQuery(mSql);
	}

	public List queryRegSource(ClientInfo clientInfo,
			QueryCondition queryCondition, String areaChain) {
		List args = new ArrayList();
		String sql = "select source,count(*) count from (select id from t_clientinfo where status = ? ";
		args.add(ClientInfo.STATUS_NORMAL);

		if (!StringUtils.isEmpty(areaChain)) {
			String[] areaChains = areaChain.split("#");
			sql += " and (";
			if (areaChains.length > 1) {
				for (int i = 0; i < areaChains.length; i++) {
					sql += " areaChain like ?";
					args.add(areaChains[i] + "%");
					if (i != areaChains.length - 1) {
						sql += " or";
					}
				}
			} else {
				sql += " areaChain like ?";
				args.add(areaChains[0] + "%");
			}
			sql += ")";
		}
		if (clientInfo != null) {
			if (clientInfo.getAge() != null && clientInfo.getAge() != 0) {
				if (clientInfo.getAge() == 1) {
					sql += " and age >= 20 and age < 30";
				} else if (clientInfo.getAge() == 2) {
					sql += " and age >= 30 and age < 40";
				} else if (clientInfo.getAge() == 3) {
					sql += " and age >= 40 and age < 50";
				} else if (clientInfo.getAge() == 4) {
					sql += " and age >= 50 and age < 60";
				} else if (clientInfo.getAge() == 5) {
					sql += " and age >= 60";
				}
			}
		}
		if (null != queryCondition) {
			if (null != queryCondition.getBeginTime()) {
				sql += " and createTime >= ?";
				args.add(queryCondition.getBeginTime());
			}

			if (null != queryCondition.getEndTime()) {
				sql += " and createTime <= ?";
				args.add(DateUtils.getAppointDate(queryCondition.getEndTime(),
						1));
			}
		}
		sql += " ) m left join t_client_extend n on m.id = n.clientId group by n.source";
		return executeNativeQuery(sql, args.toArray());
	}

	public int queryInviteCount(String initiativeinvite) {
		ArrayList args = new ArrayList();
		String sql = "SELECT COUNT(id) from t_client_extend where initiativeinvite = ?";
		args.add(initiativeinvite);
		Object obj = findUniqueResultByNativeQuery(sql, args);
		if (obj != null) {
			return Integer.parseInt(obj.toString());
		}
		return -1;
	}

	public void updateInviteCount(Integer cid, String initiativeinvite) {
		String sql = "update t_client_extend set initiativeinvite = ? where clientId = ?";
		List args = new ArrayList();
		args.add(initiativeinvite);
		args.add(cid);
		updateBySql(sql, args.toArray());
	}

	public int updateScoreAndCoins(int score, int category, Integer clientId) {
		String sql = "";

		if (ScoreConstant.SCORE_CATEGORY_SCORE == category) {
			sql = "update t_client_extend set totalScore = (totalScore + ?) , remainScore = (remainScore + ?) where clientId = ?";
		} else {
			sql = "update t_client_extend set totalCoins = (totalCoins + ?) , remainCoins = (remainCoins + ?) where clientId = ?";
		}

		return this.updateBySql(sql, new Object[] { score, score, clientId });
	}

	public int markClientFlag(Integer clientId, int flag) {
		String sql = "update t_client_extend set flag = ? where clientId = ? and flag != ?";
		return this.updateBySql(sql, new Object[] { flag, clientId, flag });
	}

	public int updateScoreInfo(ClientExtend clientExtend) {
		if (clientExtend != null) {
			String sql = "update t_client_extend set reportScore = ?, dietScore = ?, doctorScore = ?, sportScore = ? where clientId = ?";
			List args = new ArrayList();
			args.add(clientExtend.getReportScore());
			args.add(clientExtend.getDietScore());
			args.add(clientExtend.getDoctorScore());
			args.add(clientExtend.getSportScore());
			args.add(clientExtend.getClientId());
			return updateBySql(sql, args.toArray());
		}
		return 0;
	}

	public List<ClientRegEval> queryUploadMonitoringBack(Integer cid,
			Date beginDate, Date endDate, Date beginTime, Date endTime) {
		String sql = "select m.* from ( select 1 as type,COUNT(*) count from ntg_timeline_meals where clientId = ?"
				+ " and ((zaocanConsumed is NOT NULL ) or (lunchConsumed is NOT NULL ) "
				+ " or (lunchConsumed is NOT NULL ) "
				+ " or (lunchJiaConsumed is NOT NULL )"
				+ " or (dinnerConsumed is NOT NULL ))"
				+ " and createTime >= ? and createTime <= ?"
				+ " UNION "
				+ " select 2 as type, COUNT(*) count from ntg_sleep where clientId = ?"
				+ " and uploadTime >= ? and uploadTime <= ?"
				+ " UNION "
				+ " select 3 as type, COUNT(*) count from m_blood_sugar where clientId = ?"
				+ " and testDateTime >= ? and testDateTime <= ?"
				+ " UNION "
				+ " select 4 as type, totalScore count from t_client_extend where clientId = ?"
				+ ") m";

		List args = new ArrayList();
		args.add(cid);
		args.add(beginDate);
		args.add(endDate);
		args.add(cid);
		args.add(beginDate);
		args.add(endDate);
		args.add(cid);
		args.add(beginTime);
		args.add(endTime);
		args.add(cid);

		Map scalars = new LinkedHashMap();
		scalars.put("type", StandardBasicTypes.INTEGER);
		scalars.put("count", StandardBasicTypes.INTEGER);
		return executeNativeQuery(sql, null, scalars, args.toArray(),
				ClientRegEval.class);
	}

	public int updateClientBalance(double balance, Integer clientId, int type) {
		String sql = "";
		if (NTgBalanceRecord.TYPE_OF_BALANCE_RECHARGE == type) {
			sql = "update t_client_extend set balance = (balance + ?) where clientId = ?";
		} else if (NTgBalanceRecord.TYPE_OF_BALANCE_EXPENSE == type) {
			sql = "update t_client_extend set balance = (balance - ?) where clientId = ?";
		}
		return this.updateBySql(sql, new Object[] { balance, clientId });
	}
	
	public List<ClientExtend> queryClientExtend(){
		String hql = "from ClientExtend";
		return executeFind(hql);
	}
	
	public PageObject<ClientExtendExtend> queryClientExtend(QueryCondition qc, QueryInfo queryInfo){
		List args = new ArrayList();
		String sql = "select n.*, m.name, m.mobile,m.areaId from t_clientinfo m LEFT JOIN t_client_extend n on m.id = n.clientId" +
				" where m.status = ?";
		args.add(ClientInfo.STATUS_NORMAL);
		if(qc != null){
			String areaChain = qc.getAreaChain();
			if (!StringUtils.isEmpty(areaChain)) {
				String[] areaChains = areaChain.split("#");
				sql += " and (";
				if (areaChains.length > 1) {
					for (int i = 0; i < areaChains.length; i++) {
						sql += " m.areaChain like ?";
	
						args.add(areaChains[i] + "%");
	
						if (i != areaChains.length - 1) {
							sql += " or";
						}
					}
				} else {
					sql += " m.areaChain like ?";
					args.add(areaChains[0] + "%");
				}
				sql += ")";
			}
			
			if(!StringUtils.isEmpty(qc.getClientName())){
				sql += " and m.name like ?";
				args.add("%"+qc.getClientName().trim()+"%");
			}
			
			if(!StringUtils.isEmpty(qc.getMobile())){
				sql += " and m.mobile like ?";
				args.add("%"+qc.getMobile().trim()+"%");
			}
			sql += " order by m.id";
		}
		
		return queryObjectsBySql(sql, null, null, args.toArray(), queryInfo, ClientExtendExtend.class);
	}
	
	
	public void updateClientExtend(int type){
		String sql = "";
		if(type == 1){
			sql = "update t_client_extend set reportScore = (IF(reportScore >= 5, reportScore-5, 0))";
		}else if(type == 2){
			sql = "update t_client_extend set dietScore = (IF(dietScore >= 5, dietScore-5, 0))";
		}else if(type == 3){
			sql = "update t_client_extend set sportScore = (IF(sportScore >= 5, sportScore-5, 0))";
		}
		updateBySql(sql);
	}

}
