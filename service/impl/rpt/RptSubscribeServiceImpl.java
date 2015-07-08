package com.bskcare.ch.service.impl.rpt;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.OrderProductDao;
import com.bskcare.ch.dao.rpt.RptSubscribeDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.RptSubscribeService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.vo.order.OrderProduct;
import com.bskcare.ch.vo.rpt.RptSubscribe;

@Service
public class RptSubscribeServiceImpl implements RptSubscribeService {

	@Autowired
	private RptSubscribeDao rptSubscribeDao;

	@Autowired
	private OrderProductDao orderProductDao;
	
	public void createSubscribeRpt () {
		List<OrderProduct> opList = orderProductDao.queryNotSubscribeOrderProduct();
		
		Date currentTime = new Date();
		RptSubscribe rpt = new RptSubscribe();
		
		Calendar cal = Calendar.getInstance();
		for(OrderProduct op : opList){
			
			Date createTime = op.getCreateTime();
			Date expiresTime = op.getExpiresTime();
			
			for(int i = 1; i < 1000; i++){
				cal.setTime(createTime);
				cal.add(Calendar.MONTH, 3 * i);
				//System.out.println("before:" + DateUtils.format(op.getCreateTime()));
				//System.out.println("after:" + DateUtils.format(cal.getTime()));
				if(currentTime.before(cal.getTime()) && expiresTime.after(cal.getTime())) {
					rpt = new RptSubscribe();
					rpt.setClientId(op.getClientId());
					rpt.setFlag(RptSubscribe.FLAG_UNGENERATE);
					rpt.setSubscribeTime(DateUtils.format(cal.getTime()));
					rpt.setOmId(op.getOmId());
					
					rptSubscribeDao.add(rpt);
					rpt = null;
				} else {
					break;
				}
			}
		}
	}
	
	public List<RptSubscribe> queryClientsByTime(String genTime) {
		if(null != genTime) {
			return rptSubscribeDao.queryClientsByTime(genTime);
		}
		return null;
	}

	public PageObject querySubcribeReport(RptSubscribe sub,QueryInfo queryInfo) {
		return rptSubscribeDao.querySubcribeReport(sub,queryInfo);
	}

	public RptSubscribe findById(RptSubscribe subscribe) {
		return rptSubscribeDao.load(subscribe.getId());
	}

	public void updateSubcribeTime(RptSubscribe subscribe) {
		rptSubscribeDao.update(subscribe);		
	}
}
