package com.test.java;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class Ex03_Statement {
	public static void main(String[] args) {

		//m1();
		//m2(); //insert
		//m3(); //update
		//m4(); //delete
		//m5(); //create
		m6(); //UI + SQL + 데이터 사용자 입력 + 실행

	} // main

	private static void m6() {
		
		//UI + SQL + 데이터 사용자 입력 + 실행
		
		Scanner scan = new Scanner(System.in);
		
		//*** 자바의 자료형과 오라클 자료형은 !아무! 상관없다.
		
		System.out.print("이름: "); //varchar2	
		String name = scan.nextLine();
		
		System.out.print("나이: "); //number
		String age = scan.nextLine(); //number와 int가 호환될 거 같지만 아니기 때문에 사용하기 편하게 문자열로 받는다. 오라클에 집어 넣을 때만 number로 넣으면 되기 때문에 전혀 상관없다. 
		
		System.out.print("성별(m,f): ");
		String gender = scan.nextLine();
		
		System.out.print("전화번호: ");
		String tel = scan.nextLine();
		
		System.out.print("주소: ");
		String address = scan.nextLine();
		address = address.replace("'", "''"); //"'"가 있으면 에러가 발생해서 처리해준다.
		
		Connection conn = null;
		Statement stat = null;

		try {

			conn = DBUtil.open();

			if (!conn.isClosed()) {
				//String sql = "insert into tblAddress (seq, name, age, gender, tel, address, regdate) values (seqAddress.nextVal, '" + name + "'," + age + ", '" + gender + "', '" + tel + "', '" + address + "', default)"; \
				String sql = String.format("insert into tblAddress (seq, name, age, gender, tel, address, regdate) values (seqAddress.nextVal, '%s', %s, '%s', '%s', '%s', default)", name, age, gender, tel, address);
				
				stat = conn.createStatement(); 
				
				int result = stat.executeUpdate(sql);
				
				if (result == 1) {
					System.out.println("등록 성공");
				} else {
					System.out.println("등록 실패");
				}

				stat.close();
				conn.close();

			} else {
				System.out.println("DB 접속 실패");
			}

		} catch (Exception e) {
			System.out.println("Ex03_Statement.m1");
			e.printStackTrace();
		}
		
	}
	
	

	private static void m5() {
		Connection conn = null;
		Statement stat = null;

		try {

			conn = DBUtil.open();

			if (!conn.isClosed()) {

				String sql = "create table tblAddress (\r\n"
						+ "\r\n"
						+ "    seq number primary key,                                 "
						+ "    name varchar2(30) not null,                             "
						+ "    age number(3) not null check (age between 0 and 150),   "
						+ "    gender char(1) not null check (gender in ('m', 'f')),   "
						+ "    tel varchar2(15) not null,                              "
						+ "    address varchar2(300) not null,                         "
						+ "    regdate date default sysdate not null                   "
						+ ")";

				stat = conn.createStatement();

				int result = stat.executeUpdate(sql);

				if (result == 1) {
					System.out.println("등록 성공");
				} else {
					System.out.println("등록 실패"); //create는 0이라 등록 실패가 뜨는데 제대로 생성된다.
				}

				stat.close();
				conn.close();

			} else {
				System.out.println("DB 접속 실패");
			}

		} catch (Exception e) {
			System.out.println("Ex03_Statement.m1");
			e.printStackTrace();
		}
	}
	
	

	private static void m4() {
		Connection conn = null;
		Statement stat = null;

		try {

			conn = DBUtil.open();

			if (!conn.isClosed()) {

				String sql = "delete from tblAddress where seq = 2";

				stat = conn.createStatement();

				int result = stat.executeUpdate(sql);

				if (result == 1) {
					System.out.println("삭제 성공");
				} else {
					System.out.println("삭제 실패");
				}

				stat.close();
				conn.close();

			} else {
				System.out.println("DB 접속 실패");
			}

		} catch (Exception e) {
			System.out.println("Ex03_Statement.m1");
			e.printStackTrace();
		}	
	}
	
	

	private static void m3() {
		Connection conn = null;
		Statement stat = null;

		try {

			conn = DBUtil.open();

			if (!conn.isClosed()) {

				String sql = "update tblAddress set age = age + 1 where seq = 2";

				stat = conn.createStatement();

				int result = stat.executeUpdate(sql);

				if (result == 1) {
					System.out.println("수정 성공");
				} else {
					System.out.println("수정 실패");
				}

				stat.close();
				conn.close();

			} else {
				System.out.println("DB 접속 실패");
			}

		} catch (Exception e) {
			System.out.println("Ex03_Statement.m1");
			e.printStackTrace();
		}
	}

	
	
	private static void m2() {

		// JDBC
		// 1. 기본 > SQL 실행 시 자동으로 커밋이 동반된다. > Auto Commit
		// 2. 설정 > 수동 트랜잭션 제어
		Connection conn = null;
		Statement stat = null;

		try {

			conn = DBUtil.open();

			if (!conn.isClosed()) {

				String sql = "insert into tblAddress (seq, name, age, gender, tel, address, regdate) values (seqAddress.nextVal, '하하하', 20, 'm', '010-1234-5678', '서울시 강남구 역삼동 한독빌딩', default)";

				stat = conn.createStatement();

				int result = stat.executeUpdate(sql);

				if (result == 1) {
					System.out.println("등록 성공");
				} else {
					System.out.println("등록 실패");
				}

				//conn.commit();
				conn.rollback();

				stat.close();
				conn.close();

			} else {
				System.out.println("DB 접속 실패");
			}

		} catch (Exception e) {
			System.out.println("Ex03_Statement.m1");
			e.printStackTrace();
		}
	}
	
	

	private static void m1() {

		Connection conn = null;
		Statement stat = null;

		try {

			// 1. DB 접속
			conn = DBUtil.open();

			if (!conn.isClosed()) {

				// 2.
				// - 자바는 SQL을 모른다. > SQL을 문자열로 취급한다.(의미X)
				// - ORA-00933: SQL 명령어가 올바르게 종료되지 않았습니다 > 문장에서 ";"을 작성할 수 없다. 즉 1개의 문장만 작성할 수
				// 있다.

				// 자바에서 작성하면 오타를 찾기 어렵기 때문에 SQL Developer에서 작성하고 복붙한다.
				String sql = "insert into tblAddress (seq, name, age, gender, tel, address, regdate) values (seqAddress.nextVal, '홍길동', 20, 'm', '010-1234-5678', '서울시 강남구 역삼동 한독빌딩', default)"; // 문장에서 ";"을 작성할 수 없다.

				stat = conn.createStatement(); // conn이 연결 정보를 가지고 있어서

				// 반환값이 없는 쿼리
				// - int stat.executeUpdate(sql)

				// 반환값이 있는 쿼리
				// - ResultSet stat.executeQuery(sql)

				// SQL Developer > Ctrl + Enter 역할!!
				int result = stat.executeUpdate(sql); // 반환값이 없는 경우 "N개의 행이 실행되었습니다."와 같이 문구가 뜨는 것을 이용

				if (result == 1) {
					System.out.println("등록 성공");
				} else {
					System.out.println("등록 실패");
				}

				// 자원 정리
				stat.close();
				conn.close();

			} else {
				System.out.println("DB 접속 실패");
			}

		} catch (Exception e) {
			System.out.println("Ex03_Statement.m1");
			e.printStackTrace();
		}
	} // m1
}
