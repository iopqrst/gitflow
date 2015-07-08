package com.bskcare.ch.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CrmResponse {
	
	/**
	 * 接口返回失败
	 */
	public static final String CODE_FAIL = "0";
	/**
	 * 接口返回成功
	 */
	public static final String CODE_SUCCESS = "1";

	/**
	 * 返回状态
	 */
	private String code;

	private String msg;

	private JSONObject data;//单个json对象
	
	private JSONArray arrayData;//数组json对象

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CrmResponse [arrayData=" + arrayData + ", code=" + code
				+ ", data=" + data + ", msg=" + msg + "]";
	}

	public boolean isSuccessed() {
		if(null != code && CODE_SUCCESS.equals(code)){
			return true;
		}
		return false;
	}
	
	public boolean isDataEmpty() {
		if(null != data && !data.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean isArrayDataEmpty() {
		if(null != arrayData && !arrayData.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public JSONArray getArrayData() {
		return arrayData;
	}

	public void setArrayData(JSONArray arrayData) {
		this.arrayData = arrayData;
	}
	
	public static void main(String[] args) {
		JSONObject jo = new JSONObject();
//		jo.accumulate("1", "1");
		System.out.println(jo.toString());
		
		JSONArray ja = new JSONArray();
		System.out.println(ja.toString());
		
		System.out.println(jo.isEmpty());
		System.out.println(ja.isEmpty());
	}
}
