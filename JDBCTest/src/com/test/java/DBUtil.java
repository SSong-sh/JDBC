package com.test.java;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

	public static Connection open() {

		Connection conn;

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "hr";
		String pw = "java1234";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection(url, id, pw);
			//conn.setAutoCommit(false); //기본값이 true로 설정되어 있어서 commit을 하지 않아도 바로 확인이 가능
			
			return conn;
		} catch (Exception e) {
			System.out.println("DBUtil.open");
			e.printStackTrace();
		}

		return null;
	} // open

	
	
	//오버로딩
	public static Connection open(String host, String id, String pw) {

		Connection conn;

		String url = "jdbc:oracle:thin:@" + host + ":1521:xe";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection(url, id, pw);

			return conn;
		} catch (Exception e) {
			System.out.println("DBUtil.open");
			e.printStackTrace();
		}

		return null;
	} // open
} // DBUtil
