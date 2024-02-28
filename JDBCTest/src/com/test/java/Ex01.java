package com.test.java;

import java.sql.Connection;
import java.sql.DriverManager;

public class Ex01 {
	public static void main(String[] args) {
		System.out.println("[데이터베이스 접속하기]");
		
		Connection conn = null;
		
		//접속 정보 > 연결 문자열(Connection String)
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "hr";
		String pw = "java1234";
		
		//JDBC 작업 > 외부 입출력 > 예외 처리 필수
		try {
			
			//JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//DB 연결
			//- DB 접속 성공
			//- 접속 정보를 가지고 있는 Connection 객체 반환
			conn = DriverManager.getConnection(url, id, pw); //getConnection에서 DB 연결이 진행된다.
			
			//현재 오라클의 접속 상태
			//- 접속X > true
			//- 접속O > fasle(실제로 오라클에 접속된 것이다.)
			System.out.println(conn.isClosed()); 
			
			//업무 진행 ~ Query!!
			System.out.println("업무 진행");
			
			//접속 종료
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Ex01.main");
			e.printStackTrace();
		}
		
	} //main
} //Ex01