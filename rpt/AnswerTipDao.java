package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.AnswerTip;

public interface AnswerTipDao extends BaseDao<AnswerTip>{

	/**
	 * 根据答案编号查找对应的答案提示的集合,并随机抽取一个
	 */
	public List<AnswerTip> queryContent(Integer aid);
}
