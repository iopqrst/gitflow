package com.bskcare.ch.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class BskUtilsTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BskUtilsTag.class);

	private String param1; // 参数1
	private String param2; // 参数2
	private String param3; // 参数3
	private String fn; // 对应功能

	public int doStartTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
		try {
			String msg = getValueByFunction(fn, param1, param2, param3);
			out.print(msg);
		} catch (Exception e) {
		}
		return SKIP_BODY;
	}

	private String getValueByFunction(String fn, String param1, String param2,
			String param3) {
		String msg = "";
		if ("showLevel".equals(fn)) {
			msg = getClientLevel(param1);
		} else if ("show".equals(fn)) {

		}
		return msg;
	}

	/**
	 * 获取等级
	 */
	private String getClientLevel(String param) {
		Map<String, String> hashMap = (Map<String, String>) getSession()
				.getAttribute("PRODUCT_LEVEL");
		String level = "";
		if(null != hashMap) {
			level = hashMap.get(param);
		}
		return level;
	}

	private HttpSession getSession() {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		HttpSession session = request.getSession();
		return session;
	}

	public int doEndTag() throws JspTagException {
		try {
			if (bodyContent != null) {
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
			}
		} catch (IOException e) {
			throw new JspTagException("IO ERROR:" + e.getMessage());
		}
		return EVAL_PAGE;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}

	public void doInitBody() throws JspTagException {
	}

	public void setBodyContent(BodyContent bodyContent) {
		this.bodyContent = bodyContent;
	}
}
