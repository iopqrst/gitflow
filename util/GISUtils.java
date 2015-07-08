package com.bskcare.ch.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

@SuppressWarnings("unchecked")
public class GISUtils {
	/**
	 * 根据经纬度获得城市名称和街道
	 * 	 * 失败返回 fail
	 */
	
	public static String getLocationBylocation(double longitude,double latitude){
		String location = "";
		try{
			String url = "http://api.map.baidu.com/geocoder/v2/?ak=XbzHeVKdorVig9gjy2q2Ye94" +
	 		"&location="+latitude+","+longitude+"&output=json&pois=0";
		
			String inputLine = HttpClientUtils.getContentByGet(url, "utf-8");
			
			if(!StringUtils.isEmpty(inputLine)){
				Map<String, Object> mapInfo = new HashMap<String, Object>();
				mapInfo = JsonUtils.getMap4Json(inputLine);
				String result = mapInfo.get("result").toString();
				Map<String, Object> map = new HashMap<String, Object>();
				map = JsonUtils.getMap4Json(result);
				if(!CollectionUtils.isEmpty(map)){
					if(!map.get("addressComponent").equals("")){
						//location = map.get("formatted_address").toString();
						Map<String, Object> smap = new HashMap<String, Object>();
						smap = JsonUtils.getMap4Json(map.get("addressComponent").toString());
						//城市
						String city = "";
						//街道
						String street = "";
						if(smap.get("city") != null){
							city = smap.get("city").toString();
						} 
						if(smap.get("street") != null){
							street = smap.get("street").toString();
						} 
						location = street+"，"+city;
					}
				}
			}
		}catch(Exception e){
			System.out.println("定位失败");
			e.printStackTrace();
			location = "fail";
		}
	    return location;
	}
	/**
	 * 根据经纬度获得城市名称
	 * 失败返回 fail
	 */
	
	public static String getCityBylocation(double longitude,double latitude){
		String city = "";
		try{
			String url = "http://api.map.baidu.com/geocoder/v2/?ak=XbzHeVKdorVig9gjy2q2Ye94" +
	 		"&location="+latitude+","+longitude+"&output=json&pois=0";
		
			String inputLine = HttpClientUtils.getContentByGet(url, "utf-8");
			
			if(!StringUtils.isEmpty(inputLine)){
				Map<String, Object> mapInfo = new HashMap<String, Object>();
				mapInfo = JsonUtils.getMap4Json(inputLine);
				String result = mapInfo.get("result").toString();
				Map<String, Object> map = new HashMap<String, Object>();
				map = JsonUtils.getMap4Json(result);
				if(!CollectionUtils.isEmpty(map)){
					if(!map.get("addressComponent").equals("")){
						//location = map.get("formatted_address").toString();
						Map<String, Object> smap = new HashMap<String, Object>();
						smap = JsonUtils.getMap4Json(map.get("addressComponent").toString());
						//城市
						city = "";
						if(smap.get("city") != null){
							city = smap.get("city").toString();
						} 
					}
				}
			}
		}catch(Exception e){
			System.out.println("定位失败");
			e.printStackTrace();
			city = "fail";
		}
	    return city;
	}
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			System.out.println(getLocationBylocation(116.486835,39.888217));
		}
	}
	
}
