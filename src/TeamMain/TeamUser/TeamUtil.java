package TeamMain.TeamUser;

import TeamDAO.TeamSQL;

import TeamDTO.Member;

import TeamDTO.Payment;
import TeamDTO.Reservation;
import TeamMain.Admin.AdminUtil;

import java.util.List;
import java.util.Scanner;

import TeamMain.*;

public class TeamUtil {

    private TeamSQL sql;
    private Scanner sc;
    boolean run3 = true;
    boolean loginLimits = true;

    public TeamUtil(TeamSQL sql, Scanner sc) {
        this.sql = sql;
        this.sc = sc;
    }

    // 회원가입 메소드 ( 채건님 )
    public void join() {
        // 회원가입
        Member mb = new Member();

        System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
        System.out.println(color.YELLOW + "아이디는 3자 이상 10자 이하로 입력해주세요" + color.RESET);
        System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
        System.out.print(color.WHITE_BRIGHT + "아 이 디 >> " + color.RESET);
        String tId = sc.next();

        // 아이디 길이 제한
        if (tId.length() < 3 || tId.length() > 10) {
            System.out.println(color.RED + "3자 이상 10자 이하 입력하여야 합니다." + color.RESET);
            return;
        }


        boolean idCheck = sql.idCheck(tId);
        if (!idCheck) {
            System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
            System.out.println(color.YELLOW + "비밀번호는 5자이상, 특수문자포함해주세요." + color.RESET);
            System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
            System.out.print(color.WHITE_BRIGHT + "비밀번호 >> ");
            String tpw = sc.next();
            // 비밀번호 조건 : 최소 5자 이상, 특수문자 포함
            if (tpw.length() >= 5 && tpw.matches(".*[!@#$%^&*()].*")) {
                mb.settPw(tpw);

                System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
                System.out.println("이름을 입력해주세요.");
                System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
                System.out.print("성    함 >> ");
                mb.settName(sc.next());

                System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
                System.out.println("나이를 입력해주세요.");
                System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
                System.out.print("나    이 >> " + color.RESET);
                mb.settAge(sc.nextInt());
                // 나이 제한
                if (mb.gettAge() < 18) {
                    System.out.println(color.RED + "만 18세 이상만 가입이 가능합니다." + color.RESET);
                    return;
                }

                System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
                System.out.println("연락처를 입력해주세요.");
                System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
                System.out.print(color.WHITE_BRIGHT + "연 락 처 >> ");
                mb.settPhone(sc.next());

                System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
                System.out.println("이메일를 입력해주세요.");
                System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
                System.out.print("이 메 일 >> " + color.RESET);
                mb.settEmail(sc.next());

                // 보안 생성코드 : 실제로는 이렇게 보안코드를 출력하게 하면 안된다,
                String tCode = choice.ramdomNumber();
                System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);
                System.out.println(color.YELLOW + "보안코드 : 【 " + color.RESET + color.MAGENTA_BRIGHT + tCode + color.RESET + color.YELLOW + " 】" + color.RESET);
                System.out.println(color.WHITE_BRIGHT + "—————————————————————————————————————" + color.RESET);

                // 사용자 입력한 보안 코드 확인
                System.out.print(color.WHITE_BRIGHT + "보안코드 입력 >> " + color.RESET);
                String userInputCode = sc.next();

                if (tCode.equals(userInputCode)) {

                    mb.settId(tId);

                    sql.join(mb);

                } else {
                    System.out.println(color.RED + "보안코드가 일치하지 않습니다." + color.RESET);
                }
            } else {
                System.out.println(color.RED + "비밀번호는 최소 5자이상, 특수문자포함해야 합니다." + color.RESET);
            }
        } else {
            System.out.println(color.RED + "해당 아이디는 이미 존재합니다." + color.RESET);
        }
    }

    // 로그인 메소드
    public void login() {
        System.out.println("╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗");
        System.out.println("        로그인 [ ID, PW 입력 ] ");
        System.out.println("╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝");

        int attempts = 0;

        loginLimits = true;

        TeamLogin tl;

        while (loginLimits) {
            System.out.print("아 이 디 : ");
            String tId = sc.next();

            System.out.print("비밀번호 : ");
            String tPw = sc.next();

            String check = sql.login(tId, tPw);

            if (check != null) {
                if (check.equals("admin")) {
                    // 관리자로 로그인 ( adminUtil Class )
                    // 위 로그인 실행 후 ID = 'admin'과 pw를 받아서 adminUtil로 이동
                    AdminUtil aUtil = new AdminUtil(sql, sc);
                    aUtil.adminPage();
                    loginLimits = false;
                } else {
                    tl = new TeamLogin(sql, sc, tId, run3);

                    // 일반 사용자 로그인시 보여지는 환영문구
                    sql.memberName(tId);

                    int menu3 = 0;

                    System.out.println();

                    choice.wefont();
                    System.out.println();
                    System.out.println();

                    while (tl.getRun3()) {
                        System.out.println();
                        System.out.println(color.GREEN_BRIGHT + "╔━━━━━━━━━━━━⦓⟅⚽ 바라 바라 ⚽⟆⦔━━━━━━━━━━━━╗" + color.RESET);
                        System.out.println(color.GREEN_BRIGHT + "‖" + color.RESET + color.WHITE_BRIGHT + "   [1]경기일정\t  [2]경기예매\t [3]장바구니\t " + color.RESET + color.GREEN_BRIGHT + "  ‖" + color.RESET);
                        System.out.println(color.GREEN_BRIGHT + "‖" + color.RESET + color.WHITE_BRIGHT + "   \t\t[4]내정보 \t\t[5]로그아웃\t   " + color.RESET + color.GREEN_BRIGHT + "‖" + color.RESET);
                        System.out.println(color.GREEN_BRIGHT + "╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
                        choice.userChoice();
                        menu3 = sc.nextInt();

                        switch (menu3) {
                            case 1:
                                // 경기 일정 리스트 ( SQL )
                                // Calender.calender();
                                boolean s0 = sql.schedule0();

                                if (s0) {
                                    sql.scheduleList();
                                } else {
                                    System.out.println(color.RED + "등록된 스케쥴이 없습니다." + color.RESET);
                                }
                                break;
                            case 2:
                                // 경기 예매 ( 메소드 )
                                tl.footballReservation();
                                break;
                            case 3:
                                // 장바구니
                                tl.JangBaGuNi();
                                break;
                            case 4:
                                // 내 정보
                                tl.myInformation();
                                loginLimits = false;
                                break;
                            case 5:
                                tl.setRun3(false);
                                loginLimits = false;
                                System.out.println(color.YELLOW + "로그아웃 중..." + color.RESET);
                                break;
                            default:
                                System.out.println(color.RED + "잘못 입력하셨습니다." + color.RESET);
                                break;
                        }
                    }
                }
            } else {
                attempts++;
                System.out.println(color.RED + "로그인   " + attempts + " 회 실패하셨습니다." + color.RESET);
                System.out.println(color.RED + "아이디/비밀번호를 다시 확인해주세요." + color.RESET);
                if (attempts >= 5) {
                    System.out.println(color.RED + "로그인 시도횟수가 초과하였습니다." + color.RESET);
                    System.out.println(color.WHITE_BRIGHT + "이전화면으로 돌아갑니다." + color.RESET);
                    loginLimits = false;
                    break;
                }
            }
        }
    }


    // (채건님)아이디, 비밀번호 찾는 메소드
    public void find() {
        int menu5 = 0;
        boolean run5 = true;
        while (run5) {
            System.out.println(color.WHITE_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗");
            System.out.println(" [1]ID 찾기\t[2]PW 찾기\t[3]취소 ");
            System.out.println("╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);

            choice.userChoice();
            menu5 = sc.nextInt();
            switch (menu5) {
                case 1:
                    // ID 찾기
                    System.out.println(color.WHITE_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━╗");
                    System.out.println("     내  ID  찾 기   ");
                    System.out.println("╚━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
                    System.out.print(color.WHITE_BRIGHT + "연락처 입력 >> " + color.RESET);
                    Member mb = new Member();
                    mb.settPhone(sc.next());

                    // 보안 생성코드
                    String tCode = choice.ramdomNumber();
                    System.out.println(color.YELLOW + "보안코드 : 【 " + color.RESET + color.MAGENTA_BRIGHT + tCode + color.RESET + color.YELLOW + " 】" + color.RESET);

                    // 사용자 입력한 보안 코드 확인
                    System.out.print(color.WHITE_BRIGHT + "보안코드 입력 >> " + color.RESET);
                    String userInputCode = sc.next();

                    boolean checkPhone = sql.findId(mb);

                    if (checkPhone) {
                        if (tCode.equals(userInputCode)) {
                            System.out.print(color.YELLOW + "내 아이디 :  " + color.RESET);
                            sql.selectID(mb);

                        } else {
                            System.out.println(color.RED + "보안코드가 일치하지 않습니다." + color.RESET);
                        }
                    } else {
                        System.out.println(color.RED + "연락처가 일치하지 않습니다." + color.RESET);
                    }
                    break;
                case 2:
                    // PW 찾기
                    mb = new Member();
                    int attempts = 0;
                    boolean findLimits = false;

                    while (attempts < 5 && !findLimits) {
                        System.out.println(color.WHITE_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━╗");
                        System.out.println("     내  PW  찾 기   ");
                        System.out.println("╚━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);
                        System.out.print(color.WHITE_BRIGHT + "아이디 입력 >> ");
                        mb.settId(sc.next());
                        System.out.print("이메일 입력 >> " + color.RESET);
                        mb.settEmail(sc.next());

                        // 보안 생성코드
                        tCode = choice.ramdomNumber();
                        System.out.println(color.YELLOW + "보안코드 : 【 " + color.RESET + color.MAGENTA_BRIGHT + tCode + color.RESET + color.YELLOW + " 】" + color.RESET);

                        // 사용자 입력한 보안 코드 확인
                        System.out.print(color.WHITE_BRIGHT + "보안코드 입력 >> " + color.RESET);
                        userInputCode = sc.next();

                        boolean checkEmail = sql.findPW(mb);
                        boolean checkTId = sql.findPW_ID(mb);

                        if (checkTId) {
                            if (checkEmail) {
                                if (tCode.equals(userInputCode)) {
                                    System.out.print(color.YELLOW + "내 비밀번호 : " + color.RESET);
                                    sql.selectPW(mb);
                                    // 비밀 번호 찾으면 종료
                                    findLimits = true;
                                    break;

                                } else {
                                    System.out.println(color.RED + "보안코드가 일치하지 않습니다." + color.RESET);
                                }
                            } else {
                                System.out.println(color.RED + "이메일이 일치하지 않습니다." + color.RESET);
                            }
                        } else {
                            System.out.println(color.RED + "아이디가 일치하지 않습니다." + color.RESET);
                        }

                        // 시도횟수 증가
                        attempts++;
                        System.out.println(color.RED + "비밀번호 찾기를    " + attempts + " 회 실패하셨습니다." + color.RESET);

                        // 비밀번호 찾기 5회 실패시 출력
                        if (attempts >= 5) {
                            System.out.println(color.RED + "비밀번호 찾기를 위한 시도 횟수가 초과하셨습니다." + color.RESET);
                            System.out.println(color.YELLOW + "비밀번호를 변경하시겠습니까?(예: y /아니요: n)" + color.RESET);
                            String changePw = sc.next();

                            boolean reChangePw = false;

                            while (!reChangePw) {
                                int attempts2 = 0;
                                if (changePw.equals("y") || changePw.equals("Y")) {
                                    do {
                                        System.out.print(color.YELLOW + "변경할 비밀번호의 아이디를 입력해주세요. >> " + color.RESET);
                                        mb.settId(sc.next());

                                        System.out.print(color.YELLOW + "새 비밀번호 입력 >> " + color.RESET);
                                        String newPw = sc.next();
                                        // 새로운 비밀번호 설정
                                        mb.settPw(newPw);

                                        attempts2++;
                                        String currentPassword = sql.getCurrentPassword(mb);
                                        if (currentPassword != null && currentPassword.equals(mb.gettPw())) {
                                            System.out.println(color.RED + "현재의 비밀번호와 동일합니다. 다른 비밀번호를 입력해주세요." + color.RESET);

                                            System.out.println(color.RED + "비밀번호 변경을 위한 시도가    " + attempts2 + "회 실패하셨습니다." + color.RESET);

                                        } else {
                                            boolean updateSusccess = sql.updatePassword(mb);

                                            if (updateSusccess) {
                                                System.out.println(color.YELLOW + "비밀번호 변경에 성공하셨습니다." + color.RESET);
                                                // 비밀번호 변경 성공시 종료
                                                reChangePw = true;
                                                findLimits = true;
                                                break;
                                            } else {
                                                System.out.println(color.RED + "비밀번호 변경에 실패하셨습니다. 다시입력해주세요." + color.RESET);
                                            }
                                            System.out.println(color.RED + "비밀번호 변경을 위한 시도가    " + attempts2 + "회 실패하셨습니다." + color.RESET);

                                            // 비밀번호 변경 5회 실패시 출력
                                            if (attempts2 >= 4) {
                                                System.out.println(color.RED + "비밀번호 변경을 위한 시도 횟수가 초과하셨습니다." + color.RESET);
                                                reChangePw = true;
                                                findLimits = true;
                                                run5 = false;
                                                System.out.println(color.YELLOW + "첫 화면으로 돌아갑니다." + color.RESET);
                                                break;
                                            }
                                        }
                                    } while (!reChangePw);
                                } else if (changePw.equals("n") || changePw.equals("N")) {
                                    System.out.println(color.RED + "비밀번호 변경에 취소하셨습니다." + color.RESET);
                                    break;
                                } else {
                                    System.out.println(color.RED + "잘못 입력하셨습니다." + color.RESET);
                                    break;
                                }
                            }
                        }
                    }//while end
                    break;
                case 3:
                    // 종료
                    run5 = false;
                    System.out.println("ID/PW 찾기를 취소하였습니다.");
                    break;
                default:
                    System.out.println("잘못 입력하셨습니다.");
                    break;
            }
        }
    }
}
