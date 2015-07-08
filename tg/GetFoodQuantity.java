package com.bskcare.ch.tg;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.MoneyFormatUtil;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.tg.TgCookedFood;

public class GetFoodQuantity {

	// 判断生成的主食是否有重复的
	public TgCookedFood getWuWanZhushi(TgCookedFood foodZhushiZao,
			TgCookedFood foodZhushiWu, String canci, PickerCondition pc,
			List<TgCookedFood> lstCookedFood) {
		TgCookedFood cookedFood = new TgCookedFood();
		if (canci.equals(TgCookedFood.CANCI_LUNCH)) {
			TgCookedFood zhushiWu = getZhushiInfo(TgCookedFood.CANCI_LUNCH,
					lstCookedFood, -1);
			if (!foodZhushiZao.getName().equals(zhushiWu.getName())) {
				cookedFood = zhushiWu;
				return cookedFood;
			} else {
				return getWuWanZhushi(foodZhushiZao, foodZhushiWu, canci, pc,
						lstCookedFood);
			}
		} else if (canci.equals(TgCookedFood.CANCI_DINNER)) {
			TgCookedFood zhushiWan = getZhushiInfo(TgCookedFood.CANCI_DINNER,
					lstCookedFood, -1);
			if (!foodZhushiZao.getName().equals(zhushiWan.getName())
					&& !foodZhushiWu.getName().equals(zhushiWan.getName())) {
				cookedFood = zhushiWan;
				return cookedFood;
			} else {
				return getWuWanZhushi(foodZhushiZao, foodZhushiWu, canci, pc,
						lstCookedFood);
			}
		}

		return null;
	}

	public List<String> getZhushi(PickerCondition pc, int pickerType,
			List<TgCookedFood> lstCookedFood) {
		boolean hasZou = false;
		boolean hasShu = false;
		List<String> lstZhushi = new ArrayList<String>();

		// 早餐的主食
		String zaocanZhushi = "";
		// 午餐的主食
		String wucanZhushi = "";
		// 晚餐的主食
		String wancanZhushi = "";

		int index = -1;
		// 早餐的主食
		TgCookedFood foodZhushiZao = getZhushiInfo(
				TgCookedFood.CANCI_BREAKFAST, lstCookedFood, index);
		// 午餐的主食
		// TgCookedFood foodZhushiWu = getWuWanZhushi(foodZhushiZao, null,
		// TgCookedFood.CANCI_LUNCH, pc, lstCookedFood);
		TgCookedFood foodZhushiWu = getZhushiInfo(TgCookedFood.CANCI_LUNCH,
				lstCookedFood, index);

		// 晚餐的主食
		// TgCookedFood foodZhushiWan = getWuWanZhushi(foodZhushiZao,
		// foodZhushiWu, TgCookedFood.CANCI_DINNER, pc, lstCookedFood);
		TgCookedFood foodZhushiWan = getZhushiInfo(TgCookedFood.CANCI_DINNER,
				lstCookedFood, index);

		if ((pickerType != 1 && pickerType != 2) && pc.isHasGruel()) {
			hasZou = true;
		}

		if (foodZhushiZao != null && foodZhushiWu != null
				&& foodZhushiWan != null) {
			// 有薯类
			if (foodZhushiZao.getFoodType().contains("薯")
					|| foodZhushiWu.getFoodType().contains("薯")
					|| foodZhushiWan.getFoodType().contains("薯")) {
				hasShu = true;
			}

			zaocanZhushi = getZhushiDetail(TgCookedFood.CANCI_BREAKFAST,
					hasZou, hasShu, pc, foodZhushiZao);
			lstZhushi.add(zaocanZhushi);
			wucanZhushi = getZhushiDetail(TgCookedFood.CANCI_LUNCH, hasZou,
					hasShu, pc, foodZhushiWu);
			lstZhushi.add(wucanZhushi);
			wancanZhushi = getZhushiDetail(TgCookedFood.CANCI_DINNER, hasZou,
					hasShu, pc, foodZhushiWan);
			lstZhushi.add(wancanZhushi);
		}

		return lstZhushi;
	}

	public String getZhushiDetail(String canci, boolean hasZou, boolean hasShu,
			PickerCondition pc, TgCookedFood cookedFood) {
		double calories = pc.getCalories();
		double zhushixishu = pc.getZhushixishu();
		String zhushi = "";
		List<String> lstZhushi = new ArrayList<String>();

		List<String> lstZhushiNoodles = new ArrayList<String>();
		List<String> lstZhushiManTou = new ArrayList<String>();
		List<String> lstZhushiMi = new ArrayList<String>();
		List<String> lstZhushiShu = new ArrayList<String>();
		// 主食类型种数
		int zhushiTypeCount = 0;
		// 主食的总量
		int zhushiLiangZong = 0;

		if (cookedFood != null) {

			String[] foods = cookedFood.getFood().split("、");
			String[] foodTypes = cookedFood.getFoodType().split("、");
			// 每餐所占的比例
			int bili = 1;
			// 有薯类是的比例
			double shuBili = 1;
			// int hasZhouXishu = 0;

			if (!StringUtils.isEmpty(canci)) {
				// 没有粥，没有薯类
				if (hasZou == false && hasShu == false) {
					if (canci.equals(TgCookedFood.CANCI_BREAKFAST)) {
						bili = 1;
					} else if (canci.equals(TgCookedFood.CANCI_LUNCH)) {
						bili = 1;
					} else {
						bili = 1;
					}
					shuBili = 1;
				} else if (hasZou == false && hasShu == true) {// 没有粥，有薯类
					if (canci.equals(TgCookedFood.CANCI_BREAKFAST)) {
						bili = 1;
					} else if (canci.equals(TgCookedFood.CANCI_LUNCH)) {
						bili = 1;
					} else {
						bili = 1;
					}
					shuBili = 0.67;
					for (int i = 0; i < foodTypes.length; i++) {
						if (foodTypes[i].contains("薯")) {
							lstZhushiShu.add(foods[i]);
						}
					}
				}
				for (int i = 0; i < foodTypes.length; i++) {
					if (foodTypes[i].contains("面条")) {
						lstZhushiNoodles.add(foods[i]);
					}
					if (foodTypes[i].contains("馒头")) {
						lstZhushiManTou.add(foods[i]);
					} else if (foodTypes[i].contains("米")) {
						lstZhushiMi.add(foods[i]);
					}
				}
				if (!CollectionUtils.isEmpty(lstZhushiNoodles)) {
					zhushiTypeCount += 1;
				}

				if (!CollectionUtils.isEmpty(lstZhushiManTou)) {
					zhushiTypeCount += 1;
				}

				if (!CollectionUtils.isEmpty(lstZhushiMi)) {
					zhushiTypeCount += 1;
				}

				if (!CollectionUtils.isEmpty(lstZhushiShu)) {
					zhushiTypeCount += 1;
				}

				if (zhushiTypeCount > 0) {
					if (!CollectionUtils.isEmpty(lstZhushiNoodles)) {
						int liang = Integer.parseInt(MoneyFormatUtil
								.formatToMoney((calories * zhushixishu - 180)
										* bili / 3 * shuBili * 1 / 4 * 5 / 4
										* 4 / 3 * 1 / zhushiTypeCount,
										new DecimalFormat("#0")));
						String zhushiDetail = ArrayUtils.join(lstZhushiNoodles
								.toArray(), "，");
						lstZhushi.add(zhushiDetail + liang + "g");
						zhushiLiangZong += liang;
					}

					if (!CollectionUtils.isEmpty(lstZhushiManTou)) {
						int liang = Integer.parseInt(MoneyFormatUtil
								.formatToMoney((calories * zhushixishu - 180)
										* bili / 3 * shuBili * 1 / 4 * 5 / 4
										* 1.5 * 1 / zhushiTypeCount,
										new DecimalFormat("#0")));
						String zhushiDetail = ArrayUtils.join(lstZhushiManTou
								.toArray(), "，");
						lstZhushi.add(zhushiDetail + liang + "g");
						zhushiLiangZong += liang;
					}

					if (!CollectionUtils.isEmpty(lstZhushiMi)) {
						int liang = Integer.parseInt(MoneyFormatUtil
								.formatToMoney((calories * zhushixishu - 180)
										* bili / 3 * shuBili * 1 / 4 * 5 / 4
										* 2.6 * 1 / zhushiTypeCount,
										new DecimalFormat("#0")));
						String zhushiDetail = ArrayUtils.join(lstZhushiMi
								.toArray(), "，");
						lstZhushi.add(zhushiDetail + liang + "g");
						zhushiLiangZong += liang;
					}

					if (!CollectionUtils.isEmpty(lstZhushiShu)) {
						int liang = Integer.parseInt(MoneyFormatUtil
								.formatToMoney((calories * zhushixishu - 180)
										* bili / 3 * 1 / 3 / 0.8 * 1
										/ zhushiTypeCount, new DecimalFormat(
										"#0")));
						String zhushiDetail = ArrayUtils.join(lstZhushiShu
								.toArray(), "，");
						lstZhushi.add(zhushiDetail + liang + "g");
						zhushiLiangZong += liang;
					}
				}

				if (!CollectionUtils.isEmpty(lstZhushi)) {
					String name = cookedFood.getName();
					String zhushiDetail = ArrayUtils.join(lstZhushi.toArray(),
							"、");
					if (hasShu == true) {
						zhushi = name + zhushiLiangZong + "g（" + zhushiDetail
								+ "）";
					} else {
						zhushi = name + zhushiLiangZong + "g";
					}
				}
			}
		}
		return zhushi;
	}

	// 查询适合的主食
	public TgCookedFood getZhushi(String canci, PickerCondition pc) {
		TgCookedFood zhushiZao = new TgCookedFood();
		zhushiZao.setCanci(canci);
		zhushiZao.setType(TgCookedFood.TYPE_ZHUSHI);
		TgCookedFood foodZhushi = null;
		if (canci.equals(TgCookedFood.CANCI_DINNER)) {
			// TODO蛋、豆制品
			foodZhushi = pc.getCookedFoodDao().queryCookedFood(zhushiZao, null,
					null, "米（粗）、薯（粗）、馒头（粗）、面条（粗）", "and", pc.getDisease());
		} else {
			foodZhushi = pc.getCookedFoodDao().queryCookedFood(zhushiZao, null,
					null, null, null, pc.getDisease());
		}

		return foodZhushi;
	}

	// 查询适合的主食
	public TgCookedFood getZhushiInfo(String canci,
			List<TgCookedFood> lstCookedFood, int index) {
		for (int i = index + 1; i < lstCookedFood.size(); i++) {
			String foodType = lstCookedFood.get(i).getFoodType();
			String foodCanci = lstCookedFood.get(i).getCanci();

			if (canci.equals(TgCookedFood.CANCI_DINNER)) {
				if (foodCanci.contains(canci)
						&& lstCookedFood.get(i).getType().equals(
								TgCookedFood.TYPE_ZHUSHI)
						&& (!foodType.contains("米（粗）")
								&& !foodType.contains("薯（粗）")
								&& !foodType.contains("馒头（粗）") && !foodType
								.contains("面条（粗）"))) {
					index = i;
					return lstCookedFood.get(i);
				}
			} else {
				if (foodCanci.contains(canci)
						&& lstCookedFood.get(i).getType().equals(
								TgCookedFood.TYPE_ZHUSHI)) {
					index = i;
					return lstCookedFood.get(i);
				}
			}
		}
		return null;
	}

	// 获取除主食以外其他的早餐信息
	public String getZaocanOther(PickerCondition pc, int pickerType,
			List<TgCookedFood> lstCookedFood) {
		double calories = pc.getCalories();
		double douxishu = pc.getDouxishu();
		String zaocanOther = "";
		List<String> lstZaocanOther = new ArrayList<String>();

		// 模式1 模式3 模式5中早餐有蛋（煮，羹）
		if (pickerType == 1 || pickerType == 3 || pickerType == 5) {
			// String foodEgg = getFoodEgg(pc);
			String foodEgg = "";
			for (TgCookedFood cookedFood : lstCookedFood) {
				if (cookedFood.getType().equals(TgCookedFood.TYPE_DAN)
						&& cookedFood.getCanci().contains(
								TgCookedFood.CANCI_BREAKFAST)) {
					String name = cookedFood.getName();
					if (!StringUtils.isEmpty(name)) {
						foodEgg = name + "50g";
					}
					break;
				}
			}
			if (!StringUtils.isEmpty(foodEgg)) {
				lstZaocanOther.add(foodEgg);
			}
		}

		TgCookedFood zaoSucai = null;
		// 获取早餐的素菜 模式1，2，3，5是无豆制品，模式4是豆制品的菜
		if (pickerType == 1 || pickerType == 2 || pickerType == 3
				|| pickerType == 5) {
			// 早餐的素菜
			zaoSucai = new TgCookedFood();
			// zaoSucai = getSucaiInfo(pc, TgCookedFood.CANCI_BREAKFAST);
			zaoSucai = getSuCai(TgCookedFood.CANCI_BREAKFAST, lstCookedFood);
		} else {
			zaoSucai = new TgCookedFood();
			// zaoSucai = getFoodDou(pc, TgCookedFood.CANCI_BREAKFAST);
			zaoSucai = getFoodDouInfo(TgCookedFood.CANCI_BREAKFAST,
					lstCookedFood);
		}

		TgCookedFood zaoTang = null;
		// TgCookedFood zaoZou = null;
		// 模式1 没有汤
		if (pickerType != 1) {
			// 早餐的汤
			if (pickerType == 2) {
				// zaoTang = getTangEgg(pc, true, TgCookedFood.CANCI_BREAKFAST);
				zaoTang = getTangEggInfo(true, TgCookedFood.CANCI_BREAKFAST,
						lstCookedFood);
			} else {
				// zaoTang = getTangEgg(pc,false,TgCookedFood.CANCI_BREAKFAST);
				zaoTang = getTangEggInfo(false, TgCookedFood.CANCI_BREAKFAST,
						lstCookedFood);
				// 如果不能有粥，就是汤
				/*
				 * if(pc.isHasGruel() == false){ zaoTang = getTangEgg(pc, false,
				 * TgCookedFood.CANCI_BREAKFAST); }else{ //反之，就在汤和粥之间随机选一种
				 * zaoZou = getZou(pc); }
				 */
			}
		}

		// 早餐素菜中的蔬菜信息
		List<String> lstSucai = new ArrayList<String>();
		// 早餐素菜中蔬菜的种类数量
		int sucaiCount = 0;
		// 早餐汤中蔬菜信息
		List<String> lstTangSucai = new ArrayList<String>();
		// 早餐汤中蔬菜的种类数量
		int tangSucaiCount = 0;
		// 汤中的蛋类
		List<String> lstTangEgg = new ArrayList<String>();
		// 汤的总量
		int tangZong = 0;
		// 早餐素菜中豆制品的信息
		// 豆腐丝的数量
		int doufusi = 0;
		// 豆干的数量
		int dougan = 0;
		// 豆腐的数量
		int doufu = 0;
		// 大豆的数量
		int dadou = 0;
		// 腐竹的数量
		int fuzhu = 0;
		// 豆制品的种类
		int douType = 0;

		if (zaoSucai != null) {
			String[] foodsSucai = zaoSucai.getFood().split("、");
			String[] foodTypesSucai = zaoSucai.getFoodType().split("、");
			if (foodTypesSucai != null && foodTypesSucai.length > 0) {
				for (int i = 0; i < foodTypesSucai.length; i++) {
					if (foodTypesSucai[i].equals("菜")) {
						sucaiCount += 1;
						lstSucai.add(foodsSucai[i]);
					} else if (foodTypesSucai[i].equals("豆制品")) {
						if (foodsSucai[i].equals("豆腐丝")) {
							doufusi += 1;
						} else if (foodsSucai[i].equals("豆干")) {
							dougan += 1;
						} else if (foodsSucai[i].equals("豆腐")) {
							doufu += 1;
						} else if (foodsSucai[i].equals("大豆")) {
							dadou += 1;
						} else if (foodsSucai[i].equals("腐竹")) {
							fuzhu += 1;
						}
					}
				}
			}
		}

		if (zaoTang != null) {
			String[] foodsTang = zaoTang.getFood().split("、");
			String[] foodTypesTang = zaoTang.getFoodType().split("、");
			if (foodTypesTang != null && foodTypesTang.length > 0) {
				for (int i = 0; i < foodTypesTang.length; i++) {
					if (foodTypesTang[i].equals("菜")) {
						tangSucaiCount += 1;
						lstTangSucai.add(foodsTang[i]);
					} else if (foodTypesTang[i].equals("蛋")) {
						lstTangEgg.add(foodsTang[i]);
					}
				}
			}
		}

		// 素菜的总量
		int sucaiLiangZong = 0;
		// 素菜中蔬菜的种类和汤中蔬菜种类的总量
		int sucaiTypeCount = sucaiCount + tangSucaiCount;
		// 每一种蔬菜的量
		int everySucai = Integer.parseInt(MoneyFormatUtil.formatToMoney(
				200 / sucaiTypeCount, new DecimalFormat("#0")));

		if (!CollectionUtils.isEmpty(lstTangSucai)) {
			if (lstTangSucai.contains("紫菜")) {
				everySucai = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						(200 - 3) / (sucaiTypeCount - 1), new DecimalFormat(
								"#0")));
			}
		}

		List<String> lstSucaiDetail = new ArrayList<String>();

		if (!CollectionUtils.isEmpty(lstSucai)) {
			int liang = everySucai * sucaiCount;
			String sucai = ArrayUtils.join(lstSucai.toArray(), "、");
			// zaocanOther += zaoSucai.getName()+liang+"g（"+sucai+liang+"g）";
			if (!StringUtils.isEmpty(sucai)) {
				if (lstSucai.size() == 1) {
					lstSucaiDetail.add(sucai + liang + "g");
				} else {
					lstSucaiDetail.add(sucai + "共" + liang + "g");
				}
				sucaiLiangZong += liang;
			}
		}

		if (doufusi > 0) {
			douType += 1;
		}
		if (dougan > 0) {
			douType += 1;
		}
		if (doufu > 0) {
			douType += 1;
		}
		if (dadou > 0) {
			douType += 1;
		}
		if (fuzhu > 0) {
			douType += 1;
		}

		if (douType > 0) {
			if (doufusi > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 50 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("豆腐丝" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (dougan > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 50 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("豆干" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (doufu > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 100 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("豆腐" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (dadou > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 25 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("大豆" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (fuzhu > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 25 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("腐竹" + liang + "g");
				sucaiLiangZong += liang;
			}
		}

		if (!CollectionUtils.isEmpty(lstSucaiDetail)) {
			String sucai = ArrayUtils.join(lstSucaiDetail.toArray(), "，");
			String name = zaoSucai.getName();
			if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(sucai)) {
				// zaocanOther += name+sucaiLiangZong+"g（"+sucai+"）";
				lstZaocanOther.add(name + sucaiLiangZong + "g（" + sucai + "）");
			}
		}

		List<String> lstTangDetail = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(lstTangSucai)) {
			if (lstTangSucai.contains("紫菜")) {
				int liang = everySucai * (tangSucaiCount - 1);
				List<String> lstTangHaszicai = new ArrayList<String>();
				for (String tangSucai : lstTangSucai) {
					if (!tangSucai.equals("紫菜")) {
						lstTangHaszicai.add(tangSucai);
					}
				}
				if (!CollectionUtils.isEmpty(lstTangHaszicai)) {
					String sucai = ArrayUtils.join(lstTangHaszicai.toArray(),
							"、");
					lstTangDetail.add(sucai + liang + "g");
					lstTangDetail.add("紫菜3g");
					tangZong += liang + 3;
				} else {
					lstTangDetail.add("紫菜3g");
					tangZong += 3;
				}
			} else {
				int liang = everySucai * tangSucaiCount;
				String sucai = ArrayUtils.join(lstTangSucai.toArray(), "、");
				if (!StringUtils.isEmpty(sucai)) {
					lstTangDetail.add(sucai + liang + "g");
					tangZong += liang;
				}
			}
		}

		// 模式1有豆浆、豆腐脑 其他模式没有
		if (pickerType == 1) {
			String foodDouJiang = getDou();
			if (!StringUtils.isEmpty(foodDouJiang)) {
				// zaocanOther += foodDouJiang;
				lstZaocanOther.add(foodDouJiang);
			}
		}

		if (!CollectionUtils.isEmpty(lstTangEgg)) {
			String egg = ArrayUtils.join(lstTangEgg.toArray(), "、");
			int liang = 0;
			if (pickerType == 1) {
				if (lstZaocanOther.contains("豆腐脑")) {
					liang = Integer
							.parseInt(MoneyFormatUtil
									.formatToMoney((calories * douxishu / 4 * 3
											/ 5 * 1 / 4 - 45) * 60 / 9,
											new DecimalFormat("#0")));
				} else if (lstZaocanOther.contains("豆浆")) {
					liang = Integer
							.parseInt(MoneyFormatUtil
									.formatToMoney((calories * douxishu / 4 * 3
											/ 5 * 1 / 4 - 35) * 60 / 9,
											new DecimalFormat("#0")));
				}
			} else {
				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(calories
						* douxishu / 4 * 3 / 5 * 1 / 4 * 60 / 9,
						new DecimalFormat("#0")));
			}
			lstTangDetail.add(egg + liang + "g");
			tangZong += liang;
		}

		if (!CollectionUtils.isEmpty(lstTangDetail)) {
			String tangDetail = ArrayUtils.join(lstTangDetail.toArray(), "，");
			// zaocanOther += zaoTang.getName()+tangZong+"g（"+tangDetail+"）";
			String name = zaoTang.getName();
			if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(tangDetail)) {
				lstZaocanOther.add(name + "（" + tangDetail + "）");
			}
		}

		/*
		 * if(zaoZou != null){ String name = zaoZou.getName();
		 * if(!StringUtils.isEmpty(name)){ //zaocanOther += name+"一碗";
		 * lstZaocanOther.add(name+"一碗（约250克）"); } }
		 */
		if (!CollectionUtils.isEmpty(lstZaocanOther)) {
			zaocanOther = ArrayUtils.join(lstZaocanOther.toArray(), "，");
		}

		return zaocanOther;
	}

	// 获取除主食之外的午餐信息
	public String getWucanOther(PickerCondition pc, String zaocan,
			int pickerType, List<TgCookedFood> lstCookedFood) {
		String wucanOther = "";
		double calories = pc.getCalories();
		double douxishu = pc.getDouxishu();

		List<String> lstWucanOther = new ArrayList<String>();
		// 获取适合午餐的素菜
		// 模式1，2，3，4 中的素菜是无豆制品 模式5中素菜是豆制品的
		TgCookedFood wucanSucai = null;
		if (pickerType == 1 || pickerType == 2 || pickerType == 3
				|| pickerType == 4) {
			// wucanSucai = getSucaiInfo(pc, TgCookedFood.CANCI_LUNCH);
			wucanSucai = getSuCai(TgCookedFood.CANCI_LUNCH, lstCookedFood);
		} else {
			// wucanSucai = getFoodDou(pc, TgCookedFood.CANCI_BREAKFAST);
			wucanSucai = getFoodDouInfo(TgCookedFood.CANCI_LUNCH, lstCookedFood);
		}

		TgCookedFood wucanHuncai = null;
		if (pickerType != 5) { // 5：全素食，没有荤菜
			// 获取适合午餐的荤菜
			wucanHuncai = new TgCookedFood();
			// wucanHuncai = getWucanHuncai(pc);
			wucanHuncai = getWucanHuncaiInfo(lstCookedFood);
		}
		// 获取适合午餐的汤
		// TgCookedFood wucanTang = getTang(TgCookedFood.CANCI_LUNCH,
		// TgCookedFood.TYPE_TANG_SU, pc);
		TgCookedFood wucanTang = getTangInfo(TgCookedFood.CANCI_LUNCH,
				TgCookedFood.TYPE_TANG_SU, lstCookedFood);

		// 荤菜，素菜，汤 含有素菜的总种数
		int sucaiCountZong = 0;

		// 素菜中含有素菜的种数
		int sucaiCount = 0;
		List<String> lstSucai = new ArrayList<String>();
		// 豆腐丝的数量
		int doufusi = 0;
		// 豆干的数量
		int dougan = 0;
		// 豆腐的数量
		int doufu = 0;
		// 大豆的数量
		int dadou = 0;
		// 腐竹的数量
		int fuzhu = 0;
		// 豆制品的种类
		int douType = 0;
		int sucaiLiangZong = 0;

		if (wucanSucai != null) {
			String[] foodsWucanSucai = wucanSucai.getFood().split("、");
			String[] foodTypesWucanSucai = wucanSucai.getFoodType().split("、");
			if (foodTypesWucanSucai != null && foodTypesWucanSucai.length > 0) {
				for (int i = 0; i < foodTypesWucanSucai.length; i++) {
					if (foodTypesWucanSucai[i].equals("菜")) {
						sucaiCount += 1;
						lstSucai.add(foodsWucanSucai[i]);
					} else if (foodTypesWucanSucai[i].equals("豆制品")) {
						if (foodsWucanSucai[i].equals("豆腐丝")) {
							doufusi += 1;
						} else if (foodsWucanSucai[i].equals("豆干")) {
							dougan += 1;
						} else if (foodsWucanSucai[i].equals("豆腐")) {
							doufu += 1;
						} else if (foodsWucanSucai[i].equals("大豆")) {
							dadou += 1;
						} else if (foodsWucanSucai[i].equals("腐竹")) {
							fuzhu += 1;
						}
					}
				}
			}

		}

		// 荤菜中含有素菜的种数
		int huncaiSucaiCount = 0;
		List<String> lstHuncaiSucai = new ArrayList<String>();
		// 获取荤菜中畜肉的信息
		List<String> lstHuncaiRou = new ArrayList<String>();
		// 获取荤菜中鱼的信息
		List<String> lstHuncaiFish = new ArrayList<String>();
		// 获取荤菜中虾的信息
		List<String> lstHuncaiXia = new ArrayList<String>();
		// 肉的种类数
		int rouTypeCount = 0;

		if (wucanHuncai != null) {
			String[] foodsWucanHuncai = wucanHuncai.getFood().split("、");
			String[] foodTypesWucanHuncai = wucanHuncai.getFoodType()
					.split("、");
			if (foodTypesWucanHuncai != null && foodTypesWucanHuncai.length > 0) {
				for (int i = 0; i < foodTypesWucanHuncai.length; i++) {
					if (foodTypesWucanHuncai[i].equals("菜")) {
						huncaiSucaiCount += 1;
						lstHuncaiSucai.add(foodsWucanHuncai[i]);
					} else if (foodTypesWucanHuncai[i].equals("畜肉")) {
						lstHuncaiRou.add(foodsWucanHuncai[i]);
					} else if (foodTypesWucanHuncai[i].equals("鱼")) {
						lstHuncaiFish.add(foodsWucanHuncai[i]);
					} else if (foodTypesWucanHuncai[i].equals("虾")) {
						lstHuncaiXia.add(foodsWucanHuncai[i]);
					}
				}
			}

		}

		// 汤中含有素菜的种数
		int tangSucaiCount = 0;
		List<String> lstTangSucai = new ArrayList<String>();
		if (wucanTang != null) {
			String[] foodsWucanTang = wucanTang.getFood().split("、");
			String[] foodTypesWucanTang = wucanTang.getFoodType().split("、");
			if (foodTypesWucanTang != null && foodTypesWucanTang.length > 0) {
				for (int i = 0; i < foodTypesWucanTang.length; i++) {
					if (foodTypesWucanTang[i].equals("菜")) {
						tangSucaiCount += 1;
						lstTangSucai.add(foodsWucanTang[i]);
					}
				}
			}
		}

		sucaiCountZong = sucaiCount + huncaiSucaiCount + tangSucaiCount;

		double everySucai = 350 / sucaiCountZong;

		if (!CollectionUtils.isEmpty(lstTangSucai)) {
			if (lstTangSucai.contains("紫菜")) {
				everySucai = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						(350 - 3) / (sucaiCountZong - 1), new DecimalFormat(
								"#0")));
			}
		}

		List<String> lstSucaiDetail = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(lstSucai)) {
			String sucai = ArrayUtils.join(lstSucai.toArray(), "、");
			int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
					everySucai * sucaiCount, new DecimalFormat("#0")));
			if (lstSucai.size() == 1) {
				lstSucaiDetail.add(sucai + liang + "g");
			} else {
				lstSucaiDetail.add(sucai + "共" + liang + "g");
			}
			sucaiLiangZong += liang;
		}

		if (doufusi > 0) {
			douType += 1;
		}
		if (dougan > 0) {
			douType += 1;
		}
		if (doufu > 0) {
			douType += 1;
		}
		if (dadou > 0) {
			douType += 1;
		}
		if (fuzhu > 0) {
			douType += 1;
		}

		if (douType > 0) {
			if (doufusi > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 50 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("豆腐丝" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (dougan > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 50 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("豆干" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (doufu > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 100 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("豆腐" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (dadou > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 25 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("大豆" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (fuzhu > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 25 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("腐竹" + liang + "g");
				sucaiLiangZong += liang;
			}
		}

		if (!CollectionUtils.isEmpty(lstSucaiDetail)) {
			String sucai = ArrayUtils.join(lstSucaiDetail.toArray(), "，");
			String name = wucanSucai.getName();
			if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(sucai)) {
				lstWucanOther.add(name + sucaiLiangZong + "g（" + sucai + "）");
			}
		}

		String wucanHuncaiDetail = "";
		List<String> lstWucanHuncai = new ArrayList<String>();

		double rouxishu = pc.getRouxishu();
		/*
		 * double doureka = 0; if (!StringUtils.isEmpty(zaocan)) { if
		 * (zaocan.contains("豆浆")) { doureka = 35; }else
		 * if(zaocan.contains("豆腐脑")){ doureka = 45; } }
		 */
		// 荤菜的总量
		int huncaiLiangZong = 0;
		if (!CollectionUtils.isEmpty(lstHuncaiRou)) {
			rouTypeCount += 1;

		}
		if (!CollectionUtils.isEmpty(lstHuncaiFish)) {
			rouTypeCount += 1;

		}
		if (!CollectionUtils.isEmpty(lstHuncaiXia)) {
			rouTypeCount += 1;
		}

		// 肉所占的比例
		/*
		 * int rouBili = 1; int hasEggxishu = 0; if(pickerType == 4){ rouBili =
		 * 3; }else{ rouBili = 2; hasEggxishu = 75; }
		 */
		if (rouTypeCount > 0) {
			if (!CollectionUtils.isEmpty(lstHuncaiRou)) {
				int rouliang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * rouxishu * 1 / 4 * 3 / 5 * 1 / 4 * 50 / 9
								* 1 / rouTypeCount, new DecimalFormat("#0")));
				String rou = ArrayUtils.join(lstHuncaiRou.toArray(), "、");
				lstWucanHuncai.add(rou + rouliang + "g");
				huncaiLiangZong += rouliang;
			}
			if (!CollectionUtils.isEmpty(lstHuncaiFish)) {
				int fishliang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * rouxishu * 1 / 4 * 3 / 5 * 1 / 4 * 80 / 9
								* 1 / rouTypeCount, new DecimalFormat("#0")));
				String fish = ArrayUtils.join(lstHuncaiFish.toArray(), "、");
				lstWucanHuncai.add(fish + fishliang + "g");
				huncaiLiangZong += fishliang;
			}
			if (!CollectionUtils.isEmpty(lstHuncaiXia)) {
				int xialiang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * rouxishu * 1 / 4 * 3 / 5 * 1 / 4 * 80 / 9
								* 1 / rouTypeCount, new DecimalFormat("#0")));
				String xia = ArrayUtils.join(lstHuncaiXia.toArray(), "、");
				lstWucanHuncai.add(xia + xialiang + "g");
				huncaiLiangZong += xialiang;
			}
		}

		if (!CollectionUtils.isEmpty(lstHuncaiSucai)) {
			int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
					everySucai * huncaiSucaiCount, new DecimalFormat("#0")));
			String sucai = ArrayUtils.join(lstHuncaiSucai.toArray(), "、");
			if (lstHuncaiSucai.size() == 1) {
				lstWucanHuncai.add(sucai + liang + "g");
			} else {
				lstWucanHuncai.add(sucai + "共" + liang + "g");
			}
			huncaiLiangZong += liang;
		}

		if (!CollectionUtils.isEmpty(lstWucanHuncai)) {
			String huncai = ArrayUtils.join(lstWucanHuncai.toArray(), "，");
			wucanHuncaiDetail = wucanHuncai.getName() + huncaiLiangZong + "g（"
					+ huncai + "）";
			lstWucanOther.add(wucanHuncaiDetail);
		}

		String wucanTangDetail = "";
		if (!CollectionUtils.isEmpty(lstTangSucai)) {

			if (lstTangSucai.contains("紫菜")) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						everySucai * (tangSucaiCount - 1), new DecimalFormat(
								"#0")));
				List<String> lstTangHaszicai = new ArrayList<String>();
				for (String tangSucai : lstTangSucai) {
					if (!tangSucai.equals("紫菜")) {
						lstTangHaszicai.add(tangSucai);
					}
				}
				if (!CollectionUtils.isEmpty(lstTangHaszicai)) {
					String sucai = ArrayUtils.join(lstTangHaszicai.toArray(),
							"、");
					wucanTangDetail = wucanTang.getName() + liang + "g（"
							+ sucai + liang + 3 + "g，紫菜3g）";
				} else {
					wucanTangDetail = wucanTang.getName() + "（紫菜3g）";
				}
			} else {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						everySucai * tangSucaiCount, new DecimalFormat("#0")));
				String sucai = ArrayUtils.join(lstTangSucai.toArray(), "、");
				if (lstTangSucai.size() == 1) {
					wucanTangDetail = wucanTang.getName() + "（" + sucai + liang
							+ "g）";
				} else {
					wucanTangDetail = wucanTang.getName() + "（" + sucai + "共"
							+ liang + "g）";
				}
				lstWucanOther.add(wucanTangDetail);
			}
		}

		if (!CollectionUtils.isEmpty(lstWucanOther)) {
			wucanOther = ArrayUtils.join(lstWucanOther.toArray(), "，");
		}

		return wucanOther;
	}

	// 查询除主食之外晚餐的信息
	/*
	 * public String getWancanOther(PickerCondition pc) { // 查询晚餐的素菜 String
	 * foodSucai = getSucai(TgCookedFood.CANCI_DINNER, pc); return foodSucai; }
	 */

	public String getWancanOther(PickerCondition pc,
			List<TgCookedFood> lstCookedFood) {
		double calories = pc.getCalories();
		double douxishu = pc.getDouxishu();
		String wancanOther = "";
		List<String> lstWancanOther = new ArrayList<String>();
		// 查询晚餐的素菜
		// TgCookedFood wanSucai = getFoodDou(pc, TgCookedFood.CANCI_DINNER);
		TgCookedFood wanSucai = getFoodDouInfo(TgCookedFood.CANCI_DINNER,
				lstCookedFood);
		// 晚餐素菜中的蔬菜信息
		List<String> lstSucai = new ArrayList<String>();
		// 晚餐餐素菜中蔬菜的种类数量
		int sucaiCount = 0;
		// 晚餐素菜中豆制品的信息
		// 豆腐丝的数量
		int doufusi = 0;
		// 豆干的数量
		int dougan = 0;
		// 豆腐的数量
		int doufu = 0;
		// 大豆的数量
		int dadou = 0;
		// 腐竹的数量
		int fuzhu = 0;
		// 豆制品的种类
		int douType = 0;

		if (wanSucai != null) {
			String[] foodsSucai = wanSucai.getFood().split("、");
			String[] foodTypesSucai = wanSucai.getFoodType().split("、");
			if (foodTypesSucai != null && foodTypesSucai.length > 0) {
				for (int i = 0; i < foodTypesSucai.length; i++) {
					if (foodTypesSucai[i].equals("菜")) {
						sucaiCount += 1;
						lstSucai.add(foodsSucai[i]);
					} else if (foodTypesSucai[i].equals("豆制品")) {
						if (foodsSucai[i].equals("豆腐丝")) {
							doufusi += 1;
						} else if (foodsSucai[i].equals("豆干")) {
							dougan += 1;
						} else if (foodsSucai[i].equals("豆腐")) {
							doufu += 1;
						} else if (foodsSucai[i].equals("大豆")) {
							dadou += 1;
						} else if (foodsSucai[i].equals("腐竹")) {
							fuzhu += 1;
						}
					}
				}
			}
		}

		// 素菜的总量
		int sucaiLiangZong = 0;
		// 每一种蔬菜的量
		int everySucai = Integer.parseInt(MoneyFormatUtil.formatToMoney(
				200 / sucaiCount, new DecimalFormat("#0")));

		List<String> lstSucaiDetail = new ArrayList<String>();

		if (!CollectionUtils.isEmpty(lstSucai)) {
			int liang = everySucai * sucaiCount;
			String sucai = ArrayUtils.join(lstSucai.toArray(), "、");
			if (!StringUtils.isEmpty(sucai)) {
				if (lstSucai.size() == 1) {
					lstSucaiDetail.add(sucai + liang + "g");
				} else {
					lstSucaiDetail.add(sucai + "共" + liang + "g");
				}
				sucaiLiangZong += liang;
			}
		}

		if (doufusi > 0) {
			douType += 1;
		}
		if (dougan > 0) {
			douType += 1;
		}
		if (doufu > 0) {
			douType += 1;
		}
		if (dadou > 0) {
			douType += 1;
		}
		if (fuzhu > 0) {
			douType += 1;
		}

		if (douType > 0) {
			if (doufusi > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 50 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("豆腐丝" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (dougan > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 50 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("豆干" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (doufu > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 100 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("豆腐" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (dadou > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 25 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("大豆" + liang + "g");
				sucaiLiangZong += liang;
			}
			if (fuzhu > 0) {
				int liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						calories * douxishu * 1 / 4 * 3 / 5 * 1 / 4 * 25 / 9
								* 1 / douType, new DecimalFormat("#0")));
				lstSucaiDetail.add("腐竹" + liang + "g");
				sucaiLiangZong += liang;
			}
		}

		if (!CollectionUtils.isEmpty(lstSucaiDetail)) {
			String sucai = ArrayUtils.join(lstSucaiDetail.toArray(), "，");
			String name = wanSucai.getName();
			if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(sucai)) {
				lstWancanOther.add(name + sucaiLiangZong + "g（" + sucai + "）");
			}
		}

		if (!CollectionUtils.isEmpty(lstWancanOther)) {
			wancanOther = ArrayUtils.join(lstWancanOther.toArray(), "，");
		}

		return wancanOther;
	}

	// 查询适合的素菜
	public String getSucai(String canci, PickerCondition pc) {
		String sucai = "";
		TgCookedFood sucaiZao = new TgCookedFood();
		sucaiZao.setCanci(canci);
		// 素菜是3：凉素菜 4：热素菜 随机产生3，4之间的值 查询随机凉素菜 或者 热素菜
		int zaoSucai = 0;
		// 如果是中午就在凉素菜 、热素菜之间选择
		if (canci.equals(TgCookedFood.CANCI_LUNCH)) {
			zaoSucai = PickerUtils.getRandom(3, 5);
		} else if (canci.equals(TgCookedFood.CANCI_DINNER)) { // 如果晚上就是热素菜
			zaoSucai = 4;
		}
		sucaiZao.setType(zaoSucai);
		TgCookedFood foodSucai = pc.getCookedFoodDao().queryCookedFood(
				sucaiZao, null, null, "蛋、豆制品", "and", pc.getDisease());

		if (foodSucai != null) {
			String food = foodSucai.getFood();
			String name = foodSucai.getName();
			if (!StringUtils.isEmpty(food) && !StringUtils.isEmpty(name)) {
				if (!food.contains("、")) {
					sucai += name + "200g（" + food + "200g）";
				} else {
					sucai += name + "200g（" + food + "共" + "200g）";
				}
			}
		}

		return sucai;
	}

	// 查询适合的素菜
	public TgCookedFood getSucaiInfo(PickerCondition pc, String canci) {
		TgCookedFood sucai = new TgCookedFood();
		sucai.setCanci(canci);
		// 素菜是3：凉素菜 4：热素菜 随机产生3，4之间的值 查询随机凉素菜 或者 热素菜
		int sucaiType = PickerUtils.getRandom(3, 5);
		sucai.setType(sucaiType);
		TgCookedFood foodSucai = pc.getCookedFoodDao().queryCookedFood(sucai,
				null, null, "蛋、豆制品", "and", pc.getDisease());
		return foodSucai;
	}

	// 获取适合的素菜
	public TgCookedFood getSuCai(String canci, List<TgCookedFood> lstCookedFood) {
		for (TgCookedFood cookedFood : lstCookedFood) {
			if ((cookedFood.getType().equals(TgCookedFood.TYPE_SUCAI_LIANG) || cookedFood
					.getType().equals(TgCookedFood.TYPE_SUCAI_RE))
					&& (!cookedFood.getFoodType().contains("豆制品") && !cookedFood
							.getFoodType().contains("蛋"))
					&& cookedFood.getCanci().contains(canci)) {
				return cookedFood;
			}
		}
		return null;
	}

	// 获取适合的热炒豆制品菜
	public TgCookedFood getFoodDou(PickerCondition pc, String canci) {
		TgCookedFood dou = new TgCookedFood();
		dou.setCanci(canci);
		dou.setType(TgCookedFood.TYPE_DOUZHI);
		TgCookedFood foodDou = pc.getCookedFoodDao().queryCookedFood(dou, null,
				null, null, null, pc.getDisease());
		return foodDou;
	}

	// 获取适合的热炒豆制品菜
	public TgCookedFood getFoodDouInfo(String canci,
			List<TgCookedFood> lstCookedFood) {
		for (TgCookedFood cookedFood : lstCookedFood) {
			if (cookedFood.getType().equals(TgCookedFood.TYPE_DOUZHI)
					&& cookedFood.getCanci().contains(canci)) {
				return cookedFood;
			}
		}
		return null;
	}

	// 查询适合午餐的荤菜
	public TgCookedFood getWucanHuncai(PickerCondition pc) {
		TgCookedFood huncai = new TgCookedFood();
		huncai.setCanci(TgCookedFood.CANCI_LUNCH);
		huncai.setType(TgCookedFood.TYPE_HUN_RE);
		TgCookedFood foodHuncai = pc.getCookedFoodDao().queryCookedFood(huncai,
				null, null, "蛋、豆制品", "and", pc.getDisease());
		return foodHuncai;
	}

	// 获取适合午餐的荤菜
	public TgCookedFood getWucanHuncaiInfo(List<TgCookedFood> lstCookedFood) {
		for (TgCookedFood cookedFood : lstCookedFood) {
			if (cookedFood.getType().equals(TgCookedFood.TYPE_HUN_RE)
					&& (!cookedFood.getFoodType().contains("蛋") && !cookedFood
							.getFoodType().contains("豆制品"))
					&& cookedFood.getCanci().contains(TgCookedFood.CANCI_LUNCH)) {
				return cookedFood;
			}
		}
		return null;
	}

	// 查询适合的汤
	public TgCookedFood getTangInfo(String canci, Integer tangType,
			List<TgCookedFood> lstCookedFood) {
		for (TgCookedFood cookedFood : lstCookedFood) {
			if (cookedFood.getType().equals(tangType)
					&& (!cookedFood.getFoodType().contains("蛋") && !cookedFood
							.getFoodType().contains("豆制品"))
					&& cookedFood.getCanci().contains(canci)) {
				return cookedFood;
			}
		}
		return null;
	}

	// 获取适合的汤
	public TgCookedFood getTang(String canci, Integer tangType,
			PickerCondition pc) {
		TgCookedFood tang = new TgCookedFood();
		tang.setCanci(canci);
		tang.setType(tangType);
		TgCookedFood foodTang = pc.getCookedFoodDao().queryCookedFood(tang,
				null, null, "蛋、豆制品", "and", pc.getDisease());
		return foodTang;
	}

	// 查询早加餐的信息
	public String getZaojia(PickerCondition pc, List<TgCookedFood> lstCookedFood) {
		String zaojia = "";
		List<String> lstZaojia = new ArrayList<String>();
		// 查询早加餐的坚果
		/*
		 * String nuts = getNuts(pc); if (!StringUtils.isEmpty(nuts)) {
		 * lstZaojia.add(nuts); }
		 */
		// 查询早加餐的水果或者果蔬
		String fruit = getFruitInfo(pc, lstCookedFood);
		if (!StringUtils.isEmpty(fruit)) {
			lstZaojia.add(fruit);
		}
		if (!CollectionUtils.isEmpty(lstZaojia)) {
			zaojia = ArrayUtils.join(lstZaojia.toArray(), "，");
		}
		return zaojia;
	}

	// 获取午加餐的信息
	public String getWujia(PickerCondition pc, List<TgCookedFood> lstCookedFood) {
		String wujia = getMilkInfo(pc, TgCookedFood.CANCI_WUJIA, lstCookedFood);
		return wujia;
	}

	// 获取晚加餐的信息
	public String getWanjia(PickerCondition pc, List<TgCookedFood> lstCookedFood) {
		String wanjia = getMilkInfo(pc, TgCookedFood.CANCI_WUJIA, lstCookedFood);
		return wanjia;
	}

	// 获取早加餐坚果的信息
	public String getNuts(PickerCondition pc) {
		// 总热量
		double calories = pc.getCalories();
		double nutsxishu = pc.getNutsxishu();
		String nutsJiacan = "";
		// 早加的坚果
		TgCookedFood nuts = new TgCookedFood();
		nuts.setCanci(TgCookedFood.CANCI_ZAOJIA);
		nuts.setType(TgCookedFood.TYPE_NUTS);
		TgCookedFood foodNuts = pc.getCookedFoodDao().queryCookedFood(nuts,
				null, null, null, null, pc.getDisease());

		if (foodNuts != null && !StringUtils.isEmpty(foodNuts.getName())) {
			String nutsName = foodNuts.getName();
			String nutsType = foodNuts.getFoodType();
			int liang = 0;
			if (nutsName.equals("花生") || nutsName.equals("瓜子")
					|| nutsName.equals("核桃") || nutsName.equals("西瓜子")) {

				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						(calories * nutsxishu / 90 - 2.5) * 15 * 2,
						new DecimalFormat("#0")));
			} else if (nutsName.equals("松子")) {

				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						(calories * nutsxishu / 90 - 2.5) * 15 * 10 / 3,
						new DecimalFormat("#0")));
			} else if (nutsName.equals("南瓜子") || nutsName.equals("白果")) {

				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						(calories * nutsxishu / 90 - 2.5) * 15 * 10 / 7,
						new DecimalFormat("#0")));
			} else if (nutsName.equals("栗子")) {

				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						(calories * nutsxishu / 90 - 2.5) * 15 * 10 / 8,
						new DecimalFormat("#0")));
			} else if (nutsType.equals("坚果仁")) {
				nutsName = foodNuts.getName();
				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(
						(calories * nutsxishu / 90 - 2.5) * 15,
						new DecimalFormat("#0")));
			}

			if (!StringUtils.isEmpty(nutsName)) {
				nutsJiacan = nutsName + liang + "g";
			}
		}

		return nutsJiacan;
	}

	// 获取水果，果蔬早加餐信息
	public String getFruit(PickerCondition pc) {
		String fruitJiacan = "";
		// 早加的水果或者果蔬
		TgCookedFood fruit = new TgCookedFood();
		fruit.setCanci(TgCookedFood.CANCI_ZAOJIA);

		// 查询客户最后一次上传空腹或餐后两小时血糖信息
		List<BloodSugar> lstBS = pc.getBloodSugarDao().queryLatestBloodSugar(
				pc.getClientId(), null, null);
		BloodSugar bs = null;
		if (!CollectionUtils.isEmpty(lstBS)) {
			bs = lstBS.get(0);
		}

		if (bs != null
				&& ((BloodSugar.SUGAR_BEFORE.contains(bs.getBloodSugarType()
						+ "") && bs.getBloodSugarValue() >= 7) || (BloodSugar.SUGAR_AFTER
						.contains(bs.getBloodSugarType() + "") && bs
						.getBloodSugarValue() >= 10))) {
			List<String> lst = new ArrayList<String>();
			lst.add("番茄");
			lst.add("小西红柿");
			lst.add("水果菜椒");
			lst.add("黄瓜");
			lst.add("樱桃");
			lst.add("草莓");
			lst.add("柚子");
			// 获取0-6之间随机数
			int index = PickerUtils.getRandom(0, 7);
			if (index > 3) {
				fruitJiacan = lst.get(index) + "100g";
			} else {
				fruitJiacan = lst.get(index) + "250g";
			}
		} else {
			// 早加餐是12：水果或者13：果蔬 随机产生12，13的数，就是随机生成一个水果或者是果蔬
			int fruitIndex = PickerUtils.getRandom(12, 14);
			fruit.setType(fruitIndex);
			TgCookedFood foodFruitZaojia = pc.getCookedFoodDao()
					.queryCookedFood(fruit, null, null, null, null,
							pc.getDisease());

			if (foodFruitZaojia != null
					&& !StringUtils.isEmpty(foodFruitZaojia.getName())) {
				String fruitName = foodFruitZaojia.getName();
				Integer fruitType = foodFruitZaojia.getType();
				if (fruitType != null) {
					if (fruitType == TgCookedFood.TYPE_FRUIT) {
						fruitJiacan = fruitName + "100g";
					} else {
						fruitJiacan = fruitName + "250g";
					}
				}
			}
		}
		return fruitJiacan;
	}

	// 获取水果，果蔬早加餐信息
	public String getFruitInfo(PickerCondition pc,
			List<TgCookedFood> lstCookedFood) {
		String fruitJiacan = "";
		// 早加的水果或者果蔬
		TgCookedFood fruit = new TgCookedFood();
		fruit.setCanci(TgCookedFood.CANCI_ZAOJIA);

		// 查询客户最后一次上传空腹或餐后两小时血糖信息
		List<BloodSugar> lstBS = pc.getBloodSugarDao().queryLatestBloodSugar(
				pc.getClientId(), null, null);
		BloodSugar bs = null;
		if (!CollectionUtils.isEmpty(lstBS)) {
			bs = lstBS.get(0);
		}

		if (bs != null
				&& ((BloodSugar.SUGAR_BEFORE.contains(bs.getBloodSugarType()
						+ "") && bs.getBloodSugarValue() >= 7) || (BloodSugar.SUGAR_AFTER
						.contains(bs.getBloodSugarType() + "") && bs
						.getBloodSugarValue() >= 10))) {
			List<String> lst = new ArrayList<String>();
			lst.add("番茄");
			lst.add("小西红柿");
			lst.add("水果菜椒");
			lst.add("黄瓜");
			lst.add("樱桃");
			lst.add("草莓");
			lst.add("柚子");
			// 获取0-6之间随机数
			int index = PickerUtils.getRandom(0, 7);
			if (index > 3) {
				fruitJiacan = lst.get(index) + "100g";
			} else {
				fruitJiacan = lst.get(index) + "250g";
			}
		} else {
			// 早加餐是12：水果或者13：果蔬 随机产生12，13的数，就是随机生成一个水果或者是果蔬
			int fruitIndex = PickerUtils.getRandom(12, 14);
			fruit.setType(fruitIndex);
			TgCookedFood foodFruitZaojia = null;

			for (TgCookedFood cookedFood : lstCookedFood) {
				if (cookedFood.getType().equals(TgCookedFood.TYPE_FRUIT)
						|| cookedFood.getType().equals(
								TgCookedFood.TYPE_VEGETABLT)) {
					foodFruitZaojia = cookedFood;
					break;
				}
			}
			if (foodFruitZaojia != null
					&& !StringUtils.isEmpty(foodFruitZaojia.getName())) {
				String fruitName = foodFruitZaojia.getName();
				Integer fruitType = foodFruitZaojia.getType();
				if (fruitType != null) {
					if (fruitType == TgCookedFood.TYPE_FRUIT) {
						fruitJiacan = fruitName + "100g";
					} else {
						fruitJiacan = fruitName + "250g";
					}
				}
			}
		}
		return fruitJiacan;
	}

	// 获取奶类的信息（午加餐，晚加餐）
	public String getMilk(PickerCondition pc, String canci, String zaocan) {
		String milkJiacan = "";
		// 总热量
		double calories = pc.getCalories();
		/** 奶类部分计算系数 */
		double milkxishu = pc.getMilkxishu();
		TgCookedFood milk = new TgCookedFood();
		milk.setCanci(canci);
		milk.setType(TgCookedFood.TYPE_NAI);
		TgCookedFood foodMilk = pc.getCookedFoodDao().queryCookedFood(milk,
				null, null, null, null, pc.getDisease());
		/*
		 * double doureka = 0; if (!StringUtils.isEmpty(zaocan)) { if
		 * (zaocan.contains("豆浆")) { doureka = 35; } else if
		 * (zaocan.contains("豆腐脑")) { doureka = 45; } }
		 * 
		 * int hasEggxishu = 0; if(pc.isHasEggs()){ hasEggxishu = 75; }
		 */

		if (foodMilk != null && !StringUtils.isEmpty(foodMilk.getName())) {
			String milkName = foodMilk.getName();
			int liang = 0;
			if (milkName.equals("脱脂酸奶")) {
				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(calories
						* milkxishu * 3 / 5 * 1 / 4 * 1 / 4 * 100 * 1 / 3.3,
						new DecimalFormat("#0")));
			} else if (milkName.equals("酸奶")) {
				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(calories
						* milkxishu * 3 / 5 * 1 / 4 * 1 / 4 * 100 * 1 / 2.5,
						new DecimalFormat("#0")));
			} else {
				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(calories
						* milkxishu * 3 / 5 * 1 / 4 * 1 / 4 * 100 * 1 / 3,
						new DecimalFormat("#0")));
			}
			milkJiacan = milkName + liang + "g";
		}
		return milkJiacan;
	}

	// 获取奶类的信息（午加餐，晚加餐）
	public String getMilkInfo(PickerCondition pc, String canci,
			List<TgCookedFood> lstCookedFood) {
		String milkJiacan = "";
		// 总热量
		double calories = pc.getCalories();
		/** 奶类部分计算系数 */
		double milkxishu = pc.getMilkxishu();
		TgCookedFood foodMilk = null;
		for (TgCookedFood cookedFood : lstCookedFood) {
			if (cookedFood.getCanci().contains(canci)
					&& cookedFood.getType().equals(TgCookedFood.TYPE_NAI)) {
				foodMilk = cookedFood;
				break;
			}
		}

		if (foodMilk != null && !StringUtils.isEmpty(foodMilk.getName())) {
			String milkName = foodMilk.getName();
			int liang = 0;
			if (milkName.equals("脱脂酸奶")) {
				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(calories
						* milkxishu * 3 / 5 * 1 / 4 * 1 / 4 * 100 * 1 / 3.3,
						new DecimalFormat("#0")));
			} else if (milkName.equals("酸奶")) {
				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(calories
						* milkxishu * 3 / 5 * 1 / 4 * 1 / 4 * 100 * 1 / 2.5,
						new DecimalFormat("#0")));
			} else {
				liang = Integer.parseInt(MoneyFormatUtil.formatToMoney(calories
						* milkxishu * 3 / 5 * 1 / 4 * 1 / 4 * 100 * 1 / 3,
						new DecimalFormat("#0")));
			}
			milkJiacan = milkName + liang + "g";
		}
		return milkJiacan;
	}

	// 查询适合早餐的蛋
	public String getFoodEgg(PickerCondition pc) {
		String foodEgg = "";
		TgCookedFood egg = new TgCookedFood();
		egg.setType(TgCookedFood.TYPE_DAN);
		egg.setCanci(TgCookedFood.CANCI_BREAKFAST);
		TgCookedFood foodEggZao = pc.getCookedFoodDao().queryCookedFood(egg,
				null, null, null, null, pc.getDisease());
		if (foodEggZao != null) {
			String name = foodEggZao.getName();
			if (!StringUtils.isEmpty(name)) {
				foodEgg = name + "50g";
			}
		}
		return foodEgg;
	}

	public TgCookedFood getTangEgg(PickerCondition pc, boolean hasEgg,
			String canci) {
		TgCookedFood egg = new TgCookedFood();
		egg.setCanci(canci);
		egg.setType(TgCookedFood.TYPE_TANG_SU);
		if (hasEgg == true) {
			return pc.getCookedFoodDao().queryCookedFood(egg, "蛋", null, null,
					null, pc.getDisease());
		} else {
			return pc.getCookedFoodDao().queryCookedFood(egg, null, null,
					"蛋、豆制品", "and", pc.getDisease());
		}
	}

	public TgCookedFood getTangEggInfo(boolean hasEgg, String canci,
			List<TgCookedFood> lstCookedFood) {
		if (hasEgg) {
			for (TgCookedFood cookedFood : lstCookedFood) {
				if (cookedFood.getType().equals(TgCookedFood.TYPE_TANG_SU)
						&& cookedFood.getCanci().contains(canci)
						&& cookedFood.getFoodType().contains("蛋")) {
					return cookedFood;
				}
			}
		} else {
			for (TgCookedFood cookedFood : lstCookedFood) {
				if (cookedFood.getType().equals(TgCookedFood.TYPE_TANG_SU)
						&& cookedFood.getCanci().contains(canci)
						&& (!cookedFood.getFoodType().contains("蛋") && !cookedFood
								.getFoodType().contains("豆制品"))) {
					return cookedFood;
				}
			}
		}
		return null;
	}

	// 查询适合早餐的豆浆或者豆腐脑
	public String getDou() {
		int dou = PickerUtils.getRandom(0, 2);
		String zaocanDou = "";
		if (dou == 0) {
			zaocanDou = "豆浆（250g）";
		} else {
			zaocanDou = "豆腐脑（300g）";
		}
		return zaocanDou;
	}

	// 查询适合的粥
	public TgCookedFood getZou(PickerCondition pc) {
		TgCookedFood zou = new TgCookedFood();
		zou.setType(TgCookedFood.TYPE_ZOU);
		zou.setCanci(TgCookedFood.CANCI_BREAKFAST);
		return pc.getCookedFoodDao().queryCookedFood(zou, null, null, null,
				null, pc.getDisease());
	}
}
