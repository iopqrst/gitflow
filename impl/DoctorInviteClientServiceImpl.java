package com.bskcare.ch.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.bo.UrlShortResult;
import com.bskcare.ch.dao.DoctorInviteClientDao;
import com.bskcare.ch.dao.ShortMessageDao;
import com.bskcare.ch.service.DoctorInviteClientService;
import com.bskcare.ch.service.ShortMessagService;
import com.bskcare.ch.util.HttpClientUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.Message;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.DoctorInviteClient;
import com.bskcare.ch.vo.ShortMessage;

@Service
@SuppressWarnings("unchecked")
public class DoctorInviteClientServiceImpl implements DoctorInviteClientService {

	@Autowired
	private DoctorInviteClientDao doctorInviteClientDao;

	@Autowired
	private ShortMessageDao shortMessageDao;

	@Autowired
	private ShortMessagService shortMessageService;

	public String saveDoctorInviteClient(Integer doctorId, String lstClient,
			String inviteCode, String docName) {
		if (doctorId == null || StringUtils.isEmpty(lstClient)
				|| StringUtils.isEmpty(inviteCode)) {
			return JsonUtils.encapsulationJSON(0, "参数有误", "").toString();
		}

		List<DoctorInviteClient> lstDic = JsonUtils.getList4Json(lstClient,
				DoctorInviteClient.class);
		for (DoctorInviteClient doctorInviteClient : lstDic) {
			// 获取客户姓名
			String name = doctorInviteClient.getName();
			// 获取电话号码
			String mobile = doctorInviteClient.getMobile();

			// 根据医生id和客户电话号码查询，医生是否邀请过该客户
			DoctorInviteClient dic = doctorInviteClientDao
					.queryDoctorInviteClient(mobile, doctorId);

			if (dic != null) {// 如果有邀请过，就修改信息
				dic.setName(name);
				dic.setLastInviteTime(new Date());

				doctorInviteClientDao.update(dic);
			} else {// 如果没有邀请过，就添加邀请信息
				DoctorInviteClient doctor = new DoctorInviteClient();
				doctor.setName(name);
				doctor.setMobile(mobile);
				doctor.setDoctorId(doctorId);
				doctor.setCreateTime(new Date());

				doctorInviteClientDao.add(doctor);
			}

			// 判断该手机号码，是否已经发送过3条医生邀请短信
			String type = Message.getString("doctor_invite_client");
			String content = Message.getString("doctor_invite_client_sms");

			String url = "http://www.bskcare.com/TuiGuang/doc_invite_regs?invite_code="
					+ inviteCode;
			String preUrl = "http://api.t.sina.com.cn/short_url/shorten.json?source=3326430923&url_long="
					+ url;

			String shortResult = HttpClientUtils.getContentByGet(preUrl,
					"utf-8");
			if (!StringUtils.isEmpty(shortResult)) {
				
				List<UrlShortResult> lstShort = JsonUtils.getList4Json(shortResult, UrlShortResult.class);
				for (UrlShortResult urlShortResult : lstShort) {
					String shortUrl = urlShortResult.getUrl_short();
					content += shortUrl;
				}
			}

			List<ShortMessage> lstSms = shortMessageDao
					.queryDoctorInviteClientSms(type, mobile);
			// 如果发送小于3条，发动邀请短信
			if (CollectionUtils.isEmpty(lstSms)
					|| (!CollectionUtils.isEmpty(lstSms) && lstSms.size() < 3)) {

				ShortMessage shortMessage = new ShortMessage();
				shortMessage.setType(type);
				shortMessage.setMobile(mobile);
				shortMessage.setContent(content);
				shortMessage.setSendTime(new Date());
				shortMessageService.sendMessage(shortMessage, "more");
			}
		}
		return JsonUtils.encapsulationJSON(1, "操作成功", "").toString();
	}
	
	
	public String updateClientName(String mobile, Integer doctorId, String name){
		if(StringUtils.isEmpty(mobile) || doctorId == null || StringUtils.isEmpty(name)){
			return JsonUtils.encapsulationJSON(0, "参数错误", "").toString();
		}
		
		// 根据医生id和客户电话号码查询
		DoctorInviteClient dic = doctorInviteClientDao
				.queryDoctorInviteClient(mobile, doctorId);
		
		if (dic != null) {// 如果有邀请过，就修改信息
			dic.setName(name);
			dic.setLastInviteTime(new Date());

			doctorInviteClientDao.update(dic);
		} else {// 如果没有，就添加信息
			DoctorInviteClient doctor = new DoctorInviteClient();
			doctor.setName(name);
			doctor.setMobile(mobile);
			doctor.setDoctorId(doctorId);
			//不是医生邀请的客户
			doctor.setIsInvite(DoctorInviteClient.INVITE_NO);
			doctor.setCreateTime(new Date());

			doctorInviteClientDao.add(doctor);
		}
		
		return JsonUtils.encapsulationJSON(1, "操作成功", "").toString();
	}
}
