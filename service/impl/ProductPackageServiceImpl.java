package com.bskcare.ch.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.PackageVsItemDao;
import com.bskcare.ch.dao.ProductPackageDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ProductPackageService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.PackageVsItem;
import com.bskcare.ch.vo.ProductPackage;

/**
 * 产品套餐
 * @author houzhiqing
 *
 */
@Service("productPackageService")
public class ProductPackageServiceImpl implements ProductPackageService {
	
	protected transient final Logger log = Logger.getLogger(ProductPackageServiceImpl.class);
	
	@Autowired
	private ProductPackageDao productPackageDao;
	
	@Autowired
	private PackageVsItemDao packageVsItemDao;
	
	public ProductPackage add(ProductPackage t, String itemsInfo) {
		
		if(null == t || StringUtils.isEmpty(itemsInfo)) {
			return null;
		}
		
		t.setCreateTime(new Date());
		ProductPackage pp = productPackageDao.add(t);
		
		createPackageItems(itemsInfo, pp);
		
		return pp;
	}

	/**
	 * 添加套餐对应项目
	 */
	private void createPackageItems(String itemsInfo, ProductPackage pp) {
		String[] items = itemsInfo.split(",");
		for (String string : items) {
			String[] item = string.split("@");
			
			if(null != item && item.length == 2){

				if(log.isDebugEnabled()) {
					log.debug(">>>>>>>>>>>>>>>>>>>" 
							+ pp.toString() + " , item.id = " + item[0] 
							+ ", item.count = " + item[1]);
				}
				
				PackageVsItem pvi = new PackageVsItem(pp.getId(), 
						Integer.parseInt(item[0]), 
						Integer.parseInt(item[1]));
				
				packageVsItemDao.add(pvi);
			} 
		}
	}

	public void update(ProductPackage t, String itemsInfo)  {
		
		if(null == t || StringUtils.isEmpty(itemsInfo)) {
			//TODO log
			return ;
		}
		
		ProductPackage pp = load(t.getId());
		pp.setModifyTime(new Date());
		pp.setName(t.getName());
		pp.setPrice(t.getPrice());
		pp.setRemark(t.getRemark());
		
		packageVsItemDao.deleteByPackageId(t.getId());
		productPackageDao.update(pp);
		createPackageItems(itemsInfo, pp);
	}

	public void delete(int id) {
		ProductPackage pp = productPackageDao.load(id);
		pp.setStatus(Constant.STATUS_UNNORMAL);
		pp.setModifyTime(new Date());
		productPackageDao.update(pp);
	}

	public ProductPackage load(int id) {
		return productPackageDao.load(id);
	}

	public List<ProductPackage> executeFind(ProductPackage t) {
		return productPackageDao.executeFind(t);
	}
	
	public PageObject<ProductPackage> queryObjects(ProductPackage t, QueryInfo info) {
		return productPackageDao.queryObjects(t,info);
	}
	
	public String queryStringObjects(ProductPackage t, QueryInfo info) {
		PageObject<ProductPackage> po = productPackageDao.queryObjects(t,info);
		List<ProductPackage> list = po.getDatas();
		
		JSONObject jo = new JSONObject();
		jo.put("total", po.getTotalRecord());
		
		JSONArray ja = new JSONArray();
		for (ProductPackage productItem : list) {
			ja.add(JsonUtils.getJsonString4JavaPOJO(productItem,"yyyy-MM-dd HH:mm:ss"));
		}
		jo.put("productPackageList", ja.toString());
		
		System.out.println("------------------" + jo.toString());
		return jo.toString();
	}

}
