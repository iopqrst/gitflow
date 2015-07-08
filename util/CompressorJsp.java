package com.bskcare.ch.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class CompressorJsp {

	private static List<String> jspLists = new ArrayList<String>();
	
	
	public static void doCompressor(String path) {
		
		getFileList(path);
		
		System.out.println(jspLists.size());
		
		try {
			for(int i = 0; i < jspLists.size() ; i++) {
				String content = FileUtils.readFileToString(new File(jspLists.get(i)), "utf-8");
				
				String cjsp = HtmlCompressor.compress(content);

				//System.out.print(cjsp);
				FileUtils.writeStringToFile(new File(jspLists.get(i)), cjsp, "utf-8");
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取陌路下的所有jsp文件
	 * @param strPath
	 */
	private static void getFileList(String strPath) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();

		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				getFileList(files[i].getAbsolutePath());
			} else {
				String strFileName = files[i].getAbsolutePath().toLowerCase();

				if (strFileName.endsWith(".jsp")) {
					jspLists.add(files[i].getAbsolutePath());
					continue;
				}

			}
		}
	}
	
	public static void main(String[] args) {
		doCompressor("C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\");
	}

	
}