package com.bskcare.ch.dao.impl.tg;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.BloodSugarDao;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.tg.TgClientinfoService;
import com.bskcare.ch.service.timeLine.TimeLineTaskService;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.ClientInfo;

@Service("tgClientinfoService")
public class TgClientinfoServiceImpl implements TgClientinfoService {

	@Autowired
	private ClientInfoDao clientInfoDao;
	@Autowired
	private BloodSugarDao bloodSugarDao;
	@Autowired
	private TimeLineTaskService lineTaskService;

	public String personalInfomation(Integer cid, QueryInfo queryInfo) {
		JSONObject ja = new JSONObject();
		ClientInfo client = clientInfoDao.load(cid);
		String base = SystemConfig.getString("image_base_url");
		if (null != client) {
			ja.put("headPortrait", StringUtils.isEmpty(client.getHeadPortrait()) ? "" : base + client.getHeadPortrait());
			ja.put("nickName", StringUtils.isEmpty(client.getNickName()) ? "" : client.getNickName());
			ja.put("mobile", client.getMobile());
			if (client.getType().equals(ClientInfo.TYPE_VIP + "")) {
				String vipProduct = SystemConfig.getString("visited_bloodsugar_product");
				String[] vips = vipProduct.split(",");
				boolean vipFlag = false;
				for (String vip : vips) {
					if (!StringUtils.isEmpty(client.getAvailableProduct()) && client.getAvailableProduct().contains(vip)) {
						vipFlag = true;
						break;
					}
				}
				if (!StringUtils.isEmpty(client.getAvailableProduct()) && vipFlag) {
					ja.put("memberType", ClientInfo.TYPE_VIP);// 会员类型
				} else {
					ja.put("memberType", ClientInfo.TYPE_EXPERIENCE);// 体验类型
				}
			} else {
				ja.put("memberType", ClientInfo.TYPE_EXPERIENCE);// 会员类型
			}
		}
		
		int rate = lineTaskService.getTaskFillRate(cid);
		ja.put("taskRate", rate);
		
		List<BloodSugar> list = bloodSugarDao.queryBloodSugar(cid);
		if (!CollectionUtils.isEmpty(list)) {
			// 获取第一条数据(本次测量数据)
			BloodSugar bs = list.get(0);
			// 餐前为一组类型，如果血糖的类型在餐前一组，则血糖类型为餐前
			if (BloodSugar.SUGAR_BEFORE.contains(String.valueOf(bs.getBloodSugarType()))) {
				ja.put("bsType", "1");
				ja.put("bsVal", Double.valueOf(bs.getBloodSugarValue()));
				// 判断血糖标准
				if (bs.getBloodSugarValue() < 4.4) {
					ja.put("bsStatus", 1);
					ja.put("bsStandard", 4.4 - bs.getBloodSugarValue());	
				}
				if (bs.getBloodSugarValue() >= 4.4 && bs.getBloodSugarValue() <= 7) {
					ja.put("bsStandard", 0);	
				}
				if (bs.getBloodSugarValue() > 7) {
					ja.put("bsStatus", -1);
					ja.put("bsStandard", bs.getBloodSugarValue() - 7);	
				}
			}
			// 餐后一组类型，如果血糖的类型在餐前一组，则血糖类型为餐后
			if (BloodSugar.SUGAR_AFTER.contains(String.valueOf(bs.getBloodSugarType()))) {
				ja.put("bsType", "2");
				ja.put("bsVal", Double.valueOf(bs.getBloodSugarValue()));
				// 判断血糖标准
				if (bs.getBloodSugarValue() < 4.4) {
					ja.put("bsStatus", 1);
					ja.put("bsStandard", 4.4 - bs.getBloodSugarValue());	
				}
				if (bs.getBloodSugarValue() >= 4.4 && bs.getBloodSugarValue() <= 10) {
					ja.put("bsStandard", 0);	
				}
				if (bs.getBloodSugarValue() > 10) {
					ja.put("bsStatus", -1);
					ja.put("bsStandard", bs.getBloodSugarValue() - 10);	
				}
			}
		} else {
			ja.put("bsType", "0");
			ja.put("bsVal", 0);
			ja.put("bsStatus", -2);
			ja.put("bsStandard", 100);
		}
		
		return ja.toString();
	}
}
