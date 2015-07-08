package com.bskcare.ch.util.jpush;

import cn.jpush.api.DeviceEnum;
import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

public class JpushUtilV2 {
	/***
	JPushClient jpush = new JPushClient("3c3d207c463b2085e58f8653",
			"ffd996afad400575cbfa91ba", DeviceEnum.Android);**/
	JPushClient jpush = new JPushClient(
			"09444979fff597dacd012d60","e8d687333e5f162c890a70b1", DeviceEnum.Android);
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
	 * 通知    表头  您的亲友的血压信息
	 * @param tag
	 * @param msgTitle
	 * @param msgContent
	 */
	public void send(String tag,String msgTitle,String msgContent) {
		try{
			MessageResult msgResult1 = jpush.sendNotificationWithTag(sendNo, tag,msgTitle, msgContent);
			if (null != msgResult1) {
				if (msgResult1.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送成功， sendNo=" + msgResult1.getSendno());
				} else {
					System.out.println("发送失败， 错误代码=" + msgResult1.getErrcode()
							+ ", 错误消息=" + msgResult1.getErrmsg());
				}
			} else {
				System.out.println("无法获取数据");
			}
		}catch (Exception e) {
			System.out.println("极光推送失败！");
		}
	}
	/**
	 * 消息发送到版本v2
	 * @param tag
	 * @param msgTitle
	 * @param msgContent
	 */
	public void sendMessageV2(String tag,String msgTitle,String msgContent,String notMessgae) {
		try{
			JPushClient jpush = new JPushClient(
					"09444979fff597dacd012d60","e8d687333e5f162c890a70b1", DeviceEnum.Android);
			
			System.out.println("通知tag:"+tag+"msgTitle:"+msgTitle+"msgContent:"+msgContent+"notMessgae:"+notMessgae);
			MessageResult msgResultNot = jpush.sendNotificationWithTag(sendNo, tag,msgTitle, notMessgae);
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
			
			System.out.println("信息tag:"+tag+"msgTitle:"+msgTitle+"msgContent:"+msgContent+"notMessgae:"+notMessgae);
	
			MessageResult msgResult1 = jpush.sendCustomMessageWithTag(sendNo, tag,msgTitle, msgContent);
			
			if (null != msgResult1) {
				if (msgResult1.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送消息成功， sendNo=" + msgResult1.getSendno()+"tag="+tag);
				} else {
					System.out.println("发送消息失败， 错误代码=" + msgResult1.getErrcode()
							+ ", 错误消息=" + msgResult1.getErrmsg()+"tag="+tag);
				}
			} else {
				System.out.println("无法获取数据");
			}
		}catch (Exception e) {
			System.out.println("极光推送失败！");
		}

	}
	/**
	 * 报警推送
	 * @param args
	 */
	public void sendAlarm(String msgTitleMessage){
		try{
			JPushClient jpush = new JPushClient(
					"23a4e9d4196c9ae16488eef0","8e069f07b5236ff7f3d8a732", DeviceEnum.Android);
			
			System.out.println("报警通知tag:"+tag+"msgTitle:"+msgTitle+"msgContent:"+msgContent+"notMessgae:"+msgTitleMessage);
			
			MessageResult msgResultNot = jpush.sendNotificationWithTag(sendNo, tag,msgTitle, msgTitleMessage);
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
			System.out.println("报警，极光推送失败！");
		}
		/*****
		System.out.println("信息tag:"+tag+"msgTitle:"+msgTitle+"msgContent:"+msgContent+"notMessgae:"+notMessgae);

		MessageResult msgResult1 = jpush.sendCustomMessageWithTag(sendNo, tag,msgTitle, msgContent);
		
		if (null != msgResult1) {
			if (msgResult1.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				System.out.println("发送消息成功， sendNo=" + msgResult1.getSendno()+"tag="+tag);
			} else {
				System.out.println("发送消息失败， 错误代码=" + msgResult1.getErrcode()
						+ ", 错误消息=" + msgResult1.getErrmsg()+"tag="+tag);
			}
		} else {
			System.out.println("无法获取数据");
		}****/
	}
	
	
	
	public static void main(String[] args) {
		new JpushUtilV2().send("13366241553","sss","{'aaa','ssss'}");
	}
}
