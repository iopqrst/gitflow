package com.bskcare.ch.service.tg;

import com.bskcare.ch.query.util.QueryInfo;


public interface TgClientinfoService {

	/** 查询个人信息 **/
	public String personalInfomation(Integer cid,QueryInfo queryInfo);
}
