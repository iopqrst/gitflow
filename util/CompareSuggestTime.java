package com.bskcare.ch.util;

import java.util.Comparator;

import com.bskcare.ch.vo.Suggestion;

public class CompareSuggestTime implements Comparator<Suggestion> {

	public int compare(Suggestion o1, Suggestion o2) {
		if (o1.getCreateTime().before(o2.getCreateTime())) {
			return 0;
		} else {
			return 1;
		}
	}

}
