package com.bskcare.ch.base.action;

/**
 * 直接跳转
 * @author houzhiqing
 * 如果请求地址包含“.../redirect_xxxx.do?...”
 * 程序跳转到struts.common.xml中xxxx指定的页面
 *
 */
public class RedirectAction extends BaseAction {

	private static final long serialVersionUID = 8615746996405615980L;

	public String execute() throws Exception{
		
		String url = getRequest().getRequestURI();
		int startPoint = url.lastIndexOf("_");
		int endPoint = url.lastIndexOf(".");
		String reStr = url.substring(startPoint+1,endPoint);
		
		return reStr;
	}
	
	public String execute() throws Exception {
		
		
		return "hello";
	}

}
