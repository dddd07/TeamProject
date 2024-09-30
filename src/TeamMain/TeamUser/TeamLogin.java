package TeamMain.TeamUser;

import TeamDAO.TeamSQL;
import TeamDTO.Payment;
import TeamDTO.Reservation;
import TeamDTO.S_club;
import TeamMain.choice;
import TeamMain.*;

import java.util.List;
import java.util.Scanner;

public class TeamLogin {

    private TeamSQL sql;
    private Scanner sc;
    String tId;
    boolean run3;
    boolean loginLimits;

    public TeamLogin(TeamSQL sql, Scanner sc, String tId, boolean run3) {
        this.sql = sql;
        this.sc = sc;
        this.tId = tId;
        this.run3 = run3;
    }

    public boolean getRun3() {
        return run3;
    }

    public void setRun3(boolean run3) {
        this.run3 = run3;
    }

    // 경기 예매 메소드
    public void footballReservation() {
        Reservation re = new Reservation();

        S_club cu = new S_club();

        // choice.choiceCountry();     // 나라선택 메소드(static)
        // choice.userChoice();        // 선택 출력 메소드( " )
        System.out.print(color.WHITE_BRIGHT + "클럽 팀 입력 : " + color.RESET);
        cu.setClub_Name(sc.next());

        boolean checkCName = sql.checkCName(cu);

        // sql.clubName(cu.getClub_Name());

//        int choiceCountry = sc.nextInt();
//
//        if(choiceCountry == 1){
//            country.england();
//        } else if(choiceCountry == 2){
//            country.germany();
//        } else if(choiceCountry == 3){
//            country.Italy();
//        } else if(choiceCountry == 4){
//            country.spain();
//        } else if(choiceCountry == 5){
//            country.france();
//        }

        if (!checkCName) {
            System.out.println(color.RED + "검색하신 클럽은 지원하지 않습니다." + color.RESET);
        } else {
//            sql.footballClub(choiceCountry);
//            choice.userChoice();        // 선택 출력 메소드
//            int gameSchedule = sc.nextInt();

            int club = sql.clubSnum(cu);

            int dayReturn = sql.dayRe(club);

            System.out.println(color.WHITE_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
            System.out.println(color.WHITE_BRIGHT + "       선택한 클럽의 등록된 경기일정 " + color.RESET);
            System.out.println(color.WHITE_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);

            // 선택한 클럽의 경기 일정이 있나 없나 ( 있다면 if문, 없다면 else문으로 )
            boolean checkSchedule = sql.checkSchedule(club);

            if (checkSchedule) {
                // 선택한 경기에 달력으로 표시
                Calender.calender(dayReturn);
                // 선택한 클럽에 대한 경기 일정 보기
                sql.clubSchedule(club);
                choice.userChoice();        // 선택 출력 메소드
                int cs = sc.nextInt();

                System.out.print(color.WHITE_BRIGHT + "호텔예약도 같이하시겠습니까? (Y/N) >> " + color.RESET);
                String yn = sc.next();
                int ch = 0;

                if (yn.equals("Y") || yn.equals("y")) {
                    System.out.println(color.YELLOW + "선택한 클럽 인근 호텔을 선택해주세요" + color.RESET);

                    boolean checkHotel = sql.checkHotel(club);

                    if (checkHotel) {
                        // 선택한 클럽 인근 호텔 리스트 출력
                        sql.hotelList(club);
                        choice.userChoice();        // 선택 출력 메소드
                        ch = sc.nextInt();
                    } else {
                        System.out.println(color.YELLOW + "인근에 등록된 호텔이 없습니다." + color.RESET);
                        System.out.print(color.WHITE_BRIGHT + "예매을 취소하시겠습니까? (Y/N) >> " + color.RESET);
                        String yn2 = sc.next();

                        if (yn2.equals("Y") || yn2.equals("y")) {
                            System.out.println(color.RED + "예매를 취소합니다." + color.RESET);
                            return;
                        } else {
                            System.out.println(color.YELLOW + "호텔 예약 없이 예매를 진행합니다." + color.RESET);
                            sql.nullHotel();
                        }
                    }

                } else if (yn.equals("N") || yn.equals("n")) {
                    System.out.println(color.YELLOW + "호텔예약을 취소하셨습니다." + color.RESET);

                } else {
                    System.out.println(color.RED + "잘못 입력하셨습니다." + color.RESET);
                    System.out.println(color.RED + "(호텔예약이 정삭적으로 실행되지 않으셨습니다. 다시 이용해주세요)" + color.RESET);
                    return;
                }

                // sql문으로 이동해 insert 하기
                re.setrNum(sql.reservationNumber());
                re.setPayment(sql.totalAmount(cs, ch));
                System.out.println(color.WHITE_BRIGHT + "총 금액 : " + color.RESET + color.BLUE_BRIGHT + re.getPayment() + "원" + color.RESET);

                sql.reservation(tId, cs, ch, re);

            } else {
                System.out.println(color.RED + "선택한 클럽의 경기 일정이 없습니다." + color.RESET);
            }

        }

    }

    // 장바구니 메소드
    public void JangBaGuNi() {
        boolean reox1 = sql.reservation_ox(tId);

        if (reox1) {
            sql.myReservation(tId);
            System.out.println(color.GREEN_BRIGHT + "╔━━━━━━━━━━━━━━━━━⦓⟅⚽ 바라 바라 ⚽⟆⦔━━━━━━━━━━━━━━━━━╗" + color.RESET);
            System.out.println(color.GREEN_BRIGHT + "‖" + color.RESET + color.WHITE_BRIGHT + "   [1]담기취소\t  [2]결제하기\t  [3]나가기\t\t\t  " + color.RESET + color.GREEN_BRIGHT + "‖" + color.RESET);
            System.out.println(color.GREEN_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
            choice.userChoice();
            int jang = sc.nextInt();

            switch (jang) {
                case 1:
                    // 장바구니
                    System.out.print(color.WHITE_BRIGHT + "취소할 예약 선택 >> " + color.RESET);
                    int choiceRe = sc.nextInt();

                    boolean checkPayment = sql.checkPayment(choiceRe);

                    if (checkPayment) {
                        System.out.println(color.RED_BRIGHT + "결제된 예약이 있어 삭제할 수 없습니다.");
                        System.out.println("결제정보를 보고 다시 이용해주세요." + color.RESET);
                    } else {
                        sql.dReservation(choiceRe);
                    }
                    break;
                case 2:
                    System.out.print(color.WHITE_BRIGHT + "결제할 예약을 선택 >> " + color.RESET);
                    int paymentChoice = sc.nextInt();

                    List<Integer> myReNum = sql.myReNum(tId);

                    if (myReNum.contains(paymentChoice)) {
                        // paymentChoice가 myReNum 리스트에 포함되어 있을 때의 처리
                        System.out.print(color.YELLOW + "결제를 진행하시겠습니까??(Y/N) >>" + color.RESET);
                        String pyn = sc.next();

                        if (pyn.equals("Y") || pyn.equals("y")) {

                            int uP = sql.userPoint(tId);
                            int rP = sql.rePayment(paymentChoice);

                            if (uP >= rP) {
                                Payment payment = new Payment();
                                payment.setpNum(sql.paymentNumber());
                                payment.settId(tId);
                                payment.setrNum(paymentChoice);
                                payment.setHhh("결제완료");

                                sql.payment(payment);

                                int paymentAmount = sql.paymentAmount(payment);

                                sql.pointUpdate(paymentAmount, payment);

                            } else {
                                System.out.println("현재 소지하신 포인트는" + uP + "입니다.");
                                System.out.println("결제 하실 금액 : " + rP + "보다 " + (rP - uP) + "포인트 부족");
                            }

                        } else if (pyn.equals("N") || pyn.equals("n")) {
                            System.out.println(color.YELLOW_BRIGHT + "결제를 취소합니다." + color.RESET);
                        } else {
                            System.out.println(color.RED + "잘못 입력하셨습니다." + color.RESET);
                        }
                    } else {
                        // paymentChoice가 리스트에 없을 때의 처리
                        System.out.println(color.RED + "해당 예약은 현재 없습니다." + color.RESET);
                    }
                    break;
                case 3:
                    System.out.println("장바구니에서 나갑니다.");
                    break;
                default:
                    System.out.println(color.RED + "잘못입력하셨습니다." + color.RESET);
                    break;
            }
        } else {
            System.out.println(color.RED + "등록된 예약이 없습니다." + color.RESET);
        }
    }


    // 내 정보 메소드
    public void myInformation() {
        boolean run4 = true;
        while (run4) {
            System.out.println(color.CYAN + "\n【내 정보】" + color.RESET);
            sql.myInfo(tId);
            // 회원 탈퇴 기능 만들기
            System.out.println(color.GREEN_BRIGHT + "╔━━━━━━━⦓⟅⚽ 바라 바라 ⚽⟆⦔━━━━━━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
            System.out.println(color.WHITE_BRIGHT + "    1.충전하기\t2.결제정보 \t3.결제취소 " + color.RESET);
            System.out.println(color.WHITE_BRIGHT + "    4.탈퇴하기 \t5.정보보기 나가기 " + color.RESET);
            System.out.println(color.GREEN_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
            choice.userChoice();
            int myInfoMenu = sc.nextInt();

            // 회원 탈퇴 시 예약 된 정보에 제약조건이 위배되므로 예약 정보를 함께 삭제
            switch (myInfoMenu) {
                case 1:
                    // 포인트 충전
                    System.out.println(color.YELLOW + "얼마를 충전하시겠습니까?" + color.RESET);
                    System.out.print(color.WHITE_BRIGHT + "입력 >> " + color.RESET);
                    int point = sc.nextInt();

                    sql.pointCharging(point, tId);

                    break;
                case 2:
                    boolean p0 = sql.payment0(tId);

                    if (p0) {
                        // 결제한 정보 리스트
                        sql.paymentList(tId);
                    } else {
                        System.out.println(color.RED + "결제하신 내역이 없습니다." + color.RESET);
                    }

                    break;
                case 3:
                    boolean p1 = sql.payment0(tId);

                    if (p1) {
                        // 결제 취소하기
                        sql.paymentList(tId);
                        System.out.print(color.YELLOW + "취소할 결제 선택 >> " + color.RESET);
                        int deletePayment = sc.nextInt();

                        // 결제 취소 시 포인트 복원
                        int rP = sql.rePayment1(deletePayment);
                        int uP = sql.userPoint(tId);

                        sql.updateUserPoint(tId, rP + uP);
                        sql.deletePayment(deletePayment, tId);

                        break;
                    } else {
                        System.out.println(color.RED + "결제하신 내역이 없습니다." + color.RESET);
                    }
                    break;
                case 4:
                    System.out.println(color.RED + "【탈퇴 시 현재 예약된 일정도 함께 삭제됩니다.】" + color.RESET);
                    System.out.print(color.YELLOW + "정말 탈퇴하시겠습니까? (Y/N) >>" + color.RESET);
                    String ddd = sc.next();

                    if (ddd.equals("Y") || ddd.equals("y")) {
                        sql.deletePayment_m(tId);
                        // 로그인된 아이디에 있는 예약을 함께 삭제(제약조건 때문에)
                        sql.dReservation_m(tId);
                        // 회원 탈퇴
                        sql.deleteMember(tId);
                        // 탈퇴시 로그인된 메인 페이지 while문 빠져나가기
                        run3 = false;
                        run4 = false;
                        loginLimits = false;
                        setRun3(false);
                    } else {
                        System.out.println(color.RED + "탈퇴하기를 취소하셨습니다." + color.RESET);
                    }
                    break;
                case 5:
                    System.out.println(color.YELLOW + "마이페이지에서 나갑니다." + color.RESET);
                    run4 = false;
                    break;
                default:
                    System.out.println(color.RED + "잘못 입력하셨습니다." + color.RESET);
                    System.out.println(color.YELLOW + "마이페이지에서 나갑니다." + color.RESET);
                    break;
            }
        }
    }
}
