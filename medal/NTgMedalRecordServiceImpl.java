package com.bskcare.ch.service.impl.medal;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.NTgMedalRecordExtend;
import com.bskcare.ch.constant.ScoreConstant;
import com.bskcare.ch.dao.medal.NTgClientMedalDao;
import com.bskcare.ch.dao.medal.NTgMedalGradingDao;
import com.bskcare.ch.dao.medal.NTgMedalRecordDao;
import com.bskcare.ch.dao.medal.NTgMedalRuleDao;
import com.bskcare.ch.service.impl.score.ScoreRecordServiceImpl;
import com.bskcare.ch.service.medal.NTgMedalRecordService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.vo.medal.NTgClientMedal;
import com.bskcare.ch.vo.medal.NTgMedalGrading;
import com.bskcare.ch.vo.medal.NTgMedalModule;
import com.bskcare.ch.vo.medal.NTgMedalRecord;
import com.bskcare.ch.vo.medal.NTgMedalRule;
import com.bskcare.ch.vo.medal.NTgMedalTemp;

@Service
public class NTgMedalRecordServiceImpl implements NTgMedalRecordService {
	private static Logger log = Logger.getLogger(ScoreRecordServiceImpl.class);

	@Autowired
	private NTgMedalRuleDao medalRuleDao;
	@Autowired
	private NTgMedalRecordDao medalRecordDao;
	@Autowired
	private NTgClientMedalDao clientMedalDao;
	@Autowired
	private NTgMedalGradingDao medalGradingDao;

	public int addMedalRecord(Integer ruleId, Integer clientId) {

		NTgMedalRule medalRule = medalRuleDao.load(ruleId);
		boolean needAdd = false;
		if (null != medalRule) {
			Date date = new Date();
			Date beginDate = DateUtils.getDateByType(date, "yyyy-MM-dd");
			Date endDate = DateUtils.getAppointDate(beginDate, 1);

			int score = medalRecordDao.queryScoresByMedal(medalRule, clientId,
					beginDate, endDate);

			// 首次添加或者每天第一次添加
			if ((ScoreConstant.MODULE_TYPE_FIRST == medalRule.getType() || ScoreConstant.MODULE_TYPE_EVERY_DAY_FIRST == medalRule
					.getType())
					&& 0 == score) {
				needAdd = true;
				log.info("首次添加或者每天第一次添加");
			}

			// 每次都添加
			if (ScoreConstant.MODULE_TYPE_EVERYTIME == medalRule.getType()) {
				if (null == medalRule.getLimit()
						|| (null != medalRule.getLimit() && medalRule
								.getLimit() > score)) {
					needAdd = true;
					log.info("每次都添加，或者有限制且没有超过限制的分值：score = " + score
							+ ",limit = " + medalRule.getLimit());
				}
			}

			if (ScoreConstant.MODULE_TYPE_SELFDEFINE == medalRule.getType()) {
				System.out.println("调错方法了...... ");
			}

			if (needAdd) {
				saveToDB(medalRule, clientId, medalRule.getScore());
				return updateClientMedal(clientId, medalRule);
			}
		}
		return 0;
	}

	public void saveToDB(NTgMedalRule medalRule, Integer clientId, int score) {

		if (null == clientId || 0 == score) {
			log.error("参数条件不全...");
		}

		// 添加一条记录
		NTgMedalRecord record = new NTgMedalRecord();
		record.setClientId(clientId);
		record.setCreateTime(new Date());
		record.setRuleId(medalRule.getId());
		record.setMedalId(medalRule.getMedalId());
		record.setScore(score);
		medalRecordDao.add(record);
	}

	/**
	 * 
	 * @param clientId
	 * @param medalRule
	 * @return
	 */
	private int updateClientMedal(Integer clientId, NTgMedalRule medalRule) {
		
		System.out.println("------------------------------------");
		
		// 查询客户该类型勋章当前值
		NTgClientMedal clientMedal = clientMedalDao.queryClientMedal(clientId);

		int hardScore = medalRule.getScore();
		int validScore = medalRule.getScore();
		int heartNumber = medalRule.getScore();

		int isUpgrade = 0;
		NTgMedalTemp medalTemp = null;
		if ( null == clientMedal) {
			clientMedal = new NTgClientMedal();
			clientMedal.setClientId(clientId);
			clientMedal.setCreateTime(new Date());
			
			if (medalRule.getMedalId() == NTgMedalModule.MEDAL_HARD) {
				clientMedal.setHardScore(hardScore);
				// 根据分数，获取勋章等级
				int hardLevel = this.queryMedalLevel(medalRule.getMedalId(),
						hardScore);
				clientMedal.setHardLevel(hardLevel);
				isUpgrade = 1;
				
				medalTemp = new NTgMedalTemp(clientId, NTgMedalModule.MEDAL_HARD, hardScore, hardLevel);
			} else if (medalRule.getMedalId() == NTgMedalModule.MEDAL_VALID) {
				clientMedal.setValidScore(validScore);
				// 根据分数，获取勋章等级
				int validLevel = this.queryMedalLevel(medalRule.getMedalId(),
						validScore);
				clientMedal.setValidLevel(validLevel);
				isUpgrade = 2;
				
				medalTemp = new NTgMedalTemp(clientId, NTgMedalModule.MEDAL_VALID, validScore, validLevel);
			} else if (medalRule.getMedalId() == NTgMedalModule.MEDAL_HEART) {
				clientMedal.setHeartNumber(heartNumber);
				// 根据分数，获取勋章等级
				int heartLevel = this.queryMedalLevel(medalRule.getMedalId(),
						heartNumber);
				clientMedal.setHeartLevel(heartLevel);
				isUpgrade = 3;
				
				medalTemp = new NTgMedalTemp(clientId, NTgMedalModule.MEDAL_HEART, heartNumber, heartLevel);
			}

			//clientMedalDao.add(clientMedal);
			
			clientMedalDao.insertMedalTemp(medalTemp);
		} else {
			hardScore += clientMedal.getHardScore();
			validScore += clientMedal.getValidScore();
			heartNumber += clientMedal.getHeartNumber();

			//clientMedal.setCreateTime(new Date());
			if (medalRule.getMedalId() == NTgMedalModule.MEDAL_HARD) {
				clientMedal.setHardScore(hardScore);
				// 根据分数，获取勋章等级
				int hardLevel = this.queryMedalLevel(medalRule.getMedalId(),
						hardScore);
				if (hardLevel > clientMedal.getHardLevel()) {
					isUpgrade = 1;
				}

				clientMedal.setHardLevel(hardLevel);
				
				medalTemp = new NTgMedalTemp(clientId, NTgMedalModule.MEDAL_HARD, hardScore, hardLevel);
			} else if (medalRule.getMedalId() == NTgMedalModule.MEDAL_VALID) {
				clientMedal.setValidScore(validScore);
				// 根据分数，获取勋章等级
				int validLevel = this.queryMedalLevel(medalRule.getMedalId(),
						validScore);
				if (validLevel > clientMedal.getValidLevel()) {
					isUpgrade = 2;
				}

				clientMedal.setValidLevel(validLevel);
				
				medalTemp = new NTgMedalTemp(clientId, NTgMedalModule.MEDAL_VALID, validScore, validLevel);
				
			} else if (medalRule.getMedalId() == NTgMedalModule.MEDAL_HEART) {
				clientMedal.setHeartNumber(heartNumber);
				// 根据分数，获取勋章等级
				int heartLevel = this.queryMedalLevel(medalRule.getMedalId(),
						heartNumber);
				if (heartLevel > clientMedal.getHeartLevel())
					isUpgrade = 3;

				clientMedal.setHeartLevel(heartLevel);
				
				medalTemp = new NTgMedalTemp(clientId, NTgMedalModule.MEDAL_HEART, validScore, heartLevel);
			}

			//clientMedalDao.update(clientMedal);
			clientMedalDao.insertMedalTemp(medalTemp);
		}
		
		

		return isUpgrade;
	}
	
	public void batchUpdate(Integer clientId) {
		
		List<NTgMedalTemp> list = clientMedalDao.queryAllTempMedal(clientId);
		NTgClientMedal clientMedal = clientMedalDao.queryClientMedal(clientId);
		if(!CollectionUtils.isEmpty(list)) {
			
			NTgClientMedal newNtgClientMedal = null;
			
			if(null == clientMedal) {
				newNtgClientMedal = new NTgClientMedal();
				newNtgClientMedal.setCreateTime(new Date());
				newNtgClientMedal.setClientId(clientId);
			} else {
				newNtgClientMedal = clientMedal;
			}
			
			for (NTgMedalTemp nmt : list) {
				if (NTgMedalModule.MEDAL_HARD == nmt.getType() ) {
					newNtgClientMedal.setHardScore(nmt.getScore());
					newNtgClientMedal.setHardLevel(nmt.getLevel());
				} else if (NTgMedalModule.MEDAL_VALID == nmt.getType() ) {
					newNtgClientMedal.setValidScore(nmt.getScore());
					newNtgClientMedal.setValidLevel(nmt.getLevel());
				} else if ( NTgMedalModule.MEDAL_HEART == nmt.getType() ) {
					newNtgClientMedal.setHeartNumber(nmt.getScore());
					newNtgClientMedal.setHeartLevel(nmt.getLevel());
				}
			}
			
			if(null != newNtgClientMedal.getId()) {
				clientMedalDao.update(newNtgClientMedal);
			} else {
				clientMedalDao.add(newNtgClientMedal);
			}
		}
			
	}

	/**
	 * 根据当前类型勋章分值查询勋章等级
	 */
	public int queryMedalLevel(Integer medalId, int score) {
		List<NTgMedalGrading> lst = medalGradingDao.queryMedalGrading(medalId);
		if (!CollectionUtils.isEmpty(lst)) {
			for (int i = 0; i < lst.size(); i++) {
				if (score <= lst.get(i).getScore()) {
					return lst.get(i).getLevel();
				}
			}
		}
		return 0;
	}

	// 根据勋章类型查询返回的详细信息
	public String queryClientMedalDetail(Integer clientId, Integer medalId) {
		JSONObject jo = new JSONObject();
		List<NTgMedalRecord> lst = medalRecordDao.queryClientMedalRecord(
				clientId, medalId);
		if (!CollectionUtils.isEmpty(lst)) {
			NTgClientMedal clientMedal = clientMedalDao
					.queryClientMedal(clientId);
			if (medalId == NTgMedalModule.MEDAL_HARD) {
				NTgMedalGrading grading = medalGradingDao.queryMedalGrading(
						medalId, clientMedal.getHardLevel());
				jo.put("level", clientMedal.getHardLevel());
				jo.put("score", clientMedal.getHardScore());
				jo.put("gradingScore", grading.getScore());

				int bsNum = 0;
				int bpNum = 0;
				int dietNum = 0;
				int sportNum = 0;
				int sleepNum = 0;
				for (NTgMedalRecord record : lst) {
					if (record.getRuleId() >= 10 && record.getRuleId() <= 17) {
						bsNum += 1;
					} else if (record.getRuleId() == NTgMedalRule.RULE_UPLOAD_BLOODPRESSURE) {
						bpNum += 1;
					} else if (record.getRuleId() >= 3
							&& record.getRuleId() <= 5) {
						dietNum += 1;
					} else if (record.getRuleId() == NTgMedalRule.RULE_UPLOAD_SPORT) {
						sportNum += 1;
					} else if (record.getRuleId() == NTgMedalRule.RULE_UPLOAD_SLEEP) {
						sleepNum += 1;
					}
				}

				jo.put("bsNum", bsNum);
				jo.put("bpNum", bpNum);
				jo.put("dietNum", dietNum);
				jo.put("sportNum", sportNum);
				jo.put("sleepNum", sleepNum);

			} else if (medalId == NTgMedalModule.MEDAL_VALID) {
				NTgMedalGrading grading = medalGradingDao.queryMedalGrading(
						medalId, clientMedal.getValidLevel());
				jo.put("level", clientMedal.getValidLevel());
				jo.put("score", clientMedal.getValidScore());
				jo.put("gradingScore", grading.getScore());

				int bsNormal = 0;
				int bsLow = 0;
				int bsHigh = 0;
				for (NTgMedalRecord record : lst) {
					if (record.getRuleId() == NTgMedalRule.RULE_BLOODSUGAR_GOOD) {
						bsNormal += 1;
					} else if (record.getRuleId() == NTgMedalRule.RULE_BLOODSUGAR_HIGH) {
						bsHigh += 1;
					} else if (record.getRuleId() == NTgMedalRule.RULE_BLOODSUGAR_LOW) {
						bsLow += 1;
					}
				}

				jo.put("bsNormal", bsNormal);
				jo.put("bsLow", bsLow);
				jo.put("bsHigh", bsHigh);
			} else if (medalId == NTgMedalModule.MEDAL_HEART) {
				NTgMedalGrading grading = medalGradingDao.queryMedalGrading(
						medalId, clientMedal.getHeartLevel());
				jo.put("level", clientMedal.getHeartLevel());
				jo.put("score", clientMedal.getHeartNumber());
				jo.put("gradingScore", grading.getScore());

				int inviteClient = 0;
				for (NTgMedalRecord record : lst) {
					if (record.getRuleId() == NTgMedalRule.RULE_INVITE_CLIENT) {
						inviteClient += 1;
					}
				}
				jo.put("inviteClient", inviteClient * 10);
			}
			List<NTgMedalRecordExtend> lstRecord = medalRecordDao
					.queryClientMedalScore(clientId, medalId);
			if (!CollectionUtils.isEmpty(lstRecord)) {
				for (int i = 0; i < lstRecord.size(); i++) {
					if (lstRecord.get(i).getClientId() == clientId) {
						jo.put("sort", i + 1);
					}
				}
			}
		}
		return jo.toString();
	}
}
