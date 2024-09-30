package TeamDAO;

import java.sql.*;

public class DBC {
    public static Connection DBConnect(){
        // DB에 접속정보를 저장하기 위한 Connection 객체 con 선언
        Connection con = null;

        // DB 접속 계정정보
        String user = "ICIA";       // 아이디
        String password = "1111";   // 비밀번호

        // DB 접속 주소 정보
        String url = "jdbc:oracle:thin:@localhost:1521:XE";

        try {
            // (1) 오라클 데이터베이스 드라이버(ojdbc8 -> 나는 6(로쿠))
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // (2) 오라클 데이터베이스 접속정보
            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            // throw new RuntimeException(e)
            System.out.println("DB 접속 실패 : 드라이버 로딩 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB 접속 실패 : 접속 정보 오류");
            throw new RuntimeException(e);
        }

        return con;

    }
}
