package com.bskcare.ch.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

/**
 * HTTP协议消息发送工具类
 */
public class HttpClientUtils {

	private static int RESP_SUCCESS = 200;
	private static String DEFAULT_CHARSET = "UTF-8";
	private static int maxTotalConnections = 1024; // 最大活动连接数
	private static int defaultMaxConnectionsPerHost = 1024; // 最大连接数1000
	private static int defaultConnectionTimeout = 5000; // 连接超时时间(单位毫秒)
	private static int defaultSoTimeout = 60000; // 读取数据超时时间(单位毫秒)

	private static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();

	private static final Logger logger = Logger
			.getLogger(HttpClientUtils.class);

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

	/**
	 * httppost请求
	 * 
	 * @param url
	 * @param map
	 *            表单键值对
	 * @return 响应码
	 * @see
	 */
	public static int post(String url, Map<String, String> map, String charSet) {
		int statusCode = 0;
		NameValuePair[] postData = null;

		Set<Map.Entry<String, String>> set = map.entrySet();
		postData = new NameValuePair[set.size()];
		int i = 0;
		for (Iterator<Map.Entry<String, String>> iter = set.iterator(); iter
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();
			postData[i] = new NameValuePair(entry.getKey(), entry.getValue());
			i++;
		}
		HttpClient hc = getHttpClient();

		PostMethod postMethod = new UTF8PostMethod(url);
		postMethod.addParameters(postData);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, true));
		// postMethod.setRequestBody(data);
		try {
			statusCode = hc.executeMethod(postMethod);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			postMethod.releaseConnection();
		}
		return statusCode;
	}

	/**
	 * 调用crm接口
	 * 
	 * @param url
	 * @param map
	 * @param charSet
	 * @return 返回相应对象
	 */
	public static CrmResponse getCrmResponse(String url, Map<String, String> map) {
		String content = null;
		try {
			logger.info("url = " + url);
			content = getContentByPost(url, map, null);
			logger.info("CRM Response:" + content);
		} catch (Exception e1) {
			e1.printStackTrace();
			CrmResponse resp = new CrmResponse();
			resp.setCode(CrmResponse.CODE_FAIL);
			return resp;
		}
		if (!StringUtils.isEmpty(content)) {
			try {
				Map jmap = JsonUtils.getMap4Json(content);
				CrmResponse resp = null;
				if (null != jmap && jmap.size() > 0) {
					resp = new CrmResponse();

					if (null != jmap.get("code")) {
						resp.setCode(jmap.get("code") + "");
					}

					if (null != jmap.get("msg")) {
						resp.setMsg(jmap.get("msg") + "");
					}

					if (null != jmap.get("data")
							&& !"".equals(jmap.get("data") + "")) {
						boolean isArray = JSONUtils.isArray(jmap.get("data"));
						if (isArray) {
							resp.setArrayData((JSONArray) jmap.get("data"));
						} else {
							resp.setData((JSONObject) jmap.get("data"));
						}
					}
				}
				return resp;
			} catch (Exception e) {
				logger.info("crm 接口返回数据格式错误，解析失败， content : " + content);
				e.printStackTrace();
				CrmResponse resp = new CrmResponse();
				resp.setCode(CrmResponse.CODE_FAIL);
				return resp;
			}
		}
		return null;
	}

	/**
	 * httppost请求
	 * 
	 * @param url
	 * @param map
	 *            表单键值对
	 * @return 响应内容
	 * @see
	 */
	public static String getContentByPost(String url, Map<String, String> map,
			String charSet) {

		int statusCode = 0;
		String content = "";
		NameValuePair[] postData = null;
		PostMethod postMethod = new UTF8PostMethod(url);

		Set<Map.Entry<String, String>> set = map.entrySet();
		postData = new NameValuePair[set.size()];
		int i = 0;
		for (Iterator<Map.Entry<String, String>> iter = set.iterator(); iter
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();
			postData[i] = new NameValuePair(entry.getKey(), entry.getValue());
			i++;
		}
		HttpClient hc = getHttpClient();

		postMethod.addParameters(postData);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, true));
		// postMethod.setRequestBody(data);

		try {
			if (StringUtils.isEmpty(charSet)) {
				charSet = DEFAULT_CHARSET;
			}
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
			logger.info(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

		finally {
			postMethod.releaseConnection();
		}
		return content;

	}

	/**
	 * httppost请求
	 * 
	 * @param url
	 * @return 响应内容
	 * @see
	 */
	public static String getContentByPost(String url, String charSet) {

		String content = "";
		int statusCode = 0;
		PostMethod postMethod = new UTF8PostMethod(url);

		HttpClient hc = getHttpClient();

		// postMethod.setRequestBody(data);
		try {
			if (StringUtils.isEmpty(charSet)) {
				charSet = DEFAULT_CHARSET;
			}
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, true));
			statusCode = hc.executeMethod(postMethod);
			if (statusCode == RESP_SUCCESS) {
				content = StringUtils.read(
						postMethod.getResponseBodyAsStream(), charSet).trim();
			} else {
				logger.debug("使用post获取第三方平台资源[" + url + "]响应失败,响应吗："
						+ statusCode);
				throw new RuntimeException("使用post获取第三方平台资源[" + url
						+ "]响应失败,响应吗：" + statusCode);
			}
		} catch (Exception e) {
			logger.debug("请求服务[" + url + "]失败", e);
			throw new RuntimeException("请求服务[" + url + "]失败", e);
		}

		finally {
			postMethod.releaseConnection();
		}
		return content;

	}

	/**
	 * httpget请求
	 * 
	 * @param url
	 * @return 响应内容
	 * @see
	 */
	public static String getContentByGet(String url, String charSet) {
		String content = null;
		HttpClient hc = getHttpClient();
		GetMethod getMethod = new GetMethod(url);
		// postMethod.setRequestBody(data);
		try {
			if (StringUtils.isEmpty(charSet)) {
				charSet = DEFAULT_CHARSET;
			}
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, true));
			int statusCode = hc.executeMethod(getMethod);
			if (statusCode == RESP_SUCCESS) {
				content = StringUtils.read(getMethod.getResponseBodyAsStream(),
						charSet).trim();
			} else {
				logger.debug("使用get获取第三方平台资源[" + url + "]响应失败,响应吗："
						+ statusCode);
				throw new RuntimeException("使用get获取第三方平台资源[" + url
						+ "]响应失败,响应吗：" + statusCode);
			}

		} catch (Exception e) {
			logger.debug("请求服务[" + url + "]失败", e);
			throw new RuntimeException("请求服务[" + url + "]失败", e);
		} finally {
			getMethod.releaseConnection();
		}
		return content;

	}

	/**
	 * Inner Class For UTF-8
	 * 
	 * @author Administrator
	 * 
	 */
	public static class UTF8PostMethod extends PostMethod {
		public UTF8PostMethod(String url) {
			super(url);
		}

		@Override
		public String getRequestCharSet() {
			// return super.getRequestCharSet();
			return "UTF-8";
		}
	}

	public static int get(String url) {
		int statusCode = 0;
		HttpClient hc = getHttpClient();

		GetMethod getMethod = new GetMethod(url);
		// postMethod.setRequestBody(data);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, true));
		try {
			statusCode = hc.executeMethod(getMethod);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			getMethod.releaseConnection();
		}
		return statusCode;
	}

	public static void main(String[] args) throws IOException {
		// String url = "http://58.83.224.180:8090/facade/sms_sendSMS.do";
		//
		// HashMap<String, String> parmas = new HashMap<String, String>();
		//
		// parmas.put("shortMessage.mobile", "18612834872");
		// parmas.put("shortMessage.content", "我的短信测试");
		//
		// CrmResponse cr = HttpClientUtils.getCrmResponse(url, parmas);
		// System.out.println(cr.toString());

		String url = "http://api.bskcare.com/uploadMonitoringData_uploadTemperature.do";

		HashMap<String, String> parmas = new HashMap<String, String>();

		// FileUtils.readFileToByteArray(new File("c:/1.jpg"));
		// com.sun.org.apache.xerces.internal.impl.dv.util
		// String s = Base64.encode(FileUtils.readFileToByteArray(new
		// File("c:/11.jpg")));

		parmas.put("cid", "10001");
		parmas.put("mobile", "18612834872");
		// parmas.put("aid", "19");
		// parmas.put("params","{\"name\":\"新建活动3333\",\"pwd\":\"123456\",\"intro\":\"intro\",\"creator\":\"10001\"}");
		parmas.put("tp",
				"[{\"temperature\":38,\"testDateTime\":\"2015-01-15 14:21:45\"}]");
		// parmas.put("file", s);
		// parmas.put("fileName", "1.png");

		// System.out.println(url);

		// CrmResponse cr = HttpClientUtils.getCrmResponse(url, parmas);
		// System.out.println(cr.toString());

		// ddd2();
		ddd3();
		return;
	}

	public static void ddd2() {
		String url = "http://58.83.224.180:8090/facade/timeLine_updateEvaluatingResult.do";

		// resultstr=&source=bsksugar-28&cid=189431
		HashMap<String, String> parmas = new HashMap<String, String>();

		parmas.put(
				"resultstr",
				"{\"age\":28,\"birthday\":\"1987-2-27\",\"bloodPressure\":1,\"complications\":\"\",\"diseaseYears\":4,\"gad\":3,\"gender\":0,\"gestation\":0,\"height\":177,\"isEat\":0,\"isFhistory\":1,\"isShistory\":0,\"isSport\":1,\"lipids\":1,\"physicalType\":1,\"probability\":80.0,\"result\":\"疑似2型糖尿病\",\"sleep\":1,\"sugarValue\":4,\"waistline\":87,\"weight\":60}");
		parmas.put("source", "bsksugar-28");
		parmas.put("cid", "189431");
		parmas.put("mobile", "15288940982");

		CrmResponse cr = HttpClientUtils.getCrmResponse(url, parmas);
		System.out.println(cr.toString());
	}

	public static void ddd3() {
		
		String a = StringEscapeUtils.escapeJava("呵呵");
		System.out.println(a);
		String b = StringEscapeUtils.unescapeJava(a);
		System.out.println(b);
		
		String s = StringEscapeUtils.unescapeJava("\\u611f\\u5192");
		System.out.println(s);
	}

}
