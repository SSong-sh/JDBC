package com.test.java;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import oracle.jdbc.OracleTypes;

public class Ex06_CallableStatement {
	
	public static void main(String[] args) {
		
		//Ex06_CallableStatement.java
		
		//m1();
		//m2();
		//m3();
		//m4();
		m5();
		
	}//main

	private static void m5() {
		
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "{ call procM5( ?, ?) }"; 
			
			stat = conn.prepareCall(sql);
			
			stat.setString(1,"영업부");
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			
			stat.executeQuery();
			
			//out > 오라클 커서 = 결과셋 = jdbc resultset
			
			rs = (ResultSet) stat.getObject(2);
			
			while (rs.next()) {
				
				System.out.println(rs.getString("name"));
			}
			
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Ex06_CallableStatement.temp");
			e.printStackTrace();
		}
		
	}

	private static void m4() {
		
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "{ call procM4(?,?,?,?,?) }";
			
			stat = conn.prepareCall(sql);
			
			//in
			stat.setString(1, "1001");
			
			//out
			stat.registerOutParameter(2, OracleTypes.VARCHAR);
			stat.registerOutParameter(3, OracleTypes.VARCHAR);
			stat.registerOutParameter(4, OracleTypes.VARCHAR);
			stat.registerOutParameter(5, OracleTypes.VARCHAR);
			
			stat.executeQuery();
			
			System.out.println(stat.getString(2)); //인덱스만 사용가능함
			System.out.println(stat.getString(3)); 
			System.out.println(stat.getString(4)); 
			System.out.println(stat.getString(5)); 
			
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Ex06_CallableStatement.temp");
			e.printStackTrace();
		}
		
	}

	private static void m3() {
		
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "{ call procM3(?)}";
			
			stat = conn.prepareCall(sql);
			
			//in parameter
			//stat.setString(1, 값);
			
			
			//out parameter
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			
			stat.executeQuery(); //resultSet을 안 받음
			
			//PLSQL 문에서 인출을 수행할 수 없습니다.: next
			//System.out.println(rs.next());
			
			// 쿼리를 실행할때는 stat.get메서드 호출한 다음에 사용 
			int count = stat.getInt(1);
			System.out.println(count);
			
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Ex06_CallableStatement.temp");
			e.printStackTrace();
		}
		
	}

	private static void m2() {
		
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "{ call procM2(?, ?, ?, ?, ?) }";
			
			stat = conn.prepareCall(sql);
			
			stat.setString(1, "후후후");
			stat.setString(2, "22");
			stat.setString(3, "m");
			stat.setString(4, "010-3213-3212");
			stat.setString(5, "서울시");
			
			int result = stat.executeUpdate();
			System.out.println(result);
			
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Ex06_CallableStatement.temp");
			e.printStackTrace();
		}
		
	}

	private static void m1() {
		
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtil.open();
			
			String sql = "{ call procM1 }";
			
			stat = conn.prepareCall(sql); //매개변수 처리 기능 보유
			
			int result = stat.executeUpdate();
			System.out.println(result);
			
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Ex06_CallableStatement.temp");
			e.printStackTrace();
		}
		
	}
	
	private static void temp() {
		
		Connection conn = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtil.open();
			
			
			
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Ex06_CallableStatement.temp");
			e.printStackTrace();
		}
		
	}

}


















