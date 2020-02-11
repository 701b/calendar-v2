package com.example.calendar_v2.schedule;

import android.graphics.Color;

import com.example.calendar_v2.time.Date;
import com.example.calendar_v2.time.DateTime;

/**
 * 앱 사용자가 추가하는 스케줄을 담당하는 클래스이다.
 * 스케줄에 대한 정보를 담고 있다.
 */
public class UserSchedule implements Schedule {

    public static int nextID = 1;

    private static final Color defaultColor = Color.valueOf(0.8f, 1f, 0.8f);

    private int id;
    private String title;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private String memo = "";
    private boolean isAllDay;
    private Color color;


    public UserSchedule(String title, DateTime startDateTime, DateTime endDateTime, int id) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.id = id;

        isAllDay = false;
        init();
    }

    public UserSchedule(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDateTime = startDate.atTime(0, 0);
        this.endDateTime = endDate.atTime(0, 0);
        this.id = nextID++;

        isAllDay = true;
        init();
    }


    private void init() {
        color = defaultColor;
    }


    /* Getter Setter Line */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public boolean isAllDay() {
        return isAllDay;
    }
}