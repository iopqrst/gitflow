package com.bskcare.ch.dao.impl.score;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.ScoreRecordExtend;
import com.bskcare.ch.constant.ScoreConstant;
import com.bskcare.ch.dao.score.ScoreRecordDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.score.ScoreModule;
import com.bskcare.ch.vo.score.ScoreRecord;

@Repository
@SuppressWarnings("unchecked")
public class ScoreRecordDaoImpl extends BaseDaoImpl<ScoreRecord> implements
		ScoreRecordDao {

	public int queryScoresByModule(ScoreModule sm, Integer clientId, Date beginDate, Date endDate) {
		StringBuffer sql = new StringBuffer("select sum(score) from s_score_record where type = ?");
		ArrayList args = new ArrayList();
		
		args.add(ScoreConstant.TYPE_OF_RECORD_INCOME);
		
		if(null != clientId) {
			sql.append(" and clientId = ?");
			args.add(clientId);
		}
		
		if(null != sm) {
			if(null != sm.getId()) {
				sql.append(" and moduleId = ?");
				args.add(sm.getId());
			}
			
			if(0 != sm.getCategory()) {
				sql.append(" and category = ?");
				args.add(sm.getCategory());
			}
			
			//首次
			if(ScoreConstant.MODULE_TYPE_FIRST == sm.getType()) {}
			//每日首次
			if(ScoreConstant.MODULE_TYPE_EVERY_DAY_FIRST == sm.getType()) {
				sql.append(" and createTime >= ? and createTime < ?");
				args.add(beginDate);
				args.add(endDate);
			}
			//每次 (其实如果没有限制，这种类型就能随便加)
			if(ScoreConstant.MODULE_TYPE_EVERYTIME == sm.getType()) {
				if(null != sm.getLimit()) {
					sql.append(" and createTime >= ? and createTime < ?");
					args.add(beginDate);
					args.add(endDate);
				}
			}
		}
		
		Object obj = this.findUniqueResultByNativeQuery(sql.toString(), args.toArray());
		if(obj != null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	
	public PageObject<ScoreRecordExtend> queryClientScoreRecord(Integer cid, int category, QueryInfo queryInfo){
		String sql = "select m.*, n.module from (select * from s_score_record where 1 = 1";
		List args = new ArrayList();
		if(cid != null){
			sql += " and clientId = ?";
			args.add(cid);
		}
		if(category != 0){
			sql += " and category = ?";
			args.add(category);
		}
		
		sql += ") m LEFT JOIN s_score_module n on m.moduleId = n.id ORDER BY m.createTime DESC";
		
		return queryObjectsBySql(sql, null, null, args.toArray(), queryInfo, ScoreRecordExtend.class);
	}
	
	public int queryScoreRecordCount(Integer moduleId, Date beginTime, Date endTime, Integer cid){
		String sql = "select count(*) from s_score_record where moduleId = ?";
		List args = new ArrayList();
		args.add(moduleId);
		if(cid != null){
			sql += " and clientId = ?";
			args.add(cid);
		}
		if(beginTime != null){
			sql += " and createTime >= ?";
			args.add(beginTime);
		}
		if(endTime != null){
			sql += " and createTime <= ?";
			args.add(endTime);
		}
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj != null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	
	public PageObject queryClientScoreRecord(ScoreRecord scoreRecord, QueryInfo queryInfo, QueryCondition qc){
		List args = new ArrayList();
		String sql = "select m.*, n.module from s_score_record m LEFT JOIN s_score_module n on m.moduleId = n.id" +
				" where 1 = 1";
		if(scoreRecord != null){
			if(scoreRecord.getClientId() != null){
				sql += " and clientId = ? ";
				args.add(scoreRecord.getClientId());
			}
			
			if(scoreRecord.getModuleId() != null){
				sql += " and moduleId = ?";
				args.add(scoreRecord.getModuleId());
			}
		}
		
		if(qc != null){
			if(qc.getBeginTime() != null){
				sql += " and createTime >= ?";
				args.add(qc.getBeginTime());
			}
			if(qc.getEndTime() != null){
				sql += " and createTime <= ?";
				args.add(qc.getEndTime());
			}
		}
	
		sql += " ORDER BY createTime DESC";
		
		Map entities = new LinkedHashMap();
		entities.put("m", ScoreRecord.class);
		
		Map scalars = new LinkedHashMap();
		scalars.put("module", StandardBasicTypes.STRING);
		
		return queryObjectsBySql(sql, entities, scalars, args.toArray(), queryInfo, null);
	}
}
