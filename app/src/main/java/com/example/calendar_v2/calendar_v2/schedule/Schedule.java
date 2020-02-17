package com.example.calendar_v2.calendar_v2.schedule;

import android.graphics.Color;

import com.example.calendar_v2.time.DateTime;

public interface Schedule {

    public String getTitle();
    public DateTime getStartDateTime();
    public DateTime getEndDateTime();
    public boolean isAllDay();
    public Color getColor();

}
