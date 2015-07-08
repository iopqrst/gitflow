package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ServiceExpenseDao;
import com.bskcare.ch.vo.order.ServiceExpense;

@Repository
public class ServiceExpenseDaoImpl extends BaseDaoImpl<ServiceExpense>
		implements ServiceExpenseDao {

	@SuppressWarnings("unchecked")
	public List<ServiceExpense> quertServiceExpense(Integer cid, String items) {
		String hql = "from ServiceExpense where clientId = ? and osId in ("+items+") and status = ? order by expenseTime desc";
		ArrayList args = new ArrayList();
		args.add(cid);
		//args.add(ArrayUtils.join(items.toArray()));
		args.add(ServiceExpense.STATUS_SUC);
		return executeFind(hql, args.toArray());
	}

}
