package com.example.calendar_v2.calendar_v2.time;

import androidx.annotation.NonNull;




import java.time.LocalDateTime;

public class DateTime implements Cloneable {

    private int year;
    private int month;
    private int dayOfMonth;
    private int hour;
    private int minute;

    public DateTime(int year, int month, int dayOfMonth, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hour = hour;
        this.minute = minute;
    }

    public DateTime(LocalDateTime localDateTime) {
        year = localDateTime.getYear();
        month = localDateTime.getMonthValue();
        dayOfMonth = localDateTime.getDayOfMonth();
        hour = localDateTime.getHour();
        minute = localDateTime.getMinute();
    }

    /**
     * 다른 Date와 비교하여 큰지 작은지를 int로 반환한다.
     *
     * @param other 비교할 Date
     * @return other보다 나중이라면 0보다 큰 값을, other보다 이전이라면 0보다 작은 값을, 같다면 0
     */
    public int compareTo(DateTime other) {
        int cmp = year - other.year;

        if (cmp == 0) {
            cmp = month - other.month;

            if (cmp == 0) {
                cmp = dayOfMonth - other.dayOfMonth;

                if (cmp == 0) {
                    cmp = hour - other.hour;

                    if (cmp == 0) {
                        cmp = minute - other.minute;
                    }
                }
            }
        }

        return cmp;
    }

    /**
     * LocalDateTime로 변환하여 반환한다.
     *
     * @return 변환된 LocalDateTime
     */
    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute, 0);
    }

    /**
     * Date로 변환하여 반환한다.
     *
     * @return 변환된 Date
     */
    public Date toDate() {
        return new Date(year, month, dayOfMonth);
    }

    /**
     * 날짜 정보를 문자열로 변환하여 반환한다.
     * 형식은 "y년 m월 d일 (dayOfWeek)
     *
     * @return 날짜 정보가 담긴 문자열
     */
    public String toStringOnlyDate() {
        String dateStr;
        String[] dayOfWeekStrs = {"월", "화", "수", "목", "금", "토", "일"};

        dateStr = year + "년 "
                + month + "월 "
                + dayOfMonth + "일 "
                + "(" + dayOfWeekStrs[toLocalDateTime().getDayOfWeek().getValue() - 1] + ")";

        return dateStr;
    }

    /**
     * 시간 정보를 문자열로 변환하여 반환한다.
     * 형식은 "오전/오후 hh:mm"
     *
     * @return 시간 정보가 담긴 문자열
     */
    public String toStringOnlyTime() {
        String timeStr;
        String hourString;
        String minuteString;

        if (hour == 0) {
            hourString = "오전 12";
        } else if (hour > 0 && hour < 12) {
            hourString = "오전 " + hour;
        } else if (hour == 12) {
            hourString = "오후 12";
        } else {
            hourString = "오후 " + hour;
        }

        if (minute == 0) {
            minuteString = "00";
        } else if (minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = String.valueOf(minute);
        }

        timeStr = hourString + ":" + minuteString;

        return timeStr;
    }

    /**
     * 날짜, 시간 정보를 문자열로 변환하여 반환한다.
     * 형식은 "y년 m월 d일 (dayOfWeek) 오전/오후 hh:mm"
     *
     * @return 날짜, 시간 정보가 담긴 문자열
     */
    public String toString() {
        String dateTimeStr;
        LocalDateTime localDateTime = this.toLocalDateTime();
        String dayOfWeekString = "";
        String hourString = "";
        String minuteString = "";
        String[] dayOfWeekStrs = {"월", "화", "수", "목", "금", "토", "일"};

        dayOfWeekString = dayOfWeekStrs[toLocalDateTime().getDayOfWeek().getValue() - 1];

        if (hour == 0) {
            hourString = "오전 12";
        } else if (hour > 0 && hour < 12) {
            hourString = "오전 " + hour;
        } else if (hour == 12) {
            hourString = "오후 12";
        } else {
            hourString = "오후 " + hour;
        }

        if (minute == 0) {
            minuteString = "00";
        } else if (minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = String.valueOf(minute);
        }

        dateTimeStr = year + "년 "
                    + month + "월 "
                    + dayOfMonth + "일 "
                    + "(" + dayOfWeekString + ") "
                    + hourString + ":" + minuteString;

        return dateTimeStr;
    }

    @Override
    @NonNull
    public DateTime clone() throws CloneNotSupportedException {
        return (DateTime) super.clone();
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

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}