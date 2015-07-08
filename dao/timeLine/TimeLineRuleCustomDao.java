package com.bskcare.ch.dao.timeLine;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.timeLine.TimeLineContent;
import com.bskcare.ch.vo.timeLine.TimeLineRuleCustom;

public interface TimeLineRuleCustomDao extends BaseDao<TimeLineRuleCustom> {
	public List<TimeLineRuleCustom> getlineRuleCustoms(TimeLineRuleCustom custom);
	
	public void deleteRuleByCid(Integer cid);
}
