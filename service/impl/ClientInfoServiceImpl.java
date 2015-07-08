package com.bskcare.ch.service.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.bo.ClientArchive;
import com.bskcare.ch.bo.ClientInfoExtend;
import com.bskcare.ch.bo.ClientInfoSport;
import com.bskcare.ch.bo.ClientRegEval;
import com.bskcare.ch.bo.FamilyName;
import com.bskcare.ch.bo.crm.CrmClientInfo;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.AreaInfoDao;
import com.bskcare.ch.dao.ClientDiseaseFamilyDao;
import com.bskcare.ch.dao.ClientDiseaseSelfDao;
import com.bskcare.ch.dao.ClientExtendDao;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.ClientLatestPhyDao;
import com.bskcare.ch.dao.ClientMedicalHistoryDao;
import com.bskcare.ch.dao.ClientPcardDao;
import com.bskcare.ch.dao.FamilyRelationDao;
import com.bskcare.ch.dao.ProductCardDao;
import com.bskcare.ch.dao.ntg.DeliverAddressDao;
import com.bskcare.ch.dao.ntg.NTgRedEnvelopDao;
import com.bskcare.ch.mail.util.MailSenderInfo;
import com.bskcare.ch.mail.util.SimpleMailSender;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.AreaInfoService;
import com.bskcare.ch.service.BloodSugarTargetService;
import com.bskcare.ch.service.ClientDiseaseFamilyService;
import com.bskcare.ch.service.ClientDiseaseSelfService;
import com.bskcare.ch.service.ClientExtendService;
import com.bskcare.ch.service.ClientFamilyHistoryService;
import com.bskcare.ch.service.ClientHobbyService;
import com.bskcare.ch.service.ClientInfoService;
import com.bskcare.ch.service.ClientLatestPhyService;
import com.bskcare.ch.service.ClientMedicalHistoryService;
import com.bskcare.ch.service.CrmClientInfoService;
import com.bskcare.ch.service.EvaluatingResultService;
import com.bskcare.ch.service.FileCompletionService;
import com.bskcare.ch.service.OrderMasterService;
import com.bskcare.ch.service.ProductCardService;
import com.bskcare.ch.service.ShortMessagService;
import com.bskcare.ch.service.TaskListService;
import com.bskcare.ch.service.medal.NTgMedalRecordService;
import com.bskcare.ch.service.msport.MSportPlanService;
import com.bskcare.ch.service.score.ScoreRecordService;
import com.bskcare.ch.service.tg.TgActivityService;
import com.bskcare.ch.service.tg.TgPopularizeActivityService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.CrmResponse;
import com.bskcare.ch.util.CrmURLConfig;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.HttpClientUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.MD5Util;
import com.bskcare.ch.util.Message;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.AreaInfo;
import com.bskcare.ch.vo.BloodSugarTarget;
import com.bskcare.ch.vo.ClientExtend;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ClientPcard;
import com.bskcare.ch.vo.ProductCard;
import com.bskcare.ch.vo.ShortMessage;
import com.bskcare.ch.vo.client.ClientDiseaseFamily;
import com.bskcare.ch.vo.client.ClientDiseaseSelf;
import com.bskcare.ch.vo.client.ClientFamilyHistory;
import com.bskcare.ch.vo.client.ClientHobby;
import com.bskcare.ch.vo.client.ClientLatestPhy;
import com.bskcare.ch.vo.client.ClientMedicalHistory;
import com.bskcare.ch.vo.medal.NTgMedalModule;
import com.bskcare.ch.vo.medal.NTgMedalRule;
import com.bskcare.ch.vo.msport.MSportPlan;
import com.bskcare.ch.vo.ntg.DeliverAddress;
import com.bskcare.ch.vo.ntg.NTgRedEnvelop;
import com.bskcare.ch.vo.tg.TgPopularizeActivity;

@Service
@SuppressWarnings("unchecked")
public class ClientInfoServiceImpl implements ClientInfoService {

	private transient final Logger log = Logger.getLogger(getClass());
	/**
	 * 用户密码不正确
	 */
	public static final int C_false = 1;

	/**
	 * 用户密码正确
	 */
	public static final int C_true = 0;

	/**
	 * 用户名或邮箱不正确
	 */
	public static final int CE_false = 1;

	/**
	 * 用户名或邮箱正确
	 */
	public static final int CE_true = 0;

	/**
	 * 验证码不正确
	 */
	public static final int CP_false = 1;

	/**
	 * 验证码正确
	 */
	public static final int CP_true = 0;

	/**
	 * 登录成功
	 */
	public static final int LS_TRUE = 1;

	/**
	 * 登录失败
	 */
	public static final int LS_FALSE = 0;

	@Autowired
	private ClientInfoDao clientUserDao;
	@Autowired
	private ClientPcardDao clientPcardDao;
	@Autowired
	private ProductCardDao productCardDao;
	@Autowired
	private FamilyRelationDao familyRelationDao;
	@Autowired
	private OrderMasterService orderMasterService;

	@Autowired
	private ClientHobbyService hobbyService;
	@Autowired
	private ClientMedicalHistoryService medicalService;
	@Autowired
	private ClientFamilyHistoryService familyService;
	@Autowired
	private ClientLatestPhyService latestPhyService;
	@Autowired
	private AreaInfoService areaInfoService;
	@Autowired
	private FileCompletionService fileCompletionService;
	@Autowired
	private ClientDiseaseSelfService clientDiseaseSelfService;
	@Autowired
	private ClientDiseaseFamilyService clientDiseaseFamilyService;
	@Autowired
	private ProductCardService cardService;

	/** 运动计划 **/
	@Autowired
	private MSportPlanService planService;

	@Autowired
	private CrmClientInfoService crmClientService;

	@Autowired
	private AreaInfoDao areaInfoDao;
	@Autowired
	private ClientMedicalHistoryDao medicalDao;
	@Autowired
	private ClientDiseaseSelfDao diseaseSelfDao;
	@Autowired
	private ClientDiseaseFamilyDao diseaseFamilyDao;
	@Autowired
	private ClientLatestPhyDao latestPhyDao;
	@Autowired
	private ClientExtendService extendService;
	@Autowired
	private ShortMessagService smService;
	@Autowired
	private TaskListService taskService;
	@Autowired
	private OrderMasterService masterService;
	@Autowired
	private ScoreRecordService scoreService;
	@Autowired
	private ClientExtendDao extendDao;
	
	@Autowired
	private EvaluatingResultService resultService;
	@Autowired
	private ClientExtendService clientExtendService;

	@Autowired
	private TgActivityService activityService;
	@Autowired
	private TgPopularizeActivityService popuService;
	@Autowired
	private BloodSugarTargetService targetService;
	@Autowired
	private ShortMessagService shortMessagService;
	@Autowired
	private NTgRedEnvelopDao redEnvelopDao;
	@Autowired
	private DeliverAddressDao addressDao;
	@Autowired
	private NTgMedalRecordService medalRecordService;
	
	
	private ClientInfo clientInfo;
	private ClientHobby hobby;
	private ClientMedicalHistory medical;
	private ClientFamilyHistory family;
	private ClientLatestPhy latestPhy;

	public void update(ClientInfo u) {
		clientUserDao.update(u);
	}

	public ClientInfo createVIPClientInfo(ClientInfo u, ClientPcard cp,
			ProductCard productcard,String source) {

		if(u != null){
			u.setBazzaarGrade(3);//初始化市场评分
		}
		
		String MD5Pwd = MD5Util.digest(u.getPassword());
		u.setPassword(MD5Pwd);
		u.setAreaChain(areaInfoService.getAreaChainByAreaId(u.getAreaId())); // 设置区域链
		ClientInfo ci = clientUserDao.add(u);

		productCardDao.updatePcStatus(productcard.getCode());
		cp.setClientId(u.getId());

		clientPcardDao.addClientPcard(cp);

		orderMasterService.createRegisterOrder(ClientInfo.TYPE_VIP, ci);
		//升级提醒
		taskService.levelUp(ci.getAreaId(),ci.getId());
		ClientInfo client = clientUserDao.load(ci.getId());
		crmClientService.addCrmClientInfo(client);
		if(ci != null &&!StringUtils.isEmpty(source)){
			extendService.addRegSource(ci.getId(), source , null);
		}
		return ci;
	}

	public List findFamilyInfoByPcCode(String pcCode) {
		List familyInfo = clientUserDao.findFamilyInfoByPcCode(pcCode);
		return familyInfo;
	}

	public ClientInfo createExperienceClient(ClientInfo u,String source,String passivityinvite) {
		if(u != null){
			u.setBazzaarGrade(3);//初始化市场评分
		}
		if(!StringUtils.isEmpty(u.getPassword())){
			String MD5Pwd = MD5Util.digest(u.getPassword());
			u.setPassword(MD5Pwd);
		}
		u.setType(ClientInfo.TYPE_EXPERIENCE);
		u.setCreateTime(new Date());

		//判断如果是血糖来源的用户，自动分配区域
		if(!StringUtils.isEmpty(source)){
			if ( StringUtils.isXTGGUser(source.split("-")[0]) ){//血糖高管
				//
				System.out.println("血糖体验用户祖册，自动分配区域");
				String bloodsugarArea=SystemConfig.getString("bloodsugar_areaid");		
				System.out.println("获得血糖高管默认区域："+bloodsugarArea);
				Integer area =  masterService.getAreaIdByAreaInfo(Integer.parseInt(bloodsugarArea));
				System.out.println("将此用户归属到"+area+"地区");
				
				u.setAreaId(area == null ? Integer.parseInt(bloodsugarArea) : area);// 修改体验用户的归属地
				System.out.println("用户被分配到"+u.getAreaId());
			} else if(source.equals("307hospital")){
				Integer area = Integer.parseInt(SystemConfig.getString("307_area_id"));
				u.setAreaId(area);// 修改用户的归属地
				System.out.println("用户被分配到"+u.getAreaId());
			} else if(source.equals("cmcc")) {	// 移动
				Integer area = Integer.parseInt(SystemConfig.getString("cmcc_area_id"));
				u.setAreaId(area);// 修改用户的归属地
			}
		}

		u.setAreaChain(areaInfoService.getAreaChainByAreaId(u.getAreaId())); // 设置区域链
		System.out.println("用户的区域链"+u.getAreaChain());
		ClientInfo c = clientUserDao.add(u);
		
		/**
		 * hzq : 2014年12月22日14:52:19 注释掉普通用户注册生成的订单，没用
		 * orderMasterService.createRegisterOrder(ClientInfo.TYPE_EXPERIENCE, c);
		 * 
		 * 2015-04-24 ：为了配合千人推广活动，启用并修改该部分代码
		 */
		if(!StringUtils.isEmpty(source) && source.equals("php_sugar_1000")) {
			
			Integer productId = getProductIdByAreaId(c.getAreaId());
			
			if(null != productId) {
				// 根据区域获取对应的服务，直接升级
				ProductCard pcard = cardService.queryFirstUnusedCard(
						Integer.parseInt(SystemConfig.getString("online_area_id")), productId);
				
				if(null != pcard) {
					c.setVipCard(pcard.getCode());
					c.setAvailableProduct(getAvaiProduct(
							c.getAvailableProduct(), pcard.getMainProductId()));
					
					productCardDao.updatePcStatus(pcard.getCode());
					
					orderMasterService.createRegisterOrder(ClientInfo.TYPE_VIP, c);
				}
			}
		}
		
		ClientInfo client = clientUserDao.load(c.getId());
		crmClientService.addCrmClientInfo(client);
		
		registToHuanxin(client);//注册环信
		
		//发送注册短信
		sendMsgToCustomer(u, source);
		
		if(c != null&& (!StringUtils.isEmpty(source))){
			extendService.addRegSource(c.getId(), source,passivityinvite);
		}
		taskService.registerRemind(u.getAreaId(), u.getId());
		
		//注册成功，给积分
		//判断客户填的邀请码是否是客户给的
		addCoins(u, passivityinvite);
		return c;
	}
	
	private String getAvaiProduct(String availableProduct, Integer mainProductId) {
		if(!StringUtils.isEmpty(availableProduct)) {
			// 判断一下现在可用的产品是否包含要添加的，如果包含不添加，否则添加
			if(null != mainProductId && availableProduct.indexOf(mainProductId + Constant.RPT_TAG_SPLIT) < 0){
				return availableProduct + mainProductId + Constant.RPT_TAG_SPLIT;
			}
			
		} else {
			return mainProductId + Constant.RPT_TAG_SPLIT;
		}
		return availableProduct;
	}


	private Integer getProductIdByAreaId(Integer areaId) {
		String productId = null;
		switch (areaId) {
			case 1827: //组3
				productId = SystemConfig.getString("tg_group3_productId");
				break;
			case 1828: //组4
				productId = SystemConfig.getString("tg_group4_productId");
				break;
			case 1837: //对照A组
				break;
			case 1838: //对照B组
				break;
			default: //默认为5组
				productId = SystemConfig.getString("tg_group5_productId");
				break;
		}
		
		if (null == productId) {
			return null;
		}
		
		return Integer.parseInt(productId);
	}

	public int createFamilyInfoNum(ProductCard productcard) {
		Object num = clientPcardDao.getCodeNum(productcard.getCode());
		int existAmount = 0;
		int allowCount = 0;
		if (null != num) {
			existAmount = Integer.parseInt(num.toString());
		}
		Object obj = clientPcardDao.getAllowCount(productcard.getCode());
		if (null != obj) {
			allowCount = Integer.parseInt(obj.toString());
		}
		int surplus = allowCount - existAmount;
		return surplus;
	}

	public String deleteFamilyInfo(int id) {
		clientUserDao.deleteFamilyInfoById(id);
		clientPcardDao.deleteFamilyInfoByClientId(id);
		return "ok";
	}

	public String deleteClientPcard(int id) {
		clientPcardDao.deleteFamilyInfoByClientId(id);
		return "ok";
	}

	public void sendMessage(ClientInfo ci,String function){
		System.out.println("客户id为"+ci.getId()+"，电话号码为空。方法为："+function);
		ShortMessage sm = new ShortMessage();
		sm.setContent("客户id为"+ci.getId()+"，电话号码为空。方法为："+function);
		sm.setMobile("18612834872");
		sm.setClientId(10001);
		smService.sendMessage(sm, "more");
	}
	
	public void editFamilyInfo(int id, String name, String mobile, String email){
		if(id !=0 && StringUtils.isEmpty(mobile)){
			ClientInfo ci = new ClientInfo();
			ci.setId(id);
			sendMessage(ci, "editFamilyInfo");
			return;
		}else{
			clientUserDao.updateFamilyInfo(id, name, mobile, email);
		}
	}

	// 修改密码
	public void updatePwdByClientId(int id, String password) {
		clientUserDao.updatePwdByClientId(id, password);
	}

	public void getClientByAjax(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

	}

	public void updateClientInfo(ClientInfo clientInfo){
		if (null != clientInfo) {
/*			
			if(StringUtils.isEmpty(clientInfo.getMobile())){
				sendMessage(clientInfo, "updateClientInfo");
			}
*/
			
			ClientInfo ci = findClientById(clientInfo.getId());
			if (null != ci) {
				BeanUtils.copyProperties(clientInfo, ci, new String[] {
						"createTime", "account", "password", "vipCard", "type",
						"integral", "areaId", "mobile", "status", "levelId",
						"constellation", "chineseZodiac", "principalId",
						"healthIndex", "areaChain", "bazzaarGrade",
						"finishPercent", "headPortrait", "nickName","availableProduct","uid","compSource","clientCode" });

				// set value for age
				ci.setAge(DateUtils.getAgeByBirthday(ci.getBirthday()));

				clientUserDao.update(ci);
				ClientInfo client = clientUserDao.load(ci.getId());
				crmClientService.updateCrmClientInfo(client, "web");
			}

		}
	}

	public PageObject queryClientsByUserId(QueryInfo info, ClientInfo ci,
			String areaChain, QueryCondition queryCondition) {
		return clientUserDao.queryClientsByUserId(info, ci, areaChain,
				queryCondition);
	}

	/**
	 * 根据用户id查询用户的详细信息
	 */
	public ClientInfo findClientById(Integer id) {
		if (null == id)
			return null;
		return clientUserDao.load(id);
	}

	public ClientInfo findClientInfo(ClientInfo client) {
		if (null != client) {
			List<ClientInfo> clientList = clientUserDao.findClientInfo(client);
			if (!CollectionUtils.isEmpty(clientList)) {
				return clientList.get(0);
			}
		}
		return null;
	}

	public void updateBasicInfo(ClientInfo client, ClientInfo oldClient) {
		if (client.getName() != null) {
			oldClient.setName(client.getName());
		}
		if (client.getGender() != null) {
			oldClient.setGender(client.getGender());
		}
		if (client.getBirthday() != null) {
			oldClient.setBirthday(client.getBirthday());
			oldClient.setAge(DateUtils.getAgeByBirthday(client.getBirthday())); //重新计算年龄
		}
		if (!StringUtils.isEmpty(client.getMobile()) && StringUtils.isMobile(client.getMobile())) {
			oldClient.setMobile(client.getMobile());
		}
		if (client.getAge() != null) {
			oldClient.setAge(client.getAge());
		}
		if (client.getConstellation() != null) {
			oldClient.setConstellation(client.getConstellation());
		}
		if (client.getChineseZodiac() != null) {
			oldClient.setChineseZodiac(client.getChineseZodiac());
		}
		if (client.getBloodType() != null) {
			oldClient.setBloodType(client.getBloodType());
		}
		if (client.getEmail() != null) {
			oldClient.setEmail(client.getEmail());
		}
		if (client.getUsualAddress() != null) {
			oldClient.setUsualAddress(client.getUsualAddress());
		}
		if (client.getPostcode() != null) {
			oldClient.setPostcode(client.getPostcode());
		}
		if (client.getAccount() != null) {
			oldClient.setAccount(client.getAccount());
		}

		clientUserDao.update(oldClient);
		ClientInfo ci = clientUserDao.load(oldClient.getId());
		crmClientService.updateCrmClientInfo(ci, "web");
	}

	public void checkingEmail(ClientInfo client, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		MailSenderInfo mailInfo = new MailSenderInfo();
		System.out.println("发送地址" + client.getEmail());
		mailInfo.setToAddress(client.getEmail());
		mailInfo.setSubject("百生康邮件验证码");
		String htmlUrl = request.getSession().getServletContext().getRealPath(
				"/").replace("\\", "/")
				+ SystemConfig.getString("email_html");
		// 读取html文件
		File file = new File(htmlUrl);
		InputStream in = new FileInputStream(file);

		byte[] tempbytes = new byte[1000];
		in.read(tempbytes);
		// 进行二进制编码转换
		String text = new String(tempbytes, "utf-8");
		String number = RandomUtils.getRandomNumber(4);
		String image_base_url = SystemConfig.getString("image_base_url");
		StringBuffer emailMsg = new StringBuffer("<img src=\"" + image_base_url
				+ "ch/images/email_pwd_correct.jpg\"/></br>");
		emailMsg.append("欢迎使用百生康健康管理系统，祝您愉快：</br>");
		emailMsg.append("邮箱验证码：" + number);
		mailInfo.setContent(emailMsg.toString());
		// mailInfo.setContent("邮箱验证码："+number);
		System.out.println("邮箱验证码：" + number);

		boolean result = SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式

		System.out.println("发送状态" + result);
		MailSenderInfo sms = new MailSenderInfo();
		String emailLoginUrl = sms.getEmailType(client.getEmail());
		// 返回输出流
		PrintWriter out = response.getWriter();
		if (result) {
			out.println("{\"code\":\"" + number + "\",\"emailLoginUrl\":\""
					+ emailLoginUrl + "\"}");
		} else {
			out.println("{\"code\":\"" + number + "\",\"emailLoginUrl\":\""
					+ emailLoginUrl + "\"}");
		}
		out.flush();
	}

	public String getStringClientInfo(ClientInfo client) {
		if (null == client)
			return null;

		List<Object> ciList = clientUserDao.getClientInfoAndArea(client);

		JSONArray ja = new JSONArray();
		if (!ciList.isEmpty()) {
			for (Object obj : ciList) {
				JSONObject jo = new JSONObject();
				ClientInfo c = (ClientInfo) ((Object[]) obj)[0];
				AreaInfo a = (AreaInfo) (((Object[]) obj)[1]);
				jo.put("name", c.getName());
				jo.put("age", c.getAge());
				jo.put("type", c.getType());
				jo.put("gender", c.getGender());
				jo.put("mobile", c.getMobile());
				jo.put("join", DateUtils.format(c.getCreateTime()));
				jo.put("area", a.getName());
				jo.put("cid", c.getId());
				ja.add(jo);
			}
		}
		return ja.toString();
	}

	/**
	 * 根据用户条件和所在区域联查询用户列表
	 */

	public String getStringClientInfo(ClientInfo client, String areaCahin) {
		if (null == client)
			return null;
		List<Object> ciList = clientUserDao.getClientInfoAndArea(client,
				areaCahin);
		JSONArray ja = new JSONArray();
		if (!ciList.isEmpty()) {
			for (Object obj : ciList) {
				JSONObject jo = new JSONObject();
				ClientInfo c = (ClientInfo) ((Object[]) obj)[0];
				AreaInfo a = (AreaInfo) (((Object[]) obj)[1]);
				jo.put("name", c.getName());
				jo.put("age", c.getAge());
				jo.put("type", c.getType());
				jo.put("gender", c.getGender());
				jo.put("mobile", c.getMobile());
				jo.put("join", DateUtils.format(c.getCreateTime()));
				jo.put("area", a.getName());
				jo.put("cid", c.getId());
				jo.put("areaId", c.getAreaId());
				jo.put("principalId", c.getPrincipalId());
				ja.add(jo);
			}
		}
		return ja.toString();
	}

	/**
	 * 添加/修改用户健康档案的信息
	 * 
	 * @throws ParseException
	 */
	public String updateHealArchive(String cid, String data) throws Exception{
		JSONObject json = new JSONObject();
		if (!StringUtils.isEmpty(cid)) {

			if (!StringUtils.isEmpty(data)) {
				Map<String, Object> categoryMap = new HashMap<String, Object>();
				categoryMap = JsonUtils.getMap4Json(data);

				Integer id = Integer.parseInt(cid);

				// 基本信息修改
				clientInfo = new ClientInfo();
				clientInfo.setId(id);

				if (null != categoryMap.get("gender")
						&& !"null".equals(categoryMap.get("gender") + "")
						&& !"".equals(categoryMap.get("gender") + "")) {
					Integer gender = Integer.parseInt(categoryMap.get("gender")
							.toString());

					clientInfo.setGender(gender);
				}

				if (!categoryMap.get("name").equals("")) {
					clientInfo.setName(categoryMap.get("name").toString());
				}

				if (!categoryMap.get("birthday").equals("")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date birthday = sdf.parse(categoryMap.get("birthday")
							.toString());
					clientInfo.setBirthday(birthday);
					clientInfo.setAge(DateUtils.getAgeByBirthday(birthday)); //重新计算年龄
				}
				if (!categoryMap.get("usualAddress").equals("")) {
					clientInfo.setUsualAddress(categoryMap.get("usualAddress")
							.toString());
				}

				clientUserDao.updateClientInfoAndroid(clientInfo);
				ClientInfo client = clientUserDao.load(clientInfo.getId());
				crmClientService.updateCrmClientInfo(client, "web");

				// 生活习惯
				hobby = new ClientHobby();
				hobby.setClientId(id);

				if (null != categoryMap.get("hid")
						&& !"null".equals(categoryMap.get("hid") + "")
						&& !"".equals(categoryMap.get("hid") + "")) {
					hobby.setId(Integer.parseInt(categoryMap.get("hid")
							.toString()));
				}

				if (null != categoryMap.get("smoke")
						&& !"null".equals(categoryMap.get("smoke") + "")
						&&!"".equals(categoryMap.get("smoke") + "")
						&&!categoryMap.get("smoke").equals("")) {
					hobby.setSmoke(Integer.parseInt(categoryMap.get("smoke")
							.toString()));
				}
				if (null != categoryMap.get("sage")
						&& !"null".equals(categoryMap.get("sage") + "")
						&& !"".equals(categoryMap.get("sage") + "")) {

					hobby.setSage(Integer.parseInt(categoryMap.get("sage")
							.toString()));
				}

				if (null != categoryMap.get("drink")
						&& !"null".equals(categoryMap.get("drink") + "")
						&&!"".equals(categoryMap.get("drink") + "")
						&&!categoryMap.get("drink").equals("")) {
					hobby.setDrink(Integer.parseInt(categoryMap.get("drink")
							.toString()));
				}

				if (null != categoryMap.get("dage")
						&& !"null".equals(categoryMap.get("dage") + "")
						&& !"".equals(categoryMap.get("dage") + "")) {

					hobby.setDage(Integer.parseInt(categoryMap.get("dage")
							.toString()));
				}

				if (!categoryMap.get("sleeping").equals("")) {
					hobby.setSleeping(Integer.parseInt(categoryMap.get(
							"sleeping").toString()));
				}

				if (null != categoryMap.get("sportCount")
						&& !"null".equals(categoryMap.get("sportCount") + "")
						&& !"".equals(categoryMap.get("sportCount") + "")) {

					hobby.setSportCount(Integer.parseInt(categoryMap.get(
							"sportCount").toString()));
				}
				if (!categoryMap.get("sportTime").equals("")) {
					hobby.setSportTime(Integer.parseInt(categoryMap.get(
							"sportTime").toString()));
				}
				if (!categoryMap.get("sportType").equals("")) {
					// 保存用户运动类型
					// 获得原来bobby对象
					ClientHobby clientHobby = hobbyService.getClientHobby(id);
					String oldSportType = "";
					if(clientHobby!=null&&clientHobby.getSportType()!=null){
						// 原始运动类型
						oldSportType = clientHobby.getSportType();
					}
					// 上传运动类型
					Object osType = categoryMap.get("sportType");
					
					String newSportType = "";
					if(null != osType) {
						newSportType = osType.toString();
						newSportType = newSportType.replace("1", "跑步").replace("2", "游泳")
										.replace(",3", "")
										.replace("3,", "")
										.replace("3", "")
										.replace("4", "太极拳")
										.replace(",5", "")
										.replace("5,", "")
										.replace("5", "");
					}
					// 处理后的运动类型
					String sportType = hobbyService.sportTypeSole(newSportType,
							oldSportType);
					// 处理原来的运动类型与新运动类型一致
					hobby.setSportType(sportType);
				}
				if (!categoryMap.get("sportSupply").equals("") && !categoryMap.get("sportSupply").equals("null")) {
					hobby.setSportSupply(categoryMap.get("sportSupply")
							.toString());
				}
				hobbyService.saveOrUpdateAndroid(hobby);
				// 既往患病史
				medical = medicalService.getClientMedicalHistory(id);
				if (medical == null) {
					medical = new ClientMedicalHistory();
				}
				if (null != categoryMap.get("isHasMedical")
						&& !"null".equals(categoryMap.get("isHasMedical") + "")
						&& !"".equals(categoryMap.get("isHasMedical") + "")) {

					medical.setIsHasMedical(Integer.parseInt(categoryMap.get(
							"isHasMedical").toString()));
				}
				if (null != categoryMap.get("isHasFamily")
						&& !"null".equals(categoryMap.get("isHasFamily") + "")
						&& !"".equals(categoryMap.get("isHasFamily") + "")) {
					medical.setIsHasMedical(Integer.parseInt(categoryMap.get(
							"isHasFamily").toString()));
				}
				medicalService.saveOrUpdate(medical);
				// 个人疾病
				List<String> selfDiseaseList = new ArrayList<String>();
				if (null != categoryMap.get("hasGxy")
						&& !"null".equals(categoryMap.get("hasGxy") + "")
						&& !"".equals(categoryMap.get("hasGxy") + "")
						&& !"2".equals(categoryMap.get("hasGxy") + "")) {
					selfDiseaseList.add("13");
				}
				if (null != categoryMap.get("hasGxb")
						&& !"null".equals(categoryMap.get("hasGxb") + "")
						&& !"".equals(categoryMap.get("hasGxb") + "")
						&& !"2".equals(categoryMap.get("hasGxb") + "")) {
					selfDiseaseList.add("29");
				}
				if (null != categoryMap.get("hasNzf")
						&& !"null".equals(categoryMap.get("hasNzf") + "")
						&& !"".equals(categoryMap.get("hasNzf") + "")
						&& !"2".equals(categoryMap.get("hasNzf") + "")) {
					selfDiseaseList.add("30");
				}
				if (null != categoryMap.get("hasTnbI")
						&& !"null".equals(categoryMap.get("hasTnbI") + "")
						&& !"".equals(categoryMap.get("hasTnbI") + "")
						&& !"2".equals(categoryMap.get("hasTnbI") + "")) {
					selfDiseaseList.add("1");
				}
				if (null != categoryMap.get("hasExzl")
						&& !"null".equals(categoryMap.get("hasExzl") + "")
						&& !"".equals(categoryMap.get("hasExzl") + "")
						&& !"2".equals(categoryMap.get("hasExzl") + "")) {
					selfDiseaseList.add("4");
				}
				if (null != categoryMap.get("hasGxz")
						&& !"null".equals(categoryMap.get("hasGxz") + "")
						&& !"".equals(categoryMap.get("hasGxz") + "")
						&& !"2".equals(categoryMap.get("hasGxz") + "")) {
					selfDiseaseList.add("2");
				}
				if (null != categoryMap.get("hasMz")
						&& !"null".equals(categoryMap.get("hasMz") + "")
						&& !"".equals(categoryMap.get("hasMz") + "")
						&& !"2".equals(categoryMap.get("hasMz") + "")) {
					selfDiseaseList.add("31");
				}
				if (null != categoryMap.get("hasXc")
						&& !"null".equals(categoryMap.get("hasXc") + "")
						&& !"".equals(categoryMap.get("hasXc") + "")
						&& !"2".equals(categoryMap.get("hasXc") + "")) {
					selfDiseaseList.add("32");
				}
				if (null != categoryMap.get("hasFqz")
						&& !"null".equals(categoryMap.get("hasFqz") + "")
						&& !"".equals(categoryMap.get("hasFqz") + "")
						&& !"2".equals(categoryMap.get("hasFqz") + "")) {
					selfDiseaseList.add("33");
				}
				if (null != categoryMap.get("hasFjh")
						&& !"null".equals(categoryMap.get("hasFjh") + "")
						&& !"".equals(categoryMap.get("hasFjh") + "")
						&& !"2".equals(categoryMap.get("hasFjh") + "")) {
					selfDiseaseList.add("34");
				}
				String[] selfDiseases = new String[selfDiseaseList.size()];
				clientDiseaseSelfService.updateDiseaseSelf(selfDiseaseList
						.toArray(selfDiseases), id);
				//个人其他疾病
				if (null != categoryMap.get("other")
						&& !"null".equals(categoryMap.get("other") + "")
						&& !"".equals(categoryMap.get("other") + "")
						&& !"2".equals(categoryMap.get("other") + "")) {
					ClientDiseaseSelf self = clientDiseaseSelfService.queryDiseaseSelfOther(id);
					clientDiseaseSelfService.updateDiseaseSelfOther(self, id);
				}else{
					clientDiseaseSelfService.deleteOtherDiseaseByCid(id);
				}
				// 家族病史
				List<String> familyDiseaseFAList = new ArrayList<String>();// 父亲
				if (null != categoryMap.get("faHasGxy")
						&& !"null".equals(categoryMap.get("faHasGxy") + "")
						&& !"".equals(categoryMap.get("faHasGxy") + "")
						&& !"2".equals(categoryMap.get("faHasGxy") + "")) {
					familyDiseaseFAList.add("13");
				}
				if (null != categoryMap.get("faHasGxb")
						&& !"null".equals(categoryMap.get("faHasGxb") + "")
						&& !"".equals(categoryMap.get("faHasGxb") + "")
						&& !"2".equals(categoryMap.get("faHasGxb") + "")) {
					familyDiseaseFAList.add("29");
				}
				if (null != categoryMap.get("faHasNxg")
						&& !"null".equals(categoryMap.get("faHasNxg") + "")
						&& !"".equals(categoryMap.get("faHasNxg") + "")
						&& !"2".equals(categoryMap.get("faHasNxg") + "")) {
					familyDiseaseFAList.add("36");
				}
				if (null != categoryMap.get("faHasTnb")
						&& !"null".equals(categoryMap.get("faHasTnb") + "")
						&& !"".equals(categoryMap.get("faHasTnb") + "")
						&& !"2".equals(categoryMap.get("faHasTnb") + "")) {
					familyDiseaseFAList.add("1");
				}
				if (null != categoryMap.get("faHasExzl")
						&& !"null".equals(categoryMap.get("faHasExzl") + "")
						&& !"".equals(categoryMap.get("faHasExzl") + "")
						&& !"2".equals(categoryMap.get("faHasExzl") + "")) {
					familyDiseaseFAList.add("4");
				}
				if (null != categoryMap.get("faHasJhb")
						&& !"null".equals(categoryMap.get("faHasJhb") + "")
						&& !"".equals(categoryMap.get("faHasJhb") + "")
						&& !"2".equals(categoryMap.get("faHasJhb") + "")) {
					familyDiseaseFAList.add("34");
				}
				if (null != categoryMap.get("faHasGy")
						&& !"null".equals(categoryMap.get("faHasGy") + "")
						&& !"".equals(categoryMap.get("faHasGy") + "")
						&& !"2".equals(categoryMap.get("faHasGy") + "")) {
					familyDiseaseFAList.add("23");
				}
				if (null != categoryMap.get("faHasJsb")
						&& !"null".equals(categoryMap.get("faHasJsb") + "")
						&& !"".equals(categoryMap.get("faHasJsb") + "")
						&& !"2".equals(categoryMap.get("faHasJsb") + "")) {
					familyDiseaseFAList.add("37");
				}
				String[] familyDiseasesFA = new String[familyDiseaseFAList
						.size()];
				clientDiseaseFamilyService.updateDiseaseFamily(
						familyDiseaseFAList.toArray(familyDiseasesFA),
						ClientDiseaseFamily.FAMILY_FA, id);
				List<String> familyDiseaseMOList = new ArrayList<String>();// 母亲
				if (null != categoryMap.get("moHasGxy")
						&& !"null".equals(categoryMap.get("moHasGxy") + "")
						&& !"".equals(categoryMap.get("moHasGxy") + "")
						&& !"2".equals(categoryMap.get("moHasGxy") + "")) {
					familyDiseaseMOList.add("13");
				}
				if (null != categoryMap.get("moHasGxb")
						&& !"null".equals(categoryMap.get("moHasGxb") + "")
						&& !"".equals(categoryMap.get("moHasGxb") + "")
						&& !"2".equals(categoryMap.get("moHasGxb") + "")) {
					familyDiseaseMOList.add("29");
				}
				if (null != categoryMap.get("moHasNxg")
						&& !"null".equals(categoryMap.get("moHasNxg") + "")
						&& !"".equals(categoryMap.get("moHasNxg") + "")
						&& !"2".equals(categoryMap.get("moHasNxg") + "")) {
					familyDiseaseMOList.add("36");
				}
				if (null != categoryMap.get("moHasTnb")
						&& !"null".equals(categoryMap.get("moHasTnb") + "")
						&& !"".equals(categoryMap.get("moHasTnb") + "")
						&& !"2".equals(categoryMap.get("moHasTnb") + "")) {
					familyDiseaseMOList.add("1");
				}
				if (null != categoryMap.get("moHasExzl")
						&& !"null".equals(categoryMap.get("moHasExzl") + "")
						&& !"".equals(categoryMap.get("moHasExzl") + "")
						&& !"2".equals(categoryMap.get("moHasExzl") + "")) {
					familyDiseaseMOList.add("4");
				}
				if (null != categoryMap.get("moHasJhb")
						&& !"null".equals(categoryMap.get("moHasJhb") + "")
						&& !"".equals(categoryMap.get("moHasJhb") + "")
						&& !"2".equals(categoryMap.get("moHasJhb") + "")) {
					familyDiseaseMOList.add("34");
				}
				if (null != categoryMap.get("moHasGy")
						&& !"null".equals(categoryMap.get("moHasGy") + "")
						&& !"".equals(categoryMap.get("moHasGy") + "")
						&& !"2".equals(categoryMap.get("moHasGy") + "")) {
					familyDiseaseMOList.add("23");
				}
				if (null != categoryMap.get("moHasJsb")
						&& !"null".equals(categoryMap.get("moHasJsb") + "")
						&& !"".equals(categoryMap.get("moHasJsb") + "")
						&& !"2".equals(categoryMap.get("moHasJsb") + "")) {
					familyDiseaseMOList.add("37");
				}
				String[] familyDiseasesMO = new String[familyDiseaseMOList
						.size()];
				clientDiseaseFamilyService.updateDiseaseFamily(
						familyDiseaseMOList.toArray(familyDiseasesMO),
						ClientDiseaseFamily.FAMILY_MO, id);
				// 其他家族疾病
				ClientDiseaseFamily clientDiseaseFamily=null;
				if(null != categoryMap.get("faHasOther")
						&& !"null".equals(categoryMap.get("faHasOther") + "")
						&& !"".equals(categoryMap.get("faHasOther") + "")
						&& !"2".equals(categoryMap.get("faHasOther") + "")) {
					clientDiseaseFamilyService.deletefamilyOther(id, ClientDiseaseFamily.FAMILY_FA, ClientDiseaseFamily.DISEASE_CHECKED_NO);
					clientDiseaseFamily = new ClientDiseaseFamily();
					clientDiseaseFamily.setClientId(id);
					clientDiseaseFamily.setDisease(categoryMap.get("familyOther") + "");
					clientDiseaseFamily.setFamilyType(ClientDiseaseFamily.FAMILY_FA);
					clientDiseaseFamily.setType(ClientDiseaseFamily.DISEASE_CHECKED_NO);
					clientDiseaseFamilyService.add(clientDiseaseFamily);
				}else{
					clientDiseaseFamilyService.deletefamilyOther(id, ClientDiseaseFamily.FAMILY_FA, ClientDiseaseFamily.DISEASE_CHECKED_NO);
				}
				if(null != categoryMap.get("moHasOther")
						&& !"null".equals(categoryMap.get("moHasOther") + "")
						&& !"".equals(categoryMap.get("moHasOther") + "")
						&& !"2".equals(categoryMap.get("moHasOther") + "")) {
					clientDiseaseFamilyService.deletefamilyOther(id, ClientDiseaseFamily.FAMILY_MO, ClientDiseaseFamily.DISEASE_CHECKED_NO);
					clientDiseaseFamily = new ClientDiseaseFamily();
					clientDiseaseFamily.setClientId(id);
					clientDiseaseFamily.setDisease(categoryMap.get("familyOther") + "");
					clientDiseaseFamily.setFamilyType(ClientDiseaseFamily.FAMILY_MO);
					clientDiseaseFamily.setType(ClientDiseaseFamily.DISEASE_CHECKED_NO);
					clientDiseaseFamilyService.add(clientDiseaseFamily);
				}else{
					clientDiseaseFamilyService.deletefamilyOther(id, ClientDiseaseFamily.FAMILY_MO, ClientDiseaseFamily.DISEASE_CHECKED_NO);
				}
				

				// 最后一次体检体检记录
				latestPhy = new ClientLatestPhy();

				if (null != categoryMap.get("pid")
						&& !"null".equals(categoryMap.get("pid") + "")
						&& !"".equals(categoryMap.get("pid") + "")) {

					latestPhy.setId(Integer.parseInt(categoryMap.get("pid")
							.toString()));
				}

				latestPhy.setClientId(id);
				if (!categoryMap.get("sbp").equals("")) {
					latestPhy.setSbp(categoryMap.get("sbp").toString());
				}
				if (!categoryMap.get("dbp").equals("")) {
					latestPhy.setDbp(categoryMap.get("dbp").toString());
				}
				if (!categoryMap.get("weight").equals("")) {
					latestPhy.setWeight(categoryMap.get("weight").toString());
				}
				if (!categoryMap.get("height").equals("")) {
					latestPhy.setHeight(categoryMap.get("height").toString());
				}
				if (!categoryMap.get("waist").equals("")) {
					latestPhy.setWaist(categoryMap.get("waist").toString());
				}
				if (!categoryMap.get("glu").equals("")) {
					latestPhy.setGlu(categoryMap.get("glu").toString());
				}
				if (!categoryMap.get("breech").equals("")) {
					latestPhy.setBreech(categoryMap.get("breech").toString());
				}

				latestPhyService.saveOrUpdateAndroid(latestPhy);

				json.put("code", "1");
				json.put("msg", "保存成功!");
				json.put("data", "");

			} else {
				json.put("code", "0");
				json.put("msg", "数据为空!");
				json.put("data", "");
			}
			// 更新档案完成率
			fileCompletionService
					.findClientFIleCompletion(Integer.valueOf(cid));
		} else {
			json.put("code", "0");
			json.put("msg", "用户id为空!");
			json.put("data", "");
		}

		return json.toString();
	}

	// 健康档案
	public String findHealArchive(String id){
		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(id)) {
			Integer cid = Integer.parseInt(id);
			clientInfo = findClientById(cid);
			hobby = hobbyService.getClientHobby(cid);
			medical = medicalService.getClientMedicalHistory(cid);
			family = familyService.getClientFamilyHistory(cid);
			latestPhy = latestPhyService.getClientLatestPhy(cid);

			JSONObject jo = new JSONObject();

			// 基本信息
			if (clientInfo != null) {
				if (!StringUtils.isEmpty(clientInfo.getName())) {
					jo.put("name", clientInfo.getName());
				} else {
					jo.put("name", "");
				}
				if (clientInfo.getGender() != null) {
					jo.put("gender", clientInfo.getGender());
				} else {
					jo.accumulate("gender", 0);
				}

				if (null != clientInfo.getBirthday()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String birthday = sdf.format(clientInfo.getBirthday());
					jo.put("birthday", birthday);
				} else {
					jo.put("birthday", "");
				}

				// 常住地址
				if (!StringUtils.isEmpty(clientInfo.getUsualAddress())) {
					jo.put("usualAddress", clientInfo.getUsualAddress());
				} else {
					jo.put("usualAddress", "");
				}
			}
			// 生活习惯
			if (hobby != null) {
				jo.put("hid", hobby.getId());
				jo.put("smoke", hobby.getSmoke());
				if (null != hobby.getSage()) {
					jo.put("sage", hobby.getSage());
				} else {
					// jo.accumulate("sage", null);
					jo.put("sage", 0);
				}
				jo.put("drink", hobby.getDrink());
				if (null != hobby.getDage()) {
					jo.put("dage", hobby.getDage());
				} else {
					// jo.accumulate("dage", null);
					jo.put("dage", 0);
				}

				// 运动情况
				jo.put("sleeping", hobby.getSleeping());
//				if (null != hobby.getSportCount()) {
					jo.put("sportCount", hobby.getSportCount());
//				} else {
//					// jo.accumulate("sportCount", null);
//					jo.put("sportCount", 0);
//				}

				jo.put("sportTime", hobby.getSportTime());

				if (!StringUtils.isEmpty(hobby.getSportType())) {
					jo.put("sportType", getClientSportType(hobby));
				} else {
					jo.put("sportType", "");
				}
				if (!StringUtils.isEmpty(hobby.getSportSupply())) {
					jo.put("sportSupply", hobby.getSportSupply());
				} else {
					jo.put("sportSupply", "");
				}
			} else {
				// jo.accumulate("hid", null);
				jo.put("hid", 0);
				jo.put("smoke", 0);
				// jo.accumulate("sage", null);
				jo.accumulate("sage", 0);
				jo.put("drink", 0);
				// jo.accumulate("dage", null);
				jo.accumulate("dage", 0);

				// 运动情况
				jo.put("sleeping", 0);
				// jo.accumulate("sportCount", null);
				jo.accumulate("sportCount", 0);
				jo.put("sportTime", 0);
				jo.put("sportType", "");
				jo.put("sportSupply", "");
			}
			// 既往病史
			if (medical != null) {
				jo.put("mid", medical.getId());
				if (null != medical.getIsHasMedical()) {
					jo.put("isHasMedical", medical.getIsHasMedical());
				} else {
					jo.accumulate("isHasMedical", 0);
				}
				List<ClientDiseaseSelf> lstDiseaseSelf = diseaseSelfDao
						.queryDiseaseSelfByClientId(cid);
				List<String> lstDisease = new ArrayList<String>();
				for (ClientDiseaseSelf clientDiseaseSelf : lstDiseaseSelf) {
					if (!StringUtils.isEmpty(clientDiseaseSelf.getDisease())) {
						lstDisease.add(clientDiseaseSelf.getDisease());
					}
				}

				if (!CollectionUtils.isEmpty(lstDisease)) {
					if (lstDisease.contains("13")) {
						jo.put("hasGxy", "1");
					} else {
						jo.put("hasGxy", "");
					}
					if (lstDisease.contains("29")) {
						jo.put("hasGxb", "1");
					} else {
						jo.put("hasGxb", "");
					}
					if (lstDisease.contains("30")) {
						jo.put("hasNzf", "1");
					} else {
						jo.put("hasNzf", "");
					}
					if (lstDisease.contains("1")) {
						jo.put("hasTnbI", "1");
					} else {
						jo.put("hasTnbI", "");
					}
					jo.put("hasTnbII", "");
					if (lstDisease.contains("4")) {
						jo.put("hasExzl", "1");
					} else {
						jo.put("hasExzl", "");
					}
					if (lstDisease.contains("2")) {
						jo.put("hasGxz", "1");
					} else {
						jo.put("hasGxz", "");
					}
					if (lstDisease.contains("31")) {
						jo.put("hasMz", "1");
					} else {
						jo.put("hasMz", "");
					}
					if (lstDisease.contains("32")) {
						jo.put("hasXc", "1");
					} else {
						jo.put("hasXc", "");
					}
					if (lstDisease.contains("33")) {
						jo.put("hasFqz", "1");
					} else {
						jo.put("hasFqz", "");
					}
					if (lstDisease.contains("34")) {
						jo.put("hasFjh", "1");
					} else {
						jo.put("hasFjh", "");
					}
					if (lstDisease.contains("")) {
						jo.put("other", "1");
					} else {
						jo.put("other", "");
					}
				} else {
					jo.put("mid", 0);
					jo.put("isHasMedical", 0);
					jo.put("hasGxy", "");
					jo.put("hasGxb", "");
					jo.put("hasNzf", "");
					jo.put("hasTnbI", "");
					jo.put("hasTnbII", "");
					jo.put("hasExzl", "");
					jo.put("hasGxz", "");
					jo.put("hasMz", "");
					jo.put("hasXc", "");
					jo.put("hasFqz", "");
					jo.put("hasFjh", "");
					jo.put("other", "");
				}
			}
			jo.accumulate("fid", 0);
			jo.accumulate("isHasFamily", 2);
			jo.put("faHasGxy", "");
			jo.put("faHasGxb", "");
			jo.put("faHasNxg", "");
			jo.put("faHasTnb", "");
			jo.put("faHasExzl", "");
			jo.put("faHasJhb", "");
			jo.put("faHasGy", "");
			jo.put("faHasJsb", "");
			// 其他疾病
			jo.put("familyOther", "");
			jo.put("faHasOther", "");
			jo.put("moHasGxy", "");
			jo.put("moHasGxb", "");
			jo.put("moHasNxg", "");
			jo.put("moHasTnb", "");
			jo.put("moHasExzl", "");
			jo.put("moHasJhb", "");
			jo.put("moHasGy", "");
			jo.put("moHasJsb", "");
			jo.put("moHasOther", "");
			// 家族病史
			if (family != null) {
				jo.put("fid", family.getId());
				jo.put("isHasFamily", 1);
				List<ClientDiseaseFamily> lstDiseaseFamily = diseaseFamilyDao
						.queryDiseaseFamilyByClientId(cid);
				if (lstDiseaseFamily.size() > 0) {
					for (ClientDiseaseFamily clientDiseaseFamily : lstDiseaseFamily) {
						if (!StringUtils.isEmpty(clientDiseaseFamily
								.getDisease())) {
							if (clientDiseaseFamily.getDisease().equals("13")
									&& clientDiseaseFamily.getFamilyType() == 0) {
								jo.put("faHasGxy", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("13")
									&& clientDiseaseFamily.getFamilyType() == 1) {
								jo.put("moHasGxy", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("29")
									&& clientDiseaseFamily.getFamilyType() == 0) {
								jo.put("faHasGxb", "1");
								continue;

							}
							if (clientDiseaseFamily.getDisease().equals("29")
									&& clientDiseaseFamily.getFamilyType() == 1) {
								jo.put("moHasGxb", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("36")
									&& clientDiseaseFamily.getFamilyType() == 0) {
								jo.put("faHasNxg", "1");
								continue;

							}
							if (clientDiseaseFamily.getDisease().equals("36")
									&& clientDiseaseFamily.getFamilyType() == 1) {
								jo.put("moHasNxg", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("1")
									&& clientDiseaseFamily.getFamilyType() == 0) {
								jo.put("faHasTnb", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("1")
									&& clientDiseaseFamily.getFamilyType() == 1) {
								jo.put("moHasTnb", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("4")
									&& clientDiseaseFamily.getFamilyType() == 0) {
								jo.put("faHasExzl", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("4")
									&& clientDiseaseFamily.getFamilyType() == 1) {
								jo.put("moHasExzl", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("23")
									&& clientDiseaseFamily.getFamilyType() == 0) {
								jo.put("faHasGy", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("23")
									&& clientDiseaseFamily.getFamilyType() == 1) {
								jo.put("moHasGy", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("34")
									&& clientDiseaseFamily.getFamilyType() == 0) {
								jo.put("faHasJhb", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("34")
									&& clientDiseaseFamily.getFamilyType() == 1) {
								jo.put("moHasJhb", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("37")
									&& clientDiseaseFamily.getFamilyType() == 0) {
								jo.put("faHasJsb", "1");
								continue;
							}
							if (clientDiseaseFamily.getDisease().equals("37")
									&& clientDiseaseFamily.getFamilyType() == 1) {
								jo.put("moHasJsb", "1");
								continue;
							}
							if (clientDiseaseFamily.getType() == ClientDiseaseFamily.DISEASE_CHECKED_NO
									&& clientDiseaseFamily.getFamilyType() == ClientDiseaseFamily.FAMILY_FA) {
								jo.put("faHasOther", "1");
								jo.put("familyOther", clientDiseaseFamily
										.getDisease());
								continue;
							}
							if (clientDiseaseFamily.getType() == ClientDiseaseFamily.DISEASE_CHECKED_NO
									&& clientDiseaseFamily.getFamilyType() == ClientDiseaseFamily.FAMILY_MO) {
								jo.put("moHasOther", "1");
								jo.put("familyOther", clientDiseaseFamily
										.getDisease());
								continue;
							}
						}
					}
				}
			}

			// 最后一次体检
			if (latestPhy != null) {
				jo.put("pid", latestPhy.getId());
				if (!StringUtils.isEmpty(latestPhy.getHeight())) {
					jo.put("height", latestPhy.getHeight());
				} else {
					jo.put("height", "");
				}
				if (!StringUtils.isEmpty(latestPhy.getWeight())) {
					jo.put("weight", latestPhy.getWeight());
				} else {
					jo.put("weight", "");
				}
				if (!StringUtils.isEmpty(latestPhy.getWaist())) {
					jo.put("waist", latestPhy.getWaist());
				} else {
					jo.put("waist", "");
				}
				if (!StringUtils.isEmpty(latestPhy.getBreech())) {
					jo.put("breech", latestPhy.getBreech());
				} else {
					jo.put("breech", "");
				}

				// 收缩压
				if (!StringUtils.isEmpty(latestPhy.getSbp())) {
					jo.put("sbp", latestPhy.getSbp());
				} else {
					jo.put("sbp", "");
				}
				// 舒张压
				if (!StringUtils.isEmpty(latestPhy.getDbp())) {
					jo.put("dbp", latestPhy.getDbp());
				} else {
					jo.put("dbp", "");
				}
				// 空腹血糖
				if (!StringUtils.isEmpty(latestPhy.getGlu())) {
					jo.put("glu", latestPhy.getGlu());
				} else {
					jo.put("glu", "");
				}

			} else {
				// jo.accumulate("pid", null);
				jo.accumulate("pid", 0);
				jo.put("height", "");
				jo.put("weight", "");
				jo.put("waist", "");
				jo.put("breech", "");
				// 收缩压
				jo.put("sbp", "");
				// 舒张压
				jo.put("dbp", "");
				// 空腹血糖
				jo.put("glu", "");
			}

			json.put("code", "1");
			json.put("msg", "获取用户健康报告成功");
			json.put("data", jo.toString());

		} else {
			json.put("code", "0");
			json.put("msg", "用户id为空！");
		}
		System.out.println(json.toString());
		return json.toString();
	}

	/**
	 * 将运动类型 改成1：跑步 2：游泳 3：球类 4：太极拳 5：其他 String
	 */
	public String getClientSportType(ClientHobby hobby) {
		List<String> sportTypes = new ArrayList<String>();
		if (hobby != null) {
			if (hobby.getSportType() != null
					&& !StringUtils.isEmpty(hobby.getSportType())) {
				if (hobby.getSportType().contains("跑步")) {
					sportTypes.add("1");
				}
				if (hobby.getSportType().contains("游泳")) {
					sportTypes.add("2");
				}
				if (hobby.getSportType().contains("球类")) {
					sportTypes.add("3");
				}
				if (hobby.getSportType().contains("太极拳")) {
					sportTypes.add("4");
				}
			}
		}
		return ArrayUtils.join(sportTypes.toArray());
	}
	
	
	
	public void saveAreaChain(Integer areaId) {
		String chain = getAreaChain(areaId).toString().substring(1);// 截取字符串前面的逗号
		if (!StringUtils.isEmpty(chain)) {
			ClientInfo client = new ClientInfo();
			client.setAreaId(areaId);
			client.setAreaChain(chain + ",");
			clientUserDao.updateAreaChain(client);
		}
		System.out.println("final chain:" + chain + ",");
	}

	// 递归
	public StringBuffer getAreaChain(Integer areaId) {
		StringBuffer chain = new StringBuffer();
		AreaInfo areaInfo = areaInfoService.findAreaByParentId(areaId);// areaId=parentId
		// parentId=id
		if (null != areaInfo && areaInfo.getId() >= 1) {
			chain = getAreaChain(areaInfo.getParentId()).append("," + areaId);
		}
		return chain;
	}

	/*public List queryClients(ClientInfo ci, String areaChain,
			QueryCondition queryCondition) {
		return clientUserDao.queryClients(ci, areaChain, queryCondition);
	}*/

	public String queryClientInfoSport(Integer clientId) {
		// 用户基本信息
		ClientInfo clientInfo = clientUserDao.load(clientId);
		// 最后一次体检记录
		ClientLatestPhy phy = latestPhyService.getClientLatestPhy(clientId);
		// 运动计划
		MSportPlan plan = planService.queryMSportPlan(clientId);

		ClientInfoSport csport = new ClientInfoSport();
		if (clientInfo != null) {
			csport.setId(clientInfo.getId());
			csport.setGender(clientInfo.getGender());
			csport.setName(clientInfo.getName());
			csport.setAge(clientInfo.getAge());
			if(!StringUtils.isEmpty(clientInfo.getMobile())){
				csport.setMobile(clientInfo.getMobile());
			}else{
				sendMessage(clientInfo, "queryClientInfoSport");
			}
			csport.setNickName(clientInfo.getNickName());
			String base = SystemConfig.getString("image_base_url");
			csport.setHeadPortrait(base + clientInfo.getHeadPortrait());
		}
		if (phy != null) {
			csport.setHeight(phy.getHeight());
			csport.setWeight(phy.getWeight());
		}
		if (plan != null) {
			csport.setStepWidth(plan.getStepWidth());
		}

		JSONObject jo = new JSONObject();
		jo.put("code", 1);
		jo.put("msg", "成功");
		jo.put("data", JsonUtils.getJsonString4JavaPOJO(csport));
		return jo.toString();
	}

	public String updateClientInfoSport(ClientInfoSport csport){
		JSONObject jo = new JSONObject();
		if(csport != null&& StringUtils.isEmpty(csport.getMobile())){
			ClientInfo ci = new ClientInfo();
			ci.setId(csport.getId());
//			sendMessage(ci, "updateClientInfoSport");
			jo.put("code", 0);
			jo.put("msg", "电话号码为空");
			jo.put("data", JsonUtils.getJsonObject4JavaPOJO(csport));
			return jo.toString();
		}else{
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setId(csport.getId());
			clientInfo.setName(csport.getName());
			clientInfo.setAge(csport.getAge());
			clientInfo.setGender(csport.getGender());
			clientInfo.setMobile(csport.getMobile());
			clientInfo.setNickName(csport.getNickName());
	
			clientUserDao.updateClientInfo(clientInfo);
			ClientInfo client = clientUserDao.load(clientInfo.getId());
			
			crmClientService.updateCrmClientInfo(client, "web");
	
			ClientLatestPhy phy = new ClientLatestPhy();
			phy.setClientId(csport.getId());
			phy.setHeight(csport.getHeight());
			phy.setWeight(csport.getWeight());
	
			latestPhyService.saveOrUpdateSport(phy);
	
			MSportPlan plan = new MSportPlan();
			plan.setClientId(csport.getId());
			plan.setStepWidth(csport.getStepWidth());
			plan.setCreateTime(new Date());
	
			planService.addMSportPlanSport(plan);
	
			jo.put("code", 1);
			jo.put("msg", "成功");
			jo.put("data", JsonUtils.getJsonObject4JavaPOJO(csport));
			
			return jo.toString();
		}
	}

	public String updateAreaChain(String data) {
		JSONObject jo = new JSONObject();
		if (!StringUtils.isEmpty(data)) {
			List<AreaInfo> lstAreaInfo = JsonUtils.getList4Json(data,
					AreaInfo.class);
			if (!CollectionUtils.isEmpty(lstAreaInfo)) {
				for (AreaInfo areaInfo : lstAreaInfo) {
					if (areaInfo.getId() != null
							&& !StringUtils.isEmpty(areaInfo.getAreaChain())) {

						areaInfoDao.updateAreaInfo(areaInfo);

						ClientInfo clientInfo = new ClientInfo();
						clientInfo.setAreaId(areaInfo.getId());
						clientInfo.setAreaChain(areaInfo.getAreaChain());
						clientUserDao.updateAreaChain(clientInfo);
					}
				}
				jo.put("code", 1);
				jo.put("msg", "成功");
			}
		} else {
			jo.put("code", 0);
			jo.put("msg", "参数为空");
		}
		return jo.toString();
	}

	public String updateAreaChainByClientId(String data) {
		JSONObject jo = new JSONObject();
		if (!StringUtils.isEmpty(data)) {
			List<ClientInfo> lstClientInfo = JsonUtils.getList4Json(data,
					ClientInfo.class);
			if (!CollectionUtils.isEmpty(lstClientInfo)) {
				for (ClientInfo clientInfo : lstClientInfo) {
					if (clientInfo.getId() != null
							&& !StringUtils.isEmpty(clientInfo.getAreaChain())
							&& clientInfo.getAreaId() != null) {
						clientUserDao.updateAreaChainByClientId(clientInfo);
					} else {
						jo.put("code", 3);
						jo.put("msg", "参数错误");
						return jo.toString();
					}
				}
				jo.put("code", 1);
				jo.put("msg", "成功");
			}
		} else {
			jo.put("code", 0);
			jo.put("msg", "参数为空");
		}
		return jo.toString();
	}

	public void alterBazzer(Integer client, Integer bazzer) {
		clientUserDao.alterBazzer(client, bazzer);
	}

	public void alterHealth(Integer client, Integer health) {
		clientUserDao.alterHealth(client, health);
	}

	public void alterPrincipal(Integer client, Integer principalId) {
		clientUserDao.alterPrincipal(client, principalId);
	}

	public String updateClientInfoByCrm(String data){
		JSONObject jo = new JSONObject();
		if (!StringUtils.isEmpty(data)) {
			CrmClientInfo crmClientInfo = (CrmClientInfo) JsonUtils
					.getObject4JsonString(data, CrmClientInfo.class,
							"yyyy-MM-dd HH:mm:ss");
			ClientInfo clientInfo = this.getClientInfo(crmClientInfo);
			if (clientInfo != null && clientInfo.getAreaId() != null && 
					!StringUtils.isEmpty(clientInfo.getAreaChain())) {
				if(StringUtils.isEmpty(clientInfo.getMobile())){
					sendMessage(clientInfo, "updateClientInfoByCrm");
					jo.put("code", 3);
					jo.put("msg", "电话号码为空");
					return jo.toString();
				}
				int count = clientUserDao.updateClientInfoByCrm(clientInfo);
				if (count > 0) {
					jo.put("code", 1);
					jo.put("msg", "成功");
				} else {
					jo.put("code", 3);
					jo.put("msg", "操作失败");
				}
			}else{
				jo.put("code", 3);
				jo.put("msg", "区域、区域链不能为空");
				return jo.toString();
			}
		} else {
			jo.put("code", 0);
			jo.put("msg", "参数为空");
		}
		return jo.toString();
	}

	/**
	 * 根据CrmClientInfo 获取 ClientInfo
	 */
	public ClientInfo getClientInfo(CrmClientInfo crmClientInfo) {
		ClientInfo clientInfo = null;
		if (crmClientInfo != null) {
			clientInfo = new ClientInfo();
			if (crmClientInfo.getClientId() != null) {
				clientInfo.setId(crmClientInfo.getClientId());
			}
			if (!StringUtils.isEmpty(crmClientInfo.getName())) {
				clientInfo.setName(crmClientInfo.getName());
			}
			if (crmClientInfo.getAge() != null) {
				clientInfo.setAge(crmClientInfo.getAge());
			}
			if (crmClientInfo.getSex() != null) {
				clientInfo.setGender(crmClientInfo.getSex());
			}
			if (crmClientInfo.getBirthday() != null) {
				clientInfo.setBirthday(crmClientInfo.getBirthday());
			}
			if (!StringUtils.isEmpty(crmClientInfo.getPid())) {
				clientInfo.setIdCards(crmClientInfo.getPid());
			}
			if (!StringUtils.isEmpty(crmClientInfo.getMobile())) {
				clientInfo.setMobile(crmClientInfo.getMobile());
			}
			if (!StringUtils.isEmpty(crmClientInfo.getTelephone())) {
				clientInfo.setPhone(crmClientInfo.getTelephone());
			}
			if (!StringUtils.isEmpty(crmClientInfo.getAddress())) {
				clientInfo.setAddress(crmClientInfo.getAddress());
			}
			if (!StringUtils.isEmpty(crmClientInfo.getWorkAddress())) {
				clientInfo.setWorkUnits(crmClientInfo.getWorkAddress());
			}
			if (crmClientInfo.getAreaId() != null) {
				clientInfo.setAreaId(crmClientInfo.getAreaId());
			}
			if (!StringUtils.isEmpty(crmClientInfo.getCardid())) {
				clientInfo.setVipCard(crmClientInfo.getCardid());
			}
			if (!StringUtils.isEmpty(crmClientInfo.getEmail())) {
				clientInfo.setEmail(crmClientInfo.getEmail());
			}
			if (!StringUtils.isEmpty(crmClientInfo.getFlagid())) {
				clientInfo.setAreaChain(crmClientInfo.getFlagid());
			}
		}

		return clientInfo;
	}

	public void updateHeadPortrairByClientId(Integer clientId,
			String HeadPortrairName) {
		clientUserDao.updateHeadPortrairByClientId(clientId, HeadPortrairName);
	}

	/* 黄金档案部分 */
	/** 查询黄金档案 **/
	public ClientArchive queryGoldFiles(Integer clientId) {
		ClientArchive archive = new ClientArchive();
		ClientInfo clientInfo = clientUserDao.load(clientId);
		ClientLatestPhy latestPhy = latestPhyDao.getClientLastestPhy(clientId);
		ClientMedicalHistory medicalHistory = medicalService
				.getClientMedicalHistory(clientId);
		ClientHobby clientHobby = hobbyService.getClientHobby(clientId);
		// 基本信息
		if (clientInfo != null) {
			archive.setName(clientInfo.getName());
			if (clientInfo.getGender() != null) {
				archive.setGender(clientInfo.getGender() + "");
			} else {
				archive.setGender("");
			}
		}
		archive.setBirthday(clientInfo.getBirthday());
		if (latestPhy != null) {
			archive.setHeight(latestPhy.getHeight());
			archive.setWaist(latestPhy.getWaist());
			archive.setWeight(latestPhy.getWeight());
			archive.setBreech(latestPhy.getBreech());
			archive.setHeartRate(latestPhy.getHeartRate());
		}
		if (medicalHistory != null) {
			// 希望先解决的疾病
			archive.setHopeSolveHealth(medicalHistory.getHopeSolveHealth());
			// 过敏史
			archive.setAllergy(getArchiveAllergn(medicalHistory));
			// 其他过敏现象
			archive.setAllergyOther(medicalHistory.getAllergenOther());
		}
		if (clientHobby != null) {
			// 目前职业
			archive.setPhysicalType(clientHobby.getPhysicalType());
			// 生活嗜好
			archive.setBadHabits(getBadHabits(clientHobby));
			// 不喜欢的事务
			archive.setAvoidCertainFood(clientHobby.getNotEat());
			// 三餐
			archive.setBreakfast(clientHobby.getBreakfast());
			archive.setBreakfastOther(clientHobby.getBreakfastOther());
			archive.setLunch(clientHobby.getLunch());
			archive.setLunchOther(clientHobby.getLunchOther());
			archive.setDinner(clientHobby.getDinner());
			archive.setDinnerOther(clientHobby.getDinnerOther());
			archive.setJiacanCount(clientHobby.getJiacanCount());
			archive.setJiacanType(clientHobby.getJiacanType());
			// 睡眠
			archive.setSleep(clientHobby.getSleeping());
			// 运动习惯
			int sportCount = clientHobby.getSportCount();
			if(sportCount > 3){
				sportCount = 4;
			}
			archive.setSportCount(clientHobby.getSportCount());
			archive.setSportTime(clientHobby.getSportTime());
			archive.setSportType(clientHobby.getSportType());
			archive.setSportSupply(clientHobby.getSportSupply());
			archive.setSportTimeZone(clientHobby.getSportTimeZone());
			archive.setSportZoneOther(clientHobby.getSportZoneOther());
			archive.setSportHabit(clientHobby.getSportHabit());
			archive.setSportHabitOther(clientHobby.getSportHabitOther());
		}
		return archive;
	}

	//
	public void saveClientArchive(ClientArchive archive, Integer cid) {
		if (cid == null && archive.getClientId() == null)
			return;
		if (archive.getClientId() == null) {
			archive.setClientId(cid);
		}
		// 更新基本信息
		ClientInfo clientInfo = clientUserDao.load(cid);
		clientInfo.setName(archive.getName());
		clientInfo.setBirthday(archive.getBirthday());
		
		if(null != archive.getBirthday()) {
			clientInfo.setAge(DateUtils.getAgeByBirthday(archive.getBirthday())); //重新计算年龄
		}
		
		if (archive.getGender() != null) {
			if (StringUtils.isNumeric(archive.getGender())) {
				clientInfo.setGender(Integer.parseInt(archive.getGender())); // TODO
			}
		}
		clientUserDao.update(clientInfo);
		// 更新用户习惯
		ClientHobby clientHobby = hobbyService.getClientHobby(cid);
		if (clientHobby == null) {
			clientHobby = new ClientHobby();
			clientHobby.setClientId(cid);
		}
		String badHabits = archive.getBadHabits();
		if (!StringUtils.isEmpty(badHabits) && badHabits.contains("10")) {
			clientHobby.setSmoke(1);
		} else {
			clientHobby.setSmoke(2);
			clientHobby.setAverage(null);
			clientHobby.setSage(null);
			clientHobby.setSyear(null);
			clientHobby.setCigaret(null);
			clientHobby.setReSmoking(2);
			clientHobby.setShSmoke(2);
			clientHobby.setShAge(null);
			clientHobby.setDage(null);
			clientHobby.setShMinuite(null);
		}
		if (!StringUtils.isEmpty(badHabits) && badHabits.contains("11")) {
			clientHobby.setDrink(1);
		} else {
			clientHobby.setDrink(2);
			clientHobby.setDage(null);
			clientHobby.setWhite(null);
			clientHobby.setBeer(null);
			clientHobby.setRed(null);
		}
		badHabits = StringUtils.remove(badHabits, "10,", "");
		badHabits = StringUtils.remove(badHabits, "11,", "");
		badHabits = StringUtils.remove(badHabits, ",10", "");
		badHabits = StringUtils.remove(badHabits, ",11", "");
		badHabits = StringUtils.remove(badHabits, "10", "");
		badHabits = StringUtils.remove(badHabits, "11", "");
		clientHobby.setDiet(badHabits);
		clientHobby.setSportCount(archive.getSportCount());
		clientHobby.setPhysicalType(archive.getPhysicalType());
		clientHobby.setSleeping(archive.getSleep());
		clientHobby.setNotEat(archive.getAvoidCertainFood());
		clientHobby.setBreakfast(archive.getBreakfast());
		
		if(!"null".equals(archive.getSportSupply())) {
			clientHobby.setSportSupply(archive.getSportSupply());
		}
		clientHobby.setBreakfastOther(archive.getBreakfastOther());
		clientHobby.setLunch(archive.getLunch());
		clientHobby.setLunchOther(archive.getLunchOther());
		clientHobby.setDinner(archive.getDinner());
		clientHobby.setDinnerOther(archive.getDinnerOther());
		clientHobby.setJiacanCount(archive.getJiacanCount());//
		clientHobby.setJiacanType(archive.getJiacanType());
		clientHobby.setSportType(archive.getSportType());
		clientHobby.setSportTime(archive.getSportTime());
		clientHobby.setSportTimeZone(archive.getSportTimeZone());
		clientHobby.setSportZoneOther(archive.getSportZoneOther());
		clientHobby.setSportHabitOther(archive.getSportHabitOther());
		clientHobby.setSportHabit(archive.getSportHabit());
		hobbyService.saveOrUpdate(clientHobby);
		ClientLatestPhy latestPhy = latestPhyDao.getClientLastestPhy(cid);
		if (latestPhy == null) {
			latestPhy = new ClientLatestPhy();
			latestPhy.setClientId(cid);
		}
		latestPhy.setWeight(archive.getWeight());
		latestPhy.setWaist(archive.getWaist());
		latestPhy.setHeartRate(archive.getHeartRate());
		latestPhy.setHeight(archive.getHeight());
		latestPhy.setBreech(archive.getBreech());
		if (latestPhy.getId() == null)
			latestPhyDao.add(latestPhy);
		else
			latestPhyDao.update(latestPhy);
		// 过敏信息
		ClientMedicalHistory medicalHistory = medicalService
				.getClientMedicalHistory(cid);
		// 更新过敏信息
		medicalService.saveArchiveAllergy(archive.getAllergy().split(","), cid);
		if(medicalHistory==null){
			medicalHistory = new ClientMedicalHistory();
			medicalHistory.setClientId(cid);
		}
		medicalHistory.setAllergenOther(archive.getAllergyOther());
		if (archive.getHopeSolveHealth() != null
				&& archive.getHopeSolveHealth().equals("other")) {
			medicalHistory.setHopeSolveHealth(archive
					.getPrecedenceDiseaseOther());
		} else {
			medicalHistory.setHopeSolveHealth(archive.getHopeSolveHealth());
		}
		if(medicalHistory.getId()!=null){
			medicalDao.update(medicalHistory);
		}else{
			medicalDao.add(medicalHistory);
		}

	}

	/** 获得黄金档案过敏信息 */

	public String getArchiveAllergn(ClientMedicalHistory clientMedicalHistory) {
		if (clientMedicalHistory == null) {
			return null;
		}
		List<String> allergyList = new ArrayList<String>();
		if (clientMedicalHistory.getXrAllergen() != null) {
			if (clientMedicalHistory.getXrAllergen().contains("花粉")) {
				allergyList.add("花粉");
			}
			if (clientMedicalHistory.getXrAllergen().contains("粉尘")) {
				allergyList.add("粉尘");
			}
		}
		if (clientMedicalHistory.getJcAllergen() != null) {
			if (clientMedicalHistory.getJcAllergen().contains("螨虫")) {
				allergyList.add("螨虫");
			}
			if (clientMedicalHistory.getJcAllergen().contains("化妆品")) {
				allergyList.add("化妆品");
			}
			if (clientMedicalHistory.getJcAllergen().contains("冷空气")) {
				allergyList.add("冷空气");
			}
		}
		if (clientMedicalHistory.getSrAllergen() != null) {
			if (clientMedicalHistory.getSrAllergen().contains("奶类")) {
				allergyList.add("奶类");
			}
			if (clientMedicalHistory.getSrAllergen().contains("海鲜类")) {
				allergyList.add("海鲜类");
			}
			if (clientMedicalHistory.getSrAllergen().contains("牛羊肉")) {
				allergyList.add("牛羊肉");
			}
			if (clientMedicalHistory.getSrAllergen().contains("水果类")) {
				allergyList.add("水果类");
			}
		}
		return ArrayUtils.join(allergyList.toArray(), ",");
	}

	/** 拼接过敏信息 */
	public String jointAllergen(String[] allergen, String[] archiveAllergn) {
		int num = 0;
		String jo = "";
		for (String string : allergen) {
			if (StringUtils.contains(string, archiveAllergn)) {
				if (num == 1) {
					jo += ",";
				}
				jo += string;
			}
		}
		return jo;
	}

	/**
	 * 获得生活嗜好
	 */
	public String getBadHabits(ClientHobby clientHobby) {
		if (clientHobby == null) {
			return null;
		}
		List<String> badHabitsList = new ArrayList<String>();
		if (clientHobby.getSmoke() == 1) {
			badHabitsList.add("10");
		}
		if (clientHobby.getDrink() == 1) {
			badHabitsList.add("11");
		}
		if(!StringUtils.isEmpty(clientHobby.getDiet())) {
			badHabitsList.add(clientHobby.getDiet());
		}
		return ArrayUtils.join(badHabitsList.toArray(), ",");
	}
	
	/**
	 * 添加积分
	 * @param u
	 * @param passivityinvite
	 */
	private void addCoins(ClientInfo u, String passivityinvite) {
		if(!StringUtils.isEmpty(passivityinvite)){
			//用户填写了被邀请码
			ClientExtend extend = new ClientExtend();
			extend.setPassivityinvite(passivityinvite);
			ClientExtend clientExtend = extendDao.queryLastTimeByClientId(extend);
			if(clientExtend != null){
				//客户勋章升级提醒
				medalRecordService.addMedalRecord(NTgMedalRule.RULE_INVITE_CLIENT, clientExtend.getClientId());
			}
			
			//判断如果是医生给的邀请码
			if(passivityinvite.substring(0, 1).equals(ClientInfo.DOC_INVITE)){
				try{
					String url = CrmURLConfig.getString("audit_docInfo_url")
					+ CrmURLConfig.getString("doctor_earn_money_reg");
			
					HashMap<String, String> parmas = new HashMap<String, String>();
					parmas.put("bskDoctorInfo.outCode", passivityinvite);
					parmas.put("mobile", u.getMobile());
					parmas.put("cid", ""+u.getId());
					parmas.put("source", "pc");
					
					// 获得返回（跟根据url 和 参数）
					CrmResponse crs = HttpClientUtils.getCrmResponse(url, parmas);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}else{
			log.info("------没有填写邀请码------");
		}
	}

	/**
	 * 发送信息
	 * @param u
	 * @param source
	 */
	private void sendMsgToCustomer(ClientInfo u, String source) {
		if(!StringUtils.isEmpty(source)){
			if( StringUtils.isXTGGUser(source.split("-")[0]) ){// 血糖高管
				
				//TODO 血糖高管用户注册发送提示短信
				ShortMessage sm = new ShortMessage();
				sm.setContent(Message.getString("xtgg_reg_tip"));
				sm.setType(Message.getString("reg_succ"));
				sm.setMobile(u.getMobile());
				sm.setClientId(u.getId());
				smService.sendMessage(sm, "more");
				
				//免费发送560激活码
//				if(!StringUtils.isEmpty(u.getMobile())){
//					String mobile = u.getMobile();
//					String mobileKey = Base64.encode(mobile);
//					activityService.tgActivitySendCard(mobile, mobileKey);
//				}
				
			}
			
			if(source.equals("php_sugar_1000") 
					&& u.getAreaChain().contains(SystemConfig.getString("tg_1000_group3"))) { //千人推广,并且是组3
				ShortMessage sm = new ShortMessage();
				sm.setContent(Message.getString("xtgg_1000tg_tip"));
				sm.setType(Message.getString("reg_succ"));
				sm.setMobile(u.getMobile());
				sm.setClientId(u.getId());
				smService.sendMessage(sm, "more");
			}
		}
	}

	/**
	 * 注册环信
	 * @param client
	 */
	private void registToHuanxin(ClientInfo client) {
		//调用环信注册接口
		try{
			String url = CrmURLConfig.getString("audit_docInfo_url")
			+ CrmURLConfig.getString("crm_huanxin_reg");
	
			HashMap<String, String> parmas = new HashMap<String, String>();
			parmas.put("phone", client.getMobile());
	
			// 获得返回（跟根据url 和 参数）
			CrmResponse crs = HttpClientUtils.getCrmResponse(url, parmas);
			log.info(">>>>>>>>>>>>>>>>>>> 调用环信注册接口： crs code = " + crs.getCode());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public ClientHobby getHobby() {
		return hobby;
	}

	public void setHobby(ClientHobby hobby) {
		this.hobby = hobby;
	}

	public ClientMedicalHistory getMedical() {
		return medical;
	}

	public void setMedical(ClientMedicalHistory medical) {
		this.medical = medical;
	}

	public ClientFamilyHistory getFamily() {
		return family;
	}

	public void setFamily(ClientFamilyHistory family) {
		this.family = family;
	}

	public ClientLatestPhy getLatestPhy() {
		return latestPhy;
	}

	public void setLatestPhy(ClientLatestPhy latestPhy) {
		this.latestPhy = latestPhy;
	}

	public List<ClientInfo> findAllClientInfo(ClientInfo client) {
		return clientUserDao.findClientInfo(client);
	}

	public String putCode() {
		return null;
	}

	public List<FamilyName> getFamilyList(ClientInfo clientInfo) {
		return familyRelationDao.getFamilyByClientInfo(clientInfo);
	}

	public List<FamilyName> getFamilyByfamilyId(ClientInfo ci) {
		return familyRelationDao.getFamilyByfamilyId(ci);
	}

	public ClientInfo get(Integer cid) {
		return clientUserDao.load(cid);
	}

	public ClientInfo sycUser(ClientInfo u, String source) {
		String MD5Pwd = MD5Util.digest(u.getPassword());
		u.setPassword(MD5Pwd);
		u.setType(ClientInfo.TYPE_EXPERIENCE);
		u.setCreateTime(new Date());
		u.setBazzaarGrade(3);
		ClientInfo c = clientUserDao.add(u);
		orderMasterService.createRegisterOrder(ClientInfo.TYPE_EXPERIENCE, c);

		if(clientInfo != null&& !StringUtils.isEmpty(source)){
			extendService.addRegSource(c.getId(), source, null);
		}
		return c;
	}

	public ClientInfo getClientByMobile(ClientInfo u) {
		return clientUserDao.getClientByMobile(u);
	}

	public void updateInfoByProductExpire(ClientInfo u) {
		clientUserDao.updateInfoByProductExpire(u);
	}
	
	
	public void saveClientArchive(Integer clientId, List<String[]> lst, ClientDiseaseSelf diseaseSelf, ClientDiseaseFamily diseaseFamily, ClientArchive archive){
		if(!CollectionUtils.isEmpty(lst)&&lst.size() >= 3){
			String[] archiveDiseaseSelf = lst.get(0);
			clientDiseaseSelfService.updateDiseaseSelf(archiveDiseaseSelf, clientId);
			if(diseaseSelf != null){
				clientDiseaseSelfService.updateDiseaseSelfOther(diseaseSelf,clientId);
			}
			//更新家族疾病
			String[] archiveDiseaseFamilyf = lst.get(1);
			String[] archiveDiseaseFamilym = lst.get(2);
			clientDiseaseFamilyService.updateDiseaseFamily(archiveDiseaseFamilyf,ClientDiseaseFamily.FAMILY_FA,clientId);//父亲
			clientDiseaseFamilyService.updateDiseaseFamily(archiveDiseaseFamilym,ClientDiseaseFamily.FAMILY_MO,clientId);//母亲
			
			if(diseaseFamily != null){
				String[] archiveDiseaseFamilyType = lst.get(3);
				clientDiseaseFamilyService.updateDiseaseFamilyOther(archiveDiseaseFamilyType,diseaseFamily,clientId);
			}
			
			if(lst.size() > 3){
				// 更新过敏信息
				String[] archiveAllergy = lst.get(4);
				medicalService.saveArchiveAllergy(archiveAllergy,clientId);
			}
			//保存黄金档案
			if(archive != null){
				this.saveClientArchive(archive, clientId);
			}
			//修改档案完成率
			fileCompletionService.updateCompletion(clientId);
		}
	}
	
	public ClientInfo queryClientInfo(ClientInfo clientInfo){
		return clientUserDao.queryClientInfo(clientInfo);
	}
	
	
	
	public String addClientScore(String passivityinvite){
		JSONObject jo = new JSONObject();
		if(!StringUtils.isEmpty(passivityinvite)){
			ClientExtend extend = new ClientExtend();
			extend.setPassivityinvite(passivityinvite);
			ClientExtend ce = extendDao.queryLastTimeByClientId(extend);
			if(ce != null && ce.getClientId() != null){
				scoreService.addPointsAndCoins(15, ce.getClientId());
				scoreService.addPointsAndCoins(24, ce.getClientId());
				
				jo.put("code", 1);
				jo.put("msg", "添加积分成功");
			}else{
				jo.put("code", 0);
				jo.put("msg", "没有此邀请码");
				log.info("--------没有此邀请码----------");
			}
		}
		return jo.toString();
	}

/*
	public String focuseDocterScore(Integer clientId){
		JSONObject jo = new JSONObject();
		if(clientId != null){
			scoreService.addPointsAndCoins(17, clientId);
			jo.put("code", 1);
			jo.put("msg", "关注医生添加积分成功");
		}
		return jo.toString();
	}
	
	
	public String evaluateDocterScore(Integer clientId){
		JSONObject jo = new JSONObject();
		if(clientId != null){
			scoreService.addPointsAndCoins(18, clientId);
			jo.put("code", 1);
			jo.put("msg", "关注医生添加积分成功");
		}
		return jo.toString();
	}
*/
	
	
	public String regClient(ClientInfo clientInfo, String source, int evalType){
		JSONObject jo = new JSONObject();
		ClientInfo clientinfo = null;
		//手机号码，uid不能为空
		if(clientInfo != null && !StringUtils.isEmpty(clientInfo.getUid())){
			if(!StringUtils.isEmpty(clientInfo.getMobile())){
				//注册时首先判断手机号是否存在，如果手机号存在，查看uid是否为空，如果为空，修改uid的值
					//如果uid不为空，判断两个手机号码是否一致，如果不一致，修改手机号码。
				 //手机号码如果不存在，重新注册客户。
				ClientInfo c = new ClientInfo();
				c.setMobile(clientInfo.getMobile());
				//判断手机号码是否存在
				ClientInfo client = clientUserDao.getClientByMobile(c);
				//如果手机号码存在
				if(client != null){
					//判断uid是否为空，如果不为空
					if(client.getUid() != null){
						ClientInfo cin = new ClientInfo();
						cin.setUid(clientInfo.getUid());
						cin.setCompSource(clientInfo.getCompSource());
						//如果是乐语的用户
						if(clientInfo.getCompSource() != null && clientInfo.getCompSource() == 1){
							String vAreaId = SystemConfig.getString("leyu_area_id");
							if (!StringUtils.isEmpty(vAreaId)) {
								cin.setAreaId(Integer.parseInt(vAreaId));
							}
							cin.setAreaChain(areaInfoService.getAreaChainByAreaId(cin.getAreaId()));
						}
						
						clientUserDao.updateClientComp(cin);
					}else{
						ClientInfo cin = new ClientInfo();
						cin.setCompSource(clientInfo.getCompSource());
						//如果是乐语的用户
						if(clientInfo.getCompSource() != null && clientInfo.getCompSource() == 1){
							String vAreaId = SystemConfig.getString("leyu_area_id");
							if (!StringUtils.isEmpty(vAreaId)) {
								cin.setAreaId(Integer.parseInt(vAreaId));
							}
							cin.setAreaChain(areaInfoService.getAreaChainByAreaId(cin.getAreaId()));
						}
						clientUserDao.updateClientComp(cin);
					}
					
				}else{
					//手机号码不存在，判断uid是否存在
					ClientInfo ci = new ClientInfo();
					ci.setUid(clientInfo.getUid());
					ClientInfo cl = clientUserDao.getClientByMobile(ci);
					if(cl != null){
						clientUserDao.updateMobile(cl.getId(), clientInfo.getMobile());
					}else{
						jo.put("status", 2);
						return jo.toString();
					}
				}
			}else{
				ClientInfo ci = new ClientInfo();
				ci.setUid(clientInfo.getUid());
				ClientInfo cl = clientUserDao.getClientByMobile(ci);
				if(cl == null){
					jo.put("status", 2);
					return jo.toString();
				}
			}
			
			ClientInfo cinfo = new ClientInfo();
			if(!StringUtils.isEmpty(clientInfo.getMobile())){
				cinfo.setMobile(clientInfo.getMobile());
			}else{
				cinfo.setUid(clientInfo.getUid());
			}
			clientinfo = clientUserDao.getClientByMobile(cinfo);
			
			String result = loginBack(clientinfo, source, evalType);
			return result;
			
		}else{
			jo.put("status", "exception");
		}
		return jo.toString();
	}
	
	
	public String loginBack(ClientInfo clientinfo, String source, int evalType){
		JSONObject json = new JSONObject();
		if (null != clientinfo) {
			json.put("status", "success");
			json.put("cid", clientinfo.getId());
			json.put("mobile", clientinfo.getMobile());
			if (!StringUtils.isEmpty(clientinfo.getNickName())) {
				json.put("nickName", clientinfo.getNickName());
			} else {
				json.put("nickName", "");
			}
			if (!StringUtils.isEmpty(clientinfo.getName())) {
				json.put("name", clientinfo.getName());
			} else {
				json.put("name", "");
			}
			if(clientinfo.getGender() != null){
				json.put("gender", clientinfo.getGender());
			}else{
				json.put("gender", 0);
			}
			if(!StringUtils.isEmpty(source)){
				if(source.split("-")[0].equals("bsksugar")//安卓血糖高管
						||source.split("-")[0].equals("DiabetesManagement")){//ios 血糖高管
					// 返回用户的邀请码
					ClientExtend clientExtend = clientExtendService.modifyLastTimeByClientId(clientinfo.getId());
					if(clientExtend != null && !StringUtils.isEmpty(clientExtend.getInitiativeinvite()) ){
						json.put("initiativeinvite", clientExtend.getInitiativeinvite());
					}else{
						json.put("initiativeinvite", "");
					}
				}
			}
			
			// 判断用户是否参与相关软件评测
			if (-1 != evalType) {
				json.put("isEvaluate", resultService.evaluateType(evalType,clientinfo));
			}

			String base = SystemConfig.getString("image_base_url");
			if (!StringUtils.isEmpty(clientinfo.getHeadPortrait())) {
				json.put("headPortrait", base + clientinfo.getHeadPortrait());
			} else {
				json.put("headPortrait", "");
			}
			
		} else {
			json.put("status", "exception");
		}
		
		return json.toString();
	}

	public void updateClientComp(ClientInfo clientInfo){
		if(clientInfo != null){
			clientUserDao.updateClientComp(clientInfo);
		}
	}
	
	
	public PageObject queryUploadClientInfo(ClientInfo clientInfo, QueryCondition queryCondition, QueryInfo queryInfo){
		long days = 30;
		if(queryCondition != null){
			if(queryCondition.getBeginTime() != null 
					&& queryCondition.getEndTime() != null){
				Date beginDate = queryCondition.getBeginTime();
				Date endDate = queryCondition.getEndTime();
				days = DateUtils.getQuot(beginDate, endDate);
			}
		}
		
		return clientUserDao.queryUploadClientInfo(days, clientInfo, queryInfo);
	}
	
	
	public PageObject queryClientUploadCount(ClientInfo clientInfo, QueryCondition queryCondition, QueryInfo queryInfo){
		Date beginTime = null;
		Date endTime = null;
		if(queryCondition == null){
			endTime = DateUtils.getDateByType(new Date(), "yyyy-MM-dd HH:mm:ss");
			beginTime = DateUtils.getAppointDate(endTime, 30);
		}else{
			if(queryCondition.getBeginTime() != null && queryCondition.getEndTime() != null){
				endTime = queryCondition.getEndTime();
				beginTime = queryCondition.getBeginTime();
			}else{
				endTime = DateUtils.getDateByType(new Date(), "yyyy-MM-dd HH:mm:ss");
				beginTime = DateUtils.getAppointDate(endTime, 30);
			}
		}
		
		return clientUserDao.queryClientUploadCount(clientInfo, beginTime, endTime, queryInfo);
	}
	
	/**
	 * 新的登陆方法
	 */
	public JSONObject newLogin(String account , String pwd , String source ,int evalType){
		
		// 验证参数是否正确
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(pwd)) {

			return JsonUtils.encapsulationJSON(0, "登陆参数不完整", "");
		} 
		//参数对象
		ClientInfo ci = new ClientInfo();
		
		// 判断用户登录方式
		if (StringUtils.checkEmail(account)) {
			
			ci.setEmail(account);
		} else if (StringUtils.checkPhone(account)) {
			
			ci.setMobile(account);
		} else {
			
			ci.setAccount(account);
		}
		
		// 查找是否存在该账号的用户
		ClientInfo qci = this.findClientInfo(ci);

		//判断是否存在该账户
		if (qci == null) {

			return JsonUtils.encapsulationJSON(0, "该账户不存在", "") ;
		}
		
		//判断密码是否正确
		if (!qci.getPassword().equals(pwd)) {
			
			return JsonUtils.encapsulationJSON(0, "该用户名与密码不相符", "") ;
		}
		JSONObject data = this.parseClientJsonByObj(qci, source, evalType) ;
		return JsonUtils.encapsulationJSON(1, "登陆成功", data.toString()) ;
	}
	
	/**
	 * 解析数据格式
	 * @author sun
	 * @version 2015-1-15 下午02:33:23
	 * @param clientInfo
	 * @return
	 */
	public JSONObject parseClientJsonByObj(ClientInfo ci , String source ,int evalType){
		
		TgPopularizeActivity popu = popuService.queryPopularizeByClientId(ci.getId());
		String base = SystemConfig.getString("image_base_url");
		
		BloodSugarTarget starget = new BloodSugarTarget();
		starget.setClientId(ci.getId());
		BloodSugarTarget target = targetService.quertTarget(starget);
		
		JSONObject json = new JSONObject() ;
		
		json.put("cid", ci.getId());
		json.put("mobile", ci.getMobile());
		json.put("name", StringUtils.clearNull(ci.getName()) );
		json.put("gender", ci.getGender() == null ? 0 : ci.getGender() );
		if(ci.getBirthday() != null){
			json.put("birthday", DateUtils.formatDate("yyyy-MM-dd", ci.getBirthday()));
		}else{
			json.put("birthday", "");
		}
		
		//查询客户最后一次体检信息
		ClientLatestPhy phy = latestPhyDao.getClientLastestPhy(ci.getId());
		json.put("height", phy != null ? phy.getHeight() : "");
		json.put("weight", phy != null ? phy.getWeight() : "");
		
		//查询客户收货地址信息
		List<DeliverAddress> lstAddress = addressDao.queryClientDeliverAddress(ci.getId());
		if(!CollectionUtils.isEmpty(lstAddress)){
			String data = JsonUtils.getJsonString4JavaListDate(lstAddress, new String[] { "clientId", "createTime"});
			json.put("deliverAddress", data);
		}else{
			json.put("deliverAddress", "");
		}
		
		if(!StringUtils.isEmpty(source)){
			if(source.split("-")[0].equals("bsksugar")//安卓血糖高管
					||source.split("-")[0].equals("DiabetesManagement")){//ios 血糖高管
				// 返回用户的邀请码
				ClientExtend clientExtend = clientExtendService.modifyLastTimeByClientId(ci.getId());
				if(clientExtend != null && !StringUtils.isEmpty(clientExtend.getInitiativeinvite()) ){
					json.put("initiativeinvite", clientExtend.getInitiativeinvite());
				}else{
					json.put("initiativeinvite", "");
				}
			}
			
			if(source.equals("php_sugar_1000")) {
				json.accumulate("regTime", DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN, ci.getCreateTime()));
			}
		}
		// 判断用户是否参与相关软件评测
		json.put("isEvaluate", -1 != evalType ? resultService.evaluateType(evalType,ci) : null);
		json.put("headPortrait", StringUtils.isEmpty(ci.getHeadPortrait()) == false ? base+ci.getHeadPortrait() : "");
		if(popu != null){
			
			json.put("popularize",StringUtils.clearNull(popu.getPopularize()) );
		}else{
			json.put("popularize","" );
		}
		
		if(target != null){
			json.put("fbgMax", target.getFbgMax());
			json.put("fbgMin", target.getFbgMin());
			json.put("pbgMax", target.getPbgMax());
			json.put("pbgMin", target.getPbgMin());
		}else{
			json.put("fbgMax", 6.1);
			json.put("fbgMin", 3.9);
			json.put("pbgMax", 7.8);
			json.put("pbgMin", 3.9);
		}
		
		if (ci.getType().equals(ClientInfo.TYPE_VIP + "")) {
			String vipProduct = SystemConfig.getString("visited_bloodsugar_product");
			String[] vips = vipProduct.split(",");
			boolean vipFlag = false;
			for (String vip : vips) {
				if (!StringUtils.isEmpty(ci.getAvailableProduct()) && ci.getAvailableProduct().contains(vip)) {
					vipFlag = true;
					break;
				}
			}
			if (!StringUtils.isEmpty(ci.getAvailableProduct()) && vipFlag) {
				json.put("clientType", ClientInfo.TYPE_VIP);// 会员类型
			} else {
				json.put("clientType", ClientInfo.TYPE_EXPERIENCE);// 体验类型
			}
		} else {
			json.put("clientType", ClientInfo.TYPE_EXPERIENCE);// 会员类型
		}
		
		return json ;
	}

	public synchronized JSONObject newPhoneRegister(ClientInfo ci , String source , String passivityinvite ,String popularize) {
		
		JSONObject datajson = new JSONObject();

		// 检查参数  TODO 加为空判断
		if (ci == null || ci.getMobile() == null || StringUtils.isEmpty(ci.getPassword())
				|| StringUtils.isEmpty(ci.getMobile())
				|| !StringUtils.isMobile(ci.getMobile())) {
				
			return JsonUtils.encapsulationJSON(0, "参数异常", "") ;
		}

		// 判断邮箱是否存在 来源是否是php
		if (!StringUtils.isEmpty(ci.getEmail()) && source.equals("php_sugar")) {
			ClientInfo client = new ClientInfo();
			client.setEmail(ci.getEmail());
			ClientInfo oldC = this.findClientInfo(client);
			if (null != oldC) {
				return JsonUtils.encapsulationJSON(0, "您的邮箱已经被使用", "") ;
			}
		}

		// 查找该手机号是否注册
		ClientInfo findMobile = new ClientInfo () ;
		findMobile.setMobile(ci.getMobile()) ;
		ClientInfo client = this.findClientInfo(findMobile);
		
		if (null != client) {
			// 手机号已存在
			log.info(">>>>>>>>>> 账号已经存在 : mobile = " + ci.getMobile());
			
			String data = "";
			// 真不想加，sb运营,一会儿一个想法, 活动结束删掉
			if (!StringUtils.isEmpty(source) && source.contains("php_sugar")) {
				JSONObject jdata = new JSONObject();
				jdata.accumulate("cid", client.getId());
				
				data = jdata.toString();
			}
			
			return JsonUtils.encapsulationJSON(0, "您的手机号已被注册", data);
		}
		
		//体验用户注册 或者 区域不为空但是数据库不存在对应的区域，都设置为默认区域
		if(null == ci.getAreaId() || 
				(null != ci.getAreaId() && false == validateAreaId(ci.getAreaId()))) {
			String vAreaId = SystemConfig.getString("vistied_area_id");
			if (!StringUtils.isEmpty(vAreaId)) {
				ci.setAreaId(Integer.parseInt(vAreaId));
			}
		}
		
		ClientInfo regInfo = this.createExperienceClient(ci,
				source, passivityinvite);
		
		//判断是否有推广标识
		if (!StringUtils.isEmpty(popularize)) {
			TgPopularizeActivity popu = new TgPopularizeActivity();
			popu.setClientId(regInfo.getId());
			popu.setPopularize(popularize);
			popuService.addClientPopularize(popu);
		}
		
		log.info(">>>>>>>>>> " + source + "注册");
		log.info(">>>>>>>>>> 手机体验用户体验用户注册成功 : mobile = "
				+ ci.getMobile() + ",account = " + ci.getAccount());
		log.info(">>>>>>>>>> 注册信息 : vipcard = " + ci.getVipCard()
				+ ",area = " + ci.getAreaId());
		
		datajson.put("cid", regInfo.getId().toString());
		datajson.put("mobile", regInfo.getMobile());
		return JsonUtils.encapsulationJSON(1, "注册成功", datajson.toString()) ;
	}
	
	/**
	 * 判断区域是否存在
	 */
	private boolean validateAreaId(Integer areaId) {
		AreaInfo areaInfo = areaInfoService.get(areaId);
		if(null != areaInfo && Constant.STATUS_NORMAL == areaInfo.getStatus()) {
			return true;
		}
		return false;
	}

	public JSONObject sendCodeForSignIn(ClientInfo ci ,String date , String token ) {
		
		//校验参数
		if(ci == null || StringUtils.isEmpty(date) || StringUtils.isEmpty(token)){
			return JsonUtils.encapsulationJSON(0, "参数异常", "");
		}
		
		//时间参数异常
		if(!DateUtils.isValidDate(date, DateUtils.LONG_DATE_PATTERN_PLAIN)){
			return JsonUtils.encapsulationJSON(0, "时间参数异常", "");
		}
		
		// 检查参数是否正确      TODO  加上为空判断
		if (StringUtils.isEmpty(ci.getMobile())
				|| !StringUtils.isMobile(ci.getMobile())) {

			return JsonUtils.encapsulationJSON(0, "手机号码格式错误", "");
		}
		
		//加密规则   电话号  + #^%@$（*注：特殊字符对应的数字键是36524） + 所传时间的时间戳(不是时间字符串)
		String tokenStr = ci.getMobile() + "#^%@$" +  
						DateUtils.parseDate(date, DateUtils.LONG_DATE_PATTERN_PLAIN).getTime();
		
		//按规则生成对应的token
		String createToken = MD5Util.digest32(tokenStr) ;
		
		//验证token
		if(!token.equals(createToken)){
			return JsonUtils.encapsulationJSON(0, "非法请求:token is worng", "");
		}
		
		String number = RandomUtils.getRandomNumber(4);
		
		// 判断手机号是否存在
		ClientInfo findMoblie = new ClientInfo();
		findMoblie.setMobile(ci.getMobile());
		ClientInfo resultClient = this.findClientInfo(findMoblie);
		if (resultClient != null) {
			return JsonUtils.encapsulationJSON(0, "手机号已经存在", "");

		} 
		
		// 注册体验用户发送验证码
		// 发送短信
		String vcode = MessageFormat.format(Message.getString("vcode"),
				number);
		ShortMessage shortMessage = new ShortMessage(Message
				.getString("mobile_register"), vcode, null, ci.getMobile());

		//发送短信
		int result = shortMessagService.sendMessage(shortMessage, "one");

		if (ShortMessage.SEND_RESULT_SUC == result) {// resuslt值为0,发送验证码成功
			
			JSONObject json = new JSONObject();
			json.put("number", number);
			json.put("mobile", ci.getMobile());
			return JsonUtils.encapsulationJSON(1, "手机短信验证码发送成功", json.toString()) ;

		} else {
			
			return JsonUtils.encapsulationJSON(0, "短信发送失败", "") ;
		}
		
	}
	
	public JSONObject sendCodeForRetrievePwd(ClientInfo ci ,String date , String token ){
		
		//校验参数
		if(ci == null || StringUtils.isEmpty(date) || StringUtils.isEmpty(token)){
			return JsonUtils.encapsulationJSON(0, "参数异常", "");
		}
		
		//时间参数异常
		if(!DateUtils.isValidDate(date, DateUtils.LONG_DATE_PATTERN_PLAIN)){
			return JsonUtils.encapsulationJSON(0, "时间参数异常", "");
		}
		
		
		if (StringUtils.isEmpty(ci.getMobile()) || !StringUtils.checkPhone(ci.getMobile())) {
			
			return JsonUtils.encapsulationJSON(0, "手机号码格式错误", "") ;
			
		}
		
		//加密规则   电话号  + #^%@$（*注：特殊字符对应的数字键是36524） + 所传时间的时间戳(不是时间字符串)
		String tokenStr = ci.getMobile() + "#^%@$" +  
						DateUtils.parseDate(date, DateUtils.LONG_DATE_PATTERN_PLAIN).getTime();
		
		//按规则生成对应的token
		String createToken = MD5Util.digest32(tokenStr) ;
		
		//验证token
		if(!token.equals(createToken)){
			return JsonUtils.encapsulationJSON(0, "非法请求:token is worng", "");
		}
		
		String number = RandomUtils.getRandomNumber(4);
		
		
		ClientInfo findClient = new ClientInfo();
		findClient.setMobile(ci.getMobile());
		ClientInfo resultClient = this.findClientInfo(findClient);
		
		//判断是否存在该手机号
		if (resultClient == null) {
			return JsonUtils.encapsulationJSON(0, "该手机号不存在", "") ;
		}
		
		// 找回密码通过手机短信发送验证码
		String vcode = MessageFormat.format(Message.getString("vcode"),
				number);
		ShortMessage shortMessage = new ShortMessage(Message
				.getString("mobile_find_pwd"), vcode, resultClient.getId(),
				resultClient.getMobile());
		shortMessage.setContent(vcode);
		
		//发送短息
		int result = shortMessagService.sendMessage(shortMessage, "one");

		if (ShortMessage.SEND_RESULT_SUC == result) {// resuslt值为0,发送验证码成功
			
			JSONObject json = new JSONObject();
			json.put("number", number);
			json.put("mobile", resultClient.getMobile());
			return JsonUtils.encapsulationJSON(1, "验证码发送成功", json.toString()) ;
		}else {
			return JsonUtils.encapsulationJSON(0, "验证码发送失败", "") ;
		}
		
	}

	
	public int queryRegClientCount(QueryCondition queryCondition){
		Date startDate = null;
		Date endDate = null;
		if(queryCondition != null){
			if(queryCondition.getBeginTime() != null){
				startDate = queryCondition.getBeginTime();
			}
			if(queryCondition.getEndTime() != null){
				endDate = DateUtils.getCurrentDayEndTime(queryCondition.getEndTime());
			}
		}
		
		return clientUserDao.queryRegClientCount(startDate, endDate);
	}
	
	
	public int queryAvgClientCount(QueryCondition queryCondition){
		Date begTime = DateUtils.getAppointDate(new Date(), -6);
		Date startDate = DateUtils.getCurrentDayStartTime(begTime);
		Date endDate = DateUtils.getCurrentDayEndTime(new Date());
		if(queryCondition != null){
			if(queryCondition.getBeginTime() != null){
				startDate = queryCondition.getBeginTime();
			}
			if(queryCondition.getEndTime() != null){
				endDate = DateUtils.getCurrentDayEndTime(queryCondition.getEndTime());
			}
		}
		
		int allCount = clientUserDao.queryRegClientCount(startDate, endDate);
		
		//一共多少天
		int days = (int)DateUtils.getQuot(startDate, endDate)+1;
		return (int)allCount/days;
	}
	
	public PageObject<ClientRegEval> queryClientAllInfo(QueryCondition queryCondition, QueryInfo queryInfo){
		Date startDate = null;
		Date endDate = null;
		if(queryCondition != null){
			if(queryCondition.getBeginTime() != null){
				startDate = queryCondition.getBeginTime();
			}
			if(queryCondition.getEndTime() != null){
				endDate = DateUtils.getCurrentDayEndTime(queryCondition.getEndTime());
			}
		}
		
		return clientUserDao.queryAllInfoDate(startDate, endDate, queryInfo);
	}
	
	
	public List<ClientRegEval> queryClientAllInfoDetail(List<ClientRegEval> lstClientReg, QueryInfo queryInfo){
		if(!CollectionUtils.isEmpty(lstClientReg)){
			Date sdate = DateUtils.getAppointDate(lstClientReg.get(lstClientReg.size()-1).getCreateTime(), -1);
			Date startDate = DateUtils.getCurrentDayStartTime(sdate);
			Date endDate = DateUtils.getCurrentDayEndTime(lstClientReg.get(0).getCreateTime());
			
			List<ClientRegEval> lstDetail = clientUserDao.queryAllInfoDetail(startDate, endDate);
			
			List<ClientRegEval> lst = new ArrayList<ClientRegEval>();
			
			for (ClientRegEval clientRegEval : lstClientReg) {
				Date createTime = clientRegEval.getCreateTime();
				for (ClientRegEval cre : lstDetail) {
					
					if(cre.getCreateTime().equals(createTime)){
						clientRegEval.setCreateTime(createTime);
						int type = cre.getType();
						int count = cre.getCount();
						
						//注册客户数量
						if(type == 1){
							clientRegEval.setCount(count);
							String date = DateUtils.formatDate("yyyy-MM-dd", createTime);
							//自主注册
							int regSelfCount = clientUserDao.queryRegSelfCount(date);
							clientRegEval.setRegSelfCount(regSelfCount);
							//医生邀请客户
							int doctorInviteCount = clientUserDao.queryDoctorInviteClientCount(date);
							clientRegEval.setDoctorInviteCount(doctorInviteCount);
							//客户邀请客户
							int clientInviteCount = clientUserDao.queryClientInviteCount(date);
							clientRegEval.setClientInviteCount(clientInviteCount);
							
							int otherCount = count - doctorInviteCount - clientInviteCount - regSelfCount;
							clientRegEval.setOtherCount(otherCount);
							
						}else if(type == 2){  //评估报告数量
							clientRegEval.setEvalClientCount(count);
						}else if(type == 3){  //上传血糖数量
							clientRegEval.setUploadBloodSugarCount(count);
						}else if(type == 4){  //咨询客户数量
							clientRegEval.setPciClientCount(count);
						}
					}
				}
				
				Date endTime = DateUtils.getCurrentDayEndTime(createTime);
				Date startTime = DateUtils.getCurrentDayStartTime(DateUtils.getBeforeDay(endTime, -6));
				
				List<ClientRegEval> lstCre = clientUserDao.querySumInfoDetail(startTime, endTime);
				if(!CollectionUtils.isEmpty(lstCre)){
					for (ClientRegEval creval : lstCre) {
						int type = creval.getType();
						int count = creval.getCount();
						if(type == 1){
							clientRegEval.setAvgClientCount((int)count/7);
						}else if(type == 2){
							clientRegEval.setAvgEvalCount((int)count/7);
						}else if(type == 3){
							clientRegEval.setAvgUploadBloodSugarCount((int)count/7);
						}else if(type == 4){
							clientRegEval.setAvgPciCount((int)count/7);
						}
					}
				}
				
				lst.add(clientRegEval);
			}
			return lst;
		}
		return null;
	}
	
	
	public List<ClientRegEval> querySumInfoDetail(QueryCondition queryCondition){
		Date startDate = null;
		Date endDate = null;
		if(queryCondition != null){
			if(queryCondition.getBeginTime() != null){
				startDate = queryCondition.getBeginTime();
			}
			if(queryCondition.getEndTime() != null){
				endDate = DateUtils.getCurrentDayEndTime(queryCondition.getEndTime());
			}
		}
		
		return clientUserDao.querySumInfoDetail(startDate, endDate);
	}
	
	public String updateClientInfo(String data, int type, Integer cid){
		if(!StringUtils.isEmpty(data) && cid != null && type != 0){
			if(type < 5){
				clientUserDao.updateClientInfo(data, type, cid);
			}else if(type >= 5){
				latestPhyDao.updateLatestPhy(data, type, cid);
			}
			return JsonUtils.encapsulationJSON(1, "修改成功", "").toString();
		}
		return JsonUtils.encapsulationJSON(0, "参数错误", "").toString();
	}
	
	
	public String clientWelcomeInfo(Integer clientId){
		
		/* 返回数据格式
		 * {    
		 * 		"code" : 1,    
		 * 		"msg" : "",    
		 * 		"data" : {        	
		 * 			"portrait" : "http://....",        
		 * 			"name" : "姓名",        
		 * 			"mobile" : "手机号",        
		 * 			"balance" : 0,  //余额        
		 * 			"integral": 0,  //积分        
		 * 			"redEnvelopeCount": 0   //红包个数(未使用的)    
		 * 		}
		 * }
		 */
		if(clientId != null){
			ClientInfoExtend ci = clientUserDao.queryClientExtend(clientId);
			if(ci != null){
				JSONObject jo = new JSONObject();
				jo.put("name", StringUtils.isEmpty(ci.getName()) == false ? ci.getName() : "");
				jo.put("mobile", StringUtils.isEmpty(ci.getMobile()) == false ? ci.getMobile() : "");
				String baseUrl = SystemConfig.getString("image_base_url");
				jo.put("portrait", StringUtils.isEmpty(ci.getHeadPortrait())
						== false ? baseUrl + ci.getHeadPortrait() : "");
				
				jo.put("balance", ci.getBalance());
				jo.put("integral", ci.getTotalScore());
				
				int count = redEnvelopDao.queryClientRedNoUse(clientId, NTgRedEnvelop.RED_STATUS_NOT_USE);
				jo.put("redEnvelopeCount", count);
				
				return JsonUtils.encapsulationJSON(1, "查询成功", jo.toString()).toString();
			}else{
				return JsonUtils.encapsulationJSON(1, "不存在该客户", "").toString();
			}
		}
		return JsonUtils.encapsulationJSON(0, "参数有误", "").toString();
	}
	
	public void demo() {
		
		for (int i = 0; i < 3; i++) {
			ClientInfo ci = findClientById(10001);
			ci.setAge(20 + i);
			if(i == 1) {
				ci.setAddress("address");
				clientUserDao.update(ci);
			}
			clientUserDao.update(ci);
		}
	}
	
}
