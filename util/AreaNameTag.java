package com.bskcare.ch.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.service.AreaInfoService;
import com.bskcare.ch.vo.AreaInfo;

@SuppressWarnings("unchecked")
public class AreaNameTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	private String areaId;
	@Autowired
	private AreaInfoService areaInfoService ;

	public int doStartTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
		try {
			String msg = getAreaName(areaId);
			if(msg==null || msg.equals("null")) msg=areaId;
			out.print(msg);
		} catch (Exception e) {
			System.out.println("异常");
		}
		return SKIP_BODY;
	}

	private String getAreaName(String param) {
		Map<String, String> map = (Map<String, String>) getSession()
				.getAttribute(Constant.AREANAMELIST);
		String areaName = "";
		if (null != map) {
			areaName = map.get(param);
		}
		if(map == null){
			map = new HashMap<String, String>();
		}
		if(StringUtils.isEmpty(areaName)){
			AreaInfo areaInfo = areaInfoService.get(Integer.parseInt(param));
			if(areaInfo == null ) 
				areaName = param;//查询不到信息，返回id
			map.put(param, areaInfo.getName());
			getSession().setAttribute(Constant.AREANAMELIST, map);
		}
		return areaName;
	}

	private HttpSession getSession() {
		return pageContext.getSession();
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

}
