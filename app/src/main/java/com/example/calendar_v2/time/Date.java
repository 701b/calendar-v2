package com.example.calendar_v2.time;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Date {

    private int year;
    private int month;
    private int dayOfMonth;

    public Date(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public Date(LocalDate localDate) {
        year = localDate.getYear();
        month = localDate.getMonthValue();
        dayOfMonth = localDate.getDayOfMonth();
    }


    /**
     * 두 날짜 사이의 날 수를 계산하여 반환한다.
     * 예) 2019.01.20, 2019.01.01 -> 20일
     * 예) 2019.01.01, 2019.01.01 -> 1일
     *
     * @param date1 날짜1
     * @param date2 날짜2
     * @return 두 날짜 사이의 날의 수
     */
    public static int getNumberOfDayBetween(Date date1, Date date2) {
        return (int) ChronoUnit.DAYS.between(date1.toLocalDate(), date2.toLocalDate()) + 1;
    }

    public static int getNumberOfWeekBetween(Date date1, Date date2) {
        return (int) ChronoUnit.WEEKS.between(date1.toLocalDate(), date2.toLocalDate()) + 1;
    }


    /**
     * 다른 Date와 비교하여 큰지 작은지를 int로 반환한다.
     *
     * @param other 비교할 Date
     * @return other보다 나중이라면 0보다 큰 값을, other보다 이전이라면 0보다 작은 값을, 같다면 0
     */
    public int compareTo(Date other) {
        int cmp = year - other.year;

        if (cmp == 0) {
            cmp = month - other.month;

            if (cmp == 0) {
                cmp = dayOfMonth - other.dayOfMonth;
            }
        }

        return cmp;
    }

    /**
     * LocalDate로 변환하여 반환한다.
     *
     * @return 변환된 LocalDate
     */
    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * hour, minute을 인자로 받아 DateTime으로 변환하여 반환한다.
     *
     * @param hour 시간
     * @param minute 분
     * @return DateTime
     */
    public DateTime atTime(int hour, int minute) {
        return new DateTime(year, month, dayOfMonth, hour, minute);
    }

    public String toString() {
        String dateStr;
        String[] dayOfWeekStrs = {"월", "화", "수", "목", "금", "토", "일"};

        dateStr = month + "월 "
                + dayOfMonth + "일 "
                + "(" + dayOfWeekStrs[toLocalDate().getDayOfWeek().getValue() - 1] + ")";

        return dateStr;
    }

    @Override
    @NonNull
    public Date clone() throws CloneNotSupportedException {
        return (Date) super.clone();
    }


    /* Getter Setter Line*/
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }
}
