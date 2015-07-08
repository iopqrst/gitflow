package com.bskcare.ch.service.impl.online;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.BskExpertDao;
import com.bskcare.ch.dao.online.OnlineSatisfactionDao;
import com.bskcare.ch.dao.online.OnlineSubscribeDao;
import com.bskcare.ch.service.online.OnlineSatisfactionService;
import com.bskcare.ch.vo.BskExpert;
import com.bskcare.ch.vo.online.OnlineSatisfaction;
import com.bskcare.ch.vo.online.OnlineSubscribe;

@Service
public class OnlineSatisfactionServiceImpl implements OnlineSatisfactionService {

	@Autowired
	OnlineSatisfactionDao osatisfactionDao;
	@Autowired
	OnlineSubscribeDao subscribeDao;
	@Autowired
	BskExpertDao expertDao;

	public static void main(String[] args) {
		System.out.println((double)Math.round(((double)7/11)*10000)/100);
	}
	
	public OnlineSatisfaction add(OnlineSatisfaction os) {

		OnlineSatisfaction sa = osatisfactionDao.add(os);
		if (null != sa) {// 133 6624 1553
			calStatisAndSubCount(os.getExpertId());
			
			//修改当前预约的评论状态
			OnlineSubscribe osubscribe = subscribeDao.load(os.getOsId());
			osubscribe.setHasSatisfaction(OnlineSubscribe.SATISFACTION_TRUE);
			subscribeDao.update(osubscribe);
		}

		return sa;
	}

	public void calStatisAndSubCount(Integer expertId) {
		if(null == expertId) return ;
		// 预约总数
		Object subtotal = subscribeDao.querySubCount(expertId);
		Object satisTotal = osatisfactionDao.queryScore(expertId, 0);
		Object satisfaction = osatisfactionDao
				.queryScore(expertId, 2); // 满意的数 >=2
		// 重新计算满意度
		if (null != satisTotal && null != satisfaction) {
			try {
				int satis =  Integer.parseInt(satisfaction + "");
				int stotal = Integer.parseInt(satisTotal + ""); //满意度总数
				int _subtotal = Integer.parseInt(subtotal + ""); //预定数

				double percent = (double)Math.round(((double)satis/stotal)*1000)/10;
				
				System.out.println("satis=" + satis + ",stotal=" + stotal + ",percent="+percent);
				
				BskExpert be = new BskExpert();
				be.setSatisfaction(percent);
				be.setSubscribeCount(_subtotal);
				be.setId(expertId);
				expertDao.updateOnlineInfo(be);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
