package com.bskcare.ch.dao.tg;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.tg.TgRptBaseInfo;

@SuppressWarnings("unchecked")
public interface TgRptBaseInfoDao extends BaseDao<TgRptBaseInfo>{
	
	public TgRptBaseInfo queryTgRptBaseInfo(TgRptBaseInfo tgRpt);
	
	public PageObject queryTgRpt(TgRptBaseInfo tgRpt, Integer softType, QueryInfo queryInfo);
	
	public int queryTgRptCount(Integer clientId);
}
