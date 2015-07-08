package com.bskcare.ch.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 简易报告和3个月报告共用方法类
 */
public class RptUtils {

	protected static final Logger log = Logger.getLogger(RptUtils.class);

	/** 靶心率 （ 170 - age） */
	public static final int TARGET_HEART_RATE = 0;
	/** 最适(大)心率 最适心率=（220-年龄-安静心率）X75%+安静心率 */
	public static final int BEST_HEART_RATE = 1;
	/** 最小心率 （170 - age) * 0.55 */
	public static final int MIN_HEART_REATE = 2;

	/**
	 * 根据身高和体重计算BMI值
	 * 
	 * @param height
	 * @param weight
	 * @return double bmi 如果height或weight为空返回0
	 */
	public static double getBMI(String height, String weight) {
		// BMI
		if (!StringUtils.isEmpty(height) && !StringUtils.isEmpty(weight)) {
			try {
				double _height = Double.parseDouble(height);
				double _weight = Double.parseDouble(weight);

				double bmi = _weight / ((_height * _height) / 10000); // BMI
				// 计算公式：体重（kg）÷身高^2（m）

				return bmi;
			} catch (NumberFormatException e) {
				log.error("计算bmi失败，height或者width转化出现问题");
			}
		}
		return 0;
	}

	/**
	 * 保存两位小数的bmi
	 * 
	 * @param height
	 * @param weight
	 * @return 返回String类型
	 */
	public static String getStringBMI(String height, String weight) {
		return MoneyFormatUtil.formatToMoney(getBMI(height, weight), null);
	}

	/**
	 * 保存两位小数的bmi
	 * 
	 * @param height
	 * @param weight
	 * @return 返回double类型
	 */
	public static double getDoubleBMI(String height, String weight) {
		return MoneyFormatUtil.formatDouble(getBMI(height, weight));
	}

	/**
	 * 根据bmi获取相应的tags
	 * 
	 * @param bmi
	 * @reurn bmitag 偏瘦、正常、超重、轻度肥胖、中度肥胖、重度肥胖或者""
	 */
	public static String getBMITag(String bmi) {
		if (!StringUtils.isEmpty(bmi)) {
			try {
				float _bmi = Float.parseFloat(bmi);
				if (_bmi < 18.9) {
					return "偏瘦";
				} else if (_bmi >= 19 && _bmi < 24) {
					return "正常";
				} else if (_bmi >= 24 && _bmi < 28) {
					return "超重";
				} else if (_bmi >= 28 && _bmi < 30) {
					return "轻度肥胖";
				} else if (_bmi >= 30 && _bmi < 35) {
					return "中度肥胖";
				} else if (_bmi >= 35) {
					return "重度肥胖";
				}
			} catch (NumberFormatException e) {
				log.error("返回bmi tag失败 ，bmi格式存在问题");
			}
		}
		return "";
	}

	/**
	 * 根据用户年龄、平均心率
	 * 
	 * @param age
	 * @param averageHeartRate
	 * @param type
	 *            0 = （170 - age) | else （220 - age - average) * 0.75 + average
	 * @return MAX_HEART_RATE | MIN_HEART_RATE
	 */
	public static String getUserHeartRate(Integer age, String averageHeartRate,
			int type) {
		if (null != age) {
			if (TARGET_HEART_RATE == type) {// 靶心率 170 - age
				return "" + (170 - age);
			} else if (BEST_HEART_RATE == type) { // 最适心率 Math.round((220-age-
				// average) * 0.75 + average);
				if (!StringUtils.isEmpty(averageHeartRate)) {
					try {
						int average = Integer.parseInt(averageHeartRate);
						return ""
								+ Math.round((220 - age - average) * 0.75
										+ average);
					} catch (NumberFormatException e) {
						// e.printStackTrace();
						return "";
					}
				}
			} else if (MIN_HEART_REATE == type) {// 最小心率
				return "" + Math.round((220 - age) * 0.55);
			}
		}
		return "";
	}

	/**
	 * 获取每天需要饮水量
	 * 
	 * @param type
	 *            膳食报告类型 0：非运动膳食 1：运动膳食
	 * @return 返回每日所需饮水量或者空“”
	 */
	public static String getDailyWater(String height) {
		String ml = "";
		if (!StringUtils.isEmpty(height)) {
			double _height = 0;
			try {
				_height = Double.parseDouble(height); // 身高

				if (_height > 170) { // 170以上饮水1600--2000
					ml = "1600--2000";
				} else if (_height > 160 && _height <= 170) {
					ml = "1500--1700";// 160-170饮水1500—1700
				} else if (_height > 150 && _height <= 160) {
					ml = "1400--1600";// 150—160饮水1400—1600
				} else if (_height <= 150) {
					ml = "1200--1400";// 150以下饮用水1200—1400
				}

			} catch (NumberFormatException e) {
				// e.printStackTrace();
				return ml;
			}
		}
		return ml;
	}

	/**
	 * 根据用户bmi和体力活动获取计算热卡的基数
	 * 
	 * @param bmi
	 * @param physicalType
	 *            体力活动 1：极轻体力活动 2：轻体力活动 3：中等体力活动 4：重体力活动
	 */
	public static int getCalCardinality(String bmi, int physicalType) {
		int cardinality = 0;
		if (!StringUtils.isEmpty(bmi) && physicalType > 0) {
			double _bmi = Double.parseDouble(bmi);

			if (_bmi < 18.5) { // 消瘦
				if (physicalType == 1) {
					cardinality = 30;
				} else if (physicalType == 2) {
					cardinality = 35;
				} else if (physicalType == 3) {
					cardinality = 40;
				} else if (physicalType == 4) {
					cardinality = 42;
				}
			} else if (_bmi >= 18.5 && _bmi <= 24) { // 正常
				if (physicalType == 1) {
					cardinality = 23;
				} else if (physicalType == 2) {
					cardinality = 30;
				} else if (physicalType == 3) {
					cardinality = 35;
				} else if (physicalType == 4) {
					cardinality = 40;
				}
			} else if (_bmi > 24 && _bmi <= 28) { // 肥胖
				if (physicalType == 1) {
					cardinality = 18;
				} else if (physicalType == 2) {
					cardinality = 23;
				} else if (physicalType == 3) {
					cardinality = 30;
				} else if (physicalType == 4) {
					cardinality = 35;
				}
			} else if (_bmi > 28) { // 超重
				if (physicalType == 1) {
					cardinality = 18;
				} else if (physicalType == 2) {
					cardinality = 23;
				} else if (physicalType == 3) {
					cardinality = 30;
				} else if (physicalType == 4) {
					cardinality = 35;
				}
			}
		}
		return cardinality;
	}

	/**
	 * 每天需要消耗热卡
	 * 
	 * @param height
	 * @param age
	 * @param gender
	 *            0：男 1：女
	 * @param cardinality
	 *            体力计算基数
	 * @return 每天需要消耗热卡
	 */
	public static double getDailyCalorie(String height, Integer age,
			Integer gender, int cardinality) {

		// 60岁以上的男性：（身高-100）*0.9*(轻体力劳动者30)
		// 60岁以上的女性（身高-105）*0.92*(轻体力劳动者30)
		// 60岁以下（身高-105）*(轻体力劳动者30)
		double _calorie = 0;
		if (!StringUtils.isEmpty(height) && null != age && null != gender) {

			double _height = 0;

			try {
				_height = Double.parseDouble(height); // 身高

				if (age >= 60) {// 60岁以上
					if (0 == gender) {// 0:男 1:女
						_calorie = (_height - 100) * 0.9 * cardinality;
					}

					if (1 == gender) {
						_calorie = (_height - 105) * 0.92 * cardinality;
					}
				} else {// 60岁以下
					_calorie = (_height - 105) * cardinality;
				}
			} catch (NumberFormatException e) {
				// e.printStackTrace();
				return 0;
			}
		}

		return Math.round(_calorie);
	}

	/**
	 * 根据腰围和臀围计算腰臀比
	 * @return 返回double类型
	 */
	private static double getWB(String waist, String breech) {
		// BMI
		if (!StringUtils.isEmpty(waist)
				&& !StringUtils.isEmpty(waist)) {
			try {
				double _waist = Double.parseDouble(waist);
				double _breech = Double.parseDouble(breech);

				double wb = _waist / _breech; // wb

				return wb;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	/**
	 * 保存两位小数的wb
	 * @return 返回double类型
	 */
	public static double getDoubleWb(String waist, String breech) {
		return MoneyFormatUtil.formatDouble(getWB(waist, breech));
	}

	public static void main(String[] args) {
		System.out.println(Math.round(0));
	}
}
