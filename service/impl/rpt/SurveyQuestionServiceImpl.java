package com.bskcare.ch.service.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.bo.SurveyQuestionAnswerExtend;
import com.bskcare.ch.bo.SurveyQuestionExtend;
import com.bskcare.ch.dao.rpt.AnswerTipDao;
import com.bskcare.ch.dao.rpt.SurveyAnswerDao;
import com.bskcare.ch.dao.rpt.SurveyDao;
import com.bskcare.ch.dao.rpt.SurveyQuestionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.SurveyQuestionService;
import com.bskcare.ch.vo.rpt.AnswerTip;
import com.bskcare.ch.vo.rpt.Survey;
import com.bskcare.ch.vo.rpt.SurveyAnswer;
import com.bskcare.ch.vo.rpt.SurveyQuestion;

@Service
@SuppressWarnings("unchecked")
public class SurveyQuestionServiceImpl implements SurveyQuestionService {

	@Autowired
	SurveyQuestionDao surveyQuestionDao;
	@Autowired
	AnswerTipDao answerTipDao;
	@Autowired
	SurveyDao surveyDao;
	@Autowired
	SurveyAnswerDao surveyAnswerDao;

	public List<SurveyQuestionAnswerExtend> listBySid(Integer sid) {
		return surveyQuestionDao.listBySid(sid);
	}

	public String saveAnswerTipMsg() {
		Integer sid = 0;
		StringBuffer answerTipStr = new StringBuffer();
		List<Survey> list = surveyDao.querySurveylist(null);
		if (null != list && list.size() > 0) {
			// 取出最大问卷编号
			sid = list.get(0).getId();
			// 根据问卷编号找到对应的答案编号集合
			List<SurveyAnswer> listAid = surveyAnswerDao.queryAid(sid);
			if (null != listAid && listAid.size() > 0) {
				for (SurveyAnswer surveyAnswer : listAid) {
					// 根据答案编号查找相对应的答案提示集合,并随机抽取一个
					List<AnswerTip> tiplist = answerTipDao.queryContent(surveyAnswer.getAid());
					if (null != tiplist && tiplist.size() > 0) {
						answerTipStr.append(tiplist.get(0).getContent());
					} 
				}
			} else {
				answerTipStr.append("");
			}
		} else {
			answerTipStr.append("");
		}
		return answerTipStr.toString();
	}

	public SurveyQuestion save(SurveyQuestion surveyQuestion) {
		return surveyQuestionDao.add(surveyQuestion);
	}

	public PageObject<SurveyQuestionExtend> queryList(QueryInfo queryInfo) {
		return surveyQuestionDao.queryList(queryInfo);
	}

	public void deleteById(Integer sid) {
		ArrayList args = new ArrayList();
		String sql = "delete from `rpt_survey_question` where sid=?";
		args.add(sid);
		surveyQuestionDao.deleteBySql(sql, args.toArray());
	}

	public List<SurveyQuestionExtend> queryListBySid(Integer sid) {
		return surveyQuestionDao.queryListBySid(sid);
	}
}
