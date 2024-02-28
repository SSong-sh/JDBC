package com.test.java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Ex04_select {

	public static void main(String[] args) {

		// m1(); //단일값 반환
		// m2(); //다중값 반환, 1행 N열
		// m3(); //다중값 반환, N행 1열
		// m4(); //부서 입력 > 직원 명단 출력
		//m5(); // 요구사항] 특정 직원에게 보너스를 지급하시오.
		//m6(); //오류발생
		//m7();
		m8();

	}// main

	private static void m8() {
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtil.open();
			stat = conn.createStatement();
			
			//요구사항] 영업부 > 직원수와 직원명단을 출력하시오.
			//1. select count(*) as cnt from tblInsa where buseo = '영업부';
			//2. select * from tblInsa where buseo = '영업부';
			
			// *** ResultSet의 특징
			// - 커서를 가르키는 레코드의 정보 이외에는 알 수 없다. 
			
			String sql = "" ;
			sql = "select * from tblInsa where buseo = '영업부'";
			
			rs = stat.executeQuery(sql);
			
			int count = 0;
			
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				count++;
			}
			
			System.out.println(count);
			
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void m7() {
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtil.open();
			stat = conn.createStatement();

			String sql = "select \r\n"
					+ "    m.name as mname, v.name as vname\r\n"
					+ "	  from tblMember m\r\n"
					+ "    inner join tblRent r\r\n"
					+ "        on m.seq = r.member\r\n"
					+ "            inner join tblVideo v\r\n"
					+ "                on v.seq = r.video";
			
			rs = stat.executeQuery(sql);
			
			// 누가? 뭘?
			
			while (rs.next()) {
				//부적합한 열 이름
				//System.out.println("누가: " + rs.getString("m.name"));
				//System.out.println("무엇을: " + rs.getString("v.name"));

				//가능
				//System.out.println("누가: " + rs.getString(1));
				//System.out.println("무엇을: " + rs.getString(2));
				
				//둘 다 사람 이름이 출력 > as 별칭 붙여주기
				//System.out.println("누가: " + rs.getString("name"));
				//System.out.println("무엇을: " + rs.getString("name"));
				
				System.out.println("누가: " + rs.getString("mname"));
				System.out.println("무엇을: " + rs.getString("vname"));
				System.out.println();
				
			}
			
			rs.close();
			
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void m6() {
		
		//select > 오류 발생 !!
		
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtil.open();
			stat = conn.createStatement();
			
			//ORA-00904: "BUSE": 부적합한 식별자(오타)
			//ORA-00942: 테이블 또는 뷰가 존재하지 않습니다 (오타)
			String sql = "select name, buseo, jikwi from tblInsa";
			
			rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				//부적합한 열 이름 - 자바 에러 (오라클 에러x)
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("buse"));
				System.out.println(rs.getString("jikwi"));
				System.out.println();
				
			}
			
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	private static void m5() {

		// tblInsa + tblBonue
		// 요구사항] 특정 직원에게 보너스를 지급하시오.
		// 1. 모든 직원 명단을 출력(tblInsa > 직원번호, 이름, 부서, 직위) > m4 참조
		// 2. 사용자 > 직원 선택(직원 번호 입력)
		// 3. 사용자 > 보너스 금액 입력
		// 4. 보너스 지급 > insert tblBonus..
		// 5. 지급된 내역을 출력(tblBonus > 직원번호, 이름, 부서, 직위, 보너스 금액)

		Scanner scan = new Scanner(System.in);

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		try {

			conn = DBUtil.open();
			stat = conn.createStatement();

			// 1. 직원 명단 가져오기(select tblInsa)
			String sql = "select num, name, buseo, jikwi from tblInsa order by num asc";

			rs = stat.executeQuery(sql);

			System.out.println("[번호]\t[이름]\t[부서]\t[직위]");

			while (rs.next()) {
				// 레코드 1줄 = 직원 1명
				System.out.printf("%d\t%s\t%s\t%s\n"
										,rs.getInt("num")
										,rs.getString("name")
										,rs.getString("buseo") 
										,rs.getString("jikwi"));
			}
			
			rs.close();
			
			// 2. 보너스 지급할 직원 선택(scan.nextLine)
			System.out.print("직원 번호: ");
			String num = scan.nextLine();
			
			// 3. 지급할 보너스 금액 입력하기(scan.nextLine)
			System.out.print("보너스 금액: ");
			String bonus = scan.nextLine();
			
			// 4. 보너스 지급하기(insert tblBonus)
			sql = String.format("insert into tblBonus (seq,num,bonus) values (seqBonus.nextVal, %s, %s) ", num, bonus);
			
			int result = stat.executeUpdate(sql);
			
			// 5. 지급된 목록 출력하기(select tblBonus)
			System.out.println();
			
			sql = "select i.num, i.name, i.buseo, i.jikwi, b.bonus from tblBonus b inner join tblInsa i on i.num = b.num order by b.num asc";
			
			rs = stat.executeQuery(sql);
			
			System.out.println("[번호]\t[이름]\t[부서]\t[직위]\t[보너스]");
			
			while (rs.next()) {
				System.out.printf("%s\t%s\t%s\t%s\t%,10d원\n"
											,rs.getString("num")
											,rs.getString("name")
											,rs.getString("buseo") 
											,rs.getInt("jikwi")
											,rs.getInt("bonus"));
			}
			
			stat.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("Ex04_select.m5");
			e.printStackTrace();
		}

	}

	private static void m4() {
		// 부서 입력 > 직원 명단 출력
		Scanner scan = new Scanner(System.in);
//		System.out.print("부서명: ");
//		String buseo = scan.nextLine();

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		try {

			conn = DBUtil.open();
			stat = conn.createStatement();

			String sql = "select distinct buseo from tblInsa order by buseo asc";

			rs = stat.executeQuery(sql);

			System.out.println("보고 싶은 부서를 선택하세요.");
			ArrayList<String> blist = new ArrayList<String>(); // 확인할 부서 번호로 입력받기 위해서 ArrayList를 활용

			int n = 1;
			while (rs.next()) {
				System.out.printf("%d. %s\n", n, rs.getString("buseo"));
				n++;
				blist.add(rs.getString("buseo"));
			}

			System.out.print("선택(번호): ");
			int sel = scan.nextInt();

			String buseo = blist.get(sel - 1);

			sql = String.format("select * from tblInsa where buseo = '%s'", buseo);

			rs = stat.executeQuery(sql);

			System.out.println("[이름]\t[직위]\t[지역]\t[급여]");

			while (rs.next()) {
				// 레코드 1줄 = 직원 1명
				System.out.printf("%s\t%s\t%s\t%,10d원\n", rs.getString("name"), rs.getString("jikwi"),
						rs.getString("city"), rs.getInt("basicpay"));
			}

			stat.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("Ex04_select.m4");
			e.printStackTrace();
		}

	}

	private static void m3() {
		// 다중값 반환
		// - 다중 레코드
		// - N행 1열
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.open();
			stat = conn.createStatement();

			String sql = "select name from tblInsa order by name asc";

			// 60명
			rs = stat.executeQuery(sql); // BOF > 전진하기 전에는 첫번째 레코드 바로 위를 가리키고 있다.

			// ResultSet.next가 호출되지 않았음 > 커서 전진하지 않고 실행하면 해당 에러 메시지 출력된다.
			rs.next(); // 커서 전진
			System.out.println(rs.getString("name")); // 고순정

			System.out.println(rs.getString("name")); // 고순정

			rs.next(); // 커서 전진
			System.out.println(rs.getString("name")); // 권영미

			// 모든 이름 출력 반복문
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}

			stat.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("Ex04_select.m3");
			e.printStackTrace();
		}

	}

	private static void m2() {
		// 다중값 반환
		// - 1행 N열
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		try {

			conn = DBUtil.open();
			stat = conn.createStatement();

			String sql = "select * from tblAddress where seq = 6";

			rs = stat.executeQuery(sql);

			if (rs.next()) { // 존재하는지 확인(예외처리)

				String name = rs.getString("name"); // 현재 커서가 가리키는 곳의 이름
				String age = rs.getString("age");
				String address = rs.getString("address");

				System.out.println(name);
				System.out.println(age);
				System.out.println(address);

			} else {
				System.out.println("6번 데이터가 없습니다.");
			}

			stat.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("Ex04_select.m2");
			e.printStackTrace();
		}

	}

	private static void m1() {
		// 단일값 반환
		// - 1행 1열

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		try {

			conn = DBUtil.open();
			stat = conn.createStatement();

			String sql = "select count(*) as cnt from tblAddress";

			rs = stat.executeQuery(sql);

			// ResultSet == 커서(Cursor) > 판독기 == 스트림, Iterator, 향상된 for

			rs.next(); // 커서 1줄 전진

			// 현재 커서가 가리키고 있는 레코드의 원하는 컬럼을 가져오기
			// - rs.getXXX()

			// int count = rs.getInt(1); //컬럼 순서
			// int count = rs.getInt("cnt"); //컬럼명
			String count = rs.getString("cnt"); // 어떻게 받을 지 자바에서 결정할 수 있다.

			System.out.println(count);

			stat.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("Ex04_select.m1");
			e.printStackTrace();
		}

	}

}
