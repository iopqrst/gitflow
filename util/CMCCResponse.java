package com.bskcare.ch.util;

/**
 * 移动接口响应
 * @author Administrator
 *
 */
public class CMCCResponse {

	/**
	 * 接口返回失败
	 */
	public static final String STATUS_FAIL = "0";
	/**
	 * 接口返回成功
	 */
	public static final String STATUS_SUCCESS = "1";

	/**
	 * 返回状态
	 */
	private String status;

	private String errcode;

	private String errmsg;

	public CMCCResponse() {
	}

	public CMCCResponse(String status) {
		this.status = status;
	}

	public boolean isSuccessed() {
		if (null != status && STATUS_SUCCESS.equals(status)) {
			return true;
		}
		return false;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	@Override
	public String toString() {
		return "CMCCResponse [status=" + status + ", errcode=" + errcode
				+ ", errmsg=" + errmsg + "]";
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
