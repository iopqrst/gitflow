package com.bskcare.ch.service.impl.timeLine;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import net.sf.json.JSONObject;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.timeLine.TimeLineLocationDao;
import com.bskcare.ch.dao.timeLine.TimeLineTaskDao;
import com.bskcare.ch.service.timeLine.TimeLineLocationService;
import com.bskcare.ch.service.timeLine.TimeLineTaskService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.GISUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.timeLine.TimeLineRule;
import com.bskcare.ch.vo.timeLine.TimelineLocation;
@Service
public class TimeLineLocationServiceImpl implements TimeLineLocationService {

	@Autowired
	private TimeLineLocationDao lineLocationDao;
	@Autowired
	private TimeLineTaskDao lineTaskDao;
	@Autowired
	private TimeLineTaskService lineTaskService;

	public String addTimeLineLocation(TimelineLocation location) {
		JSONObject jo = new JSONObject();
		jo.put("msg", "经纬度上传错误返回错误");
		jo.put("code", Constant.INTERFACE_FAIL);
		jo.put("data", "");
		if (location != null && location.getClientId() != null
				&& location.getLatitude() != 0 && location.getLongitude() != 0
				&& location.getSoftType() != 0) {
			// 获得用户上传的经纬度城市
//			String upcity = GISUtils.getCityBylocation(location.getLongitude(),
//					location.getLatitude());
			if(StringUtils.isEmpty(location.getCity())){//判断如果没有传城市，
				location.setCity(GISUtils.getCityBylocation(location.getLongitude(),
					location.getLatitude()));//调用gis接口获得城市名
			}
			List<TimelineLocation> locations = lineLocationDao
					.queryList(location);
			JSONObject reinstall=new JSONObject();
			reinstall.put("reinstall", 1);
			if (CollectionUtils.isEmpty(locations)||StringUtils.isEmpty(locations.get(0).getCity())
					|| !locations.get(0).getCity().equals(location.getCity())) {
				// 如果用户没有上传过经纬度 或者数据库最后一条用户的城市信息有变化，从新刷新时间轴天气预报
				// 刷新用户的天气预报
				// 删除今天的天气预报
				lineTaskDao.deleteTask(location.getClientId(), DateUtils
						.getDateByType(new Date(), "yyyy-MM-dd"), DateUtils
						.getDateByType(DateUtils.getAppointDate(new Date(), 1),
								"yyyy-MM-dd"), TimeLineRule.CONTYPE_WEATHER);
				// 添加天气预报
				int reinstallint= lineTaskService.addWeatherTask(location.getClientId(), location
						.getLongitude(), location.getLatitude(), location
						.getSoftType(),location.getCity(),null);
				reinstall.put("reinstall", reinstallint);
			}
			// 保存用户最后一次上传的经纬	度信息
			location.setUpdataTime(new Date());
			lineLocationDao.add(location);
			jo.put("msg", "经纬度上传成功");
			jo.put("code", Constant.INTERFACE_SUCC);
			jo.put("data", reinstall.toString());
		}
		return jo.toString();
	}

}
