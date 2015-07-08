package com.bskcare.ch.dao;

import java.util.Date;
import java.util.List;
import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.ClientInfoExtend;
import com.bskcare.ch.bo.ClientRegEval;
import com.bskcare.ch.bo.DoctorInfo;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.HealthLevel;
import com.bskcare.ch.vo.TaskFlow;

/**
 * 
 * @author yangtao
 */
@SuppressWarnings("unchecked")
public interface ClientInfoDao extends BaseDao<ClientInfo> {

	/**
	 * 更新用户
	 */
	public Integer updateClient(ClientInfo client);

	/**
	 * 保存客户信息
	 */
	public void save(ClientInfo account);

	/**
	 * 根据账号更新客户密码
	 */
	public void updatePwdByClientId(int id, String password);

	/**
	 * 根据区域id(areaId)查询区域下的用户数量
	 */
	public int findUserCountByareaId(Integer areaId);

	/**
	 * 根据用户查询客户信息
	 */
	public PageObject queryClientsByUserId(QueryInfo info, ClientInfo ci,
			String areaChain,QueryCondition queryCondition);

	/**
	 * 根据产品卡号多表查询该会员用户下亲情信息
	 */
	public List findFamilyInfoByPcCode(String pcCode);

	/**
	 * 根据客户id删除亲情用户信息(status = 1)
	 */
	public void deleteFamilyInfoById(int id);

	/**
	 * 根据客户id修改亲情信息
	 */
	public void updateFamilyInfo(int id, String name, String mobile,
			String email);

	/**
	 * 根据客户条件查找客户
	 */
	public List<ClientInfo> findClientInfo(ClientInfo client);
	/**
	 * 根据健康等级筛查客户，用于自动分配任务
	 * @param client
	 * @param healthLevel
	 * @param flow
	 * @param type 1：黑户不发任务 ; 0：任务正常进行
	 * @return
	 */
	
	public List<ClientInfo> findClientInfo(ClientInfo client,HealthLevel healthLevel,TaskFlow flow, int type);
	
	/**
	 * 定时将生日转换为年龄
	 */
	public int updateBirthdayToAge();
	
	/**
	 * 将临时表中的用户等级更新到用户表中
	 */
	public int updateLevel(String tmpName);
	
	/**
	 * 更新用户等级
	 */
	public int updateLevelByClientId(Integer clientId);
	
	/**
	 * 查询用户信息，并且与区域表联查
	 */
	public List getClientInfoAndArea(ClientInfo c);
	public List getClientInfoAndArea(ClientInfo c,String aredId);
	
	/**
	 * 修改用户基本信息android接口
	 * @param clientInfo
	 */
	public void updateClientInfoAndroid(ClientInfo clientInfo);
	
	/**
	 * 根据用户区域编号修改区域链
	 */
	public void updateAreaChain(ClientInfo clientInfo);

	
	/*public List queryClients(ClientInfo ci,String areaChain, QueryCondition queryCondition);*/

	public void updateFinishPercent(Integer clientId,double finishPercent);
	
	/**
	 * 修改用户基本信息（运动部分）
	 */
	public void updateClientInfo(ClientInfo clientInfo);
	/**
	 * 修改客户市场评分
	 */
	public void alterBazzer(Integer client,Integer bazzer);
	/**
	 * 修改客户健康评分
	 */
	public void alterHealth(Integer client,Integer bazzer);
	
	/**根据用户id修改区域链**/
	public void updateAreaChainByClientId(ClientInfo clientInfo);
	
	/**根据用户信息修改用户（crm修改同步）**/
	public int updateClientInfoByCrm(ClientInfo clientInfo);
	/**
	 * 修改负责人id
	 */
	public void alterPrincipal(Integer client,Integer PrincipalId);
	/**
	 * 更新用户头像文件名
	 */
	public void updateHeadPortrairByClientId(Integer clientId,String HeadPortrairName);
	/**根据手机号和邮箱地址查询**/
	public ClientInfo getClientByMobile(ClientInfo u);
	/**
	 * 根据产品是否过期修改用户信息
	 */
	public void updateInfoByProductExpire(ClientInfo u);
	
	public ClientInfo queryClientInfo(ClientInfo clientInfo);
	
	public void updateClientComp(ClientInfo clientInfo);
	
	public void updateMobile(Integer cid, String mobile);
	
	
	public PageObject queryUploadClientInfo(long days, ClientInfo clientInfo, QueryInfo queryInfo);
	
	/**
	 * 查询某段时间内客户上传数据个数
	 */
	public PageObject queryClientUploadCount(ClientInfo clientInfo, Date beginTime, Date endTime, QueryInfo queryInfo);
	
	public List<ClientInfo> queryClientInfo();
	
	/**
	 * 查询邀请过该客户的医生信息
	 * @param mobile
	 * @return
	 */
	public List<DoctorInfo> queryDoctorInviteClient(String mobile);
	
	/**
	 * 查询指定时间内客户注册个数
	 */
	public int queryRegClientCount(Date startDate, Date endDate);
	
	
	/**
	 * 查询指定时间内的图文咨询个数
	 */
	public int queryClientPictureCount(Date startDate, Date endDate);
	
	/**
	 * 查询当天医生邀请客户的数量
	 */
	public int queryDoctorInviteClientCount(String date);
	
	/**
	 * 查询当天客户邀请客户的数量
	 */
	public int queryClientInviteCount(String date);
	
	/**
	 * 查询当天自主注册客户的数量
	 */
	public int queryRegSelfCount(String date);
	
	public PageObject<ClientRegEval> queryAllInfoDate(Date startDate, Date endDate, QueryInfo queryInfo);
	
	public List<ClientRegEval> queryAllInfoDetail(Date startDate, Date endDate);
	
	/**
	 * 查询所有数据的总数
	 */
	public List<ClientRegEval> querySumInfoDetail(Date startDate, Date endDate);
	
	public void updateClientInfo(String data, int type, Integer cid);
	
	public ClientInfoExtend queryClientExtend(Integer clientId);
}
