package com.bskcare.ch.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ShortMessageDao;
import com.bskcare.ch.message.util.SendMessageUtil;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ShortMessagService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.Client;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.Message;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ShortMessage;

@Service
@SuppressWarnings("unchecked")
public class ShortMessagServiceImpl extends BaseDaoImpl<ShortMessage> implements
		ShortMessagService {
	@Autowired
	private ShortMessageDao shortMessageDao;

	public ShortMessage addShortMessag(ShortMessage shortMessage,
			ClientInfo clientInfo, String source)
			throws UnsupportedEncodingException {
		// 发送多条短信，不传ClientInfo信息。
		if (clientInfo != null) {
			shortMessage.setClientId(clientInfo.getId());
			shortMessage.setResult(-1);
			shortMessage.setMobile(clientInfo.getMobile());
		}
		// 即时发送短信
		if (shortMessage.getFixedTime() == null) {
			shortMessage.setType(Message.getString("sendSMS"));
			this.sendMessage(shortMessage, source);
		} else {
			shortMessage.setType("定时短信");
			this.sendFixedMessage(shortMessage);
		}
		return null;
	}

	// 发送定时短信
	public void sendFixedMessage(ShortMessage shortMessage) {
		if (shortMessage != null) {
			// 发送多条定时短信
			if (!CollectionUtils.isEmpty(shortMessage.getLstMessageEntity())) {
				List<ShortMessage> lstMessage = shortMessage
						.getLstMessageEntity();
				for (ShortMessage message : lstMessage) {
					ShortMessage sm = new ShortMessage();
					sm.setClientId(message.getClientId());
					sm.setType(shortMessage.getType());
					sm.setContent(shortMessage.getContent());
					sm.setSendTime(new Date());
					sm.setFixedTime(shortMessage.getFixedTime());
					sm.setMobile(message.getMobile());
					sm.setResult(-1);

					shortMessageDao.addShortMessage(sm);
				}

			} else { // 发送单条定时短信
				shortMessage.setSendTime(new Date());
				shortMessageDao.addShortMessage(shortMessage);
			}
		}
	}

	public PageObject getShortMessag(ShortMessage shortMessage,
			QueryInfo queryInfo) {
		return shortMessageDao.getShortMessage(shortMessage, queryInfo);
	}

	public void canleSend(ShortMessage shortMessage) {
		// 取消短信
		shortMessage.setResult(2);
		shortMessageDao.updateShortMessage(shortMessage);
	}

	public void updateShortMessage(ShortMessage shortMessage) {
		shortMessageDao.updateShortMessage(shortMessage);
	}

	public String myShortMessge(ShortMessage shortMessage, QueryInfo queryInfo) {

		PageObject list = shortMessageDao.getShortMessage(shortMessage,
				queryInfo);
		JSONObject jo = new JSONObject();
		jo.put("total", list.getTotalRecord());
		jo.put("list", JsonUtils.getJsonString4JavaListDate(list.getDatas()));

		return jo.toString();
	}

	public void sendTimerMSM() {
		ShortMessage shortMessage = new ShortMessage();
		shortMessage.setResult(ShortMessage.SEND_RESULT_NOT);
		QueryInfo queryInfo = new QueryInfo(500, null, null, null);
		PageObject page = shortMessageDao.getShortMessage(shortMessage,
				queryInfo);
		if (!CollectionUtils.isEmpty(page.getDatas())) {
			ArrayList<ShortMessage> list = (ArrayList<ShortMessage>) page
					.getDatas();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ShortMessage shortMessage2 = (ShortMessage) iterator.next();
				ClientInfo clientInfo = new ClientInfo();
				clientInfo.setId(shortMessage2.getClientId());
				clientInfo.setMobile(shortMessage2.getMobile());
				sendMessage(shortMessage2, "more");
			}

		}
	}

	/**
	 * Client www = null; try { www = new Client("SDK-BBX-010-15672","880124");
	 * } catch (UnsupportedEncodingException e) { System.out.println("获取发送器失败");
	 * e.printStackTrace(); }
	 * 
	 * state = www.mdSmsSend_u(shortMessage.getMobile(),
	 * shortMessage.getContent()+ Message.getString("sms_prefix"), "", "", "") ;
	 */

	/**
	 * 发送信息
	 * 
	 * @param count
	 *            发送条数【one: 单条 more: 多条】
	 */
	public int sendMessage(ShortMessage shortMessage, String count) {
		if (null != shortMessage) {
			String state = "-1";
			if (StringUtils.isDevelopment()) {
				state = "0";
				System.out.println("---------当前环境为开发环境，短信无法发送！！！");
			} else {
				String mobile = "";
				try {
					if (shortMessage != null
							&& CollectionUtils.isEmpty(shortMessage
									.getLstMessageEntity())) {
						mobile = shortMessage.getMobile();

						state = SendMessageUtil.sendMessage(mobile,
								shortMessage.getContent() + "【血糖高管】", count);
					} else if (shortMessage != null
							&& !CollectionUtils.isEmpty(shortMessage
									.getLstMessageEntity())) {
						List<ShortMessage> lstMessage = shortMessage
								.getLstMessageEntity();
						List<String> lstStr = new ArrayList<String>();
						for (ShortMessage message : lstMessage) {
							lstStr.add(message.getMobile());
						}
						mobile = ArrayUtils.join(lstStr.toArray());

						state = SendMessageUtil.sendMessage(mobile,
								shortMessage.getContent() + "【血糖高管】", count);
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (shortMessage != null
					&& CollectionUtils.isEmpty(shortMessage
							.getLstMessageEntity())) {
				this.sendMessageDetail(shortMessage, state);
			} else if (shortMessage != null
					&& !CollectionUtils.isEmpty(shortMessage
							.getLstMessageEntity())) {
				List<ShortMessage> lstMessage = shortMessage
						.getLstMessageEntity();
				for (ShortMessage message : lstMessage) {
					message.setContent(shortMessage.getContent());
					message.setType(shortMessage.getType());
					this.sendMessageDetail(message, state);
				}
			}
			return ShortMessage.SEND_RESULT_SUC;
		} else {
			return ShortMessage.SEND_RESULT_FAIL;
		}
	}

	public void sendMessageDetail(ShortMessage shortMessage, String state) {
		shortMessage.setClientId(shortMessage.getClientId());
		shortMessage.setSendTime(new Date());

		if (state.equals("0")) {
			shortMessage.setResult(ShortMessage.SEND_RESULT_SUC);// 状态 -1: 未发送,
																	// 0：已发送
																	// 1：发送失败
																	// 2：取消发送
		} else {
			shortMessage.setResult(ShortMessage.SEND_RESULT_FAIL);
		}

		shortMessage.setState(new BigInteger(state));
		shortMessage.setMobile(shortMessage.getMobile());

		if (shortMessage.getType() != null
				&& shortMessage.getType().equals(
						Message.getString("fixedTimeSMS"))) {
			shortMessageDao.update(shortMessage); // 如果是定时短信，需要修改定时短信的状态
		} else {
			shortMessageDao.addShortMessage(shortMessage);
		}
	}

	public int getMobileSum(String mobile) {
		Object count = shortMessageDao.getMobileSum(mobile);
		String tmp = count + "";
		return Integer.valueOf(tmp);
	}

	public List<ShortMessage> queryShortMessage(Integer clientId) {
		return shortMessageDao.queryShortMessage(clientId);
	}

	public void addShortMessage(ShortMessage shortMessage) {
		shortMessageDao.add(shortMessage);
	}

	/**
	 * 查询邮件内容通过手机号
	 * 
	 * @author mayi
	 * @version 2014-11-4 下午05:37:44
	 * @param clientSms
	 * @return
	 */
	public PageObject queryShortMessageByMobile(ShortMessage shortMessage,
			QueryInfo queryInfo) {
		return shortMessageDao.selectShortMessageByMobile(
				shortMessage.getMobile(), queryInfo);
	}

	public static void main(String[] args) {
		Client www = null;
		try {
			www = new Client("SDK-BBX-010-15672", "880124");
		} catch (UnsupportedEncodingException e) {
			System.out.println("获取发送器失败");
			e.printStackTrace();
		}

		String state = www.mdSmsSend_u("18612834872",
				"您的短信验证码是3043，非本人操作请忽略。" + Message.getString("sms_prefix"),
				"", "", "");
		System.out.println(state);
	}
}
