package com.bskcare.ch.dao.impl.tg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgRptBaseInfoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.tg.TgRptBaseInfo;

@Repository
@SuppressWarnings("unchecked")
public class TgRptBaseInfoDaoImpl extends BaseDaoImpl<TgRptBaseInfo> implements TgRptBaseInfoDao{
	
	public TgRptBaseInfo queryTgRptBaseInfo(TgRptBaseInfo tgRpt){
		String hql = "from TgRptBaseInfo where 1=1";
		List args = new ArrayList();
		if(tgRpt != null){
			if(tgRpt.getEvalId() != null){
				hql += " and evalId = ?";
				args.add(tgRpt.getEvalId());
			}
			if(tgRpt.getClientId() != null){
				hql += " and clientId = ?";
				args.add(tgRpt.getClientId());
				hql += " order by createTime desc";
			}
		}
		List<TgRptBaseInfo> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		
		return null;
	}
	
	
	public PageObject queryTgRpt(TgRptBaseInfo tgRpt, Integer softType, QueryInfo queryInfo){
		List args = new ArrayList();		
		String sql = "select m.* from (select * from tg_rpt_baseinfo where 1 = 1";
		
		if(tgRpt != null){
			if(tgRpt.getClientId() != null){
				sql += " and clientId = ?";
				args.add(tgRpt.getClientId());
			}
		}
		
		sql += ") m left join (select * from tg_evaluating_result where 1 = 1";
		
		if(softType != null){
			sql += " and softType = ?";
			args.add(softType);
		}
		sql += ") n on m.evalId = n.id order by m.number desc";
		
		return queryObjectsBySql(sql, null, null, args.toArray(), queryInfo, TgRptBaseInfo.class);
	}
	
	public int queryTgRptCount(Integer clientId){
		List args = new ArrayList();
		String sql = "select count(*) from tg_rpt_baseinfo where clientId = ?";
		args.add(clientId);
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		
		if(obj != null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
}
