package com.bskcare.ch.dao.impl.timeLine;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.timeLine.EvaluatingResultDao;
import com.bskcare.ch.vo.timeLine.EvaluatingResult;

@SuppressWarnings("unchecked")
@Repository
public class EvaluatingResultDaoImpl extends BaseDaoImpl<EvaluatingResult>
		implements EvaluatingResultDao {

	public List<EvaluatingResult> getList() {
		// String hql =
		// "from EvaluatingResult GROUP BY clientId having max(testDate)";
		String sql = "SELECT * FROM (SELECT * from tg_evaluating_result ORDER BY testDate DESC) t1 GROUP BY t1.clientId HAVING MAX(t1.testDate)";
		return this.executeNativeQuery(sql, null, EvaluatingResult.class);
	}

	/**
	 * 条件查询
	 */
	public List<EvaluatingResult> queryResultsByClientId(EvaluatingResult er) {
		List args = new ArrayList();
		String sql = "select * from tg_evaluating_result where 1=1 ";
		if (null != er) {
			if (null != er.getClientId()) {
				sql += " and clientId=? ";
				args.add(er.getClientId());
			}
			if (-1 != er.getSoftType()) {
				sql += " and softType=? ";
				args.add(er.getSoftType());
			}
		}
		sql += " ORDER BY testDate DESC ";
		return executeNativeQuery(sql, args.toArray(),EvaluatingResult.class);
	}

	
	public EvaluatingResult queryLastEval(EvaluatingResult er){
		List args = new ArrayList();
		String hql = "from EvaluatingResult where 1=1 ";
		if (null != er) {
			if (null != er.getClientId()) {
				hql += " and clientId=? ";
				args.add(er.getClientId());
			}
			if (-1 != er.getSoftType()) {
				hql += " and softType=? ";
				args.add(er.getSoftType());
			}
		}
		hql += " order by testDate desc limit 1";
		
		List<EvaluatingResult> list = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	
	public List<EvaluatingResult> queryEvalNoCreateRpt(){
		String sql = "select * from tg_evaluating_result where id NOT IN(select evalId from tg_rpt_baseinfo)";
		//String sql = "select * from tg_evaluating_result where id NOT IN(select evalId from tg_rpt_baseinfo) LIMIT 10";
		return executeNativeQuery(sql, null, EvaluatingResult.class);
	}
}
