package com.bskcare.ch.dao.impl.timeLine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.timeLine.TimeLineSmsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.TimeLineSms;

@Repository
@SuppressWarnings("unchecked")
public class TimeLineSmsDaoImpl extends BaseDaoImpl<TimeLineSms> implements TimeLineSmsDao {

	public PageObject<TimeLineSms> querypageObject(TimeLineSms lineSMS,
			QueryInfo info) {
		List args = new ArrayList();
		String hql=" from TimeLineSms where 1=1 ";
		if(lineSMS!=null){
			if(lineSMS.getClientType()!=null){
				hql +=" and clientType = ? ";
				args.add(lineSMS.getClientType());
			}
			if(lineSMS.getIscycle()!=null){
				hql +=" and iscycle = ? ";
				args.add(lineSMS.getIscycle());
			}
			if(lineSMS.getSoftType()!=0){
				hql +=" and softType = ? ";
				args.add(lineSMS.getSoftType());
			}
		}
		return queryObjects(hql, args.toArray(), info);
	}

	public List<TimeLineSms> getListSMS(TimeLineSms lineSMS) {
		List args = new ArrayList();
		String hql=" from TimeLineSms where 1=1 ";
		if(lineSMS!=null){
				if(lineSMS.getClientType()!=null){
					hql +=" and clientType = ? ";
					args.add(lineSMS.getClientType());
				}
				if(lineSMS.getIscycle()!=null){
					hql +=" and iscycle = ? ";
					args.add(lineSMS.getIscycle());
				}
		}
		return executeFind(hql, args.toArray());
	}
	
}
