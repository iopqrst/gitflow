package com.bskcare.ch.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;

public class TableAndTable {

	// 驱动程序就是之前在classpath中配置的JDBC的驱动程序的JAR 包中
	public static final String oDBDRIVER = "com.mysql.jdbc.Driver";
	// 连接地址是由各个数据库生产商单独提供的，所以需要单独记住
	public static final String oDBURL = "jdbc:mysql://58.83.224.76:3306/bskcare";
	
//	public static final String oDBURL = "jdbc:mysql://123.56.92.44:3306/bskcare";
	// 连接数据库的用户名
	public static final String oDBUSER = "bskcare_root";
	// 连接数据库的密码
//	public static final String oDBPASS = "bsk&care+365*24";
	
	public static final String oDBPASS = "1qaz@WSX3edc";
	

	/***
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>下面为测试数据库<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	 * <<<<<<<<<<<
	 ****/
	// 驱动程序就是之前在classpath中配置的JDBC的驱动程序的JAR 包中
	public static final String nDBDRIVER = "com.mysql.jdbc.Driver";
	// 连接地址是由各个数据库生产商单独提供的，所以需要单独记住
	public static final String nDBURL = "jdbc:mysql://123.56.92.44:3306/bskcare";
	// 连接数据库的用户名
	public static final String nDBUSER = "bskcare_root";
	// 连接数据库的密码
	public static final String nDBPASS = "bsk&care+365*24";

	public static void main(String[] args) throws Exception {
		TableAndTable tableAndTable = new TableAndTable();
		HashMap<String, HashMap<String, TableInfo>> old = tableAndTable.getMap(
				oDBDRIVER, oDBURL, oDBUSER, oDBPASS);
		HashMap<String, HashMap<String, TableInfo>> newT = tableAndTable
				.getMap(nDBDRIVER, nDBURL, nDBUSER, nDBPASS);
		// 测试数据库对比正式数据库
		tableAndTable.handleMap(old, newT, "正式", "测试");
		System.out
				.println(">>>>>>>>>>>>>>>>>以下是正式服务器检测测试服务器<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		// 正式服务器对比测试服务器
		tableAndTable.handleMap(newT, old, "测试", "正式");

	}

	public HashMap<String, HashMap<String, TableInfo>> getMap(String d,
			String b, String u, String p) throws ClassNotFoundException,
			SQLException {
		Connection con = null; // 表示数据库的连接对象
		Statement stmt = null;
		Statement stmt1 = null;
		ResultSet rs1 = null;
		ResultSet rs = null;

		HashMap<String, HashMap<String, TableInfo>> map = new HashMap<String, HashMap<String, TableInfo>>();
		;

		try {
			Class.forName(d); // 1、使用CLASS 类加载驱动程序
			con = DriverManager.getConnection(b, u, p); // 2、连接数据库
			stmt = con.createStatement(); // 3、Statement 接口需要通过Connection
											// 接口进行实例化操作
			stmt1 = con.createStatement(); // 3、Statement 接口需要通过Connection
											// 接口进行实例化操作

			rs = stmt.executeQuery("show TABLES");

			while (rs.next()) {
				// System.out.println(rs.getString(1));
				rs1 = stmt1
						.executeQuery("show columns from " + rs.getString(1));

				HashMap<String, TableInfo> tmap = new HashMap<String, TableInfo>();
				while (rs1.next()) {
					TableInfo tableInfo = new TableInfo();
					tableInfo.setMyfield(rs1.getString("Field"));
					tableInfo.setMyExtra(rs1.getString("Extra"));
					tableInfo.setMynull(rs1.getString("Null"));
					tableInfo.setMyKey(rs1.getString("Key"));
					tableInfo.setMydefault(rs1.getString("Default"));
					tableInfo.setMytype(rs1.getString("Type"));

					tmap.put(rs1.getString("Field"), tableInfo);
				}
				map.put(rs.getString(1), tmap);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			rs.close();
			rs1.close();
			stmt.close();
			stmt1.close();
			con.close(); // 4、关闭数据库
		}

		return map;

	}

	public void handleMap(HashMap<String, HashMap<String, TableInfo>> old,
			HashMap<String, HashMap<String, TableInfo>> newt, String official,
			String test) {
		System.out.println(official + "服务器表数量:" + old.size() + "===" + test
				+ "服务器表数量:" + newt.size());

		for (Entry<String, HashMap<String, TableInfo>> entry : newt.entrySet()) {
			// System.out.println(entry.getKey() + ":" + entry.getValue() +
			// "\t");
			if (old.get(entry.getKey()) != null) {

			} else {
				if(!entry.getKey().startsWith("v_")) {
					System.out.println("" + test + "数据库比" + official + "数据库多出来的表:"
						+ entry.getKey());
				}
			}
		}

		for (Entry<String, HashMap<String, TableInfo>> entry : newt.entrySet()) {
			// System.out.println(entry.getKey() + ":" + entry.getValue() +
			// "\t");
			if (old.get(entry.getKey()) != null) {
				String tableName = entry.getKey();
				HashMap<String, TableInfo> nTableFile = newt.get(tableName);
				HashMap<String, TableInfo> oTableFile = old.get(tableName);

				// 找到字段
				for (Entry<String, TableInfo> entry1 : nTableFile.entrySet()) {
					String field = entry1.getKey();
					TableInfo ntableInfo = entry1.getValue();

					TableInfo otableInfo = oTableFile.get(field);
					// "+official+"数据库中没有该字段
					if (otableInfo == null) {
						System.out.println("" + official + "数据库没有该字段>>>>表:"
								+ tableName + "字段:" + field);
					} else {
						if ((ntableInfo.getMydefault() + "").equals((otableInfo
								.getMydefault() + ""))) {

						} else {
							System.out.println("" + test + "数据库表:" + tableName
									+ "字段:" + field + "属性default不同?" + test
									+ "服务器:" + ntableInfo.getMydefault() + ""
									+ official + "数据库:"
									+ otableInfo.getMydefault());
						}
						if (ntableInfo.getMyExtra().equals(
								otableInfo.getMyExtra())) {

						} else {
							System.out.println("" + test + "数据库表:" + tableName
									+ "字段:" + field + "属性Extra不同?" + test
									+ "服务器:" + ntableInfo.getMyExtra() + ""
									+ official + "数据库:"
									+ otableInfo.getMyExtra());
						}
						if (ntableInfo.getMyfield().equals(
								otableInfo.getMyfield())) {

						} else {
							System.out.println("" + test + "数据库表:" + tableName
									+ "字段:" + field + "属性field不同?" + test
									+ "服务器:" + ntableInfo.getMyfield() + ""
									+ official + "数据库:"
									+ otableInfo.getMyfield());
						}
						if (ntableInfo.getMyKey().equals(otableInfo.getMyKey())) {

						} else {
							System.out
									.println("" + test + "数据库表:" + tableName
											+ "字段:" + field + "属性Key不同?" + test
											+ "服务器:" + ntableInfo.getMyKey()
											+ "" + official + "数据库:"
											+ otableInfo.getMyKey());
						}
						if (ntableInfo.getMynull().equals(
								otableInfo.getMynull())) {

						} else {
							System.out.println("" + test + "数据库表:" + tableName
									+ "字段:" + field + "属性null不同?" + test
									+ "服务器:" + ntableInfo.getMynull() + ""
									+ official + "数据库:"
									+ otableInfo.getMynull());
						}
						if (ntableInfo.getMytype().equals(
								otableInfo.getMytype())) {

						} else {
							System.out.println("" + test + "数据库表:" + tableName
									+ "字段:" + field + "属性type不同?" + test
									+ "服务器:" + ntableInfo.getMytype() + ""
									+ official + "数据库:"
									+ otableInfo.getMytype());
						}
					}
				}

			} else {
				// System.out.println(""+test+"数据库比"+official+"数据库多出来的表:"+entry.getKey());
			}
		}

	}

	public class TableInfo {
		private String myfield;
		private String mytype;
		private String mynull;
		private String mydefault;
		private String myExtra;
		private String myKey;

		public String getMyKey() {
			return myKey;
		}

		public void setMyKey(String myKey) {
			this.myKey = myKey;
		}

		public String getMyfield() {
			return myfield;
		}

		public void setMyfield(String myfield) {
			this.myfield = myfield;
		}

		public String getMytype() {
			return mytype;
		}

		public void setMytype(String mytype) {
			this.mytype = mytype;
		}

		public String getMynull() {
			return mynull;
		}

		public void setMynull(String mynull) {
			this.mynull = mynull;
		}

		public String getMydefault() {
			return mydefault;
		}

		public void setMydefault(String mydefault) {
			this.mydefault = mydefault;
		}

		public String getMyExtra() {
			return myExtra;
		}

		public void setMyExtra(String myExtra) {
			this.myExtra = myExtra;
		}

		@Override
		public String toString() {
			return "TableInfo [myExtra=" + myExtra + ", myKey=" + myKey
					+ ", mydefault=" + mydefault + ", myfield=" + myfield
					+ ", mynull=" + mynull + ", mytype=" + mytype + "]";
		}

	}

}
