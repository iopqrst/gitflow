package com.bskcare.ch.dao.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.HealthReportDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.HealthReport;

@Repository
@SuppressWarnings("unchecked")
public class HealthReportDaoImpl extends BaseDaoImpl<HealthReport> implements
		HealthReportDao {

	public PageObject findHealthReport(HealthReport healthReport,
			QueryInfo queryInfo) {

		ArrayList args = new ArrayList();
		String hql = "from HealthReport where status = ?";
		args.add(HealthReport.HEALTHREPORTNORMAL);
		if(healthReport != null){
			if(healthReport.getUserId() != null){
				hql+=" and userId = ?";
				args.add(healthReport.getUserId());
			}
			if(healthReport.getClientId() != null){
				hql+=" and clientId = ?";
				args.add(healthReport.getClientId());
			}
			hql+=" order by createTime desc";
		}
		return queryPagerObjects(hql, args.toArray(), queryInfo);
	}
	
	/**
	 * 根据用户id和健康报告id删除上传的健康报告
	 */
	public void deleteHealthReport(Integer id){
		 String hql="update HealthReport set status = ? where id = ?";
		 Object[] obj= {HealthReport.HEALTHREPORTNOTNORMAL, id};
		 updateByHql(hql, obj);
	}
	
}
