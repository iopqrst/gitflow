package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.AnswerTipDao;
import com.bskcare.ch.vo.rpt.AnswerTip;
@Repository
@SuppressWarnings("unchecked")
public class AnswerTipDaoImpl extends BaseDaoImpl<AnswerTip> implements AnswerTipDao{

	public List<AnswerTip> queryContent(Integer aid) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append(" SELECT * FROM rpt_answer_tip a ");
		sql.append(" WHERE a.aid=? ");
		args.add(aid);
		sql.append(" ORDER BY RAND() LIMIT 1 ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), AnswerTip.class);
	}

}
