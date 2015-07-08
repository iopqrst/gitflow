package com.bskcare.ch.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.bo.ClientLocationExtend;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.ClientLocationDao;
import com.bskcare.ch.dao.msport.MSportDao;
import com.bskcare.ch.dao.msport.MsportCommentDao;
import com.bskcare.ch.dao.msport.MsportFriendDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientLocationService;
import com.bskcare.ch.util.GISUtils;
import com.bskcare.ch.util.HttpClientUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ClientLocation;
import com.bskcare.ch.vo.msport.MSport;
import com.bskcare.ch.vo.msport.MsportComment;


@Service
@SuppressWarnings("unchecked")
public class ClientLocationServiceImpl implements ClientLocationService{

	@Autowired
	private ClientLocationDao locationDao;
	
	@Autowired
	private ClientInfoDao clientInfoDao;
	
	@Autowired
	private MSportDao sportDao;
	
	@Autowired
	private MsportFriendDao friendDao;
	
	@Autowired
	private MsportCommentDao commentDao;
	
	public String saveLocation(ClientLocation location) throws Exception{
		
		JSONObject jo = new JSONObject();
		
		if(location != null){
			//经度
			 double lon = location.getLongitude();
			 //纬度
			 double lat = location.getLatitude();
			//用户位置
			 String loc = GISUtils.getLocationBylocation(lon, lat);
			 if(!StringUtils.isEmpty(loc) && !loc.equals("fail")){
				//先根据用户id查询用户是否上传过经纬度信息
				ClientLocation clientLoc = locationDao.queryLocationByClientId(location.getClientId());
				//如果没有上传过就添加
				if(clientLoc == null){
					if(!StringUtils.isEmpty(loc)){
						 location.setLocation(loc);
					 }
					locationDao.add(location);
				}else{   //如果上传过就修改
					clientLoc.setLatitude(lat);
					clientLoc.setLongitude(lon);
					if(!StringUtils.isEmpty(loc)){
						clientLoc.setLocation(loc);
					}
					locationDao.update(clientLoc);
				}
				
				jo.put("code", 1);
				jo.put("msg", "保存数据成功");
				jo.put("data", JsonUtils.getJsonObject4JavaPOJO(clientLoc));
			 }else{
				 jo.put("code", 0);
				 jo.put("msg", "定位失败");
				 jo.put("data", "");
			 }
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数不正确");
			jo.put("data", "");
		}
		
		return jo.toString();
	}
	
	
	public String queryClientByLonLat(ClientLocation location,double distance,QueryInfo queryInfo){
		JSONObject jo = new JSONObject();
		
		if(location!=null&&distance != 0){
			double lon = location.getLongitude();
			double lat = location.getLatitude();
			
			double raidus = distance*1000;
			
			double [] lonlat = getAround(lat, lon, raidus);
			if(lonlat != null){
				
				JSONArray ja = new JSONArray();
				
				PageObject pager = locationDao.queryClientByLonLat(location.getClientId(),lonlat[0], lonlat[2], lonlat[1], lonlat[3],queryInfo);
				
				ClientInfo clientInfo = clientInfoDao.load(location.getClientId());
				
				List<Object> lst = pager.getDatas();
				if(!CollectionUtils.isEmpty(lst)){
					//附近跑友数量
					int runFriendCount = pager.getTotalRecord();
					
					//附近跑友根据运动总距离排名
					int sort = 0;
					List<Object> lstLonLat = locationDao.querySortLonLog(lonlat[0], lonlat[2], lonlat[1], lonlat[3]);
					List<Object> lstMySport = friendDao.queryMySportSort(location.getClientId());
					
					double myDistanceTotal = 0;
					if(!CollectionUtils.isEmpty(lstMySport)){
						Object[] lstObj = (Object[])lstMySport.get(0);
						if(lstObj[1] != null){
							myDistanceTotal = Double.parseDouble(lstObj[1].toString());
						}
					}
					
					sort = calSort(lstLonLat, myDistanceTotal);
					
					for (Object objs : lst) {
						Object[] oo = (Object[]) objs;
						ClientLocation os = (ClientLocation) oo[0];
						
						if(!os.getClientId().equals(location.getClientId())){
							JSONObject json = new JSONObject();
							json.put("clientId", os.getClientId());
							json.put("longitude", os.getLongitude());
							json.put("latitude", os.getLatitude());
							json.put("location", os.getLocation());
							
							if(oo[1] != null){
								json.put("name", oo[1].toString());
							}else{
								json.put("name", "");
							}
							if(oo[2]!= null){
								json.put("gender", Integer.parseInt(oo[2].toString()));
							}else{
								json.put("gender", 0);	
							}
							if(oo[3] != null){
								json.put("mobile", oo[3].toString());
							}else{
								json.put("mobile", "");
							}
							if(oo[4] != null){
								json.put("nickName", oo[4].toString());
							}else{
								json.put("nickName", "");
							}
							String base = SystemConfig.getString("image_base_url");
							if(oo[5] != null){
								json.put("headPortrait", base + oo[5].toString());
							}else{
								json.put("headPortrait", "");
							}
							
							if(clientInfo != null){
								json.accumulate("mgender", clientInfo.getGender());
								json.accumulate("mname", clientInfo.getName());
							}
							if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getHeadPortrait())){
								json.put("mheadPortrait", base+clientInfo.getHeadPortrait());
							}else{
								json.put("mheadPortrait", "");
							}
							if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getNickName())){
								json.put("mnickName", clientInfo.getNickName());
							}else{
								json.put("mnickName", "");
							}
							double dis = distanceByLngLat(lon, lat, os.getLongitude(), os.getLatitude());
							
							if(dis != 0){
								json.put("distance", dis/1000);
							}else{
								json.put("distance", 0);
							}
							if(sort==0){
								sort = runFriendCount+1;
							}
							json.put("sort",sort);
							json.put("runFriendCount",runFriendCount);
							
							ja.add(json);
						}
					}
				}
				jo.put("code", 1);
				jo.put("msg", "成功");
				jo.put("data", ja);
			}else{
				jo.put("code", 1);
				jo.put("msg", "附近没有可以约跑的人");
				jo.put("data", "");
			}
		}
		return jo.toString();
	}
	
	
	
	
	
	/**
	 * 根据用户经纬度得到用户所在位置
	 */
//	public String getLocation (double longitude,double latitude){
//		String location = "";
//		try{
//			String url = "http://api.map.baidu.com/geocoder/v2/?ak=XbzHeVKdorVig9gjy2q2Ye94" +
//	 		"&location="+latitude+","+longitude+"&output=json&pois=0";
//		
//			String inputLine = HttpClientUtils.getContentByGet(url, "utf-8");
//			
//			if(!StringUtils.isEmpty(inputLine)){
//				Map<String, Object> mapInfo = new HashMap<String, Object>();
//				mapInfo = JsonUtils.getMap4Json(inputLine);
//		    
//				String result = mapInfo.get("result").toString();
//				
//				Map<String, Object> map = new HashMap<String, Object>();
//				map = JsonUtils.getMap4Json(result);
//				
//				if(!CollectionUtils.isEmpty(map)){
//					if(!map.get("addressComponent").equals("")){
//						//location = map.get("formatted_address").toString();
//						Map<String, Object> smap = new HashMap<String, Object>();
//						smap = JsonUtils.getMap4Json(map.get("addressComponent").toString());
//						
//						//城市
//						String city = "";
//						//街道
//						String street = "";
//						if(smap.get("city") != null){
//							city = smap.get("city").toString();
//						} 
//						if(smap.get("street") != null){
//							street = smap.get("street").toString();
//						} 
//						
//						location = street+"，"+city;
//					}
//				}
//			}
//		}catch(Exception e){
//			System.out.println("定位失败");
//			e.printStackTrace();
//			location = "fail";
//		}
//		
////		URL url = new URL("http://api.map.baidu.com/geocoder/v2/?ak=XbzHeVKdorVig9gjy2q2Ye94" +
////	 		"&location="+latitude+","+longitude+"&output=json&pois=0");  
////		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////		connection.setRequestMethod("POST");
////		connection.setRequestProperty("Charset", "utf-8");
////		
////	    BufferedReader in = new BufferedReader(  
////	                new InputStreamReader(url.openStream(),"UTF-8"));  
////	  
////	    String inputLine;  
////	  
////	    while ((inputLine = in.readLine()) != null)  {
////	    	if(!StringUtils.isEmpty(inputLine)){
////	    		Map<String, Object> mapInfo = new HashMap<String, Object>();
////	    		mapInfo = JsonUtils.getMap4Json(inputLine);
////	        
////	    		String result = mapInfo.get("result").toString();
////	    		
////	    		Map<String, Object> map = new HashMap<String, Object>();
////	    		map = JsonUtils.getMap4Json(result);
////	    		
////	    		if(!CollectionUtils.isEmpty(map)){
////	    			if(!map.get("formatted_address").equals("")){
////	    				location = map.get("formatted_address").toString();
////	    			}
////	    		}
////	    	}
////	    }
////	    in.close();  
//	    return location;
//	}
	
	public int getSort(List<ClientLocationExtend> lstLocation,Integer cid){
		int sort = 0;
		if(!CollectionUtils.isEmpty(lstLocation)){
			for (int i = 0; i < lstLocation.size(); i++) {
				ClientLocationExtend locationExtent = lstLocation.get(i);
				if(locationExtent.getClientId().equals(cid)){
					sort = i+1;
					return sort;
				}
			}
			if(sort==0){
				sort = lstLocation.size()+1;
			}
		}
		return sort;
	}
	
	public String queryAllRunSport(Integer cid) throws ParseException{
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		DecimalFormat df = new DecimalFormat("#.00");
		ClientLocation location = locationDao.queryLocationByClientId(cid);
		List<Integer> lstClient = new ArrayList<Integer>();
		List<Integer> lstClientInfo = new ArrayList<Integer>();
		List<ClientLocationExtend> lstLocation = sportDao.queryAllRunSport(20);
		ClientInfo clientInfo = clientInfoDao.load(cid);
		List<ClientLocationExtend> lst = sportDao.queryAllRunSport(0);
		int runFriendCount = 0;
		if(!CollectionUtils.isEmpty(lst)){
			runFriendCount =  lst.size();
		}
		if(!CollectionUtils.isEmpty(lstLocation)){
			int sort = getSort(lst,cid);
			String base = SystemConfig.getString("image_base_url");
			for (int i = 0; i < lstLocation.size(); i++) {
				JSONObject json = new JSONObject();
				ClientLocationExtend locationExtent = lstLocation.get(i);
				json.accumulate("cid", locationExtent.getClientId());
				if(!StringUtils.isEmpty(locationExtent.getName())){
					json.put("name", locationExtent.getName());
				}else{
					json.put("name", "");
				}
				if(!StringUtils.isEmpty(locationExtent.getNickName())){
					json.put("nickName", locationExtent.getNickName());
				}else{
					json.put("nickName", "");
				}
				if(locationExtent.getGender() != null){
					json.put("gender", locationExtent.getGender());
				}else{
					json.put("gender", null);
				}
				if(!StringUtils.isEmpty(locationExtent.getHeadPortrait())){
					json.put("headPortrait", base+locationExtent.getHeadPortrait());
				}else{
					json.put("headPortrait", "");
				}
				json.accumulate("mobile", locationExtent.getMobile());
				/**地址**/
				json.accumulate("location", locationExtent.getLocation());
				
				double distanceTotal = Double.parseDouble(df.format(locationExtent.getDistanceTotal()/1000));
				//总运动距离
				json.accumulate("distanceTotal", distanceTotal);
				//排名
				json.put("sort", sort);
				//距自己的距离
				double distance = distanceByLngLat(locationExtent.getLongitude(),locationExtent.getLatitude(),location.getLongitude(),location.getLatitude());
				json.accumulate("distance", distance/1000);
				lstClient.add(locationExtent.getClientId());
				
//				List<MSport> lstMsport = sportDao.querySportByCid(locationExtent.getClientId());
//				if(!CollectionUtils.isEmpty(lstMsport)){
//					MSport msport = lstMsport.get(0);
//					Date testDate = msport.getTestDate();
//					int days = this.daysBetween(testDate, new Date());
//					json.accumulate("days", days);
//				}
				
				Date testDate = locationExtent.getTestDate();
				int days = this.daysBetween(testDate, new Date());
				json.accumulate("days", days);
				
				json.accumulate("runFriendCount", runFriendCount);
				if(clientInfo != null&clientInfo.getGender() != null){
					json.put("mgender", clientInfo.getGender());
				}else{
					json.put("mgender", "");
				}
				if(clientInfo != null&&!StringUtils.isEmpty(clientInfo.getName())){
					json.put("mname", clientInfo.getName());
				}else{
					json.put("mname","");
				}
				if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getHeadPortrait())){
					json.put("mheadPortrait", base+clientInfo.getHeadPortrait());
				}else{
					json.put("mheadPortrait", "");
				}
				if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getNickName())){
					json.put("mnickName", clientInfo.getNickName());
				}else{
					json.put("mnickName", "");
				}
				ja.add(json);
			}
			
			if(!lstClient.contains(location.getClientId())){
				if(!CollectionUtils.isEmpty(lst)){
					for (int i = 0; i < lst.size(); i++) {
						JSONObject json = new JSONObject();
						ClientLocationExtend locationExtent = lst.get(i);
						if(locationExtent.getClientId().equals(location.getClientId())){
							json.accumulate("cid", locationExtent.getClientId());
							if(!StringUtils.isEmpty(locationExtent.getName())){
								json.put("name", locationExtent.getName());
							}else{
								json.put("name", "");
							}
							if(!StringUtils.isEmpty(locationExtent.getNickName())){
								json.put("nickName", locationExtent.getNickName());
							}else{
								json.put("nickName", "");
							}
							if(locationExtent.getGender() != null){
								json.put("gender", locationExtent.getGender());
							}else{
								json.put("gender", "");
							}
							if(!StringUtils.isEmpty(locationExtent.getHeadPortrait())){
								json.put("headPortrait", base+locationExtent.getHeadPortrait());
							}else{
								json.put("headPortrait", "");
							}
							json.accumulate("mobile", locationExtent.getMobile());
							/**地址**/
							json.accumulate("location", locationExtent.getLocation());
							//总运动距离
							double distanceTotal = Double.parseDouble(df.format(locationExtent.getDistanceTotal()/1000));
							json.accumulate("distanceTotal", distanceTotal);
							//排名
							json.accumulate("sort", sort);
							//距自己的距离
							double distance = distanceByLngLat(locationExtent.getLongitude(),locationExtent.getLatitude(),location.getLongitude(),location.getLatitude());
							json.accumulate("distance", distance/1000);
							
							List<MSport> lstMsport = sportDao.querySportByCid(locationExtent.getClientId());
//							if(!CollectionUtils.isEmpty(lstMsport)){
//								MSport msport = lstMsport.get(0);
//								Date testDate = msport.getTestDate();
//								int days = this.daysBetween(testDate, new Date());
//								json.accumulate("days", days);
//							}
							Date testDate = locationExtent.getTestDate();
							int days = this.daysBetween(testDate, new Date());
							json.accumulate("days", days);
							
							json.accumulate("runFriendCount", runFriendCount);
							if(clientInfo != null&clientInfo.getGender() != null){
								json.put("mgender", clientInfo.getGender());
							}else{
								json.put("mgender", "");
							}
							if(clientInfo != null&&!StringUtils.isEmpty(clientInfo.getName())){
								json.put("mname", clientInfo.getName());
							}else{
								json.put("mname","");
							}
							if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getHeadPortrait())){
								json.put("mheadPortrait", base+clientInfo.getHeadPortrait());
							}else{
								json.put("mheadPortrait", "");
							}
							if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getNickName())){
								json.put("mnickName", clientInfo.getNickName());
							}else{
								json.put("mnickName", "");
							}
							ja.add(json);
						}
					}
				}
			}
			
			for (ClientLocationExtend extend:lst) {
				lstClientInfo.add(extend.getClientId());
			}
			
			if((!CollectionUtils.isEmpty(lstClientInfo)&&!lstClientInfo.contains(location.getClientId()))||(CollectionUtils.isEmpty(lstClientInfo))){
				JSONObject json = new JSONObject();
				json.put("cid", clientInfo.getId());
				if(!StringUtils.isEmpty(clientInfo.getName())){
					json.put("name", clientInfo.getName());
				}else{
					json.put("name", "");
				}
				if(!StringUtils.isEmpty(clientInfo.getNickName())){
					json.put("nickName", clientInfo.getNickName());
				}else{
					json.put("nickName", "");
				}
				if(clientInfo.getGender() != null){
					json.put("gender", clientInfo.getGender());
				}else{
					json.put("gender", "");
				}
				if(!StringUtils.isEmpty(clientInfo.getHeadPortrait())){
					json.put("headPortrait", base+clientInfo.getHeadPortrait());
				}else{
					json.put("headPortrait", "");
				}
				json.put("mobile", clientInfo.getMobile());
				/**地址**/
				json.put("location", location.getLocation());
				//总运动距离
				json.put("distanceTotal", 0);
				//排名
				json.accumulate("sort", sort);
				//距自己的距离
				json.put("distance", 0);
				json.accumulate("days", 0);	
				json.accumulate("runFriendCount", runFriendCount);
				if(clientInfo != null&clientInfo.getGender() != null){
					json.put("mgender", clientInfo.getGender());
				}else{
					json.put("mgender", "");
				}
				if(clientInfo != null&&!StringUtils.isEmpty(clientInfo.getName())){
					json.put("mname", clientInfo.getName());
				}else{
					json.put("mname","");
				}
				if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getHeadPortrait())){
					json.put("mheadPortrait", base+clientInfo.getHeadPortrait());
				}else{
					json.put("mheadPortrait", "");
				}
				if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getNickName())){
					json.put("mnickName", clientInfo.getNickName());
				}else{
					json.put("mnickName", "");
				}
				ja.add(json);
			}
			
			
			jo.put("code", 1);
			jo.put("msg", "成功");
			jo.put("data", ja);
		}else{
			jo.put("code", 1);
			jo.put("msg", "没有数据");
			jo.put("data", "");
		}
		
		return jo.toString();
	}
	
	
	/**约跑评论**/
	public String evaluateByClient(MsportComment comment,String type){
		JSONObject jo = new JSONObject();
	     int commentCount = commentDao.queryCommentById(comment);
	     if(commentCount <= 0){
	    	 commentDao.add(comment);
			 int count = locationDao.evaluateByClient(comment.getFriendId(), type);
			 if(count > 0){
				 jo.put("code", 1);
				 jo.put("msg", "评论成功");
				 jo.put("data", "");
			 }else{
				 jo.put("code", 0);
				 jo.put("msg", "评论失败");
				 jo.put("data", "");
			 }
	     }else{
	    	 jo.put("code", 0);
			 jo.put("msg", "您今天已经评论过，不可以在评论了");
			 jo.put("data", "");
	     }
		 return jo.toString();
	}
	
	/**约跑的好友信息**/
	public String querySportFriend(ClientLocation location,QueryInfo queryInfo){
		JSONObject jo = new JSONObject();
		if(location!=null){
			JSONArray ja = new JSONArray();
			PageObject pager = locationDao.querySportFriend(location.getClientId(), queryInfo);
			ClientInfo clientInfo = clientInfoDao.load(location.getClientId());
				
			double lon = location.getLongitude();
			double lat = location.getLatitude();
				
			List<Object> lst = pager.getDatas();
			if(!CollectionUtils.isEmpty(lst)){
				//约跑好友数量
				int runFriendCount = pager.getTotalRecord();
				//在约跑好友中的排名
				int sort = 0;
				List<Object> lstFriend = friendDao.queryFriendSort(location.getClientId());
				List<Object> lstMySport = friendDao.queryMySportSort(location.getClientId());
				
				double myDistanceTotal = 0;
				if(!CollectionUtils.isEmpty(lstMySport)){
					Object[] lstObj = (Object[])lstMySport.get(0);
					myDistanceTotal = Double.parseDouble(lstObj[1].toString());
				}
				
				sort = calSort(lstFriend, myDistanceTotal);
				
				for (Object objs : lst) {
					Object[] oo = (Object[]) objs;
					ClientLocation os = (ClientLocation) oo[0];
					if(os!= null&&!os.getClientId().equals(location.getClientId())){
						JSONObject json = new JSONObject();
						json.put("clientId", os.getClientId());
						json.put("longitude", os.getLongitude());
						json.put("latitude", os.getLatitude());
						json.put("location", os.getLocation());
						
						if(oo[1] != null){
							json.put("name", oo[1].toString());
						}else{
							json.put("name", "");
						}
						if(oo[2]!= null){
							json.put("gender", Integer.parseInt(oo[2].toString()));
						}else{
							json.put("gender", 0);	
						}
						if(oo[3] != null){
							json.put("mobile", oo[3].toString());
						}else{
							json.put("mobile", "");
						}
						if(oo[4] != null){
							json.put("nickName", oo[4].toString());
						}else{
							json.put("nickName", "");
						}
						String base = SystemConfig.getString("image_base_url");
						if(oo[5] != null){
							json.put("headPortrait", base + oo[5].toString());
						}else{
							json.put("headPortrait", "");
						}
						
						if(clientInfo != null){
							json.accumulate("mgender", clientInfo.getGender());
							json.accumulate("mname", clientInfo.getName());
						}
						if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getHeadPortrait())){
							json.put("mheadPortrait", base+clientInfo.getHeadPortrait());
						}else{
							json.put("mheadPortrait", "");
						}
						if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getNickName())){
							json.put("mnickName", clientInfo.getNickName());
						}else{
							json.put("mnickName", "");
						}
						
						double dis = distanceByLngLat(lon, lat, os.getLongitude(), os.getLatitude());
						
						if(dis != 0){
							json.put("distance", dis/1000);
						}else{
							json.put("distance", 0);
						}
						
						if(sort==0){
							sort = runFriendCount+1;
						}
						json.put("sort",sort);
						json.put("runFriendCount",runFriendCount);
						
						ja.add(json);
					}
				}
				jo.put("code", 1);
				jo.put("msg", "成功");
				jo.put("data", ja);
			}else{
				jo.put("code", 1);
				jo.put("msg", "没有好友");
				jo.put("data", "");
			}
		}
		return jo.toString();
	}
	
	
	
	public static double[] getAround(double lat,double lon,double raidus){   
        
		double PI = 3.14159265;
		
		double latitude = lat;   
		double longitude = lon;   
           
		double degree = (24901*1609)/360.0;   
        double raidusMile = raidus;   
           
        double dpmLat = 1/degree;   
        double radiusLat = dpmLat*raidusMile;   
        double minLat = latitude - radiusLat;   
        double maxLat = latitude + radiusLat;   
           
        double mpdLng = degree*Math.cos(latitude * (PI/180));   
        double dpmLng = 1 / mpdLng;   
        double radiusLng = dpmLng*raidusMile;   
        double minLng = longitude - radiusLng;   
        double maxLng = longitude + radiusLng;   
        System.out.println(minLat+","+minLng+","+maxLat+","+maxLng);   
        return new double[]{minLat,minLng,maxLat,maxLng};   
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
	 
	 /**计算两个日期之间相差的天数**/
	 public static int daysBetween(Date smdate,Date bdate) throws ParseException {    
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        smdate=sdf.parse(sdf.format(smdate));  
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));           
	 }
	 
	 public int calSort(List<Object> lstLonLat,double myDistanceTotal){
		 int sort = 0;
		 if(!CollectionUtils.isEmpty(lstLonLat)){
				for (int i = 0; i < lstLonLat.size(); i++) {
					Object[] lstObj = (Object[])lstLonLat.get(i);
					if(lstObj != null){
						Double distanceTotal = Double.parseDouble(lstObj[1].toString());
						if(myDistanceTotal>distanceTotal){
							sort = i+1;
							return sort;
						}
					}
				}
			}
		 return sort;
	 }
}
