package com.bskcare.ch.dao.rpt;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SrptBaseInfo;

@SuppressWarnings("unchecked")
public interface SrptBaseInfoDao extends BaseDao<SrptBaseInfo> {
	/**
	 * 根据条件分页查询
	 */
	public PageObject queryAll(String areaChain,SrptBaseInfo srptBaseInfo,
			QueryInfo queryInfo);
	/**
	 * 逻辑删除
	 */
	public void updateStatus(Integer id);
	
	public int auditSrptBaseInfo(Integer id,int status);
	
	public SrptBaseInfo queryLatestSrpt(Integer clientId);
	/**
	 * 更新报告
	 */
	public void updateSrptBaseInfo(SrptBaseInfo baseInfo);
}
