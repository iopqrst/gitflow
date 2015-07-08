package com.bskcare.ch.service.score;

/**
 * 用户签到
 */
public interface SignInService {
	
	public String signIn(Integer clientId);

	/**
	 * 今日是否上传
	 * @param cid
	 * @return 返回是否签到 ： （sign-true->已签到，sign-false->没有签到)
	 */
	public String hasSigned(Integer cid);
}
