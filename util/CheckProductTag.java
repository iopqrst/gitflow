package com.bskcare.ch.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.ProductExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.vo.Product;
import com.google.gson.JsonArray;

@SuppressWarnings("unused")
public class CheckProductTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CheckFunctionTag.class);
	
	private String fnId; //功能Id

	public String getFnId() {
		return fnId;
	}

	public void setFnId(String fnId) {
		this.fnId = fnId;
	}
	
	public int doStartTag() throws JspTagException {
		
		JspWriter out = pageContext.getOut();
		
		try {
			if(!StringUtils.isEmpty(fnId)) {
				out.print(getProudctName(fnId));
			} else {
				out.print("");
			}
			fnId = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	/**
	 * 根据产品Id获取产品名字
	 * @param fnId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getProudctName(String productIds) {
		Object productObj = pageContext.getSession().getAttribute(Constant.MANAGE_PRODUCT_INFO);
		String name = ""; //产品列表
		if(productObj != null){
			List<ProductExtend> lstProduct = JsonUtils.getList4Json(productObj.toString(), ProductExtend.class);
			if(!CollectionUtils.isEmpty(lstProduct)){	
				List<String> lstName = new ArrayList<String>();
				
				String fnName = fnId;
				if(!StringUtils.isEmpty(fnName)){
					String[] names = fnName.split(",");
					if(names != null && !names.equals("")){
						for (String sname : names) {
							int id = Integer.parseInt(sname);
							for (ProductExtend product : lstProduct) {
								if(id == product.getId()){
									lstName.add(product.getName());
								}
							}
						}
					}
				}
				
				if(!CollectionUtils.isEmpty(lstName)){
					name = ArrayUtils.join(lstName.toArray(), ",");
				}
			}
		}
		return name;
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
