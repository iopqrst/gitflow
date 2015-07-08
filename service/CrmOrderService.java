package com.bskcare.ch.service;

import com.bskcare.ch.vo.order.OrderProduct;

public interface CrmOrderService {
	public int syncOrder(OrderProduct orderProduct,String cardid);
}
