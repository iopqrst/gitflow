package com.bskcare.ch.service.impl.tg;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.dao.tg.TgActivityCardDao;
import com.bskcare.ch.dao.tg.TgActivityDao;
import com.bskcare.ch.service.ClientInfoService;
import com.bskcare.ch.service.ShortMessagService;
import com.bskcare.ch.service.tg.TgActivityService;
import com.bskcare.ch.util.Base64;
import com.bskcare.ch.util.Message;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ShortMessage;
import com.bskcare.ch.vo.tg.TgActivity;
import com.bskcare.ch.vo.tg.TgActivityCard;

@Service
public class TgActivityServiceImpl implements TgActivityService{
	
	@Autowired
	private TgActivityDao tgActivityDao;
	
	@Autowired
	private ShortMessagService shortMessagService;

	@Autowired
	private TgActivityCardDao tgActivityCardDao;
	@Autowired
	private ClientInfoService clientInfoService;
	
	
	private transient final Logger log = Logger.getLogger(getClass());
	
	//活动发送激活码，首先判断手机号是否已经发送过激活码，
		//如果发送过，就提示不在发送，
		//如果没有发送过，就从准备好的产品卡号中选取做活动准备的产品卡号，按id正序排序，取第一个作为发送给客户的激活码
	
	public String tgActivitySendCard(String mobile, String mobileKey){
		JSONObject jo = new JSONObject();
		if(!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(mobileKey)){
			String mkey = "";
			try {
				mkey = Base64.decode(mobileKey);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(mkey.equals(mobile)){
				TgActivity tgActivity = new TgActivity();
				tgActivity.setMobile(mobile);
				TgActivity ta = tgActivityDao.queryTgActivity(tgActivity);
				ShortMessage shortMessage = new ShortMessage();
				shortMessage.setMobile(mobile);
				
				ClientInfo cinfo = new ClientInfo();
				cinfo.setMobile(mobile);
				ClientInfo client = clientInfoService.queryClientInfo(cinfo);
				if(client != null && client.getId() != null){
					shortMessage.setClientId(client.getId());
				}
				
				//等于null，说明手机号没有发送过激活码
				if(ta == null){
					TgActivityCard tgActivityCard = tgActivityCardDao.queryTgActivityCard();
					if(tgActivityCard != null){
						String card = tgActivityCard.getProductCard();
						String content = Message.getString("tg_activity_success");
						shortMessage.setContent(MessageFormat.format(content, card));
						shortMessagService.sendMessage(shortMessage, "more");
						
						//发送短信成功之后，把该激活码状态改为已发送
						tgActivityCard.setStatus(TgActivityCard.STATUS_SEND);
						tgActivityCardDao.update(tgActivityCard);
						
						tgActivity.setProductCard(card);
						tgActivity.setCreateTime(new Date());
						tgActivityDao.add(tgActivity);
						
						jo.put("code", 1);
						jo.put("msg", "发送激活码成功");
						log.info("-------发送激活码成功-----------");
						
					}else{
						jo.put("code", 2);
						jo.put("msg", "已经没有剩余的激活码");
						log.info("-------已经没有剩余的激活码-----------");
					}
				}else{
					//不等于null，说明手机号码已经发送过激活码
					jo.put("code", 3);
					jo.put("msg", "手机号码已经发送过激活码，不能再发送");
					log.info("----------手机号码已经发送过激活码，不能再发送-----------");
				}
			}else{

				jo.put("code", 4);
				jo.put("msg", "手机号码和键值不匹配");
				log.info("-------手机号码和键值不匹配-----------");
			}
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数错误");
		}
		return jo.toString();
	}
}
