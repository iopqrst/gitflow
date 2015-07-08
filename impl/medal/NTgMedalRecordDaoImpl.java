package com.bskcare.ch.dao.impl.medal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.NTgMedalRecordExtend;
import com.bskcare.ch.constant.ScoreConstant;
import com.bskcare.ch.dao.medal.NTgMedalRecordDao;
import com.bskcare.ch.vo.medal.NTgMedalRecord;
import com.bskcare.ch.vo.medal.NTgMedalRule;
import com.lowagie.tools.handout_pdf;

@Repository
@SuppressWarnings("unchecked")
public class NTgMedalRecordDaoImpl extends BaseDaoImpl<NTgMedalRecord> implements NTgMedalRecordDao{
	
	public int queryScoresByMedal(NTgMedalRule medal, Integer clientId, Date beginDate, Date endDate){
		
		StringBuffer sql = new StringBuffer("select sum(score) from ntg_medal_record where 1 = 1");
		ArrayList args = new ArrayList();
		
		if(null != clientId) {
			sql.append(" and clientId = ?");
			args.add(clientId);
		}
		
		if(null != medal) {
			if(null != medal.getId()) {
				sql.append(" and ruleId = ?");
				args.add(medal.getId());
			}
			//首次
			if(ScoreConstant.MODULE_TYPE_FIRST == medal.getType()) {}
			//每日首次
			if(ScoreConstant.MODULE_TYPE_EVERY_DAY_FIRST == medal.getType()) {
				sql.append(" and createTime >= ? and createTime < ?");
				args.add(beginDate);
				args.add(endDate);
			}
			//每次 (其实如果没有限制，这种类型就能随便加)
			if(ScoreConstant.MODULE_TYPE_EVERYTIME == medal.getType()) {
				if(null != medal.getLimit()) {
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
	
	public List<NTgMedalRecord> queryClientMedalRecord(Integer clientId, Integer medalId){
		List args = new ArrayList();
		String hql = " from NTgMedalRecord where 1 = 1";
		if(clientId != null){
			hql += " and clientId = ?";
			args.add(clientId);
		}
		if(medalId != null){
			hql += " and medalId = ?";
			args.add(medalId);
		}
		return executeFind(hql, args.toArray());
	}
	
	public List<NTgMedalRecordExtend> queryClientMedalScore(Integer clientId, Integer medalId){
		List args = new ArrayList();
		String sql = "select SUM(score) sumScore, clientId from ntg_medal_record where 1 = 1" ;
		if(clientId != null){
			sql += " and clientId = ?";
			args.add(clientId);
		}
		if(medalId != null){
			sql += " and medalId = ?";
			args.add(medalId);
		}
		sql += " GROUP BY medalId, clientId  ORDER BY SUM(score) DESC";
		
		Map scalars = new LinkedHashMap();
		scalars.put("sumScore", StandardBasicTypes.INTEGER);
		scalars.put("clientId", StandardBasicTypes.INTEGER);
		
		return executeNativeQuery(sql, null, scalars, args.toArray(), NTgMedalRecordExtend.class);
	}
}
