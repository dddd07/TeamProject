package TeamDAO;
import TeamDTO.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import TeamMain.color;
public class TeamSQL {
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    // ① DB 접속
    public void connect(){
        con = DBC.DBConnect();
    }

    // ② DB 접속 해제
    public  void close(){
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ⓐ 호텔 번호 자동 생성
    public int hotelNumber() {
        int num = 0;

        try {
            // TCLIENT 테이블에서 MAX(TNUM)을 조회
            String sql = "SELECT MAX(HNUM) FROM HOTELS";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return num + 1;
    }

    // ⓑ 스케쥴 번호 자동 생성
    public int scheduleNumber() {
        int num = 0;

        try {
            // TCLIENT 테이블에서 MAX(TNUM)을 조회
            String sql = "SELECT MAX(DNUM) FROM SCHEDULE";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return num + 1;
    }

    // ⓒ 예약 번호 자동 생성
    public int reservationNumber() {
        int num = 0;

        try {
            // TCLIENT 테이블에서 MAX(TNUM)을 조회
            String sql = "SELECT MAX(RNUM) FROM RESERVATION";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return num + 1;
    }

    // ⓓ 결제 번호 자동 생성
    public int paymentNumber() {
        int num = 0;

        try {
            // TCLIENT 테이블에서 MAX(TNUM)을 조회
            String sql = "SELECT MAX(PNUM) FROM PAYMENT";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return num + 1;
    }


    // ③ 회원가입
     public void join(Member mb){
        String sql = "INSERT INTO TMEMBER VALUES(?,?,?,?,?,?,?)";
         try {
             pstmt = con.prepareStatement(sql);

             pstmt.setString(1, mb.gettId());
             pstmt.setString(2, mb.gettPw());
             pstmt.setString(3, mb.gettName());
             pstmt.setInt(4, mb.gettAge());
             pstmt.setString(5, mb.gettPhone());
             pstmt.setString(6, mb.gettEmail());
             pstmt.setInt(7,0);
             
             int result = pstmt.executeUpdate();
             
             if(result > 0){
                 System.out.println(color.YELLOW_BRIGHT + "회원가입 성공!"  +color.RESET);
             } else {
                 System.out.println(color.RED + "회원가입 실패!" + color.RESET);
             }
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }

     }


    // ③-1 회원가입 - 아이디 체크
    public boolean idCheck(String tId){
        boolean result = false;

        String sql = "SELECT * FROM TMEMBER WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, tId);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    // ④ 로그인
    public String login(String tId, String tPw) {
        String result = null;

        String sql = "SELECT * FROM TMEMBER WHERE TID = ? AND TPW = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, tId);
            pstmt.setString(2, tPw);

            rs = pstmt.executeQuery();

            if(rs.next()){
                result = rs.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    // ④-1 로그인한 사용자 이름 출력
    public void memberName(String tId) {
        String sql = "SELECT TNAME FROM TMEMBER WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, tId);

            rs = pstmt.executeQuery();

            while (rs.next()){
                System.out.println(color.WHITE_BRIGHT + rs.getString(1) + color.RESET + color.YELLOW + "님, 찾아주셔서 감사합니다!\uD83D\uDE04" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑤ 모든 회원 리스트
    public void memberList() {

        ArrayList<Member> mList = new ArrayList<>();

        String sql = "SELECT * FROM TMEMBER";
        try {
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()){
                Member mb = new Member();
                mb.settId(rs.getString(1));
                mb.settPw(rs.getString(2));
                mb.settName(rs.getString(3));
                mb.settAge(rs.getInt(4));
                mb.settPhone(rs.getString(5));
                mb.settEmail(rs.getString(6));
                mb.settPoint(rs.getInt(7));
                mList.add(mb);
            }

            for(Member mb : mList){
                System.out.println(color.YELLOW_BRIGHT + mb + color.RESET);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑤-1 내 정보 리스트
    public void myInfo(String tId) {

        ArrayList<Member> mList = new ArrayList<>();

        String sql = "SELECT * FROM TMEMBER WHERE TID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, tId);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Member mb = new Member();
                mb.settId(rs.getString(1));
                mb.settPw(rs.getString(2));
                mb.settName(rs.getString(3));
                mb.settAge(rs.getInt(4));
                mb.settPhone(rs.getString(5));
                mb.settEmail(rs.getString(6));
                mb.settPoint(rs.getInt(7));
                mList.add(mb);
            }
            for(Member mb : mList){
                System.out.println(color.YELLOW_BRIGHT + mb + color.RESET);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑥ 해당 나라 축구 클럽 리스트
    public void footballClub(int choiceCountry) {
        String sql = "SELECT SNUM, CLUB_NAME FROM S_CLUB WHERE CNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, choiceCountry);

            rs = pstmt.executeQuery();

            System.out.println(color.YELLOW + "축구 클럽을 선택하시오." + color.RESET);
            System.out.println(color.BLUE_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
            while (rs.next()){
                System.out.print(color.WHITE_BRIGHT + " " + rs.getInt(1) + ".");
                System.out.println(rs.getString(2) + color.RESET);
            }
            System.out.println(color.BLUE_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑦ 축구 클럽 인근 호텔 리스트
    public void hotelList(int choiceClub) {
        String sql = "SELECT HNUM, HNAME, HPRICE FROM HOTELS WHERE SNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, choiceClub);

            rs = pstmt.executeQuery();
            System.out.println(color.BLUE + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
            while (rs.next()){
                System.out.print(color.WHITE_BRIGHT + " " + rs.getInt(1) + ".");
                System.out.print(rs.getString(2));
                System.out.println(" 가격 : " + rs.getInt(3) + "원" + color.RESET);
            }
            System.out.println(color.BLUE + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    // ⑧ 해당 경기 스케쥴 입력(insert)
    public void schedule(Schedule sh) {

        String sql = "INSERT INTO SCHEDULE VALUES (?,?,?,?)";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, sh.getdNum());
            pstmt.setString(2, sh.getdDate());
            pstmt.setInt(3, sh.getsNUm());
            pstmt.setInt(4, sh.getAmount());
            
            int result = pstmt.executeUpdate();
            
            if(result > 0 ){
                System.out.println(color.YELLOW_BRIGHT + "경기 일정 등록 성공!" + color.RESET);
            } else {
                System.out.println(color.RED + "경기 일정 등록 실패!" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑨ 모든 경기 일정 리스트
    public void scheduleList() {
        String sql = "SELECT SD.DNUM, SD.DDATE, SC.CLUB_NAME, SD.AMOUNT FROM SCHEDULE SD JOIN S_CLUB SC ON SD.SNUM = SC.SNUM ORDER BY SD.DNUM";

        try {
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();
            System.out.println(color.BLUE + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
            while (rs.next()){
                System.out.print(color.WHITE_BRIGHT + " " +rs.getInt(1) + ".");
                System.out.print("경기일: " + rs.getDate(2));
                System.out.print(" [" + rs.getString(3) + "]");
                System.out.println(" 금액: " + rs.getInt(4) + "원" + color.RESET);
            }
            System.out.println(color.BLUE + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑩ 호텔 등록
    public void hotelReservation(Hotel ht, int choiceClub) {
        String sql = "INSERT INTO HOTELS VALUES (?,?,?,?,?)";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, ht.gethNum());
            pstmt.setString(2, ht.gethName());
            pstmt.setInt(3, ht.gethPrice());
            pstmt.setString(4, ht.gethLocation());
            pstmt.setInt(5, choiceClub);

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println(color.YELLOW_BRIGHT + "호텔 등록 성공!" + color.RESET);
            } else {
                System.out.println(color.RED + "호텔 등록 실패!"  + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑪ 등록된 경기 일정 변경
    public void changeSchedule(int us, String colDate) {
        String sql = "UPDATE SCHEDULE SET DDATE = ? WHERE DNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, colDate);
            pstmt.setInt(2, us);

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println(color.YELLOW_BRIGHT + "일정 변경 완료!" + color.RESET);
            } else {
                System.out.println(color.RED + "일정 변경 실패!" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑫ 등록된 경기 일정 삭제
    public void deleteSchedule(int ds) {
        String sql = "DELETE FROM SCHEDULE WHERE DNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, ds);
            
            int result = pstmt.executeUpdate();
            
            if(result > 0){
                System.out.println(color.YELLOW_BRIGHT + "일정 삭제 완료!" + color.RESET);
            } else {
                System.out.println(color.RED + "일정 삭제 실패!" + color.RESET);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑬ 호텔 삭제 ( 지금은 무결성 제약 조건 즉, 스케쥴 테이블과 연결된 FK로 인해 삭제에 제약이 있음 ) [이 조건을 19-2번에서 충족함]
    public void deleteHotel(int deleteHotel) {
        String sql = "DELETE FROM HOTELS WHERE HNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, deleteHotel);

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println(color.YELLOW_BRIGHT + "등록된 호텔을 삭제하였습니다!" + color.RESET);
            } else {
                System.out.println(color.RED + "호텔 삭제 실패! 다시 확인해주세요."  + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑭ 선택한 클럽 경기 일정 스케쥴
    public void clubSchedule(int gameSchedule) {
        String sql = "SELECT SH.DNUM, SH.DDATE, SC.CLUB_NAME, SH.AMOUNT FROM SCHEDULE SH JOIN S_CLUB SC ON SH.SNUM = SC.SNUM WHERE SH.SNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, gameSchedule);

            rs = pstmt.executeQuery();
            System.out.println(color.WHITE_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
            while (rs.next()){
                System.out.println(color.WHITE_BRIGHT + " " + rs.getInt(1) + ". [" + rs.getString(3) + "] : " + rs.getDate(2) + " : " + rs.getInt(4) + "원" + color.RESET);
            }
            System.out.println(color.WHITE_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑮ 호텔 + 경기예매 금액 총합계 구하기
    public int totalAmount(int cs, int ch) {
        int result = 0;

        String sql = "SELECT SH.DNUM,\n" +
                "       COALESCE(SUM(HT.HPRICE), 0) AS HOTEL_PRICE,\n" +
                "       COALESCE(SUM(SH.AMOUNT), 0) AS SCHEDULE_AMOUNT,\n" +
                "       COALESCE(SUM(HT.HPRICE), 0) + COALESCE(SUM(SH.AMOUNT), 0) AS TOTAL_AMOUNT\n" +
                "FROM SCHEDULE SH\n" +
                "LEFT JOIN HOTELS HT ON SH.SNUM = HT.SNUM\n" +
                "WHERE SH.DNUM = ?\n" +
                "GROUP BY SH.DNUM";
        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, cs);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 호텔 선택 여부에 따라 호텔 가격을 포함할지 결정
                if (ch != 0) {  // 호텔을 선택한 경우
                    result = rs.getInt("TOTAL_AMOUNT"); // 총합계 (스케줄 + 호텔)
                } else {  // 호텔을 선택하지 않은 경우
                    result = rs.getInt("SCHEDULE_AMOUNT"); // 스케줄 금액만
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    // ⑯ 예약 저장 ( insert )
    public void reservation(String tId, int cs, int ch, Reservation re) {
        String sql = "INSERT INTO RESERVATION VALUES (?,?,?,?,?)";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, re.getrNum());
            pstmt.setInt(2, re.getPayment());
            pstmt.setString(3, tId);
            pstmt.setInt(4, ch);
            pstmt.setInt(5, cs);

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println(color.YELLOW_BRIGHT + "예약 완료!" + color.RESET);
            } else {
                System.out.println(color.RED + "예약 실패!" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // ⑰ 내 예약 정보 확인
    public void myReservation(String tId) {
        String sql = "SELECT RE.RNUM, RE.PAYMENT, RE.TID, HT.HNAME, SH.DDATE, SC.CLUB_NAME, CO.CNAME, HT.HLOCATION\n" +
                "FROM RESERVATION re\n" +
                "JOIN HOTELS HT ON RE.HNUM = HT.HNUM\n" +
                "JOIN SCHEDULE SH ON SH.DNUM = RE.DNUM\n" +
                "JOIN S_CLUB SC ON SH.SNUM = SC.SNUM\n" +
                "JOIN COUNTRY CO ON SC.CNUM = CO.CNUM\n" +
                "WHERE RE.TID = ? order by re.rnum";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, tId);

            rs = pstmt.executeQuery();

            System.out.println(color.BLUE + "────────────────────────────────────────────────────" + color.RESET);
            while (rs.next()){
                System.out.println(color.WHITE_BRIGHT + rs.getInt(1) + ". ID : " + rs.getString(3) + " │ 도착지 : " + rs.getString(7));
                System.out.println(" 〘 경기일정 : " + rs.getDate(5) + ", " + rs.getString(6) + " 〙");
                System.out.println(" 〘 호텔 : " + rs.getString(4) + ", 위치 : " + rs.getString(8) + color.RESET + " 〙" + color.BLUE_BRIGHT +  " 총액 : " + rs.getInt(2) + "원" + color.RESET);
                System.out.println();
                System.out.println(color.BLUE + "────────────────────────────────────────────────────" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑱ 회원 탈퇴
    public void deleteMember(String tId) {
        String sql = "DELETE FROM TMEMBER WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, tId);

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println(color.YELLOW_BRIGHT + "회원 탈퇴 완료!" + color.RESET);
            } else {
                System.out.println(color.RED + "회원 탈퇴 실패!" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑲-1 무결성 제약조건(TID를 참조하는 RESERVATION 테이블에 데이터를 삭제하기 위함) 삭제
    public void dReservation_m(String tId) {
        String sql = "DELETE FROM RESERVATION WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, tId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑲-2 무결성 제약조건(HNUM을 참조하는 RESERVATION 테이블에 데이터를 삭제하기 위함) 삭제
    public void dReservation_h(int deleteHotel) {
        String sql = "DELETE FROM RESERVATION WHERE HNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, deleteHotel);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ⑳ 예약된 일정 취소
    public void dReservation(int dReservation) {
        String sql = "DELETE FROM RESERVATION WHERE RNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, dReservation);

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println(color.YELLOW_BRIGHT + "예약 삭제 완료!" + color.RESET);
            } else {
                System.out.println(color.RED + "예약 삭제 실패!" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ㉑ 모든 축구 클럽 리스트
    public void footballClubList() {
        String sql = "SELECT * FROM S_CLUB";

        try {
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()){
                System.out.print(color.GREEN_BRIGHT + "〘" + rs.getInt(1) + "." + rs.getString(2));
                System.out.println(" │ 홈구장 : " + rs.getString(3) + "〙" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ㉒ 등록된 모든 호텔 리스트
    public void hotel_List() {
        String sql = "SELECT * FROM HOTELS ORDER BY HNUM";

        try {
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()){
                System.out.print(color.BLUE_BRIGHT + "〘 " + rs.getInt(1) + "." + rs.getString(2));
                System.out.println(" │ 주소 : " + rs.getString(4) + " 〙" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    
    // ㉓ 해당 경기일(Day)만 가져오기
    public int dayRe(int gameSchedule) {
        int result = 0;

        String sql = "SELECT TO_CHAR(SH.DDATE, 'DD') AS DAY\n" +
                "FROM SCHEDULE SH\n" +
                "JOIN S_CLUB SC ON SH.SNUM = SC.SNUM\n" +
                "WHERE SH.SNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, gameSchedule);

            rs = pstmt.executeQuery();

            if(rs.next()){
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    
    // ㉔ 호텔 여부 확인
    public boolean checkHotel(int cs) {
        boolean result = false;

        String sql = "SELECT * FROM HOTELS HT JOIN S_CLUB SC ON HT.SNUM = SC.SNUM WHERE SC.SNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, cs);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    
    // ㉕ 호텔이 없다면 HNUM이 0인 호텔없음을 출력하기
    public void nullHotel() {
        String sql = "SELECT HNUM, HNAME FROM HOTELS WHERE HNUM = 0";

        try {
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()){
                System.out.println(color.WHITE_BRIGHT + rs.getInt(1) + "." +rs.getString(2) + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ㉖ 스케쥴 여부를 확인하는 메소드
    public boolean checkSchedule(int gameSchedule) {
        boolean result = false;

        String sql = "SELECT * FROM SCHEDULE where snum = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, gameSchedule);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }



    // 임희진님 작품 ====
    // ㉗ 회원 변경을 각 컬럼으로 받아서 아이디와 일치하는 컴럼에 데이터를 출력
    public String checkMember(String tId, String colName) {

        String cc = null;

        // 각 colName에 맞는 컬럼명을 테이블과 정확하게 매핑
        String column = null;
        switch (colName) {
            case "tPw":
                column = "TPW";
                break;
            case "tName":
                column = "TNAME";
                break;
            case "tPhone":
                column = "TPHONE";
                break;
            case "tEmail":
                column = "TEMAIL";
                break;
            default:
                throw new IllegalArgumentException("Invalid column name: " + colName);
        }

        String sql = "SELECT " + column + " FROM TMEMBER WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, tId);

            rs = pstmt.executeQuery();

            if(rs.next()){
                cc = rs.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cc;
    }


    // ㉘ 회원 변경을 각 컬럼으로 받아서 아이디와 일치하는 컴럼에 데이터를 변경
    public void memberUpdate(String tId, String colName, String colValue) {

        String sql = "UPDATE TMEMBER SET " + colName + " = ? WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, colValue);
            pstmt.setString(2, tId);
            
            int result = pstmt.executeUpdate();
            
            if(result > 0){
                System.out.println("회원 업데이트 성공");
            } else {
                System.out.println("회원 업데이트 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    // 여기부터 받아서 씀
    // ㉙ 회원정보 삭제
    public void memberDelete(String tId) {
        try {
            // (1) sql문 작성
            String sql = "DELETE FROM TMEMBER WHERE TID = ?";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력
            pstmt.setString(1, tId);

            // (4) 실행
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {
                System.out.println("회원정보 삭제 성공!");
            } else {
                System.out.println("회원정보 삭제 실패!");
                System.out.println("존재하지 않는 아이디입니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ㉙-1 회원정보 삭제
    public void memberDelete_d(String tId) {
        try {
            // (1) sql문 작성
            String sql = "DELETE FROM RESERVATION WHERE TID = ?";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력
            pstmt.setString(1,tId);

            // (4) 실행
            pstmt.executeUpdate();

            // (5) 결과


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ㉚ 회원 예약 정보
    public void showReservationInfo(String tId) {
        try {
            // 예약번호, 아이디, 경기일정(날짜), 도착지(나라), 축구구단, 숙소(숙소이름,위치), 총결제금액
            String sql = "SELECT RE.RNUM, RE.TID, RE.PAYMENT, C.CNAME, SH.DDATE, SC.CLUB_NAME, H.HNAME, H.HLOCATION FROM RESERVATION RE\n"
                    + "JOIN HOTELS H ON RE.HNUM = H.HNUM\n"
                    + "JOIN SCHEDULE SH ON RE.DNUM = SH.DNUM\n"
                    + "JOIN S_CLUB SC ON SH.SNUM = SC.SNUM\n"
                    + "JOIN COUNTRY C ON SC.CNUM = C.CNUM\n"
                    + "WHERE RE.TID = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, tId);

            rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("예약 내역이 없습니다.");
            } else {
                while (rs.next()) {
                    System.out.println("| 예약번호 : " + rs.getInt(1) + " | ID : " + rs.getString(2) + " | 결제금액 : " + rs.getInt(3) + "원 |");
                    System.out.println("[ " + "경기일 : " + rs.getDate(5) + " | 도착지 : " + rs.getString(4) + " | 경기 : " + rs.getString(6) + " ]");
                    System.out.println("[ 호텔명(주소) : " + rs.getString(7) + "(" + rs.getString(8) + ")" + " ]");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // 여기부터는 채건님 작품 ===
    // ㉛ 찾을 아이디
    public boolean findId(Member mb) {
        boolean result = false;
        String sql = "SELECT * FROM TMEMBER WHERE TPHONE = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, mb.gettPhone());

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    // ㉜ 아이디 조회
    public void selectID(Member mb) {
        String sql = "SELECT TID FROM TMEMBER WHERE TPHONE = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, mb.gettPhone());

            rs = pstmt.executeQuery();

            while (rs.next()){
                System.out.println(color.GREEN + rs.getString(1) + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ㉝ 찾을 비밀번호
    public boolean findPW(Member mb) {
        boolean result = false;
        String sql = "SELECT * FROM TMEMBER WHERE TEMAIL = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, mb.gettEmail());

            rs = pstmt.executeQuery();

            result = rs.next();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    // ㉞ 찾을 비밀번호 - ID
    public boolean findPW_ID(Member mb) {
        boolean result = false;
        String sql = "SELECT * FROM TMEMBER WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, mb.gettId());

            rs = pstmt.executeQuery();

            result = rs.next();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    // ㉟ 비밀번호 조회
    public void selectPW(Member mb) {
        String sql = "SELECT TPW FROM TMEMBER WHERE TEMAIL = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, mb.gettEmail());

            rs = pstmt.executeQuery();

            while (rs.next()){
                System.out.println(color.GREEN + rs.getString(1) + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ㊱ (비밀번호 찾기 실패 후) 비밀번호 변경하기
    public boolean updatePassword(Member mb) {
        String sql = "UPDATE TMEMBER SET TPW = ? WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, mb.gettPw());
            pstmt.setString(2, mb.gettId());

            int result = pstmt.executeUpdate();

            if (result > 0) {
                //updatPw = true;
                System.out.println("비밀 번호 변경 성공");
            } else {

                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    // ㊲ (비밀번호 찾기 실패 후) 비밀번호 변경하기 위한 현재 비밀번호
    public String getCurrentPassword(Member mb) {
        String curPw = null;
        String sql = "SELECT TPW FROM TMEMBER WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, mb.gettId());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                curPw = rs.getString("TPW");

            } else {
                System.out.println("해당 아이디를 찾을 수 없습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return curPw;
    }

    // 채건님 작품 끝 =====


    // ㊳ 포인트 체인지( 체인지가 아니라 포인트 충전임)
    public void pointCharging(int point, String tId) {
        String sql = "UPDATE TMEMBER SET TPOINT = TPOINT + ? WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, point);
            pstmt.setString(2, tId);

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println(color.YELLOW_BRIGHT + point + "point 충전 완료!" + color.RESET);
            } else {
                System.out.println(color.RED + "포인트 충전 실패인데 어쩌라고?" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    // ㊴ 리스트로 예약 테이블에 RNUM을 받아서 return
    public List<Integer> myReNum(String tId) {
        List<Integer> resultList = new ArrayList<>();
        String sql = "SELECT RNUM FROM RESERVATION WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, tId);

            rs = pstmt.executeQuery();

            while (rs.next()) { // 모든 결과를 반복하여 가져옴
                resultList.add(rs.getInt(1)); // RNUM을 리스트에 추가
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultList; // RNUM 목록 반환
    }


    // ㊵ 결제 완료 되면 결제 테이블에 인설트
    public void payment(Payment payment) {

        String sql = "INSERT INTO PAYMENT VALUES (?,?,?,?)";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, payment.getpNum());
            pstmt.setString(2,payment.gettId());
            pstmt.setInt(3,payment.getrNum());
            pstmt.setString(4, payment.getHhh());
            
            int result = pstmt.executeUpdate();
            
            if(result > 0){
                System.out.println("결제 완료");
            } else {
                System.out.println("결제 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    // ㊶ 회원이 가지고 있는 포인트 가져가기
    public int userPoint(String tId) {
        int result = 0;

        String sql = "SELECT TPOINT FROM TMEMBER WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, tId);

            rs = pstmt.executeQuery();

            if(rs.next()){
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  result;
    }


    // ㊷ 예약 테이블에 있는 총 금액을 가져가기
    public int rePayment(int paymentChoice) {
        int result = 0;

        String sql = "SELECT PAYMENT FROM RESERVATION WHERE RNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, paymentChoice);

            rs = pstmt.executeQuery();

            if(rs.next()){
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;

    }


    // ㊸ 결제가 완료되면 결제테이블과 타 테이블들의 정보를 함께 보기
    public void paymentList(String tId) {
        String sql = "SELECT PA.PNUM, PA.HHH, PA.tid, RE.PAYMENT, SH.DDATE, HT.HNAME, HT.HLOCATION,SC.CLUB_NAME, CO.CNAME\n" +
                "FROM PAYMENT PA \n" +
                "JOIN RESERVATION RE ON PA.RNUM = RE.RNUM\n" +
                "JOIN SCHEDULE SH ON SH.DNUM = RE.DNUM\n" +
                "JOIN HOTELS HT ON HT.HNUM = RE.HNUM\n" +
                "JOIN S_CLUB SC ON SC.SNUM = SH.SNUM\n" +
                "JOIN COUNTRY CO ON CO.CNUM = SC.CNUM\n" +
                "WHERE PA.TID = ? order by pa.pnum";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, tId);

            rs = pstmt.executeQuery();

            System.out.println(color.YELLOW_BRIGHT + "━〘결제 정보〙━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + color.RESET);
            while (rs.next()){
                System.out.println(color.WHITE_BRIGHT + rs.getInt(1) + ". ID : " + rs.getString(3) + " │ 도착지 : " + rs.getString(9));
                System.out.println(" 〘 경기일정 : " + rs.getString(8) + ", " + rs.getString(5) + " 〙");
                System.out.println(" 〘 호텔 : " + rs.getString(6) + ", 위치 : " + rs.getString(7) + color.RESET + " 〙" + color.BLUE_BRIGHT +  " 결재액 : " + rs.getInt(4) + "원" + color.RESET + "\uD83D\uDEEB" +  color.MAGENTA_BRIGHT +rs.getString(2) + "\uD83D\uDEEB");
                System.out.println(color.YELLOW_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ㊹ 결제 취소
    public void deletePayment(int deletePayment, String tId) {
        String sql = "DELETE FROM PAYMENT WHERE PNUM = ? AND TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, deletePayment);
            pstmt.setString(2, tId);

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println("결제 취소 완료!");
            } else {
                System.out.println("결제 취소 실패!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    // ㊺-1 결제 취소하기 위해, 회원을 검색해서 해당 회원의 결제정보를 다 지우기
    public void deletePayment_m(String tId) {
        String sql = "DELETE FROM PAYMENT WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, tId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ㊺-2 예약 삭제를 하기 위해, 예약을 검색해서 해당 예약정보를 다 지우기
    public void dReservation_D(int ds) {

        String sql = "DELETE FROM RESERVATION WHERE DNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, ds);

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println(color.YELLOW_BRIGHT + "예약 삭제 완료!" + color.RESET);
            } else {
                System.out.println(color.RED + "예약 삭제 실패!" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // ㊻-1 결제 정보가 있는 스케쥴을 지우기 위해 스케쥴을 받은 결제 정보가 있는지 확인
    public boolean checkPayment(int ds) {
        boolean result = false;

        String sql = "SELECT * FROM PAYMENT PA JOIN RESERVATION RE ON PA.RNUM = RE.RNUM JOIN SCHEDULE SH ON SH.DNUM = RE.DNUM WHERE SH.DNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, ds);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    
    // ㊻-2 예약 정보가 있는 스케쥴을 지우기 위해 스케쥴을 받은 예약 정보가 있는지 확인
    public boolean checkReservation(int ds) {
        boolean result = false;

        String sql = "SELECT * FROM RESERVATION RE JOIN SCHEDULE SH ON RE.DNUM = SH.DNUM WHERE SH.DNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, ds);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    // ㊼ 검색한 스케쥴의 예약 정보를 보기위함 ( DNUM을 검색해서 해당되는 RNUM을 조회 )
    public int getPnum(int ds) {
        int result = 0;

        String sql = "SELECT RE.RNUM FROM RESERVATION RE JOIN SCHEDULE SH ON RE.DNUM = SH.DNUM JOIN PAYMENT PA ON RE.RNUM = PA.RNUM WHERE SH.DNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, ds);

            rs = pstmt.executeQuery();

            if(rs.next()){
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    // ㊼ 검색한 스케쥴의 결제 정보를 보기위함 ( DNUM을 검색해서 해당되는 PNUM을 조회 )


    // ㊽ 결제 완료된 정보를 삭제하기 위함
    public void deletePayment_P(int pnum) {
        String sql = "DELETE FROM PAYMENT WHERE RNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, pnum);

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println(color.YELLOW_BRIGHT + "결제 취소 완료!"  + color.RESET);
            } else {
                System.out.println(color.YELLOW_BRIGHT + "결제 취소 실패~" + color.RESET);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    // ㊾ 해당하는 회원이 예약한 금액이 총 얼마인지 확인하기 위함
    public int paymentAmount(Payment payment) {
        int result = 0;

        String sql = "SELECT PAYMENT FROM RESERVATION WHERE TID = ? AND RNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, payment.gettId());
            pstmt.setInt(2, payment.getrNum());

            rs = pstmt.executeQuery();

            if(rs.next()){
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    // ㊿ 결제 후 회원 포인트를 차감하기 위함
    public void pointUpdate(int paymentAmount, Payment p) {

        String sql = "UPDATE TMEMBER SET TPOINT = TPOINT - ? WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, paymentAmount);
            pstmt.setString(2, p.gettId());

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println(color.MAGENTA_BRIGHT + "[" + paymentAmount + "]" + color.RESET + color.YELLOW_BRIGHT + "point 차감!" + color.RESET);
            } else {
                System.out.println("포인트로 결제가 정상적으로 처리되지 않았습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean checkCName(S_club cu) {
        boolean result = false;

        String sql = "SELECT * FROM S_CLUB WHERE CLUB_NAME LIKE ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, "%" + cu.getClub_Name() + "%");

            rs = pstmt.executeQuery();

            result = rs.next();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public int clubSnum(S_club cu) {
        int results =0; // 결과를 저장할 리스트 생성

        String sql = "SELECT SNUM FROM S_CLUB WHERE CLUB_NAME LIKE ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + cu.getClub_Name() + "%");

            rs = pstmt.executeQuery();


            if(rs.next()){
                results = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return results; // 리스트 반환
    }


    public boolean reservation_ox(String tId) {
        boolean result = false;

        String sql = "SELECT * FROM RESERVATION WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, tId);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public boolean schedule0() {
        boolean result = false;

        String sql = "SELECT * FROM SCHEDULE";

        try {
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public boolean payment0(String tId) {
        boolean result = false;

        String sql = "SELECT * FROM PAYMENT WHERE TID = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, tId);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public int getHnum(int deleteHotel) {
        int result = 0;

        String sql = "SELECT PA.PNUM FROM RESERVATION RE JOIN PAYMENT PA ON PA.RNUM = RE.RNUM JOIN HOTELS HT ON RE.HNUM = HT.HNUM WHERE HT.HNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, deleteHotel);

            rs = pstmt.executeQuery();

            if(rs.next()){
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    // 결제 취소 시 포인트 복원 메소드
    public int rePayment1(int deletePayment) {
        int totalAmount = 0;

        String sql = "SELECT RE.PAYMENT FROM PAYMENT PA JOIN RESERVATION RE ON RE.RNUM = PA.RNUM WHERE PA.PNUM = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, deletePayment);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                totalAmount = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return totalAmount;
    }

    // 포인트 차감 시 업데이트 메소드
    public void updateUserPoint(String tId, int tP) {
        try {
            // (1) sql문 작성
            String sql = "UPDATE TMEMBER SET TPOINT = ? WHERE TID = ?";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력
            pstmt.setInt(1, tP);
            pstmt.setString(2, tId);

            // (4) 실행
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}