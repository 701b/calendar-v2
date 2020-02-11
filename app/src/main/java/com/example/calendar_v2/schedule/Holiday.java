package com.example.calendar_v2.schedule;

import android.graphics.Color;

import com.example.calendar_v2.time.Date;
import com.example.calendar_v2.time.DateTime;

/**
 * 공휴일을 담당하는 클래스이다. 공휴일에 관한 정보를 담고 있다.
 */
public class Holiday implements Schedule {

    private static final Color color = Color.valueOf(1f, 0.8f, 0.8f);

    private String title;
    private Date date;


    public Holiday(String title, Date date) {
        this.title = title;
        this.date = date;
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public DateTime getStartDateTime() {
        return date.atTime(0, 0);
    }

    @Override
    public DateTime getEndDateTime() {
        return date.atTime(0, 0);
    }

    @Override
    public boolean isAllDay() {
        return true;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public Date getDate() {
        return date;
    }
}
