package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientDiseaseSelf;

public interface ClientDiseaseSelfDao extends BaseDao<ClientDiseaseSelf>{
	
	/**根据用户id查询用户疾病信息**/
	public List<Object> queryDiseaseSelf(Integer clientId);
	/**添加个人疾病*/
	public void saveDiseaseSelf(ClientDiseaseSelf clientDiseaseSelf); 
	/**修改个人疾病*/
	public void updateDiseaseSelf(ClientDiseaseSelf clientDiseaseSelf); 
	/**根据用户id查询用户的疾病*/
	public List<ClientDiseaseSelf> queryDiseaseSelfByClientId(Integer clientId);
	public List queryInitDiseaseSelfByClientId(Integer clientId);	
	/**根据用户id删除所有疾病	 */
	public void deleteDiseaseByCid(Integer cid);
	/**根据用户id删除所有疾病	 */
	public void deleteOtherDiseaseByCid(Integer cid);
	
	/**根据用户id和疾病查询用户疾病表中是否有此疾病**/
	public ClientDiseaseSelf queryDiseaseSelf(Integer clientId,String disease);
	
	/**修改用户疾病**/
	public void updateDiseaseSelf(String newDisease,String disease,Integer clientId);
	
	/**根据用户id和疾病删除用户疾病**/
	public void deleteDiseaseSelf(Integer clientId,String disease);
	/**
	 * 查询用户的自定义疾病
	 */
	public ClientDiseaseSelf quertDiseaseSelfOther(Integer cid);
}
