package TeamMain;

import TeamMain.*;

public class choice {
    
    // 나라 선택 메소드
    public static void choiceCountry(){
        int count = 56; // ━ 기호의 총 개수
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= count; i++) {
            if (i % 2 != 0) { // 홀수일 경우
                sb.append(color.BLUE).append("━");
            } else { // 짝수일 경우
                sb.append(color.WHITE_BRIGHT).append("━");
            }
        }

        // 출력
        System.out.println(color.WHITE_BRIGHT + "╔" + sb.toString() + "╗" + color.RESET);
        System.out.println(color.RED_BRIGHT + "                    〖  리그 선택  〗" + color.RESET);
        System.out.println(color.WHITE_BRIGHT + "       1.영국\t2.독일\t3.이탈리아\t4.스페인\t  5.프랑스" + color.RESET);
        System.out.println(color.WHITE_BRIGHT + "╚" + sb.toString() + "╝" + color.RESET);
    }

    // 선택 출력문( "선택 >>" ) 메소드
    public static void userChoice (){
        System.out.print(color.WHITE_BRIGHT + "선택 >> " + color.RESET);
    }

    // 이건 그냥 줄 메소드인데 for문이라 좀 더러워서 그냥 여기다가 둠
    public static void Jog(){
        for (int i = 1; i <= 40; i++) {
            if (i % 2 != 0) {
                System.out.print(color.BLACK_BRIGHT + "▄" + color.RESET);
            } else {
                System.out.print(color.WHITE + "▀" + color.RESET);
            }
        }
        System.out.println();
    }

    public static String ramdomNumber(){
        // 랜덤 보안 생성 메소드
            String gscode = null;
            gscode = "";
            for (int i = 1; i <= 4; i++) {
                gscode += (int) (Math.random() * 9);
            }
            return gscode;
    }

    // 텍스트를 한 글자씩 천천히 출력하는 메서드
    public static void slowPrint(String message, long millisPerChar) {
        for (char c : message.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(millisPerChar); // 글자 사이에 지연 시간 설정
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void wefont() {
        String sql = color.CYAN_BRIGHT + "██████   █████  ██████   █████      ██████   █████  ██████   █████      ███████ ██████  \n" +
                "██   ██ ██   ██ ██   ██ ██   ██     ██   ██ ██   ██ ██   ██ ██   ██     ██      ██   ██ \n" +
                "██████  ███████ ██████  ███████     ██████  ███████ ██████  ███████     █████   ██████  \n" +
                "██   ██ ██   ██ ██   ██ ██   ██     ██   ██ ██   ██ ██   ██ ██   ██     ██      ██   ██ \n" +
                "██████  ██   ██ ██   ██ ██   ██     ██████  ██   ██ ██   ██ ██   ██     ██      ██████" + color.RESET;

        String slq = color.WHITE_BRIGHT + "     █    █    █ " + color.RESET;


        slowPrint(sql, 3);
        slowPrint(slq, 70);


    }




}
