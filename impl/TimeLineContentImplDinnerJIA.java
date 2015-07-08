package com.bskcare.ch.timeLine.impl;

import com.bskcare.ch.dao.timeLine.TimeLineContentDao;
import com.bskcare.ch.tg.TgMeals;
import com.bskcare.ch.timeLine.TimeLineContentUtils;
import com.bskcare.ch.timeLine.TimeLineTaskContent;
import com.bskcare.ch.vo.timeLine.TimeLineContent;
import com.bskcare.ch.vo.timeLine.TimeLineRule;
import com.bskcare.ch.vo.timeLine.TimeLineTask;

/**
 * 时间轴任务内容----------晚加餐
 */
public class TimeLineContentImplDinnerJIA implements TimeLineTaskContent {

	public TimeLineTask setetTaskCon(Integer soft, Integer isPay,
			String consequence, TimeLineContentDao contentDao,
			TimeLineRule timeLineRule, TgMeals meals, TimeLineTask lineTask) {
		TimeLineContent content = new TimeLineContent();
		String contentStr = "";
		content = TimeLineContentUtils.getTimeLineContent(isPay, timeLineRule
				.getConType(), soft, contentDao);
		String titleStr = "";
		if (content != null) {
			contentStr = content.getContent();
			titleStr = content.getTitle();
		} else {
			contentStr = "";
			titleStr = "";
		}
		if ((isPay != null && isPay == TimeLineContent.ISPAY_YES)
				|| timeLineRule.getIsPay() == TimeLineRule.ISPAY_ALL) {// 付费内容,和设置为所有用户的内容，权限在页面控制
			int num = -1;
			num = content.getContent().indexOf("：");
			if (num == -1) {
				num = content.getContent().indexOf(":");
			}
			if (num != -1) {
				contentStr = (content.getContent().substring(0, num + 1) + meals
						.getWanjia());// 插入菜谱
				if (num + 1 < content.getContent().length()) {
					contentStr = (contentStr + content.getContent().substring(
							num + 1, content.getContent().length()));// 插入菜谱
				} else {
					contentStr = (contentStr + "。");// 插入菜谱
				}
			} else {
				contentStr = contentStr + "今天的菜谱是：" + meals.getWanjia() + "。";
			}
		}
		lineTask.setContent(contentStr);
		lineTask.setTitle(titleStr);
		return lineTask;
	}

}
