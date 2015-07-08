package com.bskcare.ch.util.jpush;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import net.sf.json.JSONObject;
import cn.jpush.api.DeviceEnum;
import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.IOSExtra;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.bskcare.ch.bo.FamilyName;
import com.bskcare.ch.bo.JpushList;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
@SuppressWarnings("unchecked")
public class JpushUtil {
	/***
	JPushClient jpush = new JPushClient("3c3d207c463b2085e58f8653",
			"ffd996afad400575cbfa91ba", DeviceEnum.Android);**/
//	JPushClient jpush = new JPushClient(
//			"5ec7000103cf53ce0ae95fc5","ec07696d2325e52fa3b68fff", DeviceEnum.Android);
	// jpush.setEnableSSL(true);
	
	// 在实际业务中，建议 sendNo 是一个你自己的业务可以处理的一个自增数字。
	// 除非需要覆盖，请确保不要重复使用。详情请参考 API 文档相关说明。
	int sendNo = getRandomSendNo();
	String imei = "864687011708848";
	String msgTitle = "jpush";
	String msgContent = "";
	String alias = "";
	String tag = "";
	public static final int MAX = Integer.MAX_VALUE;
	public static final int MIN = MAX / 2;

	/**
	 * 保持 sendNo 的唯一性是有必要的 It is very important to keep sendNo unique.
	 * 
	 * @return sendNo
	 */
	public static int getRandomSendNo() {
		return (int) (MIN + Math.random() * (MAX - MIN));
	}

	/**
	 * 给亲情好友推送信息
	 * @param tag 手机号
	 * @param msgTitle 标题
	 * @param model
	 * @param ci 
	 */
	public void sendMessageV2(String tag,String msgTitle,JpushList model,String notMessgae, ClientInfo ci) {
		
		try{
			JPushClient jpushV2 = new JPushClient("09444979fff597dacd012d60","e8d687333e5f162c890a70b1", DeviceEnum.Android);
			
			JSONObject jo = new JSONObject();
			jo.put("title", "亲情号推送") ;
			jo.put("type", model.getType()) ;
			jo.put("cid", ci.getId()) ;
			jo.put("phone", ci.getMobile()) ;
			jo.put("name", ci.getName()==null?"":ci.getName()) ;
			
			MessageResult msgResult1 = jpushV2.sendCustomMessageWithTag(sendNo, tag,msgTitle, jo.toString());
			if (null != msgResult1) {
				if (msgResult1.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送V2消息成功， sendNo=" + msgResult1.getSendno()+"tag="+tag);
				} else {
					System.out.println("发送V2消息失败， 错误代码=" + msgResult1.getErrcode()
							+ ", 错误消息=" + msgResult1.getErrmsg()+"tag="+tag);
				}
			} else {
				System.out.println("V2无法获取数据");
			}
		}catch (Exception e) {
			System.out.println("给亲情好友推送信息，极光推送失败！");
		}
	}	
	
	/**
	 * 报警系统      推送信息
	 * @param titleMessage
	 */
	public void alarmJpushUtil(String titleMessage){
		
		if(StringUtils.isDevelopment()) {
			System.out.println(LogFormat.f("当前环境为开发环境，不推送报警信息。"));
			return ;
		}
		
		try{
			JPushClient jpush = new JPushClient("23a4e9d4196c9ae16488eef0","8e069f07b5236ff7f3d8a732", DeviceEnum.Android);
			
			System.out.println("报警通知tag:"+tag+"#msgTitle:"+msgTitle+"#msgContent:"+msgContent+"#notMessgae:"+titleMessage);
			//MessageResult msgResultNot = jpush.sendNotificationWithTag(sendNo, tag,msgTitle, titleMessage);
			//MessageResult msgResultNot = jpush.sendNotificationWithAppKey(sendNo, titleMessage, titleMessage) ;
			MessageResult msgResultNot = jpush.sendCustomMessageWithAppKey(sendNo, titleMessage, titleMessage) ;
			
			if (null != msgResultNot) {
				if (msgResultNot.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送通知成功， sendNo=" + msgResultNot.getSendno()+"tag="+tag);
				} else {
					System.out.println("发送通知失败， 错误代码=" + msgResultNot.getErrcode()
							+ ", 错误消息=" + msgResultNot.getErrmsg()+"tag="+tag);
				}
			} else {
				System.out.println("无法获取数据");
			}
		}catch (Exception e) {
			System.out.println("报警系统，极光推送失败！");
//			e.printStackTrace();
		}
		
	}
	
	public String sendChatMessage(String tags, String msgTitle, String msgContent, int soft) {
		String result = "error";
		try {
			JPushClient jpushV2 = null;
			if(Constant.SOFT_YUN_PLANT == soft) {
				//健康管家
				jpushV2 = new JPushClient("09444979fff597dacd012d60","e8d687333e5f162c890a70b1");
			} else if(Constant.SOFT_GUAN_XUE_TANG == soft) {
				//管血糖
				jpushV2 = new JPushClient("f2548db791b05d986d674add","f15acb7b57d4d4da2e766841");
			} else if(Constant.SOFT_GUAN_XUE_YA == soft) {
				//管血糖
				jpushV2 = new JPushClient("","");
			} else if(Constant.SOFT_RUABISH == soft) { //TEMP 临时
				jpushV2 = new JPushClient("eccfdfe4d291f0b7453ecef5","ae7b069e7f5a5ca052071b1a");
			}
			MessageResult msgResult1 = jpushV2.sendCustomMessageWithTag(sendNo, tags, msgTitle, msgContent);
			Map<String, Object> map = new HashMap<String, Object>();
			map = JsonUtils.getMap4Json(msgContent);
			Map<String, Object> mapResult = new HashMap<String, Object>();
			mapResult.put("uid", map.get("uid"));
			mapResult.put("ename", map.get("ename"));
			mapResult.put("createTime", map.get("createTime"));
			mapResult.put("title", "在线咨询");
			MessageResult msgResult2 = jpushV2.sendNotificationWithTag(sendNo, tags, "", msgTitle, 0, mapResult);
			
			if (null != msgResult1 && msgResult2 != null) {
			//if (null != msgResult1) {
				if (msgResult1.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送V2消息成功， sendNo=" + msgResult1.getSendno()+"tags="+tags);
					System.out.println("发送V3消息成功， sendNo=" + msgResult2.getSendno()+"tags="+tags);
				} else {
					System.out.println("发送V2消息失败， 错误代码=" + msgResult1.getErrcode()
							+ ", 错误消息=" + msgResult1.getErrmsg()+"tag="+tag);
				}
				result = "success";
			} else {
				System.out.println("V2无法获取数据");
			}
			
		} catch (Exception e) {
			System.out.println("又他妈发送失败，什么也不说了！！");
			result = "exception";
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 推送糖糖咨询
	 * @param title 咨询名字
	 * @param id 咨询id
	 */
	public void sendTTZiXun(String title, String id) {
		JPushClient jpush = new JPushClient("f2548db791b05d986d674add","f15acb7b57d4d4da2e766841", DeviceEnum.Android);
		//Android 推送信息
		MessageResult mr1 = jpush.sendCustomMessageWithAppKey(sendNo, title, "{\"title\":\"糖糖资讯推送\",\"id\":\""+ id +"\"}");
//		MessageResult mr1 = jpush.sendCustomMessageWithTag(sendNo, "18612834872", title, "{\"title\":\"糖糖资讯推送\",\"id\":\""+ id +"\"}");
		
		if (null != mr1) {
			if (mr1.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				System.out.println("给andorid推送资讯成功， sendNo=" + mr1.getSendno()+ "tag=" + tag);
			} else {
				System.out.println("给andorid发送资讯失败， 错误代码=" + mr1.getErrcode()+ ", 错误消息="
						+ mr1.getErrmsg() + "tag=" + tag);
			}
		} else {
			System.out.println("andorid资讯无法获取数据");
		}
		
		JPushClient iosPush = new JPushClient("f2548db791b05d986d674add","f15acb7b57d4d4da2e766841", DeviceEnum.IOS);
		//ios 推送信息
		Map<String, Object> mapResult = new HashMap<String, Object>();
		mapResult.put("id", id);
		mapResult.put("title", "糖糖资讯");
		IOSExtra iosExtra = new IOSExtra(1, "Windows_Logon_Sound.wav");
		mapResult.put("ios", iosExtra);
		
		MessageResult mr2 = iosPush.sendNotificationWithAppKey(sendNo, "糖糖资讯", title, 0, mapResult);
//		MessageResult mr2 = iosPush.sendNotificationWithTag(sendNo,"18612834872", "糖糖资讯", title, 0, mapResult);
		
		if (null != mr2) {
			if (mr2.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				System.out.println("给IOS端推送资讯成功， sendNo=" + mr2.getSendno()+ "tag=" + tag);
			} else {
				System.out.println("给IOS资讯消息失败， 错误代码=" + mr2.getErrcode()+ ", 错误消息=" 
						+ mr2.getErrmsg() + "tag=" + tag);
			}
		} else {
			System.out.println("IOS资讯无法获取数据");
		}
		
	}
	
	/**
	 * 选择用户推送激光信息
	 * @param tags
	 * @param content
	 * @return
	 */
	public void pushNotification(String tags, String content) {
		
		System.out.println("tags = " + tags + " ; content = " + content);
		
		JPushClient jpush = new JPushClient("f2548db791b05d986d674add","f15acb7b57d4d4da2e766841", DeviceEnum.Android);
		//Android 推送信息
		MessageResult mr1 = jpush.sendCustomMessageWithTag(sendNo, tags, "您有一条新的通知信息", "{\"title\":\"血糖高管通知\"}");
		
//		MessageResult mr1 = jpush.sendNotificationWithTag(sendNo, tags, "血糖高管通知", "您有一条新的通知信息");
		
		if (null != mr1) {
			if (mr1.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				System.out.println("给andorid推送资讯成功， sendNo=" + mr1.getSendno()+ "tag=" + tag);
			} else {
				System.out.println("给andorid发送资讯失败， 错误代码=" + mr1.getErrcode()+ ", 错误消息="
						+ mr1.getErrmsg() + "tag=" + tag);
			}
		} else {
			System.out.println("andorid资讯无法获取数据");
		}
		
		JPushClient iosPush = new JPushClient("f2548db791b05d986d674add","f15acb7b57d4d4da2e766841", DeviceEnum.IOS);
		//ios 推送信息
		Map<String, Object> mapResult = new HashMap<String, Object>();
		mapResult.put("title", "血糖高管通知");
		IOSExtra iosExtra = new IOSExtra(1, "Windows_Logon_Sound.wav");
		mapResult.put("ios", iosExtra);
		
		MessageResult mr2 = iosPush.sendNotificationWithTag(sendNo, tags, "血糖高管通知", "您有一条新的通知信息", 0, mapResult);
		
		if (null != mr2) {
			if (mr2.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				System.out.println("给IOS端推送资讯成功， sendNo=" + mr2.getSendno()+ "tag=" + tag);
			} else {
				System.out.println("给IOS资讯消息失败， 错误代码=" + mr2.getErrcode()+ ", 错误消息=" 
						+ mr2.getErrmsg() + "tag=" + tag);
			}
		} else {
			System.out.println("IOS资讯无法获取数据");
		}
	}
	
	/***
	 * 动动
	 */
	public String dongDongSendChatMessage(String tags, String msgTitle, String msgContent) {
		String result = "error";
		try {
			
			JPushClient jpushV2 = new JPushClient("eceafc42d933066600b8e61b","c07fb87ae03cbc6dedd56bf5", DeviceEnum.Android);
			MessageResult msgResult1 = jpushV2.sendCustomMessageWithTag(sendNo, tags, msgTitle, msgContent);
			
			JPushClient jpushV3 = new JPushClient("75f58961cc75ebf3f1250199","4f1e825e4cff1cefd1067057", DeviceEnum.IOS);
			//MessageResult msgResult2 = jpushV3.sendNotificationWithTag(sendNo, tags, msgTitle, msgContent);
			JPushClient jpushV4 = new JPushClient("c3bd6e89432275dd6387c8c2","15d11781e2d6bde0940855ca", DeviceEnum.IOS);
			Map<String, Object> map = new HashMap<String, Object>();
			map = JsonUtils.getMap4Json(msgContent);
			Object rmsg = map.get("msg").toString();
			String msg = "";
			if(rmsg != null){
				msg = rmsg.toString();
			}
			
			Object rname = map.get("name");
			String name = "";
			if(rname != null){
				name = rname.toString();
			}
			if(StringUtils.isEmpty(name)){
				name = map.get("mobile").toString();
			}
			
			msg = name+"：给您发送了新消息";
			
			map.remove("msg");
			map.remove("rname");
			map.remove("headPortrait");
			map.remove("gender");
			map.remove("rgender");
			map.remove("nickName");
			map.remove("headPortrait");
			map.remove("createTime");
			
			MessageResult msgResult2 = jpushV3.sendNotificationWithTag(sendNo, tags, msgTitle, msg, 0, map);
			MessageResult msgResult3 = jpushV4.sendNotificationWithTag(sendNo, tags, msgTitle, msg, 0, map);
			
			if (null != msgResult1&&msgResult2!=null&&msgResult3!=null) {
				if (msgResult1.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送V2消息成功， sendNo=" + msgResult1.getSendno()+"，tags="+tags);
				} else {
					System.out.println("发送V2消息失败， 错误代码=" + msgResult1.getErrcode()
							+ "，错误消息=" + msgResult1.getErrmsg()+"，tag="+tag);
				}
				result = "success";
			} else {
				System.out.println("V2无法获取数据");
			}
			
		} catch (Exception e) {
			System.out.println("又他妈发送失败，什么也不说了！！");
			result = "exception";
			//e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	/**
	 *  群推信息
	 * @param titleMessage
	 */
	public int sendAllMan(String titleMessage) {
		int status = 0;
		try{
			JSONObject jo = new JSONObject();
			jo.put("title", "群推信息");
	//		JPushClient jpush = new JPushClient("e563ecc8ca58f344bce5ce4b",
	//				"92ced88dbdc90a4dfaee287f", DeviceEnum.Android);// 测试版
			JPushClient jpush = new JPushClient("09444979fff597dacd012d60",
					"e8d687333e5f162c890a70b1", DeviceEnum.Android);// 群推版
	
			System.out.println("群推通知tag:" + tag + "#msgTitle:" + msgTitle
					+ "#msgContent:" + msgContent + "#notMessgae:" + titleMessage);
			MessageResult msgResultNot = jpush.sendCustomMessageWithAppKey(sendNo,titleMessage, jo.toString());// 群推
	//		MessageResult msgResultNot = jpush.sendCustomMessageWithTag(sendNo, "18666105582", titleMessage, jo.toString());// 单推
	
			if (null != msgResultNot) {
				if (msgResultNot.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送通知成功， sendNo=" + msgResultNot.getSendno()
							+ "tag=" + tag);
					status = 0;
				} else {
					System.out.println("发送通知失败， 错误代码=" + msgResultNot.getErrcode()
							+ ", 错误消息=" + msgResultNot.getErrmsg() + "tag=" + tag);
					status = 1;
				}
			} else {
				System.out.println("无法获取数据");
				status = 1;
			}
		} catch (Exception e) {
			System.out.println("群推消息，极光推送失败！");
			//e.printStackTrace();
		}
		return status;
	}

	
	/***
	 * 动动添加好友推送
	 */
	public String sportAddFriend(String tags, String msgTitle, String msgContent) {
		String result = "error";
		try {
			
			JPushClient jpushV2 = new JPushClient("eceafc42d933066600b8e61b","c07fb87ae03cbc6dedd56bf5", DeviceEnum.Android);
			MessageResult msgResult1 = jpushV2.sendCustomMessageWithTag(sendNo, tags, msgTitle, msgContent);
			
			JPushClient jpushV3 = new JPushClient("75f58961cc75ebf3f1250199","4f1e825e4cff1cefd1067057", DeviceEnum.IOS);
			//MessageResult msgResult2 = jpushV3.sendNotificationWithTag(sendNo, tags, msgTitle, msgContent);
			
			JPushClient jpushV4 = new JPushClient("c3bd6e89432275dd6387c8c2","15d11781e2d6bde0940855ca", DeviceEnum.IOS);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map = JsonUtils.getMap4Json(msgContent);
			String msg = "";
			Object rname = map.get("nickName");
			String name = "";
			if(rname!=null&&!rname.equals("")){
				name = rname.toString();
			}else if(map.get("name")!=null&&!map.get("name").equals("")){
				name = map.get("name").toString();
			}else{
				name = map.get("mobile").toString();
			}
			
			msg = name+"：请求添加您为好友";		
			MessageResult msgResult2 = jpushV3.sendNotificationWithTag(sendNo, tags, msgTitle, msg, 0, map);
			MessageResult msgResult3 = jpushV4.sendNotificationWithTag(sendNo, tags, msgTitle, msg, 0, map);
			
			if (null != msgResult1&&msgResult2!=null&&msgResult3!=null) {
				if (msgResult1.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送V2消息成功， sendNo=" + msgResult1.getSendno()+"，tags="+tags);
				} else {
					System.out.println("发送V2消息失败， 错误代码=" + msgResult1.getErrcode()
							+ "，错误消息=" + msgResult1.getErrmsg()+"，tag="+tag);
				}
				result = "success";
			} else {
				System.out.println("V2无法获取数据");
			}
			
		} catch (Exception e) {
			System.out.println("又他妈发送失败，什么也不说了！！");
			result = "exception";
			//e.printStackTrace();
		}
		return result;
	}
	/**
	 * 亲情号码推送
	 * @param flist
	 * @param ci
	 * @param model
	 * @param title
	 */
	public void sendFamilyJpush(List<FamilyName> flist, ClientInfo ci,
			JpushList model, String title) {
		// 亲情账号人员 推送消息
		JpushUtil ju = new JpushUtil();
		String familyMobile = "";

		if (!CollectionUtils.isEmpty(flist)) {
			
//			log.info(">>>>>>>>>>>>>>>>>>>>>> 总计获取亲友数：" + flist.size());
			
			for (FamilyName familyName : flist) {
				if (familyName.getMobile() != null) {
					// 推送信息
					familyMobile = familyMobile + familyName.getMobile() + ",";
				}
			}
			
			if (familyMobile.substring(familyMobile.length() - 1,
					familyMobile.length()).equals(",")) {
				familyMobile = familyMobile.substring(0,
						familyMobile.length() - 1);
			}
			// JpushList model = new JpushList() ;
			// model.setMoblie(mobile) ;
			// model.setTestdate(testDate) ;
			// model.setContent(result) ;
			// model.setType(type) ;

//			ju.sendMessage(familyMobile, "", JsonUtils.getJsonString4JavaPOJO(
//					model, "yyyy-MM-dd HH:mm:ss"), title); //老板软件推送信息

			// String mode2 = "亲情号推送" ;
			ju.sendMessageV2(familyMobile, title, model, title, ci);
		
			
		}
	}

	public static void main(String[] args) {
		//new JpushUtil().send("13366241553","sss","{'aaa','ssss'}");
		
		//new JpushUtil().alarmJpushUtil("警报") ;
		//new JpushUtil().alarmJpushUtil("国徽") ;
		//new JpushUtil().sendMessage("18701046848", "tile", "信息啊啊啊 ","中国aaaaaaa啊啊啊1111") ;
		
//		String msg = "{\"title\":\"在线咨询推送\",\"uid\":\"6\",\"ename\":\"方向花\",\"portrait\":\"http://127.0.0.1:8080/bskimages/image/portrait/20131016/20131016120650_60.jpg\",\"msg\":\"我的名字叫可可汗\",\"createTime\":\"2013-12-30 13:58:22\"}";
//		new JpushUtil().sendChatMessage("18666105582", "侯志清医生给你回复信息了", msg, 1);
		
//		String msg = "{\"title\":\"约跑聊天\",\"uid\":\"10033\",\"msg\":\"我的名字叫\",\"createTime\":\"2013-01-14 11:27:22\"}";
//		String msg = "{\"title\":\"约跑聊天\",\"uid\":\"10033\",\"nickName\":\"我的\",\"msg\":\"我的名字叫\",\"name\":\"旺旺\",\"rname\":\"李璐\",\"gender\":\"0\",\"rgender\":\"1\",\"mobile\":\"13718725223\",\"rmobile\":\"15033345680\",\"createTime\":\"2013-01-14 11:27:22\"}";
//		new JpushUtil().dongDongSendChatMessage("18666105582", "消息", msg);
//		JPushClient jpushV2 = new JPushClient("f2548db791b05d986d674add","f15acb7b57d4d4da2e766841");
//	
//	
//		MessageResult msgResult1 = jpushV2.sendCustomMessageWithTag(getRandomSendNo(), "18510798873", "title", "1234");
//		if (null != msgResult1) {
//			if (msgResult1.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
//				System.out.println("发送V2消息成功， sendNo=" + msgResult1.getSendno()+"tags=");
//			} else {
//				System.out.println("发送V2消息失败， 错误代码=" + msgResult1.getErrcode()
//						+ ", 错误消息=" + msgResult1.getErrmsg()+"tag=");
//			}
//			
//		} else {
//			System.out.println("V2无法获取数据");
//		}
		
//		new JpushUtil().sendTTZiXun("这是一条糖糖资讯2", "1");
		new JpushUtil().pushNotification("13521980792", "您有一条咨询信息，巴拉巴拉");
		new JpushUtil().pushNotification("13520941632", "您有一条咨询信息，巴拉巴拉");
		new JpushUtil().pushNotification("18612834872", "您有一条咨询信息，巴拉巴拉");
	}
}
