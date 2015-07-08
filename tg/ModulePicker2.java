package com.bskcare.ch.tg;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.MoneyFormatUtil;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.tg.TgCookedFood;

@SuppressWarnings("unchecked")
public class ModulePicker2 extends AbstractObjectPicker {

	public ModulePicker2() {
		super(true); // 有鸡蛋
	}

	public TgMeals picker(PickerCondition pc) {
		System.out.println("---------ModulePicker2, getCalories = "
				+ pc.getCalories() + ", getClientId = " + pc.getClientId()
				+ ", isHasGruel = " + pc.isHasGruel() + ", isHasEgg = "
				+ pc.isHasEggs());

		if (pc != null) {
			GetFoodQuantity foodQuantity = new GetFoodQuantity();
			//早餐 
			String zaocan = "";
			//午餐 
			String wucan = "";
			//晚餐
			String wancan = "";

			List<TgCookedFood> lstCookedFood = pc.getCookedFoodDao().queryCookedFood(pc.getDisease());
			
			//早、午、晚所有的主食信息
			List<String> lstZhushi = foodQuantity.getZhushi(pc, 2, lstCookedFood);
			if (!CollectionUtils.isEmpty(lstZhushi) && lstZhushi.size() == 3) {
				//早餐的主食信息
				String zaocanZhushi = lstZhushi.get(0);
				//午餐的主食信息
				String wucanZhushi = lstZhushi.get(1);
				//晚餐的主食信息
				String wancanZhushi = lstZhushi.get(2);

				//早餐除主食外的信息
				String zaocanOther = foodQuantity.getZaocanOther(pc, 2, lstCookedFood);
				if (!StringUtils.isEmpty(zaocanZhushi)) {
					zaocan += zaocanZhushi;
				}
				if (!StringUtils.isEmpty(zaocanOther)) {
					zaocan += "，" + zaocanOther;
				}
				//午餐除主食外的信息
				String wucanOther = foodQuantity.getWucanOther(pc, zaocan, 2, lstCookedFood);
				if (!StringUtils.isEmpty(wucanZhushi)) {
					wucan += wucanZhushi;
				}
				if (!StringUtils.isEmpty(wucanOther)) {
					wucan += "，" + wucanOther;
				}
				//晚餐除主食外的信息
				String wancanOther = foodQuantity.getWancanOther(pc, lstCookedFood);
				if (!StringUtils.isEmpty(wancanZhushi)) {
					wancan += wancanZhushi;
				}
				if (!StringUtils.isEmpty(wancanOther)) {
					wancan += "，" + wancanOther;
				}
			}

			//早加餐
			String zaojia = foodQuantity.getZaojia(pc, lstCookedFood);
			//午加餐 
			String wujia = foodQuantity.getWujia(pc, lstCookedFood);
			//晚加餐
			String wanjia = foodQuantity.getWanjia(pc, lstCookedFood);

			TgMeals meals = new TgMeals(zaocan, zaojia, wucan, wujia, wancan,
					wanjia);
			return meals;

		}
		return null;
	}

	public Class picksTo() {
		return ModulePicker2.class;
	}

	// 获取除主食以外其他的早餐信息
	public String getZaocanOther(PickerCondition pc) {
		GetFoodQuantity foodQuantity = new GetFoodQuantity();
		String zaocanOther = "";
		// 早餐的素菜
		TgCookedFood zaoSucai = foodQuantity.getSucaiInfo(pc,
				TgCookedFood.CANCI_BREAKFAST);
		// 早餐的汤
		TgCookedFood zaoTang = foodQuantity.getTangEgg(pc, true,
				TgCookedFood.CANCI_BREAKFAST);

		if (zaoSucai != null && zaoTang != null) {
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

			String[] foodsSucai = zaoSucai.getFood().split("、");
			String[] foodTypesSucai = zaoSucai.getFoodType().split("、");
			if (foodTypesSucai != null && foodTypesSucai.length > 0) {
				for (int i = 0; i < foodTypesSucai.length; i++) {
					if (foodTypesSucai[i].equals("菜")) {
						sucaiCount += 1;
						lstSucai.add(foodsSucai[i]);
					}
				}
			}

			String[] foodsTang = zaoSucai.getFood().split("、");
			String[] foodTypesTang = zaoSucai.getFoodType().split("、");
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
			// 素菜中蔬菜的种类和汤中蔬菜种类的总量
			int sucaiTypeZong = sucaiCount + tangSucaiCount;
			// 每一种蔬菜的量
			int everySucai = 0;
			if (!CollectionUtils.isEmpty(lstTangSucai)) {
				if (lstTangSucai.contains("紫菜")) {
					everySucai = Integer.parseInt(MoneyFormatUtil
							.formatToMoney((200 - 60) / (sucaiTypeZong - 1),
									new DecimalFormat("#0")));
				} else {
					everySucai = Integer.parseInt(MoneyFormatUtil
							.formatToMoney(200 / sucaiTypeZong,
									new DecimalFormat("#0")));
				}
			}

			if (!CollectionUtils.isEmpty(lstSucai)) {
				int liang = everySucai * sucaiCount;
				String sucai = ArrayUtils.join(lstSucai.toArray(), "、");
				zaocanOther += zaoSucai.getName() + liang + "g（" + sucai
						+ liang + "g）";
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
						String sucai = ArrayUtils.join(lstTangHaszicai
								.toArray(), "、");
						lstTangDetail.add(sucai + liang + "g");
						lstTangDetail.add("紫菜60g");
						tangZong += liang;
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
			if (!CollectionUtils.isEmpty(lstTangEgg)) {
				String egg = ArrayUtils.join(lstTangEgg.toArray(), "、");
				lstTangDetail.add(egg + "50g");
				tangZong += 50;
			}

			if (!CollectionUtils.isEmpty(lstTangDetail)) {
				String tangDetail = ArrayUtils.join(lstTangDetail.toArray(),
						"，");
				zaocanOther += zaoTang.getName() + tangZong + "g（" + tangDetail
						+ "）";
			}
		}

		return zaocanOther;
	}

}
