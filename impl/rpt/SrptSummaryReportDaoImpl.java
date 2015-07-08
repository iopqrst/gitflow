package com.bskcare.ch.dao.impl.rpt;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.SrptSummaryReportDao;
import com.bskcare.ch.vo.rpt.SrptSummaryReport;

@Repository
public class SrptSummaryReportDaoImpl extends BaseDaoImpl<SrptSummaryReport> implements
		SrptSummaryReportDao {
	
		public SrptSummaryReport querySSRBySrptId(Integer srptId){
			String hql = "from SrptSummaryReport where srptId = ?";
			List<SrptSummaryReport> lst = executeFind(hql, srptId);
			SrptSummaryReport ssr = null;
			if(!CollectionUtils.isEmpty(lst)){
				ssr = lst.get(0);
			}
			return ssr;
		}
		
		public int updateReportProperty(String content,String field,Integer dietId) {
			String sql = " update srpt_summary_report set "+ field +"=? where srptId=?";
			return this.updateBySql(sql, new Object[]{content,dietId});
		}
}
