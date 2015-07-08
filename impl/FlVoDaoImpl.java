package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.FlVoDao;
import com.bskcare.ch.vo.search.FlVo;
import com.bskcare.ch.vo.search.XyVo;
import com.bskcare.ch.vo.search.ZcyVo;
import com.bskcare.ch.vo.search.Zy1Vo;
import com.bskcare.ch.vo.search.ZyVo;
@Repository("flVoDao")
public class FlVoDaoImpl extends BaseDaoImpl<FlVo> implements FlVoDao {

	public List<FlVo> queryByCbm(String cbm) {
		// TODO Auto-generated method stub
		
		String hql="from FlVo where 1=1 and cypbm like'%"+cbm+"%'";
		return this.executeFind(hql);
	}
	public String queryAnyOne(String cypbm,Integer id){
		StringBuilder sb=new StringBuilder("");
		char t=cypbm.charAt(0);
		System.out.println(t);
		if(t=='X'){
		    String hql=" from  XyVo t where 1=1 and id=?";
		    		    
		    XyVo x=(XyVo)this.findUniqueResult(hql, id);
		    sb.append(x.getCgysm()+"</br>");
			
		}else if(t=='Z'){
			 String hql=" from  ZyVo t where 1=1 and id=?";
			 ZyVo z=(ZyVo)findUniqueResult(hql, id);
			 sb.append(z.getCcdms());
		}else if(t=='0'){
		
			 String hql=" from Zy1Vo t where 1=1 and id=?";
			 Zy1Vo z1=(Zy1Vo)findUniqueResult(hql, id);
			 sb.append(z1.getCcxzh());
		}else if(t=='C'){
			
			 String hql="from  ZcyVo t  where 1=1 and id=?";
			 ZcyVo zy=(ZcyVo)findUniqueResult(hql, id);
			 sb.append(zy.getCckwx());
		}else if(t=='F'){
			String hql="from  FlVo  t where 1=1 and id=?";
			 FlVo f=(FlVo)findUniqueResult(hql, id);
			 sb.append(f.getCcxzh());
		}
		
		return sb.toString();
	}
	public List<FlVo> queryByIdMsg(String id) {
		// TODO Auto-generated method stub
		String hql="from FlVo where 1=1 and id=?";
		return this.executeFind(hql, Integer.parseInt(id));
	}
   
}
