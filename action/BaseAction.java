package com.bskcare.ch.base.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.crm.CrmUserInfo;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ManageLogService;
import com.bskcare.ch.service.UserInfoService;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ManageLog;
import com.bskcare.ch.vo.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * BaseAction Action 公用的方法可以放到该类中
 * 
 * @author houzhiqing
 * 
 */
@SuppressWarnings("unchecked")
public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 6574855907824041503L;

	protected transient final Logger log = Logger.getLogger(getClass());

	protected final static String REDIRECT = "redirect";

	protected final static String URLACTION = "urlAction";

	protected PageObject pager = null;

	protected Integer pageSize = 10;

	protected Integer pageOffset = 0;

	protected Integer pageNo = 1;

	protected String order;

	protected String sort;

	protected String _;

	protected String source; // 判断操作是否要经过过滤器

	protected int userAgent; // 如果有代理管理员，判断是查询代理管理员还是查询管理员的信息 1：代理的管理员

	protected CrmUserInfo agentUser;

	/**
	 * 区域链查询条件参数
	 */
	protected String qareaChain;
	
	/**
	 * 用户id
	 */
	protected Integer cid;

	public String get_() {
		return _;
	}

	public void set_(String _) {
		this._ = _;
	}

	@Autowired
	private ManageLogService logService;
	@Autowired
	private UserInfoService buserInfoService;

	/**
	 * 初始化系统访问图片路径
	 */
	protected void initBaseImageUrl() {
		sput("base_image", SystemConfig.getString("image_base_url"));
	}

	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获取用户URL 含参数
	 * 
	 * @return
	 */
	public String getUrl() {
		HttpServletRequest request = this.getRequest();
		StringBuffer sb = new StringBuffer("");
		sb.append(request.getRequestURL().toString() + "?");

		Enumeration tmp = request.getParameterNames();
		int tmpint = 0;
		while (tmp.hasMoreElements()) {
			tmpint++;
			String parameter = (String) tmp.nextElement();
			String value = request.getParameter(parameter);
			if (tmpint == 1) {
				sb.append("");
			} else {
				sb.append("&");
			}
			sb.append(parameter + "=" + value);
		}
		return sb.toString();
	}

	protected PrintWriter getPrintWriter() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		return response.getWriter();
	}

	protected String getUrlHeader() {
		String urlHeader = "http://" + this.getRequest().getServerName() + ":"
				+ this.getRequest().getServerPort() + "/";
		return urlHeader;
	}

	protected String getRemoteIp() {
		return getRequest().getRemoteAddr();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 向ActionContext中存值<br/>
	 * ActionContext.getContext().put(key, value);
	 * 
	 * @param key
	 * @param value
	 */
	protected void cput(String key, Object value) {
		ActionContext.getContext().put(key, value);
	}

	/**
	 * 从ActionContext中获取值 ActionContext.getContext().get(key);
	 * 
	 * @param key
	 */
	protected Object cget(String key) {
		return ActionContext.getContext().get(key);
	}

	/**
	 * 向ActionContext-session中存值<br/>
	 * ActionContext.getContext().getSession().put(key, value);
	 * 
	 * @param key
	 * @param value
	 */
	protected void sput(String key, Object value) {
		ActionContext.getContext().getSession().put(key, value);
	}

	/**
	 * 从ActionContext-session中获取值<br/>
	 * ActionContext.getContext().getSession().put(key, value);
	 * 
	 * @param key
	 * @param value
	 */
	protected Object sget(String key) {
		return ActionContext.getContext().getSession().get(key);
	}

	/**
	 * 跳转的list页面
	 */
	protected String toListPage(String listUrl) {
		cput(URLACTION, listUrl);
		return REDIRECT;
	}

	public QueryInfo getQueryInfo() {
		try {
			pageOffset = Integer.parseInt(getRequest().getParameter(
					"pager.offset"));
		} catch (NumberFormatException e) {

		}

		// if (log.isDebugEnabled()) {
		// log.debug(">>>>>>>>>> current pageOffset is :	" + pageOffset);
		// log.debug(">>>>>>>>>> current pageSize is :	" + pageSize);
		// }

		QueryInfo queryInfo = new QueryInfo(pageSize, sort, order, pageOffset);
		return queryInfo;
	}

	public QueryInfo getQueryInfo(Integer pageNo) {
		if (null != pageNo) {
			QueryInfo queryInfo = new QueryInfo(pageSize, sort, order,
					(pageNo - 1) * pageSize);
			return queryInfo;
		}
		return null;
	}

	/**
	 * Client用户信息
	 */
	public ClientInfo getClientLoginUserInfo() {
		ClientInfo clientInfo = (ClientInfo) sget(Constant.CLIENT_USER);
		return clientInfo;
	}

	/**
	 * Manage用户信息
	 */
	public UserInfo getManageLoginUserInfo() {
		UserInfo ui = (UserInfo) sget(Constant.MANAGE_USER);
		return ui;
	}

	public String getManageLoginUserAreas() {
		String area = (String) sget(Constant.MANAGE_USER_AREAS);
		return area;
	}

	/**
	 * 手机端用户信息
	 */
	public ClientInfo getAndroidLoginUserInfo() {
		HttpServletRequest request = this.getRequest();
		ClientInfo ui = (ClientInfo) request
				.getAttribute(Constant.ANDROID_USER);
		return ui;
	}

	/**
	 * 获取Manage端登陆用户的用户Id
	 */
	public Integer getManageLoginUserId() {
		UserInfo user = getManageLoginUserInfo();
		return null == user ? null : user.getId();
	}

	/**
	 * 获取Client端登陆用户的用户Id
	 */
	public Integer getClientLoginUserId() {
		ClientInfo clientInfo = getClientLoginUserInfo();
		return null == clientInfo ? null : clientInfo.getId();
	}

	public String convertUniCode(String target)
			throws UnsupportedEncodingException {
		if (!StringUtils.isEmpty(target)) {
			return new String(target.getBytes("ISO-8859-1"), "UTF-8");
		}
		return target;
	}

	/**
	 * 添加日志
	 */
	protected void addLog(Integer userId, Integer clientId, Integer type,
			String content) {
		HttpServletRequest request = this.getRequest();
		String ip = request.getRemoteAddr();
		ManageLog mlog = new ManageLog();
		mlog.setType(type);
		mlog.setContent(content);
		mlog.setClientId(clientId);
		mlog.setOperateDate(new Date());
		mlog.setUserId(userId);
		mlog.setUserIp(ip);
		logService.addLog(mlog);
	}

	/**
	 * <p>
	 * PrintWriter out = getResponse().getWriter();<br/>
	 * out.print(result);<br/>
	 * out.flush();
	 * </p>
	 * 
	 * @param result
	 *            输出结果
	 */
	protected void printOut(String result) throws IOException {
		PrintWriter out = getResponse().getWriter();
		getResponse().setCharacterEncoding("utf-8");
		
//		if(log.isDebugEnabled()) {
			log.debug("响应结果：" + result);
//		}
		
		out.print(result);
		out.flush();
	}

	/**
	 * 获取登陆用户的区域链
	 * 
	 * @return
	 */
	public String getManageAreaChain() {
		String areaChain = (String) sget(Constant.MANAGE_USER_AREAS);
		return areaChain;
	}

	public CrmUserInfo getManageCrmUser() {
		CrmUserInfo crmUser = (CrmUserInfo) sget(Constant.MANAGE_CRM_USER);
		return crmUser;
	}

	public List<CrmUserInfo> getManageAgentUser() {
		List<CrmUserInfo> lstAgentUser = (List<CrmUserInfo>) sget(Constant.MANAGE_AGENT_USER);
		return lstAgentUser;
	}

	public String queryAreaChain() {
		String areaChain = "";

		if (agentUser == null
				|| (agentUser != null && agentUser.getId() == null)) {
			// 如果qareaChain不为空证明用户要按照某个区域查询，直接返回查询某个区域链,且该某个区域链包含在管理员管辖区域链内
			// if(!StringUtils.isEmpty(qareaChain) &&
			// getManageAreaChain().contains(qareaChain)) {
			if (!StringUtils.isEmpty(qareaChain)) {
				return qareaChain;
			} else {
				areaChain = getManageAreaChain();
			}
		} else {
			List<CrmUserInfo> lstCrm = getManageAgentUser();
			if (!CollectionUtils.isEmpty(lstCrm)) {
				for (CrmUserInfo crmUserInfo : lstCrm) {
					if (agentUser.getId().equals(crmUserInfo.getId())) {
						// 如果qareaChain不为空证明用户要按照某个区域查询，直接返回查询某个区域链,且该某个区域链包含在管理员管辖区域链内
						// if(!StringUtils.isEmpty(qareaChain) &&
						// crmUserInfo.getAreaChain().contains(qareaChain)) {
						if (!StringUtils.isEmpty(qareaChain)) {
							return qareaChain;
						} else {
							areaChain = crmUserInfo.getAreaChain();
							break;
						}
					}
				}
			}
		}
		return areaChain;
	}

	/**
	 * 根据区域Id获取管理员
	 * 
	 * @param areaId  区域id
	 * @param role  角色类型
	 */
	public JSONArray getBUserInfoByAreaId(Integer areaId, String role) {
		if (null == areaId || role == null)
			return null;
		Map<Integer, JSONArray> map = (Map<Integer, JSONArray>) sget("areaMap");
		JSONArray ja = null;
		if (null == map) {
			map = new HashMap<Integer, JSONArray>();
		}
		if (map.containsKey(areaId)
				&& jaIsHasRole(map.get(areaId), role, "roleType")) {
			ja = map.get(areaId);
		} else {
			ja = buserInfoService.getAdmChainByAreaIdAndUserTypeIds(areaId,
					role);
			if (ja != null) {
				map.put(areaId, ja);
				sput("areaMap", map);
			}
		}
		return ja;
	}

	//判断该用户所在区域是否有指定角色的管理员
	private boolean jaIsHasRole(JSONArray ja, String rolestr, String property) {
		boolean bool = false;
		if (ja != null && !StringUtils.isEmpty(rolestr)) {
			String[] roles = rolestr.split(",");
			for (String role : roles) {
				for (Object object : ja) { // 遍历管理员
					JSONObject jo = (JSONObject) object;
					if (jo != null) {
						String field = jo.getString(property);
						if (field != null && role.equals(field)) {
							bool = true;
						}
					}
				}
				if (!bool) {
					break;
				}
			}
		}
		return bool;
	}

	/**
	 * 根据用户id获得用户信息
	 * 
	 * @param userId
	 *            用户id
	 * @return 接口数据
	 */
	public JSONObject getUserInfoByUserId(Integer userId) {
		return buserInfoService.getUserinfoByUserId(userId);
	}

	/**
	 * 根据区域id获得所有地面管理员
	 * 
	 * @param areaId
	 *            区域id
	 * @return 接口数据
	 */
	public JSONArray getAgentAdminByAreaId(Integer areaId) {
		return buserInfoService.getAgentAdminByAreaId(areaId);
	}

	public CrmUserInfo getAgentUser() {
		return agentUser;
	}

	public void setAgentUser(CrmUserInfo agentUser) {
		this.agentUser = agentUser;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(Integer pageOffset) {
		this.pageOffset = pageOffset;
	}

	public PageObject getPager() {
		return pager;
	}

	public void setPager(PageObject pager) {
		this.pager = pager;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(int userAgent) {
		this.userAgent = userAgent;
	}

	public String getQareaChain() {
		return qareaChain;
	}

	public void setQareaChain(String qareaChain) {
		this.qareaChain = qareaChain;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

}
