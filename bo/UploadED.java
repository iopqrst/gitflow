package com.bskcare.ch.bo;

import java.util.Date;

public class UploadED {
	private static final long serialVersionUID = 1L;
	private String myfile ;  //数据流
	private String upfileFileName;//文件名称
	private Date upLoadDate  ;//上传时间
	private String type ; 
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMyfile() {
		return myfile;
	}
	public void setMyfile(String myfile) {
		this.myfile = myfile;
	}
	public String getUpfileFileName() {
		return upfileFileName;
	}
	public void setUpfileFileName(String upfileFileName) {
		this.upfileFileName = upfileFileName;
	}
	public Date getUpLoadDate() {
		return upLoadDate;
	}
	public void setUpLoadDate(Date upLoadDate) {
		this.upLoadDate = upLoadDate;
	}
	
	
}
