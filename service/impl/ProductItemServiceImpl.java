package com.bskcare.ch.service.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ProductItemDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ProductItemService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.ProductItem;

/**
 * 产品项目
 * @author houzhiqing
 *
 */
@Service("productItemService")
public class ProductItemServiceImpl implements ProductItemService {
	
	@Autowired
	private ProductItemDao productItemDao;

	public ProductItem add(ProductItem t) {
		return productItemDao.add(t);
	}

	public void update(ProductItem t) {
		productItemDao.update(t);
	}

	public void delete(int id) {
		ProductItem productItem = load(id);
		productItem.setStatus(Constant.STATUS_UNNORMAL);
		productItemDao.update(productItem);
	}

	public ProductItem load(int id) {
		return productItemDao.load(id);
	}

	public List<ProductItem> executeFind(ProductItem t) {
		return productItemDao.executeFind(t);
	}
	
	public PageObject<ProductItem> queryObjects(ProductItem t, QueryInfo info) {
		return productItemDao.queryObjects(t,info);
	}
	
	public String queryStringObjects(ProductItem t, QueryInfo info) {
		PageObject<ProductItem> po = productItemDao.queryObjects(t,info);
		List<ProductItem> list = po.getDatas();
		
		JSONObject jo = new JSONObject();
		jo.put("total", po.getTotalRecord());
		
		JSONArray ja = new JSONArray();
		for (ProductItem productItem : list) {
			productItem.setCreateTime(null);
			ja.add(JsonUtils.getJsonString4JavaPOJO(productItem,"yyyy-MM-dd HH:mm:ss"));
		}
		jo.put("productItemList", ja.toString());
		
		System.out.println("------------------" + jo.toString());
		return jo.toString();
	}

	public List<Object> queryItemsByPackageId(Integer packageId) {
		return productItemDao.queryItemsByPackageId(packageId);
	}
	
	public List<Object> queryServiceItems(Integer productId, int type) {
		return productItemDao.queryServiceItems(productId, type);
	}

	public String queryStringItemsByPId(Integer productId, int type) {
		List<Object> itmsList = productItemDao.queryServiceItems(productId,type);
		
		JSONArray ja = new JSONArray();
		for (Object obj : itmsList) {
			JSONObject joo = new JSONObject();
			ProductItem item = (ProductItem)((Object[])obj)[0];
			int quantity = (Integer)(((Object[])obj)[1]);
			//Integer packageId = (Integer)(((Object[])obj)[2]);
			joo.put("iname", item.getName());
			joo.put("quantity", quantity);
			ja.add(joo);
		}
		return ja.toString();
	}

}
