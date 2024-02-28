package com.test.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Ex05_PreparedStatement {
	
	public static void main(String[] args) {
		
		//Ex05_PreparedStatement
		
		m1();
	} //main

	private static void m1() {
		
		//PreparedStatement
		//- 매개 변수 지원
		//- Statement > 정적 sql
		//- PreparedStatement > 동적 sql
		
		//insert
		String name = "하하하";
		String age = "25";
		String gender = "m";
		String tel = "010-1234-5678";
		String address = "서울시 강동구 천호동";
		
		Connection conn = null;
		Statement stat = null;
		PreparedStatement pstat = null;
		
		try {
			
			conn = DBUtil.open();
			
			//1. Statement
//			stat = conn.createStatement();
//			
//			String sql = String.format("insert into tblAddress (seq, name, age, gender, tel, address, regdate) values (seqAddress.nextVal, '%s', %s, '%s', '%s', '%s', default)", name, age, gender, tel, address);
//			
//			int result = stat.executeUpdate(sql);
//			
//			System.out.println(result);
			
			//2. PreparedStatement
			//- ? : 오라클의 매개변수
			String sql = "insert into tblAddress (seq, name, age, gender, tel, address, regdate) values (seqAddress.nextVal, ? , ?, ?, ?, ? , default)";
			
			pstat = conn.prepareStatement(sql);
			
			//? 채우기
			pstat.setString(1, name); // 자료형은 자바
			pstat.setString(2, age);
			pstat.setString(3, gender);
			pstat.setString(4, tel);
			pstat.setString(5, address);
			
			//인덱스에서 누락된 IN 또는 OUT 매개변수:: 1 > 인덱스 1번부터 에러
			int result = pstat.executeUpdate();
			
			System.out.println(result);
			
		} catch (Exception e) {
			System.out.println("Ex05_PreparedStatement.m1");
			e.printStackTrace();
		}
		
	}
}
