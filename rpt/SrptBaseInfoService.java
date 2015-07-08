package com.bskcare.ch.service.rpt;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptAuditRecord;
import com.bskcare.ch.vo.rpt.SrptBaseInfo;

@SuppressWarnings("unchecked")
public interface SrptBaseInfoService {

	/** 生成完整健康报告 **/
	public SrptBaseInfo createRptFullBaseInfo(Integer clientId,int type);

	/**
	 * 根据条件分页查询
	 */
	public PageObject queryAll(String areaChain,SrptBaseInfo srptBaseInfo,
			QueryInfo queryInfo);

	/**
	 * 逻辑删除
	 */
	public void updateStatus(Integer id);
	
	public SrptBaseInfo querySrptBaseInfo(Integer id);
	
	public void auditSrptBaseInfo(Integer id,int status,RptAuditRecord audit);
	
	/****/
	public SrptBaseInfo queryLatestSrpt(Integer clientId);
	/**
	 * 更新报告
	 */
	public void updateSrptBaseInfo(SrptBaseInfo baseInfo);
}
