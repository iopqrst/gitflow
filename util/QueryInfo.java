package com.bskcare.ch.query.util;

import java.io.Serializable;

/**
 * @author houzhiqing
 */
public class QueryInfo implements Serializable {

	/** 排序方式: asc */
	public static final String ORDER_ASC = "ASC";
	/** 排序方式: desc */
	public static final String ORDER_DESC = "DESC";

	private static final long serialVersionUID = 961699786815859106L;

	private Integer pageSize = 10;
	private Integer pageOffset = 1;
	private String sort;
	private String order;
//	private Integer currentPage;

	public QueryInfo(Integer pageSize, String sort,
			String order, Integer pageOffset) {
		this.pageSize = pageSize;
		this.sort = sort;
		this.order = order;
//		this.currentPage = currentPage;
		this.pageOffset = pageOffset;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageOffset() {
//		if (null != currentPage && null != pageSize) {
//			return (currentPage - 1) * pageSize;
//		}
		return pageOffset;
	}

	public void setPageOffset(Integer pageOffset) {
		this.pageOffset = pageOffset;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public QueryInfo() {
		super();
	}

//	public Integer getCurrentPage() {
//		return currentPage;
//	}
//
//	public void setCurrentPage(Integer currentPage) {
//		this.currentPage = currentPage;
//	}

}
