package com.bskcare.ch.timeLine.impl;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.timeLine.TimeLineContentDao;
import com.bskcare.ch.tg.TgMeals;
import com.bskcare.ch.timeLine.TimeLineContentUtils;
import com.bskcare.ch.timeLine.TimeLineTaskContent;
import com.bskcare.ch.vo.timeLine.TimeLineContent;
import com.bskcare.ch.vo.timeLine.TimeLineRule;
import com.bskcare.ch.vo.timeLine.TimeLineTask;

/**
 * 时间轴任务内容----------其他类型
 */
public class TimeLineContentImplRetNull implements TimeLineTaskContent {

	public TimeLineTask setetTaskCon(Integer soft, Integer isPay, String consequence,
			TimeLineContentDao contentDao, TimeLineRule timeLineRule,
			TgMeals meals,TimeLineTask lineTask) {
		
		if (soft != null && soft == Constant.SOFT_GUAN_XUE_TANG) {// 血糖软件
			if (isPay != null) {// 付费内容
				TimeLineContent content = new TimeLineContent();
				content = TimeLineContentUtils.getTimeLineContent(isPay,
						timeLineRule.getConType(), soft, contentDao);
				if(content!=null){
					lineTask.setContent(content.getContent());
					lineTask.setTitle(content.getTitle());
				}
			}
		}
		return lineTask;
	}
}
