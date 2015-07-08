package com.bskcare.ch.timeLine.impl;


import com.bskcare.ch.dao.timeLine.TimeLineContentDao;
import com.bskcare.ch.tg.TgMeals;
import com.bskcare.ch.timeLine.TimeLineContentUtils;
import com.bskcare.ch.timeLine.TimeLineTaskContent;
import com.bskcare.ch.vo.timeLine.TimeLineContent;
import com.bskcare.ch.vo.timeLine.TimeLineRule;
import com.bskcare.ch.vo.timeLine.TimeLineTask;

/**
 * 时间轴任务内容------早餐
 */
public class TimeLineContentImplBreakfast implements TimeLineTaskContent {

	public TimeLineTask setetTaskCon(Integer soft, Integer isPay,
			String consequence, TimeLineContentDao contentDao,
			TimeLineRule timeLineRule, TgMeals meals, TimeLineTask lineTask) {

		// 时间轴内容信息：可以在这个模板中插入详细的信息
		TimeLineContent content = new TimeLineContent();
		content = TimeLineContentUtils.getTimeLineContent(isPay,
				TimeLineRule.CONTYPE_BREAKFAST, soft, contentDao);

		String contentStr = "";
		String titleStr = "";

		if (content != null) {
			contentStr = content.getContent();
			titleStr = content.getTitle();
		}

		if ((isPay != null && isPay == TimeLineContent.ISPAY_YES)
				|| timeLineRule.getIsPay() == TimeLineRule.ISPAY_ALL) {// 付费内容,和设置为所有用户的内容，权限在页面控制
			// 根据时间轴规则查询出适合用户的时间轴规则模版信息，时间轴内容可以插入在此模版中

			int num = -1;
			num = content.getContent().indexOf("："); // 判断是否包含“：”或者“:”
			if (num == -1) {
				num = content.getContent().indexOf(":");
			}

			if (num != -1) {
				contentStr = (content.getContent().substring(0, num + 1) + meals
						.getZaocan());// 插入菜谱
				if (num + 1 < content.getContent().length()) {
					contentStr = contentStr + content.getContent().substring(num + 1,
							content.getContent().length());// 插入菜谱
				}else{
					contentStr = (contentStr + "。");// 插入菜谱
				}
			} else {
				contentStr = contentStr + "今天的菜谱是：" + meals.getZaocan() + "。";
			}
		}
		lineTask.setContent(contentStr);
		lineTask.setTitle(titleStr);
		return lineTask;
	}

}
