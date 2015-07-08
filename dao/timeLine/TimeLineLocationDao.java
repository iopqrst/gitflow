package com.bskcare.ch.dao.timeLine;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.timeLine.TimelineLocation;

public interface TimeLineLocationDao extends BaseDao<TimelineLocation> {
	/**
	 * 查询最后一次上传的经纬度信息，
	 * location.softType  软件类型
	 * location.clientId  空时返回所有用户最后一次上传的经纬度信息，
	 * location.clientId  不为空，返回该用户最后一次上传的经纬度信息
	 * @param location
	 * @return
	 */
	public List<TimelineLocation> queryList(TimelineLocation location);

}
