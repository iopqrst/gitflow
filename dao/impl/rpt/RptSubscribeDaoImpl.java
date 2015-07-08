package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.RptSubscribeDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptSubscribe;

@Repository
@SuppressWarnings("unchecked")
public class RptSubscribeDaoImpl extends BaseDaoImpl<RptSubscribe> implements
		RptSubscribeDao {

	public List<RptSubscribe> queryClientsByTime(String genTime) {
		String hql = "select s from RptSubscribe s where subscribeTime = ? and s.flag = ? order by id";
		return executeFind(hql, new Object[] { genTime,
				RptSubscribe.FLAG_UNGENERATE });
	}

	public PageObject querySubcribeReport(RptSubscribe sub,QueryInfo queryInfo) {
		List args = new ArrayList();
		String sql = "select rs.id,tc.name tcName,rs.subscribeTime,rs.generateTime,rs.flag,tc.mobile from rpt_subscribe rs LEFT JOIN t_clientinfo tc on rs.clientId=tc.id  where 1=1 ";
		if (null != sub) {
			if (-1 != sub.getFlag()) {
				sql += " and rs.flag = ?";
				args.add(sub.getFlag());
			}
		}
		sql += " and tc.mobile is not null";
		return queryObjectsBySql(sql, args.toArray(), queryInfo);
	}


}
