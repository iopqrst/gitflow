package com.bskcare.ch.tg;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.tg.TgCookedFood;


@SuppressWarnings("unchecked")
public class ModulePicker4 extends AbstractObjectPicker{
	
	public ModulePicker4() {
		super(false); //没鸡蛋
	}
	
	public TgMeals picker(PickerCondition pc) {
		System.out.println("---------ModulePicker4, getCalories = "
				+ pc.getCalories() + ", getClientId = " + pc.getClientId()
				+ ", isHasGruel = " + pc.isHasGruel() + ", isHasEgg = " + pc.isHasEggs());
		
		if(pc != null){
			GetFoodQuantity foodQuantity = new GetFoodQuantity();
			//早餐
			String zaocan = "";
			//午餐
			String wucan = "";
			//晚餐
			String wancan = "";
			
			List<TgCookedFood> lstCookedFood = pc.getCookedFoodDao().queryCookedFood(pc.getDisease());
			
			List<String> lstZhushi = foodQuantity.getZhushi(pc, 4, lstCookedFood);
			if(!CollectionUtils.isEmpty(lstZhushi)&&lstZhushi.size()==3){
				String zaocanZhushi = lstZhushi.get(0);
				String wucanZhushi = lstZhushi.get(1);
				String wancanZhushi = lstZhushi.get(2);
				
				String zaocanOther = foodQuantity.getZaocanOther(pc, 4, lstCookedFood);
				if(!StringUtils.isEmpty(zaocanZhushi)){
					zaocan += zaocanZhushi;
				}
				if(!StringUtils.isEmpty(zaocanOther)){
					zaocan += "，"+zaocanOther;
				}
				
				String wucanOther = foodQuantity.getWucanOther(pc, zaocan, 4, lstCookedFood);
				if(!StringUtils.isEmpty(wucanZhushi)){
					wucan += wucanZhushi;
				}
				if(!StringUtils.isEmpty(wucanOther)){
					wucan += "，"+wucanOther;
				}
				
				String wancanOther = foodQuantity.getWancanOther(pc, lstCookedFood);
				if(!StringUtils.isEmpty(wancanZhushi)){
					wancan += wancanZhushi;
				}
				if(!StringUtils.isEmpty(wancanOther)){
					wancan += "，"+wancanOther;
				}
			}
			
			//早加餐
			String zaojia = foodQuantity.getZaojia(pc, lstCookedFood);
			//午加餐
			String wujia = foodQuantity.getWujia(pc, lstCookedFood);
			//晚加餐
			String wanjia = foodQuantity.getWanjia(pc, lstCookedFood);
			
			
			//System.out.println("zaocan"+zaocan  +",wucan"+wucan+",wancan"+wancan+",zaojia"+zaojia+",wujia"+wujia+",wanjia"+wanjia);

			TgMeals meals = new TgMeals(zaocan, zaojia, wucan, wujia, wancan,
					wanjia);
			return meals;
			
		}
		return null;
	}

	@Override
	public Class picksTo() {
		return ModulePicker4.class;
	}

	
	
}
