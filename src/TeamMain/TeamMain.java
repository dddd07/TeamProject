package TeamMain;

import TeamDAO.TeamSQL;
import TeamMain.TeamUser.TeamUtil;
import java.util.Scanner;
import TeamMain.*;

public class TeamMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        TeamSQL sql = new TeamSQL();

        TeamUtil util = new TeamUtil(sql, sc);

        sql.connect();

        int menu1 = 0;
        boolean run1 = true;

        while(run1){
            System.out.println(color.WHITE_BRIGHT + "╔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╗");
            System.out.println("\t[1]회원가입\t\t[2]로그인");
            System.out.println("\t[3]ID/PW 찾기\t[4]종료");
            System.out.println("╚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╝" + color.RESET);

            choice.userChoice();
            menu1 = sc.nextInt();
            switch(menu1){
                case 1:
                    // 회원가입
                    util.join();
                    break;
                case 2:
                    // 로그인
                    util.login();
                    break;
                case 3:
                    // ID 찾기
                    util.find();
                    break;
                case 4:
                    // 종료
                    run1 = false;
                    sql.close();
                    sc.close();
                    System.out.println(color.YELLOW + "프로그램을 종료합니다." + color.RESET);
                    break;
                default:
                    System.out.println(color.RED + "잘못 입력하셨습니다." + color.RESET);
                    break;
            }
        }

    }
}
