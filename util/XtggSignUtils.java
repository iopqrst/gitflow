package com.bskcare.ch.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

/**
 * 血糖高管 sign utils
 */
public class XtggSignUtils {

	protected static final Logger log = Logger.getLogger(XtggSignUtils.class);

	private static final String XTGG_MIXED_KEY = "6M4bcf5f1eebb20813647dduzop8b42cf1451919df3010cc37157Z###==";

	/**
	 * 获取血糖高管签名
	 * 
	 * @param paramMap
	 *            要计算的map
	 * @return 签名
	 */
	public static String getXtggSign(Map<String, String> paramMap) {
		return getXtggSign(paramMap, null, XTGG_MIXED_KEY);
	}

	/**
	 * 获取血糖高管签名
	 * 
	 * @param paramMap
	 *            要计算的map
	 */
	public static String getXtggSign(Map<String, String> paramMap,
			String[] excludes) {
		return getXtggSign(paramMap, excludes, XTGG_MIXED_KEY);
	}

	/**
	 * 获取血糖高管签名
	 * 
	 * @param paramMap
	 *            要计算的map
	 * @param excludes
	 *            排除map中的某些值
	 * @param mixedKey
	 *            混合计算加入的关键字
	 * @return 签名
	 */
	public static String getXtggSign(Map<String, String> paramMap,
			String[] excludes, String mixedKey) {

		if (null != paramMap && paramMap.size() > 0) {
			StringBuilder stringBuilder = new StringBuilder();

			// 对参数名进行字典排序
			String[] keyArray = paramMap.keySet().toArray(new String[0]);
			Arrays.sort(keyArray);

			// 拼接有序的参数名-值串
			stringBuilder.append(mixedKey);

			for (String key : keyArray) {

				if (null != excludes && excludes.length > 0) {
					if (ArrayUtils.contains(excludes, key)) {
						log.info(">>>>>>>>>>> 排除: key = " + key);
						continue;
					}
				}

				stringBuilder.append(key).append(paramMap.get(key));
			}

			String codes = stringBuilder.toString();

			// SHA-1编码，
			// 这里使用的是Apache-codec，即可获得签名(shaHex()会首先将中文转换为UTF8编码然后进行sha1计算，使用其他的工具包请注意UTF8编码转换)
			String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(
					codes).toUpperCase();
			return sign;
		}

		return "";
	}

	public static boolean isVerify(String sign, Map<String, String> paramMap) {
		return isVerify(sign, paramMap, null, XTGG_MIXED_KEY);
	}

	/**
	 * 验证签名是否有效
	 * 
	 * @param paramMap
	 *            要计算的map
	 */
	public static boolean isVerify(String sign, Map<String, String> paramMap,
			String[] excludes) {
		return isVerify(sign, paramMap, excludes, XTGG_MIXED_KEY);
	}

	/**
	 * 验证签名是否有效
	 */
	public static boolean isVerify(String sign, Map<String, String> paramMap,
			String[] excludes, String mixedKey) {

		boolean isXtggSign = false;

		if (!StringUtils.isEmpty(sign)) {
			String nsign = getXtggSign(paramMap, excludes, mixedKey);

			if (nsign.equals(sign)) {
				isXtggSign = true;
			} else {
				log.warn("-------------> 根据参数计算出的sign与参数中的不同, nsign=" + nsign
						+ ", sign=" + sign);
			}
		}

		return isXtggSign;
	}

	public static void main(String[] args) throws Exception {

		String mixedKey = "5589931241"; // 固定字符串

		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("city", "上海");
		paramMap.put("latitude", "31.21524");
		paramMap.put("longitude", "121.420033");
		paramMap.put("category", "美食");
		paramMap.put("region", "长宁区");
		paramMap.put("limit", "20");
		paramMap.put("radius", "2000");
		paramMap.put("offset_type", "0");
		paramMap.put("has_coupon", "1");
		paramMap.put("has_deal", "1");
		paramMap.put("keyword", "泰国菜");
		paramMap.put("sort", "7");
		paramMap.put("format", "json");

		String[] excludes = new String[] { "keyword", "format" };
		String sign = getXtggSign(paramMap, excludes, XTGG_MIXED_KEY);
		System.out.println(sign);
		boolean b = isVerify(sign, paramMap, excludes);
		System.out.println(b);

		System.out.println("-------v2----------");

		v2();
	}

	public static void v2() {
		String s = "{\"pid\":3,\"type\":0,\"payment\":0,\"payMethod\":0,\"methodKey\":\"XTGG\"}";

		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("params", s);
		paramMap.put("cid", "10001");
		paramMap.put("mobile", "18701046848");
		paramMap.put("payMethod", "0");
		paramMap.put("methodKey", "XTGG_CLIENT_RECHARGE");

		String sign = getXtggSign(paramMap);
		System.out.println(sign);

	}
}
