package com.bskcare.ch.service.impl.rpt;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.rpt.SportPrescriptionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.SportPrescriptionService;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.rpt.SportPrescription;

@Service
@SuppressWarnings("unchecked")
public class SportPrescriptionServiceImpl implements SportPrescriptionService {

	@Autowired
	SportPrescriptionDao prescriptionDao;

	public SportPrescription save(SportPrescription prescription) {
		return prescriptionDao.add(prescription);
	}

	public SportPrescription querySinglePrescript(SportPrescription sp) {
		List<SportPrescription> list = queryPrescript(sp);
		if (CollectionUtils.isEmpty(list))
			return null;
		int index = RandomUtils.getRandomIndex(list.size());
		SportPrescription material = list.get(index); // 随机获到一个对象
		return material;
	}

	public List<SportPrescription> queryPrescript(SportPrescription sp) {
		return prescriptionDao.querySportPrescription(sp, null);
	}
	
	public List<SportPrescription> queryPrescript(SportPrescription sp, String ids) {
		return prescriptionDao.querySportPrescription(sp, ids);
	}

	/**
	 * 查询所有的大处方和小处方
	 */
	public String findPrescription() {
		List<SportPrescription> list = prescriptionDao.findPrescription();
		JSONObject jo = new JSONObject();
		jo.put("lstPrescription", JsonUtils.getJONSArray4JavaList(list));

		return jo.toString();
	}

	
	/**
	 * 查询所有的处方信息分页显示
	 */
	public PageObject findPrescriptionPage(SportPrescription pres,QueryInfo queryInfo){
		return prescriptionDao.findPrescriptionPage(pres, queryInfo);
	}
	
	/**
	 * 根据id查询处方的信息
	 */
	public SportPrescription findPrescriptionById(Integer id){
		return prescriptionDao.load(id);
	}
	
	/**
	 * 根据id删除处方
	 */
	public void deletePrescriptionById(Integer id){
		prescriptionDao.delete(id);
	}
	
	/**
	 * 修改处方信息
	 */
	public void updatePrescription(SportPrescription pres){
		prescriptionDao.update(pres);
	}
	
	/**
	 * 添加处方信息
	 */
	public void addPrescription(SportPrescription pres){
		prescriptionDao.add(pres);
	}
	
	public String findPresByName(String name){
		return prescriptionDao.findPresByName(name);
	}
}
