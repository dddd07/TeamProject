package TeamMain.Admin;

import TeamDAO.TeamSQL;
import TeamMain.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class AdminSystem {

    private TeamSQL sql;
    private Scanner sc;
    boolean run3 = true;

    public AdminSystem(TeamSQL sql, Scanner sc) {
        this.sql = sql;
        this.sc = sc;
    }


    public void manageSystem() {
        System.out.println(color.YELLOW + "관리 시스템으로 들어갑니다.\uD83E\uDD2B" + color.RESET);
        int menu3 = 0;

        while (run3) {
            System.out.println();
            System.out.println(color.BLACK_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━" + color.RESET + color.CYAN + "『관리 시스템』" + color.RESET + color.BLACK_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
            System.out.println(color.WHITE_BRIGHT + " \t[1]경기 일정변경\t[2]경기 일정삭제\t[3]호텔삭제 ");
            System.out.println(" \t[4]현재 스케쥴\t[5]회원관리\t\t[6]나가기 " + color.RESET);
            System.out.println(color.BLACK_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
            choice.userChoice();
            menu3 = sc.nextInt();

            switch (menu3) {
                case 1:
                    boolean checkSchedule1 = sql.schedule0();

                    if (checkSchedule1) {

                        // 등록된 경기 일정 변경(날짜변경)
                        System.out.println(color.BLACK_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
                        System.out.println(color.WHITE_BRIGHT + "        현재 등록된 경기일정 " + color.RESET);
                        System.out.println(color.BLACK_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
                        // 경기 날짜, 클럽, 금액
                        sql.scheduleList();

                        choice.Jog();// (사실 이럴 필요는 없는 코드임)

                        System.out.print(color.YELLOW + "어느 일정을 변경하시겠습니까? >> ");
                        int us = sc.nextInt();

                        System.out.println("변경할 날짜를 입력해주세요" + color.RESET);
                        System.out.println(color.WHITE_BRIGHT + "연도 : " + color.RESET + color.GREEN + "2024" + color.RESET);
                        System.out.println(color.WHITE_BRIGHT + "월 : " + color.RESET + color.GREEN + "09" + color.RESET);
                        System.out.print(color.WHITE_BRIGHT + "일 : " + color.RESET);
                        String day = sc.next();

                        String colDate = "2024-09-" + day;

                        // 경기 일정 변경
                        sql.changeSchedule(us, colDate);
                    } else {
                        System.out.println(color.RED + "등록된 스케쥴이 없습니다." + color.RESET);
                    }

                    break;
                case 2:
                    boolean checkSchedule2 = sql.schedule0();

                    if (checkSchedule2) {

                        System.out.println(color.BLACK_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗" + color.RESET);
                        System.out.println(color.WHITE_BRIGHT + "        현재 등록된 경기일정 " + color.RESET);
                        System.out.println(color.BLACK_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
                        // 경기 날짜, 클럽, 금액
                        sql.scheduleList();

                        choice.Jog(); // 이하 동문

                        System.out.print(color.WHITE_BRIGHT + "어느 일정을 삭제하시겠습니까? >> " + color.RESET);
                        int ds = sc.nextInt();

                        boolean checkPayment = sql.checkPayment(ds);

                        boolean checkReservation = sql.checkReservation(ds);


                        if (!checkPayment && !checkReservation) {
                            // 결제도 없고 예약도 없을 때
                            System.out.print("정말로 경기 일정을 삭제하시겠습니까? (Y/N) >> ");
                            String yn = sc.next();

                            if (yn.equals("Y") || yn.equals("y")) {
                                // 경기 일정 삭제
                                sql.deleteSchedule(ds);
                            } else {
                                System.out.println("삭제 취소");
                            }
                        } else {
                            // 결제가 있는 경우
                            if (checkPayment) {
                                System.out.println("현재 결재완료된 예약이 있습니다.");
                                System.out.print("등록된 결제를 삭제하고 예약과 일정을 삭제하시겠습니까? (Y/N) >> ");
                                String yn2 = sc.next();

                                if (yn2.equals("Y") || yn2.equals("y")) {

                                    // int getPnum = sql.getPnum(ds);
                                    int getPnum = sql.getPnum(ds);
                                    // System.out.println(getRnum);
                                    sql.deletePayment_P(getPnum);

                                    // int getRnum = sql.getRnum(ds);
                                    sql.dReservation_D(ds);

                                    sql.deleteSchedule(ds);


                                } else {
                                    System.out.println("삭제 취소");
                                }
                                return; // 결제 처리 후 더 이상 진행하지 않음
                            }

                            // 예약만 있는 경우
                            if (checkReservation) {
                                System.out.println("현재 등록된 예약이 있습니다.");
                                System.out.print("등록된 예약을 삭제하고 경기 일정을 삭제하시겠습니까? (Y/N) >> ");
                                String yn2 = sc.next();

                                if (yn2.equals("Y") || yn2.equals("y")) {
                                    // int getPnum = sql.getPnum(ds);
                                    // int getDnum = sql.getDnum(ds);
                                    sql.dReservation_D(ds);

                                    sql.deleteSchedule(ds);
                                } else {
                                    System.out.println("삭제 취소");
                                }
                            }
                        }
                    } else {
                        System.out.println(color.RED + "등록된 스케쥴이 없습니다." + color.RESET);
                    }

                    break;
                case 3:
                    // 호텔 삭제
                    // 나라선택 메소드(static)
                    choice.choiceCountry();
                    // 선택 메소드
                    choice.userChoice();
                    int choiceCountry2 = sc.nextInt();

                    if (choiceCountry2 >= 6) {
                        System.out.println(color.RED + "해당 나라는 지원하지 않습니다." + color.RESET);
                    } else {
                        sql.footballClub(choiceCountry2);
                        System.out.print(color.WHITE_BRIGHT + "지역 선택 >> " + color.RESET);
                        int choiceClub2 = sc.nextInt();

                        boolean checkHotel = sql.checkHotel(choiceClub2);

                        if (checkHotel) {
                            sql.hotelList(choiceClub2);
                            System.out.print(color.WHITE_BRIGHT + "삭제할 호텔 선택 >> " + color.RESET);
                            int deleteHotel = sc.nextInt();

                            System.out.println(color.RED + "※ 현재 등록된 예약일정에서 선택한 호텔이 포함되있을 경우");
                            System.out.println("  예약명단 또한 같이 삭제됩니다." + color.RESET);
                            System.out.print(color.YELLOW + "정말로 삭제하시겠습니까? (Y/N) >> " + color.RESET);
                            String ddd = sc.next();

                            if (ddd.equals("Y") || ddd.equals("y")) {

                                int p0 = sql.getHnum(deleteHotel);
                                sql.deletePayment_P(p0);

                                // 무결성 제약조건을 위배하지 않기 위한 예약 테이블에서 HNUM을 삭제
                                sql.dReservation_h(deleteHotel);
                                // 등록된 호텔 삭제
                                sql.deleteHotel(deleteHotel);
                            } else {
                                System.out.println(color.YELLOW + "삭제하기를 취소하셨습니다." + color.RESET);
                            }
                        } else {
                            System.out.println(color.RED + "해당 클럽 인근에는 삭제할 호텔이 없습니다." + color.RESET);
                        }

                    }

                    break;
                case 4:
                    // 관리자 페이지와 동일한, 관리시스템에서도 현재 스케쥴을 보기 위함
                    sql.scheduleList();
                    break;
                case 5:
                    // (임희진님)회원 관리 부분으로 넘어가기
                    AdminMember am = new AdminMember(sql, sc);
                    am.MemberSystem();
                    break;
                case 6:
                    run3 = false;
                    System.out.println(color.YELLOW + "관리시스템에서 나갑니다." + color.RESET);
                    break;
                default:
                    System.out.println(color.RED + "잘못 입력하셨습니다." + color.RESET);
                    break;
            }
        }
    }
}
