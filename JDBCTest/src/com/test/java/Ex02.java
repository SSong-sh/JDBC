package com.test.java;

import java.sql.Connection;

public class Ex02 {

	public static void main(String[] args) {
		//
		//접속 시 발생하는 에러
		//1. 서버 주소가 틀린 경우 > 아닌 경우도 존재한다.
		// - IO 오류: The Network Adapter could not establish the connection 
		//2. 아이디 or 암호가 틀린 경우
		// - ORA-01017: 사용자명/비밀번호가 부적합, 로그온할 수 없습니다.
		//3. 서버가 중지된 경우
		// -  Listener refused the connection with the following error:
		//4. 연결 문자열 오타(ex: String url = "jdbc:oracle:thin@" + host + ":1521:xe";)
		// - 적합한 Oracle URL이 지정되었습니다
		//5. 포트번호가 틀린 경우
		// - IO 오류: The Network Adapter could not establish the connection 
		//6. SDI가 틀린 경우
		// - Listener refused the connection with the following error:
		//7. 드라이버 오타(오타가 난 부분을 알려준다.)
		// - oracle.jdbc.driver.OracleDrive
		//8. ojdbc8.jar에 문제(오타가 없는데 해당 에러 문구가 뜨면 jar 파일을 안 가져온 것이다.)
		// - oracle.jdbc.driver.OracleDriver
		
		try {
			
			//Connection conn = DBUtil.open();
			Connection conn = DBUtil.open("localhost", "hr2", "java1234"); //다른 서버에 접속하는 방법
			
			System.out.println(conn.isClosed()); //제대로 연결됐는지 확인
			
			conn.close();
			
		} catch (Exception e) {
			System.out.println("Ex02.main");
			e.printStackTrace();
		}
		
	}
	
}
