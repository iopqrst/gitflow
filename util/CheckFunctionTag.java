package com.bskcare.ch.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.crm.CrmUserPermission;
import com.bskcare.ch.constant.Constant;

@SuppressWarnings("unused")
public class CheckFunctionTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CheckFunctionTag.class);
	
	private String fnId; //功能Id

	public String getFnId() {
		return fnId;
	}

	public void setFnId(String fnId) {
		this.fnId = fnId;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspTagException {

		List<CrmUserPermission> lstFunction = (List<CrmUserPermission>) pageContext.getSession().getAttribute(Constant.MANAGE_USER_PREMISSION);

		if (!CollectionUtils.isEmpty(lstFunction)) {
			String fnName = FunctionCfg.getString(fnId);
			for (CrmUserPermission function : lstFunction) {
		
				if(function.getLevel() == 4 && fnName.equalsIgnoreCase(function.getId()+"")){
					 return EVAL_BODY_INCLUDE;  
				}
			}
		}
		
		return SKIP_BODY;
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

	public void doInitBody() throws JspTagException {
	}

	public void setBodyContent(BodyContent bodyContent) {
		this.bodyContent = bodyContent;
	}
}
