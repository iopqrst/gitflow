package com.bskcare.ch.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.service.CrmOrderService;
import com.bskcare.ch.service.ProductService;
import com.bskcare.ch.util.CrmResponse;
import com.bskcare.ch.util.CrmURLConfig;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.HttpClientUtils;
import com.bskcare.ch.vo.Product;
import com.bskcare.ch.vo.order.OrderProduct;

@Service
public class CrmOrderServiceImpl implements CrmOrderService {

	@Autowired
	private ProductService productService;
	
	public int syncOrder(OrderProduct orderProduct,String cardid) {
		
		String url = CrmURLConfig.getString("crm_base_url")
//		String url = "http://192.168.1.167:8080/kfcrm/"
				+ CrmURLConfig.getString("crm_sync_order");

		HashMap<String, String> parmas = new HashMap<String, String>();
		parmas.put("buyInfo.amount",orderProduct.getProductCount()+"");
		parmas.put("buyInfo.buytime",DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN,orderProduct.getCreateTime()));
		parmas.put("buyInfo.lastDate",DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN,orderProduct.getExpiresTime()));
		parmas.put("buyInfo.unitPrice",orderProduct.getProductPrice()+"");
		parmas.put("buyInfo.clientId",orderProduct.getClientId()+"");
		parmas.put("buyInfo.cardid",cardid);
		parmas.put("buyInfo.cardTypeName",productService.load(orderProduct.getProductId()).getName());
//		productService.load(orderProduct.getProductId()).getName();
		System.out.println("与crm同步用户购买的产品卡接口");
		CrmResponse crs = HttpClientUtils.getCrmResponse(url, parmas);
		if(crs!=null){
			return Integer.parseInt(crs.getCode());
		}
		return 0;

	}

}
