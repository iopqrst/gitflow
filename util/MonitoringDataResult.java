package com.bskcare.ch.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bskcare.ch.vo.BloodSugar;

public class MonitoringDataResult {
	
	static Map<Integer,String> temperatureTips = null; //体温管理
	
	public static Map<Integer, String> sugarType = null;// 血糖类型
	
	static {
//		32.0℃≤T≤35℃：您好，您本次体温测量疑似体温过低，或检测方法不正确，请密切监测
//		35.1℃<T≤35.8℃：您好，您的基础体温可能偏低，或者是所处环境温度较低，请您密切测量
//		35.8℃<T≤37.4℃：您好，您本次测量的体温正常。
//		37.5℃≤T≤38℃:您好，您本次测量的体温疑似低度热，请尽快查明原因，降体温至正常范围。
//		38.1℃≤T≤39℃：您好，您本次测量的体温疑似中等度热，请尽快查明原因，降体温至正常范围。
//		39.1℃≤T≤41℃：您好，您本次测量的体温疑似高热，请尽快查明原因，降体温至正常范围。
//		41.1℃<T≤42℃：您好，您本次测量的体温疑似超高热，请尽快查明原因，降体温至正常范围。
		temperatureTips = new HashMap<Integer, String>();
		temperatureTips.put(0, "尊敬的用户：您好！您本次测量体温为：{0}，体温测量疑似体温过低，或检测方法不正确，请密切监测。");
		temperatureTips.put(1, "尊敬的用户：您好！您本次测量体温为：{0}，基础体温可能偏低，或者是所处环境温度较低，请您密切测量。");
		temperatureTips.put(2, "尊敬的用户：您好！您本次测量体温为：{0}，体温正常。");
		temperatureTips.put(3, "尊敬的用户：您好！您本次测量体温为：{0}，体温疑似低度热，请尽快查明原因，降体温至正常范围。");
		temperatureTips.put(4, "尊敬的用户：您好！您本次测量体温为：{0}，体温疑似中等度热，请尽快查明原因，降体温至正常范围。");
		temperatureTips.put(5, "尊敬的用户：您好！您本次测量体温为：{0}，体温疑似高热，请尽快查明原因，降体温至正常范围。");
		temperatureTips.put(6, "尊敬的用户：您好！您本次测量体温为：{0}，体温疑似超高热，请尽快查明原因，降体温至正常范围。");
		
		sugarType = new HashMap<Integer, String>();
		
		sugarType.put(BloodSugar.SUGAR_TYPE, "空腹");
		sugarType.put(BloodSugar.SUGAR_TYPE_2H, "餐后");
		sugarType.put(BloodSugar.SUGAR_ZAOCAN_BEFORE,"空腹");
		sugarType.put(BloodSugar.SUGAR_WUCAN_BEFORRE,"午餐前");
		sugarType.put(BloodSugar.SUGAR_WANCAN_BEFORE,"晚餐前");
		sugarType.put(BloodSugar.SUGAR_SLEEP_BEFORE,"睡前");
		sugarType.put(BloodSugar.SUGAR_LINGCHEN,"凌晨");
		sugarType.put(BloodSugar.SUGAR_ZAOCAN_AFTER,"早餐后");
		sugarType.put(BloodSugar.SUGAR_WUCAN_AFTER,"午餐后");
		sugarType.put(BloodSugar.SUGAR_WANCAN_AFTER,"晚餐后");
	}
	
	/***
	 * 根据sbp和dbp获取相应的等级
	 * @param sbp 收缩压 高压
	 * @param dbp 舒张压 低压
	 * @return 血压判别等级 （0：低血压）（1：理想血压）（2：正常数据）（3：正常偏高）（4:高血压）
	 */
	public static int getBloodPressureLevel(int sbp,int dbp){
		if(sbp<dbp){
			return -1 ;
		}else{
			//低血压
			if(sbp<=89||dbp<60){
				return 0 ;
			}
			//理想血压
			if((sbp>89&&sbp<120)&&(dbp>=60&&dbp<80)){
				return 1 ;
			}
			//正常数据
			if((sbp>89&&sbp<130)&&(dbp>=60&&dbp<85)){
				return 2 ;
			}
			//正常值偏高
			if((sbp>=130&&sbp<=139&&dbp<=89)||(sbp<=139&&dbp>=85&&dbp<=89)){
				return 3 ;
			}
			//高血压
			if(sbp>139||dbp>89){
				return 4 ;
			}
			
		}
		return -1 ;
	}
	
	public static String getHashMapValue(int key){
		HashMap<Integer,String> map = new HashMap<Integer, String>() ;
			map.put(-1,"数据异常") ;
			map.put(0,"您好！您本次测量的血压偏低，清注意身体！") ;
			map.put(1,"您好！您本次测量的血压正常，请继续保持！") ;
			map.put(2,"您好！您本次测量的血压正常，请继续保持！") ;
			map.put(3,"您好！您本次测量的血压处于正常高值，请注意身体！") ;
			map.put(4,"您好！您本次测量的血压偏高，请注意身体！") ;
		return map.get(key) ;
	}
	
	public static String getHashMapValue(int key,int sbp,int dbp){
		HashMap<Integer,String> map = new HashMap<Integer, String>() ;
			map.put(-1,"数据异常") ;
			map.put(0,"尊敬的用户：您好！您本次测量的血压，收缩压： "+sbp+"，舒张压："+dbp+"，偏低，如伴不适，请及时就医！") ;
			map.put(1,"尊敬的用户：您好！您本次测量的血压，收缩压： "+sbp+"，舒张压："+dbp+"，在正常范围，请继续保持！") ;
			map.put(2,"尊敬的用户：您好！您本次测量的血压，收缩压： "+sbp+"，舒张压："+dbp+"，在正常范围，请继续保持！") ;
			map.put(3,"尊敬的用户：您好！您本次测量的血压，收缩压： "+sbp+"，舒张压："+dbp+"，属于正常高值，请注意身体！") ;
			map.put(4,"尊敬的用户：您好！您本次测量的血压，收缩压： "+sbp+"，舒张压："+dbp+"，血压高，请注意身体！") ;
		return map.get(key) ;
	}
	
	
	/****
	 * 判断血氧操作
	 * @param bloodOxygen
	 * @param heartbeat
	 * @return
	 */
	public static String getBloodOxygenResult(int bloodOxygen,int heartbeat){
		if(bloodOxygen>=95&&heartbeat<60){
			return "尊敬的用户：您好！您本次测量血氧:"+bloodOxygen+",脉搏:"+heartbeat+"。血氧饱和度正常，脉搏慢，请注意身体！" ;
		}
		if(bloodOxygen>=95&&heartbeat>=60&&heartbeat<=100){
			return "尊敬的用户：您好！您本次测量血氧:"+bloodOxygen+",脉搏:"+heartbeat+"。血氧饱和度正常，脉搏在正常范围，请继续保持！" ;
		}
		if(bloodOxygen>=95&&heartbeat>100){
			return "尊敬的用户：您好！您本次测量血氧:"+bloodOxygen+",脉搏:"+heartbeat+"。血氧饱和度正常，脉搏快，请注意身体！" ;
		}
		if(bloodOxygen<95&&heartbeat<60){
			return "尊敬的用户：您好！您本次测量血氧:"+bloodOxygen+",脉搏:"+heartbeat+"。血氧饱和度低，脉搏慢，请注意身体！" ;
		}
		if(bloodOxygen<95&&heartbeat>=60&&heartbeat<=100){
			return "尊敬的用户：您好！您本次测量血氧:"+bloodOxygen+",脉搏:"+heartbeat+"。血氧饱和度低，脉搏在正常范围，请注意身体！" ;
		}
		if(bloodOxygen<95&&heartbeat>100){
			return "尊敬的用户：您好！您本次测量血氧:"+bloodOxygen+",脉搏:"+heartbeat+"。血氧饱和度低，脉搏快，请注意身体！" ;
		}
		return "" ;		
	}
	
	/**
	 * 获取血氧等级
	 * @param bloodOxygen
	 * @param heartbeat
	 * @return 0：正常 其他异常
	 */
	public static Integer getBloodOxygenLevel(int bloodOxygen,int heartbeat){
		if(bloodOxygen>=95&&heartbeat<60){
			return 1 ;
		}
		if(bloodOxygen>=95&&heartbeat>=60&&heartbeat<=100){
			return 0 ;//正常,其他都异常
		}
		if(bloodOxygen>=95&&heartbeat>100){
			return 2 ;
		}
		if(bloodOxygen<95&&heartbeat<60){
			return 3 ;
		}
		if(bloodOxygen<95&&heartbeat>=60&&heartbeat<=100){
			return 4 ;
		}
		if(bloodOxygen<95&&heartbeat>100){
			return 5 ;
		}
		return -1 ;
	}
	
	/***
	 * 得到脉率状态
	 * @return
	 */
	public static int getHeartbeatState(int heartbeat){
		//如果血氧>=95 表示为正常
		if(heartbeat>=60&&heartbeat<=100){
			return 1 ;
			//否则血氧异常
		}else{
			return -1 ;
		}
	}
	/***
	 * 得到血氧状态
	 * @return
	 */
	public static int getBloodOxygenState(int bloodOxygen){
		//如果血氧>=95 表示为正常
		if(bloodOxygen>=95){
			return 1 ;
			//否则血氧异常
		}else{
			return -1 ;
		}
	}
	/****
	 * 血糖
	 * @param bloodSugarValue
	 * @param bloodSugarType
	 * @return
	 */
	public static String getBloodSugarResult(double bloodSugarValue,int bloodSugarType){
		
		if(bloodSugarType==1){
			if(bloodSugarValue<3.9){
				return "尊敬的用户：您好！您本次空腹血糖测量:"+bloodSugarValue+"，空腹血糖偏低，请立即进食！" ;
			}else if(bloodSugarValue>=3.9&&bloodSugarValue<6.1){
				return "尊敬的用户：您好！您本次空腹血糖测量:"+bloodSugarValue+"，空腹血糖正常，请继续保持！" ;
			}else if(bloodSugarValue>=6.1&&bloodSugarValue<7.0){
				return "尊敬的用户：您好！您本次空腹血糖测量:"+bloodSugarValue+"，空腹血糖稍高，空腹血糖受损，请改变生活方式！" ;
			}else if(bloodSugarValue>=7.0){
				return "尊敬的用户：您好！您本次空腹血糖测量:"+bloodSugarValue+"，空腹血糖高，请及时专科诊治！" ;
			}	
		}else if(bloodSugarType==2){
			if(bloodSugarValue<3.9){
				return "尊敬的用户：您好！您本次餐后2小时血糖测量:"+bloodSugarValue+"，餐后2小时血糖偏低，请注意身体！" ;
			}else if(bloodSugarValue>=3.9&&bloodSugarValue<7.8){
				return "尊敬的用户：您好！您本次餐后2小时血糖次测量:"+bloodSugarValue+"，餐后2小时血糖正常，请继续保持！" ;
			}else if(bloodSugarValue>=7.8&&bloodSugarValue<11.1){
				return "尊敬的用户：您好！您本次餐后2小时血糖次测量:"+bloodSugarValue+"，餐后2小时血糖稍高，糖耐量减低，请改变生活方式！" ;
			}else if(bloodSugarValue>=11.1){
				return "尊敬的用户：您好！您本次餐后2小时血糖次测量:"+bloodSugarValue+"，餐后2小时血糖高，请及时专科诊治！" ;
			}
		}
		
		//新增的血糖类型
		if(BloodSugar.SUGAR_ZAOCAN_BEFORE == bloodSugarType
				|| BloodSugar.SUGAR_WUCAN_BEFORRE == bloodSugarType
				|| BloodSugar.SUGAR_WANCAN_BEFORE == bloodSugarType
				|| BloodSugar.SUGAR_SLEEP_BEFORE == bloodSugarType
				|| BloodSugar.SUGAR_LINGCHEN == bloodSugarType){
			
			if(bloodSugarValue<3.9){
				return "尊敬的用户：您好！血糖高管提示您本次" +getSugarType(bloodSugarType)+ "血糖值为"+ bloodSugarValue +"，血糖偏低或疑似低血糖，请严密监控或咨询专业人士。" ;
			}else if(bloodSugarValue>=3.9&&bloodSugarValue<6.1){
				return "尊敬的用户：您好！血糖高管提示您本次" +getSugarType(bloodSugarType)+ "血糖值为"+ bloodSugarValue +"，恭喜您此次检测血糖理想，请继续保持！" ;
			}else if(bloodSugarValue>=6.1&&bloodSugarValue<7.0){
				return "尊敬的用户：您好！血糖高管提示您本次" +getSugarType(bloodSugarType)+ "血糖值为"+ bloodSugarValue +"，血糖控制良好，但不排除空腹血糖受损或糖耐量减低，请加以注意。" ;
			}else if(bloodSugarValue>=7.0){
				return "尊敬的用户：您好！血糖高管提示您本次" +getSugarType(bloodSugarType)+ "血糖值为"+ bloodSugarValue +"，血糖控制不良或疑似糖尿病，请严密监控或咨询专业人士。" ;
			}
			
		}else if(BloodSugar.SUGAR_ZAOCAN_AFTER == bloodSugarType
				|| BloodSugar.SUGAR_WUCAN_AFTER == bloodSugarType
				|| BloodSugar.SUGAR_WANCAN_AFTER == bloodSugarType){
			
			if(bloodSugarValue<3.9){
				return "尊敬的用户：您好！血糖高管提示您本次" +getSugarType(bloodSugarType)+ "血糖值为"+ bloodSugarValue +"，血糖偏低或疑似低血糖，请严密监控或咨询专业人士。" ;
			}else if(bloodSugarValue>=3.9&&bloodSugarValue<7.8){
				return "尊敬的用户：您好！血糖高管提示您本次" +getSugarType(bloodSugarType)+ "血糖值为"+ bloodSugarValue +"，恭喜您此次检测血糖理想，请继续保持！" ;
			}else if(bloodSugarValue>=7.8&&bloodSugarValue<11.1){
				return "尊敬的用户：您好！血糖高管提示您本次" +getSugarType(bloodSugarType)+ "血糖值为"+ bloodSugarValue +"，血糖控制良好，但不排除糖耐量减低，请加以注意。" ;
			}else if(bloodSugarValue>=11.1){
				return "尊敬的用户：您好！血糖高管提示您本次" +getSugarType(bloodSugarType)+ "血糖值为"+ bloodSugarValue +"，血糖控制不良或疑似糖尿病，请严密监控或咨询专业人士。" ;
			}
		}
		
		
		
		
		
		return "";		
	}
	
	/**
	 * 返回 汉字版 的血糖类型 参见map
	 * @param bloodSugarType
	 * @return
	 */
	private static String getSugarType(int bloodSugarType) {
		return sugarType.get(bloodSugarType);
	}

	/**
	 * 检测血糖 异常和正常
	 * @param bloodSugarValue
	 * @param bloodSugarType
	 * @return  -1 为异常数据 （0,1正常 其他异常）
	 * 
	 *  空腹,午餐前，晚餐前、睡前、凌晨血糖高于7    degree 2 type:6
		空腹，午餐前，晚餐前、睡前、凌晨大于6.1小于7 degree 1 type:6
		空腹，午餐前，晚餐前、睡前、凌晨血糖在3.9-6.1之间 degree 0 type:6               6
		空腹，午餐前，晚餐前、睡前、凌晨血糖低于3.9   degree -1 type:6
		 
		早、午、晚餐后两小时血糖在3.9-7.8    degree 0 type:7
		早、午、晚餐后两小时低于3.9       degree -1 type:7  
		早、午、晚餐后两小时血糖在7.8-11.1          degree 1       type:7
		早、午、晚餐后两小时大于11.1    degree 2 type:7
	 */
	public static int getBloodSugarLevel(double bloodSugarValue,int bloodSugarType){
		if(bloodSugarType == BloodSugar.SUGAR_TYPE ||
				BloodSugar.SUGAR_ZAOCAN_BEFORE == bloodSugarType
				|| BloodSugar.SUGAR_WUCAN_BEFORRE == bloodSugarType
				|| BloodSugar.SUGAR_WANCAN_BEFORE == bloodSugarType
				|| BloodSugar.SUGAR_SLEEP_BEFORE == bloodSugarType
				|| BloodSugar.SUGAR_LINGCHEN == bloodSugarType){ //空腹\早餐、午餐前，晚餐前、睡前、凌晨
			
			if(bloodSugarValue<3.9){
				return -1 ;//异常
			}else if(bloodSugarValue>=3.9&&bloodSugarValue<6.1){
				return 0 ;//正常
			}else if(bloodSugarValue>=6.1&&bloodSugarValue<7.0){
				return 1 ;//正常偏高
			}else if(bloodSugarValue>=7.0){
				return 2 ;//异常
			}	
		}else if(BloodSugar.SUGAR_TYPE_2H == bloodSugarType
			  || BloodSugar.SUGAR_ZAOCAN_AFTER == bloodSugarType
				|| BloodSugar.SUGAR_WUCAN_AFTER == bloodSugarType
				|| BloodSugar.SUGAR_WANCAN_AFTER == bloodSugarType
				){ //餐后2h
			if(bloodSugarValue<3.9){
				return -1 ;//异常
			}else if(bloodSugarValue>=3.9&&bloodSugarValue<7.8){
				return 0 ; //正常
			}else if(bloodSugarValue>=7.8&&bloodSugarValue<11.1){
				return 1 ; //正常偏高
			}else if(bloodSugarValue>=11.1){
				return 2 ; //异常
			}	
		} 
		return -10;
		
	}
	
	/**
	 * 获取标准血糖值
	 */
	public static List<String> getSugarInfoForCmcc(double bloodSugarValue, int bloodSugarType) {
		List<String> list = new ArrayList<String>();  // [cmcc血糖类型，标准值，与标准值对比 ]
		
		list.add(convertToCmccSugarType(bloodSugarType) + "");
		
		if(bloodSugarType == BloodSugar.SUGAR_TYPE ||
				BloodSugar.SUGAR_ZAOCAN_BEFORE == bloodSugarType
				|| BloodSugar.SUGAR_WUCAN_BEFORRE == bloodSugarType
				|| BloodSugar.SUGAR_WANCAN_BEFORE == bloodSugarType
				|| BloodSugar.SUGAR_SLEEP_BEFORE == bloodSugarType
				|| BloodSugar.SUGAR_LINGCHEN == bloodSugarType){ //空腹\早餐、午餐前，晚餐前、睡前、凌晨
			
			list.add("3.9~7.0");
			//（正常0 高于1 低于2 ）
			if(bloodSugarValue < 3.9) {
				list.add("2");
			} else if (bloodSugarValue > 7.0) {
				list.add("1");
			} else {
				list.add("0");
			}
		}else if(BloodSugar.SUGAR_TYPE_2H == bloodSugarType
			  || BloodSugar.SUGAR_ZAOCAN_AFTER == bloodSugarType
				|| BloodSugar.SUGAR_WUCAN_AFTER == bloodSugarType
				|| BloodSugar.SUGAR_WANCAN_AFTER == bloodSugarType
				){ //餐后2h

			list.add("3.9~11.1");
			
			if(bloodSugarValue < 3.9) {
				list.add("2");
			} else if (bloodSugarValue > 11.1) {
				list.add("1");
			} else {
				list.add("0");
			}
		} 
		return list;
	}
	
	/***
	 * 体温等级
	 */
	public static int getTemperatureLevel(double temperature){
		int level = -1;
		if(32 <= temperature && temperature <= 35) {
			level = 0; //疑似体温过低
		} else if(35 < temperature && temperature <= 35.8) {
			level = 1; //基础体温可能偏低
		} else if(35.8 < temperature && temperature <= 37.4) {
			level = 2; //体温正常
		} else if(37.4 < temperature && temperature <= 38) {
			level = 3; //体温疑似低度热
		} else if(38 < temperature && temperature <= 39) {
			level = 4; //疑似中等度热
		} else if(39 < temperature && temperature <= 41) {
			level = 5; //疑似高热
		} else if(41 < temperature && temperature <= 42) {
			level = 6; //疑似超高热
		}
		return level;
	}
	
	/**
	 * 体温相应提示语
	 */
	public static String getTemperatureTips(int level,double temperature) {
		return null != temperatureTips.get(level) ? 
				temperatureTips.get(level).replace("{0}", temperature+"") : "";
	}
	
	/**
	 * 
	 * @param bloodSugarType
	 * @return
	 */
	private static int convertToCmccSugarType (int bloodSugarType) {
		int cmccType = -1;
//		0：默认值
//		1: 早餐空腹血糖值(指隔夜空腹8小时以上)
//		2: 午餐前血糖值(餐前，10am~1pm)
//		3: 晚餐前血糖值(餐前，4pm~6pm)
//		4: 早餐后2小时血糖值(餐后2小时，8am~10am)
//		5: 午餐后2小时血糖值(餐后2小时，12am~3pm)
//		6: 晚餐后2小时血糖值(餐后2小时，6pm~8pm)
//		7: 睡前血糖值(10pm)
//		8: 夜间(凌晨2am，或3am)
		
		switch (bloodSugarType) {
			case BloodSugar.SUGAR_ZAOCAN_BEFORE:
				cmccType = 1;
				break;
			case BloodSugar.SUGAR_ZAOCAN_AFTER:
				cmccType = 4;
				break;
			case BloodSugar.SUGAR_WUCAN_AFTER:
				cmccType = 2;
				break;
			case BloodSugar.SUGAR_WUCAN_BEFORRE:
				cmccType = 5;
				break;
			case BloodSugar.SUGAR_WANCAN_AFTER:
				cmccType = 3;
				break;
			case BloodSugar.SUGAR_WANCAN_BEFORE:
				cmccType = 6;
				break;
			case BloodSugar.SUGAR_SLEEP_BEFORE:
				cmccType = 7;
				break;
			case BloodSugar.SUGAR_LINGCHEN:
				cmccType = 8;
				break;
			default:
				cmccType = 0; //随机
				break;
		}
		
		return cmccType;
	}
}
