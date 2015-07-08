package com.bskcare.ch.util;

import static com.bskcare.ch.util.StringUtils.isEmpty;

import org.apache.log4j.Logger;

/**
 * 时间轴公用类
 */
public class TimelineUtils {

	protected static final Logger log = Logger.getLogger(TimelineUtils.class);

	/**
	 * 如果用户填写单个合并症返回单个合并症，如果是多个者返回10 10表示多个合并症
	 * @param complications 用户评测结果的合并症
	 * @return
	 */
	public static int getComplication(String complications) {
		int complication = 0;
		if (!isEmpty(complications)) {
			String[] strs = complications.split(",");
			if (strs.length > 1) {
				complication = 10;// 多种并发症
			} else {
				complication = Integer.parseInt(strs[0]);
			}
		}
		return complication;
	}
	
	public static void main(String[] args) {
		
	}
}
