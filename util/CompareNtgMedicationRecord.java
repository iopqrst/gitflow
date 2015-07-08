package com.bskcare.ch.util;

import java.util.Comparator;

import com.bskcare.ch.vo.ntg.NtgMedicationRecord;

public class CompareNtgMedicationRecord implements
		Comparator<NtgMedicationRecord> {

	public int compare(NtgMedicationRecord o1, NtgMedicationRecord o2) {
		return o1.getMedicalTime().compareTo(o2.getMedicalTime());
	}

}
