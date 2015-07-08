package com.bskcare.ch.service.rpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.rpt.SportDiseaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SportDisease;

@Service
public class SportDiseaseServiceImpl implements SportDiseaseService{

	@Autowired
	SportDiseaseDao diseaseDao;
	
	public SportDisease save(SportDisease sportDisease) {
		return diseaseDao.add(sportDisease);
	}
	
	public SportDisease querySportDiseaseForRpt(SportDisease sd) {
		List<SportDisease> list = diseaseDao.querySportDisease(sd);
		if(!CollectionUtils.isEmpty(list)) {
			SportDisease disease = list.get(0);
			return disease;
		}
		return null;
	}

	public PageObject<SportDisease> querySportDiseaseList(SportDisease disease,QueryInfo queryInfo) {
		return diseaseDao.querySportDiseaseList(disease,queryInfo);
	}

	public SportDisease findDiseaseById(Integer id) {
		return diseaseDao.load(id);
	}

	public void update(SportDisease sportDisease) {
		diseaseDao.update(sportDisease);
	}

	public void delete(Integer id) {
		diseaseDao.delete(id);		
	}
	
//	public String dd() {
//		List<SportPrescription> spList = prescriptionDao.querySportPrescription(null, disease.getSportType());
//		if(!CollectionUtils.isEmpty(spList)) {
//			List<String> nameList = ListUtils.getFiledList(spList, "name");
//			String msg = ArrayUtils.join(nameList.toArray());
//			disease.setSportType(msg);
//		}
//	}

}
