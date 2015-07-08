package com.bskcare.ch.service.impl;

import java.util.Comparator;

import com.bskcare.ch.vo.ConstitutionScore;

public class CmcComparator implements Comparator<ConstitutionScore> {

	public int compare(ConstitutionScore o1, ConstitutionScore o2) {
		int result = (o1.getScore() - o2.getScore()) > 0 ? 0 : 1;
		return result;
	}

}
