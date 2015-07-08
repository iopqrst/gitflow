package com.bskcare.ch.service.impl.score;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.constant.ScoreConstant;
import com.bskcare.ch.dao.score.SignInDao;
import com.bskcare.ch.service.score.ScoreRecordService;
import com.bskcare.ch.service.score.SignInService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.vo.score.SignIn;

/**
 * 用户签到
 * @author hzq
 *
 */
@Service
public class SignInServiceImpl implements SignInService {
	
	private static Logger log = Logger.getLogger(SignInServiceImpl.class);

	@Autowired
	SignInDao signInDao;
	@Autowired
	ScoreRecordService scoreRecordService;
	
	public String signIn(Integer clientId) {
		// 1. 查询今日是否上传数据，上传后
		JSONObject jo = new JSONObject();
		jo.accumulate("data", "");
		
		if(signInDao.queryTodayRecord(clientId) > 0) {
			log.info(LogFormat.f("今日已经签到，不能在进行签到了..."));
			jo.accumulate("code", Constant.INTERFACE_FAIL);
			jo.accumulate("msg", "今天已经签到了，明天再来吧");
			return null;
		}
		
		SignIn signIn = signInDao.queryLatestRecord(clientId);
		
		int keepCount = 0;
		int score = 0;
		if(null != signIn) {
			score = calSignInScore(signIn);
			
			if(score == 1) { //如果分值为1说明签到中间有断层，需要重新开始，此时keepCount也要重新开始
				keepCount = 1;
			} else {
				keepCount = signIn.getKeepCount() + 1;
			}
			log.info(LogFormat.f("score = " + score + ", keepCount = " + keepCount));
			jo.accumulate("data", "{\"score\":"+ score +",\"coin\":0}");
		} else {
			score = 1;
			keepCount = 1; //首次签到肯定为1了，啦啦...
			//首次上传 金币1
			scoreRecordService.saveToDB(ScoreConstant.MODULE_SIGN_IN, clientId, score, 
					ScoreConstant.SCORE_CATEGORY_COIN);
			log.info(LogFormat.f("首次签到赠送金币一枚，积分1个"));
			jo.accumulate("data", "{\"score\":"+ score +",\"coin\":1}");
		}

		// 首次上传，积分 1分，其他需要计算
		scoreRecordService.saveToDB(ScoreConstant.MODULE_SIGN_IN, clientId, score, 
				ScoreConstant.SCORE_CATEGORY_SCORE);
		
		SignIn newRecord = new SignIn();
		newRecord.setClientId(clientId);
		newRecord.setCreateTime(new Date());
		newRecord.setKeepCount(keepCount);
		signInDao.add(newRecord);

		jo.accumulate("code", Constant.INTERFACE_SUCC);
		jo.accumulate("msg", "签到成功");
		
		return jo.toString();
	}
	
	public String hasSigned(Integer clientId) {
		int count = signInDao.queryTodayRecord(clientId);
		JSONObject jo = new JSONObject();
		jo.accumulate("code", Constant.INTERFACE_SUCC);
		jo.accumulate("data", "{\"sign\":"+ (count > 0 ? true : false) +"}");
		jo.accumulate("msg", "");
		return jo.toString();
	}
	
	/**
	 * 计算分值
	 * 	首次签到		1（积分）	1（金币）
	 *	连续二次签到	2	
	 *	连续三次签到	3	
	 *	连续四次签到	4	
	 *	连续五次签到	5	
	 *	连续六次签到	5
	 * @param signIn
	 * @return 最后计算的分值
	 */
	private static int calSignInScore(SignIn signIn) {
		int score = 0;
		int keepCount = signIn.getKeepCount();
		//计算分值
		Date yesterday = DateUtils.getBeforeDay(new Date(), -1);
		
		Date start = DateUtils.getCurrentDayStartTime(yesterday);
		Date end = DateUtils.getCurrentDayEndTime(yesterday);
		// 如果最后一次签到时间是昨天
		if(start.before(signIn.getCreateTime()) 
				&& end.after(signIn.getCreateTime())) {
			//昨天上传了，今天 keepCount 肯定>1
			if(keepCount < 5) {
				score = keepCount + 1;
			} else { //>=5
				score = 5;
			}
			log.info(LogFormat.f("最后一次签到的时间是昨天，score = " + score) + ", keepCount 继续叠加");
		} else {
			score = 1; //如果签到不是连续 需要从新开始
			log.info("签到断层，需要重新开始... ");
		}
		return score;
	}

	

}
