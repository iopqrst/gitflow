package com.bskcare.ch.dao.impl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ShortMessageDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ShortMessage;


@Repository
@SuppressWarnings("unchecked")
public class ShortMessageDaoImpl extends BaseDaoImpl<ShortMessage> implements ShortMessageDao {
	
	public PageObject getShortMessage(ShortMessage shortMessage,QueryInfo queryInfo){
		StringBuffer sb = new StringBuffer(" select * from t_client_sms where 1=1 ") ;
		ArrayList<Object> list = new ArrayList<Object>() ;
		if(shortMessage.getClientId()!=null){
			// 个人信息和群推信息
			list.add(shortMessage.getClientId()) ;
			sb.append(" and (clientId = ? or (clientId is null and mobile is null and " +
					" sendTime > (select createTime from t_clientinfo where id = ?)))") ;
			list.add(shortMessage.getClientId()) ;
		}else{
			//定时短信，未发送短信
			sb.append(" and fixedTime <= NOW() and fixedTime is not null and mobile is NOT NULL  ") ;
		}
		sb.append(" and result = ? ") ;
		
		
		queryInfo.setSort("sendTime") ;
		queryInfo.setOrder("desc") ;
		
		list.add(shortMessage.getResult()) ;
				
		return queryObjectsBySql(sb.toString(), null, null, list.toArray(), queryInfo, shortMessage.getClass());
		
		//return queryPagerObjects(sb.toString(),list.toArray(),queryInfo);		
	}

	public ShortMessage addShortMessage(ShortMessage shortMessage) {
		return this.add(shortMessage) ;
	}

//	public int sendMessag(ShortMessage shortMessage) throws UnsupportedEncodingException {
//		if(null != shortMessage && shortMessage.getMobile()!=null){
//			//shortMessage.setContent(shortMessage.getContent()+"[百生康云健康]") ;
//			Client www = new Client("SDK-BBX-010-15672","880124") ;
//			
//			String state = "2" ;
//			
//			if(StringUtils.isDevelopment()) {
//				System.out.println("---------当前环境为开发环境，短信无法发送！！！");
//			} else {
//				state = www.mdSmsSend_u(shortMessage.getMobile(), shortMessage.getContent()+Message.getString("sms_prefix"), "", "", "") ;
//			}
//			
//			shortMessage.setClientId(shortMessage.getClientId()) ;
//			shortMessage.setSendTime(new Date()) ;
//			
//			if(Long.valueOf(state)>1){ 
//				shortMessage.setResult(0) ;
//			}else{
//				shortMessage.setResult(1) ;
//			}
//			shortMessage.setState(Long.valueOf(state)) ;
//			shortMessage.setMobile(shortMessage.getMobile()) ;
//			
//			if(shortMessage.getType()!=null&&shortMessage.getType().equals(Message.getString("fixedTimeSMS"))){
//				this.update(shortMessage) ;
//			}else{
//				this.addShortMessage(shortMessage) ;
//
//			}
//						
//			return shortMessage.getResult() ;
//		}else{
//			return 1 ;
//		}
//		
//	}

	public void updateShortMessage(ShortMessage shortMessage) {
		if(shortMessage.getId()!=null){
			ShortMessage oldShortMessage = this.load(shortMessage.getId()) ;
			if(shortMessage.getResult()!=null){
				oldShortMessage.setResult(shortMessage.getResult()) ;
			}
			if(shortMessage.getContent()!=null){
				oldShortMessage.setContent(shortMessage.getContent()) ;
			}
			if(shortMessage.getFixedTime()!=null){
				oldShortMessage.setFixedTime(shortMessage.getFixedTime()) ;
			}			
			this.update(oldShortMessage) ;	
		}
	}

	public List<ShortMessage> getNotSendMessage() {
		
		return null;
	}

	public Object getMobileSum(String mobile) {
		return this.findUniqueResultByNativeQuery("select COUNT(id) count from t_client_sms m where m.mobile = ? and DATE_FORMAT(m.sendTime,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')", mobile);
	}
	
	public List<ShortMessage> queryShortMessage(Integer clientId){
		ArrayList<Object> list = new ArrayList<Object>() ;
		
		String hql =" from ShortMessage where result = ? " ;
		list.add(ShortMessage.SEND_RESULT_SUC);
		
		if(clientId!=null){
			hql += " and clientId = ? " ;
			list.add(clientId) ;
		}
		
		hql += " order by sendTime desc limit 20";
		return executeFind(hql,list.toArray());	
	}
	
	/**
	 * 查询短信内容通过手机号
	 * @author mayi
	 * @version 2014-11-4  下午05:37:44
	 * @param clientSms
	 * @return
	 */
	public PageObject selectShortMessageByMobile(String  mobile,QueryInfo queryInfo){
		Object[] args=new Object[1];
		String hql =" from ShortMessage where mobile = ? " ;
		hql += " order by sendTime desc ";
		args[0]=mobile;
		queryInfo.setSort("sendTime") ;
		queryInfo.setOrder("desc") ;
		return queryPagerObjects(hql,args,queryInfo);	
	}

	public List<ShortMessage> queryDoctorInviteClientSms(String type, String mobile){
		List args = new ArrayList();
		String hql = " from ShortMessage where 1 = 1";
		if(!StringUtils.isEmpty(type)){
			hql += " and type = ?";
			args.add(type);
		}
		if(!StringUtils.isEmpty(mobile)){
			hql += " and mobile = ?";
			args.add(mobile);
		}
		
		return executeFind(hql, args.toArray());
	}
}
