package com.bskcare.ch.dao.impl.timeLine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.timeLine.TimeLineLocationDao;
import com.bskcare.ch.vo.timeLine.TimelineLocation;
@Repository
@SuppressWarnings("unchecked")
public class TimeLineLocationDaoImpl extends BaseDaoImpl<TimelineLocation> implements TimeLineLocationDao{

	public List<TimelineLocation> queryList(TimelineLocation location) {
		String argsStr = "";
		List args = new ArrayList();
		if(location!=null){
			if(location.getSoftType()!=0){
				argsStr+=" and softType = ? ";
				args.add(location.getSoftType());
			}
			if(location.getClientId()!=null){
				argsStr+=" and clientId = ? ";
				args.add(location.getClientId());
			}
		}
		String sql = "SELECT * FROM (SELECT * from tg_timeline_location where 1=1 "+argsStr+" ORDER BY updataTime DESC) t1 GROUP BY t1.clientId HAVING MAX(t1.updataTime);";
		return this.executeNativeQuery(sql, args.toArray(), TimelineLocation.class);
	}

}
