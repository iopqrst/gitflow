package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.DiseaseInfoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.search.DiseaseInfo;
@Repository
public class DiseaseInfoDaoImpl extends BaseDaoImpl<DiseaseInfo> implements DiseaseInfoDao{

	public List<DiseaseInfo> queryCnr(String cbm){
		String hql=" from DiseaseInfo t where 1=1 and t.cbm like '%"+cbm+"%'";
		List<DiseaseInfo> d=this.executeFind(hql);	
		return d;
	}
	  public List<DiseaseInfo> queryByStr(String str){
		   String hql=" from DiseaseInfo d where 1=1 and d.cnr like '%"+str+"%' or d.cmc like '%"+str+"%'";
		   return this.executeFind(hql);
	   }
	public List<DiseaseInfo> queryCmc(String str) {
		// TODO Auto-generated method stub
		String hql=" from DiseaseInfo d where 1=1 and d.cmc like '%"+str+"%'";
		return this.executeFind(hql);
	}
	/**
	 * 
	 * 分页模糊查询
	 * 
	 * @param str
	 * @param queryInfo
	 * @return
	 */
	public PageObject<DiseaseInfo> queryByPage(String str,QueryInfo queryInfo){
		
		StringBuffer hql=new StringBuffer(" from DiseaseInfo t where 1=1 ");
		
		
		if(null !=str && !"".equals(str)){  
            hql.append(" and t.cmc like '%"+str+"%'");  
        } 
		 
		
		if(null !=str && !"".equals(str)){  
	            hql.append(" or t.cnr like '%"+str+"%'");  
	        }  
	        
		 PageObject<DiseaseInfo> pageObject=this.queryObjects(hql.toString(), null, queryInfo);
		 
		 
		return pageObject;
	}
   
}
