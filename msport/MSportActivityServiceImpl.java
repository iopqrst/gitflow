package com.bskcare.ch.service.impl.msport;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.quartz.impl.jdbcjobstore.MSSQLDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.MsportActivityExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.msport.MSportActivityDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.msport.MSportActivityService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.MoneyFormatUtil;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.msport.MSportActivity;


@Service
public class MSportActivityServiceImpl implements MSportActivityService {
	
	@Autowired
	private MSportActivityDao activityDao;
	
	public String queryActivityInfo(MSportActivity activity, QueryInfo queryInfo){
		JSONObject jo = new JSONObject();
		PageObject<MsportActivityExtend> pager = activityDao.queryActivityInfo(activity, queryInfo);
		List<MsportActivityExtend> lst = pager.getDatas();
		if(!CollectionUtils.isEmpty(lst)){
			
			JSONObject json = new JSONObject();
			String base = SystemConfig.getString("image_base_url");
			json.put("base", base);
			json.put("list", JsonUtils.getJsonString4JavaListDate(lst, "yyyy-MM-dd HH:mm:ss"));
			
			jo.put("code", 1);
			jo.put("msg", "获取活动信息成功");
			jo.put("data", json.toString());
		}else{
			jo.put("code", 1);
			jo.put("msg", "没有活动信息");
			jo.put("data", "");
		}
 		return jo.toString();
	}
	
	public String queryActivityClient(MSportActivity activity, QueryInfo queryInfo){
		JSONObject jo = new JSONObject();
		
		PageObject<Object> pager = activityDao.queryActivityClient(activity, queryInfo);
		List<Object> lst = pager.getDatas();
		
		queryInfo.setPageSize(99999999);
		PageObject<Object> pagerSelf = activityDao.queryActivityClient(activity, queryInfo);
		List<Object> lstSelf = pagerSelf.getDatas();
		int selfSort = 0;
		if(!CollectionUtils.isEmpty(lstSelf)){
			for (int n = 0; n < lstSelf.size(); n++) {
				Object[] objs = (Object[])lst.get(n);
				if(Integer.parseInt(objs[0]+"") == activity.getCreator()){
					selfSort = n+1;
				}
			}
		}
		
		if(!CollectionUtils.isEmpty(lst)){
			double mlongitude = 0;
			double mlatitude = 0;
			for (int j = 0; j < lst.size(); j++) {
				Object[] objs = (Object[])lst.get(j);
				if(Integer.parseInt(objs[0]+"") != activity.getCreator()){
					mlongitude = Double.parseDouble(objs[7]+"");
					mlatitude = Double.parseDouble(objs[8]+"");
				}
			}
			
			JSONArray ja = new JSONArray();
			for (int i = 0; i < lst.size(); i++) {
				JSONObject json = new JSONObject();
				Object[] objs = (Object[])lst.get(i);
				
				json.put("clientId", objs[0]);
				if(objs[1] != null){
					json.put("name", objs[1].toString());
				}else{
					json.put("name", "");
				}
				if(objs[2] != null){
					json.put("gender", objs[2]);
				}else{
					json.put("gender", "");
				}
				//电话号码
				if(objs[3] != null){
					json.put("mobile", objs[3].toString());
				}else{
					json.put("mobile", "");
				}
				if(objs[4] != null){
					json.put("nickName", objs[4].toString());
				}else{
					json.put("nickName", "");
				}
				String base = SystemConfig.getString("image_base_url");
				if(objs[5] != null){
					json.put("headPortrait", base + objs[5].toString());
				}else{
					json.put("headPortrait", "");
				}
				if(objs[6] != null){
					json.put("location", objs[6].toString());
				}else{
					json.put("location", "");
				}
				if(objs[7] != null){
					json.put("longitude", objs[7]);
				}else{
					json.put("longitude", "");
				}
				if(objs[8] != null){
					json.put("latitude", objs[8]);
				}else{
					json.put("latitude", "");
				}
				
				if(Integer.parseInt(objs[0]+"") != activity.getCreator()){
					if(objs[7] != null && objs[8] != null){
						double clongitude = Double.parseDouble(objs[7]+"");
						double clatitude = Double.parseDouble(objs[8]+"");
						double distance = distanceByLngLat(mlongitude, mlatitude, clongitude, clatitude);
						json.put("distance",MoneyFormatUtil.formatDouble(distance/1000));
					}else{
						json.put("distance", 0);
					}
				}else{
					json.put("distance", 0);
				}
				
				
				if(objs[9] != null){
					double distance = Double.parseDouble(objs[9].toString());
					json.put("distanceTotal", MoneyFormatUtil.formatDouble(distance/1000));
				}else{
					json.put("distanceTotal", 0);
				}
				//排名
				json.put("sort", i+1);
				//总数量
				json.put("runFriendCount", pager.getTotalRecord());
				
				if(objs[10] != null && objs[11] != null){
					Date startDate = DateUtils.parseDate(objs[10]+"", "yyyy-MM-dd HH:mm:ss");
					Date endDate = DateUtils.parseDate(objs[11]+"", "yyyy-MM-dd HH:mm:ss");
					long days = DateUtils.getQuot(startDate, endDate);
					if(days == 0){
						json.put("days", 1);
					}else{
						json.put("days", days);
					}
					
				}else{
					json.put("days", 0);
				}
				
				if(objs[12] != null){
					json.put("sumSteps", objs[12]);
				}else{
					json.put("sumSteps", 0);
				}
				
				if(objs[13] != null){
					double sumCalorie = Double.parseDouble(objs[13]+"");
					json.put("sumCalorie", MoneyFormatUtil.formatDouble(sumCalorie));
				}else{
					json.put("sumCalorie", 0);
				}
				json.put("selfSort", selfSort);
				ja.add(json.toString());
			}
			jo.put("code", 1);
			jo.put("msg", "成功");
			jo.put("data", ja.toArray());
		}else{
			jo.put("code", 1);
			jo.put("msg", "活动没有人");
			jo.put("data", "");
		}
		return jo.toString();
	}
	@Autowired
	MSportActivityDao msActivityDao;

	public MSportActivity add(MSportActivity ma) {
		MSportActivity activity = msActivityDao.add(ma);
		if(activity != null){
			msActivityDao.applyJoinActivity(activity.getCreator(),activity.getAid());
		}
		return activity;
	}

	public void update(MSportActivity ma) {
		msActivityDao.update(ma);
	}

	public MSportActivity load(Integer aid) {
		return msActivityDao.load(aid);
	}

	public String selfCreatedActivity(Integer cid) {
		JSONObject jo = new JSONObject();
		JSONObject ja = new JSONObject();
		String base = SystemConfig.getString("image_base_url");
		List<MSportActivity> list = msActivityDao.selfCreatedActivity(cid);
		if (!CollectionUtils.isEmpty(list)) {
			jo.put("code", Constant.INTERFACE_SUCC);
			jo.put("msg", "查询成功");
			ja.put("baseUrl", base);
			ja.put("list", JsonUtils.getJsonString4JavaListDate(list));
			jo.put("data", ja);
		} else {
			jo.put("code", Constant.INTERFACE_SUCC);
			jo.put("msg", "查询成功");
			jo.put("data", "");
		}
		return jo.toString();
	}

	public String seselfJoinedActivity(Integer cid) {
		JSONObject jo = new JSONObject();
		JSONObject ja = new JSONObject();
		String base = SystemConfig.getString("image_base_url");
		List<MSportActivity> list = msActivityDao.seselfJoinedActivity(cid);
		if (!CollectionUtils.isEmpty(list)) {
			jo.put("code", Constant.INTERFACE_SUCC);
			jo.put("msg", "查询成功");
			ja.put("baseUrl", base);
			ja.put("list", JsonUtils.getJsonString4JavaListDate(list));
			jo.put("data", ja);
		} else {
			jo.put("code", Constant.INTERFACE_SUCC);
			jo.put("msg", "查询成功");
			jo.put("data", "");
		}
		return jo.toString();
	}

	public String applyJoinActivity(Integer cid, Integer aid) {
		JSONObject jo = new JSONObject();
		int status = msActivityDao.applyJoinActivity(cid, aid);
		if (0 != status) {
			jo.put("code", Constant.INTERFACE_SUCC);
			jo.put("msg", "申请加入活动成功");
		} else {
			jo.put("code", Constant.INTERFACE_FAIL);
			jo.put("msg", "申请加入活动失败");
		}
		return jo.toString();
	}

	public int isJoinActivity(Integer cid, Integer aid) {
		return msActivityDao.isJoinActivity(cid,aid);
	}

	
	/**
	 *计算两个经纬度之间的距离
	 */
	 public static double distanceByLngLat(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        return s;
	 }
	 
	 public String queryAllActivityInfo(MSportActivity activity, QueryInfo queryInfo){
		JSONObject jo = new JSONObject();
		PageObject<MsportActivityExtend> pager = activityDao.queryActivityInfo(activity, queryInfo);
		List<MsportActivityExtend> lst = pager.getDatas();
		if(!CollectionUtils.isEmpty(lst)){
			JSONObject json = new JSONObject();
			String base = SystemConfig.getString("image_base_url");
			json.put("base", base);
			json.put("list", JsonUtils.getJsonString4JavaListDate(lst, "yyyy-MM-dd HH:mm:ss"));
			jo.put("data", json.toString());
			jo.put("total", pager.getTotalRecord());
		}else{
			jo.put("data", "");
		}
 		return jo.toString();
	}
	 
	 
	 public String joinActivity(MSportActivity activity){
		 JSONObject jo = new JSONObject();
		 MSportActivity ma = activityDao.queryActivityByPwd(activity);
		 if(ma != null){
			 int acount = this.isJoinActivity(activity.getCreator(), activity.getAid());
			 if(acount == 0){
				 int count = activityDao.applyJoinActivity(activity.getCreator(), activity.getAid());
				 if(count > 0){
					 jo.put("msg", "success");
				 }else{
					 jo.put("msg", "fail");
				 }
			 }else{
				 jo.put("msg", "join");
			 }
		 }else{
			 //验证码不正确
			 jo.put("msg", "codeFail");
		 }
		 return jo.toString();
	 }
	 
		public String queryActivityClientInfo(MSportActivity activity, QueryInfo queryInfo){
			JSONObject jo = new JSONObject();
			PageObject<Object> pager = activityDao.queryActivityClient(activity, queryInfo);
			List<Object> lst = pager.getDatas();
			PageObject<Object> pagerSelf = activityDao.queryActivityClient(activity, queryInfo);
			List<Object> lstSelf = pagerSelf.getDatas();
			int selfSort = 0;
			if(!CollectionUtils.isEmpty(lstSelf)){
				for (int n = 0; n < lstSelf.size(); n++) {
					Object[] objs = (Object[])lst.get(n);
					if(Integer.parseInt(objs[0]+"") == activity.getCreator()){
						selfSort = n+1;
					}
				}
			}
			if(!CollectionUtils.isEmpty(lst)){
				JSONArray ja = new JSONArray();
				for (int i = 0; i < lst.size(); i++) {
					JSONObject json = new JSONObject();
					Object[] objs = (Object[])lst.get(i);
					json.put("clientId", objs[0]);
					if(objs[1] != null){
						json.put("name", objs[1].toString());
					}else{
						json.put("name", "");
					}
					if(objs[2] != null){
						json.put("gender", objs[2]);
					}else{
						json.put("gender", "");
					}
					//电话号码
					if(objs[3] != null){
						json.put("mobile", objs[3].toString());
					}else{
						json.put("mobile", "");
					}
					if(objs[4] != null){
						json.put("nickName", objs[4].toString());
					}else{
						json.put("nickName", "");
					}
					String base = SystemConfig.getString("image_base_url");
					if(objs[5] != null){
						json.put("headPortrait", base + objs[5].toString());
					}else{
						json.put("headPortrait", "");
					}
					
					if(objs[9] != null){
						double distance = Double.parseDouble(objs[9].toString());
						json.put("distanceTotal", MoneyFormatUtil.formatDouble(distance/1000));
					}else{
						json.put("distanceTotal", 0);
					}
					//排名
					json.put("sort", i+1);
					//总数量
					json.put("runFriendCount", pager.getTotalRecord());
					if(objs[10] != null && objs[11] != null){
						Date startDate = DateUtils.parseDate(objs[10]+"", "yyyy-MM-dd HH:mm:ss");
						Date endDate = DateUtils.parseDate(objs[11]+"", "yyyy-MM-dd HH:mm:ss");
						long days = DateUtils.getQuot(startDate, endDate);
						if(days == 0){
							json.put("days", 1);
						}else{
							json.put("days", days);
						}
					}else{
						json.put("days", 0);
					}
					json.put("selfSort", selfSort);
					ja.add(json.toString());
				}
				String baseUrl = SystemConfig.getString("image_base_url");
				jo.put("baseUrl", baseUrl);
				jo.put("data", ja.toArray());
				jo.put("total", pager.getTotalRecord());
			}else{
				jo.put("data", "");
			}
			return jo.toString();
		}
}
