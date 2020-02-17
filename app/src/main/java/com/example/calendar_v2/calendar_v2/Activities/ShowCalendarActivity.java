package com.example.calendar_v2.calendar_v2.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar_v2.Adapters.CalendarViewAdapter;
import com.example.calendar_v2.R;
import com.example.calendar_v2.Views.MyCalendarView;

public class ShowCalendarActivity extends AppCompatActivity {
    private MyCalendarView myCalendarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_calendar_layout);
        myCalendarView = (MyCalendarView)findViewById(R.id.calendar_view);
        myCalendarView.setAdapter(new CalendarViewAdapter());
    }
}
