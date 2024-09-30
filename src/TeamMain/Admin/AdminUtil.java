package TeamMain.Admin;

import TeamDAO.TeamSQL;
import TeamDTO.Hotel;
import TeamDTO.Schedule;

import java.util.Scanner;

import TeamMain.*;

public class AdminUtil {

    private TeamSQL sql;
    private Scanner sc;

    public AdminUtil(TeamSQL sql, Scanner sc) {
        this.sql = sql;
        this.sc = sc;
    }

    public void adminPage(){
        // 관라지 전용 메인페이지 객체 생성
        AdminMain am = new AdminMain(sql, sc);
        System.out.println(color.CYAN_BRIGHT + "관리자" + color.RESET + color.YELLOW + "로 로그인합니다. 어서오십시오.\uD83D\uDE42" + color.RESET);

        int menu2 = 0;
        boolean run2 = true;
        while (run2){
            System.out.println();
            System.out.println(color.BLACK_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━" + color.RESET + color.CYAN + "『관리자 전용』" + color.RESET + color.BLACK_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━━━━╗"  + color.RESET);
            System.out.println(color.WHITE_BRIGHT + " \t[1]경기 일정추가\t[2]관리시스템\t\t[3]호텔등록 " + color.RESET);
            System.out.println(color.WHITE_BRIGHT + " \t[4]등록된 스케쥴\t[5]리스트 보기\t[6]로그아웃 " + color.RESET);
            System.out.println(color.BLACK_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
            choice.userChoice();
            menu2 = sc.nextInt();

            switch (menu2){
                case 1:
                    // 경기 일정 추가
                    am.scheduleIsert();
                    break;
                case 2:
                    // 관리 시스템
                    // 스캐너(sc)객체가 null이 되는것을 방지하기 위해
                    // 관리시스템 객체 생성
                    AdminSystem as = new AdminSystem(sql, sc);
                    as.manageSystem();
                    break;
                case 3:
                    // 호텔 등록
                    am.hotelInsert();
                    break;
                case 4:
                    // 등록된 경기 일정 보기
                    sql.scheduleList();
                    break;
                case 5:
                    // 클럽 & 호텔 리스트 보기
                    am.manageList();
                    break;
                case 6:
                    run2 = false;
                    System.out.println(color.YELLOW + "관리자에서 로그아웃 중..."  +color.RESET);
                    break;
                default:
                    System.out.println(color.RED + "잘못 입력하셨습니다." + color.RESET);
                    break;
            }
        }

    }
}
