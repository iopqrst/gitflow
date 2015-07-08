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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

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

}
