package com.bskcare.ch.service.rpt;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SrptDietPrinciple;

@SuppressWarnings("unchecked")
public interface SrptDietPrincipleService {
	
	/**添加**/
	public SrptDietPrinciple addRptDietPriciple(SrptDietPrinciple dietPrinciple,String intro,String detail);
	
	/**查询信息**/
	public PageObject queryDietPrinciple(SrptDietPrinciple dietPrinciple,QueryInfo queryInfo);
	
	/**根据id查询**/
	public SrptDietPrinciple queryDietPrincipleById(Integer id);
	
	/**根据id删除**/
	public void deleteDietPrinciple(Integer id);
	
	/**修改信息**/
	public void updateDietPrinciple(SrptDietPrinciple dietPrinciple,String intro,String detail,String lifeInfo);
	
	/**按疾病名称模糊查询**/
	public List<SrptDietPrinciple> queryDietPrincipleByDisease(String diseaseName);
	
	/**根据疾病查询单个对象**/
	public SrptDietPrinciple queryDietPrincipleByName(String diseaseName);
	
	/**查询复合病**/
	public List<SrptDietPrinciple> queryComplexDisease(String diseaseStr);
}
