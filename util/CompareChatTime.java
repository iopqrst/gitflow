package com.bskcare.ch.util;

import java.util.Comparator;

import com.bskcare.ch.vo.online.OnlineRecored;

public class CompareChatTime implements Comparator<OnlineRecored> {

	public int compare(OnlineRecored o1, OnlineRecored o2) {
		if(o1.getCreateTime().before(o2.getCreateTime())){
			return 0;
		} else {
			return 1;
		}
	}

}
