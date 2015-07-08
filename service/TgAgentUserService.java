package com.bskcare.ch.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.TgAgentUser;

public interface TgAgentUserService {

	/**
	 *  增加
	 */
	public TgAgentUser add(TgAgentUser TgAgentUser);
	/**
	 * 查询新增推广来源用户集合
	 */
	public List<TgAgentUser> queryAgentUserByTime(Date createTime);
	/**
	 * 推广来源中代理商区域所属用户注册和 社会人员注册
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public void tgAgentUserRegister(Integer minAreaId,String agent,String url,String category,ClientInfo vClient) throws NumberFormatException, IOException;
}
