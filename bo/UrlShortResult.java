package com.bskcare.ch.bo;

import java.io.Serializable;


public class UrlShortResult implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String url_short;
	private String url_long;
	private int type;
	public String getUrl_short() {
		return url_short;
	}
	public void setUrl_short(String urlShort) {
		url_short = urlShort;
	}
	public String getUrl_long() {
		return url_long;
	}
	public void setUrl_long(String urlLong) {
		url_long = urlLong;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
