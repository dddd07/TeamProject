package TeamMain.Admin;

import TeamDAO.TeamSQL;

import java.util.Scanner;

import TeamMain.*;

public class AdminMember {

    private TeamSQL sql;
    private Scanner sc;

    String tId;

    public AdminMember(TeamSQL sql, Scanner sc){
        this.sql = sql;
        this.sc = sc;
    }

    public void MemberSystem() {
        // 회원 관리 부분으로 넘어가기
        // while/switch
        boolean run = true;
        int menu = 0;

        while (run) {
            System.out.println(color.MAGENTA_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
            System.out.println(color.WHITE_BRIGHT + " [1]모든 회원보기\t\t[2]회원정보 변경\t\t[3]회원 삭제 ");
            System.out.print(" [4]회원 예약 정보 보기" + color.RESET);
            System.out.println(color.YELLOW_BRIGHT + "\t\t\t\t\t[5]나가기" + color.RESET);
            System.out.println(color.MAGENTA_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
            System.out.print(color.WHITE_BRIGHT + "선택 >> " + color.RESET);
            menu = sc.nextInt();

            switch (menu) {
                case 1:
                    // 1. 모든 회원보기 (memberList 있음)
                    System.out.println(color.MAGENTA_BRIGHT + "《 회원 목록 》" + color.RESET);
                    sql.memberList();
                    break;
                case 2:
                    // 2. 회원정보 변경하기    // 이름 검색
                    MemberUpdate();
                    break;

                case 3:
                    // 3. 회원삭제시키기(memberDelete)    // 이름 검색
                    //sql.memberDelete();
                    System.out.print(color.WHITE_BRIGHT + "회원 아이디 입력 >> " + color.RESET);
                    tId = sc.next();

                    boolean idCheck1 = sql.idCheck(tId);    // 해당 아이디의 존재여부
                    if (idCheck1) {
                        sql.deletePayment_m(tId);   // 참조키 데이터 삭제(해당 아이디의 결제 삭제)

                        sql.memberDelete_d(tId);    // 참조키 데이터 삭제(해당 아이디의 예약 삭제)

                        sql.memberDelete(tId);      // 회원삭제

                    } else {
                        System.out.println(color.RED_BRIGHT + "존재하지 않는 아이디 입니다." + color.RESET);
                    }
                    break;
                case 4:
                    // 4. 회원이름을 검색해서 회원 예약 정보 보기
                    // 예약 경기 일정(나라,구단,경기일), 숙소(숙소 이름,위치 등), 총결제금액
                    System.out.print(color.WHITE_BRIGHT + "회원 아이디 입력 >> " + color.RESET);
                    tId = sc.next();

                    boolean idCheck2 = sql.idCheck(tId);     // 해당 아이디의 존재여부
                    if (idCheck2) {
                        System.out.println(color.MAGENTA_BRIGHT + "《 회원 예약 정보 》" + color.RESET);
                        sql.showReservationInfo(tId);
                    } else {
                        System.out.println(color.RED_BRIGHT + "존재하지 않는 아이디입니다." + color.RESET);
                    }
                    break;
                case 5:
                    run = false;
                    System.out.println(color.YELLOW_BRIGHT + "회원관리에서 나갑니다." + color.RESET);
                    break;
                default:
                    System.out.println(color.RED_BRIGHT + "잘못 입력하셨습니다." + color.RESET);
                    break;
            }

        }
    }


    // 회원 업데이트 메소드
    public void MemberUpdate() {
        System.out.print(color.WHITE_BRIGHT + "회원 아이디 입력 >> " + color.RESET);
        tId = sc.next();
        boolean idCheck3 = sql.idCheck(tId);     // 해당 아이디의 존재여부

        if (idCheck3) {
            System.out.println(color.BLUE_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗");
            System.out.println("                    [ 수정할 정보 선택 ]" + color.RESET);
            System.out.println(color.WHITE_BRIGHT + "         1.비밀번호\t2.이름\t3.연락처\t\t4.이메일" + color.RESET);
            System.out.println(color.BLUE_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
            System.out.print(color.WHITE_BRIGHT + "선택 >> " + color.RESET);
            int menu4 = sc.nextInt();

            String colName = null;
            String colValue = null;

            switch (menu4) {
                case 1:  // 비밀번호 변경
                    System.out.print(color.WHITE_BRIGHT + "변경할 비밀번호 입력 >> " + color.RESET);
                    colValue = sc.next();
                    colName = "tPw";  // 비밀번호를 변경하려는 경우

                    String pw = sql.checkMember(tId, colName);  // 현재 비밀번호 확인

                    if (pw.equals(colValue)) {
                        System.out.println(color.RED_BRIGHT + "현재 사용중인 비밀번호입니다." + color.RESET);
                    } else {
                        colName = "tPw";  // 변경할 새로운 비밀번호
                        sql.memberUpdate(tId, colName, colValue);
                    }
                    break;

                case 2:  // 이름 변경
                    System.out.print(color.WHITE_BRIGHT + "변경할 이름 입력 >> " + color.RESET);
                    colValue = sc.next();
                    colName = "tName";

                    String name = sql.checkMember(tId, colName);  // 현재 이름 확인

                    if (name.equals(colValue)) {
                        System.out.println(color.RED_BRIGHT + "현재 사용중인 이름입니다." + color.RESET);
                        return;
                    } else {
                        colName = "tName";  // 변경할 새로운 이름
                        sql.memberUpdate(tId, colName, colValue);
                    }
                    break;

                case 3:  // 연락처 변경
                    System.out.print(color.WHITE_BRIGHT + "변경할 연락처 입력 >> " + color.RESET);
                    colValue = sc.next();
                    colName = "tPhone";

                    String phone = sql.checkMember(tId, colName);  // 현재 연락처 확인

                    if (phone.equals(colValue)) {
                        System.out.println(color.RED_BRIGHT + "현재 사용중인 연락처입니다." + color.RESET);
                        return;
                    } else {
                        colName = "tPhone";  // 변경할 새로운 연락처
                        sql.memberUpdate(tId, colName, colValue);
                    }
                    break;

                case 4:  // 이메일 변경
                    System.out.print(color.WHITE_BRIGHT + "변경할 이메일 입력 >> " + color.RESET);
                    colValue = sc.next();
                    colName = "tEmail";

                    String email = sql.checkMember(tId, colName);  // 현재 이메일 확인

                    if (email.equals(colValue)) {
                        System.out.println(color.RED_BRIGHT + "현재 사용중인 이메일입니다." + color.RESET);
                        return;
                    } else {
                        colName = "tEmail";  // 변경할 새로운 이메일
                        sql.memberUpdate(tId, colName, colValue);
                    }
                    break;
                default:
                    System.out.println(color.YELLOW + "다시 입력해주세요." + color.RESET);
                    break;
            }
        }else{
            System.out.println(color.RED_BRIGHT + "존재하지 않는 아이디입니다." + color.RESET);
        }
    }
}
