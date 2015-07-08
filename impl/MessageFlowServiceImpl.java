package com.bskcare.ch.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.MessageFlowDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.MessageFlowService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.jpush.JpushUtil;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.MessageFlow;
import com.bskcare.ch.vo.MessagePush;
import com.bskcare.ch.vo.TaskFlow;

@Service
public class MessageFlowServiceImpl implements MessageFlowService {

	private static final Logger log = Logger
			.getLogger(MessageFlowServiceImpl.class);

	@Autowired
	private MessageFlowDao messageFlowDao;
	@Autowired
	private ClientInfoDao clientInfoDao;

	public MessageFlow add(MessageFlow flow) {
		return messageFlowDao.add(flow);
	}

	public void delete(Integer id) {
		messageFlowDao.delete(id);
	}

	public void modify(MessageFlow flow) {
		messageFlowDao.update(flow);
	}

	public PageObject<MessageFlow> queryMessageFlow(QueryInfo queryInfo,
			MessageFlow flow) {
		return messageFlowDao.queryMessageFlow(queryInfo, flow);
	}
	
	public void automaticallyPushMsg() {
		log.info(LogFormat.b("定时任务推送信息 ** 开始 **"));
		// 定义的自动任务列表
		List<MessageFlow> taskFlows = messageFlowDao.getMessageFlowList();

		if (!CollectionUtils.isEmpty(taskFlows)) {
			int total = 0;
			JpushUtil jpush = new JpushUtil();
			for (MessageFlow flow : taskFlows) {

				log.info(LogFormat.f("当前消息流程为：" + flow.toString()));

				List<ClientInfo> clientLst = getClientsByMessageFlow(flow);

				if (CollectionUtils.isEmpty(clientLst)) {
					log.info(LogFormat.f("没有查询到符合规则的用户"));
					continue;
				} else {
					log.info(LogFormat.f("符合规则的用户：" + clientLst.size()));
				}

				total += clientLst.size();

				int i = 0, itags = 0, isqls = 0, length = clientLst.size();

				String sqlprefix = "insert into t_message_push (title, content, clientId,mobile, sendTime,status) values ";
				StringBuffer sbsql = new StringBuffer(sqlprefix);
				StringBuffer sbtags = new StringBuffer();

				for (ClientInfo client : clientLst) {

					sbsql.append("('" + flow.getTaskTitle() + "','"
							+ flow.getParticulars() + "'," + client.getId()
							+ ", '" + client.getMobile() + "',now(),0),");

					sbtags.append(client.getMobile() + ",");

					itags++;
					isqls++;
					i++;
					// 激光推送有推送限制，超过20个tags就推送失败
					if (itags == 20 || i == length) { 
						jpush.pushNotification(
								StringUtils.removeLastChat(sbtags.toString()),
								flow.getParticulars());

						sbtags = new StringBuffer();
						itags = 0;
					}

					if (isqls == 100 || i == length) { // 每100个用户（含总数不足100）保存一次
						messageFlowDao.createMessagePush(StringUtils
								.removeLastChat(sbsql.toString()));

						sbsql = new StringBuffer(sqlprefix);
						isqls = 0;
					}

				}

			}
			log.info(LogFormat.f("推送用户总数为：" + total));
		} else {
			log.info(LogFormat.b("定时任务推送信息 ** 结束 **"));
		}

		log.info(LogFormat.b("定时任务推送信息 ** 结束 **"));
	}

	public String queryUnreadCount(Integer clientId) {
		int count = messageFlowDao.queryUnReadMessages(clientId);

		return StringUtils
				.encapsulationJSON(new JSONObject(), Constant.INTERFACE_SUCC,
						"success", "{\"count\":" + count + "}");
	}

	public String updateQueryMessages(Integer clientId) {
		JSONObject jo = new JSONObject();
		
		//查询时更新客户未读消息为已读 
		this.updateMsgStatus(1, clientId);
		
		List<MessagePush> list = messageFlowDao.queryMessages(clientId);

		if (!CollectionUtils.isEmpty(list)) {
			return StringUtils.encapsulationJSON(jo, Constant.INTERFACE_SUCC,
					"查询成功", JsonUtils.getJsonString4JavaListDate(list,
							DateUtils.LONG_DATE_PATTERN));
		} else {
			return StringUtils.encapsulationJSON(jo, Constant.INTERFACE_SUCC,
					"查询成功", "");
		}
	}

	public String markUnreadMsg(Integer clientId, Integer id) {
		int effect = messageFlowDao.updateMsgStatus(1,clientId, id); //标记已读

		JSONObject jo = new JSONObject();

		if (effect > 0) {
			return StringUtils.encapsulationJSON(jo, Constant.INTERFACE_SUCC,
					"success", "");
		} else {
			return StringUtils.encapsulationJSON(jo, Constant.INTERFACE_FAIL,
					"error", "");
		}
	}
	
	public String deleteMsg(Integer clientId, Integer msgId) {
		int effect = messageFlowDao.updateMsgStatus(2, clientId, msgId); //标记删除

		JSONObject jo = new JSONObject();

		if (effect > 0) {
			return StringUtils.encapsulationJSON(jo, Constant.INTERFACE_SUCC,
					"success", "");
		} else {
			return StringUtils.encapsulationJSON(jo, Constant.INTERFACE_FAIL,
					"error", "");
		}
	}

	private List<ClientInfo> getClientsByMessageFlow(MessageFlow flow) {
		// 跟据定义计划条件，获得客户列表
		ClientInfo info = new ClientInfo();

		if (flow.getClientType() != -1) {
			info.setType(flow.getClientType() + "");
		}

		if (flow.getBazzaarGrade() != -1) {
			info.setGender(flow.getBazzaarGrade());
		}

		TaskFlow taskFlow = new TaskFlow();
		taskFlow.setBazzaarGrade(flow.getBazzaarGrade());
		taskFlow.setClientType(flow.getClientType());
		taskFlow.setCycle(flow.getCycle());
		taskFlow.setIntervals(flow.getIntervals());
		taskFlow.setIscycle(flow.getIscycle());
		
		System.out.println(taskFlow.toString());

		return clientInfoDao.findClientInfo(info, null, taskFlow, 1); // 1是为了标识黑户不发信息
	}

	//更新客户消息状态
	public int updateMsgStatus(int status, Integer clientId){
		return messageFlowDao.updateMsgStatus(status, clientId);
	}
}
