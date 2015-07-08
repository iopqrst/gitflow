package com.bskcare.ch.service.impl.score;

import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.bo.ScoreRecordExtend;
import com.bskcare.ch.constant.ScoreConstant;
import com.bskcare.ch.dao.ClientExtendDao;
import com.bskcare.ch.dao.score.ScoreModuleDao;
import com.bskcare.ch.dao.score.ScoreRecordDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.score.ScoreRecordService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientExtend;
import com.bskcare.ch.vo.score.ScoreModule;
import com.bskcare.ch.vo.score.ScoreRecord;

@Service
@SuppressWarnings("unchecked")
public class ScoreRecordServiceImpl implements ScoreRecordService {

	private static Logger log = Logger.getLogger(ScoreRecordServiceImpl.class);

	@Autowired
	ScoreModuleDao scoreModuleDao;
	@Autowired
	ScoreRecordDao scoreRecordDao;
	@Autowired
	ClientExtendDao clientExtendDao;

	public String addPointsAndCoins(Integer moduleId, Integer clientId) {

		// 1、获取得到对应的module，然后根据值做出判断
		ScoreModule module = scoreModuleDao.load(moduleId);

		boolean needAdd = false;
		if (null != module) {

			Date date = new Date();
			Date beginDate = DateUtils.getDateByType(date, "yyyy-MM-dd");
			Date endDate = DateUtils.getAppointDate(beginDate, 1);

			log.info(module.toString());

			int score = scoreRecordDao.queryScoresByModule(module, clientId,
					beginDate, endDate);

			// 首次添加或者每天第一次添加
			if ((ScoreConstant.MODULE_TYPE_FIRST == module.getType() || ScoreConstant.MODULE_TYPE_EVERY_DAY_FIRST == module
					.getType())
					&& 0 == score) {
				// TODO 添加积分或者金币
				needAdd = true;
				log.info("首次添加或者每天第一次添加");
			}

			// 每次都添加
			if (ScoreConstant.MODULE_TYPE_EVERYTIME == module.getType()) {
				if (null == module.getLimit()
						|| (null != module.getLimit() && module.getLimit() > score)) {
					// TODO 添加积分或者金币
					needAdd = true;
					log.info("每次都添加，或者有限制且没有超过限制的分值：score = " + score
							+ ",limit = " + module.getLimit());
				}
			}

			if (ScoreConstant.MODULE_TYPE_SELFDEFINE == module.getType()) {
				System.out.println("调错方法了...... ");
			}

			if (needAdd) {
				saveToDB(moduleId, clientId, module.getScore(), module
						.getCategory());

				return "success";
			}
		}

		return null;
	}

	public int saveToDB(Integer moduleId, Integer clientId, int score,
			int category) {

		if (null == clientId || 0 == score || 0 == category) {
			log.error("参数条件不全...");
			return 0;
		}

		// 添加一条记录
		ScoreRecord record = new ScoreRecord();
		record.setClientId(clientId);
		record.setCategory(category);
		record.setCreateTime(new Date());
		record.setModuleId(moduleId);
		record.setType(ScoreConstant.TYPE_OF_RECORD_INCOME);
		record.setScore(score);

		scoreRecordDao.add(record);
		// 重新计算总数
		int count = clientExtendDao.updateScoreAndCoins(score, category,
				clientId);

		return count;
	}

	public void addCoins(Integer cid, Integer moduleId, String score,
			String coin) {
		if ((cid != null && moduleId != null) && (!StringUtils.isEmpty(score))
				|| !StringUtils.isEmpty(coin)) {
			if (!StringUtils.isEmpty(score)) {
				int sc = Integer.parseInt(score);
				saveToDB(moduleId, cid, sc, ScoreConstant.SCORE_CATEGORY_SCORE);
			}
			if (!StringUtils.isEmpty(coin)) {
				int co = Integer.parseInt(coin);
				saveToDB(moduleId, cid, co, ScoreConstant.SCORE_CATEGORY_COIN);
			}
		}
	}

	public String addScoreCoin(Integer moduleId, Integer coinIdModuleId,
			Integer cid) {
		JSONObject jo = new JSONObject();
		if (cid != null) {
			if (moduleId != null) {
				addPointsAndCoins(moduleId, cid);
			}
			if (coinIdModuleId != null) {
				addPointsAndCoins(coinIdModuleId, cid);
			}
			jo.put("code", 1);
			jo.put("msg", "添加成功");
		} else {
			jo.put("code", 0);
			jo.put("msg", "参数有误");
		}
		return jo.toString();
	}

	public String addExpenseScore(Integer cid, int money) {
		JSONObject jo = new JSONObject();
		if (cid != null) {
			if (money != 0) {
				int score = money * 5;
				saveToDB(ScoreConstant.MODULE_EXPENSE, cid, score,
						ScoreConstant.SCORE_CATEGORY_SCORE);
			}
			jo.put("code", 1);
			jo.put("msg", "添加成功");
		} else {
			jo.put("code", 0);
			jo.put("msg", "参数有误");
		}
		return jo.toString();
	}

	/**
	 * 返回数据格式
	 * 
	 * <pre>
	 * {
	 *  	"code":1,    
	 *  	"msg":"请求成功",    
	 *  	"data": {
	 *  		"integral": 0,// 总积分       
	 *  		"totalPage":10，   // 总页数        
	 *  		"currentPage": 10， // 当前页数  （所有分页默认每页10条数据）        
	 *  		"detail": [
	 *  			{              
	 *  				"changeValue":  100,
	 *  				"from" : "积分来源 ",              
	 *  				"createTime" : "yyyy-MM-dd",              
	 *  				"changeType": 1       //增加 1, 还是 减少 0"            
	 *  			}, {             
	 *  				"changeValue":  100,              
	 *  				"from" : "积分来源 ",             
	 *  			 	"createTime" : "yyyy-MM-dd",              
	 *  				"changeType": 1       //增加 1, 还是 减少 0"            
	 *  			}
	 *  		]    
	 *  	}
	 * }
	 * </pre>
	 */
	public String queryClientScoreRecord(Integer clientId, QueryInfo queryInfo,
			Integer pagerNo) {

		if (clientId != null && queryInfo != null) {
			// 查询客户总积分信息
			ClientExtend cx = new ClientExtend();
			cx.setClientId(clientId);
			ClientExtend ce = clientExtendDao.queryLastTimeByClientId(cx);
			JSONObject jo = new JSONObject();
			jo.put("integral", ce != null ? ce.getTotalScore() : 0);

			PageObject<ScoreRecordExtend> pager = scoreRecordDao
					.queryClientScoreRecord(clientId,
							ScoreRecord.CATEGORY_SCORE, queryInfo);

			jo.put("totalPage", pager.getTotalPage());
			jo.put("currentPage", pagerNo);

			List<ScoreRecordExtend> lstScore = pager.getDatas();
			if (!CollectionUtils.isEmpty(lstScore)) {
				JSONArray ja = new JSONArray();
				for (ScoreRecordExtend score : lstScore) {
					JSONObject json = new JSONObject();
					json.put("changeValue", score.getScore());
					json.put("from", score.getModule());
					json.put("createTime", DateUtils.formatDate("yyyy-MM-dd",
							score.getCreateTime()));
					json.put("changeType", score.getType());

					ja.add(json.toString());
				}

				jo.put("detail", ja.toArray());
			} else {
				jo.put("detail", "");
			}
			return JsonUtils.encapsulationJSON(1, "查询成功", jo.toString())
					.toString();
		}

		return JsonUtils.encapsulationJSON(0, "参数有误", "").toString();
	}
	
	public PageObject queryClientScoreRecord(ScoreRecord scoreRecord, QueryInfo queryInfo, QueryCondition qc){
		return scoreRecordDao.queryClientScoreRecord(scoreRecord, queryInfo, qc);
	}
}
