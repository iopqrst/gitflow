package com.bskcare.ch.timeLine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.timeLine.TimeLineContentDao;
import com.bskcare.ch.timeLine.impl.TimeLineContentImplBreakfast;
import com.bskcare.ch.timeLine.impl.TimeLineContentImplBreakfastJIA;
import com.bskcare.ch.timeLine.impl.TimeLineContentImplDinnerJIA;
import com.bskcare.ch.timeLine.impl.TimeLineContentImplLunch;
import com.bskcare.ch.timeLine.impl.TimeLineContentImplDinner;
import com.bskcare.ch.timeLine.impl.TimeLineContentImplLunchJIA;
import com.bskcare.ch.timeLine.impl.TimeLineContentImplRetNull;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.vo.timeLine.TimeLineContent;
import com.bskcare.ch.vo.timeLine.TimeLineRule;

public class TimeLineContentUtils {

	public static TimeLineTaskContent choiceContent(TimeLineRule rule) {
		if (rule != null && rule.getConType() != null) {
			if (rule.getConType() == TimeLineRule.CONTYPE_BREAKFAST) {
				// 早餐
				return new TimeLineContentImplBreakfast();
			} else if (rule.getConType() == TimeLineRule.CONTYPE_LUNCH) {
				// 午餐
				return new TimeLineContentImplLunch();
			} else if (rule.getConType() == TimeLineRule.CONTYPE_DINNER) {
				// 晚餐
				return new TimeLineContentImplDinner();
			} else if (rule.getConType() == TimeLineRule.CONTYPE_BREAKFASTJIA) {
				// 早加餐
				return new TimeLineContentImplBreakfastJIA();
			} else if (rule.getConType() == TimeLineRule.CONTYPE_LUNCHJIA) {
				// 午加餐
				return new TimeLineContentImplLunchJIA();
//			} else if (rule.getConType() == TimeLineRule.CONTYPE_DINNERJIA) {
//				// 晚加餐
//				return new TimeLineContentImplDinnerJIA();
			} else {
				// 返回空白
				return new TimeLineContentImplRetNull();
			}
		}
		return null;
	}

	public static TimeLineContent getTimeLineContent(Integer isPay,
			Integer conType, Integer softType, TimeLineContentDao contentDao) {
		
		TimeLineContent content = new TimeLineContent();
		content.setIsPay(isPay);
		content.setConType(conType);
		content.setSoftType(softType);
		
		List<TimeLineContent> contents = contentDao
				.queryTimeLineContent(content);
		if(!CollectionUtils.isEmpty(contents)){
			return contents.get(RandomUtils.getRandomIndex(contents.size()));
		}
		return null;
	}
}
