package com.bskcare.ch.util;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

/**
 * HTTP协议消息发送工具类
 */
public class HttpClientUtils4CMCC {

	private static int RESP_SUCCESS = 200;
	private static String DEFAULT_CHARSET = "UTF-8";
	private static int maxTotalConnections = 1024; // 最大活动连接数
	private static int defaultMaxConnectionsPerHost = 1024; // 最大连接数1000
	private static int defaultConnectionTimeout = 5000; // 连接超时时间(单位毫秒)
	private static int defaultSoTimeout = 60000; // 读取数据超时时间(单位毫秒)

	private static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();

	private static final Logger logger = Logger
			.getLogger(HttpClientUtils4CMCC.class);

	public static HttpClient getHttpClient() {
		HttpClient hc = new HttpClient(manager);
		hc.getHttpConnectionManager().getParams()
				.setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
		hc.getHttpConnectionManager().getParams()
				.setMaxTotalConnections(maxTotalConnections);
		hc.getHttpConnectionManager().getParams()
				.setConnectionTimeout(defaultConnectionTimeout);
		hc.getHttpConnectionManager().getParams()
				.setSoTimeout(defaultSoTimeout);
		return hc;
	}

	public static CMCCResponse getContentByPostForCmcc(String method,
			String requestBody) {

		String content = null;

		try {
			if(StringUtils.isEmpty(method)) {
				return null;
			}

			String url = MessageFormat.format(
					ApiInterfaceURL.getString("cmcc_sync_url"), method);
			
			logger.info("url = " + url + ", request body ==" + requestBody);
			content = getContentByPost(url, requestBody, DEFAULT_CHARSET);
			logger.info("CMCC Response:" + content);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.info("----->" + e1.getMessage());
			return new CMCCResponse(CMCCResponse.STATUS_FAIL);
		}

		if (!StringUtils.isEmpty(content)) {
			try {
				Map jmap = JsonUtils.getMap4Json(content);
				CMCCResponse resp = null;
				if (null != jmap && jmap.size() > 0) {
					resp = new CMCCResponse();

					if (null != jmap.get("status")) {
						resp.setStatus(jmap.get("status") + "");
					}

					if (null != jmap.get("errcode")) {
						resp.setStatus(jmap.get("errcode") + "");
					}

					if (null != jmap.get("errmsg")) {
						resp.setStatus(jmap.get("errmsg") + "");
					}

				}
				return resp;
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("cmcc 接口返回数据格式错误，解析失败， content : " + content);
				return new CMCCResponse(CMCCResponse.STATUS_FAIL);
			}
		}

		return null;
	}

	/**
	 * httppost请求
	 */
	public static String getContentByPost(String url, String requestBody,
			String charSet) {

		int statusCode = 0;
		String content = "";

		if (StringUtils.isEmpty(charSet)) {
			charSet = DEFAULT_CHARSET;
		}

		PostMethod postMethod = new UTF8PostMethod(url);
		HttpClient hc = getHttpClient();

		try {
			if (null != requestBody && !"".equals(requestBody)) {
				String encoderJson = URLEncoder.encode(requestBody,
						DEFAULT_CHARSET);
				postMethod.setRequestEntity(new StringRequestEntity(
						encoderJson, "text/json", charSet));
			}

			postMethod.setRequestHeader("Content-Type", "application/json");
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, true));

			statusCode = hc.executeMethod(postMethod);

			if (statusCode == RESP_SUCCESS) {
				content = StringUtils.read(
						postMethod.getResponseBodyAsStream(), charSet).trim();
			} else {
				logger.info("使用post获取第三方平台资源[" + url + "]响应失败,响应吗："
						+ statusCode);
				throw new RuntimeException("使用post获取第三方平台资源[" + url
						+ "]响应失败,响应吗：" + statusCode);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			postMethod.releaseConnection();
		}
		return content;
	}

	/**
	 * Inner Class For UTF-8
	 * 
	 * @author Administrator
	 * 
	 */
	private static class UTF8PostMethod extends PostMethod {
		public UTF8PostMethod(String url) {
			super(url);
		}

		@Override
		public String getRequestCharSet() {
			return "UTF-8";
		}
	}

}
