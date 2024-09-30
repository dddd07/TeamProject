package TeamMain.Admin;

import TeamDAO.TeamSQL;
import TeamDTO.Hotel;
import TeamDTO.Schedule;
import TeamMain.*;

import java.util.Scanner;

public class AdminMain {

    private TeamSQL sql;
    private Scanner sc;

    public AdminMain(TeamSQL sql, Scanner sc) {
        this.sql = sql;
        this.sc = sc;
    }


    // 스캐쥴 저장 메소드
    public void scheduleIsert(){
        // 나라선택 메소드(static) -- static은 인스턴스 생성 없이 메소드 호출이 가능
        choice.choiceCountry();
        // 선택 메소드
        choice.userChoice();
        int choiceCountry1 = sc.nextInt();

        if(choiceCountry1 >= 6){
            System.out.println(color.RED + "해당 나라는 지원하지 않습니다."  + color.RESET);
        } else {
            Schedule sh = new Schedule();

            // 스케쥴 PK(DNUM)에 대한 자동증가
            sh.setdNum(sql.scheduleNumber());

            // 2. 축구 구단 선택
            sql.footballClub(choiceCountry1);
            choice.userChoice();
            sh.setsNUm(sc.nextInt());

            // 3. 일정 입력
            System.out.println();
            System.out.println(color.YELLOW + "경기 일정 입력 " + color.RESET);
            System.out.println(color.WHITE_BRIGHT + "연도 : " + color.RESET + color.GREEN +  "2024" + color.RESET);
            System.out.print(color.WHITE_BRIGHT + "월 : ");
            String month = sc.next();
            System.out.print("일 : " + color.RESET);
            String day = sc.next();

            String Dday = "2024-" +  month + "-" + day;

            sh.setdDate(Dday);

            // 4. 금액 입력
            System.out.println(color.YELLOW + "해당 경기일정에 대한 금액 입력" + color.RESET);
            System.out.print(color.WHITE_BRIGHT + "입력 >>" + color.RESET);
            sh.setAmount(sc.nextInt());

            // 5. 축구클럽, 경기날, 숙박시설을 담은 일정등록
            sql.schedule(sh);
        }
    }

    
    // 호텔 저장 메소드
    public void hotelInsert(){
        // 나라선택 메소드(static)
        choice.choiceCountry();
        // 선택 메소드
        choice.userChoice();
        int choiceCountry3 = sc.nextInt();

        if(choiceCountry3 >= 6){
            System.out.println(color.RED + "해당 나라는 지원하지 않습니다." + color.RESET);
        } else {
            // 2. 지역선택
            sql.footballClub(choiceCountry3);
            // 선택 메소드
            choice.userChoice();
            int choiceClub3 = sc.nextInt();

            // 3. 호텔 상세 정보 입력
            Hotel ht = new Hotel();

            // 호텔 PK(HNUM)에 대한 자동증가
            ht.sethNum(sql.hotelNumber());

            sc.nextLine();
            System.out.print(color.WHITE_BRIGHT + "호텔 이름 : " );
            ht.sethName(sc.nextLine());

            System.out.print("숙박 가격 : ");
            ht.sethPrice(sc.nextInt());

            sc.nextLine();
            System.out.print("호텔 위치 : " + color.RESET);
            ht.sethLocation(sc.nextLine());

            sql.hotelReservation(ht, choiceClub3);
        }

    }

    
    // 등록된 축구클럽 & 호텔 리스트 보기 메소드
    public void manageList(){
        System.out.println(color.BLACK_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
        System.out.println(color.WHITE_BRIGHT + "   1.축구클럽리스트\t2.호텔리스트\t3.나가기 " + color.RESET);
        System.out.println(color.BLACK_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
        choice.userChoice();
        int tio = sc.nextInt();

        switch (tio){
            case 1:
                // 모든 축구 클럽 리스트
                sql.footballClubList();
                break;
            case 2:
                // 등록된 모든 호텔 리스트
                sql.hotel_List();
                break;
            case 3:
                System.out.println(color.YELLOW + "이전 화면으로 돌아갑니다." + color.RESET);
                break;
            default:
                System.out.println(color.RED + "잘못 입력하셨습니다." + color.RESET);
                System.out.println(color.YELLOW + "이전 화면으로 돌아갑니다." + color.RESET);
                break;
        }
    }
}
