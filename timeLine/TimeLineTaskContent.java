package com.bskcare.ch.timeLine;

import com.bskcare.ch.dao.timeLine.TimeLineContentDao;
import com.bskcare.ch.tg.TgMeals;
import com.bskcare.ch.vo.timeLine.TimeLineRule;
import com.bskcare.ch.vo.timeLine.TimeLineTask;

public interface TimeLineTaskContent {
	/**
	 * 根据用户信息生成对应的时间轴任务内容
	 * 
	 * @param soft 软件类型
	 * @param isPay  是否付费
	 * @param consequence  测试结果
	 * @return
	 */
	public TimeLineTask setetTaskCon(Integer soft, Integer isPay, String consequence,
			TimeLineContentDao contentDao, TimeLineRule timeLineRule,
			TgMeals meals,TimeLineTask lineTask);

}
