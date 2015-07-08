package com.bskcare.ch.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.bskcare.ch.bo.ClientArchive;
import com.bskcare.ch.bo.ClientInfoSport;
import com.bskcare.ch.bo.ClientRegEval;
import com.bskcare.ch.bo.FamilyName;
import com.bskcare.ch.bo.crm.CrmClientInfo;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ClientPcard;
import com.bskcare.ch.vo.ProductCard;
import com.bskcare.ch.vo.client.ClientDiseaseFamily;
import com.bskcare.ch.vo.client.ClientDiseaseSelf;

@SuppressWarnings("unchecked")
public interface ClientInfoService {

	/**
	 * 创建会员用户信息和会员产品卡信息
	 */
	public ClientInfo createVIPClientInfo(ClientInfo u, ClientPcard cp,
			ProductCard productcard,String source);

	/**
	 * 获取创建亲情账号剩余数量
	 */
	public int createFamilyInfoNum(ProductCard productcard);

	/**
	 * 根据产品卡号多表查询该会员用户下亲情信息
	 */
	public List findFamilyInfoByPcCode(String pcCode);

	/**
	 * 根据客户id删除亲情用户信息(status = 1)
	 */
	public String deleteFamilyInfo(int id);

	/**
	 * 根据客户id修改亲情信息
	 */
	public void editFamilyInfo(int id, String name, String mobile, String email);

	/**
	 * 客户从亲情升级会员，只标记客户产品中间表状态
	 */
	public String deleteClientPcard(int id);

	/**
	 * 更新用户
	 */
	public void update(ClientInfo u);

	/**
	 * 更新用户密码
	 */
	public void updatePwdByClientId(int id, String password);

	/**
	 * 创建体验用户
	 * @param u
	 * @param source
	 * @param passivityinvite 被邀请码
	 * @return
	 */
	public ClientInfo createExperienceClient(ClientInfo u,String source,String passivityinvite);

	public void getClientByAjax(HttpServletRequest request,
			HttpServletResponse response) throws IOException;

	/**
	 * 更新用户信息
	 */
	public void updateClientInfo(ClientInfo clientInfo) ;

	/**
	 * 分区域查询用户列表
	 */
	public PageObject queryClientsByUserId(QueryInfo info, ClientInfo ci,
			String areaChain,QueryCondition queryCondition);

	/**
	 * 根据用户id查询用户的详细信息
	 */

	public ClientInfo findClientById(Integer id);
	/**
	 * 通过ClientInfo对象查出 ClientInfo
	 * @param client
	 * @return
	 */
	public ClientInfo findClientInfo(ClientInfo client);
	
	/**
	 * 更新ClientInfo 基本信息表
	 */
	public void updateBasicInfo(ClientInfo client, ClientInfo oldClient);
	
	/**
	 * 发送验证邮箱
	 */
	public void checkingEmail(ClientInfo sClientInfo, HttpServletRequest request,HttpServletResponse httpServletResponse) throws FileNotFoundException, IOException;
	/**
	 * 根据条件查询
	 */
	public List<ClientInfo> findAllClientInfo(ClientInfo client);	
	/**
	 * 返回字符串类型的用户信息，包含用户所属区域
	 */
	public String getStringClientInfo(ClientInfo c);
	public String getStringClientInfo(ClientInfo client,String areaCahin);
	/**
	 * 获取 亲情 账号
	 * @param familyRelation
	 * @return
	 */
	public List<FamilyName> getFamilyList(ClientInfo clientInfo);
	/**
	 * 获取被xxx绑定的亲情号
	 * @param ci
	 * @return
	 */
	public List<FamilyName> getFamilyByfamilyId(ClientInfo ci);
	
	public ClientInfo get(Integer cid);
	
	/**
	 * 添加/修改用户健康档案的信息
	 * @throws ParseException 
	 */
	public String updateHealArchive(String cid,String data) throws Exception;
	
	public String findHealArchive(String id);
	
	/**
	 * 根据用户所在区域编号，查询区域链并保存区域链
	 */
	public void saveAreaChain(Integer areaId);
	
	/**没用   2014-05-04**/
	/*public List queryClients(ClientInfo ci,String areaChain, QueryCondition queryCondition);*/
	
	/**查询用户信息运动数据部分**/
	public String queryClientInfoSport(Integer clientId);
	
	/**修改用户信息运动数据部分**/
	public String updateClientInfoSport(ClientInfoSport csport);

	
	/**
	 * 根据区域id修改用户区域链
	 */
	public String updateAreaChain(String ClientData);
	

	/**
	 * 修改客户市场评分
	 */
	public void alterBazzer(Integer client,Integer bazzer);
	/**
	 * 修改客户市场评分
	 */
	public void alterHealth(Integer client,Integer health);
	
	/**
	 * 根据区域id修改用户区域链
	 */
	public String updateAreaChainByClientId(String ClientData);
	
	/**根据用户信息修改用户（crm修改同步）**/
	public String updateClientInfoByCrm(String data);
	/**
	 * 修改负责人id
	 */
	public void alterPrincipal(Integer client,Integer principalId);
	
	/**
	 * 根据CrmClientInfo 获取 ClientInfo
	 */
	public ClientInfo getClientInfo(CrmClientInfo crmClientInfo);
	/**
	 * 更新用户头像文件名
	 */
	public void updateHeadPortrairByClientId(Integer clientId,String HeadPortrairName);
	
	/*黄金档案部分*/
	/**
	 * 查询黄金档案
	 */
	public ClientArchive queryGoldFiles(Integer clientId);
	
	/**
	 * 保存所有黄金档案数据，分别保存
	 */
	public void saveClientArchive(ClientArchive archive,Integer cid);
	/**crm用户转化云建康用户**/
	public ClientInfo sycUser(ClientInfo u,String source);
	/**根据手机号和邮箱地址查询**/
	public ClientInfo getClientByMobile(ClientInfo u);
	
	/**
	 * 根据产品是否过期修改用户信息
	 */
	public void updateInfoByProductExpire(ClientInfo u);
	
	public void saveClientArchive(Integer clientId, List<String[]> lst, ClientDiseaseSelf diseaseSelf, ClientDiseaseFamily diseaseFamily, ClientArchive archive);

	public ClientInfo queryClientInfo(ClientInfo clientInfo);

	

	//邀请医生添加积分
	public String addClientScore(String passivityinvite);
	
/*	
	//关注医生添加积分
	public String focuseDocterScore(Integer clientId);
	
	//评价医生添加积分
	public String evaluateDocterScore(Integer clientId);
*/
	
	public String regClient(ClientInfo clientInfo, String source, int evalType);
	
	public void updateClientComp(ClientInfo clientInfo);
	
	public String loginBack(ClientInfo clientinfo, String source, int evalType);
	
	/**
	 * 统计某段时间内血糖高管上传数据的人数
	 */
	public PageObject queryUploadClientInfo(ClientInfo clientInfo, QueryCondition queryCondition, QueryInfo queryInfo);
	
	/**
	 * 查询某段时间内客户上传数据个数
	 */
	public PageObject queryClientUploadCount(ClientInfo clientInfo, QueryCondition queryCondition, QueryInfo queryInfo);
	
	/**
	 * 新登陆接口
	 */
	public JSONObject newLogin(String account , String pwd , String source ,int evalType);

	/**
	 * 新注册接口
	 */
	public JSONObject newPhoneRegister(ClientInfo ci , String source , String passivityinvite , String popularize);

	public JSONObject sendCodeForSignIn(ClientInfo ci ,String date , String token );
	
	public JSONObject sendCodeForRetrievePwd(ClientInfo ci ,String date , String token  ) ;
	
	public int queryRegClientCount(QueryCondition queryCondition);
	
	public int queryAvgClientCount(QueryCondition queryCondition);
	
	public PageObject<ClientRegEval> queryClientAllInfo(QueryCondition queryCondition, QueryInfo queryInfo);
	
	public List<ClientRegEval> queryClientAllInfoDetail(List<ClientRegEval> lstClientReg, QueryInfo queryInfo);
	
	public List<ClientRegEval> querySumInfoDetail(QueryCondition queryCondition);
	
	
	public String updateClientInfo(String data, int type, Integer cid);
	
	/**
	 * 查询客户首页信息
	 */
	public String clientWelcomeInfo(Integer clientId);
	
	public void demo();
}
