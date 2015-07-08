package com.bskcare.ch.util;

import java.util.Random;

public class Secretkey {
	
	/***
	 * 明文加密
	 * @return
	 */
	public static String encryption(String password){
		String tmps = "" ;
		for (int i = 0; i < 3; i++) {
			Random rd=new Random();int m=rd.nextInt(24);//生成0-23的随机数
			String s="abcdefghijklmnopqrstuvwxyz";
			tmps = tmps+s.charAt(m);//根据随机的索引获得随机的字符
		}
		StringBuffer  sb = new StringBuffer(password);   
		sb.reverse();
		tmps = tmps+sb.toString() ;
		for (int i = 0; i < 3; i++) {
			Random rd=new Random();
			int m=rd.nextInt(79)+10;//生成0-23的随机数
			tmps = tmps +m ;
		}		
		return tmps ;
	}
	/**
	 * 明文解密
	 * @return
	 */
	public static String deciphering(String password){
		if(password!=null&&!password.equals("")){
			String aaa = password.substring(3, password.length()-6) ;
			StringBuffer  sb = new StringBuffer(aaa);   
			sb.reverse();
			return sb.toString() ;			
		}else{
			return "" ;			
		}
	}	
}
