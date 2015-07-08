package com.bskcare.ch.dao.impl.timeLine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.timeLine.TimeLineRuleCustomDao;
import com.bskcare.ch.vo.timeLine.TimeLineContent;
import com.bskcare.ch.vo.timeLine.TimeLineRuleCustom;

@Repository
@SuppressWarnings("unchecked")
public class TimeLineRuleCustomDaoImpl extends BaseDaoImpl<TimeLineRuleCustom>
		implements TimeLineRuleCustomDao {

	public List<TimeLineRuleCustom> getlineRuleCustoms(TimeLineRuleCustom custom) {
		String hql = "from TimeLineRuleCustom where 1 = 1 ";
		List args = new ArrayList();
		if(custom != null){
			if(custom.getClientId()!=null){
				hql+=" and clientId = ?";
				args.add(custom.getClientId());
			}
			if(custom.getSoftType()!=null){
				hql+=" and softType = ?";
				args.add(custom.getSoftType());
			}
		}
		hql+=" and status != ? order by taskTime";
		args.add(TimeLineRuleCustom.STAUTS_FORBIDDEN);
		return executeFind(hql, args.toArray());
	}

	public void deleteRuleByCid(Integer cid) {
		if(cid != null){
			String sql = "delete FROM tg_timeline_rule_custom where clientid = ?";
			this.deleteBySql(sql, cid);
		}
	}

}
