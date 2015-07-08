package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ElectrocardiogramDao;
import com.bskcare.ch.dao.rpt.RptMonitoringDataDao;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.Electrocardiogram;
import com.bskcare.ch.vo.rpt.RptMonitoringData;

@Repository
@SuppressWarnings("unchecked")
public class RptMonitoringDataDaoImpl extends BaseDaoImpl<RptMonitoringData>
		implements RptMonitoringDataDao {
	
	@Autowired	
	private ElectrocardiogramDao  electrocardiogramDao ;
	
	public List<RptMonitoringData> getReportMonitoringData(ClientInfo clientInfo,Integer rptId) {
		
		ArrayList<Integer> olist = new ArrayList<Integer>() ;
		olist.add(rptId) ;
		
		List list = this.executeNativeQuery(
				"select * from rpt_monitoring_data where rptId=?", olist.toArray(),
				RptMonitoringData.class);

		return list;
	}
	
	public int updateRptBySingleField(String field,String content,Integer mid){
		String sql = "update rpt_monitoring_data set "+ field +" = ? where mid = ?";
		return updateBySql(sql, new Object[]{content,mid});
	}

	public void updateDefaultPic(String bloodPressureChart,
			String bloodSugarChart, String bloodSugar2hChart,
			String bloodOxygenChart, String electrocardiogram, String rptId,String type) {
			if(type.equals("1")){
				//修改血压
				this.updateBySql("update rpt_monitoring_data set defaultPic = "+bloodPressureChart+" where type = 1 and rptId ="+rptId) ;
			}else if(type.equals("2")){
				//修改血氧
				this.updateBySql("update rpt_monitoring_data set defaultPic = "+bloodOxygenChart+" where type = 2 and rptId ="+rptId) ;
			}else if(type.equals("3")){
				//修改血糖
				this.updateBySql("update rpt_monitoring_data set defaultPic = "+bloodSugarChart+" where type = 3 and rptId ="+rptId) ;
			}else if(type.equals("4")){
				//修改餐后血糖
				this.updateBySql("update rpt_monitoring_data set defaultPic = "+bloodSugar2hChart+" where type = 4 and rptId ="+rptId) ;				
			}else if(type.equals("5")){			
				Electrocardiogram  tmp = electrocardiogramDao.lod(Integer.valueOf(electrocardiogram)) ;
				JSONObject json = new JSONObject();			
				json.put("electrocardiogram", JsonUtils.getJsonString4JavaPOJO(tmp,JsonUtils.LONG_DATE_PATTERN));
				
				String tmpshuju = json.toString() ;
				//修改心电数据
				this.updateBySql("update rpt_monitoring_data set pic1 = '"+tmpshuju+"' where type = 5 and rptId ="+rptId) ;
			}
			
	}
	
	public void deleteMonDataByRptId(Integer rptId){
		String sql = "delete from rpt_monitoring_data where rptId = ?";
		deleteBySql(sql, rptId);
	}
}
