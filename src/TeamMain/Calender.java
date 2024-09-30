package TeamMain;

import TeamMain.*;

public class Calender {
    public static final String red = "\u001B[31m";
    public static final String blue = "\u001B[34m";
    public static final String exit = "\u001B[0m";

    public static void calender(int highlightDay) {
        StringBuilder calendar = new StringBuilder();

        calendar.append("┌─────────────────────────────────────────┐\n");
        calendar.append("│               2024 - 09                 │\n");
        calendar.append("├─────┬─────┬─────┬─────┬─────┬─────┬─────┤\n");
        calendar.append("│ " + red + "SUN" + exit + " │ MON │ TUE │ WED │ THU │ FRI │ " + blue + "SAT" + exit + " │\n");
        calendar.append("├─────┼─────┼─────┼─────┼─────┼─────┼─────┤\n");

        int startDay = 1; // 월의 첫날 (SUN이 1일이라고 가정)
        int daysInMonth = 30; // 9월의 일수 (예: 30일)

        for (int week = 0; week < 5; week++) {
            for (int day = 1; day <= 7; day++) {
                int currentDay = week * 7 + day - startDay + 1;

                if (currentDay > 0 && currentDay <= daysInMonth) {
                    if (highlightDay == currentDay) {
                        calendar.append(" │ " + color.GREEN_BRIGHT + String.format("%2d", currentDay) + exit + " ");
                    } else {
                        // 요일에 따라 색상을 변경합니다.
                        if (day == 1) {
                            calendar.append("│ " + red + String.format("%2d", currentDay) + exit + " ");
                        } else if (day == 7) {
                            calendar.append(" │ " + blue + String.format("%2d", currentDay) + exit + " ");
                        } else {
                            calendar.append(" │ " + String.format("%2d", currentDay) + " ");
                        }
                    }
                } else {
                    calendar.append(" │    ");
                }
            }
            calendar.append("│\n");
            if (week < 4) {
                calendar.append("├─────┼─────┼─────┼─────┼─────┼─────┼─────┤\n");
            }
        }

        calendar.append("└─────┴─────┴─────┴─────┴─────┴─────┴─────┘\n");

        System.out.print(calendar.toString());
    }
}


