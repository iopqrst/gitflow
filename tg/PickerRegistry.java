package com.bskcare.ch.tg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PickerRegistry implements Serializable {

	private static final long serialVersionUID = -3894767123320768419L;

	private Map mpicker = new HashMap();

	public PickerRegistry() {

	}

	public synchronized void registerPicker(Picker picker) {
		Object obj = mpicker.get(picker.picksTo());
		
		if(null == obj) {
			mpicker.put(picker.picksTo(), picker);
		}
	}

	public synchronized void clear() {
		mpicker.clear();
	}

	public synchronized void clear(Class type) {
		Object obj = mpicker.get(type);
		if (obj != null) {
			mpicker.remove(type);
		}
	}

	/**
	 * 还能不能吃鸡蛋了 能就选择一个能吃鸡蛋的模式，否则随便一个
	 * @param eatingEggs
	 * @return 返回符合条件的筛选模式，不符合条件时返回null
	 */
	public synchronized Picker selectPicker(boolean eatingEggs) {
		List<Picker> pickerStore = new ArrayList<Picker>();
		List<Picker> eggsStore = new ArrayList<Picker>();
		
		Set set = mpicker.keySet();
		
		for(Object obj : set) {
			AbstractObjectPicker aop = ((AbstractObjectPicker) mpicker.get(obj));

			if(aop.isHasEgg()) {//如果存在鸡蛋放入到鸡蛋模式的list中
				eggsStore.add(aop);
			} else {
				pickerStore.add(aop);
			}
		}
		
		if(eatingEggs) {
			if(eggsStore.size() > 0	) {//存在相应模式
				return eggsStore.get(PickerUtils.getRandom(0, eggsStore.size()));
			} 
		} else {
			if(pickerStore.size() > 0){//存在相应模式
				return pickerStore.get(PickerUtils.getRandom(0, pickerStore.size()));
			} 
		}
			
		return null;
	}
	
}