package com.bskcare.ch.bo;

import com.bskcare.ch.poi.util.ExcelResources;

public class ProductExcel {
	
	private String code;
	private String codePwd;
	private String name;
	
	@ExcelResources(title="产品卡号", order=1)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@ExcelResources(title="产品卡号密码", order = 2)
	public String getCodePwd() {
		return codePwd;
	}
	public void setCodePwd(String codePwd) {
		this.codePwd = codePwd;
	}
	
	@ExcelResources(title="产品信息", order = 3)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
