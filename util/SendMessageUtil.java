package com.bskcare.ch.message.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bskcare.ch.util.Client;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.Message;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;

@SuppressWarnings("unchecked")
public class SendMessageUtil {

	private final static Logger log = Logger.getLogger(SendMessageUtil.class);

	public static String SMS(String postData, String postUrl) {
		try {
			// 发送POST请求
			URL url = new URL(postUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);
			conn.setDoOutput(true);

			conn.setRequestProperty("Content-Length", "" + postData.length());
			OutputStreamWriter out = new OutputStreamWriter(conn
					.getOutputStream(), "UTF-8");
			out.write(postData);
			out.flush();
			out.close();

			// 获取响应状态
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("connect failed!");
				return "";
			}
			// 获取响应内容体
			String line, result = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(conn
					.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return "";
	}

	// 发送消息
	/**
	 * @param count
	 *            发送条数【one: 单条 more: 多条】
	 */
	public static String sendMessage(String mobile, String content, String count)
			throws UnsupportedEncodingException {
		String pid = SystemConfig.getString("oneSprdid");

		if (!StringUtils.isEmpty(count) && count.equals("more")) {
			pid = SystemConfig.getString("moreSprdid");
		}

		if (!StringUtils.isEmpty(count) && count.equals("ad")) {
			pid = "1012812";
		}
		String state = "";

		if("1012838".equals(pid)) {
			String result = tempSendMsg(mobile, content);
			System.out.println("pid = " + result);
			return "0";
		}
		
		if("1012848".equals(pid)) {
			
			return "0";
		}
		
		String sname = SystemConfig.getString("sname");
		String spwd = SystemConfig.getString("spwd");

		String PostData = "sname=" + sname + "&spwd=" + spwd
				+ "&scorpid=&sprdid=" + pid + "&sdst=" + mobile + "&smsg="
				+ java.net.URLEncoder.encode("" + content + "", "utf-8");

		String result = SMS(PostData,
				"http://cf.lmobile.cn/submitdata/Service.asmx/g_Submit");
		log.info(">>>>>>>>>> pid = "+ pid +"， 短信发送响应信息：" + result);
		if (!StringUtils.isEmpty(result)) {
			String xmlStr = JsonUtils.XmlStrToJsonStr(result);
			Map<String, String> map = JsonUtils.getMap4Json(xmlStr);
			// 如果State返回0，说明发送成功
			state = map.get("State");
			
			int one = getRemain("one");
			int more = getRemain("more");
			int ad = getRemain("ad");
			if(one == 500 || more == 500){
				mobile = "18612834872";
				content = "短信剩余条数不足【血糖高管】";
				String postData = "sname=" + sname + "&spwd=" + spwd
				+ "&scorpid=&sprdid=" + pid + "&sdst=" + mobile + "&smsg="
				+ java.net.URLEncoder.encode("" + content + "", "utf-8");

				String resultData = SMS(postData,
						"http://cf.lmobile.cn/submitdata/Service.asmx/g_Submit");
				log.info(">>>>>>>>>> 短信剩余条数不足提醒：" + resultData);
			}
		} else {
			state = "-1";
		}
		
		
		System.out.println(state);
		return state;
	}

	// 查询剩余短信条数
	public static int getRemain(String source) {
		String pid = SystemConfig.getString("oneSprdid");
		if (!StringUtils.isEmpty(source) && source.equals("more")) {
			pid = SystemConfig.getString("moreSprdid");
		}
		if (!StringUtils.isEmpty(source) && source.equals("ad")) {
			pid = "1012812";
		}
		int remain = 0;
		String sname = SystemConfig.getString("sname");
		String spwd = SystemConfig.getString("spwd");
		String PostData = "sname=" + sname + "&spwd=" + spwd
				+ "&scorpid=&sprdid=" + pid + "";
		String result = SMS(PostData,
				"http://cf.lmobile.cn/submitdata/Service.asmx/Sm_GetRemain");
		if (!StringUtils.isEmpty(result)) {
			String xmlStr = JsonUtils.XmlStrToJsonStr(result);
			Map<String, String> map = JsonUtils.getMap4Json(xmlStr);
			String state = map.get("State");
			if (!StringUtils.isEmpty(state) && state.equals("0")) {
				remain = Integer.parseInt(map.get("Remain"));
				// System.out.println(remain);
			}
		} else {
			System.out.println("fail");
		}
		return remain;
	}
	
	public static void sendToMe(String content) {
		try {
			String msg = sendMessage("18612834872", content , "one");
			System.out.println(msg);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			log.error(e.getMessage());
		}
	}
	
	private static String tempSendMsg(String mobile, String content) {
		Client www = null;
		try {
			www = new Client("SDK-BBX-010-15672", "880124");
		} catch (UnsupportedEncodingException e) {
			System.out.println("获取发送器失败");
			e.printStackTrace();
		}

		String state = www.mdSmsSend_u(mobile,
				content + Message.getString("sms_prefix"),
				"", "", "");
		System.out.println("临时短信验证码通道：" + state);
		
		return state;
	}

	public static void main(String[] args) throws UnsupportedEncodingException{
		
		//sendToMe("您的短信验证码为3025，非本人操作请忽略。【血糖高管】");
		
		//tempSendMsg("18612834872", "恭喜您获得血糖高管开心体验卡，点左上角菜单进行升级，使用激活码201409230209197034按提示升级！");
		
		System.out.println("one--->" + getRemain("one"));
		System.out.println("more--->" + getRemain("more"));
		System.out.println("ad--->" + getRemain("ad"));
//		
//		String mobile = "15800538359";
//		String content = "恭喜您获得血糖高管开心体验卡，点左上角菜单进行升级，使用激活码201409230209197034按提示升级！【血糖高管】";
//		sendMessage(mobile, content, "one");
	}
}
