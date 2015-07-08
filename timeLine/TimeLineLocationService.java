package com.bskcare.ch.service.timeLine;

import com.bskcare.ch.vo.timeLine.TimelineLocation;

public interface TimeLineLocationService {
	/**
	 * 根据用户上传的经纬度信息添加天气提醒，如果地区相同不修改，否则从新生成
	 * @param location
	 * @return
	 */
	public String addTimeLineLocation(TimelineLocation location);
}
