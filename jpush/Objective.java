package com.bskcare.ch.util.jpush;

public class Objective {
	private JpushParameter jpushParameter;

	public Objective(JpushParameter jpushParameter) {
		this.jpushParameter = jpushParameter;

	}

	public void sendJpush() {
		JpushUtil ju = new JpushUtil();
		if (jpushParameter != null && jpushParameter.getType() != 0) {
			if (jpushParameter.getType() == JpushParameter.TYPE_ALARMJPUSHUTIL) {
				ju.alarmJpushUtil(jpushParameter.getPushContent());// 数据预警，传入一个字符串
			}
			if (jpushParameter.getType() == JpushParameter.TYPE_DONGDONGSENDCHATMESSAGE) {// 动动更健康推动
				ju.dongDongSendChatMessage(jpushParameter.getTag(),
						jpushParameter.getPushTitle(), jpushParameter
								.getPushContent());
			}
			if (jpushParameter.getType() == JpushParameter.TYPE_SENDALLMAN) {
				ju.sendAllMan(jpushParameter.getPushTitle());// 群推消息，插入一个标题
			}
			if (jpushParameter.getType() == JpushParameter.TYPE_SENDCHATMESSAGE) {// 推送给客户
				ju.sendChatMessage(jpushParameter.getTag(), jpushParameter
						.getPushTitle(), jpushParameter.getPushContent(),
						jpushParameter.getSoft());
			}
			if (jpushParameter.getType() == JpushParameter.TYPE_SPORTADDFRIEND) {
				ju.sportAddFriend(jpushParameter.getTag(), jpushParameter
						.getPushTitle(), jpushParameter.getPushContent());
			}
			// 亲情号码推送信息
			if (jpushParameter.getType() == JpushParameter.TYPE_SENDFAMILYJPUSH) {
				ju.sendFamilyJpush(jpushParameter.getFlist(), jpushParameter
						.getCi(), jpushParameter.getModel(), jpushParameter
						.getPushTitle());
			}
		}
		// ju.sendChatMessage(jpushParameter.getTag(),
		// jpushParameter.getPushTitle(), jpushParameter.getPushContent(),
		// jpushParameter.getSoft());
	}
}
