package com.bskcare.ch.service.impl.online;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.OnlineRecoredExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.BskExpertDao;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.ClientLocationDao;
import com.bskcare.ch.dao.UserInfoDao;
import com.bskcare.ch.dao.online.OnlineRecoredDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.online.OnlineRecoredService;
import com.bskcare.ch.util.CompareChatTime;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.HttpClientUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.util.jpush.JpushUtil;
import com.bskcare.ch.vo.BskExpert;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ClientLocation;
import com.bskcare.ch.vo.online.OnlineRecored;

@Service
@SuppressWarnings("unchecked")
public class OnlineRecoredServiceImpl implements OnlineRecoredService {

	@Autowired
	OnlineRecoredDao orecoredDao;
	@Autowired
	BskExpertDao bskExpertDao;
	@Autowired
	ClientInfoDao clientInfoDao;
	@Autowired
	UserInfoDao userInfoDao;

	@Autowired
	private ClientLocationDao locationDao;
	
	public String queryChatRecords(OnlineRecored or, QueryInfo qi,
			QueryCondition qc) {
		PageObject<OnlineRecored> poor = orecoredDao.queryChatRecords(or, qi,
				qc);
		JSONObject joo = new JSONObject();
		if (null != poor && !CollectionUtils.isEmpty(poor.getDatas())) {
			List<OnlineRecored> orList = poor.getDatas();

			CompareChatTime cct = new CompareChatTime();
			Collections.sort(orList, cct); // 重新排序

			ClientInfo client = null; // 用户发送者
			BskExpert expert = null;// 专家发送者

			if (or.getSender().startsWith("c_")) { // 发送者为用户
				client = getClientInfo(or.getSender());
				expert = getExpert(or.getReceiver());
			}

			if (or.getSender().startsWith("d_")) {// 发送者为专家
				expert = getExpert(or.getSender());
				client = getClientInfo(or.getReceiver());
			}

			joo.accumulate("soft", or.getSoft());

			String imgPath = SystemConfig.getString("image_base_url");
			if (null != expert) {
				joo.accumulate("ename", expert.getName());// 医生信息
				joo.accumulate("portrait", null == expert ? ""
						: (imgPath + expert.getPortrait()));
				joo.accumulate("role", getRoleName(expert.getRole()));
				joo.accumulate("uid", expert.getUserId());
			}

			if (null != client) {
				joo.accumulate("cname", client.getName()); // 用户信息;
				joo.accumulate("cid", client.getId());
				joo.accumulate("mobile", client.getMobile());
				joo.accumulate("cportrait",
						StringUtils.isEmpty(client.getHeadPortrait()) ? ""
								: imgPath + client.getHeadPortrait());
			}

			JSONArray ja = new JSONArray();
			// String sender = null;
			// String receiver = null;

			boolean convert = null == qc ? false : qc.isNeedConvert();

			for (int i = 0; i < orList.size(); i++) {
				OnlineRecored r = orList.get(i);
				JSONObject jo = new JSONObject();
				jo.accumulate("type", r.getType());

				if (convert) {
					jo.accumulate("msg", StringUtils.textToHtml(r.getMessage()));
				} else {
					jo.accumulate("msg", r.getMessage());
				}
				jo.accumulate(
						"createTime",
						DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN,
								r.getCreateTime()));
				ja.add(jo);

			}
			joo.accumulate("list", ja);
		}
		return joo.toString();
	}
	
	public String queryChatRecords160(OnlineRecored or, QueryInfo qi,
			QueryCondition qc) {
		PageObject<OnlineRecored> poor = orecoredDao.queryChatRecords(or, qi,
				qc);
		JSONObject joo = new JSONObject();
		if (null != poor && !CollectionUtils.isEmpty(poor.getDatas())) {
			List<OnlineRecored> orList = poor.getDatas();
			
			CompareChatTime cct = new CompareChatTime();
			Collections.sort(orList, cct); // 重新排序
			
			JSONArray ja = new JSONArray();

			boolean convert = null == qc ? false : qc.isNeedConvert();
			
			for (int i = 0; i < orList.size(); i++) {
				OnlineRecored r = orList.get(i);
				JSONObject jo = new JSONObject();
				jo.accumulate("type", r.getType());
				
				if (convert) {
					jo.accumulate("msg", StringUtils.textToHtml(r.getMessage()));
				} else {
					jo.accumulate("msg", r.getMessage());
				}
				jo.accumulate("attachments", StringUtils.isEmpty(r.getAttachments()) ? "" : r.getAttachments());
				jo.accumulate(
						"createTime",
						DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN,
								r.getCreateTime()));
				ja.add(jo);
				
			}
			joo.accumulate("list", ja);
			joo.accumulate("soft", Constant.SOFT_160);
			joo.accumulate("cid", or.getSender().replace("c_", ""));
			joo.accumulate("uid", or.getReceiver().replace("d_", ""));
			joo.accumulate("conId", or.getConsultationId());
		}
		return joo.toString();
	}

	/**
	 * 获取专家信息
	 */
	private BskExpert getExpert(String receiver) {
		try {
			Integer userId = Integer.parseInt(getNumForString(receiver));
			return bskExpertDao.queryExpertByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取客户信息
	 */
	private ClientInfo getClientInfo(String sender) {
		try {
			return clientInfoDao
					.load(Integer.parseInt(getNumForString(sender)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getRoleName(int role) {
		return role == BskExpert.ROLE_DOCTOR ? "医生" : "健康管理师";
	}

	private String getNumForString(String str) {
		if (!StringUtils.isEmpty(str)) {
			Pattern p = Pattern.compile("\\d{1,100}$");
			Matcher m = p.matcher(str);

			String num = null;
			while (m.find()) {
				num = m.group();
			}
			return num;
		}
		return null;
	}

	/**
	 * 给医生推送信息
	 */
	public int sendMsgToDoc(OnlineRecored or) {
		if (null != or && !StringUtils.isEmpty(or.getReceiver())
				&& !StringUtils.isEmpty(or.getSender())) {

			HashMap<String, String> parmas = new HashMap<String, String>();

			JSONObject jo = new JSONObject();
			jo.put("message", or.getMessage());
			jo.put("clientId", or.getSender());
			jo.put("docId", or.getReceiver());
			jo.put("soft", or.getSoft()); // 对应软件标识

			Integer clientId = Integer.parseInt(or.getSender());
			ClientInfo sclient = clientInfoDao.load(clientId);
			if (null != sclient) {
				jo.accumulate("userName", sclient.getName());
				jo.put("mobile", sclient.getMobile());
			}
			Date date = new Date();
			jo.put("createTime",
					DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN, date));

			parmas.put("msg", jo.toString());
			parmas.put("tag", "chat" + or.getReceiver());
			parmas.put("sender", or.getSender());
			String status = "";
			try {
				String url = SystemConfig.getString("image_base_url")
						+ SystemConfig.getString("push_method_url");
				status = HttpClientUtils.getContentByPost(url, parmas,
						Constant.CHARSET_UTF8);
				System.out.println("推送消息结果：" + status);
			} catch (Exception e) {
				e.printStackTrace();
			}
			or.setSender("c_" + or.getSender());
			or.setReceiver("d_" + or.getReceiver());
			or.setType(OnlineRecored.TYPE_SEND_TO_DOC);
			or.setStatus(status.equals("success") ? OnlineRecored.STATUS_READED
					: OnlineRecored.STATUS_UNREAD);
			or.setCreateTime(date);

			orecoredDao.add(or);
			return Constant.INTERFACE_SUCC;
		}
		return Constant.INTERFACE_FAIL;
	}
	
	/**
	 * 向客户端推送信息
	 */
	public int sendMsgToClient(OnlineRecored or, String tag, String pushTitle,
			String pushContent) {
		if (null != or && !StringUtils.isEmpty(or.getReceiver())
				&& !StringUtils.isEmpty(or.getSender())) {

			// 激光推送
			JpushUtil ju = new JpushUtil();

			String status = ju.sendChatMessage(tag, pushTitle, pushContent,
					or.getSoft());
			// 存入数据库
			if ("success".equals(status)) {
				or.setSender("d_" + or.getSender());
				or.setReceiver("c_" + or.getReceiver());
				or.setType(OnlineRecored.TYPE_SEND_TO_CLIENT);
				or.setStatus(OnlineRecored.STATUS_UNREAD);// 未读状态

				orecoredDao.add(or);
				return Constant.INTERFACE_SUCC;
			} else {
				return Constant.INTERFACE_FAIL;
			}
		}
		return Constant.INTERFACE_FAIL;
	}

	/**
	 * 向客户端推送信息
	 */
	public int dongDongSendMsgToClient(OnlineRecored or, String tag,
			String pushTitle, String pushContent) {
		if (null != or && !StringUtils.isEmpty(or.getReceiver())
				&& !StringUtils.isEmpty(or.getSender())) {

			// 激光推送
			JpushUtil ju = new JpushUtil();

			String status = ju.dongDongSendChatMessage(tag, pushTitle,
					pushContent);
			// 存入数据库
			if ("success".equals(status)) {
				or.setSender("c_" + or.getSender());
				or.setReceiver("c_" + or.getReceiver());
				or.setType(OnlineRecored.TYPE_CLIENT_TO_CLIENT);
				or.setStatus(OnlineRecored.STATUS_UNREAD);

				orecoredDao.add(or);
				return Constant.INTERFACE_SUCC;
			} else {
				return Constant.INTERFACE_FAIL;
			}
		}
		return Constant.INTERFACE_FAIL;
	}

	/**
	 * 动动查询当前聊天状态中前50个用户
	 */
	public String dongDongQueryFiftyChat(String clientId, QueryInfo queryInfo) {

		JSONObject jo = new JSONObject();

		if (clientId != null) {

			Integer cid = Integer.parseInt(clientId.substring(2));

			ClientInfo clientInfo = clientInfoDao.load(cid);

			ClientLocation location = locationDao.queryLocationByClientId(cid);

			PageObject pager = orecoredDao.dongDongQueryFiftyChat(clientId,
					queryInfo);

			List<Object> lst = pager.getDatas();

			double lon = location.getLongitude();
			double lat = location.getLatitude();

			JSONArray ja = new JSONArray();

			if (!CollectionUtils.isEmpty(lst)) {
				for (Object objs : lst) {
					Object[] oo = (Object[]) objs;
					ClientLocation os = (ClientLocation) oo[0];

					if (!os.getClientId().equals(location.getClientId())) {
						JSONObject json = new JSONObject();
						json.put("clientId", os.getClientId());
						json.put("longitude", os.getLongitude());
						json.put("latitude", os.getLatitude());
						json.put("location", os.getLocation());

						if (oo[1] != null) {
							json.put("name", oo[1].toString());
						} else {
							json.put("name", "");
						}
						if (oo[2] != null) {
							json.put("gender",
									Integer.parseInt(oo[2].toString()));
						} else {
							json.put("gender", 0);
						}
						if (oo[3] != null) {
							json.put("mobile", oo[3].toString());
						} else {
							json.put("mobile", "");
						}

						if (clientInfo != null) {
							json.accumulate("mgender", clientInfo.getGender());
							json.accumulate("mname", clientInfo.getName());
						}

						double dis = distanceByLngLat(lon, lat,
								os.getLongitude(), os.getLatitude());

						if (dis != 0) {
							json.put("distance", dis / 1000);
						} else {
							json.put("distance", 0);
						}

						ja.add(json);
					}
				}

				jo.put("code", 1);
				jo.put("msg", "成功");
				jo.put("data", ja);
			} else {
				jo.put("code", 1);
				jo.put("msg", "最近没有联系人");
				jo.put("data", ja);
			}
		}
		return jo.toString();
	}

	public List<OnlineRecoredExtend> queryFiftyChat(String userId, int soft) {
		return orecoredDao.queryFiftyChat(userId, soft);
	}

	public String dongDongQueryChatMsg(OnlineRecored or, QueryInfo qi,
			QueryCondition qc) {
		PageObject<OnlineRecored> pageLst = orecoredDao.queryChatRecords(or,
				qi, qc);
		JSONObject json = new JSONObject();
		if (null != pageLst && !CollectionUtils.isEmpty(pageLst.getDatas())) {
			List<OnlineRecored> orList = pageLst.getDatas();

			CompareChatTime cct = new CompareChatTime();
			Collections.sort(orList, cct); // 重新排序

			JSONArray ja = new JSONArray();

			boolean convert = null == qc ? false : qc.isNeedConvert();
			String base = SystemConfig.getString("image_base_url");
			for (int i = 0; i < orList.size(); i++) {
				OnlineRecored r = orList.get(i);
				JSONObject jo = new JSONObject();

				if (r.getSender().equals(or.getSender())) {
					Integer id = Integer.parseInt(or.getSender().substring(2));
					ClientInfo client = clientInfoDao.load(id);
					jo.accumulate("id", client.getId());
					if (!StringUtils.isEmpty(client.getName())) {
						jo.put("name", client.getName());
					} else {
						jo.put("name", "");
					}
					jo.accumulate("mobile", client.getMobile());
					if (!StringUtils.isEmpty(client.getNickName())) {
						jo.put("nickName", client.getNickName());
					} else {
						jo.put("nickName", "");
					}

					if (!StringUtils.isEmpty(client.getHeadPortrait())) {
						jo.put("headPortrait", base + client.getHeadPortrait());
					} else {
						jo.put("headPortrait", "");
					}
					if (client.getGender() != null) {
						jo.put("gender", client.getGender());
					} else {
						jo.put("gender", "");
					}
				} else {
					Integer id = Integer
							.parseInt(or.getReceiver().substring(2));
					ClientInfo client = clientInfoDao.load(id);
					jo.accumulate("id", client.getId());
					if (!StringUtils.isEmpty(client.getName())) {
						jo.put("name", client.getName());
					} else {
						jo.put("name", "");
					}
					jo.accumulate("mobile", client.getMobile());
					if (!StringUtils.isEmpty(client.getNickName())) {
						jo.put("nickName", client.getNickName());
					} else {
						jo.put("nickName", "");
					}
					if (!StringUtils.isEmpty(client.getHeadPortrait())) {
						jo.put("headPortrait", base + client.getHeadPortrait());
					} else {
						jo.put("headPortrait", "");
					}
					if (client.getGender() != null) {
						jo.put("gender", client.getGender());
					} else {
						jo.put("gender", "");
					}
				}

				if (convert) {
					jo.accumulate("msg", StringUtils.textToHtml(r.getMessage()));
				} else {
					jo.accumulate("msg", r.getMessage());
				}
				jo.accumulate(
						"createTime",
						DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN,
								r.getCreateTime()));

				ja.add(jo);
			}

			json.put("code", 1);
			json.put("msg", "成功");
			json.accumulate("data", ja);

		} else {
			json.put("code", 1);
			json.put("msg", "没有消息记录");
			json.accumulate("data", "");
		}

		return json.toString();
	}

	/**
	 * 计算两个经纬度之间的距离
	 */
	public static double distanceByLngLat(double lng1, double lat1,
			double lng2, double lat2) {
		double radLat1 = lat1 * Math.PI / 180;
		double radLat2 = lat2 * Math.PI / 180;
		double a = radLat1 - radLat2;
		double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * 约跑添加好友向客户端推送信息
	 */
	public int sportAddFriend(String tag, String pushTitle, String pushContent) {
		// 激光推送
		JpushUtil ju = new JpushUtil();
		String status = ju.sportAddFriend(tag, pushTitle, pushContent);
		// 存入数据库
		if ("success".equals(status)) {
			return Constant.INTERFACE_SUCC;
		} else {
			return Constant.INTERFACE_FAIL;
		}
	}

	public static void main(String[] args) {
		String url = "http://192.168.1.31:8080/ch/ochat_pushToDoc.do";

		HashMap<String, String> parmas = new HashMap<String, String>();

		JSONObject jo = new JSONObject();
		jo.put("message", "message 内容消34343息");
		jo.put("clientId", "10000");
		jo.put("userName", "侯志清");
		jo.put("createTime",
				DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN, new Date()));

		parmas.put("msg", jo.toString());
		parmas.put("receiver", "chat1");
		parmas.put("sender", "10000");

		String content = HttpClientUtils.getContentByPost(url, parmas, null);
		System.out.println(content);

		String a = "d_343_3434";

		Pattern p = Pattern.compile("\\d{1,100}$");
		Matcher m = p.matcher(a);

		while (m.find()) {
			System.out.println(m.group());
		}

		String msg = "d_12343";
		String msg2 = "c_12343";

		System.out.println(msg2.startsWith("d_"));
		System.out.println(msg.startsWith("c_"));

	}

	public void add(OnlineRecored or) {

	}

	public List<OnlineRecored> queryOnlineRecored(OnlineRecored recored) {
		return orecoredDao.queryOnlineRecored(recored);
	}

	public int queryOnlineRecoredCount(OnlineRecored recored) {
		return orecoredDao.queryOnlineRecoredCount(recored);
	}

	public void updateOnlineRecored(OnlineRecored recored) {
		orecoredDao.updateOnlineRecored(recored);
	}

}
