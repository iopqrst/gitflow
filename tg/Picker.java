package com.bskcare.ch.tg;

/**
 * 
 * @author Administrator
 * 
 */
public interface Picker {

	/**
	 * 根据相应的条件返回符合条件的熟食（早中晚及早加、中加、晚加）
	 */
	public TgMeals picker(PickerCondition pc);

	/**
	 * Returns the target Class for conversion.
	 * 
	 * @return the target Class for conversion.
	 */
	Class picksTo();
}
