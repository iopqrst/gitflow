package com.bskcare.ch.util.jpush;

import java.util.List;

import com.bskcare.ch.bo.FamilyName;
import com.bskcare.ch.bo.JpushList;
import com.bskcare.ch.vo.ClientInfo;

/**
 * 激光推送参数 创建队列用
 * 
 * @author chaofeng
 * 
 */
public class JpushParameter {
	/** 报警系统 推送信息 */
	public static final int TYPE_ALARMJPUSHUTIL = 1;
	/** 给用户发信息 */
	public static final int TYPE_SENDCHATMESSAGE = 2;
	/** 动动 */
	public static final int TYPE_DONGDONGSENDCHATMESSAGE = 3;
	/** 群推信息 */
	public static final int TYPE_SENDALLMAN = 4;
	/** 动动添加好友推送 */
	public static final int TYPE_SPORTADDFRIEND = 5;
	/** 亲情号码推送信息 */
	public static final int TYPE_SENDFAMILYJPUSH = 6;

	private int soft;
	private String tag;
	private String pushTitle;
	private String pushContent;
	private int type;
	private List<FamilyName> flist;
	private ClientInfo ci;
	private JpushList model;

	public JpushParameter(int soft, String tag, String pushTitle,
			String pushContent, int type, List<FamilyName> flist,
			ClientInfo ci, JpushList model) {
		this.soft = soft;
		this.tag = tag;
		this.pushTitle = pushTitle;
		this.pushContent = pushContent;
		this.type = type;
		this.flist = flist;
		this.ci = ci;
		this.model = model;
	}

	public int getSoft() {
		return soft;
	}

	public void setSoft(int soft) {
		this.soft = soft;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPushTitle() {
		return pushTitle;
	}

	public void setPushTitle(String pushTitle) {
		this.pushTitle = pushTitle;
	}

	public String getPushContent() {
		return pushContent;
	}

	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<FamilyName> getFlist() {
		return flist;
	}

	public void setFlist(List<FamilyName> flist) {
		this.flist = flist;
	}

	public ClientInfo getCi() {
		return ci;
	}

	public void setCi(ClientInfo ci) {
		this.ci = ci;
	}

	public JpushList getModel() {
		return model;
	}

	public void setModel(JpushList model) {
		this.model = model;
	}

}
