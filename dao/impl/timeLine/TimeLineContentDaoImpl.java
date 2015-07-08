package com.bskcare.ch.dao.impl.timeLine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.timeLine.TimeLineContentDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.TimeLineContent;

@Repository
@SuppressWarnings("unchecked")
public class TimeLineContentDaoImpl extends BaseDaoImpl<TimeLineContent>
		implements TimeLineContentDao {

	public PageObject<TimeLineContent> queryTimeLineContentToPage(
			QueryInfo info, TimeLineContent content) {
		String hql = "from TimeLineContent where 1=1 ";
		ArrayList args = new ArrayList();
		if (content != null) {
			if (content.getConType() != null && content.getConType() != 0) {
				hql += " and conType = ?";
				args.add(content.getConType());
			}
			if (content.getStauts() != null) {
				hql += " and stauts = ?";
				args.add(content.getStauts());
			}
			if (content.getContent() != null) {
				hql += " and content like ?";
				args.add("%" + content.getContent() + "%");
			}
			if (content.getIsPay() != null && content.getIsPay() != 0) {
				hql += " and isPay = ?";
				args.add(content.getIsPay());
			}

		}
		return this.queryObjects(hql, args.toArray(), info);
	}

	public List<TimeLineContent> queryTimeLineContent(TimeLineContent content) {
		String hql = "from TimeLineContent where 1=1 ";
		ArrayList args = new ArrayList();
		if (content != null) {
			if (content.getConType() != null) {
				hql += " and conType = ?";
				args.add(content.getConType());
			}
			if (content.getIsPay() != null) {
				hql += " and (isPay = ? or isPay = ?)";
				args.add(content.getIsPay());
				args.add(TimeLineContent.ISPAY_ALL);
			}
			if (content.getSoftType() != null && content.getSoftType() != 0) {
				hql += " and softType = ?";
				args.add(content.getSoftType());
			}
			hql += " and stauts = ?";
			args.add(TimeLineContent.STAUTS_STAUTS);

		}
		return executeFind(hql, args.toArray());
	}

}
