package com.bskcare.ch.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.ProductExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ProductDao;
import com.bskcare.ch.dao.ProductItemDao;
import com.bskcare.ch.dao.ProductVsPackageDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ProductService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.Product;
import com.bskcare.ch.vo.ProductItem;
import com.bskcare.ch.vo.ProductVsPackage;

/**
 * 综合产品
 * 
 * @author houzhiqing
 * 
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductVsPackageDao pvpDao;
	@Autowired
	private ProductItemDao itemDao;

	public Product add(Product t, Integer[] packageIds) {
		if (null == t || (t != null && t.getCategory() == 1 && (null == packageIds || packageIds.length == 0)))
			return null;

		t.setCreateTime(new Date());
		t.setStatus(Constant.STATUS_NORMAL);
		Product p = productDao.add(t);
		
		if(packageIds != null &&  packageIds.length > 0)
			addRelations(packageIds, p.getId());
		
		return p;
	}

	public void update(Product t, Integer[] packageIds) {
		if (null == t || (t != null && t.getCategory() == 1 && (null == packageIds || packageIds.length == 0)))
			return ;
		
		Product primary = productDao.load(t.getId());
		
		BeanUtils.copyProperties(t, primary, new String[] { "createTime",
				"imageUrl", "creator" });
		primary.setModifyTime(new Date());
		
		if (!StringUtils.isEmpty(t.getImageUrl()))
			primary.setImageUrl(t.getImageUrl());

		// 1. 删除之前关系 2.重新建立新关系
		pvpDao.deleteByProductId(primary.getId());
		productDao.update(primary);
		
		if(packageIds != null &&  packageIds.length > 0)
			addRelations(packageIds, primary.getId());
	}

	/**
	 * 创建产品与套餐的关系
	 */
	private void addRelations(Integer[] packageIds, Integer pId) {
		for (Integer packageId : packageIds) {
			pvpDao.add(new ProductVsPackage(pId, packageId));
		}
	}

	public void delete(Integer id) {
		Product p = productDao.load(id);
		p.setStatus(Constant.STATUS_UNNORMAL);
		p.setModifyTime(new Date());
		productDao.update(p);
	}

	public Product load(Integer id) {
		return productDao.load(id);
	}

	public List<Product> executeFind(Product t) {
		return productDao.executeFind(t);
	}

	public PageObject<Product> queryObjects(Product t, QueryInfo info) {
		return productDao.queryObjects(t, info);
	}

	public String queryStringObjects(Product t, QueryInfo info) {
		PageObject<Product> po = productDao.queryObjects(t, info);
		List<Product> list = po.getDatas();

		JSONObject jo = new JSONObject();
		jo.put("total", po.getTotalRecord());

		JSONArray ja = new JSONArray();
		for (Product product : list) {
			product.setCreateTime(null);
			ja.add(JsonUtils.getJsonString4JavaPOJO(product,
					"yyyy-MM-dd HH:mm:ss"));
		}
		jo.put("productList", ja.toString());

		System.out.println("------------------" + jo.toString());
		return jo.toString();
	}

	public String queryPackagesByPId(Integer id) {
		List<ProductVsPackage> pvpList = pvpDao.executeFind(id);
		String packages = "";
		if(!CollectionUtils.isEmpty(pvpList)) {
			for (int i = 0; i < pvpList.size(); i++) {
				packages += pvpList.get(i).getPackageId();
				if(i != pvpList.size() -1) {
					packages += ",";
				}
			}
		}
		return packages;
	}

	public List<Product> queryProductByIds(String pIds) {
		return productDao.queryProductByIds(pIds);
	}
	
	/**
	 * 查询所有可用产品，并且包好
	 * @return
	 */
	@Deprecated
	public String queryPrductVsItems(Product product) {
		
		List<Product> list = productDao.executeFind(product);
		
		JSONArray aja = new JSONArray();
		if(!list.isEmpty()) {
			for (Product p : list) {
				List<Object> itmsList = itemDao.queryServiceItems(p.getId(),0);
				
				JSONObject jo = new JSONObject();
				jo.put("pId", p.getId());
				jo.put("pname", p.getName());
				jo.put("price", p.getCurrentPrice());
				
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
				jo.put("items",ja);
				aja.add(jo);
			}
		}
		
		return aja.toString();
	}
	
	public String queryStringOfProduct(Product product) {
		List<Product> list = productDao.executeFind(product);
		return JsonUtils.getJsonString4JavaListDate(list,
				new String[]{"introduction","description","imageUrl"});
	}

	public List<ProductExtend> queryProduct(Product t){
		return productDao.queryProduct(t);
	}
	
	
	public String queryProductList(){
		List<Product> lst = productDao.queryProductList();
		if(!CollectionUtils.isEmpty(lst)){
			JSONArray ja = new JSONArray();
			for (Product product : lst) {
				JSONObject jo = new JSONObject();
				jo.put("pid", product.getId());
				jo.put("name", product.getName());
				jo.put("price", product.getCurrentPrice());
				ja.add(jo.toString());
			}
			return JsonUtils.encapsulationJSON(1, "查询成功", ja.toString()).toString();
		}
		return JsonUtils.encapsulationJSON(1, "没有可以购买的服务", "").toString();
	}
}
