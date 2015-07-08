package com.bskcare.ch.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;


/**
 * 天气的工具类
 * 
 * @author chaofeng
 * 
 */
@SuppressWarnings("unchecked")
public class WeatherUtils {

	private static final Logger log = Logger.getLogger(WeatherUtils.class);
	
	/**
	 * 根据经纬度获得天气信息，格式 116.305145,39.982368 时间轴用 精度在前，纬度在后
	 * 
	 * @param location
	 * @return
	 */
	public static String getLineTaskWeather(String location,String city) {
		System.out.println("请求接口,参数："+location);
		log.info(LogFormat.f("请求接口"));
		String weather = "";
		try {
//			//百度接口限制5000
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			if (!StringUtils.isEmpty(location)) {
				// 获取百度天气
				String url = "http://api.map.baidu.com/telematics/v3/weather?location="
						+ location + "&output=json&ak=XbzHeVKdorVig9gjy2q2Ye94";
				System.out.println("获取天气信息，+url:" + url.toString());
				// + "&output=json&ak=HKgHqTwYGSDcaiVsE4DkhsuG";//备用
				String inputLine = HttpClientUtils.getContentByGet(url, "utf-8");
				
				if (!StringUtils.isEmpty(inputLine)) {
					// 接口返回的信息
					System.out.println("接口返回成功，返回值：" + inputLine);
					Map<String, Object> mapInfo = new HashMap<String, Object>();
					mapInfo = JsonUtils.getMap4Json(inputLine);
					
					if(null == mapInfo || null == mapInfo.get("results")) {
						return "";
					}

					List<JSONObject> result = JsonUtils.getList4Json(mapInfo
							.get("results").toString(), JSONObject.class);
					Map<String, Object> resultMap = new HashMap<String, Object>();

					resultMap = JsonUtils.getMap4Json(result.get(0).toString());

					// 地区
					weather += "地区:" + resultMap.get("currentCity") + "\n";
					// pm2.5
					weather += "今日pm2.5:" + resultMap.get("pm25") + "\n";
					// 天气情况
					List<JSONObject> weather_data = JsonUtils.getList4Json(
							resultMap.get("weather_data").toString(),
							JSONObject.class);
					// 今日天气
					weather += "今日天气:"
							+ weather_data.get(0).getString("weather") + "\n";
					weather += "今日风力:" + weather_data.get(0).getString("wind")
							+ "\n";
					weather += "今日温度:"
							+ weather_data.get(0).getString("temperature")
							+ "\n";
					
					if(null != resultMap.get("index") 
							&& !StringUtils.isEmpty(resultMap.get("index").toString())) {
						weather += "温馨小提示:";
						// 生活提示
						List<JSONObject> live = JsonUtils.getList4Json(resultMap
								.get("index").toString(), JSONObject.class);
						
						for (JSONObject jsonObject : live) {
							if(!jsonObject.getString("title").equals("紫外线强度")&&
									!jsonObject.getString("title").equals("洗车")){
								weather += "" + jsonObject.getString("des") + "\n";
							}
						}
					}
					log.info(LogFormat.f("最后返回的信息" + weather));

				}
			}
		} catch (Exception e) {
			log.error(">>>>>>>>>> 获取天气预报失败");
			e.printStackTrace();
		}
		return weather;
	}

	public static void main(String[] args) {
		long beginTime= new Date().getTime();
		for (int i = 0; i < 50; i++) {
			printWeather();
		}
		long endTime= new Date().getTime();
		System.out.println(endTime-beginTime);
	}

	public static void printWeather() {
		String weather = "";
		try {

			// 获取百度天气
			String url = "http://api.map.baidu.com/telematics/v3/weather?location=116.486835,39.888217" +
					"&output=json&ak=XbzHeVKdorVig9gjy2q2Ye94";
			System.out.println("获取天气信息，+url:" + url.toString());
			// + "&output=json&ak=HKgHqTwYGSDcaiVsE4DkhsuG";//备用
			String inputLine = HttpClientUtils.getContentByGet(url, "utf-8");
			if (!StringUtils.isEmpty(inputLine)) {
				// 接口返回的信息
				System.out.println("接口返回成功，返回值：" + inputLine);
				Map<String, Object> mapInfo = new HashMap<String, Object>();
				mapInfo = JsonUtils.getMap4Json(inputLine);

				List<JSONObject> result = JsonUtils.getList4Json(mapInfo.get(
						"results").toString(), JSONObject.class);
				Map<String, Object> resultMap = new HashMap<String, Object>();

				resultMap = JsonUtils.getMap4Json(result.get(0).toString());

				// 地区
				weather += "地区:" + resultMap.get("currentCity") + "\n";
				// pm2.5
				weather += "今日pm2.5:" + resultMap.get("pm25") + "\n";
				// 生活提示
				List<JSONObject> live = JsonUtils.getList4Json(resultMap.get(
						"index").toString(), JSONObject.class);
				// 天气情况
				List<JSONObject> weather_data = JsonUtils.getList4Json(
						resultMap.get("weather_data").toString(),
						JSONObject.class);
				weather += "\n";
				for (JSONObject jsonObject : weather_data) {

					weather += "日期:" + jsonObject.getString("date") + "\n";
					weather += "天气:" + jsonObject.getString("weather") + "\n";
					weather += "风力:" + jsonObject.getString("wind") + "\n";
					weather += "温度:" + jsonObject.getString("temperature")
							+ "\n";
					weather += "\n";

				}
				weather += "\n";
				weather += "温馨小提示:";
				for (JSONObject jsonObject : live) {
					weather += "" + jsonObject.getString("des") + "\n";
				}
				System.out.println("最后返回的信息" + weather);

			}

		} catch (Exception e) {
			System.out.println("获取天气失败");
			e.printStackTrace();
		}
	}
}
